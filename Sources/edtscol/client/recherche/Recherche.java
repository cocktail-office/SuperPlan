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
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.RepartTypeGroupe;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.TypeRessource;
import org.cocktail.superplan.client.metier.TypeSalle;
import org.cocktail.superplan.client.metier.VGroupePersonne;
import org.cocktail.superplan.client.metier.VTreeObjet;
import org.cocktail.superplan.client.metier.VTreeSalles;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.eointerface.swing.EOView;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import edtscol.client.JTreeStructureUlr;
import edtscol.client.MainClient;
import edtscol.client.MainInterface;
import edtscol.client.TreeChangeSelectionListener;
import edtscol.client.TreeStructures;
import edtscol.client.URLUtilitaire;
import edtscol.client.gestionreservation.InspecteurCtrl;
import edtscol.client.panier.GestionPanier;
import edtscol.client.panier.Panier;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.Matrix;
import fr.univlr.utilities.SwapView;

public class Recherche extends EOInterfaceController implements TreeChangeSelectionListener {

	public static final int RECHERCHE = 1;
	public static final int INSPECTEUR = 2;
	public static final int AUTRE = 3;
	public static final int SEMESTRE_INSP = 4;

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public JButton bValide;
	public JComboBox popTypeRessource;
	public SwapView swapRecherche;
	private IndividuController individuCtrl;
	private RechercheSalle salleCtrl;
	private MatiereExtController matiereExtCtrl;
	private ExamenController examenCtrl;
	private ObjetController objetCtrl;
	private EOEditingContext eContext;
	public EOView treeChoixView;
	public EOTable tableIndividu, tableObjet, tableSalle;
	public MainInterface main;
	public Panier panier;
	public JTabbedPane tabRes;

	private int initType;
	public TreeStructures treeStructures;
	public JTreeStructureUlr jTreeStructureUlr;
	public EOMatrix typeChoix;
	public EODisplayGroup eodIndividu, eodObjet, eodSalle;

	private TypeRessource typeRessource;

	private final EOSortOrdering sortType = EOSortOrdering.sortOrderingWithKey(TypeRessource.TYPE_ORDRE_KEY, EOSortOrdering.CompareAscending);

	public Recherche(EOEditingContext eContext, MainInterface main, Panier panier, int typeRecherche) {
		super(eContext);
		this.eContext = editingContext();
		this.main = main;
		this.panier = panier;
		initType = typeRecherche;
		this.createSubComponents();
	}

	protected void controllerDidLoadArchive(@SuppressWarnings("rawtypes") NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		bValide.addActionListener(new ValidationAction());
		this.init();
	}

	protected void componentDidBecomeVisible() {
		WidgetHandler.setTableNotEditable(tableIndividu);
		WidgetHandler.setTableNotEditable(tableObjet);
		WidgetHandler.setTableNotEditable(tableSalle);
		if (typeRessource != null && typeRessource.typeCode().equals("PERSONNE")) {
			matrixTypeChoixChanged();
		}
		// changeResourceView();
	}

	private void init() {
		NSArray<TypeRessource> typeRes = TypeRessource.fetchTypeRessources(eContext, null, new NSArray<EOSortOrdering>(sortType));

		WidgetHandler.setObjectsToComboBox(typeRes, popTypeRessource);
		popTypeRessource.addActionListener(new JComboListener());

		if (popTypeRessource != null && typeRessource != null) {
			popTypeRessource.setSelectedItem(typeRessource);
		}

		Matrix.setSelectedIndex(0, typeChoix);
		Matrix.setListener(new MatrixListener(), typeChoix);

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				tabResSelectionChanged(index);
			}
		};
		tabRes.addChangeListener(changeListener);

		eodObjet.setSelectsFirstObjectAfterFetch(false);
		eodIndividu.setSelectsFirstObjectAfterFetch(false);
	}

	public boolean isSelectionParGroupe() {
		return tabRes.getSelectedIndex() == 1;
	}

	public NSArray<StructureUlr> getCStructuresServices(IndividuUlr user, String tRgp, String type) {

		NSMutableArray<StructureUlr> allStructs = new NSMutableArray<StructureUlr>();
		NSMutableArray<EOQualifier> args = new NSMutableArray<EOQualifier>();
		if (type.equals("")) {
			args.addObject(EOQualifier.qualifierWithQualifierFormat(StructureUlr.GRP_RESPONSABLE_KEY + " = %@",
					new NSArray<Integer>(user.noIndividu())));
			args.addObject(EOQualifier.qualifierWithQualifierFormat(StructureUlr.REPART_TYPE_GROUPE_KEY + "." + RepartTypeGroupe.TGRP_CODE_KEY
					+ " = %@", new NSArray<String>(tRgp)));
			EOQualifier qualifier = new EOAndQualifier(args);
			NSArray<StructureUlr> lesStructs = StructureUlr.fetchStructureUlrs(eContext, qualifier, null);
			for (int i = 0; i < lesStructs.count(); i++) {
				StructureUlr leStructure = lesStructs.objectAtIndex(i);
				allStructs.addObject(leStructure);
			}
		}

		args = new NSMutableArray<EOQualifier>();
		args.addObject(EOQualifier.qualifierWithQualifierFormat(type + ".individuUlr = %@", new NSArray<IndividuUlr>(user)));
		args.addObject(EOQualifier.qualifierWithQualifierFormat(StructureUlr.REPART_TYPE_GROUPE_KEY + "." + RepartTypeGroupe.TGRP_CODE_KEY + " = %@",
				new NSArray<String>(tRgp)));
		EOQualifier qualifier = new EOAndQualifier(args);
		NSArray<StructureUlr> lesStructures = StructureUlr.fetchStructureUlrs(eContext, qualifier, null);
		for (int i = 0; i < lesStructures.count(); i++) {
			StructureUlr leStructure = lesStructures.objectAtIndex(i);
			allStructs.addObject(leStructure);
		}
		NSMutableArray<StructureUlr> racine = new NSMutableArray<StructureUlr>();
		StructureUlr astruc = null;
		StructureUlr cstruc = null;
		for (int i = 0; i < allStructs.count(); i++) {
			astruc = allStructs.objectAtIndex(i);
			int j = 0;
			for (j = 0; j < allStructs.count(); j++) {
				cstruc = allStructs.objectAtIndex(j);
				if (astruc.cStructurePere().equals(cstruc.cStructure())) {
					break;
				}
			}
			if (j == allStructs.count()) {
				racine.addObject(astruc);
			}
		}
		return racine;
	}

	private NSArray<StructureUlr> mesGroupes() {
		IndividuUlr currentInd = (IndividuUlr) app.userInfo("individu");
		NSMutableArray<Integer> args = new NSMutableArray<Integer>();
		args.addObject(currentInd.noIndividu());
		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(StructureUlr.GRP_OWNER_KEY + " = %@", args);
		return StructureUlr.fetchStructureUlrs(eContext, qualifier, null);
	}

	private class MatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			matrixTypeChoixChanged();
		}
	}

	private void matrixTypeChoixChanged() {
		int choix = Matrix.getSelectedIndex(typeChoix);
		IndividuUlr currentInd = (IndividuUlr) app.userInfo("individu");
		switch (choix) {
		case 0:
			eodIndividu.setObjectArray(null);
			URLUtilitaire.informObservingAssociations(eodIndividu);
			afficherTreeStructure(currentInd, 5);
			tableIndividu.setVisible(true);
			break;
		case 1:
			eodIndividu.setObjectArray(null);
			URLUtilitaire.informObservingAssociations(eodIndividu);
			afficherTreeGroupesPersonnes(mesGroupes());
			tableIndividu.setVisible(false);
			break;
		case 2:
			eodIndividu.setObjectArray(null);
			URLUtilitaire.informObservingAssociations(eodIndividu);
			afficherTreeStructure(currentInd, 4);
			tableIndividu.setVisible(true);
			break;
		default:
			break;
		}
	}

	public InspecteurCtrl inspecteurCtrl() {
		try {
			return main.inspecteurCtrl();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getInitType() {
		return initType;
	}

	/**
	 * Permet de changer la disposition des sous composants du composant Recherche alors qu'il est déjà constuit : init() est déjà passé.
	 * ici on ne veux pas rappeler init
	 */
	public void setDisposition(int disposition) {
		matiereExtCtrl.changeDisposition(disposition);
	}

	public MatiereExtController getMatiereController() {
		return matiereExtCtrl;
	}

	public IndividuController getIndividuController() {
		return individuCtrl;
	}

	public RechercheSalle getRechercheSalle() {
		return salleCtrl;
	}

	public ExamenController getExamenController() {
		return examenCtrl;
	}

	public ObjetController getObjetController() {
		return objetCtrl;
	}

	/** renvoie les ressources selectionnes de maniere formate pour le panier */
	@SuppressWarnings("rawtypes")
	public NSArray selectedRessources(int typeRech) {
		switch (typeRech) {

		case GestionPanier.PERSONNE:
			return individuCtrl.selectedRessources();

		case GestionPanier.ENSEIGNEMENT:
			return matiereExtCtrl.selectedRessources();

		case GestionPanier.OBJET:
			return objetCtrl.selectedRessources();

		case GestionPanier.SALLE:
			return salleCtrl.selectedRessources();

		case GestionPanier.EXAMEN:
			return examenCtrl.selectedRessources();

		default:
			return new NSArray();
		}
	}

	public void createSubComponents() {
		if (individuCtrl == null) {
			individuCtrl = new IndividuController(eContext, this);
			EOArchive.loadArchiveNamed("IndividuController", individuCtrl, "edtscol.client.recherche", individuCtrl.disposableRegistry());
			individuCtrl.init();
		}
		if (salleCtrl == null) {
			salleCtrl = new RechercheSalle(eContext, this);
		}
		if (matiereExtCtrl == null) {
			matiereExtCtrl = new MatiereExtController(eContext, this);
			EOArchive.loadArchiveNamed("MatiereExtController", matiereExtCtrl, "edtscol.client.recherche", matiereExtCtrl.disposableRegistry());
			matiereExtCtrl.init();
			matiereExtCtrl.setInitType(initType);
		}
		if (examenCtrl == null) {
			examenCtrl = new ExamenController(eContext, this);
			EOArchive.loadArchiveNamed("ExamenController", examenCtrl, "edtscol.client.recherche", examenCtrl.disposableRegistry());
			examenCtrl.init();
		}
		if (objetCtrl == null) {
			objetCtrl = new ObjetController(eContext, this);
			EOArchive.loadArchiveNamed("ObjetController", objetCtrl, "edtscol.client.recherche", objetCtrl.disposableRegistry());
			objetCtrl.init();
		}
	}

	/** changement du type de ressource en cours de recherche */
	protected void changeResourceView() {
		if (popTypeRessource.getSelectedItem() == null) {
			return;
		}

		TypeRessource typeR = (TypeRessource) popTypeRessource.getSelectedItem();
		String type = typeR.typeCode();

		if (type == null) {
			return;
		}

		if (type.equals("PERSONNE")) {
			swapRecherche.setContentView(individuCtrl.currentView());
			typeChoix.setEnabled(true);
			typeChoix.setVisible(true);
			int choix = Matrix.getSelectedIndex(typeChoix);
			if (choix == 1) {
				tableIndividu.setVisible(false);
			}
			else {
				tableIndividu.setVisible(true);
			}
			URLUtilitaire.informObservingAssociations(eodIndividu);
			eodIndividu.setObjectArray(null);
			tableObjet.setVisible(false);
			tableSalle.setVisible(false);
			tabRes.setTitleAt(0, "Individus");
			tabRes.setTitleAt(1, "Groupes d'individus");
			tabRes.setEnabledAt(1, true);
		}

		if (type.equals("ENSEIGNEMENT")) {
			typeChoix.setVisible(false);
			tableObjet.setVisible(false);
			tableSalle.setVisible(false);
			tableIndividu.setVisible(false);
			swapRecherche.setContentView(matiereExtCtrl.currentView());
			tabRes.setTitleAt(0, "Enseignements");
			tabRes.setTitleAt(1, "");
			tabRes.setEnabledAt(1, false);
			tabRes.setSelectedIndex(0);
		}

		if (type.equals("OBJET")) {
			typeChoix.setVisible(false);
			swapRecherche.setContentView(objetCtrl.currentView());
			tableIndividu.setVisible(false);
			tableObjet.setVisible(true);
			URLUtilitaire.informObservingAssociations(eodObjet);
			tableSalle.setVisible(false);
			tabRes.setTitleAt(0, "Objets");
			tabRes.setTitleAt(1, "Groupes d'objets");
			tabRes.setEnabledAt(1, true);
		}

		if (type.equals("SALLE")) {
			typeChoix.setVisible(false);
			swapRecherche.setContentView(salleCtrl.currentView());
			tableIndividu.setVisible(false);
			tableObjet.setVisible(false);
			tableSalle.setVisible(true);
			tableSalle.setEnabled(true);
			eodSalle.setObjectArray(null);
			URLUtilitaire.informObservingAssociations(eodSalle);
			tabRes.setTitleAt(0, "Salles");
			tabRes.setTitleAt(1, "Groupes de salles");
			tabRes.setEnabledAt(1, true);
		}
	}

	private void tabResSelectionChanged(int tabIndex) {
		if (tabIndex == 1) {
			if (popTypeRessource.getSelectedItem() == null) {
				return;
			}
			TypeRessource typeR = (TypeRessource) popTypeRessource.getSelectedItem();
			String type = typeR.typeCode();
			if (type == null) {
				return;
			}
			if (type.equals("SALLE")) {
				afficherTreeStructure(null, 7);
			}
			if (type.equals("OBJET")) {
				afficherTreeStructure(null, 6);
			}
		}
	}

	public void afficherTreeGroupesPersonnes(NSArray<StructureUlr> lesStructs) {
		if (lesStructs == null || lesStructs.count() == 0) {
			treeChoixView.removeAll();
			treeChoixView.setLayout(new BorderLayout());
			EOQualifier globalQualifier = EOQualifier.qualifierWithQualifierFormat(VGroupePersonne.C_STRUCTURE_KEY + " = 'XXXXXXXX'", null);
			treeStructures = new TreeStructures(eContext, globalQualifier, null);
			treeStructures.addTreeChangeSelectionListener(this);
			treeChoixView.add(treeStructures, BorderLayout.CENTER);
			treeChoixView.validate();
			treeChoixView.setVisible(true);
			return;
		}
		String aStringForQualify = "";
		if (lesStructs != null || lesStructs.count() == 0) {
			for (int i = 0; i < lesStructs.count(); i++) {
				StructureUlr leStructure = lesStructs.objectAtIndex(i);
				if (i == 0) {
					aStringForQualify = VGroupePersonne.C_STRUCTURE_KEY + " = '" + leStructure.cStructure() + "'";
				}
				if (i > 0) {
					aStringForQualify += " or " + VGroupePersonne.C_STRUCTURE_KEY + " = '" + leStructure.cStructure() + "'";
				}
			}
		}

		EOQualifier globalQualifier = EOQualifier.qualifierWithQualifierFormat(aStringForQualify, null);
		treeStructures = new TreeStructures(eContext, globalQualifier, null);
		treeChoixView.removeAll();
		treeChoixView.setLayout(new BorderLayout());
		treeChoixView.add(treeStructures, BorderLayout.CENTER);
		treeChoixView.validate();
		treeChoixView.setVisible(true);
	}

	public void afficherTreeStructure(IndividuUlr individu, int type) {
		jTreeStructureUlr = new JTreeStructureUlr(eContext, type, individu);
		jTreeStructureUlr.addTreeChangeSelectionListener(this);
		WindowHandler.setWaitCursor(this);
		treeChoixView.removeAll();
		treeChoixView.setLayout(new BorderLayout());
		treeChoixView.add(jTreeStructureUlr, BorderLayout.CENTER);
		treeChoixView.validate();
		treeChoixView.setVisible(true);
		WindowHandler.setDefaultCursor(this);
	}

	public void remplirPersonne(EOGenericRecord struc) {
		NSMutableArray<EOGenericRecord> args = new NSMutableArray<EOGenericRecord>();
		args.addObject(struc);
		EOQualifier qualInd = EOQualifier.qualifierWithQualifierFormat(IndividuUlr.REPART_STRUCTURES_KEY + "." + RepartStructure.STRUCTURE_ULR_KEY
				+ " = %@", args);
		DBHandler.fetchDisplayGroup(eodIndividu, qualInd);
		eodIndividu.setSelectedObject(null);
		URLUtilitaire.informObservingAssociations(eodIndividu);
	}

	public void remplirObjet(EOGenericRecord record) {
		VTreeObjet vo = (VTreeObjet) record;
		if (vo.niveau().intValue() != 2) {
			return;
		}
		EOQualifier qualifier = new EOKeyValueQualifier(ResaObjet.RTO_KEY_KEY, EOKeyValueQualifier.QualifierOperatorEqual, vo.cle());
		// EOQualifier qualVisibilite = new EOKeyValueQualifier(ResaObjet.RO_ACCES_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
		// NSKeyValueCoding.NullValue);
		EOQualifier qualVisibilite = new EOKeyValueQualifier(ResaObjet.RO_RESERVABLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O");
		DBHandler.fetchDisplayGroup(eodObjet, new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualVisibilite, qualifier })));
		eodObjet.setSelectedObject(null);
		URLUtilitaire.informObservingAssociations(eodObjet);
	}

	public void remplirSalle(EOGenericRecord record) {
		NSMutableArray<TypeSalle> args = new NSMutableArray<TypeSalle>();
		VTreeSalles vs = (VTreeSalles) record;

		// UP - 27/02/2008 : on ne charge que c'est on a vraiement un type de salle : niveau 1 dans la vue
		if (vs != null && vs.niveau().intValue() == 1) {
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(TypeSalle.TSAL_LIBELLE_KEY + " = '" + vs.llStructure() + "'", null);
			NSArray<TypeSalle> aTs = TypeSalle.fetchTypeSalles(eContext, qualifier, null);
			args.addObject(aTs.objectAtIndex(0));
			EOQualifier qualSal = EOQualifier.qualifierWithQualifierFormat(Salles.SAL_RESERVABLE_KEY + " = 'O' AND " + Salles.C_LOCAL_KEY + " = '"
					+ vs.cStructurePere() + "' and " + Salles.TYPE_SALLE_KEY + " = %@", args);
			DBHandler.fetchDisplayGroup(eodSalle, qualSal);
			URLUtilitaire.informObservingAssociations(eodSalle);
		}
	}

	public void valider() {
		WindowHandler.setWaitCursor(this);
		try {
			if (main != null) {
				main.lesInfos = null;
			}
			TypeRessource typeR = (TypeRessource) popTypeRessource.getSelectedItem();
			String type = typeR.typeCode();

			if (type.equals("PERSONNE")) {
				individuCtrl.validerRecherche(Matrix.getSelectedIndex(typeChoix) == 1 ? false : true);
			}

			if (type.equals("ENSEIGNEMENT")) {
				matiereExtCtrl.validerRecherche();
			}

			if (type.equals("OBJET")) {
				objetCtrl.validerRecherche();
			}

			if (type.equals("SALLE")) {
				salleCtrl.validerRecherche();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			app.sendMailException(e);
		}
		WindowHandler.setDefaultCursor(this);
		WindowHandler.close(this);
	}

	public void annuler() {
		WindowHandler.setDefaultCursor(this);
		WindowHandler.close(this);
	}

	public void treeSelectionChanged(EventObject e) {
		EOGenericRecord record = (EOGenericRecord) e.getSource();
		TypeRessource typeR = (TypeRessource) popTypeRessource.getSelectedItem();
		String type = typeR.typeCode();

		if (type.equals("PERSONNE")) {
			if (record != null) {
				remplirPersonne(record);
			}
		}
		if (type.equals("OBJET")) {
			if (record != null) {
				remplirObjet(record);
			}
		}
		if (type.equals("SALLE")) {
			if (record != null) {
				remplirSalle(record);
			}
		}
	}

	/** Class de listener d'un bouton-menu */
	private class JComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeResourceView();
			TypeRessource type = (TypeRessource) popTypeRessource.getSelectedItem();
			if (type.typeCode().equals("PERSONNE")) {
				// afficherTreeGroupesPersonnes(mesGroupes());
				// tableIndividu.setVisible(false);
				// typeChoix.setVisible(true);
				// typeChoix.setEnabled(true);
				try {
					// Matrix.setSelectedIndex(1, typeChoix);
					Matrix.setSelectedIndex(0, typeChoix);
					matrixTypeChoixChanged();
				}
				catch (Exception ex) {
				}
			}
		}
	}

	private class ValidationAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			valider();
		}
	}

	public TypeRessource getTypeRessource() {
		return typeRessource;
	}

	public void setTypeRessource(TypeRessource typeRessource) {
		this.typeRessource = typeRessource;
	}

	public void setRechercheBloqueeSurTypeRessourceInitial(boolean isRechercheBloqueeSurTypeRessourceInitial) {
		if (isRechercheBloqueeSurTypeRessourceInitial) {
			popTypeRessource.setEnabled(false);
		}
	}

}
