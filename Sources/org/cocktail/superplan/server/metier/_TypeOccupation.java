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

// DO NOT EDIT.  Make changes to TypeOccupation.java instead.
package org.cocktail.superplan.server.metier;

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

public abstract class _TypeOccupation extends  EOGenericRecord {
	public static final String ENTITY_NAME = "TypeOccupation";
	public static final String ENTITY_TABLE_NAME = "GRHUM.TYPE_OCCUPATION";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "tocOrdre";

	public static final String TOC_LIBELLE_KEY = "tocLibelle";

	public static final String TOC_LIBELLE_COLKEY = "TOC_LIBELLE";

	// Relationships
	public static final String TO_DETAIL_POURCENTAGES_KEY = "toDetailPourcentages";

	// Utilities methods
	  public TypeOccupation localInstanceIn(EOEditingContext editingContext) {
	    TypeOccupation localInstance = (TypeOccupation)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static TypeOccupation getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_TypeOccupation.ENTITY_NAME);
		      return (TypeOccupation) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String tocLibelle() {
    return (String) storedValueForKey("tocLibelle");
  }

  public void setTocLibelle(String value) {
    takeStoredValueForKey(value, "tocLibelle");
  }

  public NSArray toDetailPourcentages() {
    return (NSArray)storedValueForKey("toDetailPourcentages");
  }

  public NSArray toDetailPourcentages(EOQualifier qualifier) {
    return toDetailPourcentages(qualifier, null, false);
  }

  public NSArray toDetailPourcentages(EOQualifier qualifier, boolean fetch) {
    return toDetailPourcentages(qualifier, null, fetch);
  }

  public NSArray toDetailPourcentages(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.DetailPourcentage.TYPE_OCCUPATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.DetailPourcentage.fetchDetailPourcentages(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toDetailPourcentages();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToDetailPourcentagesRelationship(org.cocktail.superplan.server.metier.DetailPourcentage object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "toDetailPourcentages");
  }

  public void removeFromToDetailPourcentagesRelationship(org.cocktail.superplan.server.metier.DetailPourcentage object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toDetailPourcentages");
  }

  public org.cocktail.superplan.server.metier.DetailPourcentage createToDetailPourcentagesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("DetailPourcentage");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toDetailPourcentages");
    return (org.cocktail.superplan.server.metier.DetailPourcentage) eo;
  }

  public void deleteToDetailPourcentagesRelationship(org.cocktail.superplan.server.metier.DetailPourcentage object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toDetailPourcentages");
    editingContext().deleteObject(object);
  }

  public void deleteAllToDetailPourcentagesRelationships() {
    Enumeration objects = toDetailPourcentages().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToDetailPourcentagesRelationship((org.cocktail.superplan.server.metier.DetailPourcentage)objects.nextElement());
    }
  }


  public static TypeOccupation createTypeOccupation(EOEditingContext editingContext) {
    TypeOccupation eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_TypeOccupation.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _TypeOccupation.ENTITY_NAME + "' !");
    } else
    {
        eo = (TypeOccupation) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllTypeOccupations(EOEditingContext editingContext) {
    return _TypeOccupation.fetchAllTypeOccupations(editingContext, null);
  }

  public static NSArray fetchAllTypeOccupations(EOEditingContext editingContext, NSArray sortOrderings) {
    return _TypeOccupation.fetchTypeOccupations(editingContext, null, sortOrderings);
  }

  public static NSArray fetchTypeOccupations(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_TypeOccupation.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static TypeOccupation fetchTypeOccupation(EOEditingContext editingContext, String keyName, Object value) {
    return _TypeOccupation.fetchTypeOccupation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TypeOccupation fetchTypeOccupation(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _TypeOccupation.fetchTypeOccupations(editingContext, qualifier, null);
    TypeOccupation eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (TypeOccupation)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one TypeOccupation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TypeOccupation fetchRequiredTypeOccupation(EOEditingContext editingContext, String keyName, Object value) {
    return _TypeOccupation.fetchRequiredTypeOccupation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TypeOccupation fetchRequiredTypeOccupation(EOEditingContext editingContext, EOQualifier qualifier) {
    TypeOccupation eoObject = _TypeOccupation.fetchTypeOccupation(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no TypeOccupation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TypeOccupation localInstanceIn(EOEditingContext editingContext, TypeOccupation eo) {
    TypeOccupation localInstance = (eo == null) ? null : (TypeOccupation)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
