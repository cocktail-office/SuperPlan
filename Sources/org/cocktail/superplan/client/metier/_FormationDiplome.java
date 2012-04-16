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

// DO NOT EDIT.  Make changes to FormationDiplome.java instead.
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

public abstract class _FormationDiplome extends  EOGenericRecord {
	public static final String ENTITY_NAME = "FormationDiplome";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_FORMATION_DIPLOME";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "fdipCode";

	public static final String COMP_CODE_KEY = "compCode";
	public static final String ETAB_CODE_KEY = "etabCode";
	public static final String FDIP_ABREVIATION_KEY = "fdipAbreviation";
	public static final String FDIP_ARRIVEE_KEY = "fdipArrivee";
	public static final String FDIP_CYCLE_KEY = "fdipCycle";
	public static final String FDIP_DEPART_KEY = "fdipDepart";
	public static final String FDIP_LIBELLE_KEY = "fdipLibelle";
	public static final String FDIP_MENTION_KEY = "fdipMention";
	public static final String FDIP_MODELE_KEY = "fdipModele";
	public static final String FDIP_SPECIALITE_KEY = "fdipSpecialite";
	public static final String FDIP_TYPE_DROIT_KEY = "fdipTypeDroit";
	public static final String FDOM_CODE_KEY = "fdomCode";
	public static final String FGRA_CODE_KEY = "fgraCode";
	public static final String FVOC_CODE_KEY = "fvocCode";
	public static final String SREMO_CODE_KEY = "sremoCode";

	public static final String COMP_CODE_COLKEY = "COMP_CODE";
	public static final String ETAB_CODE_COLKEY = "ETAB_CODE";
	public static final String FDIP_ABREVIATION_COLKEY = "FDIP_ABREVIATION";
	public static final String FDIP_ARRIVEE_COLKEY = "FDIP_ARRIVEE";
	public static final String FDIP_CYCLE_COLKEY = "FDIP_CYCLE";
	public static final String FDIP_DEPART_COLKEY = "FDIP_DEPART";
	public static final String FDIP_LIBELLE_COLKEY = "FDIP_LIBELLE";
	public static final String FDIP_MENTION_COLKEY = "FDIP_MENTION";
	public static final String FDIP_MODELE_COLKEY = "FDIP_MODELE";
	public static final String FDIP_SPECIALITE_COLKEY = "FDIP_SPECIALITE";
	public static final String FDIP_TYPE_DROIT_COLKEY = "FDIP_TYPE_DROIT";
	public static final String FDOM_CODE_COLKEY = "FDOM_CODE";
	public static final String FGRA_CODE_COLKEY = "FGRA_CODE";
	public static final String FVOC_CODE_COLKEY = "FVOC_CODE";
	public static final String SREMO_CODE_COLKEY = "SREMO_CODE";

	// Relationships

	// Utilities methods
	  public FormationDiplome localInstanceIn(EOEditingContext editingContext) {
	    FormationDiplome localInstance = (FormationDiplome)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static FormationDiplome getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_FormationDiplome.ENTITY_NAME);
		      return (FormationDiplome) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String compCode() {
    return (String) storedValueForKey("compCode");
  }

  public void setCompCode(String value) {
    takeStoredValueForKey(value, "compCode");
  }

  public String etabCode() {
    return (String) storedValueForKey("etabCode");
  }

  public void setEtabCode(String value) {
    takeStoredValueForKey(value, "etabCode");
  }

  public String fdipAbreviation() {
    return (String) storedValueForKey("fdipAbreviation");
  }

  public void setFdipAbreviation(String value) {
    takeStoredValueForKey(value, "fdipAbreviation");
  }

  public Integer fdipArrivee() {
    return (Integer) storedValueForKey("fdipArrivee");
  }

  public void setFdipArrivee(Integer value) {
    takeStoredValueForKey(value, "fdipArrivee");
  }

  public Integer fdipCycle() {
    return (Integer) storedValueForKey("fdipCycle");
  }

  public void setFdipCycle(Integer value) {
    takeStoredValueForKey(value, "fdipCycle");
  }

  public Integer fdipDepart() {
    return (Integer) storedValueForKey("fdipDepart");
  }

  public void setFdipDepart(Integer value) {
    takeStoredValueForKey(value, "fdipDepart");
  }

  public String fdipLibelle() {
    return (String) storedValueForKey("fdipLibelle");
  }

  public void setFdipLibelle(String value) {
    takeStoredValueForKey(value, "fdipLibelle");
  }

  public String fdipMention() {
    return (String) storedValueForKey("fdipMention");
  }

  public void setFdipMention(String value) {
    takeStoredValueForKey(value, "fdipMention");
  }

  public String fdipModele() {
    return (String) storedValueForKey("fdipModele");
  }

  public void setFdipModele(String value) {
    takeStoredValueForKey(value, "fdipModele");
  }

  public String fdipSpecialite() {
    return (String) storedValueForKey("fdipSpecialite");
  }

  public void setFdipSpecialite(String value) {
    takeStoredValueForKey(value, "fdipSpecialite");
  }

  public String fdipTypeDroit() {
    return (String) storedValueForKey("fdipTypeDroit");
  }

  public void setFdipTypeDroit(String value) {
    takeStoredValueForKey(value, "fdipTypeDroit");
  }

  public String fdomCode() {
    return (String) storedValueForKey("fdomCode");
  }

  public void setFdomCode(String value) {
    takeStoredValueForKey(value, "fdomCode");
  }

  public String fgraCode() {
    return (String) storedValueForKey("fgraCode");
  }

  public void setFgraCode(String value) {
    takeStoredValueForKey(value, "fgraCode");
  }

  public String fvocCode() {
    return (String) storedValueForKey("fvocCode");
  }

  public void setFvocCode(String value) {
    takeStoredValueForKey(value, "fvocCode");
  }

  public String sremoCode() {
    return (String) storedValueForKey("sremoCode");
  }

  public void setSremoCode(String value) {
    takeStoredValueForKey(value, "sremoCode");
  }


  public static FormationDiplome createFormationDiplome(EOEditingContext editingContext, String compCode
, String etabCode
, Integer fdipArrivee
, Integer fdipCycle
, Integer fdipDepart
, String fdipLibelle
, String fdipMention
, String fdipTypeDroit
, String fdomCode
, String fgraCode
, String fvocCode
) {
    FormationDiplome eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_FormationDiplome.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _FormationDiplome.ENTITY_NAME + "' !");
    } else
    {
        eo = (FormationDiplome) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCompCode(compCode);
		eo.setEtabCode(etabCode);
		eo.setFdipArrivee(fdipArrivee);
		eo.setFdipCycle(fdipCycle);
		eo.setFdipDepart(fdipDepart);
		eo.setFdipLibelle(fdipLibelle);
		eo.setFdipMention(fdipMention);
		eo.setFdipTypeDroit(fdipTypeDroit);
		eo.setFdomCode(fdomCode);
		eo.setFgraCode(fgraCode);
		eo.setFvocCode(fvocCode);
    return eo;
  }

  public static NSArray fetchAllFormationDiplomes(EOEditingContext editingContext) {
    return _FormationDiplome.fetchAllFormationDiplomes(editingContext, null);
  }

  public static NSArray fetchAllFormationDiplomes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _FormationDiplome.fetchFormationDiplomes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchFormationDiplomes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_FormationDiplome.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static FormationDiplome fetchFormationDiplome(EOEditingContext editingContext, String keyName, Object value) {
    return _FormationDiplome.fetchFormationDiplome(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FormationDiplome fetchFormationDiplome(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _FormationDiplome.fetchFormationDiplomes(editingContext, qualifier, null);
    FormationDiplome eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (FormationDiplome)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one FormationDiplome that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FormationDiplome fetchRequiredFormationDiplome(EOEditingContext editingContext, String keyName, Object value) {
    return _FormationDiplome.fetchRequiredFormationDiplome(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static FormationDiplome fetchRequiredFormationDiplome(EOEditingContext editingContext, EOQualifier qualifier) {
    FormationDiplome eoObject = _FormationDiplome.fetchFormationDiplome(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no FormationDiplome that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static FormationDiplome localInstanceIn(EOEditingContext editingContext, FormationDiplome eo) {
    FormationDiplome localInstance = (eo == null) ? null : (FormationDiplome)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
