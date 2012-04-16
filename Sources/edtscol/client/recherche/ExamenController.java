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

import javax.swing.JButton;
import javax.swing.JComboBox;

import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteEc;
import org.cocktail.superplan.client.metier.MaquetteRepartitionAp;
import org.cocktail.superplan.client.metier.ScolGroupeObjet;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.eointerface.swing.EOView;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;

import edtscol.client.MainClient;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;

/**
 * Composant utilise comme vue dans les controleurs Rechercher et Panier permet de choisir un examen --ExamenEntete-- a reserver. Doit
 * implementer la methode selectedResources, meme si aucune Interface n'est imposee.
 */

public class ExamenController extends EOInterfaceController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public JButton bSelectionner;
	public EODisplayGroup eodExamenEnt, eodMaquetteEc;
	public EOView boxExamen;
	public JComboBox comboAnnees, comboGroupes;
	public EOTable tableExamenEnt, tableMaquetteEc;

	private EOEditingContext eContext;
	private Recherche owner;

	public ExamenController(EOEditingContext eContext, Recherche owner) {
		super(eContext);
		this.eContext = eContext;
		this.owner = owner;
	}

	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
	}

	public void init() {
		initWidgets();
		NSArray formationAnnee = app.getFormationAnnees();
		comboGroupes.removeAllItems();

		comboAnnees.removeAllItems();
		for (int i = 0; i < formationAnnee.count(); i++) {
			FormationAnnee fAnnee = (FormationAnnee) formationAnnee.objectAtIndex(i);
			comboAnnees.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnees.setSelectedItem(fAnnee);
			}
		}

		eodMaquetteEc.setDelegate(this);
	}

	public FormationAnnee getFormationAnnee() {
		return (FormationAnnee) comboAnnees.getSelectedItem();
	}

	public EOView currentView() {
		return boxExamen;
	}

	public void affecterRessources(MaquetteEc ec, NSArray examens) {
		eodMaquetteEc.setObjectArray(new NSArray(ec));
		eodExamenEnt.setObjectArray(examens);
	}

	public void selectionnerEc() {
		ECSelector ecSelector = new ECSelector(eContext, this, owner);
		WindowHandler.runModal(ecSelector, "Selectionner un EC et un examen");
	}

	private void afficherGroupes(NSArray groupes) {
		comboGroupes.removeAllItems();
		comboGroupes.addItem("(Tous)");
		for (int i = 0; i < groupes.count(); i++) {
			comboGroupes.addItem(groupes.objectAtIndex(i));
		}
		comboGroupes.setSelectedItem("Tous");
	}

	private void afficherGroupesPourEc(MaquetteEc ec) {
		NSArray aps = (NSArray) ec.valueForKeyPath(MaquetteEc.MAQUETTE_REPARTITION_APS_KEY + "." + MaquetteRepartitionAp.MAQUETTE_AP_KEY);
		NSMutableArray grpObjs = new NSMutableArray();
		for (int i = 0; i < aps.count(); i++) {
			MaquetteAp currentAp = (MaquetteAp) aps.objectAtIndex(i);
			grpObjs.addObjectsFromArray((NSArray) currentAp.valueForKeyPath(MaquetteAp.SCOL_GROUPE_OBJETS_KEY + "."
					+ ScolGroupeObjet.SCOL_GROUPE_GRP_KEY));
		}
		afficherGroupes(grpObjs);
	}

	/** renvoie les ressources selectionnes a destination du panier */
	public NSArray selectedRessources() {
		NSMutableArray retour = new NSMutableArray();
		ExamenEntete currentExamen = (ExamenEntete) eodExamenEnt.selectedObject();
		if (currentExamen == null) {
			return retour;
		}
		String libelle = currentExamen.eentLibelle();
		Object grp = comboGroupes.getSelectedItem();

		if (!(grp instanceof String)) {
			Object[] objects = { "EXAMEN", libelle, NSKeyValueCoding.NullValue, grp, currentExamen };
			Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			NSDictionary ressource = new NSDictionary(objects, keys);
			retour.addObject(ressource);
		}

		else {
			Object[] objects = { "EXAMEN", libelle, NSKeyValueCoding.NullValue, "(Tous)", currentExamen };
			Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			NSDictionary ressource = new NSDictionary(objects, keys);
			retour.addObject(ressource);
		}
		return retour;
	}

	/** Callback */
	public void displayGroupDidChangeSelection(EODisplayGroup group) {
		if (group == eodMaquetteEc) {
			MaquetteEc maquetteEc = (MaquetteEc) eodMaquetteEc.selectedObject();
			if (maquetteEc != null) {
				afficherExamenPourEc(maquetteEc);
				afficherGroupesPourEc(maquetteEc);
			}
		}
	}

	/** affiche les examens prevus pour cet EC */
	protected void afficherExamenPourEc(MaquetteEc ec) {
		EOQualifier qualExam = DBHandler.getSimpleQualifier(ExamenEntete.EC_KEY, ec);
		NSArray examens = ExamenEntete.fetchExamenEntetes(eContext, qualExam, null);
		eodExamenEnt.setObjectArray(examens);
		eodExamenEnt.updateDisplayedObjects();
	}

	private void initWidgets() {
		WidgetHandler.setTableNotEditable(tableExamenEnt);
		WidgetHandler.setTableNotEditable(tableMaquetteEc);
		WidgetHandler.decorateButton("Selectionner un EC et ses examens...", "Rechercher Examen", "find", bSelectionner);
	}

}
