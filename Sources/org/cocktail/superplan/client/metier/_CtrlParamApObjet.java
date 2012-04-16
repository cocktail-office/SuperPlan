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

// DO NOT EDIT.  Make changes to CtrlParamApObjet.java instead.
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

public abstract class _CtrlParamApObjet extends  EOGenericRecord {
	public static final String ENTITY_NAME = "CtrlParamApObjet";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CTRL_PARAM_AP_OBJET";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cpaoKey";



	// Relationships
	public static final String CTRL_PARAM_AP_KEY = "ctrlParamAp";
	public static final String RESA_OBJET_KEY = "resaObjet";

	// Utilities methods
	  public CtrlParamApObjet localInstanceIn(EOEditingContext editingContext) {
	    CtrlParamApObjet localInstance = (CtrlParamApObjet)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static CtrlParamApObjet getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_CtrlParamApObjet.ENTITY_NAME);
		      return (CtrlParamApObjet) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public org.cocktail.superplan.client.metier.CtrlParamAp ctrlParamAp() {
    return (org.cocktail.superplan.client.metier.CtrlParamAp)storedValueForKey("ctrlParamAp");
  }

  public void setCtrlParamApRelationship(org.cocktail.superplan.client.metier.CtrlParamAp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.CtrlParamAp oldValue = ctrlParamAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "ctrlParamAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "ctrlParamAp");
    }
  }
  
  public org.cocktail.superplan.client.metier.ResaObjet resaObjet() {
    return (org.cocktail.superplan.client.metier.ResaObjet)storedValueForKey("resaObjet");
  }

  public void setResaObjetRelationship(org.cocktail.superplan.client.metier.ResaObjet value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ResaObjet oldValue = resaObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "resaObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "resaObjet");
    }
  }
  

  public static CtrlParamApObjet createCtrlParamApObjet(EOEditingContext editingContext, org.cocktail.superplan.client.metier.CtrlParamAp ctrlParamAp, org.cocktail.superplan.client.metier.ResaObjet resaObjet) {
    CtrlParamApObjet eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_CtrlParamApObjet.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _CtrlParamApObjet.ENTITY_NAME + "' !");
    } else
    {
        eo = (CtrlParamApObjet) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setCtrlParamApRelationship(ctrlParamAp);
    eo.setResaObjetRelationship(resaObjet);
    return eo;
  }

  public static NSArray fetchAllCtrlParamApObjets(EOEditingContext editingContext) {
    return _CtrlParamApObjet.fetchAllCtrlParamApObjets(editingContext, null);
  }

  public static NSArray fetchAllCtrlParamApObjets(EOEditingContext editingContext, NSArray sortOrderings) {
    return _CtrlParamApObjet.fetchCtrlParamApObjets(editingContext, null, sortOrderings);
  }

  public static NSArray fetchCtrlParamApObjets(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_CtrlParamApObjet.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static CtrlParamApObjet fetchCtrlParamApObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamApObjet.fetchCtrlParamApObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamApObjet fetchCtrlParamApObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _CtrlParamApObjet.fetchCtrlParamApObjets(editingContext, qualifier, null);
    CtrlParamApObjet eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (CtrlParamApObjet)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one CtrlParamApObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamApObjet fetchRequiredCtrlParamApObjet(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamApObjet.fetchRequiredCtrlParamApObjet(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamApObjet fetchRequiredCtrlParamApObjet(EOEditingContext editingContext, EOQualifier qualifier) {
    CtrlParamApObjet eoObject = _CtrlParamApObjet.fetchCtrlParamApObjet(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no CtrlParamApObjet that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamApObjet localInstanceIn(EOEditingContext editingContext, CtrlParamApObjet eo) {
    CtrlParamApObjet localInstance = (eo == null) ? null : (CtrlParamApObjet)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
