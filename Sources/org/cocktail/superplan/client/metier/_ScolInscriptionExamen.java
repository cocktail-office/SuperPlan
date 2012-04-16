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

// DO NOT EDIT.  Make changes to ScolInscriptionExamen.java instead.
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

public abstract class _ScolInscriptionExamen extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolInscriptionExamen";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_INSCRIPTION_EXAMEN";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "ieentKey";

	public static final String EENT_KEY_KEY = "eentKey";
	public static final String IDIPL_NUMERO_KEY = "idiplNumero";
	public static final String IEENT_ABSENCE_KEY = "ieentAbsence";
	public static final String IEENT_ANONYMAT_KEY = "ieentAnonymat";
	public static final String IEENT_CODE_KEY = "ieentCode";
	public static final String IEENT_KEY_KEY = "ieentKey";
	public static final String IEENT_NOM_KEY = "ieentNom";
	public static final String IEENT_NOTE_KEY = "ieentNote";
	public static final String IEENT_NOTE_DEFINITIVE_KEY = "ieentNoteDefinitive";
	public static final String IEENT_PLACE_KEY = "ieentPlace";
	public static final String IEENT_PRENOM_KEY = "ieentPrenom";
	public static final String IEENT_RANG_KEY = "ieentRang";
	public static final String IEENT_VALIDITE_KEY = "ieentValidite";
	public static final String SAL_NUMERO_KEY = "salNumero";

	public static final String EENT_KEY_COLKEY = "EENT_KEY";
	public static final String IDIPL_NUMERO_COLKEY = "IDIPL_NUMERO";
	public static final String IEENT_ABSENCE_COLKEY = "IEENT_ABSENCE";
	public static final String IEENT_ANONYMAT_COLKEY = "IEENT_ANONYMAT";
	public static final String IEENT_CODE_COLKEY = "IEENT_CODE";
	public static final String IEENT_KEY_COLKEY = "IEENT_KEY";
	public static final String IEENT_NOM_COLKEY = "IEENT_NOM";
	public static final String IEENT_NOTE_COLKEY = "IEENT_NOTE";
	public static final String IEENT_NOTE_DEFINITIVE_COLKEY = "IEENT_NOTE_DEFINITIVE";
	public static final String IEENT_PLACE_COLKEY = "IEENT_PLACE";
	public static final String IEENT_PRENOM_COLKEY = "IEENT_PRENOM";
	public static final String IEENT_RANG_COLKEY = "IEENT_RANG";
	public static final String IEENT_VALIDITE_COLKEY = "IEENT_VALIDITE";
	public static final String SAL_NUMERO_COLKEY = "SAL_NUMERO";

	// Relationships

	// Utilities methods
	  public ScolInscriptionExamen localInstanceIn(EOEditingContext editingContext) {
	    ScolInscriptionExamen localInstance = (ScolInscriptionExamen)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolInscriptionExamen getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolInscriptionExamen.ENTITY_NAME);
		      return (ScolInscriptionExamen) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer eentKey() {
    return (Integer) storedValueForKey("eentKey");
  }

  public void setEentKey(Integer value) {
    takeStoredValueForKey(value, "eentKey");
  }

  public Integer idiplNumero() {
    return (Integer) storedValueForKey("idiplNumero");
  }

  public void setIdiplNumero(Integer value) {
    takeStoredValueForKey(value, "idiplNumero");
  }

  public Integer ieentAbsence() {
    return (Integer) storedValueForKey("ieentAbsence");
  }

  public void setIeentAbsence(Integer value) {
    takeStoredValueForKey(value, "ieentAbsence");
  }

  public String ieentAnonymat() {
    return (String) storedValueForKey("ieentAnonymat");
  }

  public void setIeentAnonymat(String value) {
    takeStoredValueForKey(value, "ieentAnonymat");
  }

  public String ieentCode() {
    return (String) storedValueForKey("ieentCode");
  }

  public void setIeentCode(String value) {
    takeStoredValueForKey(value, "ieentCode");
  }

  public Integer ieentKey() {
    return (Integer) storedValueForKey("ieentKey");
  }

  public void setIeentKey(Integer value) {
    takeStoredValueForKey(value, "ieentKey");
  }

  public String ieentNom() {
    return (String) storedValueForKey("ieentNom");
  }

  public void setIeentNom(String value) {
    takeStoredValueForKey(value, "ieentNom");
  }

  public java.math.BigDecimal ieentNote() {
    return (java.math.BigDecimal) storedValueForKey("ieentNote");
  }

  public void setIeentNote(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "ieentNote");
  }

  public java.math.BigDecimal ieentNoteDefinitive() {
    return (java.math.BigDecimal) storedValueForKey("ieentNoteDefinitive");
  }

  public void setIeentNoteDefinitive(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "ieentNoteDefinitive");
  }

  public String ieentPlace() {
    return (String) storedValueForKey("ieentPlace");
  }

  public void setIeentPlace(String value) {
    takeStoredValueForKey(value, "ieentPlace");
  }

  public String ieentPrenom() {
    return (String) storedValueForKey("ieentPrenom");
  }

  public void setIeentPrenom(String value) {
    takeStoredValueForKey(value, "ieentPrenom");
  }

  public Integer ieentRang() {
    return (Integer) storedValueForKey("ieentRang");
  }

  public void setIeentRang(Integer value) {
    takeStoredValueForKey(value, "ieentRang");
  }

  public String ieentValidite() {
    return (String) storedValueForKey("ieentValidite");
  }

  public void setIeentValidite(String value) {
    takeStoredValueForKey(value, "ieentValidite");
  }

  public Integer salNumero() {
    return (Integer) storedValueForKey("salNumero");
  }

  public void setSalNumero(Integer value) {
    takeStoredValueForKey(value, "salNumero");
  }


  public static ScolInscriptionExamen createScolInscriptionExamen(EOEditingContext editingContext, Integer eentKey
, Integer idiplNumero
, Integer ieentAbsence
, Integer ieentKey
, String ieentValidite
) {
    ScolInscriptionExamen eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolInscriptionExamen.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolInscriptionExamen.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolInscriptionExamen) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setEentKey(eentKey);
		eo.setIdiplNumero(idiplNumero);
		eo.setIeentAbsence(ieentAbsence);
		eo.setIeentKey(ieentKey);
		eo.setIeentValidite(ieentValidite);
    return eo;
  }

  public static NSArray fetchAllScolInscriptionExamens(EOEditingContext editingContext) {
    return _ScolInscriptionExamen.fetchAllScolInscriptionExamens(editingContext, null);
  }

  public static NSArray fetchAllScolInscriptionExamens(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolInscriptionExamen.fetchScolInscriptionExamens(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolInscriptionExamens(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolInscriptionExamen.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolInscriptionExamen fetchScolInscriptionExamen(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolInscriptionExamen.fetchScolInscriptionExamen(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolInscriptionExamen fetchScolInscriptionExamen(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolInscriptionExamen.fetchScolInscriptionExamens(editingContext, qualifier, null);
    ScolInscriptionExamen eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolInscriptionExamen)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolInscriptionExamen that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolInscriptionExamen fetchRequiredScolInscriptionExamen(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolInscriptionExamen.fetchRequiredScolInscriptionExamen(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolInscriptionExamen fetchRequiredScolInscriptionExamen(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolInscriptionExamen eoObject = _ScolInscriptionExamen.fetchScolInscriptionExamen(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolInscriptionExamen that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolInscriptionExamen localInstanceIn(EOEditingContext editingContext, ScolInscriptionExamen eo) {
    ScolInscriptionExamen localInstance = (eo == null) ? null : (ScolInscriptionExamen)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
