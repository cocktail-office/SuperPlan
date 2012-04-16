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

// DO NOT EDIT.  Make changes to ImplantationGeo.java instead.
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

public abstract class _ImplantationGeo extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ImplantationGeo";
	public static final String ENTITY_TABLE_NAME = "GRHUM.IMPLANTATION_GEO";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "imgeoOrdre";

	public static final String C_IMPLANTATION_KEY = "cImplantation";
	public static final String LC_IMPLANTATION_GEO_KEY = "lcImplantationGeo";
	public static final String LL_IMPLANTATION_GEO_KEY = "llImplantationGeo";

	public static final String C_IMPLANTATION_COLKEY = "C_IMPLANTATION";
	public static final String LC_IMPLANTATION_GEO_COLKEY = "LC_IMPLANTATION_GEO";
	public static final String LL_IMPLANTATION_GEO_COLKEY = "LL_IMPLANTATION_GEO";

	// Relationships

	// Utilities methods
	  public ImplantationGeo localInstanceIn(EOEditingContext editingContext) {
	    ImplantationGeo localInstance = (ImplantationGeo)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ImplantationGeo getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ImplantationGeo.ENTITY_NAME);
		      return (ImplantationGeo) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cImplantation() {
    return (String) storedValueForKey("cImplantation");
  }

  public void setCImplantation(String value) {
    takeStoredValueForKey(value, "cImplantation");
  }

  public String lcImplantationGeo() {
    return (String) storedValueForKey("lcImplantationGeo");
  }

  public void setLcImplantationGeo(String value) {
    takeStoredValueForKey(value, "lcImplantationGeo");
  }

  public String llImplantationGeo() {
    return (String) storedValueForKey("llImplantationGeo");
  }

  public void setLlImplantationGeo(String value) {
    takeStoredValueForKey(value, "llImplantationGeo");
  }


  public static ImplantationGeo createImplantationGeo(EOEditingContext editingContext) {
    ImplantationGeo eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ImplantationGeo.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ImplantationGeo.ENTITY_NAME + "' !");
    } else
    {
        eo = (ImplantationGeo) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
    return eo;
  }

  public static NSArray fetchAllImplantationGeos(EOEditingContext editingContext) {
    return _ImplantationGeo.fetchAllImplantationGeos(editingContext, null);
  }

  public static NSArray fetchAllImplantationGeos(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ImplantationGeo.fetchImplantationGeos(editingContext, null, sortOrderings);
  }

  public static NSArray fetchImplantationGeos(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ImplantationGeo.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ImplantationGeo fetchImplantationGeo(EOEditingContext editingContext, String keyName, Object value) {
    return _ImplantationGeo.fetchImplantationGeo(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ImplantationGeo fetchImplantationGeo(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ImplantationGeo.fetchImplantationGeos(editingContext, qualifier, null);
    ImplantationGeo eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ImplantationGeo)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ImplantationGeo that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ImplantationGeo fetchRequiredImplantationGeo(EOEditingContext editingContext, String keyName, Object value) {
    return _ImplantationGeo.fetchRequiredImplantationGeo(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ImplantationGeo fetchRequiredImplantationGeo(EOEditingContext editingContext, EOQualifier qualifier) {
    ImplantationGeo eoObject = _ImplantationGeo.fetchImplantationGeo(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ImplantationGeo that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ImplantationGeo localInstanceIn(EOEditingContext editingContext, ImplantationGeo eo) {
    ImplantationGeo localInstance = (eo == null) ? null : (ImplantationGeo)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
