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

// DO NOT EDIT.  Make changes to ScolGroupeInclusion.java instead.
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

public abstract class _ScolGroupeInclusion extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolGroupeInclusion";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_GROUPE_INCLUSION";

	// Attributes

	public static final String GGRP_KEY1_KEY = "ggrpKey1";
	public static final String GGRP_KEY2_KEY = "ggrpKey2";

	public static final String GGRP_KEY1_COLKEY = "GGRP_KEY_1";
	public static final String GGRP_KEY2_COLKEY = "GGRP_KEY_2";

	// Relationships
	public static final String GROUPE_FILS_KEY = "groupeFils";
	public static final String GROUPE_PERE_KEY = "groupePere";
	public static final String SCOL_GROUPE_INCLUSIONS_KEY = "scolGroupeInclusions";

	// Utilities methods
	  public ScolGroupeInclusion localInstanceIn(EOEditingContext editingContext) {
	    ScolGroupeInclusion localInstance = (ScolGroupeInclusion)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolGroupeInclusion getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolGroupeInclusion.ENTITY_NAME);
		      return (ScolGroupeInclusion) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer ggrpKey1() {
    return (Integer) storedValueForKey("ggrpKey1");
  }

  public void setGgrpKey1(Integer value) {
    takeStoredValueForKey(value, "ggrpKey1");
  }

  public Integer ggrpKey2() {
    return (Integer) storedValueForKey("ggrpKey2");
  }

  public void setGgrpKey2(Integer value) {
    takeStoredValueForKey(value, "ggrpKey2");
  }

  public org.cocktail.superplan.server.metier.ScolGroupeGrp groupeFils() {
    return (org.cocktail.superplan.server.metier.ScolGroupeGrp)storedValueForKey("groupeFils");
  }

  public void setGroupeFilsRelationship(org.cocktail.superplan.server.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ScolGroupeGrp oldValue = groupeFils();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "groupeFils");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "groupeFils");
    }
  }
  
  public org.cocktail.superplan.server.metier.ScolGroupeGrp groupePere() {
    return (org.cocktail.superplan.server.metier.ScolGroupeGrp)storedValueForKey("groupePere");
  }

  public void setGroupePereRelationship(org.cocktail.superplan.server.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ScolGroupeGrp oldValue = groupePere();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "groupePere");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "groupePere");
    }
  }
  
  public NSArray scolGroupeInclusions() {
    return (NSArray)storedValueForKey("scolGroupeInclusions");
  }

  public NSArray scolGroupeInclusions(EOQualifier qualifier) {
    return scolGroupeInclusions(qualifier, null);
  }

  public NSArray scolGroupeInclusions(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = scolGroupeInclusions();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolGroupeInclusionsRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolGroupeInclusions");
  }

  public void removeFromScolGroupeInclusionsRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeInclusions");
  }

  public org.cocktail.superplan.server.metier.ScolGroupeInclusion createScolGroupeInclusionsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeInclusion");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolGroupeInclusions");
    return (org.cocktail.superplan.server.metier.ScolGroupeInclusion) eo;
  }

  public void deleteScolGroupeInclusionsRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeInclusions");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolGroupeInclusionsRelationships() {
    Enumeration objects = scolGroupeInclusions().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolGroupeInclusionsRelationship((org.cocktail.superplan.server.metier.ScolGroupeInclusion)objects.nextElement());
    }
  }


  public static ScolGroupeInclusion createScolGroupeInclusion(EOEditingContext editingContext, Integer ggrpKey1
, Integer ggrpKey2
) {
    ScolGroupeInclusion eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolGroupeInclusion.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolGroupeInclusion.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolGroupeInclusion) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setGgrpKey1(ggrpKey1);
		eo.setGgrpKey2(ggrpKey2);
    return eo;
  }

  public static NSArray fetchAllScolGroupeInclusions(EOEditingContext editingContext) {
    return _ScolGroupeInclusion.fetchAllScolGroupeInclusions(editingContext, null);
  }

  public static NSArray fetchAllScolGroupeInclusions(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolGroupeInclusion.fetchScolGroupeInclusions(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolGroupeInclusions(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolGroupeInclusion.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolGroupeInclusion fetchScolGroupeInclusion(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeInclusion.fetchScolGroupeInclusion(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeInclusion fetchScolGroupeInclusion(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolGroupeInclusion.fetchScolGroupeInclusions(editingContext, qualifier, null);
    ScolGroupeInclusion eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolGroupeInclusion)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolGroupeInclusion that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeInclusion fetchRequiredScolGroupeInclusion(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeInclusion.fetchRequiredScolGroupeInclusion(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeInclusion fetchRequiredScolGroupeInclusion(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolGroupeInclusion eoObject = _ScolGroupeInclusion.fetchScolGroupeInclusion(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolGroupeInclusion that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeInclusion localInstanceIn(EOEditingContext editingContext, ScolGroupeInclusion eo) {
    ScolGroupeInclusion localInstance = (eo == null) ? null : (ScolGroupeInclusion)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
