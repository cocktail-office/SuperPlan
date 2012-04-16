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

// DO NOT EDIT.  Make changes to ScolGroupeObjetVdi.java instead.
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

public abstract class _ScolGroupeObjetVdi extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolGroupeObjetVdi";
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
	public static final String SEMESTRE_KEY = "semestre";

	// Utilities methods
	  public ScolGroupeObjetVdi localInstanceIn(EOEditingContext editingContext) {
	    ScolGroupeObjetVdi localInstance = (ScolGroupeObjetVdi)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolGroupeObjetVdi getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolGroupeObjetVdi.ENTITY_NAME);
		      return (ScolGroupeObjetVdi) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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

  public org.cocktail.superplan.server.metier.MaquetteSemestre semestre() {
    return (org.cocktail.superplan.server.metier.MaquetteSemestre)storedValueForKey("semestre");
  }

  public void setSemestreRelationship(org.cocktail.superplan.server.metier.MaquetteSemestre value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteSemestre oldValue = semestre();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "semestre");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "semestre");
    }
  }
  

  public static ScolGroupeObjetVdi createScolGroupeObjetVdi(EOEditingContext editingContext, Integer fannKey
, Integer ggrpKey
, Integer gobjKey
) {
    ScolGroupeObjetVdi eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolGroupeObjetVdi.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolGroupeObjetVdi.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolGroupeObjetVdi) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setGgrpKey(ggrpKey);
		eo.setGobjKey(gobjKey);
    return eo;
  }

  public static NSArray fetchAllScolGroupeObjetVdis(EOEditingContext editingContext) {
    return _ScolGroupeObjetVdi.fetchAllScolGroupeObjetVdis(editingContext, null);
  }

  public static NSArray fetchAllScolGroupeObjetVdis(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolGroupeObjetVdi.fetchScolGroupeObjetVdis(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolGroupeObjetVdis(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolGroupeObjetVdi.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolGroupeObjetVdi fetchScolGroupeObjetVdi(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeObjetVdi.fetchScolGroupeObjetVdi(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeObjetVdi fetchScolGroupeObjetVdi(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolGroupeObjetVdi.fetchScolGroupeObjetVdis(editingContext, qualifier, null);
    ScolGroupeObjetVdi eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolGroupeObjetVdi)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolGroupeObjetVdi that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeObjetVdi fetchRequiredScolGroupeObjetVdi(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeObjetVdi.fetchRequiredScolGroupeObjetVdi(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeObjetVdi fetchRequiredScolGroupeObjetVdi(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolGroupeObjetVdi eoObject = _ScolGroupeObjetVdi.fetchScolGroupeObjetVdi(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolGroupeObjetVdi that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeObjetVdi localInstanceIn(EOEditingContext editingContext, ScolGroupeObjetVdi eo) {
    ScolGroupeObjetVdi localInstance = (eo == null) ? null : (ScolGroupeObjetVdi)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
