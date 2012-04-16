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

// DO NOT EDIT.  Make changes to TitreMission.java instead.
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

public abstract class _TitreMission extends  EOGenericRecord {
	public static final String ENTITY_NAME = "TitreMission";
	public static final String ENTITY_TABLE_NAME = "jefy_mission.TITRE_MISSION";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "titOrdre";

	public static final String TIT_CTRL_DATES_KEY = "titCtrlDates";
	public static final String TIT_ETAT_KEY = "titEtat";
	public static final String TIT_LIBELLE_KEY = "titLibelle";

	public static final String TIT_CTRL_DATES_COLKEY = "TIT_CTRL_DATES";
	public static final String TIT_ETAT_COLKEY = "TIT_ETAT";
	public static final String TIT_LIBELLE_COLKEY = "TIT_LIBELLE";

	// Relationships

	// Utilities methods
	  public TitreMission localInstanceIn(EOEditingContext editingContext) {
	    TitreMission localInstance = (TitreMission)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static TitreMission getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_TitreMission.ENTITY_NAME);
		      return (TitreMission) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer titCtrlDates() {
    return (Integer) storedValueForKey("titCtrlDates");
  }

  public void setTitCtrlDates(Integer value) {
    takeStoredValueForKey(value, "titCtrlDates");
  }

  public String titEtat() {
    return (String) storedValueForKey("titEtat");
  }

  public void setTitEtat(String value) {
    takeStoredValueForKey(value, "titEtat");
  }

  public String titLibelle() {
    return (String) storedValueForKey("titLibelle");
  }

  public void setTitLibelle(String value) {
    takeStoredValueForKey(value, "titLibelle");
  }


  public static TitreMission createTitreMission(EOEditingContext editingContext, Integer titCtrlDates
, String titEtat
, String titLibelle
) {
    TitreMission eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_TitreMission.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _TitreMission.ENTITY_NAME + "' !");
    } else
    {
        eo = (TitreMission) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setTitCtrlDates(titCtrlDates);
		eo.setTitEtat(titEtat);
		eo.setTitLibelle(titLibelle);
    return eo;
  }

  public static NSArray fetchAllTitreMissions(EOEditingContext editingContext) {
    return _TitreMission.fetchAllTitreMissions(editingContext, null);
  }

  public static NSArray fetchAllTitreMissions(EOEditingContext editingContext, NSArray sortOrderings) {
    return _TitreMission.fetchTitreMissions(editingContext, null, sortOrderings);
  }

  public static NSArray fetchTitreMissions(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_TitreMission.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static TitreMission fetchTitreMission(EOEditingContext editingContext, String keyName, Object value) {
    return _TitreMission.fetchTitreMission(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TitreMission fetchTitreMission(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _TitreMission.fetchTitreMissions(editingContext, qualifier, null);
    TitreMission eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (TitreMission)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one TitreMission that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TitreMission fetchRequiredTitreMission(EOEditingContext editingContext, String keyName, Object value) {
    return _TitreMission.fetchRequiredTitreMission(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static TitreMission fetchRequiredTitreMission(EOEditingContext editingContext, EOQualifier qualifier) {
    TitreMission eoObject = _TitreMission.fetchTitreMission(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no TitreMission that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static TitreMission localInstanceIn(EOEditingContext editingContext, TitreMission eo) {
    TitreMission localInstance = (eo == null) ? null : (TitreMission)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
