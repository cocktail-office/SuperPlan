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

// DO NOT EDIT.  Make changes to GrhumParametres.java instead.
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

public abstract class _GrhumParametres extends  EOGenericRecord {
	public static final String ENTITY_NAME = "GrhumParametres";
	public static final String ENTITY_TABLE_NAME = "GRHUM.GRHUM_PARAMETRES";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "paramOrdre";

	public static final String PARAM_COMMENTAIRES_KEY = "paramCommentaires";
	public static final String PARAM_KEY_KEY = "paramKey";
	public static final String PARAM_VALUE_KEY = "paramValue";

	public static final String PARAM_COMMENTAIRES_COLKEY = "PARAM_COMMENTAIRES";
	public static final String PARAM_KEY_COLKEY = "PARAM_KEY";
	public static final String PARAM_VALUE_COLKEY = "PARAM_VALUE";

	// Relationships

	// Utilities methods
	  public GrhumParametres localInstanceIn(EOEditingContext editingContext) {
	    GrhumParametres localInstance = (GrhumParametres)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static GrhumParametres getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_GrhumParametres.ENTITY_NAME);
		      return (GrhumParametres) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String paramCommentaires() {
    return (String) storedValueForKey("paramCommentaires");
  }

  public void setParamCommentaires(String value) {
    takeStoredValueForKey(value, "paramCommentaires");
  }

  public String paramKey() {
    return (String) storedValueForKey("paramKey");
  }

  public void setParamKey(String value) {
    takeStoredValueForKey(value, "paramKey");
  }

  public String paramValue() {
    return (String) storedValueForKey("paramValue");
  }

  public void setParamValue(String value) {
    takeStoredValueForKey(value, "paramValue");
  }


  public static GrhumParametres createGrhumParametres(EOEditingContext editingContext) {
    GrhumParametres eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_GrhumParametres.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _GrhumParametres.ENTITY_NAME + "' !");
    } else
    {
        eo = (GrhumParametres) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllGrhumParametreses(EOEditingContext editingContext) {
    return _GrhumParametres.fetchAllGrhumParametreses(editingContext, null);
  }

  public static NSArray fetchAllGrhumParametreses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _GrhumParametres.fetchGrhumParametreses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchGrhumParametreses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_GrhumParametres.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static GrhumParametres fetchGrhumParametres(EOEditingContext editingContext, String keyName, Object value) {
    return _GrhumParametres.fetchGrhumParametres(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static GrhumParametres fetchGrhumParametres(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _GrhumParametres.fetchGrhumParametreses(editingContext, qualifier, null);
    GrhumParametres eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (GrhumParametres)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one GrhumParametres that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static GrhumParametres fetchRequiredGrhumParametres(EOEditingContext editingContext, String keyName, Object value) {
    return _GrhumParametres.fetchRequiredGrhumParametres(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static GrhumParametres fetchRequiredGrhumParametres(EOEditingContext editingContext, EOQualifier qualifier) {
    GrhumParametres eoObject = _GrhumParametres.fetchGrhumParametres(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no GrhumParametres that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static GrhumParametres localInstanceIn(EOEditingContext editingContext, GrhumParametres eo) {
    GrhumParametres localInstance = (eo == null) ? null : (GrhumParametres)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
