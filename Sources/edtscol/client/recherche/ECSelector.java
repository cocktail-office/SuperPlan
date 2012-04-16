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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteEc;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionEc;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteRepartitionUe;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.MaquetteUe;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.metier.ScolDroitDiplome;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import edtscol.client.MainClient;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.Matrix;

public class ECSelector extends EOInterfaceController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public JButton bValider, bFermer;
	public EODisplayGroup eodExamenEntete, eodHabilitation;
	public EOTable tableExamenEntete, tableHabilitation;
	public EOMatrix matPrefDip;

	private EOEditingContext eContext;
	public JComboBox comboEcs, comboParcours, comboSemestres;
	public JTextField tDiplome, tGrade, tNiveau;

	private ExamenController examenController;

	public ECSelector(EOEditingContext eContext, ExamenController examenController, Recherche recherche) {
		super(eContext);
		this.eContext = eContext;
		this.examenController = examenController;
	}

	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);

		FormationAnnee formationAnnee = examenController.getFormationAnnee();
		if (formationAnnee == null) {
			WindowHandler.showError("Il y a un probleme avec l'annee scolaire...");
			fermer();
		}
		// initialise les composants graphiques
		comboEcs.removeAllItems();
		comboSemestres.removeAllItems();
		comboParcours.removeAllItems();

		JComboListener comboListener = new JComboListener();
		comboSemestres.addActionListener(comboListener);
		comboParcours.addActionListener(comboListener);
		comboEcs.addActionListener(comboListener);

		Matrix.setSelectedIndex(0, matPrefDip);
		Matrix.setListener(new PrefMatrixListener(), matPrefDip);
		afficherDiplomesPreferes();
		WidgetHandler.setTableNotEditable(tableExamenEntete);
		WidgetHandler.setTableNotEditable(tableHabilitation);
	}

	/** chercherDiplome */
	public void chercherDiplome() {

		WindowHandler.setWaitCursor(this);

		eodHabilitation.setDelegate(null);

		String grade = tGrade.getText();
		String diplome = tDiplome.getText();
		String niveau = tNiveau.getText();

		new StringBuffer();

		NSMutableArray sumQualifiers = new NSMutableArray();

		if (!diplome.equals("")) {
			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY + " caseInsensitiveLike '*"
					+ diplome + "*'" + " or " + FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY + " caseInsensitiveLike '*"
					+ diplome + "*'", null));
		}

		if (!grade.equals("")) {
			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FGRA_CODE_KEY + " caseInsensitiveLike '*" + grade
					+ "*'", null));
		}

		if (!niveau.equals("")) {
			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat("fhabNiveau = " + niveau, null));
		}

		FormationAnnee formationAnnee = examenController.getFormationAnnee();

		sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FANN_KEY_KEY + " = %@",
				new NSArray(formationAnnee.fannKey())));

		EOSortOrdering sortDiplome = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		EOSortOrdering sortNiveau = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FHAB_NIVEAU_KEY, EOSortOrdering.CompareAscending);
		// si edtCreateur : peut voir tous les diplomes
		boolean edtCreateur = ((Boolean) app.userInfo("edtCreateur")).booleanValue();
		NSArray droits = (NSArray) app.userInfo("droits");
		if (!edtCreateur && droits.count() > 0) {
			Number dlogKey = (Number) droits.objectAtIndex(0);
			EOQualifier qualDroits = EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.DROIT_DIPLOMES_KEY + "."
					+ ScolDroitDiplome.DLOG_KEY_KEY + " = %@", new NSArray(dlogKey));
			sumQualifiers.addObject(qualDroits);
		}

		eodHabilitation.setSortOrderings(new NSArray(new Object[] { sortDiplome, sortNiveau }));
		EOQualifier totalQualifier = new EOAndQualifier(sumQualifiers);
		DBHandler.fetchDisplayGroup(eodHabilitation, totalQualifier, null, false);
		eodHabilitation.setDelegate(this);
		WindowHandler.setDefaultCursor(this);
	}

	public void displayGroupDidChangeSelection(EODisplayGroup grp) {
		if (grp == eodHabilitation) {
			FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
			if (selectedHabilitation != null && selectedHabilitation.formationSpecialisation() != null) {
				this.afficherParcours(selectedHabilitation.formationSpecialisation());
			}
			else {
				comboParcours.removeAllItems();
				comboSemestres.removeAllItems();
				comboEcs.removeAllItems();
			}
		}
	}

	private void afficherParcours(FormationSpecialisation specialisation) {
		comboParcours.removeAllItems();

		EOQualifier qParcours = EOQualifier.qualifierWithQualifierFormat(MaquetteParcours.FORMATION_SPECIALISATION_KEY + " = %@", new NSArray(
				specialisation));

		EOSortOrdering sortParcours = EOSortOrdering.sortOrderingWithKey(MaquetteParcours.MPAR_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);
		NSArray parcours = MaquetteParcours.fetchMaquetteParcourses(eContext, qParcours, new NSArray(sortParcours));

		for (int i = 0; i < parcours.count(); i++) {
			comboParcours.addItem(parcours.objectAtIndex(i));
		}

		afficherSemestres();
	}

	/** permet d'afficher les semestres du parcours actuellement affiche */
	private void afficherSemestres() {

		WindowHandler.setWaitCursor(this);

		FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
		Object parcours = comboParcours.getSelectedItem();

		if (selectedHabilitation == null || parcours == null) {
			return;
		}

		int niveau = selectedHabilitation.fhabNiveau().intValue();
		int ordre1 = niveau * 2 - 1;
		int ordre2 = ordre1 + 1;

		FormationAnnee fAnnee = examenController.getFormationAnnee();

		EOQualifier qualSem = EOQualifier.qualifierWithQualifierFormat("(" + MaquetteRepartitionSem.SEMESTRE_KEY + "."
				+ MaquetteSemestre.MSEM_ORDRE_KEY + " = %@ or " + MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY
				+ " = %@) and " + MaquetteRepartitionSem.PARCOURS_KEY + " = %@ and " + MaquetteRepartitionSem.FANN_KEY_KEY + " = %@", new NSArray(
				new Object[] { new Integer(ordre1), new Integer(ordre2), parcours, fAnnee.fannKey() }));

		NSArray semestres = (NSArray) MaquetteRepartitionSem.fetchMaquetteRepartitionSems(eContext, qualSem, null).valueForKey(
				MaquetteRepartitionSem.SEMESTRE_KEY);

		comboSemestres.removeAllItems();
		for (int i = 0; i < semestres.count(); i++) {
			comboSemestres.addItem(semestres.objectAtIndex(i));
		}
		// mode recherche : on s'arrete au semestre, sinon, on affiche les APs et les groupes des Aps aussi.

		this.afficherEcs();
		WindowHandler.setDefaultCursor(this);
	}

	private void afficherEcs() {
		comboEcs.removeAllItems();
		examenController.getFormationAnnee();
		MaquetteSemestre selectedSemestre = (MaquetteSemestre) comboSemestres.getSelectedItem();
		if (selectedSemestre != null) {
			NSArray repartEc = (NSArray) selectedSemestre.valueForKeyPath(MaquetteSemestre.REPARTITION_UES_KEY + "."
					+ MaquetteRepartitionUe.MAQUETTE_UE_KEY + "." + MaquetteUe.REPARTITION_ECS_KEY);
			for (int j = 0; j < repartEc.count(); j++) {
				NSArray repartitionEc = (NSArray) repartEc.objectAtIndex(j);
				NSArray ecs = (NSArray) repartitionEc.valueForKey(MaquetteRepartitionEc.MAQUETTE_EC_KEY);
				for (int i = 0; i < ecs.count(); i++) {
					comboEcs.addItem(ecs.objectAtIndex(i));
				}
			}
		}
	}

	public void valider(Object sender) {
		NSArray examens = eodExamenEntete.selectedObjects();
		if (comboEcs.getSelectedItem() != null && examens.count() > 0) {

			examenController.affecterRessources((MaquetteEc) comboEcs.getSelectedItem(), examens);
			fermer();
		}
		else {
			WindowHandler.showError("Il faut un EC et un ou plusieurs examens pour valider");
		}
	}

	public void fermer() {
		WindowHandler.closeModal(this);
	}

	/** affiche les examens prevus pour cet EC */
	protected void afficherExamenPourEc() {
		MaquetteEc ec = (MaquetteEc) comboEcs.getSelectedItem();
		if (ec == null) {
			return;
		}

		// EOQualifier qualExam = EOQualifier.qualifierWithQualifierFormat("(" + ExamenEntete.EENT_TRAITE_KEY + " = 0 or "
		// + ExamenEntete.EENT_TRAITE_KEY + " = 3) and " + ExamenEntete.EENT_VALIDITE_KEY + " = 'O' and " + ExamenEntete.EC_KEY + " = %@",
		// new NSArray(ec));
		EOQualifier qualExam = EOQualifier.qualifierWithQualifierFormat("(" + ExamenEntete.EENT_TRAITE_KEY + " = 0 or "
				+ ExamenEntete.EENT_TRAITE_KEY + " = 1 or " + ExamenEntete.EENT_TRAITE_KEY + " = 3) and " + ExamenEntete.EENT_VALIDITE_KEY
				+ " = 'O' and " + ExamenEntete.EC_KEY + " = %@", new NSArray<MaquetteEc>(ec));
		NSArray<ExamenEntete> examens = ExamenEntete.fetchExamenEntetes(eContext, qualExam, null);
		eodExamenEntete.setObjectArray(examens);
		WidgetHandler.informObservingAssociation(eodExamenEntete);
	}

	private class JComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == comboParcours) {
				afficherSemestres();
			}
			else
				if (e.getSource() == comboSemestres) {
					afficherEcs();
				}
				else
					if (e.getSource() == comboEcs) {
						afficherExamenPourEc();
					}
		}
	}

	private void afficherDiplomesPreferes() {
		if (Matrix.getSelectedIndex(matPrefDip) == 0) {
			eodHabilitation.setDelegate(null);

			IndividuUlr utilisateur = (IndividuUlr) app.userInfo("individu");
			FormationAnnee formationAnnee = examenController.getFormationAnnee();
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(PrefScol.INDIVIDU_KEY, EOKeyValueQualifier.QualifierOperatorEqual, utilisateur));
			quals.addObject(new EOKeyValueQualifier(PrefScol.ANNEE_KEY + "." + FormationAnnee.FANN_KEY_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, formationAnnee.fannKey()));
			NSArray preferences = PrefScol.fetchPrefScols(eContext, new EOAndQualifier(quals), null);
			eodHabilitation.setObjectArray((NSArray) preferences.valueForKey(PrefScol.HABILITATION_KEY));
			eodHabilitation.setSelectedObject(null);
			eodHabilitation.setDelegate(this);
		}
		else {
			chercherDiplome();
		}
		WidgetHandler.informObservingAssociation(eodHabilitation);
	}

	public class WindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
		}
	}

	private class PrefMatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			afficherDiplomesPreferes();
		}
	}

}
