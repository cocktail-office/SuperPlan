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

// DO NOT EDIT.  Make changes to ReservationAp.java instead.
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

public abstract class _ReservationAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ReservationAp";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESERVATION_AP";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "resaApKey";

	public static final String GGRP_KEY_KEY = "ggrpKey";
	public static final String HCOMP_REC_KEY = "hcompRec";
	public static final String LE_MAP_KEY_KEY = "leMapKey";

	public static final String GGRP_KEY_COLKEY = "GGRP_KEY";
	public static final String HCOMP_REC_COLKEY = "HCOMP_REC";
	public static final String LE_MAP_KEY_COLKEY = "$attribute.columnName";

	// Relationships
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String RESERVATION_KEY = "reservation";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";
	public static final String V_MAQUETTE_AP_KEY = "vMaquetteAp";

	// Utilities methods
	  public ReservationAp localInstanceIn(EOEditingContext editingContext) {
	    ReservationAp localInstance = (ReservationAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ReservationAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ReservationAp.ENTITY_NAME);
		      return (ReservationAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer ggrpKey() {
    return (Integer) storedValueForKey("ggrpKey");
  }

  public void setGgrpKey(Integer value) {
    takeStoredValueForKey(value, "ggrpKey");
  }

  public Integer hcompRec() {
    return (Integer) storedValueForKey("hcompRec");
  }

  public void setHcompRec(Integer value) {
    takeStoredValueForKey(value, "hcompRec");
  }

  public Integer leMapKey() {
    return (Integer) storedValueForKey("leMapKey");
  }

  public void setLeMapKey(Integer value) {
    takeStoredValueForKey(value, "leMapKey");
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
  
  public org.cocktail.superplan.server.metier.VMaquetteAp vMaquetteAp() {
    return (org.cocktail.superplan.server.metier.VMaquetteAp)storedValueForKey("vMaquetteAp");
  }

  public void setVMaquetteApRelationship(org.cocktail.superplan.server.metier.VMaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.VMaquetteAp oldValue = vMaquetteAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "vMaquetteAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "vMaquetteAp");
    }
  }
  

  public static ReservationAp createReservationAp(EOEditingContext editingContext, Integer hcompRec
) {
    ReservationAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ReservationAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ReservationAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (ReservationAp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setHcompRec(hcompRec);
    return eo;
  }

  public static NSArray fetchAllReservationAps(EOEditingContext editingContext) {
    return _ReservationAp.fetchAllReservationAps(editingContext, null);
  }

  public static NSArray fetchAllReservationAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ReservationAp.fetchReservationAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchReservationAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ReservationAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ReservationAp fetchReservationAp(EOEditingContext editingContext, String keyName, Object value) {
    return _ReservationAp.fetchReservationAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ReservationAp fetchReservationAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ReservationAp.fetchReservationAps(editingContext, qualifier, null);
    ReservationAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ReservationAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ReservationAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ReservationAp fetchRequiredReservationAp(EOEditingContext editingContext, String keyName, Object value) {
    return _ReservationAp.fetchRequiredReservationAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ReservationAp fetchRequiredReservationAp(EOEditingContext editingContext, EOQualifier qualifier) {
    ReservationAp eoObject = _ReservationAp.fetchReservationAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ReservationAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ReservationAp localInstanceIn(EOEditingContext editingContext, ReservationAp eo) {
    ReservationAp localInstance = (eo == null) ? null : (ReservationAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
