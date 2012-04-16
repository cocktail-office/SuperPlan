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
package org.cocktail.superplan.server;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.cocktail.fwkcktlwebapp.common.CktlUserInfo;
import org.cocktail.fwkcktlwebapp.common.database.CktlUserInfoDB;
import org.cocktail.fwkcktlwebapp.common.metier.EOGrhumParametres;
import org.cocktail.fwkcktlwebapp.common.util.CRIpto;
import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.fwkcktlwebapp.server.CktlConfig;
import org.cocktail.fwkcktlwebapp.server.CktlMailBus;
import org.cocktail.fwkcktlwebapp.server.CktlResourceManager;
import org.cocktail.fwkcktlwebapp.server.CktlWebSession;
import org.cocktail.superplan.server.gestionimpression.HCompPrintFactory;
import org.cocktail.superplan.server.gestionimpression.ReportsGenerator;
import org.cocktail.superplan.server.gestionimpression.TabularEdtReport;
import org.cocktail.superplan.server.metier.ExamenEntete;
import org.cocktail.superplan.server.metier.IndividuUlr;
import org.cocktail.superplan.server.metier.MaquetteAp;
import org.cocktail.superplan.server.metier.MaquetteSemestre;
import org.cocktail.superplan.server.metier.Occupant;
import org.cocktail.superplan.server.metier.Periodicite;
import org.cocktail.superplan.server.metier.PrefUser;
import org.cocktail.superplan.server.metier.RepartStructure;
import org.cocktail.superplan.server.metier.ResaExamen;
import org.cocktail.superplan.server.metier.ResaObjet;
import org.cocktail.superplan.server.metier.ResaSalles;
import org.cocktail.superplan.server.metier.Reservation;
import org.cocktail.superplan.server.metier.ReservationAp;
import org.cocktail.superplan.server.metier.ReservationObjet;
import org.cocktail.superplan.server.metier.ReservationSemestre;
import org.cocktail.superplan.server.metier.Salles;
import org.cocktail.superplan.server.metier.ScolDroitLogin;
import org.cocktail.superplan.server.metier.ScolGroupeGrp;
import org.cocktail.superplan.server.metier.VVacancesGestorp5;

import com.webobjects.appserver.WOApplication;
import com.webobjects.eoaccess.EODatabaseContext;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eodistribution.EODistributionContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimeZone;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.CalendarHandler;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.TextHandler;
import fr.univlr.cri.planning.SPConstantes;
import fr.univlr.cri.planning.SPOccupation;
import fr.univlr.utilities.TimeHandler;

public class Session extends CktlWebSession {

	private static final long serialVersionUID = 9059310623774473558L;
	private EOEditingContext eContext;
	public NSMutableDictionary<String, Object> userInfos;
	private Application woApplication;
	private Number persId = null, noIndividu = null;
	private String userName = null;

	private CktlUserInfoDB lrUser;
	String mailTest = null;
	private MailHandler mailHandler;
	public CalendarHandler calendarHandler;

	private NSMutableArray<NSDictionary<String, Object>> plans; // contient le
	// planning lorsqu'il
	// est invoque par le
	// client.
	// private boolean appendComments;
	private PrefUser prefUser;

	private Verification verification;

	public boolean colorEc = false;

	private boolean lazyMode = false;

	public Session() {
		super();
		setTimeOut(43200); // 12heures
		woApplication = (Application) WOApplication.application();
		userInfos = new NSMutableDictionary<String, Object>();
		plans = new NSMutableArray<NSDictionary<String, Object>>();
		mailTest = config().stringForKey("APP_TEST_MAIL");
		this.eContext = this.defaultEditingContext();
		calendarHandler = new CalendarHandler();
		verification = new Verification(eContext);
	}

	public IndividuUlr agent() {
		if (isLazyMode()) {
			return null;
		}
		return (IndividuUlr) DBHandler.fetchUniqueData(eContext, IndividuUlr.ENTITY_NAME, IndividuUlr.PERS_ID_KEY, persId);
	}

	/**
	 * renvoie les valeurs de PrefUser : les preferences individuelles des utilisateurs
	 */
	public Object prefUser(String code) {
		if (prefUser == null) {
			EOQualifier qPref = DBHandler.getSimpleQualifier(PrefUser.NO_INDIVIDU_KEY, userInfos.objectForKey("noIndividu"));
			prefUser = (PrefUser) DBHandler.fetchUniqueData(eContext, PrefUser.ENTITY_NAME, qPref);
		}
		if (prefUser != null) {
			return prefUser.storedValueForKey(code);
		}
		else {
			return null;
		}
	}

	private void initUserInfos() {
		try {
			userInfos.takeValueForKey(persId, "persId");
			noIndividu = lrUser.noIndividu();
			userInfos.takeValueForKey(noIndividu, "noIndividu");
			CktlUserInfoDB lrUserInfo = new CktlUserInfoDB(woApplication.dataBus());
			lrUserInfo.compteForPersId(persId, false);

			userInfos.takeValueForKey(lrUserInfo.email(), "email");

			boolean isEdtCreateur = this.isEdtCreateur();
			userInfos.takeValueForKey(Boolean.valueOf(isEdtCreateur), "edtCreateur");

			String libDescPattern = woApplication.config().stringForKey("LIBELLE_DESC_DIPLOME_PATTERN");
			userInfos.takeValueForKey(libDescPattern, "libDescPattern");

			// Gestion des objets
			String cStructureObjets = woApplication.config().stringForKey("STRUCTURE_GESTION_OBJETS");
			Boolean gestionObjets = Boolean.TRUE;
			if (cStructureObjets != null && !cStructureObjets.trim().equals("")) {
				EOQualifier qualRepartStruct = EOQualifier.qualifierWithQualifierFormat(RepartStructure.C_STRUCTURE_KEY + " = %@ and "
						+ RepartStructure.PERS_ID_KEY + " = %@", new NSArray<Object>(new Object[] { cStructureObjets, persId }));
				NSArray<RepartStructure> data = RepartStructure.fetchRepartStructures(eContext, qualRepartStruct, null);
				gestionObjets = Boolean.valueOf(data.count() > 0);
			}
			userInfos.takeValueForKey(gestionObjets, "gestionObjets");

			// Consultation des logs d'historisation
			String cStructureLogs = woApplication.config().stringForKey("APP_STRUCTURE_LOGS_HISTORIQUE");
			Boolean consultationLogsHistorisation = Boolean.FALSE;
			if (cStructureLogs != null && !cStructureLogs.trim().equals("")) {
				EOQualifier qualRepartStruct = EOQualifier.qualifierWithQualifierFormat(RepartStructure.C_STRUCTURE_KEY + " = %@ and "
						+ RepartStructure.PERS_ID_KEY + " = %@", new NSArray<Object>(new Object[] { cStructureLogs, persId }));
				NSArray<RepartStructure> data = RepartStructure.fetchRepartStructures(eContext, qualRepartStruct, null);
				consultationLogsHistorisation = Boolean.valueOf(data.count() > 0);
			}
			userInfos.takeValueForKey(consultationLogsHistorisation, "consultationLogsHistorisation");

			userInfos.takeValueForKey(Boolean.valueOf(config().booleanForKey("APP_AUTORISE_RESERVATION_FERIE")), "autoriseReservationFerie");

			EOQualifier qPref = DBHandler.getSimpleQualifier(PrefUser.NO_INDIVIDU_KEY, userInfos.objectForKey("noIndividu"));
			PrefUser currentPrefUser = (PrefUser) DBHandler.fetchUniqueData(eContext, PrefUser.ENTITY_NAME, qPref);

			if (currentPrefUser != null) {
				Number valeurACivile = currentPrefUser.anneeCivile();
				Boolean anneeCivile = Boolean.FALSE;
				if (valeurACivile != null) {
					anneeCivile = Boolean.valueOf(valeurACivile.intValue() == 1);
				}
				userInfos.takeValueForKey(anneeCivile, "anneeCivile");
				userInfos.takeValueForKey(currentPrefUser.useTooltipPlanning(), "useTooltipPlanning");
				colorEc = "O".equals(currentPrefUser.colorEc());
			}
			else {
				userInfos.takeValueForKey(Boolean.FALSE, "anneeCivile");
				userInfos.takeValueForKey(Boolean.FALSE, "useTooltipPlanning");
				colorEc = false;
			}

			userInfos.takeValueForKey(config().stringForKey("USE_CONGES_GESTOR"), "useCongesGestor");

			userInfos.takeValueForKey(config().stringForKey("APP_INSPECTEUR_MODAL"), "appInspecteurModal");
			userInfos.takeValueForKey(config().stringForKey("APP_INSPECTEUR_AUTO_FERMER"), "appInspecteurAutoFermer");

			userInfos.takeValueForKey(Boolean.valueOf(config().booleanForKey("APP_AUTORISE_MODIF_EXAMEN_ETAT_3")), "appAutoriseModifExamenEtat3");

			// if(!isEdtCreateur) {
			EOQualifier qDroit = EOQualifier.qualifierWithQualifierFormat(ScolDroitLogin.DLOG_VALIDE_KEY + " = 'O' and "
					+ ScolDroitLogin.DLOG_LOGIN_KEY + " = '" + userName + "'", null);
			NSArray<ScolDroitLogin> droitsLogin = DBHandler.fetchData(eContext, ScolDroitLogin.ENTITY_NAME, qDroit);

			if (droitsLogin.count() > 0) {
				userInfos.takeValueForKey(new NSArray<Integer>((droitsLogin.objectAtIndex(0)).dlogKey()), "droits");
			}
			else {
				userInfos.takeValueForKey(new NSArray<Integer>(), "droits");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void terminate() {
		plans = null;
		calendarHandler = null;
		userInfos = null;
		super.terminate();

	}

	private boolean isEdtCreateur() {
		EOQualifier qCreateur = EOQualifier.qualifierWithQualifierFormat(EOGrhumParametres.PARAM_KEY_KEY + " = 'EDT_CREATEUR'", null);
		NSArray<EOGrhumParametres> resultats = DBHandler.fetchData(eContext, EOGrhumParametres.ENTITY_NAME, qCreateur);
		return ((NSArray<String>) resultats.valueForKey(EOGrhumParametres.PARAM_VALUE_KEY)).containsObject(userName);
	}

	public Boolean clientSideRequestAppSendMail() {
		return config().booleanForKey("APP_SEND_MAIL");
	}

	public Boolean clientSideRequestAppSendMailDepositaires() {
		return config().booleanForKey("APP_SEND_MAIL") && config().booleanForKey("APP_SEND_MAIL_DEPOSITAIRES");
	}

	public Boolean clientSideRequestAppReservationSalleParDepositaire() {
		return config().booleanForKey("APP_RESERVATION_SALLE_PAR_DEPOSITAIRE");
	}

	public Boolean clientSideRequestAppDroitsSurContraintesIndividus() {
		return config().booleanForKey("APP_DROITS_SUR_CONTRAINTES_INDIVIDUS");
	}

	public Boolean clientSideRequestAppControleMissions() {
		return config().booleanForKey("APP_CONTROLE_MISSIONS");
	}

	public Boolean clientSideRequestAppExigeMotifModification() {
		return config().booleanForKey("APP_EXIGE_MOTIF_MODIFICATION");
	}

	public Boolean clientSideRequestAppExigeMotifSuppression() {
		return config().booleanForKey("APP_EXIGE_MOTIF_SUPPRESSION");
	}

	public String clientSideRequestDTSAMEmail() {
		return config().stringForKey("DTSAM_EMAIL");
	}

	public String clientSideRequestAppControleApsOccupation() {
		return config().stringForKey("APP_CONTROLE_APS_OCCUPATION");
	}

	/** renvoie le timezone cote client */
	public NSTimeZone clientSideRequestDefaultTimeZone() {
		String tzStr = (String) config().valueForKey("DEFAULT_TIME_ZONE");
		NSTimeZone tz;
		if (tzStr == null) {
			tz = NSTimeZone.defaultTimeZone();
		}
		else {
			tz = NSTimeZone.timeZoneWithName(tzStr, false);
		}
		return tz;
	}

	public String clientSideRequestDefaultTimeZoneString() {
		return config().stringForKey("DEFAULT_TIME_ZONE");
	}

	/**
	 * verifie le login/mot de passe avec CRIPassword
	 */
	public NSDictionary<String, Object> clientSideRequestUserInfos(String login, String passwd, String casAuth) {
		try {
			String rootPW = config().stringForKey("APP_ADMIN_PASSWORD");
			userName = CRIpto.decrypt(login);

			String passwdClair = null;
			if (casAuth.equals("N")) {
				passwdClair = CRIpto.decrypt(passwd);
			}

			lrUser = new CktlUserInfoDB(woApplication.dataBus());

			lrUser.setRootPass(rootPW);
			lrUser.compteForLogin(userName, passwdClair, true);
			if (config().booleanForKey("APP_LIMITE_ACCES_AU_PERSONNEL") && lrUser.userStatus() != CktlUserInfo.STATUS_PERSONNEL) {
				NSLog.out.appendln("Erreur : cet individu n'est pas personnel de l'établissement, application réservée au personnel !");
				return null;
			}
			if (lrUser.errorCode() == CktlUserInfo.ERROR_NONE) {
				persId = lrUser.persId();
				String heure = FormatHandler.dateToStr(new NSTimestamp(), " @ %d/%m/%Y %H:%M");
				NSLog.out.appendln("login=" + userName + " noIndividu=" + lrUser.noIndividu() + heure);
				initUserInfos();
				return userInfos;
			}
			else {
				NSLog.out.appendln("Erreur : " + lrUser.errorMessage());
				return null;
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}

	}

	/** recupere l'adresse mail de l'individu grace a son persId */
	public String clientSideRequestEmailIndividu(Number persId) {
		CktlUserInfoDB lrUserInfo = new CktlUserInfoDB(woApplication.dataBus());
		lrUserInfo.compteForPersId(persId, false);
		return lrUserInfo.email();
		/*
		 * if (email != null && email.trim().equals("") && TextHandler.isEmailValid(email)) { return email; } else { return null; }
		 */
	}

	/** recupere les adresses email d'une liste d'individu */
	public NSArray<String> clientSideRequestEmailIndividus(NSArray<Integer> persIDs) {
		NSMutableArray<String> emails = new NSMutableArray<String>();
		try {
			Number persId = null;
			for (int i = 0; i < persIDs.count(); i++) {
				persId = persIDs.objectAtIndex(i);
				CktlUserInfoDB lrUserInfo = new CktlUserInfoDB(woApplication.dataBus());
				lrUserInfo.compteForPersId(persId, false);
				String email = lrUserInfo.email();
				if (email != null && TextHandler.isEmailValid(email)) {
					emails.addObject(email);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return emails;
	}

	public Boolean clientSideRequestSendMail(NSDictionary<String, String> data) {
		if (!config().booleanForKey("APP_SEND_MAIL")) {
			return Boolean.TRUE;
		}

		String prefix = config().stringForKey("EDT_MAIL_PREFIX") + " ";
		String from, to, text, subject, cc;

		CktlMailBus mailBus = new CktlMailBus(config());

		from = TextHandler.safeString(data.objectForKey("expediteur"));

		Object occ = data.objectForKey("cc");
		if (occ != null) {
			cc = TextHandler.safeString(data.objectForKey("cc"));
		}
		else {
			cc = null;
		}

		subject = TextHandler.safeString(data.objectForKey("sujet"));
		text = TextHandler.safeString(data.objectForKey("texte"));

		if (StringCtrl.isEmpty(mailTest) == false) {
			to = mailTest;
			text = "Mail redirigé vers @ mail de test, normalement envoyé à :\nTo : " + TextHandler.safeString(data.objectForKey("destinataire"))
					+ "\nCc : " + cc + "\n----------- mail original : -------------\n" + text;
		}
		else {
			to = TextHandler.safeString(data.objectForKey("destinataire"));
		}

		to = StringCtrl.replace(to, ";", ",");

		if (mailBus.sendMail(from, to, cc, prefix + subject, text)) {
			return Boolean.TRUE;
		}
		else {
			return Boolean.FALSE;
		}
	}

	public Object clientSideRequestPrimaryKey(EOGlobalID gid, String primKey) {
		try {
			if (gid == null) {
				return null;
			}
			else {
				EOEnterpriseObject record = eContext.faultForGlobalID(gid, eContext);
				if (record != null) {
					NSDictionary<?, ?> keys = EOUtilities.primaryKeyForObject(eContext, record);
					return keys.objectForKey(primKey);
				}
			}
		} // try
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** permet de deleguer les logs du client au serveur : invocation cote client */
	public void clientSideRequestServerLog(String msg) {
		NSLog.out.appendln("client :: " + msg);
	}

	public NSArray<String> clientSideRequestMailsDepositairesObjets(NSArray<EOGlobalID> objets) {
		try {
			if (mailHandler == null) {
				mailHandler = new MailHandler(woApplication, this);
			}
			return mailHandler.mailsDespositairesObjets(objets);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new NSArray<String>();
	}

	/**
	 * @param salles
	 * @param periodicites
	 *            tableau de dates début / date fin (2 NSTimestamp par periodicites)
	 * @return
	 */
	public NSArray<String> clientSideRequestMailsDepositairesSalles(NSArray<EOGlobalID> salles, NSArray<NSTimestamp> periodicites) {
		try {
			// pdm cherche si une des periodicites est dans la semaine en cours...
			boolean modifDansSemaineEnCours = false;
			if (periodicites != null) {
				TimeHandler th = new TimeHandler();
				int semaineEnCours = th.weekOfYear(new NSTimestamp());
				for (int i = 0; i < periodicites.count(); i += 2) {
					NSTimestamp t = periodicites.objectAtIndex(i);
					if (t != null && th.weekOfYear(t) == semaineEnCours) {
						modifDansSemaineEnCours = true;
						break;
					}
				}
			}

			if (mailHandler == null) {
				mailHandler = new MailHandler(woApplication, this);
			}
			return mailHandler.mailsDespositairesSalles(salles, modifDansSemaineEnCours);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new NSArray<String>();
	}

	public boolean distributionContextShouldFollowKeyPath(@SuppressWarnings("unused") EODistributionContext context, String path) {
		if (path.equals("session")) {
			return true;
		}
		else {
			return false;
		}
	}

	public CktlConfig config() {
		return woApplication.config();
	}

	public Number noIndividu() {
		return noIndividu;
	}

	public NSArray<NSDictionary<String, Object>> clientSideRequestPlanningParcoursSemestre(Integer mrsemKey, NSTimestamp debut, NSTimestamp fin,
			Boolean inclureParcoursCommun, Boolean inclureAutreSemestre) {
		plans.removeAllObjects();
		plans.addObjectsFromArray(new PlanningFormationProxy(this).getPlanningParcoursSemestre(mrsemKey, debut, fin,
				inclureParcoursCommun.booleanValue(), inclureAutreSemestre.booleanValue(), false));
		return plans;
	}

	public NSArray<NSDictionary<String, Object>> clientSideRequestPlanningGroupe(Integer ggrpKey, NSTimestamp debut, NSTimestamp fin) {
		plans.removeAllObjects();
		plans.addObjectsFromArray(new PlanningFormationProxy(this).getPlanningGroupe(ggrpKey, debut, fin));
		return plans;
	}

	public NSArray clientSideRequestHorairesGestorIndividu(NSTimestamp debut, NSTimestamp fin, EOGlobalID idIndividu) {
		if (woApplication.config().booleanForKey("USE_CONGES_GESTOR")) {
			return null;
		}
		NSMutableArray plans = new NSMutableArray();
		try {
			NSTimestamp dHoraire, fHoraire;
			NSMutableDictionary dRes = new NSMutableDictionary();
			NSArray conges = clientSideRequestCongesGestorIndividu(debut, fin, idIndividu);
			for (int i = 0; i < conges.count(); i++) {
				EOGenericRecord rec = (EOGenericRecord) conges.objectAtIndex(i);
				dHoraire = (NSTimestamp) rec.storedValueForKey("datedebut");
				fHoraire = (NSTimestamp) rec.storedValueForKey("datefin");
				dRes.takeValueForKey(dHoraire, "debut");
				dRes.takeValueForKey(fHoraire, "fin");
				Integer jour = new Integer(calendarHandler.getDay(dHoraire));
				dRes.takeValueForKey(jour, "jour");
				dRes.takeValueForKey("H", "ccolor");
				dRes.takeValueForKey(new NSArray(rec.storedValueForKey("libelle")), "texte");
				dRes.takeValueForKey(new Integer(1), "resa");
				plans.addObject(dRes);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return plans;
	}

	public NSArray clientSideRequestCongesGestorIndividu(NSTimestamp debut, NSTimestamp fin, EOGlobalID gidIndividu) {
		if (woApplication.config().booleanForKey("USE_CONGES_GESTOR")) {
			return null;
		}
		IndividuUlr individu = (IndividuUlr) eContext.faultForGlobalID(gidIndividu, eContext);
		Number noIndividu = (Number) DBHandler.primaryKeyForObject(eContext, individu, "noIndividu");
		NSMutableArray args = new NSMutableArray();
		NSMutableArray resas = new NSMutableArray();
		args.addObject(noIndividu);
		args.addObject(fin);
		args.addObject(debut);
		EOQualifier qualifierDate = EOQualifier.qualifierWithQualifierFormat(VVacancesGestorp5.NO_INDIVIDU_KEY + " = %@ and "
				+ VVacancesGestorp5.DATEDEB_KEY + " <= %@ and " + VVacancesGestorp5.DATEFIN_KEY + " >= %@", args);
		NSArray conges = DBHandler.fetchData(eContext, VVacancesGestorp5.ENTITY_NAME, qualifierDate);
		for (int i = 0; i < conges.count(); i++) {
			try {
				VVacancesGestorp5 rec = (VVacancesGestorp5) conges.objectAtIndex(i);
				NSTimestamp d1 = fmtDate(rec.datedeb(), rec.hdeb(), 1);
				NSTimestamp d2 = fmtDate(rec.datefin(), rec.hfin(), 2);
				Object[] objects = { d1, d2, rec.libelle() };
				Object[] keys = { "dateDebut", "dateFin", "detailsTemps" };
				NSDictionary ressource = new NSDictionary(objects, keys);
				resas.addObject(ressource);
			}
			catch (Exception e) {
			}
		}
		return resas;
	}

	public NSTimestamp fmtDate(NSTimestamp wDate, Number wHeure, int df) {
		String zd = FormatHandler.dateToStr(wDate);
		String zh = ((wHeure).toString()).replaceAll(",", ":");
		if (wHeure.doubleValue() == 0) {
			if (df == 1) {
				zh = "08:00 ";
			}
			if (df == 2) {
				zh = "20:00 ";
			}
		}
		else {
			zh = zh.replaceAll(".5", ":30");
			zh = zh.replaceAll(".25", ":15");
			zh = zh.replaceAll(".0", ":00");
		}
		return FormatHandler.strToDate(zd + " " + zh, "%d/%m/%Y %H:%M");
	}

	public NSArray<NSDictionary<String, Object>> clientSideRequestServeurDePlanning(NSTimestamp debut, NSTimestamp fin, EOGlobalID gidIndividu,
			String spMethode) {
		try {
			NSMutableArray<NSDictionary<String, Object>> resas = new NSMutableArray<NSDictionary<String, Object>>();
			NSArray<NSArray<SPOccupation>> occupsFromServeurPlanning = null;
			boolean serveurPlanningAvailable = woApplication.useServeurPlanning();
			if (serveurPlanningAvailable) {
				try {
					if (fr.univlr.cri.planning.DemandePlanning.serveurPlanningAccessible()) {
						serveurPlanningAvailable = true;
					}
					else {
						System.out.println("ATTENTION : le serveur de Planning est inaccessible !!!!!");
						serveurPlanningAvailable = false;
					}
				}
				catch (java.lang.NoClassDefFoundError noclass) {
					System.out.println("ATTENTION : pas de framework ServeurPlanningFwk, serveur de Planning inaccessible !!!!!");
					noclass.printStackTrace();
					serveurPlanningAvailable = false;
				}
				catch (Throwable e) {
					System.out.println("ATTENTION : le serveur de Planning est inaccessible !!");
					e.printStackTrace();
					serveurPlanningAvailable = false;
				}
			}

			if (serveurPlanningAvailable) {
				try {
					IndividuUlr individu = (IndividuUlr) eContext.faultForGlobalID(gidIndividu, eContext);
					Number noIndividu = (Number) DBHandler.primaryKeyForObject(eContext, individu, "noIndividu");
					Properties response = fr.univlr.cri.planning.DemandePlanning.connectionAuServeur("tmpClientEDTSCOL",
							fr.univlr.cri.planning.SPConstantes.IDKEY_INDIVIDU, noIndividu, debut, fin);
					if (fr.univlr.cri.planning.DemandePlanning.getStatut(response)) {
						occupsFromServeurPlanning = fr.univlr.cri.planning.DemandePlanning.getPlanning(response);
					}
					else {
						occupsFromServeurPlanning = null;
						throw new Exception(fr.univlr.cri.planning.DemandePlanning.getError(response));
					}
				}
				catch (Exception e) {
					System.out.println("Erreur lors de la tentative d'accès au serveur de planning : " + e);
					e.printStackTrace();
				}
				if (occupsFromServeurPlanning == null) {
					return null;
				}

				Number ah = (Number) prefUser("affHorairesHamac");
				boolean afficheHoraires = (ah != null && ah.intValue() == 1);
				for (int i = 0; i < occupsFromServeurPlanning.count(); i++) {
					NSArray<SPOccupation> spOcc = occupsFromServeurPlanning.objectAtIndex(i);
					for (int j = 0; j < spOcc.count(); j++) {
						SPOccupation occ = spOcc.objectAtIndex(j);
						if (occ.getDateDebut() != null && occ.getDateFin() != null) {
							if ((spMethode == null) || (spMethode.equals("MIS") && occ.getTypeTemps().equals(SPConstantes.SPOCC_TYPE_MISSION))
									|| (spMethode.equals("DT") && occ.getTypeTemps().equals(SPConstantes.SPOCC_TYPE_DT))
									|| (spMethode.startsWith("ICS") && occ.getTypeTemps().equals(SPConstantes.SPOCC_TYPE_ICS))) {
								if (afficheHoraires || !occ.getTypeTemps().equals(SPConstantes.SPOCC_TYPE_PRESENCE)) {
									String detail = occ.getDetailsTemps();
									String type = occ.getTypeTemps();

									if (detail != null) {
										int indexClean = detail.indexOf("statutEvent");
										if (indexClean > 0) {
											detail = detail.substring(0, indexClean);
										}
									}
									else {
										detail = "";
									}

									Object[] objects = { occ.getDateDebut(), occ.getDateFin(), detail + " [" + type + "]" };
									String[] keys = { "dateDebut", "dateFin", "detailsTemps" };
									try {
										NSDictionary<String, Object> ressource = new NSDictionary<String, Object>(objects, keys);
										resas.addObject(ressource);
									}
									catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			return resas;
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	public NSArray clientSideRequestPlanningIndividu(NSTimestamp debut, NSTimestamp fin, EOGlobalID gidIndividu, Integer rang) {

		boolean appendComments = true;
		if (!isLazyMode()) {
			appendComments = (((Number) prefUser("commentScol")).intValue() == 1);
		}
		try {
			plans.removeAllObjects();
			IndividuUlr individu = (IndividuUlr) eContext.faultForGlobalID(gidIndividu, eContext);
			if (!isLazyMode()) {
				// les horaires, conges, missions, dt et ical...
				NSArray<NSDictionary<String, Object>> sdp = this.clientSideRequestServeurDePlanning(debut, fin, gidIndividu, null);
				if (sdp != null && sdp.count() > 0) {
					plans.addObjectsFromArray(generePlan(sdp, debut, fin, individu, rang));
				}
			}

			// etudiant...
			plans.addObjectsFromArray(new PlanningFormationProxy(this).getPlanningEtudiant(individu.noIndividu(), debut, fin));

			// le reste...
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(Periodicite.DATE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThanOrEqualTo, debut));
			quals.addObject(new EOKeyValueQualifier(Periodicite.DATE_DEB_KEY, EOKeyValueQualifier.QualifierOperatorLessThanOrEqualTo, fin));
			quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, individu));
			if (isLazyMode()) {
				quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.TLOC_CODE_KEY,
						EOKeyValueQualifier.QualifierOperatorNotEqual, "p"));
				quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.TLOC_CODE_KEY,
						EOKeyValueQualifier.QualifierOperatorNotEqual, "s"));
			}
			EOFetchSpecification myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, new EOAndQualifier(quals), null);
			myFetch.setUsesDistinct(true);
			myFetch.setPrefetchingRelationshipKeyPaths(new NSArray(new Object[] { Periodicite.RESERVATION_KEY }));
			NSArray<Periodicite> resas = eContext.objectsWithFetchSpecification(myFetch);
			for (int i = 0; i < resas.count(); i++) {
				Periodicite currentResaOcc = resas.objectAtIndex(i);
				Reservation reservation = currentResaOcc.reservation();
				NSMutableDictionary dRes = new NSMutableDictionary();
				NSTimestamp debutRes = currentResaOcc.dateDeb();
				boolean resaVisible = reservation.isVisiblePourAgent(agent());
				if (!isLazyMode()) {
					dRes.takeValueForKey(eContext.globalIDForObject(reservation), "reservation");
					dRes.takeValueForKey(new Integer(calendarHandler.getDay(debutRes)), "jour");
					dRes.takeValueForKey(new Integer(1), "resa");
					if (resaVisible) {
						dRes.takeValueForKey(reservation.tlocCode(), "ccolor");
					}
					else {
						dRes.takeValueForKey("z", "ccolor");
					}
					dRes.takeValueForKey(new Integer(1), "deplace");
				}
				dRes.takeValueForKey(debutRes, "debut");
				dRes.takeValueForKey(currentResaOcc.dateFin(), "fin");

				StringBuffer com = new StringBuffer();
				String tlocCode = reservation.tlocCode();

				if (resaVisible) {
					// if (tlocCode.equals("CM") || tlocCode.equals("TD") || tlocCode.equals("TP")) {
					if (reservation.typeLocation() != null && reservation.typeLocation().tlocAppli().equals("SCOL")) {
						NSArray<ReservationAp> resaAps = reservation.reservationAp();
						MaquetteAp previousAp = null;
						ScolGroupeGrp previousGrp = null;
						for (int iResaAp = 0; iResaAp < resaAps.count(); iResaAp++) {
							ReservationAp currentReservAp = resaAps.objectAtIndex(iResaAp);
							if (previousAp == null || !previousAp.equals(currentReservAp.maquetteAp())) {
								if (iResaAp > 0) {
									com.append(", ");
								}
								try {
									com.append(currentReservAp.maquetteAp().vScolMaquetteApEc().maquetteEc().mecCode() + " - ");
								}
								catch (Exception e) {
								}
								com.append(StringCtrl.capitalizeWords(currentReservAp.maquetteAp().mapLibelle()));
								previousAp = currentReservAp.maquetteAp();
							}
							try {
								if (currentReservAp.scolGroupeGrp() != null) {
									if (previousGrp == null || !previousGrp.equals(currentReservAp.scolGroupeGrp())) {
										com.append(" (");
										com.append(currentReservAp.scolGroupeGrp().ggrpCode());
										com.append(")");
										previousGrp = currentReservAp.scolGroupeGrp();
									}
								}
							}
							catch (Exception e) {
								System.out.println("Le groupe ggrpKey = " + currentReservAp.ggrpKey()
										+ " n'existe plus, mais est utilisé dans au moins une réservation...");
							}
						}
						if (appendComments && (reservation.resaCommentaire() != null)) {
							com.append(" - " + reservation.resaCommentaire());
						}
						dRes.takeValueForKey("Edt-Ens", "typeTemps");
					}
					else {
						if (tlocCode.equals("E")) {
							// les examens :
							NSArray<ResaExamen> tmp = reservation.resaExamens();
							int nbocc = tmp.count();
							for (int iex = 0; iex < nbocc; iex++) {
								if (iex == 0) {
									com.append("Examen : ");
								}
								ResaExamen currentResaEx = tmp.objectAtIndex(iex);
								com.append(StringCtrl.capitalizeWords(currentResaEx.examenEntete().eentLibelle()));
								if (currentResaEx.scolGroupeGrp() != null) {
									com.append("(");
									com.append(currentResaEx.scolGroupeGrp().ggrpCode());
									com.append(")");
								}
								if (iex < nbocc - 1 && nbocc > 1) {
									com.append(", ");
								}
							}
							dRes.takeValueForKey("Edt-Ens", "typeTemps");
						}
						else {
							if (tlocCode.equals("SEM")) {
								NSArray<ReservationSemestre> resaSemestres = reservation.reservationsSemestres();
								for (int iResaAp = 0; iResaAp < resaSemestres.count(); iResaAp++) {
									ReservationSemestre currentReservSemestre = resaSemestres.objectAtIndex(iResaAp);
									com.append(StringCtrl.capitalizeWords(currentReservSemestre.semestre().toString()));
									try {
										if (currentReservSemestre.scolGroupeGrp() != null) {
											com.append(" (");
											com.append(currentReservSemestre.scolGroupeGrp().ggrpCode());
											com.append(")");
										}
									}
									catch (Exception e) {
										System.out.println("Le groupe ggrpKey = " + currentReservSemestre.scolGroupeGrp().ggrpKey()
												+ " n'existe plus, mais est utilisé dans au moins une réservation...");
									}

									if (iResaAp < resaSemestres.count() - 1 && resaSemestres.count() > 1) {
										com.append(", ");
									}
								}
								if (appendComments && (reservation.resaCommentaire() != null)) {
									com.append(" - " + reservation.resaCommentaire());
								}
								dRes.takeValueForKey("Edt-Ens", "typeTemps");
							}
							else {
								com.append(reservation.resaCommentaire());
								dRes.takeValueForKey("Edt", "typeTemps");
							}
						}
					}
					// affichage des salles
					NSArray<ResaSalles> tmpSalle = reservation.resaSalles();
					if (tmpSalle.count() > 0) {
						com.append(" - ");
					}
					Salles sallePrecedente = null;
					for (int j = 0; j < tmpSalle.count(); j++) {
						Salles sal = (tmpSalle.objectAtIndex(j)).salle();
						if (sallePrecedente == null || !sallePrecedente.equals(sal)) {
							if (j > 0 && j < tmpSalle.count() - 1) {
								com.append(",");
							}
							com.append(StringCtrl.capitalizeWords(sal.cLocal() + " : " + sal.salPorte()));
						}
					}
					// affichage des personnes
					NSArray<IndividuUlr> individuArray = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
							+ Occupant.INDIVIDU_KEY);
					individuArray = DBHandler.retirerMultiples(individuArray);
					if (individuArray.count() > 1) {
						com.append(" - ");
					}
					for (int io = 0; io < individuArray.count(); io++) {
						IndividuUlr ioccupant = individuArray.objectAtIndex(io);
						if (ioccupant.equals(individu) == false) {
							com.append(ioccupant.prenom().substring(0, 1) + "." + StringCtrl.capitalizeWords(ioccupant.nomUsuel()) + " ");
						}
					}
				}
				else {
					com.append("Réservation privée");
					dRes.takeValueForKey("Edt", "typeTemps");
				}

				dRes.takeValueForKey(new NSArray<String>(com.toString()), "texte");
				dRes.takeValueForKey(new Integer(1), "deplace");
				dRes.takeValueForKey(rang, "rang");
				dRes.takeValueForKey(individu.prenomNom(), "libelle");

				plans.addObject(dRes);
			}
		}
		catch (Throwable ex) {
			ex.printStackTrace();
			plans = new NSMutableArray();
		}

		return plans;
	}

	/** renvoi au client le planning complet(?) de l'individu */
	public NSArray generePlan(NSArray resas, NSTimestamp debut, NSTimestamp fin, IndividuUlr individu, Integer rang) {
		NSMutableArray plans = new NSMutableArray();
		if (resas == null || resas.count() == 0) {
			return null;
		}
		for (int i = 0; i < resas.count(); i++) {
			NSDictionary resa = (NSDictionary) resas.objectAtIndex(i);
			NSTimestamp debutRes = (NSTimestamp) resa.valueForKey("dateDebut");
			NSTimestamp finRes = (NSTimestamp) resa.valueForKey("dateFin");
			debutRes = FormatHandler.dateInLocalTZ(debutRes);
			finRes = FormatHandler.dateInLocalTZ(finRes);
			if (debutRes.after(fin) || finRes.before(debut)) {
				continue;
			}
			if (debutRes.before(debut)) {
				debutRes = FormatHandler.midnightTime(debut);
			}
			if (finRes.after(fin)) {
				finRes = FormatHandler.endOfDay(fin);
			}
			String texte = (String) resa.valueForKey("detailsTemps");
			NSArray<NSTimestamp> slices = TimeHandler.slicePeriod(debutRes, finRes);
			NSTimestamp slDeb, slFin;

			for (int s = 0; s < slices.count(); s += 2) {
				slDeb = slices.objectAtIndex(s);
				slFin = slices.objectAtIndex(s + 1);
				NSMutableDictionary<String, Object> dRes = new NSMutableDictionary<String, Object>();
				dRes.takeValueForKey(slDeb, "debut");
				dRes.takeValueForKey(slFin, "fin");
				Integer jour = new Integer(calendarHandler.getDay(slDeb));
				dRes.takeValueForKey(jour, "jour");
				dRes.takeValueForKey("A", "ccolor");
				dRes.takeValueForKey(new NSArray<String>(texte), "texte");
				dRes.takeValueForKey(new Integer(1), "resa");
				dRes.takeValueForKey(new Integer(0), "deplace");
				dRes.takeValueForKey(rang, "rang");
				dRes.takeValueForKey(individu.prenomNom(), "libelle");
				plans.addObject(dRes);
			}
		}
		return plans;
	}

	public NSArray<NSDictionary<String, Object>> clientSideRequestPlanningSalle(NSTimestamp debut, NSTimestamp fin, EOGlobalID gidSalle,
			Integer rang, Boolean affichePorte) {

		boolean appendComments = true;
		if (isLazyMode() == false) {
			appendComments = (((Number) prefUser("commentScol")).intValue() == 1);
		}

		try {
			Salles salle = (Salles) eContext.faultForGlobalID(gidSalle, eContext);

			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(Periodicite.DATE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThanOrEqualTo, debut));
			quals.addObject(new EOKeyValueQualifier(Periodicite.DATE_DEB_KEY, EOKeyValueQualifier.QualifierOperatorLessThanOrEqualTo, fin));
			quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, salle));
			EOFetchSpecification myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, new EOAndQualifier(quals), null);
			myFetch.setUsesDistinct(true);
			myFetch.setRefreshesRefetchedObjects(true);
			myFetch.setPrefetchingRelationshipKeyPaths(new NSArray<String>(new String[] { Periodicite.RESERVATION_KEY,
					Periodicite.RESERVATION_KEY + "." + Reservation.OCCUPANTS_KEY, Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY,
					Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY,
					Periodicite.RESERVATION_KEY + "." + Reservation.RESA_EXAMENS_KEY,
					Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_OBJETS_KEY,
					Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATIONS_SEMESTRES_KEY }));
			NSArray<Periodicite> periods = eContext.objectsWithFetchSpecification(myFetch);

			NSMutableArray<NSDictionary<String, Object>> newPlans = new NSMutableArray<NSDictionary<String, Object>>();
			for (int i = 0; i < periods.count(); i++) {
				Periodicite period = periods.objectAtIndex(i);
				Reservation resa = period.reservation();

				StringBuffer strOccupants = new StringBuffer("");
				StringBuffer sApGrp = new StringBuffer("");
				StringBuffer sExam = new StringBuffer("");

				NSArray<Occupant> occupants = DBHandler.retirerMultiples(resa.occupants());
				int nbOccup = occupants.count();
				if (nbOccup > 3) {
					nbOccup = 3;
				}
				for (int io = 0; io < nbOccup; io++) {
					IndividuUlr ioccupant = (occupants.objectAtIndex(io)).individu();
					strOccupants.append(ioccupant.prenom().substring(0, 1) + "." + StringCtrl.capitalizeWords(ioccupant.nomUsuel()) + " ");
				}

				NSArray<ReservationAp> resaAps = DBHandler.retirerMultiples(resa.reservationAp());
				MaquetteAp lastAp = null;
				for (int iResaAp = 0; iResaAp < resaAps.count(); iResaAp++) {
					ReservationAp currentReservAp = resaAps.objectAtIndex(iResaAp);
					MaquetteAp ap = currentReservAp.maquetteAp();

					if (lastAp != ap) {
						sApGrp.append(StringCtrl.capitalizeWords(ap.mapLibelle()));
					}
					lastAp = ap;

					try {
						if (currentReservAp.scolGroupeGrp() != null) {
							sApGrp.append(" (");
							sApGrp.append(currentReservAp.scolGroupeGrp().ggrpCode());
							sApGrp.append(")");
						}
					}
					catch (Exception e) {
						System.out.println("Le groupe ggrpKey = " + currentReservAp.ggrpKey()
								+ " n'existe plus, mais est utilisé dans au moins une réservation...");
					}

					if (iResaAp < resaAps.count() - 1 && resaAps.count() > 1) {
						sApGrp.append(", ");
					}
				}
				NSArray<ResaExamen> resaExams = DBHandler.retirerMultiples(resa.resaExamens());
				for (int iResaExam = 0; iResaExam < resaExams.count(); iResaExam++) {
					ExamenEntete examen = (resaExams.objectAtIndex(iResaExam)).examenEntete();
					sExam.append(StringCtrl.capitalizeWords(examen.eeltCode() + " - " + examen.eentLibelle()));
				}

				// CM salle valide
				NSArray<ResaSalles> aResaSalles = resa.resaSalles();
				ResaSalles resaSalles = aResaSalles.objectAtIndex(0);

				NSMutableDictionary<String, Object> dRes = new NSMutableDictionary<String, Object>();
				dRes.takeValueForKey(eContext.globalIDForObject(resa), "reservation");
				dRes.takeValueForKey(resaSalles.resaSalEtat(), "salleValide");
				NSTimestamp debutRes = period.dateDeb();
				dRes.takeValueForKey(debutRes, "debut");
				dRes.takeValueForKey(period.dateFin(), "fin");

				Integer jour = new Integer(calendarHandler.getDay(debutRes));
				dRes.takeValueForKey(jour, "jour");
				dRes.takeValueForKey(new Integer(1), "resa");

				NSMutableArray<String> texteResa = new NSMutableArray<String>();
				if (resa.isVisiblePourAgent(agent())) {
					if (affichePorte) {
						texteResa.addObject(salle.salPorte());
					}

					String tlocCode = resa.tlocCode();
					if (tlocCode.equals("b")) {
						texteResa.addObject("Blocage : ");
						texteResa.addObject(resa.resaCommentaire());
					}

					if (tlocCode.equals("CM") || tlocCode.equals("TD") || tlocCode.equals("TP")) {
						if (appendComments && (resa.resaCommentaire() != null)) {
							sApGrp.append(" - " + resa.resaCommentaire());
						}
						texteResa.addObject(StringCtrl.capitalizeWords(sApGrp.toString()));
					}

					if (tlocCode.equals("r") || tlocCode.equals("s") || tlocCode.equals("p") || tlocCode.equals("SEM")) {
						texteResa.addObject(resa.resaCommentaire());
					}

					if (tlocCode.equals("E")) {
						texteResa.addObject(StringCtrl.capitalizeWords(sExam.toString()));
					}
					texteResa.addObject(strOccupants.toString());
					dRes.takeValueForKey(tlocCode, "ccolor");
				}
				else {
					texteResa.addObject("Réservation privée (réalisée par " + StringCtrl.capitalizeWords(resa.individuAgent().prenomNom()) + ")");
					dRes.takeValueForKey("z", "ccolor");
				}
				dRes.takeValueForKey(texteResa, "texte");
				dRes.takeValueForKey(new Integer(1), "deplace");
				// dRes.takeValueForKey(salle.salPorte(), "libelle");
				dRes.takeValueForKey(rang, "rang");
				newPlans.addObject(dRes);
			}
			if (rang == null || rang.intValue() == 0) {
				plans.removeAllObjects();
			}
			plans.addObjectsFromArray(newPlans);
			return newPlans;

		}
		catch (Throwable e) {
			e.printStackTrace();
			return new NSArray<NSDictionary<String, Object>>();
		}

	}

	public NSArray<NSDictionary<String, Object>> clientSideRequestPlanningObjet(NSTimestamp debut, NSTimestamp fin, EOGlobalID gidObjet) {
		return clientSideRequestPlanningObjet(debut, fin, gidObjet, null);
	}

	public NSArray<NSDictionary<String, Object>> clientSideRequestPlanningObjet(NSTimestamp debut, NSTimestamp fin, EOGlobalID gidObjet, Integer rang) {

		plans.removeAllObjects();
		try {
			ResaObjet objet = (ResaObjet) DBHandler.safeObjectForGlobalID(eContext, gidObjet);

			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(Periodicite.DATE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThanOrEqualTo, debut));
			quals.addObject(new EOKeyValueQualifier(Periodicite.DATE_DEB_KEY, EOKeyValueQualifier.QualifierOperatorLessThanOrEqualTo, fin));
			quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_OBJETS_KEY + "."
					+ ReservationObjet.RESA_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, objet));
			NSArray<Periodicite> resas = DBHandler.fetchData(eContext, Periodicite.ENTITY_NAME, new EOAndQualifier(quals));
			for (int i = 0; i < resas.count(); i++) {
				Periodicite currentResaOcc = resas.objectAtIndex(i);
				Reservation reservation = currentResaOcc.reservation();
				NSMutableDictionary<String, Object> dRes = new NSMutableDictionary<String, Object>();
				dRes.takeValueForKey(eContext.globalIDForObject(reservation), "reservation");
				NSTimestamp debutRes = currentResaOcc.dateDeb();
				dRes.takeValueForKey(debutRes, "debut");
				dRes.takeValueForKey(currentResaOcc.dateFin(), "fin");

				Integer jour = new Integer(calendarHandler.getDay(debutRes));
				dRes.takeValueForKey(jour, "jour");
				dRes.takeValueForKey(new Integer(1), "resa");
				String tlocCode = reservation.tlocCode();

				if (rang != null) {
					dRes.takeValueForKey(rang, "rang");
				}

				if (reservation.isVisiblePourAgent(agent())) {
					NSMutableArray<String> textes = new NSMutableArray<String>();
					textes.addObject(reservation.resaCommentaire());

					if (reservation.occupants() != null && reservation.occupants().count() > 0) {
						StringBuffer strOccupants = new StringBuffer();
						NSArray<Occupant> occupants = DBHandler.retirerMultiples(reservation.occupants());
						int nbOccup = occupants.count();
						if (nbOccup > 3) {
							nbOccup = 3;
						}
						for (int io = 0; io < nbOccup; io++) {
							IndividuUlr ioccupant = (occupants.objectAtIndex(io)).individu();
							strOccupants.append(ioccupant.prenom().substring(0, 1) + "." + StringCtrl.capitalizeWords(ioccupant.nomUsuel()) + " ");
						}
						textes.addObject(strOccupants.toString());
					}

					if (reservation.resaSalles() != null && reservation.resaSalles().count() > 0) {
						dRes.takeValueForKey(((ResaSalles) reservation.resaSalles().lastObject()).resaSalEtat(), "salleValide");
						StringBuffer strSal = new StringBuffer();
						for (int j = 0; j < reservation.resaSalles().count(); j++) {
							Salles sal = ((ResaSalles) reservation.resaSalles().objectAtIndex(j)).salle();
							strSal.append(StringCtrl.capitalizeWords(sal.cLocal()) + " : " + StringCtrl.capitalizeWords(sal.salPorte()));
							if (j > 0 && j < reservation.resaSalles().count() - 1) {
								strSal.append(",");
							}
						}
						textes.addObject(strSal.toString());
					}
					dRes.takeValueForKey(textes, "texte");
					dRes.takeValueForKey(tlocCode, "ccolor");
				}
				else {
					dRes.takeValueForKey(
							new NSArray<String>("Réservation privée (réalisée par "
									+ StringCtrl.capitalizeWords(reservation.individuAgent().prenomNom()) + ")"), "texte");
					dRes.takeValueForKey("z", "ccolor");
				}

				dRes.takeValueForKey(new Integer(1), "deplace");
				dRes.takeValueForKey(objet.roLibelle1(), "libelle");
				dRes.takeValueForKey(((ReservationObjet) reservation.reservationObjets().lastObject()).resaEtat(), "objetValide");

				plans.addObject(dRes);
			}
		}
		catch (Throwable ex) {
			ex.printStackTrace();
			plans = new NSMutableArray<NSDictionary<String, Object>>();
		}

		return plans;
	}

	/** retourne les emails des gardiens de la salle */
	public NSDictionary clientSideRequestEmailGardienSalle(EOGlobalID idSalle) {
		NSDictionary dico = null;
		try {
			if (mailHandler == null) {
				mailHandler = new MailHandler(woApplication, this);
			}
			dico = mailHandler.emailGardienSalle(idSalle);
		}
		catch (Exception e) {
			NSLog.out.appendln("Erreur (clientSideRequestEmailGardienSalle):" + e.getMessage());
		}
		return dico;
	}

	/** *** VERIFICATION ******* */

	public NSArray<NSArray<?>> clientSideRequestGetNonDisponibiliteAp(EOGlobalID gidRes, NSArray<NSTimestamp> periodicites, EOGlobalID gidAp,
			Boolean avecAps, Boolean avecControleApsObligatoires, Boolean avecControleApsNonObligatoires) {
		try {
			Verification verif = new Verification(eContext);
			MaquetteAp ap = (MaquetteAp) eContext.faultForGlobalID(gidAp, eContext);
			Reservation resa = null;
			if (gidRes != null) {
				resa = (Reservation) eContext.faultForGlobalID(gidRes, eContext);
			}
			boolean controleApsObligatoires;
			if (avecControleApsObligatoires == null) {
				controleApsObligatoires = ((Application) Application.application()).isAppControleApsOccupation();
			}
			else {
				controleApsObligatoires = avecControleApsObligatoires.booleanValue();
			}
			boolean controleApsNonObligatoires;
			if (avecControleApsNonObligatoires == null) {
				controleApsNonObligatoires = ((Application) Application.application()).isAppControleApsNonObligatoiresOccupation();
			}
			else {
				controleApsNonObligatoires = avecControleApsNonObligatoires.booleanValue();
			}
			return verif.getNonDisponibiliteAp(resa, periodicites, ap, false, avecAps.booleanValue(), controleApsObligatoires,
					controleApsNonObligatoires, false);
		}
		catch (Throwable th) {
			th.printStackTrace();
			return null;
		}
	}

	public NSArray<NSTimestamp> clientSideRequestVerifSemestrePourModification(EOGlobalID gidRes, NSArray<NSTimestamp> periodicites,
			EOGlobalID gidSemestre) {
		try {
			Verification verif = new Verification(eContext);
			MaquetteSemestre sem = (MaquetteSemestre) eContext.faultForGlobalID(gidSemestre, eContext);

			Reservation resa = null;
			if (gidRes != null) {
				resa = (Reservation) eContext.faultForGlobalID(gidRes, eContext);
			}

			return verif.verifSemestrePourModification(resa, periodicites, sem);
		}
		catch (Throwable th) {
			th.printStackTrace();
			return null;
		}
	}

	public NSArray clientSideRequestGetNonDisponibliteExamen(NSArray periodicites, EOGlobalID gidExam) {
		try {
			Verification verif = new Verification(eContext);
			ExamenEntete examenEntete = (ExamenEntete) eContext.faultForGlobalID(gidExam, eContext);
			return verif.getNonDisponibliteExamen(periodicites, examenEntete);
		}
		catch (Throwable th) {
			th.printStackTrace();
			return null;
		}
	}

	public NSArray clientSideRequestVerifExamenPourModification(EOGlobalID gidRes, NSArray periodicites, EOGlobalID gidExam) {
		try {
			Verification verif = new Verification(eContext);
			ExamenEntete examenEntete = (ExamenEntete) eContext.faultForGlobalID(gidExam, eContext);
			Reservation resa = null;
			if (gidRes != null) {
				resa = (Reservation) eContext.faultForGlobalID(gidRes, eContext);
			}

			return verif.verifExamenPourModification(resa, periodicites, examenEntete);
		}
		catch (Throwable th) {
			th.printStackTrace();
			return null;
		}
	}

	public NSArray<NSArray<?>> clientSideRequestGetNonDisponibiliteGroupe(EOGlobalID gidRes, NSArray<NSTimestamp> periodicites, EOGlobalID gidAp,
			EOGlobalID gidGroupe, Boolean avecAps, Boolean avecControleApsObligatoires, Boolean avecControleApsNonObligatoires,
			Boolean avecControleGroupes, Boolean avecStopPremiereIndispo) {
		try {
			Verification verif = new Verification(eContext);
			MaquetteAp ap = null;
			if (gidAp != null) {
				ap = (MaquetteAp) eContext.faultForGlobalID(gidAp, eContext);
			}
			ScolGroupeGrp grp = null;
			if (gidGroupe != null) {
				grp = (ScolGroupeGrp) eContext.faultForGlobalID(gidGroupe, eContext);
			}
			if (grp == null) {
				return new NSArray<NSArray<?>>();
			}
			Reservation resa = null;
			if (gidRes != null) {
				resa = (Reservation) eContext.faultForGlobalID(gidRes, eContext);
			}
			boolean controleApsObligatoires;
			if (avecControleApsObligatoires == null) {
				controleApsObligatoires = ((Application) Application.application()).isAppControleApsOccupation();
			}
			else {
				controleApsObligatoires = avecControleApsObligatoires.booleanValue();
			}
			boolean controleApsNonObligatoires;
			if (avecControleApsNonObligatoires == null) {
				controleApsNonObligatoires = ((Application) Application.application()).isAppControleApsNonObligatoiresOccupation();
			}
			else {
				controleApsNonObligatoires = avecControleApsNonObligatoires.booleanValue();
			}
			boolean controleGroupes;
			if (avecControleGroupes == null) {
				controleGroupes = ((Application) Application.application()).config().booleanForKey("APP_CONTROLE_GROUPES_OCCUPATION");
			}
			else {
				controleGroupes = avecControleGroupes.booleanValue();
			}
			boolean stopPremiereIndispo;
			if (avecStopPremiereIndispo == null) {
				stopPremiereIndispo = true;
			}
			else {
				stopPremiereIndispo = avecStopPremiereIndispo.booleanValue();
			}
			return verif.getNonDisponibiliteGroupe(resa, periodicites, ap, grp, avecAps, controleApsObligatoires, controleApsNonObligatoires,
					controleGroupes, stopPremiereIndispo);
		}
		catch (Throwable th) {
			th.printStackTrace();
			return null;
		}
	}

	/** *** IMPRESSION ******* */

	public NSData clientSideRequestPrintLastDisplay(String chCalendrier, String titre, NSTimestamp debut, Boolean color, Boolean impressionHoraires,
			Boolean impTableau) {
		try {
			if (impTableau.booleanValue()) {
				TabularEdtReport ter = new TabularEdtReport(eContext);
				NSData pdfData = ter.genererPdf(plans, debut, chCalendrier, titre);
				return pdfData;
			}
			else {
				PrintFactory printFactory = new PrintFactory(woApplication.config(), this);
				printFactory.setReservations(plans);
				return printFactory.imprimerEdt(chCalendrier, titre, debut, color.booleanValue(), impressionHoraires.booleanValue());
			}
		}
		catch (Throwable th) {
			NSLog.out.appendln("(clientSideRequestPrintLastDisplay):" + th.getMessage());
			th.printStackTrace();
			return null;
		}
	}

	public NSData clientSideRequestPrintThis(String chCalendrier, String titre, NSTimestamp debut, Boolean color, Boolean impressionHoraires,
			Boolean impTableau, NSArray plannings) {
		try {
			if (impTableau.booleanValue()) {
				TabularEdtReport ter = new TabularEdtReport(eContext);
				NSData pdfData = ter.genererPdf(plannings, debut, chCalendrier, titre);
				return pdfData;
			}
			else {
				PrintFactory printFactory = new PrintFactory(woApplication.config(), this);
				printFactory.setReservations(plannings);
				return printFactory.imprimerEdt(chCalendrier, titre, debut, color.booleanValue(), impressionHoraires.booleanValue());
			}
		}
		catch (Throwable th) {
			NSLog.out.appendln("(clientSideRequestPrintLastDisplay):" + th.getMessage());
			th.printStackTrace();
			return null;
		}
	}

	public Boolean clientSideRequestExportWebLastDisplay(String titre, Number fannKey, Number msemKey, Number ggrpKey, Number weekNumber,
			NSTimestamp debut, NSTimestamp fin) {
		boolean retour = false;
		try {
			WebExport webExport = new WebExport(this);
			retour = webExport.exportWebLastDisplay(titre, fannKey, msemKey, ggrpKey, weekNumber, debut, fin);
		}
		catch (Throwable th) {
			th.printStackTrace();
			retour = false;
		}
		return Boolean.valueOf(retour);
	}

	/** permet de faire un export Excel des reservations actuellement consultees */
	public NSData clientSideRequestGetExcelExport(String chCalendrier, String titre, NSTimestamp debutSemaine) {
		try {
			FileExportFactory fileExportFactory = new FileExportFactory(this);
			fileExportFactory.setReservations(plans, FileExportFactory.COURS);
			return fileExportFactory.getExcelFileForCours(debutSemaine, chCalendrier, titre);
		}
		catch (Throwable th) {
			NSLog.out.appendln("(clientSideRequestGetExcelExport):" + th.getMessage());
			th.printStackTrace();
			return null;
		}
	}

	/** permet de faire un export Excel des reservations actuellement consultees */
	public NSData clientSideRequestExelReservationParAp(NSDictionary infos) {
		try {
			FileExportFactory fileExportFactory = new FileExportFactory(this);
			return fileExportFactory.getExcelFileForCreneaux(infos);
		}
		catch (Throwable th) {
			NSLog.out.appendln("(clientSideRequestExelReservationParAp):" + th.getMessage());
			th.printStackTrace();
			return null;
		}
	}

	public NSData clientSideRequestExcelReservationParPersonne(NSDictionary infos) {
		try {
			FileExportFactory fileExportFactory = new FileExportFactory(this);
			return fileExportFactory.getExcelFileForReservationParPersonne(infos);
		}
		catch (Throwable th) {
			NSLog.out.appendln("(clientSideRequestExcelReservationParPersonne):" + th.getMessage());
			th.printStackTrace();
			return null;
		}
	}

	/**
	 * retourne l'url de la presentation graphique(faite avec viewletbuilder) pour consultation
	 */
	public String clientSideRequestGetUrlViewlet() {
		return woApplication.config().stringForKey("ULR_VIEWLET");
	}

	public NSArray clientSideRequestFindCreneauxLibresForResa(EOGlobalID gidResa, NSArray dates, Integer dureeMin) {
		NSArray creneaux;
		try {
			CreneauxFinder cFinder = new CreneauxFinder(eContext);
			Reservation resa = (Reservation) DBHandler.safeObjectForGlobalID(eContext, gidResa);
			creneaux = cFinder.findCreneauxLibresForResa(resa, dates, dureeMin.intValue());
			creneaux = genericPlanning(creneaux, "L");
		}
		catch (Exception e) {
			e.printStackTrace();
			creneaux = new NSArray();
		}
		return creneaux;
	}

	private NSArray genericPlanning(NSArray creneaux, String code) {

		NSMutableArray plans = new NSMutableArray();

		NSTimestamp vDeb, vFin;
		for (int i = 0; i < creneaux.count(); i += 2) {
			vDeb = (NSTimestamp) creneaux.objectAtIndex(i);
			vFin = (NSTimestamp) creneaux.objectAtIndex(i + 1);
			NSArray slices = slicePeriod(vDeb, vFin);
			for (int j = 0; j < slices.count(); j += 2) {
				vDeb = (NSTimestamp) slices.objectAtIndex(j);
				vFin = (NSTimestamp) slices.objectAtIndex(j + 1);
				// preparation du creneau
				NSMutableDictionary dRes = new NSMutableDictionary();
				dRes.takeValueForKey(vDeb, "debut");
				dRes.takeValueForKey(vFin, "fin");
				Integer jour = new Integer(calendarHandler.getDay(vDeb));
				dRes.takeValueForKey(jour, "jour");
				dRes.takeValueForKey(code, "ccolor");
				dRes.takeValueForKey(new NSArray(" "), "texte");
				dRes.takeValueForKey(new Integer(1), "resa");
				plans.addObject(dRes);
			}
		}
		return plans;
	}

	public NSArray slicePeriod(NSTimestamp debut, NSTimestamp fin) {

		NSMutableArray slices = new NSMutableArray();

		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);

		int nbJ = TimeHandler.daysSeparatingDates(fin, debut);

		NSTimestamp currentDeb, currentFin;
		NSTimestamp currentDate = debut;

		// le premier jour
		currentDeb = debut;
		if (nbJ == 0) {
			slices.addObject(debut);
			slices.addObject(fin);
		}
		else {
			currentFin = FormatHandler.endOfDay(debut);
			slices.addObject(debut);
			slices.addObject(currentFin);

			String sjFin = FormatHandler.dateToStr(fin, "%d/%m/%Y");
			cal.setTime(debut);

			while (true) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				cal.setTime(cal.getTime());
				currentDate = new NSTimestamp(cal.getTime());
				currentDeb = FormatHandler.midnightTime(currentDate);
				currentFin = FormatHandler.endOfDay(currentDate);

				if (!FormatHandler.dateToStr(currentFin, "%d/%m/%Y").equals(sjFin)) {
					slices.addObject(currentDeb);
					slices.addObject(currentFin);
				}
				else {
					break;
				}
			}

			// le dernier jour
			slices.addObject(FormatHandler.midnightTime(fin));
			slices.addObject(fin);

		}// else

		return slices;
	}

	private NSArray fetchArrayWithSQL(EOEditingContext ec, String entity, String sqlString, NSArray sort, Integer maxObject, Boolean distinct) {
		EOFetchSpecification spec = new EOFetchSpecification(entity, null, sort, true, false, null);
		spec.setHints(new NSDictionary(sqlString, EODatabaseContext.CustomQueryExpressionHintKey));
		spec.setRefreshesRefetchedObjects(true);
		if (maxObject != null) {
			spec.setFetchLimit(maxObject.intValue());
		}
		if (distinct != null) {
			spec.setUsesDistinct(distinct.booleanValue());
		}
		return ec.objectsWithFetchSpecification(spec);
	}

	/** clientSideRequestRecopierEdt */
	public NSArray clientSideRequestRecopierEdt(NSDictionary args) {
		try {
			System.out.println("clientSideRequestRecopierEdt:" + args.objectForKey("login"));
			RecopieEdtHandler recopieEdtHandler = new RecopieEdtHandler(this);
			return recopieEdtHandler.recopierEdt(args);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// =============ying ajouter==============
	public void clientSideRequestActiverAlerte(String lExpediteur, String leDestinataire, String leMotif, NSTimestamp leDebut, Integer leTempsDAvance) {
		// Execution de la procedure
		activerAlerte(lExpediteur, leDestinataire, leMotif, leDebut, leTempsDAvance);
	}

	// *****************************
	public void activerAlerte(String lExpediteur, String leDestinataire, String leMotif, NSTimestamp leDebut, Integer leTempsDAvance) {
		GregorianCalendar leCalendar = new GregorianCalendar();
		leCalendar.setTime(leDebut);
		leCalendar.add(Calendar.MINUTE, -leTempsDAvance.intValue());
		Timer leTimer = new Timer();
		leTimer.schedule(new Alerte(lExpediteur, leDestinataire, leMotif, leDebut, leTempsDAvance), leCalendar.getTime());
	}

	public class Alerte extends TimerTask {
		String lExpediteur;
		String leDestinataire;
		String leMotif;
		NSTimestamp leDebut;
		Integer leTempsDAvance;
		String mailServ;

		// *****************************
		// Constructeur
		public Alerte(String lExpediteur, String leDestinataire, String leMotif, NSTimestamp leDebut, Integer leTempsDAvance) {
			this.lExpediteur = lExpediteur;
			this.leDestinataire = leDestinataire;
			this.leMotif = leMotif;
			this.leDebut = leDebut;
			this.leTempsDAvance = leTempsDAvance;
		}

		// *****************************
		// Envoi d'un mail
		public void run() {
			if (leDebut.compare(new NSTimestamp()) == NSComparator.OrderedDescending) {
				CktlMailBus mailBus = new CktlMailBus(config());
				mailBus.sendMail(lExpediteur, leDestinataire, null, "Alerte d'agenda !", "Vous avez en prevu dans " + leTempsDAvance.toString()
						+ " minutes :\n" + leMotif);

			}
		}// FM
	}

	public String clientSideRequestAppliId() {
		return woApplication.appCktlVersion().name();
	}

	public String clientSideRequestAppliVersion() {
		// return woApplication.appCktlVersion().version();
		return Version.VERSIONNUMMAJ + "." + Version.VERSIONNUMMIN + "." + Version.VERSIONNUMPATCH + "." + Version.VERSIONNUMBUILD;
	}

	public String clientSideRequestAppliDate() {
		return woApplication.appCktlVersion().date();
	}

	public String clientSideRequestDefaultConnectionUrl() {
		NSDictionary<String, Object> dico = woApplication.connectionDictionaryToDB();
		if (dico != null) {
			return (String) dico.valueForKey("URL");
		}
		return null;
	}

	public NSData clientSideRequestGenerateReport(String reportID, NSDictionary reportParams) {

		NSDictionary dico = woApplication.connectionDictionaryToDB();
		String dbConnectionURL = (String) dico.objectForKey("URL");
		String username = (String) dico.objectForKey("username");
		String password = (String) dico.objectForKey("password");
		String jdbcDriver = woApplication.config().stringForKey("JDBC_DRIVER_NAME");

		ReportsGenerator reportGenerator = new ReportsGenerator(dbConnectionURL, username, password, jdbcDriver);

		CktlResourceManager resBundle = woApplication.appResources();

		String subreportDir = resBundle.pathForResource("_reports");

		String fileName = woApplication.config().stringForKey(reportID);

		if (subreportDir == null || fileName == null) {
			return null;
		}

		subreportDir = subreportDir.trim();
		fileName = fileName.trim();

		String reportFullName = subreportDir + "/" + fileName;

		java.util.Hashtable mapParams = reportParams.hashtable();

		mapParams.put("SUBREPORT_DIR", subreportDir);

		NSData pdfData = null;
		try {
			byte[] pdfBytes = reportGenerator.getPdfBytesForReport(reportFullName, mapParams);
			if (pdfBytes != null) {
				pdfData = new NSData(pdfBytes);
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
		}

		return pdfData;

	}

	public void setLazyMode(boolean mode) {
		this.lazyMode = mode;
	}

	public boolean isLazyMode() {
		return lazyMode;
	}

	/** clientSideRequestGetPdfHcompAp */
	public NSData clientSideRequestGetPdfHcompAp(NSDictionary liste) {
		try {
			HCompPrintFactory printFactory = HCompPrintFactory.getInstance(woApplication.config());
			return printFactory.getPdfDataHcompAp(liste);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public NSData clientSideRequestPdfDetailHeures(NSDictionary liste) {
		try {
			HCompPrintFactory printFactory = HCompPrintFactory.getInstance(woApplication.config());
			return printFactory.getPdfDetailHeures(liste);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
