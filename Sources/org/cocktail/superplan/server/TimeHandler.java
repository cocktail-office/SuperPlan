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
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import org.cocktail.superplan.server.metier.Periodicite;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FixedCalendar;
import fr.univlr.common.utilities.FormatHandler;

public class TimeHandler extends GregorianCalendar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7354924058374100762L;
	private boolean useAnneeCivile = false;

	public TimeHandler() {
		super();
		setFirstDayOfWeek(Calendar.MONDAY);
		setMinimalDaysInFirstWeek(4);
	}

	/** donne date de debut de semaine */
	public NSTimestamp getStartDateForWeek(int year, int week) {
		this.set(YEAR, year);
		this.set(WEEK_OF_YEAR, week);
		return new NSTimestamp(this.getTime());
	}

	/** donne date de fin de semaine */
	public NSTimestamp getEndDateForWeek(int year, int week) {
		this.getStartDateForWeek(year, week);
		this.add(WEEK_OF_YEAR, 1);
		return new NSTimestamp(this.getTime());
	}

	/** donne date de debut et de fin du creneau de semaines passe */
	public NSArray getDateRangeForWeekRange(int year, int day, int hdeb, int mdeb, int hfin, int mfin, int startWeek, int endWeek) {
		NSMutableArray periods = new NSMutableArray();
		this.add(WEEK_OF_YEAR, (endWeek - startWeek));

		for (int iweek = startWeek; iweek < endWeek + 1; iweek++) {
			Object[] retour = getDateRangeForWeek(year, day, hdeb, mdeb, hfin, mfin, iweek);
			periods.addObject(retour);
		}
		return periods;
	}

	/** renvoi la date exacte en fonction des parametres de temps passes */
	public Object[] getDateRangeForWeek(int year, int day, int hdeb, int mdeb, int hfin, int mfin, int week) {
		this.set(YEAR, year);
		this.set(WEEK_OF_YEAR, week);
		this.set(DAY_OF_WEEK, day);
		this.set(HOUR_OF_DAY, hdeb);
		this.set(MINUTE, mdeb);
		this.set(SECOND, 0);
		NSTimestamp startdate = new NSTimestamp(this.getTime());
		this.set(HOUR_OF_DAY, hfin);
		this.set(MINUTE, mfin);
		NSTimestamp enddate = new NSTimestamp(this.getTime());
		return new Object[] { startdate, enddate };
	}

	/** remet le calendrier a la date courante */
	public void raz() {
		this.setTime(new NSTimestamp());
	}

	/** renvoie le numero de semaine */
	public int weekOfYear(NSTimestamp time) {
		this.setTime(time);
		return this.get(WEEK_OF_YEAR);
	}

	/** retourne la date de debut et de fin de semaine */
	public Object[] getWeek(int week, int ews, int bws, int maxWeek, int year) throws fr.univlr.common.utilities.EdtException {

		int currentYear = year;
		boolean semaineValide;

		if (FormatHandler.inRange(week, bws, maxWeek)) {
			semaineValide = true;
		}
		else
			if (FormatHandler.inRange(week, 0, ews)) {
				semaineValide = true;
				currentYear = year + 1;
			}
			else {
				semaineValide = false;
			}

		if (!semaineValide) {
			throw new fr.univlr.common.utilities.EdtException("la semaine " + String.valueOf(week) + " n'est pas une semaine valide");
		}

		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, currentYear);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.setTime(cal.getTime());
		NSTimestamp debut = new NSTimestamp(cal.getTime());

		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.setTime(cal.getTime());
		NSTimestamp fin = new NSTimestamp(cal.getTime());

		return new Object[] { debut, fin };
	}

	/** renvoie le jour de la semaine */
	public int dayOfWeek(NSTimestamp time) {
		this.setTime(time);
		return this.get(DAY_OF_WEEK);
	}

	/**
	 * permet de calculer les periodicites en fonction des donnees saisies/choisies, dans le cas de plusieurs jours (donnes sous forme de
	 * Range (jourdebut,jourfin) ). les periodicites sont sous forme { ddebut1, dfin1, ddebut2, dfin2 ....}, de NSTimestamp
	 */
	public NSArray computePeriodicitesWithDaysRange(int jDeb, int jFin, String semaineExcel, String shdeb, String shfin, String smdeb, String smfin,
			int annee) {

		NSMutableArray periods = new NSMutableArray();

		for (int iday = jDeb; iday < jFin + 1; iday++) {
			periods.addObjectsFromArray(computePeriodicites(iday, semaineExcel, shdeb, shfin, smdeb, smfin, annee));
		}
		return periods;
	}

	/**
	 * permet de calculer les periodicites en fonction des donnees saisies/choisies, dans le cas de plusieurs jours. les periodicites sont
	 * sous forme { ddebut1, dfin1, ddebut2, dfin2 ....}, de NSTimestamp
	 */
	public NSArray computePeriodicitesWithDays(NSArray days, String semaineExcel, String shdeb, String shfin, String smdeb, String smfin, int annee) {

		NSMutableArray periods = new NSMutableArray();

		for (int i = 0; i < days.count(); i++) {
			int day = ((Number) days.objectAtIndex(i)).intValue();
			periods.addObjectsFromArray(computePeriodicites(day, semaineExcel, shdeb, shfin, smdeb, smfin, annee));
		}
		return periods;
	}

	/**
	 * permet de calculer les periodicites en fonction des donnees saisies/choisies les periodicites sont sous forme { ddebut1, dfin1,
	 * ddebut2, dfin2 ....}, de NSTimestamp
	 */
	public NSArray computePeriodicites(int dayOfWeek, String semaineExcel, String shdeb, String shfin, String smdeb, String smfin, int annee) {
		NSArray creneaux = NSArray.componentsSeparatedByString(semaineExcel, ";");
		NSMutableArray periodicites = new NSMutableArray();

		int hdeb = FormatHandler.strToInt(shdeb);
		int hfin = FormatHandler.strToInt(shfin);
		int mdeb = FormatHandler.strToInt(smdeb);
		int mfin = FormatHandler.strToInt(smfin);

		for (int i = 0; i < creneaux.count(); i++) {
			NSArray elements = NSArray.componentsSeparatedByString((String) creneaux.objectAtIndex(i), "-");
			if (elements.count() > 1) {
				int startweek = FormatHandler.strToInt((String) elements.objectAtIndex(0));
				int endweek = FormatHandler.strToInt((String) elements.objectAtIndex(1));
				NSArray periodicite = this.getDateRangeForWeekRange(annee, dayOfWeek, hdeb, mdeb, hfin, mfin, startweek, endweek);
				for (int iper = 0; iper < periodicite.count(); iper++) {
					Object[] creneau = (Object[]) periodicite.objectAtIndex(iper);
					periodicites.addObject(creneau[0]);
					periodicites.addObject(creneau[1]);
				}
			}
			else {
				int week = FormatHandler.strToInt((String) elements.objectAtIndex(0));
				Object[] creneau = this.getDateRangeForWeek(annee, dayOfWeek, hdeb, mdeb, hfin, mfin, week);
				periodicites.addObject(creneau[0]);
				periodicites.addObject(creneau[1]);
			}
		}
		return periodicites;
	}

	/** permet de calculer les periodicites en fonction des donnees saisies/choisies */
	public Hashtable decomputePeriodicites(NSArray lesPeriodicites) {

		NSArray periodicites;

		try {
			periodicites = lesPeriodicites.sortedArrayUsingComparator(new PeriodicitesComparator());
		}
		catch (Exception e) {
			e.printStackTrace();
			periodicites = new NSArray();
		}

		Hashtable retour = new Hashtable();
		ArrayList lst = new ArrayList();

		for (int i = 0; i < periodicites.count(); i++) {
			Periodicite period = (Periodicite) periodicites.objectAtIndex(i);
			NSTimestamp dateDeb = period.dateDeb();
			NSTimestamp dateFin = period.dateFin();

			Integer week = new Integer(this.weekOfYear(dateDeb));

			lst.add(week);

			if (i == 0) {
				String heureDebut = FormatHandler.dateToStr(dateDeb, "%H:%M");
				String heureFin = FormatHandler.dateToStr(dateFin, "%H:%M");
				retour.put("heureDeb", heureDebut);
				retour.put("heureFin", heureFin);
				retour.put("jour", new Integer(this.dayOfWeek(dateDeb)));
			}
		} // for
		retour.put("semaines", this.semainesToExcel(lst));
		return retour;
	}

	/**
	 * donne les numeros de semaines decrites dans la semaine au format excel, exemple : 1-3;5;8-13 BUG : Nombres de semaines/annee
	 * incorrectement calculé
	 */
	public static ArrayList getAllWeeksFromString(String str, int year, boolean sortIt) {

		int maxWeek = FormatHandler.numberOfWeeksInYear(year);
		ArrayList weekList = new ArrayList();
		NSArray creneaux = NSArray.componentsSeparatedByString(str, ";");
		for (int i = 0; i < creneaux.count(); i++) {
			NSArray elements = NSArray.componentsSeparatedByString((String) creneaux.objectAtIndex(i), "-");
			if (elements.count() > 1) { // cas ou l'utilisateur a saisi une fourchette de semaines
				int startweek = FormatHandler.strToInt((String) elements.objectAtIndex(0));
				int endweek = FormatHandler.strToInt((String) elements.objectAtIndex(1));

				if (startweek < endweek) {
					for (int id = startweek; id < endweek + 1; id++) {
						weekList.add(new Integer(id));
					}
				}
				else {
					for (int id = startweek; id < maxWeek + 1; id++) {
						weekList.add(new Integer(id));
					}
					for (int id = 1; id < endweek + 1; id++) {
						weekList.add(new Integer(id));
					}
				}
			}
			else { // cas ou l'utilisation a saisi une semaine et pas une fourchette de semaines.
				int week = FormatHandler.strToInt((String) elements.objectAtIndex(0));
				weekList.add(new Integer(week));
			}
		}
		if (sortIt) {
			Collections.sort(weekList);
		}

		return weekList;
	}

	/** convertie les periodicites vers de nouvelles semaines */
	public NSTimestamp translatePeriodicite(NSTimestamp date, int weekStart, int rangeStart, int rangeEnd, int fannKeyDest) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);

		// infos invariable
		int hour = cal.get(Calendar.HOUR);
		int min = cal.get(Calendar.MINUTE);
		int s = cal.get(Calendar.SECOND);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		int year = cal.get(Calendar.YEAR);

		int annoDest = year + 1;
		// fin infos invariables
		int maxWeek = getMaxWeekInYear(annoDest);

		if (weekStart > rangeStart) {

			if (week > weekStart) {
				week = week + (weekStart - rangeStart);
				if (week > maxWeek) {
					week = maxWeek - week;
					// cas depassement semaines a gerer ici
				}
			}
			else {
				week = week + ((weekStart - rangeStart) - (weekStart - week));
				if (week > maxWeek) {
					week = maxWeek - week;
					// idem : cas depassement semaines
				}
			}

		}

		if (weekStart < rangeStart) {
			week = week - (rangeStart - weekStart);
		}

		// int annoDest = getYearForWeek(week,fannKeyDest,useAnneeCivile);

		cal.set(Calendar.WEEK_OF_YEAR, week);

		cal.set(Calendar.YEAR, annoDest);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, s);
		cal.set(Calendar.DAY_OF_WEEK, day);
		cal.setTime(cal.getTime());

		return new NSTimestamp(cal.getTime());
	}

	/**
	 * getMaxWeekInYear : retourne le nombre de semaines de l'annee : 52 ou 53
	 * 
	 * @param year
	 *            l'annee d'evaluation
	 */
	public static int getMaxWeekInYear(int year) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.set(Calendar.YEAR, year);
		cal.setTime(cal.getTime());
		return cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * renvoie la representation en chaine des semaines passees exemple : 15-18;21;23;25-32
	 */
	public void setUseAnneeCivile(boolean anneeCivile) {
		this.useAnneeCivile = anneeCivile;
	}

	/** retourne un tableau de periodicites datedeb-datefin pour les semaines de la chaine semaineExcel et l'annee */
	public NSArray computeWeeksDates(String semaineExcel, int annee) throws fr.univlr.common.utilities.EdtException {

		int bws = 0, ews = 0;
		// determination de la semaine de debut et de la semaine de fin d'annee universitaire
		FixedCalendar refCal = new FixedCalendar();
		refCal.set(YEAR, annee);
		refCal.set(Calendar.MONTH, Calendar.AUGUST);
		refCal.set(Calendar.DAY_OF_MONTH, 1);
		refCal.setTime(refCal.getTime());
		bws = refCal.get(Calendar.WEEK_OF_YEAR);
		refCal.set(YEAR, (annee + 1));
		refCal.set(Calendar.MONTH, Calendar.JULY);
		refCal.set(Calendar.DAY_OF_MONTH, 31);
		// refCal.set(Calendar.MONTH,Calendar.AUGUST);
		// refCal.set(Calendar.DAY_OF_MONTH,31);
		ews = refCal.get(Calendar.WEEK_OF_YEAR);

		FixedCalendar cal = new FixedCalendar();
		cal.set(Calendar.YEAR, annee);
		cal.setTime(cal.getTime());
		this.setTime(this.getTime());
		int maxWeek = cal.getMaximum(Calendar.WEEK_OF_YEAR);

		if (useAnneeCivile) {
			bws = 1;
			ews = maxWeek;
		}

		NSArray creneaux = NSArray.componentsSeparatedByString(semaineExcel, ";");

		NSMutableArray period = new NSMutableArray();

		for (int i = 0; i < creneaux.count(); i++) {
			NSArray elements = NSArray.componentsSeparatedByString((String) creneaux.objectAtIndex(i), "-");

			if (elements.count() > 1) { // cas ou l'utilisateur a saisie une fourchette de semaines
				int startweek = FormatHandler.strToInt((String) elements.objectAtIndex(0));
				int endweek = FormatHandler.strToInt((String) elements.objectAtIndex(1));

				if (startweek < endweek) {
					for (int id = startweek; id < endweek + 1; id++) {
						Object[] unePeriodicite = getWeek(id, ews, bws, maxWeek, annee);
						period.addObject(unePeriodicite[0]);
						period.addObject(unePeriodicite[1]);
					}
				}
				else { // startweek>endweek
					for (int id = startweek; id < maxWeek + 1; id++) {
						Object[] unePeriodicite = getWeek(id, ews, bws, maxWeek, annee);
						period.addObject(unePeriodicite[0]);
						period.addObject(unePeriodicite[1]);
					}
					for (int id = 0; id < endweek + 1; id++) {
						Object[] unePeriodicite = getWeek(id, ews, bws, maxWeek, annee);
						period.addObject(unePeriodicite[0]);
						period.addObject(unePeriodicite[1]);
					}
				}
			}
			else { // cas ou l'utilisation a saisie une semaine et pas une fourchette de semaines.
				int week = FormatHandler.strToInt((String) elements.objectAtIndex(0));
				Object[] unePeriodicite = getWeek(week, ews, bws, maxWeek, annee);
				period.addObject(unePeriodicite[0]);
				period.addObject(unePeriodicite[1]);
			}
		}
		return period;
	}

	public String semainesToExcel(ArrayList semaines) {

		int inc = 0;
		int prev = -1;
		int cur = 0;
		int i = 0;
		int sz = semaines.size();
		StringBuffer str = new StringBuffer();

		for (i = 0; i < sz; i++) {
			cur = ((Number) semaines.get(i)).intValue();
			if (i == 0) {
				str.append(String.valueOf(cur));
			}
			else {
				if (inc > 0 && (cur != prev + 1)) {
					str.append("-" + String.valueOf(prev));
					str.append(";" + String.valueOf(cur));
					inc = 0;
				}
				else {
					if (cur == prev + 1) {
						inc++;
					}
					else {
						str.append(";" + String.valueOf(cur));
					}
				}
			}
			prev = cur;
		}
		if (inc > 0) {
			str.append("-" + String.valueOf(((Number) semaines.get(sz - 1)).intValue()));
		}

		return str.toString();
	}

	/** retourne le nombre de millisecondes separant deux dates */
	public static long millisecondesSeparatingDates(NSTimestamp date1, NSTimestamp date2) {
		return date1.getTime() - date2.getTime();
	}

	/** retourne le nombre de jours separant deux dates */
	public static int daysSeparatingDates(NSTimestamp date1, NSTimestamp date2) {
		if (date1 != null && date2 != null) {
			return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
		}
		else {
			return -1;
		}
	}

	/** retourne le nombre d'heures separant deux dates */
	public static int hoursSeparatingDates(NSTimestamp date1, NSTimestamp date2) {
		if (date1 != null && date2 != null) {
			return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60));
		}
		else {
			return -1;
		}
	}

	/** retourne le nombre de minutes separant deux dates */
	public static int minutesSeparatingDates(NSTimestamp date1, NSTimestamp date2) {
		if (date1 != null && date2 != null) {
			return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60));
		}
		else {
			return -1;
		}
	}

	/** retourne le nombre de secondes separant deux dates */
	public static int secondesSepratingDates(NSTimestamp date1, NSTimestamp date2) {
		if (date1 != null && date2 != null) {
			return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60));
		}
		else {
			return -1;
		}
	}

	/** permet de comparer chaques créneau par sa date de debut */
	public class PeriodicitesComparator extends NSComparator {

		public int compare(Object object1, Object object2) throws NSComparator.ComparisonException {

			if (!(object1 instanceof Periodicite) || !(object2 instanceof Periodicite)) {
				throw new NSComparator.ComparisonException("Les objets compares doivent etres des instances de Periodicite");
			}

			return (((Periodicite) object1).dateDeb()).compare(((Periodicite) object2).dateDeb());
		}
	}

	/**
	 * decouper la periode en petites periodes sur un jour, exemple : 22/10/2004 23:30 - 23/10/2004 12:00 donne : 22/10/2004 23:30 -
	 * 22/10/2004 23:59 et 23/10/2004 00:00 - 23/10/2004 12:00
	 */
	public static NSArray slicePeriod2(NSTimestamp debut, NSTimestamp fin) {

		NSMutableArray slices = new NSMutableArray();

		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);

		int nbJ = daysSeparatingDates(fin, debut);

		NSTimestamp currentDeb, currentFin;
		NSTimestamp currentDate = debut;

		// le premier jour
		currentDeb = debut;
		if (nbJ == 0) {
			slices.addObject(debut);
			slices.addObject(fin);
		}
		else {
			currentFin = FormatHandler.endOfDay(debut);
			slices.addObject(debut);
			slices.addObject(currentFin);
			currentDate = currentFin;

			String sjFin = FormatHandler.dateToStr(fin, "%d/%m/%Y");

			while (true) {
				cal.add(Calendar.DAY_OF_WEEK, 1);
				cal.setTime(cal.getTime());
				currentDate = new NSTimestamp(cal.getTime());
				currentDeb = FormatHandler.midnightTime(currentDate);
				currentFin = FormatHandler.endOfDay(currentDate);

				if (!FormatHandler.dateToStr(currentFin, "%d/%m/%Y").equals(sjFin)) {
					slices.addObject(currentDeb);
					slices.addObject(currentFin);
				}
				else {
					break;
				}
			}

			// le dernier jour
			slices.addObject(FormatHandler.midnightTime(fin));
			slices.addObject(fin);

		}// else

		return slices;
	}

	/**
	 * decouper la periode en petites periodes sur un jour, exemple : 22/10/2004 23:30 - 23/10/2004 12:00 donne : 22/10/2004 23:30 -
	 * 22/10/2004 23:59 et 23/10/2004 00:00 - 23/10/2004 12:00
	 */
	public static NSArray slicePeriod(NSTimestamp debut, NSTimestamp fin) {

		NSMutableArray slices = new NSMutableArray();

		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);

		int nbJ = daysSeparatingDates(fin, debut);

		NSTimestamp currentDeb, currentFin;
		NSTimestamp currentDate = debut;

		// le premier jour
		currentDeb = debut;
		if (nbJ == 0) {
			slices.addObject(debut);
			slices.addObject(fin);
		}
		else {
			currentFin = FormatHandler.endOfDay(debut);
			slices.addObject(debut);
			slices.addObject(currentFin);
			currentDate = currentFin;
			String sjFin = FormatHandler.dateToStr(fin, "%d/%m/%Y");
			cal.setTime(debut);

			while (true) {
				cal.add(Calendar.DAY_OF_WEEK, 1);
				cal.setTime(cal.getTime());
				currentDate = new NSTimestamp(cal.getTime());
				currentDeb = FormatHandler.midnightTime(currentDate);
				currentFin = FormatHandler.endOfDay(currentDate);

				if (!FormatHandler.dateToStr(currentFin, "%d/%m/%Y").equals(sjFin)) {
					slices.addObject(currentDeb);
					slices.addObject(currentFin);
				}
				else {
					break;
				}
			}

			// le dernier jour
			slices.addObject(FormatHandler.midnightTime(fin));
			slices.addObject(fin);

		}// else

		return slices;
	}

}
