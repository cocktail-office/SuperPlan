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

import org.cocktail.superplan.client.IMaquetteAp;

import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSValidation;

public class MaquetteAp extends _MaquetteAp implements IMaquetteAp {

	public MaquetteAp() {
		super();
	}

	public String toString() {
		try {
			return mapLibelle() + " - " + vScolMaquetteApEc().maquetteEc().mecCode();
		}
		catch (Exception e) {
			return mapLibelle();
		}
	}

	public String toStringEcCode() {
		try {
			return vScolMaquetteApEc().maquetteEc().mecCode() + " - " + mapLibelle();
		}
		catch (Exception e) {
			return mapLibelle();
		}
	}

	public CtrlParamAp ctrlParamAp(ScolGroupeGrp grp) {
		CtrlParamAp cpa = null;
		NSArray<CtrlParamAp> cpas = null;
		if (grp == null) {
			cpas = ctrlParamAps(new EOKeyValueQualifier(CtrlParamAp.SCOL_GROUPE_GRP_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
					NSKeyValueCoding.NullValue));
		}
		else {
			cpas = ctrlParamAps(new EOKeyValueQualifier(CtrlParamAp.SCOL_GROUPE_GRP_KEY, EOKeyValueQualifier.QualifierOperatorEqual, grp));
		}
		if (cpas != null && cpas.count() > 0) {
			cpa = cpas.lastObject();
		}
		return cpa;
	}

	public Integer getEffectif() {
		VScolInscritsAp vs = VScolInscritsAp.fetchVScolInscritsAp(this.editingContext(), VScolInscritsAp.MAQUETTE_AP_KEY, this);
		if (vs != null) {
			return vs.effectif();
		}
		return null;
	}

	public VMaquetteApGroupes vMaquetteApGroupes(ScolGroupeGrp grp) {
		VMaquetteApGroupes vap = null;
		NSArray<VMaquetteApGroupes> vaps = null;
		if (grp == null) {
			vaps = vMaquetteApGroupeses(new EOKeyValueQualifier(VMaquetteApGroupes.SCOL_GROUPE_GRP_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
					NSKeyValueCoding.NullValue));
		}
		else {
			vaps = vMaquetteApGroupeses(new EOKeyValueQualifier(VMaquetteApGroupes.SCOL_GROUPE_GRP_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
					grp));
		}
		if (vaps != null && vaps.count() > 0) {
			vap = vaps.lastObject();
		}
		return vap;
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
