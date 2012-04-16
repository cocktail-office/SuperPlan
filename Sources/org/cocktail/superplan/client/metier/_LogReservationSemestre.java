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

// DO NOT EDIT.  Make changes to LogReservationSemestre.java instead.
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

public abstract class _LogReservationSemestre extends  EOGenericRecord {
	public static final String ENTITY_NAME = "LogReservationSemestre";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.LOG_RESERVATION_SEMESTRE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "logSemestreKey";

	public static final String LOG_OLD_HCOMP_REC_KEY = "logOldHcompRec";
	public static final String LOG_OLD_RESA_KEY_KEY = "logOldResaKey";

	public static final String LOG_OLD_HCOMP_REC_COLKEY = "LOG_OLD_HCOMP_REC";
	public static final String LOG_OLD_RESA_KEY_COLKEY = "LOG_OLD_RESA_KEY";

	// Relationships
	public static final String MAQUETTE_SEMESTRE_KEY = "maquetteSemestre";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";

	// Utilities methods
	  public LogReservationSemestre localInstanceIn(EOEditingContext editingContext) {
	    LogReservationSemestre localInstance = (LogReservationSemestre)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static LogReservationSemestre getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_LogReservationSemestre.ENTITY_NAME);
		      return (LogReservationSemestre) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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

  public org.cocktail.superplan.client.metier.MaquetteSemestre maquetteSemestre() {
    return (org.cocktail.superplan.client.metier.MaquetteSemestre)storedValueForKey("maquetteSemestre");
  }

  public void setMaquetteSemestreRelationship(org.cocktail.superplan.client.metier.MaquetteSemestre value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteSemestre oldValue = maquetteSemestre();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteSemestre");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteSemestre");
    }
  }
  
  public org.cocktail.superplan.client.metier.ScolGroupeGrp scolGroupeGrp() {
    return (org.cocktail.superplan.client.metier.ScolGroupeGrp)storedValueForKey("scolGroupeGrp");
  }

  public void setScolGroupeGrpRelationship(org.cocktail.superplan.client.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ScolGroupeGrp oldValue = scolGroupeGrp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolGroupeGrp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolGroupeGrp");
    }
  }
  

  public static LogReservationSemestre createLogReservationSemestre(EOEditingContext editingContext, Integer logOldHcompRec
, Integer logOldResaKey
, org.cocktail.superplan.client.metier.MaquetteSemestre maquetteSemestre) {
    LogReservationSemestre eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_LogReservationSemestre.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _LogReservationSemestre.ENTITY_NAME + "' !");
    } else
    {
        eo = (LogReservationSemestre) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLogOldHcompRec(logOldHcompRec);
		eo.setLogOldResaKey(logOldResaKey);
    eo.setMaquetteSemestreRelationship(maquetteSemestre);
    return eo;
  }

  public static NSArray fetchAllLogReservationSemestres(EOEditingContext editingContext) {
    return _LogReservationSemestre.fetchAllLogReservationSemestres(editingContext, null);
  }

  public static NSArray fetchAllLogReservationSemestres(EOEditingContext editingContext, NSArray sortOrderings) {
    return _LogReservationSemestre.fetchLogReservationSemestres(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLogReservationSemestres(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_LogReservationSemestre.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static LogReservationSemestre fetchLogReservationSemestre(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationSemestre.fetchLogReservationSemestre(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationSemestre fetchLogReservationSemestre(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _LogReservationSemestre.fetchLogReservationSemestres(editingContext, qualifier, null);
    LogReservationSemestre eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (LogReservationSemestre)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one LogReservationSemestre that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationSemestre fetchRequiredLogReservationSemestre(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationSemestre.fetchRequiredLogReservationSemestre(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationSemestre fetchRequiredLogReservationSemestre(EOEditingContext editingContext, EOQualifier qualifier) {
    LogReservationSemestre eoObject = _LogReservationSemestre.fetchLogReservationSemestre(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no LogReservationSemestre that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationSemestre localInstanceIn(EOEditingContext editingContext, LogReservationSemestre eo) {
    LogReservationSemestre localInstance = (eo == null) ? null : (LogReservationSemestre)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
