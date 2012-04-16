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

// DO NOT EDIT.  Make changes to CategObjet.java instead.
package org.cocktail.superplan.client.metier;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public abstract class _CategObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "CategObjet";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CATEG_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cobOrdre";

	public static final String COB_LIBELLE_KEY = "cobLibelle";

	public static final String COB_LIBELLE_COLKEY = "COB_LIBELLE";

	// Relationships
	public static final String TYPE_OBJET_MAJS_KEY = "typeObjetMajs";
	public static final String TYPE_OBJETS_KEY = "typeObjets";

	// Utilities methods
	  public CategObjet localInstanceIn(EOEditingContext editingContext) {
	    CategObjet localInstance = (CategObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static CategObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_CategObjet.ENTITY_NAME);
		      return (CategObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cobLibelle() {
    return (String) storedValueForKey("cobLibelle");
  }

  public void setCobLibelle(String value) {
    takeStoredValueForKey(value, "cobLibelle");
  }

  public NSArray typeObjetMajs() {
    return (NSArray)storedValueForKey("typeObjetMajs");
  }

  public NSArray typeObjetMajs(EOQualifier qualifier) {
    return typeObjetMajs(qualifier, null, false);
  }

  public NSArray typeObjetMajs(EOQualifier qualifier, boolean fetch) {
    return typeObjetMajs(qualifier, null, fetch);
  }

  public NSArray typeObjetMajs(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.TypeObjetMaj.CATEG_OBJET_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.TypeObjetMaj.fetchTypeObjetMajs(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = typeObjetMajs();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToTypeObjetMajsRelationship(org.cocktail.superplan.client.metier.TypeObjetMaj object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "typeObjetMajs");
  }

  public void removeFromTypeObjetMajsRelationship(org.cocktail.superplan.client.metier.TypeObjetMaj object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "typeObjetMajs");
  }

  public org.cocktail.superplan.client.metier.TypeObjetMaj createTypeObjetMajsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("TypeObjetMaj");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "typeObjetMajs");
    return (org.cocktail.superplan.client.metier.TypeObjetMaj) eo;
  }

  public void deleteTypeObjetMajsRelationship(org.cocktail.superplan.client.metier.TypeObjetMaj object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "typeObjetMajs");
    editingContext().deleteObject(object);
  }

  public void deleteAllTypeObjetMajsRelationships() {
    Enumeration objects = typeObjetMajs().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteTypeObjetMajsRelationship((org.cocktail.superplan.client.metier.TypeObjetMaj)objects.nextElement());
    }
  }

  public NSArray typeObjets() {
    return (NSArray)storedValueForKey("typeObjets");
  }

  public NSArray typeObjets(EOQualifier qualifier) {
    return typeObjets(qualifier, null, false);
  }

  public NSArray typeObjets(EOQualifier qualifier, boolean fetch) {
    return typeObjets(qualifier, null, fetch);
  }

  public NSArray typeObjets(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.TypeObjet.CATEG_OBJET_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.TypeObjet.fetchTypeObjets(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = typeObjets();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToTypeObjetsRelationship(org.cocktail.superplan.client.metier.TypeObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "typeObjets");
  }

  public void removeFromTypeObjetsRelationship(org.cocktail.superplan.client.metier.TypeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "typeObjets");
  }

  public org.cocktail.superplan.client.metier.TypeObjet createTypeObjetsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("TypeObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "typeObjets");
    return (org.cocktail.superplan.client.metier.TypeObjet) eo;
  }

  public void deleteTypeObjetsRelationship(org.cocktail.superplan.client.metier.TypeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "typeObjets");
    editingContext().deleteObject(object);
  }

  public void deleteAllTypeObjetsRelationships() {
    Enumeration objects = typeObjets().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteTypeObjetsRelationship((org.cocktail.superplan.client.metier.TypeObjet)objects.nextElement());
    }
  }


  public static CategObjet createCategObjet(EOEditingContext editingContext, String cobLibelle
) {
    CategObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_CategObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _CategObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (CategObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCobLibelle(cobLibelle);
    return eo;
  }

  public static NSArray fetchAllCategObjets(EOEditingContext editingContext) {
    return _CategObjet.fetchAllCategObjets(editingContext, null);
  }

  public static NSArray fetchAllCategObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _CategObjet.fetchCategObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchCategObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_CategObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static CategObjet fetchCategObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _CategObjet.fetchCategObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CategObjet fetchCategObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _CategObjet.fetchCategObjets(editingContext, qualifier, null);
    CategObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (CategObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one CategObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CategObjet fetchRequiredCategObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _CategObjet.fetchRequiredCategObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CategObjet fetchRequiredCategObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    CategObjet eoObject = _CategObjet.fetchCategObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no CategObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CategObjet localInstanceIn(EOEditingContext editingContext, CategObjet eo) {
    CategObjet localInstance = (eo == null) ? null : (CategObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
