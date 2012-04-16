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

// DO NOT EDIT.  Make changes to ContrainteJour.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _ContrainteJour extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ContrainteJour";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CONTRAINTE_JOUR";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "ctjKey";

	public static final String CTJ_DATE_KEY = "ctjDate";
	public static final String CTJ_NO_JOUR_KEY = "ctjNoJour";

	public static final String CTJ_DATE_COLKEY = "CTJ_DATE";
	public static final String CTJ_NO_JOUR_COLKEY = "CTJ_NO_JOUR";

	// Relationships
	public static final String CONTRAINTE_HEURES_KEY = "contrainteHeures";
	public static final String CONTRAINTE_SEMAINE_KEY = "contrainteSemaine";

	// Utilities methods
	  public ContrainteJour localInstanceIn(EOEditingContext editingContext) {
	    ContrainteJour localInstance = (ContrainteJour)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ContrainteJour getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ContrainteJour.ENTITY_NAME);
		      return (ContrainteJour) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp ctjDate() {
    return (NSTimestamp) storedValueForKey("ctjDate");
  }

  public void setCtjDate(NSTimestamp value) {
    takeStoredValueForKey(value, "ctjDate");
  }

  public Integer ctjNoJour() {
    return (Integer) storedValueForKey("ctjNoJour");
  }

  public void setCtjNoJour(Integer value) {
    takeStoredValueForKey(value, "ctjNoJour");
  }

  public org.cocktail.superplan.client.metier.ContrainteSemaine contrainteSemaine() {
    return (org.cocktail.superplan.client.metier.ContrainteSemaine)storedValueForKey("contrainteSemaine");
  }

  public void setContrainteSemaineRelationship(org.cocktail.superplan.client.metier.ContrainteSemaine value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ContrainteSemaine oldValue = contrainteSemaine();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "contrainteSemaine");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "contrainteSemaine");
    }
  }
  
  public NSArray contrainteHeures() {
    return (NSArray)storedValueForKey("contrainteHeures");
  }

  public NSArray contrainteHeures(EOQualifier qualifier) {
    return contrainteHeures(qualifier, null, false);
  }

  public NSArray contrainteHeures(EOQualifier qualifier, boolean fetch) {
    return contrainteHeures(qualifier, null, fetch);
  }

  public NSArray contrainteHeures(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ContrainteHeure.CONTRAINTE_JOUR_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ContrainteHeure.fetchContrainteHeures(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = contrainteHeures();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToContrainteHeuresRelationship(org.cocktail.superplan.client.metier.ContrainteHeure object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "contrainteHeures");
  }

  public void removeFromContrainteHeuresRelationship(org.cocktail.superplan.client.metier.ContrainteHeure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "contrainteHeures");
  }

  public org.cocktail.superplan.client.metier.ContrainteHeure createContrainteHeuresRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ContrainteHeure");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "contrainteHeures");
    return (org.cocktail.superplan.client.metier.ContrainteHeure) eo;
  }

  public void deleteContrainteHeuresRelationship(org.cocktail.superplan.client.metier.ContrainteHeure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "contrainteHeures");
    editingContext().deleteObject(object);
  }

  public void deleteAllContrainteHeuresRelationships() {
    Enumeration objects = contrainteHeures().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteContrainteHeuresRelationship((org.cocktail.superplan.client.metier.ContrainteHeure)objects.nextElement());
    }
  }


  public static ContrainteJour createContrainteJour(EOEditingContext editingContext, NSTimestamp ctjDate
, Integer ctjNoJour
, org.cocktail.superplan.client.metier.ContrainteSemaine contrainteSemaine) {
    ContrainteJour eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ContrainteJour.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ContrainteJour.ENTITY_NAME + "' !");
    } else
    {
        eo = (ContrainteJour) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCtjDate(ctjDate);
		eo.setCtjNoJour(ctjNoJour);
    eo.setContrainteSemaineRelationship(contrainteSemaine);
    return eo;
  }

  public static NSArray fetchAllContrainteJours(EOEditingContext editingContext) {
    return _ContrainteJour.fetchAllContrainteJours(editingContext, null);
  }

  public static NSArray fetchAllContrainteJours(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ContrainteJour.fetchContrainteJours(editingContext, null, sortOrderings);
  }

  public static NSArray fetchContrainteJours(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ContrainteJour.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ContrainteJour fetchContrainteJour(EOEditingContext editingContext, String keyName, Object value) {
    return _ContrainteJour.fetchContrainteJour(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ContrainteJour fetchContrainteJour(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ContrainteJour.fetchContrainteJours(editingContext, qualifier, null);
    ContrainteJour eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ContrainteJour)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ContrainteJour that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ContrainteJour fetchRequiredContrainteJour(EOEditingContext editingContext, String keyName, Object value) {
    return _ContrainteJour.fetchRequiredContrainteJour(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ContrainteJour fetchRequiredContrainteJour(EOEditingContext editingContext, EOQualifier qualifier) {
    ContrainteJour eoObject = _ContrainteJour.fetchContrainteJour(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ContrainteJour that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ContrainteJour localInstanceIn(EOEditingContext editingContext, ContrainteJour eo) {
    ContrainteJour localInstance = (eo == null) ? null : (ContrainteJour)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
