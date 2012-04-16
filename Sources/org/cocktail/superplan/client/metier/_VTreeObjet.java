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

// DO NOT EDIT.  Make changes to VTreeObjet.java instead.
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

public abstract class _VTreeObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VTreeObjet";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_TREE_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cle";

	public static final String CLE_KEY = "cle";
	public static final String CLE_PARENT_KEY = "cleParent";
	public static final String LIBELLE_KEY = "libelle";
	public static final String NIVEAU_KEY = "niveau";

	public static final String CLE_COLKEY = "CLE";
	public static final String CLE_PARENT_COLKEY = "CLE_PARENT";
	public static final String LIBELLE_COLKEY = "LIBELLE";
	public static final String NIVEAU_COLKEY = "NIVEAU";

	// Relationships
	public static final String RESA_FAMILLE_OBJET_KEY = "resaFamilleObjet";
	public static final String RESA_OBJET_KEY = "resaObjet";
	public static final String RESA_TYPE_OBJET_KEY = "resaTypeObjet";

	// Utilities methods
	  public VTreeObjet localInstanceIn(EOEditingContext editingContext) {
	    VTreeObjet localInstance = (VTreeObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VTreeObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VTreeObjet.ENTITY_NAME);
		      return (VTreeObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer cle() {
    return (Integer) storedValueForKey("cle");
  }

  public void setCle(Integer value) {
    takeStoredValueForKey(value, "cle");
  }

  public Integer cleParent() {
    return (Integer) storedValueForKey("cleParent");
  }

  public void setCleParent(Integer value) {
    takeStoredValueForKey(value, "cleParent");
  }

  public String libelle() {
    return (String) storedValueForKey("libelle");
  }

  public void setLibelle(String value) {
    takeStoredValueForKey(value, "libelle");
  }

  public Integer niveau() {
    return (Integer) storedValueForKey("niveau");
  }

  public void setNiveau(Integer value) {
    takeStoredValueForKey(value, "niveau");
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
  
  public org.cocktail.superplan.client.metier.ResaObjet resaObjet() {
    return (org.cocktail.superplan.client.metier.ResaObjet)storedValueForKey("resaObjet");
  }

  public void setResaObjetRelationship(org.cocktail.superplan.client.metier.ResaObjet value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ResaObjet oldValue = resaObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "resaObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "resaObjet");
    }
  }
  
  public org.cocktail.superplan.client.metier.ResaTypeObjet resaTypeObjet() {
    return (org.cocktail.superplan.client.metier.ResaTypeObjet)storedValueForKey("resaTypeObjet");
  }

  public void setResaTypeObjetRelationship(org.cocktail.superplan.client.metier.ResaTypeObjet value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ResaTypeObjet oldValue = resaTypeObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "resaTypeObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "resaTypeObjet");
    }
  }
  

  public static VTreeObjet createVTreeObjet(EOEditingContext editingContext, Integer cle
, Integer niveau
) {
    VTreeObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VTreeObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VTreeObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (VTreeObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCle(cle);
		eo.setNiveau(niveau);
    return eo;
  }

  public static NSArray fetchAllVTreeObjets(EOEditingContext editingContext) {
    return _VTreeObjet.fetchAllVTreeObjets(editingContext, null);
  }

  public static NSArray fetchAllVTreeObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VTreeObjet.fetchVTreeObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVTreeObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VTreeObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VTreeObjet fetchVTreeObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _VTreeObjet.fetchVTreeObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VTreeObjet fetchVTreeObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VTreeObjet.fetchVTreeObjets(editingContext, qualifier, null);
    VTreeObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VTreeObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VTreeObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VTreeObjet fetchRequiredVTreeObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _VTreeObjet.fetchRequiredVTreeObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VTreeObjet fetchRequiredVTreeObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    VTreeObjet eoObject = _VTreeObjet.fetchVTreeObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VTreeObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VTreeObjet localInstanceIn(EOEditingContext editingContext, VTreeObjet eo) {
    VTreeObjet localInstance = (eo == null) ? null : (VTreeObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
