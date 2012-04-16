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

import org.cocktail.superplan.server.metier.ContrainteHeure;
import org.cocktail.superplan.server.metier.ContrainteJour;
import org.cocktail.superplan.server.metier.ContrainteSemaine;
import org.cocktail.superplan.server.metier.ExamenEntete;
import org.cocktail.superplan.server.metier.FournisUlr;
import org.cocktail.superplan.server.metier.IndividuUlr;
import org.cocktail.superplan.server.metier.MaquetteAp;
import org.cocktail.superplan.server.metier.MaquetteEc;
import org.cocktail.superplan.server.metier.MaquetteRepartitionAp;
import org.cocktail.superplan.server.metier.MaquetteSemestre;
import org.cocktail.superplan.server.metier.Mission;
import org.cocktail.superplan.server.metier.Occupant;
import org.cocktail.superplan.server.metier.Periodicite;
import org.cocktail.superplan.server.metier.ResaExamen;
import org.cocktail.superplan.server.metier.ResaSalles;
import org.cocktail.superplan.server.metier.Reservation;
import org.cocktail.superplan.server.metier.ReservationAp;
import org.cocktail.superplan.server.metier.ReservationSemestre;
import org.cocktail.superplan.server.metier.Salles;
import org.cocktail.superplan.server.metier.ScolGroupeGrp;
import org.cocktail.superplan.server.metier.ScolGroupeInclusion;
import org.cocktail.superplan.server.metier.ScolGroupeObjet;
import org.cocktail.superplan.server.metier.ScolInscriptionAp;
import org.cocktail.superplan.server.metier.ScolInscriptionGrp;
import org.cocktail.superplan.server.metier.TitreMission;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.eof.ERXQ;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.Log;

public class Verification {

	private EOEditingContext eContext;

	public Verification(EOEditingContext eContext) {
		this.eContext = eContext;
	}

	public NSArray<NSTimestamp> getNonDisponibliteExamen(NSArray<NSTimestamp> periodicites, ExamenEntete examenEntete) throws Exception {
		MaquetteEc ec = examenEntete.ec();
		NSArray<MaquetteAp> aps = (NSArray<MaquetteAp>) ec.valueForKeyPath(MaquetteEc.MAQUETTE_REPARTITION_APS_KEY + "."
				+ MaquetteRepartitionAp.MAQUETTE_AP_KEY);
		NSMutableArray<ScolGroupeObjet> totalGroupes = new NSMutableArray<ScolGroupeObjet>();
		for (int ia = 0; ia < aps.count(); ia++) {
			MaquetteAp currentAp = aps.objectAtIndex(ia);
			totalGroupes.addObjectsFromArray(currentAp.scolGroupeObjets());
		}
		NSArray<NSTimestamp> nonDispo = new NSArray<NSTimestamp>();
		for (int i = 0; i < totalGroupes.count(); i++) {
			ScolGroupeObjet grpObj = totalGroupes.objectAtIndex(i);
			nonDispo = (NSArray<NSTimestamp>) getNonDisponibiliteGroupe(null, periodicites, null, grpObj.scolGroupeGrp(), false,
					((Application) Application.application()).isAppControleApsOccupation(),
					((Application) Application.application()).isAppControleApsNonObligatoiresOccupation(),
					((Application) Application.application()).config().booleanForKey("APP_CONTROLE_GROUPES_OCCUPATION"), true).objectAtIndex(1);
			if (nonDispo.count() > 0) {
				break;
			}
		}
		return nonDispo;
	}

	/** retourne les enfants du groupe */
	public NSArray<ScolGroupeGrp> filsPourGroupe(ScolGroupeGrp groupe) {

		NSArray<ScolGroupeGrp> descendants = new NSArray<ScolGroupeGrp>();
		NSArray<ScolGroupeInclusion> fils = groupe.inclusFils();

		for (int i = 0; i < fils.count(); i++) {
			ScolGroupeInclusion inclusion = fils.objectAtIndex(i);
			ScolGroupeGrp unFils = inclusion.groupeFils();
			if (unFils != null) {
				descendants = descendants.arrayByAddingObject(unFils);
				descendants = descendants.arrayByAddingObjectsFromArray(filsPourGroupe(unFils));
			}
		}
		return descendants;
	}

	/** retourne les ancetres du groupe */
	public NSArray<ScolGroupeGrp> parentsPourGroupe(ScolGroupeGrp groupe) {

		NSArray<ScolGroupeGrp> ascendants = new NSArray<ScolGroupeGrp>();
		NSArray<ScolGroupeInclusion> peres = groupe.inclusPere();

		for (int i = 0; i < peres.count(); i++) {
			ScolGroupeInclusion inclusion = peres.objectAtIndex(i);
			ScolGroupeGrp unPere = inclusion.groupePere();
			if (unPere != null) {
				ascendants = ascendants.arrayByAddingObject(unPere);
				ascendants = ascendants.arrayByAddingObjectsFromArray(parentsPourGroupe(unPere));
			}
		}
		return ascendants;
	}

	private NSArray<NSTimestamp> getPeriodicitesMission(NSArray<NSTimestamp> periodTest, IndividuUlr individu) {
		NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
		if (individu != null) {
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(qualifierMissionFromPeriodicites(periodTest));
			quals.addObject(ERXQ.equals(Mission.FOURNIS_KEY + "." + FournisUlr.PERS_ID_KEY, individu.persId()));
			quals.addObject(ERXQ.notEquals(Mission.MIS_ETAT_KEY, "ANNULEE"));
			quals.addObject(ERXQ.notEquals(Mission.C_CORPS_KEY, "IEXT"));
			quals.addObject(ERXQ.equals(Mission.TITRE_MISSION_KEY + "." + TitreMission.TIT_CTRL_DATES_KEY, new Integer(1)));
			NSArray<Mission> resas = Mission.fetchMissions(eContext, ERXQ.and(quals), null);
			for (int i = 0; i < resas.count(); i++) {
				Mission currentMission = resas.objectAtIndex(i);
				retour.addObject(currentMission.misDebut());
				retour.addObject(currentMission.misFin());
			}
		}
		return retour;
	}

	public NSArray<NSTimestamp> verifSallePourModification(Reservation resa, NSArray<NSTimestamp> periodicites, Salles salle) throws Exception {
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY + "."
				+ ResaSalles.SALLE_KEY + " = %@", new NSArray<Salles>(salle)));
		if (resa != null) {
			quals.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.RESERVATION_KEY + " <> %@", new NSArray<Reservation>(resa)));
		}
		EOQualifier qualifierDates = qualifierFromPeriodicites(periodicites);
		if (qualifierDates != null) {
			quals.addObject(qualifierDates);
		}
		NSArray<Periodicite> periods = DBHandler.fetchData(eContext, Periodicite.ENTITY_NAME, new EOAndQualifier(quals));
		NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
		for (int i = 0; i < periods.count(); i++) {
			Periodicite per = periods.objectAtIndex(i);
			retour.addObject(per.dateDeb());
			retour.addObject(per.dateFin());
		}
		return retour;
	}

	public NSArray<NSArray<?>> getNonDisponibiliteAp(Reservation resa, NSArray<NSTimestamp> periodicites, MaquetteAp ap, boolean strictAp,
			boolean avecAps, boolean appControleApsObligatoires, boolean appControleApsNonObligatoires, boolean resasSansGroupesUniquement)
			throws Exception {

		if (!appControleApsObligatoires && !appControleApsNonObligatoires) {
			NSMutableArray<NSArray<?>> retour = new NSMutableArray<NSArray<?>>();
			retour.addObject(new NSArray<Integer>());
			retour.addObject(new NSArray<NSTimestamp>());
			return retour;
		}

		NSMutableArray<Periodicite> periods = new NSMutableArray<Periodicite>();
		EOQualifier qualifierDates = qualifierFromPeriodicites(periodicites);

		// APs...
		NSMutableArray<EOQualifier> qualifierApArray = new NSMutableArray<EOQualifier>();
		qualifierApArray.addObject(ERXQ.equals(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
				+ ReservationAp.MAQUETTE_AP_KEY, ap));
		if (resasSansGroupesUniquement) {
			qualifierApArray.addObject(ERXQ.isNull(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
					+ ReservationAp.SCOL_GROUPE_GRP_KEY));
		}
		if (resa != null) {
			qualifierApArray.addObject(ERXQ.notEquals(Periodicite.RESERVATION_KEY, resa));
		}
		qualifierApArray.addObject(qualifierDates);
		periods.addObjectsFromArray(Periodicite.fetchPeriodicites(eContext, ERXQ.and(qualifierApArray), null));

		if (!strictAp) {
			// examens...
			NSMutableArray<EOQualifier> qualifierExamenArray = new NSMutableArray<EOQualifier>();
			qualifierExamenArray.addObject(ERXQ.equals(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_EXAMENS_KEY + "."
					+ ResaExamen.EXAMEN_ENTETE_KEY + "." + ExamenEntete.EC_KEY + "." + MaquetteEc.MAQUETTE_REPARTITION_APS_KEY + "."
					+ MaquetteRepartitionAp.MAQUETTE_AP_KEY, ap));
			if (resasSansGroupesUniquement) {
				qualifierExamenArray.addObject(ERXQ.isNull(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
						+ ReservationAp.SCOL_GROUPE_GRP_KEY));
			}
			if (resa != null) {
				qualifierExamenArray.addObject(ERXQ.notEquals(Periodicite.RESERVATION_KEY, resa));
			}
			qualifierExamenArray.addObject(qualifierDates);
			periods.addObjectsFromArray(Periodicite.fetchPeriodicites(eContext, ERXQ.and(qualifierExamenArray), null));
		}

		NSMutableArray<Integer> aps = new NSMutableArray<Integer>();
		NSMutableArray<NSTimestamp> dates = new NSMutableArray<NSTimestamp>();
		for (int i = 0; i < periods.count(); i++) {
			aps.addObject(ap.mapKey());
			aps.addObject(ap.mapKey());
			Periodicite per = periods.objectAtIndex(i);
			dates.addObject(per.dateDeb());
			dates.addObject(per.dateFin());
		}

		Log log = new Log(false);
		if (!strictAp && (appControleApsObligatoires || appControleApsNonObligatoires)) {
			// TODO prendre en compte les examens des autres APs du parcours !!!!!!!!!!!!!!!!!
			// Autres APs du parcours...
			log.startOp("Verification... getMaquetteApsParcours...");
			NSArray<MaquetteAp> listeAp = ap.getMaquetteApsParcours(appControleApsObligatoires, appControleApsNonObligatoires);
			log.endOp("FIN Verification... getMaquetteApsParcours...");
			NSMutableArray<MaquetteAp> listeApAVerifier = new NSMutableArray<MaquetteAp>();
			// if (ap.mhcoCode().equalsIgnoreCase("CM")) {
			// // notre ap est un CM, on doit vérifier par rapport à tous les autres APs obligatoires du parcours
			// listeApAVerifier.addObjectsFromArray(listeAp);
			// }
			// else {
			// // notre ap n'est pas un CM, on doit vérifier par rapport à tous les autres APs CM obligatoires du parcours
			// Enumeration<MaquetteAp> enumListeAp = listeAp.objectEnumerator();
			// while (enumListeAp.hasMoreElements()) {
			// MaquetteAp lap = enumListeAp.nextElement();
			// if (lap.mhcoCode().equalsIgnoreCase("CM")) {
			// listeApAVerifier.addObject(lap);
			// }
			// }
			// }
			listeApAVerifier.addObjectsFromArray(listeAp);
			log.startOp("Verification... verifListeApPourModification...");
			NSArray<NSArray<?>> apsDates = verifListeApPourModification(resa, periodicites, listeApAVerifier, avecAps);
			log.endOp("FIN Verification... verifListeApPourModification...");
			aps.addObjectsFromArray((NSArray<Integer>) apsDates.objectAtIndex(0));
			dates.addObjectsFromArray((NSArray<NSTimestamp>) apsDates.objectAtIndex(1));
		}
		NSMutableArray<NSArray<?>> retour = new NSMutableArray<NSArray<?>>();
		retour.addObject(aps);
		retour.addObject(dates);
		return retour;
	}

	/** verifSemestrePourModification */
	public NSArray<NSTimestamp> verifSemestrePourModification(Reservation resa, NSArray<NSTimestamp> periodicites, MaquetteSemestre sem)
			throws Exception {
		NSArray<MaquetteAp> listeAp = sem.getListeAp();
		NSMutableArray<NSTimestamp> occupations = new NSMutableArray<NSTimestamp>();
		NSArray<NSTimestamp> data = (NSArray<NSTimestamp>) verifListeApPourModification(resa, periodicites, listeAp, true).objectAtIndex(1);
		occupations.addObjectsFromArray(data);

		EOQualifier qualifierSem = EOQualifier.qualifierWithQualifierFormat(Periodicite.RESERVATION_KEY + "."
				+ Reservation.RESERVATIONS_SEMESTRES_KEY + "." + ReservationSemestre.SEMESTRE_KEY + " = %@", new NSArray<MaquetteSemestre>(sem));

		if (resa != null) {
			qualifierSem = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualifierSem,
					EOQualifier.qualifierWithQualifierFormat(Periodicite.RESERVATION_KEY + " <> %@", new NSArray<Reservation>(resa)) }));

		}

		EOQualifier qualifierDates = qualifierFromPeriodicites(periodicites);
		EOAndQualifier qualifier = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualifierSem, qualifierDates }));

		EOFetchSpecification myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, qualifier, null);
		myFetch.setUsesDistinct(true);
		myFetch.setRefreshesRefetchedObjects(true);
		NSArray<Periodicite> periods = eContext.objectsWithFetchSpecification(myFetch);

		for (int i = 0; i < periods.count(); i++) {
			Periodicite per = periods.objectAtIndex(i);
			occupations.addObject(per.dateDeb());
			occupations.addObject(per.dateFin());
		}
		return occupations;
	}

	public NSArray<NSArray<?>> verifListeApPourModification(Reservation resa, NSArray<NSTimestamp> periodicites, NSArray<MaquetteAp> listeAp,
			boolean avecAps) throws Exception {

		NSMutableArray<NSArray<?>> retour = new NSMutableArray<NSArray<?>>();
		NSMutableArray<Integer> aps = new NSMutableArray<Integer>();
		NSMutableArray<NSTimestamp> dates = new NSMutableArray<NSTimestamp>();
		if (listeAp != null && listeAp.count() > 0) {
			NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>();
			andQuals.addObject(ERXQ.in(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.MAQUETTE_AP_KEY,
					listeAp));
			andQuals.addObject(qualifierFromPeriodicites(periodicites));
			if (resa != null) {
				andQuals.addObject(ERXQ.notEquals(Periodicite.RESERVATION_KEY, resa));
			}

			Log log = new Log(false);
			log.startOp("Verification... fetch...");
			EOFetchSpecification myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, ERXQ.and(andQuals), null);
			myFetch.setUsesDistinct(true);
			NSArray<Periodicite> periods = eContext.objectsWithFetchSpecification(myFetch);
			log.endOp("FIN Verification... fetch...");

			log.startOp("Verification... for...");
			for (int i = 0; i < periods.count(); i++) {
				Periodicite per = periods.objectAtIndex(i);
				dates.addObject(per.dateDeb());
				dates.addObject(per.dateFin());
				if (avecAps) {
					Reservation res = per.reservation();
					if (res != null) {
						ReservationAp reservationAp = (ReservationAp) res.reservationAp().lastObject();
						if (reservationAp != null) {
							aps.addObject(reservationAp.leMapKey());
							aps.addObject(reservationAp.leMapKey());
						}
					}
				}
			}
			log.endOp("FIN Verification... for...");
		}
		retour.addObject(aps);
		retour.addObject(dates);
		return retour;
	}

	/** verifExamenPourModification */
	public NSArray<NSTimestamp> verifExamenPourModification(Reservation resa, NSArray<NSTimestamp> periodicites, ExamenEntete examenEntete)
			throws Exception {

		MaquetteEc ec = examenEntete.ec();
		NSArray<MaquetteAp> aps = (NSArray<MaquetteAp>) ec.valueForKeyPath(MaquetteEc.MAQUETTE_REPARTITION_APS_KEY + "."
				+ MaquetteRepartitionAp.MAQUETTE_AP_KEY);
		// NSMutableArray<ScolGroupeObjet> totalGroupes = new NSMutableArray<ScolGroupeObjet>();
		// for (int ia = 0; ia < aps.count(); ia++) {
		// MaquetteAp currentAp = aps.objectAtIndex(ia);
		// totalGroupes.addObjectsFromArray(currentAp.scolGroupeObjets());
		// }
		// for (int i = 0; i < totalGroupes.count(); i++) {
		// ScolGroupeObjet grpObj = totalGroupes.objectAtIndex(i);
		// NSArray<NSTimestamp> nonDispo = getNonDisponibiliteGroupe(resa, periodicites, grpObj.scolGroupeGrp(),
		// ((Application) Application.application()).config().booleanForKey("APP_CONTROLE_GROUPES_OCCUPATION"));
		// if (nonDispo.count() > 0) {
		// return nonDispo;
		// }
		// }
		for (int ia = 0; ia < aps.count(); ia++) {
			MaquetteAp currentAp = aps.objectAtIndex(ia);
			NSArray<NSTimestamp> nonDispo = (NSArray<NSTimestamp>) getNonDisponibiliteAp(resa, periodicites, currentAp, true, true,
					((Application) Application.application()).isAppControleApsOccupation(),
					((Application) Application.application()).isAppControleApsNonObligatoiresOccupation(), false).objectAtIndex(1);
			if (nonDispo.count() > 0) {
				return nonDispo;
			}
		}

		return NSArray.EmptyArray;
	}

	public NSArray<NSArray<?>> getNonDisponibiliteGroupe(Reservation resa, NSArray<NSTimestamp> periodicites, MaquetteAp ap, ScolGroupeGrp groupe,
			boolean avecAps, boolean avecControleApsObligatoires, boolean avecControleApsNonObligatoires, boolean avecControleGroupes,
			boolean stopPremiereIndispo) throws Exception {

		if (!avecControleGroupes) {
			NSMutableArray<NSArray<?>> retour = new NSMutableArray<NSArray<?>>();
			retour.addObject(new NSArray<Integer>());
			retour.addObject(new NSArray<NSTimestamp>());
			return retour;
		}

		NSMutableArray<Periodicite> periods = new NSMutableArray<Periodicite>();

		NSMutableArray<ScolGroupeGrp> groupesATester = new NSMutableArray<ScolGroupeGrp>();
		groupesATester.addObject(groupe);
		groupesATester.addObjectsFromArray(groupe.groupesFils());
		groupesATester.addObjectsFromArray(parentsPourGroupe(groupe));

		boolean testerAp = true;

		for (int x = 0; x < groupesATester.count(); x++) {
			if (periods.count() > 0 && stopPremiereIndispo) {
				break;
			}
			ScolGroupeGrp leGroupe = groupesATester.objectAtIndex(x);
			NSArray<ScolGroupeObjet> objGroupes = leGroupe.scolGroupeObjet();

			// APs du groupe...
			// Le groupe lui-même ou un des APs auquel est rattaché le groupe, pour une résa sans groupe...
			// on cherche les resa des aps sans groupes...
			NSMutableArray<EOQualifier> temp = new NSMutableArray<EOQualifier>();
			for (int i = 0; i < objGroupes.count(); i++) {
				ScolGroupeObjet scolGroupeObjet = objGroupes.objectAtIndex(i);
				if (scolGroupeObjet.maquetteAp() == null) {
					continue;
				}
				EOQualifier q1 = ERXQ.equals(
						Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.MAQUETTE_AP_KEY,
						scolGroupeObjet.maquetteAp());
				EOQualifier q2 = ERXQ.equals(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.GGRP_KEY_KEY,
						NSKeyValueCoding.NullValue);
				temp.addObject(ERXQ.and(q1, q2));
			}
			// ...OU les resas du groupe
			temp.addObject(ERXQ.equals(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.SCOL_GROUPE_GRP_KEY,
					leGroupe));

			NSMutableArray<EOQualifier> sumQual = new NSMutableArray<EOQualifier>(ERXQ.or(temp));

			if (resa != null) {
				sumQual.addObject(ERXQ.notEquals(Periodicite.RESERVATION_KEY, resa));
			}
			sumQual.addObject(qualifierFromPeriodicites(periodicites));
			periods.addObjectsFromArray(Periodicite.fetchPeriodicites(eContext, ERXQ.and(sumQual), null));
			if (periods.count() > 0 && stopPremiereIndispo) {
				break;
			}

			// EXAMENS du groupe...
			sumQual = new NSMutableArray<EOQualifier>();
			sumQual.addObject(ERXQ.equals(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_EXAMENS_KEY + "." + ResaExamen.SCOL_GROUPE_GRP_KEY,
					leGroupe));
			if (resa != null) {
				sumQual.addObject(ERXQ.notEquals(Periodicite.RESERVATION_KEY, resa));
				sumQual.addObject(ERXQ.isNotNull(Periodicite.RESERVATION_KEY));
			}
			sumQual.addObject(qualifierFromPeriodicites(periodicites));
			periods.addObjectsFromArray(Periodicite.fetchPeriodicites(eContext, ERXQ.and(sumQual), null));
			if (periods.count() > 0 && stopPremiereIndispo) {
				break;
			}

			if (avecControleGroupes) {
				// ETUDIANTS DES GROUPES...
				// on recupere la liste des etudiants du groupe...
				NSArray<ScolInscriptionGrp> listeEtudiantsDuGroupe = leGroupe.scolInscriptionGrps();
				if (listeEtudiantsDuGroupe != null && listeEtudiantsDuGroupe.count() > 0) {
					testerAp = false;
					// NSArray<MaquetteAp> autresApsDuParcours = ap.getMaquetteApsParcours(true, false);
					// Pour chaque étudiant inscrit à ce groupe...
					for (int i = 0; i < listeEtudiantsDuGroupe.count(); i++) {
						if (periods.count() > 0 && stopPremiereIndispo) {
							break;
						}
						ScolInscriptionGrp scolInscriptionGrp = listeEtudiantsDuGroupe.objectAtIndex(i);
						// boolean isInscritAMinima = scolInscriptionGrp.scolInscriptionEtudiant().isInscritAMinima();

						// Les groupes auxquels il est affecté...
						// on retrouve la liste des groupes auxquels il est affecté pour vérifier chaque groupe...
						EOQualifier qual = ERXQ.equals(ScolGroupeGrp.SCOL_INSCRIPTION_GRPS_KEY + "."
								+ ScolInscriptionGrp.SCOL_INSCRIPTION_ETUDIANT_KEY, scolInscriptionGrp.scolInscriptionEtudiant());
						NSArray<ScolGroupeGrp> listeGroupesEtudiant = ScolGroupeGrp.fetchScolGroupeGrps(eContext, qual, null);
						if (listeGroupesEtudiant != null) {
							NSMutableArray<ScolGroupeGrp> listeGroupes = new NSMutableArray<ScolGroupeGrp>();
							// Pour chaque groupe de l'etudiant (sauf celui de depart), on verifie s'il est occupe...
							for (int j = 0; j < listeGroupesEtudiant.count(); j++) {
								ScolGroupeGrp scolGroupeGrp = listeGroupesEtudiant.objectAtIndex(j);
								if (scolGroupeGrp.equals(leGroupe) == false) {
									// TODO si l'étudiant est inscrit à minima pour cette année, on ne contrôle que si le scolGroupeGrp est
									// sur un AP qui est dans le même parcours que l'AP en cours de vérification
									// c'est à dire que si le groupe en est L1 trucmuche, le groupe en cours de vérif en L2 trucmuche
									// et que notre gazier est inscrit en L2 passage conditionnel et L1 (donc à minima),
									// on peut lui placer un créneau pour un groupe de L2 en même temps qu'un créneau d'un groupe de L1, à
									// lui de choisir lequel il veut suivre...
									// if (isInscritAMinima) {
									// Iterator<ScolGroupeObjet> it = scolGroupeGrp.scolGroupeObjetElps().iterator();
									// boolean doitControler = false;
									// while (it.hasNext() && !doitControler) {
									// if (autresApsDuParcours != null && autresApsDuParcours.contains(it.next().maquetteAp())) {
									// doitControler = true;
									// }
									// }
									// if (!doitControler) {
									// continue;
									// }
									// }
									listeGroupes.addObject(scolGroupeGrp);
								}
							}
							if (listeGroupes != null && listeGroupes.count() > 0) {
								NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>();
								EOQualifier q1 = ERXQ.in(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
										+ ReservationAp.SCOL_GROUPE_GRP_KEY, listeGroupes);
								EOQualifier q2 = ERXQ.in(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_EXAMENS_KEY + "."
										+ ResaExamen.SCOL_GROUPE_GRP_KEY, listeGroupes);
								andQuals.addObject(ERXQ.or(q1, q2));
								if (resa != null) {
									andQuals.addObject(ERXQ.notEquals(Periodicite.RESERVATION_KEY, resa));
								}
								andQuals.addObject(qualifierFromPeriodicites(periodicites));
								periods.addObjectsFromArray(Periodicite.fetchPeriodicites(eContext, ERXQ.and(andQuals), null));
								if (periods.count() > 0 && stopPremiereIndispo) {
									break;
								}
							}
						}
						// Les APs auxquels il est inscrit...
						NSArray<ScolInscriptionAp> scolInscriptionAps = scolInscriptionGrp.scolInscriptionEtudiant().scolInscriptionAps();
						if (scolInscriptionAps != null && scolInscriptionAps.count() > 0) {
							NSMutableArray<MaquetteAp> listeAp = new NSMutableArray<MaquetteAp>();
							for (int k = 0; k < scolInscriptionAps.count(); k++) {
								ScolInscriptionAp scolInscriptionAp = scolInscriptionAps.objectAtIndex(k);
								Integer imrapDispense = scolInscriptionAp.imrapDispense();
								// on limite aux imrapDispense 0 (Inscrit), 2 (Réorientation - Arrivée), 10 (A la Carte - Non
								// Comptabilisable), 11 (A la
								// Carte - Comptabilisable)
								if (imrapDispense != null
										&& (imrapDispense.intValue() == 0 || imrapDispense.intValue() == 2 || imrapDispense.intValue() == 10 || imrapDispense
												.intValue() == 11)) {
									listeAp.addObject(scolInscriptionAp.maquetteRepartitionAp().maquetteAp());
								}
							}
							if (listeAp != null && listeAp.count() > 0) {
								NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>();
								andQuals.addObject(ERXQ.in(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
										+ ReservationAp.MAQUETTE_AP_KEY, listeAp));
								andQuals.addObject(ERXQ.equals(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
										+ ReservationAp.GGRP_KEY_KEY, NSKeyValueCoding.NullValue));
								andQuals.addObject(qualifierFromPeriodicites(periodicites));
								if (resa != null) {
									andQuals.addObject(ERXQ.notEquals(Periodicite.RESERVATION_KEY, resa));
								}
								periods.addObjectsFromArray(Periodicite.fetchPeriodicites(eContext, ERXQ.and(andQuals), null));
								if (periods.count() > 0 && stopPremiereIndispo) {
									break;
								}
							}
						}
					}
				}
			}
		}

		NSMutableArray<NSTimestamp> dates = new NSMutableArray<NSTimestamp>();
		NSMutableArray<Integer> aps = new NSMutableArray<Integer>();
		for (int i = 0; i < periods.count(); i++) {
			Periodicite per = periods.objectAtIndex(i);
			dates.addObject(per.dateDeb());
			dates.addObject(per.dateFin());
			if (avecAps) {
				Reservation res = per.reservation();
				if (res != null) {
					ReservationAp reservationAp = (ReservationAp) res.reservationAp().lastObject();
					if (reservationAp != null) {
						aps.addObject(reservationAp.leMapKey());
						aps.addObject(reservationAp.leMapKey());
					}
				}
			}
		}

		// si tous les groupes étaient vides, on vérifie par rapport aux autres APs sans groupe du parcours/semestre...
		if (periods.count() == 0 || !stopPremiereIndispo) {
			if (testerAp && ap != null) {
				NSArray<NSArray<?>> a = getNonDisponibiliteAp(resa, periodicites, ap, false, avecAps, avecControleApsObligatoires,
						avecControleApsNonObligatoires, true);
				if (a != null) {
					aps.addObjectsFromArray((NSArray<Integer>) a.objectAtIndex(0));
					dates.addObjectsFromArray((NSArray<NSTimestamp>) a.objectAtIndex(1));
				}
			}
		}

		NSMutableArray<NSArray<?>> retour = new NSMutableArray<NSArray<?>>();
		retour.addObject(aps);
		retour.addObject(dates);
		return retour;
	}

	public NSArray<NSTimestamp> getNonDisponibliteIndividu(NSArray<NSTimestamp> periodicites, IndividuUlr individu, Reservation resa)
			throws Exception {
		if (periodicites == null || periodicites.count() == 0) {
			return NSArray.EmptyArray;
		}
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.addObject(ERXQ.equals(Periodicite.RESERVATION_KEY + "." + Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY, individu));
		quals.addObject(qualifierFromPeriodicites(periodicites));
		if (resa != null) {
			quals.addObject(ERXQ.notEquals(Periodicite.RESERVATION_KEY, resa));
		}
		NSArray<Periodicite> resas = Periodicite.fetchPeriodicites(eContext, ERXQ.and(quals), null);

		NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
		for (int i = 0; i < resas.count(); i++) {
			Periodicite period = resas.objectAtIndex(i);
			retour.addObject(period.dateDeb());
			retour.addObject(period.dateFin());
		}

		// les missions
		if (((Application) Application.application()).config().booleanForKey("APP_CONTROLE_MISSIONS")) {
			retour.addObjectsFromArray(this.getPeriodicitesMission(periodicites, individu));
		}

		// contraintes
		retour.addObjectsFromArray(getPeriodicitesContraintes(periodicites, individu));

		return retour;
	}

	private NSArray<NSTimestamp> getPeriodicitesContraintes(NSArray<NSTimestamp> periodTest, IndividuUlr individu) {
		try {
			NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
			EOQualifier qContraintes = ERXQ.equals(ContrainteHeure.CONTRAINTE_JOUR_KEY + "." + ContrainteJour.CONTRAINTE_SEMAINE_KEY + "."
					+ ContrainteSemaine.INDIVIDU_ULR_KEY, individu);

			NSMutableArray<EOQualifier> qDates = new NSMutableArray<EOQualifier>();
			for (int i = 0; i < periodTest.count(); i += 2) {
				NSTimestamp debut = periodTest.objectAtIndex(i);
				NSTimestamp fin = periodTest.objectAtIndex(i + 1);
				qDates.addObject(ERXQ.and(ERXQ.lessThan(ContrainteHeure.CTH_HEURE_DEBUT_KEY, fin),
						ERXQ.greaterThan(ContrainteHeure.CTH_HEURE_FIN_KEY, debut)));
			}
			EOQualifier qualifier = ERXQ.and(qContraintes, ERXQ.or(qDates));

			NSArray<ContrainteHeure> contraintes = ContrainteHeure.fetchContrainteHeures(eContext, qualifier, null);
			if (contraintes != null) {
				for (int i = 0; i < contraintes.count(); i++) {
					ContrainteHeure contrainte = contraintes.objectAtIndex(i);
					retour.addObject(contrainte.cthHeureDebut());
					retour.addObject(contrainte.cthHeureFin());
				}
			}
			return retour;
		}
		catch (Exception e) {
			return NSArray.EmptyArray;
		}
	}

	/** construit un qualifier a partir des dates dans le tableau (debut, fin) */
	private EOQualifier qualifierFromPeriodicites(NSArray<NSTimestamp> periodicites) {
		if (periodicites != null) {
			NSMutableArray<EOQualifier> qDates = new NSMutableArray<EOQualifier>();
			for (int i = 0; i < periodicites.count(); i += 2) {
				NSTimestamp debut = periodicites.objectAtIndex(i);
				NSTimestamp fin = periodicites.objectAtIndex(i + 1);
				qDates.addObject(ERXQ.and(ERXQ.lessThan(Periodicite.DATE_DEB_KEY, fin), ERXQ.greaterThan(Periodicite.DATE_FIN_KEY, debut)));
			}
			return ERXQ.or(qDates);
		}
		return null;
	}

	/** construit un qualifier pour la table mission */
	private EOQualifier qualifierMissionFromPeriodicites(NSArray<NSTimestamp> periodicites) {
		NSMutableArray<EOQualifier> qDates = new NSMutableArray<EOQualifier>();
		for (int i = 0; i < periodicites.count(); i += 2) {
			NSTimestamp debut = periodicites.objectAtIndex(i);
			NSTimestamp fin = periodicites.objectAtIndex(i + 1);
			qDates.addObject(ERXQ.and(ERXQ.lessThan(Mission.MIS_DEBUT_KEY, fin), ERXQ.greaterThan(Mission.MIS_FIN_KEY, debut)));
		}
		return ERXQ.or(qDates);
	}

}
