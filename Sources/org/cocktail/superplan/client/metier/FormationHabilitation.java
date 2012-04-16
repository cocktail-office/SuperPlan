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

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.foundation.NSValidation;

import edtscol.client.MainClient;

public class FormationHabilitation extends _FormationHabilitation {

	public static final String CTRL_PARAM_HABILITATION_KEY = "ctrlParamHabilitation";

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public FormationHabilitation() {
		super();
	}

	public boolean equalsCtrl(FormationHabilitation other) {
		if (other == null) {
			return false;
		}
		CtrlParamHabilitation ctrlThis = ctrlParamHabilitation();
		CtrlParamHabilitation ctrlOther = other.ctrlParamHabilitation();
		if (ctrlThis == null && ctrlOther == null) {
			return true;
		}
		if (ctrlThis == null && ctrlOther != null) {
			return false;
		}
		if (ctrlThis != null && ctrlOther == null) {
			return false;
		}
		if (equals(ctrlThis.cphLunHeureDeb(), ctrlOther.cphLunHeureDeb()) && equals(ctrlThis.cphLunHeureFin(), ctrlOther.cphLunHeureFin())
				&& equals(ctrlThis.cphMarHeureDeb(), ctrlOther.cphMarHeureDeb()) && equals(ctrlThis.cphMarHeureDeb(), ctrlOther.cphMarHeureDeb())
				&& equals(ctrlThis.cphMerHeureDeb(), ctrlOther.cphMerHeureDeb()) && equals(ctrlThis.cphMerHeureDeb(), ctrlOther.cphMerHeureDeb())
				&& equals(ctrlThis.cphJeuHeureDeb(), ctrlOther.cphJeuHeureDeb()) && equals(ctrlThis.cphJeuHeureDeb(), ctrlOther.cphJeuHeureDeb())
				&& equals(ctrlThis.cphVenHeureDeb(), ctrlOther.cphVenHeureDeb()) && equals(ctrlThis.cphVenHeureDeb(), ctrlOther.cphVenHeureDeb())
				&& equals(ctrlThis.cphSamHeureDeb(), ctrlOther.cphSamHeureDeb()) && equals(ctrlThis.cphSamHeureDeb(), ctrlOther.cphSamHeureDeb())
				&& equals(ctrlThis.cphDimHeureDeb(), ctrlOther.cphDimHeureDeb()) && equals(ctrlThis.cphDimHeureDeb(), ctrlOther.cphDimHeureDeb())) {
			return true;
		}
		return false;
	}

	private boolean equals(Integer one, Integer two) {
		if (one == null && two == null) {
			return true;
		}
		if (one == null && two != null) {
			return false;
		}
		if (one != null && two == null) {
			return false;
		}
		if (one.intValue() == two.intValue()) {
			return true;
		}
		return false;
	}

	public CtrlParamHabilitation ctrlParamHabilitation() {
		if (ctrlParamHabilitations() == null || ctrlParamHabilitations().count() == 0) {
			return null;
		}
		return (CtrlParamHabilitation) ctrlParamHabilitations().lastObject();
	}

	private String getValueForAttributes(ArrayList attrs) {
		if (attrs == null) {
			return null;
		}
		else {
			StringBuffer value = new StringBuffer();
			int cpt = attrs.size();

			for (int i = 0; i < cpt; i++) {
				String attribute = (String) attrs.get(i);
				if (attribute.indexOf(".") != -1) {
					value.append(StringCtrl.normalize((String) valueForKeyPath(attribute)));
				}
				else {
					value.append(StringCtrl.normalize((String) storedValueForKey(attribute)));
				}
				if (i < cpt - 1) {
					value.append(" - ");
				}
			}
			return StringCtrl.normalize(value.toString());
		}
	}

	public String libelleColonne1() {
		String s = getValueForAttributes(app.getAttrColumn1Dipl());
		if (s == null) {
			s = formationSpecialisation().scolFormationDiplome().fdipAbreviation();
		}
		return s;
	}

	public String libelleColonne2() {
		String s = getValueForAttributes(app.getAttrColumn2Dipl());
		if (s == null) {
			s = formationSpecialisation().fspnLibelle();
		}
		return s;
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
