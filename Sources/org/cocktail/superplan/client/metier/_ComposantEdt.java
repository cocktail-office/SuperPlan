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

// DO NOT EDIT.  Make changes to ComposantEdt.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _ComposantEdt extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ComposantEdt";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.COMPOSANT_EDT";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "compKey";

	public static final String COMP_LIBELLE_KEY = "compLibelle";
	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String TLOC_CODE_KEY = "tlocCode";

	public static final String COMP_LIBELLE_COLKEY = "COMP_LIBELLE";
	public static final String D_CREATION_COLKEY = "D_CREATION";
	public static final String D_MODIFICATION_COLKEY = "D_MODIFICATION";
	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String TLOC_CODE_COLKEY = "TLOC_CODE";

	// Relationships
	public static final String ELEMENT_EDTS_KEY = "elementEdts";
	public static final String INDIVIDU_KEY = "individu";
	public static final String SEMESTRE_KEY = "semestre";
	public static final String TYPE_LOCATION_KEY = "typeLocation";

	// Utilities methods
	  public ComposantEdt localInstanceIn(EOEditingContext editingContext) {
	    ComposantEdt localInstance = (ComposantEdt)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ComposantEdt getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ComposantEdt.ENTITY_NAME);
		      return (ComposantEdt) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String compLibelle() {
    return (String) storedValueForKey("compLibelle");
  }

  public void setCompLibelle(String value) {
    takeStoredValueForKey(value, "compLibelle");
  }

  public NSTimestamp dCreation() {
    return (NSTimestamp) storedValueForKey("dCreation");
  }

  public void setDCreation(NSTimestamp value) {
    takeStoredValueForKey(value, "dCreation");
  }

  public NSTimestamp dModification() {
    return (NSTimestamp) storedValueForKey("dModification");
  }

  public void setDModification(NSTimestamp value) {
    takeStoredValueForKey(value, "dModification");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public String tlocCode() {
    return (String) storedValueForKey("tlocCode");
  }

  public void setTlocCode(String value) {
    takeStoredValueForKey(value, "tlocCode");
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
  
  public org.cocktail.superplan.client.metier.TypeLocation typeLocation() {
    return (org.cocktail.superplan.client.metier.TypeLocation)storedValueForKey("typeLocation");
  }

  public void setTypeLocationRelationship(org.cocktail.superplan.client.metier.TypeLocation value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.TypeLocation oldValue = typeLocation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "typeLocation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "typeLocation");
    }
  }
  
  public NSArray elementEdts() {
    return (NSArray)storedValueForKey("elementEdts");
  }

  public NSArray elementEdts(EOQualifier qualifier) {
    return elementEdts(qualifier, null);
  }

  public NSArray elementEdts(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = elementEdts();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToElementEdtsRelationship(org.cocktail.superplan.client.metier.ElementEdt object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "elementEdts");
  }

  public void removeFromElementEdtsRelationship(org.cocktail.superplan.client.metier.ElementEdt object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "elementEdts");
  }

  public org.cocktail.superplan.client.metier.ElementEdt createElementEdtsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ElementEdt");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "elementEdts");
    return (org.cocktail.superplan.client.metier.ElementEdt) eo;
  }

  public void deleteElementEdtsRelationship(org.cocktail.superplan.client.metier.ElementEdt object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "elementEdts");
    editingContext().deleteObject(object);
  }

  public void deleteAllElementEdtsRelationships() {
    Enumeration objects = elementEdts().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteElementEdtsRelationship((org.cocktail.superplan.client.metier.ElementEdt)objects.nextElement());
    }
  }


  public static ComposantEdt createComposantEdt(EOEditingContext editingContext, NSTimestamp dCreation
, NSTimestamp dModification
, Integer fannKey
, String tlocCode
) {
    ComposantEdt eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ComposantEdt.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ComposantEdt.ENTITY_NAME + "' !");
    } else
    {
        eo = (ComposantEdt) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDCreation(dCreation);
		eo.setDModification(dModification);
		eo.setFannKey(fannKey);
		eo.setTlocCode(tlocCode);
    return eo;
  }

  public static NSArray fetchAllComposantEdts(EOEditingContext editingContext) {
    return _ComposantEdt.fetchAllComposantEdts(editingContext, null);
  }

  public static NSArray fetchAllComposantEdts(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ComposantEdt.fetchComposantEdts(editingContext, null, sortOrderings);
  }

  public static NSArray fetchComposantEdts(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ComposantEdt.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ComposantEdt fetchComposantEdt(EOEditingContext editingContext, String keyName, Object value) {
    return _ComposantEdt.fetchComposantEdt(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ComposantEdt fetchComposantEdt(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ComposantEdt.fetchComposantEdts(editingContext, qualifier, null);
    ComposantEdt eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ComposantEdt)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ComposantEdt that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ComposantEdt fetchRequiredComposantEdt(EOEditingContext editingContext, String keyName, Object value) {
    return _ComposantEdt.fetchRequiredComposantEdt(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ComposantEdt fetchRequiredComposantEdt(EOEditingContext editingContext, EOQualifier qualifier) {
    ComposantEdt eoObject = _ComposantEdt.fetchComposantEdt(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ComposantEdt that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ComposantEdt localInstanceIn(EOEditingContext editingContext, ComposantEdt eo) {
    ComposantEdt localInstance = (eo == null) ? null : (ComposantEdt)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
