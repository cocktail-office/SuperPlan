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

import java.util.Enumeration;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.server.metier.ExamenEntete;
import org.cocktail.superplan.server.metier.FormationHabilitation;
import org.cocktail.superplan.server.metier.FormationSpecialisation;
import org.cocktail.superplan.server.metier.IndividuUlr;
import org.cocktail.superplan.server.metier.MaquetteAp;
import org.cocktail.superplan.server.metier.MaquetteEc;
import org.cocktail.superplan.server.metier.MaquetteParcours;
import org.cocktail.superplan.server.metier.MaquetteRepartitionAp;
import org.cocktail.superplan.server.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.server.metier.MaquetteSemestre;
import org.cocktail.superplan.server.metier.Occupant;
import org.cocktail.superplan.server.metier.Periodicite;
import org.cocktail.superplan.server.metier.ResaExamen;
import org.cocktail.superplan.server.metier.ResaSalles;
import org.cocktail.superplan.server.metier.Reservation;
import org.cocktail.superplan.server.metier.ReservationAp;
import org.cocktail.superplan.server.metier.ReservationSemestre;
import org.cocktail.superplan.server.metier.SalleSouhaitee;
import org.cocktail.superplan.server.metier.Salles;
import org.cocktail.superplan.server.metier.ScolGroupeGrp;
import org.cocktail.superplan.server.metier.ScolGroupeInclusion;
import org.cocktail.superplan.server.metier.ScolGroupeObjet;
import org.cocktail.superplan.server.metier.ScolGroupeObjetVdi;
import org.cocktail.superplan.server.metier.ScolInscriptionAp;
import org.cocktail.superplan.server.metier.ScolInscriptionEtudiant;
import org.cocktail.superplan.server.metier.ScolInscriptionExamen;
import org.cocktail.superplan.server.metier.ScolInscriptionGrp;
import org.cocktail.superplan.server.metier.ScolInscriptionSemestre;
import org.cocktail.superplan.server.metier.VMaquetteAp;
import org.cocktail.superplan.server.metier.VParcoursAp;
import org.cocktail.superplan.server.metier.VParcoursEc;
import org.cocktail.superplan.server.metier.VacancesScolaires;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.CalendarHandler;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;

public class PlanningFormationProxy {

	public static final int SEMESTRE_IMPAIR = 1;
	public static final int SEMESTRE_PAIR = 2;

	private EOEditingContext eContext;
	private Session session;

	public PlanningFormationProxy(Session session) {
		this.session = session;
		eContext = session.defaultEditingContext();
	}

	// Planning pour un semestre
	public NSArray<NSDictionary<String, Object>> getPlanningParcoursSemestre(Integer mrsemKey, NSTimestamp debut, NSTimestamp fin,
			boolean inclureParcoursCommun, boolean inclureAutreSemestre, boolean inclureVacances) {
		MaquetteRepartitionSem mrs = MaquetteRepartitionSem
				.fetchMaquetteRepartitionSem(eContext, MaquetteRepartitionSem.ENTITY_PRIMARY_KEY, mrsemKey);
		if (mrs == null) {
			return new NSArray<NSDictionary<String, Object>>();
		}

		NSMutableArray<NSDictionary<String, Object>> resultat = new NSMutableArray<NSDictionary<String, Object>>();

		// APs...
		NSArray<VMaquetteAp> listAp = getApsForParcoursSemestres(mrs.parcours(), inclureParcoursCommun, mrs.semestre(), inclureAutreSemestre,
				mrs.fannKey());
		NSArray<MaquetteSemestre> listSemestres = getSemestresForParcoursSemestres(mrs.parcours(), inclureParcoursCommun, mrs.semestre().msemOrdre(),
				inclureAutreSemestre, mrs.fannKey());
		resultat.addObjectsFromArray(getPlanningAps(debut, fin, listAp, listSemestres));

		// Examens...
		NSArray<MaquetteEc> listEc = getEcsForParcoursSemestres(mrs.parcours(), inclureParcoursCommun, mrs.semestre(), inclureAutreSemestre,
				mrs.fannKey());
		resultat.addObjectsFromArray(getPlanningExamens(debut, fin, listEc));

		// Vacances scolaires
		if (inclureVacances) {
			resultat.addObjectsFromArray(getPlanningVacancesScolaires(debut, fin, mrs));
		}

		return resultat.immutableClone();
	}

	/**
	 * Planning complet d'un étudiant (inscriptions + examens)
	 * 
	 * @param noIndividu
	 * @param debut
	 * @param fin
	 * @return
	 */
	public NSArray<NSDictionary<String, Object>> getPlanningEtudiant(Number noIndividu, NSTimestamp debut, NSTimestamp fin) {
		EOQualifier qual = new EOKeyValueQualifier(ScolInscriptionEtudiant.NO_INDIVIDU_KEY, EOKeyValueQualifier.QualifierOperatorEqual, noIndividu);
		EOFetchSpecification myFetch = new EOFetchSpecification(ScolInscriptionEtudiant.ENTITY_NAME, qual, null);
		myFetch.setPrefetchingRelationshipKeyPaths(new NSArray<String>(new String[] { ScolInscriptionEtudiant.SCOL_INSCRIPTION_APS_KEY,
				ScolInscriptionEtudiant.SCOL_INSCRIPTION_APS_KEY + "." + ScolInscriptionAp.MAQUETTE_REPARTITION_AP_KEY,
				ScolInscriptionEtudiant.SCOL_INSCRIPTION_GRPS_KEY }));
		myFetch.setRefreshesRefetchedObjects(true);
		myFetch.setUsesDistinct(true);
		NSArray<ScolInscriptionEtudiant> inscriptions = eContext.objectsWithFetchSpecification(myFetch);
		if (inscriptions == null || inscriptions.count() == 0) {
			return null;
		}

		EOQualifier datesQual = null;
		if (debut != null || fin != null) {
			NSMutableArray<EOQualifier> args = new NSMutableArray<EOQualifier>();
			if (debut != null) {
				args.addObject(new EOKeyValueQualifier(Periodicite.DATE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThanOrEqualTo, debut));
			}
			if (fin != null) {
				args.addObject(new EOKeyValueQualifier(Periodicite.DATE_DEB_KEY, EOKeyValueQualifier.QualifierOperatorLessThanOrEqualTo, fin));
			}
			datesQual = new EOAndQualifier(args);
		}

		NSMutableArray<EOSortOrdering> sort = new NSMutableArray<EOSortOrdering>();
		sort.addObject(EOSortOrdering.sortOrderingWithKey(Periodicite.DATE_DEB_KEY, EOSortOrdering.CompareAscending));
		sort.addObject(EOSortOrdering.sortOrderingWithKey(Periodicite.DATE_FIN_KEY, EOSortOrdering.CompareAscending));

		NSMutableArray<NSDictionary<String, Object>> planning = new NSMutableArray<NSDictionary<String, Object>>();
		NSMutableArray<Periodicite> tmp = new NSMutableArray<Periodicite>();
		for (int i = 0; i < inscriptions.count(); i++) {
			ScolInscriptionEtudiant uneInscription = inscriptions.objectAtIndex(i);
			NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>();

			// APs...
			NSArray<ScolInscriptionAp> scolInscriptionAps = uneInscription.scolInscriptionAps();
			if (scolInscriptionAps != null && scolInscriptionAps.count() > 0) {
				for (int k = 0; k < scolInscriptionAps.count(); k++) {
					ScolInscriptionAp scolInscriptionAp = scolInscriptionAps.objectAtIndex(k);
					Integer imrapDispense = scolInscriptionAp.imrapDispense();
					// on limite aux imrapDispense 0 (Inscrit), 2 (Réorientation - Arrivée), 10 (A la Carte - Non Comptabilisable), 11 (A la
					// Carte - Comptabilisable)
					if (imrapDispense != null
							&& (imrapDispense.intValue() == 0 || imrapDispense.intValue() == 2 || imrapDispense.intValue() == 10 || imrapDispense
									.intValue() == 11)) {
						MaquetteAp maquetteAp = scolInscriptionAp.maquetteRepartitionAp().maquetteAp();
						orQuals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
								+ ReservationAp.MAQUETTE_AP_KEY, EOKeyValueQualifier.QualifierOperatorEqual, maquetteAp));
					}
				}
			}

			if (orQuals.count() > 0) {
				qual = new EOOrQualifier(orQuals);
				if (datesQual != null) {
					qual = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qual, datesQual }));
				}

				myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, qual, sort);
				myFetch.setPrefetchingRelationshipKeyPaths(new NSArray<String>(new String[] { Periodicite.RESERVATION_KEY,
						Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY,
						Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.SCOL_GROUPE_GRP_KEY,
						Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY,
						Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY }));
				myFetch.setRefreshesRefetchedObjects(true);
				myFetch.setUsesDistinct(true);
				NSArray<Periodicite> resas = eContext.objectsWithFetchSpecification(myFetch);

				// si l'étudiant n'est affecté dans aucun groupe, on ne filtre pas...
				// pour récupérer ses groupes on récupère tous les groupes en tenant compte des inclusions...
				NSArray<Number> ggrpKeys = (NSArray<Number>) uneInscription.scolInscriptionGrps().valueForKey(ScolInscriptionGrp.GGRP_KEY_KEY);
				ggrpKeys = groupesAvecInclusion(ggrpKeys);
				if (ggrpKeys == null || ggrpKeys.count() == 0) {
					tmp.addObjectsFromArray(resas);
				}
				else {
					// sinon on n'ajoute que les résas qui correspondent soit à aucun groupe, soit à un groupe auquel appartient
					// l'étudiant...
					for (int j = 0; j < resas.count(); j++) {
						Periodicite periodicite = resas.objectAtIndex(j);
						NSArray<ReservationAp> tosReservationAp = periodicite.reservation().reservationAp();
						boolean shouldAdd = false;
						for (int k = 0; k < tosReservationAp.count(); k++) {
							Number ggrpKey = tosReservationAp.objectAtIndex(k).ggrpKey();
							if (ggrpKey == null || ggrpKeys.containsObject(ggrpKey)) {
								shouldAdd = true;
								break;
							}
							// ajout à tester...
							ScolGroupeGrp groupe = tosReservationAp.objectAtIndex(k).scolGroupeGrp();
							if (groupe.scolInscriptionGrps() == null || groupe.scolInscriptionGrps().count() == 0) {
								NSArray<ScolGroupeGrp> a = groupe.groupesFilsAll();
								if (a == null || a.count() == 0) {
									shouldAdd = true;
									break;
								}
								boolean tousVides = true;
								for (int x = 0; x < a.count(); x++) {
									ScolGroupeGrp grp = a.objectAtIndex(x);
									if (grp.scolInscriptionGrps() != null && grp.scolInscriptionGrps().count() > 0) {
										tousVides = false;
										break;
									}
								}
								if (tousVides) {
									shouldAdd = true;
									break;
								}
							}
							// fin ajout à tester...
						}
						if (shouldAdd) {
							tmp.addObject(periodicite);
						}
					}
				}
			}

			// Examens...
			orQuals.removeAllObjects();
			NSArray<ScolInscriptionExamen> scolInscriptionExamens = uneInscription.scolInscriptionExamens();
			if (scolInscriptionExamens != null && scolInscriptionExamens.count() > 0) {
				for (int k = 0; k < scolInscriptionExamens.count(); k++) {
					ScolInscriptionExamen scolInscriptionExamen = scolInscriptionExamens.objectAtIndex(k);
					String ieentValidite = scolInscriptionExamen.ieentValidite();
					if (ieentValidite != null && ieentValidite.equals("O")) {
						orQuals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_EXAMENS_KEY + "."
								+ ResaExamen.EXAMEN_ENTETE_KEY + "." + ExamenEntete.EENT_KEY_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
								scolInscriptionExamen.eentKey()));
					}
				}
			}
			if (orQuals.count() > 0) {
				qual = new EOOrQualifier(orQuals);
				if (datesQual != null) {
					qual = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qual, datesQual }));
				}
				myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, qual, sort);
				myFetch.setPrefetchingRelationshipKeyPaths(new NSArray<String>(new String[] { Periodicite.RESERVATION_KEY,
						Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY,
						Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.SCOL_GROUPE_GRP_KEY,
						Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY,
						Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY }));
				myFetch.setRefreshesRefetchedObjects(true);
				myFetch.setUsesDistinct(true);
				tmp.addObjectsFromArray(eContext.objectsWithFetchSpecification(myFetch));
			}

			planning.addObjectsFromArray(getPlanningMisEnForme(tmp, true));

			// Semestres
			if (uneInscription.scolInscriptionSemestres() != null) {
				for (int x = 0; x < uneInscription.scolInscriptionSemestres().count(); x++) {
					ScolInscriptionSemestre isem = (ScolInscriptionSemestre) uneInscription.scolInscriptionSemestres().objectAtIndex(x);
					NSArray<MaquetteSemestre> listSemestres = getSemestresForParcoursSemestres(isem.maquetteRepartitionSem().parcours(), true, isem
							.maquetteRepartitionSem().semestre().msemOrdre(), false, uneInscription.fannKey());
					planning.addObjectsFromArray(getPlanningSemestres(debut, fin, listSemestres, null));
				}
			}
		}
		return planning.immutableClone();
	}

	public NSArray<NSDictionary<String, Object>> getPlanningGroupe(Integer ggrpKey, NSTimestamp debut, NSTimestamp fin) {
		try {
			NSMutableArray<NSDictionary<String, Object>> planning = new NSMutableArray<NSDictionary<String, Object>>();
			NSMutableArray<Periodicite> periods = new NSMutableArray<Periodicite>();

			boolean appendComments = true;
			if (session != null && session.isLazyMode() == false) {
				appendComments = (((Number) session.prefUser("commentScol")).intValue() == 1);
			}
			Verification verification = new Verification(eContext);
			ScolGroupeGrp groupe = ScolGroupeGrp.fetchRequiredScolGroupeGrp(eContext, ScolGroupeGrp.GGRP_KEY_KEY, ggrpKey);
			NSArray<ScolGroupeGrp> groupes = verification.parentsPourGroupe(groupe);
			NSArray<ScolGroupeGrp> fils = verification.filsPourGroupe(groupe);
			groupes = groupes.arrayByAddingObjectsFromArray(fils);

			periods.addObjectsFromArray(getPeriodicitesGroupe(debut, fin, groupe));
			for (int iGr = 0; iGr < groupes.count(); iGr++) {
				periods.addObjectsFromArray(getPeriodicitesGroupe(debut, fin, groupes.objectAtIndex(iGr)));
			}
			periods = DBHandler.retirerMultiples(periods);
			for (int i = 0; i < periods.count(); i++) {
				Periodicite period = periods.objectAtIndex(i);
				Reservation resa = period.reservation();

				StringBuffer strOccupants = new StringBuffer("");
				StringBuffer sApGrp = new StringBuffer("");

				NSArray<Occupant> occupants = resa.occupants();
				int nbocc = occupants.count();
				for (int io = 0; io < nbocc; io++) {
					IndividuUlr ioccupant = occupants.objectAtIndex(io).individu();
					strOccupants.append(ioccupant.prenom().substring(0, 1) + "." + StringCtrl.capitalizeWords(ioccupant.nomUsuel()));
					if (io > 0 && io < nbocc - 1) {
						strOccupants.append(",");
					}
				}

				// Enseignements : AP
				NSArray<ReservationAp> resaAps = DBHandler.retirerMultiples(resa.reservationAp());
				String colorAp = null;

				for (int iResaAp = 0; iResaAp < resaAps.count(); iResaAp++) {
					if (iResaAp > 0) {
						sApGrp.append(" ");
					}
					ReservationAp currentReservAp = resaAps.objectAtIndex(iResaAp);
					VMaquetteAp ap = currentReservAp.vMaquetteAp();
					ScolGroupeGrp grp = currentReservAp.scolGroupeGrp();
					if (grp != null) {
						if (grp.equals(groupe) || groupes.contains(grp)) {
							if (ap != null) {
								sApGrp.append(ap.mapLibelle());
								sApGrp.append(" - ");
							}
							if (grp != null) {
								sApGrp.append(grp.ggrpCode());
							}
						}
					}
					else {
						if (ap != null) {
							NSArray<ScolGroupeGrp> tousLesGrp = (NSArray<ScolGroupeGrp>) ap.valueForKeyPath(VMaquetteAp.SCOL_GROUPE_OBJETS_KEY + "."
									+ ScolGroupeObjet.SCOL_GROUPE_GRP_KEY);
							for (int ig = 0; ig < tousLesGrp.count(); ig++) {
								ScolGroupeGrp currentGrp = tousLesGrp.objectAtIndex(ig);
								if (groupe.equals(currentGrp) || groupes.contains(currentGrp)) {
									sApGrp.append(ap.mapLibelle());
									sApGrp.append(" - ");
									sApGrp.append(groupe.ggrpCode());
									break;
								}
							}
						}
					}

					// UP : gestion des couleurs
					if (session != null && session.isLazyMode() == false && session.colorEc) {
						if (ap != null && ap.colorCode() != null) {
							colorAp = ap.colorCode();
						}
					}
				}

				// Examens : ExamenEntete
				NSArray<ResaExamen> resaExamens = resa.resaExamens();
				nbocc = resaExamens.count();
				for (int iex = 0; iex < nbocc; iex++) {
					if (iex == 0) {
						sApGrp.append("Examen : ");
					}
					ResaExamen currentResaEx = resaExamens.objectAtIndex(iex);
					sApGrp.append(StringCtrl.capitalizeWords(currentResaEx.examenEntete().eentLibelle()));

					if (currentResaEx.scolGroupeGrp() != null) {
						sApGrp.append("-");
						sApGrp.append(currentResaEx.scolGroupeGrp().ggrpCode());
					}

					if (iex < nbocc - 1 && nbocc > 1) {
						sApGrp.append(", ");
					}
				}

				NSMutableDictionary<String, Object> dRes = new NSMutableDictionary<String, Object>();
				dRes.takeValueForKey(eContext.globalIDForObject(resa), "reservation");
				NSTimestamp debutRes = period.dateDeb();
				dRes.takeValueForKey(debutRes, "debut");
				dRes.takeValueForKey(period.dateFin(), "fin");

				Integer jour = new Integer(new CalendarHandler().getDay(debutRes));
				dRes.takeValueForKey(jour, "jour");
				dRes.takeValueForKey(new Integer(1), "resa");

				if (session != null && session.isLazyMode() == false && session.colorEc && colorAp != null) {
					dRes.takeValueForKey(colorAp, "ccolor");
				}
				else {
					dRes.takeValueForKey(resa.tlocCode(), "ccolor");
				}

				StringBuffer strSal = new StringBuffer();

				NSArray<ResaSalles> tmpSalle = resa.resaSalles();
				nbocc = tmpSalle.count();
				for (int j = 0; j < nbocc; j++) {
					Salles sal = (tmpSalle.objectAtIndex(j)).salle();
					strSal.append(StringCtrl.capitalizeWords(sal.cLocal()) + " : " + StringCtrl.capitalizeWords(sal.salPorte()));

					if (j > 0 && j < nbocc - 1) {
						strSal.append(",");
					}
				}

				NSArray<SalleSouhaitee> tmpSalleSouhaitee = resa.sallesSouhaitees();
				nbocc = tmpSalleSouhaitee.count();
				for (int j = 0; j < nbocc; j++) {
					Salles sal = (tmpSalleSouhaitee.objectAtIndex(0)).salle();
					strSal.append(sal.cLocal() + " : " + sal.salPorte());

					if (j > 0 && j < nbocc - 1) {
						strSal.append(",");
					}
				}

				NSMutableArray<String> texteResa = new NSMutableArray<String>();
				if (session != null && session.isLazyMode() == false && appendComments && resa.resaCommentaire() != null) {
					sApGrp.append(" - ").append(resa.resaCommentaire());
				}

				texteResa.addObject(sApGrp.toString());
				texteResa.addObject(strOccupants.toString());
				if (strSal != null) {
					texteResa.addObject(strSal.toString());
				}
				dRes.takeValueForKey(texteResa, "texte");
				dRes.takeValueForKey("Edt-Ens", "typeTemps");
				dRes.takeValueForKey(new Integer(1), "deplace");
				planning.addObject(dRes);
			}
			return planning;

		}
		catch (Throwable e) {
			e.printStackTrace();
			return new NSArray<NSDictionary<String, Object>>();
		}
	}

	/**
	 * @param ggrpKeys
	 * @return un tableau des ggrpKey passés en paramètres + les ggrpKey dans lesquels ils sont inclus, en récursif, attention aux modifs !
	 */
	private NSArray<Number> groupesAvecInclusion(NSArray<Number> ggrpKeys) {
		if (ggrpKeys == null || ggrpKeys.count() == 0) {
			return new NSArray<Number>();
		}
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>(ggrpKeys.count());
		for (int i = 0; i < ggrpKeys.count(); i++) {
			quals.addObject(new EOKeyValueQualifier(ScolGroupeInclusion.GGRP_KEY2_KEY, EOKeyValueQualifier.QualifierOperatorEqual, ggrpKeys
					.objectAtIndex(i)));
		}
		EOOrQualifier qual = new EOOrQualifier(quals);
		EOFetchSpecification myFetch = new EOFetchSpecification(ScolGroupeInclusion.ENTITY_NAME, qual, null);
		myFetch.setRefreshesRefetchedObjects(true);
		NSArray<ScolGroupeInclusion> groupesInclusion = eContext.objectsWithFetchSpecification(myFetch);
		if (groupesInclusion == null || groupesInclusion.count() == 0) {
			return ggrpKeys;
		}
		// s'il n'y a pas de niveau d'inclusion supplémentaire, on peut le savoir de suite ici, on évite d'appeler une fois de plus la
		// méthode
		NSArray<ScolGroupeInclusion> a = (NSArray<ScolGroupeInclusion>) groupesInclusion
				.valueForKeyPath(ScolGroupeInclusion.SCOL_GROUPE_INCLUSIONS_KEY);
		if (a == null || a.count() == 0) {
			return ggrpKeys.arrayByAddingObjectsFromArray((NSArray<Number>) groupesInclusion.valueForKey(ScolGroupeInclusion.GGRP_KEY1_KEY));
		}
		return ggrpKeys.arrayByAddingObjectsFromArray(groupesAvecInclusion((NSArray<Number>) groupesInclusion
				.valueForKey(ScolGroupeInclusion.GGRP_KEY1_KEY)));
	}

	/**
	 * Renvoie les APs pour un parcours/semestre donné, avec éventuellement la possibilité de demander à inclure le parcours commun et à
	 * inclure l'autre semestre de l'année (pour obtenir toute l'année)
	 * 
	 * @param p
	 *            Parcours pour lequel on veut les APs
	 * @param isPc
	 *            Si on veut inclure le parcours commun
	 * @param s
	 *            Semestre pour lequel on veut les APs
	 * @param isSa
	 *            Si on veut inclure l'autre semestre de l'année pour obtenir l'année complète
	 * @param fannKey
	 *            Année universitaire concernée
	 * @return Un NSArray avec la liste des APs (VMaquetteAp)
	 */
	private NSArray<VMaquetteAp> getApsForParcoursSemestres(MaquetteParcours p, boolean isPc, MaquetteSemestre s, boolean isSa, Number fannKey) {

		// recup msemOrdre du semestre autre
		Integer msemOrdreAutre = (s.msemOrdre() % 2 == 0) ? new Integer(s.msemOrdre() - 1) : new Integer(s.msemOrdre() + 1);

		NSMutableArray<EOQualifier> sumQual = new NSMutableArray<EOQualifier>();

		// Parcours + Semestre
		EOQualifier tmpQual = EOQualifier.qualifierWithQualifierFormat(VMaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + " = %@ and "
				+ VMaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + " = %@", new NSArray<Object>(new Object[] { p, s }));
		sumQual.addObject(tmpQual);

		// Parcours + Semestre autre
		if (isSa) {
			tmpQual = EOQualifier.qualifierWithQualifierFormat(VMaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + " = %@ and "
					+ VMaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY + " = %@",
					new NSArray<Object>(new Object[] { p, msemOrdreAutre }));
			sumQual.addObject(tmpQual);
		}

		if (isPc && p.isParcoursCommun() == false) {
			// recup parcours commun
			MaquetteParcours pc = p.formationSpecialisation().getParcoursCommun();

			// Parcours commun + Semestre
			tmpQual = EOQualifier.qualifierWithQualifierFormat(VMaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + " = %@ and "
					+ VMaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY + " = %@",
					new NSArray<Object>(new Object[] { pc, s.msemOrdre() }));
			sumQual.addObject(tmpQual);

			// Parcours commun + Semestre autre
			if (isSa) {
				tmpQual = EOQualifier.qualifierWithQualifierFormat(VMaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + " = %@ and "
						+ VMaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY + " = %@",
						new NSArray<Object>(new Object[] { pc, msemOrdreAutre }));
				sumQual.addObject(tmpQual);
			}
		}
		tmpQual = new EOOrQualifier(sumQual);

		sumQual.removeAllObjects();
		sumQual.addObject(tmpQual);
		tmpQual = new EOKeyValueQualifier(VMaquetteAp.FANN_KEY_KEY, EOKeyValueQualifier.QualifierOperatorEqual, fannKey);
		sumQual.addObject(tmpQual);

		return VMaquetteAp.fetchVMaquetteAps(eContext, new EOAndQualifier(sumQual), null);
	}

	private NSArray<MaquetteEc> getEcsForParcoursSemestres(MaquetteParcours p, boolean isPc, MaquetteSemestre s, boolean isSa, Number fannKey) {

		// recup msemOrdre du semestre autre
		Integer msemOrdreAutre = (s.msemOrdre() % 2 == 0) ? new Integer(s.msemOrdre() - 1) : new Integer(s.msemOrdre() + 1);

		NSMutableArray<EOQualifier> sumQual = new NSMutableArray<EOQualifier>();

		// Parcours + Semestre
		EOQualifier tmpQual = EOQualifier.qualifierWithQualifierFormat(MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.PARCOURS_KEY + " = %@ and "
				+ MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.SEMESTRE_KEY + " = %@", new NSArray<Object>(new Object[] { p, s }));
		sumQual.addObject(tmpQual);

		// Parcours + Semestre autre
		if (isSa) {
			tmpQual = EOQualifier.qualifierWithQualifierFormat(MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.PARCOURS_KEY + " = %@ and "
					+ MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY + " = %@",
					new NSArray<Object>(new Object[] { p, msemOrdreAutre }));
			sumQual.addObject(tmpQual);
		}

		if (isPc && p.isParcoursCommun() == false) {
			// recup parcours commun
			MaquetteParcours pc = p.formationSpecialisation().getParcoursCommun();

			// Parcours commun + Semestre
			tmpQual = EOQualifier.qualifierWithQualifierFormat(MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.PARCOURS_KEY + " = %@ and "
					+ MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY + " = %@",
					new NSArray<Object>(new Object[] { pc, s.msemOrdre() }));
			sumQual.addObject(tmpQual);

			// Parcours commun + Semestre autre
			if (isSa) {
				tmpQual = EOQualifier.qualifierWithQualifierFormat(MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.PARCOURS_KEY + " = %@ and "
						+ MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY + " = %@",
						new NSArray<Object>(new Object[] { pc, msemOrdreAutre }));
				sumQual.addObject(tmpQual);
			}
		}
		tmpQual = new EOOrQualifier(sumQual);

		sumQual.removeAllObjects();
		sumQual.addObject(tmpQual);
		tmpQual = new EOKeyValueQualifier(MaquetteEc.FANN_KEY_KEY, EOKeyValueQualifier.QualifierOperatorEqual, fannKey);
		sumQual.addObject(tmpQual);

		return MaquetteEc.fetchMaquetteEcs(eContext, new EOAndQualifier(sumQual), null);
	}

	private NSArray<MaquetteSemestre> getSemestresForParcoursSemestres(MaquetteParcours p, boolean isPc, Integer msemOrdre, boolean isSa,
			Number fannKey) {
		NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>(), orQuals = new NSMutableArray<EOQualifier>();
		// année
		andQuals.addObject(new EOKeyValueQualifier(MaquetteRepartitionSem.FANN_KEY_KEY, EOKeyValueQualifier.QualifierOperatorEqual, fannKey));
		// ET (parcours OU parcours commun)
		orQuals.addObject(new EOKeyValueQualifier(MaquetteRepartitionSem.PARCOURS_KEY, EOKeyValueQualifier.QualifierOperatorEqual, p));
		if (isPc) {
			orQuals.addObject(new EOKeyValueQualifier(MaquetteRepartitionSem.PARCOURS_KEY, EOKeyValueQualifier.QualifierOperatorEqual, p
					.formationSpecialisation().getParcoursCommun()));
		}
		andQuals.addObject(new EOOrQualifier(orQuals));
		orQuals.removeAllObjects();
		// ET (semestre OU autre semestre)
		orQuals.addObject(new EOKeyValueQualifier(MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, msemOrdre));
		if (isSa) {
			Integer msemOrdreAutre = (msemOrdre % 2 == 0) ? new Integer(msemOrdre - 1) : new Integer(msemOrdre + 1);
			orQuals.addObject(new EOKeyValueQualifier(MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, msemOrdreAutre));
		}
		andQuals.addObject(new EOOrQualifier(orQuals));
		return (NSArray<MaquetteSemestre>) MaquetteRepartitionSem.fetchMaquetteRepartitionSems(eContext, new EOAndQualifier(andQuals), null)
				.valueForKey(MaquetteRepartitionSem.SEMESTRE_KEY);
	}

	private NSArray<NSDictionary<String, Object>> getPlanningAps(NSTimestamp debut, NSTimestamp fin, NSArray<VMaquetteAp> maquettes,
			NSArray<MaquetteSemestre> semestres) {

		NSMutableArray<NSDictionary<String, Object>> planning = new NSMutableArray<NSDictionary<String, Object>>();

		boolean appendComments = true;

		// On verifie s'il y a réellement qqu'un de connecté.
		if (!session.isLazyMode()) {
			appendComments = (((Number) session.prefUser("commentScol")).intValue() == 1);
		}

		try {
			NSArray<ScolGroupeGrp> groupesSemestres = getGroupesSemestres(semestres);

			StringBuffer qualBuffer = new StringBuffer("((");

			int mapKey = -1;
			int ggrpKey = -1;
			boolean hasGroupes = groupesSemestres != null && groupesSemestres.count() > 0;

			for (int iAp = 0; iAp < maquettes.count(); iAp++) {
				VMaquetteAp currentAp = maquettes.objectAtIndex(iAp);
				mapKey = currentAp.mapKey().intValue();
				qualBuffer.append(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.MAP_KEY_KEY + " = ")
						.append(mapKey);
				if (iAp < maquettes.count() - 1) {
					qualBuffer.append(" or ");
				}
			}
			qualBuffer.append("))");

			NSMutableArray<EOQualifier> sumQualifierAp = new NSMutableArray<EOQualifier>();
			EOQualifier qualifierAps = EOQualifier.qualifierWithQualifierFormat(qualBuffer.toString(), null);
			sumQualifierAp.addObject(qualifierAps);

			EOQualifier qualifierDates = null;
			if (debut != null && fin != null) {
				qualifierDates = EOQualifier.qualifierWithQualifierFormat(Periodicite.DATE_FIN_KEY + " >= %@ and " + Periodicite.DATE_DEB_KEY
						+ " <= %@", new NSArray<NSTimestamp>(new NSTimestamp[] { debut, fin }));
				sumQualifierAp.addObject(qualifierDates);
			}

			if (hasGroupes) {
				int grpCount = groupesSemestres.count();
				qualBuffer = new StringBuffer();
				ScolGroupeGrp currentGrp;
				for (int iGr = 0; iGr < grpCount; iGr++) {
					currentGrp = groupesSemestres.objectAtIndex(iGr);
					ggrpKey = currentGrp.ggrpKey().intValue();
					qualBuffer.append(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.GGRP_KEY_KEY + " = ")
							.append(ggrpKey);
					if (iGr < grpCount - 1) {
						qualBuffer.append(" or "); // 2er or
					}
				}
				qualBuffer.append(" or " + Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "." + ReservationAp.GGRP_KEY_KEY
						+ " = nil");
				qualBuffer.append(")");
				EOQualifier qualifierGroupes = EOQualifier.qualifierWithQualifierFormat(qualBuffer.toString(), null);
				sumQualifierAp.addObject(qualifierGroupes);
			}

			EOAndQualifier qualifier = new EOAndQualifier(sumQualifierAp);
			EOFetchSpecification myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, qualifier, null);
			myFetch.setUsesDistinct(true);
			myFetch.setRefreshesRefetchedObjects(true);
			myFetch.setPrefetchingRelationshipKeyPaths(new NSArray<String>(new String[] { Periodicite.RESERVATION_KEY }));
			NSArray<Periodicite> periodicites = eContext.objectsWithFetchSpecification(myFetch);

			planning.addObjectsFromArray(getPlanningMisEnForme(periodicites, appendComments));

			planning.addObjectsFromArray(getPlanningSemestres(debut, fin, semestres, groupesSemestres));

			return planning.immutableClone();
		}
		catch (Throwable e) {
			NSLog.out.appendln("clientSideRequestPlanningAp : " + e.getMessage());
			e.printStackTrace();
			return new NSArray<NSDictionary<String, Object>>();
		}
	}

	private NSArray<NSDictionary<String, Object>> getPlanningExamens(NSTimestamp debut, NSTimestamp fin, NSArray<MaquetteEc> ecs) {
		NSArray<NSDictionary<String, Object>> planning = null;
		boolean dispExam = true;
		boolean appendComments = true;
		if (!session.isLazyMode()) {
			dispExam = (((Number) session.prefUser("dispExam")).intValue() == 1);
			appendComments = (((Number) session.prefUser("commentScol")).intValue() == 1);
		}

		if (dispExam) {
			NSMutableArray<EOQualifier> qEcs = new NSMutableArray<EOQualifier>();
			Enumeration<MaquetteEc> eEc = ecs.objectEnumerator();
			while (eEc.hasMoreElements()) {
				qEcs.addObject(EOQualifier.qualifierWithQualifierFormat(ExamenEntete.EC_KEY + " = %@", new NSArray<MaquetteEc>(eEc.nextElement())));
			}
			NSArray<ExamenEntete> examenEntetes = ExamenEntete.fetchExamenEntetes(eContext, new EOOrQualifier(qEcs), null);
			if (examenEntetes.count() > 0) {
				EOQualifier qualifierDates = null;
				if (debut != null && fin != null) {
					qualifierDates = EOQualifier.qualifierWithQualifierFormat(Periodicite.DATE_FIN_KEY + " >= %@ and " + Periodicite.DATE_DEB_KEY
							+ " <= %@", new NSArray<NSTimestamp>(new NSTimestamp[] { debut, fin }));
				}
				NSArray<Periodicite> periodicites = getPeriodicitesExamens(examenEntetes, qualifierDates);
				if (periodicites != null && periodicites.count() > 0) {
					planning = getPlanningMisEnForme(periodicites, appendComments);
				}
			}
		}
		return planning;
	}

	private NSArray<NSDictionary<String, Object>> getPlanningMisEnForme(NSArray<Periodicite> periodicites, boolean appendComments) {
		NSMutableArray<NSDictionary<String, Object>> planning = new NSMutableArray<NSDictionary<String, Object>>();
		for (int i = 0; i < periodicites.count(); i++) {
			Periodicite period = periodicites.objectAtIndex(i);
			Reservation reservation = period.reservation();
			StringBuffer strOccupants = new StringBuffer("");
			StringBuffer sApGrp = new StringBuffer("");

			NSArray<IndividuUlr> individuArray = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
					+ Occupant.INDIVIDU_KEY);
			individuArray = DBHandler.retirerMultiples(individuArray);
			int nbocc = individuArray.count();
			for (int io = 0; io < nbocc; io++) {
				IndividuUlr ioccupant = individuArray.objectAtIndex(io);
				strOccupants.append(ioccupant.prenom().substring(0, 1) + "." + StringCtrl.capitalizeWords(ioccupant.nomUsuel()) + " ");
			}

			NSArray<EOSortOrdering> sortResaAp = new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(ReservationAp.LE_MAP_KEY_KEY,
					EOSortOrdering.CompareAscending));

			NSArray<ReservationAp> reservationApArray = EOSortOrdering.sortedArrayUsingKeyOrderArray(reservation.reservationAp(), sortResaAp);
			reservationApArray = DBHandler.retirerMultiples(reservationApArray);
			nbocc = reservationApArray.count();
			String colorAp = null;
			VMaquetteAp apPrecedent = null;
			ScolGroupeGrp grpPrecedent = null;
			for (int iResaAp = 0; iResaAp < nbocc; iResaAp++) {
				ReservationAp currentReservAp = reservationApArray.objectAtIndex(iResaAp);
				VMaquetteAp ap = currentReservAp.vMaquetteAp();

				if (apPrecedent == null || !apPrecedent.equals(ap)) {
					sApGrp.append(StringCtrl.capitalizeWords(ap.mapLibelle()));
					apPrecedent = ap;
				}

				try {
					if (currentReservAp.scolGroupeGrp() != null) {
						if (grpPrecedent == null || !grpPrecedent.equals(currentReservAp.scolGroupeGrp())) {
							sApGrp.append(" (");
							sApGrp.append(currentReservAp.scolGroupeGrp().ggrpCode());
							sApGrp.append(")");
							grpPrecedent = currentReservAp.scolGroupeGrp();
						}
					}
				}
				catch (Exception e) {
					System.out.println("Le groupe ggrpKey = " + currentReservAp.ggrpKey()
							+ " n'existe plus, mais est utilisé dans au moins une réservation...");
				}

				if (iResaAp < nbocc - 1 && nbocc > 1) {
					sApGrp.append(", ");
				}

				if (session.colorEc) {
					if (ap != null && ap.colorCode() != null) {
						colorAp = ap.colorCode();
					}
				}
			}

			// les examens :
			NSArray<ResaExamen> resaExamenArray = DBHandler.retirerMultiples(reservation.resaExamens());
			nbocc = resaExamenArray.count();
			for (int iex = 0; iex < nbocc; iex++) {
				if (iex == 0) {
					sApGrp.append("Examen : ");
				}
				ResaExamen currentResaEx = resaExamenArray.objectAtIndex(iex);
				sApGrp.append(StringCtrl.capitalizeWords(currentResaEx.examenEntete().eentLibelle()));

				try {
					if (currentResaEx.scolGroupeGrp() != null) {
						sApGrp.append("(");
						sApGrp.append(currentResaEx.scolGroupeGrp().ggrpCode());
						sApGrp.append(")");
					}
				}
				catch (Exception e) {
					System.out.println("Le groupe ggrpKey = " + currentResaEx.ggrpKey()
							+ " n'existe plus, mais est utilisé dans au moins une réservation...");
				}

				if (iex < nbocc - 1 && nbocc > 1) {
					sApGrp.append(", ");
				}
			}

			NSMutableDictionary<String, Object> dRes = new NSMutableDictionary<String, Object>();
			dRes.takeValueForKey(eContext.globalIDForObject(reservation), "reservation");
			NSTimestamp debutRes = period.dateDeb();
			dRes.takeValueForKey(debutRes, "debut");
			dRes.takeValueForKey(period.dateFin(), "fin");

			Integer jour = new Integer(session.calendarHandler.getDay(debutRes));
			dRes.takeValueForKey(jour, "jour");
			dRes.takeValueForKey(new Integer(1), "resa");

			StringBuffer strSal = new StringBuffer();

			NSArray<ResaSalles> resaSallesArray = DBHandler.retirerMultiples(reservation.resaSalles());
			nbocc = resaSallesArray.count();
			Salles sallePrecedente = null;
			for (int j = 0; j < nbocc; j++) {
				ResaSalles resaSalle = resaSallesArray.objectAtIndex(j);
				Salles sal = resaSalle.salle();
				if (sallePrecedente == null || !sallePrecedente.equals(sal)) {
					strSal.append(StringCtrl.capitalizeWords(sal.cLocal() + " : " + sal.salPorte()));
					if (j > 0 && j < nbocc - 1) {
						strSal.append(",");
					}
					sallePrecedente = sal;
				}

				if (resaSalle.resaSalEtat().equals("O")) {
					dRes.takeValueForKey("O", "salleValide");
				}
			}

			NSArray<SalleSouhaitee> salleSouhaiteeArray = reservation.sallesSouhaitees();
			nbocc = salleSouhaiteeArray.count();
			for (int j = 0; j < nbocc; j++) {
				Salles sal = (salleSouhaiteeArray.objectAtIndex(j)).salle();
				strSal.append(StringCtrl.capitalizeWords(sal.cLocal() + " : " + sal.salPorte()));

				if (j > 0 && j < nbocc - 1) {
					strSal.append(",");
				}
			}

			NSMutableArray<String> texteResa = new NSMutableArray<String>();
			// ajout les commentaires si choix effectue par utilisateur
			if (appendComments && (reservation.resaCommentaire() != null)) {
				sApGrp.append("-" + reservation.resaCommentaire());
			}

			texteResa.addObject(sApGrp.toString());
			texteResa.addObject(strOccupants.toString());
			if (strSal != null) {
				texteResa.addObject(strSal.toString());
			}

			dRes.takeValueForKey(new NSArray<String>(texteResa.componentsJoinedByString(" \n")), "texte");
			dRes.takeValueForKey("Edt-Ens", "typeTemps");
			if (session.colorEc && colorAp != null) {
				dRes.takeValueForKey(colorAp, "ccolor");
			}
			else {
				dRes.takeValueForKey(reservation.tlocCode(), "ccolor");
			}
			dRes.takeValueForKey(new Integer(1), "deplace");
			planning.addObject(dRes);
		}
		return planning;
	}

	private NSArray<Periodicite> getPeriodicitesGroupe(NSTimestamp debut, NSTimestamp fin, ScolGroupeGrp groupe) {
		try {
			EOQualifier qObjGrpAp = EOQualifier.qualifierWithQualifierFormat(MaquetteAp.SCOL_GROUPE_OBJETS_KEY + "." + ScolGroupeObjet.GGRP_KEY_KEY
					+ " = %@ and " + MaquetteAp.SCOL_GROUPE_OBJETS_KEY + "." + ScolGroupeObjet.GOBJ_TYPE_KEY + " = 'ELP'",
					new NSArray(groupe.ggrpKey()));

			NSArray aps = DBHandler.fetchData(eContext, MaquetteAp.ENTITY_NAME, qObjGrpAp);

			// on cherche les resa des aps sans groupes.
			NSMutableArray temp = new NSMutableArray();
			NSMutableArray exams = new NSMutableArray();
			MaquetteAp leAp = null;

			for (int i = 0; i < aps.count(); i++) {
				leAp = (MaquetteAp) aps.objectAtIndex(i);
				Number mapKey = (Number) EOUtilities.primaryKeyForObject(eContext, leAp).objectForKey(MaquetteAp.MAP_KEY_KEY);
				temp.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
						+ ReservationAp.MAP_KEY_KEY + " = %@ and " + Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
						+ ReservationAp.GGRP_KEY_KEY + " = nil", new NSArray(mapKey)));

				// on cherche les examens du groupe
				EOQualifier qEc = EOQualifier.qualifierWithQualifierFormat(MaquetteEc.MAQUETTE_REPARTITION_APS_KEY + "."
						+ MaquetteRepartitionAp.MAQUETTE_AP_KEY + " = %@", new NSArray(leAp));

				NSArray ecs = DBHandler.fetchData(eContext, MaquetteEc.ENTITY_NAME, qEc);
				for (int n = 0; n < ecs.count(); n++) {
					EOQualifier qualExam = DBHandler.getSimpleQualifier(ExamenEntete.EC_KEY, ecs.objectAtIndex(n));
					exams.addObjectsFromArray(DBHandler.fetchData(eContext, ExamenEntete.ENTITY_NAME, qualExam));
				}

			}

			temp.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
					+ ReservationAp.SCOL_GROUPE_GRP_KEY + " = %@", new NSArray(new Object[] { groupe })));

			EOQualifier qualifierApGrp = new EOOrQualifier(temp);

			// le balisage sur les dates
			EOQualifier qualifierDates = EOQualifier.qualifierWithQualifierFormat(Periodicite.DATE_FIN_KEY + " >= %@ and " + Periodicite.DATE_DEB_KEY
					+ " <= %@", new NSArray(new Object[] { debut, fin }));

			EOQualifier qualifier = new EOAndQualifier(new NSArray(new Object[] { qualifierApGrp, qualifierDates }));

			EOFetchSpecification myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, qualifier, null);
			myFetch.setUsesDistinct(true);
			myFetch.setRefreshesRefetchedObjects(true);
			NSArray retour = eContext.objectsWithFetchSpecification(myFetch);
			if (exams.count() > 0) {
				retour = retour.arrayByAddingObjectsFromArray(getPeriodicitesExamens(exams, qualifierDates));
			}
			return retour;
		}
		catch (Throwable e) {
			e.printStackTrace();
			return new NSArray();
		}
	}

	private NSArray<Periodicite> getPeriodicitesExamens(NSArray<ExamenEntete> examEntetes, EOQualifier qualifierDates) {
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		for (int i = 0; i < examEntetes.count(); i++) {
			quals.addObject(DBHandler.getSimpleQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_EXAMENS_KEY + "."
					+ ResaExamen.EXAMEN_ENTETE_KEY, examEntetes.objectAtIndex(i)));
		}
		EOQualifier qResaExam = new EOOrQualifier(quals);
		EOQualifier totalResaExamQualifier = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qResaExam, qualifierDates }));

		EOFetchSpecification myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, totalResaExamQualifier, null);
		myFetch.setUsesDistinct(true);
		myFetch.setRefreshesRefetchedObjects(true);

		return eContext.objectsWithFetchSpecification(myFetch);
	}

	private NSArray<ScolGroupeGrp> getGroupesSemestres(NSArray<MaquetteSemestre> semestres) {
		if (semestres == null || semestres.count() == 0) {
			return new NSArray<ScolGroupeGrp>();
		}
		NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>();
		Enumeration<MaquetteSemestre> enumerationSemestres = semestres.objectEnumerator();
		while (enumerationSemestres.hasMoreElements()) {
			orQuals.addObject(new EOKeyValueQualifier(ScolGroupeGrp.SCOL_GROUPE_OBJET_VDIS_KEY + "." + ScolGroupeObjetVdi.SEMESTRE_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, enumerationSemestres.nextElement()));
		}
		return ScolGroupeGrp.fetchScolGroupeGrps(eContext, new EOOrQualifier(orQuals), null);
	}

	private NSArray<NSDictionary<String, Object>> getPlanningSemestres(NSTimestamp debut, NSTimestamp fin, NSArray<MaquetteSemestre> semestres,
			NSArray<ScolGroupeGrp> groupesSemestres) {

		NSMutableArray<NSDictionary<String, Object>> elementsPlanning = new NSMutableArray<NSDictionary<String, Object>>();
		if (semestres == null || semestres.count() == 0) {
			return elementsPlanning;
		}
		try {
			if (groupesSemestres == null) {
				groupesSemestres = getGroupesSemestres(semestres);
			}

			NSMutableArray<EOQualifier> qualifierArray = new NSMutableArray<EOQualifier>();
			StringBuffer qualBuffer = new StringBuffer();

			for (int iSem = 0; iSem < semestres.count(); iSem++) {
				MaquetteSemestre currentSemestre = semestres.objectAtIndex(iSem);
				qualBuffer.append(
						Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATIONS_SEMESTRES_KEY + "." + ReservationSemestre.MSEM_KEY_KEY + " = ")
						.append(currentSemestre.msemKey().intValue());
				if (iSem < semestres.count() - 1) {
					qualBuffer.append(" or ");
				}
			}

			qualBuffer.append("))");

			EOQualifier qualSemestre = EOQualifier.qualifierWithQualifierFormat(qualBuffer.toString(), null);
			qualifierArray.addObject(qualSemestre);

			int grpCount = groupesSemestres.count();

			if (grpCount > 0) {
				qualBuffer = new StringBuffer();
				ScolGroupeGrp currentGrp;
				for (int iGr = 0; iGr < grpCount; iGr++) {
					currentGrp = groupesSemestres.objectAtIndex(iGr);
					qualBuffer.append(
							Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATIONS_SEMESTRES_KEY + "." + ReservationSemestre.GGRP_KEY_KEY
									+ " = ").append(currentGrp.ggrpKey().intValue());

					if (iGr < grpCount - 1) {
						qualBuffer.append(" or "); // 2er or
					}
				}
				qualBuffer.append(" or " + Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATIONS_SEMESTRES_KEY + "."
						+ ReservationSemestre.GGRP_KEY_KEY + " = nil");
				qualBuffer.append(")");

				EOQualifier qualGroupe = EOQualifier.qualifierWithQualifierFormat(qualBuffer.toString(), null);
				qualifierArray.addObject(qualGroupe);
			}

			if (debut != null && fin != null) {
				qualifierArray.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.DATE_FIN_KEY + " >= %@ and " + Periodicite.DATE_DEB_KEY
						+ " <= %@", new NSArray<NSTimestamp>(new NSTimestamp[] { debut, fin })));
			}
			EOAndQualifier qualifier = new EOAndQualifier(qualifierArray);
			EOFetchSpecification myFetch = new EOFetchSpecification(Periodicite.ENTITY_NAME, qualifier, null);

			myFetch.setUsesDistinct(true);
			myFetch.setRefreshesRefetchedObjects(true);
			NSArray<Periodicite> periodicites = eContext.objectsWithFetchSpecification(myFetch);

			for (int i = 0; i < periodicites.count(); i++) {
				Periodicite currentPeriod = periodicites.objectAtIndex(i);
				Reservation resa = currentPeriod.reservation();

				StringBuffer strOccupants = new StringBuffer();
				StringBuffer sCommentaireGrp = new StringBuffer(resa.resaCommentaire());
				sCommentaireGrp.append(" ");

				NSArray<IndividuUlr> tmpIndividus = (NSArray<IndividuUlr>) resa.valueForKeyPath(Reservation.OCCUPANTS_KEY + "."
						+ Occupant.INDIVIDU_KEY);
				tmpIndividus = DBHandler.retirerMultiples(tmpIndividus);
				int nbocc = tmpIndividus.count();
				for (int io = 0; io < nbocc; io++) {
					IndividuUlr ioccupant = tmpIndividus.objectAtIndex(io);
					strOccupants.append(ioccupant.prenom().substring(0, 1) + "." + StringCtrl.capitalizeWords(ioccupant.nomUsuel()));
					strOccupants.append(" ");
				}

				NSArray<ReservationSemestre> tmpSemestres = resa.reservationsSemestres();
				nbocc = tmpSemestres.count();
				for (int iResaSem = 0; iResaSem < nbocc; iResaSem++) {
					ReservationSemestre currentResaSem = tmpSemestres.objectAtIndex(iResaSem);

					if (currentResaSem.scolGroupeGrp() != null) {
						sCommentaireGrp.append("(");
						sCommentaireGrp.append(currentResaSem.scolGroupeGrp().ggrpCode());
						sCommentaireGrp.append(")");
					}

					if (iResaSem < nbocc - 1 && nbocc > 1) {
						sCommentaireGrp.append(", ");
					}
				} // semestres/groupes

				NSMutableDictionary<String, Object> dRes = new NSMutableDictionary<String, Object>();
				dRes.takeValueForKey(eContext.globalIDForObject(resa), "reservation");
				NSTimestamp debutRes = currentPeriod.dateDeb();
				dRes.takeValueForKey(debutRes, "debut");
				dRes.takeValueForKey(currentPeriod.dateFin(), "fin");

				Integer jour = new Integer(session.calendarHandler.getDay(debutRes));
				dRes.takeValueForKey(jour, "jour");
				dRes.takeValueForKey(new Integer(1), "resa");

				StringBuffer strSal = new StringBuffer();

				NSArray<ResaSalles> tmpSalles = resa.resaSalles();
				nbocc = tmpSalles.count();
				for (int j = 0; j < nbocc; j++) {
					ResaSalles resaSalle = tmpSalles.objectAtIndex(j);
					Salles sal = resaSalle.salle();
					strSal.append(StringCtrl.capitalizeWords(sal.cLocal() + " : " + sal.salPorte()));

					if (j > 0 && j < nbocc - 1) {
						strSal.append(",");
					}
					if (resaSalle.resaSalEtat().equals("O")) {
						dRes.takeValueForKey("O", "salleValide");
					}
				}

				NSArray<SalleSouhaitee> tmpSallesSouhaitees = resa.sallesSouhaitees();
				nbocc = tmpSallesSouhaitees.count();
				for (int j = 0; j < nbocc; j++) {
					Salles sal = (tmpSallesSouhaitees.objectAtIndex(j)).salle();
					strSal.append(StringCtrl.capitalizeWords(sal.cLocal() + " : " + sal.salPorte()));

					if (j > 0 && j < nbocc - 1) {
						strSal.append(",");
					}
				}
				NSMutableArray<String> texteResa = new NSMutableArray<String>();

				texteResa.addObject(sCommentaireGrp.toString());
				texteResa.addObject(strOccupants.toString());
				if (strSal != null) {
					texteResa.addObject(strSal.toString());
				}

				dRes.takeValueForKey(texteResa, "texte");
				dRes.takeValueForKey("Edt-Ens", "typeTemps");
				dRes.takeValueForKey("SEM", "ccolor");
				dRes.takeValueForKey(new Integer(1), "deplace");
				elementsPlanning.addObject(dRes);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return elementsPlanning;
	}

	private NSArray<NSDictionary<String, Object>> getPlanningVacancesScolaires(NSTimestamp debut, NSTimestamp fin, MaquetteRepartitionSem mrs) {

		NSMutableArray<NSDictionary<String, Object>> elementsPlanning = new NSMutableArray<NSDictionary<String, Object>>();
		if (mrs == null) {
			return elementsPlanning;
		}
		try {
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(VacancesScolaires.FORMATION_HABILITATION_KEY + "."
					+ FormationHabilitation.FORMATION_SPECIALISATION_KEY + "." + FormationSpecialisation.MAQUETTE_PARCOURS_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, mrs.parcours()));
			if (debut != null && fin != null) {
				quals.addObject(EOQualifier.qualifierWithQualifierFormat(VacancesScolaires.VS_DATE_FIN_KEY + " >= %@ and "
						+ VacancesScolaires.VS_DATE_DEBUT_KEY + " <= %@", new NSArray<NSTimestamp>(new NSTimestamp[] { debut, fin })));
			}
			EOFetchSpecification myFetch = new EOFetchSpecification(VacancesScolaires.ENTITY_NAME, new EOAndQualifier(quals), null);

			myFetch.setUsesDistinct(true);
			myFetch.setRefreshesRefetchedObjects(true);
			NSArray<VacancesScolaires> vacances = eContext.objectsWithFetchSpecification(myFetch);

			for (int i = 0; i < vacances.count(); i++) {
				VacancesScolaires vacance = vacances.objectAtIndex(i);
				NSMutableDictionary<String, Object> dRes = new NSMutableDictionary<String, Object>();
				dRes.takeValueForKey(vacance.vsDateDebut(), "debut");
				dRes.takeValueForKey(vacance.vsDateFin(), "fin");
				dRes.takeValueForKey(new NSArray<String>("Vacances scolaires"), "texte");
				dRes.takeValueForKey("Edt", "typeTemps");
				dRes.takeValueForKey("VAC", "ccolor");
				dRes.takeValueForKey(new Integer(1), "deplace");
				elementsPlanning.addObject(dRes);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return elementsPlanning;
	}

	// *****************************************************
	// ATTENTION : version foireuse, a ne pas utiliser...
	// on le laisse là juste pour ceux qui ont développé
	// ça et probablement utilisent ça dans leur
	// base bizarre...
	// *****************************************************
	/**
	 * @param fspnKey
	 * @param debut
	 * @param fin
	 * @return
	 * @deprecated
	 */
	public NSArray getPlanningFormation(Number fspnKey, NSTimestamp debut, NSTimestamp fin) {
		int typeSemestreToDisplay = SEMESTRE_IMPAIR;

		MaquetteParcours theParcours = null;
		MaquetteSemestre theSemestre = null;

		System.out.println("getPlanningFormation : " + fspnKey + " - " + debut + " - " + fin);

		String sYear = FormatHandler.dateToStr(debut, "%Y");
		String sMonth = FormatHandler.dateToStr(debut, "%m");

		int year = FormatHandler.strToInt(sYear);
		int month = FormatHandler.strToInt(sMonth);

		System.out.println("year : " + year);
		System.out.println("month : " + month);

		Number fannKey = null;

		if (month >= 9 && month <= 12) {
			fannKey = new Integer(year);
			typeSemestreToDisplay = SEMESTRE_IMPAIR;
		}
		if (month <= 8 && month >= 1) {
			fannKey = new Integer(year - 1);
			typeSemestreToDisplay = SEMESTRE_PAIR;
		}

		/*
		 * if(month>12 && month<9) { fannKey = new Integer(year-1); typeSemestreToDisplay = SEMESTRE_PAIR; } else { fannKey = new
		 * Integer(year); typeSemestreToDisplay = SEMESTRE_IMPAIR; }
		 */

		Object[] args = new Object[] { fspnKey, fannKey };

		EOQualifier qualSpecialisation = EOQualifier.qualifierWithQualifierFormat("fspnKey=%@ and habilitations.fannKey=%@", new NSArray(args));

		NSArray temp = DBHandler.fetchData(eContext, FormationSpecialisation.ENTITY_NAME, qualSpecialisation);

		if (temp.count() == 0) {
			return new NSArray();
		}

		FormationSpecialisation specialisation = (FormationSpecialisation) temp.objectAtIndex(0);
		NSArray parcours = specialisation.maquetteParcours();

		if (parcours.count() > 0) {
			theParcours = (MaquetteParcours) parcours.objectAtIndex(0);
		}

		args = new Object[] { theParcours, fannKey };

		EOQualifier qualSem = EOQualifier.qualifierWithQualifierFormat("parcours=%@ and fannKey=%@", new NSArray(args));

		temp = DBHandler.fetchData(eContext, MaquetteRepartitionSem.ENTITY_NAME, qualSem);

		temp = (NSArray) temp.valueForKey("semestre");

		MaquetteSemestre currentSemestre;
		int ordre;
		for (int i = 0; i < temp.count(); i++) {
			currentSemestre = (MaquetteSemestre) temp.objectAtIndex(i);
			ordre = currentSemestre.msemOrdre().intValue();
			boolean isCurrentSemestrePair = (ordre % 2) == 0;

			if ((isCurrentSemestrePair && typeSemestreToDisplay == SEMESTRE_PAIR)
					|| (!isCurrentSemestrePair && typeSemestreToDisplay == SEMESTRE_IMPAIR)) {
				theSemestre = currentSemestre;
			}
		}

		NSArray<VMaquetteAp> listAp = getApsForParcoursSemestres(theParcours, false, theSemestre, false, fannKey);
		return getPlanningAps(debut, fin, listAp, null);
	}

}
