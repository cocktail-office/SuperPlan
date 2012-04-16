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

// DO NOT EDIT.  Make changes to Messages.java instead.
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

public abstract class _Messages extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Messages";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.MESSAGES";

	// Attributes

	public static final String MSG_LIB_KEY = "msgLib";
	public static final String MSG_REPEAT_KEY = "msgRepeat";

	public static final String MSG_LIB_COLKEY = "MSG_LIB";
	public static final String MSG_REPEAT_COLKEY = "MSG_REPEAT";

	// Relationships

	// Utilities methods
	  public Messages localInstanceIn(EOEditingContext editingContext) {
	    Messages localInstance = (Messages)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Messages getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Messages.ENTITY_NAME);
		      return (Messages) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String msgLib() {
    return (String) storedValueForKey("msgLib");
  }

  public void setMsgLib(String value) {
    takeStoredValueForKey(value, "msgLib");
  }

  public String msgRepeat() {
    return (String) storedValueForKey("msgRepeat");
  }

  public void setMsgRepeat(String value) {
    takeStoredValueForKey(value, "msgRepeat");
  }


  public static Messages createMessages(EOEditingContext editingContext, String msgLib
, String msgRepeat
) {
    Messages eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Messages.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Messages.ENTITY_NAME + "' !");
    } else
    {
        eo = (Messages) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setMsgLib(msgLib);
		eo.setMsgRepeat(msgRepeat);
    return eo;
  }

  public static NSArray fetchAllMessageses(EOEditingContext editingContext) {
    return _Messages.fetchAllMessageses(editingContext, null);
  }

  public static NSArray fetchAllMessageses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Messages.fetchMessageses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMessageses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Messages.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Messages fetchMessages(EOEditingContext editingContext, String keyName, Object value) {
    return _Messages.fetchMessages(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Messages fetchMessages(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Messages.fetchMessageses(editingContext, qualifier, null);
    Messages eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Messages)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Messages that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Messages fetchRequiredMessages(EOEditingContext editingContext, String keyName, Object value) {
    return _Messages.fetchRequiredMessages(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Messages fetchRequiredMessages(EOEditingContext editingContext, EOQualifier qualifier) {
    Messages eoObject = _Messages.fetchMessages(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Messages that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Messages localInstanceIn(EOEditingContext editingContext, Messages eo) {
    Messages localInstance = (eo == null) ? null : (Messages)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
