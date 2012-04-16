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

// DO NOT EDIT.  Make changes to RepartTypeGroupe.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _RepartTypeGroupe extends  EOGenericRecord {
	public static final String ENTITY_NAME = "RepartTypeGroupe";
	public static final String ENTITY_TABLE_NAME = "GRHUM.REPART_TYPE_GROUPE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cStructure";

	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";
	public static final String TGRP_CODE_KEY = "tgrpCode";

	public static final String D_CREATION_COLKEY = "D_CREATION";
	public static final String D_MODIFICATION_COLKEY = "D_MODIFICATION";
	public static final String TGRP_CODE_COLKEY = "TGRP_CODE";

	// Relationships

	// Utilities methods
	  public RepartTypeGroupe localInstanceIn(EOEditingContext editingContext) {
	    RepartTypeGroupe localInstance = (RepartTypeGroupe)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static RepartTypeGroupe getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_RepartTypeGroupe.ENTITY_NAME);
		      return (RepartTypeGroupe) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp dCreation() {
    return (NSTimestamp) storedValueForKey("dCreation");
  }

  public void setDCreation(NSTimestamp value) {
    takeStoredValueForKey(value, "dCreation");
  }

  public NSTimestamp dModification() {
    return (NSTimestamp) storedValueForKey("dModification");
  }

  public void setDModification(NSTimestamp value) {
    takeStoredValueForKey(value, "dModification");
  }

  public String tgrpCode() {
    return (String) storedValueForKey("tgrpCode");
  }

  public void setTgrpCode(String value) {
    takeStoredValueForKey(value, "tgrpCode");
  }


  public static RepartTypeGroupe createRepartTypeGroupe(EOEditingContext editingContext, NSTimestamp dCreation
, NSTimestamp dModification
, String tgrpCode
) {
    RepartTypeGroupe eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_RepartTypeGroupe.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _RepartTypeGroupe.ENTITY_NAME + "' !");
    } else
    {
        eo = (RepartTypeGroupe) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDCreation(dCreation);
		eo.setDModification(dModification);
		eo.setTgrpCode(tgrpCode);
    return eo;
  }

  public static NSArray fetchAllRepartTypeGroupes(EOEditingContext editingContext) {
    return _RepartTypeGroupe.fetchAllRepartTypeGroupes(editingContext, null);
  }

  public static NSArray fetchAllRepartTypeGroupes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _RepartTypeGroupe.fetchRepartTypeGroupes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchRepartTypeGroupes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_RepartTypeGroupe.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static RepartTypeGroupe fetchRepartTypeGroupe(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartTypeGroupe.fetchRepartTypeGroupe(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartTypeGroupe fetchRepartTypeGroupe(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _RepartTypeGroupe.fetchRepartTypeGroupes(editingContext, qualifier, null);
    RepartTypeGroupe eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (RepartTypeGroupe)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one RepartTypeGroupe that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartTypeGroupe fetchRequiredRepartTypeGroupe(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartTypeGroupe.fetchRequiredRepartTypeGroupe(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartTypeGroupe fetchRequiredRepartTypeGroupe(EOEditingContext editingContext, EOQualifier qualifier) {
    RepartTypeGroupe eoObject = _RepartTypeGroupe.fetchRepartTypeGroupe(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no RepartTypeGroupe that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartTypeGroupe localInstanceIn(EOEditingContext editingContext, RepartTypeGroupe eo) {
    RepartTypeGroupe localInstance = (eo == null) ? null : (RepartTypeGroupe)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
