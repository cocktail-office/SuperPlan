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

// DO NOT EDIT.  Make changes to GardienSalle.java instead.
package org.cocktail.superplan.client.metier;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

public abstract class _GardienSalle extends  EOGenericRecord {
	public static final String ENTITY_NAME = "GardienSalle";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.GARDIEN_SALLE";

	// Attributes



	// Relationships
	public static final String REPART_STRUCTURES_KEY = "repartStructures";
	public static final String SALLE_KEY = "salle";

	// Utilities methods
	  public GardienSalle localInstanceIn(EOEditingContext editingContext) {
	    GardienSalle localInstance = (GardienSalle)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static GardienSalle getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_GardienSalle.ENTITY_NAME);
		      return (GardienSalle) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public org.cocktail.superplan.client.metier.Salles salle() {
    return (org.cocktail.superplan.client.metier.Salles)storedValueForKey("salle");
  }

  public void setSalleRelationship(org.cocktail.superplan.client.metier.Salles value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Salles oldValue = salle();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "salle");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "salle");
    }
  }
  
  public NSArray repartStructures() {
    return (NSArray)storedValueForKey("repartStructures");
  }

  public NSArray repartStructures(EOQualifier qualifier) {
    return repartStructures(qualifier, null);
  }

  public NSArray repartStructures(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = repartStructures();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartStructures");
  }

  public void removeFromRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartStructures");
  }

  public org.cocktail.superplan.client.metier.RepartStructure createRepartStructuresRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartStructure");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartStructures");
    return (org.cocktail.superplan.client.metier.RepartStructure) eo;
  }

  public void deleteRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartStructures");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartStructuresRelationships() {
    Enumeration objects = repartStructures().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartStructuresRelationship((org.cocktail.superplan.client.metier.RepartStructure)objects.nextElement());
    }
  }


  public static GardienSalle createGardienSalle(EOEditingContext editingContext) {
    GardienSalle eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_GardienSalle.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _GardienSalle.ENTITY_NAME + "' !");
    } else
    {
        eo = (GardienSalle) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllGardienSalles(EOEditingContext editingContext) {
    return _GardienSalle.fetchAllGardienSalles(editingContext, null);
  }

  public static NSArray fetchAllGardienSalles(EOEditingContext editingContext, NSArray sortOrderings) {
    return _GardienSalle.fetchGardienSalles(editingContext, null, sortOrderings);
  }

  public static NSArray fetchGardienSalles(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_GardienSalle.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static GardienSalle fetchGardienSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _GardienSalle.fetchGardienSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static GardienSalle fetchGardienSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _GardienSalle.fetchGardienSalles(editingContext, qualifier, null);
    GardienSalle eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (GardienSalle)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one GardienSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static GardienSalle fetchRequiredGardienSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _GardienSalle.fetchRequiredGardienSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static GardienSalle fetchRequiredGardienSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    GardienSalle eoObject = _GardienSalle.fetchGardienSalle(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no GardienSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static GardienSalle localInstanceIn(EOEditingContext editingContext, GardienSalle eo) {
    GardienSalle localInstance = (eo == null) ? null : (GardienSalle)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
