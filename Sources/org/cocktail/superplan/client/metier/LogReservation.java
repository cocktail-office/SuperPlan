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

package org.cocktail.superplan.client.metier;

import java.util.Enumeration;

import org.cocktail.superplan.client.composant.SaisieMotifPourLog;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSValidation;

import edtscol.client.MainClient;

public class LogReservation extends _LogReservation {

	public static final String ACTION_CREATION = "Création";
	public static final String ACTION_MODIFICATION = "Modification";
	public static final String ACTION_SUPPRESSION = "Suppression";

	// Attributs non visibles
	public static final String LOG_KEY_KEY = "logKey";
	public static final String LOG_NO_INDIVIDU_KEY = "logNoIndividu";

	public static final String LOG_KEY_COLKEY = "LOG_KEY";
	public static final String LOG_NO_INDIVIDU_COLKEY = "LOG_NO_INDIVIDU";

	public LogReservation() {
		super();
	}

	public static LogReservation getLastLogReservationFor(EOEditingContext ec, Integer resaKey) {
		NSArray<LogReservation> logs = fetchLogReservations(ec, new EOKeyValueQualifier(LOG_RESA_KEY_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
				resaKey), new NSArray<EOSortOrdering>(new EOSortOrdering(LOG_DATE_KEY, EOSortOrdering.CompareAscending)));
		if (logs != null && logs.count() > 0) {
			return logs.lastObject();
		}
		return null;
	}

	public static boolean logModificationReservation(EOEditingContext ec, Reservation resa) {
		return logReservation(ec, resa, ACTION_MODIFICATION);
	}

	public static boolean logSuppressionReservation(EOEditingContext ec, Reservation resa) {
		return logReservation(ec, resa, ACTION_SUPPRESSION);
	}

	private static boolean logReservation(EOEditingContext ec, Reservation resa, String action) {
		try {
			MainClient app = (MainClient) EOApplication.sharedApplication();
			if (resa == null) {
				return false;
			}
			Integer resaKey = (Integer) app.primaryKeyFromEO(resa, Reservation.RESA_KEY_KEY);
			if (resaKey == null) {
				return false;
			}
			String motif = null;
			if ((app.isAppExigeMotifModification() && action.equals(ACTION_MODIFICATION))
					|| (app.isAppExigeMotifSuppression() && action.equals(ACTION_SUPPRESSION))) {
				SaisieMotifPourLog saisieMotif = new SaisieMotifPourLog(null);
				saisieMotif.open(action);
				motif = saisieMotif.getMotifSaisi();
			}
			IndividuUlr agent = (IndividuUlr) app.userInfo("individu");
			LogReservation log = LogReservation.createLogReservation(ec, action, new NSTimestamp(), resa.dCreation(), resa.dModification(),
					resa.tlocCode(), resaKey, agent, resa.individuAgent(), resa.typeLocation());
			log.setLogMotif(motif);
			log.setLogOldResaCommentaire(resa.resaCommentaire());

			logReservationAp(log, resa, resaKey);
			logReservationExamen(log, resa, resaKey);
			logReservationObjet(log, resa, resaKey);
			logReservationOccupant(log, resa, resaKey);
			logReservationPeriod(log, resa, resaKey);
			logReservationSalle(log, resa, resaKey);
			logReservationSemestre(log, resa, resaKey);

			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void logReservationAp(LogReservation log, Reservation resa, Integer resaKey) {
		if (resa.reservationAp() != null) {
			Enumeration<ReservationAp> resasEnum = resa.reservationAp().objectEnumerator();
			while (resasEnum.hasMoreElements()) {
				ReservationAp resaAp = resasEnum.nextElement();
				LogReservationAp logAp = log.createLogReservationApsRelationship();
				logAp.setLogOldHcompRec(resaAp.hcompRec());
				logAp.setLogOldResaKey(resaKey);
				logAp.setMaquetteApRelationship(resaAp.maquetteAp());
				logAp.setScolGroupeGrpRelationship(resaAp.scolGroupeGrp());
			}
		}
	}

	private static void logReservationExamen(LogReservation log, Reservation resa, Integer resaKey) {
		if (resa.resaExamens() != null) {
			Enumeration<ResaExamen> resasEnum = resa.resaExamens().objectEnumerator();
			while (resasEnum.hasMoreElements()) {
				ResaExamen resaExamen = resasEnum.nextElement();
				LogReservationExamen logExamen = log.createLogReservationExamensRelationship();
				logExamen.setLogOldResaKey(resaKey);
				logExamen.setExamenEnteteRelationship(resaExamen.examenEntete());
				logExamen.setScolGroupeGrpRelationship(resaExamen.scolGroupeGrp());
			}
		}
	}

	private static void logReservationObjet(LogReservation log, Reservation resa, Integer resaKey) {
		if (resa.reservationObjets() != null) {
			Enumeration<ReservationObjet> resasEnum = resa.reservationObjets().objectEnumerator();
			while (resasEnum.hasMoreElements()) {
				ReservationObjet resaObjet = resasEnum.nextElement();
				LogReservationObjet logObjet = log.createLogReservationObjetsRelationship();
				logObjet.setLogOldResaKey(resaKey);
				logObjet.setIndividuValideurRelationship(resaObjet.individuValideur());
				logObjet.setLogOldDateValidation(resaObjet.dateValidation());
				logObjet.setLogOldMotifAnnulation(null);
				logObjet.setLogOldResaEtat(resaObjet.resaEtat());
				logObjet.setResaObjetRelationship(resaObjet.resaObjet());
			}
		}
	}

	private static void logReservationOccupant(LogReservation log, Reservation resa, Integer resaKey) {
		if (resa.reservationObjets() != null) {
			Enumeration<Occupant> resasEnum = resa.occupants().objectEnumerator();
			while (resasEnum.hasMoreElements()) {
				Occupant resaOccupant = resasEnum.nextElement();
				LogReservationOccupant logOccupant = log.createLogReservationOccupantsRelationship();
				logOccupant.setLogOldResaKey(resaKey);
				logOccupant.setIndividuUlrRelationship(resaOccupant.individu());
				logOccupant.setLogOldHcompRec(resaOccupant.hcompRec());
			}
		}
	}

	private static void logReservationPeriod(LogReservation log, Reservation resa, Integer resaKey) {
		if (resa.reservationObjets() != null) {
			Enumeration<Periodicite> resasEnum = resa.periodicites().objectEnumerator();
			while (resasEnum.hasMoreElements()) {
				Periodicite resaPeriod = resasEnum.nextElement();
				LogReservationPeriod logPeriod = log.createLogReservationPeriodsRelationship();
				logPeriod.setLogOldResaKey(resaKey);
				logPeriod.setLogOldDateDeb(resaPeriod.dateDeb());
				logPeriod.setLogOldDateFin(resaPeriod.dateFin());
				logPeriod.setLogOldHcomp(resaPeriod.hcomp());
			}
		}
	}

	private static void logReservationSalle(LogReservation log, Reservation resa, Integer resaKey) {
		if (resa.reservationObjets() != null) {
			Enumeration<ResaSalles> resasEnum = resa.resaSalles().objectEnumerator();
			while (resasEnum.hasMoreElements()) {
				ResaSalles resaSalle = resasEnum.nextElement();
				LogReservationSalle logSalle = log.createLogReservationSallesRelationship();
				logSalle.setLogOldResaKey(resaKey);
				logSalle.setLogOldResaSalDate(resaSalle.resaSalDate());
				logSalle.setLogOldResaSalEtat(resaSalle.resaSalEtat());
				logSalle.setSallesRelationship(resaSalle.salle());
			}
		}
	}

	private static void logReservationSemestre(LogReservation log, Reservation resa, Integer resaKey) {
		if (resa.reservationObjets() != null) {
			Enumeration<ReservationSemestre> resasEnum = resa.reservationsSemestres().objectEnumerator();
			while (resasEnum.hasMoreElements()) {
				ReservationSemestre resaSemestre = resasEnum.nextElement();
				LogReservationSemestre logSemestre = log.createLogReservationSemestresRelationship();
				logSemestre.setLogOldResaKey(resaKey);
				logSemestre.setLogOldHcompRec(resaSemestre.hcompRec());
				logSemestre.setMaquetteSemestreRelationship(resaSemestre.semestre());
				logSemestre.setScolGroupeGrpRelationship(resaSemestre.scolGroupeGrp());
			}
		}
	}

	public void validateForInsert() throws NSValidation.ValidationException {
		this.validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForInsert();
	}

	public void validateForUpdate() throws NSValidation.ValidationException {
		this.validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForUpdate();
	}

	public void validateForDelete() throws NSValidation.ValidationException {
		super.validateForDelete();
	}

	/**
	 * Apparemment cette methode n'est pas appelée.
	 * 
	 * @see com.webobjects.eocontrol.EOValidation#validateForUpdate()
	 */
	public void validateForSave() throws NSValidation.ValidationException {
		validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForSave();

	}

	/**
	 * Peut etre appele à partir des factories.
	 * 
	 * @throws NSValidation.ValidationException
	 */
	public void validateObjectMetier() throws NSValidation.ValidationException {

	}

	/**
	 * A appeler par les validateforsave, forinsert, forupdate.
	 * 
	 */
	private final void validateBeforeTransactionSave() throws NSValidation.ValidationException {

	}

}
