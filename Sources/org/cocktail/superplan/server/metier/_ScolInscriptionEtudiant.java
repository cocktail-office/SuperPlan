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

// DO NOT EDIT.  Make changes to ScolInscriptionEtudiant.java instead.
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
import com.webobjects.foundation.NSTimestamp;

public abstract class _ScolInscriptionEtudiant extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolInscriptionEtudiant";
	public static final String ENTITY_TABLE_NAME = "SCOLARITE.SCOL_INSCRIPTION_ETUDIANT";

	// Attributes

	public static final String ADR_CIVILITE_KEY = "adrCivilite";
	public static final String ADR_NOM_KEY = "adrNom";
	public static final String ADR_ORDRE_PARENT_KEY = "adrOrdreParent";
	public static final String ADR_ORDRE_SCOL_KEY = "adrOrdreScol";
	public static final String ADR_PRENOM_KEY = "adrPrenom";
	public static final String ADR_PRENOM2_KEY = "adrPrenom2";
	public static final String CPT_ORDRE_KEY = "cptOrdre";
	public static final String ETUD_CODE_INE_KEY = "etudCodeIne";
	public static final String ETUD_NOM_MARITAL_KEY = "etudNomMarital";
	public static final String ETUD_NUMERO_KEY = "etudNumero";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String FDIP_ABREVIATION_KEY = "fdipAbreviation";
	public static final String FDIP_CODE_KEY = "fdipCode";
	public static final String FDIP_MENTION_KEY = "fdipMention";
	public static final String FDIP_SPECIALITE_KEY = "fdipSpecialite";
	public static final String FDOM_CODE_KEY = "fdomCode";
	public static final String FGRA_CODE_KEY = "fgraCode";
	public static final String FSPN_KEY_KEY = "fspnKey";
	public static final String FSPN_LIBELLE_KEY = "fspnLibelle";
	public static final String HIST_NUMERO_KEY = "histNumero";
	public static final String IDIPL_ANNEE_SUIVIE_KEY = "idiplAnneeSuivie";
	public static final String IDIPL_DATE_DEMISSION_KEY = "idiplDateDemission";
	public static final String IDIPL_DATE_INSC_KEY = "idiplDateInsc";
	public static final String IDIPL_PASSAGE_CONDITIONNEL_KEY = "idiplPassageConditionnel";
	public static final String IDIPL_REDOUBLANT_KEY = "idiplRedoublant";
	public static final String IDIPL_TYPE_INSCRIPTION_KEY = "idiplTypeInscription";
	public static final String IETUD_BASE_KEY = "ietudBase";
	public static final String IETUD_ETAT_KEY = "ietudEtat";
	public static final String IETUD_KEY_KEY = "ietudKey";
	public static final String IETUD_MENTION1_KEY = "ietudMention1";
	public static final String IETUD_MENTION2_KEY = "ietudMention2";
	public static final String IETUD_MOYENNE1_KEY = "ietudMoyenne1";
	public static final String IETUD_MOYENNE2_KEY = "ietudMoyenne2";
	public static final String IETUD_PONDERATION_KEY = "ietudPonderation";
	public static final String IETUD_POURCENTAGE1_KEY = "ietudPourcentage1";
	public static final String IETUD_POURCENTAGE2_KEY = "ietudPourcentage2";
	public static final String IETUD_RANG_KEY = "ietudRang";
	public static final String MPAR_KEY_KEY = "mparKey";
	public static final String NO_INDIVIDU_KEY = "noIndividu";
	public static final String PERS_ID_KEY = "persId";
	public static final String RES_CODE_KEY = "resCode";

	public static final String ADR_CIVILITE_COLKEY = "ADR_CIVILITE";
	public static final String ADR_NOM_COLKEY = "ADR_NOM";
	public static final String ADR_ORDRE_PARENT_COLKEY = "ADR_ORDRE_PARENT";
	public static final String ADR_ORDRE_SCOL_COLKEY = "ADR_ORDRE_SCOL";
	public static final String ADR_PRENOM_COLKEY = "ADR_PRENOM";
	public static final String ADR_PRENOM2_COLKEY = "ADR_PRENOM2";
	public static final String CPT_ORDRE_COLKEY = "CPT_ORDRE";
	public static final String ETUD_CODE_INE_COLKEY = "ETUD_CODE_INE";
	public static final String ETUD_NOM_MARITAL_COLKEY = "ETUD_NOM_MARITAL";
	public static final String ETUD_NUMERO_COLKEY = "ETUD_NUMERO";
	public static final String FANN_KEY_COLKEY = "FANN_KEY";
	public static final String FDIP_ABREVIATION_COLKEY = "FDIP_ABREVIATION";
	public static final String FDIP_CODE_COLKEY = "FDIP_CODE";
	public static final String FDIP_MENTION_COLKEY = "FDIP_MENTION";
	public static final String FDIP_SPECIALITE_COLKEY = "FDIP_SPECIALITE";
	public static final String FDOM_CODE_COLKEY = "FDOM_CODE";
	public static final String FGRA_CODE_COLKEY = "FGRA_CODE";
	public static final String FSPN_KEY_COLKEY = "FSPN_KEY";
	public static final String FSPN_LIBELLE_COLKEY = "FSPN_LIBELLE";
	public static final String HIST_NUMERO_COLKEY = "HIST_NUMERO";
	public static final String IDIPL_ANNEE_SUIVIE_COLKEY = "IDIPL_ANNEE_SUIVIE";
	public static final String IDIPL_DATE_DEMISSION_COLKEY = "IDIPL_DATE_DEMISSION";
	public static final String IDIPL_DATE_INSC_COLKEY = "IDIPL_DATE_INSC";
	public static final String IDIPL_PASSAGE_CONDITIONNEL_COLKEY = "IDIPL_PASSAGE_CONDITIONNEL";
	public static final String IDIPL_REDOUBLANT_COLKEY = "IDIPL_REDOUBLANT";
	public static final String IDIPL_TYPE_INSCRIPTION_COLKEY = "IDIPL_TYPE_INSCRIPTION";
	public static final String IETUD_BASE_COLKEY = "IETUD_BASE";
	public static final String IETUD_ETAT_COLKEY = "IETUD_ETAT";
	public static final String IETUD_KEY_COLKEY = "IETUD_KEY";
	public static final String IETUD_MENTION1_COLKEY = "IETUD_MENTION1";
	public static final String IETUD_MENTION2_COLKEY = "IETUD_MENTION2";
	public static final String IETUD_MOYENNE1_COLKEY = "IETUD_MOYENNE1";
	public static final String IETUD_MOYENNE2_COLKEY = "IETUD_MOYENNE2";
	public static final String IETUD_PONDERATION_COLKEY = "IETUD_PONDERATION";
	public static final String IETUD_POURCENTAGE1_COLKEY = "IETUD_POURCENTAGE1";
	public static final String IETUD_POURCENTAGE2_COLKEY = "IETUD_POURCENTAGE2";
	public static final String IETUD_RANG_COLKEY = "IETUD_RANG";
	public static final String MPAR_KEY_COLKEY = "MPAR_KEY";
	public static final String NO_INDIVIDU_COLKEY = "NO_INDIVIDU";
	public static final String PERS_ID_COLKEY = "PERS_ID";
	public static final String RES_CODE_COLKEY = "RES_CODE";

	// Relationships
	public static final String FORMATION_GRADE_KEY = "formationGrade";
	public static final String FORMATION_SPECIALISATION_KEY = "formationSpecialisation";
	public static final String INDIVIDU_ULR_KEY = "individuUlr";
	public static final String SCOL_INSCRIPTION_APS_KEY = "scolInscriptionAps";
	public static final String SCOL_INSCRIPTION_EXAMENS_KEY = "scolInscriptionExamens";
	public static final String SCOL_INSCRIPTION_GRPS_KEY = "scolInscriptionGrps";
	public static final String SCOL_INSCRIPTION_SEMESTRES_KEY = "scolInscriptionSemestres";

	// Utilities methods
	  public ScolInscriptionEtudiant localInstanceIn(EOEditingContext editingContext) {
	    ScolInscriptionEtudiant localInstance = (ScolInscriptionEtudiant)EOUtilities.localInstanceOfObject(editingContext, this);
	    if (localInstance == null) {
	      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
	    }
	    return localInstance;
	  }

	
			public static ScolInscriptionEtudiant getInstance(EOEditingContext editingContext) {
			  EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(_ScolInscriptionEtudiant.ENTITY_NAME);
		      return (ScolInscriptionEtudiant) descriptionClass.createInstanceWithEditingContext(editingContext, null);
		}
		
	// Accessors methods
  public String adrCivilite() {
    return (String) storedValueForKey("adrCivilite");
  }

  public void setAdrCivilite(String value) {
    takeStoredValueForKey(value, "adrCivilite");
  }

  public String adrNom() {
    return (String) storedValueForKey("adrNom");
  }

  public void setAdrNom(String value) {
    takeStoredValueForKey(value, "adrNom");
  }

  public Long adrOrdreParent() {
    return (Long) storedValueForKey("adrOrdreParent");
  }

  public void setAdrOrdreParent(Long value) {
    takeStoredValueForKey(value, "adrOrdreParent");
  }

  public Long adrOrdreScol() {
    return (Long) storedValueForKey("adrOrdreScol");
  }

  public void setAdrOrdreScol(Long value) {
    takeStoredValueForKey(value, "adrOrdreScol");
  }

  public String adrPrenom() {
    return (String) storedValueForKey("adrPrenom");
  }

  public void setAdrPrenom(String value) {
    takeStoredValueForKey(value, "adrPrenom");
  }

  public String adrPrenom2() {
    return (String) storedValueForKey("adrPrenom2");
  }

  public void setAdrPrenom2(String value) {
    takeStoredValueForKey(value, "adrPrenom2");
  }

  public Integer cptOrdre() {
    return (Integer) storedValueForKey("cptOrdre");
  }

  public void setCptOrdre(Integer value) {
    takeStoredValueForKey(value, "cptOrdre");
  }

  public String etudCodeIne() {
    return (String) storedValueForKey("etudCodeIne");
  }

  public void setEtudCodeIne(String value) {
    takeStoredValueForKey(value, "etudCodeIne");
  }

  public String etudNomMarital() {
    return (String) storedValueForKey("etudNomMarital");
  }

  public void setEtudNomMarital(String value) {
    takeStoredValueForKey(value, "etudNomMarital");
  }

  public Integer etudNumero() {
    return (Integer) storedValueForKey("etudNumero");
  }

  public void setEtudNumero(Integer value) {
    takeStoredValueForKey(value, "etudNumero");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    takeStoredValueForKey(value, "fannKey");
  }

  public String fdipAbreviation() {
    return (String) storedValueForKey("fdipAbreviation");
  }

  public void setFdipAbreviation(String value) {
    takeStoredValueForKey(value, "fdipAbreviation");
  }

  public String fdipCode() {
    return (String) storedValueForKey("fdipCode");
  }

  public void setFdipCode(String value) {
    takeStoredValueForKey(value, "fdipCode");
  }

  public String fdipMention() {
    return (String) storedValueForKey("fdipMention");
  }

  public void setFdipMention(String value) {
    takeStoredValueForKey(value, "fdipMention");
  }

  public String fdipSpecialite() {
    return (String) storedValueForKey("fdipSpecialite");
  }

  public void setFdipSpecialite(String value) {
    takeStoredValueForKey(value, "fdipSpecialite");
  }

  public String fdomCode() {
    return (String) storedValueForKey("fdomCode");
  }

  public void setFdomCode(String value) {
    takeStoredValueForKey(value, "fdomCode");
  }

  public String fgraCode() {
    return (String) storedValueForKey("fgraCode");
  }

  public void setFgraCode(String value) {
    takeStoredValueForKey(value, "fgraCode");
  }

  public Integer fspnKey() {
    return (Integer) storedValueForKey("fspnKey");
  }

  public void setFspnKey(Integer value) {
    takeStoredValueForKey(value, "fspnKey");
  }

  public String fspnLibelle() {
    return (String) storedValueForKey("fspnLibelle");
  }

  public void setFspnLibelle(String value) {
    takeStoredValueForKey(value, "fspnLibelle");
  }

  public Long histNumero() {
    return (Long) storedValueForKey("histNumero");
  }

  public void setHistNumero(Long value) {
    takeStoredValueForKey(value, "histNumero");
  }

  public Long idiplAnneeSuivie() {
    return (Long) storedValueForKey("idiplAnneeSuivie");
  }

  public void setIdiplAnneeSuivie(Long value) {
    takeStoredValueForKey(value, "idiplAnneeSuivie");
  }

  public NSTimestamp idiplDateDemission() {
    return (NSTimestamp) storedValueForKey("idiplDateDemission");
  }

  public void setIdiplDateDemission(NSTimestamp value) {
    takeStoredValueForKey(value, "idiplDateDemission");
  }

  public NSTimestamp idiplDateInsc() {
    return (NSTimestamp) storedValueForKey("idiplDateInsc");
  }

  public void setIdiplDateInsc(NSTimestamp value) {
    takeStoredValueForKey(value, "idiplDateInsc");
  }

  public String idiplPassageConditionnel() {
    return (String) storedValueForKey("idiplPassageConditionnel");
  }

  public void setIdiplPassageConditionnel(String value) {
    takeStoredValueForKey(value, "idiplPassageConditionnel");
  }

  public String idiplRedoublant() {
    return (String) storedValueForKey("idiplRedoublant");
  }

  public void setIdiplRedoublant(String value) {
    takeStoredValueForKey(value, "idiplRedoublant");
  }

  public Integer idiplTypeInscription() {
    return (Integer) storedValueForKey("idiplTypeInscription");
  }

  public void setIdiplTypeInscription(Integer value) {
    takeStoredValueForKey(value, "idiplTypeInscription");
  }

  public java.math.BigDecimal ietudBase() {
    return (java.math.BigDecimal) storedValueForKey("ietudBase");
  }

  public void setIetudBase(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "ietudBase");
  }

  public Integer ietudEtat() {
    return (Integer) storedValueForKey("ietudEtat");
  }

  public void setIetudEtat(Integer value) {
    takeStoredValueForKey(value, "ietudEtat");
  }

  public Integer ietudKey() {
    return (Integer) storedValueForKey("ietudKey");
  }

  public void setIetudKey(Integer value) {
    takeStoredValueForKey(value, "ietudKey");
  }

  public Integer ietudMention1() {
    return (Integer) storedValueForKey("ietudMention1");
  }

  public void setIetudMention1(Integer value) {
    takeStoredValueForKey(value, "ietudMention1");
  }

  public Integer ietudMention2() {
    return (Integer) storedValueForKey("ietudMention2");
  }

  public void setIetudMention2(Integer value) {
    takeStoredValueForKey(value, "ietudMention2");
  }

  public java.math.BigDecimal ietudMoyenne1() {
    return (java.math.BigDecimal) storedValueForKey("ietudMoyenne1");
  }

  public void setIetudMoyenne1(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "ietudMoyenne1");
  }

  public java.math.BigDecimal ietudMoyenne2() {
    return (java.math.BigDecimal) storedValueForKey("ietudMoyenne2");
  }

  public void setIetudMoyenne2(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "ietudMoyenne2");
  }

  public java.math.BigDecimal ietudPonderation() {
    return (java.math.BigDecimal) storedValueForKey("ietudPonderation");
  }

  public void setIetudPonderation(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "ietudPonderation");
  }

  public java.math.BigDecimal ietudPourcentage1() {
    return (java.math.BigDecimal) storedValueForKey("ietudPourcentage1");
  }

  public void setIetudPourcentage1(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "ietudPourcentage1");
  }

  public java.math.BigDecimal ietudPourcentage2() {
    return (java.math.BigDecimal) storedValueForKey("ietudPourcentage2");
  }

  public void setIetudPourcentage2(java.math.BigDecimal value) {
    takeStoredValueForKey(value, "ietudPourcentage2");
  }

  public Integer ietudRang() {
    return (Integer) storedValueForKey("ietudRang");
  }

  public void setIetudRang(Integer value) {
    takeStoredValueForKey(value, "ietudRang");
  }

  public Integer mparKey() {
    return (Integer) storedValueForKey("mparKey");
  }

  public void setMparKey(Integer value) {
    takeStoredValueForKey(value, "mparKey");
  }

  public Integer noIndividu() {
    return (Integer) storedValueForKey("noIndividu");
  }

  public void setNoIndividu(Integer value) {
    takeStoredValueForKey(value, "noIndividu");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    takeStoredValueForKey(value, "persId");
  }

  public String resCode() {
    return (String) storedValueForKey("resCode");
  }

  public void setResCode(String value) {
    takeStoredValueForKey(value, "resCode");
  }

  public org.cocktail.superplan.server.metier.ScolFormationGrade formationGrade() {
    return (org.cocktail.superplan.server.metier.ScolFormationGrade)storedValueForKey("formationGrade");
  }

  public void setFormationGradeRelationship(org.cocktail.superplan.server.metier.ScolFormationGrade value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.ScolFormationGrade oldValue = formationGrade();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationGrade");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationGrade");
    }
  }
  
  public org.cocktail.superplan.server.metier.FormationSpecialisation formationSpecialisation() {
    return (org.cocktail.superplan.server.metier.FormationSpecialisation)storedValueForKey("formationSpecialisation");
  }

  public void setFormationSpecialisationRelationship(org.cocktail.superplan.server.metier.FormationSpecialisation value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.FormationSpecialisation oldValue = formationSpecialisation();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "formationSpecialisation");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "formationSpecialisation");
    }
  }
  
  public org.cocktail.superplan.server.metier.IndividuUlr individuUlr() {
    return (org.cocktail.superplan.server.metier.IndividuUlr)storedValueForKey("individuUlr");
  }

  public void setIndividuUlrRelationship(org.cocktail.superplan.server.metier.IndividuUlr value) {
    if (value == null) {
    	org.cocktail.superplan.server.metier.IndividuUlr oldValue = individuUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individuUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individuUlr");
    }
  }
  
  public NSArray scolInscriptionAps() {
    return (NSArray)storedValueForKey("scolInscriptionAps");
  }

  public NSArray scolInscriptionAps(EOQualifier qualifier) {
    return scolInscriptionAps(qualifier, null);
  }

  public NSArray scolInscriptionAps(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = scolInscriptionAps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolInscriptionApsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionAp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolInscriptionAps");
  }

  public void removeFromScolInscriptionApsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionAps");
  }

  public org.cocktail.superplan.server.metier.ScolInscriptionAp createScolInscriptionApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolInscriptionAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolInscriptionAps");
    return (org.cocktail.superplan.server.metier.ScolInscriptionAp) eo;
  }

  public void deleteScolInscriptionApsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolInscriptionApsRelationships() {
    Enumeration objects = scolInscriptionAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolInscriptionApsRelationship((org.cocktail.superplan.server.metier.ScolInscriptionAp)objects.nextElement());
    }
  }

  public NSArray scolInscriptionExamens() {
    return (NSArray)storedValueForKey("scolInscriptionExamens");
  }

  public NSArray scolInscriptionExamens(EOQualifier qualifier) {
    return scolInscriptionExamens(qualifier, null);
  }

  public NSArray scolInscriptionExamens(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = scolInscriptionExamens();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolInscriptionExamensRelationship(org.cocktail.superplan.server.metier.ScolInscriptionExamen object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolInscriptionExamens");
  }

  public void removeFromScolInscriptionExamensRelationship(org.cocktail.superplan.server.metier.ScolInscriptionExamen object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionExamens");
  }

  public org.cocktail.superplan.server.metier.ScolInscriptionExamen createScolInscriptionExamensRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolInscriptionExamen");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolInscriptionExamens");
    return (org.cocktail.superplan.server.metier.ScolInscriptionExamen) eo;
  }

  public void deleteScolInscriptionExamensRelationship(org.cocktail.superplan.server.metier.ScolInscriptionExamen object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionExamens");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolInscriptionExamensRelationships() {
    Enumeration objects = scolInscriptionExamens().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolInscriptionExamensRelationship((org.cocktail.superplan.server.metier.ScolInscriptionExamen)objects.nextElement());
    }
  }

  public NSArray scolInscriptionGrps() {
    return (NSArray)storedValueForKey("scolInscriptionGrps");
  }

  public NSArray scolInscriptionGrps(EOQualifier qualifier) {
    return scolInscriptionGrps(qualifier, null, false);
  }

  public NSArray scolInscriptionGrps(EOQualifier qualifier, boolean fetch) {
    return scolInscriptionGrps(qualifier, null, fetch);
  }

  public NSArray scolInscriptionGrps(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
    NSArray results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.superplan.server.metier.ScolInscriptionGrp.SCOL_INSCRIPTION_ETUDIANT_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.superplan.server.metier.ScolInscriptionGrp.fetchScolInscriptionGrps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = scolInscriptionGrps();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToScolInscriptionGrpsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionGrp object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolInscriptionGrps");
  }

  public void removeFromScolInscriptionGrpsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionGrp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionGrps");
  }

  public org.cocktail.superplan.server.metier.ScolInscriptionGrp createScolInscriptionGrpsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolInscriptionGrp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolInscriptionGrps");
    return (org.cocktail.superplan.server.metier.ScolInscriptionGrp) eo;
  }

  public void deleteScolInscriptionGrpsRelationship(org.cocktail.superplan.server.metier.ScolInscriptionGrp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionGrps");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolInscriptionGrpsRelationships() {
    Enumeration objects = scolInscriptionGrps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolInscriptionGrpsRelationship((org.cocktail.superplan.server.metier.ScolInscriptionGrp)objects.nextElement());
    }
  }

  public NSArray scolInscriptionSemestres() {
    return (NSArray)storedValueForKey("scolInscriptionSemestres");
  }

  public NSArray scolInscriptionSemestres(EOQualifier qualifier) {
    return scolInscriptionSemestres(qualifier, null);
  }

  public NSArray scolInscriptionSemestres(EOQualifier qualifier, NSArray sortOrderings) {
    NSArray results;
      results = scolInscriptionSemestres();
      if (qualifier != null) {
        results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolInscriptionSemestresRelationship(org.cocktail.superplan.server.metier.ScolInscriptionSemestre object) {
    addObjectToBothSidesOfRelationshipWithKey(object, "scolInscriptionSemestres");
  }

  public void removeFromScolInscriptionSemestresRelationship(org.cocktail.superplan.server.metier.ScolInscriptionSemestre object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionSemestres");
  }

  public org.cocktail.superplan.server.metier.ScolInscriptionSemestre createScolInscriptionSemestresRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolInscriptionSemestre");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolInscriptionSemestres");
    return (org.cocktail.superplan.server.metier.ScolInscriptionSemestre) eo;
  }

  public void deleteScolInscriptionSemestresRelationship(org.cocktail.superplan.server.metier.ScolInscriptionSemestre object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolInscriptionSemestres");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolInscriptionSemestresRelationships() {
    Enumeration objects = scolInscriptionSemestres().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolInscriptionSemestresRelationship((org.cocktail.superplan.server.metier.ScolInscriptionSemestre)objects.nextElement());
    }
  }


  public static ScolInscriptionEtudiant createScolInscriptionEtudiant(EOEditingContext editingContext, Integer etudNumero
, Integer fannKey
, Long histNumero
, String idiplPassageConditionnel
, String idiplRedoublant
, Integer ietudEtat
, Integer ietudMention1
, Integer ietudMention2
, java.math.BigDecimal ietudPonderation
, java.math.BigDecimal ietudPourcentage1
, java.math.BigDecimal ietudPourcentage2
) {
    ScolInscriptionEtudiant eo = null;
    EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_ScolInscriptionEtudiant.ENTITY_NAME);
    if(classDescription == null)
    {
        throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _ScolInscriptionEtudiant.ENTITY_NAME + "' !");
    } else
    {
        eo = (ScolInscriptionEtudiant) classDescription.createInstanceWithEditingContext(editingContext, null);
        editingContext.insertObject(eo);
    }
    
		eo.setEtudNumero(etudNumero);
		eo.setFannKey(fannKey);
		eo.setHistNumero(histNumero);
		eo.setIdiplPassageConditionnel(idiplPassageConditionnel);
		eo.setIdiplRedoublant(idiplRedoublant);
		eo.setIetudEtat(ietudEtat);
		eo.setIetudMention1(ietudMention1);
		eo.setIetudMention2(ietudMention2);
		eo.setIetudPonderation(ietudPonderation);
		eo.setIetudPourcentage1(ietudPourcentage1);
		eo.setIetudPourcentage2(ietudPourcentage2);
    return eo;
  }

  public static NSArray fetchAllScolInscriptionEtudiants(EOEditingContext editingContext) {
    return _ScolInscriptionEtudiant.fetchAllScolInscriptionEtudiants(editingContext, null);
  }

  public static NSArray fetchAllScolInscriptionEtudiants(EOEditingContext editingContext, NSArray sortOrderings) {
    return _ScolInscriptionEtudiant.fetchScolInscriptionEtudiants(editingContext, null, sortOrderings);
  }

  public static NSArray fetchScolInscriptionEtudiants(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolInscriptionEtudiant.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray eoObjects = (NSArray)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolInscriptionEtudiant fetchScolInscriptionEtudiant(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolInscriptionEtudiant.fetchScolInscriptionEtudiant(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolInscriptionEtudiant fetchScolInscriptionEtudiant(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray eoObjects = _ScolInscriptionEtudiant.fetchScolInscriptionEtudiants(editingContext, qualifier, null);
    ScolInscriptionEtudiant eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolInscriptionEtudiant)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolInscriptionEtudiant that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolInscriptionEtudiant fetchRequiredScolInscriptionEtudiant(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolInscriptionEtudiant.fetchRequiredScolInscriptionEtudiant(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolInscriptionEtudiant fetchRequiredScolInscriptionEtudiant(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolInscriptionEtudiant eoObject = _ScolInscriptionEtudiant.fetchScolInscriptionEtudiant(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolInscriptionEtudiant that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolInscriptionEtudiant localInstanceIn(EOEditingContext editingContext, ScolInscriptionEtudiant eo) {
    ScolInscriptionEtudiant localInstance = (eo == null) ? null : (ScolInscriptionEtudiant)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
