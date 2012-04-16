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

// DO NOT EDIT.  Make changes to ResaObjetDroits.java instead.
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

public abstract class _ResaObjetDroits extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ResaObjetDroits";
	public static final String ENTITY_TABLE_NAME = "RESERVATION.RESA_OBJET_DROITS";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cDroits";

	public static final String LIBELLE_KEY = "libelle";

	public static final String LIBELLE_COLKEY = "LIBELLE";

	// Relationships

	// Utilities methods
	  public ResaObjetDroits localInstanceIn(EOEditingContext editingContext) {
	    ResaObjetDroits localInstance = (ResaObjetDroits)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ResaObjetDroits getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ResaObjetDroits.ENTITY_NAME);
		      return (ResaObjetDroits) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String libelle() {
    return (String) storedValueForKey("libelle");
  }

  public void setLibelle(String value) {
    takeStoredValueForKey(value, "libelle");
  }


  public static ResaObjetDroits createResaObjetDroits(EOEditingContext editingContext, String libelle
) {
    ResaObjetDroits eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ResaObjetDroits.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ResaObjetDroits.ENTITY_NAME + "' !");
    } else
    {
        eo = (ResaObjetDroits) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setLibelle(libelle);
    return eo;
  }

  public static NSArray fetchAllResaObjetDroitses(EOEditingContext editingContext) {
    return _ResaObjetDroits.fetchAllResaObjetDroitses(editingContext, null);
  }

  public static NSArray fetchAllResaObjetDroitses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ResaObjetDroits.fetchResaObjetDroitses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchResaObjetDroitses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ResaObjetDroits.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ResaObjetDroits fetchResaObjetDroits(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaObjetDroits.fetchResaObjetDroits(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaObjetDroits fetchResaObjetDroits(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ResaObjetDroits.fetchResaObjetDroitses(editingContext, qualifier, null);
    ResaObjetDroits eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ResaObjetDroits)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ResaObjetDroits that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaObjetDroits fetchRequiredResaObjetDroits(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaObjetDroits.fetchRequiredResaObjetDroits(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaObjetDroits fetchRequiredResaObjetDroits(EOEditingContext editingContext, EOQualifier qualifier) {
    ResaObjetDroits eoObject = _ResaObjetDroits.fetchResaObjetDroits(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ResaObjetDroits that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaObjetDroits localInstanceIn(EOEditingContext editingContext, ResaObjetDroits eo) {
    ResaObjetDroits localInstance = (eo == null) ? null : (ResaObjetDroits)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
