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

// DO NOT EDIT.  Make changes to FormationAnnee.java instead.
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

public abstract class _FormationAnnee extends  EOGenericRecord {
	public static final String ENTITY_NAME = "FormationAnnee";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_FORMATION_ANNEE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "fannKey";

	public static final String FANN_COURANTE_KEY = "fannCourante";
	public static final String FANN_DEBUT_KEY = "fannDebut";
	public static final String FANN_FIN_KEY = "fannFin";
	public static final String FANN_KEY_KEY = "fannKey";

	public static final String FANN_COURANTE_COLKEY = "FANN_COURANTE";
	public static final String FANN_DEBUT_COLKEY = "FANN_DEBUT";
	public static final String FANN_FIN_COLKEY = "FANN_FIN";
	public static final String FANN_KEY_COLKEY = "FANN_KEY";

	// Relationships
	public static final String RESA_COULEUR_EC_KEY = "resaCouleurEc";

	// Utilities methods
	  public FormationAnnee localInstanceIn(EOEditingContext editingContext) {
	    FormationAnnee localInstance = (FormationAnnee)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static FormationAnnee getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_FormationAnnee.ENTITY_NAME);
		      return (FormationAnnee) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String fannCourante() {
    return (String) storedValueForKey("fannCourante");
  }

  public void setFannCourante(String value) {
    takeStoredValueForKey(value, "fannCourante");
  }

  public Integer fannDebut() {
    return (Integer) storedValueForKey("fannDebut");
  }

  public void setFannDebut(Integer value) {
    takeStoredValueForKey(value, "fannDebut");
  }

  public Integer fannFin() {
    return (Integer) storedValueForKey("fannFin");
  }

  public void setFannFin(Integer value) {
    takeStoredValueForKey(value, "fannFin");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public NSArray resaCouleurEc() {
    return (NSArray)storedValueForKey("resaCouleurEc");
  }

  public NSArray resaCouleurEc(EOQualifier qualifier) {
    return resaCouleurEc(qualifier, null, false);
  }

  public NSArray resaCouleurEc(EOQualifier qualifier, boolean fetch) {
    return resaCouleurEc(qualifier, null, fetch);
  }

  public NSArray resaCouleurEc(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ResaCouleurEc.FORMATION_ANNEE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ResaCouleurEc.fetchResaCouleurEcs(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = resaCouleurEc();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToResaCouleurEcRelationship(org.cocktail.superplan.client.metier.ResaCouleurEc object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "resaCouleurEc");
  }

  public void removeFromResaCouleurEcRelationship(org.cocktail.superplan.client.metier.ResaCouleurEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaCouleurEc");
  }

  public org.cocktail.superplan.client.metier.ResaCouleurEc createResaCouleurEcRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ResaCouleurEc");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "resaCouleurEc");
    return (org.cocktail.superplan.client.metier.ResaCouleurEc) eo;
  }

  public void deleteResaCouleurEcRelationship(org.cocktail.superplan.client.metier.ResaCouleurEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaCouleurEc");
    editingContext().deleteObject(object);
  }

  public void deleteAllResaCouleurEcRelationships() {
    Enumeration objects = resaCouleurEc().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteResaCouleurEcRelationship((org.cocktail.superplan.client.metier.ResaCouleurEc)objects.nextElement());
    }
  }


  public static FormationAnnee createFormationAnnee(EOEditingContext editingContext, String fannCourante
, Integer fannDebut
, Integer fannFin
, Integer fannKey
) {
    FormationAnnee eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_FormationAnnee.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _FormationAnnee.ENTITY_NAME + "' !");
    } else
    {
        eo = (FormationAnnee) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannCourante(fannCourante);
		eo.setFannDebut(fannDebut);
		eo.setFannFin(fannFin);
		eo.setFannKey(fannKey);
    return eo;
  }

  public static NSArray fetchAllFormationAnnees(EOEditingContext editingContext) {
    return _FormationAnnee.fetchAllFormationAnnees(editingContext, null);
  }

  public static NSArray fetchAllFormationAnnees(EOEditingContext editingContext, NSArray sortOrderings) {
    return _FormationAnnee.fetchFormationAnnees(editingContext, null, sortOrderings);
  }

  public static NSArray fetchFormationAnnees(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_FormationAnnee.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static FormationAnnee fetchFormationAnnee(EOEditingContext editingContext, String keyName, Object value) {
    return _FormationAnnee.fetchFormationAnnee(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FormationAnnee fetchFormationAnnee(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _FormationAnnee.fetchFormationAnnees(editingContext, qualifier, null);
    FormationAnnee eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (FormationAnnee)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one FormationAnnee that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FormationAnnee fetchRequiredFormationAnnee(EOEditingContext editingContext, String keyName, Object value) {
    return _FormationAnnee.fetchRequiredFormationAnnee(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FormationAnnee fetchRequiredFormationAnnee(EOEditingContext editingContext, EOQualifier qualifier) {
    FormationAnnee eoObject = _FormationAnnee.fetchFormationAnnee(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no FormationAnnee that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FormationAnnee localInstanceIn(EOEditingContext editingContext, FormationAnnee eo) {
    FormationAnnee localInstance = (eo == null) ? null : (FormationAnnee)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
