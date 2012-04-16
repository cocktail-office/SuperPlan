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

// DO NOT EDIT.  Make changes to ElementEdt.java instead.
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

public abstract class _ElementEdt extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ElementEdt";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.ELEMENT_EDT";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "eedtKey";

	public static final String ELEM_TYPE_KEY = "elemType";

	public static final String ELEM_TYPE_COLKEY = "ELEM_TYPE";

	// Relationships
	public static final String AP_KEY = "ap";
	public static final String ELEMENT_TYPE_EDT_KEY = "elementTypeEdt";
	public static final String GROUPE_KEY = "groupe";
	public static final String INDIVIDU_KEY = "individu";
	public static final String SALLE_KEY = "salle";

	// Utilities methods
	  public ElementEdt localInstanceIn(EOEditingContext editingContext) {
	    ElementEdt localInstance = (ElementEdt)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ElementEdt getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ElementEdt.ENTITY_NAME);
		      return (ElementEdt) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String elemType() {
    return (String) storedValueForKey("elemType");
  }

  public void setElemType(String value) {
    takeStoredValueForKey(value, "elemType");
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
  
  public org.cocktail.superplan.client.metier.ElementTypeEdt elementTypeEdt() {
    return (org.cocktail.superplan.client.metier.ElementTypeEdt)storedValueForKey("elementTypeEdt");
  }

  public void setElementTypeEdtRelationship(org.cocktail.superplan.client.metier.ElementTypeEdt value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ElementTypeEdt oldValue = elementTypeEdt();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "elementTypeEdt");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "elementTypeEdt");
    }
  }
  
  public org.cocktail.superplan.client.metier.ScolGroupeGrp groupe() {
    return (org.cocktail.superplan.client.metier.ScolGroupeGrp)storedValueForKey("groupe");
  }

  public void setGroupeRelationship(org.cocktail.superplan.client.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ScolGroupeGrp oldValue = groupe();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "groupe");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "groupe");
    }
  }
  
  public org.cocktail.superplan.client.metier.IndividuUlr individu() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individu");
  }

  public void setIndividuRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individu");
    }
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
  

  public static ElementEdt createElementEdt(EOEditingContext editingContext, String elemType
) {
    ElementEdt eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ElementEdt.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ElementEdt.ENTITY_NAME + "' !");
    } else
    {
        eo = (ElementEdt) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setElemType(elemType);
    return eo;
  }

  public static NSArray fetchAllElementEdts(EOEditingContext editingContext) {
    return _ElementEdt.fetchAllElementEdts(editingContext, null);
  }

  public static NSArray fetchAllElementEdts(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ElementEdt.fetchElementEdts(editingContext, null, sortOrderings);
  }

  public static NSArray fetchElementEdts(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ElementEdt.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ElementEdt fetchElementEdt(EOEditingContext editingContext, String keyName, Object value) {
    return _ElementEdt.fetchElementEdt(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ElementEdt fetchElementEdt(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ElementEdt.fetchElementEdts(editingContext, qualifier, null);
    ElementEdt eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ElementEdt)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ElementEdt that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ElementEdt fetchRequiredElementEdt(EOEditingContext editingContext, String keyName, Object value) {
    return _ElementEdt.fetchRequiredElementEdt(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ElementEdt fetchRequiredElementEdt(EOEditingContext editingContext, EOQualifier qualifier) {
    ElementEdt eoObject = _ElementEdt.fetchElementEdt(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ElementEdt that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ElementEdt localInstanceIn(EOEditingContext editingContext, ElementEdt eo) {
    ElementEdt localInstance = (eo == null) ? null : (ElementEdt)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
