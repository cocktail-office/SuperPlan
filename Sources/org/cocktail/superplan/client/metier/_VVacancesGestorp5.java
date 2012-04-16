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

// DO NOT EDIT.  Make changes to VVacancesGestorp5.java instead.
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

public abstract class _VVacancesGestorp5 extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VVacancesGestorp5";
	public static final String ENTITY_TABLE_NAME = "GRHUM.V_VACANCES_GESTORP5";

	// Attributes

	public static final String CODE_KEY = "code";
	public static final String DATEDEB_KEY = "datedeb";
	public static final String DATEFIN_KEY = "datefin";
	public static final String DUREE_KEY = "duree";
	public static final String HDEB_KEY = "hdeb";
	public static final String HFIN_KEY = "hfin";
	public static final String LIBELLE_KEY = "libelle";
	public static final String NO_INDIVIDU_KEY = "noIndividu";

	public static final String CODE_COLKEY = "CODE";
	public static final String DATEDEB_COLKEY = "DATEDEB";
	public static final String DATEFIN_COLKEY = "DATEFIN";
	public static final String DUREE_COLKEY = "DUREE";
	public static final String HDEB_COLKEY = "HDEB";
	public static final String HFIN_COLKEY = "HFIN";
	public static final String LIBELLE_COLKEY = "LIBELLE";
	public static final String NO_INDIVIDU_COLKEY = "NO_INDIVIDU";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";

	// Utilities methods
	  public VVacancesGestorp5 localInstanceIn(EOEditingContext editingContext) {
	    VVacancesGestorp5 localInstance = (VVacancesGestorp5)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VVacancesGestorp5 getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VVacancesGestorp5.ENTITY_NAME);
		      return (VVacancesGestorp5) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String code() {
    return (String) storedValueForKey("code");
  }

  public void setCode(String value) {
    takeStoredValueForKey(value, "code");
  }

  public NSTimestamp datedeb() {
    return (NSTimestamp) storedValueForKey("datedeb");
  }

  public void setDatedeb(NSTimestamp value) {
    takeStoredValueForKey(value, "datedeb");
  }

  public NSTimestamp datefin() {
    return (NSTimestamp) storedValueForKey("datefin");
  }

  public void setDatefin(NSTimestamp value) {
    takeStoredValueForKey(value, "datefin");
  }

  public Float duree() {
    return (Float) storedValueForKey("duree");
  }

  public void setDuree(Float value) {
    takeStoredValueForKey(value, "duree");
  }

  public Float hdeb() {
    return (Float) storedValueForKey("hdeb");
  }

  public void setHdeb(Float value) {
    takeStoredValueForKey(value, "hdeb");
  }

  public Float hfin() {
    return (Float) storedValueForKey("hfin");
  }

  public void setHfin(Float value) {
    takeStoredValueForKey(value, "hfin");
  }

  public String libelle() {
    return (String) storedValueForKey("libelle");
  }

  public void setLibelle(String value) {
    takeStoredValueForKey(value, "libelle");
  }

  public Integer noIndividu() {
    return (Integer) storedValueForKey("noIndividu");
  }

  public void setNoIndividu(Integer value) {
    takeStoredValueForKey(value, "noIndividu");
  }

  public org.cocktail.superplan.client.metier.IndividuUlr individuUlr() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individuUlr");
  }

  public void setIndividuUlrRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individuUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuUlr");
    }
  }
  

  public static VVacancesGestorp5 createVVacancesGestorp5(EOEditingContext editingContext, NSTimestamp datedeb
, Integer noIndividu
) {
    VVacancesGestorp5 eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VVacancesGestorp5.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VVacancesGestorp5.ENTITY_NAME + "' !");
    } else
    {
        eo = (VVacancesGestorp5) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDatedeb(datedeb);
		eo.setNoIndividu(noIndividu);
    return eo;
  }

  public static NSArray fetchAllVVacancesGestorp5s(EOEditingContext editingContext) {
    return _VVacancesGestorp5.fetchAllVVacancesGestorp5s(editingContext, null);
  }

  public static NSArray fetchAllVVacancesGestorp5s(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VVacancesGestorp5.fetchVVacancesGestorp5s(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVVacancesGestorp5s(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VVacancesGestorp5.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VVacancesGestorp5 fetchVVacancesGestorp5(EOEditingContext editingContext, String keyName, Object value) {
    return _VVacancesGestorp5.fetchVVacancesGestorp5(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VVacancesGestorp5 fetchVVacancesGestorp5(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VVacancesGestorp5.fetchVVacancesGestorp5s(editingContext, qualifier, null);
    VVacancesGestorp5 eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VVacancesGestorp5)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VVacancesGestorp5 that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VVacancesGestorp5 fetchRequiredVVacancesGestorp5(EOEditingContext editingContext, String keyName, Object value) {
    return _VVacancesGestorp5.fetchRequiredVVacancesGestorp5(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VVacancesGestorp5 fetchRequiredVVacancesGestorp5(EOEditingContext editingContext, EOQualifier qualifier) {
    VVacancesGestorp5 eoObject = _VVacancesGestorp5.fetchVVacancesGestorp5(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VVacancesGestorp5 that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VVacancesGestorp5 localInstanceIn(EOEditingContext editingContext, VVacancesGestorp5 eo) {
    VVacancesGestorp5 localInstance = (eo == null) ? null : (VVacancesGestorp5)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
