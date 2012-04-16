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

// DO NOT EDIT.  Make changes to MaquetteSemestre.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _MaquetteSemestre extends  EOGenericRecord {
	public static final String ENTITY_NAME = "MaquetteSemestre";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_SEMESTRE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "msemKey";

	public static final String MSEM_BONIFIABLE_KEY = "msemBonifiable";
	public static final String MSEM_DATE_DEBUT_KEY = "msemDateDebut";
	public static final String MSEM_DATE_FIN_KEY = "msemDateFin";
	public static final String MSEM_HORAIRE_ETU_KEY = "msemHoraireEtu";
	public static final String MSEM_KEY_KEY = "msemKey";
	public static final String MSEM_NOTE_BASE_KEY = "msemNoteBase";
	public static final String MSEM_NOTE_ELIMINATION_KEY = "msemNoteElimination";
	public static final String MSEM_NOTE_OBTENTION_KEY = "msemNoteObtention";
	public static final String MSEM_ORDRE_KEY = "msemOrdre";
	public static final String MSEM_SEMAINE_SESSION1_KEY = "msemSemaineSession1";
	public static final String MSEM_SEMAINE_SESSION2_KEY = "msemSemaineSession2";

	public static final String MSEM_BONIFIABLE_COLKEY = "MSEM_BONIFIABLE";
	public static final String MSEM_DATE_DEBUT_COLKEY = "MSEM_DATE_DEBUT";
	public static final String MSEM_DATE_FIN_COLKEY = "MSEM_DATE_FIN";
	public static final String MSEM_HORAIRE_ETU_COLKEY = "MSEM_HORAIRE_ETU";
	public static final String MSEM_KEY_COLKEY = "MSEM_KEY";
	public static final String MSEM_NOTE_BASE_COLKEY = "MSEM_NOTE_BASE";
	public static final String MSEM_NOTE_ELIMINATION_COLKEY = "MSEM_NOTE_ELIMINATION";
	public static final String MSEM_NOTE_OBTENTION_COLKEY = "MSEM_NOTE_OBTENTION";
	public static final String MSEM_ORDRE_COLKEY = "MSEM_ORDRE";
	public static final String MSEM_SEMAINE_SESSION1_COLKEY = "MSEM_SEMAINE_SESSION1";
	public static final String MSEM_SEMAINE_SESSION2_COLKEY = "MSEM_SEMAINE_SESSION2";

	// Relationships
	public static final String MAQUETTE_REPARTITION_SEMS_KEY = "maquetteRepartitionSems";
	public static final String REPARTITION_UES_KEY = "repartitionUes";
	public static final String SCOL_FORMATION_ANNEE_KEY = "scolFormationAnnee";

	// Utilities methods
	  public MaquetteSemestre localInstanceIn(EOEditingContext editingContext) {
	    MaquetteSemestre localInstance = (MaquetteSemestre)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static MaquetteSemestre getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_MaquetteSemestre.ENTITY_NAME);
		      return (MaquetteSemestre) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer msemBonifiable() {
    return (Integer) storedValueForKey("msemBonifiable");
  }

  public void setMsemBonifiable(Integer value) {
    takeStoredValueForKey(value, "msemBonifiable");
  }

  public NSTimestamp msemDateDebut() {
    return (NSTimestamp) storedValueForKey("msemDateDebut");
  }

  public void setMsemDateDebut(NSTimestamp value) {
    takeStoredValueForKey(value, "msemDateDebut");
  }

  public NSTimestamp msemDateFin() {
    return (NSTimestamp) storedValueForKey("msemDateFin");
  }

  public void setMsemDateFin(NSTimestamp value) {
    takeStoredValueForKey(value, "msemDateFin");
  }

  public java.math.BigDecimal msemHoraireEtu() {
    return (java.math.BigDecimal) storedValueForKey("msemHoraireEtu");
  }

  public void setMsemHoraireEtu(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "msemHoraireEtu");
  }

  public Integer msemKey() {
    return (Integer) storedValueForKey("msemKey");
  }

  public void setMsemKey(Integer value) {
    takeStoredValueForKey(value, "msemKey");
  }

  public java.math.BigDecimal msemNoteBase() {
    return (java.math.BigDecimal) storedValueForKey("msemNoteBase");
  }

  public void setMsemNoteBase(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "msemNoteBase");
  }

  public java.math.BigDecimal msemNoteElimination() {
    return (java.math.BigDecimal) storedValueForKey("msemNoteElimination");
  }

  public void setMsemNoteElimination(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "msemNoteElimination");
  }

  public java.math.BigDecimal msemNoteObtention() {
    return (java.math.BigDecimal) storedValueForKey("msemNoteObtention");
  }

  public void setMsemNoteObtention(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "msemNoteObtention");
  }

  public Integer msemOrdre() {
    return (Integer) storedValueForKey("msemOrdre");
  }

  public void setMsemOrdre(Integer value) {
    takeStoredValueForKey(value, "msemOrdre");
  }

  public Integer msemSemaineSession1() {
    return (Integer) storedValueForKey("msemSemaineSession1");
  }

  public void setMsemSemaineSession1(Integer value) {
    takeStoredValueForKey(value, "msemSemaineSession1");
  }

  public Integer msemSemaineSession2() {
    return (Integer) storedValueForKey("msemSemaineSession2");
  }

  public void setMsemSemaineSession2(Integer value) {
    takeStoredValueForKey(value, "msemSemaineSession2");
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
  
  public NSArray maquetteRepartitionSems() {
    return (NSArray)storedValueForKey("maquetteRepartitionSems");
  }

  public NSArray maquetteRepartitionSems(EOQualifier qualifier) {
    return maquetteRepartitionSems(qualifier, null, false);
  }

  public NSArray maquetteRepartitionSems(EOQualifier qualifier, boolean fetch) {
    return maquetteRepartitionSems(qualifier, null, fetch);
  }

  public NSArray maquetteRepartitionSems(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.MaquetteRepartitionSem.SEMESTRE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.MaquetteRepartitionSem.fetchMaquetteRepartitionSems(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = maquetteRepartitionSems();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToMaquetteRepartitionSemsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionSem object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "maquetteRepartitionSems");
  }

  public void removeFromMaquetteRepartitionSemsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionSem object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionSems");
  }

  public org.cocktail.superplan.client.metier.MaquetteRepartitionSem createMaquetteRepartitionSemsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteRepartitionSem");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "maquetteRepartitionSems");
    return (org.cocktail.superplan.client.metier.MaquetteRepartitionSem) eo;
  }

  public void deleteMaquetteRepartitionSemsRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionSem object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "maquetteRepartitionSems");
    editingContext().deleteObject(object);
  }

  public void deleteAllMaquetteRepartitionSemsRelationships() {
    Enumeration objects = maquetteRepartitionSems().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteMaquetteRepartitionSemsRelationship((org.cocktail.superplan.client.metier.MaquetteRepartitionSem)objects.nextElement());
    }
  }

  public NSArray repartitionUes() {
    return (NSArray)storedValueForKey("repartitionUes");
  }

  public NSArray repartitionUes(EOQualifier qualifier) {
    return repartitionUes(qualifier, null, false);
  }

  public NSArray repartitionUes(EOQualifier qualifier, boolean fetch) {
    return repartitionUes(qualifier, null, fetch);
  }

  public NSArray repartitionUes(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.MaquetteRepartitionUe.MAQUETTE_SEMESTRE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.MaquetteRepartitionUe.fetchMaquetteRepartitionUes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = repartitionUes();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRepartitionUesRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionUe object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartitionUes");
  }

  public void removeFromRepartitionUesRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionUe object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartitionUes");
  }

  public org.cocktail.superplan.client.metier.MaquetteRepartitionUe createRepartitionUesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteRepartitionUe");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartitionUes");
    return (org.cocktail.superplan.client.metier.MaquetteRepartitionUe) eo;
  }

  public void deleteRepartitionUesRelationship(org.cocktail.superplan.client.metier.MaquetteRepartitionUe object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartitionUes");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartitionUesRelationships() {
    Enumeration objects = repartitionUes().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartitionUesRelationship((org.cocktail.superplan.client.metier.MaquetteRepartitionUe)objects.nextElement());
    }
  }


  public static MaquetteSemestre createMaquetteSemestre(EOEditingContext editingContext, Integer msemBonifiable
, java.math.BigDecimal msemHoraireEtu
, Integer msemKey
, java.math.BigDecimal msemNoteBase
, java.math.BigDecimal msemNoteObtention
, Integer msemOrdre
) {
    MaquetteSemestre eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_MaquetteSemestre.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _MaquetteSemestre.ENTITY_NAME + "' !");
    } else
    {
        eo = (MaquetteSemestre) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setMsemBonifiable(msemBonifiable);
		eo.setMsemHoraireEtu(msemHoraireEtu);
		eo.setMsemKey(msemKey);
		eo.setMsemNoteBase(msemNoteBase);
		eo.setMsemNoteObtention(msemNoteObtention);
		eo.setMsemOrdre(msemOrdre);
    return eo;
  }

  public static NSArray fetchAllMaquetteSemestres(EOEditingContext editingContext) {
    return _MaquetteSemestre.fetchAllMaquetteSemestres(editingContext, null);
  }

  public static NSArray fetchAllMaquetteSemestres(EOEditingContext editingContext, NSArray sortOrderings) {
    return _MaquetteSemestre.fetchMaquetteSemestres(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMaquetteSemestres(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_MaquetteSemestre.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static MaquetteSemestre fetchMaquetteSemestre(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteSemestre.fetchMaquetteSemestre(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteSemestre fetchMaquetteSemestre(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _MaquetteSemestre.fetchMaquetteSemestres(editingContext, qualifier, null);
    MaquetteSemestre eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (MaquetteSemestre)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one MaquetteSemestre that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteSemestre fetchRequiredMaquetteSemestre(EOEditingContext editingContext, String keyName, Object value) {
    return _MaquetteSemestre.fetchRequiredMaquetteSemestre(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static MaquetteSemestre fetchRequiredMaquetteSemestre(EOEditingContext editingContext, EOQualifier qualifier) {
    MaquetteSemestre eoObject = _MaquetteSemestre.fetchMaquetteSemestre(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no MaquetteSemestre that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static MaquetteSemestre localInstanceIn(EOEditingContext editingContext, MaquetteSemestre eo) {
    MaquetteSemestre localInstance = (eo == null) ? null : (MaquetteSemestre)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
