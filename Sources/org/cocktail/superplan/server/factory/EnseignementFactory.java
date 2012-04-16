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
package org.cocktail.superplan.server.factory;

import org.cocktail.superplan.server.metier.MaquetteAp;
import org.cocktail.superplan.server.metier.MaquetteParcours;
import org.cocktail.superplan.server.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.server.metier.MaquetteSemestre;
import org.cocktail.superplan.server.metier.Periodicite;
import org.cocktail.superplan.server.metier.Reservation;
import org.cocktail.superplan.server.metier.ReservationAp;
import org.cocktail.superplan.server.metier.ScolGroupeGrp;
import org.cocktail.superplan.server.metier.ScolTransfertGrp;
import org.cocktail.superplan.server.metier.ScolTransfertMap;
import org.cocktail.superplan.server.metier.VParcoursAp;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.DBHandler;

public class EnseignementFactory {

	private EOEditingContext eContext;

	public EnseignementFactory(EOEditingContext eContext) {
		this.eContext = eContext;
	}

	/**
	 * retourne les AP qui concernent les elements pedagogiques passes en parametres. p = Parcours, pc = Parcours Commun, s = Semestre, ssi
	 * = Semestre Impair Inférieur, fannKey : Annee
	 */
	public NSArray getApForParcoursSemestres(MaquetteParcours p, MaquetteParcours pc, MaquetteSemestre s, MaquetteSemestre sii, Number fannKey,
			EOQualifier qualTypeAp) {

		NSMutableArray sumQual = new NSMutableArray();

		int msemOrdreImpair = -1;

		if (sii != null) {
			sii.msemOrdre().intValue();
		}

		NSArray semestres = new NSArray();

		if (pc != null) {
			EOQualifier qSemPC = EOQualifier.qualifierWithQualifierFormat(MaquetteRepartitionSem.MPAR_KEY_KEY + " = %@ and "
					+ MaquetteRepartitionSem.FANN_KEY_KEY + " = %@", new NSArray(new Object[] { pc.mparKey(), fannKey }));

			NSArray repartSem = DBHandler.fetchData(eContext, MaquetteRepartitionSem.ENTITY_NAME, qSemPC);
			semestres = (NSArray) repartSem.valueForKey(MaquetteRepartitionSem.SEMESTRE_KEY);
		}

		EOQualifier tmpQual;
		// boucle pour semestre impair inferieur du parcours commun
		if (sii != null) {
			for (int isem = 0; isem < semestres.count(); isem++) {
				MaquetteSemestre currentSemestre = (MaquetteSemestre) semestres.objectAtIndex(isem);
				if (currentSemestre.msemOrdre().intValue() == msemOrdreImpair) {
					tmpQual = EOQualifier.qualifierWithQualifierFormat(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + " = %@ and "
							+ MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + " = %@", new NSArray(new Object[] { pc,
							currentSemestre }));
					sumQual.addObject(tmpQual);
					break;
				}
			}
		}

		for (int isem = 0; isem < semestres.count(); isem++) {
			MaquetteSemestre currentSemestre = (MaquetteSemestre) semestres.objectAtIndex(isem);
			if (currentSemestre.msemOrdre().intValue() == s.msemOrdre().intValue()) {
				tmpQual = EOQualifier
						.qualifierWithQualifierFormat(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + " = %@ and "
								+ MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + " = %@", new NSArray(new Object[] { pc,
								currentSemestre }));
				sumQual.addObject(tmpQual);
				break;
			}
		}

		// inclusion de semestre choisi par l'utilisateur dans la requette
		tmpQual = EOQualifier.qualifierWithQualifierFormat(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + " = %@ and "
				+ MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + " = %@", new NSArray(new Object[] { p, s }));
		sumQual.addObject(tmpQual);

		// si un ssi est demande on l'inclus aussi dans la recherche
		if (sii != null) {
			tmpQual = EOQualifier.qualifierWithQualifierFormat(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + " = %@ and "
					+ MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + " = %@", new NSArray(new Object[] { p, sii }));
			sumQual.addObject(tmpQual);
		}

		tmpQual = new EOOrQualifier(sumQual);

		sumQual.removeAllObjects();
		sumQual.addObject(tmpQual);
		tmpQual = DBHandler.getSimpleQualifier(MaquetteAp.FANN_KEY_KEY, fannKey);
		sumQual.addObject(tmpQual);
		if (qualTypeAp != null) {
			sumQual.addObject(qualTypeAp);
		}
		return DBHandler.fetchData(eContext, MaquetteAp.ENTITY_NAME, new EOAndQualifier(sumQual));
	}

	/**
	 * recherche les reservations des ap passes en parametres pour les periodicites donnees
	 * 
	 * @param ap
	 *            tableau des AP dont on cherche les reservationd
	 * @param periodicites
	 *            tableau avec des suites de timestamp (debut-fin)
	 */
	public NSArray lookupReservationsEnseignement(NSArray ap, NSArray periodicites) {
		NSMutableArray sumQualifierAp = new NSMutableArray();
		for (int i = 0; i < ap.count(); i++) {
			MaquetteAp currentAp = (MaquetteAp) ap.objectAtIndex(i);
			sumQualifierAp.addObject(DBHandler.getSimpleQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
					+ ReservationAp.MAQUETTE_AP_KEY, currentAp));
		}
		NSMutableArray sumQualifierDates = new NSMutableArray();
		for (int i = 0; i < periodicites.count(); i += 2) {
			NSTimestamp deb = (NSTimestamp) periodicites.objectAtIndex(i);
			NSTimestamp fin = (NSTimestamp) periodicites.objectAtIndex(i + 1);
			sumQualifierDates.addObject(EOQualifier.qualifierWithQualifierFormat(Periodicite.DATE_FIN_KEY + " >= %@ and " + Periodicite.DATE_DEB_KEY
					+ " <= %@", new NSArray(new Object[] { deb, fin })));
		}
		EOQualifier qualAp = new EOOrQualifier(sumQualifierAp);
		EOQualifier qualDate = new EOOrQualifier(sumQualifierDates);

		EOQualifier qualResa = new EOAndQualifier(new NSArray(new Object[] { qualAp, qualDate }));
		return DBHandler.fetchData(eContext, Periodicite.ENTITY_NAME, qualResa);
	}

	/** retourne l'ap recopie de l'annee precedentes */
	public MaquetteAp getApTranfertMap(MaquetteAp oldAp) {
		ScolTransfertMap transfertAp = ScolTransfertMap.fetchScolTransfertMap(eContext, ScolTransfertMap.OLD_AP_KEY, oldAp);
		if (transfertAp != null) {
			return transfertAp.newAp();
		}
		else {
			return null;
		}
	}

	/** retourne le groupe recopie de l'annee precedentes */
	public ScolGroupeGrp getGrpTranfertGrp(ScolGroupeGrp oldGrp) {
		ScolTransfertGrp transfertGrp = ScolTransfertGrp.fetchScolTransfertGrp(eContext, ScolTransfertGrp.OLD_GRP_KEY, oldGrp);
		if (transfertGrp != null) {
			return transfertGrp.newGrp();
		}
		else {
			return null;
		}
	}

}
