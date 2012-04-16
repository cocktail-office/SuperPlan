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

// DO NOT EDIT.  Make changes to MaquetteRepartitionAp.java instead.
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

public abstract class _MaquetteRepartitionAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteRepartitionAp";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_REPARTITION_AP";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mrapKey";



	// Relationships
	public static final String FORMATION_ANNEE_KEY = "formationAnnee";
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String MAQUETTE_EC_KEY = "maquetteEc";

	// Utilities methods
	  public MaquetteRepartitionAp localInstanceIn(EOEditingContext editingContext) {
	    MaquetteRepartitionAp localInstance = (MaquetteRepartitionAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteRepartitionAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteRepartitionAp.ENTITY_NAME);
		      return (MaquetteRepartitionAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
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
  
  public org.cocktail.superplan.server.metier.MaquetteAp maquetteAp() {
    return (org.cocktail.superplan.server.metier.MaquetteAp)storedValueForKey("maquetteAp");
  }

  public void setMaquetteApRelationship(org.cocktail.superplan.server.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteAp oldValue = maquetteAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteAp");
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
  

  public static MaquetteRepartitionAp createMaquetteRepartitionAp(EOEditingContext editingContext) {
    MaquetteRepartitionAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteRepartitionAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteRepartitionAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteRepartitionAp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllMaquetteRepartitionAps(EOEditingContext editingContext) {
    return _MaquetteRepartitionAp.fetchAllMaquetteRepartitionAps(editingContext, null);
  }

  public static NSArray fetchAllMaquetteRepartitionAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteRepartitionAp.fetchMaquetteRepartitionAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteRepartitionAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteRepartitionAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteRepartitionAp fetchMaquetteRepartitionAp(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteRepartitionAp.fetchMaquetteRepartitionAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteRepartitionAp fetchMaquetteRepartitionAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteRepartitionAp.fetchMaquetteRepartitionAps(editingContext, qualifier, null);
    MaquetteRepartitionAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteRepartitionAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteRepartitionAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteRepartitionAp fetchRequiredMaquetteRepartitionAp(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteRepartitionAp.fetchRequiredMaquetteRepartitionAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteRepartitionAp fetchRequiredMaquetteRepartitionAp(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteRepartitionAp eoObject = _MaquetteRepartitionAp.fetchMaquetteRepartitionAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteRepartitionAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteRepartitionAp localInstanceIn(EOEditingContext editingContext, MaquetteRepartitionAp eo) {
    MaquetteRepartitionAp localInstance = (eo == null) ? null : (MaquetteRepartitionAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
