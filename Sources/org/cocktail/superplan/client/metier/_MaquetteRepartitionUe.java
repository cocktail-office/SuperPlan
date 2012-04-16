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

// DO NOT EDIT.  Make changes to MaquetteRepartitionUe.java instead.
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

public abstract class _MaquetteRepartitionUe extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteRepartitionUe";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_REPARTITION_UE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mrueKey";

	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MRUE_BONIFIABLE_KEY = "mrueBonifiable";
	public static final String MRUE_COEFFICIENT_KEY = "mrueCoefficient";
	public static final String MRUE_COMPTABILISABLE_KEY = "mrueComptabilisable";
	public static final String MRUE_NOTE_BASE_KEY = "mrueNoteBase";
	public static final String MRUE_NOTE_ELIMINATION_KEY = "mrueNoteElimination";
	public static final String MRUE_NOTE_OBTENTION_KEY = "mrueNoteObtention";
	public static final String MRUE_ORDRE_KEY = "mrueOrdre";
	public static final String MSEM_KEY_KEY = "msemKey";
	public static final String MUE_KEY_KEY = "mueKey";

	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String MRUE_BONIFIABLE_COLKEY = "MRUE_BONIFIABLE";
	public static final String MRUE_COEFFICIENT_COLKEY = "MRUE_COEFFICIENT";
	public static final String MRUE_COMPTABILISABLE_COLKEY = "MRUE_COMPTABILISABLE";
	public static final String MRUE_NOTE_BASE_COLKEY = "MRUE_NOTE_BASE";
	public static final String MRUE_NOTE_ELIMINATION_COLKEY = "MRUE_NOTE_ELIMINATION";
	public static final String MRUE_NOTE_OBTENTION_COLKEY = "MRUE_NOTE_OBTENTION";
	public static final String MRUE_ORDRE_COLKEY = "MRUE_ORDRE";
	public static final String MSEM_KEY_COLKEY = "MSEM_KEY";
	public static final String MUE_KEY_COLKEY = "MUE_KEY";

	// Relationships
	public static final String MAQUETTE_SEMESTRE_KEY = "maquetteSemestre";
	public static final String MAQUETTE_UE_KEY = "maquetteUe";

	// Utilities methods
	  public MaquetteRepartitionUe localInstanceIn(EOEditingContext editingContext) {
	    MaquetteRepartitionUe localInstance = (MaquetteRepartitionUe)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteRepartitionUe getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteRepartitionUe.ENTITY_NAME);
		      return (MaquetteRepartitionUe) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer mrueBonifiable() {
    return (Integer) storedValueForKey("mrueBonifiable");
  }

  public void setMrueBonifiable(Integer value) {
    takeStoredValueForKey(value, "mrueBonifiable");
  }

  public java.math.BigDecimal mrueCoefficient() {
    return (java.math.BigDecimal) storedValueForKey("mrueCoefficient");
  }

  public void setMrueCoefficient(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mrueCoefficient");
  }

  public Integer mrueComptabilisable() {
    return (Integer) storedValueForKey("mrueComptabilisable");
  }

  public void setMrueComptabilisable(Integer value) {
    takeStoredValueForKey(value, "mrueComptabilisable");
  }

  public java.math.BigDecimal mrueNoteBase() {
    return (java.math.BigDecimal) storedValueForKey("mrueNoteBase");
  }

  public void setMrueNoteBase(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mrueNoteBase");
  }

  public java.math.BigDecimal mrueNoteElimination() {
    return (java.math.BigDecimal) storedValueForKey("mrueNoteElimination");
  }

  public void setMrueNoteElimination(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mrueNoteElimination");
  }

  public java.math.BigDecimal mrueNoteObtention() {
    return (java.math.BigDecimal) storedValueForKey("mrueNoteObtention");
  }

  public void setMrueNoteObtention(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "mrueNoteObtention");
  }

  public Integer mrueOrdre() {
    return (Integer) storedValueForKey("mrueOrdre");
  }

  public void setMrueOrdre(Integer value) {
    takeStoredValueForKey(value, "mrueOrdre");
  }

  public Integer msemKey() {
    return (Integer) storedValueForKey("msemKey");
  }

  public void setMsemKey(Integer value) {
    takeStoredValueForKey(value, "msemKey");
  }

  public Integer mueKey() {
    return (Integer) storedValueForKey("mueKey");
  }

  public void setMueKey(Integer value) {
    takeStoredValueForKey(value, "mueKey");
  }

  public org.cocktail.superplan.client.metier.MaquetteSemestre maquetteSemestre() {
    return (org.cocktail.superplan.client.metier.MaquetteSemestre)storedValueForKey("maquetteSemestre");
  }

  public void setMaquetteSemestreRelationship(org.cocktail.superplan.client.metier.MaquetteSemestre value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteSemestre oldValue = maquetteSemestre();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteSemestre");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteSemestre");
    }
  }
  
  public org.cocktail.superplan.client.metier.MaquetteUe maquetteUe() {
    return (org.cocktail.superplan.client.metier.MaquetteUe)storedValueForKey("maquetteUe");
  }

  public void setMaquetteUeRelationship(org.cocktail.superplan.client.metier.MaquetteUe value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.MaquetteUe oldValue = maquetteUe();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "maquetteUe");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "maquetteUe");
    }
  }
  

  public static MaquetteRepartitionUe createMaquetteRepartitionUe(EOEditingContext editingContext, Integer fannKey
, Integer mrueBonifiable
, java.math.BigDecimal mrueCoefficient
, Integer mrueComptabilisable
, java.math.BigDecimal mrueNoteBase
, java.math.BigDecimal mrueNoteObtention
, Integer mrueOrdre
, Integer msemKey
, Integer mueKey
, org.cocktail.superplan.client.metier.MaquetteSemestre maquetteSemestre, org.cocktail.superplan.client.metier.MaquetteUe maquetteUe) {
    MaquetteRepartitionUe eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteRepartitionUe.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteRepartitionUe.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteRepartitionUe) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFannKey(fannKey);
		eo.setMrueBonifiable(mrueBonifiable);
		eo.setMrueCoefficient(mrueCoefficient);
		eo.setMrueComptabilisable(mrueComptabilisable);
		eo.setMrueNoteBase(mrueNoteBase);
		eo.setMrueNoteObtention(mrueNoteObtention);
		eo.setMrueOrdre(mrueOrdre);
		eo.setMsemKey(msemKey);
		eo.setMueKey(mueKey);
    eo.setMaquetteSemestreRelationship(maquetteSemestre);
    eo.setMaquetteUeRelationship(maquetteUe);
    return eo;
  }

  public static NSArray fetchAllMaquetteRepartitionUes(EOEditingContext editingContext) {
    return _MaquetteRepartitionUe.fetchAllMaquetteRepartitionUes(editingContext, null);
  }

  public static NSArray fetchAllMaquetteRepartitionUes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteRepartitionUe.fetchMaquetteRepartitionUes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteRepartitionUes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteRepartitionUe.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteRepartitionUe fetchMaquetteRepartitionUe(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteRepartitionUe.fetchMaquetteRepartitionUe(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteRepartitionUe fetchMaquetteRepartitionUe(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteRepartitionUe.fetchMaquetteRepartitionUes(editingContext, qualifier, null);
    MaquetteRepartitionUe eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteRepartitionUe)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteRepartitionUe that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteRepartitionUe fetchRequiredMaquetteRepartitionUe(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteRepartitionUe.fetchRequiredMaquetteRepartitionUe(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteRepartitionUe fetchRequiredMaquetteRepartitionUe(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteRepartitionUe eoObject = _MaquetteRepartitionUe.fetchMaquetteRepartitionUe(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteRepartitionUe that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteRepartitionUe localInstanceIn(EOEditingContext editingContext, MaquetteRepartitionUe eo) {
    MaquetteRepartitionUe localInstance = (eo == null) ? null : (MaquetteRepartitionUe)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
