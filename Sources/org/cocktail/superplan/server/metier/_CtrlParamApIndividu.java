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

// DO NOT EDIT.  Make changes to CtrlParamApIndividu.java instead.
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

public abstract class _CtrlParamApIndividu extends  EOGenericRecord {
	public static final String ENTITY_NAME = "CtrlParamApIndividu";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CTRL_PARAM_AP_INDIVIDU";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cpaiKey";



	// Relationships
	public static final String CTRL_PARAM_AP_KEY = "ctrlParamAp";
	public static final String INDIVIDU_KEY = "individu";

	// Utilities methods
	  public CtrlParamApIndividu localInstanceIn(EOEditingContext editingContext) {
	    CtrlParamApIndividu localInstance = (CtrlParamApIndividu)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static CtrlParamApIndividu getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_CtrlParamApIndividu.ENTITY_NAME);
		      return (CtrlParamApIndividu) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public org.cocktail.superplan.server.metier.CtrlParamAp ctrlParamAp() {
    return (org.cocktail.superplan.server.metier.CtrlParamAp)storedValueForKey("ctrlParamAp");
  }

  public void setCtrlParamApRelationship(org.cocktail.superplan.server.metier.CtrlParamAp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.CtrlParamAp oldValue = ctrlParamAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "ctrlParamAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "ctrlParamAp");
    }
  }
  
  public org.cocktail.superplan.server.metier.IndividuUlr individu() {
    return (org.cocktail.superplan.server.metier.IndividuUlr)storedValueForKey("individu");
  }

  public void setIndividuRelationship(org.cocktail.superplan.server.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.IndividuUlr oldValue = individu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individu");
    }
  }
  

  public static CtrlParamApIndividu createCtrlParamApIndividu(EOEditingContext editingContext, org.cocktail.superplan.server.metier.CtrlParamAp ctrlParamAp, org.cocktail.superplan.server.metier.IndividuUlr individu) {
    CtrlParamApIndividu eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_CtrlParamApIndividu.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _CtrlParamApIndividu.ENTITY_NAME + "' !");
    } else
    {
        eo = (CtrlParamApIndividu) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setCtrlParamApRelationship(ctrlParamAp);
    eo.setIndividuRelationship(individu);
    return eo;
  }

  public static NSArray fetchAllCtrlParamApIndividus(EOEditingContext editingContext) {
    return _CtrlParamApIndividu.fetchAllCtrlParamApIndividus(editingContext, null);
  }

  public static NSArray fetchAllCtrlParamApIndividus(EOEditingContext editingContext, NSArray sortOrderings) {
    return _CtrlParamApIndividu.fetchCtrlParamApIndividus(editingContext, null, sortOrderings);
  }

  public static NSArray fetchCtrlParamApIndividus(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_CtrlParamApIndividu.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static CtrlParamApIndividu fetchCtrlParamApIndividu(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamApIndividu.fetchCtrlParamApIndividu(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamApIndividu fetchCtrlParamApIndividu(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _CtrlParamApIndividu.fetchCtrlParamApIndividus(editingContext, qualifier, null);
    CtrlParamApIndividu eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (CtrlParamApIndividu)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one CtrlParamApIndividu that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamApIndividu fetchRequiredCtrlParamApIndividu(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamApIndividu.fetchRequiredCtrlParamApIndividu(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamApIndividu fetchRequiredCtrlParamApIndividu(EOEditingContext editingContext, EOQualifier qualifier) {
    CtrlParamApIndividu eoObject = _CtrlParamApIndividu.fetchCtrlParamApIndividu(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no CtrlParamApIndividu that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamApIndividu localInstanceIn(EOEditingContext editingContext, CtrlParamApIndividu eo) {
    CtrlParamApIndividu localInstance = (eo == null) ? null : (CtrlParamApIndividu)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
