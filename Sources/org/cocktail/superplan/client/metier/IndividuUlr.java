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

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSValidation;

import edtscol.client.gestioncontraintes.IRessourceContrainte;

public class IndividuUlr extends _IndividuUlr implements IRessourceContrainte {

	public static final EOSortOrdering SORT_NOM = EOSortOrdering.sortOrderingWithKey(IndividuUlr.NOM_USUEL_KEY, EOSortOrdering.CompareAscending);
	public static final EOSortOrdering SORT_PRENOM = EOSortOrdering.sortOrderingWithKey(IndividuUlr.PRENOM_KEY, EOSortOrdering.CompareAscending);

	public static final String PRENOM_NOM_KEY = "prenomNom";
	public static final String NOM_PRENOM_KEY = "nomPrenom";

	public IndividuUlr() {
		super();
	}

	public String toString() {
		return nomPrenom();
	}

	public String prenomNom() {
		return this.prenom() + " " + this.nomUsuel();
	}

	public String nomPrenom() {
		return this.nomUsuel() + " " + this.prenom();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edtscol.client.gestioncontraintes.IRessourceContrainte#getContrainteSemaines(org.cocktail.superplan.client.metier.FormationAnnee)
	 */
	public NSArray<ContrainteSemaine> getContrainteSemaines(FormationAnnee formationAnnee) {
		if (formationAnnee != null) {
			EOQualifier qualIndividu = new EOKeyValueQualifier(ContrainteSemaine.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual, this);
			EOQualifier qualAnnee = new EOKeyValueQualifier(ContrainteSemaine.FORMATION_ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
					formationAnnee);
			EOAndQualifier qual = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualIndividu, qualAnnee }));
			return ContrainteSemaine.fetchContrainteSemaines(editingContext(), qual,
					new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(ContrainteSemaine.CTS_DATE_KEY, EOSortOrdering.CompareAscending)));
		}
		else {
			return NSArray.EmptyArray;
		}
	}

	public static EOQualifier getQualifierForStudent() {
		return new EOKeyValueQualifier(IndividuUlr.SUPANN_CATEGORIE_KEY + "." + SupannCategorie.CAT_LIBELLE_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, SupannCategorie.CAT_LIBELLE_ETUDIANT);
	}

	public static EOQualifier getQualifierForNonStudent() {
		EOQualifier qual1 = new EOKeyValueQualifier(IndividuUlr.SUPANN_CATEGORIE_KEY + "." + SupannCategorie.CAT_LIBELLE_KEY,
				EOKeyValueQualifier.QualifierOperatorNotEqual, SupannCategorie.CAT_LIBELLE_ETUDIANT);
		EOQualifier qual2 = new EOKeyValueQualifier(IndividuUlr.SUPANN_CATEGORIE_KEY + "." + SupannCategorie.CAT_LIBELLE_KEY,
				EOKeyValueQualifier.QualifierOperatorNotEqual, SupannCategorie.CAT_LIBELLE_ANCIEN_ETUDIANT);
		EOQualifier andQual = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qual1, qual2 }));
		EOQualifier qual3 = new EOKeyValueQualifier(IndividuUlr.SUPANN_CATEGORIE_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
				NSKeyValueCoding.NullValue);
		return new EOOrQualifier(new NSArray<EOQualifier>(new EOQualifier[] { andQual, qual3 }));
	}

	// les structures dont l'individu est responsable
	// ou dont il est secrétaire
	public NSArray<StructureUlr> getStructuresRespOuSecr(String tgrp) {
		NSMutableArray<StructureUlr> allStructs = new NSMutableArray<StructureUlr>();
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.addObject(new EOKeyValueQualifier(StructureUlr.RESPONSABLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, this));
		if (tgrp != null) {
			quals.addObject(new EOKeyValueQualifier(StructureUlr.REPART_TYPE_GROUPE_KEY + "." + RepartTypeGroupe.TGRP_CODE_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, tgrp));
		}
		EOFetchSpecification myFetch = new EOFetchSpecification(StructureUlr.ENTITY_NAME, new EOAndQualifier(quals), null);
		myFetch.setUsesDistinct(true);
		allStructs.addObjectsFromArray(editingContext().objectsWithFetchSpecification(myFetch));
		quals.removeAllObjects();
		quals.addObject(new EOKeyValueQualifier(StructureUlr.SECRETARIATS_KEY + "." + Secretariat.INDIVIDU_ULR_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, this));
		if (tgrp != null) {
			quals.addObject(new EOKeyValueQualifier(StructureUlr.REPART_TYPE_GROUPE_KEY + "." + RepartTypeGroupe.TGRP_CODE_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, tgrp));
		}
		myFetch = new EOFetchSpecification(StructureUlr.ENTITY_NAME, new EOAndQualifier(quals), null);
		myFetch.setUsesDistinct(true);
		allStructs.addObjectsFromArray(editingContext().objectsWithFetchSpecification(myFetch));
		return allStructs;
	}

	public boolean isRespOuSecrFor(IndividuUlr individu) {
		boolean retour = false;
		NSArray<StructureUlr> structsRespOuSecr = getStructuresRespOuSecr(null);
		if (structsRespOuSecr != null && structsRespOuSecr.count() > 0) {
			NSArray<StructureUlr> structsIndividu = (NSArray<StructureUlr>) individu.repartStructures()
					.valueForKey(RepartStructure.STRUCTURE_ULR_KEY);
			if (structsIndividu != null && structsIndividu.count() > 0) {
				for (int i = 0; i < structsRespOuSecr.count(); i++) {
					StructureUlr structRespOuSecr = structsRespOuSecr.objectAtIndex(i);
					if (structsIndividu.containsObject(structRespOuSecr)) {
						return true;
					}
				}
			}
		}
		return retour;
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
