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
package edtscol.client.planningview;

import java.util.Calendar;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

public class ReservationClassifier {

	public ReservationClassifier() {
	}

	/**
	 * permet de classifier les reservations pour le planning graphique voir les methodes appellees pour plus de details
	 */
	public NSArray classifyReservations(NSArray reservations) {

		NSMutableArray resaLundi, resaMardi, resaMercredi, resaJeudi, resaVendredi, resaSamedi, resaDimanche;

		NSArray sortedResa = null;
		try {
			sortedResa = reservations.sortedArrayUsingComparator(new ReservComparator());
		}
		catch (NSComparator.ComparisonException e) {
			NSLog.out.appendln("erreur sort : " + e.getMessage());
		}

		resaLundi = new NSMutableArray();
		resaMardi = new NSMutableArray();
		resaMercredi = new NSMutableArray();
		resaJeudi = new NSMutableArray();
		resaVendredi = new NSMutableArray();
		resaSamedi = new NSMutableArray();
		resaDimanche = new NSMutableArray();

		for (int i = 0; i < sortedResa.count(); i++) {

			NSDictionary currentResa = (NSDictionary) sortedResa.objectAtIndex(i);
			Number jour = (Number) currentResa.valueForKey("jour");

			switch (jour.intValue()) {

			case Calendar.MONDAY:
				resaLundi.addObject(currentResa);
				break;

			case Calendar.TUESDAY:
				resaMardi.addObject(currentResa);
				break;

			case Calendar.WEDNESDAY:
				resaMercredi.addObject(currentResa);
				break;

			case Calendar.THURSDAY:
				resaJeudi.addObject(currentResa);
				break;

			case Calendar.FRIDAY:
				resaVendredi.addObject(currentResa);
				break;

			case Calendar.SATURDAY:
				resaSamedi.addObject(currentResa);
				break;

			case Calendar.SUNDAY:
				resaDimanche.addObject(currentResa);
				break;
			}
		}

		NSMutableArray classifiedResa = new NSMutableArray();

		if (resaLundi.count() > 0) {
			classifiedResa.addObjectsFromArray(classifyForDay(resaLundi));
		}

		if (resaMardi.count() > 0) {
			classifiedResa.addObjectsFromArray(classifyForDay(resaMardi));
		}

		if (resaMercredi.count() > 0) {
			classifiedResa.addObjectsFromArray(classifyForDay(resaMercredi));
		}

		if (resaJeudi.count() > 0) {
			classifiedResa.addObjectsFromArray(classifyForDay(resaJeudi));
		}

		if (resaVendredi.count() > 0) {
			classifiedResa.addObjectsFromArray(classifyForDay(resaVendredi));
		}

		if (resaSamedi.count() > 0) {
			classifiedResa.addObjectsFromArray(classifyForDay(resaSamedi));
		}

		if (resaDimanche.count() > 0) {
			classifiedResa.addObjectsFromArray(classifyForDay(resaDimanche));
		}

		return classifiedResa;
	}

	/** solution actuellement utilisee */
	public NSArray classifyForDayCurrent(NSArray resaDay) {

		NSMutableArray crossed = null;
		NSMutableArray total = new NSMutableArray();

		for (int i = 0; i < resaDay.count(); i++) {

			NSDictionary courante = (NSDictionary) resaDay.objectAtIndex(i);
			NSTimestamp fCourante = (NSTimestamp) courante.valueForKey("fin");
			boolean crossFound = false;
			int j = 0;
			for (j = i + 1; j < resaDay.count(); j++) {

				if (j == i + 1) {
					crossed = new NSMutableArray();
				}

				NSDictionary suivante = (NSDictionary) resaDay.objectAtIndex(j);
				NSTimestamp dSuivante = (NSTimestamp) suivante.valueForKey("debut");

				if (fCourante.compare(dSuivante) == NSComparator.OrderedDescending) { // dSuivante < fCourante : chevauchement
					crossFound = true;
					crossed.addObject(suivante);
				}
				else {
					break;
				}
			}

			if (crossFound) {
				crossed.addObject(courante);
				total.addObject(crossed);
				i = j - 1;
			}
			else {
				total.addObject(courante);
				crossFound = false;
			}
		} // for

		return total;

	}

	/** solution actuellement en developpement */
	public NSArray classifyForDay(NSArray resaDay) {

		NSMutableArray crossed = null;
		NSMutableArray total = new NSMutableArray();

		for (int i = 0; i < resaDay.count(); i++) {

			NSDictionary courante = (NSDictionary) resaDay.objectAtIndex(i);
			NSTimestamp fCourante = (NSTimestamp) courante.valueForKey("fin");
			NSTimestamp finMax = fCourante;
			boolean crossFound = false;
			int j = 0;
			for (j = i + 1; j < resaDay.count(); j++) {

				if (j == i + 1) {
					crossed = new NSMutableArray();
				}

				NSDictionary suivante = (NSDictionary) resaDay.objectAtIndex(j);
				NSTimestamp dSuivante = (NSTimestamp) suivante.valueForKey("debut");
				NSTimestamp fSuivante = (NSTimestamp) suivante.valueForKey("fin");

				if (finMax.compare(dSuivante) == NSComparator.OrderedDescending) { // dSuivante < fCourante : chevauchement
					if (finMax.compare(fSuivante) == NSComparator.OrderedAscending) {
						finMax = fSuivante;
					}
					crossFound = true;
					crossed.addObject(suivante);
				}
				else {
					break;
				}
			}

			if (crossFound) {
				crossed.addObject(courante);
				total.addObject(crossed);
				i = j - 1;
			}
			else {
				total.addObject(courante);
				crossFound = false;
			}
		} // for

		return total;

	}

	/** permet de comparer chaques créneau par sa date de debut */
	public class ReservComparator extends NSComparator {

		public int compare(Object object1, Object object2) throws NSComparator.ComparisonException {

			if (!(object1 instanceof NSDictionary) || !(object2 instanceof NSDictionary)) {
				throw new NSComparator.ComparisonException("Les objets compares doivent etres des instances de Hashtable");
			}

			NSDictionary o1 = (NSDictionary) object1;
			NSDictionary o2 = (NSDictionary) object2;

			if (!(o1.allKeys().containsObject("debut")) || !(o2.allKeys().containsObject("debut")) || !(o1.allKeys().containsObject("fin"))
					|| !(o2.allKeys().containsObject("fin"))) {
				throw new NSComparator.ComparisonException("Les objets compares doivent contenir debut et fin");
			}

			NSTimestamp time1 = (NSTimestamp) o1.valueForKey("debut");
			NSTimestamp time2 = (NSTimestamp) o2.valueForKey("debut");

			return time1.compare(time2);
		}
	}

}
