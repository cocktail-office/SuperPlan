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

// DO NOT EDIT.  Make changes to ScolFormationFiliere.java instead.
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

public abstract class _ScolFormationFiliere extends  EOGenericRecord {
	public static final String ENTITY_NAME = "FormationFiliere";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_FORMATION_FILIERE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "ffilCode";

	public static final String FFIL_ABREVIATION_KEY = "ffilAbreviation";
	public static final String FFIL_LIBELLE_KEY = "ffilLibelle";
	public static final String FFIL_VALIDITE_KEY = "ffilValidite";

	public static final String FFIL_ABREVIATION_COLKEY = "FFIL_ABREVIATION";
	public static final String FFIL_LIBELLE_COLKEY = "FFIL_LIBELLE";
	public static final String FFIL_VALIDITE_COLKEY = "FFIL_VALIDITE";

	// Relationships

	// Utilities methods
	  public ScolFormationFiliere localInstanceIn(EOEditingContext editingContext) {
	    ScolFormationFiliere localInstance = (ScolFormationFiliere)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolFormationFiliere getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolFormationFiliere.ENTITY_NAME);
		      return (ScolFormationFiliere) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String ffilAbreviation() {
    return (String) storedValueForKey("ffilAbreviation");
  }

  public void setFfilAbreviation(String value) {
    takeStoredValueForKey(value, "ffilAbreviation");
  }

  public String ffilLibelle() {
    return (String) storedValueForKey("ffilLibelle");
  }

  public void setFfilLibelle(String value) {
    takeStoredValueForKey(value, "ffilLibelle");
  }

  public String ffilValidite() {
    return (String) storedValueForKey("ffilValidite");
  }

  public void setFfilValidite(String value) {
    takeStoredValueForKey(value, "ffilValidite");
  }


  public static ScolFormationFiliere createFormationFiliere(EOEditingContext editingContext, String ffilLibelle
, String ffilValidite
) {
    ScolFormationFiliere eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolFormationFiliere.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolFormationFiliere.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolFormationFiliere) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setFfilLibelle(ffilLibelle);
		eo.setFfilValidite(ffilValidite);
    return eo;
  }

  public static NSArray fetchAllFormationFilieres(EOEditingContext editingContext) {
    return _ScolFormationFiliere.fetchAllFormationFilieres(editingContext, null);
  }

  public static NSArray fetchAllFormationFilieres(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolFormationFiliere.fetchFormationFilieres(editingContext, null, sortOrderings);
  }

  public static NSArray fetchFormationFilieres(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolFormationFiliere.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolFormationFiliere fetchFormationFiliere(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolFormationFiliere.fetchFormationFiliere(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolFormationFiliere fetchFormationFiliere(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolFormationFiliere.fetchFormationFilieres(editingContext, qualifier, null);
    ScolFormationFiliere eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolFormationFiliere)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one FormationFiliere that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolFormationFiliere fetchRequiredFormationFiliere(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolFormationFiliere.fetchRequiredFormationFiliere(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolFormationFiliere fetchRequiredFormationFiliere(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolFormationFiliere eoObject = _ScolFormationFiliere.fetchFormationFiliere(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no FormationFiliere that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolFormationFiliere localInstanceIn(EOEditingContext editingContext, ScolFormationFiliere eo) {
    ScolFormationFiliere localInstance = (eo == null) ? null : (ScolFormationFiliere)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
