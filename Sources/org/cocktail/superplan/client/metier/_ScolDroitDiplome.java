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

// DO NOT EDIT.  Make changes to ScolDroitDiplome.java instead.
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

public abstract class _ScolDroitDiplome extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolDroitDiplome";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_DROIT_DIPLOME";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "ddipKey";

	public static final String DDIP_BILAN_KEY = "ddipBilan";
	public static final String DDIP_EDT_KEY = "ddipEdt";
	public static final String DDIP_EXAMENS_KEY = "ddipExamens";
	public static final String DDIP_GROUPES_KEY = "ddipGroupes";
	public static final String DDIP_IPEDAGOGIQUES_KEY = "ddipIpedagogiques";
	public static final String DDIP_MAQUETTES_KEY = "ddipMaquettes";
	public static final String DDIP_STATISTIQUES_KEY = "ddipStatistiques";
	public static final String DLOG_KEY_KEY = "dlogKey";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String FHAB_KEY_KEY = "fhabKey";

	public static final String DDIP_BILAN_COLKEY = "DDIP_BILAN";
	public static final String DDIP_EDT_COLKEY = "DDIP_EDT";
	public static final String DDIP_EXAMENS_COLKEY = "DDIP_EXAMENS";
	public static final String DDIP_GROUPES_COLKEY = "DDIP_GROUPES";
	public static final String DDIP_IPEDAGOGIQUES_COLKEY = "DDIP_IPEDAGOGIQUES";
	public static final String DDIP_MAQUETTES_COLKEY = "DDIP_MAQUETTES";
	public static final String DDIP_STATISTIQUES_COLKEY = "DDIP_STATISTIQUES";
	public static final String DLOG_KEY_COLKEY = "DLOG_KEY";
	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String FHAB_KEY_COLKEY = "FHAB_KEY";

	// Relationships
	public static final String HABILITATION_KEY = "habilitation";

	// Utilities methods
	  public ScolDroitDiplome localInstanceIn(EOEditingContext editingContext) {
	    ScolDroitDiplome localInstance = (ScolDroitDiplome)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolDroitDiplome getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolDroitDiplome.ENTITY_NAME);
		      return (ScolDroitDiplome) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String ddipBilan() {
    return (String) storedValueForKey("ddipBilan");
  }

  public void setDdipBilan(String value) {
    takeStoredValueForKey(value, "ddipBilan");
  }

  public String ddipEdt() {
    return (String) storedValueForKey("ddipEdt");
  }

  public void setDdipEdt(String value) {
    takeStoredValueForKey(value, "ddipEdt");
  }

  public String ddipExamens() {
    return (String) storedValueForKey("ddipExamens");
  }

  public void setDdipExamens(String value) {
    takeStoredValueForKey(value, "ddipExamens");
  }

  public String ddipGroupes() {
    return (String) storedValueForKey("ddipGroupes");
  }

  public void setDdipGroupes(String value) {
    takeStoredValueForKey(value, "ddipGroupes");
  }

  public String ddipIpedagogiques() {
    return (String) storedValueForKey("ddipIpedagogiques");
  }

  public void setDdipIpedagogiques(String value) {
    takeStoredValueForKey(value, "ddipIpedagogiques");
  }

  public String ddipMaquettes() {
    return (String) storedValueForKey("ddipMaquettes");
  }

  public void setDdipMaquettes(String value) {
    takeStoredValueForKey(value, "ddipMaquettes");
  }

  public String ddipStatistiques() {
    return (String) storedValueForKey("ddipStatistiques");
  }

  public void setDdipStatistiques(String value) {
    takeStoredValueForKey(value, "ddipStatistiques");
  }

  public Integer dlogKey() {
    return (Integer) storedValueForKey("dlogKey");
  }

  public void setDlogKey(Integer value) {
    takeStoredValueForKey(value, "dlogKey");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer fhabKey() {
    return (Integer) storedValueForKey("fhabKey");
  }

  public void setFhabKey(Integer value) {
    takeStoredValueForKey(value, "fhabKey");
  }

  public org.cocktail.superplan.client.metier.FormationHabilitation habilitation() {
    return (org.cocktail.superplan.client.metier.FormationHabilitation)storedValueForKey("habilitation");
  }

  public void setHabilitationRelationship(org.cocktail.superplan.client.metier.FormationHabilitation value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.FormationHabilitation oldValue = habilitation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "habilitation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "habilitation");
    }
  }
  

  public static ScolDroitDiplome createScolDroitDiplome(EOEditingContext editingContext, String ddipBilan
, String ddipEdt
, String ddipExamens
, String ddipGroupes
, String ddipIpedagogiques
, String ddipMaquettes
, String ddipStatistiques
, Integer dlogKey
, Integer fannKey
, Integer fhabKey
) {
    ScolDroitDiplome eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolDroitDiplome.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolDroitDiplome.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolDroitDiplome) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDdipBilan(ddipBilan);
		eo.setDdipEdt(ddipEdt);
		eo.setDdipExamens(ddipExamens);
		eo.setDdipGroupes(ddipGroupes);
		eo.setDdipIpedagogiques(ddipIpedagogiques);
		eo.setDdipMaquettes(ddipMaquettes);
		eo.setDdipStatistiques(ddipStatistiques);
		eo.setDlogKey(dlogKey);
		eo.setFannKey(fannKey);
		eo.setFhabKey(fhabKey);
    return eo;
  }

  public static NSArray fetchAllScolDroitDiplomes(EOEditingContext editingContext) {
    return _ScolDroitDiplome.fetchAllScolDroitDiplomes(editingContext, null);
  }

  public static NSArray fetchAllScolDroitDiplomes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolDroitDiplome.fetchScolDroitDiplomes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolDroitDiplomes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolDroitDiplome.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolDroitDiplome fetchScolDroitDiplome(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolDroitDiplome.fetchScolDroitDiplome(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolDroitDiplome fetchScolDroitDiplome(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolDroitDiplome.fetchScolDroitDiplomes(editingContext, qualifier, null);
    ScolDroitDiplome eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolDroitDiplome)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolDroitDiplome that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolDroitDiplome fetchRequiredScolDroitDiplome(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolDroitDiplome.fetchRequiredScolDroitDiplome(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolDroitDiplome fetchRequiredScolDroitDiplome(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolDroitDiplome eoObject = _ScolDroitDiplome.fetchScolDroitDiplome(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolDroitDiplome that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolDroitDiplome localInstanceIn(EOEditingContext editingContext, ScolDroitDiplome eo) {
    ScolDroitDiplome localInstance = (eo == null) ? null : (ScolDroitDiplome)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
