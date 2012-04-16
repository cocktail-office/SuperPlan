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

// DO NOT EDIT.  Make changes to Reservation.java instead.
package org.cocktail.superplan.server.metier;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

public abstract class _Reservation extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Reservation";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESERVATION";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "resaKey";

	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";
	public static final String RESA_COMMENTAIRE_KEY = "resaCommentaire";
	public static final String TLOC_CODE_KEY = "tlocCode";

	public static final String D_CREATION_COLKEY = "D_CREATION";
	public static final String D_MODIFICATION_COLKEY = "D_MODIFICATION";
	public static final String RESA_COMMENTAIRE_COLKEY = "RESA_COMMENTAIRE";
	public static final String TLOC_CODE_COLKEY = "TLOC_CODE";

	// Relationships
	public static final String INDIVIDU_AGENT_KEY = "individuAgent";
	public static final String LOG_RESERVATIONS_KEY = "logReservations";
	public static final String OCCUPANTS_KEY = "occupants";
	public static final String PERIODICITES_KEY = "periodicites";
	public static final String RESA_EXAMENS_KEY = "resaExamens";
	public static final String RESA_SALLES_KEY = "resaSalles";
	public static final String RESERVATION_AP_KEY = "reservationAp";
	public static final String RESERVATION_OBJETS_KEY = "reservationObjets";
	public static final String RESERVATIONS_SEMESTRES_KEY = "reservationsSemestres";
	public static final String SALLES_SOUHAITEES_KEY = "sallesSouhaitees";
	public static final String TYPE_LOCATION_KEY = "typeLocation";

	// Utilities methods
	  public Reservation localInstanceIn(EOEditingContext editingContext) {
	    Reservation localInstance = (Reservation)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Reservation getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Reservation.ENTITY_NAME);
		      return (Reservation) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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

  public String resaCommentaire() {
    return (String) storedValueForKey("resaCommentaire");
  }

  public void setResaCommentaire(String value) {
    takeStoredValueForKey(value, "resaCommentaire");
  }

  public String tlocCode() {
    return (String) storedValueForKey("tlocCode");
  }

  public void setTlocCode(String value) {
    takeStoredValueForKey(value, "tlocCode");
  }

  public org.cocktail.superplan.server.metier.IndividuUlr individuAgent() {
    return (org.cocktail.superplan.server.metier.IndividuUlr)storedValueForKey("individuAgent");
  }

  public void setIndividuAgentRelationship(org.cocktail.superplan.server.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.IndividuUlr oldValue = individuAgent();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuAgent");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuAgent");
    }
  }
  
  public org.cocktail.superplan.server.metier.TypeLocation typeLocation() {
    return (org.cocktail.superplan.server.metier.TypeLocation)storedValueForKey("typeLocation");
  }

  public void setTypeLocationRelationship(org.cocktail.superplan.server.metier.TypeLocation value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.TypeLocation oldValue = typeLocation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "typeLocation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "typeLocation");
    }
  }
  
  public NSArray logReservations() {
    return (NSArray)storedValueForKey("logReservations");
  }

  public NSArray logReservations(EOQualifier qualifier) {
    return logReservations(qualifier, null);
  }

  public NSArray logReservations(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = logReservations();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToLogReservationsRelationship(org.cocktail.superplan.server.metier.LogReservation object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "logReservations");
  }

  public void removeFromLogReservationsRelationship(org.cocktail.superplan.server.metier.LogReservation object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservations");
  }

  public org.cocktail.superplan.server.metier.LogReservation createLogReservationsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("LogReservation");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "logReservations");
    return (org.cocktail.superplan.server.metier.LogReservation) eo;
  }

  public void deleteLogReservationsRelationship(org.cocktail.superplan.server.metier.LogReservation object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "logReservations");
    editingContext().deleteObject(object);
  }

  public void deleteAllLogReservationsRelationships() {
    Enumeration objects = logReservations().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLogReservationsRelationship((org.cocktail.superplan.server.metier.LogReservation)objects.nextElement());
    }
  }

  public NSArray occupants() {
    return (NSArray)storedValueForKey("occupants");
  }

  public NSArray occupants(EOQualifier qualifier) {
    return occupants(qualifier, null, false);
  }

  public NSArray occupants(EOQualifier qualifier, boolean fetch) {
    return occupants(qualifier, null, fetch);
  }

  public NSArray occupants(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.Occupant.RESERVATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.Occupant.fetchOccupants(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = occupants();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToOccupantsRelationship(org.cocktail.superplan.server.metier.Occupant object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "occupants");
  }

  public void removeFromOccupantsRelationship(org.cocktail.superplan.server.metier.Occupant object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "occupants");
  }

  public org.cocktail.superplan.server.metier.Occupant createOccupantsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Occupant");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "occupants");
    return (org.cocktail.superplan.server.metier.Occupant) eo;
  }

  public void deleteOccupantsRelationship(org.cocktail.superplan.server.metier.Occupant object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "occupants");
    editingContext().deleteObject(object);
  }

  public void deleteAllOccupantsRelationships() {
    Enumeration objects = occupants().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteOccupantsRelationship((org.cocktail.superplan.server.metier.Occupant)objects.nextElement());
    }
  }

  public NSArray periodicites() {
    return (NSArray)storedValueForKey("periodicites");
  }

  public NSArray periodicites(EOQualifier qualifier) {
    return periodicites(qualifier, null, false);
  }

  public NSArray periodicites(EOQualifier qualifier, boolean fetch) {
    return periodicites(qualifier, null, fetch);
  }

  public NSArray periodicites(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.Periodicite.RESERVATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.Periodicite.fetchPeriodicites(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = periodicites();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToPeriodicitesRelationship(org.cocktail.superplan.server.metier.Periodicite object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "periodicites");
  }

  public void removeFromPeriodicitesRelationship(org.cocktail.superplan.server.metier.Periodicite object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "periodicites");
  }

  public org.cocktail.superplan.server.metier.Periodicite createPeriodicitesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Periodicite");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "periodicites");
    return (org.cocktail.superplan.server.metier.Periodicite) eo;
  }

  public void deletePeriodicitesRelationship(org.cocktail.superplan.server.metier.Periodicite object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "periodicites");
    editingContext().deleteObject(object);
  }

  public void deleteAllPeriodicitesRelationships() {
    Enumeration objects = periodicites().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deletePeriodicitesRelationship((org.cocktail.superplan.server.metier.Periodicite)objects.nextElement());
    }
  }

  public NSArray resaExamens() {
    return (NSArray)storedValueForKey("resaExamens");
  }

  public NSArray resaExamens(EOQualifier qualifier) {
    return resaExamens(qualifier, null, false);
  }

  public NSArray resaExamens(EOQualifier qualifier, boolean fetch) {
    return resaExamens(qualifier, null, fetch);
  }

  public NSArray resaExamens(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ResaExamen.RESERVATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ResaExamen.fetchResaExamens(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = resaExamens();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToResaExamensRelationship(org.cocktail.superplan.server.metier.ResaExamen object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "resaExamens");
  }

  public void removeFromResaExamensRelationship(org.cocktail.superplan.server.metier.ResaExamen object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaExamens");
  }

  public org.cocktail.superplan.server.metier.ResaExamen createResaExamensRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ResaExamen");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "resaExamens");
    return (org.cocktail.superplan.server.metier.ResaExamen) eo;
  }

  public void deleteResaExamensRelationship(org.cocktail.superplan.server.metier.ResaExamen object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaExamens");
    editingContext().deleteObject(object);
  }

  public void deleteAllResaExamensRelationships() {
    Enumeration objects = resaExamens().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteResaExamensRelationship((org.cocktail.superplan.server.metier.ResaExamen)objects.nextElement());
    }
  }

  public NSArray resaSalles() {
    return (NSArray)storedValueForKey("resaSalles");
  }

  public NSArray resaSalles(EOQualifier qualifier) {
    return resaSalles(qualifier, null, false);
  }

  public NSArray resaSalles(EOQualifier qualifier, boolean fetch) {
    return resaSalles(qualifier, null, fetch);
  }

  public NSArray resaSalles(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ResaSalles.RESERVATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ResaSalles.fetchResaSalleses(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = resaSalles();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToResaSallesRelationship(org.cocktail.superplan.server.metier.ResaSalles object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "resaSalles");
  }

  public void removeFromResaSallesRelationship(org.cocktail.superplan.server.metier.ResaSalles object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaSalles");
  }

  public org.cocktail.superplan.server.metier.ResaSalles createResaSallesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ResaSalles");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "resaSalles");
    return (org.cocktail.superplan.server.metier.ResaSalles) eo;
  }

  public void deleteResaSallesRelationship(org.cocktail.superplan.server.metier.ResaSalles object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaSalles");
    editingContext().deleteObject(object);
  }

  public void deleteAllResaSallesRelationships() {
    Enumeration objects = resaSalles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteResaSallesRelationship((org.cocktail.superplan.server.metier.ResaSalles)objects.nextElement());
    }
  }

  public NSArray reservationAp() {
    return (NSArray)storedValueForKey("reservationAp");
  }

  public NSArray reservationAp(EOQualifier qualifier) {
    return reservationAp(qualifier, null, false);
  }

  public NSArray reservationAp(EOQualifier qualifier, boolean fetch) {
    return reservationAp(qualifier, null, fetch);
  }

  public NSArray reservationAp(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ReservationAp.RESERVATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ReservationAp.fetchReservationAps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = reservationAp();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToReservationApRelationship(org.cocktail.superplan.server.metier.ReservationAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "reservationAp");
  }

  public void removeFromReservationApRelationship(org.cocktail.superplan.server.metier.ReservationAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "reservationAp");
  }

  public org.cocktail.superplan.server.metier.ReservationAp createReservationApRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ReservationAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "reservationAp");
    return (org.cocktail.superplan.server.metier.ReservationAp) eo;
  }

  public void deleteReservationApRelationship(org.cocktail.superplan.server.metier.ReservationAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "reservationAp");
    editingContext().deleteObject(object);
  }

  public void deleteAllReservationApRelationships() {
    Enumeration objects = reservationAp().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteReservationApRelationship((org.cocktail.superplan.server.metier.ReservationAp)objects.nextElement());
    }
  }

  public NSArray reservationObjets() {
    return (NSArray)storedValueForKey("reservationObjets");
  }

  public NSArray reservationObjets(EOQualifier qualifier) {
    return reservationObjets(qualifier, null, false);
  }

  public NSArray reservationObjets(EOQualifier qualifier, boolean fetch) {
    return reservationObjets(qualifier, null, fetch);
  }

  public NSArray reservationObjets(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ReservationObjet.RESERVATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ReservationObjet.fetchReservationObjets(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = reservationObjets();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToReservationObjetsRelationship(org.cocktail.superplan.server.metier.ReservationObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "reservationObjets");
  }

  public void removeFromReservationObjetsRelationship(org.cocktail.superplan.server.metier.ReservationObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "reservationObjets");
  }

  public org.cocktail.superplan.server.metier.ReservationObjet createReservationObjetsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ReservationObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "reservationObjets");
    return (org.cocktail.superplan.server.metier.ReservationObjet) eo;
  }

  public void deleteReservationObjetsRelationship(org.cocktail.superplan.server.metier.ReservationObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "reservationObjets");
    editingContext().deleteObject(object);
  }

  public void deleteAllReservationObjetsRelationships() {
    Enumeration objects = reservationObjets().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteReservationObjetsRelationship((org.cocktail.superplan.server.metier.ReservationObjet)objects.nextElement());
    }
  }

  public NSArray reservationsSemestres() {
    return (NSArray)storedValueForKey("reservationsSemestres");
  }

  public NSArray reservationsSemestres(EOQualifier qualifier) {
    return reservationsSemestres(qualifier, null, false);
  }

  public NSArray reservationsSemestres(EOQualifier qualifier, boolean fetch) {
    return reservationsSemestres(qualifier, null, fetch);
  }

  public NSArray reservationsSemestres(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ReservationSemestre.RESERVATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ReservationSemestre.fetchReservationSemestres(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = reservationsSemestres();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToReservationsSemestresRelationship(org.cocktail.superplan.server.metier.ReservationSemestre object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "reservationsSemestres");
  }

  public void removeFromReservationsSemestresRelationship(org.cocktail.superplan.server.metier.ReservationSemestre object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "reservationsSemestres");
  }

  public org.cocktail.superplan.server.metier.ReservationSemestre createReservationsSemestresRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ReservationSemestre");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "reservationsSemestres");
    return (org.cocktail.superplan.server.metier.ReservationSemestre) eo;
  }

  public void deleteReservationsSemestresRelationship(org.cocktail.superplan.server.metier.ReservationSemestre object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "reservationsSemestres");
    editingContext().deleteObject(object);
  }

  public void deleteAllReservationsSemestresRelationships() {
    Enumeration objects = reservationsSemestres().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteReservationsSemestresRelationship((org.cocktail.superplan.server.metier.ReservationSemestre)objects.nextElement());
    }
  }

  public NSArray sallesSouhaitees() {
    return (NSArray)storedValueForKey("sallesSouhaitees");
  }

  public NSArray sallesSouhaitees(EOQualifier qualifier) {
    return sallesSouhaitees(qualifier, null, false);
  }

  public NSArray sallesSouhaitees(EOQualifier qualifier, boolean fetch) {
    return sallesSouhaitees(qualifier, null, fetch);
  }

  public NSArray sallesSouhaitees(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.SalleSouhaitee.RESERVATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.SalleSouhaitee.fetchSalleSouhaitees(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = sallesSouhaitees();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToSallesSouhaiteesRelationship(org.cocktail.superplan.server.metier.SalleSouhaitee object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "sallesSouhaitees");
  }

  public void removeFromSallesSouhaiteesRelationship(org.cocktail.superplan.server.metier.SalleSouhaitee object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "sallesSouhaitees");
  }

  public org.cocktail.superplan.server.metier.SalleSouhaitee createSallesSouhaiteesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("SalleSouhaitee");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "sallesSouhaitees");
    return (org.cocktail.superplan.server.metier.SalleSouhaitee) eo;
  }

  public void deleteSallesSouhaiteesRelationship(org.cocktail.superplan.server.metier.SalleSouhaitee object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "sallesSouhaitees");
    editingContext().deleteObject(object);
  }

  public void deleteAllSallesSouhaiteesRelationships() {
    Enumeration objects = sallesSouhaitees().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSallesSouhaiteesRelationship((org.cocktail.superplan.server.metier.SalleSouhaitee)objects.nextElement());
    }
  }


  public static Reservation createReservation(EOEditingContext editingContext, NSTimestamp dCreation
, NSTimestamp dModification
, String tlocCode
) {
    Reservation eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Reservation.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Reservation.ENTITY_NAME + "' !");
    } else
    {
        eo = (Reservation) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDCreation(dCreation);
		eo.setDModification(dModification);
		eo.setTlocCode(tlocCode);
    return eo;
  }

  public static NSArray fetchAllReservations(EOEditingContext editingContext) {
    return _Reservation.fetchAllReservations(editingContext, null);
  }

  public static NSArray fetchAllReservations(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Reservation.fetchReservations(editingContext, null, sortOrderings);
  }

  public static NSArray fetchReservations(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Reservation.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Reservation fetchReservation(EOEditingContext editingContext, String keyName, Object value) {
    return _Reservation.fetchReservation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Reservation fetchReservation(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Reservation.fetchReservations(editingContext, qualifier, null);
    Reservation eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Reservation)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Reservation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Reservation fetchRequiredReservation(EOEditingContext editingContext, String keyName, Object value) {
    return _Reservation.fetchRequiredReservation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Reservation fetchRequiredReservation(EOEditingContext editingContext, EOQualifier qualifier) {
    Reservation eoObject = _Reservation.fetchReservation(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Reservation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Reservation localInstanceIn(EOEditingContext editingContext, Reservation eo) {
    Reservation localInstance = (eo == null) ? null : (Reservation)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
