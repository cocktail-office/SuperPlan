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

// DO NOT EDIT.  Make changes to FormationHabilitation.java instead.
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

public abstract class _FormationHabilitation extends  EOGenericRecord {
	public static final String ENTITY_NAME = "FormationHabilitation";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_FORMATION_HABILITATION";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "fhabKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String FHAB_NIVEAU_KEY = "fhabNiveau";
	public static final String FHAB_OUVERT_KEY = "fhabOuvert";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String FHAB_NIVEAU_COLKEY = "FHAB_NIVEAU";
	public static final String FHAB_OUVERT_COLKEY = "FHAB_OUVERT";

	// Relationships
	public static final String CTRL_PARAM_HABILITATIONS_KEY = "ctrlParamHabilitations";
	public static final String DROIT_DIPLOMES_KEY = "droitDiplomes";
	public static final String FORMATION_ANNEE_KEY = "formationAnnee";
	public static final String FORMATION_SPECIALISATION_KEY = "formationSpecialisation";
	public static final String PREF_SCOLS_KEY = "prefScols";

	// Utilities methods
	  public FormationHabilitation localInstanceIn(EOEditingContext editingContext) {
	    FormationHabilitation localInstance = (FormationHabilitation)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static FormationHabilitation getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_FormationHabilitation.ENTITY_NAME);
		      return (FormationHabilitation) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer fhabNiveau() {
    return (Integer) storedValueForKey("fhabNiveau");
  }

  public void setFhabNiveau(Integer value) {
    takeStoredValueForKey(value, "fhabNiveau");
  }

  public String fhabOuvert() {
    return (String) storedValueForKey("fhabOuvert");
  }

  public void setFhabOuvert(String value) {
    takeStoredValueForKey(value, "fhabOuvert");
  }

  public org.cocktail.superplan.server.metier.FormationAnnee formationAnnee() {
    return (org.cocktail.superplan.server.metier.FormationAnnee)storedValueForKey("formationAnnee");
  }

  public void setFormationAnneeRelationship(org.cocktail.superplan.server.metier.FormationAnnee value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationAnnee oldValue = formationAnnee();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationAnnee");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationAnnee");
    }
  }
  
  public org.cocktail.superplan.server.metier.FormationSpecialisation formationSpecialisation() {
    return (org.cocktail.superplan.server.metier.FormationSpecialisation)storedValueForKey("formationSpecialisation");
  }

  public void setFormationSpecialisationRelationship(org.cocktail.superplan.server.metier.FormationSpecialisation value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationSpecialisation oldValue = formationSpecialisation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationSpecialisation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationSpecialisation");
    }
  }
  
  public NSArray ctrlParamHabilitations() {
    return (NSArray)storedValueForKey("ctrlParamHabilitations");
  }

  public NSArray ctrlParamHabilitations(EOQualifier qualifier) {
    return ctrlParamHabilitations(qualifier, null, false);
  }

  public NSArray ctrlParamHabilitations(EOQualifier qualifier, boolean fetch) {
    return ctrlParamHabilitations(qualifier, null, fetch);
  }

  public NSArray ctrlParamHabilitations(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.CtrlParamHabilitation.FORMATION_HABILITATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.CtrlParamHabilitation.fetchCtrlParamHabilitations(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = ctrlParamHabilitations();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToCtrlParamHabilitationsRelationship(org.cocktail.superplan.server.metier.CtrlParamHabilitation object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "ctrlParamHabilitations");
  }

  public void removeFromCtrlParamHabilitationsRelationship(org.cocktail.superplan.server.metier.CtrlParamHabilitation object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamHabilitations");
  }

  public org.cocktail.superplan.server.metier.CtrlParamHabilitation createCtrlParamHabilitationsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("CtrlParamHabilitation");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "ctrlParamHabilitations");
    return (org.cocktail.superplan.server.metier.CtrlParamHabilitation) eo;
  }

  public void deleteCtrlParamHabilitationsRelationship(org.cocktail.superplan.server.metier.CtrlParamHabilitation object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamHabilitations");
    editingContext().deleteObject(object);
  }

  public void deleteAllCtrlParamHabilitationsRelationships() {
    Enumeration objects = ctrlParamHabilitations().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteCtrlParamHabilitationsRelationship((org.cocktail.superplan.server.metier.CtrlParamHabilitation)objects.nextElement());
    }
  }

  public NSArray droitDiplomes() {
    return (NSArray)storedValueForKey("droitDiplomes");
  }

  public NSArray droitDiplomes(EOQualifier qualifier) {
    return droitDiplomes(qualifier, null, false);
  }

  public NSArray droitDiplomes(EOQualifier qualifier, boolean fetch) {
    return droitDiplomes(qualifier, null, fetch);
  }

  public NSArray droitDiplomes(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ScolDroitDiplome.HABILITATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ScolDroitDiplome.fetchScolDroitDiplomes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = droitDiplomes();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToDroitDiplomesRelationship(org.cocktail.superplan.server.metier.ScolDroitDiplome object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "droitDiplomes");
  }

  public void removeFromDroitDiplomesRelationship(org.cocktail.superplan.server.metier.ScolDroitDiplome object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "droitDiplomes");
  }

  public org.cocktail.superplan.server.metier.ScolDroitDiplome createDroitDiplomesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolDroitDiplome");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "droitDiplomes");
    return (org.cocktail.superplan.server.metier.ScolDroitDiplome) eo;
  }

  public void deleteDroitDiplomesRelationship(org.cocktail.superplan.server.metier.ScolDroitDiplome object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "droitDiplomes");
    editingContext().deleteObject(object);
  }

  public void deleteAllDroitDiplomesRelationships() {
    Enumeration objects = droitDiplomes().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteDroitDiplomesRelationship((org.cocktail.superplan.server.metier.ScolDroitDiplome)objects.nextElement());
    }
  }

  public NSArray prefScols() {
    return (NSArray)storedValueForKey("prefScols");
  }

  public NSArray prefScols(EOQualifier qualifier) {
    return prefScols(qualifier, null);
  }

  public NSArray prefScols(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = prefScols();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToPrefScolsRelationship(org.cocktail.superplan.server.metier.PrefScol object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "prefScols");
  }

  public void removeFromPrefScolsRelationship(org.cocktail.superplan.server.metier.PrefScol object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "prefScols");
  }

  public org.cocktail.superplan.server.metier.PrefScol createPrefScolsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("PrefScol");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "prefScols");
    return (org.cocktail.superplan.server.metier.PrefScol) eo;
  }

  public void deletePrefScolsRelationship(org.cocktail.superplan.server.metier.PrefScol object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "prefScols");
    editingContext().deleteObject(object);
  }

  public void deleteAllPrefScolsRelationships() {
    Enumeration objects = prefScols().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deletePrefScolsRelationship((org.cocktail.superplan.server.metier.PrefScol)objects.nextElement());
    }
  }


  public static FormationHabilitation createFormationHabilitation(EOEditingContext editingContext, Integer fannKey
, Integer fhabNiveau
, String fhabOuvert
) {
    FormationHabilitation eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_FormationHabilitation.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _FormationHabilitation.ENTITY_NAME + "' !");
    } else
    {
        eo = (FormationHabilitation) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setFhabNiveau(fhabNiveau);
		eo.setFhabOuvert(fhabOuvert);
    return eo;
  }

  public static NSArray fetchAllFormationHabilitations(EOEditingContext editingContext) {
    return _FormationHabilitation.fetchAllFormationHabilitations(editingContext, null);
  }

  public static NSArray fetchAllFormationHabilitations(EOEditingContext editingContext, NSArray sortOrderings) {
    return _FormationHabilitation.fetchFormationHabilitations(editingContext, null, sortOrderings);
  }

  public static NSArray fetchFormationHabilitations(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_FormationHabilitation.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static FormationHabilitation fetchFormationHabilitation(EOEditingContext editingContext, String keyName, Object value) {
    return _FormationHabilitation.fetchFormationHabilitation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FormationHabilitation fetchFormationHabilitation(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _FormationHabilitation.fetchFormationHabilitations(editingContext, qualifier, null);
    FormationHabilitation eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (FormationHabilitation)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one FormationHabilitation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FormationHabilitation fetchRequiredFormationHabilitation(EOEditingContext editingContext, String keyName, Object value) {
    return _FormationHabilitation.fetchRequiredFormationHabilitation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FormationHabilitation fetchRequiredFormationHabilitation(EOEditingContext editingContext, EOQualifier qualifier) {
    FormationHabilitation eoObject = _FormationHabilitation.fetchFormationHabilitation(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no FormationHabilitation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FormationHabilitation localInstanceIn(EOEditingContext editingContext, FormationHabilitation eo) {
    FormationHabilitation localInstance = (eo == null) ? null : (FormationHabilitation)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
