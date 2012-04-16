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

// DO NOT EDIT.  Make changes to Salles.java instead.
package org.cocktail.superplan.server.metier;

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
import com.webobjects.foundation.NSTimestamp;

public abstract class _Salles extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Salles";
	public static final String ENTITY_TABLE_NAME = "GRHUM.SALLES";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "salNumero";

	public static final String C_LOCAL_KEY = "cLocal";
	public static final String D_ANNULATION_KEY = "dAnnulation";
	public static final String SAL_CAPACITE_KEY = "salCapacite";
	public static final String SAL_DESCRIPTIF_KEY = "salDescriptif";
	public static final String SAL_ECRAN_KEY = "salEcran";
	public static final String SAL_ETAGE_KEY = "salEtage";
	public static final String SAL_INSONORISEE_KEY = "salInsonorisee";
	public static final String SAL_NB_ARMOIRES_KEY = "salNbArmoires";
	public static final String SAL_NB_BUREAUX_KEY = "salNbBureaux";
	public static final String SAL_NB_CHAISES_KEY = "salNbChaises";
	public static final String SAL_NB_FENETRES_KEY = "salNbFenetres";
	public static final String SAL_NB_PLACES_EXAM_LIB_KEY = "salNbPlacesExamLib";
	public static final String SAL_NB_TABLES_KEY = "salNbTables";
	public static final String SAL_NO_POSTE_KEY = "salNoPoste";
	public static final String SAL_OBSCUR_KEY = "salObscur";
	public static final String SAL_PORTE_KEY = "salPorte";
	public static final String SAL_RESERVABLE_KEY = "salReservable";
	public static final String SAL_RETRO_KEY = "salRetro";
	public static final String SAL_SUPERFICIE_KEY = "salSuperficie";
	public static final String SAL_TABLEAU_KEY = "salTableau";
	public static final String SAL_TABLEAU_BLANC_KEY = "salTableauBlanc";
	public static final String SAL_TABLEAU_PAPIER_KEY = "salTableauPapier";
	public static final String SAL_TELEVISION_KEY = "salTelevision";

	public static final String C_LOCAL_COLKEY = "C_LOCAL";
	public static final String D_ANNULATION_COLKEY = "D_ANNULATION";
	public static final String SAL_CAPACITE_COLKEY = "SAL_CAPACITE";
	public static final String SAL_DESCRIPTIF_COLKEY = "SAL_DESCRIPTIF";
	public static final String SAL_ECRAN_COLKEY = "SAL_ECRAN";
	public static final String SAL_ETAGE_COLKEY = "SAL_ETAGE";
	public static final String SAL_INSONORISEE_COLKEY = "SAL_INSONORISEE";
	public static final String SAL_NB_ARMOIRES_COLKEY = "SAL_NB_ARMOIRES";
	public static final String SAL_NB_BUREAUX_COLKEY = "SAL_NB_BUREAUX";
	public static final String SAL_NB_CHAISES_COLKEY = "SAL_NB_CHAISES";
	public static final String SAL_NB_FENETRES_COLKEY = "SAL_NB_FENETRES";
	public static final String SAL_NB_PLACES_EXAM_LIB_COLKEY = "SAL_NB_PLACES_EXAM_LIB";
	public static final String SAL_NB_TABLES_COLKEY = "SAL_NB_TABLES";
	public static final String SAL_NO_POSTE_COLKEY = "SAL_NO_POSTE";
	public static final String SAL_OBSCUR_COLKEY = "SAL_OBSCUR";
	public static final String SAL_PORTE_COLKEY = "SAL_PORTE";
	public static final String SAL_RESERVABLE_COLKEY = "SAL_RESERVABLE";
	public static final String SAL_RETRO_COLKEY = "SAL_RETRO";
	public static final String SAL_SUPERFICIE_COLKEY = "SAL_SUPERFICIE";
	public static final String SAL_TABLEAU_COLKEY = "SAL_TABLEAU";
	public static final String SAL_TABLEAU_BLANC_COLKEY = "SAL_TABLEAU_BLANC";
	public static final String SAL_TABLEAU_PAPIER_COLKEY = "SAL_TABLEAU_PAPIER";
	public static final String SAL_TELEVISION_COLKEY = "SAL_TELEVISION";

	// Relationships
	public static final String DEPOSITAIRES_KEY = "depositaires";
	public static final String DETAIL_POURCENTAGES_KEY = "detailPourcentages";
	public static final String LOCAL_KEY = "local";
	public static final String PRISESS_KEY = "prisess";
	public static final String REPART_LOT_SALLES_KEY = "repartLotSalles";
	public static final String REPARTS_BAT_IMP_GEO_KEY = "repartsBatImpGeo";
	public static final String TYPE_SALLE_KEY = "typeSalle";

	// Utilities methods
	  public Salles localInstanceIn(EOEditingContext editingContext) {
	    Salles localInstance = (Salles)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Salles getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Salles.ENTITY_NAME);
		      return (Salles) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cLocal() {
    return (String) storedValueForKey("cLocal");
  }

  public void setCLocal(String value) {
    takeStoredValueForKey(value, "cLocal");
  }

  public NSTimestamp dAnnulation() {
    return (NSTimestamp) storedValueForKey("dAnnulation");
  }

  public void setDAnnulation(NSTimestamp value) {
    takeStoredValueForKey(value, "dAnnulation");
  }

  public Integer salCapacite() {
    return (Integer) storedValueForKey("salCapacite");
  }

  public void setSalCapacite(Integer value) {
    takeStoredValueForKey(value, "salCapacite");
  }

  public String salDescriptif() {
    return (String) storedValueForKey("salDescriptif");
  }

  public void setSalDescriptif(String value) {
    takeStoredValueForKey(value, "salDescriptif");
  }

  public String salEcran() {
    return (String) storedValueForKey("salEcran");
  }

  public void setSalEcran(String value) {
    takeStoredValueForKey(value, "salEcran");
  }

  public String salEtage() {
    return (String) storedValueForKey("salEtage");
  }

  public void setSalEtage(String value) {
    takeStoredValueForKey(value, "salEtage");
  }

  public String salInsonorisee() {
    return (String) storedValueForKey("salInsonorisee");
  }

  public void setSalInsonorisee(String value) {
    takeStoredValueForKey(value, "salInsonorisee");
  }

  public Integer salNbArmoires() {
    return (Integer) storedValueForKey("salNbArmoires");
  }

  public void setSalNbArmoires(Integer value) {
    takeStoredValueForKey(value, "salNbArmoires");
  }

  public Integer salNbBureaux() {
    return (Integer) storedValueForKey("salNbBureaux");
  }

  public void setSalNbBureaux(Integer value) {
    takeStoredValueForKey(value, "salNbBureaux");
  }

  public Integer salNbChaises() {
    return (Integer) storedValueForKey("salNbChaises");
  }

  public void setSalNbChaises(Integer value) {
    takeStoredValueForKey(value, "salNbChaises");
  }

  public Integer salNbFenetres() {
    return (Integer) storedValueForKey("salNbFenetres");
  }

  public void setSalNbFenetres(Integer value) {
    takeStoredValueForKey(value, "salNbFenetres");
  }

  public Integer salNbPlacesExamLib() {
    return (Integer) storedValueForKey("salNbPlacesExamLib");
  }

  public void setSalNbPlacesExamLib(Integer value) {
    takeStoredValueForKey(value, "salNbPlacesExamLib");
  }

  public Integer salNbTables() {
    return (Integer) storedValueForKey("salNbTables");
  }

  public void setSalNbTables(Integer value) {
    takeStoredValueForKey(value, "salNbTables");
  }

  public String salNoPoste() {
    return (String) storedValueForKey("salNoPoste");
  }

  public void setSalNoPoste(String value) {
    takeStoredValueForKey(value, "salNoPoste");
  }

  public String salObscur() {
    return (String) storedValueForKey("salObscur");
  }

  public void setSalObscur(String value) {
    takeStoredValueForKey(value, "salObscur");
  }

  public String salPorte() {
    return (String) storedValueForKey("salPorte");
  }

  public void setSalPorte(String value) {
    takeStoredValueForKey(value, "salPorte");
  }

  public String salReservable() {
    return (String) storedValueForKey("salReservable");
  }

  public void setSalReservable(String value) {
    takeStoredValueForKey(value, "salReservable");
  }

  public String salRetro() {
    return (String) storedValueForKey("salRetro");
  }

  public void setSalRetro(String value) {
    takeStoredValueForKey(value, "salRetro");
  }

  public java.math.BigDecimal salSuperficie() {
    return (java.math.BigDecimal) storedValueForKey("salSuperficie");
  }

  public void setSalSuperficie(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "salSuperficie");
  }

  public String salTableau() {
    return (String) storedValueForKey("salTableau");
  }

  public void setSalTableau(String value) {
    takeStoredValueForKey(value, "salTableau");
  }

  public String salTableauBlanc() {
    return (String) storedValueForKey("salTableauBlanc");
  }

  public void setSalTableauBlanc(String value) {
    takeStoredValueForKey(value, "salTableauBlanc");
  }

  public String salTableauPapier() {
    return (String) storedValueForKey("salTableauPapier");
  }

  public void setSalTableauPapier(String value) {
    takeStoredValueForKey(value, "salTableauPapier");
  }

  public String salTelevision() {
    return (String) storedValueForKey("salTelevision");
  }

  public void setSalTelevision(String value) {
    takeStoredValueForKey(value, "salTelevision");
  }

  public org.cocktail.superplan.server.metier.Local local() {
    return (org.cocktail.superplan.server.metier.Local)storedValueForKey("local");
  }

  public void setLocalRelationship(org.cocktail.superplan.server.metier.Local value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.Local oldValue = local();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "local");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "local");
    }
  }
  
  public org.cocktail.superplan.server.metier.TypeSalle typeSalle() {
    return (org.cocktail.superplan.server.metier.TypeSalle)storedValueForKey("typeSalle");
  }

  public void setTypeSalleRelationship(org.cocktail.superplan.server.metier.TypeSalle value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.TypeSalle oldValue = typeSalle();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "typeSalle");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "typeSalle");
    }
  }
  
  public NSArray depositaires() {
    return (NSArray)storedValueForKey("depositaires");
  }

  public NSArray depositaires(EOQualifier qualifier) {
    return depositaires(qualifier, null, false);
  }

  public NSArray depositaires(EOQualifier qualifier, boolean fetch) {
    return depositaires(qualifier, null, fetch);
  }

  public NSArray depositaires(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.DepositaireSalles.SALLE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.DepositaireSalles.fetchDepositaireSalleses(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = depositaires();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToDepositairesRelationship(org.cocktail.superplan.server.metier.DepositaireSalles object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "depositaires");
  }

  public void removeFromDepositairesRelationship(org.cocktail.superplan.server.metier.DepositaireSalles object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "depositaires");
  }

  public org.cocktail.superplan.server.metier.DepositaireSalles createDepositairesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("DepositaireSalles");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "depositaires");
    return (org.cocktail.superplan.server.metier.DepositaireSalles) eo;
  }

  public void deleteDepositairesRelationship(org.cocktail.superplan.server.metier.DepositaireSalles object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "depositaires");
    editingContext().deleteObject(object);
  }

  public void deleteAllDepositairesRelationships() {
    Enumeration objects = depositaires().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteDepositairesRelationship((org.cocktail.superplan.server.metier.DepositaireSalles)objects.nextElement());
    }
  }

  public NSArray detailPourcentages() {
    return (NSArray)storedValueForKey("detailPourcentages");
  }

  public NSArray detailPourcentages(EOQualifier qualifier) {
    return detailPourcentages(qualifier, null, false);
  }

  public NSArray detailPourcentages(EOQualifier qualifier, boolean fetch) {
    return detailPourcentages(qualifier, null, fetch);
  }

  public NSArray detailPourcentages(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.DetailPourcentage.SALLES_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.DetailPourcentage.fetchDetailPourcentages(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = detailPourcentages();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToDetailPourcentagesRelationship(org.cocktail.superplan.server.metier.DetailPourcentage object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "detailPourcentages");
  }

  public void removeFromDetailPourcentagesRelationship(org.cocktail.superplan.server.metier.DetailPourcentage object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "detailPourcentages");
  }

  public org.cocktail.superplan.server.metier.DetailPourcentage createDetailPourcentagesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("DetailPourcentage");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "detailPourcentages");
    return (org.cocktail.superplan.server.metier.DetailPourcentage) eo;
  }

  public void deleteDetailPourcentagesRelationship(org.cocktail.superplan.server.metier.DetailPourcentage object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "detailPourcentages");
    editingContext().deleteObject(object);
  }

  public void deleteAllDetailPourcentagesRelationships() {
    Enumeration objects = detailPourcentages().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteDetailPourcentagesRelationship((org.cocktail.superplan.server.metier.DetailPourcentage)objects.nextElement());
    }
  }

  public NSArray prisess() {
    return (NSArray)storedValueForKey("prisess");
  }

  public NSArray prisess(EOQualifier qualifier) {
    return prisess(qualifier, null);
  }

  public NSArray prisess(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = prisess();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToPrisessRelationship(org.cocktail.superplan.server.metier.Prises object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "prisess");
  }

  public void removeFromPrisessRelationship(org.cocktail.superplan.server.metier.Prises object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "prisess");
  }

  public org.cocktail.superplan.server.metier.Prises createPrisessRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Prises");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "prisess");
    return (org.cocktail.superplan.server.metier.Prises) eo;
  }

  public void deletePrisessRelationship(org.cocktail.superplan.server.metier.Prises object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "prisess");
    editingContext().deleteObject(object);
  }

  public void deleteAllPrisessRelationships() {
    Enumeration objects = prisess().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deletePrisessRelationship((org.cocktail.superplan.server.metier.Prises)objects.nextElement());
    }
  }

  public NSArray repartLotSalles() {
    return (NSArray)storedValueForKey("repartLotSalles");
  }

  public NSArray repartLotSalles(EOQualifier qualifier) {
    return repartLotSalles(qualifier, null, false);
  }

  public NSArray repartLotSalles(EOQualifier qualifier, boolean fetch) {
    return repartLotSalles(qualifier, null, fetch);
  }

  public NSArray repartLotSalles(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.RepartLotSalle.SALLE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.RepartLotSalle.fetchRepartLotSalles(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = repartLotSalles();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRepartLotSallesRelationship(org.cocktail.superplan.server.metier.RepartLotSalle object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartLotSalles");
  }

  public void removeFromRepartLotSallesRelationship(org.cocktail.superplan.server.metier.RepartLotSalle object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartLotSalles");
  }

  public org.cocktail.superplan.server.metier.RepartLotSalle createRepartLotSallesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartLotSalle");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartLotSalles");
    return (org.cocktail.superplan.server.metier.RepartLotSalle) eo;
  }

  public void deleteRepartLotSallesRelationship(org.cocktail.superplan.server.metier.RepartLotSalle object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartLotSalles");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartLotSallesRelationships() {
    Enumeration objects = repartLotSalles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartLotSallesRelationship((org.cocktail.superplan.server.metier.RepartLotSalle)objects.nextElement());
    }
  }

  public NSArray repartsBatImpGeo() {
    return (NSArray)storedValueForKey("repartsBatImpGeo");
  }

  public NSArray repartsBatImpGeo(EOQualifier qualifier) {
    return repartsBatImpGeo(qualifier, null);
  }

  public NSArray repartsBatImpGeo(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = repartsBatImpGeo();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToRepartsBatImpGeoRelationship(org.cocktail.superplan.server.metier.RepartBatImpGeo object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartsBatImpGeo");
  }

  public void removeFromRepartsBatImpGeoRelationship(org.cocktail.superplan.server.metier.RepartBatImpGeo object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartsBatImpGeo");
  }

  public org.cocktail.superplan.server.metier.RepartBatImpGeo createRepartsBatImpGeoRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartBatImpGeo");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartsBatImpGeo");
    return (org.cocktail.superplan.server.metier.RepartBatImpGeo) eo;
  }

  public void deleteRepartsBatImpGeoRelationship(org.cocktail.superplan.server.metier.RepartBatImpGeo object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartsBatImpGeo");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartsBatImpGeoRelationships() {
    Enumeration objects = repartsBatImpGeo().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartsBatImpGeoRelationship((org.cocktail.superplan.server.metier.RepartBatImpGeo)objects.nextElement());
    }
  }


  public static Salles createSalles(EOEditingContext editingContext, String salEtage
, String salPorte
) {
    Salles eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Salles.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Salles.ENTITY_NAME + "' !");
    } else
    {
        eo = (Salles) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setSalEtage(salEtage);
		eo.setSalPorte(salPorte);
    return eo;
  }

  public static NSArray fetchAllSalleses(EOEditingContext editingContext) {
    return _Salles.fetchAllSalleses(editingContext, null);
  }

  public static NSArray fetchAllSalleses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Salles.fetchSalleses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchSalleses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Salles.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Salles fetchSalles(EOEditingContext editingContext, String keyName, Object value) {
    return _Salles.fetchSalles(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Salles fetchSalles(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Salles.fetchSalleses(editingContext, qualifier, null);
    Salles eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Salles)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Salles that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Salles fetchRequiredSalles(EOEditingContext editingContext, String keyName, Object value) {
    return _Salles.fetchRequiredSalles(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Salles fetchRequiredSalles(EOEditingContext editingContext, EOQualifier qualifier) {
    Salles eoObject = _Salles.fetchSalles(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Salles that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Salles localInstanceIn(EOEditingContext editingContext, Salles eo) {
    Salles localInstance = (eo == null) ? null : (Salles)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
