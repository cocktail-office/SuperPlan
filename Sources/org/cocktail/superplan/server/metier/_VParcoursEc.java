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

// DO NOT EDIT.  Make changes to VParcoursEc.java instead.
package org.cocktail.superplan.server.metier;

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

public abstract class _VParcoursEc extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VParcoursEc";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_PARCOURS_EC";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mecKey";

	public static final String FANN_KEY_KEY = "fannKey";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";

	// Relationships
	public static final String EXAMEN_ENTETES_KEY = "examenEntetes";
	public static final String FORMATION_DIPLOME_KEY = "formationDiplome";
	public static final String MAQUETTE_EC_KEY = "maquetteEc";
	public static final String PARCOURS_KEY = "parcours";
	public static final String SEMESTRE_KEY = "semestre";

	// Utilities methods
	  public VParcoursEc localInstanceIn(EOEditingContext editingContext) {
	    VParcoursEc localInstance = (VParcoursEc)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VParcoursEc getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VParcoursEc.ENTITY_NAME);
		      return (VParcoursEc) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public org.cocktail.superplan.server.metier.FormationDiplome formationDiplome() {
    return (org.cocktail.superplan.server.metier.FormationDiplome)storedValueForKey("formationDiplome");
  }

  public void setFormationDiplomeRelationship(org.cocktail.superplan.server.metier.FormationDiplome value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationDiplome oldValue = formationDiplome();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationDiplome");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationDiplome");
    }
  }
  
  public org.cocktail.superplan.server.metier.MaquetteEc maquetteEc() {
    return (org.cocktail.superplan.server.metier.MaquetteEc)storedValueForKey("maquetteEc");
  }

  public void setMaquetteEcRelationship(org.cocktail.superplan.server.metier.MaquetteEc value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteEc oldValue = maquetteEc();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteEc");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteEc");
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
  
  public NSArray examenEntetes() {
    return (NSArray)storedValueForKey("examenEntetes");
  }

  public NSArray examenEntetes(EOQualifier qualifier) {
    return examenEntetes(qualifier, null);
  }

  public NSArray examenEntetes(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = examenEntetes();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToExamenEntetesRelationship(org.cocktail.superplan.server.metier.ExamenEntete object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "examenEntetes");
  }

  public void removeFromExamenEntetesRelationship(org.cocktail.superplan.server.metier.ExamenEntete object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "examenEntetes");
  }

  public org.cocktail.superplan.server.metier.ExamenEntete createExamenEntetesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ExamenEntete");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "examenEntetes");
    return (org.cocktail.superplan.server.metier.ExamenEntete) eo;
  }

  public void deleteExamenEntetesRelationship(org.cocktail.superplan.server.metier.ExamenEntete object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "examenEntetes");
    editingContext().deleteObject(object);
  }

  public void deleteAllExamenEntetesRelationships() {
    Enumeration objects = examenEntetes().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteExamenEntetesRelationship((org.cocktail.superplan.server.metier.ExamenEntete)objects.nextElement());
    }
  }


  public static VParcoursEc createVParcoursEc(EOEditingContext editingContext, Integer fannKey
) {
    VParcoursEc eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VParcoursEc.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VParcoursEc.ENTITY_NAME + "' !");
    } else
    {
        eo = (VParcoursEc) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
    return eo;
  }

  public static NSArray fetchAllVParcoursEcs(EOEditingContext editingContext) {
    return _VParcoursEc.fetchAllVParcoursEcs(editingContext, null);
  }

  public static NSArray fetchAllVParcoursEcs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VParcoursEc.fetchVParcoursEcs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVParcoursEcs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VParcoursEc.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VParcoursEc fetchVParcoursEc(EOEditingContext editingContext, String keyName, Object value) {
    return _VParcoursEc.fetchVParcoursEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VParcoursEc fetchVParcoursEc(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VParcoursEc.fetchVParcoursEcs(editingContext, qualifier, null);
    VParcoursEc eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VParcoursEc)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VParcoursEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VParcoursEc fetchRequiredVParcoursEc(EOEditingContext editingContext, String keyName, Object value) {
    return _VParcoursEc.fetchRequiredVParcoursEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VParcoursEc fetchRequiredVParcoursEc(EOEditingContext editingContext, EOQualifier qualifier) {
    VParcoursEc eoObject = _VParcoursEc.fetchVParcoursEc(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VParcoursEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VParcoursEc localInstanceIn(EOEditingContext editingContext, VParcoursEc eo) {
    VParcoursEc localInstance = (eo == null) ? null : (VParcoursEc)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
