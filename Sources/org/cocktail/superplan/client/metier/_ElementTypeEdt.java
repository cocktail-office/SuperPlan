/*
 * Copyright COCKTAIL (www.cocktail.org), 1995, 2011 This software
 * is governed by the CeCILL license under French law and abiding by the
 * rules of distribution of free software. You can use, modify and/or
 * redistribute the software under the terms of the CeCILL license as
 * circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * As a counterpart to the access to the source code and rights to copy, modify
 * and redistribute granted by the license, users are provided only with a
 * limited warranty and the software's author, the holder of the economic
 * rights, and the successive licensors have only limited liability. In this
 * respect, the user's attention is drawn to the risks associated with loading,
 * using, modifying and/or developing or reproducing the software by the user
 * in light of its specific status of free software, that may mean that it
 * is complicated to manipulate, and that also therefore means that it is
 * reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the
 * software's suitability as regards their requirements in conditions enabling
 * the security of their systems and/or data to be ensured and, more generally,
 * to use and operate it in the same conditions as regards security. The
 * fact that you are presently reading this means that you have had knowledge
 * of the CeCILL license and that you accept its terms.
 */

// DO NOT EDIT.  Make changes to ElementTypeEdt.java instead.
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

public abstract class _ElementTypeEdt extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ElementTypeEdt";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.ELEMENT_TYPE_EDT";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "elemType";

	public static final String ELEM_TYPE_KEY = "elemType";
	public static final String LIBELLE_KEY = "libelle";

	public static final String ELEM_TYPE_COLKEY = "ELEM_TYPE";
	public static final String LIBELLE_COLKEY = "LIBELLE";

	// Relationships

	// Utilities methods
	  public ElementTypeEdt localInstanceIn(EOEditingContext editingContext) {
	    ElementTypeEdt localInstance = (ElementTypeEdt)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ElementTypeEdt getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ElementTypeEdt.ENTITY_NAME);
		      return (ElementTypeEdt) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String elemType() {
    return (String) storedValueForKey("elemType");
  }

  public void setElemType(String value) {
    takeStoredValueForKey(value, "elemType");
  }

  public String libelle() {
    return (String) storedValueForKey("libelle");
  }

  public void setLibelle(String value) {
    takeStoredValueForKey(value, "libelle");
  }


  public static ElementTypeEdt createElementTypeEdt(EOEditingContext editingContext, String elemType
, String libelle
) {
    ElementTypeEdt eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ElementTypeEdt.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ElementTypeEdt.ENTITY_NAME + "' !");
    } else
    {
        eo = (ElementTypeEdt) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setElemType(elemType);
		eo.setLibelle(libelle);
    return eo;
  }

  public static NSArray fetchAllElementTypeEdts(EOEditingContext editingContext) {
    return _ElementTypeEdt.fetchAllElementTypeEdts(editingContext, null);
  }

  public static NSArray fetchAllElementTypeEdts(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ElementTypeEdt.fetchElementTypeEdts(editingContext, null, sortOrderings);
  }

  public static NSArray fetchElementTypeEdts(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ElementTypeEdt.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ElementTypeEdt fetchElementTypeEdt(EOEditingContext editingContext, String keyName, Object value) {
    return _ElementTypeEdt.fetchElementTypeEdt(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ElementTypeEdt fetchElementTypeEdt(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ElementTypeEdt.fetchElementTypeEdts(editingContext, qualifier, null);
    ElementTypeEdt eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ElementTypeEdt)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ElementTypeEdt that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ElementTypeEdt fetchRequiredElementTypeEdt(EOEditingContext editingContext, String keyName, Object value) {
    return _ElementTypeEdt.fetchRequiredElementTypeEdt(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ElementTypeEdt fetchRequiredElementTypeEdt(EOEditingContext editingContext, EOQualifier qualifier) {
    ElementTypeEdt eoObject = _ElementTypeEdt.fetchElementTypeEdt(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ElementTypeEdt that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ElementTypeEdt localInstanceIn(EOEditingContext editingContext, ElementTypeEdt eo) {
    ElementTypeEdt localInstance = (eo == null) ? null : (ElementTypeEdt)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
