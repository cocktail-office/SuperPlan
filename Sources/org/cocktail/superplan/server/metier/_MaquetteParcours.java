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

// DO NOT EDIT.  Make changes to MaquetteParcours.java instead.
package org.cocktail.superplan.server.metier;

import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

public abstract class _MaquetteParcours extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteParcours";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_PARCOURS";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mparKey";

	public static final String MPAR_ABREVIATION_KEY = "mparAbreviation";
	public static final String MPAR_CODE_KEY = "mparCode";
	public static final String MPAR_KEY_KEY = "mparKey";
	public static final String MPAR_LIBELLE_KEY = "mparLibelle";
	public static final String MPAR_VALIDITE_KEY = "mparValidite";

	public static final String MPAR_ABREVIATION_COLKEY = "MPAR_ABREVIATION";
	public static final String MPAR_CODE_COLKEY = "MPAR_CODE";
	public static final String MPAR_KEY_COLKEY = "MPAR_KEY";
	public static final String MPAR_LIBELLE_COLKEY = "MPAR_LIBELLE";
	public static final String MPAR_VALIDITE_COLKEY = "MPAR_VALIDITE";

	// Relationships
	public static final String FORMATION_SPECIALISATION_KEY = "formationSpecialisation";

	// Utilities methods
	  public MaquetteParcours localInstanceIn(EOEditingContext editingContext) {
	    MaquetteParcours localInstance = (MaquetteParcours)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteParcours getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteParcours.ENTITY_NAME);
		      return (MaquetteParcours) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String mparAbreviation() {
    return (String) storedValueForKey("mparAbreviation");
  }

  public void setMparAbreviation(String value) {
    takeStoredValueForKey(value, "mparAbreviation");
  }

  public String mparCode() {
    return (String) storedValueForKey("mparCode");
  }

  public void setMparCode(String value) {
    takeStoredValueForKey(value, "mparCode");
  }

  public Integer mparKey() {
    return (Integer) storedValueForKey("mparKey");
  }

  public void setMparKey(Integer value) {
    takeStoredValueForKey(value, "mparKey");
  }

  public String mparLibelle() {
    return (String) storedValueForKey("mparLibelle");
  }

  public void setMparLibelle(String value) {
    takeStoredValueForKey(value, "mparLibelle");
  }

  public String mparValidite() {
    return (String) storedValueForKey("mparValidite");
  }

  public void setMparValidite(String value) {
    takeStoredValueForKey(value, "mparValidite");
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
  

  public static MaquetteParcours createMaquetteParcours(EOEditingContext editingContext, String mparCode
, Integer mparKey
, String mparLibelle
, String mparValidite
) {
    MaquetteParcours eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteParcours.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteParcours.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteParcours) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setMparCode(mparCode);
		eo.setMparKey(mparKey);
		eo.setMparLibelle(mparLibelle);
		eo.setMparValidite(mparValidite);
    return eo;
  }

  public static NSArray fetchAllMaquetteParcourses(EOEditingContext editingContext) {
    return _MaquetteParcours.fetchAllMaquetteParcourses(editingContext, null);
  }

  public static NSArray fetchAllMaquetteParcourses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteParcours.fetchMaquetteParcourses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteParcourses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteParcours.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteParcours fetchMaquetteParcours(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteParcours.fetchMaquetteParcours(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteParcours fetchMaquetteParcours(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteParcours.fetchMaquetteParcourses(editingContext, qualifier, null);
    MaquetteParcours eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteParcours)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteParcours that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteParcours fetchRequiredMaquetteParcours(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteParcours.fetchRequiredMaquetteParcours(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteParcours fetchRequiredMaquetteParcours(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteParcours eoObject = _MaquetteParcours.fetchMaquetteParcours(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteParcours that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteParcours localInstanceIn(EOEditingContext editingContext, MaquetteParcours eo) {
    MaquetteParcours localInstance = (eo == null) ? null : (MaquetteParcours)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
