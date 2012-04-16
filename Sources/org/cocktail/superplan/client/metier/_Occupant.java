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

// DO NOT EDIT.  Make changes to Occupant.java instead.
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

public abstract class _Occupant extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Occupant";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.OCCUPANT";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "occKey";

	public static final String HCOMP_REC_KEY = "hcompRec";

	public static final String HCOMP_REC_COLKEY = "HCOMP_REC";

	// Relationships
	public static final String INDIVIDU_KEY = "individu";
	public static final String RESERVATION_KEY = "reservation";

	// Utilities methods
	  public Occupant localInstanceIn(EOEditingContext editingContext) {
	    Occupant localInstance = (Occupant)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Occupant getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Occupant.ENTITY_NAME);
		      return (Occupant) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer hcompRec() {
    return (Integer) storedValueForKey("hcompRec");
  }

  public void setHcompRec(Integer value) {
    takeStoredValueForKey(value, "hcompRec");
  }

  public org.cocktail.superplan.client.metier.IndividuUlr individu() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individu");
  }

  public void setIndividuRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individu");
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
  

  public static Occupant createOccupant(EOEditingContext editingContext, Integer hcompRec
, org.cocktail.superplan.client.metier.IndividuUlr individu) {
    Occupant eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Occupant.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Occupant.ENTITY_NAME + "' !");
    } else
    {
        eo = (Occupant) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setHcompRec(hcompRec);
    eo.setIndividuRelationship(individu);
    return eo;
  }

  public static NSArray fetchAllOccupants(EOEditingContext editingContext) {
    return _Occupant.fetchAllOccupants(editingContext, null);
  }

  public static NSArray fetchAllOccupants(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Occupant.fetchOccupants(editingContext, null, sortOrderings);
  }

  public static NSArray fetchOccupants(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Occupant.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Occupant fetchOccupant(EOEditingContext editingContext, String keyName, Object value) {
    return _Occupant.fetchOccupant(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Occupant fetchOccupant(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Occupant.fetchOccupants(editingContext, qualifier, null);
    Occupant eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Occupant)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Occupant that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Occupant fetchRequiredOccupant(EOEditingContext editingContext, String keyName, Object value) {
    return _Occupant.fetchRequiredOccupant(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Occupant fetchRequiredOccupant(EOEditingContext editingContext, EOQualifier qualifier) {
    Occupant eoObject = _Occupant.fetchOccupant(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Occupant that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Occupant localInstanceIn(EOEditingContext editingContext, Occupant eo) {
    Occupant localInstance = (eo == null) ? null : (Occupant)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
