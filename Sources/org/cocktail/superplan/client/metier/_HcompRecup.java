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

// DO NOT EDIT.  Make changes to HcompRecup.java instead.
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

public abstract class _HcompRecup extends  EOGenericRecord {
	public static final String ENTITY_NAME = "HcompRecup";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.HCOMP_RECUP";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "hcompKey";

	public static final String HCR_TAG_KEY = "hcrTag";

	public static final String HCR_TAG_COLKEY = "HCR_TAG";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String PERIODICITE_KEY = "periodicite";
	public static final String RESERVATION_KEY = "reservation";

	// Utilities methods
	  public HcompRecup localInstanceIn(EOEditingContext editingContext) {
	    HcompRecup localInstance = (HcompRecup)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static HcompRecup getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_HcompRecup.ENTITY_NAME);
		      return (HcompRecup) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
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
  
  public org.cocktail.superplan.client.metier.Periodicite periodicite() {
    return (org.cocktail.superplan.client.metier.Periodicite)storedValueForKey("periodicite");
  }

  public void setPeriodiciteRelationship(org.cocktail.superplan.client.metier.Periodicite value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Periodicite oldValue = periodicite();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "periodicite");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "periodicite");
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
  

  public static HcompRecup createHcompRecup(EOEditingContext editingContext, Integer hcrTag
) {
    HcompRecup eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_HcompRecup.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _HcompRecup.ENTITY_NAME + "' !");
    } else
    {
        eo = (HcompRecup) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setHcrTag(hcrTag);
    return eo;
  }

  public static NSArray fetchAllHcompRecups(EOEditingContext editingContext) {
    return _HcompRecup.fetchAllHcompRecups(editingContext, null);
  }

  public static NSArray fetchAllHcompRecups(EOEditingContext editingContext, NSArray sortOrderings) {
    return _HcompRecup.fetchHcompRecups(editingContext, null, sortOrderings);
  }

  public static NSArray fetchHcompRecups(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_HcompRecup.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static HcompRecup fetchHcompRecup(EOEditingContext editingContext, String keyName, Object value) {
    return _HcompRecup.fetchHcompRecup(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static HcompRecup fetchHcompRecup(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _HcompRecup.fetchHcompRecups(editingContext, qualifier, null);
    HcompRecup eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (HcompRecup)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one HcompRecup that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static HcompRecup fetchRequiredHcompRecup(EOEditingContext editingContext, String keyName, Object value) {
    return _HcompRecup.fetchRequiredHcompRecup(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static HcompRecup fetchRequiredHcompRecup(EOEditingContext editingContext, EOQualifier qualifier) {
    HcompRecup eoObject = _HcompRecup.fetchHcompRecup(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no HcompRecup that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static HcompRecup localInstanceIn(EOEditingContext editingContext, HcompRecup eo) {
    HcompRecup localInstance = (eo == null) ? null : (HcompRecup)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
