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

import org.cocktail.superplan.client.metier.Salles;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

public class ConfigurationReservation {

	private Salles salle;
	private NSMutableArray<NSTimestamp> periodicites = new NSMutableArray<NSTimestamp>();

	public ConfigurationReservation() {
	}

	public ConfigurationReservation(Salles aSalle, NSArray<NSTimestamp> aPeriodicites) {
		this.salle = aSalle;
		this.periodicites = aPeriodicites.mutableClone();
	}

	public void setSalleFromSalNumero(EOEditingContext context, Number salNumero) {
		salle = Salles.fetchSalles(context, Salles.SAL_NUMERO_KEY, salNumero);
	}

	public void addPeriodicite(NSTimestamp debut, NSTimestamp fin) {
		if (debut != null && fin != null) {
			periodicites.addObject(debut);
			periodicites.addObject(fin);
		}
	}

	public void addPeriodiciteFromMemoPeriod(MemoPeriode mPeriode) {
		if (mPeriode != null) {
			periodicites.addObject(mPeriode.getDeb());
			periodicites.addObject(mPeriode.getFin());
		}
	}

	public void setPeriodicitesFromMemoPeriods(NSArray<MemoPeriode> mPeriods) {
		for (int i = 0; i < mPeriods.count(); i++) {
			addPeriodiciteFromMemoPeriod(mPeriods.objectAtIndex(i));
		}
	}

	public Salles getSalle() {
		return salle;
	}

	public void setSalle(Salles salle) {
		this.salle = salle;
	}

	public NSMutableArray<NSTimestamp> getPeriodicites() {
		return periodicites;
	}

	public void setPeriodicites(NSMutableArray<NSTimestamp> periodicites) {
		this.periodicites = periodicites;
	}

	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("salle=").append(salle);
		bf.append(" periodicites=").append(periodicites);
		return bf.toString();
	}

}
