/*
 * Copyright COCKTAIL (www.cocktail.org), 2001, 2012 
 * 
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software. You can use, 
 * modify and/or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty and the software's author, the holder of the
 * economic rights, and the successive licensors have only limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading, using, modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean that it is complicated to manipulate, and that also
 * therefore means that it is reserved for developers and experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and, more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */

// DO NOT EDIT.  Make changes to ScolInscriptionSemestre.java instead.
package org.cocktail.superplan.server.metier;

import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

public abstract class _ScolInscriptionSemestre extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolInscriptionSemestre";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_INSCRIPTION_SEMESTRE";

	// Attributes

	public static final String FANN_KEY_KEY = "fannKey";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";

	// Relationships
	public static final String MAQUETTE_REPARTITION_SEM_KEY = "maquetteRepartitionSem";

	// Utilities methods
	  public ScolInscriptionSemestre localInstanceIn(EOEditingContext editingContext) {
	    ScolInscriptionSemestre localInstance = (ScolInscriptionSemestre)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolInscriptionSemestre getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolInscriptionSemestre.ENTITY_NAME);
		      return (ScolInscriptionSemestre) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public org.cocktail.superplan.server.metier.MaquetteRepartitionSem maquetteRepartitionSem() {
    return (org.cocktail.superplan.server.metier.MaquetteRepartitionSem)storedValueForKey("maquetteRepartitionSem");
  }

  public void setMaquetteRepartitionSemRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionSem value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteRepartitionSem oldValue = maquetteRepartitionSem();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteRepartitionSem");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteRepartitionSem");
    }
  }
  

  public static ScolInscriptionSemestre createScolInscriptionSemestre(EOEditingContext editingContext, Integer fannKey
, org.cocktail.superplan.server.metier.MaquetteRepartitionSem maquetteRepartitionSem) {
    ScolInscriptionSemestre eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolInscriptionSemestre.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolInscriptionSemestre.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolInscriptionSemestre) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
    eo.setMaquetteRepartitionSemRelationship(maquetteRepartitionSem);
    return eo;
  }

  public static NSArray fetchAllScolInscriptionSemestres(EOEditingContext editingContext) {
    return _ScolInscriptionSemestre.fetchAllScolInscriptionSemestres(editingContext, null);
  }

  public static NSArray fetchAllScolInscriptionSemestres(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolInscriptionSemestre.fetchScolInscriptionSemestres(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolInscriptionSemestres(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolInscriptionSemestre.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolInscriptionSemestre fetchScolInscriptionSemestre(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolInscriptionSemestre.fetchScolInscriptionSemestre(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolInscriptionSemestre fetchScolInscriptionSemestre(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolInscriptionSemestre.fetchScolInscriptionSemestres(editingContext, qualifier, null);
    ScolInscriptionSemestre eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolInscriptionSemestre)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolInscriptionSemestre that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolInscriptionSemestre fetchRequiredScolInscriptionSemestre(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolInscriptionSemestre.fetchRequiredScolInscriptionSemestre(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolInscriptionSemestre fetchRequiredScolInscriptionSemestre(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolInscriptionSemestre eoObject = _ScolInscriptionSemestre.fetchScolInscriptionSemestre(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolInscriptionSemestre that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolInscriptionSemestre localInstanceIn(EOEditingContext editingContext, ScolInscriptionSemestre eo) {
    ScolInscriptionSemestre localInstance = (eo == null) ? null : (ScolInscriptionSemestre)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
