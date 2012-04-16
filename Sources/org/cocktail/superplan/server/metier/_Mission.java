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

// DO NOT EDIT.  Make changes to Mission.java instead.
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

public abstract class _Mission extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Mission";
	public static final String ENTITY_TABLE_NAME = "jefy_mission.MISSION";

	// Attributes

	public static final String C_CORPS_KEY = "cCorps";
	public static final String MIS_DEBUT_KEY = "misDebut";
	public static final String MIS_ETAT_KEY = "misEtat";
	public static final String MIS_FIN_KEY = "misFin";
	public static final String MIS_MOTIF_KEY = "misMotif";

	public static final String C_CORPS_COLKEY = "C_CORPS";
	public static final String MIS_DEBUT_COLKEY = "MIS_DEBUT";
	public static final String MIS_ETAT_COLKEY = "MIS_ETAT";
	public static final String MIS_FIN_COLKEY = "MIS_FIN";
	public static final String MIS_MOTIF_COLKEY = "MIS_MOTIF";

	// Relationships
	public static final String FOURNIS_KEY = "fournis";
	public static final String TITRE_MISSION_KEY = "titreMission";

	// Utilities methods
	  public Mission localInstanceIn(EOEditingContext editingContext) {
	    Mission localInstance = (Mission)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static Mission getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_Mission.ENTITY_NAME);
		      return (Mission) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String cCorps() {
    return (String) storedValueForKey("cCorps");
  }

  public void setCCorps(String value) {
    takeStoredValueForKey(value, "cCorps");
  }

  public NSTimestamp misDebut() {
    return (NSTimestamp) storedValueForKey("misDebut");
  }

  public void setMisDebut(NSTimestamp value) {
    takeStoredValueForKey(value, "misDebut");
  }

  public String misEtat() {
    return (String) storedValueForKey("misEtat");
  }

  public void setMisEtat(String value) {
    takeStoredValueForKey(value, "misEtat");
  }

  public NSTimestamp misFin() {
    return (NSTimestamp) storedValueForKey("misFin");
  }

  public void setMisFin(NSTimestamp value) {
    takeStoredValueForKey(value, "misFin");
  }

  public String misMotif() {
    return (String) storedValueForKey("misMotif");
  }

  public void setMisMotif(String value) {
    takeStoredValueForKey(value, "misMotif");
  }

  public org.cocktail.superplan.server.metier.FournisUlr fournis() {
    return (org.cocktail.superplan.server.metier.FournisUlr)storedValueForKey("fournis");
  }

  public void setFournisRelationship(org.cocktail.superplan.server.metier.FournisUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FournisUlr oldValue = fournis();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "fournis");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "fournis");
    }
  }
  
  public org.cocktail.superplan.server.metier.TitreMission titreMission() {
    return (org.cocktail.superplan.server.metier.TitreMission)storedValueForKey("titreMission");
  }

  public void setTitreMissionRelationship(org.cocktail.superplan.server.metier.TitreMission value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.TitreMission oldValue = titreMission();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "titreMission");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "titreMission");
    }
  }
  

  public static Mission createMission(EOEditingContext editingContext, NSTimestamp misDebut
, String misEtat
, NSTimestamp misFin
, String misMotif
, org.cocktail.superplan.server.metier.TitreMission titreMission) {
    Mission eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_Mission.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _Mission.ENTITY_NAME + "' !");
    } else
    {
        eo = (Mission) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setMisDebut(misDebut);
		eo.setMisEtat(misEtat);
		eo.setMisFin(misFin);
		eo.setMisMotif(misMotif);
    eo.setTitreMissionRelationship(titreMission);
    return eo;
  }

  public static NSArray fetchAllMissions(EOEditingContext editingContext) {
    return _Mission.fetchAllMissions(editingContext, null);
  }

  public static NSArray fetchAllMissions(EOEditingContext editingContext, NSArray sortOrderings) {
    return _Mission.fetchMissions(editingContext, null, sortOrderings);
  }

  public static NSArray fetchMissions(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Mission.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Mission fetchMission(EOEditingContext editingContext, String keyName, Object value) {
    return _Mission.fetchMission(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Mission fetchMission(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _Mission.fetchMissions(editingContext, qualifier, null);
    Mission eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Mission)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Mission that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Mission fetchRequiredMission(EOEditingContext editingContext, String keyName, Object value) {
    return _Mission.fetchRequiredMission(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Mission fetchRequiredMission(EOEditingContext editingContext, EOQualifier qualifier) {
    Mission eoObject = _Mission.fetchMission(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Mission that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Mission localInstanceIn(EOEditingContext editingContext, Mission eo) {
    Mission localInstance = (eo == null) ? null : (Mission)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
