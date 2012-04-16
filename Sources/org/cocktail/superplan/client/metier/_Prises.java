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

// DO NOT EDIT.  Make changes to Prises.java instead.
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

public abstract class _Prises extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Prises";
	public static final String ENTITY_TABLE_NAME = "SALLE.PRISES";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "priCode";

	public static final String PRI_ORDRE_KEY = "priOrdre";
	public static final String PRI_TYPE_KEY = "priType";

	public static final String PRI_ORDRE_COLKEY = "PRI_ORDRE";
	public static final String PRI_TYPE_COLKEY = "PRI_TYPE";

	// Relationships

	// Utilities methods
	  public Prises localInstanceIn(EOEditingContext editingContext) {
	    Prises localInstance = (Prises)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Prises getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Prises.ENTITY_NAME);
		      return (Prises) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer priOrdre() {
    return (Integer) storedValueForKey("priOrdre");
  }

  public void setPriOrdre(Integer value) {
    takeStoredValueForKey(value, "priOrdre");
  }

  public String priType() {
    return (String) storedValueForKey("priType");
  }

  public void setPriType(String value) {
    takeStoredValueForKey(value, "priType");
  }


  public static Prises createPrises(EOEditingContext editingContext) {
    Prises eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Prises.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Prises.ENTITY_NAME + "' !");
    } else
    {
        eo = (Prises) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllPriseses(EOEditingContext editingContext) {
    return _Prises.fetchAllPriseses(editingContext, null);
  }

  public static NSArray fetchAllPriseses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Prises.fetchPriseses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchPriseses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Prises.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Prises fetchPrises(EOEditingContext editingContext, String keyName, Object value) {
    return _Prises.fetchPrises(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Prises fetchPrises(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Prises.fetchPriseses(editingContext, qualifier, null);
    Prises eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Prises)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Prises that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Prises fetchRequiredPrises(EOEditingContext editingContext, String keyName, Object value) {
    return _Prises.fetchRequiredPrises(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Prises fetchRequiredPrises(EOEditingContext editingContext, EOQualifier qualifier) {
    Prises eoObject = _Prises.fetchPrises(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Prises that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Prises localInstanceIn(EOEditingContext editingContext, Prises eo) {
    Prises localInstance = (eo == null) ? null : (Prises)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
