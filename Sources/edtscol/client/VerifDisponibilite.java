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

import java.util.ArrayList;

import org.cocktail.superplan.client.metier.ContrainteHeure;
import org.cocktail.superplan.client.metier.ContrainteJour;
import org.cocktail.superplan.client.metier.ContrainteSemaine;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FournisUlr;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.Mission;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationObjet;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.TitreMission;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EODialogs;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.gestionreservation.InspecteurCtrl;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;

public class VerifDisponibilite {
	public static boolean verifDisponibiliteIndividu(InspecteurCtrl ic, IndividuUlr individu) {
		if (ic == null) {
			return true;
		}
		ic.reunionfactory.getNonDisponibiliteIndividu(calculerPeriodicites(ic), individu, null, null, null);
		return true;
	}

	public static NSArray<NSTimestamp> getNonDisponibiliteIndividu(EOEditingContext ec, NSArray<NSTimestamp> periodicites, IndividuUlr individu,
			Reservation resa, NSArray<Periodicite> periodicitesCache, NSArray<Mission> missionsCache) {
		if (periodicites == null || periodicites.count() == 0) {
			return NSArray.EmptyArray;
		}
		NSMutableArray<Periodicite> resas = new NSMutableArray<Periodicite>();
		if (periodicitesCache == null) {
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, individu));
			quals.addObject(qualifierFromPeriodicites(periodicites));
			if (resa != null) {
				quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, resa));
			}
			resas.addObjectsFromArray(Periodicite.fetchPeriodicites(ec, new EOAndQualifier(quals), null));
		}
		else {
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(qualifierFromPeriodicites(periodicites));
			if (resa != null) {
				quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, resa));
			}
			NSArray<Periodicite> filteredResas = EOQualifier.filteredArrayWithQualifier(periodicitesCache, new EOAndQualifier(quals));
			for (int i = 0; i < filteredResas.count(); i++) {
				Periodicite p = filteredResas.objectAtIndex(i);
				NSArray<Occupant> occupants = p.reservation().occupants(
						new EOKeyValueQualifier(Occupant.INDIVIDU_KEY, EOKeyValueQualifier.QualifierOperatorEqual, individu));
				if (occupants != null && occupants.count() > 0) {
					resas.addObject(p);
				}
			}
		}

		NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
		for (int i = 0; i < resas.count(); i++) {
			Periodicite period = resas.objectAtIndex(i);
			retour.addObject(period.dateDeb());
			retour.addObject(period.dateFin());
		}

		// les missions
		if (((MainClient) EOApplication.sharedApplication()).isAppControleMissions()) {
			retour.addObjectsFromArray(getPeriodicitesMission(ec, periodicites, individu, missionsCache));
		}

		// contraintes
		retour.addObjectsFromArray(getPeriodicitesContraintes(ec, periodicites, individu));

		return retour;
	}

	public static boolean verifDisponibiliteSalle(InspecteurCtrl ic, Salles salle) {
		if (ic == null) {
			return true;
		}
		NSArray<NSTimestamp> dispoInd = ic.reunionfactory.getNonDisponibiliteSalle(calculerPeriodicites(ic), salle, ic.getReservation(), null);
		if (dispoInd.count() > 0) {
			NSTimestamp d1 = dispoInd.objectAtIndex(0);
			NSTimestamp d2 = dispoInd.objectAtIndex(1);
			String msg = "La salle " + salle + " n'est pas libre entre le ";
			String FORMAT = "%d/%m/%Y %H:%M";
			msg = msg + FormatHandler.dateToStr(d1, FORMAT) + " et le " + FormatHandler.dateToStr(d2, FORMAT);
			WindowHandler.showError(msg);
			return false;
		}
		return true;
	}

	public static NSArray<NSTimestamp> getNonDisponibiliteSalle(EOEditingContext ec, NSArray<NSTimestamp> periodicites, Salles salle,
			Reservation resa, NSArray<Periodicite> periodicitesCache) {
		try {
			if (periodicites == null || periodicites.count() == 0) {
				return NSArray.EmptyArray;
			}
			NSMutableArray<Periodicite> resas = new NSMutableArray<Periodicite>();
			if (periodicitesCache == null) {
				NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
				quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY,
						EOKeyValueQualifier.QualifierOperatorEqual, salle));
				quals.addObject(qualifierFromPeriodicites(periodicites));
				if (resa != null) {
					quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, resa));
				}
				resas.addObjectsFromArray(Periodicite.fetchPeriodicites(ec, new EOAndQualifier(quals), null));
			}
			else {
				NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
				quals.addObject(qualifierFromPeriodicites(periodicites));
				if (resa != null) {
					quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, resa));
				}
				NSArray<Periodicite> filteredResas = EOQualifier.filteredArrayWithQualifier(periodicitesCache, new EOAndQualifier(quals));
				for (int i = 0; i < filteredResas.count(); i++) {
					Periodicite p = filteredResas.objectAtIndex(i);
					NSArray<ResaSalles> resaSalles = p.reservation().resaSalles(
							new EOKeyValueQualifier(ResaSalles.SALLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, salle));
					if (resaSalles != null && resaSalles.count() > 0) {
						resas.addObject(p);
					}
				}
			}
			NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
			for (int i = 0; i < resas.count(); i++) {
				Periodicite period = resas.objectAtIndex(i);
				retour.addObject(period.dateDeb());
				retour.addObject(period.dateFin());
			}
			return retour;
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	public static boolean verifDisponibiliteObjet(InspecteurCtrl ic, ResaObjet objet) {
		if (ic == null) {
			return true;
		}
		NSArray<NSTimestamp> dispoInd = ic.reunionfactory.getNonDisponibiliteObjet(calculerPeriodicites(ic), objet, null, null);
		if (dispoInd.count() > 0) {
			NSTimestamp d1 = dispoInd.objectAtIndex(0);
			NSTimestamp d2 = dispoInd.objectAtIndex(1);
			String msg = "L'objet " + objet + " n'est pas libre entre le ";
			String FORMAT = "%d/%m/%Y %H:%M";
			msg = msg + FormatHandler.dateToStr(d1, FORMAT) + " et le " + FormatHandler.dateToStr(d2, FORMAT);
			EODialogs.runErrorDialog("ATTENTION", msg);
			return false;
		}
		return true;
	}

	public static NSArray<NSTimestamp> getNonDisponibiliteObjet(EOEditingContext ec, NSArray<NSTimestamp> periodicites, ResaObjet objet,
			Reservation resa, NSArray<Periodicite> periodicitesCache) {
		if (periodicites == null || periodicites.count() == 0) {
			return NSArray.EmptyArray;
		}
		NSMutableArray<Periodicite> resas = new NSMutableArray<Periodicite>();
		if (periodicitesCache == null) {
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_OBJETS_KEY + "."
					+ ReservationObjet.RESA_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, objet));
			quals.addObject(qualifierFromPeriodicites(periodicites));
			if (resa != null) {
				quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, resa));
			}
			resas.addObjectsFromArray(Periodicite.fetchPeriodicites(ec, new EOAndQualifier(quals), null));
		}
		else {
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(qualifierFromPeriodicites(periodicites));
			if (resa != null) {
				quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, resa));
			}
			NSArray<Periodicite> filteredResas = EOQualifier.filteredArrayWithQualifier(periodicitesCache, new EOAndQualifier(quals));
			for (int i = 0; i < filteredResas.count(); i++) {
				Periodicite p = filteredResas.objectAtIndex(i);
				NSArray<ReservationObjet> reservationObjets = p.reservation().reservationObjets(
						new EOKeyValueQualifier(ReservationObjet.RESA_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, objet));
				if (reservationObjets != null && reservationObjets.count() > 0) {
					resas.addObject(p);
				}
			}
		}
		NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
		for (int i = 0; i < resas.count(); i++) {
			Periodicite period = resas.objectAtIndex(i);
			retour.addObject(period.dateDeb());
			retour.addObject(period.dateFin());
		}
		return retour;
	}

	public static EOQualifier qualifierFromPeriodicites(NSArray<NSTimestamp> periodicites) {
		if (periodicites != null) {
			NSMutableArray<EOQualifier> qDates = new NSMutableArray<EOQualifier>();
			for (int i = 0; i < periodicites.count(); i += 2) {
				NSTimestamp debut = periodicites.objectAtIndex(i);
				NSTimestamp fin = periodicites.objectAtIndex(i + 1);
				EOQualifier qual1 = new EOKeyValueQualifier(Periodicite.DATE_DEB_KEY, EOKeyValueQualifier.QualifierOperatorLessThan, fin);
				EOQualifier qual2 = new EOKeyValueQualifier(Periodicite.DATE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThan, debut);
				qDates.addObject(new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qual1, qual2 })));
			}
			return new EOOrQualifier(qDates);
		}
		return null;
	}

	private static NSArray<NSTimestamp> getPeriodicitesContraintes(EOEditingContext ec, NSArray<NSTimestamp> periodTest, IndividuUlr individu) {
		try {
			NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
			EOQualifier qContraintes = new EOKeyValueQualifier(ContrainteHeure.CONTRAINTE_JOUR_KEY + "." + ContrainteJour.CONTRAINTE_SEMAINE_KEY
					+ "." + ContrainteSemaine.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual, individu);

			NSMutableArray<EOQualifier> qDates = new NSMutableArray<EOQualifier>();
			for (int i = 0; i < periodTest.count(); i += 2) {
				NSTimestamp debut = periodTest.objectAtIndex(i);
				NSTimestamp fin = periodTest.objectAtIndex(i + 1);
				EOQualifier q1 = new EOKeyValueQualifier(ContrainteHeure.CTH_HEURE_DEBUT_KEY, EOKeyValueQualifier.QualifierOperatorLessThan, fin);
				EOQualifier q2 = new EOKeyValueQualifier(ContrainteHeure.CTH_HEURE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThan, debut);
				qDates.addObject(new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { q1, q2 })));
			}
			EOQualifier qualifier = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qContraintes, new EOOrQualifier(qDates) }));
			NSArray<ContrainteHeure> contraintes = ContrainteHeure.fetchContrainteHeures(ec, qualifier, null);
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

	private static NSArray<NSTimestamp> getPeriodicitesMission(EOEditingContext ec, NSArray<NSTimestamp> periodTest, IndividuUlr individu,
			NSArray<Mission> missionsCache) {
		NSMutableArray<NSTimestamp> retour = new NSMutableArray<NSTimestamp>();
		NSArray<Mission> missions = getMissions(ec, periodTest, individu, missionsCache);
		for (int i = 0; i < missions.count(); i++) {
			Mission currentMission = missions.objectAtIndex(i);
			retour.addObject(currentMission.misDebut());
			retour.addObject(currentMission.misFin());
		}
		return retour;
	}

	public static NSArray<Mission> getMissions(EOEditingContext ec, NSArray<NSTimestamp> periodicites, IndividuUlr individu,
			NSArray<Mission> missionsCache) {
		if (periodicites == null || periodicites.count() == 0 || individu == null) {
			return NSArray.EmptyArray;
		}
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.addObject(qualifierMissionFromPeriodicites(periodicites));
		quals.addObject(new EOKeyValueQualifier(Mission.FOURNIS_KEY + "." + FournisUlr.PERS_ID_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
				individu.persId()));
		quals.addObject(new EOKeyValueQualifier(Mission.MIS_ETAT_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, "ANNULEE"));
		quals.addObject(new EOKeyValueQualifier(Mission.C_CORPS_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, "IEXT"));
		quals.addObject(new EOKeyValueQualifier(Mission.TITRE_MISSION_KEY + "." + TitreMission.TIT_CTRL_DATES_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, new Integer(1)));
		if (missionsCache == null) {
			return Mission.fetchMissions(ec, new EOAndQualifier(quals), null);
		}
		else {
			return EOQualifier.filteredArrayWithQualifier(missionsCache, new EOAndQualifier(quals));
		}
	}

	private static EOQualifier qualifierMissionFromPeriodicites(NSArray<NSTimestamp> periodicites) {
		NSMutableArray<EOQualifier> qDates = new NSMutableArray<EOQualifier>();
		for (int i = 0; i < periodicites.count(); i += 2) {
			NSTimestamp debut = periodicites.objectAtIndex(i);
			NSTimestamp fin = periodicites.objectAtIndex(i + 1);
			EOQualifier q1 = new EOKeyValueQualifier(Mission.MIS_DEBUT_KEY, EOKeyValueQualifier.QualifierOperatorLessThan, fin);
			EOQualifier q2 = new EOKeyValueQualifier(Mission.MIS_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThan, debut);
			qDates.addObject(new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { q1, q2 })));
		}
		return new EOOrQualifier(qDates);
	}

	private static NSMutableArray<NSTimestamp> calculerPeriodicites(InspecteurCtrl ic) {
		String hdeb = ic.getMyView().getTHrDeb().getText(), hfin = ic.getMyView().getTHrFin().getText(), mdeb = ic.getMyView().getTMinDeb().getText(), mfin = ic
				.getMyView().getTMinFin().getText(), semaineExcel = ic.getMyView().getTSemaines().getText();
		int annee = ((FormationAnnee) ic.getMyView().getComboAnnees().getSelectedItem()).fannKey().intValue();
		ArrayList<Integer> selectedDays = ic.dayList.getSelectedDays();
		NSMutableArray<NSTimestamp> tmperiod = new NSMutableArray<NSTimestamp>();
		int currentJour;
		for (int iJour = 0; iJour < selectedDays.size(); iJour++) {
			currentJour = (selectedDays.get(iJour)).intValue();
			NSArray<NSTimestamp> block = new NSArray<NSTimestamp>();
			try {
				block = ic.timeHandler.computePeriodicites(currentJour, semaineExcel, hdeb, hfin, mdeb, mfin, annee);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			tmperiod.addObjectsFromArray(block);
		}
		NSMutableArray<NSTimestamp> periodicites = new NSMutableArray<NSTimestamp>();
		tmperiod = TimeHandler.testCoherenceDates(tmperiod);
		if (tmperiod == null) {
			return null;
		}

		for (int idate = 0; idate < tmperiod.count(); idate++) {
			NSTimestamp currentDateTest = tmperiod.objectAtIndex(idate);
			periodicites.addObject(currentDateTest);
		}
		return periodicites;
	}
}
