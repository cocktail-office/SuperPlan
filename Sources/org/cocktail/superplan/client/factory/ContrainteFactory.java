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
package org.cocktail.superplan.client.factory;

import java.util.GregorianCalendar;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.superplan.client.metier.ContrainteHeure;
import org.cocktail.superplan.client.metier.ContrainteJour;
import org.cocktail.superplan.client.metier.ContrainteSemaine;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.IndividuUlr;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.gestioncontraintes.IRessourceContrainte;

public class ContrainteFactory {

	public static ContrainteSemaine createContrainteSemaine(EOEditingContext ec, GregorianCalendar date, FormationAnnee formationAnnee,
			IRessourceContrainte ressource) {
		if (date == null) {
			return null;
		}
		GregorianCalendar localDate = (GregorianCalendar) date.clone();
		localDate.set(GregorianCalendar.HOUR_OF_DAY, 0);
		localDate.set(GregorianCalendar.MINUTE, 0);
		localDate.set(GregorianCalendar.SECOND, 0);
		localDate.set(GregorianCalendar.MILLISECOND, 0);
		return createContrainteSemaine(ec, new NSTimestamp(localDate.getTime()), formationAnnee, ressource);
	}

	public static ContrainteSemaine createContrainteSemaine(EOEditingContext ec, NSTimestamp date, FormationAnnee formationAnnee,
			IRessourceContrainte ressource) {
		if (date == null) {
			return null;
		}
		GregorianCalendar gcDate = new GregorianCalendar();
		gcDate.setTime(date);
		ContrainteSemaine ctrl = ContrainteSemaine.createContrainteSemaine(ec, date, Integer.valueOf(gcDate.get(GregorianCalendar.WEEK_OF_YEAR)),
				formationAnnee);
		if (ressource != null) {
			if (ressource instanceof IndividuUlr) {
				ctrl.setIndividuUlrRelationship((IndividuUlr) ressource);
			}
		}
		return ctrl;
	}

	public static ContrainteSemaine createContrainteSemaineCascade(EOEditingContext ec, GregorianCalendar date, FormationAnnee formationAnnee,
			IRessourceContrainte ressource) {
		if (date == null) {
			return null;
		}
		ContrainteSemaine ctrl = createContrainteSemaine(ec, date, formationAnnee, ressource);

		GregorianCalendar localDate = (GregorianCalendar) date.clone();
		localDate.set(GregorianCalendar.HOUR_OF_DAY, 0);
		localDate.set(GregorianCalendar.MINUTE, 0);
		localDate.set(GregorianCalendar.SECOND, 0);
		localDate.set(GregorianCalendar.MILLISECOND, 0);
		for (int i = 0; i < 7; i++) {
			ctrl.addToContrainteJoursRelationship(createContrainteJourCascade(ec, ctrl, localDate));
			localDate.add(GregorianCalendar.DATE, 1);
		}
		return ctrl;
	}

	public static ContrainteJour createContrainteJour(EOEditingContext ec, ContrainteSemaine contrainteSemaine, GregorianCalendar day) {
		if (day == null) {
			return null;
		}
		GregorianCalendar localDay = (GregorianCalendar) day.clone();
		localDay.set(GregorianCalendar.HOUR_OF_DAY, 0);
		localDay.set(GregorianCalendar.MINUTE, 0);
		localDay.set(GregorianCalendar.SECOND, 0);
		localDay.set(GregorianCalendar.MILLISECOND, 0);
		NSTimestamp dayTimestamp = new NSTimestamp(localDay.getTime());
		ContrainteJour ctrl = ContrainteJour.createContrainteJour(ec, dayTimestamp, Integer.valueOf(DateCtrl.getDayOfWeek(dayTimestamp)),
				contrainteSemaine);
		return ctrl;
	}

	public static ContrainteJour createContrainteJourCascade(EOEditingContext ec, ContrainteSemaine contrainteSemaine, GregorianCalendar day) {
		if (day == null) {
			return null;
		}
		ContrainteJour ctrl = createContrainteJour(ec, contrainteSemaine, day);

		GregorianCalendar localDay = (GregorianCalendar) day.clone();
		localDay.set(GregorianCalendar.HOUR_OF_DAY, 0);
		localDay.set(GregorianCalendar.MINUTE, 0);
		localDay.set(GregorianCalendar.SECOND, 0);
		localDay.set(GregorianCalendar.MILLISECOND, 0);

		GregorianCalendar localDayEnd = (GregorianCalendar) localDay.clone();
		localDayEnd.set(GregorianCalendar.HOUR_OF_DAY, 23);
		localDayEnd.set(GregorianCalendar.MINUTE, 59);
		localDayEnd.set(GregorianCalendar.SECOND, 59);
		localDayEnd.set(GregorianCalendar.MILLISECOND, 999);
		ctrl.addToContrainteHeuresRelationship(createContrainteHeure(ec, ctrl, localDay, localDayEnd));
		return ctrl;
	}

	public static ContrainteHeure createContrainteHeure(EOEditingContext ec, ContrainteJour contrainteJour, GregorianCalendar start,
			GregorianCalendar end) {
		if (start == null || end == null) {
			return null;
		}
		ContrainteHeure ctrl = ContrainteHeure.createContrainteHeure(ec, new NSTimestamp(start.getTime()), new NSTimestamp(end.getTime()),
				contrainteJour);
		return ctrl;
	}
}
