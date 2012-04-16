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

// DO NOT EDIT.  Make changes to LogReservationPeriod.java instead.
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

public abstract class _LogReservationPeriod extends  EOGenericRecord {
	public static final String ENTITY_NAME = "LogReservationPeriod";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.LOG_RESERVATION_PERIOD";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "logPeriodiciteKey";

	public static final String LOG_OLD_DATE_DEB_KEY = "logOldDateDeb";
	public static final String LOG_OLD_DATE_FIN_KEY = "logOldDateFin";
	public static final String LOG_OLD_HCOMP_KEY = "logOldHcomp";
	public static final String LOG_OLD_RESA_KEY_KEY = "logOldResaKey";

	public static final String LOG_OLD_DATE_DEB_COLKEY = "LOG_OLD_DATE_DEB";
	public static final String LOG_OLD_DATE_FIN_COLKEY = "LOG_OLD_DATE_FIN";
	public static final String LOG_OLD_HCOMP_COLKEY = "LOG_OLD_HCOMP";
	public static final String LOG_OLD_RESA_KEY_COLKEY = "LOG_OLD_RESA_KEY";

	// Relationships

	// Utilities methods
	  public LogReservationPeriod localInstanceIn(EOEditingContext editingContext) {
	    LogReservationPeriod localInstance = (LogReservationPeriod)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static LogReservationPeriod getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_LogReservationPeriod.ENTITY_NAME);
		      return (LogReservationPeriod) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp logOldDateDeb() {
    return (NSTimestamp) storedValueForKey("logOldDateDeb");
  }

  public void setLogOldDateDeb(NSTimestamp value) {
    takeStoredValueForKey(value, "logOldDateDeb");
  }

  public NSTimestamp logOldDateFin() {
    return (NSTimestamp) storedValueForKey("logOldDateFin");
  }

  public void setLogOldDateFin(NSTimestamp value) {
    takeStoredValueForKey(value, "logOldDateFin");
  }

  public Integer logOldHcomp() {
    return (Integer) storedValueForKey("logOldHcomp");
  }

  public void setLogOldHcomp(Integer value) {
    takeStoredValueForKey(value, "logOldHcomp");
  }

  public Integer logOldResaKey() {
    return (Integer) storedValueForKey("logOldResaKey");
  }

  public void setLogOldResaKey(Integer value) {
    takeStoredValueForKey(value, "logOldResaKey");
  }


  public static LogReservationPeriod createLogReservationPeriod(EOEditingContext editingContext, NSTimestamp logOldDateDeb
, NSTimestamp logOldDateFin
, Integer logOldHcomp
, Integer logOldResaKey
) {
    LogReservationPeriod eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_LogReservationPeriod.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _LogReservationPeriod.ENTITY_NAME + "' !");
    } else
    {
        eo = (LogReservationPeriod) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLogOldDateDeb(logOldDateDeb);
		eo.setLogOldDateFin(logOldDateFin);
		eo.setLogOldHcomp(logOldHcomp);
		eo.setLogOldResaKey(logOldResaKey);
    return eo;
  }

  public static NSArray fetchAllLogReservationPeriods(EOEditingContext editingContext) {
    return _LogReservationPeriod.fetchAllLogReservationPeriods(editingContext, null);
  }

  public static NSArray fetchAllLogReservationPeriods(EOEditingContext editingContext, NSArray sortOrderings) {
    return _LogReservationPeriod.fetchLogReservationPeriods(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLogReservationPeriods(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_LogReservationPeriod.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static LogReservationPeriod fetchLogReservationPeriod(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationPeriod.fetchLogReservationPeriod(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationPeriod fetchLogReservationPeriod(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _LogReservationPeriod.fetchLogReservationPeriods(editingContext, qualifier, null);
    LogReservationPeriod eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (LogReservationPeriod)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one LogReservationPeriod that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationPeriod fetchRequiredLogReservationPeriod(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationPeriod.fetchRequiredLogReservationPeriod(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationPeriod fetchRequiredLogReservationPeriod(EOEditingContext editingContext, EOQualifier qualifier) {
    LogReservationPeriod eoObject = _LogReservationPeriod.fetchLogReservationPeriod(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no LogReservationPeriod that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationPeriod localInstanceIn(EOEditingContext editingContext, LogReservationPeriod eo) {
    LogReservationPeriod localInstance = (eo == null) ? null : (LogReservationPeriod)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
