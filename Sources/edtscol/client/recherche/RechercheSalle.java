/*
 * Copyright COCKTAIL (www.cocktail.org), 1995, ${year} This software
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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.metier.DepositaireSalles;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.Local;
import org.cocktail.superplan.client.metier.LotSalle;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.RepartLotIndividu;
import org.cocktail.superplan.client.metier.RepartLotSalle;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.TypeSalle;
import org.cocktail.superplan.client.metier.VMaquetteApGroupes;
import org.cocktail.superplan.client.swing.TableSorter;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTable.ZEOTableListener;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import edtscol.client.panier.GestionPanier;
import edtscol.client.panier.Panier;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.SwapView;

/**
 * 
 * @author pdemeyer
 */
public class RechercheSalle extends javax.swing.JPanel {

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EODisplayGroup eodSalle = new EODisplayGroup();
	private EODisplayGroup eodLotSalle = new EODisplayGroup();
	private ZEOTable eoTableSalle, eoTableLotSalle;
	private SwapView swapViewTable = new SwapView();
	private Recherche recherche;
	private EOEditingContext eContext;
	SalleFactory salleFactory;

	/** Creates new form Salle */
	public RechercheSalle(EOEditingContext eContext, Recherche recherche) {
		initComponents();
		this.eContext = eContext;
		this.recherche = recherche;
		salleFactory = new SalleFactory(eContext);
		init();
	}

	protected void init() {
		initWidgets();
		initTables();
		swapViewSallesLotsDeSalles();
		if (recherche.getInitType() != Recherche.INSPECTEUR) {
			cbFiltreCapacite.setVisible(false);
		}
		if (salleFactory.estDepositaire((IndividuUlr) app.userInfo("individu"))) {
			afficherSallesDepositaire();
		}
	}

	private void initWidgets() {
		WidgetHandler.decorateButton("rechercher", null, "find", bRechercheSalle);
	}

	public JPanel currentView() {
		return this;
	}

	private void initTables() {
		// salles
		Vector<ZEOTableModelColumn> myColsSalle = new Vector<ZEOTableModelColumn>();

		ZEOTableModelColumn col = new ZEOTableModelColumn(eodSalle, Salles.SAL_PORTE_KEY, "Porte", 20);
		col.setAlignment(SwingConstants.LEFT);
		myColsSalle.add(col);
		col = new ZEOTableModelColumn(eodSalle, Salles.TYPE_SALLE_KEY + "." + TypeSalle.TSAL_LIBELLE_KEY, "Type", 20);
		col.setAlignment(SwingConstants.LEFT);
		myColsSalle.add(col);
		col = new ZEOTableModelColumn(eodSalle, Salles.SAL_CAPACITE_KEY, "Places", 10);
		col.setColumnClass(Integer.class);
		col.setAlignment(SwingConstants.LEFT);
		myColsSalle.add(col);
		col = new ZEOTableModelColumn(eodSalle, Salles.LOCAL_KEY + "." + Local.C_LOCAL_KEY, "Code bat.", 10);
		col.setAlignment(SwingConstants.LEFT);
		myColsSalle.add(col);
		col = new ZEOTableModelColumn(eodSalle, Salles.SAL_ETAGE_KEY, "Etage", 10);
		col.setColumnClass(Integer.class);
		col.setAlignment(SwingConstants.LEFT);
		myColsSalle.add(col);

		ZEOTableModel myTableModel = new ZEOTableModel(eodSalle, myColsSalle);
		TableSorter myTableSorter = new TableSorter(myTableModel);

		eoTableSalle = new ZEOTable(myTableSorter);
		myTableSorter.setTableHeader(eoTableSalle.getTableHeader());

		eoTableSalle.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		eoTableSalle.addListener(new ListenerTableSalles());

		// lots de salles
		Vector<ZEOTableModelColumn> myColsLotSalle = new Vector<ZEOTableModelColumn>();

		col = new ZEOTableModelColumn(eodLotSalle, LotSalle.LOT_LIBELLE_KEY, "Lot", 20);
		col.setAlignment(SwingConstants.LEFT);
		myColsLotSalle.add(col);
		col = new ZEOTableModelColumn(eodLotSalle, LotSalle.SALLES_SUMMARY_KEY, "Salles...", 20);
		col.setAlignment(SwingConstants.LEFT);
		myColsLotSalle.add(col);

		myTableModel = new ZEOTableModel(eodLotSalle, myColsLotSalle);
		myTableSorter = new TableSorter(myTableModel);

		eoTableLotSalle = new ZEOTable(myTableSorter);
		myTableSorter.setTableHeader(eoTableLotSalle.getTableHeader());

		eoTableLotSalle.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		viewTable.setLayout(new BorderLayout());
		viewTable.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTable.add(swapViewTable, BorderLayout.CENTER);
	}

	private void swapViewSallesLotsDeSalles() {
		boolean rechercheSalles = !cbLotsDeSalles.isSelected();
		tfLocal.setEnabled(rechercheSalles);
		tfEtage.setEnabled(rechercheSalles);
		tfPorte.setEnabled(rechercheSalles);
		bRechercheSalle.setEnabled(rechercheSalles);
		cbFiltreCapacite.setEnabled(rechercheSalles);
		if (rechercheSalles) {
			swapViewTable.setContentView(new JScrollPane(eoTableSalle));
		}
		else {
			swapViewTable.setContentView(new JScrollPane(eoTableLotSalle));
			rechercherLotSalle();
		}
	}

	public NSArray<NSDictionary<String, Object>> selectedRessources() {
		NSMutableArray<NSDictionary<String, Object>> retour = new NSMutableArray<NSDictionary<String, Object>>();
		NSMutableArray<Salles> currentSalles = new NSMutableArray<Salles>();
		if (cbLotsDeSalles.isSelected()) {
			NSArray<LotSalle> lots = eodLotSalle.selectedObjects();
			if (lots != null && lots.count() > 0) {
				for (int i = 0; i < lots.count(); i++) {
					currentSalles
							.addObjectsFromArray((NSArray<Salles>) lots.objectAtIndex(i).repartLotSalles().valueForKey(RepartLotSalle.SALLE_KEY));
				}
			}
		}
		else {
			currentSalles.addObjectsFromArray(eodSalle.selectedObjects());
		}

		for (int i = 0; i < currentSalles.count(); i++) {
			Salles currentSalle = currentSalles.objectAtIndex(i);
			String libelle = currentSalle.salPorte() + " - " + currentSalle.local().appellation();
			NSDictionary<String, Object> ressource = null;
			String[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			if (rbSalle.isSelected()) {
				Object[] objects = { "SALLE", libelle, NSKeyValueCoding.NullValue, "1", currentSalle };
				ressource = new NSDictionary<String, Object>(objects, keys);
			}
			else {
				Object[] objects = { "CHOIX", libelle, NSKeyValueCoding.NullValue, "1", currentSalle };
				ressource = new NSDictionary<String, Object>(objects, keys);
			}
			retour.addObject(ressource);
		}
		return retour;
	}

	public void rechercherSalle() {
		String etage = tfEtage.getText().toUpperCase();
		String local = tfLocal.getText().toUpperCase();
		String porte = tfPorte.getText().toUpperCase();
		NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();

		if (!etage.equals("")) {
			qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.SAL_ETAGE_KEY + " = '" + etage + "'", null));
		}

		if (!local.equals("")) {
			NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>();
			orQuals.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.LOCAL_KEY + "." + Local.APPELLATION_KEY + " caseInsensitiveLike '*"
					+ local + "*'", null));
			orQuals.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.LOCAL_KEY + "." + Local.C_LOCAL_KEY + " caseInsensitiveLike '*" + local
					+ "*'", null));
			qualifiers.addObject(new EOOrQualifier(orQuals));
		}

		if (!porte.equals("")) {
			qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.SAL_PORTE_KEY + " caseInsensitiveLike '*" + porte + "*'", null));
		}

		qualifiers.addObject(new EOKeyValueQualifier(Salles.SAL_RESERVABLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O"));
		qualifiers
				.addObject(new EOKeyValueQualifier(Salles.D_ANNULATION_KEY, EOKeyValueQualifier.QualifierOperatorEqual, NSKeyValueCoding.NullValue));

		if (cbFiltreCapacite.isSelected()) {
			int nbEtudiants = getNbEtudiants();
			if (nbEtudiants > 0) {
				NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>();
				orQuals.addObject(new EOKeyValueQualifier(Salles.SAL_CAPACITE_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThanOrEqualTo,
						new Integer(nbEtudiants)));
				orQuals.addObject(new EOKeyValueQualifier(Salles.SAL_CAPACITE_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
						NSKeyValueCoding.NullValue));
				qualifiers.addObject(new EOOrQualifier(orQuals));
			}
		}
		if (qualifiers.count() > 0) {
			EOSortOrdering sortSalle = EOSortOrdering.sortOrderingWithKey(Salles.SAL_PORTE_KEY, EOSortOrdering.CompareAscending);
			eodSalle.setObjectArray(Salles.fetchSalleses(eContext, new EOAndQualifier(qualifiers), new NSArray<EOSortOrdering>(sortSalle)));
			eoTableSalle.updateData();
		}
	}

	public void rechercherLotSalle() {
		NSMutableArray<LotSalle> lots;
		EOQualifier qual1 = new EOKeyValueQualifier(LotSalle.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual, app.userInfo("individu"));
		lots = LotSalle.fetchLotSalles(eContext, qual1, null).mutableClone();

		EOQualifier qual21 = new EOKeyValueQualifier(LotSalle.REPART_LOT_INDIVIDUS_KEY + "." + RepartLotIndividu.INDIVIDU_ULR_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, app.userInfo("individu"));
		EOQualifier qual22 = new EOKeyValueQualifier(LotSalle.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual,
				app.userInfo("individu"));
		lots.addObjectsFromArray(LotSalle.fetchLotSalles(eContext,
				new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qual21, qual22 })), null));

		EOSortOrdering sortLotSalle = EOSortOrdering.sortOrderingWithKey(LotSalle.LOT_LIBELLE_KEY, EOSortOrdering.CompareAscending);
		EOSortOrdering.sortArrayUsingKeyOrderArray(lots, new NSArray<EOSortOrdering>(sortLotSalle));

		eodLotSalle.setObjectArray(lots);
		eoTableLotSalle.updateData();
	}

	private int getNbEtudiants() {
		int nbEtudiants = 0;
		if (recherche != null && recherche.panier != null) {
			NSArray<NSDictionary<String, Object>> apGrps;
			if (recherche.panier.getVapUniqueNonVisible() != null) {
				VMaquetteApGroupes vap = recherche.panier.getVapUniqueNonVisible();
				NSMutableDictionary<String, Object> dico = new NSMutableDictionary<String, Object>();
				dico.takeValueForKey(vap.maquetteAp(), "AP");
				if (vap.scolGroupeGrp() != null) {
					dico.takeValueForKey(vap.scolGroupeGrp(), "GRP");
				}
				apGrps = new NSArray<NSDictionary<String, Object>>(dico);
			}
			else {
				apGrps = recherche.panier.getResourcesWithType("ENSEIGNEMENT_INSP");
			}
			if (apGrps != null) {
				for (int i = 0; i < apGrps.count(); i++) {
					NSDictionary<String, Object> apGroupe = apGrps.objectAtIndex(i);
					Object objet = apGroupe.valueForKey("GRP");
					Integer effectif = null;
					if (objet != null && !(objet instanceof String)) {
						ScolGroupeGrp grp = (ScolGroupeGrp) apGroupe.valueForKey("GRP");
						effectif = grp.getEffectif();
					}
					else {
						MaquetteAp ap = (MaquetteAp) apGroupe.valueForKey("AP");
						effectif = ap.getEffectif();
					}
					if (effectif != null && effectif.intValue() > 0) {
						nbEtudiants += effectif.intValue();
					}
				}
			}
		}
		return nbEtudiants;
	}

	private class ListenerTableSalles implements ZEOTableListener {

		public void onDbClick() {
			recherche.valider();
		}

		public void onSelectionChanged() {
		}
	}

	public void validerRecherche() {
		if (recherche.isSelectionParGroupe()) {
			if (recherche.eodSalle.selectedObjects().count() > 0) {
				recherche.main.planningOfSalles(recherche.eodSalle.selectedObjects());
			}
		}
		else {
			Salles salle = null;
			if (cbLotsDeSalles.isSelected()) {
				NSArray<LotSalle> lots = eodLotSalle.selectedObjects();
				if (lots != null && lots.count() > 0) {
					NSMutableArray<Salles> salles = new NSMutableArray<Salles>();
					for (int i = 0; i < lots.count(); i++) {
						salles.addObjectsFromArray((NSArray<Salles>) lots.objectAtIndex(i).repartLotSalles().valueForKey(RepartLotSalle.SALLE_KEY));
					}
					if (salles.count() == 0) {
						return;
					}
					if (salles.count() > 1) {
						recherche.main.planningOfSalles(salles);
						return;
					}
					else {
						salle = salles.lastObject();
					}
				}
			}
			else {
				if (eodSalle.selectedObjects() != null && eodSalle.selectedObjects().count() > 1) {
					recherche.main.planningOfSalles(eodSalle.selectedObjects());
					return;
				}
				else {
					salle = (Salles) eodSalle.selectedObject();
				}
			}
			if (salle != null) {
				Object[] objets = { new Integer(GestionPanier.SALLE), salle };
				String[] clefs = { "type", "resRecord" };

				NSDictionary<String, Object> dico = new NSDictionary<String, Object>(objets, clefs);
				NSNotificationCenter.defaultCenter().postNotification("validerRessource", dico);
				return;
			}
		}
	}

	protected void afficherSallesDepositaire() {
		NSArray<RepartStructure> repartDepositaires = salleFactory.structuresDepositaire((IndividuUlr) app.userInfo("individu"));

		NSArray<StructureUlr> structDepos = (NSArray<StructureUlr>) repartDepositaires.valueForKey(RepartStructure.STRUCTURE_ULR_KEY);

		NSMutableArray<EOQualifier> sum = new NSMutableArray<EOQualifier>();
		for (int i = 0; i < structDepos.count(); i++) {
			sum.addObject(EOQualifier.qualifierWithQualifierFormat(DepositaireSalles.STRUCTURE_ULR_KEY + " = %@", new NSArray<StructureUlr>(
					structDepos.objectAtIndex(i))));
		}
		EOQualifier sumQualifier = new EOOrQualifier(sum);
		NSArray<DepositaireSalles> depositairesSalles = DepositaireSalles.fetchDepositaireSalleses(eContext, sumQualifier, null);
		eodSalle.setObjectArray((NSArray<Salles>) depositairesSalles.valueForKey("salle"));
		eoTableSalle.updateData();
	}

	public void affecterSalles(NSArray<Salles> salles, NSArray<Salles> sallesSelectionnees) {
		eodSalle.setObjectArray(salles);
		eodSalle.selectObjectsIdenticalTo(sallesSelectionnees);
		eoTableSalle.updateData();
	}

	public void rechercheEtendue() {
		try {
			SalleLibreController ctrl = new SalleLibreController(eContext, new Integer(getNbEtudiants()));
			if (recherche != null && recherche.inspecteurCtrl() != null) {
				NSArray<NSTimestamp> periodicites = recherche.inspecteurCtrl().getPeriodicitesSouhaitees();
				if (periodicites != null && periodicites.count() > 0) {
					ctrl.setPeriodicitesRecherche(periodicites);
				}
			}
			WindowHandler.runModal(ctrl, "Recherche étendue de salle");
			if (ctrl.getSallesSelectionnees() != null && ctrl.getSallesSelectionnees().count() > 0) {
				if (recherche.inspecteurCtrl() != null && recherche.inspecteurCtrl().panier() != null) {
					Panier pan = recherche.inspecteurCtrl().panier();

					int type = rbSalle.isSelected() ? GestionPanier.SALLE : GestionPanier.CHOIX;
					NSArray<NSDictionary<String, Object>> resSalles = ctrl.getSallesSelectionneesFormatRessource(type);
					pan.addResourcesWithSalleControl(resSalles);
				}
				else {
					recherche.getRechercheSalle().affecterSalles(ctrl.getSallesTrouvees(), ctrl.getSallesSelectionnees());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		tfLocal = new javax.swing.JTextField();
		tfEtage = new javax.swing.JTextField();
		tfPorte = new javax.swing.JTextField();
		bRechercheSalle = new javax.swing.JButton();
		cbFiltreCapacite = new javax.swing.JCheckBox();
		viewTable = new javax.swing.JPanel();
		rbSalle = new javax.swing.JRadioButton();
		rbChoix = new javax.swing.JRadioButton();
		bEtendue = new javax.swing.JButton();
		cbLotsDeSalles = new javax.swing.JCheckBox();

		jLabel1.setText("Bâtiment");

		jLabel2.setText("Etage");

		jLabel3.setText("Nom ou porte");

		tfLocal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfLocalActionPerformed(evt);
			}
		});

		tfEtage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfEtageActionPerformed(evt);
			}
		});

		tfPorte.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfPorteActionPerformed(evt);
			}
		});

		bRechercheSalle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bRechercheSalleActionPerformed(evt);
			}
		});

		cbFiltreCapacite.setText(" / capacité");
		cbFiltreCapacite.setToolTipText("N'afficher que les salles ayant une capacité suffisante pour le nombre d'étudiants prévus.");
		cbFiltreCapacite.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbFiltreCapaciteActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout viewTableLayout = new org.jdesktop.layout.GroupLayout(viewTable);
		viewTable.setLayout(viewTableLayout);
		viewTableLayout.setHorizontalGroup(viewTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 425, Short.MAX_VALUE));
		viewTableLayout.setVerticalGroup(viewTableLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 186, Short.MAX_VALUE));

		buttonGroup1.add(rbSalle);
		rbSalle.setSelected(true);
		rbSalle.setText("Salle");

		buttonGroup1.add(rbChoix);
		rbChoix.setText("Choix");

		bEtendue.setText("Recherche étendue");
		bEtendue.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bEtendueActionPerformed(evt);
			}
		});

		cbLotsDeSalles.setText("Lots de salles");
		cbLotsDeSalles.setToolTipText("Afficher mes lots de salles");
		cbLotsDeSalles.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		cbLotsDeSalles.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		cbLotsDeSalles.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbLotsDeSallesActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
								.add(org.jdesktop.layout.GroupLayout.LEADING, tfLocal)
								.add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
								.add(tfEtage)
								.add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false).add(tfPorte)
								.add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(bRechercheSalle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED).add(cbFiltreCapacite).add(18, 18, 18)
						.add(cbLotsDeSalles, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE).addContainerGap())
				.add(layout.createSequentialGroup().addContainerGap()
						.add(rbSalle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
						.add(rbChoix, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(114, 114, 114)
						.add(bEtendue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.add(viewTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout
										.createSequentialGroup()
										.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jLabel1).add(jLabel2)
												.add(jLabel3))
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(layout
												.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
												.add(tfLocal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.add(tfEtage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.add(tfPorte, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.add(bRechercheSalle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
								.add(layout
										.createSequentialGroup()
										.add(17, 17, 17)
										.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(cbFiltreCapacite)
												.add(cbLotsDeSalles))))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(viewTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING).add(bEtendue)
								.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(rbSalle).add(rbChoix)))));
	}// </editor-fold>//GEN-END:initComponents

	private void cbLotsDeSallesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbLotsDeSallesActionPerformed
		swapViewSallesLotsDeSalles();
	}// GEN-LAST:event_cbLotsDeSallesActionPerformed

	private void bRechercheSalleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bRechercheSalleActionPerformed
		rechercherSalle();
	}// GEN-LAST:event_bRechercheSalleActionPerformed

	private void cbFiltreCapaciteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFiltreCapaciteActionPerformed
		rechercherSalle();
	}// GEN-LAST:event_cbFiltreCapaciteActionPerformed

	private void tfLocalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfLocalActionPerformed
		rechercherSalle();
	}// GEN-LAST:event_tfLocalActionPerformed

	private void tfPorteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfPorteActionPerformed
		rechercherSalle();
	}// GEN-LAST:event_tfPorteActionPerformed

	private void tfEtageActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfEtageActionPerformed
		rechercherSalle();
	}// GEN-LAST:event_tfEtageActionPerformed

	private void bEtendueActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bEtendueActionPerformed
		rechercheEtendue();
	}// GEN-LAST:event_bEtendueActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bEtendue;
	private javax.swing.JButton bRechercheSalle;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JCheckBox cbFiltreCapacite;
	private javax.swing.JCheckBox cbLotsDeSalles;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JRadioButton rbChoix;
	private javax.swing.JRadioButton rbSalle;
	private javax.swing.JTextField tfEtage;
	private javax.swing.JTextField tfLocal;
	private javax.swing.JTextField tfPorte;
	private javax.swing.JPanel viewTable;
	// End of variables declaration//GEN-END:variables

}
