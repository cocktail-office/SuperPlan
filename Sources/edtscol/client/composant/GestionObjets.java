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
package edtscol.client.composant;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.factory.VerificationFactory;
import org.cocktail.superplan.client.metier.ImplantationGeo;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.ResaFamilleObjet;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaObjetDepositaire;
import org.cocktail.superplan.client.metier.ResaObjetReserve;
import org.cocktail.superplan.client.metier.ResaTypeObjet;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.VTreeObjet;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eodistribution.client.EODistributedDataSource;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;

public class GestionObjets extends javax.swing.JDialog {

	// Variables declaration - do not modify
	private javax.swing.JButton bAddGroupeDepo;
	private javax.swing.JButton bAddGroupeUtilisateur;
	private javax.swing.JButton bQuitter;
	private javax.swing.JButton bRemoveGroupeDepo;
	private javax.swing.JButton bRemoveGroupeUtilisateur;
	private javax.swing.JComboBox comboImplantationGeo;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JPanel panelTableDepos;
	private javax.swing.JPanel panelTableUtilisateurs;
	private javax.swing.JTabbedPane tabAdminUtilObjet;
	private javax.swing.JTree treeObjets;
	// End of variables declaration

	private EOEditingContext eContext;
	private Object selectedNode;

	private EODisplayGroup eodResaObjetDepositaire, eodResaObjetReserve;
	private ZEOTable tableResaObjetDepositaire, tableResaObjetReserve;

	private ObjetTreeModel objetModel;
	private Component parent;

	private MainClient app;

	/** Creates new form GestionObjets */
	public GestionObjets(MainClient app, Component parent, EOEditingContext context) {
		super((Frame) parent, "Gestion des objets", true);
		eContext = context;
		this.app = app;
		this.parent = parent;
		initComponents();
		initExtra();
	}

	public void open() {
		this.setLocation(parent.getX() + 20, parent.getY() + 20);
		this.setVisible(true);
	}

	public void close() {
		this.setVisible(false);
	}

	private boolean isDroitGestionObjets() {
		Boolean gestionObjets = (Boolean) app.userInfo("gestionObjets");
		return (gestionObjets != null && gestionObjets.booleanValue());
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		treeObjets = new javax.swing.JTree();
		tabAdminUtilObjet = new javax.swing.JTabbedPane();
		jPanel2 = new javax.swing.JPanel();
		panelTableUtilisateurs = new javax.swing.JPanel();
		bAddGroupeUtilisateur = new javax.swing.JButton();
		bRemoveGroupeUtilisateur = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		panelTableDepos = new javax.swing.JPanel();
		bAddGroupeDepo = new javax.swing.JButton();
		bRemoveGroupeDepo = new javax.swing.JButton();
		bQuitter = new javax.swing.JButton();
		comboImplantationGeo = new javax.swing.JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

		jScrollPane1.setViewportView(treeObjets);

		org.jdesktop.layout.GroupLayout panelTableUtilisateursLayout = new org.jdesktop.layout.GroupLayout(panelTableUtilisateurs);
		panelTableUtilisateurs.setLayout(panelTableUtilisateursLayout);
		panelTableUtilisateursLayout.setHorizontalGroup(panelTableUtilisateursLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 464, Short.MAX_VALUE));
		panelTableUtilisateursLayout.setVerticalGroup(panelTableUtilisateursLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				0, 290, Short.MAX_VALUE));

		bAddGroupeUtilisateur.setText("Ajouter un groupe");
		bAddGroupeUtilisateur.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAddGroupeUtilisateurActionPerformed(evt);
			}
		});

		bRemoveGroupeUtilisateur.setText("Retirer un groupe");
		bRemoveGroupeUtilisateur.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bRemoveGroupeUtilisateurActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
						jPanel2Layout
								.createSequentialGroup()
								.addContainerGap()
								.add(jPanel2Layout
										.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
										.add(org.jdesktop.layout.GroupLayout.TRAILING, panelTableUtilisateurs,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(jPanel2Layout.createSequentialGroup().add(bAddGroupeUtilisateur)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 185, Short.MAX_VALUE)
												.add(bRemoveGroupeUtilisateur))).addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				jPanel2Layout
						.createSequentialGroup()
						.addContainerGap(17, Short.MAX_VALUE)
						.add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(bAddGroupeUtilisateur)
								.add(bRemoveGroupeUtilisateur))
						.add(18, 18, 18)
						.add(panelTableUtilisateurs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap()));

		tabAdminUtilObjet.addTab("Utilisateurs", jPanel2);

		org.jdesktop.layout.GroupLayout panelTableDeposLayout = new org.jdesktop.layout.GroupLayout(panelTableDepos);
		panelTableDepos.setLayout(panelTableDeposLayout);
		panelTableDeposLayout.setHorizontalGroup(panelTableDeposLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 464,
				Short.MAX_VALUE));
		panelTableDeposLayout.setVerticalGroup(panelTableDeposLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 290,
				Short.MAX_VALUE));

		bAddGroupeDepo.setText("Ajouter un groupe");
		bAddGroupeDepo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAddGroupeDepoActionPerformed(evt);
			}
		});

		bRemoveGroupeDepo.setText("Retirer un groupe");
		bRemoveGroupeDepo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bRemoveGroupeDepoActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.add(jPanel1Layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(panelTableDepos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.add(jPanel1Layout.createSequentialGroup().add(bAddGroupeDepo)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 185, Short.MAX_VALUE).add(bRemoveGroupeDepo)))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap(17, Short.MAX_VALUE)
						.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(bAddGroupeDepo).add(bRemoveGroupeDepo))
						.add(18, 18, 18)
						.add(panelTableDepos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap()));

		tabAdminUtilObjet.addTab("Dépositaires", jPanel1);

		bQuitter.setText("Quitter");
		bQuitter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bQuitterActionPerformed(evt);
			}
		});

		comboImplantationGeo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.add(24, 24, 24)
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 221,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(comboImplantationGeo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
								.add(tabAdminUtilObjet, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
								.add(layout.createSequentialGroup().add(bQuitter).addContainerGap()))));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout
										.createSequentialGroup()
										.add(17, 17, 17)
										.add(tabAdminUtilObjet, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 419,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(bQuitter))
								.add(layout
										.createSequentialGroup()
										.add(26, 26, 26)
										.add(comboImplantationGeo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(18, 18, 18)
										.add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	private void initExtra() {

		bAddGroupeDepo.setEnabled(false);
		bAddGroupeUtilisateur.setEnabled(false);
		bRemoveGroupeDepo.setEnabled(false);
		bRemoveGroupeUtilisateur.setEnabled(false);

		objetModel = new ObjetTreeModel(eContext, false);
		treeObjets.setModel(objetModel);
		treeObjets.addMouseListener(new MouseListener());

		treeObjets.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				treeObjetsSelectionChanged();
			}
		});

		SalleFactory salleFactory = new SalleFactory(eContext);
		NSArray<ImplantationGeo> localisations = salleFactory.getLocalisationsGeo();

		WidgetHandler.setObjectsToComboBox(localisations, comboImplantationGeo);

		ComboListener comboListener = new ComboListener();

		comboImplantationGeo.addActionListener(comboListener);
		processChangementImplantationGeo();

		// creation table dépositaires
		EODistributedDataSource doctorantSource = new EODistributedDataSource(eContext, ResaObjetDepositaire.ENTITY_NAME);
		eodResaObjetDepositaire = new EODisplayGroup();
		eodResaObjetDepositaire.setDataSource(doctorantSource);
		// eodDoctorant.fetch();
		java.util.Vector<ZEOTableModelColumn> columns = new java.util.Vector<ZEOTableModelColumn>();
		columns.add(new ZEOTableModelColumn(eodResaObjetDepositaire, ResaObjetDepositaire.STRUCTURE_ULR_KEY + "." + StructureUlr.LL_STRUCTURE_KEY,
				"Groupes dépositaires"));

		ZEOTableModel resaObjetDepositaireModel = new ZEOTableModel(eodResaObjetDepositaire, columns);
		tableResaObjetDepositaire = new ZEOTable(resaObjetDepositaireModel);

		JScrollPane tableScroll = new JScrollPane(tableResaObjetDepositaire);
		tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tableScroll.setPreferredSize(panelTableDepos.getPreferredSize());
		panelTableDepos.setLayout(new BorderLayout());
		panelTableDepos.add(tableScroll, BorderLayout.NORTH);

		// creation table usagers (resa_objet_reserve)

		EODistributedDataSource resaObjetReserveSource = new EODistributedDataSource(eContext, ResaObjetReserve.ENTITY_NAME);
		eodResaObjetReserve = new EODisplayGroup();
		eodResaObjetReserve.setDataSource(resaObjetReserveSource);
		columns = new Vector<ZEOTableModelColumn>();
		columns.add(new ZEOTableModelColumn(eodResaObjetReserve, ResaObjetReserve.STRUCTURE_ULR_KEY + "." + StructureUlr.LL_STRUCTURE_KEY,
				"Groupes utilisateurs"));

		ZEOTableModel resaObjetReserveModel = new ZEOTableModel(eodResaObjetReserve, columns);
		tableResaObjetReserve = new ZEOTable(resaObjetReserveModel);

		tableScroll = new JScrollPane(tableResaObjetReserve);
		tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tableScroll.setPreferredSize(panelTableUtilisateurs.getPreferredSize());
		panelTableUtilisateurs.setLayout(new BorderLayout());
		panelTableUtilisateurs.add(tableScroll, BorderLayout.NORTH);

	}

	private void treeObjetsSelectionChanged() {
		Object node = treeObjets.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		if ((node instanceof String) && node.equals(ObjetTreeModel.ROOT_NODE)) {
			selectedNode = null;
		}
		else {
			selectedNode = node;
			if (selectedNode instanceof ResaObjet) {
				ResaObjet objet = (ResaObjet) selectedNode;

				bAddGroupeDepo.setEnabled(true);
				bAddGroupeUtilisateur.setEnabled(true);
				bRemoveGroupeDepo.setEnabled(true);
				bRemoveGroupeUtilisateur.setEnabled(true);

				EOQualifier qual = new EOKeyValueQualifier(ResaObjetReserve.RESA_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, objet);
				DBHandler.fetchDisplayGroup(eodResaObjetReserve, qual);
				tableResaObjetReserve.updateData();

				DBHandler.fetchDisplayGroup(eodResaObjetDepositaire, qual);
				tableResaObjetDepositaire.updateData();
			}
			else {
				bAddGroupeDepo.setEnabled(false);
				bAddGroupeUtilisateur.setEnabled(false);
				bRemoveGroupeDepo.setEnabled(false);
				bRemoveGroupeUtilisateur.setEnabled(false);
			}
		}
	}

	private void bAddGroupeDepoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAddGroupeDepoActionPerformed
		addGroupeDepositaire();
	}// GEN-LAST:event_bAddGroupeDepoActionPerformed

	private void bRemoveGroupeDepoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bRemoveGroupeDepoActionPerformed
		removeGroupeDepositaire();
	}// GEN-LAST:event_bRemoveGroupeDepoActionPerformed

	private void bAddGroupeUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {
		addGroupeUtilisateur();
	}

	private void bRemoveGroupeUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {
		removeGroupeUtilisateur();
	}

	private void bQuitterActionPerformed(java.awt.event.ActionEvent evt) {
		setVisible(false);
	}

	private void addGroupeDepositaire() {
		if (isDroitGestionObjets() == false) {
			WindowHandler.showError("Vous n'avez pas le droit de gérer les dépositaires d'objet !");
			return;
		}
		ResaObjet objet = (ResaObjet) selectedNode;
		if (objet == null) {
			WindowHandler.showError("Aucun objet n'est selectionné dans l'arborescence à gauche !");
			return;
		}

		StructureUlrSelector structureSelector = new StructureUlrSelector(this, eContext, true);
		structureSelector.setVisible(true);
		StructureUlr structDepos = structureSelector.getSelectedStructure();

		if (structDepos == null) {
			WindowHandler.showError("Aucune structure n'a été selectionnée !");
			return;
		}

		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat(ResaObjetDepositaire.STRUCTURE_ULR_KEY + " = %@ and "
				+ ResaObjetDepositaire.RESA_OBJET_KEY + " = %@", new NSArray<Object>(new Object[] { structDepos, objet }));
		NSArray<ResaObjetDepositaire> data = ResaObjetDepositaire.fetchResaObjetDepositaires(eContext, qual, null);
		if (data.count() > 0) {
			WindowHandler.showError("Ce groupe est déjà dépositaire de cet objet!");
			return;
		}

		if (structDepos != null) {
			ResaObjetDepositaire depos = ResaObjetDepositaire.createResaObjetDepositaire(eContext, new NSTimestamp());
			depos.setStructureUlrRelationship(structDepos);
			depos.setResaObjetRelationship(objet);

			try {
				eContext.saveChanges();
			}
			catch (Exception e) {
				e.printStackTrace();
				WindowHandler.showError("Erreur d'ajout de dépositaire.");
				e.printStackTrace();
			}

			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(ResaObjetDepositaire.RESA_OBJET_KEY + " = %@", new NSArray<Object>(
					selectedNode));
			DBHandler.fetchDisplayGroup(eodResaObjetDepositaire, qualifier);
			tableResaObjetDepositaire.updateData();

		}
	}

	private void removeGroupeDepositaire() {
		ResaObjetDepositaire resaDepos = (ResaObjetDepositaire) eodResaObjetDepositaire.selectedObject();
		if (resaDepos != null) {
			if (isDroitGestionObjets() == false) {
				WindowHandler.showError("Vous n'avez pas le droit de gérer les dépositaires d'objet !");
				return;
			}
			resaDepos.setResaObjetRelationship(null);
			resaDepos.setStructureUlrRelationship(null);
			eContext.deleteObject(resaDepos);
			try {
				eContext.saveChanges();
			}
			catch (Exception e) {
				e.printStackTrace();
				WindowHandler.showError("Erreur de suppression de dépositaire.");
				e.printStackTrace();
			}
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(ResaObjetDepositaire.RESA_OBJET_KEY + " = %@", new NSArray<Object>(
					selectedNode));
			DBHandler.fetchDisplayGroup(eodResaObjetDepositaire, qualifier);
			tableResaObjetDepositaire.updateData();

		}

	}

	private void addGroupeUtilisateur() {
		ResaObjet objet = (ResaObjet) selectedNode;
		if (objet == null) {
			WindowHandler.showError("Aucun objet n'est selectionné dans l'arborescence à gauche !");
			return;
		}
		if (isDroitGestionObjets() == false
				&& VerificationFactory.testIndividuEstDepositaireObjet(eContext, (IndividuUlr) app.userInfo("individu"), objet) == false) {
			WindowHandler.showError("Vous n'avez aucun droit sur cet objet (vous n'êtes pas dépositaire) !");
			return;
		}

		StructureUlrSelector structureSelector = new StructureUlrSelector(this, eContext, false);
		structureSelector.setVisible(true);
		StructureUlr structDepos = structureSelector.getSelectedStructure();

		if (structDepos == null) {
			WindowHandler.showError("Aucun groupe n'a été choisi !");
			return;
		}

		EOQualifier qual = EOQualifier.qualifierWithQualifierFormat(ResaObjetReserve.STRUCTURE_ULR_KEY + " = %@ and "
				+ ResaObjetReserve.RESA_OBJET_KEY + " = %@", new NSArray<Object>(new Object[] { structDepos, objet }));
		NSArray<ResaObjetReserve> data = ResaObjetReserve.fetchResaObjetReserves(eContext, qual, null);
		if (data.count() > 0) {
			WindowHandler.showError("Ce groupe est déjà utilisateur de cet objet !");
			return;
		}

		if (structDepos != null) {
			ResaObjetReserve reserve = ResaObjetReserve.createResaObjetReserve(eContext, new NSTimestamp());
			reserve.setStructureUlrRelationship(structDepos);
			reserve.setResaObjetRelationship(objet);

			try {
				eContext.saveChanges();
			}
			catch (Exception e) {
				e.printStackTrace();
				WindowHandler.showError("Erreur d'ajout du groupe utilisateur.");
				e.printStackTrace();
			}

			EOQualifier qualifier = EOQualifier
					.qualifierWithQualifierFormat(ResaObjetReserve.RESA_OBJET_KEY + " = %@", new NSArray<ResaObjet>(objet));
			DBHandler.fetchDisplayGroup(eodResaObjetReserve, qualifier);
			tableResaObjetReserve.updateData();

			System.out.println("eodResaObjetReserve=" + eodResaObjetReserve.displayedObjects().count());

		}
	}

	private void removeGroupeUtilisateur() {
		ResaObjetReserve reserve = (ResaObjetReserve) eodResaObjetReserve.selectedObject();
		if (reserve != null) {
			if (isDroitGestionObjets() == false
					&& VerificationFactory.testIndividuEstDepositaireObjet(eContext, (IndividuUlr) app.userInfo("individu"), reserve.resaObjet()) == false) {
				WindowHandler.showError("Vous n'avez aucun droit sur cet objet (vous n'êtes pas dépositaire) !");
				return;
			}
			eContext.deleteObject(reserve);
			try {
				eContext.saveChanges();
			}
			catch (Exception e) {
				e.printStackTrace();
				WindowHandler.showError("Erreur de suppression de dépositaire.");
				e.printStackTrace();
			}

			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(ResaObjetReserve.RESA_OBJET_KEY + " = %@", new NSArray<Object>(
					selectedNode));
			DBHandler.fetchDisplayGroup(eodResaObjetReserve, qualifier);
			tableResaObjetReserve.updateData();

		}
	}

	/** creation d'objets ou de types d'objets */

	public void creationObjet(MouseEvent e) {
		if (selectedNode == null) {
			System.out.println("popupFamille");
			popupFamille(e);
		}
		else
			if (selectedNode instanceof ResaFamilleObjet) {
				System.out.println("popupType");
				popupType(e);
			}
			else
				if (selectedNode instanceof ResaTypeObjet) {
					System.out.println("popupObjet");
					popupObjet(e);
				}
				else
					if (selectedNode instanceof ResaObjet) {
						System.out.println("popupObjetModif");
						popupObjetEdit(e);
					}
	}

	public void editObjet(MouseEvent e) {
		if (selectedNode == null) {
			return;
		}
		if (selectedNode instanceof ResaObjet) {
			editObjetPanel();
		}
	}

	private void popupFamille(MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		menu.add(new AMenuAction(AMenuAction.ACTION_AJOUT_FAMILLE, "Ajouter une famille d'objets"));
		menu.show(treeObjets, e.getX(), e.getY());
	}

	private void popupType(MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		menu.add(new AMenuAction(AMenuAction.ACTION_AJOUT_TYPE, "Ajouter un Type d'Objets"));
		menu.show(treeObjets, e.getX(), e.getY());
	}

	private void popupObjet(MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		menu.add(new AMenuAction(AMenuAction.ACTION_AJOUT_OBJET, "Ajouter un Objet"));
		menu.show(treeObjets, e.getX(), e.getY());
	}

	private void popupObjetEdit(MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		menu.add(new AMenuAction(AMenuAction.ACTION_EDIT_OBJET, "Editer l'objet"));
		menu.show(treeObjets, e.getX(), e.getY());
	}

	/** class pour le bouton droit de la souris sur l'arbre */
	protected class MouseListener extends MouseAdapter {

		public MouseListener() {
			super();
		}

		public void mousePressed(MouseEvent e) {

			try {
				if (e.getButton() == MouseEvent.BUTTON3) {
					creationObjet(e);
				}

				if (e.getButton() == MouseEvent.BUTTON2) {
				}

				if (e.getButton() == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2) {
						editObjet(e);
					}
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				WindowHandler.showError(ex.getMessage());
			}
		}
	}

	public void ajouterObjetPanel() {
		if (isDroitGestionObjets() == false) {
			WindowHandler.showError("Vous n'avez pas le droit de gérer les objets !");
			return;
		}
		CreationObjetCtrl objCtrl = new CreationObjetCtrl(this, "Saisie d'un objet", eContext, null);
		objCtrl.setVisible(true);
		String label = objCtrl.getLabel();
		Salles salle = objCtrl.getSalle();
		boolean isReservable = objCtrl.isReservable();

		if (label != null) {
			ResaTypeObjet typeObjet = (ResaTypeObjet) selectedNode;
			ResaObjet newObjet = ResaObjet.createResaObjet(eContext, label, typeObjet);
			newObjet.setRoLibelle2(label);
			newObjet.setRoReservable(isReservable ? "O" : "N");
			if (salle != null) {
				newObjet.setSalleRelationship(salle);
			}

			try {
				eContext.lock();
				eContext.saveChanges();
			}
			catch (Exception e) {
				WindowHandler.showError(e.getMessage());
				e.printStackTrace();
			}
			finally {
				eContext.unlock();
			}
			refreshSelectedNode();
		}
	}

	public void editObjetPanel() {
		ResaObjet objet = (ResaObjet) selectedNode;
		if (objet == null) {
			return;
		}
		if (isDroitGestionObjets() == false
				&& VerificationFactory.testIndividuEstDepositaireObjet(eContext, (IndividuUlr) app.userInfo("individu"), objet) == false) {
			WindowHandler.showError("Vous n'avez aucun droit sur cet objet (vous n'êtes pas dépositaire) !");
			return;
		}

		CreationObjetCtrl objCtrl = new CreationObjetCtrl(this, "Edition d'un objet", eContext, objet);
		objCtrl.setVisible(true);
		String label = objCtrl.getLabel();
		Salles salle = objCtrl.getSalle();
		boolean isReservable = objCtrl.isReservable();
		if (label != null) {
			objet.setRoLibelle1(label);
			objet.setRoLibelle2(label);
			objet.setRoReservable(isReservable ? "O" : "N");
			objet.setSalleRelationship(salle);
			try {
				eContext.lock();
				eContext.saveChanges();
			}
			catch (Exception e) {
				WindowHandler.showError(e.getMessage());
				e.printStackTrace();
			}
			finally {
				eContext.unlock();
			}
			refreshSelectedNode();
		}
	}

	private void refreshSelectedNode() {

		Object record = treeObjets.getLastSelectedPathComponent();
		if (record != null) {
			if (record instanceof VTreeObjet) {
				DBHandler.invalidateObject(eContext, (VTreeObjet) record);
			}
		}
		treeObjets.updateUI();
	}

	public void ajouterObjetTypePanel() {
		if (isDroitGestionObjets() == false) {
			WindowHandler.showError("Vous n'avez pas le droit de gérer les objets !");
			return;
		}
		CreationTypeCtrl objCtrl = new CreationTypeCtrl(this, "Saisie d'un type d'objets");
		objCtrl.setVisible(true);
		String label = objCtrl.getLabel();

		if (label != null) {
			ResaFamilleObjet familleObjet = (ResaFamilleObjet) selectedNode;
			ResaTypeObjet newType = ResaTypeObjet.createResaTypeObjet(eContext, label);
			newType.setRtoCommentaire(label);

			newType.setResaFamilleObjetRelationship(familleObjet);
			try {
				eContext.saveChanges();
			}
			catch (Exception e) {
				WindowHandler.showError(e.getMessage());
				e.printStackTrace();
			}
		}

		refreshSelectedNode();
	}

	public void ajouterObjetFamillePanel() {
		if (isDroitGestionObjets() == false) {
			WindowHandler.showError("Vous n'avez pas le droit de gérer les objets !");
			return;
		}
		CreationTypeCtrl objCtrl = new CreationTypeCtrl(this, "Saisie d'une famille d'objets");
		objCtrl.setVisible(true);
		String label = objCtrl.getLabel();

		if (label != null) {
			ResaFamilleObjet newfamille = ResaFamilleObjet.createResaFamilleObjet(eContext, label);
			newfamille.setRfoCommentaire(label);

			try {
				eContext.saveChanges();
			}
			catch (Exception e) {
				WindowHandler.showError(e.getMessage());
				e.printStackTrace();
			}
		}
		refreshSelectedNode();
	}

	private void processChangementImplantationGeo() {
		ImplantationGeo impGeo = (ImplantationGeo) comboImplantationGeo.getSelectedItem();
		objetModel = new ObjetTreeModel(eContext, false);
		objetModel.setFilterByImplantationGeo(impGeo);
		treeObjets.setModel(objetModel);
	}

	private class AMenuAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public static final int ACTION_EDIT_OBJET = 3;
		public static final int ACTION_AJOUT_OBJET = 0;
		public static final int ACTION_AJOUT_TYPE = 1;
		public static final int ACTION_AJOUT_FAMILLE = 2;

		private int actionType;

		public AMenuAction(int anActionType, String name) {
			putValue(Action.NAME, name);
			actionType = anActionType;
		}

		public void actionPerformed(ActionEvent event) {
			if (actionType == ACTION_EDIT_OBJET) {
				editObjetPanel();
			}

			if (actionType == ACTION_AJOUT_OBJET) {
				ajouterObjetPanel();
			}

			if (actionType == ACTION_AJOUT_TYPE) {
				ajouterObjetTypePanel();
			}

			if (actionType == ACTION_AJOUT_FAMILLE) {
				ajouterObjetFamillePanel();
			}
		}
	}

	private class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboImplantationGeo) {
				processChangementImplantationGeo();
			}
		}
	}

}
