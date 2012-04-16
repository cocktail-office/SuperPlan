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

import javax.swing.JButton;
import javax.swing.JTextField;

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.StructuresAutorisees;
import org.cocktail.superplan.client.metier.VGroupePersonne;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EODialogs;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
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
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotificationCenter;

import edtscol.client.MainClient;
import edtscol.client.panier.GestionPanier;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.cri.javaclient.CRITree;
import fr.univlr.utilities.Matrix;
import fr.univlr.utilities.SwapView;

public class IndividuController extends EOInterfaceController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private static final int INDIVIDU = 0;
	private static final int GROUPE = 1;

	private int typeRecherche;

	public EODisplayGroup eodIndividuUlr;
	public SwapView swapRecherche;
	public JTextField tfNom, tfPrenom;
	public CRITree treeGroupes;
	public EOView viewGroupes, boxIndividu, boxRechercheIndGrp;
	public JButton btInspecteur, bRecherche;
	public EOMatrix matIndividuGrp, matEnsEtud;
	public EOTable tableIndividuUlr;

	private EOEditingContext eContext;
	private Recherche owner;

	private boolean treeGroupInitialized = false;

	public IndividuController(EOEditingContext eContext, Recherche owner) {
		super(eContext);
		this.eContext = eContext;
		this.owner = owner;
	}

	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		this.rechercheIndividus();
		Matrix.setSelectedIndex(0, matIndividuGrp);
		Matrix.setListener(new MatrixListener(), matIndividuGrp);

		// matIndividuGrp.setVisible(false);

		Matrix.setListener(new MatrixListenerEnsEtud(), matEnsEtud);
	}

	public void init() {
		initWidgets();
		rechercheIndividus();
		eodIndividuUlr.insertObjectAtIndex(app.userInfo("individu"), 0);
		eodIndividuUlr.updateDisplayedObjects();
		Matrix.setSelectedIndex(0, matIndividuGrp);
		Matrix.setListener(new MatrixListener(), matIndividuGrp);
		Matrix.setListener(new MatrixListenerEnsEtud(), matEnsEtud);
		Matrix.setSelectedIndex(0, matEnsEtud);
	}

	/** rechercher un individu de l'annuaire */
	public void rechercheIndividus() {
		swapRecherche.setContentView(boxIndividu);
		typeRecherche = INDIVIDU;
	}

	/** rechercher un groupe de l'annuaire */
	private void rechercheGroupes() {
		if (!treeGroupInitialized) {
			this.initTreeGroupes();
			treeGroupInitialized = true;
		}
		swapRecherche.setContentView(viewGroupes);
		typeRecherche = GROUPE;
	}

	public EOView currentView() {
		return boxRechercheIndGrp;
	}

	/** renvoi la ressource selectionnee */
	public NSArray<NSDictionary<String, Object>> selectedRessources() {
		NSMutableArray<NSDictionary<String, Object>> retour = new NSMutableArray<NSDictionary<String, Object>>();

		if (typeRecherche == INDIVIDU) {
			NSArray<IndividuUlr> currentIndividus = eodIndividuUlr.selectedObjects();
			for (int i = 0; i < currentIndividus.count(); i++) {
				IndividuUlr currentIndividu = currentIndividus.objectAtIndex(i);
				String libelle = currentIndividu.nomUsuel() + " " + currentIndividu.prenom();
				Object[] objects = { "PERSONNE", libelle, NSKeyValueCoding.NullValue, "1", currentIndividu };
				String[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
				NSDictionary<String, Object> ressource = new NSDictionary<String, Object>(objects, keys);
				retour.addObject(ressource);
			}
		}

		if (typeRecherche == GROUPE) {
			if (treeGroupes.selectedObject() != null) {
				StructuresAutorisees currentGroupe = (StructuresAutorisees) treeGroupes.selectedObject();
				String libelle = currentGroupe.libelleGroupe();
				Object[] objects = { "GROUPE", libelle, NSKeyValueCoding.NullValue, "GROUPE", currentGroupe };
				String[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
				NSDictionary<String, Object> ressource = new NSDictionary<String, Object>(objects, keys);
				retour.addObject(ressource);
			}
		}
		return retour;
	}

	/** recherche d'un individu */
	public void rechercherIndividu() {

		WindowHandler.setWaitCursor(this.component());

		String nom = tfNom.getText().toUpperCase();
		String prenom = tfPrenom.getText().toUpperCase();
		if (nom.equals("") && prenom.equals("")) {
			return;
		}
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.addObject(new EOKeyValueQualifier(IndividuUlr.NOM_USUEL_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*" + nom + "*"));
		quals.addObject(new EOKeyValueQualifier(IndividuUlr.PRENOM_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*" + prenom + "*"));
		quals.addObject(new EOKeyValueQualifier(IndividuUlr.TEM_VALIDE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O"));
		if (Matrix.getSelectedIndex(matEnsEtud) == 0) {
			// non etudiants
			quals.addObject(IndividuUlr.getQualifierForNonStudent());
		}
		else {
			// etudiants
			quals.addObject(IndividuUlr.getQualifierForStudent());
		}

		NSArray<EOSortOrdering> sort = new NSArray<EOSortOrdering>(new EOSortOrdering[] { IndividuUlr.SORT_NOM, IndividuUlr.SORT_PRENOM });
		DBHandler.fetchDisplayGroup(eodIndividuUlr, new EOAndQualifier(quals), sort, true);
		eodIndividuUlr.updateDisplayedObjects();

		WindowHandler.setDefaultCursor(this.component());
	}

	public void validerRecherche(boolean useEodIndividu) {
		if (owner.tabRes.getSelectedIndex() == 0) {
			Object individu = eodIndividuUlr.selectedObject();
			if (individu != null) {
				Object[] objets = { new Integer(GestionPanier.PERSONNE), (IndividuUlr) individu };
				String[] clefs = { "type", "resRecord" };

				NSDictionary<String, Object> dico = new NSDictionary<String, Object>(objets, clefs);
				NSNotificationCenter.defaultCenter().postNotification("validerRessource", dico);
				return;
			}
		}
		if (owner.tabRes.getSelectedIndex() == 1) {
			if (useEodIndividu) {
				if (owner.eodIndividu.selectedObjects().count() > 0) {
					owner.main.planningOfIndividus(owner.eodIndividu.selectedObjects());
				}
				else {
					owner.main.planningOfIndividus(owner.eodIndividu.displayedObjects());
				}
			}
			else {
				if (owner.treeStructures.selectedNotes().count() > 0) {
					rechercheTousLesIndividus();
				}
			}
		}
	}

	public void rechercheTousLesIndividus() {
		WindowHandler.setWaitCursor(this.component());
		NSArray lesIndividusChoisis = owner.treeStructures.selectedNotes();
		NSArray<VGroupePersonne> allFeuilles = owner.treeStructures.getAllFeuilles_MultiSelect(lesIndividusChoisis);
		NSMutableArray<EOQualifier> args = new NSMutableArray<EOQualifier>();
		EOQualifier qualInd = null;
		if (allFeuilles.count() == 0) {
			WindowHandler.setDefaultCursor(this.component());
			EODialogs.runInformationDialog("ATTENTION", "Aucune personne pour cette selection");
			return;
		}
		for (int i = 0; i < allFeuilles.count(); i++) {
			qualInd = EOQualifier.qualifierWithQualifierFormat(IndividuUlr.PERS_ID_KEY + " = " + allFeuilles.objectAtIndex(i).persId(), null);
			args.addObject(qualInd);
		}
		EOQualifier globalQualifier = new EOOrQualifier(args);
		NSArray<EOSortOrdering> ordering = new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(IndividuUlr.NOM_USUEL_KEY,
				EOSortOrdering.CompareAscending));
		NSArray<IndividuUlr> allIndividus = IndividuUlr.fetchIndividuUlrs(eContext, globalQualifier, ordering);
		owner.main.planningOfIndividus(allIndividus);
		WindowHandler.setDefaultCursor(this.component());
	}

	protected void initTreeGroupes() {
		EOQualifier firstColumnQualifier = EOQualifier.qualifierWithQualifierFormat(StructuresAutorisees.NUMERO_GROUPE_KEY + " = "
				+ StructuresAutorisees.NUMERO_PERE_KEY, null);
		EOQualifier restrictionQualifier = EOQualifier.qualifierWithQualifierFormat(StructuresAutorisees.ACCES_KEY + " = '+' or "
				+ StructuresAutorisees.AUTORISE_KEY + " = " + ((IndividuUlr) app.userInfo("individu")).noIndividu(), null);
		treeGroupes.setQualifierForFirstColumn(firstColumnQualifier);
		treeGroupes.setRestrictionQualifier(restrictionQualifier);
		treeGroupes.setFieldForDisplay(StructuresAutorisees.LIBELLE_GROUPE_KEY);
		treeGroupes.setKeyParentKey(StructuresAutorisees.NUMERO_GROUPE_KEY, StructuresAutorisees.NUMERO_PERE_KEY);
		if (!treeGroupes.initialize(false)) {
			NSLog.out.appendln(this.getClass().getName() + ".initTreeGroupes() - Erreur : l'initialisation du treeView a echoue");
		}
		treeGroupes.setRootVisible(true);
	}

	public class MatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Matrix.getSelectedIndex(matIndividuGrp) == 0) {
				rechercheIndividus();
			}
			else {
				rechercheGroupes();
			}
		}
	}

	public class MatrixListenerEnsEtud implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			bRecherche.doClick();
		}
	}

	private void initWidgets() {
		WidgetHandler.decorateButton("rechercher", null, "find", bRecherche);
		WidgetHandler.setTableNotEditable(tableIndividuUlr);
	}

	public boolean isTypeRechercheGroupe() {
		return typeRecherche == GROUPE;
	}
}
