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

// DO NOT EDIT.  Make changes to ExamenEntete.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _ExamenEntete extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ExamenEntete";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_EXAMEN_ENTETE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "eentKey";

	public static final String EELT_CODE_KEY = "eeltCode";
	public static final String EENT_COEFFICIENT_KEY = "eentCoefficient";
	public static final String EENT_DATE_DEBUT_KEY = "eentDateDebut";
	public static final String EENT_DATE_FIN_KEY = "eentDateFin";
	public static final String EENT_DUREE_KEY = "eentDuree";
	public static final String EENT_EPREUVE_KEY = "eentEpreuve";
	public static final String EENT_LIBELLE_KEY = "eentLibelle";
	public static final String EENT_NOTE_BASE_KEY = "eentNoteBase";
	public static final String EENT_QCM_KEY = "eentQcm";
	public static final String EENT_SEMESTRE_KEY = "eentSemestre";
	public static final String EENT_SESSION_KEY = "eentSession";
	public static final String EENT_TRAITE_KEY = "eentTraite";
	public static final String EENT_VALIDITE_KEY = "eentValidite";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String PERS_ID_KEY = "persId";

	public static final String EELT_CODE_COLKEY = "EELT_CODE";
	public static final String EENT_COEFFICIENT_COLKEY = "EENT_COEFFICIENT";
	public static final String EENT_DATE_DEBUT_COLKEY = "EENT_DATE_DEBUT";
	public static final String EENT_DATE_FIN_COLKEY = "EENT_DATE_FIN";
	public static final String EENT_DUREE_COLKEY = "EENT_DUREE";
	public static final String EENT_EPREUVE_COLKEY = "EENT_EPREUVE";
	public static final String EENT_LIBELLE_COLKEY = "EENT_LIBELLE";
	public static final String EENT_NOTE_BASE_COLKEY = "EENT_NOTE_BASE";
	public static final String EENT_QCM_COLKEY = "EENT_QCM";
	public static final String EENT_SEMESTRE_COLKEY = "EENT_SEMESTRE";
	public static final String EENT_SESSION_COLKEY = "EENT_SESSION";
	public static final String EENT_TRAITE_COLKEY = "EENT_TRAITE";
	public static final String EENT_VALIDITE_COLKEY = "EENT_VALIDITE";
	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String PERS_ID_COLKEY = "PERS_ID";

	// Relationships
	public static final String EC_KEY = "ec";

	// Utilities methods
	  public ExamenEntete localInstanceIn(EOEditingContext editingContext) {
	    ExamenEntete localInstance = (ExamenEntete)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ExamenEntete getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ExamenEntete.ENTITY_NAME);
		      return (ExamenEntete) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String eeltCode() {
    return (String) storedValueForKey("eeltCode");
  }

  public void setEeltCode(String value) {
    takeStoredValueForKey(value, "eeltCode");
  }

  public java.math.BigDecimal eentCoefficient() {
    return (java.math.BigDecimal) storedValueForKey("eentCoefficient");
  }

  public void setEentCoefficient(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "eentCoefficient");
  }

  public NSTimestamp eentDateDebut() {
    return (NSTimestamp) storedValueForKey("eentDateDebut");
  }

  public void setEentDateDebut(NSTimestamp value) {
    takeStoredValueForKey(value, "eentDateDebut");
  }

  public NSTimestamp eentDateFin() {
    return (NSTimestamp) storedValueForKey("eentDateFin");
  }

  public void setEentDateFin(NSTimestamp value) {
    takeStoredValueForKey(value, "eentDateFin");
  }

  public NSTimestamp eentDuree() {
    return (NSTimestamp) storedValueForKey("eentDuree");
  }

  public void setEentDuree(NSTimestamp value) {
    takeStoredValueForKey(value, "eentDuree");
  }

  public Integer eentEpreuve() {
    return (Integer) storedValueForKey("eentEpreuve");
  }

  public void setEentEpreuve(Integer value) {
    takeStoredValueForKey(value, "eentEpreuve");
  }

  public String eentLibelle() {
    return (String) storedValueForKey("eentLibelle");
  }

  public void setEentLibelle(String value) {
    takeStoredValueForKey(value, "eentLibelle");
  }

  public java.math.BigDecimal eentNoteBase() {
    return (java.math.BigDecimal) storedValueForKey("eentNoteBase");
  }

  public void setEentNoteBase(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "eentNoteBase");
  }

  public String eentQcm() {
    return (String) storedValueForKey("eentQcm");
  }

  public void setEentQcm(String value) {
    takeStoredValueForKey(value, "eentQcm");
  }

  public Integer eentSemestre() {
    return (Integer) storedValueForKey("eentSemestre");
  }

  public void setEentSemestre(Integer value) {
    takeStoredValueForKey(value, "eentSemestre");
  }

  public Integer eentSession() {
    return (Integer) storedValueForKey("eentSession");
  }

  public void setEentSession(Integer value) {
    takeStoredValueForKey(value, "eentSession");
  }

  public Integer eentTraite() {
    return (Integer) storedValueForKey("eentTraite");
  }

  public void setEentTraite(Integer value) {
    takeStoredValueForKey(value, "eentTraite");
  }

  public String eentValidite() {
    return (String) storedValueForKey("eentValidite");
  }

  public void setEentValidite(String value) {
    takeStoredValueForKey(value, "eentValidite");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public org.cocktail.superplan.server.metier.MaquetteEc ec() {
    return (org.cocktail.superplan.server.metier.MaquetteEc)storedValueForKey("ec");
  }

  public void setEcRelationship(org.cocktail.superplan.server.metier.MaquetteEc value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.MaquetteEc oldValue = ec();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "ec");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "ec");
    }
  }
  

  public static ExamenEntete createExamenEntete(EOEditingContext editingContext, String eeltCode
, java.math.BigDecimal eentCoefficient
, Integer eentEpreuve
, java.math.BigDecimal eentNoteBase
, String eentQcm
, Integer eentSemestre
, Integer eentSession
, Integer eentTraite
, String eentValidite
, Integer fannKey
) {
    ExamenEntete eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ExamenEntete.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ExamenEntete.ENTITY_NAME + "' !");
    } else
    {
        eo = (ExamenEntete) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setEeltCode(eeltCode);
		eo.setEentCoefficient(eentCoefficient);
		eo.setEentEpreuve(eentEpreuve);
		eo.setEentNoteBase(eentNoteBase);
		eo.setEentQcm(eentQcm);
		eo.setEentSemestre(eentSemestre);
		eo.setEentSession(eentSession);
		eo.setEentTraite(eentTraite);
		eo.setEentValidite(eentValidite);
		eo.setFannKey(fannKey);
    return eo;
  }

  public static NSArray fetchAllExamenEntetes(EOEditingContext editingContext) {
    return _ExamenEntete.fetchAllExamenEntetes(editingContext, null);
  }

  public static NSArray fetchAllExamenEntetes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ExamenEntete.fetchExamenEntetes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchExamenEntetes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ExamenEntete.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ExamenEntete fetchExamenEntete(EOEditingContext editingContext, String keyName, Object value) {
    return _ExamenEntete.fetchExamenEntete(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ExamenEntete fetchExamenEntete(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ExamenEntete.fetchExamenEntetes(editingContext, qualifier, null);
    ExamenEntete eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ExamenEntete)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ExamenEntete that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ExamenEntete fetchRequiredExamenEntete(EOEditingContext editingContext, String keyName, Object value) {
    return _ExamenEntete.fetchRequiredExamenEntete(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ExamenEntete fetchRequiredExamenEntete(EOEditingContext editingContext, EOQualifier qualifier) {
    ExamenEntete eoObject = _ExamenEntete.fetchExamenEntete(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ExamenEntete that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ExamenEntete localInstanceIn(EOEditingContext editingContext, ExamenEntete eo) {
    ExamenEntete localInstance = (eo == null) ? null : (ExamenEntete)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
