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

// DO NOT EDIT.  Make changes to CtrlParamHabilitation.java instead.
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

public abstract class _CtrlParamHabilitation extends  EOGenericRecord {
	public static final String ENTITY_NAME = "CtrlParamHabilitation";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CTRL_PARAM_HABILITATION";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cphKey";

	public static final String CPH_DIM_HEURE_DEB_KEY = "cphDimHeureDeb";
	public static final String CPH_DIM_HEURE_FIN_KEY = "cphDimHeureFin";
	public static final String CPH_JEU_HEURE_DEB_KEY = "cphJeuHeureDeb";
	public static final String CPH_JEU_HEURE_FIN_KEY = "cphJeuHeureFin";
	public static final String CPH_LUN_HEURE_DEB_KEY = "cphLunHeureDeb";
	public static final String CPH_LUN_HEURE_FIN_KEY = "cphLunHeureFin";
	public static final String CPH_MAR_HEURE_DEB_KEY = "cphMarHeureDeb";
	public static final String CPH_MAR_HEURE_FIN_KEY = "cphMarHeureFin";
	public static final String CPH_MER_HEURE_DEB_KEY = "cphMerHeureDeb";
	public static final String CPH_MER_HEURE_FIN_KEY = "cphMerHeureFin";
	public static final String CPH_SAM_HEURE_DEB_KEY = "cphSamHeureDeb";
	public static final String CPH_SAM_HEURE_FIN_KEY = "cphSamHeureFin";
	public static final String CPH_VEN_HEURE_DEB_KEY = "cphVenHeureDeb";
	public static final String CPH_VEN_HEURE_FIN_KEY = "cphVenHeureFin";

	public static final String CPH_DIM_HEURE_DEB_COLKEY = "CPH_DIM_HEURE_DEB";
	public static final String CPH_DIM_HEURE_FIN_COLKEY = "CPH_DIM_HEURE_FIN";
	public static final String CPH_JEU_HEURE_DEB_COLKEY = "CPH_JEU_HEURE_DEB";
	public static final String CPH_JEU_HEURE_FIN_COLKEY = "CPH_JEU_HEURE_FIN";
	public static final String CPH_LUN_HEURE_DEB_COLKEY = "CPH_LUN_HEURE_DEB";
	public static final String CPH_LUN_HEURE_FIN_COLKEY = "CPH_LUN_HEURE_FIN";
	public static final String CPH_MAR_HEURE_DEB_COLKEY = "CPH_MAR_HEURE_DEB";
	public static final String CPH_MAR_HEURE_FIN_COLKEY = "CPH_MAR_HEURE_FIN";
	public static final String CPH_MER_HEURE_DEB_COLKEY = "CPH_MER_HEURE_DEB";
	public static final String CPH_MER_HEURE_FIN_COLKEY = "CPH_MER_HEURE_FIN";
	public static final String CPH_SAM_HEURE_DEB_COLKEY = "CPH_SAM_HEURE_DEB";
	public static final String CPH_SAM_HEURE_FIN_COLKEY = "CPH_SAM_HEURE_FIN";
	public static final String CPH_VEN_HEURE_DEB_COLKEY = "CPH_VEN_HEURE_DEB";
	public static final String CPH_VEN_HEURE_FIN_COLKEY = "CPH_VEN_HEURE_FIN";

	// Relationships
	public static final String FORMATION_HABILITATION_KEY = "formationHabilitation";

	// Utilities methods
	  public CtrlParamHabilitation localInstanceIn(EOEditingContext editingContext) {
	    CtrlParamHabilitation localInstance = (CtrlParamHabilitation)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static CtrlParamHabilitation getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_CtrlParamHabilitation.ENTITY_NAME);
		      return (CtrlParamHabilitation) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer cphDimHeureDeb() {
    return (Integer) storedValueForKey("cphDimHeureDeb");
  }

  public void setCphDimHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cphDimHeureDeb");
  }

  public Integer cphDimHeureFin() {
    return (Integer) storedValueForKey("cphDimHeureFin");
  }

  public void setCphDimHeureFin(Integer value) {
    takeStoredValueForKey(value, "cphDimHeureFin");
  }

  public Integer cphJeuHeureDeb() {
    return (Integer) storedValueForKey("cphJeuHeureDeb");
  }

  public void setCphJeuHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cphJeuHeureDeb");
  }

  public Integer cphJeuHeureFin() {
    return (Integer) storedValueForKey("cphJeuHeureFin");
  }

  public void setCphJeuHeureFin(Integer value) {
    takeStoredValueForKey(value, "cphJeuHeureFin");
  }

  public Integer cphLunHeureDeb() {
    return (Integer) storedValueForKey("cphLunHeureDeb");
  }

  public void setCphLunHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cphLunHeureDeb");
  }

  public Integer cphLunHeureFin() {
    return (Integer) storedValueForKey("cphLunHeureFin");
  }

  public void setCphLunHeureFin(Integer value) {
    takeStoredValueForKey(value, "cphLunHeureFin");
  }

  public Integer cphMarHeureDeb() {
    return (Integer) storedValueForKey("cphMarHeureDeb");
  }

  public void setCphMarHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cphMarHeureDeb");
  }

  public Integer cphMarHeureFin() {
    return (Integer) storedValueForKey("cphMarHeureFin");
  }

  public void setCphMarHeureFin(Integer value) {
    takeStoredValueForKey(value, "cphMarHeureFin");
  }

  public Integer cphMerHeureDeb() {
    return (Integer) storedValueForKey("cphMerHeureDeb");
  }

  public void setCphMerHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cphMerHeureDeb");
  }

  public Integer cphMerHeureFin() {
    return (Integer) storedValueForKey("cphMerHeureFin");
  }

  public void setCphMerHeureFin(Integer value) {
    takeStoredValueForKey(value, "cphMerHeureFin");
  }

  public Integer cphSamHeureDeb() {
    return (Integer) storedValueForKey("cphSamHeureDeb");
  }

  public void setCphSamHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cphSamHeureDeb");
  }

  public Integer cphSamHeureFin() {
    return (Integer) storedValueForKey("cphSamHeureFin");
  }

  public void setCphSamHeureFin(Integer value) {
    takeStoredValueForKey(value, "cphSamHeureFin");
  }

  public Integer cphVenHeureDeb() {
    return (Integer) storedValueForKey("cphVenHeureDeb");
  }

  public void setCphVenHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cphVenHeureDeb");
  }

  public Integer cphVenHeureFin() {
    return (Integer) storedValueForKey("cphVenHeureFin");
  }

  public void setCphVenHeureFin(Integer value) {
    takeStoredValueForKey(value, "cphVenHeureFin");
  }

  public org.cocktail.superplan.client.metier.FormationHabilitation formationHabilitation() {
    return (org.cocktail.superplan.client.metier.FormationHabilitation)storedValueForKey("formationHabilitation");
  }

  public void setFormationHabilitationRelationship(org.cocktail.superplan.client.metier.FormationHabilitation value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.FormationHabilitation oldValue = formationHabilitation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationHabilitation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationHabilitation");
    }
  }
  

  public static CtrlParamHabilitation createCtrlParamHabilitation(EOEditingContext editingContext, org.cocktail.superplan.client.metier.FormationHabilitation formationHabilitation) {
    CtrlParamHabilitation eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_CtrlParamHabilitation.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _CtrlParamHabilitation.ENTITY_NAME + "' !");
    } else
    {
        eo = (CtrlParamHabilitation) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setFormationHabilitationRelationship(formationHabilitation);
    return eo;
  }

  public static NSArray fetchAllCtrlParamHabilitations(EOEditingContext editingContext) {
    return _CtrlParamHabilitation.fetchAllCtrlParamHabilitations(editingContext, null);
  }

  public static NSArray fetchAllCtrlParamHabilitations(EOEditingContext editingContext, NSArray sortOrderings) {
    return _CtrlParamHabilitation.fetchCtrlParamHabilitations(editingContext, null, sortOrderings);
  }

  public static NSArray fetchCtrlParamHabilitations(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_CtrlParamHabilitation.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static CtrlParamHabilitation fetchCtrlParamHabilitation(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamHabilitation.fetchCtrlParamHabilitation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamHabilitation fetchCtrlParamHabilitation(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _CtrlParamHabilitation.fetchCtrlParamHabilitations(editingContext, qualifier, null);
    CtrlParamHabilitation eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (CtrlParamHabilitation)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one CtrlParamHabilitation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamHabilitation fetchRequiredCtrlParamHabilitation(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamHabilitation.fetchRequiredCtrlParamHabilitation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamHabilitation fetchRequiredCtrlParamHabilitation(EOEditingContext editingContext, EOQualifier qualifier) {
    CtrlParamHabilitation eoObject = _CtrlParamHabilitation.fetchCtrlParamHabilitation(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no CtrlParamHabilitation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamHabilitation localInstanceIn(EOEditingContext editingContext, CtrlParamHabilitation eo) {
    CtrlParamHabilitation localInstance = (eo == null) ? null : (CtrlParamHabilitation)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
