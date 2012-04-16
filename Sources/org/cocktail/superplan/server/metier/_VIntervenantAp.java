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

// DO NOT EDIT.  Make changes to VIntervenantAp.java instead.
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

public abstract class _VIntervenantAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VIntervenantAp";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_INTERVENANT_AP";

	// Attributes

	public static final String MAP_KEY_KEY = "mapKey";
	public static final String NO_INDIVIDU_KEY = "noIndividu";

	public static final String MAP_KEY_COLKEY = "MAP_KEY";
	public static final String NO_INDIVIDU_COLKEY = "NO_INDIVIDU";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String MAQUETTE_AP_KEY = "maquetteAp";

	// Utilities methods
	  public VIntervenantAp localInstanceIn(EOEditingContext editingContext) {
	    VIntervenantAp localInstance = (VIntervenantAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VIntervenantAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VIntervenantAp.ENTITY_NAME);
		      return (VIntervenantAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer mapKey() {
    return (Integer) storedValueForKey("mapKey");
  }

  public void setMapKey(Integer value) {
    takeStoredValueForKey(value, "mapKey");
  }

  public Integer noIndividu() {
    return (Integer) storedValueForKey("noIndividu");
  }

  public void setNoIndividu(Integer value) {
    takeStoredValueForKey(value, "noIndividu");
  }

  public org.cocktail.superplan.server.metier.IndividuUlr individuUlr() {
    return (org.cocktail.superplan.server.metier.IndividuUlr)storedValueForKey("individuUlr");
  }

  public void setIndividuUlrRelationship(org.cocktail.superplan.server.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.IndividuUlr oldValue = individuUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuUlr");
    }
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
  

  public static VIntervenantAp createVIntervenantAp(EOEditingContext editingContext, Integer mapKey
, Integer noIndividu
) {
    VIntervenantAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VIntervenantAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VIntervenantAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (VIntervenantAp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setMapKey(mapKey);
		eo.setNoIndividu(noIndividu);
    return eo;
  }

  public static NSArray fetchAllVIntervenantAps(EOEditingContext editingContext) {
    return _VIntervenantAp.fetchAllVIntervenantAps(editingContext, null);
  }

  public static NSArray fetchAllVIntervenantAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VIntervenantAp.fetchVIntervenantAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVIntervenantAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VIntervenantAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VIntervenantAp fetchVIntervenantAp(EOEditingContext editingContext, String keyName, Object value) {
    return _VIntervenantAp.fetchVIntervenantAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VIntervenantAp fetchVIntervenantAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VIntervenantAp.fetchVIntervenantAps(editingContext, qualifier, null);
    VIntervenantAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VIntervenantAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VIntervenantAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VIntervenantAp fetchRequiredVIntervenantAp(EOEditingContext editingContext, String keyName, Object value) {
    return _VIntervenantAp.fetchRequiredVIntervenantAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VIntervenantAp fetchRequiredVIntervenantAp(EOEditingContext editingContext, EOQualifier qualifier) {
    VIntervenantAp eoObject = _VIntervenantAp.fetchVIntervenantAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VIntervenantAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VIntervenantAp localInstanceIn(EOEditingContext editingContext, VIntervenantAp eo) {
    VIntervenantAp localInstance = (eo == null) ? null : (VIntervenantAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
