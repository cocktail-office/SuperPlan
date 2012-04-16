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

// DO NOT EDIT.  Make changes to MaquetteAp.java instead.
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

public abstract class _MaquetteAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteAp";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_AP";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mapKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MAP_GROUPE_PREVU_KEY = "mapGroupePrevu";
	public static final String MAP_KEY_KEY = "mapKey";
	public static final String MAP_LIBELLE_KEY = "mapLibelle";
	public static final String MAP_SEUIL_KEY = "mapSeuil";
	public static final String MAP_VALEUR_KEY = "mapValeur";
	public static final String MHCO_CODE_KEY = "mhcoCode";
	public static final String MHMO_CODE_KEY = "mhmoCode";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String MAP_GROUPE_PREVU_COLKEY = "MAP_GROUPE_PREVU";
	public static final String MAP_KEY_COLKEY = "MAP_KEY";
	public static final String MAP_LIBELLE_COLKEY = "MAP_LIBELLE";
	public static final String MAP_SEUIL_COLKEY = "MAP_SEUIL";
	public static final String MAP_VALEUR_COLKEY = "MAP_VALEUR";
	public static final String MHCO_CODE_COLKEY = "MHCO_CODE";
	public static final String MHMO_CODE_COLKEY = "MHMO_CODE";

	// Relationships
	public static final String CTRL_PARAM_APS_KEY = "ctrlParamAps";
	public static final String HORAIRE_CODE_KEY = "horaireCode";
	public static final String MAQUETTE_REPARTITION_APS_KEY = "maquetteRepartitionAps";
	public static final String RESERVATION_APS_KEY = "reservationAps";
	public static final String SCOL_FORMATION_ANNEE_KEY = "scolFormationAnnee";
	public static final String SCOL_GROUPE_OBJETS_KEY = "scolGroupeObjets";
	public static final String V_MAQUETTE_AP_GROUPESES_KEY = "vMaquetteApGroupeses";
	public static final String V_PARCOURS_APS_KEY = "vParcoursAps";
	public static final String V_SCOL_MAQUETTE_AP_EC_KEY = "vScolMaquetteApEc";

	// Utilities methods
	  public MaquetteAp localInstanceIn(EOEditingContext editingContext) {
	    MaquetteAp localInstance = (MaquetteAp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteAp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteAp.ENTITY_NAME);
		      return (MaquetteAp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
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

  public org.cocktail.superplan.client.metier.HoraireCode horaireCode() {
    return (org.cocktail.superplan.client.metier.HoraireCode)storedValueForKey("horaireCode");
  }

  public void setHoraireCodeRelationship(org.cocktail.superplan.client.metier.HoraireCode value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.HoraireCode oldValue = horaireCode();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "horaireCode");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "horaireCode");
    }
  }
  
  public org.cocktail.superplan.client.metier.FormationAnnee scolFormationAnnee() {
    return (org.cocktail.superplan.client.metier.FormationAnnee)storedValueForKey("scolFormationAnnee");
  }

  public void setScolFormationAnneeRelationship(org.cocktail.superplan.client.metier.FormationAnnee value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.FormationAnnee oldValue = scolFormationAnnee();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "scolFormationAnnee");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "scolFormationAnnee");
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
  
  public NSArray ctrlParamAps() {
    return (NSArray)storedValueForKey("ctrlParamAps");
  }

  public NSArray ctrlParamAps(EOQualifier qualifier) {
    return ctrlParamAps(qualifier, null, false);
  }

  public NSArray ctrlParamAps(EOQualifier qualifier, boolean fetch) {
    return ctrlParamAps(qualifier, null, fetch);
  }

  public NSArray ctrlParamAps(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.CtrlParamAp.MAQUETTE_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.CtrlParamAp.fetchCtrlParamAps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = ctrlParamAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToCtrlParamApsRelationship(org.cocktail.superplan.client.metier.CtrlParamAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "ctrlParamAps");
  }

  public void removeFromCtrlParamApsRelationship(org.cocktail.superplan.client.metier.CtrlParamAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamAps");
  }

  public org.cocktail.superplan.client.metier.CtrlParamAp createCtrlParamApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("CtrlParamAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "ctrlParamAps");
    return (org.cocktail.superplan.client.metier.CtrlParamAp) eo;
  }

  public void deleteCtrlParamApsRelationship(org.cocktail.superplan.client.metier.CtrlParamAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "ctrlParamAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllCtrlParamApsRelationships() {
    Enumeration objects = ctrlParamAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteCtrlParamApsRelationship((org.cocktail.superplan.client.metier.CtrlParamAp)objects.nextElement());
    }
  }

  public NSArray maquetteRepartitionAps() {
    return (NSArray)storedValueForKey("maquetteRepartitionAps");
  }

  public NSArray maquetteRepartitionAps(EOQualifier qualifier) {
    return maquetteRepartitionAps(qualifier, null, false);
  }

  public NSArray maquetteRepartitionAps(EOQualifier qualifier, boolean fetch) {
    return maquetteRepartitionAps(qualifier, null, fetch);
  }

  public NSArray maquetteRepartitionAps(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.MaquetteRepartitionAp.MAQUETTE_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.MaquetteRepartitionAp.fetchMaquetteRepartitionAps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = maquetteRepartitionAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToMaquetteRepartitionApsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "maquetteRepartitionAps");
  }

  public void removeFromMaquetteRepartitionApsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionAps");
  }

  public org.cocktail.superplan.client.metier.MaquetteRepartitionAp createMaquetteRepartitionApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteRepartitionAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "maquetteRepartitionAps");
    return (org.cocktail.superplan.client.metier.MaquetteRepartitionAp) eo;
  }

  public void deleteMaquetteRepartitionApsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllMaquetteRepartitionApsRelationships() {
    Enumeration objects = maquetteRepartitionAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteMaquetteRepartitionApsRelationship((org.cocktail.superplan.client.metier.MaquetteRepartitionAp)objects.nextElement());
    }
  }

  public NSArray reservationAps() {
    return (NSArray)storedValueForKey("reservationAps");
  }

  public NSArray reservationAps(EOQualifier qualifier) {
    return reservationAps(qualifier, null, false);
  }

  public NSArray reservationAps(EOQualifier qualifier, boolean fetch) {
    return reservationAps(qualifier, null, fetch);
  }

  public NSArray reservationAps(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ReservationAp.MAQUETTE_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ReservationAp.fetchReservationAps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = reservationAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToReservationApsRelationship(org.cocktail.superplan.client.metier.ReservationAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "reservationAps");
  }

  public void removeFromReservationApsRelationship(org.cocktail.superplan.client.metier.ReservationAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "reservationAps");
  }

  public org.cocktail.superplan.client.metier.ReservationAp createReservationApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ReservationAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "reservationAps");
    return (org.cocktail.superplan.client.metier.ReservationAp) eo;
  }

  public void deleteReservationApsRelationship(org.cocktail.superplan.client.metier.ReservationAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "reservationAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllReservationApsRelationships() {
    Enumeration objects = reservationAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteReservationApsRelationship((org.cocktail.superplan.client.metier.ReservationAp)objects.nextElement());
    }
  }

  public NSArray scolGroupeObjets() {
    return (NSArray)storedValueForKey("scolGroupeObjets");
  }

  public NSArray scolGroupeObjets(EOQualifier qualifier) {
    return scolGroupeObjets(qualifier, null, false);
  }

  public NSArray scolGroupeObjets(EOQualifier qualifier, boolean fetch) {
    return scolGroupeObjets(qualifier, null, fetch);
  }

  public NSArray scolGroupeObjets(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ScolGroupeObjet.MAQUETTE_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ScolGroupeObjet.fetchScolGroupeObjets(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = scolGroupeObjets();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToScolGroupeObjetsRelationship(org.cocktail.superplan.client.metier.ScolGroupeObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolGroupeObjets");
  }

  public void removeFromScolGroupeObjetsRelationship(org.cocktail.superplan.client.metier.ScolGroupeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjets");
  }

  public org.cocktail.superplan.client.metier.ScolGroupeObjet createScolGroupeObjetsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolGroupeObjets");
    return (org.cocktail.superplan.client.metier.ScolGroupeObjet) eo;
  }

  public void deleteScolGroupeObjetsRelationship(org.cocktail.superplan.client.metier.ScolGroupeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjets");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolGroupeObjetsRelationships() {
    Enumeration objects = scolGroupeObjets().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolGroupeObjetsRelationship((org.cocktail.superplan.client.metier.ScolGroupeObjet)objects.nextElement());
    }
  }

  public NSArray vMaquetteApGroupeses() {
    return (NSArray)storedValueForKey("vMaquetteApGroupeses");
  }

  public NSArray vMaquetteApGroupeses(EOQualifier qualifier) {
    return vMaquetteApGroupeses(qualifier, null, false);
  }

  public NSArray vMaquetteApGroupeses(EOQualifier qualifier, boolean fetch) {
    return vMaquetteApGroupeses(qualifier, null, fetch);
  }

  public NSArray vMaquetteApGroupeses(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.VMaquetteApGroupes.MAQUETTE_AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.VMaquetteApGroupes.fetchVMaquetteApGroupeses(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = vMaquetteApGroupeses();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToVMaquetteApGroupesesRelationship(org.cocktail.superplan.client.metier.VMaquetteApGroupes object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "vMaquetteApGroupeses");
  }

  public void removeFromVMaquetteApGroupesesRelationship(org.cocktail.superplan.client.metier.VMaquetteApGroupes object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "vMaquetteApGroupeses");
  }

  public org.cocktail.superplan.client.metier.VMaquetteApGroupes createVMaquetteApGroupesesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("VMaquetteApGroupes");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "vMaquetteApGroupeses");
    return (org.cocktail.superplan.client.metier.VMaquetteApGroupes) eo;
  }

  public void deleteVMaquetteApGroupesesRelationship(org.cocktail.superplan.client.metier.VMaquetteApGroupes object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "vMaquetteApGroupeses");
    editingContext().deleteObject(object);
  }

  public void deleteAllVMaquetteApGroupesesRelationships() {
    Enumeration objects = vMaquetteApGroupeses().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteVMaquetteApGroupesesRelationship((org.cocktail.superplan.client.metier.VMaquetteApGroupes)objects.nextElement());
    }
  }

  public NSArray vParcoursAps() {
    return (NSArray)storedValueForKey("vParcoursAps");
  }

  public NSArray vParcoursAps(EOQualifier qualifier) {
    return vParcoursAps(qualifier, null, false);
  }

  public NSArray vParcoursAps(EOQualifier qualifier, boolean fetch) {
    return vParcoursAps(qualifier, null, fetch);
  }

  public NSArray vParcoursAps(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.VParcoursAp.AP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.VParcoursAp.fetchVParcoursAps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = vParcoursAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToVParcoursApsRelationship(org.cocktail.superplan.client.metier.VParcoursAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "vParcoursAps");
  }

  public void removeFromVParcoursApsRelationship(org.cocktail.superplan.client.metier.VParcoursAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "vParcoursAps");
  }

  public org.cocktail.superplan.client.metier.VParcoursAp createVParcoursApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("VParcoursAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "vParcoursAps");
    return (org.cocktail.superplan.client.metier.VParcoursAp) eo;
  }

  public void deleteVParcoursApsRelationship(org.cocktail.superplan.client.metier.VParcoursAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "vParcoursAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllVParcoursApsRelationships() {
    Enumeration objects = vParcoursAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteVParcoursApsRelationship((org.cocktail.superplan.client.metier.VParcoursAp)objects.nextElement());
    }
  }


  public static MaquetteAp createMaquetteAp(EOEditingContext editingContext, Integer fannKey
, Integer mapGroupePrevu
, Integer mapKey
, String mapLibelle
, Integer mapSeuil
, java.math.BigDecimal mapValeur
, String mhcoCode
, String mhmoCode
, org.cocktail.superplan.client.metier.VScolMaquetteApEc vScolMaquetteApEc) {
    MaquetteAp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteAp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteAp.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteAp) classDescription.createInstanceWithEditingContext(editingContext, null);
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

  public static NSArray fetchAllMaquetteAps(EOEditingContext editingContext) {
    return _MaquetteAp.fetchAllMaquetteAps(editingContext, null);
  }

  public static NSArray fetchAllMaquetteAps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteAp.fetchMaquetteAps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteAp fetchMaquetteAp(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteAp.fetchMaquetteAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteAp fetchMaquetteAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteAp.fetchMaquetteAps(editingContext, qualifier, null);
    MaquetteAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteAp fetchRequiredMaquetteAp(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteAp.fetchRequiredMaquetteAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteAp fetchRequiredMaquetteAp(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteAp eoObject = _MaquetteAp.fetchMaquetteAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteAp localInstanceIn(EOEditingContext editingContext, MaquetteAp eo) {
    MaquetteAp localInstance = (eo == null) ? null : (MaquetteAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
