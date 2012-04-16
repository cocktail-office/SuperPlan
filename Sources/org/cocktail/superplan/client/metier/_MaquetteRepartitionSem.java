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

// DO NOT EDIT.  Make changes to MaquetteRepartitionSem.java instead.
package org.cocktail.superplan.client.metier;

import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

public abstract class _MaquetteRepartitionSem extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteRepartitionSem";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_REPARTITION_SEM";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mrsemKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MPAR_KEY_KEY = "mparKey";
	public static final String MRSEM_KEY_KEY = "mrsemKey";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String MPAR_KEY_COLKEY = "MPAR_KEY";
	public static final String MRSEM_KEY_COLKEY = "MRSEM_KEY";

	// Relationships
	public static final String PARCOURS_KEY = "parcours";
	public static final String SEMESTRE_KEY = "semestre";

	// Utilities methods
	  public MaquetteRepartitionSem localInstanceIn(EOEditingContext editingContext) {
	    MaquetteRepartitionSem localInstance = (MaquetteRepartitionSem)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteRepartitionSem getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteRepartitionSem.ENTITY_NAME);
		      return (MaquetteRepartitionSem) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer mparKey() {
    return (Integer) storedValueForKey("mparKey");
  }

  public void setMparKey(Integer value) {
    takeStoredValueForKey(value, "mparKey");
  }

  public Integer mrsemKey() {
    return (Integer) storedValueForKey("mrsemKey");
  }

  public void setMrsemKey(Integer value) {
    takeStoredValueForKey(value, "mrsemKey");
  }

  public org.cocktail.superplan.client.metier.MaquetteParcours parcours() {
    return (org.cocktail.superplan.client.metier.MaquetteParcours)storedValueForKey("parcours");
  }

  public void setParcoursRelationship(org.cocktail.superplan.client.metier.MaquetteParcours value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteParcours oldValue = parcours();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "parcours");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "parcours");
    }
  }
  
  public org.cocktail.superplan.client.metier.MaquetteSemestre semestre() {
    return (org.cocktail.superplan.client.metier.MaquetteSemestre)storedValueForKey("semestre");
  }

  public void setSemestreRelationship(org.cocktail.superplan.client.metier.MaquetteSemestre value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteSemestre oldValue = semestre();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "semestre");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "semestre");
    }
  }
  

  public static MaquetteRepartitionSem createMaquetteRepartitionSem(EOEditingContext editingContext, Integer fannKey
, Integer mparKey
, Integer mrsemKey
) {
    MaquetteRepartitionSem eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteRepartitionSem.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteRepartitionSem.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteRepartitionSem) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setMparKey(mparKey);
		eo.setMrsemKey(mrsemKey);
    return eo;
  }

  public static NSArray fetchAllMaquetteRepartitionSems(EOEditingContext editingContext) {
    return _MaquetteRepartitionSem.fetchAllMaquetteRepartitionSems(editingContext, null);
  }

  public static NSArray fetchAllMaquetteRepartitionSems(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteRepartitionSem.fetchMaquetteRepartitionSems(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteRepartitionSems(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteRepartitionSem.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteRepartitionSem fetchMaquetteRepartitionSem(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteRepartitionSem.fetchMaquetteRepartitionSem(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteRepartitionSem fetchMaquetteRepartitionSem(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteRepartitionSem.fetchMaquetteRepartitionSems(editingContext, qualifier, null);
    MaquetteRepartitionSem eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteRepartitionSem)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteRepartitionSem that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteRepartitionSem fetchRequiredMaquetteRepartitionSem(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteRepartitionSem.fetchRequiredMaquetteRepartitionSem(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteRepartitionSem fetchRequiredMaquetteRepartitionSem(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteRepartitionSem eoObject = _MaquetteRepartitionSem.fetchMaquetteRepartitionSem(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteRepartitionSem that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteRepartitionSem localInstanceIn(EOEditingContext editingContext, MaquetteRepartitionSem eo) {
    MaquetteRepartitionSem localInstance = (eo == null) ? null : (MaquetteRepartitionSem)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
