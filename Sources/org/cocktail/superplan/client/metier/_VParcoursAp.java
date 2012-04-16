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

// DO NOT EDIT.  Make changes to VParcoursAp.java instead.
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

public abstract class _VParcoursAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VParcoursAp";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_PARCOURS_AP";

	// Attributes

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MTEC_CODE_KEY = "mtecCode";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String MTEC_CODE_COLKEY = "MTEC_CODE";

	// Relationships
	public static final String AP_KEY = "ap";
	public static final String DIPLOME_KEY = "diplome";
	public static final String EC_KEY = "ec";
	public static final String EXAMEN_ENTETES_KEY = "examenEntetes";
	public static final String PARCOURS_KEY = "parcours";
	public static final String SEMESTRE_KEY = "semestre";
	public static final String SPECIALISATION_KEY = "specialisation";

	// Utilities methods
	  public VParcoursAp localInstanceIn(EOEditingContext editingContext) {
	    VParcoursAp localInstance = (VParcoursAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VParcoursAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VParcoursAp.ENTITY_NAME);
		      return (VParcoursAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public String mtecCode() {
    return (String) storedValueForKey("mtecCode");
  }

  public void setMtecCode(String value) {
    takeStoredValueForKey(value, "mtecCode");
  }

  public org.cocktail.superplan.client.metier.MaquetteAp ap() {
    return (org.cocktail.superplan.client.metier.MaquetteAp)storedValueForKey("ap");
  }

  public void setApRelationship(org.cocktail.superplan.client.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteAp oldValue = ap();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "ap");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "ap");
    }
  }
  
  public org.cocktail.superplan.client.metier.FormationDiplome diplome() {
    return (org.cocktail.superplan.client.metier.FormationDiplome)storedValueForKey("diplome");
  }

  public void setDiplomeRelationship(org.cocktail.superplan.client.metier.FormationDiplome value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.FormationDiplome oldValue = diplome();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "diplome");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "diplome");
    }
  }
  
  public org.cocktail.superplan.client.metier.MaquetteEc ec() {
    return (org.cocktail.superplan.client.metier.MaquetteEc)storedValueForKey("ec");
  }

  public void setEcRelationship(org.cocktail.superplan.client.metier.MaquetteEc value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteEc oldValue = ec();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "ec");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "ec");
    }
  }
  
  public org.cocktail.superplan.client.metier.MaquetteParcours parcours() {
    return (org.cocktail.superplan.client.metier.MaquetteParcours)storedValueForKey("parcours");
  }

  public void setParcoursRelationship(org.cocktail.superplan.client.metier.MaquetteParcours value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteParcours oldValue = parcours();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "parcours");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "parcours");
    }
  }
  
  public org.cocktail.superplan.client.metier.MaquetteSemestre semestre() {
    return (org.cocktail.superplan.client.metier.MaquetteSemestre)storedValueForKey("semestre");
  }

  public void setSemestreRelationship(org.cocktail.superplan.client.metier.MaquetteSemestre value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteSemestre oldValue = semestre();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "semestre");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "semestre");
    }
  }
  
  public org.cocktail.superplan.client.metier.FormationSpecialisation specialisation() {
    return (org.cocktail.superplan.client.metier.FormationSpecialisation)storedValueForKey("specialisation");
  }

  public void setSpecialisationRelationship(org.cocktail.superplan.client.metier.FormationSpecialisation value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.FormationSpecialisation oldValue = specialisation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "specialisation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "specialisation");
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
  
  public void addToExamenEntetesRelationship(org.cocktail.superplan.client.metier.ExamenEntete object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "examenEntetes");
  }

  public void removeFromExamenEntetesRelationship(org.cocktail.superplan.client.metier.ExamenEntete object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "examenEntetes");
  }

  public org.cocktail.superplan.client.metier.ExamenEntete createExamenEntetesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ExamenEntete");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "examenEntetes");
    return (org.cocktail.superplan.client.metier.ExamenEntete) eo;
  }

  public void deleteExamenEntetesRelationship(org.cocktail.superplan.client.metier.ExamenEntete object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "examenEntetes");
    editingContext().deleteObject(object);
  }

  public void deleteAllExamenEntetesRelationships() {
    Enumeration objects = examenEntetes().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteExamenEntetesRelationship((org.cocktail.superplan.client.metier.ExamenEntete)objects.nextElement());
    }
  }


  public static VParcoursAp createVParcoursAp(EOEditingContext editingContext, Integer fannKey
, String mtecCode
, org.cocktail.superplan.client.metier.MaquetteEc ec, org.cocktail.superplan.client.metier.MaquetteParcours parcours, org.cocktail.superplan.client.metier.MaquetteSemestre semestre) {
    VParcoursAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VParcoursAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VParcoursAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (VParcoursAp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setMtecCode(mtecCode);
    eo.setEcRelationship(ec);
    eo.setParcoursRelationship(parcours);
    eo.setSemestreRelationship(semestre);
    return eo;
  }

  public static NSArray fetchAllVParcoursAps(EOEditingContext editingContext) {
    return _VParcoursAp.fetchAllVParcoursAps(editingContext, null);
  }

  public static NSArray fetchAllVParcoursAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VParcoursAp.fetchVParcoursAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVParcoursAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VParcoursAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VParcoursAp fetchVParcoursAp(EOEditingContext editingContext, String keyName, Object value) {
    return _VParcoursAp.fetchVParcoursAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VParcoursAp fetchVParcoursAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VParcoursAp.fetchVParcoursAps(editingContext, qualifier, null);
    VParcoursAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VParcoursAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VParcoursAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VParcoursAp fetchRequiredVParcoursAp(EOEditingContext editingContext, String keyName, Object value) {
    return _VParcoursAp.fetchRequiredVParcoursAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VParcoursAp fetchRequiredVParcoursAp(EOEditingContext editingContext, EOQualifier qualifier) {
    VParcoursAp eoObject = _VParcoursAp.fetchVParcoursAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VParcoursAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VParcoursAp localInstanceIn(EOEditingContext editingContext, VParcoursAp eo) {
    VParcoursAp localInstance = (eo == null) ? null : (VParcoursAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
