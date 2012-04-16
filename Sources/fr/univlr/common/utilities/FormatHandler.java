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

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

public class FormatHandler {

	private static final String STD_FORMAT = "%d/%m/%Y %H:%M:%S";

	/* Gestion des nombres, des dates et chaines */

	/** renvoie le Integer representant le String */
	public static Integer strToInteger(String str) {
		try {
			return new Integer(str);
		}
		catch (Exception e) {
			return new Integer(-1);
		}
	}

	/** renvoie le int representant le String */
	public static int strToInt(String str) {
		try {
			return strToInteger(str).intValue();
		}
		catch (Exception e) {
			return -1;
		}
	}

	public static int numberOfWeeksInYear(int year) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(2);
		cal.setMinimalDaysInFirstWeek(4);
		cal.set(1, year);
		cal.setTime(cal.getTime());
		return cal.getMaximum(3);
	}

	/* Gestion des formats de dates */

	/** obtenir la chaine representant l'instant date */
	public static String dateToStr(NSTimestamp date) {
		return dateToStr(date, "%d/%m/%Y");
	}

	/** obtenir la chaine representant l'instant date */
	public static String dateToStr(NSTimestamp date, String format) {
		NSTimestampFormatter formatter = new NSTimestampFormatter(format);
		return formatter.format(date);
	}

	/** obtenir l'objet date a partir de la chaine et le format */
	public static NSTimestamp strToDate(String strDate) {
		return strToDate(strDate, "%d/%m/%Y %H:%M:%S");
	}

	/** obtenir l'objet date a partir de la chaine et le format */
	public static NSTimestamp strToDate(String strDate, String format) {

		NSTimestampFormatter formatter = new NSTimestampFormatter(format);
		try {
			return (NSTimestamp) formatter.parseObject(strDate, new ParsePosition(0));
		}
		catch (Exception e) {
			return null;
		}
	}

	/** reformate la date pour minuit */
	public static NSTimestamp midnightTime(NSTimestamp time) {
		String strDate = dateToStr(time);
		return strToDate(strDate + " 00:00:00", "%d/%m/%Y %H:%M:%S");
	}

	/** reformate la date pour minuit */
	public static NSTimestamp midnightTime(String sTime) {
		return strToDate(sTime + " 00:00:00", "%d/%m/%Y %H:%M:%S");
	}

	/** reformate la date pour la fin du jour */
	public static NSTimestamp endOfDay(NSTimestamp time) {
		String strDate = dateToStr(dateInLocalTZ(time));
		// System.out.println(" time " + time + " strDate " + strDate);
		return strToDate(strDate + " 23:59:59", "%d/%m/%Y %H:%M:%S");
	}

	/** reformate la date pour la fin du jour */
	public static NSTimestamp endOfDay(String sTime) {
		return strToDate(sTime + " 23:59:59", "%d/%m/%Y %H:%M:%S");
	}

	public static String getShortDayName(NSTimestamp date) {
		return getDayName(date).substring(0, 3);
	}

	public static String getDayName(NSTimestamp date) {
		DateFormat f = new SimpleDateFormat("EEEE");
		try {
			return f.format(date);
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/** reconstruit le NSTimestamp en utilisant le TimeZone de l'application : pour les dates venant de tables enregistrees en GMT */
	public static NSTimestamp dateInLocalTZ(NSTimestamp date) {
		NSTimestampFormatter formatter = new NSTimestampFormatter("%d/%m/%Y %H:%M:%S");
		String sDate = formatter.format(date);
		return FormatHandler.strToDate(sDate, STD_FORMAT);
	}

	/** retourne le nom du jour de la semaine */
	public static String intToDay(int day) {
		String retour = "";
		switch (day) {
		case Calendar.MONDAY:
			retour = "Lundi";
			break;
		case Calendar.TUESDAY:
			retour = "Mardi";
			break;
		case Calendar.WEDNESDAY:
			retour = "Mercredi";
			break;
		case Calendar.THURSDAY:
			retour = "Jeudi";
			break;
		case Calendar.FRIDAY:
			retour = "Vendredi";
			break;
		case Calendar.SATURDAY:
			retour = "Samedi";
			break;
		case Calendar.SUNDAY:
			retour = "Dimanche";
			break;
		}
		return retour;
	}

	/** retourne le nom du jour de la semaine */
	public static String dayString(NSTimestamp date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String retour = "";
		switch (day) {
		case Calendar.MONDAY:
			retour = "Lundi";
			break;
		case Calendar.TUESDAY:
			retour = "Mardi";
			break;
		case Calendar.WEDNESDAY:
			retour = "Mercredi";
			break;
		case Calendar.THURSDAY:
			retour = "Jeudi";
			break;
		case Calendar.FRIDAY:
			retour = "Vendredi";
			break;
		case Calendar.SATURDAY:
			retour = "Samedi";
			break;
		case Calendar.SUNDAY:
			retour = "Dimanche";
			break;
		}
		return retour;
	}

	/**
	 * teste si nombre est dans [borneInf,borneSup] ou non.
	 */
	public static boolean inRange(int nombre, int borneInf, int borneSup) {

		if ((nombre == borneInf) || (nombre == borneSup)) {
			return true;
		}

		if ((nombre > borneInf) && (nombre < borneSup)) {
			return true;
		}

		return false;
	}

	/** retourne le jour du mois de la date passee */
	public static int dayOfMonth(NSTimestamp date) {
		String sDay = FormatHandler.dateToStr(date, "%d");
		return strToInt(sDay);
	}

	public static NSTimestamp replaceDayOfMonthInDate(int dayOfMonth, NSTimestamp date) {
		String repDate = dateToStr(date, "/%m/%Y %H:%M:%S");
		return strToDate(String.valueOf(dayOfMonth) + repDate, "%d/%m/%Y %H:%M:%S");
	}

	/** permet d'arrondir un double a la valeur la plus proche avec 2 decimales : ex 0.666667 -> 0.67 */
	public static double roundDouble(double number) {
		number *= 100;
		number = (int) (number + .5);
		number /= 100;
		return number;
	}

}
