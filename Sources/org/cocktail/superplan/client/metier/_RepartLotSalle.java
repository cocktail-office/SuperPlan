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

// DO NOT EDIT.  Make changes to RepartLotSalle.java instead.
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

public abstract class _RepartLotSalle extends  EOGenericRecord {
	public static final String ENTITY_NAME = "RepartLotSalle";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.REPART_LOT_SALLE";

	// Attributes



	// Relationships
	public static final String LOT_SALLE_KEY = "lotSalle";
	public static final String SALLE_KEY = "salle";

	// Utilities methods
	  public RepartLotSalle localInstanceIn(EOEditingContext editingContext) {
	    RepartLotSalle localInstance = (RepartLotSalle)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static RepartLotSalle getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_RepartLotSalle.ENTITY_NAME);
		      return (RepartLotSalle) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
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
  
  public org.cocktail.superplan.client.metier.Salles salle() {
    return (org.cocktail.superplan.client.metier.Salles)storedValueForKey("salle");
  }

  public void setSalleRelationship(org.cocktail.superplan.client.metier.Salles value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Salles oldValue = salle();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "salle");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "salle");
    }
  }
  

  public static RepartLotSalle createRepartLotSalle(EOEditingContext editingContext) {
    RepartLotSalle eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_RepartLotSalle.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _RepartLotSalle.ENTITY_NAME + "' !");
    } else
    {
        eo = (RepartLotSalle) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllRepartLotSalles(EOEditingContext editingContext) {
    return _RepartLotSalle.fetchAllRepartLotSalles(editingContext, null);
  }

  public static NSArray fetchAllRepartLotSalles(EOEditingContext editingContext, NSArray sortOrderings) {
    return _RepartLotSalle.fetchRepartLotSalles(editingContext, null, sortOrderings);
  }

  public static NSArray fetchRepartLotSalles(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_RepartLotSalle.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static RepartLotSalle fetchRepartLotSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartLotSalle.fetchRepartLotSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartLotSalle fetchRepartLotSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _RepartLotSalle.fetchRepartLotSalles(editingContext, qualifier, null);
    RepartLotSalle eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (RepartLotSalle)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one RepartLotSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartLotSalle fetchRequiredRepartLotSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartLotSalle.fetchRequiredRepartLotSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartLotSalle fetchRequiredRepartLotSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    RepartLotSalle eoObject = _RepartLotSalle.fetchRepartLotSalle(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no RepartLotSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartLotSalle localInstanceIn(EOEditingContext editingContext, RepartLotSalle eo) {
    RepartLotSalle localInstance = (eo == null) ? null : (RepartLotSalle)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
