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

// DO NOT EDIT.  Make changes to VScolInscritsGroupe.java instead.
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

public abstract class _VScolInscritsGroupe extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VScolInscritsGroupe";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.V_SCOL_INSCRITS_GROUPE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "ggrpKey";

	public static final String EFFECTIF_KEY = "effectif";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String GGRP_KEY_KEY = "ggrpKey";

	public static final String EFFECTIF_COLKEY = "EFFECTIF";
	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String GGRP_KEY_COLKEY = "GGRP_KEY";

	// Relationships
	public static final String SCOL_FORMATION_ANNEE_KEY = "scolFormationAnnee";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";

	// Utilities methods
	  public VScolInscritsGroupe localInstanceIn(EOEditingContext editingContext) {
	    VScolInscritsGroupe localInstance = (VScolInscritsGroupe)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VScolInscritsGroupe getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VScolInscritsGroupe.ENTITY_NAME);
		      return (VScolInscritsGroupe) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer effectif() {
    return (Integer) storedValueForKey("effectif");
  }

  public void setEffectif(Integer value) {
    takeStoredValueForKey(value, "effectif");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer ggrpKey() {
    return (Integer) storedValueForKey("ggrpKey");
  }

  public void setGgrpKey(Integer value) {
    takeStoredValueForKey(value, "ggrpKey");
  }

  public org.cocktail.superplan.server.metier.FormationAnnee scolFormationAnnee() {
    return (org.cocktail.superplan.server.metier.FormationAnnee)storedValueForKey("scolFormationAnnee");
  }

  public void setScolFormationAnneeRelationship(org.cocktail.superplan.server.metier.FormationAnnee value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationAnnee oldValue = scolFormationAnnee();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolFormationAnnee");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolFormationAnnee");
    }
  }
  
  public org.cocktail.superplan.server.metier.ScolGroupeGrp scolGroupeGrp() {
    return (org.cocktail.superplan.server.metier.ScolGroupeGrp)storedValueForKey("scolGroupeGrp");
  }

  public void setScolGroupeGrpRelationship(org.cocktail.superplan.server.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ScolGroupeGrp oldValue = scolGroupeGrp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolGroupeGrp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolGroupeGrp");
    }
  }
  

  public static VScolInscritsGroupe createVScolInscritsGroupe(EOEditingContext editingContext, Integer fannKey
, Integer ggrpKey
, org.cocktail.superplan.server.metier.FormationAnnee scolFormationAnnee, org.cocktail.superplan.server.metier.ScolGroupeGrp scolGroupeGrp) {
    VScolInscritsGroupe eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VScolInscritsGroupe.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VScolInscritsGroupe.ENTITY_NAME + "' !");
    } else
    {
        eo = (VScolInscritsGroupe) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setGgrpKey(ggrpKey);
    eo.setScolFormationAnneeRelationship(scolFormationAnnee);
    eo.setScolGroupeGrpRelationship(scolGroupeGrp);
    return eo;
  }

  public static NSArray fetchAllVScolInscritsGroupes(EOEditingContext editingContext) {
    return _VScolInscritsGroupe.fetchAllVScolInscritsGroupes(editingContext, null);
  }

  public static NSArray fetchAllVScolInscritsGroupes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VScolInscritsGroupe.fetchVScolInscritsGroupes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVScolInscritsGroupes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VScolInscritsGroupe.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VScolInscritsGroupe fetchVScolInscritsGroupe(EOEditingContext editingContext, String keyName, Object value) {
    return _VScolInscritsGroupe.fetchVScolInscritsGroupe(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VScolInscritsGroupe fetchVScolInscritsGroupe(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VScolInscritsGroupe.fetchVScolInscritsGroupes(editingContext, qualifier, null);
    VScolInscritsGroupe eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VScolInscritsGroupe)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VScolInscritsGroupe that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VScolInscritsGroupe fetchRequiredVScolInscritsGroupe(EOEditingContext editingContext, String keyName, Object value) {
    return _VScolInscritsGroupe.fetchRequiredVScolInscritsGroupe(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VScolInscritsGroupe fetchRequiredVScolInscritsGroupe(EOEditingContext editingContext, EOQualifier qualifier) {
    VScolInscritsGroupe eoObject = _VScolInscritsGroupe.fetchVScolInscritsGroupe(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VScolInscritsGroupe that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VScolInscritsGroupe localInstanceIn(EOEditingContext editingContext, VScolInscritsGroupe eo) {
    VScolInscritsGroupe localInstance = (eo == null) ? null : (VScolInscritsGroupe)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
