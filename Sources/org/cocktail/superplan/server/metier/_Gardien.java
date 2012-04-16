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

// DO NOT EDIT.  Make changes to Gardien.java instead.
package org.cocktail.superplan.server.metier;

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

public abstract class _Gardien extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Gardien";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.GARDIEN";

	// Attributes

	public static final String C_LOCAL_KEY = "cLocal";

	public static final String C_LOCAL_COLKEY = "C_LOCAL";

	// Relationships
	public static final String LOCAL_KEY = "local";
	public static final String REPART_STRUCTURES_KEY = "repartStructures";

	// Utilities methods
	  public Gardien localInstanceIn(EOEditingContext editingContext) {
	    Gardien localInstance = (Gardien)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Gardien getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Gardien.ENTITY_NAME);
		      return (Gardien) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cLocal() {
    return (String) storedValueForKey("cLocal");
  }

  public void setCLocal(String value) {
    takeStoredValueForKey(value, "cLocal");
  }

  public org.cocktail.superplan.server.metier.Local local() {
    return (org.cocktail.superplan.server.metier.Local)storedValueForKey("local");
  }

  public void setLocalRelationship(org.cocktail.superplan.server.metier.Local value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.Local oldValue = local();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "local");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "local");
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
  
  public void addToRepartStructuresRelationship(org.cocktail.superplan.server.metier.RepartStructure object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartStructures");
  }

  public void removeFromRepartStructuresRelationship(org.cocktail.superplan.server.metier.RepartStructure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartStructures");
  }

  public org.cocktail.superplan.server.metier.RepartStructure createRepartStructuresRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartStructure");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartStructures");
    return (org.cocktail.superplan.server.metier.RepartStructure) eo;
  }

  public void deleteRepartStructuresRelationship(org.cocktail.superplan.server.metier.RepartStructure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartStructures");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartStructuresRelationships() {
    Enumeration objects = repartStructures().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartStructuresRelationship((org.cocktail.superplan.server.metier.RepartStructure)objects.nextElement());
    }
  }


  public static Gardien createGardien(EOEditingContext editingContext, String cLocal
) {
    Gardien eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Gardien.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Gardien.ENTITY_NAME + "' !");
    } else
    {
        eo = (Gardien) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCLocal(cLocal);
    return eo;
  }

  public static NSArray fetchAllGardiens(EOEditingContext editingContext) {
    return _Gardien.fetchAllGardiens(editingContext, null);
  }

  public static NSArray fetchAllGardiens(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Gardien.fetchGardiens(editingContext, null, sortOrderings);
  }

  public static NSArray fetchGardiens(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Gardien.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Gardien fetchGardien(EOEditingContext editingContext, String keyName, Object value) {
    return _Gardien.fetchGardien(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Gardien fetchGardien(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Gardien.fetchGardiens(editingContext, qualifier, null);
    Gardien eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Gardien)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Gardien that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Gardien fetchRequiredGardien(EOEditingContext editingContext, String keyName, Object value) {
    return _Gardien.fetchRequiredGardien(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Gardien fetchRequiredGardien(EOEditingContext editingContext, EOQualifier qualifier) {
    Gardien eoObject = _Gardien.fetchGardien(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Gardien that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Gardien localInstanceIn(EOEditingContext editingContext, Gardien eo) {
    Gardien localInstance = (eo == null) ? null : (Gardien)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
