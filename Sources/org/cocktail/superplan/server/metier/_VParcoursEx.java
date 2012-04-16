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

// DO NOT EDIT.  Make changes to VParcoursEx.java instead.
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

public abstract class _VParcoursEx extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VParcoursEx";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_PARCOURS_EX";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "eentKey";



	// Relationships
	public static final String DIPLOME_KEY = "diplome";
	public static final String EXAMEN_ENTETE_KEY = "examenEntete";
	public static final String PARCOURS_KEY = "parcours";
	public static final String SEMESTRE_KEY = "semestre";
	public static final String SPECIALISATION_KEY = "specialisation";

	// Utilities methods
	  public VParcoursEx localInstanceIn(EOEditingContext editingContext) {
	    VParcoursEx localInstance = (VParcoursEx)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VParcoursEx getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VParcoursEx.ENTITY_NAME);
		      return (VParcoursEx) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public org.cocktail.superplan.server.metier.FormationDiplome diplome() {
    return (org.cocktail.superplan.server.metier.FormationDiplome)storedValueForKey("diplome");
  }

  public void setDiplomeRelationship(org.cocktail.superplan.server.metier.FormationDiplome value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationDiplome oldValue = diplome();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "diplome");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "diplome");
    }
  }
  
  public org.cocktail.superplan.server.metier.ExamenEntete examenEntete() {
    return (org.cocktail.superplan.server.metier.ExamenEntete)storedValueForKey("examenEntete");
  }

  public void setExamenEnteteRelationship(org.cocktail.superplan.server.metier.ExamenEntete value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ExamenEntete oldValue = examenEntete();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "examenEntete");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "examenEntete");
    }
  }
  
  public org.cocktail.superplan.server.metier.MaquetteParcours parcours() {
    return (org.cocktail.superplan.server.metier.MaquetteParcours)storedValueForKey("parcours");
  }

  public void setParcoursRelationship(org.cocktail.superplan.server.metier.MaquetteParcours value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteParcours oldValue = parcours();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "parcours");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "parcours");
    }
  }
  
  public org.cocktail.superplan.server.metier.MaquetteSemestre semestre() {
    return (org.cocktail.superplan.server.metier.MaquetteSemestre)storedValueForKey("semestre");
  }

  public void setSemestreRelationship(org.cocktail.superplan.server.metier.MaquetteSemestre value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteSemestre oldValue = semestre();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "semestre");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "semestre");
    }
  }
  
  public org.cocktail.superplan.server.metier.FormationSpecialisation specialisation() {
    return (org.cocktail.superplan.server.metier.FormationSpecialisation)storedValueForKey("specialisation");
  }

  public void setSpecialisationRelationship(org.cocktail.superplan.server.metier.FormationSpecialisation value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationSpecialisation oldValue = specialisation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "specialisation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "specialisation");
    }
  }
  

  public static VParcoursEx createVParcoursEx(EOEditingContext editingContext) {
    VParcoursEx eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VParcoursEx.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VParcoursEx.ENTITY_NAME + "' !");
    } else
    {
        eo = (VParcoursEx) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllVParcoursExes(EOEditingContext editingContext) {
    return _VParcoursEx.fetchAllVParcoursExes(editingContext, null);
  }

  public static NSArray fetchAllVParcoursExes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VParcoursEx.fetchVParcoursExes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVParcoursExes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VParcoursEx.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VParcoursEx fetchVParcoursEx(EOEditingContext editingContext, String keyName, Object value) {
    return _VParcoursEx.fetchVParcoursEx(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VParcoursEx fetchVParcoursEx(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VParcoursEx.fetchVParcoursExes(editingContext, qualifier, null);
    VParcoursEx eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VParcoursEx)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VParcoursEx that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VParcoursEx fetchRequiredVParcoursEx(EOEditingContext editingContext, String keyName, Object value) {
    return _VParcoursEx.fetchRequiredVParcoursEx(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VParcoursEx fetchRequiredVParcoursEx(EOEditingContext editingContext, EOQualifier qualifier) {
    VParcoursEx eoObject = _VParcoursEx.fetchVParcoursEx(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VParcoursEx that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VParcoursEx localInstanceIn(EOEditingContext editingContext, VParcoursEx eo) {
    VParcoursEx localInstance = (eo == null) ? null : (VParcoursEx)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
