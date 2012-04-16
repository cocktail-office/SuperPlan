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

// DO NOT EDIT.  Make changes to Affectation.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _Affectation extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Affectation";
	public static final String ENTITY_TABLE_NAME = "MANGUE.AFFECTATION";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "noSeqAffectation";

	public static final String C_STRUCTURE_KEY = "cStructure";
	public static final String D_DEB_AFFECTATION_KEY = "dDebAffectation";
	public static final String D_FIN_AFFECTATION_KEY = "dFinAffectation";
	public static final String NO_DOSSIER_PERS_KEY = "noDossierPers";
	public static final String TEM_VALIDE_KEY = "temValide";

	public static final String C_STRUCTURE_COLKEY = "C_STRUCTURE";
	public static final String D_DEB_AFFECTATION_COLKEY = "D_DEB_AFFECTATION";
	public static final String D_FIN_AFFECTATION_COLKEY = "D_FIN_AFFECTATION";
	public static final String NO_DOSSIER_PERS_COLKEY = "NO_DOSSIER_PERS";
	public static final String TEM_VALIDE_COLKEY = "TEM_VALIDE";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String STRUCTURE_ULR_KEY = "structureUlr";

	// Utilities methods
	  public Affectation localInstanceIn(EOEditingContext editingContext) {
	    Affectation localInstance = (Affectation)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Affectation getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Affectation.ENTITY_NAME);
		      return (Affectation) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cStructure() {
    return (String) storedValueForKey("cStructure");
  }

  public void setCStructure(String value) {
    takeStoredValueForKey(value, "cStructure");
  }

  public NSTimestamp dDebAffectation() {
    return (NSTimestamp) storedValueForKey("dDebAffectation");
  }

  public void setDDebAffectation(NSTimestamp value) {
    takeStoredValueForKey(value, "dDebAffectation");
  }

  public NSTimestamp dFinAffectation() {
    return (NSTimestamp) storedValueForKey("dFinAffectation");
  }

  public void setDFinAffectation(NSTimestamp value) {
    takeStoredValueForKey(value, "dFinAffectation");
  }

  public Integer noDossierPers() {
    return (Integer) storedValueForKey("noDossierPers");
  }

  public void setNoDossierPers(Integer value) {
    takeStoredValueForKey(value, "noDossierPers");
  }

  public String temValide() {
    return (String) storedValueForKey("temValide");
  }

  public void setTemValide(String value) {
    takeStoredValueForKey(value, "temValide");
  }

  public org.cocktail.superplan.client.metier.IndividuUlr individuUlr() {
    return (org.cocktail.superplan.client.metier.IndividuUlr)storedValueForKey("individuUlr");
  }

  public void setIndividuUlrRelationship(org.cocktail.superplan.client.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.IndividuUlr oldValue = individuUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuUlr");
    }
  }
  
  public org.cocktail.superplan.client.metier.StructureUlr structureUlr() {
    return (org.cocktail.superplan.client.metier.StructureUlr)storedValueForKey("structureUlr");
  }

  public void setStructureUlrRelationship(org.cocktail.superplan.client.metier.StructureUlr value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.StructureUlr oldValue = structureUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "structureUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "structureUlr");
    }
  }
  

  public static Affectation createAffectation(EOEditingContext editingContext, String cStructure
, NSTimestamp dDebAffectation
, Integer noDossierPers
, String temValide
, org.cocktail.superplan.client.metier.IndividuUlr individuUlr, org.cocktail.superplan.client.metier.StructureUlr structureUlr) {
    Affectation eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Affectation.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Affectation.ENTITY_NAME + "' !");
    } else
    {
        eo = (Affectation) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCStructure(cStructure);
		eo.setDDebAffectation(dDebAffectation);
		eo.setNoDossierPers(noDossierPers);
		eo.setTemValide(temValide);
    eo.setIndividuUlrRelationship(individuUlr);
    eo.setStructureUlrRelationship(structureUlr);
    return eo;
  }

  public static NSArray fetchAllAffectations(EOEditingContext editingContext) {
    return _Affectation.fetchAllAffectations(editingContext, null);
  }

  public static NSArray fetchAllAffectations(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Affectation.fetchAffectations(editingContext, null, sortOrderings);
  }

  public static NSArray fetchAffectations(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Affectation.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Affectation fetchAffectation(EOEditingContext editingContext, String keyName, Object value) {
    return _Affectation.fetchAffectation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Affectation fetchAffectation(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Affectation.fetchAffectations(editingContext, qualifier, null);
    Affectation eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Affectation)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Affectation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Affectation fetchRequiredAffectation(EOEditingContext editingContext, String keyName, Object value) {
    return _Affectation.fetchRequiredAffectation(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Affectation fetchRequiredAffectation(EOEditingContext editingContext, EOQualifier qualifier) {
    Affectation eoObject = _Affectation.fetchAffectation(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Affectation that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Affectation localInstanceIn(EOEditingContext editingContext, Affectation eo) {
    Affectation localInstance = (eo == null) ? null : (Affectation)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
