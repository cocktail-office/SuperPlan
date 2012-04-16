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

// DO NOT EDIT.  Make changes to ReservationSemestre.java instead.
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

public abstract class _ReservationSemestre extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ReservationSemestre";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.RESERVATION_SEMESTRE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "resaSemKey";

	public static final String HCOMP_REC_KEY = "hcompRec";

	public static final String HCOMP_REC_COLKEY = "HCOMP_REC";

	// Relationships
	public static final String RESERVATION_KEY = "reservation";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";
	public static final String SEMESTRE_KEY = "semestre";

	// Utilities methods
	  public ReservationSemestre localInstanceIn(EOEditingContext editingContext) {
	    ReservationSemestre localInstance = (ReservationSemestre)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ReservationSemestre getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ReservationSemestre.ENTITY_NAME);
		      return (ReservationSemestre) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer hcompRec() {
    return (Integer) storedValueForKey("hcompRec");
  }

  public void setHcompRec(Integer value) {
    takeStoredValueForKey(value, "hcompRec");
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
  
  public org.cocktail.superplan.server.metier.MaquetteSemestre semestre() {
    return (org.cocktail.superplan.server.metier.MaquetteSemestre)storedValueForKey("semestre");
  }

  public void setSemestreRelationship(org.cocktail.superplan.server.metier.MaquetteSemestre value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteSemestre oldValue = semestre();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "semestre");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "semestre");
    }
  }
  

  public static ReservationSemestre createReservationSemestre(EOEditingContext editingContext, Integer hcompRec
) {
    ReservationSemestre eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ReservationSemestre.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ReservationSemestre.ENTITY_NAME + "' !");
    } else
    {
        eo = (ReservationSemestre) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setHcompRec(hcompRec);
    return eo;
  }

  public static NSArray fetchAllReservationSemestres(EOEditingContext editingContext) {
    return _ReservationSemestre.fetchAllReservationSemestres(editingContext, null);
  }

  public static NSArray fetchAllReservationSemestres(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ReservationSemestre.fetchReservationSemestres(editingContext, null, sortOrderings);
  }

  public static NSArray fetchReservationSemestres(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ReservationSemestre.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ReservationSemestre fetchReservationSemestre(EOEditingContext editingContext, String keyName, Object value) {
    return _ReservationSemestre.fetchReservationSemestre(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ReservationSemestre fetchReservationSemestre(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ReservationSemestre.fetchReservationSemestres(editingContext, qualifier, null);
    ReservationSemestre eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ReservationSemestre)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ReservationSemestre that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ReservationSemestre fetchRequiredReservationSemestre(EOEditingContext editingContext, String keyName, Object value) {
    return _ReservationSemestre.fetchRequiredReservationSemestre(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ReservationSemestre fetchRequiredReservationSemestre(EOEditingContext editingContext, EOQualifier qualifier) {
    ReservationSemestre eoObject = _ReservationSemestre.fetchReservationSemestre(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ReservationSemestre that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ReservationSemestre localInstanceIn(EOEditingContext editingContext, ReservationSemestre eo) {
    ReservationSemestre localInstance = (eo == null) ? null : (ReservationSemestre)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
