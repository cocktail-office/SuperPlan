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

// DO NOT EDIT.  Make changes to ScolGroupeObjet.java instead.
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

public abstract class _ScolGroupeObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolGroupeObjet";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_GROUPE_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "gobjKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String GGRP_KEY_KEY = "ggrpKey";
	public static final String GOBJ_KEY_KEY = "gobjKey";
	public static final String GOBJ_TYPE_KEY = "gobjType";
	public static final String MAP_KEY_KEY = "mapKey";
	public static final String MSEM_KEY_KEY = "msemKey";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String GGRP_KEY_COLKEY = "GGRP_KEY";
	public static final String GOBJ_KEY_COLKEY = "GOBJ_KEY";
	public static final String GOBJ_TYPE_COLKEY = "GOBJ_TYPE";
	public static final String MAP_KEY_COLKEY = "MAP_KEY";
	public static final String MSEM_KEY_COLKEY = "MSEM_KEY";

	// Relationships
	public static final String INCLUS_FILS_KEY = "inclusFils";
	public static final String INCLUS_PERE_KEY = "inclusPere";
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";
	public static final String SEMESTRE_KEY = "semestre";

	// Utilities methods
	  public ScolGroupeObjet localInstanceIn(EOEditingContext editingContext) {
	    ScolGroupeObjet localInstance = (ScolGroupeObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolGroupeObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolGroupeObjet.ENTITY_NAME);
		      return (ScolGroupeObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer ggrpKey() {
    return (Integer) storedValueForKey("ggrpKey");
  }

  public void setGgrpKey(Integer value) {
    takeStoredValueForKey(value, "ggrpKey");
  }

  public Integer gobjKey() {
    return (Integer) storedValueForKey("gobjKey");
  }

  public void setGobjKey(Integer value) {
    takeStoredValueForKey(value, "gobjKey");
  }

  public String gobjType() {
    return (String) storedValueForKey("gobjType");
  }

  public void setGobjType(String value) {
    takeStoredValueForKey(value, "gobjType");
  }

  public Integer mapKey() {
    return (Integer) storedValueForKey("mapKey");
  }

  public void setMapKey(Integer value) {
    takeStoredValueForKey(value, "mapKey");
  }

  public Integer msemKey() {
    return (Integer) storedValueForKey("msemKey");
  }

  public void setMsemKey(Integer value) {
    takeStoredValueForKey(value, "msemKey");
  }

  public org.cocktail.superplan.client.metier.MaquetteAp maquetteAp() {
    return (org.cocktail.superplan.client.metier.MaquetteAp)storedValueForKey("maquetteAp");
  }

  public void setMaquetteApRelationship(org.cocktail.superplan.client.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteAp oldValue = maquetteAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteAp");
    }
  }
  
  public org.cocktail.superplan.client.metier.ScolGroupeGrp scolGroupeGrp() {
    return (org.cocktail.superplan.client.metier.ScolGroupeGrp)storedValueForKey("scolGroupeGrp");
  }

  public void setScolGroupeGrpRelationship(org.cocktail.superplan.client.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ScolGroupeGrp oldValue = scolGroupeGrp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolGroupeGrp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolGroupeGrp");
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
  
  public NSArray inclusFils() {
    return (NSArray)storedValueForKey("inclusFils");
  }

  public NSArray inclusFils(EOQualifier qualifier) {
    return inclusFils(qualifier, null);
  }

  public NSArray inclusFils(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = inclusFils();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToInclusFilsRelationship(org.cocktail.superplan.client.metier.ScolGroupeInclusion object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "inclusFils");
  }

  public void removeFromInclusFilsRelationship(org.cocktail.superplan.client.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "inclusFils");
  }

  public org.cocktail.superplan.client.metier.ScolGroupeInclusion createInclusFilsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeInclusion");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "inclusFils");
    return (org.cocktail.superplan.client.metier.ScolGroupeInclusion) eo;
  }

  public void deleteInclusFilsRelationship(org.cocktail.superplan.client.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "inclusFils");
    editingContext().deleteObject(object);
  }

  public void deleteAllInclusFilsRelationships() {
    Enumeration objects = inclusFils().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteInclusFilsRelationship((org.cocktail.superplan.client.metier.ScolGroupeInclusion)objects.nextElement());
    }
  }

  public NSArray inclusPere() {
    return (NSArray)storedValueForKey("inclusPere");
  }

  public NSArray inclusPere(EOQualifier qualifier) {
    return inclusPere(qualifier, null);
  }

  public NSArray inclusPere(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = inclusPere();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToInclusPereRelationship(org.cocktail.superplan.client.metier.ScolGroupeInclusion object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "inclusPere");
  }

  public void removeFromInclusPereRelationship(org.cocktail.superplan.client.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "inclusPere");
  }

  public org.cocktail.superplan.client.metier.ScolGroupeInclusion createInclusPereRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeInclusion");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "inclusPere");
    return (org.cocktail.superplan.client.metier.ScolGroupeInclusion) eo;
  }

  public void deleteInclusPereRelationship(org.cocktail.superplan.client.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "inclusPere");
    editingContext().deleteObject(object);
  }

  public void deleteAllInclusPereRelationships() {
    Enumeration objects = inclusPere().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteInclusPereRelationship((org.cocktail.superplan.client.metier.ScolGroupeInclusion)objects.nextElement());
    }
  }


  public static ScolGroupeObjet createScolGroupeObjet(EOEditingContext editingContext, Integer fannKey
, Integer ggrpKey
, Integer gobjKey
, String gobjType
) {
    ScolGroupeObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolGroupeObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolGroupeObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolGroupeObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setGgrpKey(ggrpKey);
		eo.setGobjKey(gobjKey);
		eo.setGobjType(gobjType);
    return eo;
  }

  public static NSArray fetchAllScolGroupeObjets(EOEditingContext editingContext) {
    return _ScolGroupeObjet.fetchAllScolGroupeObjets(editingContext, null);
  }

  public static NSArray fetchAllScolGroupeObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolGroupeObjet.fetchScolGroupeObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolGroupeObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolGroupeObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolGroupeObjet fetchScolGroupeObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeObjet.fetchScolGroupeObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeObjet fetchScolGroupeObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolGroupeObjet.fetchScolGroupeObjets(editingContext, qualifier, null);
    ScolGroupeObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolGroupeObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolGroupeObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeObjet fetchRequiredScolGroupeObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeObjet.fetchRequiredScolGroupeObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeObjet fetchRequiredScolGroupeObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolGroupeObjet eoObject = _ScolGroupeObjet.fetchScolGroupeObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolGroupeObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeObjet localInstanceIn(EOEditingContext editingContext, ScolGroupeObjet eo) {
    ScolGroupeObjet localInstance = (eo == null) ? null : (ScolGroupeObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
