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

// DO NOT EDIT.  Make changes to TypeSalle.java instead.
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

public abstract class _TypeSalle extends  EOGenericRecord {
	public static final String ENTITY_NAME = "TypeSalle";
	public static final String ENTITY_TABLE_NAME = "GRHUM.TYPE_SALLE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "tsalNumero";

	public static final String TSAL_LIBELLE_KEY = "tsalLibelle";

	public static final String TSAL_LIBELLE_COLKEY = "TSAL_LIBELLE";

	// Relationships

	// Utilities methods
	  public TypeSalle localInstanceIn(EOEditingContext editingContext) {
	    TypeSalle localInstance = (TypeSalle)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static TypeSalle getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_TypeSalle.ENTITY_NAME);
		      return (TypeSalle) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String tsalLibelle() {
    return (String) storedValueForKey("tsalLibelle");
  }

  public void setTsalLibelle(String value) {
    takeStoredValueForKey(value, "tsalLibelle");
  }


  public static TypeSalle createTypeSalle(EOEditingContext editingContext) {
    TypeSalle eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_TypeSalle.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _TypeSalle.ENTITY_NAME + "' !");
    } else
    {
        eo = (TypeSalle) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllTypeSalles(EOEditingContext editingContext) {
    return _TypeSalle.fetchAllTypeSalles(editingContext, null);
  }

  public static NSArray fetchAllTypeSalles(EOEditingContext editingContext, NSArray sortOrderings) {
    return _TypeSalle.fetchTypeSalles(editingContext, null, sortOrderings);
  }

  public static NSArray fetchTypeSalles(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_TypeSalle.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static TypeSalle fetchTypeSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _TypeSalle.fetchTypeSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TypeSalle fetchTypeSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _TypeSalle.fetchTypeSalles(editingContext, qualifier, null);
    TypeSalle eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (TypeSalle)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one TypeSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TypeSalle fetchRequiredTypeSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _TypeSalle.fetchRequiredTypeSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TypeSalle fetchRequiredTypeSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    TypeSalle eoObject = _TypeSalle.fetchTypeSalle(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no TypeSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TypeSalle localInstanceIn(EOEditingContext editingContext, TypeSalle eo) {
    TypeSalle localInstance = (eo == null) ? null : (TypeSalle)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
