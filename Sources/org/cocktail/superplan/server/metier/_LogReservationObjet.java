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

// DO NOT EDIT.  Make changes to LogReservationObjet.java instead.
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

public abstract class _LogReservationObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "LogReservationObjet";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.LOG_RESERVATION_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "logObjetKey";

	public static final String LOG_OLD_DATE_VALIDATION_KEY = "logOldDateValidation";
	public static final String LOG_OLD_MOTIF_ANNULATION_KEY = "logOldMotifAnnulation";
	public static final String LOG_OLD_RESA_ETAT_KEY = "logOldResaEtat";
	public static final String LOG_OLD_RESA_KEY_KEY = "logOldResaKey";

	public static final String LOG_OLD_DATE_VALIDATION_COLKEY = "LOG_OLD_DATE_VALIDATION";
	public static final String LOG_OLD_MOTIF_ANNULATION_COLKEY = "LOG_OLD_MOTIF_ANNULATION";
	public static final String LOG_OLD_RESA_ETAT_COLKEY = "LOG_OLD_RESA_ETAT";
	public static final String LOG_OLD_RESA_KEY_COLKEY = "LOG_OLD_RESA_KEY";

	// Relationships
	public static final String INDIVIDU_VALIDEUR_KEY = "individuValideur";
	public static final String RESA_OBJET_KEY = "resaObjet";

	// Utilities methods
	  public LogReservationObjet localInstanceIn(EOEditingContext editingContext) {
	    LogReservationObjet localInstance = (LogReservationObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static LogReservationObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_LogReservationObjet.ENTITY_NAME);
		      return (LogReservationObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp logOldDateValidation() {
    return (NSTimestamp) storedValueForKey("logOldDateValidation");
  }

  public void setLogOldDateValidation(NSTimestamp value) {
    takeStoredValueForKey(value, "logOldDateValidation");
  }

  public String logOldMotifAnnulation() {
    return (String) storedValueForKey("logOldMotifAnnulation");
  }

  public void setLogOldMotifAnnulation(String value) {
    takeStoredValueForKey(value, "logOldMotifAnnulation");
  }

  public String logOldResaEtat() {
    return (String) storedValueForKey("logOldResaEtat");
  }

  public void setLogOldResaEtat(String value) {
    takeStoredValueForKey(value, "logOldResaEtat");
  }

  public Integer logOldResaKey() {
    return (Integer) storedValueForKey("logOldResaKey");
  }

  public void setLogOldResaKey(Integer value) {
    takeStoredValueForKey(value, "logOldResaKey");
  }

  public org.cocktail.superplan.server.metier.IndividuUlr individuValideur() {
    return (org.cocktail.superplan.server.metier.IndividuUlr)storedValueForKey("individuValideur");
  }

  public void setIndividuValideurRelationship(org.cocktail.superplan.server.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.IndividuUlr oldValue = individuValideur();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuValideur");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuValideur");
    }
  }
  
  public org.cocktail.superplan.server.metier.ResaObjet resaObjet() {
    return (org.cocktail.superplan.server.metier.ResaObjet)storedValueForKey("resaObjet");
  }

  public void setResaObjetRelationship(org.cocktail.superplan.server.metier.ResaObjet value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ResaObjet oldValue = resaObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "resaObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "resaObjet");
    }
  }
  

  public static LogReservationObjet createLogReservationObjet(EOEditingContext editingContext, String logOldResaEtat
, Integer logOldResaKey
, org.cocktail.superplan.server.metier.ResaObjet resaObjet) {
    LogReservationObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_LogReservationObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _LogReservationObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (LogReservationObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLogOldResaEtat(logOldResaEtat);
		eo.setLogOldResaKey(logOldResaKey);
    eo.setResaObjetRelationship(resaObjet);
    return eo;
  }

  public static NSArray fetchAllLogReservationObjets(EOEditingContext editingContext) {
    return _LogReservationObjet.fetchAllLogReservationObjets(editingContext, null);
  }

  public static NSArray fetchAllLogReservationObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _LogReservationObjet.fetchLogReservationObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLogReservationObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_LogReservationObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static LogReservationObjet fetchLogReservationObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationObjet.fetchLogReservationObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationObjet fetchLogReservationObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _LogReservationObjet.fetchLogReservationObjets(editingContext, qualifier, null);
    LogReservationObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (LogReservationObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one LogReservationObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationObjet fetchRequiredLogReservationObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservationObjet.fetchRequiredLogReservationObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservationObjet fetchRequiredLogReservationObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    LogReservationObjet eoObject = _LogReservationObjet.fetchLogReservationObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no LogReservationObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservationObjet localInstanceIn(EOEditingContext editingContext, LogReservationObjet eo) {
    LogReservationObjet localInstance = (eo == null) ? null : (LogReservationObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
