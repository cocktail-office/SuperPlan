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
package org.cocktail.superplan.server.gestionimpression;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

public class ListeCours extends NSMutableArray {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7359807751474054742L;
	private NSTimestamp dateDebutMin;
	private NSTimestamp dateFintMin;

	public ListeCours() {
		super();
	}

	public void addObject(CoursImpress arg0) {
		super.addObject(arg0);
	}

	public void addObjects(ListeCours arg0) {
		super.addObject(arg0);
	}

	public boolean isComplexAtIndex(int arg0) {
		return (objectAtIndex(arg0)).getClass().getName().equals("org.cocktail.superplan.server.gestionimpression.ListeCours");
	}

	public ListeCours listeCoursForCreneau(int arg0) {
		ListeCours tmp = new ListeCours();
		for (int j = 0; j < this.count(); j++) {
			if (!isComplexAtIndex(j)) {
				if (arg0 == ((CoursImpress) objectAtIndex(j)).numeroCreneau) {
					tmp.addObject(objectAtIndex(j));
				}
			}
			else {

				ListeCours listCours = (ListeCours) objectAtIndex(j);
				tmp.addObjectsFromArray(listCours.listeCoursForCreneau(arg0));
			}

		}
		return tmp;
	}

	public NSTimestamp dateDebutAtIndex(int index, NSTimestamp debutImpress) {
		if (isComplexAtIndex(index)) {
			NSTimestamp dateMin = debutImpress;
			for (int i = 0; i < ((NSArray) objectAtIndex(index)).count(); i++) {
				NSTimestamp currentDate = ((ListeCours) objectAtIndex(index)).dateDebutAtIndex(i, dateMin);

				if (currentDate.before(dateMin)) {
					dateMin = currentDate;
				}

			}
			return dateMin;
		}
		else {
			return ((CoursImpress) objectAtIndex(index)).heuredeb();
		}
	}

	public NSTimestamp dateFinAtIndex(int index, NSTimestamp finImpress) {
		if (isComplexAtIndex(index)) {
			NSTimestamp dateMax = finImpress;
			for (int i = 0; i < ((NSArray) objectAtIndex(index)).count(); i++) {
				NSTimestamp currentDate = ((ListeCours) objectAtIndex(index)).dateFinAtIndex(i, dateMax);

				if (currentDate.after(dateMax)) {
					dateMax = currentDate;
				}

			}
			return dateMax;
		}
		else {
			return ((CoursImpress) objectAtIndex(index)).heurefin();
		}
	}

	public int height(int i) {
		if (isComplexAtIndex(i)) {
			return ((NSArray) objectAtIndex(i)).count();
		}
		else {
			return 0;
		}
	}

	public void setDateDebutMin(NSTimestamp debutMin) {
		dateDebutMin = debutMin;
	}

	public void setDateFinMax(NSTimestamp finMax) {
		dateFintMin = finMax;
	}

	public NSTimestamp dateDebutMin() {
		return dateDebutMin;
	}

	public NSTimestamp dateFinMax() {
		return dateFintMin;
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < this.count(); i++) {
			if (isComplexAtIndex(i)) {
				str = "\\" + ((ListeCours) this.objectAtIndex(i)).toString();
			}
			else {
				str = "\t" + ((CoursImpress) this.objectAtIndex(i)).toString();
			}
		}
		return str;
	}

}
