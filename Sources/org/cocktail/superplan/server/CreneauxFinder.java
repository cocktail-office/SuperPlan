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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.cocktail.superplan.server.metier.IndividuUlr;
import org.cocktail.superplan.server.metier.MaquetteAp;
import org.cocktail.superplan.server.metier.Occupant;
import org.cocktail.superplan.server.metier.ResaSalles;
import org.cocktail.superplan.server.metier.Reservation;
import org.cocktail.superplan.server.metier.ReservationAp;
import org.cocktail.superplan.server.metier.Salles;
import org.cocktail.superplan.server.metier.ScolGroupeGrp;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;

/** permet de recuperer des creneaux libres sur une periode donnee etant donnees des ressources */
public class CreneauxFinder {

	private Verification verification;

	public CreneauxFinder(EOEditingContext eContext) {
		verification = new Verification(eContext);
	}

	/** recherche des creneaux libres sur les periodes contenues dans dateRech */
	public NSArray<NSTimestamp> findCreneauxLibresForResa(Reservation resa, NSArray<NSTimestamp> dateRech, int dureeMin) throws Exception {

		TimeHandler tHandler = new TimeHandler();
		NSMutableArray<NSTimestamp> somme = new NSMutableArray<NSTimestamp>();
		String formatD = "%d/%m/%Y";
		HashMap<Integer, String> listDaysFirstWeek = new HashMap<Integer, String>();

		NSTimestamp debPer1 = dateRech.objectAtIndex(0);
		NSTimestamp finPer1 = dateRech.objectAtIndex(1);
		int nbDays = TimeHandler.daysSeparatingDates(finPer1, debPer1);
		listDaysFirstWeek.put(new Integer(tHandler.dayOfWeek(debPer1)), FormatHandler.dateToStr(debPer1, formatD));
		listDaysFirstWeek.put(new Integer(tHandler.dayOfWeek(finPer1)), FormatHandler.dateToStr(finPer1, formatD));

		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setTime(debPer1);

		for (int i = 0; i < nbDays; i++) {
			cal.add(Calendar.DAY_OF_WEEK, 1);
			cal.setTime(cal.getTime());
			NSTimestamp date = new NSTimestamp(cal.getTime());
			listDaysFirstWeek.put(new Integer(tHandler.dayOfWeek(date)), FormatHandler.dateToStr(date, formatD));
		}

		// recherche pour la premiere periode :
		somme.addObjectsFromArray(this.getNonDisponibiliteRessources(resa, debPer1, finPer1));

		for (int k = 2; k < dateRech.count(); k += 2) {

			NSArray<NSTimestamp> localSomme = new NSArray<NSTimestamp>();
			try {
				localSomme = this.getNonDisponibiliteRessources(resa, debPer1, finPer1);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// les periodes suivantes : on recale les creneaux trouves sur la premiere semaine
			if (k > 1) {
				NSTimestamp lDeb, lFin;
				String lhDeb, lhFin;
				Integer tmpDay;
				String reqDate;

				for (int n = 0; n < localSomme.count(); n += 2) {
					lDeb = localSomme.objectAtIndex(n);
					lFin = localSomme.objectAtIndex(n + 1);
					tmpDay = new Integer(tHandler.dayOfWeek(lDeb));

					reqDate = listDaysFirstWeek.get(tmpDay);
					lhDeb = reqDate + " " + FormatHandler.dateToStr(lDeb, "%H:%M");
					tmpDay = new Integer(tHandler.dayOfWeek(lFin));

					reqDate = listDaysFirstWeek.get(tmpDay);
					lhFin = reqDate + " " + FormatHandler.dateToStr(lFin, "%H:%M");
					somme.addObject(FormatHandler.strToDate(lhDeb, "%d/%m/%Y %H:%M"));
					somme.addObject(FormatHandler.strToDate(lhFin, "%d/%m/%Y %H:%M"));
				}
			}
		}

		if (somme.count() == 0) {
			somme.addObjectsFromArray(new NSArray<NSTimestamp>(new NSTimestamp[] { dateRech.objectAtIndex(0), dateRech.objectAtIndex(1) }));
		}

		NSMutableArray<HashMap<String, NSTimestamp>> mapArray = new NSMutableArray<HashMap<String, NSTimestamp>>();
		for (int n = 0; n < somme.count(); n += 2) {
			HashMap<String, NSTimestamp> map = new HashMap<String, NSTimestamp>();
			map.put("debut", somme.objectAtIndex(n));
			map.put("fin", somme.objectAtIndex(n + 1));
			mapArray.addObject(map);
		}

		mapArray = DBHandler.retirerMultiples(mapArray);

		NSArray<HashMap<String, NSTimestamp>> creneauxTries = null;
		try {
			creneauxTries = mapArray.sortedArrayUsingComparator(new CreneauxComparator());
		}

		catch (Exception e) {
			e.printStackTrace();
			return NSArray.EmptyArray;
		}

		NSArray<HashMap<String, NSTimestamp>> creneauxLibres = this.calculCreneaux(creneauxTries, debPer1, finPer1, dureeMin);

		System.out.println("creneauxLibres = " + creneauxLibres);

		NSMutableArray<NSTimestamp> resultat = new NSMutableArray<NSTimestamp>();
		int i = 0;
		NSTimestamp leDebut, laFin;
		for (i = 0; i < creneauxLibres.count(); i++) {
			HashMap<String, NSTimestamp> currentCreneau = creneauxLibres.objectAtIndex(i);
			leDebut = currentCreneau.get("debut");
			laFin = currentCreneau.get("fin");
			resultat.addObject(leDebut);
			resultat.addObject(laFin);
		}

		return resultat;
	}

	/** retourne les dates de non disponibilites des ressources de la reservation sur la periode specifiee */
	private NSArray<NSTimestamp> getNonDisponibiliteRessources(Reservation resa, NSTimestamp debut, NSTimestamp fin) throws Exception {

		NSArray<IndividuUlr> occupants = (NSArray<IndividuUlr>) resa.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		NSArray<Salles> salles = (NSArray<Salles>) resa.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		NSArray<ReservationAp> enseignements = (NSArray<ReservationAp>) resa.valueForKey(Reservation.RESERVATION_AP_KEY);
		NSMutableArray<NSTimestamp> localSomme = new NSMutableArray<NSTimestamp>();

		NSArray<NSTimestamp> currentPeriode = new NSArray<NSTimestamp>(new NSTimestamp[] { debut, fin });

		int i = 0;
		for (i = 0; i < occupants.count(); i++) {
			IndividuUlr individu = occupants.objectAtIndex(i);
			localSomme.addObjectsFromArray(verification.getNonDisponibliteIndividu(currentPeriode, individu, null));
		}

		for (i = 0; i < salles.count(); i++) {
			Salles salle = salles.objectAtIndex(i);
			localSomme.addObjectsFromArray(verification.verifSallePourModification(null, currentPeriode, salle));
		}

		for (i = 0; i < enseignements.count(); i++) {
			ReservationAp resAp = enseignements.objectAtIndex(i);
			MaquetteAp currentAp = resAp.maquetteAp();
			ScolGroupeGrp currentGrp = resAp.scolGroupeGrp();
			if (currentGrp == null) {
				localSomme.addObjectsFromArray((NSArray<NSTimestamp>) verification.getNonDisponibiliteAp(null, currentPeriode, currentAp, false,
						true, ((Application) Application.application()).isAppControleApsOccupation(),
						((Application) Application.application()).isAppControleApsNonObligatoiresOccupation(), false).objectAtIndex(1));
			}
			else {
				localSomme.addObjectsFromArray((NSArray<NSTimestamp>) verification.getNonDisponibiliteGroupe(null, currentPeriode, currentAp,
						currentGrp, false, ((Application) Application.application()).isAppControleApsOccupation(),
						((Application) Application.application()).isAppControleApsNonObligatoiresOccupation(),
						((Application) Application.application()).config().booleanForKey("APP_CONTROLE_GROUPES_OCCUPATION"), false).objectAtIndex(1));
			}
		}

		// a faire.
		// for (i = 0; i < examens.count(); i++) {
		// ResaExamen resEx = (ResaExamen) examens.objectAtIndex(i);
		// }
		return localSomme;
	}

	/**
	 * parmis les resa passees en parametre dans un ordre trie ascendant, recupere les creneaux vides entre les resa, sous forme "dateDebut
	 * et dateFin"
	 */
	private NSArray<HashMap<String, NSTimestamp>> calculCreneaux(NSArray<HashMap<String, NSTimestamp>> creneauxTries, NSTimestamp debut,
			NSTimestamp fin, int dureeMinutes) {

		NSTimestamp finC, debS;

		NSMutableArray creneauxLibres = new NSMutableArray();

		for (int i = 0; i < creneauxTries.count(); i++) {
			HashMap<String, NSTimestamp> cCourant = creneauxTries.objectAtIndex(i);
			HashMap<String, NSTimestamp> cSuivant;

			if (i == 0) {
				NSTimestamp debCourant = cCourant.get("debut");

				finC = debut;
				debS = debCourant;

				if (finC.before(debS)) {
					int longueur = TimeHandler.minutesSeparatingDates(finC, debS);
					if (longueur >= dureeMinutes) {
						ArrayList<HashMap<String, NSTimestamp>> list = traiterCandidat(finC, debS, dureeMinutes);
						creneauxLibres.addObjects(list.toArray());
					}
				}

				if (creneauxTries.count() == 1) {
					finC = cCourant.get("fin");
					debS = fin;
					if (finC.before(debS)) {
						int longueur = TimeHandler.minutesSeparatingDates(finC, debS);
						if (longueur >= dureeMinutes) {
							ArrayList<HashMap<String, NSTimestamp>> list = traiterCandidat(finC, debS, dureeMinutes);
							creneauxLibres.addObjects(list.toArray());
						}
					}
				} //
			}
			else {
				if (i < creneauxTries.count() - 1) {
					cSuivant = creneauxTries.objectAtIndex(i + 1);
					debS = cSuivant.get("debut");
				}
				else {
					debS = fin;
				}
				finC = cCourant.get("fin");

				if (finC.before(debS)) {
					int longueur = TimeHandler.minutesSeparatingDates(finC, debS);
					if (longueur >= dureeMinutes) {
						ArrayList<HashMap<String, NSTimestamp>> list = traiterCandidat(finC, debS, dureeMinutes);
						creneauxLibres.addObjects(list.toArray());
					}
				}
			}
		}
		return creneauxLibres;
	}

	/** traite le creneau candidat et renvoie le decoupage prevu */
	private ArrayList<HashMap<String, NSTimestamp>> traiterCandidat(NSTimestamp debut, NSTimestamp fin, int reqDureeMinute) {

		ArrayList<HashMap<String, NSTimestamp>> list = new ArrayList<HashMap<String, NSTimestamp>>();
		int longueur = TimeHandler.minutesSeparatingDates(debut, fin);
		int jDeb = FormatHandler.dayOfMonth(debut);
		int jFin = FormatHandler.dayOfMonth(fin);
		int nbJ = jFin - jDeb;

		// decoupage en creneaux utiles.
		if (nbJ == 0 && longueur >= reqDureeMinute) {
			HashMap<String, NSTimestamp> creneau = new HashMap<String, NSTimestamp>();
			creneau.put("debut", debut);
			creneau.put("fin", fin);
			list.add(creneau);
		}
		else {

			for (int i = jDeb; i < jFin + 1; i++) {
				NSTimestamp currentDebut, currentFin, currentDay;
				if (i == jDeb) {
					currentDebut = debut;
					currentFin = FormatHandler.endOfDay(currentDebut);
				}
				else
					if (i == jFin) {
						currentDebut = FormatHandler.midnightTime(fin);
						currentFin = fin;
					}
					else {
						currentDay = FormatHandler.replaceDayOfMonthInDate(i, debut);
						currentDebut = FormatHandler.midnightTime(currentDay);
						currentFin = FormatHandler.endOfDay(currentDay);
					}

				HashMap<String, NSTimestamp> creneau = new HashMap<String, NSTimestamp>();
				if (i > jFin) {
					currentFin = fin;
				}
				longueur = TimeHandler.minutesSeparatingDates(currentDebut, currentFin);
				if (longueur >= reqDureeMinute) {
					creneau.put("debut", currentDebut);
					creneau.put("fin", currentFin);
				}
				list.add(creneau);
			}
		}
		return list;
	}

	/** permet de comparer chaques créneau par sa date de debut */
	private class CreneauxComparator extends NSComparator {

		public int compare(Object object1, Object object2) throws NSComparator.ComparisonException {

			if (!(object1 instanceof HashMap<?, ?>) || !(object2 instanceof HashMap<?, ?>)) {
				throw new NSComparator.ComparisonException("Les objets compares doivent etres des instances de HashMap");
			}

			HashMap<String, NSTimestamp> o1 = (HashMap<String, NSTimestamp>) object1;
			HashMap<String, NSTimestamp> o2 = (HashMap<String, NSTimestamp>) object2;

			if (!(o1.containsKey("debut")) || !(o2.containsKey("debut")) || !(o1.containsKey("fin")) || !(o2.containsKey("fin"))) {
				throw new NSComparator.ComparisonException("Les objets compares doivent contenir debut et fin !");
			}

			NSTimestamp time1 = o1.get("debut");
			NSTimestamp time2 = o2.get("debut");

			return time1.compare(time2);
		}
	}

}
