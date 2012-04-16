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

// DO NOT EDIT.  Make changes to LotSalle.java instead.
package org.cocktail.superplan.client.metier;

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

public abstract class _LotSalle extends  EOGenericRecord {
	public static final String ENTITY_NAME = "LotSalle";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.LOT_SALLE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "lotKey";

	public static final String LOT_LIBELLE_KEY = "lotLibelle";

	public static final String LOT_LIBELLE_COLKEY = "LOT_LIBELLE";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String REPART_LOT_INDIVIDUS_KEY = "repartLotIndividus";
	public static final String REPART_LOT_SALLES_KEY = "repartLotSalles";

	// Utilities methods
	  public LotSalle localInstanceIn(EOEditingContext editingContext) {
	    LotSalle localInstance = (LotSalle)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static LotSalle getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_LotSalle.ENTITY_NAME);
		      return (LotSalle) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String lotLibelle() {
    return (String) storedValueForKey("lotLibelle");
  }

  public void setLotLibelle(String value) {
    takeStoredValueForKey(value, "lotLibelle");
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
  
  public NSArray repartLotIndividus() {
    return (NSArray)storedValueForKey("repartLotIndividus");
  }

  public NSArray repartLotIndividus(EOQualifier qualifier) {
    return repartLotIndividus(qualifier, null, false);
  }

  public NSArray repartLotIndividus(EOQualifier qualifier, boolean fetch) {
    return repartLotIndividus(qualifier, null, fetch);
  }

  public NSArray repartLotIndividus(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.RepartLotIndividu.LOT_SALLE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.RepartLotIndividu.fetchRepartLotIndividus(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = repartLotIndividus();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRepartLotIndividusRelationship(org.cocktail.superplan.client.metier.RepartLotIndividu object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartLotIndividus");
  }

  public void removeFromRepartLotIndividusRelationship(org.cocktail.superplan.client.metier.RepartLotIndividu object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartLotIndividus");
  }

  public org.cocktail.superplan.client.metier.RepartLotIndividu createRepartLotIndividusRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartLotIndividu");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartLotIndividus");
    return (org.cocktail.superplan.client.metier.RepartLotIndividu) eo;
  }

  public void deleteRepartLotIndividusRelationship(org.cocktail.superplan.client.metier.RepartLotIndividu object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartLotIndividus");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartLotIndividusRelationships() {
    Enumeration objects = repartLotIndividus().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartLotIndividusRelationship((org.cocktail.superplan.client.metier.RepartLotIndividu)objects.nextElement());
    }
  }

  public NSArray repartLotSalles() {
    return (NSArray)storedValueForKey("repartLotSalles");
  }

  public NSArray repartLotSalles(EOQualifier qualifier) {
    return repartLotSalles(qualifier, null, false);
  }

  public NSArray repartLotSalles(EOQualifier qualifier, boolean fetch) {
    return repartLotSalles(qualifier, null, fetch);
  }

  public NSArray repartLotSalles(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.RepartLotSalle.LOT_SALLE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.RepartLotSalle.fetchRepartLotSalles(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = repartLotSalles();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRepartLotSallesRelationship(org.cocktail.superplan.client.metier.RepartLotSalle object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartLotSalles");
  }

  public void removeFromRepartLotSallesRelationship(org.cocktail.superplan.client.metier.RepartLotSalle object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartLotSalles");
  }

  public org.cocktail.superplan.client.metier.RepartLotSalle createRepartLotSallesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartLotSalle");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartLotSalles");
    return (org.cocktail.superplan.client.metier.RepartLotSalle) eo;
  }

  public void deleteRepartLotSallesRelationship(org.cocktail.superplan.client.metier.RepartLotSalle object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartLotSalles");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartLotSallesRelationships() {
    Enumeration objects = repartLotSalles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartLotSallesRelationship((org.cocktail.superplan.client.metier.RepartLotSalle)objects.nextElement());
    }
  }


  public static LotSalle createLotSalle(EOEditingContext editingContext, String lotLibelle
) {
    LotSalle eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_LotSalle.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _LotSalle.ENTITY_NAME + "' !");
    } else
    {
        eo = (LotSalle) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLotLibelle(lotLibelle);
    return eo;
  }

  public static NSArray fetchAllLotSalles(EOEditingContext editingContext) {
    return _LotSalle.fetchAllLotSalles(editingContext, null);
  }

  public static NSArray fetchAllLotSalles(EOEditingContext editingContext, NSArray sortOrderings) {
    return _LotSalle.fetchLotSalles(editingContext, null, sortOrderings);
  }

  public static NSArray fetchLotSalles(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_LotSalle.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static LotSalle fetchLotSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _LotSalle.fetchLotSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LotSalle fetchLotSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _LotSalle.fetchLotSalles(editingContext, qualifier, null);
    LotSalle eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (LotSalle)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one LotSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LotSalle fetchRequiredLotSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _LotSalle.fetchRequiredLotSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static LotSalle fetchRequiredLotSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    LotSalle eoObject = _LotSalle.fetchLotSalle(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no LotSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static LotSalle localInstanceIn(EOEditingContext editingContext, LotSalle eo) {
    LotSalle localInstance = (eo == null) ? null : (LotSalle)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
