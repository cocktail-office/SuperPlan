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

// DO NOT EDIT.  Make changes to VTreeObjets.java instead.
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

public abstract class _VTreeObjets extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VTreeObjets";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_TREE_OBJETS";

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
	public static final String TO_OBJET_FILS_KEY = "toObjetFils";
	public static final String TO_OBJET_PERE_KEY = "toObjetPere";
	public static final String TO_PERSONNE_KEY = "toPersonne";

	// Utilities methods
	  public VTreeObjets localInstanceIn(EOEditingContext editingContext) {
	    VTreeObjets localInstance = (VTreeObjets)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VTreeObjets getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VTreeObjets.ENTITY_NAME);
		      return (VTreeObjets) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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

  public org.cocktail.superplan.server.metier.VTreeObjets toObjetPere() {
    return (org.cocktail.superplan.server.metier.VTreeObjets)storedValueForKey("toObjetPere");
  }

  public void setToObjetPereRelationship(org.cocktail.superplan.server.metier.VTreeObjets value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.VTreeObjets oldValue = toObjetPere();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toObjetPere");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toObjetPere");
    }
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
  
  public NSArray toObjetFils() {
    return (NSArray)storedValueForKey("toObjetFils");
  }

  public NSArray toObjetFils(EOQualifier qualifier) {
    return toObjetFils(qualifier, null, false);
  }

  public NSArray toObjetFils(EOQualifier qualifier, boolean fetch) {
    return toObjetFils(qualifier, null, fetch);
  }

  public NSArray toObjetFils(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.VTreeObjets.TO_OBJET_PERE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.VTreeObjets.fetchVTreeObjetses(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toObjetFils();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToObjetFilsRelationship(org.cocktail.superplan.server.metier.VTreeObjets object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "toObjetFils");
  }

  public void removeFromToObjetFilsRelationship(org.cocktail.superplan.server.metier.VTreeObjets object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toObjetFils");
  }

  public org.cocktail.superplan.server.metier.VTreeObjets createToObjetFilsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("VTreeObjets");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toObjetFils");
    return (org.cocktail.superplan.server.metier.VTreeObjets) eo;
  }

  public void deleteToObjetFilsRelationship(org.cocktail.superplan.server.metier.VTreeObjets object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toObjetFils");
    editingContext().deleteObject(object);
  }

  public void deleteAllToObjetFilsRelationships() {
    Enumeration objects = toObjetFils().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToObjetFilsRelationship((org.cocktail.superplan.server.metier.VTreeObjets)objects.nextElement());
    }
  }


  public static VTreeObjets createVTreeObjets(EOEditingContext editingContext, String cStructure
, Integer persId
) {
    VTreeObjets eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VTreeObjets.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VTreeObjets.ENTITY_NAME + "' !");
    } else
    {
        eo = (VTreeObjets) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCStructure(cStructure);
		eo.setPersId(persId);
    return eo;
  }

  public static NSArray fetchAllVTreeObjetses(EOEditingContext editingContext) {
    return _VTreeObjets.fetchAllVTreeObjetses(editingContext, null);
  }

  public static NSArray fetchAllVTreeObjetses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VTreeObjets.fetchVTreeObjetses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVTreeObjetses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VTreeObjets.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VTreeObjets fetchVTreeObjets(EOEditingContext editingContext, String keyName, Object value) {
    return _VTreeObjets.fetchVTreeObjets(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VTreeObjets fetchVTreeObjets(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VTreeObjets.fetchVTreeObjetses(editingContext, qualifier, null);
    VTreeObjets eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VTreeObjets)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VTreeObjets that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VTreeObjets fetchRequiredVTreeObjets(EOEditingContext editingContext, String keyName, Object value) {
    return _VTreeObjets.fetchRequiredVTreeObjets(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VTreeObjets fetchRequiredVTreeObjets(EOEditingContext editingContext, EOQualifier qualifier) {
    VTreeObjets eoObject = _VTreeObjets.fetchVTreeObjets(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VTreeObjets that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VTreeObjets localInstanceIn(EOEditingContext editingContext, VTreeObjets eo) {
    VTreeObjets localInstance = (eo == null) ? null : (VTreeObjets)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
