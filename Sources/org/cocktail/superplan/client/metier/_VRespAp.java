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

// DO NOT EDIT.  Make changes to VRespAp.java instead.
package org.cocktail.superplan.client.metier;

import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

public abstract class _VRespAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VRespAp";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_RESP_AP";

	// Attributes

	public static final String FANN_KEY_KEY = "fannKey";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";

	// Relationships
	public static final String AP_KEY = "ap";
	public static final String INDIVIDU_KEY = "individu";

	// Utilities methods
	  public VRespAp localInstanceIn(EOEditingContext editingContext) {
	    VRespAp localInstance = (VRespAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VRespAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VRespAp.ENTITY_NAME);
		      return (VRespAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public org.cocktail.superplan.client.metier.MaquetteAp ap() {
    return (org.cocktail.superplan.client.metier.MaquetteAp)storedValueForKey("ap");
  }

  public void setApRelationship(org.cocktail.superplan.client.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteAp oldValue = ap();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "ap");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "ap");
    }
  }
  
  public org.cocktail.superplan.client.metier.IndividuUlr individu() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individu");
  }

  public void setIndividuRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individu");
    }
  }
  

  public static VRespAp createVRespAp(EOEditingContext editingContext, Integer fannKey
) {
    VRespAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VRespAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VRespAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (VRespAp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
    return eo;
  }

  public static NSArray fetchAllVRespAps(EOEditingContext editingContext) {
    return _VRespAp.fetchAllVRespAps(editingContext, null);
  }

  public static NSArray fetchAllVRespAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VRespAp.fetchVRespAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVRespAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VRespAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VRespAp fetchVRespAp(EOEditingContext editingContext, String keyName, Object value) {
    return _VRespAp.fetchVRespAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VRespAp fetchVRespAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VRespAp.fetchVRespAps(editingContext, qualifier, null);
    VRespAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VRespAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VRespAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VRespAp fetchRequiredVRespAp(EOEditingContext editingContext, String keyName, Object value) {
    return _VRespAp.fetchRequiredVRespAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VRespAp fetchRequiredVRespAp(EOEditingContext editingContext, EOQualifier qualifier) {
    VRespAp eoObject = _VRespAp.fetchVRespAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VRespAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VRespAp localInstanceIn(EOEditingContext editingContext, VRespAp eo) {
    VRespAp localInstance = (eo == null) ? null : (VRespAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
