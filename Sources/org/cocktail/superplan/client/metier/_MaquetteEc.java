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

// DO NOT EDIT.  Make changes to MaquetteEc.java instead.
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

public abstract class _MaquetteEc extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteEc";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_EC";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mecKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String FDSC_KEY_KEY = "fdscKey";
	public static final String MEC_BASE_KEY = "mecBase";
	public static final String MEC_CODE_KEY = "mecCode";
	public static final String MEC_HORAIRE_ETU_KEY = "mecHoraireEtu";
	public static final String MEC_LIBELLE_KEY = "mecLibelle";
	public static final String MEC_LIBELLE_COURT_KEY = "mecLibelleCourt";
	public static final String MEC_POINTS_KEY = "mecPoints";
	public static final String MEC_SESSION1_KEY = "mecSession1";
	public static final String MEC_SESSION2_KEY = "mecSession2";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String FDSC_KEY_COLKEY = "FDSC_KEY";
	public static final String MEC_BASE_COLKEY = "MEC_BASE";
	public static final String MEC_CODE_COLKEY = "MEC_CODE";
	public static final String MEC_HORAIRE_ETU_COLKEY = "MEC_HORAIRE_ETU";
	public static final String MEC_LIBELLE_COLKEY = "MEC_LIBELLE";
	public static final String MEC_LIBELLE_COURT_COLKEY = "MEC_LIBELLE_COURT";
	public static final String MEC_POINTS_COLKEY = "MEC_POINTS";
	public static final String MEC_SESSION1_COLKEY = "MEC_SESSION1";
	public static final String MEC_SESSION2_COLKEY = "MEC_SESSION2";

	// Relationships
	public static final String EXAMEN_ENTETES_KEY = "examenEntetes";
	public static final String FORMATION_ANNEE_KEY = "formationAnnee";
	public static final String MAQUETTE_REPARTITION_APS_KEY = "maquetteRepartitionAps";
	public static final String MAQUETTE_REPARTITION_ECS_KEY = "maquetteRepartitionEcs";
	public static final String MAQUETTE_RESPONSABLE_ECS_KEY = "maquetteResponsableEcs";
	public static final String RESA_COULEUR_EC_KEY = "resaCouleurEc";
	public static final String V_PARCOURS_EC_KEY = "vParcoursEc";

	// Utilities methods
	  public MaquetteEc localInstanceIn(EOEditingContext editingContext) {
	    MaquetteEc localInstance = (MaquetteEc)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteEc getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteEc.ENTITY_NAME);
		      return (MaquetteEc) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer fdscKey() {
    return (Integer) storedValueForKey("fdscKey");
  }

  public void setFdscKey(Integer value) {
    takeStoredValueForKey(value, "fdscKey");
  }

  public java.math.BigDecimal mecBase() {
    return (java.math.BigDecimal) storedValueForKey("mecBase");
  }

  public void setMecBase(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mecBase");
  }

  public String mecCode() {
    return (String) storedValueForKey("mecCode");
  }

  public void setMecCode(String value) {
    takeStoredValueForKey(value, "mecCode");
  }

  public java.math.BigDecimal mecHoraireEtu() {
    return (java.math.BigDecimal) storedValueForKey("mecHoraireEtu");
  }

  public void setMecHoraireEtu(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mecHoraireEtu");
  }

  public String mecLibelle() {
    return (String) storedValueForKey("mecLibelle");
  }

  public void setMecLibelle(String value) {
    takeStoredValueForKey(value, "mecLibelle");
  }

  public String mecLibelleCourt() {
    return (String) storedValueForKey("mecLibelleCourt");
  }

  public void setMecLibelleCourt(String value) {
    takeStoredValueForKey(value, "mecLibelleCourt");
  }

  public Integer mecPoints() {
    return (Integer) storedValueForKey("mecPoints");
  }

  public void setMecPoints(Integer value) {
    takeStoredValueForKey(value, "mecPoints");
  }

  public String mecSession1() {
    return (String) storedValueForKey("mecSession1");
  }

  public void setMecSession1(String value) {
    takeStoredValueForKey(value, "mecSession1");
  }

  public String mecSession2() {
    return (String) storedValueForKey("mecSession2");
  }

  public void setMecSession2(String value) {
    takeStoredValueForKey(value, "mecSession2");
  }

  public org.cocktail.superplan.client.metier.FormationAnnee formationAnnee() {
    return (org.cocktail.superplan.client.metier.FormationAnnee)storedValueForKey("formationAnnee");
  }

  public void setFormationAnneeRelationship(org.cocktail.superplan.client.metier.FormationAnnee value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.FormationAnnee oldValue = formationAnnee();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationAnnee");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationAnnee");
    }
  }
  
  public org.cocktail.superplan.client.metier.VParcoursEc vParcoursEc() {
    return (org.cocktail.superplan.client.metier.VParcoursEc)storedValueForKey("vParcoursEc");
  }

  public void setVParcoursEcRelationship(org.cocktail.superplan.client.metier.VParcoursEc value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.VParcoursEc oldValue = vParcoursEc();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "vParcoursEc");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "vParcoursEc");
    }
  }
  
  public NSArray examenEntetes() {
    return (NSArray)storedValueForKey("examenEntetes");
  }

  public NSArray examenEntetes(EOQualifier qualifier) {
    return examenEntetes(qualifier, null, false);
  }

  public NSArray examenEntetes(EOQualifier qualifier, boolean fetch) {
    return examenEntetes(qualifier, null, fetch);
  }

  public NSArray examenEntetes(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ExamenEntete.EC_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ExamenEntete.fetchExamenEntetes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = examenEntetes();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToExamenEntetesRelationship(org.cocktail.superplan.client.metier.ExamenEntete object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "examenEntetes");
  }

  public void removeFromExamenEntetesRelationship(org.cocktail.superplan.client.metier.ExamenEntete object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "examenEntetes");
  }

  public org.cocktail.superplan.client.metier.ExamenEntete createExamenEntetesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ExamenEntete");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "examenEntetes");
    return (org.cocktail.superplan.client.metier.ExamenEntete) eo;
  }

  public void deleteExamenEntetesRelationship(org.cocktail.superplan.client.metier.ExamenEntete object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "examenEntetes");
    editingContext().deleteObject(object);
  }

  public void deleteAllExamenEntetesRelationships() {
    Enumeration objects = examenEntetes().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteExamenEntetesRelationship((org.cocktail.superplan.client.metier.ExamenEntete)objects.nextElement());
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
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.MaquetteRepartitionAp.MAQUETTE_EC_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
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

  public NSArray maquetteRepartitionEcs() {
    return (NSArray)storedValueForKey("maquetteRepartitionEcs");
  }

  public NSArray maquetteRepartitionEcs(EOQualifier qualifier) {
    return maquetteRepartitionEcs(qualifier, null, false);
  }

  public NSArray maquetteRepartitionEcs(EOQualifier qualifier, boolean fetch) {
    return maquetteRepartitionEcs(qualifier, null, fetch);
  }

  public NSArray maquetteRepartitionEcs(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.MaquetteRepartitionEc.MAQUETTE_EC_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.MaquetteRepartitionEc.fetchMaquetteRepartitionEcs(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = maquetteRepartitionEcs();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToMaquetteRepartitionEcsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionEc object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "maquetteRepartitionEcs");
  }

  public void removeFromMaquetteRepartitionEcsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionEcs");
  }

  public org.cocktail.superplan.client.metier.MaquetteRepartitionEc createMaquetteRepartitionEcsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteRepartitionEc");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "maquetteRepartitionEcs");
    return (org.cocktail.superplan.client.metier.MaquetteRepartitionEc) eo;
  }

  public void deleteMaquetteRepartitionEcsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionEcs");
    editingContext().deleteObject(object);
  }

  public void deleteAllMaquetteRepartitionEcsRelationships() {
    Enumeration objects = maquetteRepartitionEcs().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteMaquetteRepartitionEcsRelationship((org.cocktail.superplan.client.metier.MaquetteRepartitionEc)objects.nextElement());
    }
  }

  public NSArray maquetteResponsableEcs() {
    return (NSArray)storedValueForKey("maquetteResponsableEcs");
  }

  public NSArray maquetteResponsableEcs(EOQualifier qualifier) {
    return maquetteResponsableEcs(qualifier, null, false);
  }

  public NSArray maquetteResponsableEcs(EOQualifier qualifier, boolean fetch) {
    return maquetteResponsableEcs(qualifier, null, fetch);
  }

  public NSArray maquetteResponsableEcs(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.MaquetteResponsableEc.MAQUETTE_EC_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.MaquetteResponsableEc.fetchMaquetteResponsableEcs(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = maquetteResponsableEcs();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToMaquetteResponsableEcsRelationship(org.cocktail.superplan.client.metier.MaquetteResponsableEc object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "maquetteResponsableEcs");
  }

  public void removeFromMaquetteResponsableEcsRelationship(org.cocktail.superplan.client.metier.MaquetteResponsableEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteResponsableEcs");
  }

  public org.cocktail.superplan.client.metier.MaquetteResponsableEc createMaquetteResponsableEcsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteResponsableEc");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "maquetteResponsableEcs");
    return (org.cocktail.superplan.client.metier.MaquetteResponsableEc) eo;
  }

  public void deleteMaquetteResponsableEcsRelationship(org.cocktail.superplan.client.metier.MaquetteResponsableEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteResponsableEcs");
    editingContext().deleteObject(object);
  }

  public void deleteAllMaquetteResponsableEcsRelationships() {
    Enumeration objects = maquetteResponsableEcs().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteMaquetteResponsableEcsRelationship((org.cocktail.superplan.client.metier.MaquetteResponsableEc)objects.nextElement());
    }
  }

  public NSArray resaCouleurEc() {
    return (NSArray)storedValueForKey("resaCouleurEc");
  }

  public NSArray resaCouleurEc(EOQualifier qualifier) {
    return resaCouleurEc(qualifier, null, false);
  }

  public NSArray resaCouleurEc(EOQualifier qualifier, boolean fetch) {
    return resaCouleurEc(qualifier, null, fetch);
  }

  public NSArray resaCouleurEc(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.ResaCouleurEc.MAQUETTE_EC_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.ResaCouleurEc.fetchResaCouleurEcs(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = resaCouleurEc();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToResaCouleurEcRelationship(org.cocktail.superplan.client.metier.ResaCouleurEc object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "resaCouleurEc");
  }

  public void removeFromResaCouleurEcRelationship(org.cocktail.superplan.client.metier.ResaCouleurEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaCouleurEc");
  }

  public org.cocktail.superplan.client.metier.ResaCouleurEc createResaCouleurEcRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ResaCouleurEc");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "resaCouleurEc");
    return (org.cocktail.superplan.client.metier.ResaCouleurEc) eo;
  }

  public void deleteResaCouleurEcRelationship(org.cocktail.superplan.client.metier.ResaCouleurEc object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "resaCouleurEc");
    editingContext().deleteObject(object);
  }

  public void deleteAllResaCouleurEcRelationships() {
    Enumeration objects = resaCouleurEc().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteResaCouleurEcRelationship((org.cocktail.superplan.client.metier.ResaCouleurEc)objects.nextElement());
    }
  }


  public static MaquetteEc createMaquetteEc(EOEditingContext editingContext, Integer fannKey
, Integer fdscKey
, java.math.BigDecimal mecBase
, String mecCode
, java.math.BigDecimal mecHoraireEtu
, Integer mecPoints
) {
    MaquetteEc eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteEc.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteEc.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteEc) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setFdscKey(fdscKey);
		eo.setMecBase(mecBase);
		eo.setMecCode(mecCode);
		eo.setMecHoraireEtu(mecHoraireEtu);
		eo.setMecPoints(mecPoints);
    return eo;
  }

  public static NSArray fetchAllMaquetteEcs(EOEditingContext editingContext) {
    return _MaquetteEc.fetchAllMaquetteEcs(editingContext, null);
  }

  public static NSArray fetchAllMaquetteEcs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteEc.fetchMaquetteEcs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteEcs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteEc.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteEc fetchMaquetteEc(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteEc.fetchMaquetteEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteEc fetchMaquetteEc(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteEc.fetchMaquetteEcs(editingContext, qualifier, null);
    MaquetteEc eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteEc)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteEc fetchRequiredMaquetteEc(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteEc.fetchRequiredMaquetteEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteEc fetchRequiredMaquetteEc(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteEc eoObject = _MaquetteEc.fetchMaquetteEc(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteEc localInstanceIn(EOEditingContext editingContext, MaquetteEc eo) {
    MaquetteEc localInstance = (eo == null) ? null : (MaquetteEc)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
