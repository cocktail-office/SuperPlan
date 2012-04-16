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

// DO NOT EDIT.  Make changes to FormationSpecialisation.java instead.
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

public abstract class _FormationSpecialisation extends  EOGenericRecord {
	public static final String ENTITY_NAME = "FormationSpecialisation";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_FORMATION_SPECIALISATION";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "fspnKey";

	public static final String FSPN_ABREVIATION_KEY = "fspnAbreviation";
	public static final String FSPN_LIBELLE_KEY = "fspnLibelle";

	public static final String FSPN_ABREVIATION_COLKEY = "FSPN_ABREVIATION";
	public static final String FSPN_LIBELLE_COLKEY = "FSPN_LIBELLE";

	// Relationships
	public static final String HABILITATIONS_KEY = "habilitations";
	public static final String MAQUETTE_PARCOURS_KEY = "maquetteParcours";
	public static final String SCOL_FORMATION_DIPLOME_KEY = "scolFormationDiplome";
	public static final String V_PARCOURS_APS_KEY = "vParcoursAps";

	// Utilities methods
	  public FormationSpecialisation localInstanceIn(EOEditingContext editingContext) {
	    FormationSpecialisation localInstance = (FormationSpecialisation)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static FormationSpecialisation getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_FormationSpecialisation.ENTITY_NAME);
		      return (FormationSpecialisation) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String fspnAbreviation() {
    return (String) storedValueForKey("fspnAbreviation");
  }

  public void setFspnAbreviation(String value) {
    takeStoredValueForKey(value, "fspnAbreviation");
  }

  public String fspnLibelle() {
    return (String) storedValueForKey("fspnLibelle");
  }

  public void setFspnLibelle(String value) {
    takeStoredValueForKey(value, "fspnLibelle");
  }

  public org.cocktail.superplan.client.metier.FormationDiplome scolFormationDiplome() {
    return (org.cocktail.superplan.client.metier.FormationDiplome)storedValueForKey("scolFormationDiplome");
  }

  public void setScolFormationDiplomeRelationship(org.cocktail.superplan.client.metier.FormationDiplome value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.FormationDiplome oldValue = scolFormationDiplome();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolFormationDiplome");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolFormationDiplome");
    }
  }
  
  public NSArray habilitations() {
    return (NSArray)storedValueForKey("habilitations");
  }

  public NSArray habilitations(EOQualifier qualifier) {
    return habilitations(qualifier, null, false);
  }

  public NSArray habilitations(EOQualifier qualifier, boolean fetch) {
    return habilitations(qualifier, null, fetch);
  }

  public NSArray habilitations(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.FormationHabilitation.FORMATION_SPECIALISATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.FormationHabilitation.fetchFormationHabilitations(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = habilitations();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToHabilitationsRelationship(org.cocktail.superplan.client.metier.FormationHabilitation object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "habilitations");
  }

  public void removeFromHabilitationsRelationship(org.cocktail.superplan.client.metier.FormationHabilitation object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "habilitations");
  }

  public org.cocktail.superplan.client.metier.FormationHabilitation createHabilitationsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("FormationHabilitation");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "habilitations");
    return (org.cocktail.superplan.client.metier.FormationHabilitation) eo;
  }

  public void deleteHabilitationsRelationship(org.cocktail.superplan.client.metier.FormationHabilitation object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "habilitations");
    editingContext().deleteObject(object);
  }

  public void deleteAllHabilitationsRelationships() {
    Enumeration objects = habilitations().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteHabilitationsRelationship((org.cocktail.superplan.client.metier.FormationHabilitation)objects.nextElement());
    }
  }

  public NSArray maquetteParcours() {
    return (NSArray)storedValueForKey("maquetteParcours");
  }

  public NSArray maquetteParcours(EOQualifier qualifier) {
    return maquetteParcours(qualifier, null, false);
  }

  public NSArray maquetteParcours(EOQualifier qualifier, boolean fetch) {
    return maquetteParcours(qualifier, null, fetch);
  }

  public NSArray maquetteParcours(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.MaquetteParcours.FORMATION_SPECIALISATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.MaquetteParcours.fetchMaquetteParcourses(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = maquetteParcours();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToMaquetteParcoursRelationship(org.cocktail.superplan.client.metier.MaquetteParcours object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "maquetteParcours");
  }

  public void removeFromMaquetteParcoursRelationship(org.cocktail.superplan.client.metier.MaquetteParcours object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteParcours");
  }

  public org.cocktail.superplan.client.metier.MaquetteParcours createMaquetteParcoursRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteParcours");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "maquetteParcours");
    return (org.cocktail.superplan.client.metier.MaquetteParcours) eo;
  }

  public void deleteMaquetteParcoursRelationship(org.cocktail.superplan.client.metier.MaquetteParcours object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteParcours");
    editingContext().deleteObject(object);
  }

  public void deleteAllMaquetteParcoursRelationships() {
    Enumeration objects = maquetteParcours().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteMaquetteParcoursRelationship((org.cocktail.superplan.client.metier.MaquetteParcours)objects.nextElement());
    }
  }

  public NSArray vParcoursAps() {
    return (NSArray)storedValueForKey("vParcoursAps");
  }

  public NSArray vParcoursAps(EOQualifier qualifier) {
    return vParcoursAps(qualifier, null, false);
  }

  public NSArray vParcoursAps(EOQualifier qualifier, boolean fetch) {
    return vParcoursAps(qualifier, null, fetch);
  }

  public NSArray vParcoursAps(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.VParcoursAp.SPECIALISATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.VParcoursAp.fetchVParcoursAps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = vParcoursAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToVParcoursApsRelationship(org.cocktail.superplan.client.metier.VParcoursAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "vParcoursAps");
  }

  public void removeFromVParcoursApsRelationship(org.cocktail.superplan.client.metier.VParcoursAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "vParcoursAps");
  }

  public org.cocktail.superplan.client.metier.VParcoursAp createVParcoursApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("VParcoursAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "vParcoursAps");
    return (org.cocktail.superplan.client.metier.VParcoursAp) eo;
  }

  public void deleteVParcoursApsRelationship(org.cocktail.superplan.client.metier.VParcoursAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "vParcoursAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllVParcoursApsRelationships() {
    Enumeration objects = vParcoursAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteVParcoursApsRelationship((org.cocktail.superplan.client.metier.VParcoursAp)objects.nextElement());
    }
  }


  public static FormationSpecialisation createFormationSpecialisation(EOEditingContext editingContext) {
    FormationSpecialisation eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_FormationSpecialisation.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _FormationSpecialisation.ENTITY_NAME + "' !");
    } else
    {
        eo = (FormationSpecialisation) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllFormationSpecialisations(EOEditingContext editingContext) {
    return _FormationSpecialisation.fetchAllFormationSpecialisations(editingContext, null);
  }

  public static NSArray fetchAllFormationSpecialisations(EOEditingContext editingContext, NSArray sortOrderings) {
    return _FormationSpecialisation.fetchFormationSpecialisations(editingContext, null, sortOrderings);
  }

  public static NSArray fetchFormationSpecialisations(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_FormationSpecialisation.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static FormationSpecialisation fetchFormationSpecialisation(EOEditingContext editingContext, String keyName, Object value) {
    return _FormationSpecialisation.fetchFormationSpecialisation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FormationSpecialisation fetchFormationSpecialisation(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _FormationSpecialisation.fetchFormationSpecialisations(editingContext, qualifier, null);
    FormationSpecialisation eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (FormationSpecialisation)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one FormationSpecialisation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FormationSpecialisation fetchRequiredFormationSpecialisation(EOEditingContext editingContext, String keyName, Object value) {
    return _FormationSpecialisation.fetchRequiredFormationSpecialisation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FormationSpecialisation fetchRequiredFormationSpecialisation(EOEditingContext editingContext, EOQualifier qualifier) {
    FormationSpecialisation eoObject = _FormationSpecialisation.fetchFormationSpecialisation(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no FormationSpecialisation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FormationSpecialisation localInstanceIn(EOEditingContext editingContext, FormationSpecialisation eo) {
    FormationSpecialisation localInstance = (eo == null) ? null : (FormationSpecialisation)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
