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

// DO NOT EDIT.  Make changes to ReservationObjet.java instead.
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

public abstract class _ReservationObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ReservationObjet";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESERVATION_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "resaObjetKey";

	public static final String DATE_VALIDATION_KEY = "dateValidation";
	public static final String RESA_ETAT_KEY = "resaEtat";

	public static final String DATE_VALIDATION_COLKEY = "DATE_VALIDATION";
	public static final String RESA_ETAT_COLKEY = "RESA_ETAT";

	// Relationships
	public static final String INDIVIDU_VALIDEUR_KEY = "individuValideur";
	public static final String RESA_OBJET_KEY = "resaObjet";
	public static final String RESERVATION_KEY = "reservation";

	// Utilities methods
	  public ReservationObjet localInstanceIn(EOEditingContext editingContext) {
	    ReservationObjet localInstance = (ReservationObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ReservationObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ReservationObjet.ENTITY_NAME);
		      return (ReservationObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp dateValidation() {
    return (NSTimestamp) storedValueForKey("dateValidation");
  }

  public void setDateValidation(NSTimestamp value) {
    takeStoredValueForKey(value, "dateValidation");
  }

  public String resaEtat() {
    return (String) storedValueForKey("resaEtat");
  }

  public void setResaEtat(String value) {
    takeStoredValueForKey(value, "resaEtat");
  }

  public org.cocktail.superplan.client.metier.IndividuUlr individuValideur() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individuValideur");
  }

  public void setIndividuValideurRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individuValideur();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuValideur");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuValideur");
    }
  }
  
  public org.cocktail.superplan.client.metier.ResaObjet resaObjet() {
    return (org.cocktail.superplan.client.metier.ResaObjet)storedValueForKey("resaObjet");
  }

  public void setResaObjetRelationship(org.cocktail.superplan.client.metier.ResaObjet value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ResaObjet oldValue = resaObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "resaObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "resaObjet");
    }
  }
  
  public org.cocktail.superplan.client.metier.Reservation reservation() {
    return (org.cocktail.superplan.client.metier.Reservation)storedValueForKey("reservation");
  }

  public void setReservationRelationship(org.cocktail.superplan.client.metier.Reservation value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Reservation oldValue = reservation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "reservation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "reservation");
    }
  }
  

  public static ReservationObjet createReservationObjet(EOEditingContext editingContext, String resaEtat
) {
    ReservationObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ReservationObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ReservationObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (ReservationObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setResaEtat(resaEtat);
    return eo;
  }

  public static NSArray fetchAllReservationObjets(EOEditingContext editingContext) {
    return _ReservationObjet.fetchAllReservationObjets(editingContext, null);
  }

  public static NSArray fetchAllReservationObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ReservationObjet.fetchReservationObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchReservationObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ReservationObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ReservationObjet fetchReservationObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _ReservationObjet.fetchReservationObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ReservationObjet fetchReservationObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ReservationObjet.fetchReservationObjets(editingContext, qualifier, null);
    ReservationObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ReservationObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ReservationObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ReservationObjet fetchRequiredReservationObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _ReservationObjet.fetchRequiredReservationObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ReservationObjet fetchRequiredReservationObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    ReservationObjet eoObject = _ReservationObjet.fetchReservationObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ReservationObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ReservationObjet localInstanceIn(EOEditingContext editingContext, ReservationObjet eo) {
    ReservationObjet localInstance = (eo == null) ? null : (ReservationObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
