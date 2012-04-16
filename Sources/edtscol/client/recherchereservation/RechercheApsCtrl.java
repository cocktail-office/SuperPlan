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
package edtscol.client.recherchereservation;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.cocktail.superplan.client.factory.GenericFactory;
import org.cocktail.superplan.client.metier.CtrlParamAp;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.LogReservation;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ScolDroitDiplome;
import org.cocktail.superplan.client.metier.VMaquetteApGroupes;
import org.cocktail.superplan.client.metier.VParcoursAp;
import org.cocktail.superplan.client.metier.VScolMaquetteApEc;
import org.cocktail.superplan.client.swing.ZEOTable.ZEOTableListener;
import org.cocktail.superplan.client.swing.ZEOTableCellRenderer;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;

import edtscol.client.MainClient;
import edtscol.client.gestionreservation.InspecteurCtrl;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WindowHandler;

public class RechercheApsCtrl {

	private static RechercheApsCtrl sharedInstance;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EOEditingContext ec;
	private Component parent;

	private EODisplayGroup eodHabilitations = new EODisplayGroup();
	private EODisplayGroup eodAps = new EODisplayGroup();

	private RechercheApsView myView;

	private static final Color COLOR_BGD_LEGEND_OK = new Color(202, 255, 202);
	private static final Color COLOR_BGD_LEGEND_KO = new Color(255, 178, 178);

	public RechercheApsCtrl(Component parent, EOEditingContext editingContext) {
		super();
		myView = new RechercheApsView(new javax.swing.JFrame(), true, eodHabilitations, eodAps, new DataTableCellApsRenderer());
		ec = editingContext;
		this.parent = parent;
		init();
	}

	private void init() {
		myView.getBtFermer().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				myView.setVisible(false);
			}
		});

		myView.getBtSaisirParametresFormation().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saisirParametresHabilitation();
			}
		});

		myView.getBtVoirResasAp().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				afficherResasAp();
			}
		});

		myView.getBtReserverAp().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reserverAp();
			}
		});

		myView.getBtSupprimerResas().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				supprimerResas();
			}
		});

		myView.getBtReserverAuto().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reserverAuto();
			}
		});

		myView.getBtSaisirParametresAp().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saisirParametresAp();
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
		myView.getTfGrade().addActionListener(myRechercheListener);
		myView.getTfDiplome().addActionListener(myRechercheListener);
		myView.getRbPref().addActionListener(myRechercheListener);
		myView.getRbTous().addActionListener(myRechercheListener);
		myView.getCbAnnee().addActionListener(myRechercheListener);

		NSArray<FormationHabilitation> prefs = app.getPreferencesHabilitations((FormationAnnee) myView.getCbAnnee().getSelectedItem());
		if (prefs != null && prefs.count() > 0) {
			myView.getRbPref().setSelected(true);
		}
		else {
			myView.getRbTous().setSelected(true);
		}

		myView.getCbParcours().addActionListener(new CbParcoursListener());

		FiltresListener myFiltresListener = new FiltresListener();
		myView.getRbToutesAps().addActionListener(myFiltresListener);
		myView.getRbApsNonCompletes().addActionListener(myFiltresListener);
		myView.getjCheckBoxGroupes().addActionListener(myFiltresListener);

		myView.getMyEOTableHabilitations().addListener(new ListenerTableHabilitations());
		myView.getMyEOTableAps().addListener(new ListenerTableAps());

		EOSortOrdering sortDiplome = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);
		EOSortOrdering sortNiveau = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FHAB_NIVEAU_KEY, EOSortOrdering.CompareAscending);
		eodHabilitations.setSortOrderings(new NSArray<EOSortOrdering>(new EOSortOrdering[] { sortDiplome, sortNiveau }));

		EOSortOrdering sortMaquetteAp1 = EOSortOrdering.sortOrderingWithKey(VMaquetteApGroupes.MAP_LIBELLE_KEY, EOSortOrdering.CompareAscending);
		EOSortOrdering sortMaquetteAp2 = EOSortOrdering.sortOrderingWithKey(VMaquetteApGroupes.GGRP_LIBELLE_KEY, EOSortOrdering.CompareAscending);
		eodAps.setSortOrderings(new NSArray<EOSortOrdering>(new EOSortOrdering[] { sortMaquetteAp1, sortMaquetteAp2 }));

		myView.getTfLegendeOk().setBackground(COLOR_BGD_LEGEND_OK);
		myView.getTfLegendeKo().setBackground(COLOR_BGD_LEGEND_KO);

		updateEodHabilitations();
	}

	public static RechercheApsCtrl sharedInstance(Component parent, EOEditingContext editingContext) {
		if (sharedInstance == null) {
			sharedInstance = new RechercheApsCtrl(parent, editingContext);
		}
		return sharedInstance;
	}

	public void open() {
		myView.setLocation(parent.getX() + 20, parent.getY() + 20);
		myView.setVisible(true);
	}

	public void close() {
		myView.setVisible(false);
	}

	private void saisirParametresHabilitation() {
		if (eodHabilitations.selectedObjects() != null && eodHabilitations.selectedObjects().count() > 0) {
			SaisieParametresHabilitationCtrl saisieParametresHabilitationCtrl = new SaisieParametresHabilitationCtrl(ec, app.mainInterface(),
					eodHabilitations.selectedObjects(), true);
			saisieParametresHabilitationCtrl.open();
			myView.getMyEOTableHabilitations().updateData();
		}
	}

	private void afficherResasAp() {
		if (eodAps.selectedObject() != null) {
			RechercheResasApCtrl.sharedInstance(ec, myView, ((VMaquetteApGroupes) eodAps.selectedObject()).maquetteAp(),
					((VMaquetteApGroupes) eodAps.selectedObject()).scolGroupeGrp()).open();
		}
	}

	private void reserverAp() {
		if (eodAps.selectedObject() != null) {
			VMaquetteApGroupes vMaquetteApGroupes = (VMaquetteApGroupes) eodAps.selectedObject();
			if (InspecteurCtrl.isOpen() == true) {
				return;
			}
			close();

			InspecteurCtrl inspecteur = new InspecteurCtrl(ec, app.mainInterface(), app.isInspecteurModal());
			inspecteur.commencerNouvelleReservation(vMaquetteApGroupes.maquetteAp(), vMaquetteApGroupes.scolGroupeGrp());
			inspecteur.open();
		}
	}

	private void supprimerResas() {
		try {
			if (eodAps.selectedObjects() != null && eodAps.selectedObjects().count() > 0) {
				if (WindowHandler
						.showConfirmation("ATTENTION: vous allez supprimer toutes les réservations (passées ou non) sur les APs sélectionnés... Vous confirmez ?")) {
					if (WindowHandler
							.showConfirmation("Euh... supprimer TOUTES les réservations de ces APs ? Cette action est brutale et irréversible... Vous confirmez quand même ?")) {
						NSMutableArray<Reservation> resasASupprimer = new NSMutableArray<Reservation>();
						for (int i = 0; i < eodAps.selectedObjects().count(); i++) {
							VMaquetteApGroupes vap = (VMaquetteApGroupes) eodAps.selectedObjects().objectAtIndex(i);
							Enumeration<ReservationAp> e = vap.maquetteAp().reservationAps().objectEnumerator();
							while (e.hasMoreElements()) {
								ReservationAp resaAp = e.nextElement();
								if (vap.scolGroupeGrp() == null || vap.scolGroupeGrp().equals(resaAp.scolGroupeGrp())) {
									if (resasASupprimer.containsObject(resaAp.reservation()) == false) {
										resasASupprimer.addObject(resaAp.reservation());
									}
								}
							}
						}
						boolean problem = false;
						for (int i = 0; i < resasASupprimer.count(); i++) {
							if (app.aDroitSuppression(resasASupprimer.objectAtIndex(i)) == false) {
								if (WindowHandler
										.showConfirmation("Vous n'avez pas les droits pour supprimer certaines réservations... supprimer quand même celles pour lesquelles vous avez l'autorisation ?") == false) {
									app.revertChanges();
									return;
								}
							}
							try {
								LogReservation.logSuppressionReservation(ec, resasASupprimer.objectAtIndex(i));
								new GenericFactory(ec).deleteReservation(resasASupprimer.objectAtIndex(i), false);
							}
							catch (Exception e) {
								problem = true;
							}
						}
						if (problem) {
							if (WindowHandler
									.showConfirmation("Certaines réservations n'ont pas pu être supprimées... valider quand même la suppression des autres ?") == false) {
								app.revertChanges();
								return;
							}
						}
						if (app.saveChanges()) {
							for (int i = 0; i < eodAps.selectedObjects().count(); i++) {
								VMaquetteApGroupes vap = (VMaquetteApGroupes) eodAps.selectedObjects().objectAtIndex(i);
								DBHandler.invalidateObjects(ec, new NSArray<MaquetteAp>(vap.maquetteAp()));
							}
							updateEodAps();
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reserverAuto() {
		if (eodAps.selectedObjects() != null && eodAps.selectedObjects().count() > 0) {
			ReservationAutoCtrl reservationAutoCtrl = new ReservationAutoCtrl(ec, app.mainInterface(),
					(NSArray<VMaquetteApGroupes>) eodAps.selectedObjects(), (FormationHabilitation) eodHabilitations.selectedObject(), true);
			reservationAutoCtrl.open();
			myView.getMyEOTableAps().updateData();
		}
	}

	private void saisirParametresAp() {
		if (eodAps.selectedObjects() != null && eodAps.selectedObjects().count() > 0) {
			SaisieParametresApCtrl saisieParametresApCtrl = new SaisieParametresApCtrl(ec, app.mainInterface(),
					(NSArray<VMaquetteApGroupes>) eodAps.selectedObjects(), (FormationHabilitation) eodHabilitations.selectedObject(), true);
			saisieParametresApCtrl.open();
			myView.getMyEOTableAps().updateData();
		}
	}

	private class RechercheListener implements ActionListener {
		public RechercheListener() {
			super();
		}

		public void actionPerformed(ActionEvent anAction) {
			updateEodHabilitations();
		}
	}

	private class CbParcoursListener implements ActionListener {
		public CbParcoursListener() {
			super();
		}

		public void actionPerformed(ActionEvent anAction) {
			updateEodAps();
		}
	}

	private class FiltresListener implements ActionListener {
		public FiltresListener() {
			super();
		}

		public void actionPerformed(ActionEvent anAction) {
			updateEodAps();
		}
	}

	private void updateEodHabilitations() {
		boolean edtCreateur = ((Boolean) app.userInfo("edtCreateur")).booleanValue();
		NSArray<Object> droits = (NSArray<Object>) app.userInfo("droits");
		if (!edtCreateur && (droits == null || droits.count() == 0)) {
			eodHabilitations.setObjectArray(null);
			eodHabilitations.clearSelection();
			myView.getMyEOTableHabilitations().updateData();
			return;
		}
		if (myView.getRbPref().isSelected()) {
			eodHabilitations.setObjectArray(app.getPreferencesHabilitations((FormationAnnee) myView.getCbAnnee().getSelectedItem()));
			eodHabilitations.clearSelection();
			myView.getMyEOTableHabilitations().updateData();
		}
		else {
			String grade = myView.getTfGrade().getText();
			String diplome = myView.getTfDiplome().getText();
			NSMutableArray<EOQualifier> sumQualifiers = new NSMutableArray<EOQualifier>();
			sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.FORMATION_ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
					myView.getCbAnnee().getSelectedItem()));
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

			if (!edtCreateur) {
				sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.DROIT_DIPLOMES_KEY + "." + ScolDroitDiplome.DLOG_KEY_KEY,
						EOKeyValueQualifier.QualifierOperatorEqual, droits.objectAtIndex(0)));
				sumQualifiers.addObject(new EOKeyValueQualifier(FormationHabilitation.DROIT_DIPLOMES_KEY + "." + ScolDroitDiplome.DDIP_EDT_KEY,
						EOKeyValueQualifier.QualifierOperatorEqual, "A"));
			}
			eodHabilitations.setObjectArray(FormationHabilitation.fetchFormationHabilitations(ec, new EOAndQualifier(sumQualifiers), null));
			eodHabilitations.clearSelection();
			myView.getMyEOTableHabilitations().updateData();
		}
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

			for (int i = 0; i < semestres.count(); i++) {
				myView.getCbParcours().addItem(semestres.objectAtIndex(i));
			}
			PrefScol prefScol = getPrefScolForHabilitation(selectedHabilitation);
			if (prefScol != null && prefScol.maquetteRepartitionSem() != null) {
				myView.getCbParcours().setSelectedItem(prefScol.maquetteRepartitionSem());
			}
		}
	}

	private PrefScol getPrefScolForHabilitation(FormationHabilitation habilitation) {
		if (habilitation == null) {
			return null;
		}
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>(2);
		quals.addObject(new EOKeyValueQualifier(PrefScol.INDIVIDU_KEY, EOKeyValueQualifier.QualifierOperatorEqual, app.userInfo("individu")));
		quals.addObject(new EOKeyValueQualifier(PrefScol.ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, myView.getCbAnnee().getSelectedItem()));
		NSArray<PrefScol> prefScols = habilitation.prefScols(new EOAndQualifier(quals));
		if (prefScols != null && prefScols.count() > 0) {
			return prefScols.objectAtIndex(0);
		}
		return null;
	}

	private void updateEodAps() {
		NSArray<VMaquetteApGroupes> aps = null;
		MaquetteRepartitionSem mrs = (MaquetteRepartitionSem) myView.getCbParcours().getSelectedItem();
		if (mrs != null) {
			NSMutableArray<EOQualifier> sumQualifiers = new NSMutableArray<EOQualifier>();
			sumQualifiers.addObject(new EOKeyValueQualifier(VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.V_PARCOURS_APS_KEY + "."
					+ VParcoursAp.FANN_KEY_KEY, EOKeyValueQualifier.QualifierOperatorEqual, mrs.fannKey()));
			sumQualifiers.addObject(new EOKeyValueQualifier(VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.V_PARCOURS_APS_KEY + "."
					+ VParcoursAp.SEMESTRE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, mrs.semestre()));
			sumQualifiers.addObject(new EOKeyValueQualifier(VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.V_PARCOURS_APS_KEY + "."
					+ VParcoursAp.PARCOURS_KEY, EOKeyValueQualifier.QualifierOperatorEqual, mrs.parcours()));
			if (myView.getjCheckBoxGroupes().isSelected() == false) {
				sumQualifiers.addObject(new EOKeyValueQualifier(VMaquetteApGroupes.SCOL_GROUPE_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
						NSKeyValueCoding.NullValue));
			}
			EOFetchSpecification fetchSpec = new EOFetchSpecification(VMaquetteApGroupes.ENTITY_NAME, new EOAndQualifier(sumQualifiers), null);
			fetchSpec.setIsDeep(true);
			fetchSpec.setRefreshesRefetchedObjects(true);
			fetchSpec.setPrefetchingRelationshipKeyPaths(new NSArray<String>(new String[] {
					VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.RESERVATION_APS_KEY + "." + ReservationAp.V_MAQUETTE_AP_KEY,
					VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.RESERVATION_APS_KEY + "." + ReservationAp.RESERVATION_KEY + "."
							+ Reservation.PERIODICITES_KEY, VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.CTRL_PARAM_APS_KEY,
					VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.CTRL_PARAM_APS_KEY + "." + CtrlParamAp.CTRL_PARAM_AP_INDIVIDUS_KEY,
					VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.CTRL_PARAM_APS_KEY + "." + CtrlParamAp.CTRL_PARAM_AP_SALLES_KEY,
					VMaquetteApGroupes.MAQUETTE_AP_KEY + "." + MaquetteAp.CTRL_PARAM_APS_KEY + "." + CtrlParamAp.CTRL_PARAM_AP_SALLE_CHOIXS_KEY,
					VMaquetteApGroupes.V_SCOL_MAQUETTE_AP_EC_KEY + "." + VScolMaquetteApEc.MAQUETTE_EC_KEY }));

			aps = ec.objectsWithFetchSpecification(fetchSpec);
			if (myView.getRbApsNonCompletes().isSelected()) {
				aps = EOQualifier.filteredArrayWithQualifier(aps,
						EOQualifier.qualifierWithQualifierFormat(VMaquetteApGroupes.TOTAL_PREVU + " > " + VMaquetteApGroupes.TOTAL_RESERVE, null));
			}
		}
		eodAps.setObjectArray(aps);
		eodAps.clearSelection();
		myView.getMyEOTableAps().updateData();
	}

	private class ListenerTableHabilitations implements ZEOTableListener {

		public void onDbClick() {
			saisirParametresHabilitation();
		}

		public void onSelectionChanged() {
			updateCbParcours();
		}
	}

	private class ListenerTableAps implements ZEOTableListener {

		public void onDbClick() {
			afficherResasAp();
		}

		public void onSelectionChanged() {
			if (eodAps.selectedObjects() == null || eodAps.selectedObjects().count() == 0) {
				myView.btReserverAp.setEnabled(false);
				myView.btVoirResasAp.setEnabled(false);
				myView.btReserverAuto.setEnabled(false);
				myView.btSaisirParametresAp.setEnabled(false);
			}
			else {
				myView.btReserverAuto.setEnabled(true);
				myView.btSaisirParametresAp.setEnabled(true);
				if (eodAps.selectedObjects().count() == 1) {
					myView.btReserverAp.setEnabled(true);
					myView.btVoirResasAp.setEnabled(true);
				}
				else {
					myView.btReserverAp.setEnabled(false);
					myView.btVoirResasAp.setEnabled(false);
				}
			}
		}
	}

	private class DataTableCellApsRenderer extends ZEOTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if (isSelected) {
				label.setBackground(table.getSelectionBackground());
			}
			else {
				VMaquetteApGroupes selectedMaquetteApGroupes = (VMaquetteApGroupes) myView.getMyEOTableAps().getObjectAtRow(row);
				if (selectedMaquetteApGroupes.totalPrevu().compareTo(selectedMaquetteApGroupes.totalReserve()) == -1) {
					label.setBackground(COLOR_BGD_LEGEND_KO);
				}
				else {
					if (selectedMaquetteApGroupes.totalPrevu().compareTo(selectedMaquetteApGroupes.totalReserve()) == 0) {
						label.setBackground(COLOR_BGD_LEGEND_OK);
					}
					else {
						label.setBackground(table.getBackground());
					}
				}
			}
			if (isSelected) {
				label.setForeground(table.getSelectionForeground());
			}
			else {
				label.setForeground(table.getForeground());
			}

			return label;
		}

	}

}
