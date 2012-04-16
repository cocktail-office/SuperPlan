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

// DO NOT EDIT.  Make changes to ResaDate.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _ResaDate extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ResaDate";
	public static final String ENTITY_TABLE_NAME = "RESERVATION.RESA_DATE";

	// Attributes

	public static final String RESA_DEBUT_KEY = "resaDebut";
	public static final String RESA_FIN_KEY = "resaFin";
	public static final String RESA_POS_DEBUT_KEY = "resaPosDebut";
	public static final String RESA_POS_FIN_KEY = "resaPosFin";

	public static final String RESA_DEBUT_COLKEY = "RESA_DEBUT";
	public static final String RESA_FIN_COLKEY = "RESA_FIN";
	public static final String RESA_POS_DEBUT_COLKEY = "RESA_POS_DEBUT";
	public static final String RESA_POS_FIN_COLKEY = "RESA_POS_FIN";

	// Relationships

	// Utilities methods
	  public ResaDate localInstanceIn(EOEditingContext editingContext) {
	    ResaDate localInstance = (ResaDate)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ResaDate getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ResaDate.ENTITY_NAME);
		      return (ResaDate) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp resaDebut() {
    return (NSTimestamp) storedValueForKey("resaDebut");
  }

  public void setResaDebut(NSTimestamp value) {
    takeStoredValueForKey(value, "resaDebut");
  }

  public NSTimestamp resaFin() {
    return (NSTimestamp) storedValueForKey("resaFin");
  }

  public void setResaFin(NSTimestamp value) {
    takeStoredValueForKey(value, "resaFin");
  }

  public Integer resaPosDebut() {
    return (Integer) storedValueForKey("resaPosDebut");
  }

  public void setResaPosDebut(Integer value) {
    takeStoredValueForKey(value, "resaPosDebut");
  }

  public Integer resaPosFin() {
    return (Integer) storedValueForKey("resaPosFin");
  }

  public void setResaPosFin(Integer value) {
    takeStoredValueForKey(value, "resaPosFin");
  }


  public static ResaDate createResaDate(EOEditingContext editingContext, NSTimestamp resaDebut
, NSTimestamp resaFin
) {
    ResaDate eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ResaDate.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ResaDate.ENTITY_NAME + "' !");
    } else
    {
        eo = (ResaDate) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setResaDebut(resaDebut);
		eo.setResaFin(resaFin);
    return eo;
  }

  public static NSArray fetchAllResaDates(EOEditingContext editingContext) {
    return _ResaDate.fetchAllResaDates(editingContext, null);
  }

  public static NSArray fetchAllResaDates(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ResaDate.fetchResaDates(editingContext, null, sortOrderings);
  }

  public static NSArray fetchResaDates(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ResaDate.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ResaDate fetchResaDate(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaDate.fetchResaDate(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaDate fetchResaDate(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ResaDate.fetchResaDates(editingContext, qualifier, null);
    ResaDate eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ResaDate)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ResaDate that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaDate fetchRequiredResaDate(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaDate.fetchRequiredResaDate(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaDate fetchRequiredResaDate(EOEditingContext editingContext, EOQualifier qualifier) {
    ResaDate eoObject = _ResaDate.fetchResaDate(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ResaDate that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaDate localInstanceIn(EOEditingContext editingContext, ResaDate eo) {
    ResaDate localInstance = (eo == null) ? null : (ResaDate)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
