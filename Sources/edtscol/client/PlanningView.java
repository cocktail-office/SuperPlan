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
package edtscol.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.ResaExamen;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.client.EOClientResourceBundle;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

import edtscol.client.planningview.CaseReservation;
import edtscol.client.planningview.EDTMouseListener;
import edtscol.client.planningview.EDTPlanningPane;
import edtscol.client.planningview.JoursView;
import edtscol.client.planningview.Parametres;
import edtscol.client.planningview.ZoneEditable;
import edtscol.client.planningview.ZoneHeures;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;

public class PlanningView extends JPanel implements Printable {

	private static final long serialVersionUID = 6009707869431262650L;
	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EDTPlanningPane edtPlanning, lesJours;

	private EDTMouseListener edtListener;
	private ZoneEditable zoneEditable;
	private ZoneHeures zoneHeures;
	private JoursView joursView;

	// les boutons de defilement.
	private JButton btGauche, btDroite, btHaut, btBas, btDefaut;

	private JToggleButton btPlusieursJours;

	private ActionDefilement actionDefilement;
	// les formatters.
	private static final NSTimestampFormatter hourForm = new NSTimestampFormatter("%H");
	private static final NSTimestampFormatter minuteForm = new NSTimestampFormatter("%M");
	// objets webobjects.
	public MainInterface superviseur;
	private static EOClientResourceBundle leBundle;

	int nbGroupes = 1;

	private boolean creation = false;

	private EnseignementFactory ensFactory;

	/**
	 * Classe representant l'objet global Emploi Du Temps.
	 */
	public PlanningView() {
		super();
		// this.setPreferredSize(Parametres.TAILLE_PLANNING);

	}

	/**
	 * Attribue une reference au superviseur sur cet objet.
	 * 
	 * @param unSuper
	 *            le superviseur qui va controller cet objet.
	 */
	public void setSuperviseur(MainInterface unSuper) {
		superviseur = unSuper;
	}

	/**
	 * autoriser la creation ou le deplacement de resa au choix.
	 */
	public void enableCreation() {
		creation = !creation;
		if (edtListener != null) {
			edtListener.enableCreation(creation);
		}
	}

	/** efface le contenu affiche dans l'EDT */
	public void clear() {
		zoneEditable.viderListeReservations();
	}

	public int getHorizontalBarValue() {
		if (edtPlanning == null) {
			return 0;
		}
		return edtPlanning.getHorizontalBarValue();
	}

	/** efface le contenu afficheé dans l'EDT et affecte la nouvelle semaine */
	public void clearWithDate(Date date) {
		if (zoneEditable != null) {
			zoneEditable.viderListeReservations();
		}
		if (joursView != null) {
			joursView.setTime(date);
		}
	}

	/**
	 * Methode d'initialisation du composant.
	 */
	public void initialiser(MainInterface superv) {
		superviseur = superv;

		leBundle = WidgetHandler.getResourceBundle();
		this.setLayout(new BorderLayout());
		actionDefilement = new ActionDefilement("global");

		lesJours = new EDTPlanningPane();
		zoneEditable = new ZoneEditable();
		edtListener = new EDTMouseListener(zoneEditable, this);
		zoneEditable.addMouseListener(edtListener);
		zoneEditable.addMouseMotionListener(edtListener);

		zoneHeures = new ZoneHeures();
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(zoneHeures, BorderLayout.NORTH);
		panel.add(zoneEditable, BorderLayout.CENTER);
		// le calendrier qui s'affiche sur le cote.
		String tzString = (String) app.invokeRemoteMethod(app.editingContext(), "session", "clientSideRequestDefaultTimeZoneString", null);
		if (tzString == null) {
			tzString = "Europe/Paris";
		}
		joursView = new JoursView(tzString);
		joursView.setPlanningView(this);
		lesJours.setViewportView(joursView);

		edtPlanning = new EDTPlanningPane();

		edtPlanning.setViewportView(panel);
		edtPlanning.setDebutVisible(8.0);

		this.add(edtPlanning);
		// ajout de la vue des jours de la semaine(le calendrier)
		this.add(lesJours, BorderLayout.WEST);

		placerBoutons();
	}

	/**
	 * Rafraichit le planning selon la vue sÃ©lectionnÃ©e (jour ou non) AppelÃ© par JoursView
	 * 
	 */
	public void refresh() {

		if (joursView.unSeulJour()) {
			LinkedList<CaseReservation> liste = new LinkedList<CaseReservation>();
			CaseReservation cas;
			int listIndex = 0;
			while (listIndex < zoneEditable.getListeReservations().size()) {
				cas = (CaseReservation) (zoneEditable.getListeReservations().get(listIndex));
				// Journal.print("Position de la resa: " + cas.getY() + " jour dÃ©duit : " + positionToJour(cas.getY()) + " jour select : "
				// + joursView.getUnJour());
				// On prend le milieu de la reservation et on regarde si elle est dans le jour selectionnÃ©
				int pos = cas.getY() + (cas.getHeight() / 2);
				if (positionToJour(pos) == joursView.getUnJour()) {
					int y = (cas.getY() - (Parametres.HAUTEUR_JOUR * joursView.getUnJour())) * 5;
					cas.setY(y);
					cas.setHeight(cas.getHeight() * 5);
					liste.add(cas);
				}
				listIndex++;
			}

			this.clear();
			zoneEditable.viderListeComposants();
			zoneEditable.preparerGrille(false);
			zoneEditable.getListeReservations().addAll(liste);
		}
		else {
			app.mainInterface().refreshPlanning();
			zoneEditable.preparerGrille(true);
		}
		zoneEditable.repaint();
	}

	/*
	 * preparer un JButton.
	 */
	private void preparerBouton(JButton leBouton, String icone) {
		leBouton.setText(null);
		leBouton.setIcon((ImageIcon) leBundle.getObject(icone));
		leBouton.setPreferredSize(Parametres.TAILLE_BOUTON);
	}

	// creation des boutons de defilement.
	private void placerBoutons() {

		btGauche = new JButton();
		btGauche.setAction(new ActionDefilement("g"));
		btGauche.setToolTipText("Defilement l'EDT vers la droite");
		this.preparerBouton(btGauche, "gauche");

		btDroite = new JButton();
		btDroite.setAction(actionDefilement);
		btDroite.setToolTipText("Defilement de l'EDT vers la gauche");
		this.preparerBouton(btDroite, "droite");

		btHaut = new JButton();
		btHaut.setAction(actionDefilement);
		btHaut.setToolTipText("Defilement vers le haut");
		this.preparerBouton(btHaut, "haut");

		btBas = new JButton();
		btBas.setAction(actionDefilement);
		btBas.setToolTipText("Defilement vers le bas");
		this.preparerBouton(btBas, "bas");

		btDefaut = new JButton();
		btDefaut.setAction(actionDefilement);
		btDefaut.setToolTipText("afficher les creneaux 8h-20h");
		this.preparerBouton(btDefaut, "main");

		JPanel panelBoutons = new JPanel();
		panelBoutons.setPreferredSize(new Dimension(840, 25));
		panelBoutons.add(btGauche);
		panelBoutons.add(btDroite);
		panelBoutons.add(btDefaut);
		panelBoutons.add(btHaut);
		panelBoutons.add(btBas);
		// panelBoutons.add(btPlusieursJours);
		this.add(panelBoutons, BorderLayout.NORTH);
	}

	/**
	 * fixe le nombre de groupe de la ressource dont on affiche l'EDT.
	 */
	public void setNbGroupes(int nbGroupes) {
		this.nbGroupes = nbGroupes;
	}

	public ArrayList<String> getDetailedStringsForResa(EOGlobalID idResa) {
		ArrayList<String> lst = new ArrayList<String>();
		Reservation resa = (Reservation) app.editingContext().faultForGlobalID(idResa, app.editingContext());

		if (ensFactory == null) {
			ensFactory = new EnseignementFactory(app.editingContext());
		}

		// les occupants.
		NSArray<Occupant> occupants = resa.occupants();
		StringBuffer bfr = new StringBuffer();
		if (resa.resaCommentaire() != null) {
			bfr.append(resa.resaCommentaire()).append("\n");
		}

		for (int i = 0; i < occupants.count(); i++) {
			IndividuUlr individu = (occupants.objectAtIndex(i)).individu();
			bfr.append(individu.nomPrenom());
			bfr.append("\n");
		}
		lst.add(bfr.toString());

		bfr = new StringBuffer();

		// les salles
		NSArray<ResaSalles> resaSalles = resa.resaSalles();
		bfr = new StringBuffer();

		for (int i = 0; i < resaSalles.count(); i++) {
			Salles salle = (resaSalles.objectAtIndex(i)).salle();
			bfr.append("Bat. ");
			bfr.append(salle.cLocal());
			bfr.append(" - Salle : ");
			bfr.append(salle.salPorte());
			bfr.append("\n");
		}
		lst.add(bfr.toString());

		// les enseignements
		NSArray<ReservationAp> reservationAp = resa.reservationAp();
		bfr = new StringBuffer();

		ReservationAp resaAp;
		MaquetteAp apPrecedent = null;
		ScolGroupeGrp grpPrecedent = null;
		for (int i = 0; i < reservationAp.count(); i++) {
			resaAp = reservationAp.objectAtIndex(i);
			MaquetteAp ap = resaAp.maquetteAp();
			// bfr.append(ensFactory.detailDiplomePourAp(ap));
			// bfr.append("\n -");
			if (apPrecedent == null || !apPrecedent.equals(ap)) {
				bfr.append(ap.toString());
				apPrecedent = ap;
			}
			if (resaAp.scolGroupeGrp() != null) {
				if (grpPrecedent == null || !grpPrecedent.equals(resaAp.scolGroupeGrp())) {
					bfr.append("Groupe : ");
					bfr.append(resaAp.scolGroupeGrp().ggrpCode());
					grpPrecedent = resaAp.scolGroupeGrp();
				}
			}
			bfr.append("\n");
		}
		lst.add(bfr.toString());

		// les examens
		NSArray<ResaExamen> resaExamen = resa.resaExamens();
		bfr = new StringBuffer();

		for (int i = 0; i < resaExamen.count(); i++) {
			ExamenEntete eentete = (resaExamen.objectAtIndex(i)).examenEntete();
			bfr.append(ensFactory.detailDiplomePourExamen(eentete));
			bfr.append("\n -");
			bfr.append(eentete.eentLibelle());
		}
		lst.add(bfr.toString());

		return lst;
	}

	/**
	 * renvoi une chaine au format HH:MM de l'heure reperesentant la position pos verticale sur le planning
	 */
	public String positionToStringTime(int pos) {

		int nbHeures = (pos / 60);
		int nbMinutes = pos - nbHeures * 60;
		String chaineDate = String.valueOf(nbHeures) + ":" + String.valueOf(nbMinutes);
		return chaineDate;
	}

	/**
	 * renvoi la position horizontale sur le planning correspondant à l'heure de la date
	 */
	protected int heureToPosition(NSTimestamp date) {
		int heure = FormatHandler.strToInt(hourForm.format(date));
		int minute = FormatHandler.strToInt(minuteForm.format(date));
		return ((heure * 60 + minute) * Parametres.TAILLE_MINUTE);
	}

	/** renvoie la position du groupe */
	protected int groupeToPosition(int groupeMark, int jour, int nombreGroupes) {
		int jourPlanning = 0;
		switch (jour) {
		case Calendar.MONDAY:
			jourPlanning = 0;
			break;

		case Calendar.TUESDAY:
			jourPlanning = 1;
			break;

		case Calendar.WEDNESDAY:
			jourPlanning = 2;
			break;

		case Calendar.THURSDAY:
			jourPlanning = 3;
			break;

		case Calendar.FRIDAY:
			jourPlanning = 4;
			break;

		case Calendar.SATURDAY:
			jourPlanning = 5;
			break;

		case Calendar.SUNDAY:
			jourPlanning = 6;
			break;
		}

		int hCalcule = (jourPlanning * Parametres.HAUTEUR_JOUR) + (groupeMark - 1) * this.tailleDeGroupe(nombreGroupes);

		if (hCalcule == 102) {
			return 105;
		}
		if (hCalcule == 34) {
			return 35;
		}

		return hCalcule;
	}

	/**
	 * devine le jour de la resa a partir de la position sur le planning pour etre sur on passe le milieu de la hauteur de la position : pas
	 * de risque d'erreur en autre on utilisera cette methode uniquement pour les reservations qui prennent une hauteur entiere : pas
	 * plusieurs groupes
	 */
	protected int positionToJour(int pos) {
		int haut = Parametres.HAUTEUR_JOUR;

		if (Parametres.dansFourchette(pos, 0, haut)) {
			return 0; // le lundi
		}

		if (Parametres.dansFourchette(pos, haut, haut * 2)) {
			return 1; // le Mardi
		}

		if (Parametres.dansFourchette(pos, haut * 2, haut * 3)) {
			return 2; // le Mercredi
		}

		if (Parametres.dansFourchette(pos, haut * 3, haut * 4)) {
			return 3; // le Jeudi
		}

		if (Parametres.dansFourchette(pos, haut * 4, haut * 5)) {
			return 4; // le Vendredi
		}

		if (Parametres.dansFourchette(pos, haut * 5, haut * 6)) {
			return 5; // le Samedi
		}

		if (Parametres.dansFourchette(pos, haut * 6, haut * 7)) {
			return 6; // le Dimanche
		}

		return 0;
	}

	/** hauteur d'un goupe etant donnee le nombre de groupes afficher */
	protected int hauteurGroupe(int gDeb, int gFin, int nombreGroupes) {
		return (gFin - gDeb) * this.tailleDeGroupe(nombreGroupes);
	}

	protected int tailleDeGroupe(int nombreGroupes) {
		if (nombreGroupes != 0) {
			return (Parametres.HAUTEUR_JOUR / nombreGroupes);
		}
		else {
			return -1; // ya vraiment un probleme.
		}
	}

	public void afficherPlanningGenerique(NSArray<NSDictionary<String, Object>> resa, boolean clear) {
		if (clear) {
			this.clear();
		}
		for (int i = 0; i < resa.count(); i++) {
			afficherCreneauSimple(resa.objectAtIndex(i), 1, 2, 1, false);
		}
	}

	public void afficherPlanningMultiples(NSArray<NSArray<NSDictionary<String, Object>>> resa, int nombreGroupes) {
		this.clear();
		for (int i = 0; i < resa.count(); i++) {
			NSArray<NSDictionary<String, Object>> creneau = resa.objectAtIndex(i);
			afficherCreneauMulti(creneau, nombreGroupes, true);
		}
	}

	public void afficherPlanningEnseignement(NSArray resas) {
		this.clear();

		for (int i = 0; i < resas.count(); i++) {
			Object creneau = resas.objectAtIndex(i);
			if (creneau instanceof NSDictionary) {
				afficherCreneauSimple((NSDictionary) creneau, 1, 2, 1, false);
			}

			if (creneau instanceof NSMutableArray || creneau instanceof NSArray) {
				afficherCreneauMulti((NSArray) creneau, false);
			}
		}
	}

	public void afficherPlanningUbiquite(NSArray resa) {
		this.clear();

		for (int i = 0; i < resa.count(); i++) {
			Object creneau = resa.objectAtIndex(i);
			if (creneau instanceof NSDictionary) {
				afficherCreneauSimple((NSDictionary) creneau, 1, 2, 1, false);
			}

			if (creneau instanceof NSMutableArray || creneau instanceof NSArray) {
				afficherCreneauMulti((NSArray) creneau, false);
			}
		}
	}

	public void afficherCreneauSimple(NSDictionary<String, Object> creneau, int groupDeb, int groupFin, int nombreGroupes, boolean affLibelle) {
		int x = 0, y = 0, h = 0, w = 0;
		NSTimestamp debut = (NSTimestamp) creneau.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) creneau.valueForKey("fin");

		x = this.heureToPosition(debut);
		w = this.heureToPosition(fin) - x;
		int jour = ((Number) creneau.valueForKey("jour")).intValue();

		EOGlobalID idResa = (EOGlobalID) creneau.valueForKey("reservation");

		// if(creneau.valueForKey("d"))
		Number deplace = (Number) creneau.valueForKey("deplace");
		if (deplace == null) {
			deplace = new Integer(0);
		}

		NSMutableArray texte = new NSMutableArray();
		if (creneau.valueForKey("libelle") != null && nombreGroupes > 1 && affLibelle) {
			texte.addObject(creneau.valueForKey("libelle"));
		}
		texte.addObjectsFromArray((NSArray) creneau.valueForKey("texte"));
		String ccolor = (String) creneau.valueForKey("ccolor");
		if (creneau.valueForKey("rang") != null && nombreGroupes > 1) {
			switch (((Integer) creneau.valueForKey("rang")).intValue()) {
			case 0:
				ccolor = "r";
				break;
			case 1:
				ccolor = "s";
				break;
			case 2:
				ccolor = "C";
				break;
			case 3:
				ccolor = "L";
				break;
			case 4:
				ccolor = "CM";
				break;
			case 5:
				ccolor = "TP";
				break;
			default:
				break;
			}
		}
		String salleValide = (String) creneau.valueForKey("salleValide");
		String objetValide = (String) creneau.valueForKey("objetValide");
		int typeDraw = 1;

		// Dans le cas du changement de semaine on reste en vue jour.
		if (joursView.unSeulJour()) {
			// FIXME : Pourquoi + 2 ?
			if (joursView.getUnJour() + 2 == jour) {
				y = ((this.groupeToPosition(groupDeb, jour, nombreGroupes) - (Parametres.HAUTEUR_JOUR * joursView.getUnJour()))) * 5;
				h = this.hauteurGroupe(groupDeb, groupFin, nombreGroupes) * 5;
			}
		}
		else {
			y = this.groupeToPosition(groupDeb, jour, nombreGroupes);
			h = this.hauteurGroupe(groupDeb, groupFin, nombreGroupes);
		}
		// this.ajouterCreneau(x,y,w,h,typeDraw, texte,color,
		// idResa,"EDT",salleValide,deplace);
		this.ajouterCreneau(x, y, w, h, typeDraw, texte, ccolor, idResa, salleValide, objetValide, deplace);
	}

	public void afficherCreneauMulti(NSArray creneau, boolean affLibelle) {
		int nombreGroupes = creneau.count();
		for (int i = 0; i < nombreGroupes; i++) {
			int gDebut = i + 1;
			int gFin = i + 2;
			this.afficherCreneauSimple((NSDictionary) creneau.objectAtIndex(i), gDebut, gFin, nombreGroupes, affLibelle);
		}
	}

	public void afficherCreneauMulti(NSArray<NSDictionary<String, Object>> creneau, int nombreGroupes, boolean affLibelle) {
		for (int i = 0; i < creneau.count(); i++) {
			NSDictionary<String, Object> unInfo = creneau.objectAtIndex(i);
			int gDebut = ((Integer) unInfo.valueForKey("rang")).intValue() + 1;
			int gFin = gDebut + 1;
			this.afficherCreneauSimple(unInfo, gDebut, gFin, nombreGroupes, affLibelle);
		}
	}

	public void modifierSemaines(EOGlobalID resa) {
		NSDictionary dictio = new NSDictionary(new Object[] { (Reservation) app.editingContext().faultForGlobalID(resa, app.editingContext()) },
				new Object[] { "reservation" });
		NSNotificationCenter.defaultCenter().postNotification("edtModifierSemaines", dictio);
	}

	public void inspecterReservation(EOGlobalID resa) {
		NSDictionary dictio = new NSDictionary((Reservation) app.editingContext().faultForGlobalID(resa, app.editingContext()), "reservation");
		NSNotificationCenter.defaultCenter().postNotification("inspecterReservation", dictio);
	}

	/** declenche la notification de suppression de reservation */
	public boolean supprimerReservation(EOGlobalID resa) {

		try {
			Reservation reservation = (Reservation) app.editingContext().faultForGlobalID(resa, app.editingContext());

			NSDictionary dictio = new NSDictionary(new Object[] { reservation }, new Object[] { "reservation" });
			NSNotificationCenter.defaultCenter().postNotification("edtSupprimerReservation", dictio);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * selon le code : on ajoute dans la liste des reservation ou des composants : voir ZoneEditable
	 */
	public void ajouterCreneau(float x, float y, float w, float h, int typeD, NSArray lesChaines, String ccolor, EOGlobalID resa, String salleValide,
			String objetValide, Number deplacer) {
		CaseReservation res = new CaseReservation((int) x, (int) y, (int) h, (int) w, typeD, lesChaines, ccolor, resa, (deplacer.intValue() == 1),
				this);
		if (salleValide != null && salleValide.equals("O")) {
			res.setSalleValide(true);
		}
		if (objetValide != null && objetValide.equals("R")) {
			res.setObjetValide(true);
		}
		res.setResizeable(false);
		if ("H".equals(ccolor) || "C".equals(ccolor) || "z".equals(ccolor)) {
			zoneEditable.addToListeStatique(res);
		}
		else {
			zoneEditable.addToListeReservations(res);
		}
	}

	public void versGauche() {
		edtPlanning.versGauche();
	}

	public void versDroite() {
		edtPlanning.versDroite();
	}

	int cmptBas = 0;

	public void versHaut() {
		if (cmptBas > 0) {
			cmptBas--;
			edtPlanning.versHaut();
			lesJours.versHaut();
		}
	}

	public void versBas() {
		if (cmptBas < 2) {
			cmptBas++;
			edtPlanning.versBas();
			lesJours.versBas();
		}
	}

	class ActionDefilement extends AbstractAction {
		private static final long serialVersionUID = -1588202467535460923L;
		int count = 0;

		/**
		 * Constructeur
		 * 
		 * @param name
		 *            nom de l'action.
		 * @param icon
		 *            icone du bouton.
		 */
		public ActionDefilement(String name, Icon icon) {
			putValue(Action.NAME, name);
			putValue(Action.SMALL_ICON, icon);
		}

		/**
		 * Autre constructeur
		 * 
		 * @param name
		 *            nom de l'action.
		 */
		public ActionDefilement(String name) {
			putValue(Action.NAME, name);
		}

		/**
		 * (methode lancee quand une action est applique au bouton)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btDroite) {
				edtPlanning.versDroite();
			}

			if (e.getSource() == btGauche) {
				edtPlanning.versGauche();
			}

			if ((e.getSource() == btHaut)) {
				versHaut();
			}

			if ((e.getSource() == btBas)) {
				versBas();
			}

			if (e.getSource() == btDefaut) {
				edtPlanning.setDebutVisible(8.0);
			}

			if (e.getSource() == btPlusieursJours) {
				edtListener.setPlusieursJours(!edtListener.getPlusieursJours());
				return;
			}

			edtPlanning.repaint();
		}
	}

	/**
	 * Implementation de l'interface Printable. methode pour imprimer le composant sur papier : juste pour le teste : on utilisera autre
	 * chose pour imprimer.
	 */
	public int print(Graphics g, @SuppressWarnings("unused") PageFormat pf, int index) throws PrinterException {
		if (index == 0) {
			g.translate(50, 50);
			paint(g);
			return Printable.PAGE_EXISTS;
		}
		else {
			return Printable.NO_SUCH_PAGE;
		}
	}

	/** versImprimante : declenche la tache d'impression du composant */
	public void versImprimante() {
		PrinterJob impression = PrinterJob.getPrinterJob();
		PageFormat pformat = impression.pageDialog(impression.defaultPage());

		impression.setPrintable(this, pformat);
		if (impression.printDialog()) {
			try {
				impression.print();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}