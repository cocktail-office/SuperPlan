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

// DO NOT EDIT.  Make changes to ResaTypeObjet.java instead.
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

public abstract class _ResaTypeObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ResaTypeObjet";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESA_TYPE_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "rtoKey";

	public static final String RTO_COMMENTAIRE_KEY = "rtoCommentaire";
	public static final String RTO_LIBELLE_KEY = "rtoLibelle";

	public static final String RTO_COMMENTAIRE_COLKEY = "RTO_COMMENTAIRE";
	public static final String RTO_LIBELLE_COLKEY = "RTO_LIBELLE";

	// Relationships
	public static final String RESA_FAMILLE_OBJET_KEY = "resaFamilleObjet";
	public static final String RESA_OBJETS_KEY = "resaObjets";

	// Utilities methods
	  public ResaTypeObjet localInstanceIn(EOEditingContext editingContext) {
	    ResaTypeObjet localInstance = (ResaTypeObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ResaTypeObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ResaTypeObjet.ENTITY_NAME);
		      return (ResaTypeObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String rtoCommentaire() {
    return (String) storedValueForKey("rtoCommentaire");
  }

  public void setRtoCommentaire(String value) {
    takeStoredValueForKey(value, "rtoCommentaire");
  }

  public String rtoLibelle() {
    return (String) storedValueForKey("rtoLibelle");
  }

  public void setRtoLibelle(String value) {
    takeStoredValueForKey(value, "rtoLibelle");
  }

  public org.cocktail.superplan.client.metier.ResaFamilleObjet resaFamilleObjet() {
    return (org.cocktail.superplan.client.metier.ResaFamilleObjet)storedValueForKey("resaFamilleObjet");
  }

  public void setResaFamilleObjetRelationship(org.cocktail.superplan.client.metier.ResaFamilleObjet value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ResaFamilleObjet oldValue = resaFamilleObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "resaFamilleObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "resaFamilleObjet");
    }
  }
  
  public NSArray resaObjets() {
    return (NSArray)storedValueForKey("resaObjets");
  }

  public NSArray resaObjets(EOQualifier qualifier) {
    return resaObjets(qualifier, null, false);
  }

  public NSArray resaObjets(EOQualifier qualifier, boolean fetch) {
    return resaObjets(qualifier, null, fetch);
  }

  public NSArray resaObjets(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ResaObjet.RESA_TYPE_OBJET_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ResaObjet.fetchResaObjets(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = resaObjets();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToResaObjetsRelationship(org.cocktail.superplan.client.metier.ResaObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "resaObjets");
  }

  public void removeFromResaObjetsRelationship(org.cocktail.superplan.client.metier.ResaObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaObjets");
  }

  public org.cocktail.superplan.client.metier.ResaObjet createResaObjetsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ResaObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "resaObjets");
    return (org.cocktail.superplan.client.metier.ResaObjet) eo;
  }

  public void deleteResaObjetsRelationship(org.cocktail.superplan.client.metier.ResaObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaObjets");
    editingContext().deleteObject(object);
  }

  public void deleteAllResaObjetsRelationships() {
    Enumeration objects = resaObjets().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteResaObjetsRelationship((org.cocktail.superplan.client.metier.ResaObjet)objects.nextElement());
    }
  }


  public static ResaTypeObjet createResaTypeObjet(EOEditingContext editingContext, String rtoLibelle
) {
    ResaTypeObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ResaTypeObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ResaTypeObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (ResaTypeObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setRtoLibelle(rtoLibelle);
    return eo;
  }

  public static NSArray fetchAllResaTypeObjets(EOEditingContext editingContext) {
    return _ResaTypeObjet.fetchAllResaTypeObjets(editingContext, null);
  }

  public static NSArray fetchAllResaTypeObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ResaTypeObjet.fetchResaTypeObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchResaTypeObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ResaTypeObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ResaTypeObjet fetchResaTypeObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaTypeObjet.fetchResaTypeObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaTypeObjet fetchResaTypeObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ResaTypeObjet.fetchResaTypeObjets(editingContext, qualifier, null);
    ResaTypeObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ResaTypeObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ResaTypeObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaTypeObjet fetchRequiredResaTypeObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaTypeObjet.fetchRequiredResaTypeObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaTypeObjet fetchRequiredResaTypeObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    ResaTypeObjet eoObject = _ResaTypeObjet.fetchResaTypeObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ResaTypeObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaTypeObjet localInstanceIn(EOEditingContext editingContext, ResaTypeObjet eo) {
    ResaTypeObjet localInstance = (eo == null) ? null : (ResaTypeObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
