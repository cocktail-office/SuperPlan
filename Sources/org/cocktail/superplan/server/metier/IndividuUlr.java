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

import java.util.Enumeration;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSValidation;

public class IndividuUlr extends _IndividuUlr {

	public IndividuUlr() {
		super();
	}

	public String toString() {
		return nomPrenom();
	}

	public String prenomNom() {
		return prenom() + " " + nomUsuel();
	}

	public String nomPrenom() {
		return nomUsuel() + " " + prenom();
	}

	public boolean memeServiceQue(IndividuUlr autreIndividu) {
		if (autreIndividu == null) {
			return false;
		}
		if (this.equals(autreIndividu)) {
			return true;
		}

		NSArray<StructureUlr> structuresIndividu = null;
		NSArray<StructureUlr> structuresAutreIndividu = null;

		// recherche par les affectations de Mangue
		NSMutableArray<EOQualifier> qualCommunArray = new NSMutableArray<EOQualifier>();
		qualCommunArray.addObject(new EOKeyValueQualifier(Affectation.TEM_VALIDE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O"));
		qualCommunArray.addObject(new EOKeyValueQualifier(Affectation.D_DEB_AFFECTATION_KEY, EOKeyValueQualifier.QualifierOperatorLessThanOrEqualTo,
				DateCtrl.now()));
		EOQualifier qualDateFin1 = new EOKeyValueQualifier(Affectation.D_FIN_AFFECTATION_KEY,
				EOKeyValueQualifier.QualifierOperatorGreaterThanOrEqualTo, DateCtrl.now());
		EOQualifier qualDateFin2 = new EOKeyValueQualifier(Affectation.D_FIN_AFFECTATION_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
				NSKeyValueCoding.NullValue);
		qualCommunArray.addObject(new EOOrQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualDateFin1, qualDateFin2 })));
		EOQualifier qualCommun = new EOAndQualifier(qualCommunArray);

		// this
		EOQualifier qualThisMangue = new EOKeyValueQualifier(Affectation.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual, this);
		structuresIndividu = (NSArray<StructureUlr>) Affectation.fetchAffectations(editingContext(),
				new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualCommun, qualThisMangue })), null).valueForKey(
				Affectation.STRUCTURE_ULR_KEY);
		// autreIndividu
		EOQualifier qualAutreMangue = new EOKeyValueQualifier(Affectation.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual, autreIndividu);
		structuresAutreIndividu = (NSArray<StructureUlr>) Affectation.fetchAffectations(editingContext(),
				new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualCommun, qualAutreMangue })), null).valueForKey(
				Affectation.STRUCTURE_ULR_KEY);

		if (structuresIndividu == null || structuresIndividu.count() == 0 || structuresAutreIndividu == null || structuresAutreIndividu.count() == 0) {
			// recherche par les structures de GRHUM
			NSMutableArray<EOQualifier> qualServicesArray = new NSMutableArray<EOQualifier>();
			// Service
			qualServicesArray.addObject(new EOKeyValueQualifier(RepartStructure.STRUCTURE_ULR_KEY + "." + StructureUlr.REPART_TYPE_GROUPE_KEY + "."
					+ RepartTypeGroupe.TGRP_CODE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "S"));
			// LAboratoire
			qualServicesArray.addObject(new EOKeyValueQualifier(RepartStructure.STRUCTURE_ULR_KEY + "." + StructureUlr.REPART_TYPE_GROUPE_KEY + "."
					+ RepartTypeGroupe.TGRP_CODE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "LA"));
			// DEpartement
			qualServicesArray.addObject(new EOKeyValueQualifier(RepartStructure.STRUCTURE_ULR_KEY + "." + StructureUlr.REPART_TYPE_GROUPE_KEY + "."
					+ RepartTypeGroupe.TGRP_CODE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "DE"));
			EOQualifier qualServices = new EOOrQualifier(qualServicesArray);

			if (structuresIndividu == null || structuresIndividu.count() == 0) {
				// this
				EOQualifier qualThisGrhum = new EOKeyValueQualifier(RepartStructure.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
						this);
				structuresIndividu = (NSArray<StructureUlr>) RepartStructure.fetchRepartStructures(editingContext(),
						new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualServices, qualThisGrhum })), null).valueForKey(
						RepartStructure.STRUCTURE_ULR_KEY);
			}
			if (structuresAutreIndividu == null || structuresAutreIndividu.count() == 0) {
				// autreIndividu
				EOQualifier qualAutreGrhum = new EOKeyValueQualifier(RepartStructure.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
						autreIndividu);
				structuresAutreIndividu = (NSArray<StructureUlr>) RepartStructure.fetchRepartStructures(editingContext(),
						new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualServices, qualAutreGrhum })), null).valueForKey(
						RepartStructure.STRUCTURE_ULR_KEY);
			}
		}

		if (structuresIndividu == null || structuresIndividu.count() == 0 || structuresAutreIndividu == null || structuresAutreIndividu.count() == 0) {
			return false;
		}

		Enumeration<StructureUlr> enumStructuresIndividu = structuresIndividu.objectEnumerator();
		boolean memeService = false;
		while (enumStructuresIndividu.hasMoreElements() && !memeService) {
			StructureUlr structureIndividu = enumStructuresIndividu.nextElement();
			if (structuresAutreIndividu.containsObject(structureIndividu)) {
				memeService = true;
			}
		}

		return memeService;
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
