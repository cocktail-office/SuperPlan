/*
 * Copyright COCKTAIL (www.cocktail.org), 1995, 2011 This software
 * is governed by the CeCILL license under French law and abiding by the
 * rules of distribution of free software. You can use, modify and/or
 * redistribute the software under the terms of the CeCILL license as
 * circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * As a counterpart to the access to the source code and rights to copy, modify
 * and redistribute granted by the license, users are provided only with a
 * limited warranty and the software's author, the holder of the economic
 * rights, and the successive licensors have only limited liability. In this
 * respect, the user's attention is drawn to the risks associated with loading,
 * using, modifying and/or developing or reproducing the software by the user
 * in light of its specific status of free software, that may mean that it
 * is complicated to manipulate, and that also therefore means that it is
 * reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the
 * software's suitability as regards their requirements in conditions enabling
 * the security of their systems and/or data to be ensured and, more generally,
 * to use and operate it in the same conditions as regards security. The
 * fact that you are presently reading this means that you have had knowledge
 * of the CeCILL license and that you accept its terms.
 */
package edtscol.client.planningview;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.cocktail.superplan.client.composant.LogsHistorisation;
import org.cocktail.superplan.client.metier.Reservation;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSNotificationCenter;

import edtscol.client.MainClient;
import edtscol.client.PlanningView;
import fr.univlr.common.utilities.WindowHandler;

public class EDTMouseListener implements MouseMotionListener, MouseListener {

	private ZoneEditable zoneEditable;
	private int mousePressedX;
	private boolean mouseReleased = false;
	private int selectedIndex = -1;
	private int movedIndex = -1;

	private boolean creation = false;
	private boolean agrandir = false;
	private boolean sourisRelache = true;
	private boolean nouvelleCreation = false;
	private boolean mouseDragged = false;
	private int difX = 0, difY = 0;
	private int xCrea, yCrea;
	private boolean plusieursJours = false;

	private boolean macosx = false;
	private CaseReservation uneResa;
	private PlanningView lePlanning;
	private int currentButton = MouseEvent.BUTTON3;
	// CM ajout pour copier 12/04/2007
	private MainClient app = (MainClient) EOApplication.sharedApplication();
	// private EOGlobalID copierResaID;
	// private EOGlobalID couperResaID;

	private JPopupMenu menu;
	private MenuAction menuActionHistorique = new MenuAction("Consulter l'historique...", "M_CH");

	private boolean afficherTooltip = false;

	/**
	 * constructeur de l'objet ecouteur des evenements survenant au planning.
	 * 
	 * @param objet
	 *            : une zoneEditable que va etre ecoutee.
	 */
	public EDTMouseListener(ZoneEditable objet, PlanningView lePlanning) {
		this.zoneEditable = objet;
		this.lePlanning = lePlanning;
		if ("Mac OS X".equals(System.getProperty("os.name"))) {
			macosx = true;
		}

		menu = new JPopupMenu();
		menu.add(new MenuAction("Gestion des semaines...", "M_GS"));
		menu.addSeparator();
		menu.add(new MenuAction("Inspecter la reservation...", "M_IR"));
		menu.add(new MenuAction("Supprimer la reservation...", "M_SR"));
		menu.addSeparator();
		menu.add(menuActionHistorique);
		menu.addSeparator();
		menu.add(new MenuAction("Afficher les creneaux libres", "M_CL"));

		/*
		 * menu.addSeparator(); menu.add(new MenuAction("Copier","M_CI")); menu.add(new MenuAction("Couper","M_CP"));
		 */

		String tooltip = (String) app.userInfo("useTooltipPlanning");
		afficherTooltip = "O".equals(tooltip);
	}

	/**
	 * mode creation ou mode deplacement/modification.
	 */
	public void enableCreation(boolean tf) {
		creation = tf;
		if (creation) {
			zoneEditable.setSouris(Parametres.REDIMENSION);
		}
		else {
			zoneEditable.setSouris(Parametres.MAIN);
		}
	}

	/**
	 * implementation de MouseMotionListener
	 */
	public void mouseMoved(@SuppressWarnings("unused") MouseEvent e) {
		zoneEditable.retirerMessage();
	}

	/**
	 * Fixe si la creation pourra se fair sur plusieurs jours ou non.
	 * 
	 * @param plusieurs
	 *            booleen pour valider ou non ce mode de creation.
	 */
	public void setPlusieursJours(boolean plusieurs) {
		this.plusieursJours = plusieurs;
	}

	/**
	 * Renvoie si la creation sur plusieurs jours est acivee
	 * 
	 * @return vrai ou faux.
	 */
	public boolean getPlusieursJours() {
		return plusieursJours;
	}

	/**
	 * deplacement d'une reservation sur l'EDT.
	 */
	private void deplacerObjet(MouseEvent e) {
		// changement des coordonnees de l'objet.
		CaseReservation caseR = null;
		if ((!mouseReleased) && (selectedIndex != -1)) {
			caseR = ((CaseReservation) zoneEditable.getListeReservations().get(selectedIndex));

			if (!caseR.isDeplacable()) {
				return;
			}

			int nouvelX = e.getX() - difX;

			if (nouvelX < 0) {
				nouvelX = 0;
			}

			if (nouvelX > Parametres.A4_HEIGHT - caseR.getWidth()) {
				nouvelX = Parametres.A4_HEIGHT - caseR.getWidth();
			}

			caseR.setX(nouvelX);

			int nouvelY = e.getY() - difY;

			if (nouvelY < 0) {
				nouvelY = 0;
			}

			if (nouvelY > 7 * Parametres.HAUTEUR_JOUR - caseR.getHeight()) {
				nouvelY = 7 * Parametres.HAUTEUR_JOUR - caseR.getHeight();
			}
			caseR.setY(nouvelY);

			if (nouvelY > 5 * Parametres.HAUTEUR_JOUR) {
				lePlanning.versBas();
			}

			if (this.getPlusieursJours()) {
				this.replacerMultiple(caseR);
			}
			else {
				this.replacerHorizontalement(caseR);
				this.replacerVerticalement(caseR);
			}
		}
		this.repaint();
	}

	/**
	 * permet de redimensionner une reservation qui vient d'etre cree afin d'etre multiple d'un quart d'heure
	 */
	void redimensionner(CaseReservation laResa) {
		int larg = laResa.getWidth();
		int rightBorder = laResa.getX() + larg;
		int nouvelX = 0, ajout = 0;
		int inc = Parametres.TAILLE_QUART_HEURE;

		for (int lesX = 0; lesX < Parametres.A4_HEIGHT - inc + 1; lesX += inc) {
			if (Parametres.dansFourchette(rightBorder, lesX, lesX + inc)) {
				nouvelX = Parametres.getPlusProche(rightBorder, lesX, lesX + inc);

				if ((rightBorder < nouvelX) || (rightBorder < nouvelX)) {
					ajout = nouvelX - rightBorder;
					laResa.setWidth(larg + ajout);
					break;
				}

				if (rightBorder == nouvelX) {
					break;
				}
			}
		}
		zoneEditable.repaint();
	}

	/**
	 * cree un nouveau creneau. cette methode est rappelee pendant le drag de la souris pour agrandir la zone qui se cree.
	 */
	private void creerObjet(MouseEvent e) {
		if (!agrandir) {
			if (uneResa != null) {
				// / TODO : permettre le deplacement ou la creation sur le meme etat.
				this.deplacerObjet(e);

				if (this.getPlusieursJours()) {
					this.replacerMultiple(uneResa);
				}

				else {
					this.replacerHorizontalement(uneResa);
					this.replacerVerticalement(uneResa);
				}

			}
			// this.replacer(uneResa); // recadrage de la resa sur le 1/4 heure.

			xCrea = e.getX();
			yCrea = e.getY();
			NSArray texteTmp = new NSArray();

			uneResa = new CaseReservation(xCrea, yCrea, 2, 2, 1, texteTmp, new Color(0x9A, 0xCD, 0x32, 0x41), null, "EDT", true, null);
			nouvelleCreation = true;
			uneResa.setResizeable(true); // dans le cas d'une creation à la
			// souris on autorise le recadrage
			// redimensionner l'objet cree
			this.redimensionner(uneResa);
			zoneEditable.addToListeReservations(uneResa);
			zoneEditable.repaint();
		}

		if (agrandir) {
			zoneEditable.setSouris(Parametres.REDIMENSION);
			int tx, ty;
			tx = e.getX() - xCrea;
			ty = e.getY() - yCrea;
			uneResa.setWidth(tx);
			uneResa.setHeight(ty);
			zoneEditable.repaint();
		}
	}

	/**
	 * Supprime un objet du planning et de la liste.
	 */
	public void supprimerObjet(int indexObjet) {
		if (confirmerSuppression() == 0) {
			CaseReservation resaASupprimer = (CaseReservation) zoneEditable.getReservationAtIndex(indexObjet);
			if (resaASupprimer instanceof CaseReservation) {
				EOGlobalID resa = resaASupprimer.getResa();
				if (resa == null) {
					return;
				}
				lePlanning.supprimerReservation(resa);
			}
		}
	}

	private int confirmerSuppression() {
		Object[] options = { "Supprimer", "Conserver" };
		int sortie = JOptionPane.showOptionDialog(null, "Vous avez choisi de supprimer une reservation." + "\n" + "Que faire ?",
				"choisir une suite :", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		return sortie;
	}

	/** affiche le menu contextuel au clique droit */
	private void afficherMenuRightClick(MouseEvent e) {
		if (selectedIndex == -1) {
			return;
		}
		CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
		EOGlobalID resaID = selectedResa.getResa();
		if (resaID == null) {
			return;
		}
		Reservation resa = (Reservation) app.editingContext().faultForGlobalID(resaID, app.editingContext());
		if (resa != null && resa.hasHistorique()) {
			menuActionHistorique.setEnabled(true);
		}
		else {
			menuActionHistorique.setEnabled(false);
		}

		menu.show(zoneEditable, e.getX(), e.getY());
		// menu.getComponent(3).setEnabled(false);
	}

	/** evenements du menu contextuel */
	public class MenuAction extends AbstractAction {

		private String id;

		private static final long serialVersionUID = 4368451643255197580L;

		public MenuAction(String name, String anId) {
			putValue(Action.NAME, name);
			id = anId;
		}

		public void setId(String anId) {
			id = anId;
		}

		public String getId() {
			return id;
		}

		public void actionPerformed(@SuppressWarnings("unused") ActionEvent event) {
			// clique droit : semaines de la reservation
			if (id.equals("M_GS")) {
				zoneEditable.retirerMessage();
				if (selectedIndex == -1) {
					return;
				}
				CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
				EOGlobalID resa = selectedResa.getResa();
				if (resa == null) {
					return;
				}
				lePlanning.modifierSemaines(resa);
				return;
			}

			// clique droit : supprimer reservation
			if (id.equals("M_SR")) {
				if (selectedIndex != -1) {
					supprimerObjet(selectedIndex);
				}
				return;
			}

			// clique droit : inspecter reservation
			if (id.equals("M_IR")) {
				if (selectedIndex == -1) {
					return;
				}
				CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
				EOGlobalID resa = selectedResa.getResa();
				if (resa == null) {
					return;
				}
				lePlanning.inspecterReservation(resa);
				return;
			}

			// clique droit : consulter historique
			if (id.equals("M_CH")) {
				if (selectedIndex == -1) {
					return;
				}
				CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
				EOGlobalID resaID = selectedResa.getResa();
				if (resaID == null) {
					return;
				}
				Reservation resa = (Reservation) app.editingContext().faultForGlobalID(resaID, app.editingContext());
				if (app.aDroitModification(resa) || app.aDroitSuppression(resa)) {
					LogsHistorisation.sharedInstance(null).open(resa);
				}
				else {
					WindowHandler.showError("Vous n'avez pas les droits pour consulter l'historique de cette réservation.");
				}
				return;
			}

			if (id.equals("M_CL")) {
				if (selectedIndex == -1) {
					return;
				}
				CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
				rechercherCreneaux(selectedResa);
				return;
			}
			// if (id.equals("M_CI")) {
			// if (selectedIndex == -1) {
			// return;
			// }
			// CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
			// EOGlobalID resa = selectedResa.getResa();
			//
			// if (resa == null) {
			// return;
			// }
			// copierResaID = resa;
			// }
			// if (id.equals("M_CP")) {
			// if (selectedIndex == -1) {
			// return;
			// }
			// CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
			// EOGlobalID resa = selectedResa.getResa();
			//
			// if (resa == null) {
			// return;
			// }
			// couperResaID = resa;
			// }
		}
	}

	/**
	 * Replace l'objet laResa pour qu'il ne deborder pas sur le planning verticalement.
	 */
	private void replacerVerticalement(CaseReservation laResa) {
		/*
		 * if(laResa.isResizeable()==false) return;
		 */
		// ne pas deborder sur un jour verticalement.
		if ((laResa.getHeight() > Parametres.HAUTEUR_JOUR) || (laResa.getHeight() < Parametres.HAUTEUR_JOUR)) {
			if (laResa.isResizeable()) {
				laResa.setHeight(Parametres.HAUTEUR_JOUR);
			}
		}

		int y = laResa.getY();
		int nouvelY = 0;
		int inc = 0;
		int pp = 0;

		inc = Parametres.HAUTEUR_EDT / 7;
		for (int lesY = 0; lesY < Parametres.HAUTEUR_EDT - inc + 1; lesY += inc) {
			pp = lesY + Parametres.HAUTEUR_JOUR;
			if (Parametres.dansFourchette(y, lesY, pp)) {
				nouvelY = Parametres.getPlusProche(y, lesY, lesY + inc);
				laResa.setY(nouvelY);
				break;
			}
		}
	}

	/**
	 * Replace l'objet laResa pour qu'il ne deborder pas sur le planning horizontal.
	 */
	private void replacerHorizontalement(CaseReservation laResa) {

		int x = laResa.getX();
		int nouvelX = 0;
		int inc = 0;

		// ne pas deborder sur un jour horizontalement.
		inc = Parametres.TAILLE_QUART_HEURE;

		laResa.getWidth();
		int w = laResa.getWidth();
		int ecart = w % inc;

		if (ecart >= inc / 2) {
			w = w - ecart + inc;
		}
		else {
			w = w - ecart;
		}

		for (int lesX = 0; lesX < Parametres.A4_HEIGHT - inc + 1; lesX += inc) {
			if (Parametres.dansFourchette(x, lesX, lesX + inc)) {
				nouvelX = Parametres.getPlusProche(x, lesX, lesX + inc);
				laResa.setX(nouvelX);
				laResa.setWidth(w);
				break;
			}
		}
	}

	/**
	 * Replace l'objet - laResa - pour qu'il ne deborder pas sur le planning verticalement.
	 */
	private void replacerMultiple(CaseReservation laResa) {
		// if(laResa.isResizeable()==false)
		// return;

		// ne pas deborder sur un jour verticalement.
		double multiple = (laResa.getHeight() / Parametres.HAUTEUR_JOUR);
		int hauteur = (int) multiple * Parametres.HAUTEUR_JOUR;

		if (laResa.isResizeable()) {
			laResa.setHeight(hauteur);
		}

		int y = laResa.getY();
		int nouvelY = 0;
		int inc = 0;

		inc = Parametres.HAUTEUR_EDT / 7;
		for (int lesY = 0; lesY < Parametres.HAUTEUR_EDT - inc + 1; lesY += inc) {
			if (Parametres.dansFourchette(y, lesY, lesY + Parametres.HAUTEUR_JOUR)) {
				nouvelY = Parametres.getPlusProche(y, lesY, lesY + inc);
				laResa.setY(nouvelY);
				break;
			}
		}

		// horizontal

		int x = laResa.getX();
		int nouvelX = 0;

		// ne pas deborder sur un jour horizontalement.
		inc = Parametres.TAILLE_QUART_HEURE;
		for (int lesX = 0; lesX < Parametres.A4_HEIGHT - inc + 1; lesX += inc) {
			if (Parametres.dansFourchette(x, lesX, lesX + inc)) {
				nouvelX = Parametres.getPlusProche(x, lesX, lesX + inc);
				laResa.setX(nouvelX);
				break;
			}
		}
	}

	/*
	 * Permet de rafraichir le composant.
	 */
	private void repaint() {
		zoneEditable.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		try {
			mouseDragged = true;
			if (!creation) {
				if (((movedIndex != selectedIndex) || (sourisRelache)) && (selectedIndex != -1)) {
					movedIndex = selectedIndex;
					sourisRelache = false;
					CaseReservation uneCase = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
					difX = e.getX() - uneCase.getX();
					difY = e.getY() - uneCase.getY();
				}
				if (currentButton == MouseEvent.BUTTON1) { // bouton droit
					this.deplacerObjet(e);
				}
			}

			if (creation) {
				creerObjet(e);
				agrandir = true;
			}
		}
		catch (Exception ex) {
			System.out.println("mouseDragged : " + ex.getMessage());
		}
	}

	/**
	 * Juste pour faire des tests d'appuie notamment sous MacOS ...
	 * 
	 * @param e
	 *            un mouseEvent genere par une interface listener : genre MouseListener.
	 */
	/*
	 * public void printMouseEventButton(MouseEvent e) { int modifiers = e.getModifiers(); if ((modifiers & InputEvent.BUTTON1_MASK) ==
	 * InputEvent.BUTTON1_MASK) { Journal.print("DROIT = BUTTON1"); }
	 * 
	 * if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) { Journal.print("MILIEU = BUTTON2"); }
	 * 
	 * if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) { Journal.print("GAUCHE = BUTTON3"); } }
	 */
	/*
	 * implements MouseListener : begin
	 */
	public void mouseClicked(MouseEvent e) {
		if (selectedIndex == -1) {
			zoneEditable.retirerMessage();
		}
	}

	public void mouseEntered(@SuppressWarnings("unused") MouseEvent e) {
	}

	public void mouseExited(@SuppressWarnings("unused") MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		mouseDragged = false;
		mouseReleased = false;

		currentButton = e.getButton();
		int modifiers = e.getModifiers();

		mousePressedX = e.getX();
		selectedIndex = indexComposant(zoneEditable.getListeReservations(), e.getX(), e.getY());

		if (selectedIndex != -1) {
			CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
			String leTexte = selectedResa.getText();
			if (leTexte == null || leTexte.equals("") || leTexte.equals(" ")) {
				return;
			}

			selectedResa.setBorderColor(Parametres.SELECTED_BORDER);

			int leX = selectedResa.getX(), leY = selectedResa.getY();
			EOGlobalID idResa = selectedResa.getResa();

			// if (e.getButton() == MouseEvent.BUTTON1) { // affiche le message
			// if (afficherTooltip) {
			// java.util.ArrayList<String> infosDetaillees = null;
			// if (idResa != null) {
			// infosDetaillees = lePlanning.getDetailedStringsForResa(idResa);
			// zoneEditable.afficherMessage(infosDetaillees, zoneEditable, e.getPoint());
			// }
			// else {
			// zoneEditable.afficherMessage(selectedResa.getStrings(), zoneEditable, e.getPoint());
			// }
			// }
			// }
		}
		if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
			zoneEditable.setSouris(Parametres.REDIMENSION);
		}
	}

	/**
	 * implementation de l'interface : souris relachee.
	 */
	public void mouseReleased(MouseEvent e) {
		// essai de replacement juste apres creation.
		if (selectedIndex != -1 && creation && nouvelleCreation && !agrandir) { // AJOUT
			try { // lastIndex
				this.replacerHorizontalement((CaseReservation) zoneEditable.getListeReservations().get(selectedIndex));
				this.replacerVerticalement((CaseReservation) zoneEditable.getListeReservations().get(selectedIndex));
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (selectedIndex != -1 && mouseDragged && !nouvelleCreation && !agrandir && !creation) {
			try {
				this.modifierPeriodicites((CaseReservation) zoneEditable.getListeReservations().get(selectedIndex));
				return;
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		int modifiers = e.getModifiers();
		mouseReleased = true;

		if (selectedIndex == movedIndex) {
			sourisRelache = true;
		}

		if (((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK)
				|| (((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) && (macosx))) {
			if (selectedIndex != -1) {
				this.afficherMenuRightClick(e);
				return;
			}
		}

		if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
			agrandir = false;
		}

		if ((nouvelleCreation) && (!agrandir)) {
			this.replacerHorizontalement(uneResa);
			this.replacerVerticalement(uneResa);
			zoneEditable.repaint();
			this.creationDansInspecteur(uneResa);
			nouvelleCreation = false;
			return;
		}

		if (e.getButton() == MouseEvent.BUTTON1 && selectedIndex != -1) { // affiche le message
			if (afficherTooltip) {
				CaseReservation selectedResa = (CaseReservation) zoneEditable.getListeReservations().get(selectedIndex);
				EOGlobalID idResa = selectedResa.getResa();
				java.util.ArrayList<String> infosDetaillees = null;
				if (idResa != null) {
					infosDetaillees = lePlanning.getDetailedStringsForResa(idResa);
					zoneEditable.afficherMessage(infosDetaillees, zoneEditable, e.getPoint());
				}
				else {
					zoneEditable.afficherMessage(selectedResa.getStrings(), zoneEditable, e.getPoint());
				}
			}
		}
	}

	/**
	 * implementation MouseListener OK
	 */
	// CM modif bu pc lors du drag and drop
	/*
	 * private void planningViewDoubleClicked() {
	 * 
	 * if(selectedIndex != -1) { zoneEditable.retirerMessage(); CaseReservation selectedResa =
	 * (CaseReservation)zoneEditable.getListeReservations().get(selectedIndex);
	 * 
	 * if(selectedResa.getResa()==null) return; lePlanning.inspecterReservation(selectedResa.getResa(),selectedResa.getAppli()); } }
	 */
	/**
	 * renvoi l'index du composant sous la souris au moment du release, ou alors -1 s'il n'y'en a pas
	 */
	private int indexComposant(LinkedList<Object> laListe, int mouseX, int mouseY) {
		Object obj = null;
		for (int index = 0; index < laListe.size(); index++) {
			obj = laListe.get(index);
			if (obj instanceof CaseReservation) {

				if (yaQuelqueChose((CaseReservation) obj, mouseX, mouseY)) {
					return index;
				}
			}
		}
		return -1;
	}

	/**
	 * teste si le composant case est sous le curseur de la souris ou non
	 */
	private boolean yaQuelqueChose(CaseReservation laCase, int mouseX, int mouseY) {

		int x = laCase.getX(), y = laCase.getY(), w = laCase.getWidth(), h = laCase.getHeight();
		boolean xOk = false;
		boolean yOk = false;

		for (int i = x; i < x + w; i++) {
			if (i == mouseX) {
				xOk = true;
			}
		}

		for (int j = y; j < y + h; j++) {
			if (j == mouseY) {
				yOk = true;
			}
		}

		if ((xOk) && (yOk)) {
			return true;
		}
		else {
			return false;
		}

	}

	/** lors de la création avec la souris : lancer l'inspecteur pour continuer */
	public void creationDansInspecteur(CaseReservation reservationx) {
		String start = lePlanning.positionToStringTime(reservationx.getX());
		String end = lePlanning.positionToStringTime(reservationx.getX() + reservationx.getWidth());
		// on prend la position verticale à la moitié du creneau.
		// int jourCreation = lePlanning.positionToJour( reservation.getY()+(
		// (int)(reservation.getHeight()/2) ) );
		// System.out.println("creationDansInspecteur");
		int jourCreation = positionToJour(reservationx);
		NSDictionary dictio = new NSDictionary(new Object[] { (start + ":" + end), new Integer(jourCreation) }, new Object[] { "time", "jour" });

		// if (copierResaID != null || couperResaID != null) {
		// String msg = null;
		// EOGlobalID rID = null;
		// if (copierResaID != null) {
		// msg = "Copier la r\u00e9servation s\u00e9lectionn\u00e9e";
		// rID = copierResaID;
		// }
		// if (couperResaID != null) {
		// msg = "Coller la r\u00e9servation s\u00e9lectionn\u00e9e";
		// rID = couperResaID;
		// }
		// int choix = JOptionPane.showConfirmDialog(null, msg, "Confirmation", JOptionPane.YES_NO_OPTION);
		// if (choix == 0) {
		// Reservation r = (Reservation) (app.editingContext().faultForGlobalID(rID, app.editingContext()));
		// ReunionFactory reunionFactory = new ReunionFactory(app.editingContext());
		//
		// NSArray objets = (NSArray) r.valueForKeyPath(Reservation.RESERVATION_OBJETS_KEY + "." + ReservationObjet.RESA_OBJET_KEY);
		//
		// IndividuUlr agent = r.individuAgent();
		// NSArray tmpObj = r.periodicites();
		// NSMutableArray periodicites = new NSMutableArray();
		// GregorianCalendar cal = new GregorianCalendar();
		// cal.setTime(lePlanning.superviseur.debut);
		// cal.add(Calendar.DAY_OF_WEEK, jourCreation);
		// NSTimestamp dm = new NSTimestamp(cal.getTime());
		// NSTimestampFormatter formatter = new NSTimestampFormatter("%d/%m/%Y");
		// String ds = formatter.format(dm);
		// formatter = new NSTimestampFormatter("%d/%m/%Y %H:%M");
		// if (start.length() == 4) {
		// start = start + "0";
		// }
		// if (end.length() == 4) {
		// end = end + "0";
		// }
		// NSTimestamp dateDeb = new NSTimestamp();
		// NSTimestamp dateFin = new NSTimestamp();
		// try {
		// dateDeb = (NSTimestamp) formatter.parseObject(ds + " " + start);
		// dateFin = (NSTimestamp) formatter.parseObject(ds + " " + end);
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
		// periodicites.addObject(dateDeb);
		// periodicites.addObject(dateFin);
		// tmpObj = r.occupants();
		// NSMutableArray occupants = new NSMutableArray();
		// for (int i = 0; i < tmpObj.count(); i++) {
		// Occupant oc = (Occupant) tmpObj.objectAtIndex(i);
		// occupants.addObject(oc.individu());
		// }
		// tmpObj = r.resaSalles();
		// NSMutableArray resaSalles = new NSMutableArray();
		// for (int i = 0; i < tmpObj.count(); i++) {
		// ResaSalles rp = (ResaSalles) tmpObj.objectAtIndex(i);
		// resaSalles.addObject(rp.salle());
		// }
		// NSArray groupes = new NSArray();
		// NSArray choixSalles = new NSArray();
		// int tloc = 0;
		// if (r.tlocCode().equals("s")) {
		// tloc = 0;
		// }
		// if (r.tlocCode().equals("r")) {
		// tloc = 1;
		// }
		// if (r.tlocCode().equals("p")) {
		// tloc = 2;
		// }
		//
		// try {
		// reservation = reunionFactory.creerReunion(null, agent, periodicites, occupants, groupes, resaSalles, choixSalles, objets, r
		// .resaCommentaire(), tloc);
		// if (couperResaID != null) {
		// NSArray aPer = r.periodicites();
		// Periodicite per = (Periodicite) aPer.objectAtIndex(0);
		// NSTimestamp oldDeb = per.dateDeb();
		// NSTimestamp oldFin = per.dateFin();
		// lePlanning.superviseur.nePasAfficherMessage = true;
		// lePlanning.supprimerReservation(couperResaID);
		// ModifierPeriodicites mp = new ModifierPeriodicites(app.editingContext(), reservation);
		// mp.envoyerMail((Periodicite) reservation.periodicites().objectAtIndex(0), oldDeb, oldFin);
		// }
		// if (copierResaID != null) {
		// InspecteurCtrl ges = new InspecteurCtrl(app.editingContext(), lePlanning.superviseur, app.isInspecteurModal());
		// ges.prepareMailPersonne(1, reservation);
		// }
		// lePlanning.superviseur.processPlanning(lePlanning.superviseur.getInformations());
		// copierResaID = null;
		// couperResaID = null;
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
		// return;
		// }
		// else {
		// copierResaID = null;
		// couperResaID = null;
		// }
		//
		// }
		NSNotificationCenter.defaultCenter().postNotification("edtNouvelleReservation", dictio);
	}

	/**
	 * permet de changer les periodicites de la reservation apres deplacement sur l'EDT graphique
	 */
	public void modifierPeriodicites(CaseReservation reservation) {

		if (Math.abs(mousePressedX - reservation.getX()) < 5) {
			return;
		}

		if (reservation == null || reservation.getResa() == null) {
			return;
		}

		int w = reservation.getWidth();

		String start = lePlanning.positionToStringTime(reservation.getX());
		String end = lePlanning.positionToStringTime(reservation.getX() + w);

		int jourCreation = positionToJour(reservation);

		NSDictionary dictio = new NSDictionary(
				new Object[] { new Integer(w), (start + ":" + end), new Integer(jourCreation), reservation.getResa() }, new Object[] { "duree",
						"time", "jour", "reservation" });
		// System.out.println("dictio " + dictio);
		NSNotificationCenter.defaultCenter().postNotification("edtDeplacerCreneau", dictio);
	}

	public void rechercherCreneaux(CaseReservation reservation) {
		if (reservation == null || reservation.getResa() == null) {
			return;
		}

		Integer w = new Integer(reservation.getWidth());
		Integer x = new Integer(reservation.getX());
		Integer y = new Integer(reservation.getY());

		NSDictionary dictio = new NSDictionary(new Object[] { reservation.getResa(), w, x, y }, new Object[] { "reservation", "duree", "x", "y" });

		NSNotificationCenter.defaultCenter().postNotification("edtRechercherCreneaux", dictio);
	}

	/**
	 * devine le jour de la resa a partir de la position sur le planning pour etre sur on passe le milieu de la hauteur de la position : pas
	 * de risque d'erreur en autre on utilisera cette methode uniquement pour les reservations qui prennent une hauteur entiere : pas
	 * plusieurs groupes
	 */
	protected int positionToJour(CaseReservation res) {
		int haut = Parametres.HAUTEUR_JOUR;
		// System.out.println("res.getY() " + res.getY() + "res.getHeight() " +
		// res.getHeight());
		// int pos = res.getY()+(int)(res.getHeight()/2);
		int pos = res.getY();
		int val = 0;

		if (Parametres.dansFourchette(pos, 0, haut)) {
			val = 0; // le lundi
		}

		if (Parametres.dansFourchette(pos, haut, haut * 2)) {
			val = 1; // le Mardi
		}

		if (Parametres.dansFourchette(pos, haut * 2, haut * 3)) {
			val = 2; // le Mercredi
		}

		if (Parametres.dansFourchette(pos, haut * 3, haut * 4)) {
			val = 3; // le Jeudi
		}

		if (Parametres.dansFourchette(pos, haut * 4, haut * 5)) {
			val = 4; // le Vendredi
		}

		if (Parametres.dansFourchette(pos, haut * 5, haut * 6)) {
			val = 5; // le Samedi
		}

		if (Parametres.dansFourchette(pos, haut * 6, haut * 7)) {
			val = 6; // le Dimanche
		}

		return val;
	}

}
