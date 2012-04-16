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
package edtscol.client.gestionreservation;

import java.util.Iterator;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

public class MemoPeriode {

	private int id;
	private NSTimestamp deb, fin;
	private boolean active = true;

	public MemoPeriode(int anId, NSTimestamp aDeb, NSTimestamp aFin) {
		id = anId;
		deb = aDeb;
		fin = aFin;
	}

	public String toString() {
		return String.valueOf(getId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NSTimestamp getDeb() {
		return deb;
	}

	public void setDeb(NSTimestamp deb) {
		this.deb = deb;
	}

	public NSTimestamp getFin() {
		return fin;
	}

	public void setFin(NSTimestamp fin) {
		this.fin = fin;
	}

	public static NSArray<MemoPeriode> periodesToMemoPeriodes(NSArray<NSTimestamp> periodes) {

		NSMutableArray<MemoPeriode> mPeriodicites = new NSMutableArray<MemoPeriode>();

		int ip = 0;
		for (int k = 0; k < periodes.count(); k += 2) {
			NSTimestamp lDeb = periodes.objectAtIndex(k);
			NSTimestamp lFin = periodes.objectAtIndex(k + 1);
			mPeriodicites.addObject(new MemoPeriode(ip++, lDeb, lFin));
		}

		return mPeriodicites;
	}

	public static boolean containsMoreActivePeriods(NSArray arrayOfPeriods) {
		Iterator<MemoPeriode> iter = arrayOfPeriods.vector().iterator();
		boolean hasMoreActive = false;
		while (iter.hasNext()) {
			hasMoreActive = (iter.next()).isActive();
			if (hasMoreActive) {
				return hasMoreActive;
			}
		}
		return hasMoreActive;
	}

	public static NSArray getActivePeriods(NSArray arrayOfPeriods) {
		Iterator<MemoPeriode> iter = arrayOfPeriods.vector().iterator();
		NSMutableArray array = new NSMutableArray();
		while (iter.hasNext()) {
			MemoPeriode currentPeriod = iter.next();
			if (currentPeriod.isActive()) {
				array.addObject(currentPeriod);
			}
		}
		return array;
	}

	public static MemoPeriode memoPeriodWithId(NSArray periods, int id) {
		MemoPeriode currentPeriod;
		for (int i = 0; i < periods.count(); i++) {
			currentPeriod = (MemoPeriode) periods.objectAtIndex(i);
			if (currentPeriod.getId() == id) {
				return currentPeriod;
			}
		}
		return null;
	}

	public static NSArray memoPeriodsWithIds(NSArray periods, NSArray arrayPeriodIds) {

		NSMutableArray array = new NSMutableArray();
		int id;
		MemoPeriode currentPeriod;
		for (int i = 0; i < periods.count(); i++) {
			for (int j = 0; j < arrayPeriodIds.count(); j++) {
				currentPeriod = (MemoPeriode) periods.objectAtIndex(i);
				id = Integer.parseInt((String) arrayPeriodIds.objectAtIndex(j));
				if (currentPeriod.getId() == id) {
					array.addObject(currentPeriod);
				}
			}
		}
		return array;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
