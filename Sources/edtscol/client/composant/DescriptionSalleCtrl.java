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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JDialog;

import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.TypeSalle;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import fr.univlr.common.utilities.WidgetHandler;

public class DescriptionSalleCtrl extends javax.swing.JDialog {

	private EOEditingContext eContext;
	private SalleFactory salleFactory;
	private Salles salle;
	private Component parent;

	/** Creates new form DescriptionSalleCtrl */
	public DescriptionSalleCtrl() {
		initComponents();
	}

	/** Creates new form GestionObjets */
	public DescriptionSalleCtrl(Component parent, EOEditingContext context) {
		super((JDialog) parent, "Gestion des salles", true);
		eContext = context;
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
		salleFactory = new SalleFactory(eContext);
		NSArray<TypeSalle> typesSalles = salleFactory.getTypesSalles();
		WidgetHandler.setObjectsToComboBox(typesSalles, comboTypeSalle);
		setAllowEdition(false);
	}

	private void showInfosSalle() {

		cleanAll();

		Number numberValue = null;
		String stringValue = "";

		numberValue = salle.salNbArmoires();
		if (numberValue != null) {
			stringValue = String.valueOf(numberValue.intValue());
			tArmoires.setText(stringValue);
		}

		numberValue = salle.salNbBureaux();
		if (numberValue != null) {
			stringValue = String.valueOf(numberValue.intValue());
			tBureaux.setText(stringValue);
		}

		numberValue = salle.salCapacite();
		if (numberValue != null) {
			stringValue = String.valueOf(numberValue.intValue());
			tCapacite.setText(stringValue);
		}

		numberValue = salle.salNbChaises();
		if (numberValue != null) {
			stringValue = String.valueOf(numberValue.intValue());
			tChaises.setText(stringValue);
		}

		numberValue = salle.salNbFenetres();
		if (numberValue != null) {
			stringValue = String.valueOf(numberValue.intValue());
			tFenetre.setText(stringValue);
		}

		numberValue = salle.salSuperficie();
		if (numberValue != null) {
			stringValue = String.valueOf(numberValue.intValue());
			tSuperficie.setText(stringValue);
		}

		numberValue = salle.salNbTables();
		if (numberValue != null) {
			stringValue = String.valueOf(numberValue.intValue());
			tTables.setText(stringValue);
		}

		tDescSalle.setText(salle.salDescriptif());
		tEtage.setText(salle.salEtage());
		tPorte.setText(salle.salPorte());

		comboTypeSalle.setSelectedItem(salle.typeSalle());
	}

	private void cleanAll() {

		tArmoires.setText("");
		tBureaux.setText("");
		tCapacite.setText("");
		tChaises.setText("");
		tDescSalle.setText("");
		tEtage.setText("");
		tFenetre.setText("");
		tPorte.setText("");
		tSuperficie.setText("");
		tTables.setText("");
		comboTypeSalle.setSelectedItem(null);
	}

	private void setAllowEdition(boolean bool) {

		Color noEditionColor = Color.decode("0xE8EEFA");
		Color editionColor = Color.WHITE;

		if (bool) {
			tArmoires.setBackground(editionColor);
			tBureaux.setBackground(editionColor);
			tCapacite.setBackground(editionColor);
			tChaises.setBackground(editionColor);
			tDescSalle.setBackground(editionColor);
			tEtage.setBackground(editionColor);
			tFenetre.setBackground(editionColor);
			tPorte.setBackground(editionColor);
			tSuperficie.setBackground(editionColor);
			tTables.setBackground(editionColor);
		}
		else {
			tArmoires.setBackground(noEditionColor);
			tBureaux.setBackground(noEditionColor);
			tCapacite.setBackground(noEditionColor);
			tChaises.setBackground(noEditionColor);
			tDescSalle.setBackground(noEditionColor);
			tEtage.setBackground(noEditionColor);
			tFenetre.setBackground(noEditionColor);
			tPorte.setBackground(noEditionColor);
			tSuperficie.setBackground(noEditionColor);
			tTables.setBackground(noEditionColor);
		}

		tArmoires.setEditable(bool);
		tBureaux.setEditable(bool);
		tCapacite.setEditable(bool);
		tChaises.setEditable(bool);
		tDescSalle.setEditable(bool);
		tEtage.setEditable(bool);
		tFenetre.setEditable(bool);
		tPorte.setEditable(bool);
		tSuperficie.setEditable(bool);
		tTables.setEditable(bool);
		comboTypeSalle.setEnabled(bool);
	}

	public Salles getSalle() {
		return salle;
	}

	public void setSalle(Salles salle) {
		this.salle = salle;
		showInfosSalle();
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		tabbedPane = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		tPorte = new javax.swing.JTextField();
		tEtage = new javax.swing.JTextField();
		comboTypeSalle = new javax.swing.JComboBox();
		tCapacite = new javax.swing.JTextField();
		tSuperficie = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		tFenetre = new javax.swing.JTextField();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		tDescSalle = new javax.swing.JTextField();
		tChaises = new javax.swing.JTextField();
		jLabel8 = new javax.swing.JLabel();
		tTables = new javax.swing.JTextField();
		jLabel9 = new javax.swing.JLabel();
		tBureaux = new javax.swing.JTextField();
		jLabel10 = new javax.swing.JLabel();
		tArmoires = new javax.swing.JTextField();
		jLabel11 = new javax.swing.JLabel();
		bValider = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		comboTypeSalle.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

		jLabel1.setText("porte - intitulé de la salle");

		jLabel2.setText("étage");

		jLabel3.setText("type de salle");

		jLabel4.setText("capacité");

		jLabel5.setText("supérficie");

		jLabel6.setText("fenêtres");

		jLabel7.setText("descriptif de la salle");

		jLabel8.setText("chaises");

		jLabel9.setText("tables");

		jLabel10.setText("bureaux");

		jLabel11.setText("armoires");

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
						jPanel1Layout
								.createSequentialGroup()
								.addContainerGap()
								.add(jPanel1Layout
										.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(tPorte, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 309,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
												.add(18, 18, 18)
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
														.add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.add(tEtage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
														.add(jPanel1Layout
																.createSequentialGroup()
																.add(comboTypeSalle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 283,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 88,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).addContainerGap())
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(tCapacite, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tSuperficie, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tFenetre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap(496, Short.MAX_VALUE))
										.add(jPanel1Layout.createSequentialGroup()
												.add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
												.add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE).add(498, 498, 498))
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 144,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap(658, Short.MAX_VALUE))
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(tChaises, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(jLabel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(tTables, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(jLabel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(tBureaux, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(jLabel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(tArmoires, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(jLabel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
												.add(392, 392, 392))
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(tDescSalle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 475,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap()))));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel1Layout
								.createSequentialGroup()
								.add(15, 15, 15)
								.add(jPanel1Layout
										.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jLabel1)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tPorte, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jLabel2)
														.add(jLabel3))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jPanel1Layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
														.add(tEtage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(comboTypeSalle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jLabel4).add(jLabel5)
										.add(jLabel6))
								.add(4, 4, 4)
								.add(jPanel1Layout
										.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
										.add(tCapacite, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(tSuperficie, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(tFenetre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(18, 18, 18)
								.add(jLabel7)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(tDescSalle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
								.add(jPanel1Layout
										.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jLabel8)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tChaises, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jLabel9)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tTables, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jLabel10)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tBureaux, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel1Layout
												.createSequentialGroup()
												.add(jLabel11)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(tArmoires, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(36, Short.MAX_VALUE)));

		tabbedPane.addTab("Description", jPanel1);

		bValider.setText("Fermer");
		bValider.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bValiderActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(org.jdesktop.layout.GroupLayout.TRAILING,
						layout.createSequentialGroup()
								.add(layout
										.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
										.add(bValider)
										.add(tabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 746,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.add(tabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
						.add(bValider).addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void bValiderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bValiderActionPerformed
		setVisible(false);
	}// GEN-LAST:event_bValiderActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bValider;
	private javax.swing.JComboBox comboTypeSalle;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JTextField tArmoires;
	private javax.swing.JTextField tBureaux;
	private javax.swing.JTextField tCapacite;
	private javax.swing.JTextField tChaises;
	private javax.swing.JTextField tDescSalle;
	private javax.swing.JTextField tEtage;
	private javax.swing.JTextField tFenetre;
	private javax.swing.JTextField tPorte;
	private javax.swing.JTextField tSuperficie;
	private javax.swing.JTextField tTables;
	private javax.swing.JTabbedPane tabbedPane;
	// End of variables declaration//GEN-END:variables

}
