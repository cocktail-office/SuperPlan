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

// DO NOT EDIT.  Make changes to TypeMotifLog.java instead.
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

public abstract class _TypeMotifLog extends  EOGenericRecord {
	public static final String ENTITY_NAME = "TypeMotifLog";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.TYPE_MOTIF_LOG";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "tmlKey";

	public static final String TML_ACTION_KEY = "tmlAction";
	public static final String TML_MOTIF_KEY = "tmlMotif";

	public static final String TML_ACTION_COLKEY = "TML_ACTION";
	public static final String TML_MOTIF_COLKEY = "TML_MOTIF";

	// Relationships

	// Utilities methods
	  public TypeMotifLog localInstanceIn(EOEditingContext editingContext) {
	    TypeMotifLog localInstance = (TypeMotifLog)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static TypeMotifLog getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_TypeMotifLog.ENTITY_NAME);
		      return (TypeMotifLog) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String tmlAction() {
    return (String) storedValueForKey("tmlAction");
  }

  public void setTmlAction(String value) {
    takeStoredValueForKey(value, "tmlAction");
  }

  public String tmlMotif() {
    return (String) storedValueForKey("tmlMotif");
  }

  public void setTmlMotif(String value) {
    takeStoredValueForKey(value, "tmlMotif");
  }


  public static TypeMotifLog createTypeMotifLog(EOEditingContext editingContext) {
    TypeMotifLog eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_TypeMotifLog.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _TypeMotifLog.ENTITY_NAME + "' !");
    } else
    {
        eo = (TypeMotifLog) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllTypeMotifLogs(EOEditingContext editingContext) {
    return _TypeMotifLog.fetchAllTypeMotifLogs(editingContext, null);
  }

  public static NSArray fetchAllTypeMotifLogs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _TypeMotifLog.fetchTypeMotifLogs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchTypeMotifLogs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_TypeMotifLog.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static TypeMotifLog fetchTypeMotifLog(EOEditingContext editingContext, String keyName, Object value) {
    return _TypeMotifLog.fetchTypeMotifLog(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TypeMotifLog fetchTypeMotifLog(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _TypeMotifLog.fetchTypeMotifLogs(editingContext, qualifier, null);
    TypeMotifLog eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (TypeMotifLog)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one TypeMotifLog that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TypeMotifLog fetchRequiredTypeMotifLog(EOEditingContext editingContext, String keyName, Object value) {
    return _TypeMotifLog.fetchRequiredTypeMotifLog(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TypeMotifLog fetchRequiredTypeMotifLog(EOEditingContext editingContext, EOQualifier qualifier) {
    TypeMotifLog eoObject = _TypeMotifLog.fetchTypeMotifLog(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no TypeMotifLog that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TypeMotifLog localInstanceIn(EOEditingContext editingContext, TypeMotifLog eo) {
    TypeMotifLog localInstance = (eo == null) ? null : (TypeMotifLog)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
