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

// DO NOT EDIT.  Make changes to MaquetteRepartitionEc.java instead.
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

public abstract class _MaquetteRepartitionEc extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteRepartitionEc";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_REPARTITION_EC";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mrecKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MEC_KEY_KEY = "mecKey";
	public static final String MREC_BONIFIABLE_KEY = "mrecBonifiable";
	public static final String MREC_COEFFICIENT_KEY = "mrecCoefficient";
	public static final String MREC_COMPTABILISABLE_KEY = "mrecComptabilisable";
	public static final String MREC_NIVEAU_KEY = "mrecNiveau";
	public static final String MREC_NOTE_BASE_KEY = "mrecNoteBase";
	public static final String MREC_NOTE_ELIMINATION_KEY = "mrecNoteElimination";
	public static final String MREC_NOTE_OBTENTION_KEY = "mrecNoteObtention";
	public static final String MREC_ORDRE_KEY = "mrecOrdre";
	public static final String MTEC_CODE_KEY = "mtecCode";
	public static final String MUE_KEY_KEY = "mueKey";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String MEC_KEY_COLKEY = "MEC_KEY";
	public static final String MREC_BONIFIABLE_COLKEY = "MREC_BONIFIABLE";
	public static final String MREC_COEFFICIENT_COLKEY = "MREC_COEFFICIENT";
	public static final String MREC_COMPTABILISABLE_COLKEY = "MREC_COMPTABILISABLE";
	public static final String MREC_NIVEAU_COLKEY = "MREC_NIVEAU";
	public static final String MREC_NOTE_BASE_COLKEY = "MREC_NOTE_BASE";
	public static final String MREC_NOTE_ELIMINATION_COLKEY = "MREC_NOTE_ELIMINATION";
	public static final String MREC_NOTE_OBTENTION_COLKEY = "MREC_NOTE_OBTENTION";
	public static final String MREC_ORDRE_COLKEY = "MREC_ORDRE";
	public static final String MTEC_CODE_COLKEY = "MTEC_CODE";
	public static final String MUE_KEY_COLKEY = "MUE_KEY";

	// Relationships
	public static final String MAQUETTE_EC_KEY = "maquetteEc";
	public static final String MAQUETTE_UE_KEY = "maquetteUe";

	// Utilities methods
	  public MaquetteRepartitionEc localInstanceIn(EOEditingContext editingContext) {
	    MaquetteRepartitionEc localInstance = (MaquetteRepartitionEc)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteRepartitionEc getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteRepartitionEc.ENTITY_NAME);
		      return (MaquetteRepartitionEc) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer mecKey() {
    return (Integer) storedValueForKey("mecKey");
  }

  public void setMecKey(Integer value) {
    takeStoredValueForKey(value, "mecKey");
  }

  public Integer mrecBonifiable() {
    return (Integer) storedValueForKey("mrecBonifiable");
  }

  public void setMrecBonifiable(Integer value) {
    takeStoredValueForKey(value, "mrecBonifiable");
  }

  public java.math.BigDecimal mrecCoefficient() {
    return (java.math.BigDecimal) storedValueForKey("mrecCoefficient");
  }

  public void setMrecCoefficient(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mrecCoefficient");
  }

  public Integer mrecComptabilisable() {
    return (Integer) storedValueForKey("mrecComptabilisable");
  }

  public void setMrecComptabilisable(Integer value) {
    takeStoredValueForKey(value, "mrecComptabilisable");
  }

  public String mrecNiveau() {
    return (String) storedValueForKey("mrecNiveau");
  }

  public void setMrecNiveau(String value) {
    takeStoredValueForKey(value, "mrecNiveau");
  }

  public java.math.BigDecimal mrecNoteBase() {
    return (java.math.BigDecimal) storedValueForKey("mrecNoteBase");
  }

  public void setMrecNoteBase(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mrecNoteBase");
  }

  public java.math.BigDecimal mrecNoteElimination() {
    return (java.math.BigDecimal) storedValueForKey("mrecNoteElimination");
  }

  public void setMrecNoteElimination(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mrecNoteElimination");
  }

  public java.math.BigDecimal mrecNoteObtention() {
    return (java.math.BigDecimal) storedValueForKey("mrecNoteObtention");
  }

  public void setMrecNoteObtention(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mrecNoteObtention");
  }

  public Integer mrecOrdre() {
    return (Integer) storedValueForKey("mrecOrdre");
  }

  public void setMrecOrdre(Integer value) {
    takeStoredValueForKey(value, "mrecOrdre");
  }

  public String mtecCode() {
    return (String) storedValueForKey("mtecCode");
  }

  public void setMtecCode(String value) {
    takeStoredValueForKey(value, "mtecCode");
  }

  public Integer mueKey() {
    return (Integer) storedValueForKey("mueKey");
  }

  public void setMueKey(Integer value) {
    takeStoredValueForKey(value, "mueKey");
  }

  public org.cocktail.superplan.server.metier.MaquetteEc maquetteEc() {
    return (org.cocktail.superplan.server.metier.MaquetteEc)storedValueForKey("maquetteEc");
  }

  public void setMaquetteEcRelationship(org.cocktail.superplan.server.metier.MaquetteEc value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteEc oldValue = maquetteEc();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteEc");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteEc");
    }
  }
  
  public org.cocktail.superplan.server.metier.MaquetteUe maquetteUe() {
    return (org.cocktail.superplan.server.metier.MaquetteUe)storedValueForKey("maquetteUe");
  }

  public void setMaquetteUeRelationship(org.cocktail.superplan.server.metier.MaquetteUe value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteUe oldValue = maquetteUe();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteUe");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteUe");
    }
  }
  

  public static MaquetteRepartitionEc createMaquetteRepartitionEc(EOEditingContext editingContext, Integer fannKey
, Integer mecKey
, Integer mrecBonifiable
, java.math.BigDecimal mrecCoefficient
, Integer mrecComptabilisable
, java.math.BigDecimal mrecNoteBase
, java.math.BigDecimal mrecNoteObtention
, Integer mrecOrdre
, String mtecCode
, Integer mueKey
, org.cocktail.superplan.server.metier.MaquetteEc maquetteEc, org.cocktail.superplan.server.metier.MaquetteUe maquetteUe) {
    MaquetteRepartitionEc eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteRepartitionEc.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteRepartitionEc.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteRepartitionEc) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setMecKey(mecKey);
		eo.setMrecBonifiable(mrecBonifiable);
		eo.setMrecCoefficient(mrecCoefficient);
		eo.setMrecComptabilisable(mrecComptabilisable);
		eo.setMrecNoteBase(mrecNoteBase);
		eo.setMrecNoteObtention(mrecNoteObtention);
		eo.setMrecOrdre(mrecOrdre);
		eo.setMtecCode(mtecCode);
		eo.setMueKey(mueKey);
    eo.setMaquetteEcRelationship(maquetteEc);
    eo.setMaquetteUeRelationship(maquetteUe);
    return eo;
  }

  public static NSArray fetchAllMaquetteRepartitionEcs(EOEditingContext editingContext) {
    return _MaquetteRepartitionEc.fetchAllMaquetteRepartitionEcs(editingContext, null);
  }

  public static NSArray fetchAllMaquetteRepartitionEcs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteRepartitionEc.fetchMaquetteRepartitionEcs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteRepartitionEcs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteRepartitionEc.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteRepartitionEc fetchMaquetteRepartitionEc(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteRepartitionEc.fetchMaquetteRepartitionEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteRepartitionEc fetchMaquetteRepartitionEc(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteRepartitionEc.fetchMaquetteRepartitionEcs(editingContext, qualifier, null);
    MaquetteRepartitionEc eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteRepartitionEc)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteRepartitionEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteRepartitionEc fetchRequiredMaquetteRepartitionEc(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteRepartitionEc.fetchRequiredMaquetteRepartitionEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteRepartitionEc fetchRequiredMaquetteRepartitionEc(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteRepartitionEc eoObject = _MaquetteRepartitionEc.fetchMaquetteRepartitionEc(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteRepartitionEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteRepartitionEc localInstanceIn(EOEditingContext editingContext, MaquetteRepartitionEc eo) {
    MaquetteRepartitionEc localInstance = (eo == null) ? null : (MaquetteRepartitionEc)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
