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

// DO NOT EDIT.  Make changes to HoraireCode.java instead.
package org.cocktail.superplan.server.metier;

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

public abstract class _HoraireCode extends  EOGenericRecord {
	public static final String ENTITY_NAME = "HoraireCode";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_MAQUETTE_HORAIRE_CODE";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "mhcoCode";

	public static final String MHCO_ABREVIATION_KEY = "mhcoAbreviation";
	public static final String MHCO_CODE_KEY = "mhcoCode";
	public static final String MHCO_LIBELLE_KEY = "mhcoLibelle";
	public static final String MHCO_PRIORITE_KEY = "mhcoPriorite";
	public static final String MHCO_VALIDITE_KEY = "mhcoValidite";

	public static final String MHCO_ABREVIATION_COLKEY = "MHCO_ABREVIATION";
	public static final String MHCO_CODE_COLKEY = "MHCO_CODE";
	public static final String MHCO_LIBELLE_COLKEY = "MHCO_LIBELLE";
	public static final String MHCO_PRIORITE_COLKEY = "MHCO_PRIORITE";
	public static final String MHCO_VALIDITE_COLKEY = "MHCO_VALIDITE";

	// Relationships
	public static final String SCOL_MAQUETTE_APS_KEY = "scolMaquetteAps";

	// Utilities methods
	  public HoraireCode localInstanceIn(EOEditingContext editingContext) {
	    HoraireCode localInstance = (HoraireCode)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static HoraireCode getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_HoraireCode.ENTITY_NAME);
		      return (HoraireCode) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String mhcoAbreviation() {
    return (String) storedValueForKey("mhcoAbreviation");
  }

  public void setMhcoAbreviation(String value) {
    takeStoredValueForKey(value, "mhcoAbreviation");
  }

  public String mhcoCode() {
    return (String) storedValueForKey("mhcoCode");
  }

  public void setMhcoCode(String value) {
    takeStoredValueForKey(value, "mhcoCode");
  }

  public String mhcoLibelle() {
    return (String) storedValueForKey("mhcoLibelle");
  }

  public void setMhcoLibelle(String value) {
    takeStoredValueForKey(value, "mhcoLibelle");
  }

  public Integer mhcoPriorite() {
    return (Integer) storedValueForKey("mhcoPriorite");
  }

  public void setMhcoPriorite(Integer value) {
    takeStoredValueForKey(value, "mhcoPriorite");
  }

  public String mhcoValidite() {
    return (String) storedValueForKey("mhcoValidite");
  }

  public void setMhcoValidite(String value) {
    takeStoredValueForKey(value, "mhcoValidite");
  }

  public NSArray scolMaquetteAps() {
    return (NSArray)storedValueForKey("scolMaquetteAps");
  }

  public NSArray scolMaquetteAps(EOQualifier qualifier) {
    return scolMaquetteAps(qualifier, null, false);
  }

  public NSArray scolMaquetteAps(EOQualifier qualifier, boolean fetch) {
    return scolMaquetteAps(qualifier, null, fetch);
  }

  public NSArray scolMaquetteAps(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.MaquetteAp.HORAIRE_CODE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.MaquetteAp.fetchMaquetteAps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = scolMaquetteAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToScolMaquetteApsRelationship(org.cocktail.superplan.server.metier.MaquetteAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolMaquetteAps");
  }

  public void removeFromScolMaquetteApsRelationship(org.cocktail.superplan.server.metier.MaquetteAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolMaquetteAps");
  }

  public org.cocktail.superplan.server.metier.MaquetteAp createScolMaquetteApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("MaquetteAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolMaquetteAps");
    return (org.cocktail.superplan.server.metier.MaquetteAp) eo;
  }

  public void deleteScolMaquetteApsRelationship(org.cocktail.superplan.server.metier.MaquetteAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolMaquetteAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolMaquetteApsRelationships() {
    Enumeration objects = scolMaquetteAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolMaquetteApsRelationship((org.cocktail.superplan.server.metier.MaquetteAp)objects.nextElement());
    }
  }


  public static HoraireCode createHoraireCode(EOEditingContext editingContext, String mhcoCode
, String mhcoLibelle
, Integer mhcoPriorite
, String mhcoValidite
) {
    HoraireCode eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_HoraireCode.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _HoraireCode.ENTITY_NAME + "' !");
    } else
    {
        eo = (HoraireCode) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setMhcoCode(mhcoCode);
		eo.setMhcoLibelle(mhcoLibelle);
		eo.setMhcoPriorite(mhcoPriorite);
		eo.setMhcoValidite(mhcoValidite);
    return eo;
  }

  public static NSArray fetchAllHoraireCodes(EOEditingContext editingContext) {
    return _HoraireCode.fetchAllHoraireCodes(editingContext, null);
  }

  public static NSArray fetchAllHoraireCodes(EOEditingContext editingContext, NSArray sortOrderings) {
    return _HoraireCode.fetchHoraireCodes(editingContext, null, sortOrderings);
  }

  public static NSArray fetchHoraireCodes(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_HoraireCode.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static HoraireCode fetchHoraireCode(EOEditingContext editingContext, String keyName, Object value) {
    return _HoraireCode.fetchHoraireCode(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static HoraireCode fetchHoraireCode(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _HoraireCode.fetchHoraireCodes(editingContext, qualifier, null);
    HoraireCode eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (HoraireCode)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one HoraireCode that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static HoraireCode fetchRequiredHoraireCode(EOEditingContext editingContext, String keyName, Object value) {
    return _HoraireCode.fetchRequiredHoraireCode(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static HoraireCode fetchRequiredHoraireCode(EOEditingContext editingContext, EOQualifier qualifier) {
    HoraireCode eoObject = _HoraireCode.fetchHoraireCode(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no HoraireCode that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static HoraireCode localInstanceIn(EOEditingContext editingContext, HoraireCode eo) {
    HoraireCode localInstance = (eo == null) ? null : (HoraireCode)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
