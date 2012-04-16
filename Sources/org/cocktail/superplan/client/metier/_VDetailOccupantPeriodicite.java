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

// DO NOT EDIT.  Make changes to VDetailOccupantPeriodicite.java instead.
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

public abstract class _VDetailOccupantPeriodicite extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VDetailOccupantPeriodicite";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_DETAIL_OCCUPANT_PERIODICITE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "resaKey";

	public static final String DATE_DEB_KEY = "dateDeb";
	public static final String DATE_FIN_KEY = "dateFin";
	public static final String MAP_KEY_KEY = "mapKey";
	public static final String TLOC_CODE_KEY = "tlocCode";

	public static final String DATE_DEB_COLKEY = "DATE_DEB";
	public static final String DATE_FIN_COLKEY = "DATE_FIN";
	public static final String MAP_KEY_COLKEY = "MAP_KEY";
	public static final String TLOC_CODE_COLKEY = "TLOC_CODE";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String RESERVATION_KEY = "reservation";

	// Utilities methods
	  public VDetailOccupantPeriodicite localInstanceIn(EOEditingContext editingContext) {
	    VDetailOccupantPeriodicite localInstance = (VDetailOccupantPeriodicite)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VDetailOccupantPeriodicite getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VDetailOccupantPeriodicite.ENTITY_NAME);
		      return (VDetailOccupantPeriodicite) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp dateDeb() {
    return (NSTimestamp) storedValueForKey("dateDeb");
  }

  public void setDateDeb(NSTimestamp value) {
    takeStoredValueForKey(value, "dateDeb");
  }

  public NSTimestamp dateFin() {
    return (NSTimestamp) storedValueForKey("dateFin");
  }

  public void setDateFin(NSTimestamp value) {
    takeStoredValueForKey(value, "dateFin");
  }

  public Integer mapKey() {
    return (Integer) storedValueForKey("mapKey");
  }

  public void setMapKey(Integer value) {
    takeStoredValueForKey(value, "mapKey");
  }

  public String tlocCode() {
    return (String) storedValueForKey("tlocCode");
  }

  public void setTlocCode(String value) {
    takeStoredValueForKey(value, "tlocCode");
  }

  public org.cocktail.superplan.client.metier.IndividuUlr individuUlr() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individuUlr");
  }

  public void setIndividuUlrRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individuUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuUlr");
    }
  }
  
  public org.cocktail.superplan.client.metier.MaquetteAp maquetteAp() {
    return (org.cocktail.superplan.client.metier.MaquetteAp)storedValueForKey("maquetteAp");
  }

  public void setMaquetteApRelationship(org.cocktail.superplan.client.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteAp oldValue = maquetteAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteAp");
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
  

  public static VDetailOccupantPeriodicite createVDetailOccupantPeriodicite(EOEditingContext editingContext, NSTimestamp dateDeb
, NSTimestamp dateFin
, Integer mapKey
, String tlocCode
) {
    VDetailOccupantPeriodicite eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VDetailOccupantPeriodicite.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VDetailOccupantPeriodicite.ENTITY_NAME + "' !");
    } else
    {
        eo = (VDetailOccupantPeriodicite) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDateDeb(dateDeb);
		eo.setDateFin(dateFin);
		eo.setMapKey(mapKey);
		eo.setTlocCode(tlocCode);
    return eo;
  }

  public static NSArray fetchAllVDetailOccupantPeriodicites(EOEditingContext editingContext) {
    return _VDetailOccupantPeriodicite.fetchAllVDetailOccupantPeriodicites(editingContext, null);
  }

  public static NSArray fetchAllVDetailOccupantPeriodicites(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VDetailOccupantPeriodicite.fetchVDetailOccupantPeriodicites(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVDetailOccupantPeriodicites(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VDetailOccupantPeriodicite.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VDetailOccupantPeriodicite fetchVDetailOccupantPeriodicite(EOEditingContext editingContext, String keyName, Object value) {
    return _VDetailOccupantPeriodicite.fetchVDetailOccupantPeriodicite(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VDetailOccupantPeriodicite fetchVDetailOccupantPeriodicite(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VDetailOccupantPeriodicite.fetchVDetailOccupantPeriodicites(editingContext, qualifier, null);
    VDetailOccupantPeriodicite eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VDetailOccupantPeriodicite)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VDetailOccupantPeriodicite that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VDetailOccupantPeriodicite fetchRequiredVDetailOccupantPeriodicite(EOEditingContext editingContext, String keyName, Object value) {
    return _VDetailOccupantPeriodicite.fetchRequiredVDetailOccupantPeriodicite(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VDetailOccupantPeriodicite fetchRequiredVDetailOccupantPeriodicite(EOEditingContext editingContext, EOQualifier qualifier) {
    VDetailOccupantPeriodicite eoObject = _VDetailOccupantPeriodicite.fetchVDetailOccupantPeriodicite(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VDetailOccupantPeriodicite that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VDetailOccupantPeriodicite localInstanceIn(EOEditingContext editingContext, VDetailOccupantPeriodicite eo) {
    VDetailOccupantPeriodicite localInstance = (eo == null) ? null : (VDetailOccupantPeriodicite)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
