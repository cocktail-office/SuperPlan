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

// DO NOT EDIT.  Make changes to ContrainteHeure.java instead.
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

public abstract class _ContrainteHeure extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ContrainteHeure";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.CONTRAINTE_HEURE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "cthKey";

	public static final String CTH_HEURE_DEBUT_KEY = "cthHeureDebut";
	public static final String CTH_HEURE_FIN_KEY = "cthHeureFin";

	public static final String CTH_HEURE_DEBUT_COLKEY = "CTH_HEURE_DEBUT";
	public static final String CTH_HEURE_FIN_COLKEY = "CTH_HEURE_FIN";

	// Relationships
	public static final String CONTRAINTE_JOUR_KEY = "contrainteJour";

	// Utilities methods
	  public ContrainteHeure localInstanceIn(EOEditingContext editingContext) {
	    ContrainteHeure localInstance = (ContrainteHeure)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ContrainteHeure getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ContrainteHeure.ENTITY_NAME);
		      return (ContrainteHeure) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public NSTimestamp cthHeureDebut() {
    return (NSTimestamp) storedValueForKey("cthHeureDebut");
  }

  public void setCthHeureDebut(NSTimestamp value) {
    takeStoredValueForKey(value, "cthHeureDebut");
  }

  public NSTimestamp cthHeureFin() {
    return (NSTimestamp) storedValueForKey("cthHeureFin");
  }

  public void setCthHeureFin(NSTimestamp value) {
    takeStoredValueForKey(value, "cthHeureFin");
  }

  public org.cocktail.superplan.client.metier.ContrainteJour contrainteJour() {
    return (org.cocktail.superplan.client.metier.ContrainteJour)storedValueForKey("contrainteJour");
  }

  public void setContrainteJourRelationship(org.cocktail.superplan.client.metier.ContrainteJour value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.ContrainteJour oldValue = contrainteJour();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "contrainteJour");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "contrainteJour");
    }
  }
  

  public static ContrainteHeure createContrainteHeure(EOEditingContext editingContext, NSTimestamp cthHeureDebut
, NSTimestamp cthHeureFin
, org.cocktail.superplan.client.metier.ContrainteJour contrainteJour) {
    ContrainteHeure eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ContrainteHeure.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ContrainteHeure.ENTITY_NAME + "' !");
    } else
    {
        eo = (ContrainteHeure) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCthHeureDebut(cthHeureDebut);
		eo.setCthHeureFin(cthHeureFin);
    eo.setContrainteJourRelationship(contrainteJour);
    return eo;
  }

  public static NSArray fetchAllContrainteHeures(EOEditingContext editingContext) {
    return _ContrainteHeure.fetchAllContrainteHeures(editingContext, null);
  }

  public static NSArray fetchAllContrainteHeures(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ContrainteHeure.fetchContrainteHeures(editingContext, null, sortOrderings);
  }

  public static NSArray fetchContrainteHeures(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ContrainteHeure.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ContrainteHeure fetchContrainteHeure(EOEditingContext editingContext, String keyName, Object value) {
    return _ContrainteHeure.fetchContrainteHeure(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ContrainteHeure fetchContrainteHeure(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ContrainteHeure.fetchContrainteHeures(editingContext, qualifier, null);
    ContrainteHeure eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ContrainteHeure)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ContrainteHeure that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ContrainteHeure fetchRequiredContrainteHeure(EOEditingContext editingContext, String keyName, Object value) {
    return _ContrainteHeure.fetchRequiredContrainteHeure(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ContrainteHeure fetchRequiredContrainteHeure(EOEditingContext editingContext, EOQualifier qualifier) {
    ContrainteHeure eoObject = _ContrainteHeure.fetchContrainteHeure(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ContrainteHeure that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ContrainteHeure localInstanceIn(EOEditingContext editingContext, ContrainteHeure eo) {
    ContrainteHeure localInstance = (eo == null) ? null : (ContrainteHeure)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
