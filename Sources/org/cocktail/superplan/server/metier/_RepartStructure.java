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

// DO NOT EDIT.  Make changes to RepartStructure.java instead.
package org.cocktail.superplan.server.metier;

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

public abstract class _RepartStructure extends  EOGenericRecord {
	public static final String ENTITY_NAME = "RepartStructure";
	public static final String ENTITY_TABLE_NAME = "GRHUM.REPART_STRUCTURE";

	// Attributes

	public static final String C_STRUCTURE_KEY = "cStructure";
	public static final String PERS_ID_KEY = "persId";

	public static final String C_STRUCTURE_COLKEY = "C_STRUCTURE";
	public static final String PERS_ID_COLKEY = "PERS_ID";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String STRUCTURE_ULR_KEY = "structureUlr";

	// Utilities methods
	  public RepartStructure localInstanceIn(EOEditingContext editingContext) {
	    RepartStructure localInstance = (RepartStructure)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static RepartStructure getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_RepartStructure.ENTITY_NAME);
		      return (RepartStructure) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cStructure() {
    return (String) storedValueForKey("cStructure");
  }

  public void setCStructure(String value) {
    takeStoredValueForKey(value, "cStructure");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public org.cocktail.superplan.server.metier.StructureUlr structureUlr() {
    return (org.cocktail.superplan.server.metier.StructureUlr)storedValueForKey("structureUlr");
  }

  public void setStructureUlrRelationship(org.cocktail.superplan.server.metier.StructureUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.StructureUlr oldValue = structureUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "structureUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "structureUlr");
    }
  }
  
  public NSArray individuUlr() {
    return (NSArray)storedValueForKey("individuUlr");
  }

  public NSArray individuUlr(EOQualifier qualifier) {
    return individuUlr(qualifier, null, false);
  }

  public NSArray individuUlr(EOQualifier qualifier, boolean fetch) {
    return individuUlr(qualifier, null, fetch);
  }

  public NSArray individuUlr(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.IndividuUlr.REPART_STRUCTURES_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.IndividuUlr.fetchIndividuUlrs(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = individuUlr();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToIndividuUlrRelationship(org.cocktail.superplan.server.metier.IndividuUlr object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "individuUlr");
  }

  public void removeFromIndividuUlrRelationship(org.cocktail.superplan.server.metier.IndividuUlr object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "individuUlr");
  }

  public org.cocktail.superplan.server.metier.IndividuUlr createIndividuUlrRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("IndividuUlr");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "individuUlr");
    return (org.cocktail.superplan.server.metier.IndividuUlr) eo;
  }

  public void deleteIndividuUlrRelationship(org.cocktail.superplan.server.metier.IndividuUlr object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "individuUlr");
    editingContext().deleteObject(object);
  }

  public void deleteAllIndividuUlrRelationships() {
    Enumeration objects = individuUlr().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteIndividuUlrRelationship((org.cocktail.superplan.server.metier.IndividuUlr)objects.nextElement());
    }
  }


  public static RepartStructure createRepartStructure(EOEditingContext editingContext, String cStructure
, Integer persId
) {
    RepartStructure eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_RepartStructure.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _RepartStructure.ENTITY_NAME + "' !");
    } else
    {
        eo = (RepartStructure) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCStructure(cStructure);
		eo.setPersId(persId);
    return eo;
  }

  public static NSArray fetchAllRepartStructures(EOEditingContext editingContext) {
    return _RepartStructure.fetchAllRepartStructures(editingContext, null);
  }

  public static NSArray fetchAllRepartStructures(EOEditingContext editingContext, NSArray sortOrderings) {
    return _RepartStructure.fetchRepartStructures(editingContext, null, sortOrderings);
  }

  public static NSArray fetchRepartStructures(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_RepartStructure.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static RepartStructure fetchRepartStructure(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartStructure.fetchRepartStructure(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartStructure fetchRepartStructure(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _RepartStructure.fetchRepartStructures(editingContext, qualifier, null);
    RepartStructure eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (RepartStructure)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one RepartStructure that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartStructure fetchRequiredRepartStructure(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartStructure.fetchRequiredRepartStructure(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartStructure fetchRequiredRepartStructure(EOEditingContext editingContext, EOQualifier qualifier) {
    RepartStructure eoObject = _RepartStructure.fetchRepartStructure(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no RepartStructure that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartStructure localInstanceIn(EOEditingContext editingContext, RepartStructure eo) {
    RepartStructure localInstance = (eo == null) ? null : (RepartStructure)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
