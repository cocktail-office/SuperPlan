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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;

import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.LotSalle;
import org.cocktail.superplan.client.metier.RepartLotIndividu;
import org.cocktail.superplan.client.metier.RepartLotSalle;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eodistribution.client.EODistributedDataSource;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;

import edtscol.client.MainClient;
import edtscol.client.composant.recherche.RechercheIndividu;
import edtscol.client.recherche.SalleLibreController;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.WindowHandler;

public class LotsDeSallesCtrl extends javax.swing.JDialog {

	private MainClient app = (MainClient) EOApplication.sharedApplication();

	private EODisplayGroup eodLotSalle, eodRepartLotSalle, eodRepartLotIndividu;
	private ZEOTable tableLotSalle, tableRepartLotSalle, tableRepartLotIndividu;
	private EOEditingContext eContext;
	private DateField dateField;

	private SalleFactory salleFactory;

	private RechercheIndividu indFinder;
	private Component parent;

	public LotsDeSallesCtrl(Component parent, EOEditingContext context) {
		super((Frame) parent, "Constitution et gestion des lots de salles", true);
		eContext = context;
		salleFactory = new SalleFactory(eContext);
		initComponents();
		initExtra();
		this.parent = parent;
	}

	public void open() {
		this.setLocation(parent.getX() + 20, parent.getY() + 20);
		this.setVisible(true);
	}

	public void close() {
		this.setVisible(false);
	}

	private void initExtra() {

		// table : listes des lots de salles

		// TODO virer ca définitivement...
		// panelDateField.setLayout(new FlowLayout());
		// dateField = new DateField(this);
		// panelDateField.add(dateField);
		bEdition.setVisible(false);

		EODistributedDataSource lotSalleSource = new EODistributedDataSource(eContext, LotSalle.ENTITY_NAME);
		eodLotSalle = new EODisplayGroup();
		eodLotSalle.setDataSource(lotSalleSource);

		java.util.Vector<ZEOTableModelColumn> columns = new java.util.Vector<ZEOTableModelColumn>();
		columns.add(new ZEOTableModelColumn(eodLotSalle, LotSalle.LOT_LIBELLE_KEY, "Libelle"));

		ZEOTableModel lotSalleModel = new ZEOTableModel(eodLotSalle, columns);
		tableLotSalle = new ZEOTable(lotSalleModel);

		JScrollPane tableScroll = new JScrollPane(tableLotSalle);
		tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tableScroll.setPreferredSize(panelLots.getPreferredSize());
		panelLots.setLayout(new BorderLayout());
		panelLots.add(tableScroll, BorderLayout.NORTH);

		// table : listes des salles de chaque lot

		EODistributedDataSource repartLotSalleSource = new EODistributedDataSource(eContext, RepartLotSalle.ENTITY_NAME);
		eodRepartLotSalle = new EODisplayGroup();
		eodRepartLotSalle.setDataSource(repartLotSalleSource);

		columns = new java.util.Vector<ZEOTableModelColumn>();
		columns.add(new ZEOTableModelColumn(eodRepartLotSalle, RepartLotSalle.SALLE_KEY + "." + Salles.LIBELLE_COMPLET_KEY, "Libelle"));
		columns.add(new ZEOTableModelColumn(eodRepartLotSalle, RepartLotSalle.SALLE_KEY + "." + Salles.LIBELLE_CAPACITE_KEY, "Capacité"));

		ZEOTableModel repartLotSalleModel = new ZEOTableModel(eodRepartLotSalle, columns);
		tableRepartLotSalle = new ZEOTable(repartLotSalleModel);

		tableScroll = new JScrollPane(tableRepartLotSalle);
		tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		Dimension dim = tableRepartLotSalle.getPreferredSize();
		int width20 = (int) dim.getWidth() / 20;
		TableColumn col = tableRepartLotSalle.getColumnModel().getColumn(0);
		col.setPreferredWidth(19 * width20);
		col = tableRepartLotSalle.getColumnModel().getColumn(1);
		col.setPreferredWidth(width20);

		tableScroll.setPreferredSize(panelSalles.getPreferredSize());
		panelSalles.setLayout(new BorderLayout());
		panelSalles.add(tableScroll, BorderLayout.NORTH);

		tableLotSalle.addListener(new MyTableListener());

		// table : liste des individus rattachés au lot

		EODistributedDataSource repartLotIndividuSource = new EODistributedDataSource(eContext, RepartLotIndividu.ENTITY_NAME);
		eodRepartLotIndividu = new EODisplayGroup();
		eodRepartLotIndividu.setDataSource(repartLotIndividuSource);

		columns = new java.util.Vector<ZEOTableModelColumn>();
		columns.add(new ZEOTableModelColumn(eodRepartLotIndividu, RepartLotIndividu.INDIVIDU_ULR_KEY + "." + IndividuUlr.NOM_PRENOM_KEY,
				"Nom et prénom"));

		ZEOTableModel repartLotIndividuModel = new ZEOTableModel(eodRepartLotIndividu, columns);
		tableRepartLotIndividu = new ZEOTable(repartLotIndividuModel);

		tableScroll = new JScrollPane(tableRepartLotIndividu);
		tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tableScroll.setPreferredSize(panelIndividus.getPreferredSize());
		panelIndividus.setLayout(new BorderLayout());
		panelIndividus.add(tableScroll, BorderLayout.NORTH);

		fetchLotsUser();
		fetchSallesForLot();
		fetchIndividusForLot();

		setResizable(false);
	}

	private void fetchLotsUser() {
		DBHandler.fetchDisplayGroup(eodLotSalle, DBHandler.getSimpleQualifier(LotSalle.INDIVIDU_ULR_KEY, app.userInfo("individu")));
		tableLotSalle.updateData();
	}

	private void fetchIndividusForLot() {
		LotSalle selectedLot = (LotSalle) eodLotSalle.selectedObject();
		if (selectedLot != null) {
			DBHandler.fetchDisplayGroup(eodRepartLotIndividu, DBHandler.getSimpleQualifier(RepartLotIndividu.LOT_SALLE_KEY, selectedLot));
		}
		else {
			eodRepartLotIndividu.setObjectArray(null);
		}
		tableRepartLotIndividu.updateData();
	}

	private void fetchSallesForLot() {
		LotSalle selectedLot = (LotSalle) eodLotSalle.selectedObject();
		if (selectedLot != null) {
			DBHandler.fetchDisplayGroup(eodRepartLotSalle, DBHandler.getSimpleQualifier(RepartLotSalle.LOT_SALLE_KEY, selectedLot));
		}
		else {
			eodRepartLotSalle.setObjectArray(null);
		}
		tableRepartLotSalle.updateData();
	}

	private void bAddSalleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAddSalleActionPerformed
		SalleLibreController ctrl = new SalleLibreController(eContext);
		ctrl.setModeFinder(true);
		WindowHandler.runModal(ctrl, "Recherche étendue de salle");

		NSArray<Salles> sallesSelectionnees = ctrl.getSallesSelectionnees();
		if (sallesSelectionnees == null || sallesSelectionnees.count() == 0) {
			return;
		}

		LotSalle lotSalle = (LotSalle) eodLotSalle.selectedObject();
		for (int i = 0; i < sallesSelectionnees.count(); i++) {
			Salles currentSalle = sallesSelectionnees.objectAtIndex(i);

			EOQualifier qualRepart = EOQualifier.qualifierWithQualifierFormat(RepartLotSalle.LOT_SALLE_KEY + " = %@ and " + RepartLotSalle.SALLE_KEY
					+ " = %@", new NSArray<Object>(new Object[] { lotSalle, currentSalle }));
			NSArray<RepartLotSalle> data = RepartLotSalle.fetchRepartLotSalles(eContext, qualRepart, null);

			if (data.count() > 0) {
				WindowHandler.showInfo("La salle \"" + currentSalle.salPorte() + "\" fait déjà partie du lot !");
			}
			else {
				RepartLotSalle repartLotSalle = RepartLotSalle.createRepartLotSalle(eContext);
				lotSalle.addToRepartLotSallesRelationship(repartLotSalle);
				currentSalle.addToRepartLotSallesRelationship(repartLotSalle);
			}
		}
		app.saveChanges();
		fetchSallesForLot();
	}// GEN-LAST:event_bAddSalleActionPerformed

	private void bRemoveSalleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bRemoveSalleActionPerformed
		NSArray<RepartLotSalle> objects = eodRepartLotSalle.selectedObjects();
		for (int i = 0; i < objects.count(); i++) {
			RepartLotSalle rls = objects.objectAtIndex(i);
			eContext.deleteObject(rls);
		}
		app.saveChanges();
		tableRepartLotSalle.updateData();
	}// GEN-LAST:event_bRemoveSalleActionPerformed

	private void bQuitterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bQuitterActionPerformed
		setVisible(false);
	}// GEN-LAST:event_bQuitterActionPerformed

	private void bOkCapaciteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bOkCapaciteActionPerformed

		NSArray<RepartLotSalle> repartSalles = eodRepartLotSalle.selectedObjects();
		NSArray<Salles> salles = (NSArray<Salles>) repartSalles.valueForKey(RepartLotSalle.SALLE_KEY);
		String strCapa = tCapacite.getText().trim();
		if (strCapa.equals("")) {
			WindowHandler.showError("Il faut entrer la capacité à affecter aux salles sélectionnées !");
			return;
		}

		Integer newCapacite = null;
		try {
			newCapacite = new Integer(strCapa);
		}
		catch (Exception e) {
			e.printStackTrace();
			WindowHandler.showError("La capacité est exprimée en nombre entier valide !");
			return;
		}

		Salles currentSalle = null;
		IndividuUlr agent = (IndividuUlr) app.userInfo("individu");
		for (int i = 0; i < salles.count(); i++) {
			currentSalle = salles.objectAtIndex(i);
			if (salleFactory.estDepositaireDeSalle(agent, currentSalle)) {
				currentSalle.setSalCapacite(newCapacite);
			}
			else {
				WindowHandler.showError("Vous n'êtes pas connu(e) comme dépositaire de la salle :\"" + currentSalle.libelleComplet()
						+ "\"\nVous ne pouvez par conséquent pas en changer la capacité !");
			}
		}
		tableRepartLotSalle.updateData();
		app.saveChanges();
	}// GEN-LAST:event_bOkCapaciteActionPerformed

	private void bDelLotActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bDelLotActionPerformed
		LotSalle lot = (LotSalle) eodLotSalle.selectedObject();
		lot.deleteAllRepartLotIndividusRelationships();
		lot.deleteAllRepartLotSallesRelationships();
		eContext.deleteObject(lot);
		app.saveChanges();
		fetchLotsUser();
	}// GEN-LAST:event_bDelLotActionPerformed

	private void bAddLotActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAddLotActionPerformed
		LotLibelleCtrl lotCtrl = new LotLibelleCtrl(this);
		lotCtrl.setVisible(true);
		String libelle = lotCtrl.getLibelle();
		if (libelle != null) {
			EOQualifier qual = EOQualifier.qualifierWithQualifierFormat(LotSalle.LOT_LIBELLE_KEY + " caseInsensitiveLike '" + libelle + "'", null);
			LotSalle data = LotSalle.fetchLotSalle(eContext, qual);
			if (data != null) {
				String msg = "Un lot de salle intitulé \"" + data.lotLibelle() + "\" existe déjà.\nVeuillez choisir un autre libellé.";
				WindowHandler.showError(msg);
				return;
			}
			IndividuUlr currentUser = (IndividuUlr) app.userInfo("individu");
			LotSalle lotSalle = LotSalle.createLotSalle(eContext, libelle);
			lotSalle.setIndividuUlrRelationship(currentUser);
			app.saveChanges();
			fetchLotsUser();
		}
	}// GEN-LAST:event_bAddLotActionPerformed

	private void bEditionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bEditionActionPerformed

		String stringDateDeb = dateField.getText();
		LotSalle lot = (LotSalle) eodLotSalle.selectedObject();
		Number lotKey = (Number) app.primaryKeyFromEO(lot, LotSalle.LOT_KEY_KEY);

		ReportsProxy reportProxy = new ReportsProxy(eContext, null);
		reportProxy.printOccupationLotSalle(new Integer(lotKey.intValue()), stringDateDeb);
	}// GEN-LAST:event_bEditionActionPerformed

	private void bAddIndActionPerformed(java.awt.event.ActionEvent evt) {

		if (indFinder == null) {
			indFinder = new RechercheIndividu(this, eContext);
		}
		indFinder.setLocationRelativeTo(bAddInd);
		indFinder.setVisible(true);

		IndividuUlr ind = indFinder.getSelectedIndividu();

		if (ind != null) {
			LotSalle lot = (LotSalle) eodLotSalle.selectedObject();
			try {
				lot.ajouterIndividu(ind);
				app.saveChanges();
			}
			catch (Exception e) {
				if (e instanceof EdtException) {
					WindowHandler.showError(e.getMessage());
				}
				else {
					e.printStackTrace();
				}
			}
			fetchIndividusForLot();
		}
	}

	private void bRemoveIndActionPerformed(java.awt.event.ActionEvent evt) {

		RepartLotIndividu repartLotIndToDelete = (RepartLotIndividu) eodRepartLotIndividu.selectedObject();

		if (repartLotIndToDelete != null) {
			StringBuffer question = new StringBuffer();
			question.append("Confirmez vous le retrait de ");
			question.append(repartLotIndToDelete.individuUlr().prenomNom());
			question.append(" de la liste associée au lot :\n");
			question.append("\"").append(repartLotIndToDelete.lotSalle().lotLibelle()).append("\"");
			question.append(" ?");

			if (WindowHandler.showConfirmation(question.toString())) {
				eContext.deleteObject(repartLotIndToDelete);
				app.saveChanges();
				tableRepartLotIndividu.updateData();
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		panelDateField = new javax.swing.JPanel();
		bEdition = new javax.swing.JButton();
		panelLots = new javax.swing.JPanel();
		bAddLot = new javax.swing.JButton();
		bDelLot = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		panelSalles = new javax.swing.JPanel();
		bAddSalle = new javax.swing.JButton();
		bRemoveSalle = new javax.swing.JButton();
		tCapacite = new javax.swing.JTextField();
		bOkCapacite = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		panelIndividus = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		bAddInd = new javax.swing.JButton();
		bRemoveInd = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		bQuitter = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

		jLabel1.setText("Lots de salles :");

		org.jdesktop.layout.GroupLayout panelDateFieldLayout = new org.jdesktop.layout.GroupLayout(panelDateField);
		panelDateField.setLayout(panelDateFieldLayout);
		panelDateFieldLayout.setHorizontalGroup(panelDateFieldLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 188,
				Short.MAX_VALUE));
		panelDateFieldLayout.setVerticalGroup(panelDateFieldLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 34,
				Short.MAX_VALUE));

		bEdition.setText("Imprimer");
		bEdition.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bEditionActionPerformed(evt);
			}
		});

		panelLots.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		org.jdesktop.layout.GroupLayout panelLotsLayout = new org.jdesktop.layout.GroupLayout(panelLots);
		panelLots.setLayout(panelLotsLayout);
		panelLotsLayout.setHorizontalGroup(panelLotsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 820, Short.MAX_VALUE));
		panelLotsLayout.setVerticalGroup(panelLotsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 133, Short.MAX_VALUE));

		bAddLot.setText("Ajouter Lot");
		bAddLot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAddLotActionPerformed(evt);
			}
		});

		bDelLot.setText("Supprimer Lot");
		bDelLot.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bDelLotActionPerformed(evt);
			}
		});

		jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		jLabel2.setText("Salles du lot sélectionné :");

		panelSalles.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		org.jdesktop.layout.GroupLayout panelSallesLayout = new org.jdesktop.layout.GroupLayout(panelSalles);
		panelSalles.setLayout(panelSallesLayout);
		panelSallesLayout.setHorizontalGroup(panelSallesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 629,
				Short.MAX_VALUE));
		panelSallesLayout.setVerticalGroup(panelSallesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 155, Short.MAX_VALUE));

		bAddSalle.setText("Ajouter salle");
		bAddSalle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAddSalleActionPerformed(evt);
			}
		});

		bRemoveSalle.setText("Retirer salle");
		bRemoveSalle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bRemoveSalleActionPerformed(evt);
			}
		});

		bOkCapacite.setText("OK");
		bOkCapacite.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bOkCapaciteActionPerformed(evt);
			}
		});

		jLabel3.setText("Définir Capacité :");

		panelIndividus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		org.jdesktop.layout.GroupLayout panelIndividusLayout = new org.jdesktop.layout.GroupLayout(panelIndividus);
		panelIndividus.setLayout(panelIndividusLayout);
		panelIndividusLayout.setHorizontalGroup(panelIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 629,
				Short.MAX_VALUE));
		panelIndividusLayout.setVerticalGroup(panelIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 122,
				Short.MAX_VALUE));

		jLabel4.setText("Individus associés au lot sélectionné :");

		bAddInd.setText("Ajouter individu");
		bAddInd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAddIndActionPerformed(evt);
			}
		});

		bRemoveInd.setText("Retirer individu");
		bRemoveInd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bRemoveIndActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel1Layout
						.createSequentialGroup()
						.add(jPanel1Layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(jPanel1Layout
										.createSequentialGroup()
										.add(20, 20, 20)
										.add(panelSalles, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jPanel1Layout
												.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
														.add(org.jdesktop.layout.GroupLayout.LEADING, bAddSalle,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.add(org.jdesktop.layout.GroupLayout.LEADING, bRemoveSalle,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
												.add(jPanel1Layout
														.createSequentialGroup()
														.add(1, 1, 1)
														.add(tCapacite, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(bOkCapacite))
												.add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
								.add(jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.add(panelIndividus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.add(12, 12, 12)
										.add(jPanel1Layout
												.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
												.add(org.jdesktop.layout.GroupLayout.LEADING, bAddInd, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(org.jdesktop.layout.GroupLayout.LEADING, bRemoveInd,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))).addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel1Layout
						.createSequentialGroup()
						.add(jLabel2)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jPanel1Layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(jPanel1Layout
										.createSequentialGroup()
										.add(bAddSalle)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(bRemoveSalle)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jLabel3)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jPanel1Layout
												.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
												.add(tCapacite, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.add(bOkCapacite)))
								.add(panelSalles, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jLabel4)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jPanel1Layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(panelIndividus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jPanel1Layout.createSequentialGroup().add(bAddInd).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(bRemoveInd))).addContainerGap(9, Short.MAX_VALUE)));

		bQuitter.setText("Quitter");
		bQuitter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bQuitterActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				jPanel2Layout.createSequentialGroup().addContainerGap(788, Short.MAX_VALUE)
						.add(bQuitter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(55, 55, 55)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(bQuitter));

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout
										.createSequentialGroup()
										.add(layout
												.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
												.add(org.jdesktop.layout.GroupLayout.TRAILING,
														layout.createSequentialGroup()
																.add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
																.add(panelDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																		org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(bEdition)
																.add(484, 484, 484))
												.add(layout
														.createSequentialGroup()
														.add(layout
																.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
																.add(panelLots, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																		org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																		org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
														.add(layout
																.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
																.add(bAddLot, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																		org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.add(bDelLot, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																		org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).add(17, 17, 17))
								.add(layout
										.createSequentialGroup()
										.add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE).addContainerGap()))));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
								.add(jLabel1)
								.add(bEdition)
								.add(org.jdesktop.layout.GroupLayout.LEADING,
										layout.createSequentialGroup()
												.add(6, 6, 6)
												.add(panelDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout.createSequentialGroup().add(bAddLot).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(bDelLot))
								.add(panelLots, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 354, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(31, 31, 31)
						.add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(37, 37, 37)));

		pack();
	}// </editor-fold>

	// Variables declaration - do not modify
	private javax.swing.JButton bAddInd;
	private javax.swing.JButton bAddLot;
	private javax.swing.JButton bAddSalle;
	private javax.swing.JButton bDelLot;
	private javax.swing.JButton bEdition;
	private javax.swing.JButton bOkCapacite;
	private javax.swing.JButton bQuitter;
	private javax.swing.JButton bRemoveInd;
	private javax.swing.JButton bRemoveSalle;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel panelDateField;
	private javax.swing.JPanel panelIndividus;
	private javax.swing.JPanel panelLots;
	private javax.swing.JPanel panelSalles;
	private javax.swing.JTextField tCapacite;

	// End of variables declaration

	private class LotLibelleCtrl extends JDialog implements ActionListener {

		JButton bValider = new JButton("Valider");
		JButton bAnnuler = new JButton("Annuler");
		JTextField fieldLibelle = new JTextField(40);
		private String libelle;
		private JDialog parent;

		public LotLibelleCtrl(JDialog aParent) {
			super(aParent, "Lot de salles", true);
			this.parent = aParent;
			initComponent();
		}

		private void initComponent() {
			Container cont = getContentPane();
			cont.setLayout(new BorderLayout());
			JPanel panButtons = new JPanel(new FlowLayout());

			fieldLibelle.addActionListener(this);
			bAnnuler.addActionListener(this);
			bValider.addActionListener(this);

			panButtons.add(bAnnuler);
			panButtons.add(bValider);

			cont.add(fieldLibelle, BorderLayout.NORTH);
			cont.add(panButtons, BorderLayout.SOUTH);
			pack();
			setLocationRelativeTo(parent);
		}

		private void annuler() {
			libelle = null;
			setVisible(false);
		}

		private void valider() {
			libelle = fieldLibelle.getText();
			setVisible(false);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fieldLibelle || e.getSource() == bValider) {
				valider();
			}
			else {
				annuler();
			}
		}

		public String getLibelle() {
			return libelle;
		}
	}

	private class MyTableListener implements ZEOTable.ZEOTableListener {

		public MyTableListener() {
		}

		public void onDbClick() {
			// valider();
		}

		public void onSelectionChanged() {
			WindowHandler.setWaitCursor(getContentPane());
			fetchSallesForLot();
			fetchIndividusForLot();
			WindowHandler.setDefaultCursor(getContentPane());
		}
	}

}
