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
package edtscol.client.gestionreservation;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.composant.LogsHistorisation;
import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.factory.ExamenFactory;
import org.cocktail.superplan.client.factory.ExpertFactory;
import org.cocktail.superplan.client.factory.GenericFactory;
import org.cocktail.superplan.client.factory.ReunionFactory;
import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.factory.VerificationFactory;
import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.HcompRec;
import org.cocktail.superplan.client.metier.HoraireCode;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.LogReservation;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteEc;
import org.cocktail.superplan.client.metier.MaquetteRepartitionAp;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.ResaExamen;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ReservationObjet;
import org.cocktail.superplan.client.metier.ReservationSemestre;
import org.cocktail.superplan.client.metier.SalleSouhaitee;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.StructuresAutorisees;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOSimpleWindowController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

import edtscol.client.DayList;
import edtscol.client.MailReservation;
import edtscol.client.MainClient;
import edtscol.client.MainInterface;
import edtscol.client.ParamsHandler;
import edtscol.client.PreferencesCtrl;
import edtscol.client.panier.GestionPanier;
import edtscol.client.panier.Panier;
import edtscol.client.recherche.MatiereExtController;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;

public class InspecteurCtrl {

	private static boolean IS_OPEN = false;

	private InspecteurView myView;

	public static final int TYPE_RESA_ENSEIGNEMENT = 0;
	public static final int TYPE_RESA_REUNION = 1;
	public static final int TYPE_RESA_EXAMEN = 2;
	public static final int TYPE_RESA_BLOCAGE = 3;
	public static final int TYPE_RESA_REUNION_ETUDIANT = 4;

	public static final int VISIBILITE_SERVICE = 0;
	public static final int VISIBILITE_PUBLIC = 1;
	public static final int VISIBILITE_PRIVE = 2;

	public static final int CREATION = 100;
	public static final int EDITION = 200;

	public static final int MAIL_CREATION = 20;
	public static final int MAIL_MODIFICATION = 21;
	public static final int MAIL_SUPPRESSION = 22;

	boolean resaSucceed = false;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	public DayList dayList = new DayList();
	private EOEditingContext eContext;
	private MainInterface main;
	private Panier panier;
	private Integer jour;
	private int semaine;
	private String time = null;
	// private Reservation resa = null;

	public TimeHandler timeHandler;

	private GenericFactory genericFactory;
	private EnseignementFactory ensFactory;
	public ReunionFactory reunionfactory;
	private SalleFactory salleFactory;

	private NSArray<NSDictionary<String, Periodicite>> periodicitesModif;
	private boolean toutesPeriodicites;

	private Reservation reservation;
	private boolean modificationEnabled = false;

	public InspecteurCtrl(EOEditingContext eContext, MainInterface main, boolean modal) {
		this.eContext = eContext;
		this.main = main;
		myView = new InspecteurView((Frame) ((EOSimpleWindowController) main.supercontroller()).window(), modal);
		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());
		genericFactory = new GenericFactory(eContext);
		ensFactory = new EnseignementFactory(eContext);
		reunionfactory = new ReunionFactory(eContext);
		salleFactory = new SalleFactory(eContext);
		init(null);
	}

	public InspecteurCtrl(EOEditingContext eContext, MainInterface main, Reservation resa, boolean modal) {
		this.eContext = eContext;
		this.main = main;
		myView = new InspecteurView((Frame) ((EOSimpleWindowController) main.supercontroller()).window(), modal);
		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());
		genericFactory = new GenericFactory(eContext);
		ensFactory = new EnseignementFactory(eContext);
		reunionfactory = new ReunionFactory(eContext);
		salleFactory = new SalleFactory(eContext);
		init(resa);
	}

	public InspecteurCtrl(EOEditingContext eContext, MainInterface main, String time, Integer jour, int semaine, boolean modal) {
		this.eContext = eContext;
		this.main = main;
		myView = new InspecteurView((Frame) ((EOSimpleWindowController) main.supercontroller()).window(), modal);
		this.jour = jour;
		this.semaine = semaine;
		this.time = time;
		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());
		genericFactory = new GenericFactory(eContext);
		ensFactory = new EnseignementFactory(eContext);
		reunionfactory = new ReunionFactory(eContext);
		salleFactory = new SalleFactory(eContext);
		init(null);
	}

	public void open() {
		myView.setLocation(WindowHandler.getWindowFromController(main).getX() + 20, WindowHandler.getWindowFromController(main).getY() + 20);
		IS_OPEN = true;
		myView.setVisible(true);
	}

	public void close() {
		IS_OPEN = false;
		myView.setVisible(false);
	}

	private void init(Reservation resa) {
		myView.getDayListPanel().removeAll();
		myView.getDayListPanel().add(dayList, BorderLayout.CENTER);

		if (panier == null) {
			panier = new Panier(eContext, main);
		}
		// CM modif edtscol - on passe au panier le pere
		panier.init(this);

		myView.getVuePanier().removeAll();
		myView.getVuePanier().add(panier.currentView(), BorderLayout.CENTER);

		myView.getTNumero().setBackground(ParamsHandler.C_BACKGRD_NO);
		myView.getTNumero().setToolTipText(null);
		WidgetHandler.decorateButton("Consulter l'historique de cette réservation...", null, "minisave", myView.getBtHistorique());
		WidgetHandler.decorateButton("Commencer une nouvelle réservation", null, "new", myView.getBNouveau());
		WidgetHandler.decorateButton("Enregistrer la réservation", null, "save", myView.getBEnregistrer());
		WidgetHandler.decorateButton("Modifier la réservation", null, "reload", myView.getBModifier());
		WidgetHandler.decorateButton("Dupliquer la réservation", null, "new", myView.getBDuppliquer());
		WidgetHandler.decorateButton("Supprimer la réservation", null, "delete", myView.getBSupprimer());

		myView.getBtHistorique().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				consulterHistorique();
			}
		});
		myView.getBNouveau().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nouvelleReservation();
			}
		});
		myView.getBEnregistrer().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				valider();
			}
		});
		myView.getBModifier().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				modifier();
			}
		});
		myView.getBDuppliquer().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				duppliquer();
			}
		});
		myView.getBSupprimer().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				supprimer();
			}
		});

		NSArray<FormationAnnee> formationAnnee = app.getFormationAnnees();
		myView.getComboAnnees().removeAllItems();
		FormationAnnee currentWorking = app.getCurrentWorkingAnnee();

		// boolean useAnneeCivile = ((Boolean) app.userInfo("anneeCivile")).booleanValue();
		for (int i = 0; i < formationAnnee.count(); i++) {
			FormationAnnee fAnnee = formationAnnee.objectAtIndex(i);
			myView.getComboAnnees().addItem(fAnnee);
			if (currentWorking == null && fAnnee.fannCourante().equals("O")) {
				myView.getComboAnnees().setSelectedItem(fAnnee);
			}
		}
		if (currentWorking != null) {
			myView.getComboAnnees().setSelectedItem(currentWorking);
		}
		myView.getComboAnnees().addActionListener(new JComboListener());

		MatrixListener myMatrixListener = new MatrixListener();
		Enumeration<AbstractButton> e = myView.getMatTypeResa().getElements();
		while (e.hasMoreElements()) {
			e.nextElement().addActionListener(myMatrixListener);
		}

		TimeFocusListener timeFocusListener = new TimeFocusListener();
		myView.getTMinDeb().addFocusListener(timeFocusListener);
		myView.getTMinFin().addFocusListener(timeFocusListener);
		myView.getTMinDuree().addFocusListener(timeFocusListener);

		myView.getCbExpert().setSelected(false);
		myView.getCbExpert().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setModeExpert(myView.getCbExpert().isSelected());
			}
		});
		setModeExpert(false);
		// FIXME pdm cbExpert à mettre en place quand ce sera ok, pour l'instant on ne montre pas...
		// myView.getCbExpert().setVisible(false);

		changeDispositionPanier();

		if (time != null) {
			commencerNouvelleReservation(time, jour, semaine);
		}
		if (resa != null) {
			inspectReservation(resa);
		}

		String dtsamEmail = (String) app.invokeRemoteMethod(eContext, "session", "clientSideRequestDTSAMEmail", null);
		if (dtsamEmail == null || dtsamEmail.trim().length() == 0) {
			myView.getCkDt().setVisible(false);
		}
		myView.getCkDt().setSelected(false);

		myView.addWindowListener(new WindowListener());
	}

	public Reservation getReservation() {
		return reservation;
	}

	public EOEditingContext editingContext() {
		return eContext;
	}

	public void setModeExpert(boolean modeExpert) {
		myView.getLabelHrDebut().setText(modeExpert ? "Entre" : "Heures de");
		myView.getLabelHrFin().setText(modeExpert ? "et" : "à");
		myView.getLabelDuree().setVisible(modeExpert);
		myView.getLabelDuree2().setVisible(modeExpert);
		myView.getTHrDuree().setVisible(modeExpert);
		myView.getTMinDuree().setVisible(modeExpert);
	}

	/** teste les saisies des dates, heures, semaines etc */
	public static boolean testEntries(String hdeb, String mdeb, String hfin, String mfin, String semaineExcel) {

		if (hdeb.equals("") || mdeb.equals("")) {
			WindowHandler.showError("Donnez l'heure de début");
			return false;
		}
		if (hfin.equals("") || mfin.equals("")) {
			WindowHandler.showError("Donnez l'heure de fin");
			return false;
		}
		if (semaineExcel.equals("")) {
			WindowHandler.showError("Donnez les semaines de réservation");
			return false;
		}
		return true;
	}

	public void consulterHistorique() {
		if (app.aDroitModification(reservation) || app.aDroitSuppression(reservation)) {
			LogsHistorisation.sharedInstance(null).open(reservation);
		}
		else {
			WindowHandler.showError("Vous n'avez pas les droits pour consulter l'historique de cette réservation.");
		}
	}

	/** valide la reservation */
	public void valider() {
		try {
			String commentaire = myView.getTaCommentaire().getText();

			boolean isDT = myView.getCkDt().isSelected();
			boolean isBloquage = (getSelectedIndex(myView.getMatTypeResa()) == TYPE_RESA_BLOCAGE);
			boolean isReunion = (getSelectedIndex(myView.getMatTypeResa()) == TYPE_RESA_REUNION);
			boolean hasCommentaire = !commentaire.equals("");
			boolean doNotContinue = false;

			if (isDT && !hasCommentaire) {
				WindowHandler.showError("Vous devez saisir un objet pour la création de la Demande de Travaux !");
				doNotContinue = true;
			}

			if (isBloquage && !hasCommentaire) {
				WindowHandler.showError("Vous devez saisir un objet pour ce blocage !");
				doNotContinue = true;
			}

			if (isReunion && !hasCommentaire) {
				WindowHandler.showError("Vous devez saisir un objet pour cette réunion !");
				doNotContinue = true;
			}

			if (doNotContinue) {
				WindowHandler.setDefaultCursor(myView);
				myView.getBEnregistrer().setEnabled(true);
				this.resaFailed();
				return;
			}

			// sinon on continue les op d'enregistrement
			myView.getBEnregistrer().setEnabled(false);

			if (modificationEnabled) {
				this.enregistrerModification();
				if (!resaSucceed) {
					return;
				}
			}
			else {
				boolean ok = this.enregistrerReservation();
				if (!ok) {
					return;
				}
			}

			if (app.isInspecteurAutoFermer()) {
				close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			app.sendMailException(e);
		}
	}

	/** action sur le bouton -nouveau- commence une nouvelle reservation */
	public void nouvelleReservation() {
		modificationEnabled = false;
		this.cleanUp();
		int year = TimeHandler.getYearFor(main.debut, ((Boolean) app.userInfo("anneeCivile")).booleanValue());
		setComboAnneesSelected(year);

		NSArray<NSDictionary<String, Object>> lesInfos = null;
		if (main != null) {
			lesInfos = main.getLesInfos();
			if (lesInfos == null || lesInfos.count() == 0) {
				NSDictionary<String, Object> informations = main.getInformations();
				if (informations != null) {
					lesInfos = new NSArray<NSDictionary<String, Object>>(informations);
				}
			}
		}
		panier.initPanier(lesInfos);

		// =====ying ajouter pour le cas ou c'est l'autre personne que celui de
		// connecter ou plusieur personnes=====
		if (main.etatMpers) {
			// NSArray individusPanier =
			// panier.getResourcesWithType("PERSONNE");

			NSMutableArray<IndividuUlr> allPersonnesInInfosMpers = new NSMutableArray<IndividuUlr>();
			if (main.infosMpers != null) {
				for (int i = 0; i < main.infosMpers.count(); i++) {
					allPersonnesInInfosMpers.addObject((IndividuUlr) (main.infosMpers.objectAtIndex(i)).objectForKey("resRecord"));
				}
				NSMutableArray<NSDictionary<String, Object>> allPersRes = new NSMutableArray<NSDictionary<String, Object>>();
				for (int i = 0; i < allPersonnesInInfosMpers.count(); i++) {
					IndividuUlr currentIndividu = allPersonnesInInfosMpers.objectAtIndex(i);
					String libelle = currentIndividu.nomUsuel() + " " + currentIndividu.prenom();
					Object[] objects = { "PERSONNE", libelle, NSKeyValueCoding.NullValue, "1", currentIndividu };
					String[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
					NSDictionary<String, Object> ressource = new NSDictionary<String, Object>(objects, keys);
					allPersRes.addObject(ressource);
				}
			}
		}
		// ==fin ying ajouter

		setDefaultVisibilite();
		setDefaultTypeResa();

		activateWidgets(true);
	}

	private void setComboAnneesSelected(int year) {
		for (int i = 0; i < myView.getComboAnnees().getItemCount(); i++) {
			FormationAnnee fa = (FormationAnnee) myView.getComboAnnees().getItemAt(i);
			if (fa != null && fa.fannDebut().intValue() == year) {
				myView.getComboAnnees().setSelectedIndex(i);
				break;
			}
		}
	}

	public void commencerNouvelleReservation(MaquetteAp ap, ScolGroupeGrp groupe) {
		this.nouvelleReservation();
		NSMutableDictionary<String, Object> ressource = new NSMutableDictionary<String, Object>();
		ressource.takeValueForKey("ENSEIGNEMENT", "resType");
		ressource.takeValueForKey(ap.mapLibelle(), "resLibelle");
		ressource.takeValueForKey(NSKeyValueCoding.NullValue, "resDepos");
		ressource.takeValueForKey(ap, "resRecord");
		if (groupe == null) {
			ressource.takeValueForKey(MatiereExtController.TOUS_LES_GROUPES, "resUnite");
		}
		else {
			ressource.takeValueForKey(groupe, "resUnite");
		}
		setSelectedIndex(TYPE_RESA_ENSEIGNEMENT, myView.getMatTypeResa());
		panier().addResources(new NSArray<NSDictionary<String, Object>>(ressource));
	}

	public void commencerNouvelleReservation(String time, Integer jour, int semaine) {
		this.nouvelleReservation();
		NSArray<String> elementsHeure = NSArray.componentsSeparatedByString(time, ":");
		myView.getTHrDeb().setText(elementsHeure.objectAtIndex(0));
		myView.getTMinDeb().setText(elementsHeure.objectAtIndex(1));
		myView.getTHrFin().setText(elementsHeure.objectAtIndex(2));
		myView.getTMinFin().setText(elementsHeure.objectAtIndex(3));
		myView.getTSemaines().setText(String.valueOf(semaine));
		dayList.setSelectedIndex(jour.intValue());
	}

	private boolean enregistrerReservation() {
		if (myView.getCbExpert().isSelected()) {
			return enregistrementExpert();
		}
		else {
			// NSArray<Object> choixSalles = panier.getResourcesWithType("CHOIX");
			// if (choixSalles.count() > 0) {
			// return enregistrementSegmentation();
			// }
			// else {
			return enregistrerReservationNormale();
			// }
		}
	}

	public int getSelectedIndex(ButtonGroup bg) {
		if (bg == null) {
			return -1;
		}
		Enumeration<AbstractButton> e = bg.getElements();
		int i = 0;
		while (e.hasMoreElements()) {
			if (e.nextElement().isSelected()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public void setSelectedIndex(int index, ButtonGroup bg) {
		if (bg == null) {
			return;
		}
		Enumeration<AbstractButton> e = bg.getElements();
		int i = 0;
		while (e.hasMoreElements()) {
			if (i == index) {
				e.nextElement().setSelected(true);
				return;
			}
			e.nextElement();
			i++;
		}
	}

	/** creation d'une nouvelle reservation */
	private boolean enregistrerReservationNormale() {

		WindowHandler.setWaitCursor(myView);

		Iterator<Integer> daysIterator = dayList.getSelectedDays().iterator();
		while (daysIterator.hasNext()) {
			String hdeb = myView.getTHrDeb().getText(), hfin = myView.getTHrFin().getText(), mdeb = myView.getTMinDeb().getText(), mfin = myView
					.getTMinFin().getText(), semaineExcel = myView.getTSemaines().getText();
			IndividuUlr agent = (IndividuUlr) app.userInfo("individu");

			if (!testEntries(hdeb, mdeb, hfin, mfin, semaineExcel)) {
				WindowHandler.setDefaultCursor(myView);
				myView.getBEnregistrer().setEnabled(true);
				return false;
			}

			if (myView.getComboAnnees() == null) {
				NSLog.out.appendln("comboAnnees==null");
			}

			int annee = ((FormationAnnee) myView.getComboAnnees().getSelectedItem()).fannDebut().intValue();

			NSArray<NSTimestamp> creneauxDates = null;
			try {
				// creneauxDates = timeHandler.computePeriodicites(dayList.getSelectedDay(), semaineExcel, hdeb, hfin, mdeb, mfin, annee);
				creneauxDates = timeHandler.computePeriodicites(daysIterator.next(), semaineExcel, hdeb, hfin, mdeb, mfin, annee);
				creneauxDates = creneauxDates.sortedArrayUsingComparator(NSComparator.AscendingTimestampComparator);
				creneauxDates = TimeHandler.testCoherenceDates(creneauxDates);
				creneauxDates = TimeHandler.retirerIncoherences(creneauxDates);
			}
			catch (Exception e1) {
				e1.printStackTrace();
				WindowHandler.setDefaultCursor(myView);
				myView.getBEnregistrer().setEnabled(true);
			}

			if (creneauxDates == null) {
				WindowHandler.showError("Aucune date réservable !\nVérifiez vos périodicités...");
				WindowHandler.setDefaultCursor(myView);
				myView.getBEnregistrer().setEnabled(true);
				return false;
			}

			NSMutableArray<NSTimestamp> periods = new NSMutableArray<NSTimestamp>();
			for (int idate = 0; idate < creneauxDates.count(); idate++) {
				NSTimestamp currentDateTest = creneauxDates.objectAtIndex(idate);
				if (!verificationJourFerie(currentDateTest)) {
					periods.addObject(currentDateTest);
				}
			}

			// UP 19/11/2008 : ajout pour controle
			if (periods == null || periods.count() == 0) {
				WindowHandler.showError("Aucune date n'est réservable !\nLa réservation en jour férié n'est pas possible.");
				WindowHandler.setDefaultCursor(myView);
				myView.getBEnregistrer().setEnabled(true);
				return false;
			}

			NSArray<IndividuUlr> personnes = panier.getResourcesWithType("PERSONNE");
			NSArray<Salles> salles = panier.getResourcesWithType("SALLE");
			NSArray<Salles> choixSalles = panier.getResourcesWithType("CHOIX");
			NSArray<StructuresAutorisees> groupes = panier.getResourcesWithType("GROUPE");
			NSArray<ResaObjet> objets = panier.getResourcesWithType("OBJET");
			NSArray<NSKeyValueCoding> enseigns = panier.getResourcesWithType("ENSEIGNEMENT_INSP");
			NSArray<NSDictionary<String, Object>> examens = panier.getResourcesWithType("EXAMEN");
			NSArray<NSDictionary<String, Object>> semestresGroupes = panier.getResourcesWithType("SEMESTRE_INSP");

			String commentaire = myView.getTaCommentaire().getText();
			int typeReservation = getSelectedIndex(myView.getMatTypeResa());

			NSMutableArray<NSTimestamp> periodicites = new NSMutableArray<NSTimestamp>();
			periodicites.addObjectsFromArray(periods);

			if (typeReservation == TYPE_RESA_BLOCAGE) {

				if (!testRessourcesBlocage(salles, personnes, objets)) {
					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
					return false;
				}

				if (!WindowHandler.showConfirmation("Vous confirmez le blocage des ressources selectionnees ?")) {
					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
					return false;
				}

				StringBuffer comment = new StringBuffer("Blocage");

				if (!commentaire.equals("")) {
					comment.append(" ");
					comment.append(commentaire);
				}

				try {
					reservation = reunionfactory.creerBlocage(null, agent, periodicites, personnes, salles, objets, comment.toString());
				}
				catch (Exception e) {
					WindowHandler.showError(e.getMessage());
					reservation = null;
					app.sendMailException(e);

					if (e instanceof Exception) {
						e.printStackTrace();
					}

					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
				}
				finally {
					eContext.revert();
				}

				if (reservation != null) {
					this.resaSucceed();
				}
				else {
					this.resaFailed();
					return false;
				}
			}

			// CM reunion + agenda
			if (typeReservation == TYPE_RESA_REUNION) {
				if (!testRessourcesReunion(personnes, groupes, salles, choixSalles, objets)) {
					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
					return false;
				}
				int typeAffichage = getSelectedIndex(myView.getMatAff());

				try {
					reservation = reunionfactory.creerReunion(null, agent, periodicites, personnes, groupes, salles, choixSalles, objets,
							commentaire, typeAffichage, true, true);
				}
				catch (Exception e) {
					WindowHandler.showError(e.getMessage());
					reservation = null;
					app.sendMailException(e);

					if (e instanceof Exception) {
						e.printStackTrace();
					}

					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
				}
				finally {
					eContext.revert();
				}

				if (reservation != null) {
					this.resaSucceed();
				}
				else {
					this.resaFailed();
					return false;
				}
			}
			// Edt enseignement
			if (typeReservation == TYPE_RESA_ENSEIGNEMENT) {

				if (!testRessourcesEnseignement(enseigns)) {
					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
					return false;
				}
				try {
					NSArray<MaquetteAp> aps = panier.getResourcesWithType("ENSEIGNEMENT");
					this.ajouterResponsablesEC(personnes, aps);
					reservation = ensFactory.creerEdtEnseignement(null, agent, personnes, periodicites, enseigns, salles, choixSalles, objets,
							commentaire, true, true);
				}
				catch (Exception e) {
					WindowHandler.showError(e.getMessage());
					eContext.revert();
					reservation = null;
					app.sendMailException(e);

					if (e instanceof Exception) {
						e.printStackTrace();
					}

					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
				}

				if (reservation != null) {
					this.resaSucceed();
				}
				else {
					this.resaFailed();
					return false;
				}

			}
			// EXAMEN
			if (typeReservation == TYPE_RESA_EXAMEN) {
				if (!testRessourcesExamen(examens)) {
					myView.getBEnregistrer().setEnabled(true);
					WindowHandler.setDefaultCursor(myView);
					return false;
				}
				ExamenFactory examenFactory = new ExamenFactory(eContext);
				try {
					reservation = examenFactory.creerExamen(null, agent, personnes, periodicites, examens, salles, choixSalles, objets, commentaire);
				}
				catch (Exception e) {
					WindowHandler.showError(e.getMessage());
					reservation = null;
					app.sendMailException(e);
					if (e instanceof Exception) {
						e.printStackTrace();
					}
					eContext.revert();
					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
				}

				if (reservation != null) {
					this.resaSucceed();
				}
				else {
					this.resaFailed();
					return false;
				}

			}

			if (typeReservation == TYPE_RESA_REUNION_ETUDIANT) {

				// commentaire obligatoire dans ce cas
				if (commentaire.equals("")) {
					WindowHandler
							.showError("Pour une réservation de promo étudiants; il faut mettre un commentaire !\nLe commentaire sera affiché sur l'EDT.");
					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
					return false;
				}

				if (!testRessourcesReunionsEtudiants(semestresGroupes)) {
					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
					return false;
				}

				try {
					reservation = ensFactory.creerEdtSemestre(null, agent, personnes, periodicites, semestresGroupes, salles, choixSalles, objets,
							commentaire);
				}
				catch (Exception e) {
					WindowHandler.showError(e.getMessage());
					eContext.revert();
					reservation = null;
					app.sendMailException(e);

					if (!(e instanceof EdtException)) {
						e.printStackTrace();
					}

					WindowHandler.setDefaultCursor(myView);
					myView.getBEnregistrer().setEnabled(true);
				}

				if (reservation != null) {
					this.resaSucceed();
				}
				else {
					this.resaFailed();
					return false;
				}

			} // REUNION_ETUDIANT

			// Fin de la resa, envoi de mail si tout est ok
			// mail aux dépositaires (salles et objets)...
			NSDictionary<String, Object> ressourcesMail = null;
			if (reservation != null && app.isSendMailDepositaires()) {
				String sDebResa = hdeb + ":" + mdeb;
				String sFinResa = hfin + ":" + mfin;

				NSMutableArray<MaquetteAp> enseignements = new NSMutableArray<MaquetteAp>();
				for (int ie = 0; ie < enseigns.count(); ie++) {
					enseignements.addObject((MaquetteAp) ((NSDictionary<String, Object>) enseigns.objectAtIndex(ie)).valueForKey("AP"));
				}
				NSArray<IndividuUlr> occupants = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
						+ Occupant.INDIVIDU_KEY);
				ressourcesMail = this.preparerMailDetailResa(periodicites, sDebResa, sFinResa, agent, occupants, salles, choixSalles, enseignements,
						examens, objets, commentaire, MAIL_CREATION);

				if (ressourcesMail != null) {

					String destinataire = (String) ressourcesMail.valueForKey("destinataire");

					if (destinataire != null && !destinataire.trim().equals("")) {
						MailReservation mailReservation = app.mailReservation();
						mailReservation.setMailInfos(ressourcesMail);
						mailReservation.enregistrer();
						// mailReservation.show();
					}
				}
			}

			// mail aux occupants...
			if ((typeReservation == TYPE_RESA_REUNION || typeReservation == TYPE_RESA_ENSEIGNEMENT || typeReservation == TYPE_RESA_EXAMEN)
					&& app.isSendMailOccupants()) {
				if (reservation != null && (personnes.count() > 0 || groupes.count() > 0)) {
					NSArray<IndividuUlr> occupants = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
							+ Occupant.INDIVIDU_KEY);
					if (occupants != null && occupants.count() > 0 && (occupants.count() > 1 || agent.equals(occupants.objectAtIndex(0)) == false)) {
						prepareMailPersonne(MAIL_CREATION, reservation, null, null, null, null, null, null);
					}
				}
			}

			// Creation de DT si necessaire...
			if (myView.getCkDt().isSelected()) {
				String dtsamEmail = (String) app.invokeRemoteMethod(eContext, "session", "clientSideRequestDTSAMEmail", null);
				if (dtsamEmail != null && dtsamEmail.trim().length() > 0) {
					if (WindowHandler.showConfirmation("Création de la DT associée ?")) {
						if (ressourcesMail == null) {
							NSMutableArray<MaquetteAp> enseignements = new NSMutableArray<MaquetteAp>();
							for (int ie = 0; ie < enseigns.count(); ie++) {
								enseignements.addObject((MaquetteAp) ((NSDictionary<String, Object>) enseigns.objectAtIndex(ie)).valueForKey("AP"));
							}
							NSArray<IndividuUlr> occupants = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
									+ Occupant.INDIVIDU_KEY);
							ressourcesMail = this.preparerMailDetailResa(periodicites, hdeb + ":" + mdeb, hfin + ":" + mfin, agent, occupants,
									salles, choixSalles, enseignements, examens, objets, commentaire, MAIL_CREATION);
						}
						if (ressourcesMail != null
								&& app.remoteSendMail((String) app.userInfo("email"), dtsamEmail, null,
										(String) ressourcesMail.objectForKey("sujet"), (String) ressourcesMail.objectForKey("texte"))) {
							WindowHandler.showInfo("DT créée !");
						}
						else {
							WindowHandler.showError("Une erreur est survenue, la DT n'a pas pu être créée !");
						}
					}
				}
			}
		}
		WindowHandler.setDefaultCursor(myView);
		myView.getBEnregistrer().setEnabled(true);

		return true;
	}

	private Integer getMinutes(String heures, String minutes) {
		Integer result = null;
		try {
			if (StringCtrl.isEmpty(heures) == false) {
				int h = FormatHandler.strToInt(heures);
				int m = StringCtrl.isEmpty(minutes) ? 0 : FormatHandler.strToInt(minutes);
				result = new Integer((h * 60) + m);
			}
		}
		catch (Exception e) {
			result = null;
		}
		return result;
	}

	private boolean enregistrementExpert() {

		WindowHandler.setWaitCursor(myView);

		try {
			String hdeb = myView.getTHrDeb().getText(), hfin = myView.getTHrFin().getText(), mdeb = myView.getTMinDeb().getText(), mfin = myView
					.getTMinFin().getText(), hduree = myView.getTHrDuree().getText(), mduree = myView.getTMinDuree().getText();
			Integer debut = getMinutes(hdeb, mdeb), fin = getMinutes(hfin, mfin), duree = getMinutes(hduree, mduree);
			Integer intervalleMinimum = app.getPrefUser().intervalleMinimum();
			if (intervalleMinimum == null) {
				intervalleMinimum = new Integer(5);
			}
			ExpertFactory ef = new ExpertFactory(eContext);
			ef.setAnnee(((FormationAnnee) myView.getComboAnnees().getSelectedItem()).fannDebut());
			ef.setDebut(debut);
			ef.setFin(fin);
			ef.setDuree(duree);
			ef.setIntervalleMinimum(intervalleMinimum);
			ef.setJours(dayList.getSelectedDays());
			ef.setSemaines(myView.getTSemaines().getText());
			ef.setTypeReservation(getSelectedIndex(myView.getMatTypeResa()));
			ef.setCommentaire(myView.getTaCommentaire().getText());
			ef.setVisibilite(getSelectedIndex(myView.getMatAff()));
			ef.setPanier(panier);

			boolean result = false;

			try {
				reservation = ef.enregistrementExpert(true, false, true);
			}
			catch (EdtException e) {
				reservation = null;
				eContext.revert();
				if (e.isBloquant()) {
					WindowHandler.showError(e.getMessage());
					e.printStackTrace();
				}
				else {
					WindowHandler.showInfo(e.getMessage());
				}
			}

			if (reservation != null) {
				this.resaSucceed();
				result = true;
			}
			else {
				this.resaFailed();
			}

			WindowHandler.setDefaultCursor(myView);
			myView.getBEnregistrer().setEnabled(true);

			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
			app.sendMailException(e);
			WindowHandler.setDefaultCursor(myView);
			myView.getBEnregistrer().setEnabled(true);
			return false;
		}
	}

	public static void prepareMailPersonne(int typeOperation, Reservation reservation, NSArray<Salles> salles, NSArray<IndividuUlr> personnes,
			NSArray<Periodicite> periodicites, NSArray<MaquetteAp> enseignements, NSArray<ExamenEntete> examens, IndividuUlr agent) {

		if (reservation == null) {
			return;
		}
		if (typeOperation != MAIL_CREATION && typeOperation != MAIL_MODIFICATION && typeOperation != MAIL_SUPPRESSION) {
			return;
		}

		MainClient app = (MainClient) EOApplication.sharedApplication();
		if (agent == null) {
			agent = reservation.individuAgent();
		}
		String expediteur = app.getEmailIndividu(agent);
		String sujet = "";

		int typeReservation = TYPE_RESA_ENSEIGNEMENT;
		String tlocCode = reservation.tlocCode();
		if (tlocCode == null || "rsp".indexOf(tlocCode) >= 0) {
			typeReservation = TYPE_RESA_REUNION;
		}
		if (tlocCode.equals("E")) {
			typeReservation = TYPE_RESA_EXAMEN;
		}
		MailReservation mailReservation = app.mailReservation();
		StringBuffer destinataires = new StringBuffer();
		if (salles == null) {
			salles = (NSArray<Salles>) reservation.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		}
		if (personnes == null) {
			personnes = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		}
		if (enseignements == null) {
			enseignements = (NSArray<MaquetteAp>) reservation.valueForKeyPath(Reservation.RESERVATION_AP_KEY + "." + ReservationAp.MAQUETTE_AP_KEY);
		}
		if (examens == null) {
			examens = (NSArray<ExamenEntete>) reservation.valueForKeyPath(Reservation.RESA_EXAMENS_KEY + "." + ResaExamen.EXAMEN_ENTETE_KEY);
		}
		if (periodicites == null) {
			periodicites = reservation.periodicites();
		}
		StringBuffer msg = new StringBuffer();
		switch (typeReservation) {
		case TYPE_RESA_REUNION:
			if (StringCtrl.isEmpty(sujet)) {
				sujet = "EdT Réunion...";
			}
			switch (typeOperation) {
			case MAIL_CREATION:
				msg.append("Vous êtes convié(e)s à la réunion suivante : ");
				break;
			case MAIL_MODIFICATION:
				msg.append("La réunion suivante a été modifiée : ");
				msg.append(MainClient.RETURN);
				msg.append("Nouvelles informations : ");
				msg.append(MainClient.RETURN);
				break;
			case MAIL_SUPPRESSION:
				msg.append("La réunion suivante a été annulée : ");
				break;
			}
			break;
		case TYPE_RESA_ENSEIGNEMENT:
			if (StringCtrl.isEmpty(sujet)) {
				sujet = "EdT Enseignement...";
			}
			switch (typeOperation) {
			case MAIL_CREATION:
				msg.append("CREATION : ");
				msg.append(MainClient.RETURN);
				break;
			case MAIL_MODIFICATION:
				msg.append("MODIFICATION : ");
				msg.append(MainClient.RETURN);
				msg.append("Nouvelles informations : ");
				msg.append(MainClient.RETURN);
				break;
			case MAIL_SUPPRESSION:
				msg.append("SUPPRESSION : ");
				msg.append(MainClient.RETURN);
				break;
			}
			break;
		case TYPE_RESA_EXAMEN:
			if (StringCtrl.isEmpty(sujet)) {
				sujet = "EdT Examen...";
			}
			switch (typeOperation) {
			case MAIL_CREATION:
				msg.append("Un examen a été programmé pour vous : ");
				break;
			case MAIL_MODIFICATION:
				msg.append("La réservation d'examen suivante a été modifiée : ");
				msg.append(MainClient.RETURN);
				msg.append("Nouvelles informations : ");
				msg.append(MainClient.RETURN);
				break;
			case MAIL_SUPPRESSION:
				msg.append("La réservation d'examen suivante a été annulée : ");
			}
		}
		if (typeReservation == TYPE_RESA_REUNION) {
			msg.append("\n" + reservation.resaCommentaire());
		}
		if (typeReservation == TYPE_RESA_ENSEIGNEMENT) {
			// int cnt = enseignements.count();
			// if (cnt > 0) {
			// msg.append("Enseignement(s) : ");
			// }
			for (int i = 0; i < enseignements.count(); i++) {
				// if (enseignements.objectAtIndex(i) != null && enseignements.objectAtIndex(i).equals(NSKeyValueCoding.NullValue) == false)
				// {
				if (enseignements.objectAtIndex(i) != null) {
					MaquetteAp currentAP = enseignements.objectAtIndex(i);
					// msg.append(app.ensFactory().detailDiplomePourAp(currentAP));
					// msg.append("\n - AP : ");
					msg.append(currentAP.toString());
					msg.append(MainClient.RETURN);
				}
			}
			if (StringCtrl.isEmpty(reservation.resaCommentaire()) == false) {
				msg.append("(Commentaire : " + reservation.resaCommentaire() + ")" + MainClient.RETURN);
			}
		}
		if (typeReservation == TYPE_RESA_EXAMEN) {
			int cnt = examens.count();
			if (cnt > 0) {
				msg.append("Examen(s) : ");
			}
			for (int i = 0; i < examens.count(); i++) {
				ExamenEntete examEntete = examens.objectAtIndex(i);
				if (examEntete != null) {
					msg.append(examEntete.eentLibelle());
				}
				msg.append(MainClient.RETURN);
			}
			if (StringCtrl.isEmpty(reservation.resaCommentaire()) == false) {
				msg.append("Commentaire : " + reservation.resaCommentaire() + MainClient.RETURN);
			}
		}

		// recherche des destinataires
		NSArray<String> emails = app.getEmailIndividus((NSArray<Integer>) personnes.valueForKey(IndividuUlr.PERS_ID_KEY));
		String dest = emails.componentsJoinedByString(",");
		destinataires.append(dest);
		NSTimestamp date = null;
		for (int i = 0; i < periodicites.count(); i++) {
			msg.append("\nle ");
			date = (periodicites.objectAtIndex(i)).dateDeb();
			msg.append(FormatHandler.dateToStr(date, "%A %d/%m/%Y"));
			msg.append(" de ");
			msg.append(FormatHandler.dateToStr(date, "%Hh%M"));
			date = (periodicites.objectAtIndex(i)).dateFin();
			msg.append(" à ");
			msg.append(FormatHandler.dateToStr(date, "%Hh%M"));
		}
		for (int i = 0; i < salles.count(); i++) {
			Salles salle = salles.objectAtIndex(i);
			msg.append("\nSalle " + salle.salPorte() + " - Bat. " + salle.local().appellation() + " (Etage " + salle.salEtage() + ")");
		}
		msg.append("\n\nCordialement,\n");
		msg.append(agent.prenomNom());

		mailReservation.setMailInfos(msg.toString(), sujet, destinataires.toString(), expediteur);
		mailReservation.show();
	}

	/** envoie un mail de notification de reservation au(x) depositaire(s) */
	private NSDictionary<String, Object> preparerMailDetailResa(NSArray<NSTimestamp> periodicites, String debut, String fin, IndividuUlr agent,
			NSArray<IndividuUlr> occupants, NSArray<Salles> salles, NSArray<Salles> choixSalles, NSArray<MaquetteAp> enseignements,
			NSArray<?> examens, NSArray<ResaObjet> objets, String commentaire, int typeMail) {

		NSMutableArray<Salles> requestedSalles = new NSMutableArray<Salles>();
		NSMutableArray<ResaObjet> requestedObjets = new NSMutableArray<ResaObjet>();

		IndividuUlr currentAgent = (IndividuUlr) app.userInfo("individu");
		// exclure les salles dont l'agent est depositaire
		for (int i = 0; i < salles.count(); i++) {
			if (!salleFactory.estDepositaireDeSalle(currentAgent, salles.objectAtIndex(i))) {
				requestedSalles.addObject(salles.objectAtIndex(i));
			}
		}
		for (int i = 0; i < choixSalles.count(); i++) {
			if (!salleFactory.estDepositaireDeSalle(currentAgent, choixSalles.objectAtIndex(i))) {
				requestedSalles.addObject(choixSalles.objectAtIndex(i));
			}
		}
		// exclure les objets dont l'agent est depositaire
		if (objets != null) {
			for (int i = 0; i < objets.count(); i++) {
				if (VerificationFactory.testIndividuEstDepositaireObjet(eContext, currentAgent, objets.objectAtIndex(i)) == false) {
					requestedObjets.addObject(objets.objectAtIndex(i));
				}
			}
		}

		NSMutableArray<String> mailDeposSallesEtObjets = new NSMutableArray<String>();
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();

		if (app.isSendMailDepositaires()) {
			// chercher les mails des depos pour les salles
			if (requestedSalles.count() > 0) {
				NSArray<EOGlobalID> gidSalles = DBHandler.globalIDsForObjects(eContext, requestedSalles);
				NSArray<String> tmpMail = (NSArray<String>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session",
						"clientSideRequestMailsDepositairesSalles", new Class[] { NSArray.class, NSArray.class }, new Object[] { gidSalles,
								periodicites }, false);
				if (tmpMail != null && tmpMail.count() > 0) {
					mailDeposSallesEtObjets.addObjectsFromArray(tmpMail);
				}
			}
			// chercher les mails des depos pour les objets
			if (requestedObjets.count() > 0) {
				NSArray<EOGlobalID> gidObjets = DBHandler.globalIDsForObjects(eContext, requestedObjets);
				NSArray<String> tmpMail = (NSArray<String>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session",
						"clientSideRequestMailsDepositairesObjets", new Class[] { NSArray.class }, new Object[] { gidObjets }, false);
				if (tmpMail != null && tmpMail.count() > 0) {
					mailDeposSallesEtObjets.addObjectsFromArray(tmpMail);
				}
			}
		}

		// si la personne qui agit n'est pas celle qui a cree la resa, on
		// informe le createur des modifications apportees
		if (!agent.equals(currentAgent)) {
			String mailAgent = app.getEmailIndividu(agent);
			if (mailAgent != null) {
				mailDeposSallesEtObjets.addObject(mailAgent);
			}
		}

		if (mailDeposSallesEtObjets.count() == 0) {
			return null;
		}

		String sujet = null;
		StringBuffer message = new StringBuffer();

		message.append("Réalisé par : " + currentAgent.prenom() + " " + currentAgent.nomUsuel());
		if (typeMail == MAIL_CREATION) {
			sujet = "Création de réservation";
		}
		if (typeMail == MAIL_MODIFICATION) {
			sujet = "Modification de réservation";
		}

		if (typeMail == MAIL_SUPPRESSION) {
			sujet = "Suppression de réservation";
		}
		if (salles.count() > 0 || choixSalles.count() > 0) {
			sujet += " de salle";
			if (salles.count() + choixSalles.count() > 1) {
				sujet += "s";
			}
		}

		NSMutableArray<Object> args = new NSMutableArray<Object>();
		args.addObject(currentAgent);
		EOQualifier qualifier = EOQualifier
				.qualifierWithQualifierFormat(RepartStructure.INDIVIDU_ULR_KEY + " = %@ and " + RepartStructure.STRUCTURE_ULR_KEY + "."
						+ StructureUlr.STRUCTURE_ULR_KEY + "." + StructureUlr.C_TYPE_STRUCTURE_KEY + " = 'E'", args);
		NSArray<RepartStructure> aStruc = RepartStructure.fetchRepartStructures(eContext, qualifier, null);
		if (aStruc.count() > 0) {
			StructureUlr struc = (aStruc.objectAtIndex(0)).structureUlr();
			message.append(MainClient.RETURN);
			message.append("membre de : " + struc.llStructure());
		}

		message.append(MainClient.RETURN);
		message.append("Objet : " + commentaire);
		message.append(MainClient.RETURN);

		message.append("Pour la(les) date(s) :");
		message.append(MainClient.RETURN);
		NSTimestampFormatter formatter = new NSTimestampFormatter("%A %d %B %Y");
		for (int i = 0; i < periodicites.count(); i += 2) {
			message.append(formatter.format(periodicites.objectAtIndex(i)));
			message.append(MainClient.RETURN);
		}

		String a[] = debut.split(":");
		if (a[1].length() == 1) {
			debut = debut + "0";
		}
		String b[] = fin.split(":");
		if (b[1].length() == 1) {
			fin = fin + "0";
		}
		String fDebut = debut.replaceAll(":", "H");
		String fFin = fin.replaceAll(":", "H");
		message.append("de " + fDebut + " à " + fFin);

		message.append(MainClient.RETURN);

		if (salles != null) {
			for (int i = 0; i < salles.count(); i++) {
				if (i == 0) {
					message.append("Salle(s) :");
					message.append(MainClient.RETURN);
				}
				Salles salle = salles.objectAtIndex(i);
				message.append("No " + salle.salPorte() + " - Bat. " + salle.local().appellation() + "(Etage " + salle.salEtage() + ")");
			}
		}

		if (choixSalles != null && choixSalles.count() > 0) {
			message.append("Salles au choix :");
			message.append(MainClient.RETURN);

			for (int i = 0; i < choixSalles.count(); i++) {
				Salles salle = choixSalles.objectAtIndex(i);
				message.append("No " + salle.salPorte() + " - Bat. " + salle.local().appellation() + "(Etage " + salle.salEtage() + ")");
			}
		}

		if (occupants != null) {
			for (int i = 0; i < occupants.count(); i++) {
				if (i == 0) {
					message.append(MainClient.RETURN);
					message.append("Occupant(s) : ");
					message.append(MainClient.RETURN);
				}
				IndividuUlr occupant = occupants.objectAtIndex(i);
				message.append(occupant.nomUsuel() + " " + occupant.prenom());
				message.append(MainClient.RETURN);
			}
		}

		if (objets != null) {
			for (int i = 0; i < objets.count(); i++) {
				if (i == 0) {
					message.append("Matériels(s) : ");
					message.append(MainClient.RETURN);
				}
				ResaObjet currentObjet = objets.objectAtIndex(i);
				message.append(currentObjet.roLibelle1());
				message.append(MainClient.RETURN);
			}
		}

		message.append(MainClient.RETURN);
		message.append(MainClient.RETURN);

		int cnt = enseignements.count();
		if (cnt > 0) {
			message.append("Enseignement(s) : ");
		}
		for (int i = 0; i < enseignements.count(); i++) {
			MaquetteAp currentAP = enseignements.objectAtIndex(i);

			message.append(ensFactory.detailDiplomePourAp(currentAP));
			message.append("\n - AP : ");
			message.append(currentAP.mapLibelle());
			message.append(MainClient.RETURN);
		}

		cnt = examens.count();
		if (cnt > 0) {
			message.append("Examen(s) : ");
		}
		for (int i = 0; i < examens.count(); i++) {
			Object objExamen = examens.objectAtIndex(i);
			ExamenEntete examEntete = null;
			if (objExamen instanceof ExamenEntete) {
				examEntete = (ExamenEntete) objExamen;
			}
			else {
				examEntete = (ExamenEntete) ((NSDictionary<String, Object>) objExamen).valueForKey("EXAM");
			}
			if (examEntete != null) {
				message.append(examEntete.eentLibelle());
			}
			message.append(MainClient.RETURN);
		}

		String dest = mailDeposSallesEtObjets.componentsJoinedByString(",");
		String exp = (String) app.userInfo("email");

		NSDictionary<String, Object> dicoMail = null;

		if (exp != null && dest != null && sujet != null) {
			String[] keys = { "expediteur", "destinataire", "sujet", "texte" };
			Object[] values = { exp, dest, sujet, message.toString() };
			dicoMail = new NSDictionary<String, Object>(values, keys);
		}
		return dicoMail;
	}

	/**
	 * construit un dictionnaire de globalids a partir des ressources salles & personnes
	 */

	/** teste si les ressources minimales sont choisies pour la resa de cours */
	public static boolean testRessourcesEnseignement(NSArray<NSKeyValueCoding> atomesPedagogiques) {
		if (atomesPedagogiques.count() == 0) {
			WindowHandler.showError("Il faut au moins un enseignement pour faire un cours");
			return false;
		}
		return true;
	}

	private boolean testRessourcesReunionsEtudiants(NSArray<NSDictionary<String, Object>> semestresGroupes) {
		if (semestresGroupes.count() == 0) {
			WindowHandler
					.showError("Il faut au moins une promo pour faire cette réunion !\n Veuillez mettre un semestre avec eventuellement des groupes dans le panier");
			return false;
		}
		return true;
	}

	/** teste si les ressources minimales sont choisies pour la resa de cours */
	private boolean testRessourcesExamen(NSArray<NSDictionary<String, Object>> exams) {
		if (exams.count() == 0) {
			WindowHandler.showError("Il faut au moins un examen dans le panier");
			return false;
		}
		return true;
	}

	/** teste si les ressources minimales sont choisies pour la resa de reunion */
	public static boolean testRessourcesReunion(NSArray<IndividuUlr> occupants, NSArray<StructuresAutorisees> groupes, NSArray<Salles> salles,
			NSArray<Salles> choixSalles, NSArray<ResaObjet> objets) {
		if ((occupants == null || occupants.count() == 0) && (groupes == null || groupes.count() == 0) && (salles == null || salles.count() == 0)
				&& (choixSalles == null || choixSalles.count() == 0) && (objets == null || objets.count() == 0)) {
			WindowHandler.showError("Il faut des individus et/ou salles et/ou objets pour faire une réservation");
			return false;
		}
		return true;
	}

	/** teste si les ressources minimales sont choisies pour le blocage */
	private boolean testRessourcesBlocage(NSArray<Salles> salles, NSArray<IndividuUlr> personnes, NSArray<ResaObjet> objets) {
		if ((salles == null || salles.count() == 0) && (personnes == null || personnes.count() == 0) && (objets == null || objets.count() == 0)) {
			WindowHandler.showError("Il faut au moins une salle ou un individu pour faire un blocage");
			return false;
		}
		return true;
	}

	/**
	 * teste si la resa est recuperee par hcom, si tous les creneaux ne sont pas recuperes, on renvoie une nouvelle resa avec les creneaux
	 * non recuperes pour modification
	 */
	protected Reservation testRecuperationHComp(Reservation resa) throws EdtException {
		EOQualifier qualifier = DBHandler.getSimpleQualifier(HcompRec.RESERVATION_KEY, resa);
		NSArray<HcompRec> hcomp = HcompRec.fetchHcompRecs(eContext, qualifier, null);
		if (hcomp.count() == 0) {
			return resa;
		}
		else {
			NSMutableArray<NSTimestamp> newPeriodicites = new NSMutableArray<NSTimestamp>();
			NSArray<NSTimestamp> periodsRecuperees = (NSArray<NSTimestamp>) hcomp.valueForKey(HcompRec.DATE_RESA_KEY);
			qualifier = DBHandler.getSimpleQualifier(Periodicite.RESERVATION_KEY, resa);
			NSArray<Periodicite> periods = Periodicite.fetchPeriodicites(eContext, qualifier, null);

			for (int i = 0; i < periods.count(); i++) {
				NSTimestamp dateDeb = (periods.objectAtIndex(i)).dateDeb();

				if (!periodsRecuperees.containsObject(dateDeb)) {
					newPeriodicites.addObject(dateDeb);
					newPeriodicites.addObject((periods.objectAtIndex(i)).dateFin());
				}
			}
			if (periodsRecuperees.count() > 1) {
				return ensFactory.copyReservationAvecPeriodicites(resa, newPeriodicites, true); // faire
				// saveChanges
			}
			else {
				return null;
			}
		}
	}

	/** retourne les periodicites en fonction des elements du calendrier saisis */
	public NSArray<NSTimestamp> getPeriodicitesSouhaitees() throws EdtException {

		String hdeb = myView.getTHrDeb().getText(), hfin = myView.getTHrFin().getText(), mdeb = myView.getTMinDeb().getText(), mfin = myView
				.getTMinFin().getText(), semaineExcel = myView.getTSemaines().getText();

		// if (!testEntries(hdeb, mdeb, hfin, mfin, semaineExcel)) {
		// return new NSArray();
		// }

		int annee = ((FormationAnnee) myView.getComboAnnees().getSelectedItem()).fannDebut().intValue();
		int dayOfWeek = dayList.getSelectedDay();

		NSArray<NSTimestamp> tmperiod = timeHandler.computePeriodicites(dayOfWeek, semaineExcel, hdeb, hfin, mdeb, mfin, annee);
		NSMutableArray<NSTimestamp> periodicites = new NSMutableArray<NSTimestamp>();

		tmperiod = TimeHandler.testCoherenceDates(tmperiod);

		if (tmperiod == null) {
			return new NSArray<NSTimestamp>();
		}

		int cmpt = 0;
		for (int idate = 0; idate < tmperiod.count(); idate++) {
			NSTimestamp currentDateTest = tmperiod.objectAtIndex(idate);
			// if (DateCtrl.isHolidayFR(currentDateTest)) {
			if (verificationJourFerie(currentDateTest)) {
				cmpt++;
				if (cmpt == 2) {
					WindowHandler.showInfo("le " + FormatHandler.dateToStr(currentDateTest) + " est un jour ferie, on ne peut pas le reserver");
					cmpt = 0;
				}
			}
			else {
				periodicites.addObject(currentDateTest);
			}
		}
		return periodicites;
	}

	/** enregistre les modifications apportees a une reservation */
	private void enregistrerModification() {

		WindowHandler.setWaitCursor(myView);
		IndividuUlr agent = (IndividuUlr) app.userInfo("individu");
		IndividuUlr agentOrigine = reservation.individuAgent();

		if (!app.aDroitModification(reservation)) {
			WindowHandler.showError("Vous n'avez pas les droits de modifier cette réservation.");
			WindowHandler.setDefaultCursor(myView);
			myView.getBEnregistrer().setEnabled(true);
			resaSucceed = false;
			return;
		}

		String hdeb = myView.getTHrDeb().getText(), hfin = myView.getTHrFin().getText(), mdeb = myView.getTMinDeb().getText(), mfin = myView
				.getTMinFin().getText(), semaineExcel = myView.getTSemaines().getText();

		if (!testEntries(hdeb, mdeb, hfin, mfin, semaineExcel)) {
			WindowHandler.setDefaultCursor(myView);
			myView.getBEnregistrer().setEnabled(true);
			resaSucceed = false;
			return;
		}
		int annee = ((FormationAnnee) myView.getComboAnnees().getSelectedItem()).fannKey().intValue();

		TimeHandler localTimeHandler = new TimeHandler();
		localTimeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());

		NSArray<NSTimestamp> creneauxDates = new NSArray<NSTimestamp>();
		try {
			creneauxDates = localTimeHandler.computePeriodicites(dayList.getSelectedDay(), semaineExcel, hdeb, hfin, mdeb, mfin, annee);
			creneauxDates = creneauxDates.sortedArrayUsingComparator(NSComparator.AscendingTimestampComparator);
			creneauxDates = TimeHandler.testCoherenceDates(creneauxDates);
			creneauxDates = TimeHandler.retirerIncoherences(creneauxDates);
		}
		catch (Exception e1) {
			e1.printStackTrace();
			WindowHandler.setDefaultCursor(myView);
			myView.getBEnregistrer().setEnabled(true);
		}

		NSMutableArray<NSTimestamp> periodicites = new NSMutableArray<NSTimestamp>();

		for (int idate = 0; idate < creneauxDates.count(); idate++) {
			NSTimestamp currentDateTest = creneauxDates.objectAtIndex(idate);
			if (!verificationJourFerie(currentDateTest)) {
				periodicites.addObject(currentDateTest);
			}
			else {
				System.out.println("holiday=" + currentDateTest);
			}
		}

		// UP 19/11/2008 : ajout pour controle
		if (periodicites == null || periodicites.count() == 0) {
			WindowHandler.showError("Aucune date n'est réservable !\nLa réservation en jour férié n'est pas possible.");
			WindowHandler.setDefaultCursor(myView);
			myView.getBEnregistrer().setEnabled(true);
			resaSucceed = false;
			return;
		}

		// /////////////////////////////////////////////

		int typeReservation = getSelectedIndex(myView.getMatTypeResa());
		NSArray<IndividuUlr> occupants = panier.getResourcesWithType("PERSONNE");
		NSArray<Salles> salles = panier.getResourcesWithType("SALLE");
		NSArray<Salles> choixSalles = panier.getResourcesWithType("CHOIX");
		NSArray<StructuresAutorisees> groupes = panier.getResourcesWithType("GROUPE");
		NSArray<NSKeyValueCoding> enseigns = panier.getResourcesWithType("ENSEIGNEMENT_INSP");
		NSArray<ResaObjet> objets = panier.getResourcesWithType("OBJET");
		NSArray<NSDictionary<String, Object>> examens = panier.getResourcesWithType("EXAMEN");
		NSArray<NSDictionary<String, Object>> semestresGroupes = panier.getResourcesWithType("SEMESTRE_INSP");

		String commentaire = myView.getTaCommentaire().getText();
		NSMutableArray<NSTimestamp> lesPeriods = null;
		try {

			LogReservation.logModificationReservation(eContext, reservation);

			resaSucceed = true;
			if (toutesPeriodicites == false) {

				System.out.println("MODIFICATION POUR QUELQUES PERIODICITES");

				lesPeriods = new NSMutableArray<NSTimestamp>();
				NSMutableArray<Periodicite> eoPeriods = new NSMutableArray<Periodicite>();

				for (int k = 0; k < periodicitesModif.count(); k++) {
					Periodicite unePeriod = (Periodicite) (periodicitesModif.objectAtIndex(k)).valueForKey("eoPeriode");
					// pdm on tient compte des jours, heures et minutes saisis qui
					// peuvent avoir changé...
					GregorianCalendar gcdeb = new GregorianCalendar();
					gcdeb.setTime(unePeriod.dateDeb());
					gcdeb.set(Calendar.DAY_OF_WEEK, dayList.getSelectedDay());
					gcdeb.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(hdeb));
					gcdeb.set(GregorianCalendar.MINUTE, Integer.parseInt(mdeb));
					lesPeriods.addObject(new NSTimestamp(gcdeb.getTime()));
					GregorianCalendar gcfin = new GregorianCalendar();
					gcfin.setTime(unePeriod.dateFin());
					gcfin.set(Calendar.DAY_OF_WEEK, dayList.getSelectedDay());
					gcfin.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(hfin));
					gcfin.set(GregorianCalendar.MINUTE, Integer.parseInt(mfin));
					lesPeriods.addObject(new NSTimestamp(gcfin.getTime()));
					eoPeriods.addObject(unePeriod);
				}

				genericFactory.removePeriodicites(eoPeriods, reservation, false);

				if (typeReservation == TYPE_RESA_ENSEIGNEMENT) {
					if (!testRessourcesEnseignement(enseigns)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}

					NSArray<MaquetteAp> aps = panier.getResourcesWithType("ENSEIGNEMENT");
					this.ajouterResponsablesEC(occupants, aps);

					reservation = ensFactory.creerEdtEnseignement(reservation, agent, occupants, lesPeriods, enseigns, salles, choixSalles, objets,
							commentaire, true, true);
				}

				if (typeReservation == TYPE_RESA_REUNION_ETUDIANT) {
					if (!testRessourcesReunionsEtudiants(semestresGroupes)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}

					reservation = ensFactory.creerEdtSemestre(reservation, agent, occupants, lesPeriods, semestresGroupes, salles, choixSalles,
							objets, commentaire);
				}

				if (typeReservation == TYPE_RESA_EXAMEN) {
					if (!testRessourcesExamen(examens)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}
					ExamenFactory examenFactory = new ExamenFactory(eContext);
					reservation = examenFactory.creerExamen(reservation, agent, occupants, lesPeriods, examens, salles, choixSalles, objets,
							commentaire);
				}

				if (typeReservation == TYPE_RESA_REUNION) {
					if (!testRessourcesReunion(occupants, groupes, salles, choixSalles, objets)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}
					int typeAffichage = getSelectedIndex(myView.getMatAff());
					reservation = reunionfactory.creerReunion(reservation, agent, new NSArray<NSTimestamp>(lesPeriods), occupants, groupes, salles,
							choixSalles, objets, commentaire, typeAffichage, true, true);
				}

				if (typeReservation == TYPE_RESA_BLOCAGE) {
					if (!testRessourcesBlocage(salles, occupants, objets)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}
					reservation = reunionfactory.creerBlocage(reservation, agent, new NSArray<NSTimestamp>(lesPeriods), occupants, salles, objets,
							commentaire);
				}

				periodicitesModif = new NSArray<NSDictionary<String, Periodicite>>();
			}
			// fin if(toutePeriodicites==false)

			else {

				// modification pour toutes les periodicites
				switch (getSelectedIndex(myView.getMatTypeResa())) {

				case TYPE_RESA_ENSEIGNEMENT:
					if (!testRessourcesEnseignement(enseigns)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}
					NSArray<MaquetteAp> aps = panier.getResourcesWithType("ENSEIGNEMENT");
					this.ajouterResponsablesEC(occupants, aps);

					reservation = ensFactory.modifierEdt(reservation, agent, occupants, periodicites, enseigns, salles, choixSalles, objets,
							commentaire);
					break;

				case TYPE_RESA_REUNION_ETUDIANT:
					// TODO
					// if (!testRessourcesReunionsEtudiants(semestresGroupes)) {
					// WindowHandler.setDefaultCursor(myView);
					// myView.getBEnregistrer().setEnabled(true);
					// resaSucceed = false;
					// return;
					// }
					// reservation = ensFactory.modifierSemestre(reservation, agent, occupants, periodicites, semestresGroupes, salles,
					// choixSalles,
					// objets, commentaire);
					break;

				case TYPE_RESA_EXAMEN:
					if (!testRessourcesExamen(examens)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}
					ExamenFactory examenFactory = new ExamenFactory(eContext);
					reservation = examenFactory.modifierExamen(reservation, agent, occupants, periodicites, examens, salles, choixSalles, objets,
							commentaire);
					break;

				case TYPE_RESA_REUNION:
					if (!testRessourcesReunion(occupants, groupes, salles, choixSalles, objets)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}
					int typeAffichage = getSelectedIndex(myView.getMatAff());

					reservation = reunionfactory.modifierReunion(reservation, agent, periodicites, occupants, groupes, salles, choixSalles, objets,
							commentaire, typeAffichage);

					break;

				case TYPE_RESA_BLOCAGE:
					if (!testRessourcesBlocage(salles, occupants, objets)) {
						WindowHandler.setDefaultCursor(myView);
						myView.getBEnregistrer().setEnabled(true);
						resaSucceed = false;
						return;
					}
					reservation = reunionfactory.modifierBlocage(reservation, agent, periodicites, occupants, salles, commentaire);
					break;
				}

			} // else

			if (reservation != null) {
				resaSucceed();
			}
			else {
				resaFailed();
				WindowHandler.setDefaultCursor(myView);
				myView.getBEnregistrer().setEnabled(true);
			}
		}
		catch (Exception e) {
			if (e instanceof EdtException) {
				WindowHandler.showError(e.getMessage());
			}
			else {
				e.printStackTrace();
			}
			app.sendMailException(e);
			eContext.revert();
			resaSucceed = false;
			WindowHandler.setDefaultCursor(myView);
			DBHandler.invalidateObject(eContext, reservation);

			WindowHandler.setDefaultCursor(myView);
			myView.getBEnregistrer().setEnabled(true);
		}
		if (reservation != null && resaSucceed) {
			modificationEnabled = false;

			DBHandler.invalidateObject(eContext, reservation);
			String sDebResa = hdeb + ":" + mdeb;
			String sFinResa = hfin + ":" + mfin;

			NSMutableArray<MaquetteAp> enseignements = new NSMutableArray<MaquetteAp>();
			if (typeReservation == TYPE_RESA_ENSEIGNEMENT) {
				for (int ie = 0; ie < enseigns.count(); ie++) {
					enseignements.addObject((MaquetteAp) ((NSDictionary<String, Object>) enseigns.objectAtIndex(ie)).valueForKey("AP"));
				}
			}

			if (!toutesPeriodicites) {
				periodicites = lesPeriods;
			}

			if (reservation != null) {
				occupants = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
				// Envoi du mail cache vers le createur de la resa si ce n'est pas le meme que l'utilisateur actuel et que ce ne sera pas
				// fait par l'envoi aux dépositaires
				if (app.isSendMailDepositaires() == false && agentOrigine.equals(agent) == false) {
					String emailAgent = app.getEmailIndividu(agent);
					String emailAgentOrigine = app.getEmailIndividu(agentOrigine);
					String ms = app.mailReservation().messageFromResources(reservation, occupants, salles, choixSalles, enseignements, examens,
							periodicites, agent, true);
					app.prevenirModification(ms, emailAgent, emailAgentOrigine, null, false);
				}

				if (app.isSendMailDepositaires()) {
					NSDictionary<String, Object> ressourcesMail = this.preparerMailDetailResa(periodicites, sDebResa, sFinResa, agentOrigine,
							occupants, salles, choixSalles, enseignements, examens, objets, commentaire, MAIL_MODIFICATION);

					if (ressourcesMail != null) {
						app.mailReservation().setMailInfos(ressourcesMail);
						app.mailReservation().enregistrer();
						// app.mailReservation().show();
					}
				}

				if ((typeReservation == TYPE_RESA_REUNION || typeReservation == TYPE_RESA_ENSEIGNEMENT || typeReservation == TYPE_RESA_EXAMEN)
						&& app.isSendMailOccupants()) {
					if (occupants != null && occupants.count() > 0 && (occupants.count() > 1 || agent.equals(occupants.objectAtIndex(0)) == false)) {
						prepareMailPersonne(MAIL_MODIFICATION, reservation, null, null, null, null, null, null);
					}
				}
			}
		}

		WindowHandler.setDefaultCursor(myView);
		myView.getBEnregistrer().setEnabled(true);
	}

	/**
	 * renseigne quelles dates modifier, si l'utilisateur decide de modifier une partie des periodicites de la reservation, on n'autorise
	 * pas le changement d'heures et de semaines
	 */
	public void setDatesModification(NSArray<NSDictionary<String, Periodicite>> periodicites, boolean all) {
		periodicitesModif = periodicites;
		toutesPeriodicites = all;

		if (!all && periodicites != null) {
			Hashtable<String, Object> per = timeHandler.decomputePeriodicites(periodicites);
			myView.getTSemaines().setText((String) per.get("semaines"));
		}
		// pdm on autorise la modif des heures même si on ne modifie qu'une
		// partie des périodicités... pkoi pas huh ? une objection ?
		// tHrDeb.setEnabled(toutesPeriodicites);
		// tHrFin.setEnabled(toutesPeriodicites);
		// tMinDeb.setEnabled(toutesPeriodicites);
		// tMinFin.setEnabled(toutesPeriodicites);
		myView.getTSemaines().setEnabled(toutesPeriodicites);
	}

	/** Callback modification reserve */
	public void modifier() {
		try {
			reservation = testRecuperationHComp(reservation);
		}
		catch (EdtException e) {
			WindowHandler.showError(e.getMessage());
		}

		if (reservation != null) {

			if (!app.aDroitModification(reservation)) {
				WindowHandler.showError("Vous n'avez pas les droits de modifier cette réservation.");
				modificationEnabled = false;
				activateWidgets(false);
				return;
			}

			activateWidgets(true);
			myView.getCkDt().setEnabled(false);
			myView.getCkDt().setSelected(false);
			myView.getCbExpert().setEnabled(false);
			myView.getCbExpert().setSelected(false);
			dayList.setMultipleSelectionAllowed(false);

			if (reservation.periodicites().count() > 0) {
				if (reservation.periodicites().count() > 1) {
					PeriodicitesModification periodicitesModification = new PeriodicitesModification(this);
					WindowHandler.runModal(periodicitesModification, "Periodicites a modifier");
					if (periodicitesModif == null) {
						// on a annulé...
						modificationEnabled = false;
						activateWidgets(false);
						return;
					}
					modificationEnabled = true;
				}
				else {
					setDatesModification(reservation.periodicites(), true);
					modificationEnabled = true;
					return;
				}
			}
		}
		else {
			modificationEnabled = false;
			activateWidgets(false);
		}
	}

	/** Callback dupplication reserve */
	public void duppliquer() {
		if (reservation != null && reservation.tlocCode() != null && "E".equals(reservation.tlocCode())) {
			return;
		}
		modificationEnabled = false;
		reservation = null;
		activateWidgets(true);
	}

	/** supprimer une reservation */
	public void supprimer() {
		if (!app.aDroitSuppression(reservation)) {
			WindowHandler.showError("Vous n'avez pas les droits de supprimer cette réservation.");
			return;
		}
		setDatesModification(null, false);

		if (reservation.periodicites() != null && reservation.periodicites().count() > 1) {
			PeriodicitesModification periodicitesModification = new PeriodicitesModification(this);
			WindowHandler.runModal(periodicitesModification, "Périodicités à supprimer");
			if (periodicitesModif == null) {
				return;
			}
			if (periodicitesModif.count() == reservation.periodicites().count()) {
				toutesPeriodicites = true;
			}
		}
		else {
			toutesPeriodicites = true;
		}
		if (!WindowHandler.showConfirmation("Vous confirmez la suppression de cette réservation ?\n Elle sera définitivement perdue !")) {
			return;
		}

		try {
			WindowHandler.setWaitCursor(myView);
			NSArray<IndividuUlr> occupants = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
					+ Occupant.INDIVIDU_KEY);
			NSArray<Salles> salles = (NSArray<Salles>) reservation.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
			NSArray<ResaObjet> objets = (NSArray<ResaObjet>) reservation.valueForKeyPath(Reservation.RESERVATION_OBJETS_KEY + "."
					+ ReservationObjet.RESA_OBJET_KEY);
			NSArray<Salles> choixSalles = (NSArray<Salles>) reservation.valueForKeyPath(Reservation.SALLES_SOUHAITEES_KEY + "."
					+ SalleSouhaitee.SALLE_KEY);
			NSArray<MaquetteAp> enseignements = (NSArray<MaquetteAp>) reservation.valueForKeyPath(Reservation.RESERVATION_AP_KEY + "."
					+ ReservationAp.MAQUETTE_AP_KEY);
			NSArray<ExamenEntete> examens = (NSArray<ExamenEntete>) reservation.valueForKeyPath(Reservation.RESA_EXAMENS_KEY + "."
					+ ResaExamen.EXAMEN_ENTETE_KEY);
			IndividuUlr agentOrigine = reservation.individuAgent();
			String commentaire = reservation.resaCommentaire();
			NSArray<Periodicite> periodicites = null;

			NSMutableArray<NSTimestamp> lesPeriods = new NSMutableArray<NSTimestamp>();
			WindowHandler.setWaitCursor(myView);
			if (toutesPeriodicites == false) {
				periodicites = (NSArray<Periodicite>) periodicitesModif.valueForKey("eoPeriode");
				NSMutableArray<Periodicite> eoPeriods = new NSMutableArray<Periodicite>();
				for (int k = 0; k < periodicitesModif.count(); k++) {
					Periodicite unePeriod = (Periodicite) (periodicitesModif.objectAtIndex(k)).valueForKey("eoPeriode");
					lesPeriods.addObject(unePeriod.dateDeb());
					lesPeriods.addObject(unePeriod.dateFin());
					eoPeriods.addObject(unePeriod);
				}
				LogReservation.logModificationReservation(eContext, reservation);
				if (genericFactory.removePeriodicites(eoPeriods, reservation, true) == false) {
					throw new Exception("Echec de la suppression!");
				}
			}
			else {
				periodicites = reservation.periodicites();
				for (int i = 0; i < reservation.periodicites().count(); i++) {
					Periodicite period = (Periodicite) reservation.periodicites().objectAtIndex(i);
					lesPeriods.addObject(period.dateDeb());
					lesPeriods.addObject(period.dateFin());
				}
				LogReservation.logSuppressionReservation(eContext, reservation);
				if (genericFactory.deleteReservation(reservation, true) == false) {
					throw new Exception("Echec de la suppression!!");
				}
			}

			// Envoi du mail cache vers le createur de la resa si ce n''est pas le meme que l'utilisateur actuel
			// et que ce ne sera pas fait dans l'envoi aux dépositaires
			IndividuUlr agent = (IndividuUlr) app.userInfo("individu");
			if (app.isSendMailDepositaires() == false && agentOrigine.equals(agent) == false) {
				String emailAgent = (String) app.userInfo("email");
				String emailAgentOrigine = app.getEmailIndividu(agentOrigine);
				String ms = app.mailReservation().messageFromResources(reservation, occupants, salles, choixSalles, enseignements, examens,
						lesPeriods, (IndividuUlr) app.userInfo("individu"), true);
				app.prevenirModification(ms, emailAgent, emailAgentOrigine, null, true);
			}

			if (app.isSendMailDepositaires()) {
				String heureDebut = null;
				String heureFin = null;
				if (lesPeriods.count() > 0) {
					heureDebut = FormatHandler.dateToStr(lesPeriods.objectAtIndex(0), "%H:%M");
					heureFin = FormatHandler.dateToStr(lesPeriods.objectAtIndex(1), "%H:%M");
				}
				NSDictionary<String, Object> dicoMail = preparerMailDetailResa(lesPeriods, heureDebut, heureFin, agentOrigine, occupants, salles,
						choixSalles, enseignements, examens, objets, commentaire, MAIL_SUPPRESSION);
				if (dicoMail != null) {
					WindowHandler.setDefaultCursor(myView);
					MailReservation mailReservation = app.mailReservation();
					mailReservation.setMailInfos(dicoMail);
					mailReservation.enregistrer();
					// mailReservation.show();
				}
			}

			if (app.isSendMailOccupants()) {
				if (occupants != null && occupants.count() > 0 && (occupants.count() > 1 || agentOrigine.equals(occupants.objectAtIndex(0)) == false)) {
					NSMutableArray<ExamenEntete> examenEnteteArray = new NSMutableArray<ExamenEntete>();
					if (examens != null) {
						for (int i = 0; i < examens.count(); i++) {
							ExamenEntete examEntete = examens.objectAtIndex(i);
							if (examEntete != null) {
								examenEnteteArray.addObject(examEntete);
							}
						}
					}
					prepareMailPersonne(MAIL_SUPPRESSION, reservation, salles, occupants, periodicites, enseignements, examenEnteteArray,
							(IndividuUlr) app.userInfo("individu"));
				}
			}

			WindowHandler.setDefaultCursor(myView);
			close();
			main.refreshPlanning();
		}
		catch (Exception e) {
			e.printStackTrace();
			app.revertChanges();
			WindowHandler.showError(e.getMessage());
			WindowHandler.setDefaultCursor(myView);
		}
	}

	private void resaSucceed() {
		resaSucceed(true);
	}

	/** succes de creation de reservation : affichage du no reservation */
	private void resaSucceed(boolean refresh) {
		if (reservation != null) {

			Integer resaKey = null;
			StringBuffer infosRes = new StringBuffer();

			if (reservation.editingContext() != null) {
				resaKey = (Integer) app.primaryKey(eContext.globalIDForObject(reservation), Reservation.RESA_KEY_KEY);
				infosRes.append(reservation.valueForKeyPath(Reservation.INDIVIDU_AGENT_KEY + "." + IndividuUlr.PRENOM_KEY));
				infosRes.append(" ");
				infosRes.append(reservation.valueForKeyPath(Reservation.INDIVIDU_AGENT_KEY + "." + IndividuUlr.NOM_USUEL_KEY));
				infosRes.append(" - N " + resaKey.intValue());
				infosRes.append(" - dc = ");
				infosRes.append(FormatHandler.dateToStr(reservation.dCreation(), "%d/%m/%Y %H:%M"));
				infosRes.append(" - dm = ");
				infosRes.append(FormatHandler.dateToStr(reservation.dModification(), "%d/%m/%Y %H:%M"));
			}

			myView.getTNumero().setText(infosRes.toString());
			LogReservation logResa = LogReservation.getLastLogReservationFor(eContext, resaKey);
			if (logResa != null) {
				myView.getTNumero().setToolTipText("Motif de dernière modification: " + logResa.logMotif());
			}
			else {
				myView.getTNumero().setToolTipText(null);
			}
			String tlocCode = reservation.tlocCode();
			if (tlocCode.equals("CM") || tlocCode.equals("TD") || tlocCode.equals("TP")) {
				setSelectedIndex(TYPE_RESA_ENSEIGNEMENT, myView.getMatTypeResa());
			}
			else {
				if (tlocCode.equals("r") || tlocCode.equals("s") || tlocCode.equals("p")) {
					setSelectedIndex(TYPE_RESA_REUNION, myView.getMatTypeResa());
					if (tlocCode.equals("s")) {
						setSelectedIndex(VISIBILITE_SERVICE, myView.getMatAff());
					}
					if (tlocCode.equals("r")) {
						setSelectedIndex(VISIBILITE_PUBLIC, myView.getMatAff());
					}
					if (tlocCode.equals("p")) {
						setSelectedIndex(VISIBILITE_PRIVE, myView.getMatAff());
					}
				}
				else {
					if (tlocCode.equals("E")) {
						setSelectedIndex(TYPE_RESA_EXAMEN, myView.getMatTypeResa());
					}
					else {
						if (tlocCode.equals("b")) {
							setSelectedIndex(TYPE_RESA_BLOCAGE, myView.getMatTypeResa());
						}
					}
				}
			}

			this.activateWidgets(false);
		}
		else {
			NSLog.out.appendln("ERREUR");
		}
		// remettre a jour l'affichage
		if (refresh) {
			main.refreshPlanning();
		}
		WindowHandler.setDefaultCursor(myView);
	}

	/** echec de creation d'une reservation */
	private void resaFailed() {
		myView.getTNumero().setText("");
		myView.getTNumero().setToolTipText(null);
		activateWidgets(true);
		WindowHandler.setDefaultCursor(myView);
	}

	/** active ou desactive les elements de l'IHM */
	private void activateWidgets(boolean activate) {
		myView.getBDuppliquer().setEnabled(!activate);
		myView.getBEnregistrer().setEnabled(activate);
		myView.getBModifier().setEnabled(!activate);
		myView.getBSupprimer().setEnabled(!activate);
		myView.getTHrDeb().setEnabled(activate);
		myView.getTHrFin().setEnabled(activate);
		myView.getTHrDuree().setEnabled(activate);
		myView.getTMinDeb().setEnabled(activate);
		myView.getTMinFin().setEnabled(activate);
		myView.getTMinDuree().setEnabled(activate);
		myView.getCbExpert().setEnabled(activate);
		myView.getTSemaines().setEnabled(activate);
		myView.getTaCommentaire().setEnabled(activate);
		myView.getComboAnnees().setEnabled(activate);
		dayList.setEnabled(activate);
		Enumeration<AbstractButton> eAff = myView.getMatAff().getElements();
		while (eAff.hasMoreElements()) {
			eAff.nextElement().setEnabled(activate);
		}
		Enumeration<AbstractButton> eTypeResa = myView.getMatTypeResa().getElements();
		while (eTypeResa.hasMoreElements()) {
			eTypeResa.nextElement().setEnabled(activate);
		}
		panier.autoriserModification(activate);
	}

	/**
	 * permet d'inspecter une reservation pour pouvoir la modifier ou la supprimer chargement des ressources et des variables dans
	 * l'inspecteur
	 */
	public void inspectReservation(Reservation uneReservation) {

		WindowHandler.setWaitCursor(myView);

		cleanUp();

		this.reservation = uneReservation;
		NSArray<IndividuUlr> occupants = (NSArray<IndividuUlr>) uneReservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
				+ Occupant.INDIVIDU_KEY);
		occupants = DBHandler.retirerMultiples(occupants);
		NSArray<Salles> salles = (NSArray<Salles>) uneReservation.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		NSArray<Salles> sallesSouhaitees = (NSArray<Salles>) uneReservation.valueForKeyPath(Reservation.SALLES_SOUHAITEES_KEY + "."
				+ SalleSouhaitee.SALLE_KEY);
		NSArray<Periodicite> periodicites = uneReservation.periodicites();
		NSArray<ResaExamen> examens = reservation.resaExamens();

		NSArray<ReservationAp> enseignements = uneReservation.reservationAp();

		NSArray<ResaObjet> objets = (NSArray<ResaObjet>) uneReservation.valueForKeyPath(Reservation.RESERVATION_OBJETS_KEY + "."
				+ ReservationObjet.RESA_OBJET_KEY);

		String tlocCode = uneReservation.tlocCode();
		this.resaSucceed(false); // ne pas rafraichir l'affichage

		String resaCom = uneReservation.resaCommentaire();
		if (resaCom != null) {
			myView.getTaCommentaire().setText(resaCom);
		}

		getMyView().getBtHistorique().setEnabled(reservation.hasHistorique());

		Hashtable<String, Object> per = timeHandler.decomputePeriodicites(periodicites);

		// Sélection de l'année de la résa...
		if (periodicites != null && periodicites.count() > 0) {
			int annee = TimeHandler.getYearFor((periodicites.objectAtIndex(0)).dateDeb(), ((Boolean) app.userInfo("anneeCivile")).booleanValue());
			setComboAnneesSelected(annee);
		}

		String heureDebut = (String) per.get("heureDeb");
		String heureFin = (String) per.get("heureFin");
		// fin du todo
		StringTokenizer heureToken;
		heureToken = new StringTokenizer(heureDebut, ":");
		while (heureToken.hasMoreTokens()) {
			myView.getTHrDeb().setText(heureToken.nextToken());
			myView.getTMinDeb().setText(heureToken.nextToken());
		}

		heureToken = new StringTokenizer(heureFin, ":");
		while (heureToken.hasMoreTokens()) {
			myView.getTHrFin().setText(heureToken.nextToken());
			myView.getTMinFin().setText(heureToken.nextToken());
		}

		myView.getTSemaines().setText((String) per.get("semaines"));

		Number jour = (Number) per.get("jour");

		int indexInList = jour.intValue() - 2;
		// Les jours dans le calendriers commencent à 1, les index d'une JList commencent à 0 :
		// gestion du dimanche
		if (indexInList == -1) {
			indexInList = 6;
		}
		dayList.setSelectedIndex(indexInList);

		panier.addResources(GestionPanier.ressourcesFromRecords(occupants, GestionPanier.PERSONNE)); // ressource
		// commune
		panier.addResources(GestionPanier.ressourcesFromRecords(salles, GestionPanier.SALLE)); // idem
		panier.addResources(GestionPanier.ressourcesFromRecords(sallesSouhaitees, GestionPanier.CHOIX));
		panier.addResources(GestionPanier.ressourcesFromRecords(objets, GestionPanier.OBJET));
		panier.addResources(GestionPanier.ressourcesFromRecords(examens, GestionPanier.EXAMEN));

		NSArray<HoraireCode> horaireCodes = app.getHoraireCodes();
		NSArray<String> mhcoCodes = (NSArray<String>) horaireCodes.valueForKey(HoraireCode.MHCO_CODE_KEY);

		if (mhcoCodes.containsObject(tlocCode)) {
			panier.addResources(GestionPanier.ressourcesFromRecords(enseignements, GestionPanier.ENSEIGNEMENT_INSP));
			setSelectedIndex(TYPE_RESA_ENSEIGNEMENT, myView.getMatTypeResa());
		}

		if (tlocCode.equals(Reservation.TLOC_SEMESTRE)) {
			NSArray<ReservationSemestre> semestres = uneReservation.reservationsSemestres();
			panier.addResources(GestionPanier.ressourcesFromRecords(semestres, GestionPanier.SEMESTRE_GRP_INSP));
			setSelectedIndex(TYPE_RESA_REUNION_ETUDIANT, myView.getMatTypeResa());
		}

		if (tlocCode.equals("E")) {
			setSelectedIndex(TYPE_RESA_EXAMEN, myView.getMatTypeResa());
		}

		changeDispositionPanier();

		myView.getCbExpert().setEnabled(false);
		myView.getCbExpert().setSelected(false);
		setModeExpert(false);

		WindowHandler.setDefaultCursor(myView);
	}

	/** ajouterResponsablesEC */
	public void ajouterResponsablesEC(NSArray<IndividuUlr> individus, NSArray<MaquetteAp> aps) {

		FormationAnnee fAnnee = (FormationAnnee) myView.getComboAnnees().getSelectedItem();
		for (int i = 0; i < aps.count(); i++) {
			MaquetteAp ap = aps.objectAtIndex(i);
			EOQualifier qEc = EOQualifier.qualifierWithQualifierFormat(MaquetteEc.MAQUETTE_REPARTITION_APS_KEY + "."
					+ MaquetteRepartitionAp.MAQUETTE_AP_KEY + " = %@", new NSArray<MaquetteAp>(ap));
			NSArray<MaquetteEc> ecs = MaquetteEc.fetchMaquetteEcs(eContext, qEc, null);
			for (int j = 0; j < ecs.count(); j++) {
				MaquetteEc currentEc = ecs.objectAtIndex(j);
				for (int k = 0; k < individus.count(); k++) {
					IndividuUlr currentIndividu = individus.objectAtIndex(k);
					testEtAjoutResponsableEc(currentIndividu, fAnnee.fannKey(), currentEc);
				}
			}
		}
	}

	/** testEtAjoutResponsableEc */
	public void testEtAjoutResponsableEc(IndividuUlr individu, Number fannKey, MaquetteEc ec) {
		if (!ensFactory.isResponsableEc(individu.persId(), ec)) {
			StringBuffer msg = new StringBuffer();
			msg.append(individu.cCivilite());
			msg.append(" ");
			msg.append(individu.nomPrenom());
			msg.append(" ne fait pas partie des enseignants de ");
			msg.append(ec.mecLibelle());
			msg.append("\nVoulez vous l'ajouter comme enseignant dans la maquette ?");
			if (WindowHandler.showConfirmation(msg.toString())) {
				try {
					ensFactory.addToResponsablesEc(individu.persId(), fannKey, ec);
				}
				catch (Exception e) {
					WindowHandler.showError(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	public void afficherResponsablesAp(MaquetteAp ap) {
		FormationAnnee fAnnee = (FormationAnnee) myView.getComboAnnees().getSelectedItem();
		NSArray<IndividuUlr> individus = ensFactory.getResponsablesAp(ap, fAnnee.fannKey());

		// test si les individus ne sont pas déjà dans le panier;
		if (individus.count() > 0) {
			new RespApController(this).afficher(individus);
		}
	}

	/**
	 * permet de commencer une reservation avec un creneau trouve : voir MainInterface.creneauLibreTrouve
	 */
	public void creneauLibreTrouve(NSDictionary<String, Object> dico) {
		NSArray<NSDictionary<String, Object>> ressources = (NSArray<NSDictionary<String, Object>>) dico.valueForKey("ressources");
		int jour = ((Number) dico.valueForKey("jour")).intValue();
		String heureDeb = (String) dico.valueForKey("heureDeb");
		String heureFin = (String) dico.valueForKey("heureFin");

		StringTokenizer heureToken;
		heureToken = new StringTokenizer(heureDeb, ":");
		while (heureToken.hasMoreTokens()) {
			myView.getTHrDeb().setText(heureToken.nextToken());
			myView.getTMinDeb().setText(heureToken.nextToken());
		}

		heureToken = new StringTokenizer(heureFin, ":");
		while (heureToken.hasMoreTokens()) {
			myView.getTHrFin().setText(heureToken.nextToken());
			myView.getTMinFin().setText(heureToken.nextToken());
		}

		myView.getTSemaines().setText((String) dico.valueForKey("semaine"));

		dayList.setSelectedIndex(jour - 2);

		panier.addResources(ressources);
	}

	public Panier panier() {
		return panier;
	}

	/** netoyage de l'inspecteur des reservations */
	private void cleanUp() {
		reservation = null;
		myView.getBDuppliquer().setEnabled(false);
		myView.getBEnregistrer().setEnabled(true);
		myView.getBModifier().setEnabled(false);
		myView.getBSupprimer().setEnabled(false);
		myView.getTHrDeb().setText("");
		myView.getTHrFin().setText("");
		myView.getTHrDuree().setText("");
		myView.getTMinDeb().setText("");
		myView.getTMinFin().setText("");
		myView.getTMinDuree().setText("");
		myView.getCbExpert().setEnabled(true);
		myView.getCbExpert().setSelected(false);
		setModeExpert(false);
		dayList.setMultipleSelectionAllowed(true);
		myView.getCkDt().setEnabled(true);
		myView.getCkDt().setSelected(false);
		// myView.getTSemaines().setText("");
		myView.getTNumero().setText("");
		myView.getTNumero().setToolTipText(null);
		myView.getTaCommentaire().setText("");
		setDefaultVisibilite();
		setDefaultTypeResa();
		changeDispositionPanier();
		panier.viderPanier();
		myView.getBtHistorique().setEnabled(false);
		// comboAnnees;
		// CM
	}

	private void setDefaultVisibilite() {
		String visibilite = app.getPrefUser().defaultVisibilite();
		if (visibilite == null || visibilite.equals(PreferencesCtrl.VISIBILITE_STRINGS[VISIBILITE_PUBLIC])) {
			setSelectedIndex(VISIBILITE_PUBLIC, myView.getMatAff());
		}
		else {
			if (visibilite.equals(PreferencesCtrl.VISIBILITE_STRINGS[VISIBILITE_SERVICE])) {
				setSelectedIndex(VISIBILITE_SERVICE, myView.getMatAff());
			}
			else {
				setSelectedIndex(VISIBILITE_PRIVE, myView.getMatAff());
			}
		}
	}

	private void setDefaultTypeResa() {
		String typeResa = app.getPrefUser().defaultTypeResa();
		if (typeResa == null || typeResa.equals(PreferencesCtrl.TYPE_RESA_STRINGS[TYPE_RESA_ENSEIGNEMENT])) {
			setSelectedIndex(TYPE_RESA_ENSEIGNEMENT, myView.getMatTypeResa());
		}
		else {
			if (typeResa.equals(PreferencesCtrl.TYPE_RESA_STRINGS[TYPE_RESA_REUNION])) {
				setSelectedIndex(TYPE_RESA_REUNION, myView.getMatTypeResa());
			}
			else {
				if (typeResa.equals(PreferencesCtrl.TYPE_RESA_STRINGS[TYPE_RESA_EXAMEN])) {
					setSelectedIndex(TYPE_RESA_EXAMEN, myView.getMatTypeResa());
				}
				else {
					if (typeResa.equals(PreferencesCtrl.TYPE_RESA_STRINGS[TYPE_RESA_BLOCAGE])) {
						setSelectedIndex(TYPE_RESA_BLOCAGE, myView.getMatTypeResa());
					}
					else {
						setSelectedIndex(TYPE_RESA_REUNION_ETUDIANT, myView.getMatTypeResa());
					}
				}
			}
		}
	}

	private void formationAnneeChanged() {
		FormationAnnee formAnnee = (FormationAnnee) myView.getComboAnnees().getSelectedItem();
		panier.rechargerEnseignements(formAnnee);
		app.setCurrentWorkingAnnee(formAnnee);

	}

	/** Class de listener d'un bouton-menu */
	private class JComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			formationAnneeChanged();
		}
	}

	private void switchPanierEnseignement() {
		panier.setEnabledTab(Panier.TAB_IDX_PERSONNE, true);
		panier.setEnabledTab(Panier.TAB_IDX_ENSEIGNEMENT, true);
		panier.setEnabledTab(Panier.TAB_IDX_EXAMEN, false); // on devalide les examens (onglet d'index 3 dans panier)
		panier.setEnabledTab(Panier.TAB_IDX_OBJET, true);
		panier.setEnabledTab(Panier.TAB_IDX_SALLE, true);
		panier.setTypeReservation(getSelectedIndex(myView.getMatTypeResa()));
	}

	private void switchPanierReunion() {
		panier.setEnabledTab(Panier.TAB_IDX_PERSONNE, true);
		panier.setEnabledTab(Panier.TAB_IDX_ENSEIGNEMENT, false);
		panier.setEnabledTab(Panier.TAB_IDX_EXAMEN, false); // on devalide les examens (onglet d'index 3 dans panier)
		panier.setEnabledTab(Panier.TAB_IDX_OBJET, true);
		panier.setEnabledTab(Panier.TAB_IDX_SALLE, true);
	}

	private void switchPanierExamen() {
		panier.setEnabledTab(Panier.TAB_IDX_PERSONNE, true);
		panier.setEnabledTab(Panier.TAB_IDX_EXAMEN, true);
		panier.setEnabledTab(Panier.TAB_IDX_ENSEIGNEMENT, false); // on devalide les enseignements (onglet d'index 2 dans panier)
		panier.setEnabledTab(Panier.TAB_IDX_OBJET, true);
		panier.setEnabledTab(Panier.TAB_IDX_SALLE, true);
	}

	private void switchPanierBlocage() {
		panier.setEnabledTab(Panier.TAB_IDX_OBJET, true);
		panier.setEnabledTab(Panier.TAB_IDX_EXAMEN, false);
		panier.setEnabledTab(Panier.TAB_IDX_ENSEIGNEMENT, false); // on autorise le blocage des salles uniquement
		panier.setEnabledTab(Panier.TAB_IDX_PERSONNE, true);
		panier.setEnabledTab(Panier.TAB_IDX_SALLE, true);
	}

	private void switchPanierReunionEtudiant() {
		panier.setEnabledTab(Panier.TAB_IDX_PERSONNE, true);
		panier.setEnabledTab(Panier.TAB_IDX_ENSEIGNEMENT, true);
		panier.setEnabledTab(Panier.TAB_IDX_EXAMEN, false); // on devalide les examens (onglet d'index 3 dans panier)
		panier.setEnabledTab(Panier.TAB_IDX_OBJET, true);
		panier.setEnabledTab(Panier.TAB_IDX_SALLE, true);
		panier.setTypeReservation(getSelectedIndex(myView.getMatTypeResa()));
	}

	private void changeDispositionPanier() {

		int typeRes = getSelectedIndex(myView.getMatTypeResa());

		switch (typeRes) {

		case TYPE_RESA_ENSEIGNEMENT:
			switchPanierEnseignement();
			break;

		case TYPE_RESA_REUNION:
			switchPanierReunion();
			break;

		case TYPE_RESA_EXAMEN:
			switchPanierExamen();
			break;

		case TYPE_RESA_BLOCAGE:
			switchPanierBlocage();
			break;

		case TYPE_RESA_REUNION_ETUDIANT:
			switchPanierReunionEtudiant();
			break;
		}

	}

	private class MatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeDispositionPanier();
		}
	}

	// private NSArray retirerJoursFeries(NSArray dates) {
	// int cmpt = 0;
	// NSMutableArray periodicites = new NSMutableArray();
	// for (int idate = 0; idate < dates.count(); idate++) {
	// NSTimestamp currentDateTest = (NSTimestamp) dates.objectAtIndex(idate);
	// // Mugnier
	// // if (DateCtrl.isHolidayFR(currentDateTest)) {
	// if (verificationJourFerie(currentDateTest)) {
	// cmpt++;
	// if (cmpt == 2) {
	// // pdm on ne prévient plus quand y'a un jour férié et une résa sur plusieurs périodicités...
	// if (dates.count() <= 2) {
	// WindowHandler.showInfo("le " + FormatHandler.dateToStr(currentDateTest) + " est un jour ferie, on ne peut pas le reserver");
	// cmpt = 0;
	// }
	// }
	// }
	// else {
	// periodicites.addObject(currentDateTest);
	// }
	// }
	// return periodicites;
	// }

	// private boolean enregistrementSegmentation() {
	//
	// IndividuUlr agent = (IndividuUlr) app.userInfo("individu");
	//
	// String hdeb = myView.getTHrDeb().getText(), hfin = myView.getTHrFin().getText(), mdeb = myView.getTMinDeb().getText(), mfin = myView
	// .getTMinFin().getText(), semaineExcel = myView.getTSemaines().getText();
	// int annee = ((FormationAnnee) myView.getComboAnnees().getSelectedItem()).fannDebut().intValue();
	//
	// NSArray<NSTimestamp> creneauxDates = new NSArray<NSTimestamp>();
	// try {
	// creneauxDates = timeHandler.computePeriodicites(dayList.getSelectedDay(), semaineExcel, hdeb, hfin, mdeb, mfin, annee);
	// creneauxDates = creneauxDates.sortedArrayUsingComparator(NSComparator.AscendingTimestampComparator);
	// creneauxDates = TimeHandler.testCoherenceDates(creneauxDates);
	// creneauxDates = TimeHandler.retirerIncoherences(creneauxDates);
	// }
	// catch (Exception e1) {
	// e1.printStackTrace();
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	// }
	//
	// NSMutableArray<NSTimestamp> periodicites = new NSMutableArray<NSTimestamp>();
	//
	// for (int idate = 0; idate < creneauxDates.count(); idate++) {
	// NSTimestamp currentDateTest = creneauxDates.objectAtIndex(idate);
	// // if (!DateCtrl.isHolidayFR(currentDateTest)) {
	// if (!verificationJourFerie(currentDateTest)) {
	// periodicites.addObject(currentDateTest);
	// }
	// else {
	// System.out.println("holiday=" + currentDateTest);
	// }
	// }
	//
	// // UP 19/11/2008 : ajout pour controle
	// if (periodicites == null || periodicites.count() == 0) {
	// WindowHandler.showError("Aucune date n'est réservable !\nLa réservation en jour férié n'est pas possible.");
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	// return false;
	// }
	//
	// // /////////////////////////////////////////////
	//
	// NSArray<IndividuUlr> personnes = panier.getResourcesWithType("PERSONNE");
	// NSArray<Salles> salles = panier.getResourcesWithType("SALLE");
	// NSArray<Salles> choixSalles = panier.getResourcesWithType("CHOIX");
	// NSArray<StructuresAutorisees> groupes = panier.getResourcesWithType("GROUPE");
	// NSArray<ResaObjet> objets = panier.getResourcesWithType("OBJET");
	// NSArray<NSKeyValueCoding> enseigns = panier.getResourcesWithType("ENSEIGNEMENT_INSP");
	// NSArray<NSDictionary<String, Object>> examens = panier.getResourcesWithType("EXAMEN");
	// NSArray<NSDictionary<String, Object>> semestreGroupe = panier.getResourcesWithType("SEMESTRE_INSP");
	//
	// String commentaire = myView.getTaCommentaire().getText();
	//
	// if (commentaire == null || commentaire.equals("")) {
	// commentaire = "test algo segmentation";
	// }
	//
	// int typeReservation = getSelectedIndex(myView.getMatTypeResa());
	//
	// System.out.println("Liste des salles :");
	// for (int is = 0; is < choixSalles.count(); is++) {
	// System.out.println((choixSalles.objectAtIndex(is)).intSalNumero() + " => " + (choixSalles.objectAtIndex(is)).salPorte());
	// }
	//
	// Salles currentChoixSalle;
	// NSArray<MemoPeriode> periodes = MemoPeriode.periodesToMemoPeriodes(periodicites);
	//
	// StringBuffer patternResa = new StringBuffer();
	// MemoPeriode currentPeriode;
	// NSMutableArray<MemoPeriode> nextPeriodes = new NSMutableArray<MemoPeriode>();
	//
	// nextPeriodes.addObjectsFromArray(periodes);
	//
	// for (int isc = 0; isc < choixSalles.count(); isc++) {
	// currentChoixSalle = choixSalles.objectAtIndex(isc);
	//
	// if (MemoPeriode.containsMoreActivePeriods(nextPeriodes)) {
	// patternResa.append("$SAL$").append(currentChoixSalle.intSalNumero());
	// patternResa.append("$IP$");
	// }
	// else {
	// break;
	// }
	//
	// for (int ip = 0; ip < nextPeriodes.count(); ip++) {
	// currentPeriode = nextPeriodes.objectAtIndex(ip);
	//
	// if (currentPeriode.isActive() && ensFactory.isSalleDisponible(currentChoixSalle, currentPeriode.getDeb(), currentPeriode.getFin())) {
	// if (!patternResa.toString().endsWith("$IP$")) {
	// patternResa.append("-");
	// }
	// patternResa.append(currentPeriode.getId());
	// currentPeriode.setActive(false);
	// }
	// }
	// }
	//
	// String finalPattern = patternResa.toString();
	//
	// NSArray<String> slicesResa = NSArray.componentsSeparatedByString(finalPattern, "$SAL$");
	// String tmp = null;
	//
	// NSMutableArray<ConfigurationReservation> configsResa = new NSMutableArray<ConfigurationReservation>();
	//
	// for (int iR = 0; iR < slicesResa.count(); iR++) {
	// tmp = slicesResa.objectAtIndex(iR);
	//
	// NSArray<String> elementsResa = NSArray.componentsSeparatedByString(tmp, "$IP$");
	// if (elementsResa.count() == 2) {
	// String salNumero = elementsResa.objectAtIndex(0);
	// String periodeIds = elementsResa.objectAtIndex(1);
	//
	// NSArray<String> arrayPeriodIds = NSArray.componentsSeparatedByString(periodeIds, "-");
	//
	// if (arrayPeriodIds.count() >= 2) {
	// ConfigurationReservation currentConfig = new ConfigurationReservation();
	// currentConfig.setSalleFromSalNumero(eContext, new Integer(salNumero));
	// currentConfig.setPeriodicitesFromMemoPeriods(MemoPeriode.memoPeriodsWithIds(nextPeriodes, arrayPeriodIds));
	// configsResa.addObject(currentConfig);
	// }
	//
	// }
	// }
	//
	// // toutes les periodicites n'ont pas pu avoir de salle => on le signale...
	//
	// if (configsResa.count() == 0) {
	// WindowHandler.showError("Aucune réservation n'est possible pour les ressources choisies !");
	// WindowHandler.setDefaultCursor(myView);
	// resaFailed();
	// return false;
	// }
	//
	// if (MemoPeriode.containsMoreActivePeriods(nextPeriodes)) {
	// StringBuffer msgNoSalles = new StringBuffer();
	// msgNoSalles.append("Les semaines choisies n'ont pas pu toutes être pourvues en salles,\n");
	// msgNoSalles.append("les dates suivantes ne seront pas reservées, faute de salles :\n\n");
	// NSArray<MemoPeriode> activePeriods = MemoPeriode.getActivePeriods(nextPeriodes);
	// for (int i = 0; i < activePeriods.count(); i++) {
	// MemoPeriode period = activePeriods.objectAtIndex(i);
	// msgNoSalles.append("- ").append(FormatHandler.dateToStr(period.getDeb(), "%d/%m/%Y"));
	// msgNoSalles.append("\n");
	// }
	// msgNoSalles.append("\n");
	//
	// msgNoSalles.append("Voulez-vous poursuivre la réservation pour les salles disponibles ?");
	//
	// boolean poursuivre = WindowHandler.showConfirmation(msgNoSalles.toString());
	// if (!poursuivre) {
	// this.resaFailed();
	// return false;
	// }
	// }
	//
	// // collecter les resa créées pour l'envoi de mail
	// NSMutableArray<Reservation> allCreatedResa = new NSMutableArray<Reservation>();
	// NSMutableArray<Salles> allSallesReservees = new NSMutableArray<Salles>();
	// NSMutableArray<NSTimestamp> allPeriodicitesReservees = new NSMutableArray<NSTimestamp>();
	//
	// boolean result = false;
	//
	// if (typeReservation == ENSEIGNEMENT) {
	// if (!testRessourcesEnseignement(enseigns)) {
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	// return false;
	// }
	// try {
	// NSArray<MaquetteAp> aps = panier.getResourcesWithType("ENSEIGNEMENT");
	// this.ajouterResponsablesEC(personnes, aps);
	//
	// for (int iC = 0; iC < configsResa.count(); iC++) {
	// ConfigurationReservation currentConfig = configsResa.objectAtIndex(iC);
	// NSArray<Salles> cfSalles = new NSArray<Salles>(currentConfig.getSalle());
	// NSArray<NSTimestamp> cfPeriodicites = currentConfig.getPeriodicites();
	// reservation = ensFactory.creerEdt(null, agent, personnes, cfPeriodicites, enseigns, cfSalles, null, objets, commentaire, true);
	// allSallesReservees.addObjectsFromArray(cfSalles);
	// allPeriodicitesReservees.addObjectsFromArray(cfPeriodicites);
	// allCreatedResa.addObject(reservation);
	// }
	//
	// }
	// catch (Exception e) {
	// WindowHandler.showError(e.getMessage());
	// eContext.revert();
	//
	// if (e instanceof Exception) {
	// e.printStackTrace();
	// }
	//
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	// }
	//
	// if (reservation != null) {
	// this.resaSucceed();
	// result = true;
	// }
	// else {
	// this.resaFailed();
	// result = false;
	// }
	// }
	//
	// // EXAMEN
	// if (typeReservation == EXAMEN) {
	// if (!testRessourcesExamen(examens)) {
	// myView.getBEnregistrer().setEnabled(true);
	// WindowHandler.setDefaultCursor(myView);
	// return false;
	// }
	// ExamenFactory examenFactory = new ExamenFactory(eContext);
	// try {
	//
	// for (int iC = 0; iC < configsResa.count(); iC++) {
	// ConfigurationReservation currentConfig = configsResa.objectAtIndex(iC);
	// NSArray<Salles> cfSalles = new NSArray<Salles>(currentConfig.getSalle());
	// NSArray<NSTimestamp> cfPeriodicites = currentConfig.getPeriodicites();
	// reservation = examenFactory.creerExamen(null, agent, personnes, cfPeriodicites, examens, cfSalles, null, objets, commentaire);
	// allSallesReservees.addObjectsFromArray(cfSalles);
	// allPeriodicitesReservees.addObjectsFromArray(cfPeriodicites);
	// allCreatedResa.addObject(reservation);
	// }
	// }
	// catch (Exception e) {
	// WindowHandler.showError(e.getMessage());
	// if (e instanceof Exception) {
	// e.printStackTrace();
	// }
	// eContext.revert();
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	// }
	//
	// if (reservation != null) {
	// this.resaSucceed();
	// result = true;
	// }
	// else {
	// this.resaFailed();
	// result = false;
	// }
	// }
	//
	// if (typeReservation == REUNION_ETUDIANT) {
	//
	// try {
	// for (int iC = 0; iC < configsResa.count(); iC++) {
	// ConfigurationReservation currentConfig = configsResa.objectAtIndex(iC);
	// NSArray<Salles> cfSalles = new NSArray<Salles>(currentConfig.getSalle());
	// NSArray<NSTimestamp> cfPeriodicites = currentConfig.getPeriodicites();
	//
	// reservation = ensFactory.creerEdtSemestre(null, agent, personnes, cfPeriodicites, semestreGroupe, cfSalles, null, objets,
	// commentaire);
	//
	// allSallesReservees.addObjectsFromArray(cfSalles);
	// allPeriodicitesReservees.addObjectsFromArray(cfPeriodicites);
	// allCreatedResa.addObject(reservation);
	// }
	// }
	// catch (Exception e) {
	// WindowHandler.showError(e.getMessage());
	// eContext.revert();
	//
	// if (e instanceof Exception) {
	// e.printStackTrace();
	// }
	//
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	// }
	//
	// if (reservation != null) {
	// this.resaSucceed();
	// result = true;
	// }
	// else {
	// this.resaFailed();
	// result = false;
	// }
	//
	// } // REUNION_ETUDIANT
	//
	// if (typeReservation == BLOCAGE) {
	// WindowHandler.showError("Veuillez ne pas mettre de salles 'au choix' pour effectuer un blocage !");
	// return false;
	// }
	//
	// /**** TYPE DE RESERVATION : REUNION **/
	// if (typeReservation == REUNION) {
	//
	// if (!testRessourcesReunion(personnes, groupes, salles, choixSalles, objets)) {
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	// return false;
	// }
	//
	// try {
	// int typeAffichage = getSelectedIndex(myView.getMatAff());
	//
	// for (int iC = 0; iC < configsResa.count(); iC++) {
	// ConfigurationReservation currentConfig = configsResa.objectAtIndex(iC);
	// NSArray<Salles> cfSalles = new NSArray<Salles>(currentConfig.getSalle());
	// NSArray<NSTimestamp> cfPeriodicites = currentConfig.getPeriodicites();
	// reservation = reunionfactory.creerReunion(null, agent, new NSArray<NSTimestamp>(cfPeriodicites), personnes, groupes, cfSalles,
	// null, objets, commentaire, typeAffichage, true);
	// allSallesReservees.addObjectsFromArray(cfSalles);
	// allPeriodicitesReservees.addObjectsFromArray(cfPeriodicites);
	// allCreatedResa.addObject(reservation);
	// }
	// }
	// catch (Exception e) {
	// WindowHandler.showError(e.getMessage());
	//
	// if (e instanceof Exception) {
	// e.printStackTrace();
	// }
	// allCreatedResa.removeAllObjects();
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	// }
	// finally {
	// eContext.revert();
	// }
	//
	// if (reservation != null) {
	// this.resaSucceed();
	// result = true;
	// }
	// else {
	// this.resaFailed();
	// result = false;
	// }
	// }
	//
	// NSDictionary<String, Object> ressourcesMail = null;
	// if (allCreatedResa.count() > 0 && app.isSendMailDepositaires()) {
	// String sDebResa = hdeb + ":" + mdeb;
	// String sFinResa = hfin + ":" + mfin;
	//
	// NSMutableArray<MaquetteAp> enseignements = new NSMutableArray<MaquetteAp>();
	// for (int ie = 0; ie < enseigns.count(); ie++) {
	// enseignements.addObject((MaquetteAp) ((NSDictionary<String, Object>) enseigns.objectAtIndex(ie)).valueForKey("AP"));
	// }
	// ressourcesMail = this.preparerMailDetailResa(allPeriodicitesReservees, sDebResa, sFinResa, agent, personnes, allSallesReservees,
	// null,
	// enseignements, examens, objets, commentaire, MAIL_CREATION);
	//
	// if (ressourcesMail != null) {
	// String dest = (String) ressourcesMail.valueForKey("destinataire");
	// if (!"".equals(dest)) {
	// MailReservation mailReservation = app.mailReservation();
	// mailReservation.setMailInfos(ressourcesMail);
	// mailReservation.enregistrer();
	// // mailReservation.show();
	// }
	// }
	// }
	//
	// if ((typeReservation == REUNION || typeReservation == ENSEIGNEMENT || typeReservation == EXAMEN) && app.isSendMailOccupants()) {
	// if (reservation != null && personnes.count() > 0 && allSallesReservees.count() == 0) {
	// NSArray<IndividuUlr> occupants = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
	// + Occupant.INDIVIDU_KEY);
	// if (occupants != null && occupants.count() > 0 && (occupants.count() > 1 || agent.equals(occupants.objectAtIndex(0)) == false)) {
	// prepareMailPersonne(MAIL_CREATION, reservation, null, null, null, null, null, null);
	// }
	// }
	// }
	//
	// WindowHandler.setDefaultCursor(myView);
	// myView.getBEnregistrer().setEnabled(true);
	//
	// /*
	// * MailPersSalObj mailPersSalObj= new MailPersSalObj(eContext,reservation); mailPersSalObj.init();
	// */
	//
	// // Creation de DT si necessaire...
	// if (myView.getCkDt().isSelected()) {
	// String dtsamEmail = (String) app.invokeRemoteMethod(eContext, "session", "clientSideRequestDTSAMEmail", null);
	// if (dtsamEmail != null && dtsamEmail.trim().length() > 0) {
	// if (WindowHandler.showConfirmation("Création de la DT associée ?")) {
	// if (ressourcesMail == null) {
	// NSMutableArray<MaquetteAp> enseignements = new NSMutableArray<MaquetteAp>();
	// for (int ie = 0; ie < enseigns.count(); ie++) {
	// enseignements.addObject((MaquetteAp) ((NSDictionary<String, Object>) enseigns.objectAtIndex(ie)).valueForKey("AP"));
	// }
	// ressourcesMail = this.preparerMailDetailResa(allPeriodicitesReservees, hdeb + ":" + mdeb, hfin + ":" + mfin, agent,
	// personnes, allSallesReservees, null, enseignements, examens, objets, commentaire, MAIL_CREATION);
	// }
	// if (ressourcesMail != null
	// && app.remoteSendMail((String) app.userInfo("email"), dtsamEmail, null, (String) ressourcesMail.objectForKey("sujet"),
	// (String) ressourcesMail.objectForKey("texte"))) {
	// WindowHandler.showInfo("DT créée !");
	// }
	// else {
	// WindowHandler.showError("Une erreur est survenue, la DT n'a pas pu être créée !");
	// }
	// }
	// }
	// }
	// return result;
	// }

	// verifie qu'on peut reserver : verificationJourFerie
	// c'est bien un jour ferie en tenant compte de l'autorisation de reservation les jours feriés
	public boolean verificationJourFerie(NSTimestamp date) {
		boolean autoriseReservationFerie = app.autoriseReservationFerie();
		if (autoriseReservationFerie) {
			return false;
		}
		return DateCtrl.isHolidayFR(date);
	}

	private class TimeFocusListener implements FocusListener {

		private static final String DEFAULT_VALUE = "00";

		public void focusGained(FocusEvent e) {
		}

		public void focusLost(FocusEvent e) {

			JTextField src = (JTextField) e.getSource();
			String value = src.getText().trim();
			if (!value.equals("")) {
				return;
			}
			src.setText(DEFAULT_VALUE);
		}

	}

	public class WindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			main.refreshPlanning();
			IS_OPEN = false;
		}
	}

	public static boolean isOpen() {
		return IS_OPEN;
	}

	public InspecteurView getMyView() {
		return myView;
	}

	public GenericFactory getGenericFactory() {
		return genericFactory;
	}

}
