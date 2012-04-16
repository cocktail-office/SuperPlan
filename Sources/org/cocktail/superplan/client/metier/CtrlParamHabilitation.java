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

import java.util.ArrayList;

import com.webobjects.foundation.NSValidation;

import edtscol.client.DayList;
import edtscol.client.recherchereservation.ICtrlParam;

public class CtrlParamHabilitation extends _CtrlParamHabilitation implements ICtrlParam {

	// Attributs non visibles
	public static final String FHAB_KEY_KEY = "fhabKey";
	public static final String CPH_KEY_KEY = "cphKey";

	public static final String FHAB_KEY_COLKEY = "FHAB_KEY";
	public static final String CPH_KEY_COLKEY = "CPH_KEY";

	public static final String IS_PARAM_SAISIS = "isParamSaisis";

	public CtrlParamHabilitation() {
		super();
	}

	public String isParamSaisis() {
		return isComplet() ? "Tous" : isUtilisable() ? "Partiel" : null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#setJourHeures(int, java.lang.Integer, java.lang.Integer)
	 */
	public void setJourHeures(int jour, Integer heuredeb, Integer heurefin) {
		switch (jour) {
		case 0:
			setCphLunHeureDeb(heuredeb);
			setCphLunHeureFin(heurefin);
		case 1:
			setCphMarHeureDeb(heuredeb);
			setCphMarHeureFin(heurefin);
		case 2:
			setCphMerHeureDeb(heuredeb);
			setCphMerHeureFin(heurefin);
		case 3:
			setCphJeuHeureDeb(heuredeb);
			setCphJeuHeureFin(heurefin);
		case 4:
			setCphVenHeureDeb(heuredeb);
			setCphVenHeureFin(heurefin);
		case 5:
			setCphSamHeureDeb(heuredeb);
			setCphSamHeureFin(heurefin);
		case 6:
			setCphDimHeureDeb(heuredeb);
			setCphDimHeureFin(heurefin);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#getHeureDeb(int)
	 */
	public Integer getHeureDeb(int jour) {
		switch (jour) {
		case 0:
			return cphLunHeureDeb();
		case 1:
			return cphMarHeureDeb();
		case 2:
			return cphMerHeureDeb();
		case 3:
			return cphJeuHeureDeb();
		case 4:
			return cphVenHeureDeb();
		case 5:
			return cphSamHeureDeb();
		case 6:
			return cphDimHeureDeb();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#getHeureDeb()
	 */
	public Integer getHeureDeb() {
		for (int i = 0; i < 7; i++) {
			if (getHeureDeb(i) != null) {
				return getHeureDeb(i);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#getHeureFin(int)
	 */
	public Integer getHeureFin(int jour) {
		switch (jour) {
		case 0:
			return cphLunHeureFin();
		case 1:
			return cphMarHeureFin();
		case 2:
			return cphMerHeureFin();
		case 3:
			return cphJeuHeureFin();
		case 4:
			return cphVenHeureFin();
		case 5:
			return cphSamHeureFin();
		case 6:
			return cphDimHeureFin();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#getHeureFin()
	 */
	public Integer getHeureFin() {
		for (int i = 0; i < 7; i++) {
			if (getHeureFin(i) != null) {
				return getHeureFin(i);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#getDuree()
	 */
	public Integer getDuree() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#jours()
	 */
	public ArrayList<Integer> jours() {
		ArrayList<Integer> jours = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			if (getHeureDeb(i) != null) {
				jours.add(DayList.getSelectedDay(i));
			}
		}
		return jours;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#isUtilisable()
	 */
	public boolean isUtilisable() {
		for (int i = 0; i < 7; i++) {
			if (getHeureDeb(i) != null && getHeureFin(i) != null) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edtscol.client.recherchereservation.ICtrlParam#isComplet()
	 */
	public boolean isComplet() {
		return false;
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
