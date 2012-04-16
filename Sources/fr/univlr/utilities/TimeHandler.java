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
package fr.univlr.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import org.cocktail.superplan.client.metier.FormationAnnee;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FixedCalendar;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;

public class TimeHandler extends GregorianCalendar {

	private static final long serialVersionUID = -2742027252927728500L;
	private boolean useAnneeCivile = true;

	FixedCalendar myCal;

	public TimeHandler() {
		super();
		initCal();
	}

	private void initCal() {
		setFirstDayOfWeek(Calendar.MONDAY);
		setMinimalDaysInFirstWeek(4); // le premier Jeudi de Janvier...
		myCal = new FixedCalendar();
	}

	public void set(int field, int value) {
		super.set(field, value);
		super.setTime(getTime());
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

	/** renvoi la date exacte en fonction des parametres de temps passes */
	public NSTimestamp[] getDateRangeForWeek(int ayear, int day, int hdeb, int mdeb, int hfin, int mfin, int week) {

		myCal.set(Calendar.YEAR, ayear);
		myCal.set(Calendar.WEEK_OF_YEAR, week);
		myCal.set(Calendar.DAY_OF_WEEK, day);
		myCal.set(Calendar.HOUR_OF_DAY, hdeb);
		myCal.set(Calendar.MINUTE, mdeb);
		myCal.set(Calendar.SECOND, 0);
		// this.setTime(this.getTime());
		NSTimestamp startdate = new NSTimestamp(myCal.getTime());
		myCal.set(Calendar.YEAR, ayear);
		myCal.set(Calendar.WEEK_OF_YEAR, week);
		myCal.set(Calendar.DAY_OF_WEEK, day);

		myCal.set(Calendar.HOUR_OF_DAY, hfin);
		myCal.set(Calendar.MINUTE, mfin);
		// this.setTime(this.getTime());
		NSTimestamp enddate = new NSTimestamp(myCal.getTime());
		return new NSTimestamp[] { startdate, enddate };
	}

	public static int getYearForWeekAndFannKey(int week, int year, boolean anneeCivile) {
		if (anneeCivile) {
			return year;
		}

		int semaineDebut = 0, semaineFin = 0;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.set(Calendar.YEAR, year);
		cal.setTime(cal.getTime());
		semaineFin = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		cal.set(Calendar.MONTH, Calendar.JULY);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.setTime(cal.getTime());
		semaineDebut = cal.get(Calendar.WEEK_OF_YEAR);

		if (week > semaineDebut && week <= semaineFin) {
			return year;
		}
		else {
			return year + 1;
		}
	}

	public static int getYearForWeek(int week, int year, boolean anneeCivile) {
		if (anneeCivile) {
			return year;
		}

		int semaineDebut = 0, semaineFin = 0;
		int annee = year;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.set(Calendar.YEAR, year);
		cal.setTime(cal.getTime());
		semaineFin = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		cal.set(Calendar.MONTH, Calendar.JULY);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.setTime(cal.getTime());
		semaineDebut = cal.get(Calendar.WEEK_OF_YEAR);
		if (!(week > semaineDebut && week <= semaineFin)) {
			annee = annee - 1;
		}
		return annee;
	}

	public static int getYearForWeek(FormationAnnee formationAnnee, int week, boolean anneeCivile) {
		if (anneeCivile) {
			return formationAnnee.fannDebut();
		}

		int semaineDebut = 0, semaineFin = 0;
		int annee = formationAnnee.fannDebut();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.set(Calendar.YEAR, annee);
		cal.setTime(cal.getTime());
		semaineFin = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		cal.set(Calendar.MONTH, Calendar.JULY);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.setTime(cal.getTime());
		semaineDebut = cal.get(Calendar.WEEK_OF_YEAR);
		if (!(week > semaineDebut && week <= semaineFin)) {
			annee = formationAnnee.fannFin();
		}
		return annee;
	}

	public static int getYearFor(NSTimestamp date, boolean anneeCivile) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return getYearForWeek(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), anneeCivile);
	}

	/** donne les dates de debut et de fin de la semaine dans l'annee passee */
	public static NSTimestamp[] getBeginAndEndOfWeek(int weeknumber, int year) {
		NSTimestamp debut, fin;

		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, weeknumber);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.setTime(cal.getTime());
		debut = new NSTimestamp(cal.getTime());
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.setTime(cal.getTime());
		fin = new NSTimestamp(cal.getTime());
		return new NSTimestamp[] { debut, fin };
	}

	/** remet le calendrier a la date courante */
	public void raz() {
		this.setTime(new NSTimestamp());
	}

	/** renvoie le numero de semaine */
	public int weekOfYear(NSTimestamp time) {
		if (time == null) {
			return -1;
		}
		this.setTime(time);
		return this.get(WEEK_OF_YEAR);
	}

	public int lastWeekOfYear(int year) {
		int trueYear = useAnneeCivile ? year : year + 1;
		int month = useAnneeCivile ? Calendar.DECEMBER : Calendar.JULY;
		Calendar cal = new GregorianCalendar(trueYear, month, 31);
		return weekOfYear(new NSTimestamp(cal.getTime()));
	}

	public int addWeeks(int year, int week, int nbWeeks) {
		if (nbWeeks <= 0) {
			return week;
		}
		int res = week + nbWeeks;
		Calendar cal = new GregorianCalendar(year, Calendar.DECEMBER, 31);
		int maxWeek = cal.get(Calendar.WEEK_OF_YEAR);
		return res <= maxWeek ? res : nbWeeks - (maxWeek - week);
	}

	/** renvoie le jour de la semaine */
	public int dayOfWeek(NSTimestamp time) {
		this.setTime(time);
		return this.get(DAY_OF_WEEK);
	}

	/** retourne un couple periodicites avec les infos passees en parametre */
	private NSTimestamp[] getPeriodiciteValide(int hdeb, int mdeb, int hfin, int mfin, int dayOfWeek, int week, int annee, int bws, int ews,
			int maxWeek) throws EdtException {

		int currentAnnee = annee;
		boolean semaineValide;
		// System.out.println("getPeriodiciteValide: mdeb = " + mdeb + ", mfin = " + mfin + ", week = " + week + ", annee = " + annee +
		// ", bws = " + bws
		// + ", ews = " + ews + ", maxWeek = " + maxWeek + ", currentWeek = " + currentWeek);
		if (FormatHandler.inRange(week, bws, maxWeek)) {
			semaineValide = true;
		}
		else
			if (FormatHandler.inRange(week, 0, ews)) {
				semaineValide = true;
				currentAnnee = annee + 1;
			}
			else {
				semaineValide = false;
			}

		if (!semaineValide) {
			throw new EdtException("la semaine " + String.valueOf(week) + " n'est pas une semaine valide");
		}

		return this.getDateRangeForWeek(currentAnnee, dayOfWeek, hdeb, mdeb, hfin, mfin, week);

		/*
		 * NSTimestamp debut,fin; FixedCalendar refCal = new FixedCalendar(); refCal.set(YEAR,currentAnnee);
		 * refCal.set(Calendar.WEEK_OF_YEAR,week); refCal.set(Calendar.DAY_OF_WEEK,dayOfWeek);
		 * 
		 * refCal.set(HOUR,hdeb); refCal.set(MINUTE,mdeb); refCal.set(SECOND,0); refCal.setTime(refCal.getTime()); debut =
		 * refCal.myGetTime();
		 * 
		 * refCal.set(HOUR,hfin); refCal.set(MINUTE,mfin); refCal.set(SECOND,0); refCal.setTime(refCal.getTime()); fin = refCal.myGetTime();
		 * 
		 * return new Object[]{debut,fin};
		 */
	}

	/** permet de switcher vers l'utilisation de l'annee civile pour calculer les periodicites */
	public void setUseAnneeCivile(boolean anneeCivile) {
		this.useAnneeCivile = anneeCivile;
	}

	public static NSArray<Integer> getSemainesArrayFromSemainesString(String semaines, Integer annee) {
		NSMutableArray<Integer> semainesArray = new NSMutableArray<Integer>();

		FixedCalendar cal = new FixedCalendar();
		cal.set(Calendar.YEAR, annee);
		cal.setTime(cal.getTime());
		int maxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		NSArray<String> creneaux = NSArray.componentsSeparatedByString(semaines, ";");
		for (int i = 0; i < creneaux.count(); i++) {
			NSArray<String> elements = NSArray.componentsSeparatedByString(creneaux.objectAtIndex(i), "-");
			if (elements.count() > 1) {
				int startweek = FormatHandler.strToInt(elements.objectAtIndex(0));
				int endweek = FormatHandler.strToInt(elements.objectAtIndex(1));
				if (startweek == endweek) {
					semainesArray.addObject(new Integer(startweek));
				}
				else {
					if (startweek < endweek) {
						for (int id = startweek; id < endweek + 1; id++) {
							semainesArray.addObject(new Integer(id));
						}
					}
					else {
						for (int id = startweek; id < maxWeek + 1; id++) {
							semainesArray.addObject(new Integer(id));
						}
						for (int id = 1; id < endweek + 1; id++) {
							semainesArray.addObject(new Integer(id));
						}
					}
				}
			}
			else {
				semainesArray.addObject(new Integer(FormatHandler.strToInt(elements.objectAtIndex(0))));
			}
		}
		return semainesArray.immutableClone();
	}

	public NSArray<NSTimestamp> computePeriodicites(int dayOfWeek, String semaineExcel, String shdeb, String shfin, String smdeb, String smfin,
			int annee) throws EdtException {
		int bws = 0, ews = 0;
		// determination de la semaine de debut et de la semaine de fin d'annee universitaire
		FixedCalendar refCal = new FixedCalendar();
		refCal.set(YEAR, annee);
		refCal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		refCal.set(Calendar.DAY_OF_MONTH, 1);
		refCal.setTime(refCal.getTime());
		bws = refCal.get(Calendar.WEEK_OF_YEAR);
		refCal.set(YEAR, (annee + 1));
		refCal.set(Calendar.MONTH, Calendar.AUGUST);
		refCal.set(Calendar.DAY_OF_MONTH, 31);
		ews = refCal.get(Calendar.WEEK_OF_YEAR);

		int hdeb = FormatHandler.strToInt(shdeb);
		int hfin = FormatHandler.strToInt(shfin);
		int mdeb = FormatHandler.strToInt(smdeb);
		int mfin = FormatHandler.strToInt(smfin);

		FixedCalendar cal = new FixedCalendar();
		cal.set(Calendar.YEAR, annee);
		cal.setTime(cal.getTime());
		this.setTime(this.getTime());
		int maxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);

		if (useAnneeCivile) {
			bws = 1;
			ews = maxWeek;
		}

		NSMutableArray<NSTimestamp> period = new NSMutableArray<NSTimestamp>();
		NSArray<Integer> semaines = getSemainesArrayFromSemainesString(semaineExcel, annee);
		for (int i = 0; i < semaines.count(); i++) {
			NSTimestamp[] unePeriodicite = getPeriodiciteValide(hdeb, mdeb, hfin, mfin, dayOfWeek, semaines.objectAtIndex(i).intValue(), annee, bws,
					ews, maxWeek);
			period.addObject(unePeriodicite[0]);
			period.addObject(unePeriodicite[1]);
		}
		return period;
	}

	/** permet de calculer les periodicites en fonction des donnees saisies/choisies */
	public Hashtable<String, Object> decomputePeriodicites(NSArray lesPeriodicites) {
		NSArray periodicites;
		try {
			periodicites = lesPeriodicites.sortedArrayUsingComparator(new PeriodicitesComparator());
		}
		catch (Exception e) {
			periodicites = new NSArray();
		}

		Hashtable<String, Object> retour = new Hashtable<String, Object>();
		ArrayList<Integer> lst = new ArrayList<Integer>();

		for (int i = 0; i < periodicites.count(); i++) {
			NSKeyValueCoding period = (NSKeyValueCoding) periodicites.objectAtIndex(i);
			NSTimestamp dateDeb = (NSTimestamp) period.valueForKey("dateDeb");
			NSTimestamp dateFin = (NSTimestamp) period.valueForKey("dateFin");

			Integer week = new Integer(this.weekOfYear(dateDeb));

			lst.add(week);

			if (i == 0) {
				String heureDebut = FormatHandler.dateToStr(dateDeb, "%H:%M");
				String heureFin = FormatHandler.dateToStr(dateFin, "%H:%M");

				String annee = FormatHandler.dateToStr(dateDeb, "%Y");

				retour.put("annee", annee);
				retour.put("heureDeb", heureDebut);
				retour.put("heureFin", heureFin);
				retour.put("jour", new Integer(this.dayOfWeek(dateDeb)));
			}
		} // for
		retour.put("semaines", this.semainesToExcel(lst));
		return retour;
	}

	/**
	 * renvoie la representation en chaine des semaines passees exemple : 15-18;21;23;25-32
	 */
	private String semainesToExcel(ArrayList<Integer> semaines) {
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

	/** retourne le nombre de jours separant deux dates */
	public static int daysSeparatingDates(NSTimestamp date1, NSTimestamp date2) {
		if (date1 != null && date2 != null) {
			return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
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

	/** permet de comparer chaques créneau par sa date de debut */
	public class PeriodicitesComparator extends NSComparator {

		public int compare(Object object1, Object object2) throws NSComparator.ComparisonException {

			if (!(object1 instanceof NSKeyValueCoding) || !(object2 instanceof NSKeyValueCoding)) {
				throw new NSComparator.ComparisonException("Les objets compares doivent etres des instances de NSKeyValueCoding");
			}

			return ((NSTimestamp) ((NSKeyValueCoding) object1).valueForKey("dateDeb")).compare((NSTimestamp) ((NSKeyValueCoding) object2)
					.valueForKey("dateDeb"));
		}
	}

	public static NSArray slicePeriod(NSTimestamp debut, NSTimestamp fin) {

		NSMutableArray slices = new NSMutableArray();

		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);

		int nbJ = TimeHandler.daysSeparatingDates(fin, debut);

		NSTimestamp currentDeb, currentFin;
		NSTimestamp currentDate = debut;

		// le premier jour
		currentDeb = debut;
		if (nbJ == 0) {
			slices.addObject(debut);
			slices.addObject(fin);
		}
		else {
			// System.out.println(" debut " + debut + " fin " + fin);
			currentFin = FormatHandler.endOfDay(debut);
			slices.addObject(debut);
			slices.addObject(currentFin);

			String sjFin = FormatHandler.dateToStr(fin, "%d/%m/%Y");
			cal.setTime(debut);

			while (true) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
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

	public static NSMutableArray retirerDoublonsDate(NSArray anArray) {
		NSMutableArray stDates = new NSMutableArray();
		NSMutableArray resultat = new NSMutableArray();
		for (int t = 0; t < anArray.count(); t++) {
			String str = FormatHandler.dateToStr((NSTimestamp) anArray.objectAtIndex(t), "%d/%m/%Y %H:%M:%S");
			// System.out.println("retirerDoublonsDate str "+ str + " " + t);
			if (!stDates.containsObject(str)) {
				stDates.addObject(str);
			}
		}
		for (int i = 0; i < stDates.count(); i++) {
			String current = (String) stDates.objectAtIndex(i);
			NSTimestamp aDate = FormatHandler.strToDate(current, "%d/%m/%Y %H:%M:%S");
			if (!resultat.containsObject(aDate)) {
				resultat.addObject(aDate);
			}
		}
		return resultat;
	}

	/**
	 * teste si la date de debut est bien avant la date de fin pour les periodicites
	 */
	public static NSMutableArray<NSTimestamp> testCoherenceDates(NSArray<NSTimestamp> dates) {
		NSMutableArray<NSTimestamp> myDates = TimeHandler.retirerDoublonsDate(dates);

		if (myDates.count() > 1) {
			NSTimestamp debut = myDates.objectAtIndex(0);
			NSTimestamp fin = myDates.objectAtIndex(1);
			if (!(debut.compare(fin) == NSComparator.OrderedAscending)) {
				WindowHandler.showError("L'heure de début doit etre avant l'heure de fin de réservation");
				return null;
			}
			else {
				return myDates;
			}
		}
		else {
			WindowHandler.showError("aucune périodicité (date début - date fin) trouvée");
			return null;
		}
	}

	public static NSMutableArray<NSTimestamp> retirerIncoherences(NSArray<NSTimestamp> anArray) {
		NSMutableArray<NSTimestamp> resultat = new NSMutableArray<NSTimestamp>();
		for (int t = 0; t < anArray.count(); t += 2) {
			String str1 = FormatHandler.dateToStr(anArray.objectAtIndex(t), "%d/%m/%Y %H:%M:%S");
			String str2 = FormatHandler.dateToStr(anArray.objectAtIndex(t + 1), "%d/%m/%Y %H:%M:%S");
			if (!str1.equals(str2)) {
				resultat.addObject(anArray.objectAtIndex(t));
				resultat.addObject(anArray.objectAtIndex(t + 1));
			}
		}
		return resultat;
	}

}
