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

// DO NOT EDIT.  Make changes to LogReservationOccupant.java instead.
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

public abstract class _LogReservationOccupant extends  EOGenericRecord {
	public static final String ENTITY_NAME = "LogReservationOccupant";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.LOG_RESERVATION_OCCUPANT";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "logOccupantKey";

	public static final String LOG_OLD_HCOMP_REC_KEY = "logOldHcompRec";
	public static final String LOG_OLD_RESA_KEY_KEY = "logOldResaKey";

	public static final String LOG_OLD_HCOMP_REC_COLKEY = "LOG_OLD_HCOMP_REC";
	public static final String LOG_OLD_RESA_KEY_COLKEY = "LOG_OLD_RESA_KEY";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";

	// Utilities methods
	  public LogReservationOccupant localInstanceIn(EOEditingContext editingContext) {
	    LogReservationOccupant localInstance = (LogReservationOccupant)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static LogReservationOccupant getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_LogReservationOccupant.ENTITY_NAME);
		      return (LogReservationOccupant) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer logOldHcompRec() {
    return (Integer) storedValueForKey("logOldHcompRec");
  }

  public void setLogOldHcompRec(Integer value) {
    takeStoredValueForKey(value, "logOldHcompRec");
  }

  public Integer logOldResaKey() {
    return (Integer) storedValueForKey("logOldResaKey");
  }

  public void setLogOldResaKey(Integer value) {
    takeStoredValueForKey(value, "logOldResaKey");
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
  

  public static LogReservationOccupant createLogReservationOccupant(EOEditingContext editingContext, Integer logOldHcompRec
, Integer logOldResaKey
, org.cocktail.superplan.server.metier.IndividuUlr individuUlr) {
    LogReservationOccupant eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_LogReservationOccupant.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _LogReservationOccupant.ENTITY_NAME + "' !");
    } else
    {
        eo = (LogReservationOccupant) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLogOldHcompRec(logOldHcompRec);
		eo.setLogOldResaKey(logOldResaKey);
    eo.setIndividuUlrRelationship(individuUlr);
    return eo;
  }

  public static NSArray fetchAllLogReservationOccupants(EOEditingContext editingContext) {
    return _LogReservationOccupant.fetchAllLogReservationOccupants(editingContext, null);
  }

  public static NSArray fetchAllLogReservationOccupants(EOEditingContext editingContext, NSArray sortOrderings) {
    return _LogReservationOccupant.fetchLogReservationOccupants(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLogReservationOccupants(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_LogReservationOccupant.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static LogReservationOccupant fetchLogReservationOccupant(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationOccupant.fetchLogReservationOccupant(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationOccupant fetchLogReservationOccupant(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _LogReservationOccupant.fetchLogReservationOccupants(editingContext, qualifier, null);
    LogReservationOccupant eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (LogReservationOccupant)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one LogReservationOccupant that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationOccupant fetchRequiredLogReservationOccupant(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationOccupant.fetchRequiredLogReservationOccupant(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationOccupant fetchRequiredLogReservationOccupant(EOEditingContext editingContext, EOQualifier qualifier) {
    LogReservationOccupant eoObject = _LogReservationOccupant.fetchLogReservationOccupant(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no LogReservationOccupant that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationOccupant localInstanceIn(EOEditingContext editingContext, LogReservationOccupant eo) {
    LogReservationOccupant localInstance = (eo == null) ? null : (LogReservationOccupant)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
