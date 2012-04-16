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

// DO NOT EDIT.  Make changes to CtrlParamApSalle.java instead.
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

public abstract class _CtrlParamApSalle extends  EOGenericRecord {
	public static final String ENTITY_NAME = "CtrlParamApSalle";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CTRL_PARAM_AP_SALLE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cpasKey";



	// Relationships
	public static final String CTRL_PARAM_AP_KEY = "ctrlParamAp";
	public static final String SALLE_KEY = "salle";

	// Utilities methods
	  public CtrlParamApSalle localInstanceIn(EOEditingContext editingContext) {
	    CtrlParamApSalle localInstance = (CtrlParamApSalle)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static CtrlParamApSalle getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_CtrlParamApSalle.ENTITY_NAME);
		      return (CtrlParamApSalle) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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
  
  public org.cocktail.superplan.client.metier.Salles salle() {
    return (org.cocktail.superplan.client.metier.Salles)storedValueForKey("salle");
  }

  public void setSalleRelationship(org.cocktail.superplan.client.metier.Salles value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Salles oldValue = salle();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "salle");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "salle");
    }
  }
  

  public static CtrlParamApSalle createCtrlParamApSalle(EOEditingContext editingContext, org.cocktail.superplan.client.metier.CtrlParamAp ctrlParamAp, org.cocktail.superplan.client.metier.Salles salle) {
    CtrlParamApSalle eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_CtrlParamApSalle.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _CtrlParamApSalle.ENTITY_NAME + "' !");
    } else
    {
        eo = (CtrlParamApSalle) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setCtrlParamApRelationship(ctrlParamAp);
    eo.setSalleRelationship(salle);
    return eo;
  }

  public static NSArray fetchAllCtrlParamApSalles(EOEditingContext editingContext) {
    return _CtrlParamApSalle.fetchAllCtrlParamApSalles(editingContext, null);
  }

  public static NSArray fetchAllCtrlParamApSalles(EOEditingContext editingContext, NSArray sortOrderings) {
    return _CtrlParamApSalle.fetchCtrlParamApSalles(editingContext, null, sortOrderings);
  }

  public static NSArray fetchCtrlParamApSalles(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_CtrlParamApSalle.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static CtrlParamApSalle fetchCtrlParamApSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamApSalle.fetchCtrlParamApSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamApSalle fetchCtrlParamApSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _CtrlParamApSalle.fetchCtrlParamApSalles(editingContext, qualifier, null);
    CtrlParamApSalle eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (CtrlParamApSalle)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one CtrlParamApSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamApSalle fetchRequiredCtrlParamApSalle(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamApSalle.fetchRequiredCtrlParamApSalle(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamApSalle fetchRequiredCtrlParamApSalle(EOEditingContext editingContext, EOQualifier qualifier) {
    CtrlParamApSalle eoObject = _CtrlParamApSalle.fetchCtrlParamApSalle(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no CtrlParamApSalle that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamApSalle localInstanceIn(EOEditingContext editingContext, CtrlParamApSalle eo) {
    CtrlParamApSalle localInstance = (eo == null) ? null : (CtrlParamApSalle)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
