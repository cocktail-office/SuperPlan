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

// DO NOT EDIT.  Make changes to VGroupePersonne.java instead.
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

public abstract class _VGroupePersonne extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VGroupePersonne";
	public static final String ENTITY_TABLE_NAME = "GRHUM.V_GROUPE_PERSONNE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cStructure";

	public static final String C_STRUCTURE_KEY = "cStructure";
	public static final String C_STRUCTURE_PERE_KEY = "cStructurePere";
	public static final String LL_STRUCTURE_KEY = "llStructure";
	public static final String PERS_ID_KEY = "persId";

	public static final String C_STRUCTURE_COLKEY = "C_STRUCTURE";
	public static final String C_STRUCTURE_PERE_COLKEY = "C_STRUCTURE_PERE";
	public static final String LL_STRUCTURE_COLKEY = "LL_STRUCTURE";
	public static final String PERS_ID_COLKEY = "PERS_ID";

	// Relationships
	public static final String TO_PERSONNE_KEY = "toPersonne";
	public static final String TO_STRUCTURE_PERE_KEY = "toStructurePere";
	public static final String TO_STRUCTURES_FILLES_KEY = "toStructuresFilles";

	// Utilities methods
	  public VGroupePersonne localInstanceIn(EOEditingContext editingContext) {
	    VGroupePersonne localInstance = (VGroupePersonne)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VGroupePersonne getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VGroupePersonne.ENTITY_NAME);
		      return (VGroupePersonne) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cStructure() {
    return (String) storedValueForKey("cStructure");
  }

  public void setCStructure(String value) {
    takeStoredValueForKey(value, "cStructure");
  }

  public String cStructurePere() {
    return (String) storedValueForKey("cStructurePere");
  }

  public void setCStructurePere(String value) {
    takeStoredValueForKey(value, "cStructurePere");
  }

  public String llStructure() {
    return (String) storedValueForKey("llStructure");
  }

  public void setLlStructure(String value) {
    takeStoredValueForKey(value, "llStructure");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public org.cocktail.superplan.server.metier.Personne toPersonne() {
    return (org.cocktail.superplan.server.metier.Personne)storedValueForKey("toPersonne");
  }

  public void setToPersonneRelationship(org.cocktail.superplan.server.metier.Personne value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.Personne oldValue = toPersonne();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toPersonne");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toPersonne");
    }
  }
  
  public org.cocktail.superplan.server.metier.VGroupePersonne toStructurePere() {
    return (org.cocktail.superplan.server.metier.VGroupePersonne)storedValueForKey("toStructurePere");
  }

  public void setToStructurePereRelationship(org.cocktail.superplan.server.metier.VGroupePersonne value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.VGroupePersonne oldValue = toStructurePere();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toStructurePere");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toStructurePere");
    }
  }
  
  public NSArray toStructuresFilles() {
    return (NSArray)storedValueForKey("toStructuresFilles");
  }

  public NSArray toStructuresFilles(EOQualifier qualifier) {
    return toStructuresFilles(qualifier, null, false);
  }

  public NSArray toStructuresFilles(EOQualifier qualifier, boolean fetch) {
    return toStructuresFilles(qualifier, null, fetch);
  }

  public NSArray toStructuresFilles(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.VGroupePersonne.TO_STRUCTURE_PERE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.VGroupePersonne.fetchVGroupePersonnes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toStructuresFilles();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToStructuresFillesRelationship(org.cocktail.superplan.server.metier.VGroupePersonne object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "toStructuresFilles");
  }

  public void removeFromToStructuresFillesRelationship(org.cocktail.superplan.server.metier.VGroupePersonne object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toStructuresFilles");
  }

  public org.cocktail.superplan.server.metier.VGroupePersonne createToStructuresFillesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("VGroupePersonne");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toStructuresFilles");
    return (org.cocktail.superplan.server.metier.VGroupePersonne) eo;
  }

  public void deleteToStructuresFillesRelationship(org.cocktail.superplan.server.metier.VGroupePersonne object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toStructuresFilles");
    editingContext().deleteObject(object);
  }

  public void deleteAllToStructuresFillesRelationships() {
    Enumeration objects = toStructuresFilles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToStructuresFillesRelationship((org.cocktail.superplan.server.metier.VGroupePersonne)objects.nextElement());
    }
  }


  public static VGroupePersonne createVGroupePersonne(EOEditingContext editingContext, String cStructure
, Integer persId
) {
    VGroupePersonne eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VGroupePersonne.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VGroupePersonne.ENTITY_NAME + "' !");
    } else
    {
        eo = (VGroupePersonne) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCStructure(cStructure);
		eo.setPersId(persId);
    return eo;
  }

  public static NSArray fetchAllVGroupePersonnes(EOEditingContext editingContext) {
    return _VGroupePersonne.fetchAllVGroupePersonnes(editingContext, null);
  }

  public static NSArray fetchAllVGroupePersonnes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VGroupePersonne.fetchVGroupePersonnes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVGroupePersonnes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VGroupePersonne.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VGroupePersonne fetchVGroupePersonne(EOEditingContext editingContext, String keyName, Object value) {
    return _VGroupePersonne.fetchVGroupePersonne(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VGroupePersonne fetchVGroupePersonne(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VGroupePersonne.fetchVGroupePersonnes(editingContext, qualifier, null);
    VGroupePersonne eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VGroupePersonne)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VGroupePersonne that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VGroupePersonne fetchRequiredVGroupePersonne(EOEditingContext editingContext, String keyName, Object value) {
    return _VGroupePersonne.fetchRequiredVGroupePersonne(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VGroupePersonne fetchRequiredVGroupePersonne(EOEditingContext editingContext, EOQualifier qualifier) {
    VGroupePersonne eoObject = _VGroupePersonne.fetchVGroupePersonne(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VGroupePersonne that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VGroupePersonne localInstanceIn(EOEditingContext editingContext, VGroupePersonne eo) {
    VGroupePersonne localInstance = (eo == null) ? null : (VGroupePersonne)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
