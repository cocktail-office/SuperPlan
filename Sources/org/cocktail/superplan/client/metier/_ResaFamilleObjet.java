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

// DO NOT EDIT.  Make changes to ResaFamilleObjet.java instead.
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

public abstract class _ResaFamilleObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ResaFamilleObjet";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESA_FAMILLE_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "rfoKey";

	public static final String RFO_COMMENTAIRE_KEY = "rfoCommentaire";
	public static final String RFO_LIBELLE_KEY = "rfoLibelle";

	public static final String RFO_COMMENTAIRE_COLKEY = "RFO_COMMENTAIRE";
	public static final String RFO_LIBELLE_COLKEY = "RFO_LIBELLE";

	// Relationships
	public static final String RESA_TYPES_OBJET_KEY = "resaTypesObjet";

	// Utilities methods
	  public ResaFamilleObjet localInstanceIn(EOEditingContext editingContext) {
	    ResaFamilleObjet localInstance = (ResaFamilleObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ResaFamilleObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ResaFamilleObjet.ENTITY_NAME);
		      return (ResaFamilleObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String rfoCommentaire() {
    return (String) storedValueForKey("rfoCommentaire");
  }

  public void setRfoCommentaire(String value) {
    takeStoredValueForKey(value, "rfoCommentaire");
  }

  public String rfoLibelle() {
    return (String) storedValueForKey("rfoLibelle");
  }

  public void setRfoLibelle(String value) {
    takeStoredValueForKey(value, "rfoLibelle");
  }

  public NSArray resaTypesObjet() {
    return (NSArray)storedValueForKey("resaTypesObjet");
  }

  public NSArray resaTypesObjet(EOQualifier qualifier) {
    return resaTypesObjet(qualifier, null, false);
  }

  public NSArray resaTypesObjet(EOQualifier qualifier, boolean fetch) {
    return resaTypesObjet(qualifier, null, fetch);
  }

  public NSArray resaTypesObjet(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ResaTypeObjet.RESA_FAMILLE_OBJET_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ResaTypeObjet.fetchResaTypeObjets(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = resaTypesObjet();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToResaTypesObjetRelationship(org.cocktail.superplan.client.metier.ResaTypeObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "resaTypesObjet");
  }

  public void removeFromResaTypesObjetRelationship(org.cocktail.superplan.client.metier.ResaTypeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaTypesObjet");
  }

  public org.cocktail.superplan.client.metier.ResaTypeObjet createResaTypesObjetRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ResaTypeObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "resaTypesObjet");
    return (org.cocktail.superplan.client.metier.ResaTypeObjet) eo;
  }

  public void deleteResaTypesObjetRelationship(org.cocktail.superplan.client.metier.ResaTypeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaTypesObjet");
    editingContext().deleteObject(object);
  }

  public void deleteAllResaTypesObjetRelationships() {
    Enumeration objects = resaTypesObjet().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteResaTypesObjetRelationship((org.cocktail.superplan.client.metier.ResaTypeObjet)objects.nextElement());
    }
  }


  public static ResaFamilleObjet createResaFamilleObjet(EOEditingContext editingContext, String rfoLibelle
) {
    ResaFamilleObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ResaFamilleObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ResaFamilleObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (ResaFamilleObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setRfoLibelle(rfoLibelle);
    return eo;
  }

  public static NSArray fetchAllResaFamilleObjets(EOEditingContext editingContext) {
    return _ResaFamilleObjet.fetchAllResaFamilleObjets(editingContext, null);
  }

  public static NSArray fetchAllResaFamilleObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ResaFamilleObjet.fetchResaFamilleObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchResaFamilleObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ResaFamilleObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ResaFamilleObjet fetchResaFamilleObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaFamilleObjet.fetchResaFamilleObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaFamilleObjet fetchResaFamilleObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ResaFamilleObjet.fetchResaFamilleObjets(editingContext, qualifier, null);
    ResaFamilleObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ResaFamilleObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ResaFamilleObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaFamilleObjet fetchRequiredResaFamilleObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaFamilleObjet.fetchRequiredResaFamilleObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaFamilleObjet fetchRequiredResaFamilleObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    ResaFamilleObjet eoObject = _ResaFamilleObjet.fetchResaFamilleObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ResaFamilleObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaFamilleObjet localInstanceIn(EOEditingContext editingContext, ResaFamilleObjet eo) {
    ResaFamilleObjet localInstance = (eo == null) ? null : (ResaFamilleObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
