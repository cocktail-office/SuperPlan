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

// DO NOT EDIT.  Make changes to CtrlParamAp.java instead.
package org.cocktail.superplan.client.metier;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public abstract class _CtrlParamAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "CtrlParamAp";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CTRL_PARAM_AP";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cpaKey";

	public static final String CPA_DIM_HEURE_DEB_KEY = "cpaDimHeureDeb";
	public static final String CPA_DIM_HEURE_FIN_KEY = "cpaDimHeureFin";
	public static final String CPA_DUREE_KEY = "cpaDuree";
	public static final String CPA_JEU_HEURE_DEB_KEY = "cpaJeuHeureDeb";
	public static final String CPA_JEU_HEURE_FIN_KEY = "cpaJeuHeureFin";
	public static final String CPA_LUN_HEURE_DEB_KEY = "cpaLunHeureDeb";
	public static final String CPA_LUN_HEURE_FIN_KEY = "cpaLunHeureFin";
	public static final String CPA_MAR_HEURE_DEB_KEY = "cpaMarHeureDeb";
	public static final String CPA_MAR_HEURE_FIN_KEY = "cpaMarHeureFin";
	public static final String CPA_MER_HEURE_DEB_KEY = "cpaMerHeureDeb";
	public static final String CPA_MER_HEURE_FIN_KEY = "cpaMerHeureFin";
	public static final String CPA_SAM_HEURE_DEB_KEY = "cpaSamHeureDeb";
	public static final String CPA_SAM_HEURE_FIN_KEY = "cpaSamHeureFin";
	public static final String CPA_VEN_HEURE_DEB_KEY = "cpaVenHeureDeb";
	public static final String CPA_VEN_HEURE_FIN_KEY = "cpaVenHeureFin";

	public static final String CPA_DIM_HEURE_DEB_COLKEY = "CPA_DIM_HEURE_DEB";
	public static final String CPA_DIM_HEURE_FIN_COLKEY = "CPA_DIM_HEURE_FIN";
	public static final String CPA_DUREE_COLKEY = "CPA_DUREE";
	public static final String CPA_JEU_HEURE_DEB_COLKEY = "CPA_JEU_HEURE_DEB";
	public static final String CPA_JEU_HEURE_FIN_COLKEY = "CPA_JEU_HEURE_FIN";
	public static final String CPA_LUN_HEURE_DEB_COLKEY = "CPA_LUN_HEURE_DEB";
	public static final String CPA_LUN_HEURE_FIN_COLKEY = "CPA_LUN_HEURE_FIN";
	public static final String CPA_MAR_HEURE_DEB_COLKEY = "CPA_MAR_HEURE_DEB";
	public static final String CPA_MAR_HEURE_FIN_COLKEY = "CPA_MAR_HEURE_FIN";
	public static final String CPA_MER_HEURE_DEB_COLKEY = "CPA_MER_HEURE_DEB";
	public static final String CPA_MER_HEURE_FIN_COLKEY = "CPA_MER_HEURE_FIN";
	public static final String CPA_SAM_HEURE_DEB_COLKEY = "CPA_SAM_HEURE_DEB";
	public static final String CPA_SAM_HEURE_FIN_COLKEY = "CPA_SAM_HEURE_FIN";
	public static final String CPA_VEN_HEURE_DEB_COLKEY = "CPA_VEN_HEURE_DEB";
	public static final String CPA_VEN_HEURE_FIN_COLKEY = "CPA_VEN_HEURE_FIN";

	// Relationships
	public static final String CTRL_PARAM_AP_INDIVIDUS_KEY = "ctrlParamApIndividus";
	public static final String CTRL_PARAM_AP_OBJETS_KEY = "ctrlParamApObjets";
	public static final String CTRL_PARAM_AP_SALLE_CHOIXS_KEY = "ctrlParamApSalleChoixs";
	public static final String CTRL_PARAM_AP_SALLES_KEY = "ctrlParamApSalles";
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";

	// Utilities methods
	  public CtrlParamAp localInstanceIn(EOEditingContext editingContext) {
	    CtrlParamAp localInstance = (CtrlParamAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static CtrlParamAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_CtrlParamAp.ENTITY_NAME);
		      return (CtrlParamAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer cpaDimHeureDeb() {
    return (Integer) storedValueForKey("cpaDimHeureDeb");
  }

  public void setCpaDimHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cpaDimHeureDeb");
  }

  public Integer cpaDimHeureFin() {
    return (Integer) storedValueForKey("cpaDimHeureFin");
  }

  public void setCpaDimHeureFin(Integer value) {
    takeStoredValueForKey(value, "cpaDimHeureFin");
  }

  public Integer cpaDuree() {
    return (Integer) storedValueForKey("cpaDuree");
  }

  public void setCpaDuree(Integer value) {
    takeStoredValueForKey(value, "cpaDuree");
  }

  public Integer cpaJeuHeureDeb() {
    return (Integer) storedValueForKey("cpaJeuHeureDeb");
  }

  public void setCpaJeuHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cpaJeuHeureDeb");
  }

  public Integer cpaJeuHeureFin() {
    return (Integer) storedValueForKey("cpaJeuHeureFin");
  }

  public void setCpaJeuHeureFin(Integer value) {
    takeStoredValueForKey(value, "cpaJeuHeureFin");
  }

  public Integer cpaLunHeureDeb() {
    return (Integer) storedValueForKey("cpaLunHeureDeb");
  }

  public void setCpaLunHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cpaLunHeureDeb");
  }

  public Integer cpaLunHeureFin() {
    return (Integer) storedValueForKey("cpaLunHeureFin");
  }

  public void setCpaLunHeureFin(Integer value) {
    takeStoredValueForKey(value, "cpaLunHeureFin");
  }

  public Integer cpaMarHeureDeb() {
    return (Integer) storedValueForKey("cpaMarHeureDeb");
  }

  public void setCpaMarHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cpaMarHeureDeb");
  }

  public Integer cpaMarHeureFin() {
    return (Integer) storedValueForKey("cpaMarHeureFin");
  }

  public void setCpaMarHeureFin(Integer value) {
    takeStoredValueForKey(value, "cpaMarHeureFin");
  }

  public Integer cpaMerHeureDeb() {
    return (Integer) storedValueForKey("cpaMerHeureDeb");
  }

  public void setCpaMerHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cpaMerHeureDeb");
  }

  public Integer cpaMerHeureFin() {
    return (Integer) storedValueForKey("cpaMerHeureFin");
  }

  public void setCpaMerHeureFin(Integer value) {
    takeStoredValueForKey(value, "cpaMerHeureFin");
  }

  public Integer cpaSamHeureDeb() {
    return (Integer) storedValueForKey("cpaSamHeureDeb");
  }

  public void setCpaSamHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cpaSamHeureDeb");
  }

  public Integer cpaSamHeureFin() {
    return (Integer) storedValueForKey("cpaSamHeureFin");
  }

  public void setCpaSamHeureFin(Integer value) {
    takeStoredValueForKey(value, "cpaSamHeureFin");
  }

  public Integer cpaVenHeureDeb() {
    return (Integer) storedValueForKey("cpaVenHeureDeb");
  }

  public void setCpaVenHeureDeb(Integer value) {
    takeStoredValueForKey(value, "cpaVenHeureDeb");
  }

  public Integer cpaVenHeureFin() {
    return (Integer) storedValueForKey("cpaVenHeureFin");
  }

  public void setCpaVenHeureFin(Integer value) {
    takeStoredValueForKey(value, "cpaVenHeureFin");
  }

  public org.cocktail.superplan.client.metier.MaquetteAp maquetteAp() {
    return (org.cocktail.superplan.client.metier.MaquetteAp)storedValueForKey("maquetteAp");
  }

  public void setMaquetteApRelationship(org.cocktail.superplan.client.metier.MaquetteAp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteAp oldValue = maquetteAp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteAp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteAp");
    }
  }
  
  public org.cocktail.superplan.client.metier.ScolGroupeGrp scolGroupeGrp() {
    return (org.cocktail.superplan.client.metier.ScolGroupeGrp)storedValueForKey("scolGroupeGrp");
  }

  public void setScolGroupeGrpRelationship(org.cocktail.superplan.client.metier.ScolGroupeGrp value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ScolGroupeGrp oldValue = scolGroupeGrp();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolGroupeGrp");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolGroupeGrp");
    }
  }
  
  public NSArray ctrlParamApIndividus() {
    return (NSArray)storedValueForKey("ctrlParamApIndividus");
  }

  public NSArray ctrlParamApIndividus(EOQualifier qualifier) {
    return ctrlParamApIndividus(qualifier, null, false);
  }

  public NSArray ctrlParamApIndividus(EOQualifier qualifier, boolean fetch) {
    return ctrlParamApIndividus(qualifier, null, fetch);
  }

  public NSArray ctrlParamApIndividus(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.CtrlParamApIndividu.CTRL_PARAM_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.CtrlParamApIndividu.fetchCtrlParamApIndividus(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = ctrlParamApIndividus();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToCtrlParamApIndividusRelationship(org.cocktail.superplan.client.metier.CtrlParamApIndividu object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "ctrlParamApIndividus");
  }

  public void removeFromCtrlParamApIndividusRelationship(org.cocktail.superplan.client.metier.CtrlParamApIndividu object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamApIndividus");
  }

  public org.cocktail.superplan.client.metier.CtrlParamApIndividu createCtrlParamApIndividusRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("CtrlParamApIndividu");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "ctrlParamApIndividus");
    return (org.cocktail.superplan.client.metier.CtrlParamApIndividu) eo;
  }

  public void deleteCtrlParamApIndividusRelationship(org.cocktail.superplan.client.metier.CtrlParamApIndividu object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamApIndividus");
    editingContext().deleteObject(object);
  }

  public void deleteAllCtrlParamApIndividusRelationships() {
    Enumeration objects = ctrlParamApIndividus().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteCtrlParamApIndividusRelationship((org.cocktail.superplan.client.metier.CtrlParamApIndividu)objects.nextElement());
    }
  }

  public NSArray ctrlParamApObjets() {
    return (NSArray)storedValueForKey("ctrlParamApObjets");
  }

  public NSArray ctrlParamApObjets(EOQualifier qualifier) {
    return ctrlParamApObjets(qualifier, null, false);
  }

  public NSArray ctrlParamApObjets(EOQualifier qualifier, boolean fetch) {
    return ctrlParamApObjets(qualifier, null, fetch);
  }

  public NSArray ctrlParamApObjets(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.CtrlParamApObjet.CTRL_PARAM_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.CtrlParamApObjet.fetchCtrlParamApObjets(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = ctrlParamApObjets();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToCtrlParamApObjetsRelationship(org.cocktail.superplan.client.metier.CtrlParamApObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "ctrlParamApObjets");
  }

  public void removeFromCtrlParamApObjetsRelationship(org.cocktail.superplan.client.metier.CtrlParamApObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamApObjets");
  }

  public org.cocktail.superplan.client.metier.CtrlParamApObjet createCtrlParamApObjetsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("CtrlParamApObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "ctrlParamApObjets");
    return (org.cocktail.superplan.client.metier.CtrlParamApObjet) eo;
  }

  public void deleteCtrlParamApObjetsRelationship(org.cocktail.superplan.client.metier.CtrlParamApObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamApObjets");
    editingContext().deleteObject(object);
  }

  public void deleteAllCtrlParamApObjetsRelationships() {
    Enumeration objects = ctrlParamApObjets().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteCtrlParamApObjetsRelationship((org.cocktail.superplan.client.metier.CtrlParamApObjet)objects.nextElement());
    }
  }

  public NSArray ctrlParamApSalleChoixs() {
    return (NSArray)storedValueForKey("ctrlParamApSalleChoixs");
  }

  public NSArray ctrlParamApSalleChoixs(EOQualifier qualifier) {
    return ctrlParamApSalleChoixs(qualifier, null, false);
  }

  public NSArray ctrlParamApSalleChoixs(EOQualifier qualifier, boolean fetch) {
    return ctrlParamApSalleChoixs(qualifier, null, fetch);
  }

  public NSArray ctrlParamApSalleChoixs(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.CtrlParamApSalleChoix.CTRL_PARAM_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.CtrlParamApSalleChoix.fetchCtrlParamApSalleChoixes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = ctrlParamApSalleChoixs();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToCtrlParamApSalleChoixsRelationship(org.cocktail.superplan.client.metier.CtrlParamApSalleChoix object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "ctrlParamApSalleChoixs");
  }

  public void removeFromCtrlParamApSalleChoixsRelationship(org.cocktail.superplan.client.metier.CtrlParamApSalleChoix object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamApSalleChoixs");
  }

  public org.cocktail.superplan.client.metier.CtrlParamApSalleChoix createCtrlParamApSalleChoixsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("CtrlParamApSalleChoix");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "ctrlParamApSalleChoixs");
    return (org.cocktail.superplan.client.metier.CtrlParamApSalleChoix) eo;
  }

  public void deleteCtrlParamApSalleChoixsRelationship(org.cocktail.superplan.client.metier.CtrlParamApSalleChoix object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamApSalleChoixs");
    editingContext().deleteObject(object);
  }

  public void deleteAllCtrlParamApSalleChoixsRelationships() {
    Enumeration objects = ctrlParamApSalleChoixs().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteCtrlParamApSalleChoixsRelationship((org.cocktail.superplan.client.metier.CtrlParamApSalleChoix)objects.nextElement());
    }
  }

  public NSArray ctrlParamApSalles() {
    return (NSArray)storedValueForKey("ctrlParamApSalles");
  }

  public NSArray ctrlParamApSalles(EOQualifier qualifier) {
    return ctrlParamApSalles(qualifier, null, false);
  }

  public NSArray ctrlParamApSalles(EOQualifier qualifier, boolean fetch) {
    return ctrlParamApSalles(qualifier, null, fetch);
  }

  public NSArray ctrlParamApSalles(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.CtrlParamApSalle.CTRL_PARAM_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.CtrlParamApSalle.fetchCtrlParamApSalles(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = ctrlParamApSalles();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToCtrlParamApSallesRelationship(org.cocktail.superplan.client.metier.CtrlParamApSalle object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "ctrlParamApSalles");
  }

  public void removeFromCtrlParamApSallesRelationship(org.cocktail.superplan.client.metier.CtrlParamApSalle object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamApSalles");
  }

  public org.cocktail.superplan.client.metier.CtrlParamApSalle createCtrlParamApSallesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("CtrlParamApSalle");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "ctrlParamApSalles");
    return (org.cocktail.superplan.client.metier.CtrlParamApSalle) eo;
  }

  public void deleteCtrlParamApSallesRelationship(org.cocktail.superplan.client.metier.CtrlParamApSalle object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamApSalles");
    editingContext().deleteObject(object);
  }

  public void deleteAllCtrlParamApSallesRelationships() {
    Enumeration objects = ctrlParamApSalles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteCtrlParamApSallesRelationship((org.cocktail.superplan.client.metier.CtrlParamApSalle)objects.nextElement());
    }
  }


  public static CtrlParamAp createCtrlParamAp(EOEditingContext editingContext, org.cocktail.superplan.client.metier.MaquetteAp maquetteAp) {
    CtrlParamAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_CtrlParamAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _CtrlParamAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (CtrlParamAp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setMaquetteApRelationship(maquetteAp);
    return eo;
  }

  public static NSArray fetchAllCtrlParamAps(EOEditingContext editingContext) {
    return _CtrlParamAp.fetchAllCtrlParamAps(editingContext, null);
  }

  public static NSArray fetchAllCtrlParamAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _CtrlParamAp.fetchCtrlParamAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchCtrlParamAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_CtrlParamAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static CtrlParamAp fetchCtrlParamAp(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamAp.fetchCtrlParamAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamAp fetchCtrlParamAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _CtrlParamAp.fetchCtrlParamAps(editingContext, qualifier, null);
    CtrlParamAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (CtrlParamAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one CtrlParamAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamAp fetchRequiredCtrlParamAp(EOEditingContext editingContext, String keyName, Object value) {
    return _CtrlParamAp.fetchRequiredCtrlParamAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static CtrlParamAp fetchRequiredCtrlParamAp(EOEditingContext editingContext, EOQualifier qualifier) {
    CtrlParamAp eoObject = _CtrlParamAp.fetchCtrlParamAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no CtrlParamAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CtrlParamAp localInstanceIn(EOEditingContext editingContext, CtrlParamAp eo) {
    CtrlParamAp localInstance = (eo == null) ? null : (CtrlParamAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
