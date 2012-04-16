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

import org.cocktail.fwkcktlwebapp.server.CktlERXStaticResourceRequestHandler;
import org.cocktail.fwkcktlwebapp.server.CktlWebApplication;
import org.cocktail.fwkcktlwebapp.server.version.A_CktlVersion;

import com.webobjects.eoaccess.EOModel;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSTimeZone;

import er.extensions.appserver.ERXApplication;

public class Application extends CktlWebApplication {

	private static final String MAIN_MODEL_NAME = "SuperPlan";
	private static final String CONFIG_FILE_NAME = "SuperPlan.config";
	private static final String CONFIG_TABLE_NAME = "FwkCktlWebApp_GrhumParametres";

	// Paramètres obligatoires / optionnels (dans le configFileName et le configTableName) ?
	private static final String[] CONFIG_MANDATORY_KEYS = new String[] { "APP_ID" };
	private static final String[] CONFIG_OPTIONAL_KEYS = new String[] { "SAUT_USER", "SQL_DEBUG", "EDT_MAIL_PREFIX", "APP_SEND_MAIL",
			"APP_SEND_MAIL_DEPOSITAIRES", "APP_CONTROLE_APS_OCCUPATION", "APP_CONTROLE_GROUPES_OCCUPATION", "APP_CONTROLE_MISSIONS",
			"APP_RESERVATION_SALLE_PAR_DEPOSITAIRE" };

	// Collecte des informations ou non ??
	private static final boolean COCKTAIL_COLLECTE = true;

	private A_CktlVersion _appCktlVersion = null;
	private A_CktlVersion _appCktlVersionDB = null;

	private boolean useServeurPlanning = false;
	private String appControleApsOccupation = "BOF";

	// public static void main(String argv[]) {
	// WOApplication.main(argv, Application.class);
	// }

	public static void main(String argv[]) {
		ERXApplication.main(argv, Application.class);
	}

	public Application() {
		super();

		rawLogModelInfos();
		checkModel();
		rawLogVersionInfos();

		if (config().booleanForKey("SQL_DEBUG")) {
			NSLog.setAllowedDebugGroups(NSLog.DebugLevelDetailed);
			NSLog.allowDebugLoggingForGroups(NSLog.DebugGroupSQLGeneration);
			NSLog.allowDebugLoggingForGroups(NSLog.DebugGroupDatabaseAccess);
		}

		// WebService WSReservation
		/*
		 * try { WOWebServiceRegistrar.registerWebService(WSReservationProvider.class, true); } catch (Exception e) { e.printStackTrace(); }
		 */
		rawLogAppInfos();

		// settage du timezone
		String tzString = null;
		if (config() != null) {
			tzString = config().stringForKey("DEFAULT_TIME_ZONE");
		}
		if (tzString == null) {
			tzString = "Europe/Paris";
		}
		java.util.TimeZone tz = java.util.TimeZone.getTimeZone(tzString);
		java.util.TimeZone.setDefault(tz);
		NSTimeZone.setDefault(tz);

		useServeurPlanning = config().booleanForKey("UTILISER_SERVEUR_PLANNING");
		System.out.println("Utiliser serveur planning : " + useServeurPlanning);

		appControleApsOccupation = config().stringForKey("APP_CONTROLE_APS_OCCUPATION");

		// fix pour que les WebServerResources marchent en javaclient
		if (isDirectConnectEnabled()) {
			registerRequestHandler(new CktlERXStaticResourceRequestHandler(), "wr");
		}
	}

	public String configFileName() {
		return CONFIG_FILE_NAME;
	}

	public String configTableName() {
		return CONFIG_TABLE_NAME;
	}

	public String mainModelName() {
		return MAIN_MODEL_NAME;
	}

	public boolean appShouldSendCollecte() {
		return COCKTAIL_COLLECTE;
	}

	public String[] configMandatoryKeys() {
		return CONFIG_MANDATORY_KEYS;
	}

	public String[] configOptionalKeys() {
		return CONFIG_OPTIONAL_KEYS;
	}

	public A_CktlVersion appCktlVersion() {
		if (_appCktlVersion == null) {
			_appCktlVersion = new Version();
		}
		return _appCktlVersion;
	}

	public A_CktlVersion appCktlVersionDb() {
		if (_appCktlVersionDB == null) {
			_appCktlVersionDB = new VersionDB();
		}
		return _appCktlVersionDB;
	}

	public NSDictionary connectionDictionaryToDB() {
		final EOModelGroup modelGroup = EOModelGroup.defaultGroup();
		final EOModel model = modelGroup.modelNamed(config().stringForKey("MAIN_MODEL_NAME"));
		return model.connectionDictionary();
	}

	public boolean useServeurPlanning() {
		return useServeurPlanning;
	}

	public boolean isAppControleApsNonObligatoiresOccupation() {
		return isAppControleApsOccupation();
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

}
