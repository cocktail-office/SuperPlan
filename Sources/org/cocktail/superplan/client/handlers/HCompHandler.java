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
package org.cocktail.superplan.client.handlers;

import org.cocktail.superplan.client.metier.HcompRecup;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.server.metier.Reservation;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.utilities.TimeHandler;

public class HCompHandler {

	private EOEditingContext eContext;

	public HCompHandler(EOEditingContext eContext) {
		this.eContext = eContext;
	}

	/**
	 * retourne les periodicites reservees pour l'ap et l'individu passes en parametre, sur la periode entre debut et fin si val==true
	 * retourne les periodicites validees au niveau hcomp, sinon les non valides.
	 */
	public NSArray getPeriodicites(MaquetteAp ap, IndividuUlr individu, NSTimestamp debut, NSTimestamp fin, boolean val) {

		Integer validHcomp = (val) ? new Integer(1) : new Integer(0);
		NSMutableArray qualifiers = new NSMutableArray();
		qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.HCOMP_KEY + " = %@", new NSArray(new Object[] { validHcomp })));
		if (debut != null) {
			qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.DATE_FIN_KEY + " >= %@", new NSArray(new Object[] { debut })));
		}
		if (fin != null) {
			qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.DATE_DEB_KEY + " <= %@", new NSArray(new Object[] { fin })));
		}
		qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
				+ ReservationAp.MAQUETTE_AP_KEY + " = %@", new NSArray(new Object[] { ap })));
		qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.RESERVATION_KEY + "." + Reservation.OCCUPANTS_KEY + "."
				+ Occupant.INDIVIDU_KEY + " = %@", new NSArray(new Object[] { individu })));

		return Periodicite.fetchPeriodicites(eContext, new EOAndQualifier(qualifiers), null);
	}

	/** fait la somme en heures des periodicites -Periodicite- */
	public double getTotalFromPeriodicites(NSArray periodicites) {
		Periodicite period;
		NSTimestamp deb, fin;
		double total = 0.0;
		for (int i = 0; i < periodicites.count(); i++) {
			period = (Periodicite) periodicites.objectAtIndex(i);
			deb = period.dateDeb();
			fin = period.dateFin();
			int min = TimeHandler.minutesSeparatingDates(deb, fin);
			total += min / 60.0;
		}
		return total;
	}

	/** retourne les enregistrements HcompRec pour les criteres donnes */
	public NSArray getHcomp(MaquetteAp ap, IndividuUlr individu, NSTimestamp debut, NSTimestamp fin, boolean etatImport) {
		Integer hcompImportees = (etatImport) ? new Integer(1) : new Integer(0);

		NSMutableArray qualifiers = new NSMutableArray();
		qualifiers
				.addObject(EOQualifier.qualifierWithQualifierFormat(HcompRecup.HCR_TAG_KEY + " = %@", new NSArray(new Object[] { hcompImportees })));
		if (debut != null) {
			qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(HcompRecup.PERIODICITE_KEY + "." + Periodicite.DATE_FIN_KEY + " >= %@",
					new NSArray(new Object[] { debut })));
		}
		if (fin != null) {
			qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(HcompRecup.PERIODICITE_KEY + "." + Periodicite.DATE_DEB_KEY + " <= %@",
					new NSArray(new Object[] { fin })));
		}
		qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(HcompRecup.MAQUETTE_AP_KEY + " = %@", new NSArray(new Object[] { ap })));
		qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(HcompRecup.INDIVIDU_ULR_KEY + " = %@", new NSArray(new Object[] { individu })));

		return HcompRecup.fetchHcompRecups(eContext, new EOAndQualifier(qualifiers), null);
	}

	/** fait la somme en heures des periodicites -Periodicite- ratachees aux hcomp -HcompRecup- */
	public double getTotalFromHcomp(NSArray hcomp) {
		Periodicite period;
		NSTimestamp deb, fin;
		double total = 0.0;
		for (int i = 0; i < hcomp.count(); i++) {
			period = ((HcompRecup) hcomp.objectAtIndex(i)).periodicite();
			deb = period.dateDeb();
			fin = period.dateFin();
			int min = TimeHandler.minutesSeparatingDates(deb, fin);
			total += min / 60.0;
			total = FormatHandler.roundDouble(total);
		}
		return total;
	}

	/** valide les periodicites en hcomp */
	public void validerHComp(IndividuUlr individu, MaquetteAp ap, NSArray periodicites) throws Exception {

		Periodicite currentPeriode;
		HcompRecup currentHcomp;
		for (int i = 0; i < periodicites.count(); i++) {
			currentPeriode = (Periodicite) periodicites.objectAtIndex(i);
			currentPeriode.setHcomp(new Integer(1));
			currentHcomp = HcompRecup.createHcompRecup(eContext, new Integer(0));
			currentHcomp.setReservationRelationship(currentPeriode.reservation());
			currentHcomp.setIndividuUlrRelationship(individu);
			currentHcomp.setMaquetteApRelationship(ap);
			currentHcomp.setPeriodiciteRelationship(currentPeriode);
		}
		eContext.saveChanges();
	}

	/** permet de devalider les hcomp (si pas encore importees) */
	public void devaliderHcomp(NSArray hcomp) throws Exception {

		HcompRecup currentHcomp;
		for (int i = 0; i < hcomp.count(); i++) {
			currentHcomp = (HcompRecup) hcomp.objectAtIndex(i);
			currentHcomp.periodicite().setHcomp(new Integer(0));
			eContext.deleteObject(currentHcomp);
		}
		eContext.saveChanges();
	}

	public void revert() {
		eContext.revert();
	}

}
