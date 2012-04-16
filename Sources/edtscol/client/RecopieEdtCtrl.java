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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.PrefScol;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.Matrix;

public class RecopieEdtCtrl extends EOInterfaceController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public JButton bCommencer, bChercherHab;
	public JCheckBox ckCm, ckEnseignants, ckEnseignements, ckGroupesEtudiants, ckSalles, ckTd, ckTp;
	public JComboBox comboAnnee, comboAnneeDest, comboParcours, comboSemestre;
	public EODisplayGroup eodErreur, eodHabilitation;
	public EOMatrix matTypeRecopie;
	public EOTable tableErreur, tableHabilitation;
	public JTextField tSemaineDest, tSemaines, tGrade, tDiplome;

	private EOEditingContext eContext;
	private EnseignementFactory ensFactory;

	public RecopieEdtCtrl(EOEditingContext substitutionEditingContext) {
		super(substitutionEditingContext);
		eContext = substitutionEditingContext;
	}

	protected void controllerDidLoadArchive(NSDictionary objects) {
		super.controllerDidLoadArchive(objects);
		init();
	}

	protected void componentDidBecomeVisible() {
		initWidgets();
		afficherDiplomesPreferes();
	}

	private void init() {
		NSArray formationAnnee = app.getFormationAnnees();
		if (formationAnnee.count() == 0) {
			WindowHandler.showError("Pas d'annee scolaire disponible");
			return;
		}
		_clean();
		comboAnnee.removeAllItems();
		comboAnneeDest.removeAllItems();
		for (int i = 0; i < formationAnnee.count(); i++) {
			FormationAnnee fAnnee = (FormationAnnee) formationAnnee.objectAtIndex(i);
			comboAnnee.addItem(fAnnee);
			comboAnneeDest.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnee.setSelectedItem(fAnnee);
				if (i + 1 < formationAnnee.count()) {
					comboAnneeDest.setSelectedItem(formationAnnee.objectAtIndex(i + 1));
				}
			}
		}
		eodHabilitation.setDelegate(this);
		ckCm.setSelected(true);
		ckTd.setSelected(true);
		ckTp.setSelected(true);
		ckEnseignants.setSelected(true);
		ckEnseignements.setSelected(true);
		ckGroupesEtudiants.setSelected(true);
		ckSalles.setSelected(true);
		Matrix.setSelectedIndex(0, matTypeRecopie);
		comboParcours.addActionListener(new JComboListener());
		ensFactory = new EnseignementFactory(eContext);
	}

	private void afficherDiplomesPreferes() {
		_clean();
		IndividuUlr utilisateur = (IndividuUlr) app.userInfo("individu");
		EOQualifier qIndividu = DBHandler.getSimpleQualifier(PrefScol.INDIVIDU_KEY, utilisateur);
		NSArray preferences = PrefScol.fetchPrefScols(eContext, qIndividu, null);
		eodHabilitation.setObjectArray((NSArray) preferences.valueForKey(PrefScol.HABILITATION_KEY));
		eodHabilitation.setSelectedObject(null);
		eodHabilitation.setDelegate(this);
		WidgetHandler.informObservingAssociation(eodHabilitation);
		WindowHandler.setDefaultCursor(this);
	}

	public void displayGroupDidChangeSelection(EODisplayGroup group) {
		if (group == eodHabilitation) {
			_clean();
			FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
			if (selectedHabilitation != null && selectedHabilitation.formationSpecialisation() != null) {
				this.afficherParcours(selectedHabilitation.formationSpecialisation());
			}
		}
	}

	private void _clean() {
		comboParcours.removeAllItems();
		comboSemestre.removeAllItems();
	}

	public void commencer() {

		MaquetteParcours parc = (MaquetteParcours) comboParcours.getSelectedItem();
		MaquetteSemestre sem = (MaquetteSemestre) comboSemestre.getSelectedItem();
		FormationAnnee annee = (FormationAnnee) comboAnnee.getSelectedItem();
		FormationAnnee anneeDest = (FormationAnnee) comboAnneeDest.getSelectedItem();
		if (parc == null || sem == null || annee == null || anneeDest == null) {
			return;
		}

		WindowHandler.setWaitCursor(this);

		EOGlobalID idPar = eContext.globalIDForObject(parc);
		EOGlobalID idSem = eContext.globalIDForObject(sem);

		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();

		NSMutableDictionary args = new NSMutableDictionary();
		args.setObjectForKey(idPar, "idParcours");
		args.setObjectForKey(idSem, "idSemestre");
		args.setObjectForKey(annee.fannKey(), "fannKey");
		args.setObjectForKey(anneeDest.fannKey(), "fannKeyDest");
		args.setObjectForKey(tSemaines.getText(), "semaines");
		args.setObjectForKey(tSemaineDest.getText(), "semaineDest");

		args.setObjectForKey(ckCm.isSelected() ? "O" : "N", "copierCM");
		args.setObjectForKey(ckTd.isSelected() ? "O" : "N", "copierTD");
		args.setObjectForKey(ckTp.isSelected() ? "O" : "N", "copierTP");

		args.setObjectForKey(ckGroupesEtudiants.isSelected() ? "O" : "N", "copierGroupes");
		args.setObjectForKey(ckEnseignants.isSelected() ? "O" : "N", "copierOccupants");
		args.setObjectForKey(ckSalles.isSelected() ? "O" : "N", "copierSalles");

		args.setObjectForKey(app.getUserName(), "login");
		NSArray erreurs = (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestRecopierEdt",
				new Class[] { NSDictionary.class }, new Object[] { args.immutableClone() }, false);
		eodErreur.setObjectArray(erreurs);
		WindowHandler.setDefaultCursor(this);
	}

	public void rechercherHabilitation() {
		WindowHandler.setWaitCursor(this);
		String grade = tGrade.getText();
		String diplome = tDiplome.getText();
		FormationAnnee annee = (FormationAnnee) comboAnnee.getSelectedItem();

		NSArray droits = (NSArray) app.userInfo("droits");
		if (droits.count() > 0 && annee != null) {
			Number droit = (Number) droits.objectAtIndex(0);
			eodHabilitation.setObjectArray(ensFactory.getHabilitationsPourDroit(diplome, grade, annee.fannKey(), droit));
		}
		WindowHandler.setDefaultCursor(this);
	}

	/** afficherParcours */
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

	/** afficherSemestres */
	public void afficherSemestres() {
		WindowHandler.setWaitCursor(this);
		FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
		Object parcours = comboParcours.getSelectedItem();

		if (selectedHabilitation == null || parcours == null) {
			return;
		}

		int niveau = selectedHabilitation.fhabNiveau().intValue();
		int ordre1 = niveau * 2 - 1;
		int ordre2 = ordre1 + 1;

		FormationAnnee fAnnee = (FormationAnnee) comboAnnee.getSelectedItem();

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

		WindowHandler.setDefaultCursor(this);
	}

	/** Class de listener d'un bouton-menu */
	private class JComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboAnnee) {
				rechercherHabilitation();
			}
			else
				if (e.getSource() == comboParcours) {
					afficherSemestres();
				}
		}
	}

	private void initWidgets() {
		WidgetHandler.decorateButton("Commencer la recopie des EDT", null, "right", bCommencer);
		WidgetHandler.decorateButton("Recherche les diplomes", null, "find", bChercherHab);
		WidgetHandler.setTableNotEditable(tableHabilitation);
		WidgetHandler.setTableNotEditable(tableErreur);
	}

}