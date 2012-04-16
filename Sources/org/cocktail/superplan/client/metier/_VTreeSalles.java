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

// DO NOT EDIT.  Make changes to VTreeSalles.java instead.
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

public abstract class _VTreeSalles extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VTreeSalles";
	public static final String ENTITY_TABLE_NAME = "GRHUM.V_TREE_SALLES";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cStructure";

	public static final String C_STRUCTURE_KEY = "cStructure";
	public static final String C_STRUCTURE_PERE_KEY = "cStructurePere";
	public static final String LL_STRUCTURE_KEY = "llStructure";
	public static final String NIVEAU_KEY = "niveau";
	public static final String PERS_ID_KEY = "persId";

	public static final String C_STRUCTURE_COLKEY = "C_STRUCTURE";
	public static final String C_STRUCTURE_PERE_COLKEY = "C_STRUCTURE_PERE";
	public static final String LL_STRUCTURE_COLKEY = "LL_STRUCTURE";
	public static final String NIVEAU_COLKEY = "NIVEAU";
	public static final String PERS_ID_COLKEY = "PERS_ID";

	// Relationships
	public static final String TO_PERSONNE_KEY = "toPersonne";
	public static final String TO_SALLE_FILS_KEY = "toSalleFils";
	public static final String TO_SALLE_PERE_KEY = "toSallePere";

	// Utilities methods
	  public VTreeSalles localInstanceIn(EOEditingContext editingContext) {
	    VTreeSalles localInstance = (VTreeSalles)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VTreeSalles getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VTreeSalles.ENTITY_NAME);
		      return (VTreeSalles) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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

  public Integer niveau() {
    return (Integer) storedValueForKey("niveau");
  }

  public void setNiveau(Integer value) {
    takeStoredValueForKey(value, "niveau");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public org.cocktail.superplan.client.metier.Personne toPersonne() {
    return (org.cocktail.superplan.client.metier.Personne)storedValueForKey("toPersonne");
  }

  public void setToPersonneRelationship(org.cocktail.superplan.client.metier.Personne value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Personne oldValue = toPersonne();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toPersonne");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toPersonne");
    }
  }
  
  public org.cocktail.superplan.client.metier.VTreeSalles toSallePere() {
    return (org.cocktail.superplan.client.metier.VTreeSalles)storedValueForKey("toSallePere");
  }

  public void setToSallePereRelationship(org.cocktail.superplan.client.metier.VTreeSalles value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.VTreeSalles oldValue = toSallePere();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toSallePere");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toSallePere");
    }
  }
  
  public NSArray toSalleFils() {
    return (NSArray)storedValueForKey("toSalleFils");
  }

  public NSArray toSalleFils(EOQualifier qualifier) {
    return toSalleFils(qualifier, null, false);
  }

  public NSArray toSalleFils(EOQualifier qualifier, boolean fetch) {
    return toSalleFils(qualifier, null, fetch);
  }

  public NSArray toSalleFils(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.VTreeSalles.TO_SALLE_PERE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.VTreeSalles.fetchVTreeSalleses(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toSalleFils();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToSalleFilsRelationship(org.cocktail.superplan.client.metier.VTreeSalles object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "toSalleFils");
  }

  public void removeFromToSalleFilsRelationship(org.cocktail.superplan.client.metier.VTreeSalles object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toSalleFils");
  }

  public org.cocktail.superplan.client.metier.VTreeSalles createToSalleFilsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("VTreeSalles");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toSalleFils");
    return (org.cocktail.superplan.client.metier.VTreeSalles) eo;
  }

  public void deleteToSalleFilsRelationship(org.cocktail.superplan.client.metier.VTreeSalles object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toSalleFils");
    editingContext().deleteObject(object);
  }

  public void deleteAllToSalleFilsRelationships() {
    Enumeration objects = toSalleFils().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToSalleFilsRelationship((org.cocktail.superplan.client.metier.VTreeSalles)objects.nextElement());
    }
  }


  public static VTreeSalles createVTreeSalles(EOEditingContext editingContext, String cStructure
, Integer niveau
, Integer persId
) {
    VTreeSalles eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VTreeSalles.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VTreeSalles.ENTITY_NAME + "' !");
    } else
    {
        eo = (VTreeSalles) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCStructure(cStructure);
		eo.setNiveau(niveau);
		eo.setPersId(persId);
    return eo;
  }

  public static NSArray fetchAllVTreeSalleses(EOEditingContext editingContext) {
    return _VTreeSalles.fetchAllVTreeSalleses(editingContext, null);
  }

  public static NSArray fetchAllVTreeSalleses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VTreeSalles.fetchVTreeSalleses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVTreeSalleses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VTreeSalles.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VTreeSalles fetchVTreeSalles(EOEditingContext editingContext, String keyName, Object value) {
    return _VTreeSalles.fetchVTreeSalles(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VTreeSalles fetchVTreeSalles(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VTreeSalles.fetchVTreeSalleses(editingContext, qualifier, null);
    VTreeSalles eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VTreeSalles)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VTreeSalles that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VTreeSalles fetchRequiredVTreeSalles(EOEditingContext editingContext, String keyName, Object value) {
    return _VTreeSalles.fetchRequiredVTreeSalles(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VTreeSalles fetchRequiredVTreeSalles(EOEditingContext editingContext, EOQualifier qualifier) {
    VTreeSalles eoObject = _VTreeSalles.fetchVTreeSalles(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VTreeSalles that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VTreeSalles localInstanceIn(EOEditingContext editingContext, VTreeSalles eo) {
    VTreeSalles localInstance = (eo == null) ? null : (VTreeSalles)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
