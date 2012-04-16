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

// DO NOT EDIT.  Make changes to MaquetteResponsableEc.java instead.
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

public abstract class _MaquetteResponsableEc extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteResponsableEc";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_RESPONSABLE_EC";

	// Attributes

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MBEC_TYPE_KEY = "mbecType";
	public static final String PERS_ID_KEY = "persId";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String MBEC_TYPE_COLKEY = "MBEC_TYPE";
	public static final String PERS_ID_COLKEY = "PERS_ID";

	// Relationships
	public static final String MAQUETTE_EC_KEY = "maquetteEc";

	// Utilities methods
	  public MaquetteResponsableEc localInstanceIn(EOEditingContext editingContext) {
	    MaquetteResponsableEc localInstance = (MaquetteResponsableEc)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteResponsableEc getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteResponsableEc.ENTITY_NAME);
		      return (MaquetteResponsableEc) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public String mbecType() {
    return (String) storedValueForKey("mbecType");
  }

  public void setMbecType(String value) {
    takeStoredValueForKey(value, "mbecType");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public org.cocktail.superplan.server.metier.MaquetteEc maquetteEc() {
    return (org.cocktail.superplan.server.metier.MaquetteEc)storedValueForKey("maquetteEc");
  }

  public void setMaquetteEcRelationship(org.cocktail.superplan.server.metier.MaquetteEc value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteEc oldValue = maquetteEc();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteEc");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteEc");
    }
  }
  

  public static MaquetteResponsableEc createMaquetteResponsableEc(EOEditingContext editingContext, Integer fannKey
, String mbecType
) {
    MaquetteResponsableEc eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteResponsableEc.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteResponsableEc.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteResponsableEc) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setMbecType(mbecType);
    return eo;
  }

  public static NSArray fetchAllMaquetteResponsableEcs(EOEditingContext editingContext) {
    return _MaquetteResponsableEc.fetchAllMaquetteResponsableEcs(editingContext, null);
  }

  public static NSArray fetchAllMaquetteResponsableEcs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteResponsableEc.fetchMaquetteResponsableEcs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteResponsableEcs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteResponsableEc.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteResponsableEc fetchMaquetteResponsableEc(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteResponsableEc.fetchMaquetteResponsableEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteResponsableEc fetchMaquetteResponsableEc(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteResponsableEc.fetchMaquetteResponsableEcs(editingContext, qualifier, null);
    MaquetteResponsableEc eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteResponsableEc)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteResponsableEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteResponsableEc fetchRequiredMaquetteResponsableEc(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteResponsableEc.fetchRequiredMaquetteResponsableEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteResponsableEc fetchRequiredMaquetteResponsableEc(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteResponsableEc eoObject = _MaquetteResponsableEc.fetchMaquetteResponsableEc(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteResponsableEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteResponsableEc localInstanceIn(EOEditingContext editingContext, MaquetteResponsableEc eo) {
    MaquetteResponsableEc localInstance = (eo == null) ? null : (MaquetteResponsableEc)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
