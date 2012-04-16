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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.VacancesScolaires;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EODialogs;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.eointerface.swing.EOTextArea;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.Matrix;
import fr.univlr.utilities.ULRDateTimeWindow;

public class GestionVacances extends EOInterfaceController {

	public static final int CREATION = 1;
	public static final int MODIFICATION = 2;
	public static final int CONSULTATION = 3;
	public EOMatrix matPrefDip;

	private final MainClient app = (MainClient) EOApplication.sharedApplication();

	public JButton bAjouterVac, bDebutVac, bFinVac, bModifierVac, bSupprimerVac, bValider;
	public JComboBox comboAnnees;
	public EODisplayGroup eodHabilitation, eodVacances;
	public EOTable tableHabilitation, tableVacances;
	public EOTextArea taCommentaires;
	public JTextField tDebut, tFin, tGrade, tDiplome;
	private final EOEditingContext eContext;
	private int mode = CONSULTATION;
	private final EnseignementFactory ensFactory;

	private EOSortOrdering sortDiplome, sortNiveau;

	public GestionVacances(final EOEditingContext eContext) {
		super(eContext);
		this.eContext = eContext;
		ensFactory = new EnseignementFactory(eContext);
	}

	protected void controllerDidLoadArchive(final NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		this.init();
		initWidgets();
	}

	protected void componentDidBecomeVisible() {
		this.activateWidgets(false);
	}

	private void init() {

		Matrix.setSelectedIndex(0, matPrefDip);
		Matrix.setListener(new PrefMatrixListener(), matPrefDip);

		sortDiplome = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		sortNiveau = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FHAB_NIVEAU_KEY, EOSortOrdering.CompareAscending);

		final NSArray formationAnnee = app.getFormationAnnees();
		comboAnnees.removeAllItems();
		for (int i = 0; i < formationAnnee.count(); i++) {
			final FormationAnnee fAnnee = (FormationAnnee) formationAnnee.objectAtIndex(i);
			comboAnnees.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnees.setSelectedItem(fAnnee);
			}
		}
		comboAnnees.addActionListener(new JComboListener());

		eodHabilitation.setDelegate(this);
		eodVacances.setDelegate(this);
		rechercherDiplome();
	}

	public void rechercherDiplome() {
		if (Matrix.getSelectedIndex(matPrefDip) == 0) {
			eodHabilitation.setDelegate(null);
			eodHabilitation.setSortOrderings(new NSArray(new Object[] { sortDiplome, sortNiveau }));
			eodHabilitation.setObjectArray(app.getPreferencesHabilitations((FormationAnnee) comboAnnees.getSelectedItem()));
			WidgetHandler.selectNoneInDisplayGroup(eodHabilitation);
			eodHabilitation.setDelegate(this);
		}
		else {
			rechercherHabilitation();
		}
		eodHabilitation.updateDisplayedObjects();
	}

	/** active ou desactive les controles sur la fenetre */
	private void activateWidgets(boolean active) {
		if (mode == MODIFICATION) {
			bAjouterVac.setEnabled(false);
		}
		else {
			bAjouterVac.setEnabled(true);
		}
		bDebutVac.setEnabled(active);
		bFinVac.setEnabled(active);
		if (mode == MODIFICATION) {
			bModifierVac.setEnabled(false);
		}
		else {
			bModifierVac.setEnabled(!active);
		}
		bSupprimerVac.setEnabled(active);
		bValider.setEnabled(active);
		tDebut.setEditable(active);
		tFin.setEditable(active);
		taCommentaires.setEditable(active);
		if (mode == CREATION) {
			tDebut.setText(null);
			tFin.setText(null);
			taCommentaires.setText(null);
		}
	}

	public void rechercherHabilitation() {
		final String grade = tGrade.getText();
		final String diplome = tDiplome.getText();
		final FormationAnnee annee = (FormationAnnee) comboAnnees.getSelectedItem();

		eodHabilitation.setSortOrderings(new NSArray(sortDiplome));

		final NSArray droits = (NSArray) app.userInfo("droits");
		if (droits.count() > 0) {
			final Number droit = (Number) droits.objectAtIndex(0);
			eodHabilitation.setObjectArray(ensFactory.getHabilitationsPourDroit(diplome, grade, annee.fannKey(), droit));
		}
	}

	public void ajouterVacances() {
		mode = CREATION;
		this.activateWidgets(true);
	}

	public void modifierVacances() {
		mode = MODIFICATION;
		this.activateWidgets(true);
	}

	public void selectionnerDebut(final Object sender) {
		new ULRDateTimeWindow(this, sender, "setDateDebut");
	}

	public void selectionnerFin(final Object sender) {
		new ULRDateTimeWindow(this, sender, "setDateFin");
	}

	public void setDateDebut(final String debut) {
		tDebut.setText(debut);
	}

	public void setDateFin(final String fin) {
		tFin.setText(fin);
	}

	/** supprimer l'unite vacances selectionnee dans la table -eodVacances- */
	public void supprimerVacances() {
		if (eodVacances.selectedObjects() == null || eodVacances.selectedObjects().count() == 0) {
			return;
		}

		final int choix = JOptionPane.showConfirmDialog(null, "Supprimer les vacances selectionnées", "Confirmation", JOptionPane.YES_NO_OPTION);

		if (choix == JOptionPane.NO_OPTION) {
			return;
		}
		final NSArray<VacancesScolaires> selectedVacances = eodVacances.selectedObjects();
		for (int i = 0; i < selectedVacances.count(); i++) {
			final VacancesScolaires selectedVacance = selectedVacances.objectAtIndex(i);
			selectedVacance.setFormationHabilitationRelationship(null);
			eContext.deleteObject(selectedVacance);
		}

		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (final Exception exe) {
			exe.printStackTrace();
			eContext.revert();
		}
		finally {
			eContext.unlock();
			eodVacances.updateDisplayedObjects();
		}
		displayGroupDidChangeSelection(eodVacances);
	}

	/** validation d'un nouvel enregistrement ou d'une modification */
	public void valider() {
		final String commentaire = taCommentaires.textComponent().getText();
		final String sDebut = tDebut.getText();
		final String sFin = tFin.getText();
		final FormationAnnee annee = (FormationAnnee) comboAnnees.getSelectedItem();

		if (sDebut.equals("") || sFin.equals("")) {
			return;
		}

		final NSTimestamp debut = FormatHandler.strToDate(sDebut, "%d/%m/%Y");
		final NSTimestamp fin = FormatHandler.strToDate(sFin, "%d/%m/%Y");

		if (fin.before(debut)) {
			EODialogs.runInformationDialog("Erreur", "Une date de fin postérieure à la date de début, ce serait pas mal ça ! :-)");
			return;
		}

		final NSArray habilitations = eodHabilitation.selectedObjects();

		if (mode == CREATION) {
			boolean vacances = creerVacances(annee, debut, fin, commentaire, habilitations);
			if (!vacances) {
				WindowHandler.showError("Echec d'enregistrement des vacances");
				return;
			}
			mode = CONSULTATION;
			this.activateWidgets(false);
			displayGroupDidChangeSelection(eodHabilitation);
		}

		if (mode == MODIFICATION) {
			if (eodVacances.selectedObjects().count() == 0) {
				return;
			}
			else {
				final FormationHabilitation habilitation = (FormationHabilitation) eodHabilitation.selectedObject();
				final VacancesScolaires currentVacances = (VacancesScolaires) eodVacances.selectedObject();
				if (majVacances(currentVacances, debut, fin, commentaire, habilitation)) {
					eodVacances.updateDisplayedObjects();
				}
				mode = CONSULTATION;
				this.activateWidgets(false);
			}
		}
		displayGroupDidChangeSelection(eodVacances);
	}

	/** permet d'enregister un element de vacances scolaires pour un tableau d'habilitations */
	public boolean creerVacances(final FormationAnnee annee, final NSTimestamp debut, final NSTimestamp fin, final String commentaire,
			final NSArray habilitations) {

		for (int i = 0; i < habilitations.count(); i++) {
			final FormationHabilitation currentHab = (FormationHabilitation) habilitations.objectAtIndex(i);
			final VacancesScolaires vacances = VacancesScolaires.createVacancesScolaires(eContext, annee.fannKey(),
					FormatHandler.midnightTime(debut), FormatHandler.endOfDay(fin));
			vacances.setVsCommentaire(commentaire);
			vacances.setFormationHabilitationRelationship(currentHab);
		}
		boolean retour = true;
		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (final Exception exe) {
			exe.printStackTrace();
			eContext.revert();
			retour = false;
		}
		finally {
			eContext.unlock();
		}
		return retour;
	}

	/** permet de modifier un element de vacances scolaires */
	public boolean majVacances(final VacancesScolaires vacances, final NSTimestamp debut, final NSTimestamp fin, final String commentaire,
			final FormationHabilitation habilitation) {

		vacances.setVsDateDebut(FormatHandler.midnightTime(debut));
		vacances.setVsDateFin(FormatHandler.endOfDay(fin));
		vacances.setVsCommentaire(commentaire);
		vacances.setFormationHabilitationRelationship(habilitation);
		boolean retour = true;
		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (final Exception exe) {
			exe.printStackTrace();
			eContext.revert();
			retour = false;
		}
		finally {
			eContext.unlock();
		}
		return retour;
	}

	/** callback delegate de selection d'un diplome/habilitation */
	public void displayGroupDidChangeSelection(final EODisplayGroup group) {
		if ((group == eodHabilitation) && (eodHabilitation.selectedObjects().count() > 0)) {
			final FormationHabilitation habilitation = (FormationHabilitation) eodHabilitation.selectedObject();
			final EOQualifier qVacances = EOQualifier.qualifierWithQualifierFormat(VacancesScolaires.FORMATION_HABILITATION_KEY + " = %@",
					new NSArray(habilitation));
			final EOSortOrdering sortVac = EOSortOrdering.sortOrderingWithKey(VacancesScolaires.VS_DATE_DEBUT_KEY, EOSortOrdering.CompareDescending);
			DBHandler.fetchDisplayGroup(eodVacances, qVacances, sortVac);
			afficherVacances((VacancesScolaires) eodVacances.selectedObject());
		}

		if (group == eodVacances) {
			final VacancesScolaires vacances = (VacancesScolaires) eodVacances.selectedObject();
			this.afficherVacances(vacances);
		}

	}

	/** affiche les informations dans les controles pour eventuelles modifications */
	private void afficherVacances(final VacancesScolaires vacances) {
		if (vacances != null) {
			final String debut = FormatHandler.dateToStr(vacances.vsDateDebut());
			final String fin = FormatHandler.dateToStr(vacances.vsDateFin());
			tDebut.setText(debut);
			tFin.setText(fin);
			taCommentaires.textComponent().setText(vacances.vsCommentaire());
			bSupprimerVac.setEnabled(true);
			bModifierVac.setEnabled(true);
		}
		else {
			tDebut.setText("");
			tFin.setText("");
			taCommentaires.textComponent().setText("");
			bSupprimerVac.setEnabled(false);
			bModifierVac.setEnabled(false);
		}
	}

	/** init Widgets */
	private void initWidgets() {
		WidgetHandler.setTableNotEditable(tableHabilitation);
		WidgetHandler.setTableNotEditable(tableVacances);
		tDebut.setBackground(ParamsHandler.C_BACKGRD_E);
		tFin.setBackground(ParamsHandler.C_BACKGRD_E);
		WidgetHandler.decorateButton("Enregister les vacances", null, "new", bAjouterVac);
		WidgetHandler.decorateButton("Enregister les vacances", null, "save", bValider);
		WidgetHandler.decorateButton("Supprimer les vacances", null, "delete", bSupprimerVac);
		WidgetHandler.decorateButton("Modifier les vacances", null, "reload", bModifierVac);
		WidgetHandler.decorateButton("Calendrier : debut", null, "cal", bDebutVac);
		WidgetHandler.decorateButton("Calendrier : fin", null, "cal", bFinVac);
		WidgetHandler.setTableNotEditable(tableHabilitation);
		WidgetHandler.setTableNotEditable(tableVacances);
	}

	/** Class de listener d'un bouton-menu */
	private class JComboListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			rechercherHabilitation();
		}
	}

	private class PrefMatrixListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			rechercherDiplome();
		}
	}

}
