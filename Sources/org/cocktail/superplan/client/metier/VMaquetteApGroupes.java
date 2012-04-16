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

package org.cocktail.superplan.client.metier;

import java.math.BigDecimal;
import java.util.Enumeration;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSValidation;

import fr.univlr.utilities.TimeHandler;

public class VMaquetteApGroupes extends _VMaquetteApGroupes {

	public static final String TOTAL_PREVU = "totalPrevu";
	public static final String TOTAL_RESERVE = "totalReserve";
	public static final String TOTAL_PREVU_STRING = "totalPrevuString";
	public static final String TOTAL_RESERVE_STRING = "totalReserveString";
	public static final String MAP_VALEUR_STRING = "mapValeurString";
	public static final String CTRL_PARAM_AP_KEY = "ctrlParamAp";

	// Attributs non visibles
	public static final String GOBJ_KEY_KEY = "gobjKey";
	public static final String MAPG_KEY_KEY = "mapgKey";
	public static final String GGRP_KEY_KEY = "ggrpKey";
	public static final String MAP_KEY_KEY = "mapKey";

	public static final String GOBJ_KEY_COLKEY = "GOBJ_KEY";
	public static final String MAPG_KEY_COLKEY = "MAPG_KEY";
	public static final String GGRP_KEY_COLKEY = "GGRP_KEY";
	public static final String MAP_KEY_COLKEY = "MAP_KEY";

	public VMaquetteApGroupes() {
		super();
	}

	public CtrlParamAp ctrlParamAp() {
		// if (ctrlParamAps() == null || ctrlParamAps().count() == 0) {
		// return null;
		// }
		// return (CtrlParamAp) ctrlParamAps().lastObject();
		if (maquetteAp() == null) {
			return null;
		}
		return (CtrlParamAp) maquetteAp().ctrlParamAp(scolGroupeGrp());
		// if (scolGroupeGrp() == null) {
		// return (CtrlParamAp) ctrlParamAps().lastObject();
		// }
		// NSArray<CtrlParamAp> cpas = ctrlParamAps(new EOKeyValueQualifier(CtrlParamAp.SCOL_GROUPE_GRP_KEY,
		// EOKeyValueQualifier.QualifierOperatorEqual,
		// scolGroupeGrp()));
		// if (cpas == null || cpas.count() == 0) {
		// return null;
		// }
		// return cpas.lastObject();
	}

	public boolean equalsCtrl(VMaquetteApGroupes other) {
		if (other == null) {
			return false;
		}
		CtrlParamAp ctrlThis = ctrlParamAp();
		CtrlParamAp ctrlOther = other.ctrlParamAp();
		if (ctrlThis == null && ctrlOther == null) {
			return true;
		}
		if (ctrlThis == null && ctrlOther != null) {
			return false;
		}
		if (ctrlThis != null && ctrlOther == null) {
			return false;
		}
		if (!equals(ctrlThis.cpaLunHeureDeb(), ctrlOther.cpaLunHeureDeb()) || !equals(ctrlThis.cpaLunHeureFin(), ctrlOther.cpaLunHeureFin())
				|| !equals(ctrlThis.cpaMarHeureDeb(), ctrlOther.cpaMarHeureDeb()) || !equals(ctrlThis.cpaMarHeureDeb(), ctrlOther.cpaMarHeureDeb())
				|| !equals(ctrlThis.cpaMerHeureDeb(), ctrlOther.cpaMerHeureDeb()) || !equals(ctrlThis.cpaMerHeureDeb(), ctrlOther.cpaMerHeureDeb())
				|| !equals(ctrlThis.cpaJeuHeureDeb(), ctrlOther.cpaJeuHeureDeb()) || !equals(ctrlThis.cpaJeuHeureDeb(), ctrlOther.cpaJeuHeureDeb())
				|| !equals(ctrlThis.cpaVenHeureDeb(), ctrlOther.cpaVenHeureDeb()) || !equals(ctrlThis.cpaVenHeureDeb(), ctrlOther.cpaVenHeureDeb())
				|| !equals(ctrlThis.cpaSamHeureDeb(), ctrlOther.cpaSamHeureDeb()) || !equals(ctrlThis.cpaSamHeureDeb(), ctrlOther.cpaSamHeureDeb())
				|| !equals(ctrlThis.cpaDimHeureDeb(), ctrlOther.cpaDimHeureDeb()) || !equals(ctrlThis.cpaDimHeureDeb(), ctrlOther.cpaDimHeureDeb())
				|| !equals(ctrlThis.cpaDuree(), ctrlOther.cpaDuree())) {
			return false;
		}
		if (equalsOnIndividu(ctrlThis.ctrlParamApIndividus(), ctrlOther.ctrlParamApIndividus()) == false) {
			return false;
		}
		if (equalsOnSalle(ctrlThis.ctrlParamApSalles(), ctrlOther.ctrlParamApSalles()) == false) {
			return false;
		}
		if (equalsOnSalleChoix(ctrlThis.ctrlParamApSalleChoixs(), ctrlOther.ctrlParamApSalleChoixs()) == false) {
			return false;
		}
		if (equalsOnObjet(ctrlThis.ctrlParamApObjets(), ctrlOther.ctrlParamApObjets()) == false) {
			return false;
		}

		return true;
	}

	private boolean equalsOnIndividu(NSArray<CtrlParamApIndividu> a, NSArray<CtrlParamApIndividu> b) {
		if (equalsArrayOnCount(a, b) == false) {
			return false;
		}
		NSArray<IndividuUlr> arrayA = (NSArray<IndividuUlr>) a.valueForKey(CtrlParamApIndividu.INDIVIDU_KEY);
		NSArray<IndividuUlr> arrayB = (NSArray<IndividuUlr>) b.valueForKey(CtrlParamApIndividu.INDIVIDU_KEY);
		return equalsArrayOnElements(arrayA, arrayB);
	}

	private boolean equalsOnSalle(NSArray<CtrlParamApSalle> a, NSArray<CtrlParamApSalle> b) {
		if (equalsArrayOnCount(a, b) == false) {
			return false;
		}
		NSArray<Salles> arrayA = (NSArray<Salles>) a.valueForKey(CtrlParamApSalle.SALLE_KEY);
		NSArray<Salles> arrayB = (NSArray<Salles>) b.valueForKey(CtrlParamApSalle.SALLE_KEY);
		return equalsArrayOnElements(arrayA, arrayB);
	}

	private boolean equalsOnSalleChoix(NSArray<CtrlParamApSalleChoix> a, NSArray<CtrlParamApSalleChoix> b) {
		if (equalsArrayOnCount(a, b) == false) {
			return false;
		}
		NSArray<Salles> arrayA = (NSArray<Salles>) a.valueForKey(CtrlParamApSalleChoix.SALLE_KEY);
		NSArray<Salles> arrayB = (NSArray<Salles>) b.valueForKey(CtrlParamApSalleChoix.SALLE_KEY);
		return equalsArrayOnElements(arrayA, arrayB);
	}

	private boolean equalsOnObjet(NSArray<CtrlParamApObjet> a, NSArray<CtrlParamApObjet> b) {
		if (equalsArrayOnCount(a, b) == false) {
			return false;
		}
		NSArray<ResaObjet> arrayA = (NSArray<ResaObjet>) a.valueForKey(CtrlParamApObjet.RESA_OBJET_KEY);
		NSArray<ResaObjet> arrayB = (NSArray<ResaObjet>) b.valueForKey(CtrlParamApObjet.RESA_OBJET_KEY);
		return equalsArrayOnElements(arrayA, arrayB);
	}

	private boolean equalsArrayOnCount(NSArray<?> a, NSArray<?> b) {
		if ((a == null || a.count() == 0) && (b == null || b.count() == 0)) {
			return true;
		}
		if ((a == null || a.count() == 0) && (b != null && b.count() > 0)) {
			return false;
		}
		if ((a != null && a.count() > 0) && (b == null || b.count() == 0)) {
			return false;
		}
		if (a.count() != b.count()) {
			return false;
		}
		return true;
	}

	private boolean equalsArrayOnElements(NSArray<?> a, NSArray<?> b) {
		Enumeration<?> en = a.objectEnumerator();
		while (en.hasMoreElements()) {
			if (b.containsObject(en.nextElement()) == false) {
				return false;
			}
		}
		return true;
	}

	private boolean equals(Integer a, Integer b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		if (a.intValue() == b.intValue()) {
			return true;
		}
		return false;
	}

	public BigDecimal totalPrevu() {
		return new BigDecimal(totalPrevuMinutes() / 60);
	}

	public BigDecimal totalReserve() {
		return new BigDecimal((double) totalReserveMinutes() / 60);
	}

	public BigDecimal totalRestantAPlacer() {
		return totalPrevu().subtract(totalReserve());
	}

	public String mapValeurString() {
		int minutes = mapValeur().multiply(new BigDecimal(60)).intValue();
		return toString(minutes);
	}

	public String totalPrevuString() {
		int minutes = totalPrevuMinutes();
		return toString(minutes);
	}

	public String totalReserveString() {
		int minutes = totalReserveMinutes();
		return toString(minutes);
	}

	private String toString(int minutes) {
		return "" + (minutes / 60) + ":" + (minutes % 60 < 10 ? "0" : "") + (minutes % 60);
	}

	private int totalPrevuMinutes() {
		double total = mapValeur().doubleValue() * 60;
		Number grpPrevus = mapGroupePrevu();
		if (grpPrevus != null) {
			total = total * grpPrevus.doubleValue();
		}
		return (int) total;
	}

	private int totalReserveMinutes() {
		int total = 0;
		if (maquetteAp() != null && maquetteAp().reservationAps() != null) {
			Enumeration<ReservationAp> enumReservationAp = maquetteAp().reservationAps().objectEnumerator();
			while (enumReservationAp.hasMoreElements()) {
				ReservationAp resaAp = enumReservationAp.nextElement();
				if (scolGroupeGrp() == null || scolGroupeGrp().equals(resaAp.scolGroupeGrp())) {
					if (resaAp.reservation() != null && resaAp.reservation().periodicites() != null) {
						Enumeration<Periodicite> enumPeriodicite = resaAp.reservation().periodicites().objectEnumerator();
						while (enumPeriodicite.hasMoreElements()) {
							Periodicite periodicite = enumPeriodicite.nextElement();
							total += TimeHandler.minutesSeparatingDates(periodicite.dateDeb(), periodicite.dateFin());
						}
					}
				}
			}
		}
		return total;
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
