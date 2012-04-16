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
package fr.univlr.common.utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.webobjects.foundation.NSTimestamp;

public class CalendarHandler {

	private GregorianCalendar calendar;

	public CalendarHandler() {
		// calendar = new GregorianCalendar( NSTimeZone.systemTimeZone() );
		calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
	}

	/** retourne l'annee en cours : indiquee par le calendrier */
	public int currentYear() {
		return calendar.get(Calendar.YEAR);
	}

	public int getDay(NSTimestamp time) {
		calendar.setTime(time);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public void raz() {
		calendar.setTime(new NSTimestamp());
	}

	public void setHour(int hour) {
		calendar.set(Calendar.HOUR_OF_DAY, hour);
	}

	public void setMinute(int min) {
		calendar.set(Calendar.MINUTE, min);
	}

	public void setDay(int day) {
		calendar.add(Calendar.DAY_OF_MONTH, day);
	}

	public void setMonth(int month) {
		calendar.add(Calendar.MONTH, month);
	}

	public void setYear(int year) {
		calendar.add(Calendar.YEAR, year);
	}

	public void setTime(NSTimestamp time) {
		calendar.setTime(time);
	}

	public NSTimestamp getTime() {
		return new NSTimestamp(calendar.getTime());
	}
}
