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

// DO NOT EDIT.  Make changes to LogReservation.java instead.
package org.cocktail.superplan.client.metier;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

public abstract class _LogReservation extends  EOGenericRecord {
	public static final String ENTITY_NAME = "LogReservation";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.LOG_RESERVATION";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "logKey";

	public static final String LOG_ACTION_KEY = "logAction";
	public static final String LOG_DATE_KEY = "logDate";
	public static final String LOG_MOTIF_KEY = "logMotif";
	public static final String LOG_OLD_D_CREATION_KEY = "logOldDCreation";
	public static final String LOG_OLD_D_MODIFICATION_KEY = "logOldDModification";
	public static final String LOG_OLD_RESA_COMMENTAIRE_KEY = "logOldResaCommentaire";
	public static final String LOG_OLD_TLOC_CODE_KEY = "logOldTlocCode";
	public static final String LOG_RESA_KEY_KEY = "logResaKey";

	public static final String LOG_ACTION_COLKEY = "LOG_ACTION";
	public static final String LOG_DATE_COLKEY = "LOG_DATE";
	public static final String LOG_MOTIF_COLKEY = "LOG_MOTIF";
	public static final String LOG_OLD_D_CREATION_COLKEY = "LOG_OLD_D_CREATION";
	public static final String LOG_OLD_D_MODIFICATION_COLKEY = "LOG_OLD_D_MODIFICATION";
	public static final String LOG_OLD_RESA_COMMENTAIRE_COLKEY = "LOG_OLD_RESA_COMMENTAIRE";
	public static final String LOG_OLD_TLOC_CODE_COLKEY = "LOG_OLD_TLOC_CODE";
	public static final String LOG_RESA_KEY_COLKEY = "LOG_RESA_KEY";

	// Relationships
	public static final String INDIVIDU_LOG_AGENT_KEY = "individuLogAgent";
	public static final String INDIVIDU_OLD_AGENT_KEY = "individuOldAgent";
	public static final String LOG_RESERVATION_APS_KEY = "logReservationAps";
	public static final String LOG_RESERVATION_EXAMENS_KEY = "logReservationExamens";
	public static final String LOG_RESERVATION_OBJETS_KEY = "logReservationObjets";
	public static final String LOG_RESERVATION_OCCUPANTS_KEY = "logReservationOccupants";
	public static final String LOG_RESERVATION_PERIODS_KEY = "logReservationPeriods";
	public static final String LOG_RESERVATION_SALLES_KEY = "logReservationSalles";
	public static final String LOG_RESERVATION_SEMESTRES_KEY = "logReservationSemestres";
	public static final String TYPE_LOCATION_KEY = "typeLocation";

	// Utilities methods
	  public LogReservation localInstanceIn(EOEditingContext editingContext) {
	    LogReservation localInstance = (LogReservation)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static LogReservation getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_LogReservation.ENTITY_NAME);
		      return (LogReservation) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String logAction() {
    return (String) storedValueForKey("logAction");
  }

  public void setLogAction(String value) {
    takeStoredValueForKey(value, "logAction");
  }

  public NSTimestamp logDate() {
    return (NSTimestamp) storedValueForKey("logDate");
  }

  public void setLogDate(NSTimestamp value) {
    takeStoredValueForKey(value, "logDate");
  }

  public String logMotif() {
    return (String) storedValueForKey("logMotif");
  }

  public void setLogMotif(String value) {
    takeStoredValueForKey(value, "logMotif");
  }

  public NSTimestamp logOldDCreation() {
    return (NSTimestamp) storedValueForKey("logOldDCreation");
  }

  public void setLogOldDCreation(NSTimestamp value) {
    takeStoredValueForKey(value, "logOldDCreation");
  }

  public NSTimestamp logOldDModification() {
    return (NSTimestamp) storedValueForKey("logOldDModification");
  }

  public void setLogOldDModification(NSTimestamp value) {
    takeStoredValueForKey(value, "logOldDModification");
  }

  public String logOldResaCommentaire() {
    return (String) storedValueForKey("logOldResaCommentaire");
  }

  public void setLogOldResaCommentaire(String value) {
    takeStoredValueForKey(value, "logOldResaCommentaire");
  }

  public String logOldTlocCode() {
    return (String) storedValueForKey("logOldTlocCode");
  }

  public void setLogOldTlocCode(String value) {
    takeStoredValueForKey(value, "logOldTlocCode");
  }

  public Integer logResaKey() {
    return (Integer) storedValueForKey("logResaKey");
  }

  public void setLogResaKey(Integer value) {
    takeStoredValueForKey(value, "logResaKey");
  }

  public org.cocktail.superplan.client.metier.IndividuUlr individuLogAgent() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individuLogAgent");
  }

  public void setIndividuLogAgentRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individuLogAgent();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuLogAgent");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuLogAgent");
    }
  }
  
  public org.cocktail.superplan.client.metier.IndividuUlr individuOldAgent() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individuOldAgent");
  }

  public void setIndividuOldAgentRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individuOldAgent();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuOldAgent");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuOldAgent");
    }
  }
  
  public org.cocktail.superplan.client.metier.TypeLocation typeLocation() {
    return (org.cocktail.superplan.client.metier.TypeLocation)storedValueForKey("typeLocation");
  }

  public void setTypeLocationRelationship(org.cocktail.superplan.client.metier.TypeLocation value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.TypeLocation oldValue = typeLocation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "typeLocation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "typeLocation");
    }
  }
  
  public NSArray logReservationAps() {
    return (NSArray)storedValueForKey("logReservationAps");
  }

  public NSArray logReservationAps(EOQualifier qualifier) {
    return logReservationAps(qualifier, null);
  }

  public NSArray logReservationAps(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = logReservationAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToLogReservationApsRelationship(org.cocktail.superplan.client.metier.LogReservationAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "logReservationAps");
  }

  public void removeFromLogReservationApsRelationship(org.cocktail.superplan.client.metier.LogReservationAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationAps");
  }

  public org.cocktail.superplan.client.metier.LogReservationAp createLogReservationApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("LogReservationAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "logReservationAps");
    return (org.cocktail.superplan.client.metier.LogReservationAp) eo;
  }

  public void deleteLogReservationApsRelationship(org.cocktail.superplan.client.metier.LogReservationAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllLogReservationApsRelationships() {
    Enumeration objects = logReservationAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLogReservationApsRelationship((org.cocktail.superplan.client.metier.LogReservationAp)objects.nextElement());
    }
  }

  public NSArray logReservationExamens() {
    return (NSArray)storedValueForKey("logReservationExamens");
  }

  public NSArray logReservationExamens(EOQualifier qualifier) {
    return logReservationExamens(qualifier, null);
  }

  public NSArray logReservationExamens(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = logReservationExamens();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToLogReservationExamensRelationship(org.cocktail.superplan.client.metier.LogReservationExamen object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "logReservationExamens");
  }

  public void removeFromLogReservationExamensRelationship(org.cocktail.superplan.client.metier.LogReservationExamen object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationExamens");
  }

  public org.cocktail.superplan.client.metier.LogReservationExamen createLogReservationExamensRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("LogReservationExamen");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "logReservationExamens");
    return (org.cocktail.superplan.client.metier.LogReservationExamen) eo;
  }

  public void deleteLogReservationExamensRelationship(org.cocktail.superplan.client.metier.LogReservationExamen object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationExamens");
    editingContext().deleteObject(object);
  }

  public void deleteAllLogReservationExamensRelationships() {
    Enumeration objects = logReservationExamens().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLogReservationExamensRelationship((org.cocktail.superplan.client.metier.LogReservationExamen)objects.nextElement());
    }
  }

  public NSArray logReservationObjets() {
    return (NSArray)storedValueForKey("logReservationObjets");
  }

  public NSArray logReservationObjets(EOQualifier qualifier) {
    return logReservationObjets(qualifier, null);
  }

  public NSArray logReservationObjets(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = logReservationObjets();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToLogReservationObjetsRelationship(org.cocktail.superplan.client.metier.LogReservationObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "logReservationObjets");
  }

  public void removeFromLogReservationObjetsRelationship(org.cocktail.superplan.client.metier.LogReservationObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationObjets");
  }

  public org.cocktail.superplan.client.metier.LogReservationObjet createLogReservationObjetsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("LogReservationObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "logReservationObjets");
    return (org.cocktail.superplan.client.metier.LogReservationObjet) eo;
  }

  public void deleteLogReservationObjetsRelationship(org.cocktail.superplan.client.metier.LogReservationObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationObjets");
    editingContext().deleteObject(object);
  }

  public void deleteAllLogReservationObjetsRelationships() {
    Enumeration objects = logReservationObjets().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLogReservationObjetsRelationship((org.cocktail.superplan.client.metier.LogReservationObjet)objects.nextElement());
    }
  }

  public NSArray logReservationOccupants() {
    return (NSArray)storedValueForKey("logReservationOccupants");
  }

  public NSArray logReservationOccupants(EOQualifier qualifier) {
    return logReservationOccupants(qualifier, null);
  }

  public NSArray logReservationOccupants(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = logReservationOccupants();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToLogReservationOccupantsRelationship(org.cocktail.superplan.client.metier.LogReservationOccupant object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "logReservationOccupants");
  }

  public void removeFromLogReservationOccupantsRelationship(org.cocktail.superplan.client.metier.LogReservationOccupant object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationOccupants");
  }

  public org.cocktail.superplan.client.metier.LogReservationOccupant createLogReservationOccupantsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("LogReservationOccupant");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "logReservationOccupants");
    return (org.cocktail.superplan.client.metier.LogReservationOccupant) eo;
  }

  public void deleteLogReservationOccupantsRelationship(org.cocktail.superplan.client.metier.LogReservationOccupant object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationOccupants");
    editingContext().deleteObject(object);
  }

  public void deleteAllLogReservationOccupantsRelationships() {
    Enumeration objects = logReservationOccupants().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLogReservationOccupantsRelationship((org.cocktail.superplan.client.metier.LogReservationOccupant)objects.nextElement());
    }
  }

  public NSArray logReservationPeriods() {
    return (NSArray)storedValueForKey("logReservationPeriods");
  }

  public NSArray logReservationPeriods(EOQualifier qualifier) {
    return logReservationPeriods(qualifier, null);
  }

  public NSArray logReservationPeriods(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = logReservationPeriods();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToLogReservationPeriodsRelationship(org.cocktail.superplan.client.metier.LogReservationPeriod object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "logReservationPeriods");
  }

  public void removeFromLogReservationPeriodsRelationship(org.cocktail.superplan.client.metier.LogReservationPeriod object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationPeriods");
  }

  public org.cocktail.superplan.client.metier.LogReservationPeriod createLogReservationPeriodsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("LogReservationPeriod");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "logReservationPeriods");
    return (org.cocktail.superplan.client.metier.LogReservationPeriod) eo;
  }

  public void deleteLogReservationPeriodsRelationship(org.cocktail.superplan.client.metier.LogReservationPeriod object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationPeriods");
    editingContext().deleteObject(object);
  }

  public void deleteAllLogReservationPeriodsRelationships() {
    Enumeration objects = logReservationPeriods().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLogReservationPeriodsRelationship((org.cocktail.superplan.client.metier.LogReservationPeriod)objects.nextElement());
    }
  }

  public NSArray logReservationSalles() {
    return (NSArray)storedValueForKey("logReservationSalles");
  }

  public NSArray logReservationSalles(EOQualifier qualifier) {
    return logReservationSalles(qualifier, null);
  }

  public NSArray logReservationSalles(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = logReservationSalles();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToLogReservationSallesRelationship(org.cocktail.superplan.client.metier.LogReservationSalle object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "logReservationSalles");
  }

  public void removeFromLogReservationSallesRelationship(org.cocktail.superplan.client.metier.LogReservationSalle object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationSalles");
  }

  public org.cocktail.superplan.client.metier.LogReservationSalle createLogReservationSallesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("LogReservationSalle");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "logReservationSalles");
    return (org.cocktail.superplan.client.metier.LogReservationSalle) eo;
  }

  public void deleteLogReservationSallesRelationship(org.cocktail.superplan.client.metier.LogReservationSalle object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationSalles");
    editingContext().deleteObject(object);
  }

  public void deleteAllLogReservationSallesRelationships() {
    Enumeration objects = logReservationSalles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLogReservationSallesRelationship((org.cocktail.superplan.client.metier.LogReservationSalle)objects.nextElement());
    }
  }

  public NSArray logReservationSemestres() {
    return (NSArray)storedValueForKey("logReservationSemestres");
  }

  public NSArray logReservationSemestres(EOQualifier qualifier) {
    return logReservationSemestres(qualifier, null);
  }

  public NSArray logReservationSemestres(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = logReservationSemestres();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToLogReservationSemestresRelationship(org.cocktail.superplan.client.metier.LogReservationSemestre object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "logReservationSemestres");
  }

  public void removeFromLogReservationSemestresRelationship(org.cocktail.superplan.client.metier.LogReservationSemestre object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationSemestres");
  }

  public org.cocktail.superplan.client.metier.LogReservationSemestre createLogReservationSemestresRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("LogReservationSemestre");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "logReservationSemestres");
    return (org.cocktail.superplan.client.metier.LogReservationSemestre) eo;
  }

  public void deleteLogReservationSemestresRelationship(org.cocktail.superplan.client.metier.LogReservationSemestre object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservationSemestres");
    editingContext().deleteObject(object);
  }

  public void deleteAllLogReservationSemestresRelationships() {
    Enumeration objects = logReservationSemestres().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLogReservationSemestresRelationship((org.cocktail.superplan.client.metier.LogReservationSemestre)objects.nextElement());
    }
  }


  public static LogReservation createLogReservation(EOEditingContext editingContext, String logAction
, NSTimestamp logDate
, NSTimestamp logOldDCreation
, NSTimestamp logOldDModification
, String logOldTlocCode
, Integer logResaKey
, org.cocktail.superplan.client.metier.IndividuUlr individuLogAgent, org.cocktail.superplan.client.metier.IndividuUlr individuOldAgent, org.cocktail.superplan.client.metier.TypeLocation typeLocation) {
    LogReservation eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_LogReservation.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _LogReservation.ENTITY_NAME + "' !");
    } else
    {
        eo = (LogReservation) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLogAction(logAction);
		eo.setLogDate(logDate);
		eo.setLogOldDCreation(logOldDCreation);
		eo.setLogOldDModification(logOldDModification);
		eo.setLogOldTlocCode(logOldTlocCode);
		eo.setLogResaKey(logResaKey);
    eo.setIndividuLogAgentRelationship(individuLogAgent);
    eo.setIndividuOldAgentRelationship(individuOldAgent);
    eo.setTypeLocationRelationship(typeLocation);
    return eo;
  }

  public static NSArray fetchAllLogReservations(EOEditingContext editingContext) {
    return _LogReservation.fetchAllLogReservations(editingContext, null);
  }

  public static NSArray fetchAllLogReservations(EOEditingContext editingContext, NSArray sortOrderings) {
    return _LogReservation.fetchLogReservations(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLogReservations(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_LogReservation.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static LogReservation fetchLogReservation(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservation.fetchLogReservation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservation fetchLogReservation(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _LogReservation.fetchLogReservations(editingContext, qualifier, null);
    LogReservation eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (LogReservation)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one LogReservation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservation fetchRequiredLogReservation(EOEditingContext editingContext, String keyName, Object value) {
    return _LogReservation.fetchRequiredLogReservation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LogReservation fetchRequiredLogReservation(EOEditingContext editingContext, EOQualifier qualifier) {
    LogReservation eoObject = _LogReservation.fetchLogReservation(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no LogReservation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LogReservation localInstanceIn(EOEditingContext editingContext, LogReservation eo) {
    LogReservation localInstance = (eo == null) ? null : (LogReservation)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
