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

// DO NOT EDIT.  Make changes to Secretariat.java instead.
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

public abstract class _Secretariat extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Secretariat";
	public static final String ENTITY_TABLE_NAME = "GRHUM.SECRETARIAT";

	// Attributes

	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";

	public static final String D_CREATION_COLKEY = "D_CREATION";
	public static final String D_MODIFICATION_COLKEY = "D_MODIFICATION";

	// Relationships
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String STRUCTURE_ULR_KEY = "structureUlr";

	// Utilities methods
	  public Secretariat localInstanceIn(EOEditingContext editingContext) {
	    Secretariat localInstance = (Secretariat)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Secretariat getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Secretariat.ENTITY_NAME);
		      return (Secretariat) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp dCreation() {
    return (NSTimestamp) storedValueForKey("dCreation");
  }

  public void setDCreation(NSTimestamp value) {
    takeStoredValueForKey(value, "dCreation");
  }

  public NSTimestamp dModification() {
    return (NSTimestamp) storedValueForKey("dModification");
  }

  public void setDModification(NSTimestamp value) {
    takeStoredValueForKey(value, "dModification");
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
  

  public static Secretariat createSecretariat(EOEditingContext editingContext, NSTimestamp dCreation
, NSTimestamp dModification
) {
    Secretariat eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Secretariat.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Secretariat.ENTITY_NAME + "' !");
    } else
    {
        eo = (Secretariat) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setDCreation(dCreation);
		eo.setDModification(dModification);
    return eo;
  }

  public static NSArray fetchAllSecretariats(EOEditingContext editingContext) {
    return _Secretariat.fetchAllSecretariats(editingContext, null);
  }

  public static NSArray fetchAllSecretariats(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Secretariat.fetchSecretariats(editingContext, null, sortOrderings);
  }

  public static NSArray fetchSecretariats(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Secretariat.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Secretariat fetchSecretariat(EOEditingContext editingContext, String keyName, Object value) {
    return _Secretariat.fetchSecretariat(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Secretariat fetchSecretariat(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Secretariat.fetchSecretariats(editingContext, qualifier, null);
    Secretariat eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Secretariat)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Secretariat that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Secretariat fetchRequiredSecretariat(EOEditingContext editingContext, String keyName, Object value) {
    return _Secretariat.fetchRequiredSecretariat(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Secretariat fetchRequiredSecretariat(EOEditingContext editingContext, EOQualifier qualifier) {
    Secretariat eoObject = _Secretariat.fetchSecretariat(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Secretariat that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Secretariat localInstanceIn(EOEditingContext editingContext, Secretariat eo) {
    Secretariat localInstance = (eo == null) ? null : (Secretariat)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
