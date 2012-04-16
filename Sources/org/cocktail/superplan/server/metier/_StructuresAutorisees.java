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

// DO NOT EDIT.  Make changes to StructuresAutorisees.java instead.
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

public abstract class _StructuresAutorisees extends  EOGenericRecord {
	public static final String ENTITY_NAME = "StructuresAutorisees";
	public static final String ENTITY_TABLE_NAME = "GRHUM.V_STRUCTURES_AUTORISEES";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "numeroGroupe";

	public static final String ACCES_KEY = "acces";
	public static final String ALIAS_GROUPE_KEY = "aliasGroupe";
	public static final String AUTORISE_KEY = "autorise";
	public static final String LIBELLE_GROUPE_KEY = "libelleGroupe";
	public static final String NUMERO_GROUPE_KEY = "numeroGroupe";
	public static final String NUMERO_PERE_KEY = "numeroPere";
	public static final String PERS_ID_KEY = "persId";
	public static final String TYPE_STRUCT_KEY = "typeStruct";

	public static final String ACCES_COLKEY = "ACCES";
	public static final String ALIAS_GROUPE_COLKEY = "ALIAS_GROUPE";
	public static final String AUTORISE_COLKEY = "AUTORISE";
	public static final String LIBELLE_GROUPE_COLKEY = "LIBELLE_GROUPE";
	public static final String NUMERO_GROUPE_COLKEY = "NUMERO_GROUPE";
	public static final String NUMERO_PERE_COLKEY = "NUMERO_PERE";
	public static final String PERS_ID_COLKEY = "PERS_ID";
	public static final String TYPE_STRUCT_COLKEY = "TYPE_STRUCT";

	// Relationships

	// Utilities methods
	  public StructuresAutorisees localInstanceIn(EOEditingContext editingContext) {
	    StructuresAutorisees localInstance = (StructuresAutorisees)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static StructuresAutorisees getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_StructuresAutorisees.ENTITY_NAME);
		      return (StructuresAutorisees) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String acces() {
    return (String) storedValueForKey("acces");
  }

  public void setAcces(String value) {
    takeStoredValueForKey(value, "acces");
  }

  public String aliasGroupe() {
    return (String) storedValueForKey("aliasGroupe");
  }

  public void setAliasGroupe(String value) {
    takeStoredValueForKey(value, "aliasGroupe");
  }

  public Integer autorise() {
    return (Integer) storedValueForKey("autorise");
  }

  public void setAutorise(Integer value) {
    takeStoredValueForKey(value, "autorise");
  }

  public String libelleGroupe() {
    return (String) storedValueForKey("libelleGroupe");
  }

  public void setLibelleGroupe(String value) {
    takeStoredValueForKey(value, "libelleGroupe");
  }

  public Integer numeroGroupe() {
    return (Integer) storedValueForKey("numeroGroupe");
  }

  public void setNumeroGroupe(Integer value) {
    takeStoredValueForKey(value, "numeroGroupe");
  }

  public Integer numeroPere() {
    return (Integer) storedValueForKey("numeroPere");
  }

  public void setNumeroPere(Integer value) {
    takeStoredValueForKey(value, "numeroPere");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public String typeStruct() {
    return (String) storedValueForKey("typeStruct");
  }

  public void setTypeStruct(String value) {
    takeStoredValueForKey(value, "typeStruct");
  }


  public static StructuresAutorisees createStructuresAutorisees(EOEditingContext editingContext, Integer numeroGroupe
, Integer persId
) {
    StructuresAutorisees eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_StructuresAutorisees.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _StructuresAutorisees.ENTITY_NAME + "' !");
    } else
    {
        eo = (StructuresAutorisees) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setNumeroGroupe(numeroGroupe);
		eo.setPersId(persId);
    return eo;
  }

  public static NSArray fetchAllStructuresAutoriseeses(EOEditingContext editingContext) {
    return _StructuresAutorisees.fetchAllStructuresAutoriseeses(editingContext, null);
  }

  public static NSArray fetchAllStructuresAutoriseeses(EOEditingContext editingContext, NSArray sortOrderings) {
    return _StructuresAutorisees.fetchStructuresAutoriseeses(editingContext, null, sortOrderings);
  }

  public static NSArray fetchStructuresAutoriseeses(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_StructuresAutorisees.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static StructuresAutorisees fetchStructuresAutorisees(EOEditingContext editingContext, String keyName, Object value) {
    return _StructuresAutorisees.fetchStructuresAutorisees(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static StructuresAutorisees fetchStructuresAutorisees(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _StructuresAutorisees.fetchStructuresAutoriseeses(editingContext, qualifier, null);
    StructuresAutorisees eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (StructuresAutorisees)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one StructuresAutorisees that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static StructuresAutorisees fetchRequiredStructuresAutorisees(EOEditingContext editingContext, String keyName, Object value) {
    return _StructuresAutorisees.fetchRequiredStructuresAutorisees(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static StructuresAutorisees fetchRequiredStructuresAutorisees(EOEditingContext editingContext, EOQualifier qualifier) {
    StructuresAutorisees eoObject = _StructuresAutorisees.fetchStructuresAutorisees(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no StructuresAutorisees that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static StructuresAutorisees localInstanceIn(EOEditingContext editingContext, StructuresAutorisees eo) {
    StructuresAutorisees localInstance = (eo == null) ? null : (StructuresAutorisees)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
