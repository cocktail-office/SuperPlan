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

// DO NOT EDIT.  Make changes to PrefScol.java instead.
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

public abstract class _PrefScol extends  EOGenericRecord {
	public static final String ENTITY_NAME = "PrefScol";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.PREF_SCOL";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "pScolKey";

	public static final String MRSEM_KEY_KEY = "mrsemKey";

	public static final String MRSEM_KEY_COLKEY = "MRSEM_KEY";

	// Relationships
	public static final String ANNEE_KEY = "annee";
	public static final String HABILITATION_KEY = "habilitation";
	public static final String INDIVIDU_KEY = "individu";
	public static final String MAQUETTE_REPARTITION_SEM_KEY = "maquetteRepartitionSem";

	// Utilities methods
	  public PrefScol localInstanceIn(EOEditingContext editingContext) {
	    PrefScol localInstance = (PrefScol)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static PrefScol getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_PrefScol.ENTITY_NAME);
		      return (PrefScol) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer mrsemKey() {
    return (Integer) storedValueForKey("mrsemKey");
  }

  public void setMrsemKey(Integer value) {
    takeStoredValueForKey(value, "mrsemKey");
  }

  public org.cocktail.superplan.server.metier.FormationAnnee annee() {
    return (org.cocktail.superplan.server.metier.FormationAnnee)storedValueForKey("annee");
  }

  public void setAnneeRelationship(org.cocktail.superplan.server.metier.FormationAnnee value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationAnnee oldValue = annee();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "annee");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "annee");
    }
  }
  
  public org.cocktail.superplan.server.metier.FormationHabilitation habilitation() {
    return (org.cocktail.superplan.server.metier.FormationHabilitation)storedValueForKey("habilitation");
  }

  public void setHabilitationRelationship(org.cocktail.superplan.server.metier.FormationHabilitation value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationHabilitation oldValue = habilitation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "habilitation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "habilitation");
    }
  }
  
  public org.cocktail.superplan.server.metier.IndividuUlr individu() {
    return (org.cocktail.superplan.server.metier.IndividuUlr)storedValueForKey("individu");
  }

  public void setIndividuRelationship(org.cocktail.superplan.server.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.IndividuUlr oldValue = individu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individu");
    }
  }
  
  public org.cocktail.superplan.server.metier.MaquetteRepartitionSem maquetteRepartitionSem() {
    return (org.cocktail.superplan.server.metier.MaquetteRepartitionSem)storedValueForKey("maquetteRepartitionSem");
  }

  public void setMaquetteRepartitionSemRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionSem value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteRepartitionSem oldValue = maquetteRepartitionSem();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteRepartitionSem");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteRepartitionSem");
    }
  }
  

  public static PrefScol createPrefScol(EOEditingContext editingContext) {
    PrefScol eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_PrefScol.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _PrefScol.ENTITY_NAME + "' !");
    } else
    {
        eo = (PrefScol) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllPrefScols(EOEditingContext editingContext) {
    return _PrefScol.fetchAllPrefScols(editingContext, null);
  }

  public static NSArray fetchAllPrefScols(EOEditingContext editingContext, NSArray sortOrderings) {
    return _PrefScol.fetchPrefScols(editingContext, null, sortOrderings);
  }

  public static NSArray fetchPrefScols(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_PrefScol.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static PrefScol fetchPrefScol(EOEditingContext editingContext, String keyName, Object value) {
    return _PrefScol.fetchPrefScol(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static PrefScol fetchPrefScol(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _PrefScol.fetchPrefScols(editingContext, qualifier, null);
    PrefScol eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (PrefScol)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one PrefScol that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static PrefScol fetchRequiredPrefScol(EOEditingContext editingContext, String keyName, Object value) {
    return _PrefScol.fetchRequiredPrefScol(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static PrefScol fetchRequiredPrefScol(EOEditingContext editingContext, EOQualifier qualifier) {
    PrefScol eoObject = _PrefScol.fetchPrefScol(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no PrefScol that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static PrefScol localInstanceIn(EOEditingContext editingContext, PrefScol eo) {
    PrefScol localInstance = (eo == null) ? null : (PrefScol)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
