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

// DO NOT EDIT.  Make changes to IndividuUlr.java instead.
package org.cocktail.superplan.client.metier;

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

public abstract class _IndividuUlr extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IndividuUlr";
	public static final String ENTITY_TABLE_NAME = "GRHUM.INDIVIDU_ULR";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "noIndividu";

	public static final String CATEGORIE_PRINC_KEY = "categoriePrinc";
	public static final String C_CIVILITE_KEY = "cCivilite";
	public static final String IND_AGENDA_KEY = "indAgenda";
	public static final String IND_PHOTO_KEY = "indPhoto";
	public static final String IND_QUALITE_KEY = "indQualite";
	public static final String NO_INDIVIDU_KEY = "noIndividu";
	public static final String NOM_USUEL_KEY = "nomUsuel";
	public static final String PERS_ID_KEY = "persId";
	public static final String PRENOM_KEY = "prenom";
	public static final String TEM_VALIDE_KEY = "temValide";

	public static final String CATEGORIE_PRINC_COLKEY = "CATEGORIE_PRINC";
	public static final String C_CIVILITE_COLKEY = "C_CIVILITE";
	public static final String IND_AGENDA_COLKEY = "IND_AGENDA";
	public static final String IND_PHOTO_COLKEY = "IND_PHOTO";
	public static final String IND_QUALITE_COLKEY = "IND_QUALITE";
	public static final String NO_INDIVIDU_COLKEY = "NO_INDIVIDU";
	public static final String NOM_USUEL_COLKEY = "NOM_USUEL";
	public static final String PERS_ID_COLKEY = "PERS_ID";
	public static final String PRENOM_COLKEY = "PRENOM";
	public static final String TEM_VALIDE_COLKEY = "TEM_VALIDE";

	// Relationships
	public static final String PERSONNE_KEY = "personne";
	public static final String REPART_LOT_INDIVIDUS_KEY = "repartLotIndividus";
	public static final String REPART_STRUCTURES_KEY = "repartStructures";
	public static final String SUPANN_CATEGORIE_KEY = "supannCategorie";
	public static final String TO_SECRETARIAT_KEY = "toSecretariat";
	public static final String TO_STRUCTURE_ULR_KEY = "toStructureUlr";

	// Utilities methods
	  public IndividuUlr localInstanceIn(EOEditingContext editingContext) {
	    IndividuUlr localInstance = (IndividuUlr)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static IndividuUlr getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_IndividuUlr.ENTITY_NAME);
		      return (IndividuUlr) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer categoriePrinc() {
    return (Integer) storedValueForKey("categoriePrinc");
  }

  public void setCategoriePrinc(Integer value) {
    takeStoredValueForKey(value, "categoriePrinc");
  }

  public String cCivilite() {
    return (String) storedValueForKey("cCivilite");
  }

  public void setCCivilite(String value) {
    takeStoredValueForKey(value, "cCivilite");
  }

  public String indAgenda() {
    return (String) storedValueForKey("indAgenda");
  }

  public void setIndAgenda(String value) {
    takeStoredValueForKey(value, "indAgenda");
  }

  public String indPhoto() {
    return (String) storedValueForKey("indPhoto");
  }

  public void setIndPhoto(String value) {
    takeStoredValueForKey(value, "indPhoto");
  }

  public String indQualite() {
    return (String) storedValueForKey("indQualite");
  }

  public void setIndQualite(String value) {
    takeStoredValueForKey(value, "indQualite");
  }

  public Integer noIndividu() {
    return (Integer) storedValueForKey("noIndividu");
  }

  public void setNoIndividu(Integer value) {
    takeStoredValueForKey(value, "noIndividu");
  }

  public String nomUsuel() {
    return (String) storedValueForKey("nomUsuel");
  }

  public void setNomUsuel(String value) {
    takeStoredValueForKey(value, "nomUsuel");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public String prenom() {
    return (String) storedValueForKey("prenom");
  }

  public void setPrenom(String value) {
    takeStoredValueForKey(value, "prenom");
  }

  public String temValide() {
    return (String) storedValueForKey("temValide");
  }

  public void setTemValide(String value) {
    takeStoredValueForKey(value, "temValide");
  }

  public org.cocktail.superplan.client.metier.Personne personne() {
    return (org.cocktail.superplan.client.metier.Personne)storedValueForKey("personne");
  }

  public void setPersonneRelationship(org.cocktail.superplan.client.metier.Personne value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.Personne oldValue = personne();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "personne");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "personne");
    }
  }
  
  public org.cocktail.superplan.client.metier.SupannCategorie supannCategorie() {
    return (org.cocktail.superplan.client.metier.SupannCategorie)storedValueForKey("supannCategorie");
  }

  public void setSupannCategorieRelationship(org.cocktail.superplan.client.metier.SupannCategorie value) {
    if (value == null) {
    	org.cocktail.superplan.client.metier.SupannCategorie oldValue = supannCategorie();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "supannCategorie");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "supannCategorie");
    }
  }
  
  public NSArray repartLotIndividus() {
    return (NSArray)storedValueForKey("repartLotIndividus");
  }

  public NSArray repartLotIndividus(EOQualifier qualifier) {
    return repartLotIndividus(qualifier, null, false);
  }

  public NSArray repartLotIndividus(EOQualifier qualifier, boolean fetch) {
    return repartLotIndividus(qualifier, null, fetch);
  }

  public NSArray repartLotIndividus(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.RepartLotIndividu.INDIVIDU_ULR_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.RepartLotIndividu.fetchRepartLotIndividus(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = repartLotIndividus();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRepartLotIndividusRelationship(org.cocktail.superplan.client.metier.RepartLotIndividu object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartLotIndividus");
  }

  public void removeFromRepartLotIndividusRelationship(org.cocktail.superplan.client.metier.RepartLotIndividu object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartLotIndividus");
  }

  public org.cocktail.superplan.client.metier.RepartLotIndividu createRepartLotIndividusRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartLotIndividu");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartLotIndividus");
    return (org.cocktail.superplan.client.metier.RepartLotIndividu) eo;
  }

  public void deleteRepartLotIndividusRelationship(org.cocktail.superplan.client.metier.RepartLotIndividu object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartLotIndividus");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartLotIndividusRelationships() {
    Enumeration objects = repartLotIndividus().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartLotIndividusRelationship((org.cocktail.superplan.client.metier.RepartLotIndividu)objects.nextElement());
    }
  }

  public NSArray repartStructures() {
    return (NSArray)storedValueForKey("repartStructures");
  }

  public NSArray repartStructures(EOQualifier qualifier) {
    return repartStructures(qualifier, null, false);
  }

  public NSArray repartStructures(EOQualifier qualifier, boolean fetch) {
    return repartStructures(qualifier, null, fetch);
  }

  public NSArray repartStructures(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.RepartStructure.INDIVIDU_ULR_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.RepartStructure.fetchRepartStructures(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = repartStructures();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "repartStructures");
  }

  public void removeFromRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartStructures");
  }

  public org.cocktail.superplan.client.metier.RepartStructure createRepartStructuresRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RepartStructure");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "repartStructures");
    return (org.cocktail.superplan.client.metier.RepartStructure) eo;
  }

  public void deleteRepartStructuresRelationship(org.cocktail.superplan.client.metier.RepartStructure object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "repartStructures");
    editingContext().deleteObject(object);
  }

  public void deleteAllRepartStructuresRelationships() {
    Enumeration objects = repartStructures().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRepartStructuresRelationship((org.cocktail.superplan.client.metier.RepartStructure)objects.nextElement());
    }
  }

  public NSArray toSecretariat() {
    return (NSArray)storedValueForKey("toSecretariat");
  }

  public NSArray toSecretariat(EOQualifier qualifier) {
    return toSecretariat(qualifier, null, false);
  }

  public NSArray toSecretariat(EOQualifier qualifier, boolean fetch) {
    return toSecretariat(qualifier, null, fetch);
  }

  public NSArray toSecretariat(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.Secretariat.INDIVIDU_ULR_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.Secretariat.fetchSecretariats(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toSecretariat();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToSecretariatRelationship(org.cocktail.superplan.client.metier.Secretariat object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "toSecretariat");
  }

  public void removeFromToSecretariatRelationship(org.cocktail.superplan.client.metier.Secretariat object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toSecretariat");
  }

  public org.cocktail.superplan.client.metier.Secretariat createToSecretariatRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Secretariat");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toSecretariat");
    return (org.cocktail.superplan.client.metier.Secretariat) eo;
  }

  public void deleteToSecretariatRelationship(org.cocktail.superplan.client.metier.Secretariat object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toSecretariat");
    editingContext().deleteObject(object);
  }

  public void deleteAllToSecretariatRelationships() {
    Enumeration objects = toSecretariat().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToSecretariatRelationship((org.cocktail.superplan.client.metier.Secretariat)objects.nextElement());
    }
  }

  public NSArray toStructureUlr() {
    return (NSArray)storedValueForKey("toStructureUlr");
  }

  public NSArray toStructureUlr(EOQualifier qualifier) {
    return toStructureUlr(qualifier, null, false);
  }

  public NSArray toStructureUlr(EOQualifier qualifier, boolean fetch) {
    return toStructureUlr(qualifier, null, fetch);
  }

  public NSArray toStructureUlr(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.client.metier.StructureUlr.RESPONSABLE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.client.metier.StructureUlr.fetchStructureUlrs(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toStructureUlr();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToStructureUlrRelationship(org.cocktail.superplan.client.metier.StructureUlr object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "toStructureUlr");
  }

  public void removeFromToStructureUlrRelationship(org.cocktail.superplan.client.metier.StructureUlr object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toStructureUlr");
  }

  public org.cocktail.superplan.client.metier.StructureUlr createToStructureUlrRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("StructureUlr");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toStructureUlr");
    return (org.cocktail.superplan.client.metier.StructureUlr) eo;
  }

  public void deleteToStructureUlrRelationship(org.cocktail.superplan.client.metier.StructureUlr object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toStructureUlr");
    editingContext().deleteObject(object);
  }

  public void deleteAllToStructureUlrRelationships() {
    Enumeration objects = toStructureUlr().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToStructureUlrRelationship((org.cocktail.superplan.client.metier.StructureUlr)objects.nextElement());
    }
  }


  public static IndividuUlr createIndividuUlr(EOEditingContext editingContext, String cCivilite
, Integer noIndividu
, String nomUsuel
, Integer persId
, String prenom
, String temValide
) {
    IndividuUlr eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_IndividuUlr.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _IndividuUlr.ENTITY_NAME + "' !");
    } else
    {
        eo = (IndividuUlr) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setCCivilite(cCivilite);
		eo.setNoIndividu(noIndividu);
		eo.setNomUsuel(nomUsuel);
		eo.setPersId(persId);
		eo.setPrenom(prenom);
		eo.setTemValide(temValide);
    return eo;
  }

  public static NSArray fetchAllIndividuUlrs(EOEditingContext editingContext) {
    return _IndividuUlr.fetchAllIndividuUlrs(editingContext, null);
  }

  public static NSArray fetchAllIndividuUlrs(EOEditingContext editingContext, NSArray sortOrderings) {
    return _IndividuUlr.fetchIndividuUlrs(editingContext, null, sortOrderings);
  }

  public static NSArray fetchIndividuUlrs(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IndividuUlr.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IndividuUlr fetchIndividuUlr(EOEditingContext editingContext, String keyName, Object value) {
    return _IndividuUlr.fetchIndividuUlr(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IndividuUlr fetchIndividuUlr(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _IndividuUlr.fetchIndividuUlrs(editingContext, qualifier, null);
    IndividuUlr eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IndividuUlr)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IndividuUlr that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IndividuUlr fetchRequiredIndividuUlr(EOEditingContext editingContext, String keyName, Object value) {
    return _IndividuUlr.fetchRequiredIndividuUlr(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IndividuUlr fetchRequiredIndividuUlr(EOEditingContext editingContext, EOQualifier qualifier) {
    IndividuUlr eoObject = _IndividuUlr.fetchIndividuUlr(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IndividuUlr that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IndividuUlr localInstanceIn(EOEditingContext editingContext, IndividuUlr eo) {
    IndividuUlr localInstance = (eo == null) ? null : (IndividuUlr)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
