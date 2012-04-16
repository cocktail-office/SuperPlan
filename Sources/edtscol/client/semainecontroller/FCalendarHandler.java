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
package edtscol.client.semainecontroller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.utilities.TimeHandler;

public class FCalendarHandler {

	private static NSTimestamp getDebutSemestreImpair(Number fannKey) {
		int Y = fannKey.intValue();
		return FormatHandler.strToDate("01/08/" + Y + " 02:00", "%d/%m/%Y %H:%M");
	}

	private static NSTimestamp getFinSemestrePair(Number fannKey) {
		int Y = fannKey.intValue();
		int YY = Y + 1;
		return FormatHandler.strToDate("31/07/" + YY + " 23:59", "%d/%m/%Y %H:%M");
	}

	public static NSArray<Integer> listeSemainesForAnneeScolaire(Number fannKey) {

		NSTimestamp debutSem1 = getDebutSemestreImpair(fannKey);
		NSTimestamp finSem2 = getFinSemestrePair(fannKey);

		int startWeek1, endWeek1, startWeek2, endWeek2;

		endWeek1 = TimeHandler.getMaxWeekInYear(fannKey.intValue());

		GregorianCalendar cal = new GregorianCalendar();

		cal.setTime(debutSem1);
		startWeek1 = cal.get(Calendar.WEEK_OF_YEAR);

		cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, fannKey.intValue() + 1);

		startWeek2 = 1;

		cal = new GregorianCalendar();
		cal.setTime(finSem2);
		endWeek2 = cal.get(Calendar.WEEK_OF_YEAR);

		if (startWeek1 == endWeek2) {
			if (startWeek1 == listeSemainesForAnneeScolaire(new Integer(fannKey.intValue() - 1)).lastObject().intValue()) {
				startWeek1++;
			}
			else {
				endWeek2--;
			}
		}

		NSMutableArray<Integer> liste = new NSMutableArray<Integer>();
		for (int i = startWeek1; i <= endWeek1; i++) {
			liste.addObject(new Integer(i));
		}
		for (int i = startWeek2; i <= endWeek2; i++) {
			liste.addObject(new Integer(i));
		}
		return liste;
	}

}
