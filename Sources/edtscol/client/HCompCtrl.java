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
import javax.swing.JDialog;
import javax.swing.JTextField;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.handlers.HCompHandler;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.HcompRecup;
import org.cocktail.superplan.client.metier.HoraireCode;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.metier.VIntervenantAp;
import org.cocktail.superplan.client.metier.VParcoursAp;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.FileHandler;
import fr.univlr.utilities.Matrix;
import fr.univlr.utilities.ULRDateTimeWindow;

public class HCompCtrl extends EOInterfaceController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public JButton bAfficher, bDebut, bFin, bImprimer, bVoirDetails, bRechercheHab;
	public JComboBox comboAnnee, comboParcours, comboSemestre, comboHoraireCode;
	public EODisplayGroup eodHabilitation, eodIndividuTotal, eodMaquetteAp;
	public EOTable tableHabilitation, tableIndividuTotal, tableMaquetteAp;
	public JTextField tDebut, tDiplome, tFin, tGrade;
	public EOMatrix matTypeHcomp, matPrefDip;

	private EOEditingContext eContext;
	private EnseignementFactory ensFactory;
	private HCompHandler hcompHandler;
	private DetailHCompHandler detailHCompHandler;

	private EOSortOrdering sortAp, sortHab;

	public HCompCtrl(EOEditingContext substitutionEditingContext) {
		super(substitutionEditingContext);
		this.eContext = substitutionEditingContext;
		ensFactory = new EnseignementFactory(eContext);
		hcompHandler = new HCompHandler(eContext);
	}

	protected void controllerDidLoadArchive(NSDictionary objects) {
		super.controllerDidLoadArchive(objects);
		detailHCompHandler = new DetailHCompHandler(this);
		initWidgets();
		init();
	}

	protected void componentDidBecomeVisible() {
		afficherDiplomesPreferes();
	}

	protected HCompHandler hCompHandler() {
		return hcompHandler;
	}

	protected EnseignementFactory enseignementFactory() {
		return ensFactory;
	}

	private void init() {
		sortHab = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		sortAp = EOSortOrdering.sortOrderingWithKey(MaquetteAp.MAP_LIBELLE_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);

		comboAnnee.removeAllItems();
		comboParcours.removeAllItems();
		comboSemestre.removeAllItems();
		comboHoraireCode.removeAllItems();

		WidgetHandler.setTableNotEditable(tableHabilitation);
		WidgetHandler.setTableNotEditable(tableIndividuTotal);
		WidgetHandler.setTableNotEditable(tableMaquetteAp);

		NSArray formationAnnee = app.getFormationAnnees();
		if (formationAnnee.count() == 0) {
			WindowHandler.showError("Pas d'annee scolaire disponible");
			return;
		}

		for (int i = 0; i < formationAnnee.count(); i++) {
			FormationAnnee fAnnee = (FormationAnnee) formationAnnee.objectAtIndex(i);
			comboAnnee.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnee.setSelectedItem(fAnnee);
			}
		}

		NSMutableArray horaireCodes = new NSMutableArray();
		horaireCodes.addObject("Tous");
		horaireCodes.addObjectsFromArray(app.getHoraireCodesPresenciel());
		WidgetHandler.setObjectsToComboBox(horaireCodes, comboHoraireCode);

		JComboListener comboListener = new JComboListener();
		comboAnnee.addActionListener(comboListener);
		comboParcours.addActionListener(comboListener);
		comboSemestre.addActionListener(comboListener);
		comboHoraireCode.addActionListener(comboListener);

		eodHabilitation.setDelegate(this);
		eodMaquetteAp.setDelegate(this);
		eodIndividuTotal.setDelegate(this);

		Matrix.setSelectedIndex(0, matTypeHcomp);
		Matrix.setListener(new MatrixListener(), matTypeHcomp);
	}

	private void initWidgets() {
		WidgetHandler.decorateButton("Calendrier : debut", null, "cal", bDebut);
		WidgetHandler.decorateButton("Calendrier : fin", null, "cal", bFin);
		WidgetHandler.decorateButton(null, null, "find", bRechercheHab);
		WidgetHandler.decorateButton("Imprimer : fichier PDF", "Imprimer fichier PDF", "smallprint", bImprimer);

	}

	/**
	 * netoie l'interface niveau 0 : vide les parcours - semestres - ap niveau 1 : vide les semestres - ap niveau 2 : vide les ap : (non
	 * utilise)
	 */
	private void _clean(int niveau) {
		if (niveau == 0) {
			comboParcours.removeAllItems();
		}
		if (niveau < 2) {
			comboSemestre.removeAllItems();
		}
		WidgetHandler.flushDisplayGroup(eodMaquetteAp);
	}

	public void displayGroupDidChangeSelection(EODisplayGroup group) {
		if (group == eodHabilitation) {
			_clean(0);
			FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
			if (selectedHabilitation != null && selectedHabilitation.formationSpecialisation() != null) {
				this.afficherParcours(selectedHabilitation.formationSpecialisation());
			}
		}
		if (group == eodMaquetteAp) {
			rechercher();
		}
		if (group == eodIndividuTotal) {
			if (eodIndividuTotal.selectedObjects() != null && eodIndividuTotal.selectedObjects().count() > 1) {
				if (Matrix.getSelectedIndex(matTypeHcomp) == 0) {
					bVoirDetails.setText("Valider");
				}
				else {
					bVoirDetails.setText("Dévalider");
				}
			}
			else {
				if (Matrix.getSelectedIndex(matTypeHcomp) == 0) {
					bVoirDetails.setText("Voir détail / Valider");
				}
				else {
					bVoirDetails.setText("Voir détail / Dévalider");
				}
			}
		}
	}

	private void afficherDiplomesPreferes() {
		_clean(0);
		IndividuUlr utilisateur = (IndividuUlr) app.userInfo("individu");
		EOQualifier qIndividu = DBHandler.getSimpleQualifier(PrefScol.INDIVIDU_KEY, utilisateur);
		NSArray preferences = DBHandler.fetchData(eContext, PrefScol.ENTITY_NAME, qIndividu);
		eodHabilitation.setObjectArray((NSArray) preferences.valueForKey(PrefScol.HABILITATION_KEY));
		eodHabilitation.setSelectedObject(null);
		eodHabilitation.setDelegate(this);
		WidgetHandler.informObservingAssociation(eodHabilitation);
		WindowHandler.setDefaultCursor(this);
	}

	public void rechercherHabilitation() {
		String grade = tGrade.getText();
		String diplome = tDiplome.getText();
		FormationAnnee fAnnee = (FormationAnnee) comboAnnee.getSelectedItem();
		NSArray droits = (NSArray) app.userInfo("droits");

		if (fAnnee == null || droits.count() == 0) {
			return;
		}

		WindowHandler.setWaitCursor(this);

		Number droit = (Number) droits.objectAtIndex(0);
		eodHabilitation.setSortOrderings(new NSArray(sortHab));
		eodHabilitation.setObjectArray(ensFactory.getHabilitationsPourDroit(diplome, grade, fAnnee.fannKey(), droit));
		WidgetHandler.informObservingAssociation(eodHabilitation);
		WindowHandler.setDefaultCursor(this);
		displayGroupDidChangeSelection(eodHabilitation);
	}

	/** ouvre le dialog de selection de date de debut de recherche */
	public void choisirDebut(Object sender) {
		new ULRDateTimeWindow(this, (JDialog) WindowHandler.getWindowFromController(this), sender, "setDebut");
	}

	/** ouvre le dialog de selection de date de fin de recherche */
	public void choisirFin(Object sender) {
		new ULRDateTimeWindow(this, (JDialog) WindowHandler.getWindowFromController(this), sender, "setFin");
	}

	/**
	 * callback d'effectation de date de debut
	 * 
	 * @see ULRDateTimeWindow.stateChanged
	 */
	public void setDebut(String debut) {
		tDebut.setText(debut);
	}

	/**
	 * callback d'effectation de date de fin *
	 * 
	 * @see ULRDateTimeWindow.stateChanged
	 */
	public void setFin(String fin) {
		tFin.setText(fin);
	}

	/** imprimerElementIndividuTotal */
	public void imprimerElementIndividuTotal() {
		NSMutableDictionary liste = new NSMutableDictionary();
		if (_remplirInfosContextePourImpression(liste)) {
			WindowHandler.setWaitCursor(this);
		}
		else {
			return;
		}

		if (eodMaquetteAp.selectedObjects() == null || eodMaquetteAp.selectedObjects().count() != 1) {
			return;
		}
		MaquetteAp selectedAp = (MaquetteAp) eodMaquetteAp.selectedObject();
		if (selectedAp == null) {
			return;
		}

		liste.setObjectForKey(selectedAp.mapLibelle(), "ap");

		NSArray totaux = eodIndividuTotal.displayedObjects();

		NSMutableArray lignes = new NSMutableArray();
		for (int i = 0; i < totaux.count(); i++) {
			NSDictionary element = (NSDictionary) totaux.objectAtIndex(i);
			NSMutableDictionary dico = new NSMutableDictionary();
			dico.setObjectForKey(element.objectForKey("nom") + " " + element.objectForKey("prenom"), "nom_complet");
			dico.setObjectForKey(element.objectForKey("total").toString(), "nombre_heures");
			lignes.addObject(dico);
		}

		liste.setObjectForKey(lignes, "lignes");

		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSData pdfData = (NSData) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestGetPdfHcompAp",
				new Class[] { NSDictionary.class }, new Object[] { liste }, false);
		if (pdfData != null) {
			try {
				FileHandler fileHandler = new FileHandler();
				String filePath = fileHandler.dataToXXX(pdfData, "hcomp", "pdf");
				fileHandler.openFile(filePath);
			}
			catch (Exception e) {
				e.printStackTrace();
				WindowHandler.showError(e.getMessage());
			}
		}
		else {
			WindowHandler.showError("Echec de l'impression");
		}
		WindowHandler.setDefaultCursor(this);
	}

	/** imprimerHeuresValides */
	protected void imprimerHeuresValides(MaquetteAp ap) {
		NSMutableDictionary liste = new NSMutableDictionary();
		if (!_remplirInfosContextePourImpression(liste)) {
			return;
		}

		NSArray lignesHcomp = eodIndividuTotal.selectedObjects();
		if (lignesHcomp == null || lignesHcomp.count() != 1) {
			return;
		}
		NSDictionary ligneHComp = (NSDictionary) lignesHcomp.objectAtIndex(0);
		if (ligneHComp == null) {
			return;
		}
		NSArray data = (NSArray) ligneHComp.valueForKey("hcomp");
		liste.setObjectForKey(ligneHComp.objectForKey("prenom") + " " + ligneHComp.objectForKey("nom"), "nom_complet");
		liste.setObjectForKey(String.valueOf(((Double) ligneHComp.objectForKey("total")).doubleValue()), "total");

		NSMutableArray lignes = new NSMutableArray();

		liste.setObjectForKey(ap.mhcoCode(), "type_ap");
		liste.setObjectForKey(ap.mapLibelle(), "libelle_ap");

		for (int i = 0; i < data.count(); i++) {
			HcompRecup currentHC = (HcompRecup) data.objectAtIndex(i);
			lignes.addObject(currentHC.valueForKeyPath("periodicite.dateDeb"));
			lignes.addObject(currentHC.valueForKeyPath("periodicite.dateFin"));
		}
		liste.setObjectForKey(lignes, "periodicites");
		_requestPdfDatailHeures(liste);
	}

	/** imprimerHeuresNonValides */
	protected void imprimerHeuresNonValides(MaquetteAp ap) {
		NSMutableDictionary liste = new NSMutableDictionary();
		if (!_remplirInfosContextePourImpression(liste)) {
			return;
		}

		NSArray lignesHcomp = eodIndividuTotal.selectedObjects();
		if (lignesHcomp == null || lignesHcomp.count() != 1) {
			return;
		}
		NSDictionary ligneHComp = (NSDictionary) lignesHcomp.objectAtIndex(0);
		if (ligneHComp == null) {
			return;
		}
		NSArray data = (NSArray) ligneHComp.valueForKey("periodicites");
		liste.setObjectForKey(ligneHComp.objectForKey("prenom") + " " + ligneHComp.objectForKey("nom"), "nom_complet");
		liste.setObjectForKey(String.valueOf(((Double) ligneHComp.objectForKey("total")).doubleValue()), "total");

		NSMutableArray lignes = new NSMutableArray();

		liste.setObjectForKey(ap.mhcoCode(), "type_ap");
		liste.setObjectForKey(ap.mapLibelle(), "libelle_ap");

		for (int i = 0; i < data.count(); i++) {
			Periodicite currentPer = (Periodicite) data.objectAtIndex(i);
			lignes.addObject(currentPer.dateDeb());
			lignes.addObject(currentPer.dateFin());
		}
		liste.setObjectForKey(lignes, "periodicites");

		_requestPdfDatailHeures(liste);
	}

	private void _requestPdfDatailHeures(NSDictionary dico) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSData pdfData = (NSData) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestPdfDetailHeures",
				new Class[] { NSDictionary.class }, new Object[] { dico }, false);
		if (pdfData != null) {
			try {
				FileHandler fileHandler = new FileHandler();
				String filePath = fileHandler.dataToXXX(pdfData, "hcomp", "pdf");
				fileHandler.openFile(filePath);
			}
			catch (Exception e) {
				e.printStackTrace();
				WindowHandler.showError(e.getMessage());
			}
		}
		else {
			WindowHandler.showError("Echec de l'impression");
		}

	}

	/** cette methode met dans le dico les infos communes lors de l'impression (nom du diplome etc) */
	private boolean _remplirInfosContextePourImpression(NSMutableDictionary liste) {

		FormationHabilitation hab = (FormationHabilitation) eodHabilitation.selectedObject();
		MaquetteAp ap = (MaquetteAp) eodMaquetteAp.selectedObject();
		MaquetteParcours parcours = (MaquetteParcours) comboParcours.getSelectedItem();
		MaquetteSemestre semestre = (MaquetteSemestre) comboSemestre.getSelectedItem();
		FormationAnnee fAnnee = (FormationAnnee) comboAnnee.getSelectedItem();

		if (hab == null || ap == null || parcours == null || semestre == null || fAnnee == null) {
			return false;
		}

		StringBuffer diplome = new StringBuffer();
		diplome.append(hab.valueForKeyPath(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY));

		if (hab.valueForKeyPath(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "." + FormationSpecialisation.FSPN_LIBELLE_KEY) != null) {
			diplome.append(", ");
			diplome.append(hab.valueForKeyPath(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "." + FormationSpecialisation.FSPN_LIBELLE_KEY));
		}

		liste.setObjectForKey(diplome.toString(), "diplome");
		liste.setObjectForKey(parcours.mparLibelle(), "parcours");
		liste.setObjectForKey("Semestre " + semestre.msemOrdre(), "semestre");
		String annee = fAnnee.fannDebut() + "-" + fAnnee.fannFin();
		liste.setObjectForKey(annee, "annee");

		if (Matrix.getSelectedIndex(matTypeHcomp) == 0) {
			liste.setObjectForKey("[Non valid\u00e9es]", "etat");
		}
		else {
			liste.setObjectForKey("[Valid\u00e9es]", "etat");
		}

		return true;
	}

	/** rechercher */
	public void rechercher() {
		if (Matrix.getSelectedIndex(matTypeHcomp) == 0) {
			rechercherPeriodicites(false);
		}
		else {
			rechercherHcomp();
		}
	}

	/**
	 * rechercherPeriodicites sur la periode donnee etatValidation indique si on cherche les periodicites validees par hcomp ou non
	 */
	public void rechercherPeriodicites(boolean etatValidation) {

		WidgetHandler.flushDisplayGroup(eodIndividuTotal);

		WindowHandler.setWaitCursor(this);

		String formatDate = "%d/%m/%Y %H:%M:%S";
		NSTimestamp debut = null, fin = null;
		if (StringCtrl.isEmpty(tDebut.getText()) == false) {
			debut = FormatHandler.strToDate(StringCtrl.normalize(tDebut.getText()) + " 00:00:00", formatDate);
		}
		if (StringCtrl.isEmpty(tFin.getText()) == false) {
			fin = FormatHandler.strToDate(StringCtrl.normalize(tFin.getText()) + " 23:59:59", formatDate);
		}

		for (int i = 0; i < eodMaquetteAp.selectedObjects().count(); i++) {
			MaquetteAp ap = (MaquetteAp) eodMaquetteAp.selectedObjects().objectAtIndex(i);
			EOQualifier qualIntervenant = DBHandler.getSimpleQualifier("maquetteAp", ap);
			NSArray tmp = DBHandler.fetchData(eContext, "VIntervenantAp", qualIntervenant);
			tmp = (NSArray) tmp.valueForKey("individuUlr");

			for (int j = 0; j < tmp.count(); j++) {
				IndividuUlr individu = (IndividuUlr) tmp.objectAtIndex(j);
				NSArray periodicites = hcompHandler.getPeriodicites(ap, individu, debut, fin, etatValidation);
				double total = hcompHandler.getTotalFromPeriodicites(periodicites);
				Object[] values = new Object[] { ap, individu, individu.nomUsuel(), individu.prenom(), periodicites, new Double(total) };
				Object[] keys = new Object[] { "ap", "individu", "nom", "prenom", "periodicites", "total" };
				NSDictionary element = new NSDictionary(values, keys);
				eodIndividuTotal.insertObjectAtIndex(element, 0);
			}
		}

		WindowHandler.setDefaultCursor(this);
	}

	/** rechercherHcomp : recherche parmis les hcomp valides mais non encore importes */
	private void rechercherHcomp() {

		WidgetHandler.flushDisplayGroup(eodIndividuTotal);

		WindowHandler.setWaitCursor(this);

		String formatDate = "%d/%m/%Y %H:%M:%S";
		NSTimestamp debut = null, fin = null;
		if (StringCtrl.isEmpty(tDebut.getText()) == false) {
			debut = FormatHandler.strToDate(StringCtrl.normalize(tDebut.getText()) + " 00:00:00", formatDate);
		}
		if (StringCtrl.isEmpty(tFin.getText()) == false) {
			fin = FormatHandler.strToDate(StringCtrl.normalize(tFin.getText()) + " 23:59:59", formatDate);
		}

		for (int i = 0; i < eodMaquetteAp.selectedObjects().count(); i++) {
			MaquetteAp ap = (MaquetteAp) eodMaquetteAp.selectedObjects().objectAtIndex(i);
			EOQualifier qualIntervenant = DBHandler.getSimpleQualifier(VIntervenantAp.MAQUETTE_AP_KEY, ap);
			NSArray tmp = DBHandler.fetchData(eContext, VIntervenantAp.ENTITY_NAME, qualIntervenant);
			tmp = (NSArray) tmp.valueForKey(VIntervenantAp.INDIVIDU_ULR_KEY);

			NSArray hcomp;
			for (int j = 0; j < tmp.count(); j++) {
				IndividuUlr individu = (IndividuUlr) tmp.objectAtIndex(j);
				hcomp = hcompHandler.getHcomp(ap, individu, debut, fin, false); // false=non importees hcrTag=0
				double total = hcompHandler.getTotalFromHcomp(hcomp);
				Object[] values = new Object[] { ap, individu, individu.nomUsuel(), individu.prenom(), hcomp, new Double(total) };
				Object[] keys = new Object[] { "ap", "individu", "nom", "prenom", "hcomp", "total" };
				NSDictionary element = new NSDictionary(values, keys);
				eodIndividuTotal.insertObjectAtIndex(element, 0);
			}
		}

		WindowHandler.setDefaultCursor(this);

	}

	/** voirDetails */
	public void voirDetails() {
		// NSDictionary element = (NSDictionary) eodIndividuTotal.selectedObject();
		// if (element == null) {
		// return;
		// }
		NSArray elements = eodIndividuTotal.selectedObjects();
		if (elements == null || elements.count() == 0) {
			return;
		}
		if (Matrix.getSelectedIndex(matTypeHcomp) == 0) {
			if (elements.count() == 1) {
				// on ouvre un panneau pour afficher le détail avant de valider...
				NSDictionary dico = (NSDictionary) elements.objectAtIndex(0);
				detailHCompHandler.voirDetailNonValide((MaquetteAp) dico.valueForKey("ap"), (IndividuUlr) dico.valueForKey("individu"),
						(NSArray) dico.valueForKey("periodicites"));
			}
			else {
				// on valide tout sans panneau de détail
				if (!WindowHandler.showConfirmation("Vous allez valider toutes les heures des personnes sélectionnées... OK ?")) {
					return;
				}
				for (int i = 0; i < elements.count(); i++) {
					NSDictionary dico = (NSDictionary) elements.objectAtIndex(i);
					validerHcomp((MaquetteAp) dico.valueForKey("ap"), (IndividuUlr) dico.valueForKey("individu"),
							(NSArray) dico.valueForKey("periodicites"));
				}
				rechercher();
			}
		}
		else {
			if (elements.count() == 1) {
				// on ouvre un panneau pour afficher le détail avant de valider...
				NSDictionary dico = (NSDictionary) elements.objectAtIndex(0);
				detailHCompHandler.voirDetailValide((MaquetteAp) dico.valueForKey("ap"), (IndividuUlr) dico.valueForKey("individu"),
						(NSArray) dico.valueForKey("hcomp"));
			}
			else {
				// on valide tout sans panneau de détail
				if (!WindowHandler.showConfirmation("Vous allez valider toutes les heures des personnes sélectionnées... OK ?")) {
					return;
				}
				NSMutableArray hcomp = new NSMutableArray();
				for (int i = 0; i < elements.count(); i++) {
					NSDictionary dico = (NSDictionary) elements.objectAtIndex(i);
					hcomp.addObjectsFromArray((NSArray) dico.valueForKey("hcomp"));
				}
				if (devaliderHcomp(hcomp)) {
					rechercher();
				}
			}
		}
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
		_clean(1);
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

		this.afficherAp();
		WindowHandler.setDefaultCursor(this);

	}

	public void afficherAp() {
		FormationAnnee selectedAnnee = (FormationAnnee) comboAnnee.getSelectedItem();
		Number fannKey = selectedAnnee.fannKey();
		MaquetteSemestre selectedSemestre = (MaquetteSemestre) comboSemestre.getSelectedItem();

		if (selectedAnnee != null && selectedSemestre != null) {

			String mhcoCode = null;
			if (comboHoraireCode.getSelectedItem() == null) {
				return;
			}
			if (comboHoraireCode.getSelectedItem().getClass().equals(HoraireCode.class)) {
				HoraireCode selection = (HoraireCode) comboHoraireCode.getSelectedItem();
				if (selection != null) {
					mhcoCode = selection.mhcoCode();
				}
			}

			NSMutableArray sumQualifiers = new NSMutableArray();
			if (mhcoCode != null) {
				sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(MaquetteAp.MHCO_CODE_KEY + " = '" + mhcoCode + "'", null));
			}

			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.FANN_KEY_KEY
					+ " = %@ and " + MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + " = %@", new NSArray(new Object[] { fannKey,
					selectedSemestre })));

			EOQualifier qualAp = new EOAndQualifier(sumQualifiers);
			DBHandler.fetchDisplayGroup(eodMaquetteAp, qualAp, sortAp);
			WidgetHandler.informObservingAssociation(eodMaquetteAp);

			if (eodMaquetteAp.displayedObjects().count() > 0) {
				bAfficher.setEnabled(true);
			}
			else {
				bAfficher.setEnabled(false);
			}
		}
	}

	// vers HCompHandler
	protected boolean validerHcomp(MaquetteAp ap, IndividuUlr individu, NSArray periodicites) {
		if (individu == null || ap == null) {
			return false;
		}

		boolean status = true;
		try {
			hcompHandler.validerHComp(individu, ap, periodicites);
		}
		catch (Exception e) {
			WindowHandler.showError(e.getMessage());
			e.printStackTrace();
			hcompHandler.revert();
			status = false;
		}
		return status;
	}

	// vers HCompHandler
	protected boolean devaliderHcomp(NSArray hcomp) {
		boolean status = true;
		try {
			hcompHandler.devaliderHcomp(hcomp);
		}
		catch (Exception e) {
			WindowHandler.showError(e.getMessage());
			e.printStackTrace();
			hcompHandler.revert();
			status = false;
		}
		return status;
	}

	private class MatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			rechercher();
		}
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
				else
					if (e.getSource() == comboSemestre || e.getSource() == comboHoraireCode) {
						afficherAp();
					}
		}
	}
}