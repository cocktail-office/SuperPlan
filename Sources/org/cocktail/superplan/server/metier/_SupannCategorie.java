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

// DO NOT EDIT.  Make changes to SupannCategorie.java instead.
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

public abstract class _SupannCategorie extends  EOGenericRecord {
	public static final String ENTITY_NAME = "SupannCategorie";
	public static final String ENTITY_TABLE_NAME = "GRHUM.SUPANN_CATEGORIE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "catNumero";

	public static final String CAT_LIBELLE_KEY = "catLibelle";
	public static final String CAT_LIBELLE2_KEY = "catLibelle2";
	public static final String CAT_LISTE_ROUGE_KEY = "catListeRouge";
	public static final String CAT_NUMERO_KEY = "catNumero";

	public static final String CAT_LIBELLE_COLKEY = "CAT_LIBELLE";
	public static final String CAT_LIBELLE2_COLKEY = "CAT_LIBELLE2";
	public static final String CAT_LISTE_ROUGE_COLKEY = "CAT_LISTE_ROUGE";
	public static final String CAT_NUMERO_COLKEY = "CAT_NUMERO";

	// Relationships

	// Utilities methods
	  public SupannCategorie localInstanceIn(EOEditingContext editingContext) {
	    SupannCategorie localInstance = (SupannCategorie)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static SupannCategorie getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_SupannCategorie.ENTITY_NAME);
		      return (SupannCategorie) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String catLibelle() {
    return (String) storedValueForKey("catLibelle");
  }

  public void setCatLibelle(String value) {
    takeStoredValueForKey(value, "catLibelle");
  }

  public String catLibelle2() {
    return (String) storedValueForKey("catLibelle2");
  }

  public void setCatLibelle2(String value) {
    takeStoredValueForKey(value, "catLibelle2");
  }

  public String catListeRouge() {
    return (String) storedValueForKey("catListeRouge");
  }

  public void setCatListeRouge(String value) {
    takeStoredValueForKey(value, "catListeRouge");
  }

  public Integer catNumero() {
    return (Integer) storedValueForKey("catNumero");
  }

  public void setCatNumero(Integer value) {
    takeStoredValueForKey(value, "catNumero");
  }


  public static SupannCategorie createSupannCategorie(EOEditingContext editingContext, Integer catNumero
) {
    SupannCategorie eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_SupannCategorie.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _SupannCategorie.ENTITY_NAME + "' !");
    } else
    {
        eo = (SupannCategorie) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCatNumero(catNumero);
    return eo;
  }

  public static NSArray fetchAllSupannCategories(EOEditingContext editingContext) {
    return _SupannCategorie.fetchAllSupannCategories(editingContext, null);
  }

  public static NSArray fetchAllSupannCategories(EOEditingContext editingContext, NSArray sortOrderings) {
    return _SupannCategorie.fetchSupannCategories(editingContext, null, sortOrderings);
  }

  public static NSArray fetchSupannCategories(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_SupannCategorie.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static SupannCategorie fetchSupannCategorie(EOEditingContext editingContext, String keyName, Object value) {
    return _SupannCategorie.fetchSupannCategorie(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static SupannCategorie fetchSupannCategorie(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _SupannCategorie.fetchSupannCategories(editingContext, qualifier, null);
    SupannCategorie eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (SupannCategorie)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SupannCategorie that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static SupannCategorie fetchRequiredSupannCategorie(EOEditingContext editingContext, String keyName, Object value) {
    return _SupannCategorie.fetchRequiredSupannCategorie(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static SupannCategorie fetchRequiredSupannCategorie(EOEditingContext editingContext, EOQualifier qualifier) {
    SupannCategorie eoObject = _SupannCategorie.fetchSupannCategorie(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SupannCategorie that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static SupannCategorie localInstanceIn(EOEditingContext editingContext, SupannCategorie eo) {
    SupannCategorie localInstance = (eo == null) ? null : (SupannCategorie)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
