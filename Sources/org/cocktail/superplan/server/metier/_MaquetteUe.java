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

// DO NOT EDIT.  Make changes to MaquetteUe.java instead.
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

public abstract class _MaquetteUe extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteUe";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_UE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mueKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String FDOM_CODE_KEY = "fdomCode";
	public static final String MUE_CODE_KEY = "mueCode";
	public static final String MUE_HORAIRE_ETU_KEY = "mueHoraireEtu";
	public static final String MUE_LIBELLE_KEY = "mueLibelle";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String FDOM_CODE_COLKEY = "FDOM_CODE";
	public static final String MUE_CODE_COLKEY = "MUE_CODE";
	public static final String MUE_HORAIRE_ETU_COLKEY = "MUE_HORAIRE_ETU";
	public static final String MUE_LIBELLE_COLKEY = "MUE_LIBELLE";

	// Relationships
	public static final String MAQUETTE_REPARTITION_UES_KEY = "maquetteRepartitionUes";
	public static final String REPARTITION_ECS_KEY = "repartitionEcs";

	// Utilities methods
	  public MaquetteUe localInstanceIn(EOEditingContext editingContext) {
	    MaquetteUe localInstance = (MaquetteUe)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteUe getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteUe.ENTITY_NAME);
		      return (MaquetteUe) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public String fdomCode() {
    return (String) storedValueForKey("fdomCode");
  }

  public void setFdomCode(String value) {
    takeStoredValueForKey(value, "fdomCode");
  }

  public String mueCode() {
    return (String) storedValueForKey("mueCode");
  }

  public void setMueCode(String value) {
    takeStoredValueForKey(value, "mueCode");
  }

  public java.math.BigDecimal mueHoraireEtu() {
    return (java.math.BigDecimal) storedValueForKey("mueHoraireEtu");
  }

  public void setMueHoraireEtu(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mueHoraireEtu");
  }

  public String mueLibelle() {
    return (String) storedValueForKey("mueLibelle");
  }

  public void setMueLibelle(String value) {
    takeStoredValueForKey(value, "mueLibelle");
  }

  public NSArray maquetteRepartitionUes() {
    return (NSArray)storedValueForKey("maquetteRepartitionUes");
  }

  public NSArray maquetteRepartitionUes(EOQualifier qualifier) {
    return maquetteRepartitionUes(qualifier, null, false);
  }

  public NSArray maquetteRepartitionUes(EOQualifier qualifier, boolean fetch) {
    return maquetteRepartitionUes(qualifier, null, fetch);
  }

  public NSArray maquetteRepartitionUes(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.MaquetteRepartitionUe.MAQUETTE_UE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.MaquetteRepartitionUe.fetchMaquetteRepartitionUes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = maquetteRepartitionUes();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToMaquetteRepartitionUesRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionUe object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "maquetteRepartitionUes");
  }

  public void removeFromMaquetteRepartitionUesRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionUe object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionUes");
  }

  public org.cocktail.superplan.server.metier.MaquetteRepartitionUe createMaquetteRepartitionUesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteRepartitionUe");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "maquetteRepartitionUes");
    return (org.cocktail.superplan.server.metier.MaquetteRepartitionUe) eo;
  }

  public void deleteMaquetteRepartitionUesRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionUe object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionUes");
    editingContext().deleteObject(object);
  }

  public void deleteAllMaquetteRepartitionUesRelationships() {
    Enumeration objects = maquetteRepartitionUes().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteMaquetteRepartitionUesRelationship((org.cocktail.superplan.server.metier.MaquetteRepartitionUe)objects.nextElement());
    }
  }

  public NSArray repartitionEcs() {
    return (NSArray)storedValueForKey("repartitionEcs");
  }

  public NSArray repartitionEcs(EOQualifier qualifier) {
    return repartitionEcs(qualifier, null, false);
  }

  public NSArray repartitionEcs(EOQualifier qualifier, boolean fetch) {
    return repartitionEcs(qualifier, null, fetch);
  }

  public NSArray repartitionEcs(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.MaquetteRepartitionEc.MAQUETTE_UE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.MaquetteRepartitionEc.fetchMaquetteRepartitionEcs(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = repartitionEcs();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRepartitionEcsRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionEc object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartitionEcs");
  }

  public void removeFromRepartitionEcsRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartitionEcs");
  }

  public org.cocktail.superplan.server.metier.MaquetteRepartitionEc createRepartitionEcsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteRepartitionEc");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartitionEcs");
    return (org.cocktail.superplan.server.metier.MaquetteRepartitionEc) eo;
  }

  public void deleteRepartitionEcsRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartitionEcs");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartitionEcsRelationships() {
    Enumeration objects = repartitionEcs().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartitionEcsRelationship((org.cocktail.superplan.server.metier.MaquetteRepartitionEc)objects.nextElement());
    }
  }


  public static MaquetteUe createMaquetteUe(EOEditingContext editingContext, Integer fannKey
, String fdomCode
, String mueCode
, java.math.BigDecimal mueHoraireEtu
) {
    MaquetteUe eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteUe.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteUe.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteUe) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setFdomCode(fdomCode);
		eo.setMueCode(mueCode);
		eo.setMueHoraireEtu(mueHoraireEtu);
    return eo;
  }

  public static NSArray fetchAllMaquetteUes(EOEditingContext editingContext) {
    return _MaquetteUe.fetchAllMaquetteUes(editingContext, null);
  }

  public static NSArray fetchAllMaquetteUes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteUe.fetchMaquetteUes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteUes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteUe.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteUe fetchMaquetteUe(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteUe.fetchMaquetteUe(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteUe fetchMaquetteUe(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteUe.fetchMaquetteUes(editingContext, qualifier, null);
    MaquetteUe eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteUe)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteUe that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteUe fetchRequiredMaquetteUe(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteUe.fetchRequiredMaquetteUe(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteUe fetchRequiredMaquetteUe(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteUe eoObject = _MaquetteUe.fetchMaquetteUe(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteUe that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteUe localInstanceIn(EOEditingContext editingContext, MaquetteUe eo) {
    MaquetteUe localInstance = (eo == null) ? null : (MaquetteUe)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
