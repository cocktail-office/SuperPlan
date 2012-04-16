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

// DO NOT EDIT.  Make changes to PrefUser.java instead.
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

public abstract class _PrefUser extends  EOGenericRecord {
	public static final String ENTITY_NAME = "PrefUser";
	public static final String ENTITY_TABLE_NAME = "EDTSCOL.PREF_USER";

	// Attributes
 public static final String ENTITY_PRIMARY_KEY = "pUserKey";

	public static final String AFF_HORAIRES_HAMAC_KEY = "affHorairesHamac";
	public static final String ANNEE_CIVILE_KEY = "anneeCivile";
	public static final String COLOR_EC_KEY = "colorEc";
	public static final String COMMENT_SCOL_KEY = "commentScol";
	public static final String DEFAULT_TYPE_RESA_KEY = "defaultTypeResa";
	public static final String DEFAULT_VISIBILITE_KEY = "defaultVisibilite";
	public static final String DIP_EDT_PERS_KEY = "dipEdtPers";
	public static final String DIP_EDT_SAL_KEY = "dipEdtSal";
	public static final String DISP_EXAM_KEY = "dispExam";
	public static final String DISP_PAR_COM_KEY = "dispParCom";
	public static final String HEURE_DEB_IMPRESS_KEY = "heureDebImpress";
	public static final String HEURE_FIN_IMPRESS_KEY = "heureFinImpress";
	public static final String INTERVALLE_MINIMUM_KEY = "intervalleMinimum";
	public static final String PAS_MAIL_HORS_SEMAINE_KEY = "pasMailHorsSemaine";
	public static final String SELECTION_GROUPE_MULTIPLE_KEY = "selectionGroupeMultiple";
	public static final String SEND_MAIL_DEPOSITAIRES_KEY = "sendMailDepositaires";
	public static final String SEND_MAIL_OCCUPANTS_KEY = "sendMailOccupants";
	public static final String USE_RESP_AP_KEY = "useRespAp";
	public static final String USE_TOOLTIP_PLANNING_KEY = "useTooltipPlanning";

	public static final String AFF_HORAIRES_HAMAC_COLKEY = "AFF_HORAIRES_HAMAC";
	public static final String ANNEE_CIVILE_COLKEY = "ANNEE_CIVILE";
	public static final String COLOR_EC_COLKEY = "COLOR_EC";
	public static final String COMMENT_SCOL_COLKEY = "COMMENT_SCOL";
	public static final String DEFAULT_TYPE_RESA_COLKEY = "DEFAULT_TYPE_RESA";
	public static final String DEFAULT_VISIBILITE_COLKEY = "DEFAULT_VISIBILITE";
	public static final String DIP_EDT_PERS_COLKEY = "DIP_EDT_PERS";
	public static final String DIP_EDT_SAL_COLKEY = "DIP_EDT_SAL";
	public static final String DISP_EXAM_COLKEY = "DISP_EXAM";
	public static final String DISP_PAR_COM_COLKEY = "DISP_PAR_COM";
	public static final String HEURE_DEB_IMPRESS_COLKEY = "HEURE_DEB_IMPRESS";
	public static final String HEURE_FIN_IMPRESS_COLKEY = "HEURE_FIN_IMPRESS";
	public static final String INTERVALLE_MINIMUM_COLKEY = "INTERVALLE_MINIMUM";
	public static final String PAS_MAIL_HORS_SEMAINE_COLKEY = "PAS_MAIL_HORS_SEMAINE";
	public static final String SELECTION_GROUPE_MULTIPLE_COLKEY = "SELECTION_GROUPE_MULTIPLE";
	public static final String SEND_MAIL_DEPOSITAIRES_COLKEY = "SEND_MAIL_DEPOSITAIRES";
	public static final String SEND_MAIL_OCCUPANTS_COLKEY = "SEND_MAIL_OCCUPANTS";
	public static final String USE_RESP_AP_COLKEY = "USE_RESP_AP";
	public static final String USE_TOOLTIP_PLANNING_COLKEY = "USE_TOOLTIP_PLANNING";

	// Relationships
	public static final String INDIVIDU_KEY = "individu";

	// Utilities methods
	  public PrefUser localInstanceIn(EOEditingContext editingContext) {
	    PrefUser localInstance = (PrefUser)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static PrefUser getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_PrefUser.ENTITY_NAME);
		      return (PrefUser) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public Integer affHorairesHamac() {
    return (Integer) storedValueForKey("affHorairesHamac");
  }

  public void setAffHorairesHamac(Integer value) {
    takeStoredValueForKey(value, "affHorairesHamac");
  }

  public Integer anneeCivile() {
    return (Integer) storedValueForKey("anneeCivile");
  }

  public void setAnneeCivile(Integer value) {
    takeStoredValueForKey(value, "anneeCivile");
  }

  public String colorEc() {
    return (String) storedValueForKey("colorEc");
  }

  public void setColorEc(String value) {
    takeStoredValueForKey(value, "colorEc");
  }

  public Integer commentScol() {
    return (Integer) storedValueForKey("commentScol");
  }

  public void setCommentScol(Integer value) {
    takeStoredValueForKey(value, "commentScol");
  }

  public String defaultTypeResa() {
    return (String) storedValueForKey("defaultTypeResa");
  }

  public void setDefaultTypeResa(String value) {
    takeStoredValueForKey(value, "defaultTypeResa");
  }

  public String defaultVisibilite() {
    return (String) storedValueForKey("defaultVisibilite");
  }

  public void setDefaultVisibilite(String value) {
    takeStoredValueForKey(value, "defaultVisibilite");
  }

  public Integer dipEdtPers() {
    return (Integer) storedValueForKey("dipEdtPers");
  }

  public void setDipEdtPers(Integer value) {
    takeStoredValueForKey(value, "dipEdtPers");
  }

  public Integer dipEdtSal() {
    return (Integer) storedValueForKey("dipEdtSal");
  }

  public void setDipEdtSal(Integer value) {
    takeStoredValueForKey(value, "dipEdtSal");
  }

  public Integer dispExam() {
    return (Integer) storedValueForKey("dispExam");
  }

  public void setDispExam(Integer value) {
    takeStoredValueForKey(value, "dispExam");
  }

  public Integer dispParCom() {
    return (Integer) storedValueForKey("dispParCom");
  }

  public void setDispParCom(Integer value) {
    takeStoredValueForKey(value, "dispParCom");
  }

  public Integer heureDebImpress() {
    return (Integer) storedValueForKey("heureDebImpress");
  }

  public void setHeureDebImpress(Integer value) {
    takeStoredValueForKey(value, "heureDebImpress");
  }

  public Integer heureFinImpress() {
    return (Integer) storedValueForKey("heureFinImpress");
  }

  public void setHeureFinImpress(Integer value) {
    takeStoredValueForKey(value, "heureFinImpress");
  }

  public Integer intervalleMinimum() {
    return (Integer) storedValueForKey("intervalleMinimum");
  }

  public void setIntervalleMinimum(Integer value) {
    takeStoredValueForKey(value, "intervalleMinimum");
  }

  public Integer pasMailHorsSemaine() {
    return (Integer) storedValueForKey("pasMailHorsSemaine");
  }

  public void setPasMailHorsSemaine(Integer value) {
    takeStoredValueForKey(value, "pasMailHorsSemaine");
  }

  public String selectionGroupeMultiple() {
    return (String) storedValueForKey("selectionGroupeMultiple");
  }

  public void setSelectionGroupeMultiple(String value) {
    takeStoredValueForKey(value, "selectionGroupeMultiple");
  }

  public String sendMailDepositaires() {
    return (String) storedValueForKey("sendMailDepositaires");
  }

  public void setSendMailDepositaires(String value) {
    takeStoredValueForKey(value, "sendMailDepositaires");
  }

  public String sendMailOccupants() {
    return (String) storedValueForKey("sendMailOccupants");
  }

  public void setSendMailOccupants(String value) {
    takeStoredValueForKey(value, "sendMailOccupants");
  }

  public String useRespAp() {
    return (String) storedValueForKey("useRespAp");
  }

  public void setUseRespAp(String value) {
    takeStoredValueForKey(value, "useRespAp");
  }

  public String useTooltipPlanning() {
    return (String) storedValueForKey("useTooltipPlanning");
  }

  public void setUseTooltipPlanning(String value) {
    takeStoredValueForKey(value, "useTooltipPlanning");
  }

  public org.cocktail.superplan.server.metier.IndividuUlr individu() {
    return (org.cocktail.superplan.server.metier.IndividuUlr)storedValueForKey("individu");
  }

  public void setIndividuRelationship(org.cocktail.superplan.server.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.IndividuUlr oldValue = individu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individu");
    }
  }
  

  public static PrefUser createPrefUser(EOEditingContext editingContext, String colorEc
, Integer commentScol
, String defaultTypeResa
, Integer dipEdtPers
, Integer dipEdtSal
, Integer dispExam
, Integer dispParCom
, Integer heureDebImpress
, Integer heureFinImpress
, String selectionGroupeMultiple
, String sendMailDepositaires
, String sendMailOccupants
, String useTooltipPlanning
) {
    PrefUser eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_PrefUser.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _PrefUser.ENTITY_NAME + "' !");
    } else
    {
        eo = (PrefUser) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setColorEc(colorEc);
		eo.setCommentScol(commentScol);
		eo.setDefaultTypeResa(defaultTypeResa);
		eo.setDipEdtPers(dipEdtPers);
		eo.setDipEdtSal(dipEdtSal);
		eo.setDispExam(dispExam);
		eo.setDispParCom(dispParCom);
		eo.setHeureDebImpress(heureDebImpress);
		eo.setHeureFinImpress(heureFinImpress);
		eo.setSelectionGroupeMultiple(selectionGroupeMultiple);
		eo.setSendMailDepositaires(sendMailDepositaires);
		eo.setSendMailOccupants(sendMailOccupants);
		eo.setUseTooltipPlanning(useTooltipPlanning);
    return eo;
  }

  public static NSArray fetchAllPrefUsers(EOEditingContext editingContext) {
    return _PrefUser.fetchAllPrefUsers(editingContext, null);
  }

  public static NSArray fetchAllPrefUsers(EOEditingContext editingContext, NSArray sortOrderings) {
    return _PrefUser.fetchPrefUsers(editingContext, null, sortOrderings);
  }

  public static NSArray fetchPrefUsers(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_PrefUser.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static PrefUser fetchPrefUser(EOEditingContext editingContext, String keyName, Object value) {
    return _PrefUser.fetchPrefUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static PrefUser fetchPrefUser(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _PrefUser.fetchPrefUsers(editingContext, qualifier, null);
    PrefUser eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (PrefUser)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one PrefUser that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static PrefUser fetchRequiredPrefUser(EOEditingContext editingContext, String keyName, Object value) {
    return _PrefUser.fetchRequiredPrefUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static PrefUser fetchRequiredPrefUser(EOEditingContext editingContext, EOQualifier qualifier) {
    PrefUser eoObject = _PrefUser.fetchPrefUser(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no PrefUser that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static PrefUser localInstanceIn(EOEditingContext editingContext, PrefUser eo) {
    PrefUser localInstance = (eo == null) ? null : (PrefUser)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
