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

// DO NOT EDIT.  Make changes to ContrainteSemaine.java instead.
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

public abstract class _ContrainteSemaine extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ContrainteSemaine";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CONTRAINTE_SEMAINE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "ctsKey";

	public static final String CTS_DATE_KEY = "ctsDate";
	public static final String CTS_NO_SEMAINE_KEY = "ctsNoSemaine";

	public static final String CTS_DATE_COLKEY = "CTS_DATE";
	public static final String CTS_NO_SEMAINE_COLKEY = "CTS_NO_SEMAINE";

	// Relationships
	public static final String CONTRAINTE_JOURS_KEY = "contrainteJours";
	public static final String FORMATION_ANNEE_KEY = "formationAnnee";
	public static final String INDIVIDU_ULR_KEY = "individuUlr";

	// Utilities methods
	  public ContrainteSemaine localInstanceIn(EOEditingContext editingContext) {
	    ContrainteSemaine localInstance = (ContrainteSemaine)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ContrainteSemaine getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ContrainteSemaine.ENTITY_NAME);
		      return (ContrainteSemaine) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp ctsDate() {
    return (NSTimestamp) storedValueForKey("ctsDate");
  }

  public void setCtsDate(NSTimestamp value) {
    takeStoredValueForKey(value, "ctsDate");
  }

  public Integer ctsNoSemaine() {
    return (Integer) storedValueForKey("ctsNoSemaine");
  }

  public void setCtsNoSemaine(Integer value) {
    takeStoredValueForKey(value, "ctsNoSemaine");
  }

  public org.cocktail.superplan.client.metier.FormationAnnee formationAnnee() {
    return (org.cocktail.superplan.client.metier.FormationAnnee)storedValueForKey("formationAnnee");
  }

  public void setFormationAnneeRelationship(org.cocktail.superplan.client.metier.FormationAnnee value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.FormationAnnee oldValue = formationAnnee();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationAnnee");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationAnnee");
    }
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
  
  public NSArray contrainteJours() {
    return (NSArray)storedValueForKey("contrainteJours");
  }

  public NSArray contrainteJours(EOQualifier qualifier) {
    return contrainteJours(qualifier, null, false);
  }

  public NSArray contrainteJours(EOQualifier qualifier, boolean fetch) {
    return contrainteJours(qualifier, null, fetch);
  }

  public NSArray contrainteJours(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ContrainteJour.CONTRAINTE_SEMAINE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ContrainteJour.fetchContrainteJours(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = contrainteJours();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToContrainteJoursRelationship(org.cocktail.superplan.client.metier.ContrainteJour object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "contrainteJours");
  }

  public void removeFromContrainteJoursRelationship(org.cocktail.superplan.client.metier.ContrainteJour object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "contrainteJours");
  }

  public org.cocktail.superplan.client.metier.ContrainteJour createContrainteJoursRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ContrainteJour");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "contrainteJours");
    return (org.cocktail.superplan.client.metier.ContrainteJour) eo;
  }

  public void deleteContrainteJoursRelationship(org.cocktail.superplan.client.metier.ContrainteJour object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "contrainteJours");
    editingContext().deleteObject(object);
  }

  public void deleteAllContrainteJoursRelationships() {
    Enumeration objects = contrainteJours().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteContrainteJoursRelationship((org.cocktail.superplan.client.metier.ContrainteJour)objects.nextElement());
    }
  }


  public static ContrainteSemaine createContrainteSemaine(EOEditingContext editingContext, NSTimestamp ctsDate
, Integer ctsNoSemaine
, org.cocktail.superplan.client.metier.FormationAnnee formationAnnee) {
    ContrainteSemaine eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ContrainteSemaine.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ContrainteSemaine.ENTITY_NAME + "' !");
    } else
    {
        eo = (ContrainteSemaine) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCtsDate(ctsDate);
		eo.setCtsNoSemaine(ctsNoSemaine);
    eo.setFormationAnneeRelationship(formationAnnee);
    return eo;
  }

  public static NSArray fetchAllContrainteSemaines(EOEditingContext editingContext) {
    return _ContrainteSemaine.fetchAllContrainteSemaines(editingContext, null);
  }

  public static NSArray fetchAllContrainteSemaines(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ContrainteSemaine.fetchContrainteSemaines(editingContext, null, sortOrderings);
  }

  public static NSArray fetchContrainteSemaines(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ContrainteSemaine.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ContrainteSemaine fetchContrainteSemaine(EOEditingContext editingContext, String keyName, Object value) {
    return _ContrainteSemaine.fetchContrainteSemaine(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ContrainteSemaine fetchContrainteSemaine(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ContrainteSemaine.fetchContrainteSemaines(editingContext, qualifier, null);
    ContrainteSemaine eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ContrainteSemaine)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ContrainteSemaine that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ContrainteSemaine fetchRequiredContrainteSemaine(EOEditingContext editingContext, String keyName, Object value) {
    return _ContrainteSemaine.fetchRequiredContrainteSemaine(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ContrainteSemaine fetchRequiredContrainteSemaine(EOEditingContext editingContext, EOQualifier qualifier) {
    ContrainteSemaine eoObject = _ContrainteSemaine.fetchContrainteSemaine(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ContrainteSemaine that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ContrainteSemaine localInstanceIn(EOEditingContext editingContext, ContrainteSemaine eo) {
    ContrainteSemaine localInstance = (eo == null) ? null : (ContrainteSemaine)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
