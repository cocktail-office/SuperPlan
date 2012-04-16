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

// DO NOT EDIT.  Make changes to HcompRec.java instead.
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

public abstract class _HcompRec extends  EOGenericRecord {
	public static final String ENTITY_NAME = "HcompRec";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.HCOMP_REC";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "hcompKey";

	public static final String DATE_RESA_KEY = "dateResa";
	public static final String HCR_TAG_KEY = "hcrTag";

	public static final String DATE_RESA_COLKEY = "DATE_RESA";
	public static final String HCR_TAG_COLKEY = "HCR_TAG";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String RESERVATION_KEY = "reservation";

	// Utilities methods
	  public HcompRec localInstanceIn(EOEditingContext editingContext) {
	    HcompRec localInstance = (HcompRec)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static HcompRec getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_HcompRec.ENTITY_NAME);
		      return (HcompRec) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp dateResa() {
    return (NSTimestamp) storedValueForKey("dateResa");
  }

  public void setDateResa(NSTimestamp value) {
    takeStoredValueForKey(value, "dateResa");
  }

  public Integer hcrTag() {
    return (Integer) storedValueForKey("hcrTag");
  }

  public void setHcrTag(Integer value) {
    takeStoredValueForKey(value, "hcrTag");
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
  

  public static HcompRec createHcompRec(EOEditingContext editingContext, NSTimestamp dateResa
, Integer hcrTag
) {
    HcompRec eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_HcompRec.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _HcompRec.ENTITY_NAME + "' !");
    } else
    {
        eo = (HcompRec) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDateResa(dateResa);
		eo.setHcrTag(hcrTag);
    return eo;
  }

  public static NSArray fetchAllHcompRecs(EOEditingContext editingContext) {
    return _HcompRec.fetchAllHcompRecs(editingContext, null);
  }

  public static NSArray fetchAllHcompRecs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _HcompRec.fetchHcompRecs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchHcompRecs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_HcompRec.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static HcompRec fetchHcompRec(EOEditingContext editingContext, String keyName, Object value) {
    return _HcompRec.fetchHcompRec(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static HcompRec fetchHcompRec(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _HcompRec.fetchHcompRecs(editingContext, qualifier, null);
    HcompRec eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (HcompRec)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one HcompRec that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static HcompRec fetchRequiredHcompRec(EOEditingContext editingContext, String keyName, Object value) {
    return _HcompRec.fetchRequiredHcompRec(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static HcompRec fetchRequiredHcompRec(EOEditingContext editingContext, EOQualifier qualifier) {
    HcompRec eoObject = _HcompRec.fetchHcompRec(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no HcompRec that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static HcompRec localInstanceIn(EOEditingContext editingContext, HcompRec eo) {
    HcompRec localInstance = (eo == null) ? null : (HcompRec)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
