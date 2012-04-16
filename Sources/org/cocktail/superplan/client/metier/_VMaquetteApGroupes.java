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

// DO NOT EDIT.  Make changes to VMaquetteApGroupes.java instead.
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

public abstract class _VMaquetteApGroupes extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VMaquetteApGroupes";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.V_MAQUETTE_AP_GROUPES";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mapgKey";

	public static final String GGRP_CODE_KEY = "ggrpCode";
	public static final String GGRP_KEY_KEY = "ggrpKey";
	public static final String GGRP_LIBELLE_KEY = "ggrpLibelle";
	public static final String GOBJ_KEY_KEY = "gobjKey";
	public static final String MAPG_KEY_KEY = "mapgKey";
	public static final String MAP_GROUPE_PREVU_KEY = "mapGroupePrevu";
	public static final String MAP_KEY_KEY = "mapKey";
	public static final String MAP_LIBELLE_KEY = "mapLibelle";
	public static final String MAP_LIBELLE_AFFICHAGE_KEY = "mapLibelleAffichage";
	public static final String MAP_SEUIL_KEY = "mapSeuil";
	public static final String MAP_VALEUR_KEY = "mapValeur";

	public static final String GGRP_CODE_COLKEY = "GGRP_CODE";
	public static final String GGRP_KEY_COLKEY = "GGRP_KEY";
	public static final String GGRP_LIBELLE_COLKEY = "GGRP_LIBELLE";
	public static final String GOBJ_KEY_COLKEY = "GOBJ_KEY";
	public static final String MAPG_KEY_COLKEY = "MAPG_KEY";
	public static final String MAP_GROUPE_PREVU_COLKEY = "MAP_GROUPE_PREVU";
	public static final String MAP_KEY_COLKEY = "MAP_KEY";
	public static final String MAP_LIBELLE_COLKEY = "MAP_LIBELLE";
	public static final String MAP_LIBELLE_AFFICHAGE_COLKEY = "MAP_LIBELLE_AFFICHAGE";
	public static final String MAP_SEUIL_COLKEY = "MAP_SEUIL";
	public static final String MAP_VALEUR_COLKEY = "MAP_VALEUR";

	// Relationships
	public static final String MAQUETTE_AP_KEY = "maquetteAp";
	public static final String SCOL_GROUPE_GRP_KEY = "scolGroupeGrp";
	public static final String SCOL_GROUPE_OBJET_KEY = "scolGroupeObjet";
	public static final String V_SCOL_MAQUETTE_AP_EC_KEY = "vScolMaquetteApEc";

	// Utilities methods
	  public VMaquetteApGroupes localInstanceIn(EOEditingContext editingContext) {
	    VMaquetteApGroupes localInstance = (VMaquetteApGroupes)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static VMaquetteApGroupes getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_VMaquetteApGroupes.ENTITY_NAME);
		      return (VMaquetteApGroupes) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String ggrpCode() {
    return (String) storedValueForKey("ggrpCode");
  }

  public void setGgrpCode(String value) {
    takeStoredValueForKey(value, "ggrpCode");
  }

  public Integer ggrpKey() {
    return (Integer) storedValueForKey("ggrpKey");
  }

  public void setGgrpKey(Integer value) {
    takeStoredValueForKey(value, "ggrpKey");
  }

  public String ggrpLibelle() {
    return (String) storedValueForKey("ggrpLibelle");
  }

  public void setGgrpLibelle(String value) {
    takeStoredValueForKey(value, "ggrpLibelle");
  }

  public Integer gobjKey() {
    return (Integer) storedValueForKey("gobjKey");
  }

  public void setGobjKey(Integer value) {
    takeStoredValueForKey(value, "gobjKey");
  }

  public String mapgKey() {
    return (String) storedValueForKey("mapgKey");
  }

  public void setMapgKey(String value) {
    takeStoredValueForKey(value, "mapgKey");
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

  public String mapLibelleAffichage() {
    return (String) storedValueForKey("mapLibelleAffichage");
  }

  public void setMapLibelleAffichage(String value) {
    takeStoredValueForKey(value, "mapLibelleAffichage");
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
  
  public org.cocktail.superplan.client.metier.ScolGroupeObjet scolGroupeObjet() {
    return (org.cocktail.superplan.client.metier.ScolGroupeObjet)storedValueForKey("scolGroupeObjet");
  }

  public void setScolGroupeObjetRelationship(org.cocktail.superplan.client.metier.ScolGroupeObjet value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ScolGroupeObjet oldValue = scolGroupeObjet();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolGroupeObjet");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolGroupeObjet");
    }
  }
  
  public org.cocktail.superplan.client.metier.VScolMaquetteApEc vScolMaquetteApEc() {
    return (org.cocktail.superplan.client.metier.VScolMaquetteApEc)storedValueForKey("vScolMaquetteApEc");
  }

  public void setVScolMaquetteApEcRelationship(org.cocktail.superplan.client.metier.VScolMaquetteApEc value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.VScolMaquetteApEc oldValue = vScolMaquetteApEc();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "vScolMaquetteApEc");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "vScolMaquetteApEc");
    }
  }
  

  public static VMaquetteApGroupes createVMaquetteApGroupes(EOEditingContext editingContext, String mapgKey
, Integer mapGroupePrevu
, Integer mapKey
, String mapLibelle
, String mapLibelleAffichage
, Integer mapSeuil
, java.math.BigDecimal mapValeur
, org.cocktail.superplan.client.metier.MaquetteAp maquetteAp, org.cocktail.superplan.client.metier.VScolMaquetteApEc vScolMaquetteApEc) {
    VMaquetteApGroupes eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_VMaquetteApGroupes.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _VMaquetteApGroupes.ENTITY_NAME + "' !");
    } else
    {
        eo = (VMaquetteApGroupes) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setMapgKey(mapgKey);
		eo.setMapGroupePrevu(mapGroupePrevu);
		eo.setMapKey(mapKey);
		eo.setMapLibelle(mapLibelle);
		eo.setMapLibelleAffichage(mapLibelleAffichage);
		eo.setMapSeuil(mapSeuil);
		eo.setMapValeur(mapValeur);
    eo.setMaquetteApRelationship(maquetteAp);
    eo.setVScolMaquetteApEcRelationship(vScolMaquetteApEc);
    return eo;
  }

  public static NSArray fetchAllVMaquetteApGroupeses(EOEditingContext editingContext) {
    return _VMaquetteApGroupes.fetchAllVMaquetteApGroupeses(editingContext, null);
  }

  public static NSArray fetchAllVMaquetteApGroupeses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _VMaquetteApGroupes.fetchVMaquetteApGroupeses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchVMaquetteApGroupeses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VMaquetteApGroupes.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VMaquetteApGroupes fetchVMaquetteApGroupes(EOEditingContext editingContext, String keyName, Object value) {
    return _VMaquetteApGroupes.fetchVMaquetteApGroupes(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VMaquetteApGroupes fetchVMaquetteApGroupes(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _VMaquetteApGroupes.fetchVMaquetteApGroupeses(editingContext, qualifier, null);
    VMaquetteApGroupes eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VMaquetteApGroupes)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VMaquetteApGroupes that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VMaquetteApGroupes fetchRequiredVMaquetteApGroupes(EOEditingContext editingContext, String keyName, Object value) {
    return _VMaquetteApGroupes.fetchRequiredVMaquetteApGroupes(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VMaquetteApGroupes fetchRequiredVMaquetteApGroupes(EOEditingContext editingContext, EOQualifier qualifier) {
    VMaquetteApGroupes eoObject = _VMaquetteApGroupes.fetchVMaquetteApGroupes(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VMaquetteApGroupes that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VMaquetteApGroupes localInstanceIn(EOEditingContext editingContext, VMaquetteApGroupes eo) {
    VMaquetteApGroupes localInstance = (eo == null) ? null : (VMaquetteApGroupes)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
