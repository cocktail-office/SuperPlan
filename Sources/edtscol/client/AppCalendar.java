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

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

public class AppCalendar extends GregorianCalendar {

	private static final long serialVersionUID = 683078276580193259L;
	private static NSTimestamp debutSemaine;
	private static NSTimestamp finSemaine;

	public AppCalendar() {
		super();
		setFirstDayOfWeek(Calendar.MONDAY);
		setMinimalDaysInFirstWeek(4);
	}

	// static
	public static void storeDebutSemaine(NSTimestamp debut) {
		debutSemaine = debut;
	}

	public static NSTimestamp storedDebutSemaine() {
		return debutSemaine;
	}

	public static void storeFinSemaine(NSTimestamp fin) {
		finSemaine = fin;
	}

	public static NSTimestamp storedFinSemaine() {
		return finSemaine;
	}

	/** donne la date de debut de semaine */
	public NSTimestamp getStartWeekDate() {
		setTime(new NSTimestamp());
		set(DAY_OF_WEEK, MONDAY);
		return new NSTimestamp(getTime());
	}

	/** donne la date de fin de semaine */
	public NSTimestamp getEndWeekDate() {
		setTime(new NSTimestamp());
		set(DAY_OF_WEEK, SUNDAY);
		return new NSTimestamp(getTime());
	}

	public void setWeekNumber(int week) {
		set(DAY_OF_WEEK, MONDAY);
		set(WEEK_OF_YEAR, week);
		setTime(getTime());
	}

	public NSArray<NSTimestamp> nextRangeDateWeek() {
		// add(WEEK_OF_YEAR,1);
		add(DAY_OF_WEEK, 7);
		set(DAY_OF_WEEK, MONDAY);
		setTime(getTime());
		NSTimestamp start = new NSTimestamp(getTime());
		set(DAY_OF_WEEK, SUNDAY);
		setTime(getTime());
		NSTimestamp end = new NSTimestamp(getTime());
		return new NSArray<NSTimestamp>(new NSTimestamp[] { start, end });
	}

	public NSArray<NSTimestamp> previousRangeDateWeek() {
		add(DAY_OF_WEEK, -7);
		set(DAY_OF_WEEK, MONDAY);
		setTime(getTime());
		NSTimestamp start = new NSTimestamp(getTime());
		set(DAY_OF_WEEK, SUNDAY);
		setTime(getTime());
		NSTimestamp end = new NSTimestamp(getTime());
		return new NSArray<NSTimestamp>(new NSTimestamp[] { start, end });
	}

	/** calcul le debut et la fin de la semaine pour la date passee en parametre */
	public NSArray<NSTimestamp> rangeDateWeekForDate(NSTimestamp date) {
		setTime(date);
		set(DAY_OF_WEEK, MONDAY);
		setTime(getTime());
		NSTimestamp start = new NSTimestamp(getTime());
		set(DAY_OF_WEEK, SUNDAY);
		setTime(getTime());
		NSTimestamp end = new NSTimestamp(getTime());
		return new NSArray<NSTimestamp>(new NSTimestamp[] { start, end });
	}

	/** retourne l'annee en cours */
	public Number annee() {
		return new Integer(this.get(YEAR));
	}
}
