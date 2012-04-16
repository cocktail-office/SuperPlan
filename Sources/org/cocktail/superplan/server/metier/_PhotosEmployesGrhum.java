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

// DO NOT EDIT.  Make changes to PhotosEmployesGrhum.java instead.
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
import com.webobjects.foundation.NSData;

public abstract class _PhotosEmployesGrhum extends  EOGenericRecord {
	public static final String ENTITY_NAME = "PhotoEmploye";
	public static final String ENTITY_TABLE_NAME = "GRHUM.PHOTOS_EMPLOYES_GRHUM";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "noIndividu";

	public static final String IMAGE_KEY = "image";

	public static final String IMAGE_COLKEY = "DATAS_PHOTO";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";

	// Utilities methods
	  public PhotosEmployesGrhum localInstanceIn(EOEditingContext editingContext) {
	    PhotosEmployesGrhum localInstance = (PhotosEmployesGrhum)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static PhotosEmployesGrhum getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_PhotosEmployesGrhum.ENTITY_NAME);
		      return (PhotosEmployesGrhum) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSData image() {
    return (NSData) storedValueForKey("image");
  }

  public void setImage(NSData value) {
    takeStoredValueForKey(value, "image");
  }

  public org.cocktail.superplan.server.metier.IndividuUlr individuUlr() {
    return (org.cocktail.superplan.server.metier.IndividuUlr)storedValueForKey("individuUlr");
  }

  public void setIndividuUlrRelationship(org.cocktail.superplan.server.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.IndividuUlr oldValue = individuUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuUlr");
    }
  }
  

  public static PhotosEmployesGrhum createPhotoEmploye(EOEditingContext editingContext) {
    PhotosEmployesGrhum eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_PhotosEmployesGrhum.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _PhotosEmployesGrhum.ENTITY_NAME + "' !");
    } else
    {
        eo = (PhotosEmployesGrhum) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllPhotoEmployes(EOEditingContext editingContext) {
    return _PhotosEmployesGrhum.fetchAllPhotoEmployes(editingContext, null);
  }

  public static NSArray fetchAllPhotoEmployes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _PhotosEmployesGrhum.fetchPhotoEmployes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchPhotoEmployes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_PhotosEmployesGrhum.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static PhotosEmployesGrhum fetchPhotoEmploye(EOEditingContext editingContext, String keyName, Object value) {
    return _PhotosEmployesGrhum.fetchPhotoEmploye(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static PhotosEmployesGrhum fetchPhotoEmploye(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _PhotosEmployesGrhum.fetchPhotoEmployes(editingContext, qualifier, null);
    PhotosEmployesGrhum eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (PhotosEmployesGrhum)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one PhotoEmploye that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static PhotosEmployesGrhum fetchRequiredPhotoEmploye(EOEditingContext editingContext, String keyName, Object value) {
    return _PhotosEmployesGrhum.fetchRequiredPhotoEmploye(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static PhotosEmployesGrhum fetchRequiredPhotoEmploye(EOEditingContext editingContext, EOQualifier qualifier) {
    PhotosEmployesGrhum eoObject = _PhotosEmployesGrhum.fetchPhotoEmploye(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no PhotoEmploye that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static PhotosEmployesGrhum localInstanceIn(EOEditingContext editingContext, PhotosEmployesGrhum eo) {
    PhotosEmployesGrhum localInstance = (eo == null) ? null : (PhotosEmployesGrhum)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
