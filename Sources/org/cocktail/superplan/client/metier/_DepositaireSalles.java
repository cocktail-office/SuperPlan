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

// DO NOT EDIT.  Make changes to DepositaireSalles.java instead.
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

public abstract class _DepositaireSalles extends  EOGenericRecord {
	public static final String ENTITY_NAME = "DepositaireSalles";
	public static final String ENTITY_TABLE_NAME = "GRHUM.DEPOSITAIRE_SALLES";

	// Attributes

	public static final String C_STRUCTURE_KEY = "cStructure";

	public static final String C_STRUCTURE_COLKEY = "C_STRUCTURE";

	// Relationships
	public static final String SALLE_KEY = "salle";
	public static final String STRUCTURE_ULR_KEY = "structureUlr";

	// Utilities methods
	  public DepositaireSalles localInstanceIn(EOEditingContext editingContext) {
	    DepositaireSalles localInstance = (DepositaireSalles)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static DepositaireSalles getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_DepositaireSalles.ENTITY_NAME);
		      return (DepositaireSalles) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cStructure() {
    return (String) storedValueForKey("cStructure");
  }

  public void setCStructure(String value) {
    takeStoredValueForKey(value, "cStructure");
  }

  public org.cocktail.superplan.client.metier.Salles salle() {
    return (org.cocktail.superplan.client.metier.Salles)storedValueForKey("salle");
  }

  public void setSalleRelationship(org.cocktail.superplan.client.metier.Salles value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Salles oldValue = salle();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "salle");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "salle");
    }
  }
  
  public org.cocktail.superplan.client.metier.StructureUlr structureUlr() {
    return (org.cocktail.superplan.client.metier.StructureUlr)storedValueForKey("structureUlr");
  }

  public void setStructureUlrRelationship(org.cocktail.superplan.client.metier.StructureUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.StructureUlr oldValue = structureUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "structureUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "structureUlr");
    }
  }
  

  public static DepositaireSalles createDepositaireSalles(EOEditingContext editingContext, String cStructure
) {
    DepositaireSalles eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_DepositaireSalles.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _DepositaireSalles.ENTITY_NAME + "' !");
    } else
    {
        eo = (DepositaireSalles) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCStructure(cStructure);
    return eo;
  }

  public static NSArray fetchAllDepositaireSalleses(EOEditingContext editingContext) {
    return _DepositaireSalles.fetchAllDepositaireSalleses(editingContext, null);
  }

  public static NSArray fetchAllDepositaireSalleses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _DepositaireSalles.fetchDepositaireSalleses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchDepositaireSalleses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_DepositaireSalles.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static DepositaireSalles fetchDepositaireSalles(EOEditingContext editingContext, String keyName, Object value) {
    return _DepositaireSalles.fetchDepositaireSalles(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static DepositaireSalles fetchDepositaireSalles(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _DepositaireSalles.fetchDepositaireSalleses(editingContext, qualifier, null);
    DepositaireSalles eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (DepositaireSalles)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one DepositaireSalles that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static DepositaireSalles fetchRequiredDepositaireSalles(EOEditingContext editingContext, String keyName, Object value) {
    return _DepositaireSalles.fetchRequiredDepositaireSalles(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static DepositaireSalles fetchRequiredDepositaireSalles(EOEditingContext editingContext, EOQualifier qualifier) {
    DepositaireSalles eoObject = _DepositaireSalles.fetchDepositaireSalles(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no DepositaireSalles that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static DepositaireSalles localInstanceIn(EOEditingContext editingContext, DepositaireSalles eo) {
    DepositaireSalles localInstance = (eo == null) ? null : (DepositaireSalles)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
