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

public class CtrlParamAp extends _CtrlParamAp implements ICtrlParam {

	// Attributs non visibles
	public static final String MAP_KEY_KEY = "mapKey";
	public static final String CPA_KEY_KEY = "cpaKey";

	public static final String MAP_KEY_COLKEY = "MAP_KEY";
	public static final String CPA_KEY_COLKEY = "CPA_KEY";
	
	public static final String IS_PARAM_SAISIS = "isParamSaisis";

	public CtrlParamAp() {
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
			setCpaLunHeureDeb(heuredeb);
			setCpaLunHeureFin(heurefin);
		case 1:
			setCpaMarHeureDeb(heuredeb);
			setCpaMarHeureFin(heurefin);
		case 2:
			setCpaMerHeureDeb(heuredeb);
			setCpaMerHeureFin(heurefin);
		case 3:
			setCpaJeuHeureDeb(heuredeb);
			setCpaJeuHeureFin(heurefin);
		case 4:
			setCpaVenHeureDeb(heuredeb);
			setCpaVenHeureFin(heurefin);
		case 5:
			setCpaSamHeureDeb(heuredeb);
			setCpaSamHeureFin(heurefin);
		case 6:
			setCpaDimHeureDeb(heuredeb);
			setCpaDimHeureFin(heurefin);
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
			return cpaLunHeureDeb();
		case 1:
			return cpaMarHeureDeb();
		case 2:
			return cpaMerHeureDeb();
		case 3:
			return cpaJeuHeureDeb();
		case 4:
			return cpaVenHeureDeb();
		case 5:
			return cpaSamHeureDeb();
		case 6:
			return cpaDimHeureDeb();
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
			return cpaLunHeureFin();
		case 1:
			return cpaMarHeureFin();
		case 2:
			return cpaMerHeureFin();
		case 3:
			return cpaJeuHeureFin();
		case 4:
			return cpaVenHeureFin();
		case 5:
			return cpaSamHeureFin();
		case 6:
			return cpaDimHeureFin();
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
		return cpaDuree();
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
		if (cpaDuree() == null) {
			return false;
		}
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
		if (isUtilisable() == false) {
			return false;
		}
		if (ctrlParamApIndividus() == null || ctrlParamApIndividus().count() == 0) {
			return false;
		}
		if (ctrlParamApSalles() == null || ctrlParamApSalles().count() == 0) {
			if (ctrlParamApSalleChoixs() == null || ctrlParamApSalleChoixs().count() == 0) {
				return false;
			}
		}
		return true;
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
