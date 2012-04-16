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

// DO NOT EDIT.  Make changes to ScolTransfertMap.java instead.
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

public abstract class _ScolTransfertMap extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolTransfertMap";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_TRANSFERT_MAP";

	// Attributes



	// Relationships
	public static final String NEW_AP_KEY = "newAp";
	public static final String OLD_AP_KEY = "oldAp";

	// Utilities methods
	  public ScolTransfertMap localInstanceIn(EOEditingContext editingContext) {
	    ScolTransfertMap localInstance = (ScolTransfertMap)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolTransfertMap getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolTransfertMap.ENTITY_NAME);
		      return (ScolTransfertMap) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public org.cocktail.superplan.server.metier.MaquetteAp newAp() {
    return (org.cocktail.superplan.server.metier.MaquetteAp)storedValueForKey("newAp");
  }

  public void setNewApRelationship(org.cocktail.superplan.server.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteAp oldValue = newAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "newAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "newAp");
    }
  }
  
  public org.cocktail.superplan.server.metier.MaquetteAp oldAp() {
    return (org.cocktail.superplan.server.metier.MaquetteAp)storedValueForKey("oldAp");
  }

  public void setOldApRelationship(org.cocktail.superplan.server.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteAp oldValue = oldAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "oldAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "oldAp");
    }
  }
  

  public static ScolTransfertMap createScolTransfertMap(EOEditingContext editingContext, org.cocktail.superplan.server.metier.MaquetteAp newAp, org.cocktail.superplan.server.metier.MaquetteAp oldAp) {
    ScolTransfertMap eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolTransfertMap.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolTransfertMap.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolTransfertMap) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setNewApRelationship(newAp);
    eo.setOldApRelationship(oldAp);
    return eo;
  }

  public static NSArray fetchAllScolTransfertMaps(EOEditingContext editingContext) {
    return _ScolTransfertMap.fetchAllScolTransfertMaps(editingContext, null);
  }

  public static NSArray fetchAllScolTransfertMaps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolTransfertMap.fetchScolTransfertMaps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolTransfertMaps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolTransfertMap.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolTransfertMap fetchScolTransfertMap(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolTransfertMap.fetchScolTransfertMap(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolTransfertMap fetchScolTransfertMap(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolTransfertMap.fetchScolTransfertMaps(editingContext, qualifier, null);
    ScolTransfertMap eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolTransfertMap)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolTransfertMap that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolTransfertMap fetchRequiredScolTransfertMap(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolTransfertMap.fetchRequiredScolTransfertMap(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolTransfertMap fetchRequiredScolTransfertMap(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolTransfertMap eoObject = _ScolTransfertMap.fetchScolTransfertMap(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolTransfertMap that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolTransfertMap localInstanceIn(EOEditingContext editingContext, ScolTransfertMap eo) {
    ScolTransfertMap localInstance = (eo == null) ? null : (ScolTransfertMap)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
