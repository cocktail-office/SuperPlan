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

// DO NOT EDIT.  Make changes to ScolGroupeObjetElp.java instead.
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

public abstract class _ScolGroupeObjetElp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolGroupeObjetElp";
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
	public static final String MAQUETTE_AP_KEY = "maquetteAp";

	// Utilities methods
	  public ScolGroupeObjetElp localInstanceIn(EOEditingContext editingContext) {
	    ScolGroupeObjetElp localInstance = (ScolGroupeObjetElp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolGroupeObjetElp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolGroupeObjetElp.ENTITY_NAME);
		      return (ScolGroupeObjetElp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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

  public org.cocktail.superplan.server.metier.MaquetteAp maquetteAp() {
    return (org.cocktail.superplan.server.metier.MaquetteAp)storedValueForKey("maquetteAp");
  }

  public void setMaquetteApRelationship(org.cocktail.superplan.server.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteAp oldValue = maquetteAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteAp");
    }
  }
  

  public static ScolGroupeObjetElp createScolGroupeObjetElp(EOEditingContext editingContext, Integer fannKey
, Integer ggrpKey
, Integer gobjKey
) {
    ScolGroupeObjetElp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolGroupeObjetElp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolGroupeObjetElp.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolGroupeObjetElp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setGgrpKey(ggrpKey);
		eo.setGobjKey(gobjKey);
    return eo;
  }

  public static NSArray fetchAllScolGroupeObjetElps(EOEditingContext editingContext) {
    return _ScolGroupeObjetElp.fetchAllScolGroupeObjetElps(editingContext, null);
  }

  public static NSArray fetchAllScolGroupeObjetElps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolGroupeObjetElp.fetchScolGroupeObjetElps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolGroupeObjetElps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolGroupeObjetElp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolGroupeObjetElp fetchScolGroupeObjetElp(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeObjetElp.fetchScolGroupeObjetElp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeObjetElp fetchScolGroupeObjetElp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolGroupeObjetElp.fetchScolGroupeObjetElps(editingContext, qualifier, null);
    ScolGroupeObjetElp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolGroupeObjetElp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolGroupeObjetElp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeObjetElp fetchRequiredScolGroupeObjetElp(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeObjetElp.fetchRequiredScolGroupeObjetElp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeObjetElp fetchRequiredScolGroupeObjetElp(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolGroupeObjetElp eoObject = _ScolGroupeObjetElp.fetchScolGroupeObjetElp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolGroupeObjetElp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeObjetElp localInstanceIn(EOEditingContext editingContext, ScolGroupeObjetElp eo) {
    ScolGroupeObjetElp localInstance = (eo == null) ? null : (ScolGroupeObjetElp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
