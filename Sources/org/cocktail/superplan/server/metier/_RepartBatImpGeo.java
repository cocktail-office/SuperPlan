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

// DO NOT EDIT.  Make changes to RepartBatImpGeo.java instead.
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

public abstract class _RepartBatImpGeo extends  EOGenericRecord {
	public static final String ENTITY_NAME = "RepartBatImpGeo";
	public static final String ENTITY_TABLE_NAME = "GRHUM.REPART_BAT_IMP_GEO";

	// Attributes

	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";

	public static final String D_CREATION_COLKEY = "D_CREATION";
	public static final String D_MODIFICATION_COLKEY = "D_MODIFICATION";

	// Relationships
	public static final String IMPLANTATION_GEO_KEY = "implantationGeo";
	public static final String LOCAL_KEY = "local";

	// Utilities methods
	  public RepartBatImpGeo localInstanceIn(EOEditingContext editingContext) {
	    RepartBatImpGeo localInstance = (RepartBatImpGeo)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static RepartBatImpGeo getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_RepartBatImpGeo.ENTITY_NAME);
		      return (RepartBatImpGeo) descriptionClass.createInstanceWithEditingContext(editingContext, null);
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

  public org.cocktail.superplan.server.metier.ImplantationGeo implantationGeo() {
    return (org.cocktail.superplan.server.metier.ImplantationGeo)storedValueForKey("implantationGeo");
  }

  public void setImplantationGeoRelationship(org.cocktail.superplan.server.metier.ImplantationGeo value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ImplantationGeo oldValue = implantationGeo();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "implantationGeo");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "implantationGeo");
    }
  }
  
  public org.cocktail.superplan.server.metier.Local local() {
    return (org.cocktail.superplan.server.metier.Local)storedValueForKey("local");
  }

  public void setLocalRelationship(org.cocktail.superplan.server.metier.Local value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.Local oldValue = local();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "local");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "local");
    }
  }
  

  public static RepartBatImpGeo createRepartBatImpGeo(EOEditingContext editingContext, org.cocktail.superplan.server.metier.ImplantationGeo implantationGeo) {
    RepartBatImpGeo eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_RepartBatImpGeo.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _RepartBatImpGeo.ENTITY_NAME + "' !");
    } else
    {
        eo = (RepartBatImpGeo) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    eo.setImplantationGeoRelationship(implantationGeo);
    return eo;
  }

  public static NSArray fetchAllRepartBatImpGeos(EOEditingContext editingContext) {
    return _RepartBatImpGeo.fetchAllRepartBatImpGeos(editingContext, null);
  }

  public static NSArray fetchAllRepartBatImpGeos(EOEditingContext editingContext, NSArray sortOrderings) {
    return _RepartBatImpGeo.fetchRepartBatImpGeos(editingContext, null, sortOrderings);
  }

  public static NSArray fetchRepartBatImpGeos(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_RepartBatImpGeo.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static RepartBatImpGeo fetchRepartBatImpGeo(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartBatImpGeo.fetchRepartBatImpGeo(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartBatImpGeo fetchRepartBatImpGeo(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _RepartBatImpGeo.fetchRepartBatImpGeos(editingContext, qualifier, null);
    RepartBatImpGeo eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (RepartBatImpGeo)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one RepartBatImpGeo that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartBatImpGeo fetchRequiredRepartBatImpGeo(EOEditingContext editingContext, String keyName, Object value) {
    return _RepartBatImpGeo.fetchRequiredRepartBatImpGeo(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static RepartBatImpGeo fetchRequiredRepartBatImpGeo(EOEditingContext editingContext, EOQualifier qualifier) {
    RepartBatImpGeo eoObject = _RepartBatImpGeo.fetchRepartBatImpGeo(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no RepartBatImpGeo that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static RepartBatImpGeo localInstanceIn(EOEditingContext editingContext, RepartBatImpGeo eo) {
    RepartBatImpGeo localInstance = (eo == null) ? null : (RepartBatImpGeo)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
