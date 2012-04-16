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

// DO NOT EDIT.  Make changes to FournisUlr.java instead.
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

public abstract class _FournisUlr extends  EOGenericRecord {
	public static final String ENTITY_NAME = "FournisUlr";
	public static final String ENTITY_TABLE_NAME = "GRHUM.FOURNIS_ULR";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "fouOrdre";

	public static final String PERS_ID_KEY = "persId";

	public static final String PERS_ID_COLKEY = "PERS_ID";

	// Relationships

	// Utilities methods
	  public FournisUlr localInstanceIn(EOEditingContext editingContext) {
	    FournisUlr localInstance = (FournisUlr)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static FournisUlr getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_FournisUlr.ENTITY_NAME);
		      return (FournisUlr) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }


  public static FournisUlr createFournisUlr(EOEditingContext editingContext, Integer persId
) {
    FournisUlr eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_FournisUlr.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _FournisUlr.ENTITY_NAME + "' !");
    } else
    {
        eo = (FournisUlr) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setPersId(persId);
    return eo;
  }

  public static NSArray fetchAllFournisUlrs(EOEditingContext editingContext) {
    return _FournisUlr.fetchAllFournisUlrs(editingContext, null);
  }

  public static NSArray fetchAllFournisUlrs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _FournisUlr.fetchFournisUlrs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchFournisUlrs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_FournisUlr.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static FournisUlr fetchFournisUlr(EOEditingContext editingContext, String keyName, Object value) {
    return _FournisUlr.fetchFournisUlr(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FournisUlr fetchFournisUlr(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _FournisUlr.fetchFournisUlrs(editingContext, qualifier, null);
    FournisUlr eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (FournisUlr)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one FournisUlr that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FournisUlr fetchRequiredFournisUlr(EOEditingContext editingContext, String keyName, Object value) {
    return _FournisUlr.fetchRequiredFournisUlr(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FournisUlr fetchRequiredFournisUlr(EOEditingContext editingContext, EOQualifier qualifier) {
    FournisUlr eoObject = _FournisUlr.fetchFournisUlr(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no FournisUlr that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FournisUlr localInstanceIn(EOEditingContext editingContext, FournisUlr eo) {
    FournisUlr localInstance = (eo == null) ? null : (FournisUlr)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
