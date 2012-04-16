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

// DO NOT EDIT.  Make changes to ResaObjetReserve.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _ResaObjetReserve extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ResaObjetReserve";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESA_OBJET_RESERVE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "rorKey";

	public static final String DATE_DEB_KEY = "dateDeb";
	public static final String DATE_FIN_KEY = "dateFin";

	public static final String DATE_DEB_COLKEY = "DATE_DEB";
	public static final String DATE_FIN_COLKEY = "DATE_FIN";

	// Relationships
	public static final String REPART_STRUCTURES_KEY = "repartStructures";
	public static final String RESA_OBJET_KEY = "resaObjet";
	public static final String STRUCTURE_ULR_KEY = "structureUlr";

	// Utilities methods
	  public ResaObjetReserve localInstanceIn(EOEditingContext editingContext) {
	    ResaObjetReserve localInstance = (ResaObjetReserve)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ResaObjetReserve getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ResaObjetReserve.ENTITY_NAME);
		      return (ResaObjetReserve) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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

  public org.cocktail.superplan.client.metier.ResaObjet resaObjet() {
    return (org.cocktail.superplan.client.metier.ResaObjet)storedValueForKey("resaObjet");
  }

  public void setResaObjetRelationship(org.cocktail.superplan.client.metier.ResaObjet value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ResaObjet oldValue = resaObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "resaObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "resaObjet");
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


  public static ResaObjetReserve createResaObjetReserve(EOEditingContext editingContext, NSTimestamp dateDeb
) {
    ResaObjetReserve eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ResaObjetReserve.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ResaObjetReserve.ENTITY_NAME + "' !");
    } else
    {
        eo = (ResaObjetReserve) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDateDeb(dateDeb);
    return eo;
  }

  public static NSArray fetchAllResaObjetReserves(EOEditingContext editingContext) {
    return _ResaObjetReserve.fetchAllResaObjetReserves(editingContext, null);
  }

  public static NSArray fetchAllResaObjetReserves(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ResaObjetReserve.fetchResaObjetReserves(editingContext, null, sortOrderings);
  }

  public static NSArray fetchResaObjetReserves(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ResaObjetReserve.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ResaObjetReserve fetchResaObjetReserve(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaObjetReserve.fetchResaObjetReserve(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaObjetReserve fetchResaObjetReserve(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ResaObjetReserve.fetchResaObjetReserves(editingContext, qualifier, null);
    ResaObjetReserve eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ResaObjetReserve)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ResaObjetReserve that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaObjetReserve fetchRequiredResaObjetReserve(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaObjetReserve.fetchRequiredResaObjetReserve(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaObjetReserve fetchRequiredResaObjetReserve(EOEditingContext editingContext, EOQualifier qualifier) {
    ResaObjetReserve eoObject = _ResaObjetReserve.fetchResaObjetReserve(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ResaObjetReserve that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaObjetReserve localInstanceIn(EOEditingContext editingContext, ResaObjetReserve eo) {
    ResaObjetReserve localInstance = (eo == null) ? null : (ResaObjetReserve)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
