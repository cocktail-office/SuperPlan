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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.metier.ScolDroitDiplome;
import org.cocktail.superplan.client.swing.ZEOTable.ZEOTableListener;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;

public class PreferencesDiplomeCtrl {

	private PreferencesDiplomeView myView;
	private Component parent;
	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EOEditingContext ec;

	private EODisplayGroup eodHabilitations = new EODisplayGroup();
	private EODisplayGroup eodHabilitationsChoisies = new EODisplayGroup();

	public PreferencesDiplomeCtrl(EOEditingContext eContext, Component parent, boolean modal) {
		this.parent = parent;
		myView = new PreferencesDiplomeView(new javax.swing.JFrame(), modal, eodHabilitations, eodHabilitationsChoisies);
		ec = eContext;
		this.parent = parent;
		init();
	}

	public void open() {
		myView.setLocation(parent.getX() + 20, parent.getY() + 20);
		myView.setVisible(true);
	}

	public void close() {
		myView.setVisible(false);
	}

	private void init() {
		myView.getBtChercher().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateEodHabilitations();
			}
		});
		myView.getBtAjouter().setIcon(WidgetHandler.imageIcon("down"));
		myView.getBtAjouter().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ajouterPreference();
			}
		});
		myView.getBtSupprimer().setIcon(WidgetHandler.imageIcon("up"));
		myView.getBtSupprimer().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				retirerPreference();
			}
		});

		NSArray<FormationAnnee> formationAnnee = app.getFormationAnnees();
		myView.getCbAnnee().removeAllItems();
		for (int i = 0; i < formationAnnee.count(); i++) {
			FormationAnnee fAnnee = formationAnnee.objectAtIndex(i);
			myView.getCbAnnee().addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				myView.getCbAnnee().setSelectedItem(fAnnee);
			}
		}

		myView.getCbParcours().removeAllItems();

		RechercheListener myRechercheListener = new RechercheListener();
		myView.getBtChercher().addActionListener(myRechercheListener);
		myView.getTfGrade().addActionListener(myRechercheListener);
		myView.getTfDiplome().addActionListener(myRechercheListener);
		myView.getCbAnnee().addActionListener(new ChangementAnneeListener());

		myView.getMyEOTableHabilitations().addListener(new ListenerTableHabilitations());

		EOSortOrdering sortDiplome = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);
		EOSortOrdering sortNiveau = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FHAB_NIVEAU_KEY, EOSortOrdering.CompareAscending);
		eodHabilitations.setSortOrderings(new NSArray<EOSortOrdering>(new EOSortOrdering[] { sortDiplome, sortNiveau }));

		EOSortOrdering sortDiplomePref = EOSortOrdering.sortOrderingWithKey(PrefScol.HABILITATION_KEY + "."
				+ FormationHabilitation.FORMATION_SPECIALISATION_KEY + "." + FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "."
				+ FormationDiplome.FDIP_ABREVIATION_KEY, EOSortOrdering.CompareAscending);
		EOSortOrdering sortNiveauPref = EOSortOrdering.sortOrderingWithKey(PrefScol.HABILITATION_KEY + "." + FormationHabilitation.FHAB_NIVEAU_KEY,
				EOSortOrdering.CompareAscending);
		eodHabilitationsChoisies.setSortOrderings(new NSArray<EOSortOrdering>(new EOSortOrdering[] { sortDiplomePref, sortNiveauPref }));

		updateEodHabilitations();
		updatePreferences();
	}

	private class RechercheListener implements ActionListener {
		public RechercheListener() {
			super();
		}

		public void actionPerformed(ActionEvent anAction) {
			updateEodHabilitations();
		}
	}

	private class ChangementAnneeListener implements ActionListener {
		public ChangementAnneeListener() {
			super();
		}

		public void actionPerformed(ActionEvent anAction) {
			updateEodHabilitations();
			updatePreferences();
		}
	}

	private void updateEodHabilitations() {
		boolean edtCreateur = ((Boolean) app.userInfo("edtCreateur")).booleanValue();
		NSArray<Object> droits = (NSArray<Object>) app.userInfo("droits");
		if (!edtCreateur && (droits == null || droits.count() == 0)) {
			eodHabilitations.setObjectArray(null);
			myView.getMyEOTableHabilitations().updateData();
			return;
		}

		String grade = myView.getTfGrade().getText();
		String diplome = myView.getTfDiplome().getText();

		NSMutableArray<EOQualifier> sumQualifiers = new NSMutableArray<EOQualifier>();

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

		sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FANN_KEY_KEY + " = %@", new NSArray<Object>(
				((FormationAnnee) myView.getCbAnnee().getSelectedItem()).fannKey())));

		// si edtCreateur : peut voir tous les diplomes
		if (!edtCreateur) {
			sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.DROIT_DIPLOMES_KEY + "." + ScolDroitDiplome.DLOG_KEY_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, droits.objectAtIndex(0)));
			sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.DROIT_DIPLOMES_KEY + "." + ScolDroitDiplome.DDIP_EDT_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, "A"));
		}

		EOQualifier totalQualifier = new EOAndQualifier(sumQualifiers);

		EOFetchSpecification myFetch = new EOFetchSpecification(FormationHabilitation.ENTITY_NAME, totalQualifier, null);
		myFetch.setUsesDistinct(true);
		myFetch.setRefreshesRefetchedObjects(true);
		eodHabilitations.setObjectArray(ec.objectsWithFetchSpecification(myFetch));
		myView.getMyEOTableHabilitations().updateData();
	}

	private void updateCbParcours() {
		myView.getCbParcours().removeAllItems();
		if (eodHabilitations.selectedObjects() != null && eodHabilitations.selectedObjects().count() == 1) {
			FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitations.selectedObject();
			int niveau = selectedHabilitation.fhabNiveau().intValue();
			int ordre1 = niveau * 2 - 1;
			int ordre2 = ordre1 + 1;

			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat("(" + MaquetteRepartitionSem.SEMESTRE_KEY + "."
					+ MaquetteSemestre.MSEM_ORDRE_KEY + " = %@ or " + MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY
					+ " = %@) and " + MaquetteRepartitionSem.PARCOURS_KEY + "." + MaquetteParcours.FORMATION_SPECIALISATION_KEY + " = %@ and "
					+ MaquetteRepartitionSem.FANN_KEY_KEY + " = %@", new NSArray<Object>(new Object[] { new Integer(ordre1), new Integer(ordre2),
					selectedHabilitation.formationSpecialisation(), selectedHabilitation.fannKey() }));
			EOSortOrdering sortSemestres = EOSortOrdering.sortOrderingWithKey(MaquetteRepartitionSem.SEMESTRE_KEY + "."
					+ MaquetteSemestre.MSEM_ORDRE_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);
			EOSortOrdering sortParcours = EOSortOrdering.sortOrderingWithKey(MaquetteRepartitionSem.PARCOURS_KEY + "."
					+ MaquetteParcours.MPAR_LIBELLE_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);

			NSArray<MaquetteRepartitionSem> semestres = MaquetteRepartitionSem.fetchMaquetteRepartitionSems(ec, qualifier,
					new NSArray<EOSortOrdering>(new EOSortOrdering[] { sortSemestres, sortParcours }));

			myView.getCbParcours().addItem(null);
			for (int i = 0; i < semestres.count(); i++) {
				myView.getCbParcours().addItem(semestres.objectAtIndex(i));
			}
		}
	}

	private void updatePreferences() {
		EOQualifier qual1 = new EOKeyValueQualifier(PrefScol.INDIVIDU_KEY, EOKeyValueQualifier.QualifierOperatorEqual, app.userInfo("individu"));
		EOQualifier qual2 = new EOKeyValueQualifier(PrefScol.ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, myView.getCbAnnee()
				.getSelectedItem());
		EOQualifier qualifier = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qual1, qual2 }));
		EOFetchSpecification myFetch = new EOFetchSpecification(PrefScol.ENTITY_NAME, qualifier, null);
		eodHabilitationsChoisies.setObjectArray(ec.objectsWithFetchSpecification(myFetch));
		myView.getMyEOTableHabilitationsChoisies().updateData();
	}

	public void ajouterPreference() {
		NSArray<FormationHabilitation> habilitations = eodHabilitations.selectedObjects();
		if (habilitations == null || habilitations.count() == 0) {
			WindowHandler.showInfo("Veuillez rechercher et sélectionner un diplôme dans la table du dessus");
			return;
		}

		for (int i = 0; i < habilitations.count(); i++) {
			FormationHabilitation habilitation = habilitations.objectAtIndex(i);
			NSArray<FormationHabilitation> preferedHabilitations = (NSArray<FormationHabilitation>) eodHabilitationsChoisies.displayedObjects()
					.valueForKey(PrefScol.HABILITATION_KEY);
			if (preferedHabilitations.containsObject(habilitation)) {
				WindowHandler.showInfo("Ce diplôme est déjà dans votre liste des préférences ("
						+ habilitation.formationSpecialisation().scolFormationDiplome().fdipAbreviation() + ")");
				if (habilitations.count() == 1) {
					return;
				}
				continue;
			}

			PrefScol prefScol = PrefScol.createPrefScol(ec);
			prefScol.setHabilitationRelationship(habilitation);
			prefScol.setAnneeRelationship((FormationAnnee) myView.getCbAnnee().getSelectedItem());
			prefScol.setIndividuRelationship((IndividuUlr) app.userInfo("individu"));
			if (myView.getCbParcours().getSelectedItem() != null) {
				prefScol.setMaquetteRepartitionSemRelationship((MaquetteRepartitionSem) myView.getCbParcours().getSelectedItem());
			}
		}
		if (!app.saveChanges()) {
			WindowHandler.showError("Impossible d'ajouter le(s) diplôme(s) aux préférences : Contactez le service info SVP");
		}
		else {
			updatePreferences();
		}
	}

	public void retirerPreference() {
		NSArray<PrefScol> prefScols = eodHabilitationsChoisies.selectedObjects();
		if (prefScols == null || prefScols.count() == 0) {
			return;
		}
		for (int i = 0; i < prefScols.count(); i++) {
			PrefScol prefScol = prefScols.objectAtIndex(i);
			prefScol.setHabilitationRelationship(null);
			prefScol.setIndividuRelationship(null);
			prefScol.setAnneeRelationship(null);
			ec.deleteObject(prefScol);
		}
		if (!app.saveChanges()) {
			WindowHandler.showError("Impossible de retirer le(s) diplôme(s) des préférences : Contactez le service info SVP");
		}
		else {
			updatePreferences();
		}
	}

	private class ListenerTableHabilitations implements ZEOTableListener {

		public void onDbClick() {
			ajouterPreference();
		}

		public void onSelectionChanged() {
			updateCbParcours();
		}
	}

}
