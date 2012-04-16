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

// DO NOT EDIT.  Make changes to Personne.java instead.
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

public abstract class _Personne extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Personne";
	public static final String ENTITY_TABLE_NAME = "GRHUM.PERSONNE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "persId";

	public static final String PERS_ID_KEY = "persId";
	public static final String PERS_LC_KEY = "persLc";
	public static final String PERS_LIBELLE_KEY = "persLibelle";
	public static final String PERS_NOMPTR_KEY = "persNomptr";
	public static final String PERS_ORDRE_KEY = "persOrdre";
	public static final String PERS_TYPE_KEY = "persType";

	public static final String PERS_ID_COLKEY = "PERS_ID";
	public static final String PERS_LC_COLKEY = "PERS_LC";
	public static final String PERS_LIBELLE_COLKEY = "PERS_LIBELLE";
	public static final String PERS_NOMPTR_COLKEY = "PERS_NOMPTR";
	public static final String PERS_ORDRE_COLKEY = "PERS_ORDRE";
	public static final String PERS_TYPE_COLKEY = "PERS_TYPE";

	// Relationships
	public static final String TO_INDIVIDU_KEY = "toIndividu";

	// Utilities methods
	  public Personne localInstanceIn(EOEditingContext editingContext) {
	    Personne localInstance = (Personne)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Personne getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Personne.ENTITY_NAME);
		      return (Personne) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public String persLc() {
    return (String) storedValueForKey("persLc");
  }

  public void setPersLc(String value) {
    takeStoredValueForKey(value, "persLc");
  }

  public String persLibelle() {
    return (String) storedValueForKey("persLibelle");
  }

  public void setPersLibelle(String value) {
    takeStoredValueForKey(value, "persLibelle");
  }

  public String persNomptr() {
    return (String) storedValueForKey("persNomptr");
  }

  public void setPersNomptr(String value) {
    takeStoredValueForKey(value, "persNomptr");
  }

  public Integer persOrdre() {
    return (Integer) storedValueForKey("persOrdre");
  }

  public void setPersOrdre(Integer value) {
    takeStoredValueForKey(value, "persOrdre");
  }

  public String persType() {
    return (String) storedValueForKey("persType");
  }

  public void setPersType(String value) {
    takeStoredValueForKey(value, "persType");
  }

  public org.cocktail.superplan.client.metier.IndividuUlr toIndividu() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("toIndividu");
  }

  public void setToIndividuRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = toIndividu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toIndividu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toIndividu");
    }
  }
  

  public static Personne createPersonne(EOEditingContext editingContext, Integer persId
, String persLc
, String persNomptr
) {
    Personne eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Personne.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Personne.ENTITY_NAME + "' !");
    } else
    {
        eo = (Personne) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setPersId(persId);
		eo.setPersLc(persLc);
		eo.setPersNomptr(persNomptr);
    return eo;
  }

  public static NSArray fetchAllPersonnes(EOEditingContext editingContext) {
    return _Personne.fetchAllPersonnes(editingContext, null);
  }

  public static NSArray fetchAllPersonnes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Personne.fetchPersonnes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchPersonnes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Personne.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Personne fetchPersonne(EOEditingContext editingContext, String keyName, Object value) {
    return _Personne.fetchPersonne(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Personne fetchPersonne(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Personne.fetchPersonnes(editingContext, qualifier, null);
    Personne eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Personne)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Personne that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Personne fetchRequiredPersonne(EOEditingContext editingContext, String keyName, Object value) {
    return _Personne.fetchRequiredPersonne(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Personne fetchRequiredPersonne(EOEditingContext editingContext, EOQualifier qualifier) {
    Personne eoObject = _Personne.fetchPersonne(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Personne that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Personne localInstanceIn(EOEditingContext editingContext, Personne eo) {
    Personne localInstance = (eo == null) ? null : (Personne)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
