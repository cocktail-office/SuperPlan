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

// DO NOT EDIT.  Make changes to DetailPourcentage.java instead.
package org.cocktail.superplan.client.metier;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

public abstract class _DetailPourcentage extends  EOGenericRecord {
	public static final String ENTITY_NAME = "DetailPourcentage";
	public static final String ENTITY_TABLE_NAME = "GRHUM.DETAIL_POURCENTAGE";

	// Attributes

	public static final String C_STRUCTURE_KEY = "cStructure";
	public static final String DET_POURCENTAGE_KEY = "detPourcentage";
	public static final String SAL_NUMERO_KEY = "salNumero";
	public static final String TOC_ORDRE_KEY = "tocOrdre";

	public static final String C_STRUCTURE_COLKEY = "C_STRUCTURE";
	public static final String DET_POURCENTAGE_COLKEY = "DET_POURCENTAGE";
	public static final String SAL_NUMERO_COLKEY = "SAL_NUMERO";
	public static final String TOC_ORDRE_COLKEY = "TOC_ORDRE";

	// Relationships
	public static final String REPART_STRUCTURES_KEY = "repartStructures";
	public static final String SALLES_KEY = "salles";
	public static final String STRUCTURE_ULR_KEY = "structureUlr";
	public static final String TYPE_OCCUPATION_KEY = "typeOccupation";

	// Utilities methods
	  public DetailPourcentage localInstanceIn(EOEditingContext editingContext) {
	    DetailPourcentage localInstance = (DetailPourcentage)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static DetailPourcentage getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_DetailPourcentage.ENTITY_NAME);
		      return (DetailPourcentage) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cStructure() {
    return (String) storedValueForKey("cStructure");
  }

  public void setCStructure(String value) {
    takeStoredValueForKey(value, "cStructure");
  }

  public java.math.BigDecimal detPourcentage() {
    return (java.math.BigDecimal) storedValueForKey("detPourcentage");
  }

  public void setDetPourcentage(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "detPourcentage");
  }

  public Integer salNumero() {
    return (Integer) storedValueForKey("salNumero");
  }

  public void setSalNumero(Integer value) {
    takeStoredValueForKey(value, "salNumero");
  }

  public Integer tocOrdre() {
    return (Integer) storedValueForKey("tocOrdre");
  }

  public void setTocOrdre(Integer value) {
    takeStoredValueForKey(value, "tocOrdre");
  }

  public org.cocktail.superplan.client.metier.Salles salles() {
    return (org.cocktail.superplan.client.metier.Salles)storedValueForKey("salles");
  }

  public void setSallesRelationship(org.cocktail.superplan.client.metier.Salles value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Salles oldValue = salles();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "salles");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "salles");
    }
  }
  
  public org.cocktail.superplan.client.metier.StructureUlr structureUlr() {
    return (org.cocktail.superplan.client.metier.StructureUlr)storedValueForKey("structureUlr");
  }

  public void setStructureUlrRelationship(org.cocktail.superplan.client.metier.StructureUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.StructureUlr oldValue = structureUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "structureUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "structureUlr");
    }
  }
  
  public org.cocktail.superplan.client.metier.TypeOccupation typeOccupation() {
    return (org.cocktail.superplan.client.metier.TypeOccupation)storedValueForKey("typeOccupation");
  }

  public void setTypeOccupationRelationship(org.cocktail.superplan.client.metier.TypeOccupation value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.TypeOccupation oldValue = typeOccupation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "typeOccupation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "typeOccupation");
    }
  }
  
  public NSArray repartStructures() {
    return (NSArray)storedValueForKey("repartStructures");
  }

  public NSArray repartStructures(EOQualifier qualifier) {
    return repartStructures(qualifier, null);
  }

  public NSArray repartStructures(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = repartStructures();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartStructures");
  }

  public void removeFromRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartStructures");
  }

  public org.cocktail.superplan.client.metier.RepartStructure createRepartStructuresRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartStructure");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartStructures");
    return (org.cocktail.superplan.client.metier.RepartStructure) eo;
  }

  public void deleteRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartStructures");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartStructuresRelationships() {
    Enumeration objects = repartStructures().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartStructuresRelationship((org.cocktail.superplan.client.metier.RepartStructure)objects.nextElement());
    }
  }


  public static DetailPourcentage createDetailPourcentage(EOEditingContext editingContext, String cStructure
, Integer salNumero
, Integer tocOrdre
, org.cocktail.superplan.client.metier.Salles salles, org.cocktail.superplan.client.metier.StructureUlr structureUlr, org.cocktail.superplan.client.metier.TypeOccupation typeOccupation) {
    DetailPourcentage eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_DetailPourcentage.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _DetailPourcentage.ENTITY_NAME + "' !");
    } else
    {
        eo = (DetailPourcentage) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCStructure(cStructure);
		eo.setSalNumero(salNumero);
		eo.setTocOrdre(tocOrdre);
    eo.setSallesRelationship(salles);
    eo.setStructureUlrRelationship(structureUlr);
    eo.setTypeOccupationRelationship(typeOccupation);
    return eo;
  }

  public static NSArray fetchAllDetailPourcentages(EOEditingContext editingContext) {
    return _DetailPourcentage.fetchAllDetailPourcentages(editingContext, null);
  }

  public static NSArray fetchAllDetailPourcentages(EOEditingContext editingContext, NSArray sortOrderings) {
    return _DetailPourcentage.fetchDetailPourcentages(editingContext, null, sortOrderings);
  }

  public static NSArray fetchDetailPourcentages(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_DetailPourcentage.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static DetailPourcentage fetchDetailPourcentage(EOEditingContext editingContext, String keyName, Object value) {
    return _DetailPourcentage.fetchDetailPourcentage(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static DetailPourcentage fetchDetailPourcentage(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _DetailPourcentage.fetchDetailPourcentages(editingContext, qualifier, null);
    DetailPourcentage eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (DetailPourcentage)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one DetailPourcentage that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static DetailPourcentage fetchRequiredDetailPourcentage(EOEditingContext editingContext, String keyName, Object value) {
    return _DetailPourcentage.fetchRequiredDetailPourcentage(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static DetailPourcentage fetchRequiredDetailPourcentage(EOEditingContext editingContext, EOQualifier qualifier) {
    DetailPourcentage eoObject = _DetailPourcentage.fetchDetailPourcentage(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no DetailPourcentage that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static DetailPourcentage localInstanceIn(EOEditingContext editingContext, DetailPourcentage eo) {
    DetailPourcentage localInstance = (eo == null) ? null : (DetailPourcentage)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
