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

// DO NOT EDIT.  Make changes to Depositaire.java instead.
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

public abstract class _Depositaire extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Depositaire";
	public static final String ENTITY_TABLE_NAME = "GRHUM.DEPOSITAIRE";

	// Attributes



	// Relationships

	// Utilities methods
	  public Depositaire localInstanceIn(EOEditingContext editingContext) {
	    Depositaire localInstance = (Depositaire)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Depositaire getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Depositaire.ENTITY_NAME);
		      return (Depositaire) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods

  public static Depositaire createDepositaire(EOEditingContext editingContext) {
    Depositaire eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Depositaire.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Depositaire.ENTITY_NAME + "' !");
    } else
    {
        eo = (Depositaire) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllDepositaires(EOEditingContext editingContext) {
    return _Depositaire.fetchAllDepositaires(editingContext, null);
  }

  public static NSArray fetchAllDepositaires(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Depositaire.fetchDepositaires(editingContext, null, sortOrderings);
  }

  public static NSArray fetchDepositaires(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Depositaire.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Depositaire fetchDepositaire(EOEditingContext editingContext, String keyName, Object value) {
    return _Depositaire.fetchDepositaire(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Depositaire fetchDepositaire(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Depositaire.fetchDepositaires(editingContext, qualifier, null);
    Depositaire eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Depositaire)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Depositaire that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Depositaire fetchRequiredDepositaire(EOEditingContext editingContext, String keyName, Object value) {
    return _Depositaire.fetchRequiredDepositaire(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Depositaire fetchRequiredDepositaire(EOEditingContext editingContext, EOQualifier qualifier) {
    Depositaire eoObject = _Depositaire.fetchDepositaire(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Depositaire that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Depositaire localInstanceIn(EOEditingContext editingContext, Depositaire eo) {
    Depositaire localInstance = (eo == null) ? null : (Depositaire)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
