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
package edtscol.client.gestioncontraintes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.swing.ZEOTable.ZEOTableListener;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import edtscol.client.MainClient;

public class GestionContraintesCtrl {

	private static GestionContraintesCtrl sharedInstance;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EOEditingContext ec;
	private Component parent;

	private EODisplayGroup eodIndividus = new EODisplayGroup();
	private GestionContraintesView myView;

	private GestionContraintesIndisponibilitesCtrl gcIndispIndividus;

	public GestionContraintesCtrl(EOEditingContext editingContext, Component parent) {
		super();

		myView = new GestionContraintesView(eodIndividus);
		ec = editingContext;
		this.parent = parent;
		init();
		initIndividus();
	}

	public void onWindowOpened() {
		gcIndispIndividus.onWindowOpened();
	}

	private void init() {
		// myView.getJTabbedPane1().add("Individus", GestionContraintesIndisponibilitesCtrl.sharedInstance(ec).getMyView());

		myView.getBtFermer().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				myView.setVisible(false);
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

		myView.getCbAnnee().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				gcIndispIndividus.setFormationAnnee((FormationAnnee) myView.getCbAnnee().getSelectedItem());
				updateIndividu();
			}
		});
	}

	public static GestionContraintesCtrl sharedInstance(EOEditingContext editingContext, Component parent) {
		if (sharedInstance == null) {
			sharedInstance = new GestionContraintesCtrl(editingContext, parent);
		}
		return sharedInstance;
	}

	// Individu...

	private void initIndividus() {
		gcIndispIndividus = new GestionContraintesIndisponibilitesCtrl(ec, true);

		myView.getjPanelIndisponibilitesIndividus().setLayout(new BorderLayout());
		myView.getjPanelIndisponibilitesIndividus().removeAll();
		myView.getjPanelIndisponibilitesIndividus().add(gcIndispIndividus.getMyView(), BorderLayout.CENTER);

		gcIndispIndividus.setFormationAnnee((FormationAnnee) myView.getCbAnnee().getSelectedItem());

		myView.getMyEOTableIndividus().addListener(new ListenerIndividuTable());
		EOSortOrdering sortIndividus = EOSortOrdering.sortOrderingWithKey(IndividuUlr.NOM_PRENOM_KEY, EOSortOrdering.CompareAscending);
		eodIndividus.setSortOrderings(new NSArray<EOSortOrdering>(sortIndividus));

		RechercheIndividuListener myRechercheListener = new RechercheIndividuListener();
		myView.getTfRechercheNom().addActionListener(myRechercheListener);
		myView.getTfRecherchePrenom().addActionListener(myRechercheListener);
		myView.getRbRechercheEtudiants().addActionListener(myRechercheListener);
		myView.getRbRechercheNonEtudiants().addActionListener(myRechercheListener);
		myView.getBoutonRecherche().addActionListener(myRechercheListener);

		eodIndividus.setObjectArray(new NSArray<IndividuUlr>((IndividuUlr) app.userInfo("individu")));
		myView.getMyEOTableIndividus().updateData();
		eodIndividus.setSelectionIndexes(new NSArray<Integer>(Integer.valueOf(0)));
	}

	private void updateIndividu() {
		gcIndispIndividus.updateSemaines((IndividuUlr) eodIndividus.selectedObject());
	}

	private class RechercheIndividuListener implements ActionListener {
		public RechercheIndividuListener() {
			super();
		}

		public void actionPerformed(ActionEvent anAction) {
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(IndividuUlr.TEM_VALIDE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O"));
			if (StringCtrl.isEmpty(myView.getTfRechercheNom().getText()) == false) {
				quals.addObject(new EOKeyValueQualifier(IndividuUlr.NOM_USUEL_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*"
						+ myView.getTfRechercheNom().getText() + "*"));
			}
			if (StringCtrl.isEmpty(myView.getTfRecherchePrenom().getText()) == false) {
				quals.addObject(new EOKeyValueQualifier(IndividuUlr.PRENOM_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*"
						+ myView.getTfRecherchePrenom().getText() + "*"));
			}
			if (myView.getRbRechercheNonEtudiants().isSelected()) {
				quals.addObject(IndividuUlr.getQualifierForNonStudent());
			}
			else {
				quals.addObject(IndividuUlr.getQualifierForStudent());
			}
			eodIndividus.setObjectArray(IndividuUlr.fetchIndividuUlrs(ec, new EOAndQualifier(quals), null));
			myView.getMyEOTableIndividus().updateData();
			eodIndividus.setSelectionIndexes(new NSArray<Integer>(Integer.valueOf(0)));
		}
	}

	private class ListenerIndividuTable implements ZEOTableListener {

		public void onDbClick() {
		}

		public void onSelectionChanged() {
			updateIndividu();
			if (app.isAppDroitsSurContraintesIndividus()) {
				boolean shouldSetEnabled = false;
				IndividuUlr individuSelected = (IndividuUlr) eodIndividus.selectedObject();
				if (individuSelected != null) {
					IndividuUlr agent = (IndividuUlr) app.userInfo("individu");
					if (agent != null) {
						if (individuSelected.equals(agent) || agent.isRespOuSecrFor(individuSelected)) {
							shouldSetEnabled = true;
						}
					}
				}
				gcIndispIndividus.setEnabled(shouldSetEnabled);
			}
		}
	}

	public FormationAnnee formationAnnee() {
		return (FormationAnnee) myView.getCbAnnee().getSelectedItem();
	}

	public void open() {
		myView.setLocation(parent.getX() + 20, parent.getY() + 20);
		myView.setVisible(true);
	}

	public void close() {
		myView.setVisible(false);
	}

	public GestionContraintesView getMyView() {
		return myView;
	}

}
