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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;

import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.impression.ImpressionExportWebDialog;
import edtscol.client.panier.GestionPanier;
import edtscol.client.planningview.ReservationClassifier;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.ReflectHelper;
import fr.univlr.utilities.FileHandler;
import fr.univlr.utilities.TimeHandler;

public class GestionPlanning {

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	private EOEditingContext eContext;
	private NSTimestamp startDate, endDate;
	private PlanningView planningView;
	public NSMutableArray<NSArray<NSDictionary<String, Object>>> plans;

	private NSDictionary planningDescription;

	private MainInterface owner;

	/** constructeur avec editingContext -de preference celui de l'application- */
	public GestionPlanning(MainInterface owner, EOEditingContext eContext, PlanningView planningView) {
		this.eContext = eContext;
		this.planningView = planningView;
		this.owner = owner;
	}

	/** operations d'initialisation */
	public void init() {

	}

	/** affecte la date de debut et la date de fin de la periode de recherche - 1 semaine - */
	public void setDateRange(NSTimestamp theStartDate, NSTimestamp theEndDate) {
		this.startDate = theStartDate;
		this.endDate = theEndDate;
	}

	public void processPlanningMultiples(NSArray infos) {
		plans = new NSMutableArray();
		for (int i = 0; i < infos.count(); i++) {
			NSDictionary unInfo = (NSDictionary) infos.objectAtIndex(i);
			EOGenericRecord resRecord = (EOGenericRecord) unInfo.objectForKey("resRecord");
			if (resRecord instanceof Salles) {
				plans.addObject(this.getPlanningSalle((Salles) resRecord, new Integer(i)));
			}
			if (resRecord instanceof IndividuUlr) {
				plans.addObject(this.getPlanningIndividu((IndividuUlr) resRecord, new Integer(i)));
			}
			if (resRecord instanceof ResaObjet) {
				plans.addObject(this.getPlanningObjet((ResaObjet) resRecord, new Integer(i)));
			}
		}
		planningView.afficherPlanningMultiples(plans, infos.count());
	}

	public void printAndExportWebOperations(Object sender, ArrayList<Integer> weekList, boolean print, boolean exportWeb, boolean impHoraire,
			boolean impTableau) {

		int type = ((Number) planningDescription.objectForKey("type")).intValue();

		if (type == GestionPanier.ENSEIGNEMENT || type == GestionPanier.GROUPE_SCOL) {
			printAndExportEnseignement(sender, weekList, print, exportWeb, impHoraire, impTableau, type);
		}
		else {
			printPlanningSimple(sender, weekList, type, impHoraire, impTableau);
		}
	}

	public void printPlanningSimple(Object sender, ArrayList<Integer> weekList, int type, boolean impHoraire, boolean impTableau) {
		EOGenericRecord resRecord = (EOGenericRecord) planningDescription.objectForKey("resRecord");
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		String libelle = owner.currentResourceLibelle();

		Number aAnneeScol = app.getCurrentDisplayedYear();
		NSData pdfData;

		for (int i = 0; i < weekList.size(); i++) {
			int weekNumber = (weekList.get(i)).intValue();
			int year = TimeHandler.getYearForWeekAndFannKey(weekNumber, aAnneeScol.intValue(), !app.isAnneeUniversitaire());

			NSTimestamp[] dates = TimeHandler.getBeginAndEndOfWeek(weekNumber, year);
			NSTimestamp aDebut = dates[0];
			NSTimestamp aFin = dates[1];
			String chCal = "Semaine " + weekNumber + " - " + FormatHandler.dateToStr(aDebut) + " - " + FormatHandler.dateToStr(aFin);

			if (getPlans() == null) {
				switch (type) {
				case GestionPanier.PERSONNE:
					getPlanningIndividu((IndividuUlr) resRecord, aDebut, aFin);
					break;

				case GestionPanier.SALLE:
					getPlanningSalle((Salles) resRecord, aDebut, aFin);
					break;

				case GestionPanier.OBJET:
					getPlanningObjet((ResaObjet) resRecord, aDebut, aFin);
					break;

				}
				pdfData = (NSData) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPrintLastDisplay", new Class[] {
						String.class, String.class, NSTimestamp.class, Boolean.class, Boolean.class, Boolean.class }, new Object[] { chCal, libelle,
						aDebut, Boolean.TRUE, Boolean.valueOf(impHoraire), Boolean.valueOf(impTableau) }, false);
			}
			else {
				NSMutableArray<NSDictionary<String, Object>> ma = new NSMutableArray<NSDictionary<String, Object>>();
				Enumeration<NSArray<NSDictionary<String, Object>>> e = getPlans().objectEnumerator();
				while (e.hasMoreElements()) {
					ma.addObjectsFromArray(e.nextElement());
				}
				pdfData = (NSData) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPrintThis", new Class[] {
						String.class, String.class, NSTimestamp.class, Boolean.class, Boolean.class, Boolean.class, NSArray.class }, new Object[] {
						chCal, libelle, aDebut, Boolean.TRUE, Boolean.valueOf(impHoraire), Boolean.valueOf(impTableau), ma }, false);
			}
			FileHandler fileHandler = new FileHandler();
			if (fileHandler != null) {
				String fname = "Edt-" + weekNumber;
				String filePath = fileHandler.dataToPDF(pdfData, fname);
				try {
					fileHandler.openFile(filePath);
				}
				catch (Exception e) {
					System.out.println("Erreur a l'ouverture du pdf :" + e.getMessage());
				}
			}
		} // for
	}

	public void printAndExportEnseignement(Object sender, ArrayList<Integer> weekList, boolean print, boolean exportWeb, boolean impHoraire,
			boolean impTableau, int type) {
		MaquetteSemestre aSemestre = (MaquetteSemestre) planningDescription.objectForKey("semestre");
		Boolean isAutreSemestre = (Boolean) planningDescription.objectForKey("isAutreSemestre");
		Boolean isParcoursCommun = (Boolean) planningDescription.objectForKey("isParcoursCommun");
		Number aAnneeScol = (Number) planningDescription.objectForKey("anneeScol");
		EOGenericRecord resRecord = (EOGenericRecord) planningDescription.objectForKey("resRecord");

		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();

		String libelle = (String) planningDescription.valueForKey("libelle");
		for (int i = 0; i < weekList.size(); i++) {
			int weekNumber = (weekList.get(i)).intValue();

			int year = TimeHandler.getYearForWeekAndFannKey(weekNumber, aAnneeScol.intValue(), !app.isAnneeUniversitaire());
			NSTimestamp[] dates = TimeHandler.getBeginAndEndOfWeek(weekNumber, year);

			NSTimestamp aDebut = dates[0];
			NSTimestamp aFin = dates[1];

			String chCal = "Semaine " + weekNumber + " - " + FormatHandler.dateToStr(aDebut) + " - " + FormatHandler.dateToStr(aFin);

			if (type == GestionPanier.ENSEIGNEMENT) {
				// StringBuffer sb = new StringBuffer();
				// sb.append("resRecord = " + resRecord + "\n");
				// sb.append("isParcoursCommun = " + isParcoursCommun + "\n");
				// sb.append("aSemestre = " + aSemestre + "\n");
				// sb.append("isAutreSemestre = " + isAutreSemestre + "\n");
				// sb.append("aAnneeScol = " + aAnneeScol + "\n");
				// sb.append("aDebut = " + aDebut + "\n");
				// sb.append("aFin = " + aFin);
				// EODialogs.runInformationDialog("Log", sb.toString());
				getPlanningDiplome((MaquetteParcours) resRecord, isParcoursCommun, aSemestre, isAutreSemestre, aAnneeScol, aDebut, aFin);
			}
			if (type == GestionPanier.GROUPE_SCOL) {
				getPlanningGroupe((ScolGroupeGrp) resRecord, aDebut, aFin);
			}
			NSData pdfData = (NSData) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPrintLastDisplay",
					new Class[] { String.class, String.class, NSTimestamp.class, Boolean.class, Boolean.class, Boolean.class }, new Object[] { chCal,
							libelle, aDebut, Boolean.TRUE, Boolean.valueOf(impHoraire), Boolean.valueOf(impTableau) }, false);
			if (pdfData == null) {
				ReflectHelper.invokeMethod(sender, ImpressionExportWebDialog.ON_PROGRESS_NOTIFICATION_METHOD, "Echec de génération du fichier pdf !",
						String.class);
				continue;
			}

			Boolean status = Boolean.FALSE;
			// Export web
			if (exportWeb && pdfData != null) {
				Number msemKey = (Number) app.primaryKeyFromEO(aSemestre, "msemKey");
				// Si c'est un groupe pédagogique
				if (type == GestionPanier.GROUPE_SCOL) {

					Number ggrpKey = (Number) app.primaryKeyFromEO(resRecord, "ggrpKey");
					status = (Boolean) objectStore
							.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestExportWebLastDisplay", new Class[] { String.class,
									Number.class, Number.class, Number.class, Number.class, NSTimestamp.class, NSTimestamp.class }, new Object[] {
									libelle, aAnneeScol, msemKey, ggrpKey, new Integer(weekNumber), aDebut, aFin }, false);
				}
				// Si c'est une mention complète
				if (type == GestionPanier.ENSEIGNEMENT) {

					status = (Boolean) objectStore
							.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestExportWebLastDisplay", new Class[] { String.class,
									Number.class, Number.class, Number.class, Number.class, NSTimestamp.class, NSTimestamp.class }, new Object[] {
									libelle, aAnneeScol, msemKey, null, new Integer(weekNumber), aDebut, aFin }, false);
				}
				String message = status ? "Succès" : "Echec";
				message += " export semaine " + weekNumber;

				ReflectHelper.invokeMethod(sender, ImpressionExportWebDialog.ON_PROGRESS_NOTIFICATION_METHOD, message, String.class);
			}

			// Generation du pdf pour impression
			if (print && pdfData != null) {
				FileHandler fileHandler = new FileHandler();
				if (fileHandler != null) {
					String fname;
					if (aSemestre.msemOrdre() == -1) {
						fname = "Edt-" + weekNumber;
					}
					else {
						fname = "Edt-" + weekNumber;
					}

					String filePath = fileHandler.dataToPDF(pdfData, fname);
					try {
						fileHandler.openFile(filePath);
					}
					catch (Exception e) {
						e.printStackTrace();
					}

					String message = (pdfData != null) ? "Succès" : "Echec";
					message += " impression semaine " + weekNumber;

					ReflectHelper.invokeMethod(sender, ImpressionExportWebDialog.ON_PROGRESS_NOTIFICATION_METHOD, message, String.class);
				}
			} // print
		}
	}

	/** calcule et demander au PlanningView d'afficher le planning en fonction du type de ressource choisie */
	public void processPlanning(NSDictionary infos) {

		planningDescription = infos;

		Number type = (Number) infos.objectForKey("type");
		EOGenericRecord resRecord = (EOGenericRecord) infos.objectForKey("resRecord");
		MaquetteSemestre leSemestre = null;
		NSArray resas = null;
		switch (type.intValue()) {
		case GestionPanier.PERSONNE:
			boolean clearFirst = true;

			if (clearFirst) {
				planningView.clear();
			}

			NSMutableArray plans = new NSMutableArray();
			resas = this.getPlanningIndividu((IndividuUlr) resRecord);
			if (resas != null && resas.count() > 0) {
				plans.addObjectsFromArray(resas);
			}

			if (plans != null && plans.count() > 0) {
				ReservationClassifier resaClassifier = new ReservationClassifier();
				planningView.afficherPlanningUbiquite(resaClassifier.classifyReservations(plans));
			}
			break;

		case GestionPanier.SALLE:
			resas = this.getPlanningSalle((Salles) resRecord, startDate, endDate);
			planningView.afficherPlanningGenerique(resas, true);
			break;

		case GestionPanier.ENSEIGNEMENT:
			leSemestre = (MaquetteSemestre) infos.objectForKey("semestre");
			Boolean isAutreSemestre = (Boolean) infos.objectForKey("isAutreSemestre");
			FormationHabilitation formation = (FormationHabilitation) infos.objectForKey("formation");
			Boolean isParcoursCommun = (Boolean) infos.objectForKey("isParcoursCommun");
			Number anneeScol = (Number) infos.objectForKey("anneeScol");
			resas = this.getPlanningDiplome((MaquetteParcours) resRecord, isParcoursCommun, leSemestre, isAutreSemestre, anneeScol);
			ReservationClassifier resaClassifier = new ReservationClassifier();
			NSArray resaClassees = resaClassifier.classifyReservations(resas);
			planningView.afficherPlanningEnseignement(resaClassees);
			// les vacances :
			// resas = vacancesFormation(formation.formationSpecialisation(), startDate, endDate);
			// planningView.afficherPlanningGenerique(resas, false);

			break;

		case GestionPanier.GROUPE_SCOL:
			resas = this.getPlanningGroupe((ScolGroupeGrp) resRecord);
			resaClassifier = new ReservationClassifier();
			planningView.afficherPlanningEnseignement(resaClassifier.classifyReservations(resas));
			break;

		case GestionPanier.OBJET:
			resas = this.getPlanningObjet((ResaObjet) resRecord);
			planningView.afficherPlanningGenerique(resas, true);
			break;

		}
	}

	/**
	 * renvoie les planning dans le format fixe pour traitement par l'algo de tri -ReservationClassifier- pour un parcours donnee, un
	 * semestre et une annee scolaire. On prends les AP ratachees au parcours et somme leurs plannings.
	 */
	public NSArray getPlanningDiplome(MaquetteParcours parcours, Boolean isParcoursCommun, MaquetteSemestre semestre, Boolean isAutreSemestre,
			Number anneeScolaire) {
		return getPlanningDiplome(parcours, isParcoursCommun, semestre, isAutreSemestre, anneeScolaire, startDate, endDate);
	}

	public NSArray getPlanningDiplome(MaquetteParcours parcours, Boolean isParcoursCommun, MaquetteSemestre semestre, Boolean isAutreSemestre,
			Number anneeScolaire, NSTimestamp debut, NSTimestamp fin) {

		if (parcours == null || semestre == null) {
			return new NSArray();
		}
		NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>(2);
		andQuals.addObject(new EOKeyValueQualifier(MaquetteRepartitionSem.PARCOURS_KEY, EOKeyValueQualifier.QualifierOperatorEqual, parcours));
		andQuals.addObject(new EOKeyValueQualifier(MaquetteRepartitionSem.SEMESTRE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, semestre));
		MaquetteRepartitionSem mrs = MaquetteRepartitionSem.fetchMaquetteRepartitionSem(eContext, new EOAndQualifier(andQuals));
		if (mrs == null) {
			return new NSArray();
		}
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPlanningParcoursSemestre", new Class[] {
				Integer.class, NSTimestamp.class, NSTimestamp.class, Boolean.class, Boolean.class }, new Object[] { mrs.mrsemKey(), debut, fin,
				isParcoursCommun, isAutreSemestre }, false);
	}

	public NSArray<NSDictionary<String, Object>> getPlanningIndividu(IndividuUlr individu) {
		return getPlanningIndividu(individu, startDate, endDate);
	}

	/** renvoie le planning complet d'un individu : Enseignements - missions - agenda etc */
	public NSArray<NSDictionary<String, Object>> getPlanningIndividu(IndividuUlr individu, NSTimestamp debut, NSTimestamp fin) {
		EOGlobalID gidIndividu = eContext.globalIDForObject(individu);
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray<NSDictionary<String, Object>>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session",
				"clientSideRequestPlanningIndividu", new Class[] { NSTimestamp.class, NSTimestamp.class, EOGlobalID.class, Integer.class },
				new Object[] { debut, fin, gidIndividu, new Integer(0) }, false);
	}

	public NSArray<NSDictionary<String, Object>> getPlanningIndividu(IndividuUlr individu, Integer rang) {
		EOGlobalID gidIndividu = eContext.globalIDForObject(individu);
		// System.out.println("getPlanningIndividu " + startDate + " - " + endDate);
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray<NSDictionary<String, Object>>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session",
				"clientSideRequestPlanningIndividu", new Class[] { NSTimestamp.class, NSTimestamp.class, EOGlobalID.class, Integer.class },
				new Object[] { startDate, endDate, gidIndividu, rang }, false);
	}

	public NSArray getPlanningSalle(Salles uneSalle, Integer rang) {
		EOGlobalID gidSalle = eContext.globalIDForObject(uneSalle);
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPlanningSalle", new Class[] {
				NSTimestamp.class, NSTimestamp.class, EOGlobalID.class, Integer.class, Boolean.class }, new Object[] { startDate, endDate, gidSalle,
				rang, Boolean.TRUE }, false);

	}

	public NSArray getPlanningSalle(Salles uneSalle, NSTimestamp debut, NSTimestamp fin) {
		EOGlobalID gidSalle = eContext.globalIDForObject(uneSalle);
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPlanningSalle", new Class[] {
				NSTimestamp.class, NSTimestamp.class, EOGlobalID.class, Integer.class, Boolean.class }, new Object[] { debut, fin, gidSalle,
				new Integer(0), Boolean.FALSE }, false);

	}

	public NSArray<NSArray<NSDictionary<String, Object>>> getPlans() {
		return plans;
	}

	public void setPlans(NSMutableArray<NSArray<NSDictionary<String, Object>>> plans) {
		this.plans = plans;
	}

	/** getPlanningObjet */
	public NSArray getPlanningObjet(ResaObjet objet) {
		return getPlanningObjet(objet, startDate, endDate);
	}

	/** getPlanningObjet */
	public NSArray getPlanningObjet(ResaObjet objet, NSTimestamp debut, NSTimestamp fin) {

		EOGlobalID gidObjet = eContext.globalIDForObject(objet);
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPlanningObjet", new Class[] {
				NSTimestamp.class, NSTimestamp.class, EOGlobalID.class }, new Object[] { debut, fin, gidObjet }, false);
	}

	public NSArray getPlanningObjet(ResaObjet objet, Integer rang) {

		EOGlobalID gidObjet = eContext.globalIDForObject(objet);
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPlanningObjet", new Class[] {
				NSTimestamp.class, NSTimestamp.class, EOGlobalID.class, Integer.class }, new Object[] { startDate, endDate, gidObjet, rang }, false);
	}

	public NSArray getPlanningGroupe(ScolGroupeGrp groupe) {
		return getPlanningGroupe(groupe, startDate, endDate);
	}

	public NSArray getPlanningGroupe(ScolGroupeGrp groupe, NSTimestamp debut, NSTimestamp fin) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		return (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPlanningGroupe", new Class[] {
				Integer.class, NSTimestamp.class, NSTimestamp.class }, new Object[] { groupe.ggrpKey(), debut, fin }, false);

	}

	/** renvoie les vacances sous forme affichable pour la formationSpecialisation passee en parametre */
	// public NSArray vacancesFormation(FormationSpecialisation formation, NSTimestamp debut, NSTimestamp fin) {
	//
	// CalendarHandler calendarHandler = new CalendarHandler();
	//
	// EOQualifier qualVac = DBHandler.getSimpleQualifier(VacancesScolaires.FORMATION_HABILITATION_KEY + "."
	// + FormationHabilitation.FORMATION_SPECIALISATION_KEY, formation);
	// EOQualifier qualDat = EOQualifier.qualifierWithQualifierFormat(VacancesScolaires.VS_DATE_FIN_KEY + " => %@ and "
	// + VacancesScolaires.VS_DATE_DEBUT_KEY + " =< %@", new NSArray(new Object[] { debut, fin }));
	//
	// EOQualifier finalQual = new EOAndQualifier(new NSArray(new Object[] { qualVac, qualDat }));
	//
	// NSArray vacances = DBHandler.fetchData(eContext, VacancesScolaires.ENTITY_NAME, finalQual);
	//
	// NSMutableArray plans = new NSMutableArray();
	//
	// NSTimestamp vDeb, vFin;
	// for (int i = 0; i < vacances.count(); i++) {
	// VacancesScolaires currentVac = (VacancesScolaires) vacances.objectAtIndex(i);
	// vDeb = currentVac.vsDateDebut();
	// vFin = currentVac.vsDateFin();
	//
	// NSArray slices = TimeHandler.slicePeriod(vDeb, vFin);
	// for (int j = 0; j < slices.count(); j += 2) {
	// vDeb = (NSTimestamp) slices.objectAtIndex(j);
	// vFin = (NSTimestamp) slices.objectAtIndex(j + 1);
	//
	// if ((vFin.before(fin) || vFin.equals(fin)) && (vDeb.after(debut) || vDeb.equals(debut))) {
	// // preparation du creneau
	// NSMutableDictionary dRes = new NSMutableDictionary();
	// dRes.takeValueForKey(vDeb, "debut");
	// dRes.takeValueForKey(vFin, "fin");
	// Integer jour = new Integer(calendarHandler.getDay(vDeb));
	// dRes.takeValueForKey(jour, "jour");
	// dRes.takeValueForKey("J", "ccolor");
	// dRes.takeValueForKey(new NSArray("vacances"), "texte");
	// dRes.takeValueForKey(new Integer(1), "resa");
	// plans.addObject(dRes);
	// }
	// }
	// }
	// return plans;
	// }

	// pour chaque type de résa on fixe une couleur d'affichage différente.
	public static Color reservationColor(String code) {

		if (code == null) {
			return Color.WHITE;
		}

		if (code.startsWith("0x")) {
			return Color.decode(code);
		}

		// autre
		if (code.equals("z")) {
			return new Color(0xFF, 0xFF, 0xFF, 0x80);
		}

		// blocage
		if (code.equals("b")) {
			return new Color(0xBB, 0x22, 0x00, 0x80);
		}

		// Horaires de travail (Appli Conges)
		if (code.equals("H")) {
			return new Color(0x1E, 0x90, 0xFF, 0x09);
		}

		// Les Absences (Appli Conges)
		if (code.equals("C")) {
			return new Color(0xFF, 0xD7, 0x00, 0x80); //
		}

		// creneaux libres
		if (code.equals("L")) {
			return new Color(0x98, 0xFB, 0x98, 0x80);
		}

		// Enseignement Salles
		if (code.equals("CM")) {
			return new Color(0x00, 0xFF, 0xFF, 0x80);
		}

		if (code.equals("TD")) {
			return new Color(0x00, 0x64, 0x00, 0x80); // vers foncé
		}

		if (code.equals("TP")) {
			return new Color(0xFF, 0xD7, 0x00, 0x80); // jaune
		}

		if (code.equals("SEM")) {
			return new Color(0x7F, 0xFF, 0x9F, 0x80); // orange clair
		}

		if (code.equals("E")) {
			return new Color(0xFF, 0x00, 0x00, 0x80); // rouge
		}

		// Non enseignement Salles
		if (code.equals("f")) {
			return new Color(0x8B, 0x00, 0x00, 0x80); // rouge foncé
		}

		if (code.equals("n")) {
			return new Color(0x0B, 0x00, 0x8B, 0x80); // magenta
		}

		if (code.equals("s")) {
			return new Color(0x00, 0x8C, 0x00, 0x80); // orange
		}
		if (code.equals("r")) {
			return new Color(0xFF, 0x8C, 0x00, 0x80); // orange
		}
		if (code.equals("p")) {
			return new Color(0xFF, 0x00, 0x00, 0x80); // orange
		}

		if (code.equals("t")) {
			return new Color(0xD2, 0x69, 0x1E, 0x80); // orange foncé
		}

		// Indispo Enseignant
		if (code.equals("q")) {
			return new Color(0.133f, 0.58f, 0.612f, 0.5f);
		}

		// Resas
		if ((code.equals("R")) || (code.equals("*"))) {
			return new Color(0xFF, 0xD7, 0x00, 0x80);
		}

		// AGENDA personnel
		if (code.equals("P")) {
			return new Color(0xB0, 0xC4, 0xDE, 0x80);
		}

		// Horaires (I), Absences EDT + Missions (A), Jours feries (J)
		if (code.equals("A")) {
			return new Color(0xFF, 0xD7, 0x00, 0x80);
		}

		if (code.equals("I")) {
			return new Color(1.0f, 0.686f, 0.662f, 0.5f);
		}

		if (code.equals("J")) {
			return new Color(0.662f, 0.662f, 1.0f, 0.5f);
		}

		// Demande de travaux
		if (code.equals("DT")) {
			return new Color(0xA4, 0xBF, 0x9B, 0x80);
		}

		return new Color(0xFF, 0xFF, 0xFF);
	}

}
