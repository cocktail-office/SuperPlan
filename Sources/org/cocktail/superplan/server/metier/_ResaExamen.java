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

// DO NOT EDIT.  Make changes to ResaExamen.java instead.
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

public abstract class _ResaExamen extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ResaExamen";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESA_EXAMEN";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "examKey";

	public static final String GGRP_KEY_KEY = "ggrpKey";

	public static final String GGRP_KEY_COLKEY = "GGRP_KEY";

	// Relationships
	public static final String EXAMEN_ENTETE_KEY = "examenEntete";
	public static final String RESERVATION_KEY = "reservation";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";

	// Utilities methods
	  public ResaExamen localInstanceIn(EOEditingContext editingContext) {
	    ResaExamen localInstance = (ResaExamen)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ResaExamen getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ResaExamen.ENTITY_NAME);
		      return (ResaExamen) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer ggrpKey() {
    return (Integer) storedValueForKey("ggrpKey");
  }

  public void setGgrpKey(Integer value) {
    takeStoredValueForKey(value, "ggrpKey");
  }

  public org.cocktail.superplan.server.metier.ExamenEntete examenEntete() {
    return (org.cocktail.superplan.server.metier.ExamenEntete)storedValueForKey("examenEntete");
  }

  public void setExamenEnteteRelationship(org.cocktail.superplan.server.metier.ExamenEntete value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ExamenEntete oldValue = examenEntete();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "examenEntete");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "examenEntete");
    }
  }
  
  public org.cocktail.superplan.server.metier.Reservation reservation() {
    return (org.cocktail.superplan.server.metier.Reservation)storedValueForKey("reservation");
  }

  public void setReservationRelationship(org.cocktail.superplan.server.metier.Reservation value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.Reservation oldValue = reservation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "reservation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "reservation");
    }
  }
  
  public org.cocktail.superplan.server.metier.ScolGroupeGrp scolGroupeGrp() {
    return (org.cocktail.superplan.server.metier.ScolGroupeGrp)storedValueForKey("scolGroupeGrp");
  }

  public void setScolGroupeGrpRelationship(org.cocktail.superplan.server.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ScolGroupeGrp oldValue = scolGroupeGrp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolGroupeGrp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolGroupeGrp");
    }
  }
  

  public static ResaExamen createResaExamen(EOEditingContext editingContext) {
    ResaExamen eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ResaExamen.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ResaExamen.ENTITY_NAME + "' !");
    } else
    {
        eo = (ResaExamen) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllResaExamens(EOEditingContext editingContext) {
    return _ResaExamen.fetchAllResaExamens(editingContext, null);
  }

  public static NSArray fetchAllResaExamens(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ResaExamen.fetchResaExamens(editingContext, null, sortOrderings);
  }

  public static NSArray fetchResaExamens(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ResaExamen.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ResaExamen fetchResaExamen(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaExamen.fetchResaExamen(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaExamen fetchResaExamen(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ResaExamen.fetchResaExamens(editingContext, qualifier, null);
    ResaExamen eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ResaExamen)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ResaExamen that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaExamen fetchRequiredResaExamen(EOEditingContext editingContext, String keyName, Object value) {
    return _ResaExamen.fetchRequiredResaExamen(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ResaExamen fetchRequiredResaExamen(EOEditingContext editingContext, EOQualifier qualifier) {
    ResaExamen eoObject = _ResaExamen.fetchResaExamen(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ResaExamen that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ResaExamen localInstanceIn(EOEditingContext editingContext, ResaExamen eo) {
    ResaExamen localInstance = (eo == null) ? null : (ResaExamen)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
