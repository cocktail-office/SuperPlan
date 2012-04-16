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

// DO NOT EDIT.  Make changes to Local.java instead.
package org.cocktail.superplan.client.metier;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public abstract class _Local extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Local";
	public static final String ENTITY_TABLE_NAME = "GRHUM.LOCAL";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cLocal";

	public static final String APPELLATION_KEY = "appellation";
	public static final String C_LOCAL_KEY = "cLocal";

	public static final String APPELLATION_COLKEY = "APPELLATION";
	public static final String C_LOCAL_COLKEY = "C_LOCAL";

	// Relationships
	public static final String REPART_BAT_IMP_GEOS_KEY = "repartBatImpGeos";
	public static final String SALLESS_KEY = "salless";

	// Utilities methods
	  public Local localInstanceIn(EOEditingContext editingContext) {
	    Local localInstance = (Local)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Local getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Local.ENTITY_NAME);
		      return (Local) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String appellation() {
    return (String) storedValueForKey("appellation");
  }

  public void setAppellation(String value) {
    takeStoredValueForKey(value, "appellation");
  }

  public String cLocal() {
    return (String) storedValueForKey("cLocal");
  }

  public void setCLocal(String value) {
    takeStoredValueForKey(value, "cLocal");
  }

  public NSArray repartBatImpGeos() {
    return (NSArray)storedValueForKey("repartBatImpGeos");
  }

  public NSArray repartBatImpGeos(EOQualifier qualifier) {
    return repartBatImpGeos(qualifier, null, false);
  }

  public NSArray repartBatImpGeos(EOQualifier qualifier, boolean fetch) {
    return repartBatImpGeos(qualifier, null, fetch);
  }

  public NSArray repartBatImpGeos(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.RepartBatImpGeo.LOCAL_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.RepartBatImpGeo.fetchRepartBatImpGeos(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = repartBatImpGeos();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRepartBatImpGeosRelationship(org.cocktail.superplan.client.metier.RepartBatImpGeo object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartBatImpGeos");
  }

  public void removeFromRepartBatImpGeosRelationship(org.cocktail.superplan.client.metier.RepartBatImpGeo object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartBatImpGeos");
  }

  public org.cocktail.superplan.client.metier.RepartBatImpGeo createRepartBatImpGeosRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartBatImpGeo");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartBatImpGeos");
    return (org.cocktail.superplan.client.metier.RepartBatImpGeo) eo;
  }

  public void deleteRepartBatImpGeosRelationship(org.cocktail.superplan.client.metier.RepartBatImpGeo object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartBatImpGeos");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartBatImpGeosRelationships() {
    Enumeration objects = repartBatImpGeos().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartBatImpGeosRelationship((org.cocktail.superplan.client.metier.RepartBatImpGeo)objects.nextElement());
    }
  }

  public NSArray salless() {
    return (NSArray)storedValueForKey("salless");
  }

  public NSArray salless(EOQualifier qualifier) {
    return salless(qualifier, null, false);
  }

  public NSArray salless(EOQualifier qualifier, boolean fetch) {
    return salless(qualifier, null, fetch);
  }

  public NSArray salless(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.Salles.LOCAL_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.Salles.fetchSalleses(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = salless();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToSallessRelationship(org.cocktail.superplan.client.metier.Salles object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "salless");
  }

  public void removeFromSallessRelationship(org.cocktail.superplan.client.metier.Salles object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "salless");
  }

  public org.cocktail.superplan.client.metier.Salles createSallessRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Salles");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "salless");
    return (org.cocktail.superplan.client.metier.Salles) eo;
  }

  public void deleteSallessRelationship(org.cocktail.superplan.client.metier.Salles object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "salless");
    editingContext().deleteObject(object);
  }

  public void deleteAllSallessRelationships() {
    Enumeration objects = salless().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSallessRelationship((org.cocktail.superplan.client.metier.Salles)objects.nextElement());
    }
  }


  public static Local createLocal(EOEditingContext editingContext, String cLocal
) {
    Local eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Local.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Local.ENTITY_NAME + "' !");
    } else
    {
        eo = (Local) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCLocal(cLocal);
    return eo;
  }

  public static NSArray fetchAllLocals(EOEditingContext editingContext) {
    return _Local.fetchAllLocals(editingContext, null);
  }

  public static NSArray fetchAllLocals(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Local.fetchLocals(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLocals(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Local.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Local fetchLocal(EOEditingContext editingContext, String keyName, Object value) {
    return _Local.fetchLocal(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Local fetchLocal(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Local.fetchLocals(editingContext, qualifier, null);
    Local eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Local)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Local that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Local fetchRequiredLocal(EOEditingContext editingContext, String keyName, Object value) {
    return _Local.fetchRequiredLocal(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Local fetchRequiredLocal(EOEditingContext editingContext, EOQualifier qualifier) {
    Local eoObject = _Local.fetchLocal(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Local that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Local localInstanceIn(EOEditingContext editingContext, Local eo) {
    Local localInstance = (eo == null) ? null : (Local)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
