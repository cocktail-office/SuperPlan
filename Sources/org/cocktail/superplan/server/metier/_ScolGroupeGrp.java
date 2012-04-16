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

// DO NOT EDIT.  Make changes to ScolGroupeGrp.java instead.
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

public abstract class _ScolGroupeGrp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolGroupeGrp";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_GROUPE_GRP";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "ggrpKey";

	public static final String GGRP_CODE_KEY = "ggrpCode";
	public static final String GGRP_DATE_DEBUT_KEY = "ggrpDateDebut";
	public static final String GGRP_DATE_FIN_KEY = "ggrpDateFin";
	public static final String GGRP_INT_CAPACITE_KEY = "ggrpIntCapacite";
	public static final String GGRP_KEY_KEY = "ggrpKey";
	public static final String GGRP_LIBELLE_KEY = "ggrpLibelle";
	public static final String GGRP_MAX_CAPACITE_KEY = "ggrpMaxCapacite";
	public static final String GGRP_MAX_TEMOIN_KEY = "ggrpMaxTemoin";

	public static final String GGRP_CODE_COLKEY = "GGRP_CODE";
	public static final String GGRP_DATE_DEBUT_COLKEY = "GGRP_DATE_DEBUT";
	public static final String GGRP_DATE_FIN_COLKEY = "GGRP_DATE_FIN";
	public static final String GGRP_INT_CAPACITE_COLKEY = "GGRP_INT_CAPACITE";
	public static final String GGRP_KEY_COLKEY = "GGRP_KEY";
	public static final String GGRP_LIBELLE_COLKEY = "GGRP_LIBELLE";
	public static final String GGRP_MAX_CAPACITE_COLKEY = "GGRP_MAX_CAPACITE";
	public static final String GGRP_MAX_TEMOIN_COLKEY = "GGRP_MAX_TEMOIN";

	// Relationships
	public static final String INCLUS_FILS_KEY = "inclusFils";
	public static final String INCLUS_PERE_KEY = "inclusPere";
	public static final String SCOL_GROUPE_OBJET_KEY = "scolGroupeObjet";
	public static final String SCOL_GROUPE_OBJET_ELPS_KEY = "scolGroupeObjetElps";
	public static final String SCOL_GROUPE_OBJET_VDIS_KEY = "scolGroupeObjetVdis";
	public static final String SCOL_INSCRIPTION_GRPS_KEY = "scolInscriptionGrps";

	// Utilities methods
	  public ScolGroupeGrp localInstanceIn(EOEditingContext editingContext) {
	    ScolGroupeGrp localInstance = (ScolGroupeGrp)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolGroupeGrp getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolGroupeGrp.ENTITY_NAME);
		      return (ScolGroupeGrp) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String ggrpCode() {
    return (String) storedValueForKey("ggrpCode");
  }

  public void setGgrpCode(String value) {
    takeStoredValueForKey(value, "ggrpCode");
  }

  public Integer ggrpDateDebut() {
    return (Integer) storedValueForKey("ggrpDateDebut");
  }

  public void setGgrpDateDebut(Integer value) {
    takeStoredValueForKey(value, "ggrpDateDebut");
  }

  public Integer ggrpDateFin() {
    return (Integer) storedValueForKey("ggrpDateFin");
  }

  public void setGgrpDateFin(Integer value) {
    takeStoredValueForKey(value, "ggrpDateFin");
  }

  public Integer ggrpIntCapacite() {
    return (Integer) storedValueForKey("ggrpIntCapacite");
  }

  public void setGgrpIntCapacite(Integer value) {
    takeStoredValueForKey(value, "ggrpIntCapacite");
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

  public Integer ggrpMaxCapacite() {
    return (Integer) storedValueForKey("ggrpMaxCapacite");
  }

  public void setGgrpMaxCapacite(Integer value) {
    takeStoredValueForKey(value, "ggrpMaxCapacite");
  }

  public String ggrpMaxTemoin() {
    return (String) storedValueForKey("ggrpMaxTemoin");
  }

  public void setGgrpMaxTemoin(String value) {
    takeStoredValueForKey(value, "ggrpMaxTemoin");
  }

  public NSArray inclusFils() {
    return (NSArray)storedValueForKey("inclusFils");
  }

  public NSArray inclusFils(EOQualifier qualifier) {
    return inclusFils(qualifier, null, false);
  }

  public NSArray inclusFils(EOQualifier qualifier, boolean fetch) {
    return inclusFils(qualifier, null, fetch);
  }

  public NSArray inclusFils(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ScolGroupeInclusion.GROUPE_PERE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ScolGroupeInclusion.fetchScolGroupeInclusions(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = inclusFils();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToInclusFilsRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "inclusFils");
  }

  public void removeFromInclusFilsRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "inclusFils");
  }

  public org.cocktail.superplan.server.metier.ScolGroupeInclusion createInclusFilsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeInclusion");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "inclusFils");
    return (org.cocktail.superplan.server.metier.ScolGroupeInclusion) eo;
  }

  public void deleteInclusFilsRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "inclusFils");
    editingContext().deleteObject(object);
  }

  public void deleteAllInclusFilsRelationships() {
    Enumeration objects = inclusFils().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteInclusFilsRelationship((org.cocktail.superplan.server.metier.ScolGroupeInclusion)objects.nextElement());
    }
  }

  public NSArray inclusPere() {
    return (NSArray)storedValueForKey("inclusPere");
  }

  public NSArray inclusPere(EOQualifier qualifier) {
    return inclusPere(qualifier, null, false);
  }

  public NSArray inclusPere(EOQualifier qualifier, boolean fetch) {
    return inclusPere(qualifier, null, fetch);
  }

  public NSArray inclusPere(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ScolGroupeInclusion.GROUPE_FILS_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ScolGroupeInclusion.fetchScolGroupeInclusions(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = inclusPere();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToInclusPereRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "inclusPere");
  }

  public void removeFromInclusPereRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "inclusPere");
  }

  public org.cocktail.superplan.server.metier.ScolGroupeInclusion createInclusPereRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeInclusion");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "inclusPere");
    return (org.cocktail.superplan.server.metier.ScolGroupeInclusion) eo;
  }

  public void deleteInclusPereRelationship(org.cocktail.superplan.server.metier.ScolGroupeInclusion object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "inclusPere");
    editingContext().deleteObject(object);
  }

  public void deleteAllInclusPereRelationships() {
    Enumeration objects = inclusPere().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteInclusPereRelationship((org.cocktail.superplan.server.metier.ScolGroupeInclusion)objects.nextElement());
    }
  }

  public NSArray scolGroupeObjet() {
    return (NSArray)storedValueForKey("scolGroupeObjet");
  }

  public NSArray scolGroupeObjet(EOQualifier qualifier) {
    return scolGroupeObjet(qualifier, null, false);
  }

  public NSArray scolGroupeObjet(EOQualifier qualifier, boolean fetch) {
    return scolGroupeObjet(qualifier, null, fetch);
  }

  public NSArray scolGroupeObjet(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ScolGroupeObjet.SCOL_GROUPE_GRP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ScolGroupeObjet.fetchScolGroupeObjets(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = scolGroupeObjet();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToScolGroupeObjetRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjet object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolGroupeObjet");
  }

  public void removeFromScolGroupeObjetRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjet");
  }

  public org.cocktail.superplan.server.metier.ScolGroupeObjet createScolGroupeObjetRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeObjet");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolGroupeObjet");
    return (org.cocktail.superplan.server.metier.ScolGroupeObjet) eo;
  }

  public void deleteScolGroupeObjetRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjet object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjet");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolGroupeObjetRelationships() {
    Enumeration objects = scolGroupeObjet().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolGroupeObjetRelationship((org.cocktail.superplan.server.metier.ScolGroupeObjet)objects.nextElement());
    }
  }

  public NSArray scolGroupeObjetElps() {
    return (NSArray)storedValueForKey("scolGroupeObjetElps");
  }

  public NSArray scolGroupeObjetElps(EOQualifier qualifier) {
    return scolGroupeObjetElps(qualifier, null);
  }

  public NSArray scolGroupeObjetElps(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = scolGroupeObjetElps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolGroupeObjetElpsRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjetElp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolGroupeObjetElps");
  }

  public void removeFromScolGroupeObjetElpsRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjetElp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjetElps");
  }

  public org.cocktail.superplan.server.metier.ScolGroupeObjetElp createScolGroupeObjetElpsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeObjetElp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolGroupeObjetElps");
    return (org.cocktail.superplan.server.metier.ScolGroupeObjetElp) eo;
  }

  public void deleteScolGroupeObjetElpsRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjetElp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjetElps");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolGroupeObjetElpsRelationships() {
    Enumeration objects = scolGroupeObjetElps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolGroupeObjetElpsRelationship((org.cocktail.superplan.server.metier.ScolGroupeObjetElp)objects.nextElement());
    }
  }

  public NSArray scolGroupeObjetVdis() {
    return (NSArray)storedValueForKey("scolGroupeObjetVdis");
  }

  public NSArray scolGroupeObjetVdis(EOQualifier qualifier) {
    return scolGroupeObjetVdis(qualifier, null);
  }

  public NSArray scolGroupeObjetVdis(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = scolGroupeObjetVdis();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolGroupeObjetVdisRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjetVdi object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolGroupeObjetVdis");
  }

  public void removeFromScolGroupeObjetVdisRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjetVdi object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjetVdis");
  }

  public org.cocktail.superplan.server.metier.ScolGroupeObjetVdi createScolGroupeObjetVdisRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolGroupeObjetVdi");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolGroupeObjetVdis");
    return (org.cocktail.superplan.server.metier.ScolGroupeObjetVdi) eo;
  }

  public void deleteScolGroupeObjetVdisRelationship(org.cocktail.superplan.server.metier.ScolGroupeObjetVdi object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolGroupeObjetVdis");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolGroupeObjetVdisRelationships() {
    Enumeration objects = scolGroupeObjetVdis().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolGroupeObjetVdisRelationship((org.cocktail.superplan.server.metier.ScolGroupeObjetVdi)objects.nextElement());
    }
  }

  public NSArray scolInscriptionGrps() {
    return (NSArray)storedValueForKey("scolInscriptionGrps");
  }

  public NSArray scolInscriptionGrps(EOQualifier qualifier) {
    return scolInscriptionGrps(qualifier, null);
  }

  public NSArray scolInscriptionGrps(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = scolInscriptionGrps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolInscriptionGrpsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionGrp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolInscriptionGrps");
  }

  public void removeFromScolInscriptionGrpsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionGrp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionGrps");
  }

  public org.cocktail.superplan.server.metier.ScolInscriptionGrp createScolInscriptionGrpsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolInscriptionGrp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolInscriptionGrps");
    return (org.cocktail.superplan.server.metier.ScolInscriptionGrp) eo;
  }

  public void deleteScolInscriptionGrpsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionGrp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionGrps");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolInscriptionGrpsRelationships() {
    Enumeration objects = scolInscriptionGrps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolInscriptionGrpsRelationship((org.cocktail.superplan.server.metier.ScolInscriptionGrp)objects.nextElement());
    }
  }


  public static ScolGroupeGrp createScolGroupeGrp(EOEditingContext editingContext, String ggrpCode
, Integer ggrpDateDebut
, Integer ggrpKey
, String ggrpLibelle
, String ggrpMaxTemoin
) {
    ScolGroupeGrp eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolGroupeGrp.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolGroupeGrp.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolGroupeGrp) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setGgrpCode(ggrpCode);
		eo.setGgrpDateDebut(ggrpDateDebut);
		eo.setGgrpKey(ggrpKey);
		eo.setGgrpLibelle(ggrpLibelle);
		eo.setGgrpMaxTemoin(ggrpMaxTemoin);
    return eo;
  }

  public static NSArray fetchAllScolGroupeGrps(EOEditingContext editingContext) {
    return _ScolGroupeGrp.fetchAllScolGroupeGrps(editingContext, null);
  }

  public static NSArray fetchAllScolGroupeGrps(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolGroupeGrp.fetchScolGroupeGrps(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolGroupeGrps(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolGroupeGrp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolGroupeGrp fetchScolGroupeGrp(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeGrp.fetchScolGroupeGrp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeGrp fetchScolGroupeGrp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolGroupeGrp.fetchScolGroupeGrps(editingContext, qualifier, null);
    ScolGroupeGrp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolGroupeGrp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolGroupeGrp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeGrp fetchRequiredScolGroupeGrp(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolGroupeGrp.fetchRequiredScolGroupeGrp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolGroupeGrp fetchRequiredScolGroupeGrp(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolGroupeGrp eoObject = _ScolGroupeGrp.fetchScolGroupeGrp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolGroupeGrp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolGroupeGrp localInstanceIn(EOEditingContext editingContext, ScolGroupeGrp eo) {
    ScolGroupeGrp localInstance = (eo == null) ? null : (ScolGroupeGrp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
