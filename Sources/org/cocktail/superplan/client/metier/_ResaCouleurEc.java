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

// DO NOT EDIT.  Make changes to ResaCouleurEc.java instead.
package org.cocktail.superplan.client.metier;

import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

public abstract class _ResaCouleurEc extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ResaCouleurEc";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESA_COULEUR_EC";

	// Attributes

	public static final String COLOR_CODE_KEY = "colorCode";

	public static final String COLOR_CODE_COLKEY = "COLOR_CODE";

	// Relationships
	public static final String FORMATION_ANNEE_KEY = "formationAnnee";
	public static final String MAQUETTE_EC_KEY = "maquetteEc";

	// Utilities methods
	  public ResaCouleurEc localInstanceIn(EOEditingContext editingContext) {
	    ResaCouleurEc localInstance = (ResaCouleurEc)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ResaCouleurEc getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ResaCouleurEc.ENTITY_NAME);
		      return (ResaCouleurEc) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String colorCode() {
    return (String) storedValueForKey("colorCode");
  }

  public void setColorCode(String value) {
    takeStoredValueForKey(value, "colorCode");
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
  
  public org.cocktail.superplan.client.metier.MaquetteEc maquetteEc() {
    return (org.cocktail.superplan.client.metier.MaquetteEc)storedValueForKey("maquetteEc");
  }

  public void setMaquetteEcRelationship(org.cocktail.superplan.client.metier.MaquetteEc value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteEc oldValue = maquetteEc();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteEc");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteEc");
    }
  }
  

  public static ResaCouleurEc createResaCouleurEc(EOEditingContext editingContext, String colorCode
) {
    ResaCouleurEc eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ResaCouleurEc.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ResaCouleurEc.ENTITY_NAME + "' !");
    } else
    {
        eo = (ResaCouleurEc) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setColorCode(colorCode);
    return eo;
  }

  public static NSArray fetchAllResaCouleurEcs(EOEditingContext editingContext) {
    return _ResaCouleurEc.fetchAllResaCouleurEcs(editingContext, null);
  }

  public static NSArray fetchAllResaCouleurEcs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ResaCouleurEc.fetchResaCouleurEcs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchResaCouleurEcs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ResaCouleurEc.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ResaCouleurEc fetchResaCouleurEc(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaCouleurEc.fetchResaCouleurEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaCouleurEc fetchResaCouleurEc(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ResaCouleurEc.fetchResaCouleurEcs(editingContext, qualifier, null);
    ResaCouleurEc eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ResaCouleurEc)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ResaCouleurEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaCouleurEc fetchRequiredResaCouleurEc(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaCouleurEc.fetchRequiredResaCouleurEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaCouleurEc fetchRequiredResaCouleurEc(EOEditingContext editingContext, EOQualifier qualifier) {
    ResaCouleurEc eoObject = _ResaCouleurEc.fetchResaCouleurEc(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ResaCouleurEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaCouleurEc localInstanceIn(EOEditingContext editingContext, ResaCouleurEc eo) {
    ResaCouleurEc localInstance = (eo == null) ? null : (ResaCouleurEc)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
