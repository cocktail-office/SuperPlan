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

// DO NOT EDIT.  Make changes to ScolTransfertGrp.java instead.
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

public abstract class _ScolTransfertGrp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolTransfertGrp";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_TRANSFERT_GRP";

	// Attributes



	// Relationships
	public static final String NEW_GRP_KEY = "newGrp";
	public static final String OLD_GRP_KEY = "oldGrp";

	// Utilities methods
	  public ScolTransfertGrp localInstanceIn(EOEditingContext editingContext) {
	    ScolTransfertGrp localInstance = (ScolTransfertGrp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolTransfertGrp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolTransfertGrp.ENTITY_NAME);
		      return (ScolTransfertGrp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public org.cocktail.superplan.server.metier.ScolGroupeGrp newGrp() {
    return (org.cocktail.superplan.server.metier.ScolGroupeGrp)storedValueForKey("newGrp");
  }

  public void setNewGrpRelationship(org.cocktail.superplan.server.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ScolGroupeGrp oldValue = newGrp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "newGrp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "newGrp");
    }
  }
  
  public org.cocktail.superplan.server.metier.ScolGroupeGrp oldGrp() {
    return (org.cocktail.superplan.server.metier.ScolGroupeGrp)storedValueForKey("oldGrp");
  }

  public void setOldGrpRelationship(org.cocktail.superplan.server.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ScolGroupeGrp oldValue = oldGrp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "oldGrp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "oldGrp");
    }
  }
  

  public static ScolTransfertGrp createScolTransfertGrp(EOEditingContext editingContext, org.cocktail.superplan.server.metier.ScolGroupeGrp newGrp, org.cocktail.superplan.server.metier.ScolGroupeGrp oldGrp) {
    ScolTransfertGrp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolTransfertGrp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolTransfertGrp.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolTransfertGrp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setNewGrpRelationship(newGrp);
    eo.setOldGrpRelationship(oldGrp);
    return eo;
  }

  public static NSArray fetchAllScolTransfertGrps(EOEditingContext editingContext) {
    return _ScolTransfertGrp.fetchAllScolTransfertGrps(editingContext, null);
  }

  public static NSArray fetchAllScolTransfertGrps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolTransfertGrp.fetchScolTransfertGrps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolTransfertGrps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolTransfertGrp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolTransfertGrp fetchScolTransfertGrp(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolTransfertGrp.fetchScolTransfertGrp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolTransfertGrp fetchScolTransfertGrp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolTransfertGrp.fetchScolTransfertGrps(editingContext, qualifier, null);
    ScolTransfertGrp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolTransfertGrp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolTransfertGrp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolTransfertGrp fetchRequiredScolTransfertGrp(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolTransfertGrp.fetchRequiredScolTransfertGrp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolTransfertGrp fetchRequiredScolTransfertGrp(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolTransfertGrp eoObject = _ScolTransfertGrp.fetchScolTransfertGrp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolTransfertGrp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolTransfertGrp localInstanceIn(EOEditingContext editingContext, ScolTransfertGrp eo) {
    ScolTransfertGrp localInstance = (eo == null) ? null : (ScolTransfertGrp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
