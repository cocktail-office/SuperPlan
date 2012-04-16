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

// DO NOT EDIT.  Make changes to VacancesScolaires.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _VacancesScolaires extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VacancesScolaires";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.VACANCES_SCOLAIRES";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "vsKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String VS_COMMENTAIRE_KEY = "vsCommentaire";
	public static final String VS_DATE_DEBUT_KEY = "vsDateDebut";
	public static final String VS_DATE_FIN_KEY = "vsDateFin";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String VS_COMMENTAIRE_COLKEY = "VS_COMMENTAIRE";
	public static final String VS_DATE_DEBUT_COLKEY = "VS_DATE_DEBUT";
	public static final String VS_DATE_FIN_COLKEY = "VS_DATE_FIN";

	// Relationships
	public static final String FORMATION_HABILITATION_KEY = "formationHabilitation";

	// Utilities methods
	  public VacancesScolaires localInstanceIn(EOEditingContext editingContext) {
	    VacancesScolaires localInstance = (VacancesScolaires)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VacancesScolaires getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VacancesScolaires.ENTITY_NAME);
		      return (VacancesScolaires) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public String vsCommentaire() {
    return (String) storedValueForKey("vsCommentaire");
  }

  public void setVsCommentaire(String value) {
    takeStoredValueForKey(value, "vsCommentaire");
  }

  public NSTimestamp vsDateDebut() {
    return (NSTimestamp) storedValueForKey("vsDateDebut");
  }

  public void setVsDateDebut(NSTimestamp value) {
    takeStoredValueForKey(value, "vsDateDebut");
  }

  public NSTimestamp vsDateFin() {
    return (NSTimestamp) storedValueForKey("vsDateFin");
  }

  public void setVsDateFin(NSTimestamp value) {
    takeStoredValueForKey(value, "vsDateFin");
  }

  public org.cocktail.superplan.server.metier.FormationHabilitation formationHabilitation() {
    return (org.cocktail.superplan.server.metier.FormationHabilitation)storedValueForKey("formationHabilitation");
  }

  public void setFormationHabilitationRelationship(org.cocktail.superplan.server.metier.FormationHabilitation value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationHabilitation oldValue = formationHabilitation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationHabilitation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationHabilitation");
    }
  }
  

  public static VacancesScolaires createVacancesScolaires(EOEditingContext editingContext, Integer fannKey
, NSTimestamp vsDateDebut
, NSTimestamp vsDateFin
) {
    VacancesScolaires eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VacancesScolaires.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VacancesScolaires.ENTITY_NAME + "' !");
    } else
    {
        eo = (VacancesScolaires) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setVsDateDebut(vsDateDebut);
		eo.setVsDateFin(vsDateFin);
    return eo;
  }

  public static NSArray fetchAllVacancesScolaireses(EOEditingContext editingContext) {
    return _VacancesScolaires.fetchAllVacancesScolaireses(editingContext, null);
  }

  public static NSArray fetchAllVacancesScolaireses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VacancesScolaires.fetchVacancesScolaireses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVacancesScolaireses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VacancesScolaires.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VacancesScolaires fetchVacancesScolaires(EOEditingContext editingContext, String keyName, Object value) {
    return _VacancesScolaires.fetchVacancesScolaires(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VacancesScolaires fetchVacancesScolaires(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VacancesScolaires.fetchVacancesScolaireses(editingContext, qualifier, null);
    VacancesScolaires eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VacancesScolaires)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VacancesScolaires that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VacancesScolaires fetchRequiredVacancesScolaires(EOEditingContext editingContext, String keyName, Object value) {
    return _VacancesScolaires.fetchRequiredVacancesScolaires(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VacancesScolaires fetchRequiredVacancesScolaires(EOEditingContext editingContext, EOQualifier qualifier) {
    VacancesScolaires eoObject = _VacancesScolaires.fetchVacancesScolaires(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VacancesScolaires that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VacancesScolaires localInstanceIn(EOEditingContext editingContext, VacancesScolaires eo) {
    VacancesScolaires localInstance = (eo == null) ? null : (VacancesScolaires)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
