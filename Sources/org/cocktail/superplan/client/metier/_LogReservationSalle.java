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

// DO NOT EDIT.  Make changes to LogReservationSalle.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _LogReservationSalle extends  EOGenericRecord {
	public static final String ENTITY_NAME = "LogReservationSalle";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.LOG_RESERVATION_SALLE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "logSalleKey";

	public static final String LOG_OLD_RESA_KEY_KEY = "logOldResaKey";
	public static final String LOG_OLD_RESA_SAL_DATE_KEY = "logOldResaSalDate";
	public static final String LOG_OLD_RESA_SAL_ETAT_KEY = "logOldResaSalEtat";

	public static final String LOG_OLD_RESA_KEY_COLKEY = "LOG_OLD_RESA_KEY";
	public static final String LOG_OLD_RESA_SAL_DATE_COLKEY = "LOG_OLD_RESA_SAL_DATE";
	public static final String LOG_OLD_RESA_SAL_ETAT_COLKEY = "LOG_OLD_RESA_SAL_ETAT";

	// Relationships
	public static final String SALLES_KEY = "salles";

	// Utilities methods
	  public LogReservationSalle localInstanceIn(EOEditingContext editingContext) {
	    LogReservationSalle localInstance = (LogReservationSalle)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static LogReservationSalle getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_LogReservationSalle.ENTITY_NAME);
		      return (LogReservationSalle) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer logOldResaKey() {
    return (Integer) storedValueForKey("logOldResaKey");
  }

  public void setLogOldResaKey(Integer value) {
    takeStoredValueForKey(value, "logOldResaKey");
  }

  public NSTimestamp logOldResaSalDate() {
    return (NSTimestamp) storedValueForKey("logOldResaSalDate");
  }

  public void setLogOldResaSalDate(NSTimestamp value) {
    takeStoredValueForKey(value, "logOldResaSalDate");
  }

  public String logOldResaSalEtat() {
    return (String) storedValueForKey("logOldResaSalEtat");
  }

  public void setLogOldResaSalEtat(String value) {
    takeStoredValueForKey(value, "logOldResaSalEtat");
  }

  public org.cocktail.superplan.client.metier.Salles salles() {
    return (org.cocktail.superplan.client.metier.Salles)storedValueForKey("salles");
  }

  public void setSallesRelationship(org.cocktail.superplan.client.metier.Salles value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Salles oldValue = salles();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "salles");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "salles");
    }
  }
  

  public static LogReservationSalle createLogReservationSalle(EOEditingContext editingContext, Integer logOldResaKey
, org.cocktail.superplan.client.metier.Salles salles) {
    LogReservationSalle eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_LogReservationSalle.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _LogReservationSalle.ENTITY_NAME + "' !");
    } else
    {
        eo = (LogReservationSalle) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLogOldResaKey(logOldResaKey);
    eo.setSallesRelationship(salles);
    return eo;
  }

  public static NSArray fetchAllLogReservationSalles(EOEditingContext editingContext) {
    return _LogReservationSalle.fetchAllLogReservationSalles(editingContext, null);
  }

  public static NSArray fetchAllLogReservationSalles(EOEditingContext editingContext, NSArray sortOrderings) {
    return _LogReservationSalle.fetchLogReservationSalles(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLogReservationSalles(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_LogReservationSalle.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static LogReservationSalle fetchLogReservationSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationSalle.fetchLogReservationSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationSalle fetchLogReservationSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _LogReservationSalle.fetchLogReservationSalles(editingContext, qualifier, null);
    LogReservationSalle eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (LogReservationSalle)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one LogReservationSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationSalle fetchRequiredLogReservationSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationSalle.fetchRequiredLogReservationSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationSalle fetchRequiredLogReservationSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    LogReservationSalle eoObject = _LogReservationSalle.fetchLogReservationSalle(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no LogReservationSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationSalle localInstanceIn(EOEditingContext editingContext, LogReservationSalle eo) {
    LogReservationSalle localInstance = (eo == null) ? null : (LogReservationSalle)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
