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
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.fwkcktlwebapp.common.util.SystemCtrl;
import org.cocktail.superplan.client.composant.LogsHistorisation;
import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.PhotosEmployesGrhum;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.TypeRessource;
import org.cocktail.superplan.client.recherchereservation.ReservationsParPersonne;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eoapplication.EODialogs;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eoapplication.EOSimpleWindowController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOImageView;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.composant.DepositaireObjetCtrl;
import edtscol.client.composant.GestionObjets;
import edtscol.client.composant.LotsDeSallesCtrl;
import edtscol.client.composant.WeekButton;
import edtscol.client.gestioncontraintes.GestionContraintesCtrl;
import edtscol.client.gestionreservation.InspecteurCtrl;
import edtscol.client.impression.ImpressionExportWebDialog;
import edtscol.client.panier.GestionPanier;
import edtscol.client.recherche.Recherche;
import edtscol.client.recherchereservation.RechercheApsCtrl;
import edtscol.client.recherchereservation.ReservationParAp;
import edtscol.client.semainecontroller.SemaineController;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.AProposCtrl;
import fr.univlr.utilities.FileHandler;
import fr.univlr.utilities.ULRDateTimeWindow;

public class MainInterface extends EOInterfaceController {

	public JButton bCalendar, bImprime, bLock, btInspecteur, calUp, calDown, bRefresh, btAlerte, bRessource;
	public JComboBox ppAgent, typePlanning;
	public JTextField tRessource, tCalendrier;
	public PlanningView planningView;
	public EOImageView vPhoto;
	public EODisplayGroup eodPhotoEmploye;
	public MainClient app;
	private EOEditingContext eContext;
	private InspecteurCtrl inspecteurCtrl = null;
	private GestionPlanning gestionPlanning;

	private AppCalendar appCalendar;
	public NSDictionary<String, Object> infos;
	private boolean bloque = true;
	public NSTimestamp debut, fin;

	protected static final Color COLOR_CURRENT_RESOURCE = Color.decode("0xFFBD6F");
	public JPanel panelRessources, panelWeekButtons;

	private NSArray<IndividuUlr> ppIndiv;

	private Number lastPersID;

	EOGenericRecord resRecord = null;

	int msemOrdre = -1, fannKey = -1;
	int typeImpression;

	public IndividuUlr connectedIndividu; // ying ajouter
	public NSArray<StructureUlr> lesStructsSsRsp;
	public boolean etatMpers;
	public NSArray<NSDictionary<String, Object>> infosMpers;
	public NSMutableArray<NSDictionary<String, Object>> lesInfos;

	public boolean nePasAfficherMessage;
	public int choixType;
	public boolean editionDT, impressionHoraires;

	private ButtonGroup groupTypePlanning;

	private WeekButton[] weekButtons = null;

	private Integer gestionPanierTypeRessource;

	// private NSDictionary planningDescription = null;

	public MainInterface(EOEditingContext eContext, MainClient app) {
		super(eContext);
		this.eContext = eContext;
		this.app = app;
	}

	protected void connectionWasEstablished() {
		super.connectionWasEstablished();
	}

	protected void controllerDidLoadArchive(@SuppressWarnings("rawtypes") NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		this.initUI();
		// this.createSubComponents();
		this.init();
	}

	public class WindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			quitter();
		}
	}

	protected void componentDidBecomeVisible() {
		super.componentDidBecomeVisible();
		new MenuHandler(this);
		WindowHandler.getWindowFromController(this).addWindowListener(new WindowListener());
		app.waitingHandler().setMessage("Chargement de votre agenda personnel ...");
		// par defaut on initialise l'agenda de la personne connectee
		Object[] objets = { new Integer(GestionPanier.PERSONNE), (IndividuUlr) app.userInfo("individu") };
		String[] clefs = { "type", "resRecord" };
		infos = new NSDictionary<String, Object>(objets, clefs);

		// lastPersID = ((IndividuUlr)app.userInfo("individu")).persId();

		// =====ying ajouter
		connectedIndividu = (IndividuUlr) app.userInfo("individu");// ying ajouter
		lesStructsSsRsp = connectedIndividu.getStructuresRespOuSecr("AG");
		ppAgent.removeAllItems();
		ppAgent.addItem(connectedIndividu);
		ppAgent.setEnabled(false);
		if (lesStructsSsRsp.count() > 0) {
			initPpAgent();
		}

		// ===fin ying ajouter

		// typePlanning.addActionListener(new typePlanningListener());
		this.processPlanning(infos);
		app.waitingHandler().close();
	}

	public void chooseTypePlanning() {
		Recherche localRecherche = new Recherche(eContext, this, null, Recherche.RECHERCHE);
		EOArchive.loadArchiveNamed("Recherche", localRecherche, "edtscol.client.recherche", localRecherche.disposableRegistry());
		localRecherche.setTypeRessource(getSelectedTypeRessource());
		WindowHandler.runModal(localRecherche, "Rechercher une ressource");
	}

	private TypeRessource getSelectedTypeRessource() {
		int idx = 0;
		for (java.util.Enumeration<AbstractButton> e = groupTypePlanning.getElements(); e.hasMoreElements();) {
			idx++;
			MemorizedButton currentButton = (MemorizedButton) e.nextElement();
			if (currentButton.isStated()) {
				return (TypeRessource) DBHandler.fetchUniqueData(eContext, "TypeRessource", "typeOrdre", new Integer(idx));
			}
		}
		return null;
	}

	private void setTypePlanning(String typePlanning) {
		Recherche localRecherche = new Recherche(eContext, this, null, Recherche.RECHERCHE);
		EOArchive.loadArchiveNamed("Recherche", localRecherche, "edtscol.client.recherche", localRecherche.disposableRegistry());
		localRecherche.setTypeRessource(getSelectedTypeRessource());
		WindowHandler.runModal(localRecherche, "Rechercher une ressource");

		// TYPE_RES_INDIVIDU donc...
		try {
			if ("1".equals(typePlanning) && localRecherche.isSelectionParGroupe() == false) {
				NSArray<NSDictionary<String, Object>> foundResources = localRecherche.selectedRessources(GestionPanier.PERSONNE);
				if (foundResources.count() == 1) {
					Object individu = (foundResources.objectAtIndex(0)).valueForKey("resRecord");
					int itemsCount = ppAgent.getItemCount();
					boolean found = false;
					for (int i = 0; i < itemsCount; i++) {
						if (ppAgent.getItemAt(i).equals(individu)) {
							ppAgent.setSelectedItem(individu);
							found = true;
							break;
						}
					}

					if (!found) {
						ppAgent.setSelectedItem(null);
					}
				}
			}
			else {
				ppAgent.setSelectedItem(null);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPpAgent() {
		NSMutableArray<EOQualifier> arg = new NSMutableArray<EOQualifier>();
		EOQualifier qual = null;
		for (int i = 0; i < lesStructsSsRsp.count(); i++) {
			StructureUlr struc = lesStructsSsRsp.objectAtIndex(i);
			qual = EOQualifier.qualifierWithQualifierFormat(IndividuUlr.REPART_STRUCTURES_KEY + "." + RepartStructure.C_STRUCTURE_KEY + " = %@",
					new NSArray<String>(struc.cStructure()));
			arg.addObject(qual);
		}
		qual = new EOOrQualifier(arg);
		EOSortOrdering nomOrdering = EOSortOrdering.sortOrderingWithKey(IndividuUlr.NOM_USUEL_KEY, EOSortOrdering.CompareAscending);
		ppIndiv = IndividuUlr.fetchIndividuUlrs(eContext, qual, new NSArray<EOSortOrdering>(nomOrdering));

		ppIndiv = DBHandler.retirerMultiples(ppIndiv);

		ppAgent.removeAllItems();
		for (int i = 0; i < ppIndiv.count(); i++) {
			IndividuUlr ind = ppIndiv.objectAtIndex(i);
			ppAgent.addItem(ind);
		}
		ppAgent.setSelectedItem(connectedIndividu);
		ppAgent.setEnabled(true);
		ppAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IndividuUlr ind = (IndividuUlr) ppAgent.getSelectedItem();
				if (ind != null) {
					Object[] objets = { new Integer(GestionPanier.PERSONNE), ind };
					String[] clefs = { "type", "resRecord" };
					infos = new NSDictionary<String, Object>(objets, clefs);
					lesInfos = new NSMutableArray<NSDictionary<String, Object>>();
					processPlanning(infos);
				}
			}
		});
		etatMpers = true;// /+
		NSMutableArray<NSDictionary<String, Object>> lesInfos = new NSMutableArray<NSDictionary<String, Object>>();
		lesInfos.addObject(infos);
		infosMpers = (NSArray<NSDictionary<String, Object>>) lesInfos.clone();// /+
	}

	/** retourne l'instance de InspecteurCtrl */
	public InspecteurCtrl inspecteurCtrl() {
		return inspecteurCtrl;
	}

	/** retourne le calendrier global de l'application */
	public AppCalendar appCalendar() {
		return this.appCalendar;
	}

	/** initialisation, ajout des listeners de notifications ... */
	private void init() {
		NSSelector selector = new NSSelector("receiveResource", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "validerRessource", null);

		selector = new NSSelector("inspecterReservation", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "inspecterReservation", null);
		selector = new NSSelector("edtSupprimerReservation", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "edtSupprimerReservation", null);
		selector = new NSSelector("edtNouvelleReservation", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "edtNouvelleReservation", null);

		selector = new NSSelector("edtDeplacerCreneau", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "edtDeplacerCreneau", null);

		selector = new NSSelector("edtModifierSemaines", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "edtModifierSemaines", null);

		selector = new NSSelector("refreshPlanning", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "refreshPlanning", null);

		selector = new NSSelector("creneauLibreTrouve", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "creneauLibreTrouve", null);

		selector = new NSSelector("edtRechercherCreneaux", new Class[] { NSNotification.class });
		NSNotificationCenter.defaultCenter().addObserver(this, selector, "edtRechercherCreneaux", null);

		gestionPlanning = new GestionPlanning(this, eContext, planningView);

		this.initAppCalendar();
		planningView.initialiser(this);
	}

	/** initialise le calendrier global de l'application */
	private void initAppCalendar() {
		appCalendar = new AppCalendar();
		NSArray<NSTimestamp> range = new NSArray<NSTimestamp>(new NSTimestamp[] { appCalendar.getStartWeekDate(), appCalendar.getEndWeekDate() });
		this.displayDate(range);
	}

	/** affiche les dates de debut & fin de semaine puis rafraichie le planning */
	private void displayDate(NSArray<NSTimestamp> range) {
		debut = range.objectAtIndex(0);
		fin = range.objectAtIndex(1);
		planningView.clearWithDate(debut);
		String debutSemaine = FormatHandler.dateToStr(debut);
		int noSemaine = appCalendar.get(Calendar.WEEK_OF_YEAR);
		app.setNoSemaine(noSemaine);
		String finSemaine = FormatHandler.dateToStr(fin);

		tCalendrier.setText("Semaine " + noSemaine + " - du " + debutSemaine + " au " + finSemaine);

		NSTimestamp leDebut = FormatHandler.midnightTime(debut);
		NSTimestamp laFin = FormatHandler.endOfDay(fin);

		AppCalendar.storeDebutSemaine(leDebut);
		AppCalendar.storeFinSemaine(laFin);

		gestionPlanning.setDateRange(leDebut, laFin);

		updateWeekButtons(noSemaine);

		this.refreshPlanning();
	}

	/** semaine */
	public void nextWeek() {
		WindowHandler.setWaitCursor(this.component());
		NSArray<NSTimestamp> range = appCalendar.nextRangeDateWeek();
		this.displayDate(range);
		WindowHandler.setDefaultCursor(this.component());
	}

	/** recule d'une semaine */
	public void previousWeek() {
		WindowHandler.setWaitCursor(this.component());
		NSArray<NSTimestamp> range = appCalendar.previousRangeDateWeek();
		this.displayDate(range);
		WindowHandler.setDefaultCursor(this.component());
	}

	/** affiche le calendrier pour choisir une date a visualiser */
	public void selectDate(Object sender) {
		new ULRDateTimeWindow(this, sender, "setDate");
	}

	/**
	 * positionne le calendrier de l'application sur la semaine donnee par la date cette methode est appellee par -ULRDateTimeWindow- quand
	 * l'utilisateur choisi une date
	 */
	public void setDate(String stDate) {
		setDate(FormatHandler.strToDate(stDate, "%d/%m/%Y"));
	}

	public void setDate(NSTimestamp date) {
		WindowHandler.setDefaultCursor(this.component());
		NSArray<NSTimestamp> range = appCalendar.rangeDateWeekForDate(date);
		if (range.count() == 2) {
			this.displayDate(range);
		}
		WindowHandler.setDefaultCursor(this.component());
	}

	/** initialisation de l'IHM */
	private void initUI() {
		tRessource.setFont(new Font("Helvetica", Font.PLAIN, 12));
		tRessource.setForeground(new Color(0x00008B));
		tRessource.setBackground(ParamsHandler.C_BACKGRD_E);
		tCalendrier.setBackground(ParamsHandler.C_BACKGRD_E);
		WidgetHandler.decorateButton("", null, "Fleche_droite", calUp);
		WidgetHandler.decorateButton("", null, "Fleche_gauche", calDown);
		WidgetHandler.decorateButton("Calendrier : changer la date", null, "cal", bCalendar);
		WidgetHandler.decorateButton("Afficher l'inspecteur", null, "info", btInspecteur);
		// WidgetHandler.decorateButton("Rechercher une
		// ressource",null,"find",btRecherche);
		WidgetHandler.decorateButton("Débloquer l'EDT graphique", null, "locked", bLock);
		WidgetHandler.decorateButton("Imprimer et exporter les EDT vers le Web", null, "print", bImprime);
		WidgetHandler.decorateButton("Rafraîchir, recharger le planning", null, "run", bRefresh);
		WidgetHandler.decorateButton("Alerte", null, "mail", btAlerte);

		WidgetHandler.decorateButton("Choisir le type de planning a visualiser", null, "open", bRessource);

		// construction des radiobutttons des types de ressources
		EOSortOrdering sortType = EOSortOrdering.sortOrderingWithKey(TypeRessource.TYPE_ORDRE_KEY, EOSortOrdering.CompareAscending);
		NSArray<TypeRessource> typeRes = TypeRessource.fetchAllTypeRessources(eContext, new NSArray<EOSortOrdering>(sortType));

		MemorizedButton button;
		groupTypePlanning = new ButtonGroup();
		RadioButtonRessourceListener radioListener = new RadioButtonRessourceListener();

		panelRessources.setLayout(new FlowLayout());

		for (int i = 0; i < typeRes.count(); i++) {
			TypeRessource aType = typeRes.objectAtIndex(i);
			button = new MemorizedButton(aType.toString());
			button.setStated(i == 0);

			if (i == 0) {
				button.setStated(true);
				button.setBackground(COLOR_CURRENT_RESOURCE);
			}
			else {
				button.setBackground(Color.WHITE);
			}

			// button.setPreferredSize(new Dimension(80,25));
			button.setActionCommand(String.valueOf(aType.typeOrdre().intValue()));
			button.addActionListener(radioListener);
			groupTypePlanning.add(button);
			panelRessources.add(button);
		}
		panelRessources.setBorder(BorderFactory.createEtchedBorder());

		JPanel weekButtonsLine = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 4));
		for (int i = 0; i < weekButtons().length; i++) {
			weekButtonsLine.add(weekButtons()[i]);
		}
		panelWeekButtons.setBorder(BorderFactory.createEmptyBorder());
		panelWeekButtons.setLayout(new BorderLayout());
		panelWeekButtons.add(weekButtonsLine, BorderLayout.CENTER);
	}

	private WeekButton[] weekButtons() {
		if (weekButtons == null) {
			weekButtons = new WeekButton[53];
			ActionListener al = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					goWeek(evt);
				}
			};
			for (int i = 0; i < 53; i++) {
				WeekButton b = new WeekButton();
				b.addActionListener(al);
				weekButtons[i] = b;
			}
		}
		return weekButtons;
	}

	// private WeekButton weekButtonFor(int week) {
	// WeekButton wButton = null;
	// for (int i = 0; i < weekButtons().length; i++) {
	// if (weekButtons()[i].week() == week) {
	// wButton = weekButtons()[i];
	// break;
	// }
	// }
	// return wButton;
	// }

	private void updateWeekButtons(int noSemaine) {
		NSTimestamp date = null;
		if (((Boolean) app.userInfo("anneeCivile")).booleanValue() == false) {
			if (appCalendar.get(Calendar.MONTH) < 7) {
				date = new NSTimestamp(appCalendar.get(Calendar.YEAR) - 1, 8, 1, 0, 0, 0, null);
			}
			else {
				date = new NSTimestamp(appCalendar.get(Calendar.YEAR), 8, 1, 0, 0, 0, null);
			}
		}
		else {
			date = new NSTimestamp(appCalendar.get(Calendar.YEAR), 1, 1, 0, 0, 0, null);
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
		for (int i = 0; i < weekButtons().length; i++) {
			weekButtons()[i].setDate(gc);
			weekButtons()[i].setSelected(gc.get(GregorianCalendar.WEEK_OF_YEAR) == noSemaine);
			gc.add(GregorianCalendar.DATE, 7);
		}
		panelWeekButtons.updateUI();
	}

	private void goWeek(java.awt.event.ActionEvent evt) {
		if (evt.getSource() == null) {
			return;
		}
		WeekButton b = (WeekButton) evt.getSource();
		setDate(new NSTimestamp(b.date().getTime()));
	}

	private void effacerPhoto() {
		vPhoto.setImage(null);
		vPhoto.setVisible(false);
	}

	/** afficher la photo */
	private void afficherPhoto(IndividuUlr recIndividu, boolean vf) throws Exception {

		if (vf && recIndividu != null && "O".equals(recIndividu.indPhoto())) {

			Number noInd = (Number) app.primaryKeyFromEO(recIndividu, IndividuUlr.NO_INDIVIDU_KEY);
			EOQualifier qual = DBHandler.getSimpleQualifier(IndividuUlr.NO_INDIVIDU_KEY, noInd);
			NSArray<PhotosEmployesGrhum> data = PhotosEmployesGrhum.fetchPhotoEmployes(eContext, qual, null);

			if (data != null && data.count() > 0) {
				NSData lesDonnees = data.objectAtIndex(0).image();
				if (lesDonnees != null) {
					ImageIcon image = new ImageIcon(lesDonnees.bytes());
					vPhoto.setVisible(true);
					vPhoto.setImage(image.getImage());
					vPhoto.validate();
				}
			}
			else {
				vPhoto.setVisible(false);
			}
		}
		else {
			vPhoto.setVisible(false);
		}

	}

	/**
	 * callback pour les notifications des sous-composants de -Recherche- : EnseignementController ...
	 */
	public void receiveResource(NSNotification notification) {
		infos = (NSDictionary<String, Object>) notification.object();
		this.processPlanning(infos);
	}

	public NSDictionary<String, Object> getInformations() {
		return infos;
	}

	// CM méthode pour récupérer les groupes
	public NSMutableArray<NSDictionary<String, Object>> getLesInfos() {
		return lesInfos;
	}

	/** declenche le calcul et affichage du planning */
	// ANCHOR
	public void processPlanning(NSDictionary<String, Object> informations) {

		try {
			if (lesInfos == null || lesInfos.count() == 0) {
				lesInfos = new NSMutableArray<NSDictionary<String, Object>>();
				lesInfos.addObject(informations);
			}

			NSDictionary<String, Object> planningDescription = lesInfos.objectAtIndex(0);
			gestionPanierTypeRessource = (Integer) planningDescription.objectForKey("type");
			resRecord = (EOGenericRecord) planningDescription.objectForKey("resRecord");

			WindowHandler.setWaitCursor(this.component());
			if (lesInfos.count() == 1) {
				switch (gestionPanierTypeRessource.intValue()) {
				case GestionPanier.ENSEIGNEMENT:
					effacerPhoto();
					typeImpression = 1; // COURS
					tRessource.setText((informations.hashtable().containsKey("libelle")) ? (String) informations.objectForKey("libelle") : "");
					msemOrdre = ((Number) informations.objectForKey("type")).intValue();
					fannKey = ((Number) informations.objectForKey("anneeScol")).intValue();
					break;

				case GestionPanier.SALLE:
					effacerPhoto();
					typeImpression = 2; // AUTRE
					tRessource.setText("Salle : " + resRecord.valueForKey("salPorte") + " Bat. " + resRecord.valueForKeyPath("local.appellation"));
					msemOrdre = -1;
					break;

				case GestionPanier.GROUPE_SCOL:
					effacerPhoto();
					typeImpression = 1; // AUTRE mais comme COURS
					tRessource.setText((informations.hashtable().containsKey("libelle")) ? (String) informations.objectForKey("libelle") : "");
					msemOrdre = -1;
					break;

				case GestionPanier.OBJET:
					effacerPhoto();
					typeImpression = 2; // AUTRE
					tRessource.setText("Objet : " + resRecord.valueForKeyPath("resaTypeObjet.rtoLibelle") + " - "
							+ resRecord.valueForKey("roLibelle1"));
					msemOrdre = -1;
					break;

				case GestionPanier.PERSONNE:
					typeImpression = 2; // AUTRE
					IndividuUlr individu = (IndividuUlr) resRecord;
					if (lastPersID == null || individu.persId().intValue() != lastPersID.intValue()) {
						lastPersID = individu.persId();

						try {
							this.afficherPhoto(individu, true);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}

					String nom = StringCtrl.capitalize(individu.prenom()) + " " + individu.nomUsuel();
					tRessource.setText("Personne : " + nom);
					msemOrdre = -1;
					break;
				}
			}

			if (lesInfos.count() == 1) {
				gestionPlanning.processPlanning(planningDescription);
			}
			// Cas où il y'a superposition de plusieurs plannings
			else {
				if (lesInfos.count() > 1) {
					NSDictionary<String, Object> unInfo = null;
					switch (gestionPanierTypeRessource.intValue()) {
					case 0:
						NSMutableArray<IndividuUlr> individusChoisis = new NSMutableArray<IndividuUlr>();
						for (int i = 0; i < lesInfos.count(); i++) {
							unInfo = lesInfos.objectAtIndex(i);
							individusChoisis.addObject((IndividuUlr) unInfo.objectForKey("resRecord"));
						}
						planningOfIndividus(individusChoisis);
						break;
					case 1:
						NSMutableArray<Salles> sallesChoisies = new NSMutableArray<Salles>();
						for (int i = 0; i < lesInfos.count(); i++) {
							unInfo = lesInfos.objectAtIndex(i);
							sallesChoisies.addObject((Salles) unInfo.objectForKey("resRecord"));
						}
						planningOfSalles(sallesChoisies);
						break;
					case 4:
						NSMutableArray<ResaObjet> objetsChoisis = new NSMutableArray<ResaObjet>();
						for (int i = 0; i < lesInfos.count(); i++) {
							unInfo = lesInfos.objectAtIndex(i);
							objetsChoisis.addObject((ResaObjet) unInfo.objectForKey("resRecord"));
						}
						planningOfObjets(objetsChoisis);
						break;
					default:
						break;
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			app.sendMailException(e);
		}

		WindowHandler.setDefaultCursor(this.component());
	}

	/** notification */
	public void refreshPlanning(NSNotification notif) {
		this.refreshPlanning();
	}

	/**
	 * rafraichie le planning, si les infos sont disponibles invoquee a partir de -InspecteurCtrl-
	 */
	public void refreshPlanning() {
		WindowHandler.setWaitCursor(this.component());

		if (infos != null) {
			this.processPlanning(infos);
		}
		WindowHandler.setDefaultCursor(this.component());
	}

	public void inspecterReservation(NSNotification notification) {
		NSDictionary<String, Object> dicoNotifier = (NSDictionary<String, Object>) notification.object();
		Reservation resa = (Reservation) dicoNotifier.objectForKey("reservation");
		if (resa != null) {
			if (inspecteurCtrl() != null) {
				inspecteurCtrl().inspectReservation(resa);
				inspecteurCtrl().open();
			}
			else {
				inspecteurCtrl = new InspecteurCtrl(eContext, this, resa, app.isInspecteurModal());
				inspecteurCtrl().open();
			}
		}
	}

	public void edtSupprimerReservation(NSNotification notification) throws Exception {
		NSDictionary<String, Object> dicoNotifier = (NSDictionary<String, Object>) notification.object();
		Reservation resa = (Reservation) dicoNotifier.objectForKey("reservation");
		if (resa != null) {
			if (inspecteurCtrl() != null) {
				inspecteurCtrl().inspectReservation(resa);
			}
			else {
				inspecteurCtrl = new InspecteurCtrl(eContext, this, resa, app.isInspecteurModal());
			}
			inspecteurCtrl().supprimer();
		}
	}

	public void edtNouvelleReservation(NSNotification notification) throws Exception {
		WindowHandler.setWaitCursor(this.component());

		NSDictionary<String, Object> dicoNotifier = (NSDictionary<String, Object>) notification.object();
		String time = (String) dicoNotifier.objectForKey("time");
		Integer jour = (Integer) dicoNotifier.objectForKey("jour");

		if (InspecteurCtrl.isOpen() == true) {
			inspecteurCtrl().close();
		}
		inspecteurCtrl = new InspecteurCtrl(eContext, this, time, jour, appCalendar.get(Calendar.WEEK_OF_YEAR), app.isInspecteurModal());
		inspecteurCtrl().open();

		WindowHandler.setDefaultCursor(this.component());
	}

	public void edtDeplacerCreneau(NSNotification notification) throws Exception {
		WindowHandler.setWaitCursor(this.component());

		NSDictionary<String, Object> dicoNotifier = (NSDictionary<String, Object>) notification.object();
		String time = (String) dicoNotifier.objectForKey("time");
		Integer jour = (Integer) dicoNotifier.objectForKey("jour");
		Reservation reservation = (Reservation) eContext.faultForGlobalID((EOGlobalID) dicoNotifier.objectForKey("reservation"), eContext);

		// verification si modification possible...
		if (!app.aDroitModification(reservation)) {
			WindowHandler.showError("Vous n'avez pas les droits de modifier cette réservation.");
			WindowHandler.setDefaultCursor(this.component());
			this.refreshPlanning();
			return;
		}

		NSArray<String> elementsTime = NSArray.componentsSeparatedByString(time, ":");

		int heDeb = (new Integer(elementsTime.objectAtIndex(0))).intValue();
		int miDeb = (new Integer(elementsTime.objectAtIndex(1))).intValue();

		int heFin = (new Integer(elementsTime.objectAtIndex(2))).intValue();
		int miFin = (new Integer(elementsTime.objectAtIndex(3))).intValue();

		DBHandler.invalidateObject(eContext, reservation);

		int dayOfWeek = DayList.getSelectedDay(jour.intValue());

		ModifierPeriodicites modifPeriods = new ModifierPeriodicites(eContext, reservation, dayOfWeek, heDeb, miDeb, heFin, miFin);
		if (reservation != null && reservation.periodicites() != null && reservation.periodicites().count() == 1) {
			if (WindowHandler.showConfirmation("Vous confirmez le déplacement ?")) {
				modifPeriods.valider();
			}
			else {
				refreshPlanning();
			}
			WindowHandler.setDefaultCursor(this.component());
		}
		else {
			WindowHandler.run(modifPeriods, "Deplacement de creneaux");
		}
	}

	public void edtModifierSemaines(NSNotification notification) throws Exception {
		NSDictionary<String, Object> dicoNotifier = (NSDictionary<String, Object>) notification.object();
		Reservation resa = (Reservation) dicoNotifier.objectForKey("reservation");
		Frame frame = (Frame) ((EOSimpleWindowController) this.supercontroller()).window();

		boolean adroit = app.aDroitModification(resa);

		new SemaineController(frame, resa, adroit);
	}

	/**
	 * notification venant de RechercheCreaux, l'utilisateur choisir de creer une resa a partir d'un creneau libre trouve
	 */
	public void creneauLibreTrouve(NSNotification notification) throws Exception {
		if (InspecteurCtrl.isOpen() == true) {
			inspecteurCtrl().close();
		}
		inspecteurCtrl = new InspecteurCtrl(eContext, this, false);
		inspecteurCtrl().open();
		inspecteurCtrl().creneauLibreTrouve((NSDictionary<String, Object>) notification.object());
	}

	/** lance l'affichage des creneaux libres */
	public void edtRechercherCreneaux(NSNotification notification) {

		NSDictionary<String, Object> dicoNotifier = (NSDictionary<String, Object>) notification.object();
		Integer dureeMin = (Integer) dicoNotifier.objectForKey("duree");
		EOGlobalID gidResa = (EOGlobalID) dicoNotifier.objectForKey("reservation");

		int x = ((Integer) dicoNotifier.objectForKey("x")).intValue();
		int y = ((Integer) dicoNotifier.objectForKey("y")).intValue();
		Reservation reservation = (Reservation) eContext.faultForGlobalID((EOGlobalID) dicoNotifier.objectForKey("reservation"), eContext);

		PeriodicitesResController periodicitesResController = new PeriodicitesResController(this, x, y, reservation);
		periodicitesResController.setVisible(true);

		NSArray<NSTimestamp> selectedSemaines = periodicitesResController.getSelectedSemaines();

		if (selectedSemaines == null || selectedSemaines.count() == 0) {
			return;
		}

		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSArray<NSDictionary<String, Object>> creneaux = (NSArray<NSDictionary<String, Object>>) objectStore.invokeRemoteMethodWithKeyPath(eContext,
				"session", "clientSideRequestFindCreneauxLibresForResa", new Class[] { EOGlobalID.class, NSArray.class, Integer.class },
				new Object[] { gidResa, selectedSemaines, dureeMin }, false);

		if (creneaux.count() > 0) {
			planningView.afficherPlanningGenerique(creneaux, false);
		}
	}

	/** bloquerDebloquerEdt */
	public void bloquerDebloquerEdt() {
		planningView.enableCreation();
		bloque = !bloque;
		if (bloque) {
			WidgetHandler.decorateButton("Débloquer l'EDT graphique (passer en mode création)", null, "locked", bLock);
		}
		else {
			WidgetHandler.decorateButton("Bloquer l'EDT graphique (passer en mode déplacement)", null, "unlocked", bLock);
		}

	}

	public void rechercheReservationsParAp() {
		ReservationParAp rechercheParAp = new ReservationParAp(eContext, this);
		WindowHandler.run(rechercheParAp, "Recherche des réservations par Atomes Pédagogiques");
	}

	public void lancerRechercheAps() {
		RechercheApsCtrl.sharedInstance(WindowHandler.getWindowFromController(this), eContext).open();
	}

	public void rechercheReservationsParPersonne() {
		// ReservationParPersonne rechercheParPers = new ReservationParPersonne(eContext);
		// WindowHandler.run(rechercheParPers, "Recherche des réservations par Personne");
		ReservationsParPersonne.sharedInstance(WindowHandler.getWindowFromController(this), eContext).open();
	}

	public void lancerConsultationLogsHistorisation() {
		if (app.isDroitConsultationLogsHistorisation()) {
			LogsHistorisation.sharedInstance(WindowHandler.getWindowFromController(this)).open();
		}
		else {
			WindowHandler.showInfo("Vous n'avez pas les droits pour consulter ces logs.");
		}
	}

	/** Recherche des creneaux libres */
	public void rechercheCreneauxLibres() {
		RechercheCreneaux rechercheCreneaux = new RechercheCreneaux(eContext, this);
		WindowHandler.run(rechercheCreneaux, "Recherche des créneaux libres");
	}

	public void gererContraintes() {
		GestionContraintesCtrl.sharedInstance(eContext, WindowHandler.getWindowFromController(this)).open();
	}

	/** ouvre le paneau -VacancesScolaires- de gestion des vacances scolaires */
	public void gererVacancesScolaires() {
		WindowHandler.setWaitCursor(this.component());

		GestionVacances vacancesCtr = new GestionVacances(eContext);
		WindowHandler.run(vacancesCtr, "Gestion des Vacances Scolaires");

		WindowHandler.setDefaultCursor(this.component());
	}

	/** ouvre le paneau -DepositairesSalles- des depositaires de salles */
	public void ouvrirDepositaireSalles() {
		WindowHandler.setWaitCursor(this.component());

		SalleFactory salleFactory = new SalleFactory(eContext);
		if (salleFactory.estDepositaire((IndividuUlr) app.userInfo("individu"))) {
			DepositairesSalles deposSalles = new DepositairesSalles(eContext);
			WindowHandler.run(deposSalles, "Dépositaire des Salles : Gestion/Validation/Affectation");
		}
		else {
			WindowHandler.showInfo("Vous ne faites pas partie d'un groupe dépositaire.\nVeuillez contacter le STU pour avoir ces droits.");
		}

		WindowHandler.setDefaultCursor(this.component());
	}

	/** lance l'inspecteur */
	public void inspecteur() {

		WindowHandler.setWaitCursor(this.component());

		if (InspecteurCtrl.isOpen() == true) {
			inspecteurCtrl().open();
			WindowHandler.setDefaultCursor(this.component());
			return;
		}
		inspecteurCtrl = new InspecteurCtrl(eContext, this, app.isInspecteurModal());
		inspecteurCtrl().open();
		inspecteurCtrl().nouvelleReservation();

		WindowHandler.setDefaultCursor(this.component());
	}

	/** declenche la generation de documents pdf d'EDT */
	public void setChoixEdition(int choixType, boolean editionDT, boolean impressionHoraires) {
		this.choixType = choixType;
		this.editionDT = editionDT;
		this.impressionHoraires = impressionHoraires;
	}

	public void imprimer() {
		if (tRessource.equals("ENSEIGNEMENT") == false && lesInfos.count() > 1) {
			NSArray<NSArray<NSDictionary<String, Object>>> resas = gestionPlanning.getPlans();
			if (resas == null) {
				resas = new NSArray<NSArray<NSDictionary<String, Object>>>();
			}
			new Imprime(tRessource.getText(), tCalendrier.getText(), resas, debut, infos.valueForKey("resRecord"), true);
			return;
		}

		if (tRessource.equals("ENSEIGNEMENT") == true || lesInfos.count() <= 1) {
			gestionPlanning.setPlans(null);
		}

		ImpressionExportWebDialog impExportWebCtrl = new ImpressionExportWebDialog(this);

		impExportWebCtrl.initWithWeek(new Integer(app.noSemaine()));
		int typeRes = gestionPanierTypeRessource.intValue();
		if (typeRes == GestionPanier.ENSEIGNEMENT || typeRes == GestionPanier.GROUPE_SCOL) {
			impExportWebCtrl.setEnableExporting(true);
		}
		else {
			impExportWebCtrl.setEnableExporting(false);
		}

		impExportWebCtrl.setVisible(true);
	}

	public void exportExcel() {
		app.waitingHandler().setMessage("Création d'un fichier Excel en cours ...");
		try {
			EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
			NSData pdfData = (NSData) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestGetExcelExport", new Class[] {
					String.class, String.class, NSTimestamp.class }, new Object[] { tCalendrier.getText(), tRessource.getText(), debut }, false);
			// PdfManager pdfManager= new PdfManager();
			FileHandler fileHandler = new FileHandler();
			if (fileHandler != null) {
				String fname;
				if (msemOrdre == -1) {
					fname = "Edt-" + msemOrdre + "-" + app.noSemaine();
				}
				else {
					fname = "Edt";
				}
				String filePath = fileHandler.dataToXXX(pdfData, fname, "xls");
				try {
					fileHandler.openFile(filePath);
				}
				catch (Exception e) {
					System.out.println("Erreur à l'ouverture du fichier :" + e.getMessage());
				}
			}
		}
		catch (Exception e) {
			app.waitingHandler().close();
			e.printStackTrace();
		}
		app.waitingHandler().close();
	}

	public void exportWeb() {
		imprimer();
	}

	public void afficherManuel() {
	}

	public void afficherViewlet() {
		WindowHandler.setWaitCursor(this.component());
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		String urlViewlet = (String) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestGetUrlViewlet", null, null,
				false);
		if (urlViewlet == null) {
			WindowHandler.setDefaultCursor(this.component());
			return;
		}

		String retour = SystemCtrl.openFileInBrowser(urlViewlet);
		if (retour != null) {
			EODialogs.runErrorDialog("Erreur d'ouverture", "retour");
		}

		WindowHandler.setDefaultCursor(this.component());
	}

	public void afficherPreferencesInterface() {
		new PreferencesCtrl(eContext, WindowHandler.getWindowFromController(app.mainInterface()), true).open();
	}

	public void afficherPreferencesHabilitation() {
		// PrefDiplome prefDiplome = new PrefDiplome(eContext);
		// WindowHandler.run(prefDiplome, "Preferences diplomes");
		new PreferencesDiplomeCtrl(eContext, WindowHandler.getWindowFromController(app.mainInterface()), true).open();
	}

	public void afficherAPropos() {
		new AProposCtrl(app.getServerApplicationInfos(), WindowHandler.getJFrameFromController(this));
	}

	/** quitter */
	protected void quitter() {
		if (WindowHandler.showConfirmation("Voulez-vous vraiement quitter l'application ?")) {
			app.setCanQuit(true);
			app.quit();
		}
	}

	/* ================ying ajouter================ */
	public void alerte() {
		WindowHandler.setWaitCursor(this.component());
		NSArray<NSDictionary<String, Object>> listResa = gestionPlanning.getPlanningIndividu(connectedIndividu);
		Alerte alerteWindow = new Alerte(this);
		alerteWindow.afficher(listResa);

		WindowHandler.setDefaultCursor(this.component());
	}

	public void alerter(NSTimestamp resaDebut, String resaMotif, int tempo) {
		((EODistributedObjectStore) EOEditingContext.defaultParentObjectStore()).invokeRemoteMethodWithKeyPath(eContext, "session",
				"clientSideRequestActiverAlerte", new Class[] { String.class, String.class, String.class, NSTimestamp.class, Integer.class },
				new Object[] { app.getEmailIndividu(connectedIndividu), app.getEmailIndividu(connectedIndividu), resaMotif, resaDebut,
						new Integer(tempo) }, true);
		EODialogs.runInformationDialog("Notification", "Vous serez notifié(e) par Email " + String.valueOf(tempo)
				+ " minutes avant le début de cette réservation");
	}

	public void planningOfSalles(NSArray<Salles> sallesChoisies) {
		String salle = "";
		bImprime.setEnabled(true);
		int nbrSallesChoisies = sallesChoisies.count();
		etatMpers = true;
		btAlerte.setEnabled(false);
		// if(nbrSallesChoisies>1) bImprime.setEnabled(false);
		lesInfos = new NSMutableArray<NSDictionary<String, Object>>();
		for (int i = 0; i < nbrSallesChoisies; i++) {
			Salles laSalle = sallesChoisies.objectAtIndex(i);
			salle += StringCtrl.capitalize(laSalle.salPorte());
			if (i < nbrSallesChoisies - 1) {
				salle += ", ";
			}
			Object[] objets = { new Integer(GestionPanier.SALLE), laSalle };
			String[] clefs = { "type", "resRecord" };
			NSDictionary<String, Object> unInfos = new NSDictionary<String, Object>(objets, clefs);
			lesInfos.addObject(unInfos);
			// tabSalles.addObject(laSalle.salNumero());
		}
		if (nbrSallesChoisies > 0) {
			tRessource.setText("Planning de : " + salle);
			typeImpression = 2; // AUTRE
			msemOrdre = -1;
			gestionPlanning.processPlanningMultiples(lesInfos);
		}
	}

	public void planningOfObjets(NSArray<ResaObjet> objetsChoisis) {
		String objet = "";
		bImprime.setEnabled(true);
		int nbrObjetsChoisis = objetsChoisis.count();
		etatMpers = true;
		btAlerte.setEnabled(false);
		// if(nbrObjetsChoisis>1) bImprime.setEnabled(false);
		lesInfos = new NSMutableArray<NSDictionary<String, Object>>();
		for (int i = 0; i < nbrObjetsChoisis; i++) {
			ResaObjet lObjet = objetsChoisis.objectAtIndex(i);
			objet += StringCtrl.capitalize(lObjet.roLibelle1());
			if (i < nbrObjetsChoisis - 1) {
				objet += ", ";
			}
			Object[] objets = { new Integer(GestionPanier.OBJET), lObjet };
			String[] clefs = { "type", "resRecord" };
			NSDictionary<String, Object> unInfo = new NSDictionary<String, Object>(objets, clefs);
			lesInfos.addObject(unInfo);
		}
		if (nbrObjetsChoisis > 0) {
			tRessource.setText("Planning de : " + objet);
			typeImpression = 2; // AUTRE
			msemOrdre = -1;
			gestionPlanning.processPlanningMultiples(lesInfos);
		}
	}

	public void planningOfIndividus(NSArray<IndividuUlr> individusChoisis) {
		String personne = "";
		bImprime.setEnabled(true);
		int nbrIndividusChoisis = individusChoisis.count();
		etatMpers = true;
		btAlerte.setEnabled(false);
		// if(nbrIndividusChoisis>1) bImprime.setEnabled(false);
		lesInfos = new NSMutableArray<NSDictionary<String, Object>>();
		for (int i = 0; i < nbrIndividusChoisis; i++) {
			IndividuUlr individu = individusChoisis.objectAtIndex(i);
			personne += StringCtrl.capitalize(individu.prenomNom());
			if (i < nbrIndividusChoisis - 1) {
				personne += ", ";
			}
			Object[] objets = { new Integer(GestionPanier.PERSONNE), individu };
			String[] clefs = { "type", "resRecord" };
			NSDictionary<String, Object> unInfos = new NSDictionary<String, Object>(objets, clefs);
			lesInfos.addObject(unInfos);
			// tabSalles.addObject(laSalle.salNumero());
		}
		if (nbrIndividusChoisis > 0) {
			tRessource.setText("Planning de : " + personne);
			typeImpression = 2; // AUTRE
			msemOrdre = -1;
			gestionPlanning.processPlanningMultiples(lesInfos);
		}
	}

	public void afficherHComp() {
		HCompCtrl hcompCtrl = new HCompCtrl(eContext);
		WindowHandler.runModal(hcompCtrl, "Consultation - Validation d'Heures Comp.");
	}

	public void afficherRecopieEdt() {
		if (WindowHandler
				.showConfirmation("Avez-vous recopi\u00e9 les groupes dans l'application des groupes ?\nOu souhaitez-vous ne pas recopier la ressource 'groupe' des EDT ?")) {

			RecopieEdtCtrl recopieCtrl = new RecopieEdtCtrl(eContext);
			WindowHandler.runModal(recopieCtrl, "Recopie d'emploi du temps");
		}
	}

	public void afficherGestionCouleurEc() {
		ECColorController ecColorCtrl = new ECColorController(eContext, this);
		WindowHandler.runModal(ecColorCtrl, "Gestion des couleur d'affichage des EC");
	}

	public void lancerGestionObjets() {
		// Boolean gestionObjets = (Boolean) app.userInfo("gestionObjets");
		// if (gestionObjets.booleanValue()) {
		new GestionObjets(app, WindowHandler.getWindowFromController(this), eContext).open();
		// }
		// else {
		// WindowHandler.showError("Droits insuffisants.\nVous ne figurez pas dans le groupe des personnes pouvant créer ou modifier des objets.");
		// }
	}

	public void lancerDepositairesObjets() {
		new DepositaireObjetCtrl(WindowHandler.getWindowFromController(this), eContext).open();
	}

	public void lancerGestionLotsSalles() {
		new LotsDeSallesCtrl(WindowHandler.getWindowFromController(this), eContext).open();
	}

	private void revealButtonInGroup(JComponent button, ButtonGroup bg) {
		for (java.util.Enumeration<AbstractButton> e = bg.getElements(); e.hasMoreElements();) {
			MemorizedButton currentButton = (MemorizedButton) e.nextElement();
			if (button == currentButton) {
				currentButton.setBackground(COLOR_CURRENT_RESOURCE);
				currentButton.setStated(true);
			}
			else {
				currentButton.setBackground(Color.WHITE);
				currentButton.setStated(false);
			}
		}
	}

	private class RadioButtonRessourceListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			revealButtonInGroup((MemorizedButton) e.getSource(), groupTypePlanning);
			setTypePlanning(e.getActionCommand());
		}

	}

	public GestionPlanning gestionPlanning() {
		return gestionPlanning;
	}

	public void setGestionPlanning(GestionPlanning gestionPlanning) {
		this.gestionPlanning = gestionPlanning;
	}

	public String currentResourceLibelle() {
		return tRessource.getText();
	}

}
