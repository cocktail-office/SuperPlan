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

// DO NOT EDIT.  Make changes to LogReservationAp.java instead.
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

public abstract class _LogReservationAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "LogReservationAp";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.LOG_RESERVATION_AP";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "logApKey";

	public static final String LOG_OLD_HCOMP_REC_KEY = "logOldHcompRec";
	public static final String LOG_OLD_RESA_KEY_KEY = "logOldResaKey";

	public static final String LOG_OLD_HCOMP_REC_COLKEY = "LOG_OLD_HCOMP_REC";
	public static final String LOG_OLD_RESA_KEY_COLKEY = "LOG_OLD_RESA_KEY";

	// Relationships
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";

	// Utilities methods
	  public LogReservationAp localInstanceIn(EOEditingContext editingContext) {
	    LogReservationAp localInstance = (LogReservationAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static LogReservationAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_LogReservationAp.ENTITY_NAME);
		      return (LogReservationAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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
  
  public org.cocktail.superplan.server.metier.ScolGroupeGrp scolGroupeGrp() {
    return (org.cocktail.superplan.server.metier.ScolGroupeGrp)storedValueForKey("scolGroupeGrp");
  }

  public void setScolGroupeGrpRelationship(org.cocktail.superplan.server.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ScolGroupeGrp oldValue = scolGroupeGrp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolGroupeGrp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolGroupeGrp");
    }
  }
  

  public static LogReservationAp createLogReservationAp(EOEditingContext editingContext, Integer logOldHcompRec
, Integer logOldResaKey
, org.cocktail.superplan.server.metier.MaquetteAp maquetteAp) {
    LogReservationAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_LogReservationAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _LogReservationAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (LogReservationAp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLogOldHcompRec(logOldHcompRec);
		eo.setLogOldResaKey(logOldResaKey);
    eo.setMaquetteApRelationship(maquetteAp);
    return eo;
  }

  public static NSArray fetchAllLogReservationAps(EOEditingContext editingContext) {
    return _LogReservationAp.fetchAllLogReservationAps(editingContext, null);
  }

  public static NSArray fetchAllLogReservationAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _LogReservationAp.fetchLogReservationAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLogReservationAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_LogReservationAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static LogReservationAp fetchLogReservationAp(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationAp.fetchLogReservationAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationAp fetchLogReservationAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _LogReservationAp.fetchLogReservationAps(editingContext, qualifier, null);
    LogReservationAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (LogReservationAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one LogReservationAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationAp fetchRequiredLogReservationAp(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationAp.fetchRequiredLogReservationAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationAp fetchRequiredLogReservationAp(EOEditingContext editingContext, EOQualifier qualifier) {
    LogReservationAp eoObject = _LogReservationAp.fetchLogReservationAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no LogReservationAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationAp localInstanceIn(EOEditingContext editingContext, LogReservationAp eo) {
    LogReservationAp localInstance = (eo == null) ? null : (LogReservationAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
