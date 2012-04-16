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

// DO NOT EDIT.  Make changes to TypeObjet.java instead.
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

public abstract class _TypeObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "TypeObjet";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.TYPE_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "typeCode";

	public static final String TYPE_CODE_KEY = "typeCode";

	public static final String TYPE_CODE_COLKEY = "TOB_LIBELLE";

	// Relationships
	public static final String CATEG_OBJET_KEY = "categObjet";

	// Utilities methods
	  public TypeObjet localInstanceIn(EOEditingContext editingContext) {
	    TypeObjet localInstance = (TypeObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static TypeObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_TypeObjet.ENTITY_NAME);
		      return (TypeObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String typeCode() {
    return (String) storedValueForKey("typeCode");
  }

  public void setTypeCode(String value) {
    takeStoredValueForKey(value, "typeCode");
  }

  public org.cocktail.superplan.server.metier.CategObjet categObjet() {
    return (org.cocktail.superplan.server.metier.CategObjet)storedValueForKey("categObjet");
  }

  public void setCategObjetRelationship(org.cocktail.superplan.server.metier.CategObjet value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.CategObjet oldValue = categObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "categObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "categObjet");
    }
  }
  

  public static TypeObjet createTypeObjet(EOEditingContext editingContext, String typeCode
) {
    TypeObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_TypeObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _TypeObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (TypeObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setTypeCode(typeCode);
    return eo;
  }

  public static NSArray fetchAllTypeObjets(EOEditingContext editingContext) {
    return _TypeObjet.fetchAllTypeObjets(editingContext, null);
  }

  public static NSArray fetchAllTypeObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _TypeObjet.fetchTypeObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchTypeObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_TypeObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static TypeObjet fetchTypeObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _TypeObjet.fetchTypeObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TypeObjet fetchTypeObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _TypeObjet.fetchTypeObjets(editingContext, qualifier, null);
    TypeObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (TypeObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one TypeObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TypeObjet fetchRequiredTypeObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _TypeObjet.fetchRequiredTypeObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TypeObjet fetchRequiredTypeObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    TypeObjet eoObject = _TypeObjet.fetchTypeObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no TypeObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TypeObjet localInstanceIn(EOEditingContext editingContext, TypeObjet eo) {
    TypeObjet localInstance = (eo == null) ? null : (TypeObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
