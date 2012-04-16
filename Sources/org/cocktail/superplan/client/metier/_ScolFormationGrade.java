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

// DO NOT EDIT.  Make changes to ScolFormationGrade.java instead.
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

public abstract class _ScolFormationGrade extends  EOGenericRecord {
	public static final String ENTITY_NAME = "FormationGrade";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_FORMATION_GRADE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "fgraCode";

	public static final String FGRA_ABREVIATION_KEY = "fgraAbreviation";
	public static final String FGRA_CODAGE_KEY = "fgraCodage";
	public static final String FGRA_LIBELLE_KEY = "fgraLibelle";
	public static final String FGRA_VALIDITE_KEY = "fgraValidite";

	public static final String FGRA_ABREVIATION_COLKEY = "FGRA_ABREVIATION";
	public static final String FGRA_CODAGE_COLKEY = "FGRA_CODAGE";
	public static final String FGRA_LIBELLE_COLKEY = "FGRA_LIBELLE";
	public static final String FGRA_VALIDITE_COLKEY = "FGRA_VALIDITE";

	// Relationships

	// Utilities methods
	  public ScolFormationGrade localInstanceIn(EOEditingContext editingContext) {
	    ScolFormationGrade localInstance = (ScolFormationGrade)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolFormationGrade getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolFormationGrade.ENTITY_NAME);
		      return (ScolFormationGrade) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String fgraAbreviation() {
    return (String) storedValueForKey("fgraAbreviation");
  }

  public void setFgraAbreviation(String value) {
    takeStoredValueForKey(value, "fgraAbreviation");
  }

  public String fgraCodage() {
    return (String) storedValueForKey("fgraCodage");
  }

  public void setFgraCodage(String value) {
    takeStoredValueForKey(value, "fgraCodage");
  }

  public String fgraLibelle() {
    return (String) storedValueForKey("fgraLibelle");
  }

  public void setFgraLibelle(String value) {
    takeStoredValueForKey(value, "fgraLibelle");
  }

  public String fgraValidite() {
    return (String) storedValueForKey("fgraValidite");
  }

  public void setFgraValidite(String value) {
    takeStoredValueForKey(value, "fgraValidite");
  }


  public static ScolFormationGrade createFormationGrade(EOEditingContext editingContext, String fgraLibelle
, String fgraValidite
) {
    ScolFormationGrade eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolFormationGrade.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolFormationGrade.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolFormationGrade) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFgraLibelle(fgraLibelle);
		eo.setFgraValidite(fgraValidite);
    return eo;
  }

  public static NSArray fetchAllFormationGrades(EOEditingContext editingContext) {
    return _ScolFormationGrade.fetchAllFormationGrades(editingContext, null);
  }

  public static NSArray fetchAllFormationGrades(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolFormationGrade.fetchFormationGrades(editingContext, null, sortOrderings);
  }

  public static NSArray fetchFormationGrades(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolFormationGrade.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolFormationGrade fetchFormationGrade(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolFormationGrade.fetchFormationGrade(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolFormationGrade fetchFormationGrade(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolFormationGrade.fetchFormationGrades(editingContext, qualifier, null);
    ScolFormationGrade eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolFormationGrade)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one FormationGrade that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolFormationGrade fetchRequiredFormationGrade(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolFormationGrade.fetchRequiredFormationGrade(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolFormationGrade fetchRequiredFormationGrade(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolFormationGrade eoObject = _ScolFormationGrade.fetchFormationGrade(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no FormationGrade that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolFormationGrade localInstanceIn(EOEditingContext editingContext, ScolFormationGrade eo) {
    ScolFormationGrade localInstance = (eo == null) ? null : (ScolFormationGrade)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
