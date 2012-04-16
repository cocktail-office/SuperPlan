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

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.cocktail.superplan.client.metier.CtrlParamAp;
import org.cocktail.superplan.client.metier.CtrlParamHabilitation;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.MaquetteEc;
import org.cocktail.superplan.client.metier.VMaquetteApGroupes;
import org.cocktail.superplan.client.metier.VScolMaquetteApEc;
import org.cocktail.superplan.client.swing.TableSorter;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTableCellRenderer;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;
import org.cocktail.superplan.client.utilities.HeuresFormat;

import com.webobjects.eointerface.EODisplayGroup;


public class RechercheApsView extends javax.swing.JDialog {

	protected EODisplayGroup eodHabilitations, eodAps;
	protected ZEOTable myEOTableHabilitations, myEOTableAps;
	protected ZEOTableCellRenderer tableApsRenderer;

	/** Creates new form RechercheApsView */
	public RechercheApsView(java.awt.Frame parent, boolean modal, EODisplayGroup displayGroupHabilitations, EODisplayGroup displayGroupAps,
			ZEOTableCellRenderer tableApsRenderer) {
		super(parent, modal);
		initComponents();
		eodHabilitations = displayGroupHabilitations;
		eodAps = displayGroupAps;
		this.tableApsRenderer = tableApsRenderer;
		initGui();
	}

	@SuppressWarnings("deprecation")
	public void initGui() {
		Vector<ZEOTableModelColumn> myColsHabilitations = new Vector<ZEOTableModelColumn>();

		ZEOTableModelColumn col = new ZEOTableModelColumn(eodHabilitations, FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FGRA_CODE_KEY, "Grade", 20);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitations.add(col);
		col = new ZEOTableModelColumn(eodHabilitations, FormationHabilitation.FHAB_NIVEAU_KEY, "Niveau", 20);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitations.add(col);
		col = new ZEOTableModelColumn(eodHabilitations, FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY, "Diplome", 200);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitations.add(col);
		col = new ZEOTableModelColumn(eodHabilitations, FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.FSPN_LIBELLE_KEY, "Spécialisation", 100);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitations.add(col);
		col = new ZEOTableModelColumn(eodHabilitations, FormationHabilitation.CTRL_PARAM_HABILITATION_KEY + "."
				+ CtrlParamHabilitation.IS_PARAM_SAISIS, "Params. saisis", 30);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitations.add(col);

		ZEOTableModel myTableModel = new ZEOTableModel(eodHabilitations, myColsHabilitations);
		TableSorter myTableSorter = new TableSorter(myTableModel);

		myEOTableHabilitations = new ZEOTable(myTableSorter);
		myTableSorter.setTableHeader(myEOTableHabilitations.getTableHeader());

		myEOTableHabilitations.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		viewTableHabilitations.setLayout(new BorderLayout());
		viewTableHabilitations.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableHabilitations.removeAll();
		viewTableHabilitations.add(new JScrollPane(myEOTableHabilitations), BorderLayout.CENTER);

		Vector<ZEOTableModelColumn> myColsAps = new Vector<ZEOTableModelColumn>();

		col = new ZEOTableModelColumn(eodAps, VMaquetteApGroupes.MAP_LIBELLE_AFFICHAGE_KEY, "Libellé AP", 80);
		col.setAlignment(SwingConstants.LEFT);
		myColsAps.add(col);
		col = new ZEOTableModelColumn(eodAps, VMaquetteApGroupes.V_SCOL_MAQUETTE_AP_EC_KEY + "." + VScolMaquetteApEc.MAQUETTE_EC_KEY + "."
				+ MaquetteEc.MEC_CODE_KEY, "Code EC", 20);
		col.setAlignment(SwingConstants.LEFT);
		myColsAps.add(col);
		col = new ZEOTableModelColumn(eodAps, VMaquetteApGroupes.MAP_VALEUR_KEY, "Heures prévues", 15);
		col.setColumnClass(BigDecimal.class);
		col.setFormatDisplay(new HeuresFormat());
		col.setAlignment(SwingConstants.RIGHT);
		myColsAps.add(col);
		col = new ZEOTableModelColumn(eodAps, VMaquetteApGroupes.MAP_GROUPE_PREVU_KEY, "Nb groupes prévus", 15);
		col.setColumnClass(Integer.class);
		col.setAlignment(SwingConstants.RIGHT);
		myColsAps.add(col);
		col = new ZEOTableModelColumn(eodAps, VMaquetteApGroupes.TOTAL_PREVU, "Total prévu", 10);
		col.setColumnClass(BigDecimal.class);
		col.setFormatDisplay(new HeuresFormat());
		col.setAlignment(SwingConstants.RIGHT);
		myColsAps.add(col);
		col = new ZEOTableModelColumn(eodAps, VMaquetteApGroupes.TOTAL_RESERVE, "Total réservé", 10);
		col.setColumnClass(BigDecimal.class);
		col.setFormatDisplay(new HeuresFormat());
		col.setAlignment(SwingConstants.RIGHT);
		myColsAps.add(col);
		col = new ZEOTableModelColumn(eodAps, VMaquetteApGroupes.GGRP_LIBELLE_KEY, "Groupe", 30);
		col.setAlignment(SwingConstants.LEFT);
		myColsAps.add(col);
		col = new ZEOTableModelColumn(eodAps, VMaquetteApGroupes.CTRL_PARAM_AP_KEY + "." + CtrlParamAp.IS_PARAM_SAISIS, "Params. saisis", 10);
		col.setAlignment(SwingConstants.CENTER);
		myColsAps.add(col);

		ZEOTableModel myTableModelAps = new ZEOTableModel(eodAps, myColsAps);
		TableSorter myTableSorterAps = new TableSorter(myTableModelAps);

		myEOTableAps = new ZEOTable(myTableSorterAps, tableApsRenderer);
		myTableSorterAps.setTableHeader(myEOTableAps.getTableHeader());

		myEOTableAps.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		viewTableAps.setLayout(new BorderLayout());
		viewTableAps.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableAps.removeAll();
		viewTableAps.add(new JScrollPane(myEOTableAps), BorderLayout.CENTER);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		buttonGroup2 = new javax.swing.ButtonGroup();
		viewTableHabilitations = new javax.swing.JPanel();
		tfGrade = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		tfDiplome = new javax.swing.JTextField();
		rbTous = new javax.swing.JRadioButton();
		rbPref = new javax.swing.JRadioButton();
		cbAnnee = new javax.swing.JComboBox();
		btFermer = new javax.swing.JButton();
		cbParcours = new javax.swing.JComboBox();
		jLabel3 = new javax.swing.JLabel();
		viewTableAps = new javax.swing.JPanel();
		tfLegendeOk = new javax.swing.JTextField();
		tfLegendeKo = new javax.swing.JTextField();
		btReserverAp = new javax.swing.JButton();
		btVoirResasAp = new javax.swing.JButton();
		btReserverAuto = new javax.swing.JButton();
		btSaisirParametresAp = new javax.swing.JButton();
		rbApsNonCompletes = new javax.swing.JRadioButton();
		rbToutesAps = new javax.swing.JRadioButton();
		btSaisirParametresFormation = new javax.swing.JButton();
		jCheckBoxGroupes = new javax.swing.JCheckBox();
		btSupprimerResas = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Recherche des APs");

		viewTableHabilitations.setLayout(new java.awt.BorderLayout());

		jLabel1.setText("Grade");

		jLabel2.setText("Diplome");

		buttonGroup1.add(rbTous);
		rbTous.setText("Tous");

		buttonGroup1.add(rbPref);
		rbPref.setSelected(true);
		rbPref.setText("Pref.");

		cbAnnee.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

		btFermer.setText("Fermer");

		cbParcours.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

		jLabel3.setText("Semestre - Parcours");

		viewTableAps.setLayout(new java.awt.BorderLayout());

		tfLegendeOk.setEditable(false);
		tfLegendeOk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		tfLegendeOk.setText("Total réservé = total prévu");
		tfLegendeOk.setBorder(null);

		tfLegendeKo.setEditable(false);
		tfLegendeKo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		tfLegendeKo.setText("Total réservé > total prévu");
		tfLegendeKo.setBorder(null);

		btReserverAp.setText("Réserver cet AP...");

		btVoirResasAp.setText("Voir les résas");

		btReserverAuto.setText("Réserver en automatique");

		btSaisirParametresAp.setText("Paramètres AP(s)");

		buttonGroup2.add(rbApsNonCompletes);
		rbApsNonCompletes.setText("APs non complets");

		buttonGroup2.add(rbToutesAps);
		rbToutesAps.setSelected(true);
		rbToutesAps.setText("Tous les APs");

		btSaisirParametresFormation.setText("Paramètres formation");

		jCheckBoxGroupes.setText("+ Groupes");
		jCheckBoxGroupes.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

		btSupprimerResas.setText("Supprimer les résas...");

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(viewTableHabilitations, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
								.add(viewTableAps, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
								.add(org.jdesktop.layout.GroupLayout.TRAILING,
										layout.createSequentialGroup()
												.add(layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(tfGrade, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(jLabel1))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(jLabel2)
														.add(tfDiplome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 283,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
												.add(32, 32, 32)
												.add(layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(layout
																.createSequentialGroup()
																.add(rbPref)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 253, Short.MAX_VALUE)
																.add(cbAnnee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 107,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
														.add(layout.createSequentialGroup().add(rbTous)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 360, Short.MAX_VALUE)))
												.add(20, 20, 20))
								.add(layout
										.createSequentialGroup()
										.add(tfLegendeKo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 149,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap(647, Short.MAX_VALUE))
								.add(org.jdesktop.layout.GroupLayout.TRAILING,
										layout.createSequentialGroup()
												.add(layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
														.add(org.jdesktop.layout.GroupLayout.LEADING,
																layout.createSequentialGroup()
																		.add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 113,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(cbParcours, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 360,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
																		.add(rbToutesAps)
																		.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
																		.add(rbApsNonCompletes)
																		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 30, Short.MAX_VALUE)
																		.add(jCheckBoxGroupes))
														.add(btSaisirParametresFormation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap())
								.add(layout
										.createSequentialGroup()
										.add(tfLegendeOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(18, 18, 18)
										.add(layout
												.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
												.add(org.jdesktop.layout.GroupLayout.TRAILING,
														layout.createSequentialGroup()
																.add(btSupprimerResas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 392, Short.MAX_VALUE)
																.add(btFermer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
												.add(layout
														.createSequentialGroup()
														.add(btVoirResasAp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(btReserverAp)
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 119, Short.MAX_VALUE)
														.add(btSaisirParametresAp).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
														.add(btReserverAuto))).addContainerGap()))));
		layout.setVerticalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout
										.createSequentialGroup()
										.add(layout
												.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
												.add(rbPref)
												.add(cbAnnee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(rbTous))
								.add(layout
										.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
										.add(layout
												.createSequentialGroup()
												.add(jLabel1)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tfGrade, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(layout
												.createSequentialGroup()
												.add(jLabel2)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tfDiplome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(viewTableHabilitations, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(btSaisirParametresFormation)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel3)
								.add(cbParcours, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(rbApsNonCompletes).add(rbToutesAps).add(jCheckBoxGroupes))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(viewTableAps, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
						.add(18, 18, 18)
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(tfLegendeOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(btReserverAuto).add(btVoirResasAp).add(btReserverAp)
								.add(btSaisirParametresAp))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout
										.createSequentialGroup()
										.add(tfLegendeKo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(7, 7, 7).add(btFermer)).add(btSupprimerResas)).addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				RechercheApsView dialog = new RechercheApsView(new javax.swing.JFrame(), true, null, null, null);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	protected javax.swing.JButton btFermer;
	protected javax.swing.JButton btReserverAp;
	protected javax.swing.JButton btReserverAuto;
	protected javax.swing.JButton btSaisirParametresAp;
	protected javax.swing.JButton btSaisirParametresFormation;
	protected javax.swing.JButton btSupprimerResas;
	protected javax.swing.JButton btVoirResasAp;
	protected javax.swing.ButtonGroup buttonGroup1;
	protected javax.swing.ButtonGroup buttonGroup2;
	protected javax.swing.JComboBox cbAnnee;
	protected javax.swing.JComboBox cbParcours;
	protected javax.swing.JCheckBox jCheckBoxGroupes;
	protected javax.swing.JLabel jLabel1;
	protected javax.swing.JLabel jLabel2;
	protected javax.swing.JLabel jLabel3;
	protected javax.swing.JRadioButton rbApsNonCompletes;
	protected javax.swing.JRadioButton rbPref;
	protected javax.swing.JRadioButton rbTous;
	protected javax.swing.JRadioButton rbToutesAps;
	protected javax.swing.JTextField tfDiplome;
	protected javax.swing.JTextField tfGrade;
	protected javax.swing.JTextField tfLegendeKo;
	protected javax.swing.JTextField tfLegendeOk;
	protected javax.swing.JPanel viewTableAps;
	protected javax.swing.JPanel viewTableHabilitations;

	// End of variables declaration//GEN-END:variables

	public javax.swing.JComboBox getCbAnnee() {
		return cbAnnee;
	}

	public void setCbAnnee(javax.swing.JComboBox cbAnnee) {
		this.cbAnnee = cbAnnee;
	}

	public javax.swing.JRadioButton getRbPref() {
		return rbPref;
	}

	public void setRbPref(javax.swing.JRadioButton rbPref) {
		this.rbPref = rbPref;
	}

	public javax.swing.JRadioButton getRbTous() {
		return rbTous;
	}

	public void setRbTous(javax.swing.JRadioButton rbTous) {
		this.rbTous = rbTous;
	}

	public javax.swing.JTextField getTfDiplome() {
		return tfDiplome;
	}

	public void setTfDiplome(javax.swing.JTextField tfDiplome) {
		this.tfDiplome = tfDiplome;
	}

	public javax.swing.JTextField getTfGrade() {
		return tfGrade;
	}

	public void setTfGrade(javax.swing.JTextField tfGrade) {
		this.tfGrade = tfGrade;
	}

	public javax.swing.JButton getBtFermer() {
		return btFermer;
	}

	public void setBtFermer(javax.swing.JButton btFermer) {
		this.btFermer = btFermer;
	}

	public ZEOTable getMyEOTableHabilitations() {
		return myEOTableHabilitations;
	}

	public void setMyEOTableHabilitations(ZEOTable myEOTableHabilitations) {
		this.myEOTableHabilitations = myEOTableHabilitations;
	}

	public javax.swing.JComboBox getCbParcours() {
		return cbParcours;
	}

	public void setCbParcours(javax.swing.JComboBox cbParcours) {
		this.cbParcours = cbParcours;
	}

	public ZEOTable getMyEOTableAps() {
		return myEOTableAps;
	}

	public void setMyEOTableAps(ZEOTable myEOTableAps) {
		this.myEOTableAps = myEOTableAps;
	}

	public javax.swing.JTextField getTfLegendeKo() {
		return tfLegendeKo;
	}

	public void setTfLegendeKo(javax.swing.JTextField tfLegendeKo) {
		this.tfLegendeKo = tfLegendeKo;
	}

	public javax.swing.JTextField getTfLegendeOk() {
		return tfLegendeOk;
	}

	public void setTfLegendeOk(javax.swing.JTextField tfLegendeOk) {
		this.tfLegendeOk = tfLegendeOk;
	}

	public javax.swing.JButton getBtReserverAp() {
		return btReserverAp;
	}

	public void setBtReserverAp(javax.swing.JButton btReserverAp) {
		this.btReserverAp = btReserverAp;
	}

	public javax.swing.JButton getBtVoirResasAp() {
		return btVoirResasAp;
	}

	public void setBtVoirResasAp(javax.swing.JButton btVoirResasAp) {
		this.btVoirResasAp = btVoirResasAp;
	}

	public javax.swing.JButton getBtReserverAuto() {
		return btReserverAuto;
	}

	public void setBtReserverAuto(javax.swing.JButton btReserverAuto) {
		this.btReserverAuto = btReserverAuto;
	}

	public javax.swing.JButton getBtSaisirParametresAp() {
		return btSaisirParametresAp;
	}

	public void setBtSaisirParametresAp(javax.swing.JButton btSaisirParametres) {
		this.btSaisirParametresAp = btSaisirParametres;
	}

	public javax.swing.JRadioButton getRbApsNonCompletes() {
		return rbApsNonCompletes;
	}

	public void setRbApsNonCompletes(javax.swing.JRadioButton rbApsNonCompletes) {
		this.rbApsNonCompletes = rbApsNonCompletes;
	}

	public javax.swing.JRadioButton getRbToutesAps() {
		return rbToutesAps;
	}

	public void setRbToutesAps(javax.swing.JRadioButton rbToutesAps) {
		this.rbToutesAps = rbToutesAps;
	}

	public javax.swing.JButton getBtSaisirParametresFormation() {
		return btSaisirParametresFormation;
	}

	public javax.swing.JCheckBox getjCheckBoxGroupes() {
		return jCheckBoxGroupes;
	}

	public javax.swing.JButton getBtSupprimerResas() {
		return btSupprimerResas;
	}

}
