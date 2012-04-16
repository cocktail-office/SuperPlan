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
package edtscol.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;

import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.MaquetteEc;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.metier.ResaCouleurEc;
import org.cocktail.superplan.client.metier.VParcoursEc;
import org.cocktail.superplan.server.metier.ScolDroitDiplome;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EODialogController;
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

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.GenericListHandler;
import fr.univlr.utilities.Matrix;

public class ECColorController extends EOInterfaceController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public JButton bColor, bFermer;
	public JComboBox comboAnnees, comboParcours, comboSemestre;
	public EODisplayGroup eodHabilitation;
	public GenericListHandler listEC;
	public EOMatrix matPrefDip;
	public EOTable tableHabilitation;
	public JTextField tDiplome, tGrade;

	private EOEditingContext eContext;
	private EOSortOrdering sortDiplome, sortNiveau;

	public ECColorController(EOEditingContext eContext, MainInterface main) {
		super(eContext);
		this.eContext = eContext;
	}

	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		init();
		initWidgets();
		JComboListener comboListener = new JComboListener();
		comboSemestre.addActionListener(comboListener);
		comboParcours.addActionListener(comboListener);

	}

	private void init() {
		sortDiplome = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		sortNiveau = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FHAB_NIVEAU_KEY, EOSortOrdering.CompareAscending);

		NSArray formationAnnee = app.getFormationAnnees();
		comboAnnees.removeAllItems();
		for (int i = 0; i < formationAnnee.count(); i++) {
			FormationAnnee fAnnee = (FormationAnnee) formationAnnee.objectAtIndex(i);
			comboAnnees.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnees.setSelectedItem(fAnnee);
			}
		}

		Matrix.setSelectedIndex(0, matPrefDip);
		Matrix.setListener(new PrefMatrixListener(), matPrefDip);
		afficherPreferences();
	}

	public void displayGroupDidChangeSelection(EODisplayGroup group) {
		if (group == eodHabilitation) {
			clean();
			FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
			if (selectedHabilitation != null && selectedHabilitation.formationSpecialisation() != null) {
				this.afficherParcours(selectedHabilitation.formationSpecialisation());
				PrefScol prefScol = getPrefScolForHabilitation(selectedHabilitation);
				if (prefScol != null && prefScol.maquetteRepartitionSem() != null) {
					comboParcours.setSelectedItem(prefScol.maquetteRepartitionSem().parcours());
					comboSemestre.setSelectedItem(prefScol.maquetteRepartitionSem().semestre());
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
		quals.addObject(new EOKeyValueQualifier(PrefScol.ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, comboAnnees.getSelectedItem()));
		NSArray<PrefScol> prefScols = habilitation.prefScols(new EOAndQualifier(quals));
		if (prefScols != null && prefScols.count() > 0) {
			return prefScols.objectAtIndex(0);
		}
		return null;
	}

	private void afficherParcours(FormationSpecialisation specialisation) {
		comboParcours.removeAllItems();

		EOQualifier qParcours = EOQualifier.qualifierWithQualifierFormat(MaquetteParcours.FORMATION_SPECIALISATION_KEY + " = %@", new NSArray(
				specialisation));

		EOSortOrdering sortParcours = EOSortOrdering.sortOrderingWithKey(MaquetteParcours.MPAR_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		NSArray parcours = DBHandler.fetchData(eContext, MaquetteParcours.ENTITY_NAME, qParcours, sortParcours);

		for (int i = 0; i < parcours.count(); i++) {
			comboParcours.addItem(parcours.objectAtIndex(i));
		}

		afficherSemestres();
	}

	public void afficherPreferences() {
		if (Matrix.getSelectedIndex(matPrefDip) == 0) {
			eodHabilitation.setDelegate(null);
			eodHabilitation.setSortOrderings(new NSArray(new Object[] { sortDiplome, sortNiveau }));
			NSArray objets = app.getPreferencesHabilitations((FormationAnnee) comboAnnees.getSelectedItem());
			eodHabilitation.setObjectArray(objets);
			WidgetHandler.selectNoneInDisplayGroup(eodHabilitation);
			eodHabilitation.setDelegate(this);
			if (eodHabilitation.displayedObjects().count() == 0) {
				clean();
			}
		}
		else {
			rechercherEC();
		}
		WidgetHandler.informObservingAssociation(eodHabilitation);
	}

	private void clean() {
		comboParcours.removeAllItems();
		comboSemestre.removeAllItems();
	}

	/** initialise les composants graphiques de la fenetre */
	public void initWidgets() {

		comboSemestre.removeAllItems();
		comboParcours.removeAllItems();

		WidgetHandler.decorateButton(null, "Fermer", "cancel", bFermer);
		WidgetHandler.decorateButton(null, null, "color", bColor);

		WidgetHandler.setTableNotEditable(tableHabilitation);
		listEC.getJList().setCellRenderer(new ECColorCellRenderer());
	}

	public void choisirCouleur(Object sender) {

		Color ecColor = JColorChooser.showDialog(component(), "Choisir la couleur de l'EC selectionnée", component().getBackground());

		if (ecColor == null) {
			return;
		}

		String r = Integer.toHexString(ecColor.getRed());
		String g = Integer.toHexString(ecColor.getGreen());
		String b = Integer.toHexString(ecColor.getBlue());

		if (r.length() < 2) {
			r = "0".concat(r);
		}

		if (g.length() < 2) {
			g = "0".concat(g);
		}

		if (b.length() < 2) {
			b = "0".concat(b);
		}

		String color = "0x" + r + g + b;

		MaquetteEc selectedEc = (MaquetteEc) listEC.getSelectedItem();
		FormationAnnee selectedAnnee = (FormationAnnee) comboAnnees.getSelectedItem();

		if (selectedEc == null || selectedAnnee == null || color == null) {
			return;
		}

		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat(ResaCouleurEc.MAQUETTE_EC_KEY + " = %@ and " + ResaCouleurEc.FORMATION_ANNEE_KEY
				+ " = %@", new NSArray(new Object[] { selectedEc, selectedAnnee }));
		ResaCouleurEc resaCouleur = (ResaCouleurEc) DBHandler.fetchUniqueData(eContext, ResaCouleurEc.ENTITY_NAME, qual);

		if (resaCouleur != null) {
			resaCouleur.setColorCode(color);
			app.saveChanges();
		}
		else {
			ResaCouleurEc couleurEc = ResaCouleurEc.createResaCouleurEc(eContext, color);
			selectedEc.addToResaCouleurEcRelationship(couleurEc);
			selectedAnnee.addToResaCouleurEcRelationship(couleurEc);
			app.saveChanges();
		}
	}

	public void fermer(Object sender) {
		((EODialogController) supercontroller()).closeWindow();
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

		FormationAnnee fAnnee = (FormationAnnee) comboAnnees.getSelectedItem();

		EOQualifier qualSem = EOQualifier.qualifierWithQualifierFormat("(" + MaquetteRepartitionSem.SEMESTRE_KEY + "."
				+ MaquetteSemestre.MSEM_ORDRE_KEY + " = %@ or " + MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY
				+ " = %@) and " + MaquetteRepartitionSem.PARCOURS_KEY + " = %@ and " + MaquetteRepartitionSem.FANN_KEY_KEY + " = %@", new NSArray(
				new Object[] { new Integer(ordre1), new Integer(ordre2), parcours, fAnnee.fannKey() }));

		NSArray semestres = (NSArray) DBHandler.fetchData(eContext, MaquetteRepartitionSem.ENTITY_NAME, qualSem).valueForKey(
				MaquetteRepartitionSem.SEMESTRE_KEY);
		comboSemestre.removeAllItems();
		for (int i = 0; i < semestres.count(); i++) {
			comboSemestre.addItem(semestres.objectAtIndex(i));
		}

		this.afficherEC();
		WindowHandler.setDefaultCursor(this);
	}

	public void rechercherEC() {
		WindowHandler.setWaitCursor(this);

		eodHabilitation.setDelegate(null);

		String grade = tGrade.getText();
		String diplome = tDiplome.getText();

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

		sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FORMATION_ANNEE_KEY + " = %@",
				new NSArray(comboAnnees.getSelectedItem())));

		// si edtCreateur : peut voir tous les diplomes
		boolean edtCreateur = ((Boolean) app.userInfo("edtCreateur")).booleanValue();
		NSArray droits = (NSArray) app.userInfo("droits");
		if (!edtCreateur && droits.count() > 0) {
			EOQualifier qualDroits = EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.DROIT_DIPLOMES_KEY + "."
					+ ScolDroitDiplome.DLOG_KEY_KEY + " = %@", new NSArray(droits.objectAtIndex(0)));
			sumQualifiers.addObject(qualDroits);
		}

		eodHabilitation.setSortOrderings(new NSArray<EOSortOrdering>(new EOSortOrdering[] { sortDiplome, sortNiveau }));
		EOQualifier totalQualifier = new EOAndQualifier(sumQualifiers);

		DBHandler.fetchDisplayGroup(eodHabilitation, totalQualifier, null, false);
		WidgetHandler.selectNoneInDisplayGroup(eodHabilitation);
		eodHabilitation.setDelegate(this);

		if (eodHabilitation.displayedObjects().count() == 0) {
			clean();
		}

		WindowHandler.setDefaultCursor(this);

	}

	private void afficherEC() {
		FormationAnnee selectedAnnee = (FormationAnnee) comboAnnees.getSelectedItem();
		Number fannKey = selectedAnnee.fannKey();
		MaquetteSemestre selectedSemestre = (MaquetteSemestre) comboSemestre.getSelectedItem();

		NSArray lesEC = new NSArray();

		if (selectedAnnee != null && selectedSemestre != null) {

			EOQualifier qualEC = EOQualifier.qualifierWithQualifierFormat(MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.FANN_KEY_KEY
					+ " = %@ and " + MaquetteEc.V_PARCOURS_EC_KEY + "." + VParcoursEc.SEMESTRE_KEY + " = %@", new NSArray<Object>(new Object[] {
					fannKey, selectedSemestre }));
			lesEC = DBHandler.fetchData(eContext, MaquetteEc.ENTITY_NAME, qualEC);
		}
		else {
			return;
		}

		listEC.setObjects(lesEC);

	}

	private class JComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == comboParcours) {
				afficherSemestres();
			}
			else
				if (e.getSource() == comboSemestre) {
					afficherEC();
				}
		}
	}

	/** class d'ecoute du groupe de radioboutons de changement de recherche depositaire/tous */
	private class PrefMatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			afficherPreferences();
		}
	}

	private static class ECColorCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			MaquetteEc maquetteEc = (MaquetteEc) value;

			if (maquetteEc != null && maquetteEc.couleurEc() != null) {
				setBackground(Color.decode(maquetteEc.couleurEc()));
			}

			if (isSelected) {
				setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			}
			else {
				setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
			}

			return this;
		}
	}

}
