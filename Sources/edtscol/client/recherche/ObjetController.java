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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.metier.ImplantationGeo;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.ResaObjet;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.eointerface.swing.EOView;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotificationCenter;

import edtscol.client.MainClient;
import edtscol.client.composant.ObjetTreeModel;
import edtscol.client.panier.GestionPanier;
import fr.univlr.common.utilities.WidgetHandler;

public class ObjetController extends EOInterfaceController {

	public EOView boxObjet;
	public EODisplayGroup eodResaObjet;
	public EOTable tableResaObjet;
	public JComboBox comboCategObjet, comboTypeObjet;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private Recherche recherche;
	private EOEditingContext eContext;

	private JComboBox comboImplantationGeo;
	private ObjetTreeModel objetModel;
	private JTree treeObjets;

	public IndividuUlr agent;
	private ResaObjet selectedObjet;

	public ObjetController(EOEditingContext eContext, Recherche recherche) {
		super(eContext);
		this.eContext = eContext;
		this.recherche = recherche;
	}

	protected void init() {
		JScrollPane scollPaneTreeObjets = new JScrollPane();

		treeObjets = new JTree();

		scollPaneTreeObjets.setViewportView(treeObjets);

		scollPaneTreeObjets.setPreferredSize(boxObjet.getPreferredSize());

		objetModel = new ObjetTreeModel(eContext, true);
		treeObjets.setModel(objetModel);

		comboImplantationGeo = new JComboBox();
		SalleFactory salleFactory = new SalleFactory(eContext);
		NSArray localisations = salleFactory.getLocalisationsGeo();

		WidgetHandler.setObjectsToComboBox(localisations, comboImplantationGeo);

		ComboListener comboListener = new ComboListener();

		comboImplantationGeo.addActionListener(comboListener);
		processChangementImplantationGeo();

		boxObjet.removeAll();
		boxObjet.setLayout(new BorderLayout());

		boxObjet.add(comboImplantationGeo, BorderLayout.NORTH);
		boxObjet.add(scollPaneTreeObjets, BorderLayout.CENTER);

		treeObjets.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				treeObjetsSelectionChanged();
			}
		});

	}

	private void treeObjetsSelectionChanged() {
		Object node = treeObjets.getLastSelectedPathComponent();

		if (node == null) {
			selectedObjet = null;
		}
		else
			if (node instanceof ResaObjet) {
				selectedObjet = (ResaObjet) node;
			}
			else {
				selectedObjet = null;
			}
	}

	/** retourne la vue courante */
	public EOView currentView() {
		return boxObjet;
	}

	public NSArray selectedRessources() {

		NSMutableArray retour = new NSMutableArray();
		if (selectedObjet != null) {
			String libelle = selectedObjet.roLibelle1();
			NSDictionary ressource = null;
			Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			Object[] objects = { "OBJET", libelle, NSKeyValueCoding.NullValue, "1", selectedObjet };
			ressource = new NSDictionary(objects, keys);
			retour.addObject(ressource);
		}

		return retour;
	}

	/** valider la recherche */
	public void validerRecherche() {
		if (recherche.tabRes.getSelectedIndex() == 0) {
			if (selectedObjet != null) {
				Object[] objets = { new Integer(GestionPanier.OBJET), selectedObjet };
				String[] clefs = { "type", "resRecord" };

				NSDictionary<String, Object> dico = new NSDictionary<String, Object>(objets, clefs);
				NSNotificationCenter.defaultCenter().postNotification("validerRessource", dico);
				return;
			}
		}
		if (recherche.tabRes.getSelectedIndex() == 1) {
			if (recherche.eodObjet.selectedObjects().count() > 0) {
				recherche.main.planningOfObjets(recherche.eodObjet.selectedObjects());
			}
			else {
				recherche.main.planningOfObjets(recherche.eodObjet.displayedObjects());
			}
		}
	}

	private void processChangementImplantationGeo() {
		ImplantationGeo impGeo = (ImplantationGeo) comboImplantationGeo.getSelectedItem();
		objetModel = new ObjetTreeModel(eContext, true);
		objetModel.setFilterByImplantationGeo(impGeo);
		treeObjets.setModel(objetModel);
	}

	/** Class de listener d'un bouton-menu */
	private class ComboListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboImplantationGeo) {
				processChangementImplantationGeo();
			}
		}

	}

}
