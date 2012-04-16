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

// DO NOT EDIT.  Make changes to EdtGedexport.java instead.
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

public abstract class _EdtGedexport extends  EOGenericRecord {
	public static final String ENTITY_NAME = "EdtGedexport";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.EDT_GEDEXPORT";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "egKey";

	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";
	public static final String DOC_ID_KEY = "docId";
	public static final String MOT_CLEF_KEY = "motClef";
	public static final String NO_INDIVIDU_KEY = "noIndividu";

	public static final String D_CREATION_COLKEY = "D_CREATION";
	public static final String D_MODIFICATION_COLKEY = "D_MODIFICATION";
	public static final String DOC_ID_COLKEY = "DOC_ID";
	public static final String MOT_CLEF_COLKEY = "MOT_CLEF";
	public static final String NO_INDIVIDU_COLKEY = "NO_INDIVIDU";

	// Relationships

	// Utilities methods
	  public EdtGedexport localInstanceIn(EOEditingContext editingContext) {
	    EdtGedexport localInstance = (EdtGedexport)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static EdtGedexport getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_EdtGedexport.ENTITY_NAME);
		      return (EdtGedexport) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp dCreation() {
    return (NSTimestamp) storedValueForKey("dCreation");
  }

  public void setDCreation(NSTimestamp value) {
    takeStoredValueForKey(value, "dCreation");
  }

  public NSTimestamp dModification() {
    return (NSTimestamp) storedValueForKey("dModification");
  }

  public void setDModification(NSTimestamp value) {
    takeStoredValueForKey(value, "dModification");
  }

  public Integer docId() {
    return (Integer) storedValueForKey("docId");
  }

  public void setDocId(Integer value) {
    takeStoredValueForKey(value, "docId");
  }

  public String motClef() {
    return (String) storedValueForKey("motClef");
  }

  public void setMotClef(String value) {
    takeStoredValueForKey(value, "motClef");
  }

  public Integer noIndividu() {
    return (Integer) storedValueForKey("noIndividu");
  }

  public void setNoIndividu(Integer value) {
    takeStoredValueForKey(value, "noIndividu");
  }


  public static EdtGedexport createEdtGedexport(EOEditingContext editingContext, NSTimestamp dCreation
, NSTimestamp dModification
, Integer docId
, String motClef
, Integer noIndividu
) {
    EdtGedexport eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_EdtGedexport.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _EdtGedexport.ENTITY_NAME + "' !");
    } else
    {
        eo = (EdtGedexport) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDCreation(dCreation);
		eo.setDModification(dModification);
		eo.setDocId(docId);
		eo.setMotClef(motClef);
		eo.setNoIndividu(noIndividu);
    return eo;
  }

  public static NSArray fetchAllEdtGedexports(EOEditingContext editingContext) {
    return _EdtGedexport.fetchAllEdtGedexports(editingContext, null);
  }

  public static NSArray fetchAllEdtGedexports(EOEditingContext editingContext, NSArray sortOrderings) {
    return _EdtGedexport.fetchEdtGedexports(editingContext, null, sortOrderings);
  }

  public static NSArray fetchEdtGedexports(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_EdtGedexport.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static EdtGedexport fetchEdtGedexport(EOEditingContext editingContext, String keyName, Object value) {
    return _EdtGedexport.fetchEdtGedexport(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static EdtGedexport fetchEdtGedexport(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _EdtGedexport.fetchEdtGedexports(editingContext, qualifier, null);
    EdtGedexport eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (EdtGedexport)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one EdtGedexport that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static EdtGedexport fetchRequiredEdtGedexport(EOEditingContext editingContext, String keyName, Object value) {
    return _EdtGedexport.fetchRequiredEdtGedexport(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static EdtGedexport fetchRequiredEdtGedexport(EOEditingContext editingContext, EOQualifier qualifier) {
    EdtGedexport eoObject = _EdtGedexport.fetchEdtGedexport(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no EdtGedexport that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static EdtGedexport localInstanceIn(EOEditingContext editingContext, EdtGedexport eo) {
    EdtGedexport localInstance = (eo == null) ? null : (EdtGedexport)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
