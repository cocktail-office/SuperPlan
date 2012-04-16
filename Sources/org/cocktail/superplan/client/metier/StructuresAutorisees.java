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

import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSValidation;

public class StructuresAutorisees extends _StructuresAutorisees {

	public StructuresAutorisees() {
		super();
	}

	/** renvoie les repartitions -RepartStructure- groupe-membre de la structure */
	public NSArray getRepartsStructure() {
		int cStructure = numeroGroupe().intValue();
		EOQualifier qualRepart = EOQualifier.qualifierWithQualifierFormat(RepartStructure.C_STRUCTURE_KEY + " = %@",
				new NSArray(String.valueOf(cStructure)));
		EOFetchSpecification fetchSpec = new EOFetchSpecification(RepartStructure.ENTITY_NAME, qualRepart, null);
		fetchSpec.setUsesDistinct(true);
		NSArray<RepartStructure> reparts = editingContext().objectsWithFetchSpecification(fetchSpec);
		return reparts;
	}

	/** renvoie les membres -IndividuUlr- du groupe */
	public NSArray structureMembers() {
		NSArray repartStructures = this.getRepartsStructure();
		NSMutableArray membres = new NSMutableArray();

		for (int i = 0; i < repartStructures.count(); i++) {
			RepartStructure currentRepart = (RepartStructure) repartStructures.objectAtIndex(i);
			NSArray individus = currentRepart.individuUlr();
			if (individus != null && individus.count() > 0) {
				membres.addObject(individus.objectAtIndex(0));
			}
		}
		return membres;
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