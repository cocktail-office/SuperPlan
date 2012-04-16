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

package org.cocktail.superplan.server.metier;

import java.math.BigDecimal;
import java.util.Enumeration;

import org.cocktail.superplan.server.IMaquetteAp;

import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSValidation;

import er.extensions.eof.ERXQ;
import fr.univlr.utilities.TimeHandler;

public class MaquetteAp extends _MaquetteAp implements IMaquetteAp {

	public static final String TOTAL_RESERVE = "totalReserve";

	public MaquetteAp() {
		super();
	}

	public String toString() {
		return mapLibelle() + "-" + mapKey();
	}

	public String colorCode() {
		return null;
	}

	public NSArray<MaquetteAp> getMaquetteApsParcours(boolean apsObligatoires, boolean apsNonObligatoires) {
		if (!apsObligatoires && !apsNonObligatoires) {
			return NSArray.EmptyArray;
		}
		NSArray<VParcoursAp> parcours = this.vParcoursAps();
		if (parcours == null || parcours.isEmpty()) {
			return NSArray.EmptyArray;
		}
		NSMutableArray<EOQualifier> orQualifiers = new NSMutableArray<EOQualifier>();
		NSMutableArray<EOQualifier> andQualifiers = new NSMutableArray<EOQualifier>();

		andQualifiers.addObject(ERXQ.equals(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.FANN_KEY_KEY, fannKey()));
		andQualifiers.addObject(ERXQ.notEquals(MaquetteAp.MAP_KEY_KEY, mapKey()));
		if (!apsObligatoires || !apsNonObligatoires) {
			if (apsObligatoires) {
				andQualifiers.addObject(ERXQ.equals(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.MTEC_CODE_KEY, "O"));
			}
			else {
				andQualifiers.addObject(ERXQ.notEquals(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.MTEC_CODE_KEY, "O"));
			}
		}

		Enumeration<VParcoursAp> enumParcours = parcours.objectEnumerator();
		while (enumParcours.hasMoreElements()) {
			VParcoursAp vParcoursAp = enumParcours.nextElement();

			// Recherche dans le meme parcours / meme semestre
			EOQualifier qualSemestre = ERXQ.equals(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY, vParcoursAp.semestre());
			EOQualifier qualParcours = ERXQ.equals(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY, vParcoursAp.parcours());
			orQualifiers.addObject(ERXQ.and(qualParcours, qualSemestre));

			// si on n'est pas dans le parcours commun, recherche dans le parcours commun de la meme specialisation et meme semestre
			if (vParcoursAp.parcours().isParcoursCommun() == false) {
				qualParcours = ERXQ.isNotNull(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.PARCOURS_KEY + "."
						+ MaquetteParcours.MPAR_ABREVIATION_KEY);
				EOQualifier qualSpecialisation = ERXQ.equals(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SPECIALISATION_KEY,
						vParcoursAp.specialisation());
				orQualifiers.addObject(ERXQ.and(qualParcours, qualSemestre, qualSpecialisation));
			}
		}
		andQualifiers.addObject(ERXQ.or(orQualifiers));
		return MaquetteAp.fetchMaquetteAps(editingContext(), ERXQ.and(andQualifiers), null);
	}

	public BigDecimal totalPrevu() {
		double total = mapValeur().doubleValue();
		Number grpPrevus = mapGroupePrevu();
		if (grpPrevus != null) {
			total = total * grpPrevus.doubleValue();
		}
		return new BigDecimal(total);
	}

	public BigDecimal totalReserve() {
		int total = 0;
		if (reservationAps() != null) {
			Enumeration<ReservationAp> enumReservationAp = reservationAps().objectEnumerator();
			while (enumReservationAp.hasMoreElements()) {
				ReservationAp resaAp = enumReservationAp.nextElement();
				if (resaAp.reservation() != null && resaAp.reservation().periodicites() != null) {
					Enumeration<Periodicite> enumPeriodicite = resaAp.reservation().periodicites().objectEnumerator();
					while (enumPeriodicite.hasMoreElements()) {
						Periodicite periodicite = enumPeriodicite.nextElement();
						total += TimeHandler.minutesSeparatingDates(periodicite.dateDeb(), periodicite.dateFin());
					}
				}
			}
		}
		return new BigDecimal(total / 60);
	}

	public void validateForInsert() throws NSValidation.ValidationException {
		this.validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForInsert();
	}

	public void validateForUpdate() throws NSValidation.ValidationException {
		this.validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForUpdate();
	}

	public void validateForDelete() throws NSValidation.ValidationException {
		super.validateForDelete();
	}

	/**
	 * Apparemment cette methode n'est pas appelée.
	 * 
	 * @see com.webobjects.eocontrol.EOValidation#validateForUpdate()
	 */
	public void validateForSave() throws NSValidation.ValidationException {
		validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForSave();

	}

	/**
	 * Peut etre appele à partir des factories.
	 * 
	 * @throws NSValidation.ValidationException
	 */
	public void validateObjectMetier() throws NSValidation.ValidationException {

	}

	/**
	 * A appeler par les validateforsave, forinsert, forupdate.
	 * 
	 */
	private final void validateBeforeTransactionSave() throws NSValidation.ValidationException {

	}

}
