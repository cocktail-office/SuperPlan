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

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.factory.VerificationFactory;
import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.HoraireCode;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.Messages;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.metier.PrefUser;
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
import org.cocktail.superplan.client.metier.ScolDroitDiplome;
import org.cocktail.superplan.client.metier.Secretariat;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.VParcoursAp;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eoapplication.EOFrameController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimeZone;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.CalendarHandler;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;
import fr.univlr.utilities.WaitingHandler;

public class MainClient extends EOApplication {

	public static final String RETURN = System.getProperty("line.separator");

	// chemin vers les attributs décrivant un diplome en partant d'un scolFormationHabilitation au niveau du modèle.
	private static final NSDictionary dicoLibelles = new NSDictionary(new Object[] {
			"",
			FormationHabilitation.FORMATION_SPECIALISATION_KEY + "." + FormationSpecialisation.FSPN_LIBELLE_KEY,
			FormationHabilitation.FORMATION_SPECIALISATION_KEY + "." + FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "."
					+ FormationDiplome.FDIP_ABREVIATION_KEY }, new Object[] { "VOID", "SPECIALISATION_LIB", "DIPLOME_ABREV" });

	private MainInterface mainInterface;
	private EOEditingContext eContext = new EOEditingContext();
	private NSMutableDictionary userInfos;
	private String userName;
	private CalendarHandler calendarHandler;
	private NSArray<FormationAnnee> formationAnnees;
	private MailReservation mailReservation;
	// annee scolaire courante, déclarée dans SCOLARITE.SCOL_FORMATION_ANNEE
	private Number currentFannKey;

	public WaitingHandler waitingHandler;

	private int noSemaine = 0;
	// preferences utilisateur
	private PrefUser prefUser;
	// les factories
	EnseignementFactory ensFactory;

	private FormationAnnee currentWorkingAnnee;

	private ArrayList attrColumn1Dipl, attrColumn2Dipl;
	public static final String VOID_COLUMN = "VOID";

	private boolean isReservationSalleParDepositaire, isSendMailDepositaires, isSendMail, isAppControleMissions, isAppDroitsSurContraintesIndividus;
	private boolean isAppExigeMotifModification, isAppExigeMotifSuppression;
	private String appControleApsOccupation;

	private String lastMessageDisplayed = null;

	/** methode appelee a la fin du demarrage de l'application */
	public void finishInitialization() {
		super.finishInitialization();
		setQuitsOnLastWindowClose(false);

		this.initApplication();
	}

	private void initApplication() {
		// controle versions...
		Hashtable<String, String> appInfos = getServerApplicationInfos();
		if (appInfos == null) {
			WindowHandler.showError("Problème pour lancer l'application (impossible de déterminer la version utilisée).");
			quit();
		}
		if (appInfos.get("version") == null || appInfos.get("date") == null) {
			WindowHandler.showError("Problème pour lancer l'application (impossible de déterminer la version utilisée)...");
			quit();
		}
		if (appInfos.get("version").equals(VersionClient.version()) == false) {
			WindowHandler.showError("Problème pour lancer l'application (la version cliente est différente de la version serveur: client v"
					+ VersionClient.version() + ", serveur v" + appInfos.get("version") + ").\nContacter le responsable de cette installation.");
			quit();
		}
		if (appInfos.get("date").equals(VersionClient.VERSIONDATE) == false) {
			WindowHandler
					.showError("Problème pour lancer l'application (la date de la version cliente est différente de la date de la version serveur: client date version "
							+ VersionClient.VERSIONDATE
							+ ", serveur date version "
							+ appInfos.get("date")
							+ ").\nContacter le responsable de cette installation.");
			quit();
		}

		LoginPasswd loginPass = new LoginPasswd(eContext);
		String casUserName = LoginPasswd.getCASUserName(this);
		if (casUserName == null) {
			EOArchive.loadArchiveNamed("LoginPasswd", loginPass, "edtscol.client", loginPass.disposableRegistry());
			loginPass.afficher();
		}
		else {
			setUserName(casUserName);
			setUserInfos(loginPass.getUserInfos(casUserName, null, "O"));
		}

		waitingHandler = WaitingHandler.getInstance("SuperPlan", "Lancement en cours ...", "", new Dimension(300, 60));

		// Recuperation du time zone a partir du serveur
		String tzString = (String) invokeRemoteMethod(eContext, "session", "clientSideRequestDefaultTimeZoneString", null);
		if (tzString == null) {
			tzString = "Europe/Paris";
		}
		java.util.TimeZone tz = java.util.TimeZone.getTimeZone(tzString);
		java.util.TimeZone.setDefault(tz);
		NSTimeZone.setDefault(tz);

		String libDescPattern = (String) userInfo("libDescPattern");
		if (StringCtrl.isEmpty(libDescPattern) == false) {
			NSArray<String> comp = NSArray.componentsSeparatedByString(libDescPattern, "|");
			setAttrColumn1Dipl(parseAttributes(comp.objectAtIndex(0)));
			setAttrColumn2Dipl(parseAttributes(comp.objectAtIndex(1)));
		}
		else {
			setAttrColumn1Dipl(null);
			setAttrColumn2Dipl(null);
		}
		ensFactory = new EnseignementFactory(eContext);

		mailReservation = new MailReservation(this);

		EOSortOrdering sortAnnees = EOSortOrdering.sortOrderingWithKey(FormationAnnee.FANN_KEY_KEY, EOSortOrdering.CompareAscending);
		formationAnnees = FormationAnnee.fetchAllFormationAnnees(eContext, new NSArray<EOSortOrdering>(sortAnnees));
		boolean anneeCivile = ((Boolean) this.userInfo("anneeCivile")).booleanValue();
		if (formationAnnees != null) {
			for (int i = 0; i < formationAnnees.count(); i++) {
				FormationAnnee fa = formationAnnees.objectAtIndex(i);
				fa.setUseAnneeCivile(anneeCivile);

				if (fa.fannCourante().equals("O")) {
					setCurrentFannKey(fa.fannKey());
				}

			}
		}

		this.calendarHandler = new CalendarHandler();
		mainInterface = new MainInterface(eContext, this);
		EOArchive.loadArchiveNamed("MainInterface", mainInterface, "edtscol.client", mainInterface.disposableRegistry());

		IndividuUlr agent = (IndividuUlr) userInfo("individu");

		String navn = StringCtrl.capitalize(agent.prenom()) + " " + agent.nomUsuel();
		String titre = "SuperPlan - [ " + navn + " ]";
		if (anneeCivile) {
			titre = titre + " - Année civile - ";
		}
		else {
			titre = titre + " - Année universitaire - ";
		}
		String defaultConnectionUrl = (String) invokeRemoteMethod(eContext, "session", "clientSideRequestDefaultConnectionUrl", null);
		if (defaultConnectionUrl != null) {
			titre = titre + defaultConnectionUrl;
		}

		isReservationSalleParDepositaire = ((Boolean) invokeRemoteMethod(eContext, "session", "clientSideRequestAppReservationSalleParDepositaire",
				null)).booleanValue();
		isAppDroitsSurContraintesIndividus = ((Boolean) invokeRemoteMethod(eContext, "session", "clientSideRequestAppDroitsSurContraintesIndividus",
				null)).booleanValue();
		isSendMailDepositaires = ((Boolean) invokeRemoteMethod(eContext, "session", "clientSideRequestAppSendMailDepositaires", null)).booleanValue();
		isSendMail = ((Boolean) invokeRemoteMethod(eContext, "session", "clientSideRequestAppSendMail", null)).booleanValue();
		isAppControleMissions = ((Boolean) invokeRemoteMethod(eContext, "session", "clientSideRequestAppControleMissions", null)).booleanValue();
		appControleApsOccupation = ((String) invokeRemoteMethod(eContext, "session", "clientSideRequestAppControleApsOccupation", null));
		isAppExigeMotifModification = ((Boolean) invokeRemoteMethod(eContext, "session", "clientSideRequestAppExigeMotifModification", null))
				.booleanValue();
		isAppExigeMotifSuppression = ((Boolean) invokeRemoteMethod(eContext, "session", "clientSideRequestAppExigeMotifSuppression", null))
				.booleanValue();

		Timer tictac = new Timer(true);
		tictac.schedule(new TimerTask() {
			public void run() {
				Messages msg = getMessageForClients();
				if (msg != null) {
					String msgLib = msg.msgLib();
					if (msgLib != null) {
						if (lastMessageDisplayed == null || lastMessageDisplayed.equals(msgLib) == false) {
							lastMessageDisplayed = msgLib;
							if (msg.msgRepeat().equals("O")) {
								msgLib += "\n\nCe message s'affiche automatiquement toutes les 3 minutes... voulez-vous le désactiver ?";
								int choix = JOptionPane.showConfirmDialog(mainInterface().component(), msgLib, "Information",
										JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
								if (choix != 0) {
									lastMessageDisplayed = null;
								}
							}
							else {
								JOptionPane.showMessageDialog(mainInterface().component(), msgLib, "Information", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
				else {
					lastMessageDisplayed = null;
				}
			}
		}, 15000, 180000);

		EOFrameController.runControllerInNewFrame(mainInterface, titre);
		setQuitsOnLastWindowClose(true);
	}

	public Messages getMessageForClients() {
		try {
			NSArray<Messages> a = Messages.fetchAllMessageses(eContext);
			if (a != null && a.count() > 0) {
				return a.objectAtIndex(0);
			}
		}
		catch (Exception e) {
			return null;
		}
		return null;
	}

	public Hashtable<String, String> getServerApplicationInfos() {
		EODistributedObjectStore dos = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		String name = (String) dos.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestAppliId", null, null, false);
		String version = (String) dos.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestAppliVersion", null, null, false);
		String date = (String) dos.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestAppliDate", null, null, false);
		Hashtable<String, String> infosApp = new Hashtable<String, String>();
		infosApp.put("applicationName", name);
		infosApp.put("version", version);
		infosApp.put("date", date);
		return infosApp;
	}

	public void setUserInfos(NSDictionary userinfos) {
		this.userInfos = new NSMutableDictionary(userinfos);

		IndividuUlr individu = IndividuUlr.fetchIndividuUlr(eContext, IndividuUlr.PERS_ID_KEY, userinfos.objectForKey("persId"));
		if (individu != null) {
			this.userInfos.setObjectForKey(individu, "individu");
		}

		// l'utilisateur est ok : on charge ses preferences.
		prefUser = PrefUser.fetchPrefUser(eContext, PrefUser.INDIVIDU_KEY, individu);
		if (prefUser == null) {
			String debImp = grhumParametre("EDT_HEURE_DEB_IMPRESSION");
			String finImp = grhumParametre("EDT_HEURE_FIN_IMPRESSION");
			Integer debImpress, finImpress;
			if (debImp == null) {
				debImpress = new Integer(7);
			}
			else {
				debImpress = new Integer(debImp);
			}
			if (finImp == null) {
				finImpress = new Integer(20);
			}
			else {
				finImpress = new Integer(finImp);
			}
			prefUser = PrefUser.getInstance(eContext);
			eContext.insertObject(prefUser);
			prefUser.setIndividuRelationship(individu);
			prefUser.setAnneeCivile(new Integer(0));
			prefUser.setDipEdtSal(new Integer(0));
			prefUser.setDipEdtPers(new Integer(0));
			prefUser.setCommentScol(new Integer(0));
			prefUser.setDispParCom(new Integer(1));
			prefUser.setDispExam(new Integer(0));
			prefUser.setPasMailHorsSemaine(new Integer(0));
			prefUser.setUseRespAp("O");
			prefUser.setColorEc("N");
			prefUser.setSendMailOccupants("O");
			prefUser.setSendMailDepositaires("O");
			prefUser.setDefaultVisibilite(PreferencesCtrl.VISIBILITE_STRINGS[1]);
			prefUser.setDefaultTypeResa(PreferencesCtrl.TYPE_RESA_STRINGS[0]);
			prefUser.setHeureDebImpress(debImpress);
			prefUser.setHeureFinImpress(finImpress);
			prefUser.setSelectionGroupeMultiple("N");
			prefUser.setUseTooltipPlanning("O");
			prefUser.setAffHorairesHamac(new Integer(0));
			saveChanges();

			LoginPasswd loginPass = new LoginPasswd(eContext);
			String casUserName = LoginPasswd.getCASUserName(this);
			setUserInfos(loginPass.getUserInfos(casUserName != null ? casUserName : userName, null, "O"));
			return;
		}
	}

	public NSDictionary userInfos() {
		return userInfos;
	}

	public EOEditingContext editingContext() {
		return this.eContext;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public PrefUser getPrefUser() {
		return prefUser;
	}

	public boolean isSendMail() {
		return isSendMail;
	}

	public boolean isSendMailDepositaires() {
		return isSendMailDepositaires;
	}

	public boolean isSendMailOccupants() {
		return isSendMail() && "O".equals(getPrefUser().sendMailOccupants());
	}

	public boolean isReservationSalleParDepositaire() {
		return isReservationSalleParDepositaire;
	}

	public boolean isAppDroitsSurContraintesIndividus() {
		return isAppDroitsSurContraintesIndividus;
	}

	public boolean isAnneeUniversitaire() {
		Number anneeCivile = prefUser.anneeCivile();
		return (anneeCivile != null) && (anneeCivile.intValue() == 0);
	}

	public boolean isAppControleMissions() {
		return isAppControleMissions;
	}

	public boolean isAppControleApsOccupation() {
		return isAppControleApsOccupationOui() || isAppControleApsOccupationBof();
	}

	public boolean isAppControleApsOccupationOui() {
		return appControleApsOccupation != null
				&& (appControleApsOccupation.equalsIgnoreCase("YES") || appControleApsOccupation.equalsIgnoreCase("OUI")
						|| appControleApsOccupation.equalsIgnoreCase("O") || appControleApsOccupation.equalsIgnoreCase("TRUE"));
	}

	// default
	public boolean isAppControleApsOccupationBof() {
		return appControleApsOccupation == null || appControleApsOccupation.equalsIgnoreCase("BOF")
				|| (!isAppControleApsOccupationOui() && !isAppControleApsOccupationNon());
	}

	public boolean isAppControleApsOccupationNon() {
		return appControleApsOccupation != null
				&& (appControleApsOccupation.equalsIgnoreCase("NO") || appControleApsOccupation.equalsIgnoreCase("NON")
						|| appControleApsOccupation.equalsIgnoreCase("N") || appControleApsOccupation.equalsIgnoreCase("FALSE"));
	}

	public boolean autoriseReservationFerie() {
		Boolean info = (Boolean) userInfo("autoriseReservationFerie");
		return info.booleanValue();
	}

	public boolean isDroitConsultationLogsHistorisation() {
		Boolean consultationLogsHistorisation = (Boolean) userInfo("consultationLogsHistorisation");
		return (consultationLogsHistorisation != null && consultationLogsHistorisation.booleanValue());
	}

	public MainInterface mainInterface() {
		return mainInterface;
	}

	public AppCalendar appCalendar() {
		return (mainInterface != null) ? mainInterface.appCalendar() : null;
	}

	/** envoi de mail cote serveur toujours : le serveur mail n'accepte pas les connexions externes */
	public boolean remoteSendMail(String exp, String dest, String cc, String objet, String msg) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSDictionary<String, String> dicoMail;
		if (exp == null) {
			exp = getEmailIndividu((IndividuUlr) userInfo("individu"));
		}
		if (cc != null) {
			String[] keys = { "expediteur", "destinataire", "cc", "sujet", "texte" };
			String[] values = { exp, dest, cc, objet, msg };
			dicoMail = new NSDictionary<String, String>(values, keys);
		}
		else {
			String[] keys = { "expediteur", "destinataire", "sujet", "texte" };
			String[] values = { exp, dest, objet, msg };
			dicoMail = new NSDictionary<String, String>(values, keys);
		}
		Boolean ok = (Boolean) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestSendMail",
				new Class[] { NSDictionary.class }, new Object[] { dicoMail }, false);

		return ok.booleanValue();
	}

	public void sendMailException(Throwable t) {
		try {
			if (t != null && t instanceof EdtException == false) {
				remoteSendMail(null, "pdm@univ-lr.fr", null, "[SuperPlan] Exception client", getStackTrace(t));
			}
		}
		catch (Throwable t2) {
			t2.printStackTrace();
		}
	}

	private String getStackTrace(Throwable t) {
		StringBuffer sb = new StringBuffer();
		if (t != null) {
			sb.append(t.toString() + "\n");
			StackTraceElement[] ste = t.getStackTrace();
			if (ste != null) {
				for (int i = 0; i < ste.length; i++) {
					sb.append("   ");
					sb.append(ste[i].toString());
					sb.append("\n");
				}
			}
			if (t.getCause() != null) {
				sb.append("Caused by:\n");
				sb.append(getStackTrace(t.getCause()));
			}
		}
		else {
			sb.append("No stack trace !!!");
		}
		return sb.toString();
	}

	/** retourne l'adresse email principale de l'individu */
	public String getEmailIndividu(IndividuUlr individu) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (String) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestEmailIndividu",
				new Class[] { Number.class }, new Object[] { individu.persId() }, false);
	}

	/** retourne l'adresse email principale des individus */
	public NSArray<String> getEmailIndividus(NSArray<Integer> persIDs) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray<String>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestEmailIndividus",
				new Class[] { NSArray.class }, new Object[] { persIDs }, false);
	}

	public EnseignementFactory ensFactory() {
		return ensFactory;
	}

	/** envoie le mail pour prevenir de la modification ou la suppression de reservation */
	public void prevenirModification(String message, String emailAgent, String emailAgentOrigine, String cc, boolean suppr) {
		if (emailAgentOrigine == null || emailAgent == null) {
			return;
		}
		try {
			String objet;
			if (suppr) {
				objet = "ALERTE : Suppression réservation";
			}
			else {
				objet = "ALERTE : Modification réservation";
			}

			remoteSendMail(emailAgent, emailAgentOrigine, cc, objet, message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** envoie un parametre a partir de la table des parametres de reservation */
	public String grhumParametre(String param) {
		EOQualifier qualifierParam = EOQualifier.qualifierWithQualifierFormat("paramKey like '*" + param + "*'", null);
		EOGenericRecord retour = DBHandler.fetchUniqueData(eContext, "FwkCktlWebApp_GrhumParametres", qualifierParam);
		String valeur = null;
		if (retour != null) {
			valeur = (String) retour.valueForKey("paramValue");
		}
		return valeur;
	}

	public MailReservation mailReservation() {
		return mailReservation;
	}

	private ArrayList parseAttributes(String pattern) {
		ArrayList list = new ArrayList();

		NSArray<String> comp = null;

		if (pattern == null || pattern.equals("VOID")) {
			return null;
		}

		if (pattern.indexOf("+") != -1) {
			comp = NSArray.componentsSeparatedByString(pattern, "+");
			String attr1 = comp.objectAtIndex(0);
			String attr2 = comp.objectAtIndex(1);

			list.add(dicoLibelles.objectForKey(attr1));
			list.add(dicoLibelles.objectForKey(attr2));

			return list;
		}
		else {

			list.add(dicoLibelles.objectForKey(pattern));
			return list;
		}
	}

	/** retourne les formationAnnees */
	public NSArray<FormationAnnee> getFormationAnnees() {
		return formationAnnees;
	}

	/**
	 * retourne les dates de debut et de fin de l'annee universitaire dans le cas d'annee civile ou non.
	 */
	public static NSTimestamp[] getDateRangeForAnneeUniv(FormationAnnee fAnnee) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		NSTimestamp debut, fin;
		int fannDebut = fAnnee.fannDebut().intValue();
		int fannFin = fAnnee.fannFin().intValue();

		if (fannDebut == fannFin) {
			cal.set(Calendar.YEAR, fAnnee.fannKey().intValue());
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.setTime(cal.getTime());
			debut = new NSTimestamp(cal.getTime());
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 31);
			cal.setTime(cal.getTime());
			fin = new NSTimestamp(cal.getTime());
		}
		else {
			cal.set(Calendar.YEAR, fannDebut);
			cal.set(Calendar.MONTH, Calendar.JULY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.setTime(cal.getTime());
			debut = new NSTimestamp(cal.getTime());
			cal.set(Calendar.YEAR, fannFin);
			cal.set(Calendar.MONTH, Calendar.JUNE);
			cal.set(Calendar.DAY_OF_MONTH, 31);
			cal.setTime(cal.getTime());
			fin = new NSTimestamp(cal.getTime());
		}
		return new NSTimestamp[] { debut, fin };
	}

	public NSArray getHoraireCodes() {
		EOKeyValueQualifier qualifier = new EOKeyValueQualifier(HoraireCode.MHCO_VALIDITE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O");
		return HoraireCode.fetchHoraireCodes(
				eContext,
				qualifier,
				new NSArray(new EOSortOrdering[] { EOSortOrdering.sortOrderingWithKey(HoraireCode.MHCO_CODE_KEY,
						EOSortOrdering.CompareCaseInsensitiveAscending) }));
	}

	public NSArray getHoraireCodesPresenciel() {
		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat(HoraireCode.MHCO_PRIORITE_KEY + " <= 3", null);
		EOSortOrdering libelleOrdering = EOSortOrdering.sortOrderingWithKey(HoraireCode.MHCO_LIBELLE_KEY, EOSortOrdering.CompareAscending);

		return HoraireCode.fetchHoraireCodes(eContext, qual, new NSArray(libelleOrdering));
	}

	/** renvoie les formations specialisations auquelles l'agent a droit */
	private NSArray<FormationHabilitation> formationSpecialisationAgent(Integer fannKey) {
		NSArray droits = (NSArray) userInfo("droits");

		NSMutableArray sumQualifiers = new NSMutableArray();
		if (droits.count() > 0) {
			EOQualifier qualDroitsDiplomes = EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.DROIT_DIPLOMES_KEY + "."
					+ ScolDroitDiplome.DLOG_KEY_KEY + " = %@", new NSArray(droits.objectAtIndex(0)));
			sumQualifiers.addObject(qualDroitsDiplomes);
			EOQualifier qualDroitsAnnee = EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.DROIT_DIPLOMES_KEY + "."
					+ ScolDroitDiplome.FANN_KEY_KEY + " = %@", new NSArray(fannKey));
			sumQualifiers.addObject(qualDroitsAnnee);
			EOQualifier qualDroitsEdt = EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.DROIT_DIPLOMES_KEY + "."
					+ ScolDroitDiplome.DDIP_EDT_KEY + " = %@", new NSArray("A"));
			sumQualifiers.addObject(qualDroitsEdt);
		}
		else {
			return new NSArray();
		}
		EOQualifier totalQualifier = new EOAndQualifier(sumQualifiers);

		return (NSArray<FormationHabilitation>) FormationHabilitation.fetchFormationHabilitations(eContext, totalQualifier, null).valueForKey(
				FormationHabilitation.FORMATION_SPECIALISATION_KEY);
	}

	public NSArray<FormationHabilitation> getPreferencesHabilitations(FormationAnnee fa) {
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.addObject(new EOKeyValueQualifier(PrefScol.INDIVIDU_KEY, EOKeyValueQualifier.QualifierOperatorEqual, userInfo("individu")));
		if (fa != null) {
			quals.addObject(new EOKeyValueQualifier(PrefScol.ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, fa));
		}
		NSArray<PrefScol> preferences = PrefScol.fetchPrefScols(eContext, new EOAndQualifier(quals), null);
		return (NSArray<FormationHabilitation>) preferences.valueForKey(PrefScol.HABILITATION_KEY);
	}

	public boolean aDroitSuppressionExamen(Reservation resa, IndividuUlr agent) {
		NSArray<ExamenEntete> entExams = (NSArray<ExamenEntete>) resa.valueForKeyPath(Reservation.RESA_EXAMENS_KEY + "."
				+ ResaExamen.EXAMEN_ENTETE_KEY);
		for (int k = 0; k < entExams.count(); k++) {
			int traite = entExams.objectAtIndex(k).eentTraite().intValue();
			if (traite == 4) {
				return false;
			}
			if (traite == 3 && ((Boolean) userInfo("appAutoriseModifExamenEtat3")).booleanValue() == false) {
				return false;
			}
			if (resa.individuAgent().equals(agent)) {
				return true;
			}
		}
		NSArray<Salles> salles = (NSArray<Salles>) resa.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		SalleFactory salleFactory = new SalleFactory(eContext);
		for (int i = 0; i < salles.count(); i++) {
			if (salleFactory.estDepositaireDeSalle(agent, salles.objectAtIndex(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean aDroitModification(Reservation resa) {
		return aDroitSuppression(resa);
	}

	/** test si l'agent a droit de supprimer la resa */
	public boolean aDroitSuppression(Reservation resa) {

		IndividuUlr agent = (IndividuUlr) userInfo("individu");
		if (agent == null) {
			return false;
		}

		if ("E".equals(resa.tlocCode())) {
			return aDroitSuppressionExamen(resa, agent);
		}

		// pdm Si créateur de la résa, ok !
		if (agent.equals(resa.individuAgent())) {
			System.out.println("aDroitSuppression car createur !!");
			return true;
		}

		// if (mainInterface().etatMpers && ((Number) mainInterface().infos.objectForKey("type")).intValue() == GestionPanier.PERSONNE
		// && "S".equals(resa.individuAgent().indAgenda()) && ("rsp").indexOf(resa.tlocCode()) >= 0) {
		// System.out.println("aDroitSuppression car individu concerné a indAgenda à S !!");
		// return true;
		// }

		if (resa.resaSalles() != null && resa.resaSalles().count() > 0) {
			for (int i = 0; i < resa.resaSalles().count(); i++) {
				Salles salle = ((ResaSalles) resa.resaSalles().objectAtIndex(i)).salle();
				if (SalleFactory.testIndividuADroitReserverSalle(eContext, agent, salle, isReservationSalleParDepositaire()) == false) {
					System.out.println("NOT aDroitSuppression car n'a pas de droits sur la salle (salle " + salle.salPorte() + ") !!");
					return false;
				}
			}
		}

		if (resa.reservationObjets() != null && resa.reservationObjets().count() > 0) {
			for (int i = 0; i < resa.reservationObjets().count(); i++) {
				ResaObjet ro = ((ReservationObjet) resa.reservationObjets().objectAtIndex(i)).resaObjet();
				if (VerificationFactory.testIndividuADroitReserverObjet(eContext, agent, ro) == false) {
					System.out.println("NOT aDroitSuppression car n'a pas de droits sur l'objet (objet " + ro.roLibelle1() + ") !!");
					return false;
				}
			}
		}

		NSArray<Secretariat> secretariatsAgentEnCours = agent.toSecretariat();
		if (secretariatsAgentEnCours != null) {
			EOQualifier qualifier = new EOKeyValueQualifier(RepartStructure.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
					resa.individuAgent());
			NSArray<RepartStructure> aRep2 = RepartStructure.fetchRepartStructures(eContext, qualifier, null);
			if (aRep2 != null) {
				NSArray<StructureUlr> structuresAgentResa = (NSArray<StructureUlr>) aRep2.valueForKey(RepartStructure.STRUCTURE_ULR_KEY);
				if (structuresAgentResa != null) {
					for (int i = 0; i < secretariatsAgentEnCours.count(); i++) {
						StructureUlr str1 = (secretariatsAgentEnCours.objectAtIndex(i)).structureUlr();
						if (structuresAgentResa.containsObject(str1)) {
							System.out.println("aDroitSuppression car secrétariat de l'agent qui a créé la résa !!");
							return true;
						}
					}
				}
			}
			NSArray<StructureUlr> structuresSecretariatAgentResa = (NSArray<StructureUlr>) resa.individuAgent().valueForKeyPath(
					IndividuUlr.TO_SECRETARIAT_KEY + "." + Secretariat.STRUCTURE_ULR_KEY);
			if (structuresSecretariatAgentResa != null) {
				for (int i = 0; i < secretariatsAgentEnCours.count(); i++) {
					StructureUlr str1 = (secretariatsAgentEnCours.objectAtIndex(i)).structureUlr();
					if (structuresSecretariatAgentResa.containsObject(str1)) {
						System.out.println("aDroitSuppression car même secrétariat que l'agent qui a créé la résa !!");
						return true;
					}
				}
			}
		}
		// si agent est occupant, ok
		NSArray<Occupant> occupants = resa.occupants();
		for (int i = 0; i < occupants.count(); i++) {
			IndividuUlr indiv = occupants.objectAtIndex(i).individu();
			if (agent.equals(indiv)) {
				System.out.println("aDroitSuppression car agent est occupant !!");
				return true;
			}
		}

		NSArray<Salles> salles = (NSArray<Salles>) resa.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		NSArray<Salles> sallesSouhaitees = (NSArray<Salles>) resa.valueForKeyPath(Reservation.SALLES_SOUHAITEES_KEY + "." + SalleSouhaitee.SALLE_KEY);
		if (sallesSouhaitees != null && sallesSouhaitees.count() > 0) {
			salles = salles.arrayByAddingObjectsFromArray(sallesSouhaitees);
		}

		NSArray<ResaObjet> objets = (NSArray<ResaObjet>) resa.valueForKeyPath(Reservation.RESERVATION_OBJETS_KEY + "."
				+ ReservationObjet.RESA_OBJET_KEY);

		SalleFactory salleFactory = new SalleFactory(eContext);
		String code = resa.tlocCode();

		// Si droits sur la maquette de la résa, peut intervenir sur la résa...
		if (code.equals("CM") || code.equals("TD") || code.equals("TP") || code.equals("SEM")) {
			NSArray<FormationHabilitation> droitFormSpe = null;
			if (code.equals("CM") || code.equals("TD") || code.equals("TP")) {
				NSArray<MaquetteAp> aps = (NSArray<MaquetteAp>) resa.valueForKeyPath(Reservation.RESERVATION_AP_KEY + "."
						+ ReservationAp.MAQUETTE_AP_KEY);
				if (aps != null) {
					for (int x = 0; x < aps.count(); x++) {
						MaquetteAp uneAp = aps.objectAtIndex(x);
						NSArray<FormationSpecialisation> formSpeAp = (NSArray<FormationSpecialisation>) uneAp
								.valueForKeyPath(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SPECIALISATION_KEY);
						if (formSpeAp == null) {
							continue;
						}
						if (droitFormSpe == null) {
							droitFormSpe = this.formationSpecialisationAgent(uneAp.fannKey());
						}
						for (int i = 0; i < formSpeAp.count(); i++) {
							Object formation = formSpeAp.objectAtIndex(i);
							if (droitFormSpe.containsObject(formation)) {
								System.out.println("aDroitSuppression car a les droits sur la maquette pour un AP !!");
								return true;
							}
						}
					}
				}
			}
			if (code.equals("SEM")) {
				NSArray<MaquetteSemestre> semestres = (NSArray<MaquetteSemestre>) resa.valueForKeyPath(Reservation.RESERVATIONS_SEMESTRES_KEY + "."
						+ ReservationSemestre.SEMESTRE_KEY);
				if (semestres != null) {
					for (int x = 0; x < semestres.count(); x++) {
						MaquetteSemestre unSemestre = semestres.objectAtIndex(x);
						NSArray<FormationSpecialisation> formSpeSemestre = (NSArray<FormationSpecialisation>) unSemestre
								.valueForKeyPath(MaquetteSemestre.MAQUETTE_REPARTITION_SEMS_KEY + "." + MaquetteRepartitionSem.PARCOURS_KEY + "."
										+ MaquetteParcours.FORMATION_SPECIALISATION_KEY);
						if (formSpeSemestre == null) {
							continue;
						}
						if (droitFormSpe == null) {
							droitFormSpe = this.formationSpecialisationAgent(unSemestre.scolFormationAnnee().fannKey());
						}
						for (int i = 0; i < formSpeSemestre.count(); i++) {
							Object formation = formSpeSemestre.objectAtIndex(i);
							if (droitFormSpe.containsObject(formation)) {
								System.out.println("aDroitSuppression car a les droits sur la maquette pour un semestre !!");
								return true;
							}
						}
					}
				}
			}
			for (int i = 0; i < salles.count(); i++) {
				if (salleFactory.estDepositaireDeSalle(agent, salles.objectAtIndex(i))) {
					System.out.println("aDroitSuppression car est dépositaire de la salle !");
					return true;
				}
			}
			for (int i = 0; i < objets.count(); i++) {
				if (VerificationFactory.testIndividuEstDepositaireObjet(eContext, agent, objets.objectAtIndex(i))) {
					System.out.println("aDroitSuppression car est dépositaire de l'objet !");
					return true;
				}
			}
			return false;
		}
		else {
			// pdm Si résa de type réunion ou blocage, il faut être dépositaire de la salle ou de l'objet...
			Salles currentSalle = null;
			if (code.equals("r") || code.equals("b")) {
				for (int i = 0; i < salles.count(); i++) {
					currentSalle = salles.objectAtIndex(i);
					if (salleFactory.estDepositaireDeSalle(agent, currentSalle)) {
						System.out.println("aDroitSuppression car est dépositaire de la salle !!");
						return true;
					}
				}
				for (int i = 0; i < objets.count(); i++) {
					if (VerificationFactory.testIndividuEstDepositaireObjet(eContext, agent, objets.objectAtIndex(i))) {
						System.out.println("aDroitSuppression car est dépositaire de l'objet !!");
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	/**
	 * Invoque une methode sur le serveur. Attention il faut imperativement que params contienne des objets exactement du meme type que les
	 * parametres de la methode et dans le meme ordre. Sinon utiliser l'autre version de la methode qui recoit la liste des classes.
	 */
	public Object invokeRemoteMethod(EOEditingContext edC, String path, String name, NSArray params) {
		NSArray classArray = null;
		Class[] classNames = null;
		Object[] objects = null;
		if (params != null) {
			classArray = (NSArray) params.valueForKey("getClass");

			classNames = new Class[classArray.count()];
			for (int i = 0; i < classArray.count(); i++) {
				classNames[i] = (Class) classArray.objectAtIndex(i);
			}
			objects = params.objects();
		}
		Object result;
		try {
			result = ((EODistributedObjectStore) EOEditingContext.defaultParentObjectStore()).invokeRemoteMethodWithKeyPath(edC, path, name,
					classNames, objects, true);
		}
		catch (Throwable th) {
			th.printStackTrace();
			result = null;
		}

		return result;
	}

	/** retourne une valeur particuliere des parametres pour l'utilisateur */
	public Object userInfo(String key) {
		if (userInfos() != null) {
			return userInfos().objectForKey(key);
		}
		else {
			return null;
		}
	}

	public boolean isInspecteurModal() {
		return StringCtrl.toBool((String) userInfo("appInspecteurModal"));
	}

	public boolean isInspecteurAutoFermer() {
		return StringCtrl.toBool((String) userInfo("appInspecteurAutoFermer"));
	}

	public Object primaryKeyFromEO(EOGenericRecord eObject, String primKey) {
		if (eObject != null) {
			EOGlobalID gid = eContext.globalIDForObject(eObject);
			return this.primaryKey(gid, primKey);
		}
		else {
			return null;
		}
	}

	public Object primaryKey(EOGlobalID gid, String primKey) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPrimaryKey", new Class[] { EOGlobalID.class,
				String.class }, new Object[] { gid, primKey }, false);

	}

	public void serverLog(String msg) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestServerLog", new Class[] { String.class },
				new Object[] { msg }, false);
	}

	public boolean saveChanges() {
		boolean retour = true;
		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (Exception exe) {
			exe.printStackTrace();
			eContext.revert();
			retour = false;
		}
		finally {
			eContext.unlock();
		}
		return retour;
	}

	public boolean revertChanges() {
		boolean retour = true;
		try {
			eContext.lock();
			eContext.revert();
		}
		catch (Exception exe) {
			exe.printStackTrace();
			retour = false;
		}
		finally {
			eContext.unlock();
		}
		return retour;
	}

	public void quit() {
		super.quit();
	}

	public FormationAnnee getCurrentWorkingAnnee() {
		return currentWorkingAnnee;
	}

	public void setCurrentWorkingAnnee(FormationAnnee currentWorkingAnnee) {
		this.currentWorkingAnnee = currentWorkingAnnee;
	}

	public Number getCurrentFannKey() {
		return currentFannKey;
	}

	public void setCurrentFannKey(Number currentFannKey) {
		this.currentFannKey = currentFannKey;
	}

	public Number getCurrentDisplayedYear() {
		NSTimestamp debut = mainInterface().debut;
		return TimeHandler.getYearFor(debut, !isAnneeUniversitaire());
	}

	public ArrayList getAttrColumn1Dipl() {
		return attrColumn1Dipl;
	}

	public void setAttrColumn1Dipl(ArrayList attrColumn1Dipl) {
		this.attrColumn1Dipl = attrColumn1Dipl;
	}

	public ArrayList getAttrColumn2Dipl() {
		return attrColumn2Dipl;
	}

	public void setAttrColumn2Dipl(ArrayList attrColumn2Dipl) {
		this.attrColumn2Dipl = attrColumn2Dipl;
	}

	public WaitingHandler waitingHandler() {
		return waitingHandler;
	}

	public void setNoSemaine(int noSemaine) {
		this.noSemaine = noSemaine;
	}

	public int noSemaine() {
		return this.noSemaine;
	}

	public boolean isAppExigeMotifModification() {
		return isAppExigeMotifModification;
	}

	public boolean isAppExigeMotifSuppression() {
		return isAppExigeMotifSuppression;
	}

}
