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

// DO NOT EDIT.  Make changes to RepartLotIndividu.java instead.
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

public abstract class _RepartLotIndividu extends  EOGenericRecord {
	public static final String ENTITY_NAME = "RepartLotIndividu";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.REPART_LOT_INDIVIDU";

	// Attributes



	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String LOT_SALLE_KEY = "lotSalle";

	// Utilities methods
	  public RepartLotIndividu localInstanceIn(EOEditingContext editingContext) {
	    RepartLotIndividu localInstance = (RepartLotIndividu)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static RepartLotIndividu getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_RepartLotIndividu.ENTITY_NAME);
		      return (RepartLotIndividu) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
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
  
  public org.cocktail.superplan.client.metier.LotSalle lotSalle() {
    return (org.cocktail.superplan.client.metier.LotSalle)storedValueForKey("lotSalle");
  }

  public void setLotSalleRelationship(org.cocktail.superplan.client.metier.LotSalle value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.LotSalle oldValue = lotSalle();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "lotSalle");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "lotSalle");
    }
  }
  

  public static RepartLotIndividu createRepartLotIndividu(EOEditingContext editingContext, org.cocktail.superplan.client.metier.IndividuUlr individuUlr, org.cocktail.superplan.client.metier.LotSalle lotSalle) {
    RepartLotIndividu eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_RepartLotIndividu.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _RepartLotIndividu.ENTITY_NAME + "' !");
    } else
    {
        eo = (RepartLotIndividu) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setIndividuUlrRelationship(individuUlr);
    eo.setLotSalleRelationship(lotSalle);
    return eo;
  }

  public static NSArray fetchAllRepartLotIndividus(EOEditingContext editingContext) {
    return _RepartLotIndividu.fetchAllRepartLotIndividus(editingContext, null);
  }

  public static NSArray fetchAllRepartLotIndividus(EOEditingContext editingContext, NSArray sortOrderings) {
    return _RepartLotIndividu.fetchRepartLotIndividus(editingContext, null, sortOrderings);
  }

  public static NSArray fetchRepartLotIndividus(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_RepartLotIndividu.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static RepartLotIndividu fetchRepartLotIndividu(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartLotIndividu.fetchRepartLotIndividu(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartLotIndividu fetchRepartLotIndividu(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _RepartLotIndividu.fetchRepartLotIndividus(editingContext, qualifier, null);
    RepartLotIndividu eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (RepartLotIndividu)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one RepartLotIndividu that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartLotIndividu fetchRequiredRepartLotIndividu(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartLotIndividu.fetchRequiredRepartLotIndividu(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartLotIndividu fetchRequiredRepartLotIndividu(EOEditingContext editingContext, EOQualifier qualifier) {
    RepartLotIndividu eoObject = _RepartLotIndividu.fetchRepartLotIndividu(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no RepartLotIndividu that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartLotIndividu localInstanceIn(EOEditingContext editingContext, RepartLotIndividu eo) {
    RepartLotIndividu localInstance = (eo == null) ? null : (RepartLotIndividu)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
