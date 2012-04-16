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

// DO NOT EDIT.  Make changes to SalleSouhaitee.java instead.
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

public abstract class _SalleSouhaitee extends  EOGenericRecord {
	public static final String ENTITY_NAME = "SalleSouhaitee";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.SALLE_SOUHAITEE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "salSouKey";

	public static final String SOU_POSITION_KEY = "souPosition";

	public static final String SOU_POSITION_COLKEY = "SOU_POSITION";

	// Relationships
	public static final String RESERVATION_KEY = "reservation";
	public static final String SALLE_KEY = "salle";

	// Utilities methods
	  public SalleSouhaitee localInstanceIn(EOEditingContext editingContext) {
	    SalleSouhaitee localInstance = (SalleSouhaitee)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static SalleSouhaitee getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_SalleSouhaitee.ENTITY_NAME);
		      return (SalleSouhaitee) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer souPosition() {
    return (Integer) storedValueForKey("souPosition");
  }

  public void setSouPosition(Integer value) {
    takeStoredValueForKey(value, "souPosition");
  }

  public org.cocktail.superplan.server.metier.Reservation reservation() {
    return (org.cocktail.superplan.server.metier.Reservation)storedValueForKey("reservation");
  }

  public void setReservationRelationship(org.cocktail.superplan.server.metier.Reservation value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.Reservation oldValue = reservation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "reservation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "reservation");
    }
  }
  
  public org.cocktail.superplan.server.metier.Salles salle() {
    return (org.cocktail.superplan.server.metier.Salles)storedValueForKey("salle");
  }

  public void setSalleRelationship(org.cocktail.superplan.server.metier.Salles value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.Salles oldValue = salle();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "salle");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "salle");
    }
  }
  

  public static SalleSouhaitee createSalleSouhaitee(EOEditingContext editingContext) {
    SalleSouhaitee eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_SalleSouhaitee.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _SalleSouhaitee.ENTITY_NAME + "' !");
    } else
    {
        eo = (SalleSouhaitee) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllSalleSouhaitees(EOEditingContext editingContext) {
    return _SalleSouhaitee.fetchAllSalleSouhaitees(editingContext, null);
  }

  public static NSArray fetchAllSalleSouhaitees(EOEditingContext editingContext, NSArray sortOrderings) {
    return _SalleSouhaitee.fetchSalleSouhaitees(editingContext, null, sortOrderings);
  }

  public static NSArray fetchSalleSouhaitees(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_SalleSouhaitee.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static SalleSouhaitee fetchSalleSouhaitee(EOEditingContext editingContext, String keyName, Object value) {
    return _SalleSouhaitee.fetchSalleSouhaitee(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static SalleSouhaitee fetchSalleSouhaitee(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _SalleSouhaitee.fetchSalleSouhaitees(editingContext, qualifier, null);
    SalleSouhaitee eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (SalleSouhaitee)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SalleSouhaitee that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static SalleSouhaitee fetchRequiredSalleSouhaitee(EOEditingContext editingContext, String keyName, Object value) {
    return _SalleSouhaitee.fetchRequiredSalleSouhaitee(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static SalleSouhaitee fetchRequiredSalleSouhaitee(EOEditingContext editingContext, EOQualifier qualifier) {
    SalleSouhaitee eoObject = _SalleSouhaitee.fetchSalleSouhaitee(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SalleSouhaitee that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static SalleSouhaitee localInstanceIn(EOEditingContext editingContext, SalleSouhaitee eo) {
    SalleSouhaitee localInstance = (eo == null) ? null : (SalleSouhaitee)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
