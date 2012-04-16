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

// DO NOT EDIT.  Make changes to VMaquetteAp.java instead.
package org.cocktail.superplan.server.metier;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

public abstract class _VMaquetteAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VMaquetteAp";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_MAQUETTE_AP";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mapKey";

	public static final String COLOR_CODE_KEY = "colorCode";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MAP_GROUPE_PREVU_KEY = "mapGroupePrevu";
	public static final String MAP_KEY_KEY = "mapKey";
	public static final String MAP_LIBELLE_KEY = "mapLibelle";
	public static final String MAP_SEUIL_KEY = "mapSeuil";
	public static final String MAP_VALEUR_KEY = "mapValeur";
	public static final String MHCO_CODE_KEY = "mhcoCode";
	public static final String MHMO_CODE_KEY = "mhmoCode";

	public static final String COLOR_CODE_COLKEY = "COLOR_CODE";
	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String MAP_GROUPE_PREVU_COLKEY = "MAP_GROUPE_PREVU";
	public static final String MAP_KEY_COLKEY = "MAP_KEY";
	public static final String MAP_LIBELLE_COLKEY = "MAP_LIBELLE";
	public static final String MAP_SEUIL_COLKEY = "MAP_SEUIL";
	public static final String MAP_VALEUR_COLKEY = "MAP_VALEUR";
	public static final String MHCO_CODE_COLKEY = "MHCO_CODE";
	public static final String MHMO_CODE_COLKEY = "MHMO_CODE";

	// Relationships
	public static final String HORAIRE_CODE_KEY = "horaireCode";
	public static final String MAQUETTE_REPARTITION_APS_KEY = "maquetteRepartitionAps";
	public static final String SCOL_FORMATION_ANNEE_KEY = "scolFormationAnnee";
	public static final String SCOL_GROUPE_OBJETS_KEY = "scolGroupeObjets";
	public static final String V_PARCOURS_APS_KEY = "vParcoursAps";
	public static final String V_SCOL_MAQUETTE_AP_EC_KEY = "vScolMaquetteApEc";

	// Utilities methods
	  public VMaquetteAp localInstanceIn(EOEditingContext editingContext) {
	    VMaquetteAp localInstance = (VMaquetteAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VMaquetteAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VMaquetteAp.ENTITY_NAME);
		      return (VMaquetteAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String colorCode() {
    return (String) storedValueForKey("colorCode");
  }

  public void setColorCode(String value) {
    takeStoredValueForKey(value, "colorCode");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer mapGroupePrevu() {
    return (Integer) storedValueForKey("mapGroupePrevu");
  }

  public void setMapGroupePrevu(Integer value) {
    takeStoredValueForKey(value, "mapGroupePrevu");
  }

  public Integer mapKey() {
    return (Integer) storedValueForKey("mapKey");
  }

  public void setMapKey(Integer value) {
    takeStoredValueForKey(value, "mapKey");
  }

  public String mapLibelle() {
    return (String) storedValueForKey("mapLibelle");
  }

  public void setMapLibelle(String value) {
    takeStoredValueForKey(value, "mapLibelle");
  }

  public Integer mapSeuil() {
    return (Integer) storedValueForKey("mapSeuil");
  }

  public void setMapSeuil(Integer value) {
    takeStoredValueForKey(value, "mapSeuil");
  }

  public java.math.BigDecimal mapValeur() {
    return (java.math.BigDecimal) storedValueForKey("mapValeur");
  }

  public void setMapValeur(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mapValeur");
  }

  public String mhcoCode() {
    return (String) storedValueForKey("mhcoCode");
  }

  public void setMhcoCode(String value) {
    takeStoredValueForKey(value, "mhcoCode");
  }

  public String mhmoCode() {
    return (String) storedValueForKey("mhmoCode");
  }

  public void setMhmoCode(String value) {
    takeStoredValueForKey(value, "mhmoCode");
  }

  public org.cocktail.superplan.server.metier.HoraireCode horaireCode() {
    return (org.cocktail.superplan.server.metier.HoraireCode)storedValueForKey("horaireCode");
  }

  public void setHoraireCodeRelationship(org.cocktail.superplan.server.metier.HoraireCode value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.HoraireCode oldValue = horaireCode();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "horaireCode");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "horaireCode");
    }
  }
  
  public org.cocktail.superplan.server.metier.FormationAnnee scolFormationAnnee() {
    return (org.cocktail.superplan.server.metier.FormationAnnee)storedValueForKey("scolFormationAnnee");
  }

  public void setScolFormationAnneeRelationship(org.cocktail.superplan.server.metier.FormationAnnee value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationAnnee oldValue = scolFormationAnnee();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolFormationAnnee");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolFormationAnnee");
    }
  }
  
  public org.cocktail.superplan.server.metier.VScolMaquetteApEc vScolMaquetteApEc() {
    return (org.cocktail.superplan.server.metier.VScolMaquetteApEc)storedValueForKey("vScolMaquetteApEc");
  }

  public void setVScolMaquetteApEcRelationship(org.cocktail.superplan.server.metier.VScolMaquetteApEc value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.VScolMaquetteApEc oldValue = vScolMaquetteApEc();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "vScolMaquetteApEc");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "vScolMaquetteApEc");
    }
  }
  
  public NSArray maquetteRepartitionAps() {
    return (NSArray)storedValueForKey("maquetteRepartitionAps");
  }

  public NSArray maquetteRepartitionAps(EOQualifier qualifier) {
    return maquetteRepartitionAps(qualifier, null);
  }

  public NSArray maquetteRepartitionAps(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = maquetteRepartitionAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToMaquetteRepartitionApsRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "maquetteRepartitionAps");
  }

  public void removeFromMaquetteRepartitionApsRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionAps");
  }

  public org.cocktail.superplan.server.metier.MaquetteRepartitionAp createMaquetteRepartitionApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteRepartitionAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "maquetteRepartitionAps");
    return (org.cocktail.superplan.server.metier.MaquetteRepartitionAp) eo;
  }

  public void deleteMaquetteRepartitionApsRelationship(org.cocktail.superplan.server.metier.MaquetteRepartitionAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllMaquetteRepartitionApsRelationships() {
    Enumeration objects = maquetteRepartitionAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteMaquetteRepartitionApsRelationship((org.cocktail.superplan.server.metier.MaquetteRepartitionAp)objects.nextElement());
    }
  }

  public NSArray scolGroupeObjets() {
    return (NSArray)storedValueForKey("scolGroupeObjets");
  }

  public NSArray scolGroupeObjets(EOQualifier qualifier) {
    return scolGroupeObjets(qualifier, null);
  }

  public NSArray scolGroupeObjets(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = scolGroupeObjets();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolGroupeObjetsRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolGroupeObjets");
  }

  public void removeFromScolGroupeObjetsRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjets");
  }

  public org.cocktail.superplan.server.metier.ScolGroupeObjet createScolGroupeObjetsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolGroupeObjets");
    return (org.cocktail.superplan.server.metier.ScolGroupeObjet) eo;
  }

  public void deleteScolGroupeObjetsRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjets");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolGroupeObjetsRelationships() {
    Enumeration objects = scolGroupeObjets().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolGroupeObjetsRelationship((org.cocktail.superplan.server.metier.ScolGroupeObjet)objects.nextElement());
    }
  }

  public NSArray vParcoursAps() {
    return (NSArray)storedValueForKey("vParcoursAps");
  }

  public NSArray vParcoursAps(EOQualifier qualifier) {
    return vParcoursAps(qualifier, null);
  }

  public NSArray vParcoursAps(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = vParcoursAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToVParcoursApsRelationship(org.cocktail.superplan.server.metier.VParcoursAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "vParcoursAps");
  }

  public void removeFromVParcoursApsRelationship(org.cocktail.superplan.server.metier.VParcoursAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "vParcoursAps");
  }

  public org.cocktail.superplan.server.metier.VParcoursAp createVParcoursApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("VParcoursAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "vParcoursAps");
    return (org.cocktail.superplan.server.metier.VParcoursAp) eo;
  }

  public void deleteVParcoursApsRelationship(org.cocktail.superplan.server.metier.VParcoursAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "vParcoursAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllVParcoursApsRelationships() {
    Enumeration objects = vParcoursAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteVParcoursApsRelationship((org.cocktail.superplan.server.metier.VParcoursAp)objects.nextElement());
    }
  }


  public static VMaquetteAp createVMaquetteAp(EOEditingContext editingContext, Integer fannKey
, Integer mapGroupePrevu
, Integer mapKey
, String mapLibelle
, Integer mapSeuil
, java.math.BigDecimal mapValeur
, String mhcoCode
, String mhmoCode
, org.cocktail.superplan.server.metier.VScolMaquetteApEc vScolMaquetteApEc) {
    VMaquetteAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VMaquetteAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VMaquetteAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (VMaquetteAp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setMapGroupePrevu(mapGroupePrevu);
		eo.setMapKey(mapKey);
		eo.setMapLibelle(mapLibelle);
		eo.setMapSeuil(mapSeuil);
		eo.setMapValeur(mapValeur);
		eo.setMhcoCode(mhcoCode);
		eo.setMhmoCode(mhmoCode);
    eo.setVScolMaquetteApEcRelationship(vScolMaquetteApEc);
    return eo;
  }

  public static NSArray fetchAllVMaquetteAps(EOEditingContext editingContext) {
    return _VMaquetteAp.fetchAllVMaquetteAps(editingContext, null);
  }

  public static NSArray fetchAllVMaquetteAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VMaquetteAp.fetchVMaquetteAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVMaquetteAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VMaquetteAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VMaquetteAp fetchVMaquetteAp(EOEditingContext editingContext, String keyName, Object value) {
    return _VMaquetteAp.fetchVMaquetteAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VMaquetteAp fetchVMaquetteAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VMaquetteAp.fetchVMaquetteAps(editingContext, qualifier, null);
    VMaquetteAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VMaquetteAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VMaquetteAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VMaquetteAp fetchRequiredVMaquetteAp(EOEditingContext editingContext, String keyName, Object value) {
    return _VMaquetteAp.fetchRequiredVMaquetteAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VMaquetteAp fetchRequiredVMaquetteAp(EOEditingContext editingContext, EOQualifier qualifier) {
    VMaquetteAp eoObject = _VMaquetteAp.fetchVMaquetteAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VMaquetteAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VMaquetteAp localInstanceIn(EOEditingContext editingContext, VMaquetteAp eo) {
    VMaquetteAp localInstance = (eo == null) ? null : (VMaquetteAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
