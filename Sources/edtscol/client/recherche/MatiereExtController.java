/*
 * Copyright COCKTAIL (www.cocktail.org), 1995, 2011 This software
 * is governed by the CeCILL license under French law and abiding by the
 * rules of distribution of free software. You can use, modify and/or
 * redistribute the software under the terms of the CeCILL license as
 * circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * As a counterpart to the access to the source code and rights to copy, modify
 * and redistribute granted by the license, users are provided only with a
 * limited warranty and the software's author, the holder of the economic
 * rights, and the successive licensors have only limited liability. In this
 * respect, the user's attention is drawn to the risks associated with loading,
 * using, modifying and/or developing or reproducing the software by the user
 * in light of its specific status of free software, that may mean that it
 * is complicated to manipulate, and that also therefore means that it is
 * reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the
 * software's suitability as regards their requirements in conditions enabling
 * the security of their systems and/or data to be ensured and, more generally,
 * to use and operate it in the same conditions as regards security. The
 * fact that you are presently reading this means that you have had knowledge
 * of the CeCILL license and that you accept its terms.
 */
package edtscol.client.recherche;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteEc;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionAp;
import org.cocktail.superplan.client.metier.MaquetteRepartitionEc;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteRepartitionUe;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.MaquetteUe;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.metier.PrefUser;
import org.cocktail.superplan.client.metier.ScolDroitDiplome;
import org.cocktail.superplan.client.metier.ScolFormationFiliere;
import org.cocktail.superplan.client.metier.ScolFormationGrade;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.ScolGroupeObjetElp;
import org.cocktail.superplan.client.metier.VParcoursAp;
import org.cocktail.superplan.client.swing.ComboBoxRendererWithToolTip;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.eointerface.swing.EOView;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import edtscol.client.gestionreservation.InspecteurCtrl;
import edtscol.client.panier.GestionPanier;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.Matrix;

public class MatiereExtController extends EOInterfaceController {

	private static final int CM = 0;
	private static final int TD = 1;
	private static final int TP = 2;
	private static final int TOUT = 3;

	public static final String TOUS_LES_GROUPES = "(TOUS)";
	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public JComboBox comboAnnees, comboAps, comboGroupes, comboGroupesSem, comboParcours, comboSemestres;
	public EODisplayGroup eodHabilitation;
	public EOTable tableHabilitation;
	public JTextField tDiplome, tGrade, lblGrpAp, lblGrpSem;
	public JButton bChoixGroupesAp;
	public EOMatrix matTypeAp, matPrefDip;
	public EOView viewMatiereExt;
	public JCheckBox checkSemPrec;

	private int initType = Recherche.AUTRE;
	private Recherche recherche;
	private EOEditingContext eContext;
	private EOSortOrdering sortDiplome, sortNiveau;
	private JComboListener comboListener;

	private NSArray<ScolGroupeGrp> listGroupesAp = null;
	private NSArray listSelectedGroupesAp = new NSArray();

	public MatiereExtController(EOEditingContext eContext, Recherche owner) {
		super(eContext);
		this.eContext = editingContext();
		this.recherche = owner;
		comboListener = new JComboListener();
		sortDiplome = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		sortNiveau = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FHAB_NIVEAU_KEY, EOSortOrdering.CompareAscending);
	}

	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
	}

	/** retourne la vue pour incorporation dans un autre composant -Recherche- */
	public EOView currentView() {
		return viewMatiereExt;
	}

	/** Permet de changer la disposition du composant alors qu'il est déjà constuit : init() est déjà passé. */
	public void changeDisposition(int disposition) {
		setInitType(disposition);
	}

	public void setInitType(int initType) {
		PrefUser prefUser = app.getPrefUser();
		this.initType = initType;
		if (initType == Recherche.RECHERCHE) {
			checkSemPrec.setToolTipText("Inclure l'EDT de l'autre semestre de l'année.");
			checkSemPrec.setVisible(true);
			checkSemPrec.setSelected(false);

			comboGroupes.setVisible(false);
			bChoixGroupesAp.setVisible(false);

			comboAps.setVisible(false);
			matTypeAp.setVisible(false);
			lblGrpAp.setVisible(false);
			comboGroupesSem.setVisible(true);
		}
		else {
			if (initType == Recherche.SEMESTRE_INSP) {
				checkSemPrec.setVisible(false);
				comboGroupes.setVisible(false);
				bChoixGroupesAp.setVisible(false);
				comboAps.setVisible(false);
				matTypeAp.setVisible(false);
				lblGrpAp.setVisible(false);
				comboSemestres.setVisible(true);
				comboGroupesSem.setVisible(true);
			}
			else {
				checkSemPrec.setVisible(false);
				bChoixGroupesAp.setVisible(prefUser.isEnableMultiGroupSelection());
				comboGroupes.setVisible(!prefUser.isEnableMultiGroupSelection());
				comboAps.setVisible(true);
				matTypeAp.setVisible(true);
				lblGrpAp.setVisible(true);
				comboGroupesSem.setVisible(false);
			}
		}
	}

	public void init() {

		comboAnnees.removeAllItems();
		comboAps.removeAllItems();
		comboParcours.removeAllItems();
		comboSemestres.removeAllItems();
		comboGroupes.removeAllItems();
		comboGroupesSem.removeAllItems();
		WidgetHandler.setTableNotEditable(tableHabilitation);

		Matrix.setSelectedIndex(0, matTypeAp);
		Matrix.setSelectedIndex(0, matPrefDip);
		Matrix.setListener(new LocalMatrixListener(), matTypeAp);
		Matrix.setListener(new PrefMatrixListener(), matPrefDip);

		NSArray<FormationAnnee> formationAnnee = app.getFormationAnnees();
		comboAnnees.removeAllItems();
		FormationAnnee currentWorking = app.getCurrentWorkingAnnee();

		for (int i = 0; i < formationAnnee.count(); i++) {
			FormationAnnee fAnnee = formationAnnee.objectAtIndex(i);
			comboAnnees.addItem(fAnnee);
			if (currentWorking == null && fAnnee.fannCourante().equals("O")) {
				comboAnnees.setSelectedItem(fAnnee);
			}
		}

		if (currentWorking != null) {
			comboAnnees.setSelectedItem(currentWorking);
		}

		comboAnnees.addActionListener(comboListener);

		// ---------------------------------------------------

		PrefUser prefUser = app.getPrefUser();

		ComboBoxRendererWithToolTip cbr = new ComboBoxRendererWithToolTip();

		comboAps.addActionListener(comboListener);
		comboAps.setRenderer(cbr);

		if (!prefUser.isEnableMultiGroupSelection()) {
			comboGroupes.addActionListener(comboListener);
		}
		comboGroupes.setRenderer(cbr);

		comboParcours.addActionListener(comboListener);
		comboParcours.setRenderer(cbr);
		comboSemestres.addActionListener(comboListener);
		comboSemestres.setRenderer(cbr);
		comboGroupesSem.setRenderer(cbr);

		TooltipRenderer tooltipRenderer = new TooltipRenderer();
		TableColumnModel tcm = tableHabilitation.table().getColumnModel();
		TableColumn tc = null;

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tc = tcm.getColumn(i);
			tc.setCellRenderer(tooltipRenderer);
		}

		afficherDiplomesPreferes();
	}

	public void rechargerEnseignements(FormationAnnee fAnnee) {
		comboAnnees.setSelectedItem(fAnnee);
	}

	public void displayGroupDidChangeSelection(EODisplayGroup grp) {
		if (grp == eodHabilitation) {
			FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
			if (selectedHabilitation != null && selectedHabilitation.formationSpecialisation() != null) {
				this.afficherParcours(selectedHabilitation);
				PrefScol prefScol = getPrefScolForHabilitation(selectedHabilitation);
				if (prefScol != null && prefScol.maquetteRepartitionSem() != null) {
					comboParcours.setSelectedItem(prefScol.maquetteRepartitionSem().parcours());
					comboSemestres.setSelectedItem(prefScol.maquetteRepartitionSem().semestre());
				}
			}
		}
	}

	private PrefScol getPrefScolForHabilitation(FormationHabilitation habilitation) {
		if (habilitation == null) {
			return null;
		}
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>(2);
		quals.addObject(new EOKeyValueQualifier(PrefScol.INDIVIDU_KEY, EOKeyValueQualifier.QualifierOperatorEqual, app.userInfo("individu")));
		quals.addObject(new EOKeyValueQualifier(PrefScol.ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, getSelectedFormationAnnee()));
		NSArray<PrefScol> prefScols = habilitation.prefScols(new EOAndQualifier(quals));
		if (prefScols != null && prefScols.count() > 0) {
			return prefScols.objectAtIndex(0);
		}
		return null;
	}

	private void afficherParcours(FormationHabilitation habilitation) {
		comboParcours.removeAllItems();

		if (habilitation == null || habilitation.formationSpecialisation() == null) {
			return;
		}
		EOQualifier qParcours = EOQualifier.qualifierWithQualifierFormat(MaquetteParcours.FORMATION_SPECIALISATION_KEY + " = %@",
				new NSArray<FormationSpecialisation>(habilitation.formationSpecialisation()));

		EOSortOrdering sortParcours = EOSortOrdering.sortOrderingWithKey(MaquetteParcours.MPAR_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		NSArray<MaquetteParcours> parcours = MaquetteParcours.fetchMaquetteParcourses(eContext, qParcours, new NSArray<EOSortOrdering>(sortParcours));

		for (int i = 0; i < parcours.count(); i++) {
			comboParcours.addItem(parcours.objectAtIndex(i));
		}
		NSArray<MaquetteSemestre> semestres = null;
		for (int i = 0; i < comboParcours.getItemCount(); i++) {
			MaquetteParcours mp = (MaquetteParcours) comboParcours.getItemAt(i);
			semestres = getSemestresForParcours(mp, habilitation);
			if (semestres != null && semestres.count() > 0) {
				comboParcours.setSelectedItem(mp);
				break;
			}
		}

		afficherSemestres(semestres);
	}

	private NSArray<MaquetteSemestre> getSemestresForParcours(MaquetteParcours parcours, FormationHabilitation habilitation) {
		if (habilitation == null || parcours == null) {
			return null;
		}
		int niveau = habilitation.fhabNiveau().intValue();
		int ordre1 = niveau * 2 - 1;
		int ordre2 = ordre1 + 1;
		EOQualifier qualSem = EOQualifier.qualifierWithQualifierFormat("(" + MaquetteRepartitionSem.SEMESTRE_KEY + "."
				+ MaquetteSemestre.MSEM_ORDRE_KEY + " = %@ or " + MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY
				+ " = %@) and " + MaquetteRepartitionSem.PARCOURS_KEY + " = %@ and " + MaquetteRepartitionSem.FANN_KEY_KEY + " = %@",
				new NSArray<Object>(new Object[] { new Integer(ordre1), new Integer(ordre2), parcours, habilitation.fannKey() }));
		EOSortOrdering sortSemestre = EOSortOrdering.sortOrderingWithKey(MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		return (NSArray<MaquetteSemestre>) MaquetteRepartitionSem.fetchMaquetteRepartitionSems(eContext, qualSem,
				new NSArray<EOSortOrdering>(sortSemestre)).valueForKey(MaquetteRepartitionSem.SEMESTRE_KEY);
	}

	/** permet d'afficher les semestres du parcours actuellement affiche */
	private void afficherSemestres(NSArray<MaquetteSemestre> semestres) {
		setWaitCursor();

		FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
		MaquetteParcours parcours = (MaquetteParcours) comboParcours.getSelectedItem();
		if (selectedHabilitation == null || parcours == null) {
			return;
		}

		if (semestres == null) {
			semestres = getSemestresForParcours(parcours, selectedHabilitation);
		}

		comboSemestres.removeActionListener(comboListener);
		comboSemestres.removeAllItems();
		for (int i = 0; i < semestres.count(); i++) {
			comboSemestres.addItem(semestres.objectAtIndex(i));
		}
		comboSemestres.addActionListener(comboListener);

		// sélection du semestre en cours...
		NSTimestamp now = new NSTimestamp();
		for (int i = 0; i < semestres.count(); i++) {
			MaquetteSemestre s = semestres.objectAtIndex(i);
			if (s.msemDateDebut() != null && s.msemDateFin() != null) {
				if (now.after(s.msemDateDebut()) && now.before(s.msemDateFin())) {
					comboSemestres.setSelectedItem(s);
					break;
				}
			}
		}

		// mode recherche : on s'arrete au semestre, sinon, on affiche les APs et les groupes des Aps aussi.
		if (initType != Recherche.RECHERCHE) {
			this.afficherAps();
		}

		this.afficherGroupesSemestre();
		setDefaultCursor();
	}

	/** afficherAps */
	public void afficherAps() {
		FormationAnnee selectedAnnee = (FormationAnnee) comboAnnees.getSelectedItem();
		Number fannKey = selectedAnnee.fannKey();
		MaquetteSemestre selectedSemestre = (MaquetteSemestre) comboSemestres.getSelectedItem();

		NSArray<VParcoursAp> requestedAps = new NSArray<VParcoursAp>();

		String mhcoCode = null;
		int typeAp = Matrix.getSelectedIndex(matTypeAp);
		switch (typeAp) {
		case CM:
			mhcoCode = "CM";
			break;
		case TD:
			mhcoCode = "TD";
			break;
		case TP:
			mhcoCode = "TP";
			break;
		case TOUT:
			mhcoCode = null;
			break;
		}

		if (selectedAnnee != null && selectedSemestre != null) {
			EOQualifier qualAp = EOQualifier.qualifierWithQualifierFormat(VParcoursAp.FANN_KEY_KEY + " = %@ and " + VParcoursAp.SEMESTRE_KEY
					+ " = %@", new NSArray<Object>(new Object[] { fannKey, selectedSemestre }));
			NSArray<EOSortOrdering> sortAp = new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(VParcoursAp.AP_KEY + "."
					+ MaquetteAp.MAP_LIBELLE_KEY, EOSortOrdering.CompareAscending));
			EOFetchSpecification fetchSpec = new EOFetchSpecification(VParcoursAp.ENTITY_NAME, qualAp, sortAp);
			fetchSpec.setIsDeep(true);
			fetchSpec.setPrefetchingRelationshipKeyPaths(new NSArray<String>(new String[] { VParcoursAp.AP_KEY, VParcoursAp.EC_KEY }));
			NSArray<VParcoursAp> aps = eContext.objectsWithFetchSpecification(fetchSpec);

			// afficher les aps dont le type est coche dans les cases a cocher de type de l'ap
			if (mhcoCode != null) {
				EOQualifier qSelectionAp = EOQualifier.qualifierWithQualifierFormat(VParcoursAp.AP_KEY + "." + MaquetteAp.MHCO_CODE_KEY + " = '"
						+ mhcoCode + "'", null);
				requestedAps = EOQualifier.filteredArrayWithQualifier(aps, qSelectionAp);
			}
			else {
				requestedAps = aps;
			}
		}

		comboAps.removeActionListener(comboListener);

		comboAps.removeAllItems();
		for (int i = 0; i < requestedAps.count(); i++) {
			comboAps.addItem(requestedAps.objectAtIndex(i));
		}

		this.afficherGroupes();

		comboAps.addActionListener(comboListener);

	}

	/** afficherGroupesSemestre */
	public void afficherGroupesSemestre() {
		MaquetteSemestre selectedSemestre = (MaquetteSemestre) comboSemestres.getSelectedItem();
		NSArray<ScolGroupeGrp> groupesSem = new NSArray<ScolGroupeGrp>();
		if (selectedSemestre != null) {
			EOSortOrdering sortGrp = EOSortOrdering.sortOrderingWithKey(ScolGroupeGrp.GGRP_CODE_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);

			EOQualifier qualifGrp = new EOKeyValueQualifier(ScolGroupeGrp.SCOL_GROUPE_OBJET_ELPS_KEY + "." + ScolGroupeObjetElp.MAQUETTE_AP_KEY + "."
					+ MaquetteAp.MAQUETTE_REPARTITION_APS_KEY + "." + MaquetteRepartitionAp.MAQUETTE_EC_KEY + "."
					+ MaquetteEc.MAQUETTE_REPARTITION_ECS_KEY + "." + MaquetteRepartitionEc.MAQUETTE_UE_KEY + "."
					+ MaquetteUe.MAQUETTE_REPARTITION_UES_KEY + "." + MaquetteRepartitionUe.MAQUETTE_SEMESTRE_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, selectedSemestre);
			EOFetchSpecification fetchSpec = new EOFetchSpecification(ScolGroupeGrp.ENTITY_NAME, qualifGrp, new NSArray<EOSortOrdering>(sortGrp));
			fetchSpec.setIsDeep(true);
			fetchSpec.setUsesDistinct(true);
			groupesSem = eContext.objectsWithFetchSpecification(fetchSpec);
		}
		affecterGroupesSemestre(groupesSem);
	}

	/** afficher les groupes rattaches a l'ap */
	private void afficherGroupes() {
		PrefUser prefUser = app.getPrefUser();
		if (comboAps.getSelectedItem() != null) {
			MaquetteAp selectedAp = ((VParcoursAp) comboAps.getSelectedItem()).ap();
			if (selectedAp != null) {
				listGroupesAp = EnseignementFactory.getGroupesForAp(eContext, selectedAp, null);
				if (!prefUser.isEnableMultiGroupSelection()) {
					affecterGroupes(listGroupesAp);
				}
			}
		}
	}

	/** affecte les groupes au popupButton des groupes */
	private void affecterGroupesSemestre(NSArray<ScolGroupeGrp> groupes) {
		comboGroupesSem.removeAllItems();
		comboGroupesSem.addItem("(Tous)");
		for (int i = 0; i < groupes.count(); i++) {
			comboGroupesSem.addItem(groupes.objectAtIndex(i));
		}
	}

	/** affecte les groupes au popupButton des groupes */
	private void affecterGroupes(NSArray<ScolGroupeGrp> groupes) {
		comboGroupes.removeAllItems();
		comboGroupes.addItem("(Tous)");
		for (int i = 0; i < groupes.count(); i++) {
			comboGroupes.addItem(groupes.objectAtIndex(i));
		}
	}

	private FormationAnnee getSelectedFormationAnnee() {
		return (FormationAnnee) comboAnnees.getSelectedItem();
	}

	private NSArray<PrefScol> getPrefScols() {
		EOQualifier qPrefDipl = EOQualifier.qualifierWithQualifierFormat(PrefScol.INDIVIDU_KEY + " = %@ and " + PrefScol.ANNEE_KEY + " = %@",
				new NSArray<Object>(new Object[] { (IndividuUlr) app.userInfo("individu"), getSelectedFormationAnnee() }));
		return PrefScol.fetchPrefScols(eContext, qPrefDipl, null);
	}

	/**
	 * reaction au changement de selection dans les radioBouton Pref-Tous, affiche les diplomes preferes ou recherche normale
	 */

	private void afficherDiplomesPreferes() {
		if (Matrix.getSelectedIndex(matPrefDip) == 0) {
			eodHabilitation.setDelegate(null);
			NSArray<PrefScol> preferences = getPrefScols();
			eodHabilitation.setSortOrderings(new NSArray<EOSortOrdering>(new EOSortOrdering[] { sortDiplome, sortNiveau }));
			eodHabilitation.setObjectArray((NSArray<FormationHabilitation>) preferences.valueForKey(PrefScol.HABILITATION_KEY));
			eodHabilitation.setSelectedObject(null);
			eodHabilitation.setDelegate(this);
		}
		else {
			chercherDiplome();
		}
		WidgetHandler.informObservingAssociation(eodHabilitation);
		tableHabilitation.table().updateUI();
	}

	private void setWaitCursor() {
		if (initType == Recherche.RECHERCHE) {
			WindowHandler.setWaitCursor(recherche);
		}
		else {
			recherche.inspecteurCtrl();
			if (recherche.inspecteurCtrl() != null && InspecteurCtrl.isOpen()) {
				WindowHandler.setWaitCursor(recherche.inspecteurCtrl().getMyView());
			}
		}
	}

	private void setDefaultCursor() {
		if (initType == Recherche.RECHERCHE) {
			WindowHandler.setDefaultCursor(recherche);
		}
		else {
			if (recherche.inspecteurCtrl() != null && InspecteurCtrl.isOpen()) {
				WindowHandler.setDefaultCursor(recherche.inspecteurCtrl().getMyView());
			}
		}
	}

	/** chercherDiplome */
	public void chercherDiplome() {

		NSArray droits = (NSArray) app.userInfo("droits");

		setWaitCursor();
		eodHabilitation.setDelegate(null);

		String grade = tGrade.getText();
		String diplome = tDiplome.getText();

		NSMutableArray<EOQualifier> sumQualifiers = new NSMutableArray<EOQualifier>();

		if (!diplome.equals("")) {
			NSMutableArray<EOQualifier> orQualifiers = new NSMutableArray<EOQualifier>();
			orQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY,
					EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*" + diplome + "*"));
			orQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_LIBELLE_KEY,
					EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*" + diplome + "*"));
			sumQualifiers.addObject(new EOOrQualifier(orQualifiers));
		}

		if (!grade.equals("")) {
			sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FGRA_CODE_KEY,
					EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*" + grade + "*"));
		}

		sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.FORMATION_ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
				comboAnnees.getSelectedItem()));

		// si edtCreateur : peut voir tous les diplomes
		boolean edtCreateur = ((Boolean) app.userInfo("edtCreateur")).booleanValue();
		if (!edtCreateur && droits.count() > 0) {
			sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.DROIT_DIPLOMES_KEY + "." + ScolDroitDiplome.DLOG_KEY_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, droits.objectAtIndex(0)));
			sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.DROIT_DIPLOMES_KEY + "." + ScolDroitDiplome.DDIP_EDT_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, "A"));
		}

		if (edtCreateur || droits.count() > 0) {
			eodHabilitation.setSortOrderings(new NSArray<EOSortOrdering>(new EOSortOrdering[] { sortDiplome, sortNiveau }));
			DBHandler.fetchDisplayGroup(eodHabilitation, new EOAndQualifier(sumQualifiers), null, false);
			WidgetHandler.selectNoneInDisplayGroup(eodHabilitation);
			eodHabilitation.setDelegate(this);
		}

		tableHabilitation.table().updateUI();

		setDefaultCursor();
	}

	public void selectionnerGroupes(Object sender) {

		GroupeSelector groupeSel = new GroupeSelector((JComponent) sender);

		if (listGroupesAp != null) {
			groupeSel.setListGroupes(listGroupesAp);
			groupeSel.setVisible(true);
			listSelectedGroupesAp = groupeSel.getSelectedGroupes();

			if (groupeSel.isValidate()) {
				recherche.inspecteurCtrl().panier().addResources(selectedRessources());
			}
		}
	}

	/**
	 * Interface des appels de l'exterieur
	 */

	public NSArray selectedRessources() {
		if (initType == Recherche.INSPECTEUR) {
			return selectedRessourcesEnseignement();
		}
		else
			if (initType == Recherche.SEMESTRE_INSP) {
				return selectedRessourcesSemestreGroupes();
			}
			else {
				return new NSArray();
			}
	}

	public NSArray<NSMutableDictionary<String, Object>> selectedRessourcesEnseignement() {
		MaquetteAp selectedAp = null;
		if (comboAps.getSelectedItem() != null) {
			selectedAp = ((VParcoursAp) comboAps.getSelectedItem()).ap();
		}
		if (selectedAp == null) {
			return NSArray.EmptyArray;
		}
		NSMutableArray<NSMutableDictionary<String, Object>> resourcesArray = new NSMutableArray<NSMutableDictionary<String, Object>>();

		//

		PrefUser prefUser = app.getPrefUser();
		if (!prefUser.isEnableMultiGroupSelection()) {
			Object groupe = comboGroupes.getSelectedItem();
			if (groupe != null) {
				listSelectedGroupesAp = new NSArray(groupe);
			}
			else {
				listSelectedGroupesAp = new NSArray();
			}
		}

		if (listSelectedGroupesAp.count() == 0) {
			listSelectedGroupesAp = new NSArray(TOUS_LES_GROUPES);
		}

		for (int i = 0; i < listSelectedGroupesAp.count(); i++) {
			NSMutableDictionary<String, Object> ressource = new NSMutableDictionary<String, Object>();

			Object groupe = listSelectedGroupesAp.objectAtIndex(i);

			ressource.takeValueForKey("ENSEIGNEMENT", "resType");
			ressource.takeValueForKey(selectedAp.toString(), "resLibelle");
			ressource.takeValueForKey(NSKeyValueCoding.NullValue, "resDepos");
			ressource.takeValueForKey(selectedAp, "resRecord");

			// si groupe selectionne
			if (groupe instanceof ScolGroupeGrp) {
				ressource.takeValueForKey(groupe, "resUnite");
			}
			else { // sinon pas de groupe => tous
				ressource.takeValueForKey(TOUS_LES_GROUPES, "resUnite");
			}

			resourcesArray.addObject(ressource);
		}

		return resourcesArray;

		// return new NSArray(ressource);
	}

	// UP 30/09/2009
	// ajout de la gestion de reservations de semestres pour les réunions étudiants-enseignants.
	public NSArray selectedRessourcesSemestreGroupes() {

		NSMutableArray resourcesArray = new NSMutableArray();
		MaquetteSemestre selectedSemestre = (MaquetteSemestre) comboSemestres.getSelectedItem();
		if (selectedSemestre == null) {
			return resourcesArray;
		}

		NSMutableDictionary ressource = new NSMutableDictionary();

		ressource.takeValueForKey("SEMESTRE", "resType");
		ressource.takeValueForKey(selectedSemestre.toString(), "resLibelle");
		ressource.takeValueForKey(NSKeyValueCoding.NullValue, "resDepos");
		ressource.takeValueForKey(selectedSemestre, "resRecord");

		Object groupe = comboGroupesSem.getSelectedItem();
		if (groupe != null) {
			if (groupe instanceof ScolGroupeGrp) {
				ressource.takeValueForKey(groupe, "resUnite");
			}
			else { // sinon pas de groupe => tous
				ressource.takeValueForKey(TOUS_LES_GROUPES, "resUnite");
			}
		}
		resourcesArray.addObject(ressource);
		return resourcesArray;
	}

	/**
	 * permet de valider l'affichage de l'EDT de la ressource selectionnee elle peut etre soit un groupe soit un Semestre
	 */
	public void validerRecherche() {

		Integer type = null;
		StringBuffer libelle = new StringBuffer();

		Object selection = comboGroupesSem.getSelectedItem();

		FormationHabilitation habilitation = (FormationHabilitation) eodHabilitation.selectedObject();

		// s'il y'a un groupe dans le combo, on va visualiser son EDT
		if (selection != null && !(selection instanceof String)) {
			if (!(selection instanceof ScolGroupeGrp)) {
				return;
			}
			ScolGroupeGrp resRecord = (ScolGroupeGrp) selection;
			if (habilitation == null) {
				return;
			}

			FormationSpecialisation formation = habilitation.formationSpecialisation();
			MaquetteParcours parcours = (MaquetteParcours) comboParcours.getSelectedItem();
			MaquetteSemestre semestre = (MaquetteSemestre) comboSemestres.getSelectedItem();
			FormationAnnee annee = (FormationAnnee) comboAnnees.getSelectedItem();

			if (parcours == null || semestre == null || annee == null) {
				return;
			}
			libelle.append("Groupe : " + resRecord.ggrpCode() + " - ");

			if (formation.scolFormationDiplome() != null) {
				FormationDiplome diplome = formation.scolFormationDiplome();

				EOGenericRecord filGrad = ScolFormationGrade.fetchFormationGrade(eContext, ScolFormationGrade.FGRA_CODE_KEY, diplome.fgraCode());
				if (filGrad != null) {
					libelle.append((String) filGrad.valueForKey(ScolFormationGrade.FGRA_ABREVIATION_KEY));
				}
				else {
					filGrad = ScolFormationFiliere.fetchFormationFiliere(eContext, ScolFormationFiliere.FFIL_CODE_KEY, diplome.fgraCode());
					if (filGrad != null) {
						libelle.append((String) filGrad.valueForKey(ScolFormationFiliere.FFIL_ABREVIATION_KEY));
					}
					else {
						libelle.append(diplome.fdipLibelle());
					}
				}

				libelle.append(" " + diplome.fdipAbreviation() + " - ");
			}

			if (formation.fspnAbreviation() != null) {
				libelle.append(formation.fspnAbreviation() + " - ");
			}
			else {
				if (formation.fspnLibelle() != null) {
					libelle.append(formation.fspnLibelle() + " - ");
				}
			}

			libelle.append(parcours.mparLibelle() + " - ");
			libelle.append("Semestre" + semestre.msemOrdre());

			type = new Integer(GestionPanier.GROUPE_SCOL);

			boolean isParcoursCommun = false;
			if (app.getPrefUser().dispParCom() != null && app.getPrefUser().dispParCom().intValue() == 1) {
				isParcoursCommun = true;
			}
			Object[] objets = { type, resRecord, libelle.toString(), annee.fannKey(), comboSemestres.getSelectedItem(), habilitation,
					Boolean.valueOf(isParcoursCommun), Boolean.valueOf(checkSemPrec.isSelected()) };
			Object[] clefs = { "type", "resRecord", "libelle", "anneeScol", "semestre", "formation", "isParcoursCommun", "isAutreSemestre" };

			NSDictionary dico = new NSDictionary(objets, clefs);
			NSNotificationCenter.defaultCenter().postNotification("validerRessource", dico);
		}
		// sinon, on veut afficher l'EDT d'un parcours/semestre
		else {
			if (habilitation == null) {
				return;
			}
			FormationSpecialisation formation = habilitation.formationSpecialisation();
			MaquetteParcours parcours = (MaquetteParcours) comboParcours.getSelectedItem();
			MaquetteSemestre semestre = (MaquetteSemestre) comboSemestres.getSelectedItem();
			FormationAnnee annee = (FormationAnnee) comboAnnees.getSelectedItem();

			if (parcours == null || semestre == null || annee == null) {
				return;
			}

			type = new Integer(GestionPanier.ENSEIGNEMENT);

			ArrayList<String> column1 = app.getAttrColumn1Dipl();
			ArrayList<String> column2 = app.getAttrColumn2Dipl();

			if (column1 != null) {
				Iterator<String> itr = column1.iterator();
				while (itr.hasNext()) {
					String element = itr.next();
					if (!MainClient.VOID_COLUMN.equals(element)) {
						libelle.append(habilitation.valueForKeyPath(element));
						libelle.append(" ");
					}
				}

				if (column2 != null) {
					itr = column2.iterator();
					while (itr.hasNext()) {
						String element = itr.next();
						if (!MainClient.VOID_COLUMN.equals(element)) {
							libelle.append(habilitation.valueForKeyPath(element));
							libelle.append(" ");
						}
					}
				}
			}
			// Standard Scolarix : toutes les infos ont un sens.
			else {
				if (formation.scolFormationDiplome() != null) {
					FormationDiplome diplome = formation.scolFormationDiplome();

					EOGenericRecord filGrad = ScolFormationGrade.fetchFormationGrade(eContext, ScolFormationGrade.FGRA_CODE_KEY, diplome.fgraCode());
					if (filGrad != null) {
						libelle.append((String) filGrad.valueForKey(ScolFormationGrade.FGRA_ABREVIATION_KEY));
					}
					else {
						filGrad = ScolFormationFiliere.fetchFormationFiliere(eContext, ScolFormationFiliere.FFIL_CODE_KEY, diplome.fgraCode());
						if (filGrad != null) {
							libelle.append((String) filGrad.valueForKey(ScolFormationFiliere.FFIL_ABREVIATION_KEY));
						}
						else {
							libelle.append(diplome.fdipLibelle());
						}
					}
					libelle.append(" " + diplome.fdipAbreviation() + " - ");
				}
				if (formation.fspnAbreviation() != null) {
					libelle.append(formation.fspnAbreviation() + " - ");
				}
				else {
					if (formation.fspnLibelle() != null) {
						libelle.append(formation.fspnLibelle() + " - ");
					}
				}
			}

			libelle.append(parcours.mparLibelle() + " - ");
			libelle.append("Semestre" + semestre.msemOrdre());

			if (parcours != null) {
				NSMutableDictionary<String, Object> dico = new NSMutableDictionary<String, Object>();
				dico.takeValueForKey(type, "type");
				dico.takeValueForKey(parcours, "resRecord");
				dico.takeValueForKey(semestre, "semestre");
				dico.takeValueForKey(libelle.toString(), "libelle");
				dico.takeValueForKey(habilitation, "formation");
				dico.takeValueForKey(annee.fannKey(), "anneeScol");
				if (app.getPrefUser().dispParCom() != null && app.getPrefUser().dispParCom().intValue() == 1) {
					dico.takeValueForKey(Boolean.TRUE, "isParcoursCommun");
				}
				else {
					dico.takeValueForKey(Boolean.FALSE, "isParcoursCommun");
				}
				dico.takeValueForKey(Boolean.valueOf(checkSemPrec.isSelected()), "isAutreSemestre");
				dico.takeValueForKey(new Integer(Matrix.getSelectedIndex(matTypeAp)), "typeAp");
				if (comboAps.getSelectedItem() != null) {
					dico.takeValueForKey(((VParcoursAp) comboAps.getSelectedItem()).ap(), "ap");
				}
				// UP temporaire
				// dico.takeValueForKey(comboGroupes.getSelectedItem(), "groupe");
				NSNotificationCenter.defaultCenter().postNotification("validerRessource", dico);
			}
		}

	}

	/** getGrpScolSelectionne modifié à UP le 28/03/2008 */
	public ScolGroupeGrp getGrpScolSelectionne() {

		Object selection = null;
		PrefUser prefUser = app.getPrefUser();
		if (prefUser.isEnableMultiGroupSelection()) {
			if (listSelectedGroupesAp != null && listSelectedGroupesAp.count() > 0) {
				selection = listSelectedGroupesAp.objectAtIndex(0); // pas le choix, on prend le premier
			}
		}
		else {
			selection = comboGroupes.getSelectedItem();
		}

		if (selection != null && (selection instanceof String)) {
			return (ScolGroupeGrp) selection;
		}
		else {
			return null;
		}
	}

	private void anneeSelectionChanged() {
		WindowHandler.setWaitCursor(recherche.component());
		FormationAnnee annee = (FormationAnnee) comboAnnees.getSelectedItem();
		if (!annee.equals(app.getCurrentWorkingAnnee())) {
			app.setCurrentWorkingAnnee(annee);
		}
		afficherDiplomesPreferes();
		WindowHandler.setDefaultCursor(recherche.component());
	}

	/** Class de listener d'un bouton-menu */
	private class JComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboAnnees) {
				anneeSelectionChanged();
			}
			else
				if (e.getSource() == comboParcours) {
					afficherSemestres(null);
				}
				else
					if (e.getSource() == comboSemestres) {
						if (initType == Recherche.RECHERCHE || initType == Recherche.SEMESTRE_INSP) {
							afficherGroupesSemestre();
						}
						else {
							afficherAps();
						}
					}
					else
						if (e.getSource() == comboAps) {
							afficherGroupes();
						}
		}
	}

	/** class d'ecoute du groupe de radioboutons de changement de recherche depositaire/tous */
	private class LocalMatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			afficherAps();
		}
	}

	private class PrefMatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			afficherDiplomesPreferes();
		}
	}

	public class TooltipRenderer extends DefaultTableCellRenderer {

		public TooltipRenderer() {
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (value != null) {
				setToolTipText(value.toString());
			}
			return cell;
		}
	}

}
