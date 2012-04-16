/*
 * Copyright COCKTAIL (www.cocktail.org), 2001, 2012 
 * 
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software. You can use, 
 * modify and/or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty and the software's author, the holder of the
 * economic rights, and the successive licensors have only limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading, using, modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean that it is complicated to manipulate, and that also
 * therefore means that it is reserved for developers and experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and, more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package org.cocktail.superplan.client.composant;

import java.awt.Component;

import org.cocktail.superplan.client.metier.TypeMotifLog;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;

import edtscol.client.MainClient;

public class SaisieMotifPourLog extends javax.swing.JDialog {
	private static SaisieMotifPourLog sharedInstance;
	private Component parent;
	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EOEditingContext eContext;
	private String motifSaisi = null;
	private String action = null;

	/** Creates new form SaisieMotifPourLog */
	public SaisieMotifPourLog(Component parent) {
		super(new javax.swing.JFrame(), true);
		initComponents();
		this.parent = parent;
		this.eContext = app.editingContext();
		init();
	}

	public static SaisieMotifPourLog sharedInstance() {
		if (sharedInstance == null) {
			sharedInstance = new SaisieMotifPourLog(null);
		}
		return sharedInstance;
	}

	public void open(String action) {
		if (equals(action, this.action) == false) {
			this.action = action;
			init();
		}
		if (parent != null) {
			setLocation(parent.getX() + 20, parent.getY() + 20);
		}
		else {
			center();
		}
		setVisible(true);
	}

	private void center() {
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);
	}

	public String getMotifSaisi() {
		return motifSaisi;
	}

	private void init() {
		cbMotifPreSaisi.removeAllItems();
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>(2);
		quals.addObject(new EOKeyValueQualifier(TypeMotifLog.TML_ACTION_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, action));
		quals.addObject(new EOKeyValueQualifier(TypeMotifLog.TML_ACTION_KEY, EOKeyValueQualifier.QualifierOperatorEqual, NSKeyValueCoding.NullValue));
		NSArray<TypeMotifLog> types = TypeMotifLog.fetchTypeMotifLogs(eContext, new EOOrQualifier(quals), null);
		cbMotifPreSaisi.addItem("Autre...");
		if (types != null) {
			for (int i = 0; i < types.count(); i++) {
				cbMotifPreSaisi.addItem(types.objectAtIndex(i));
			}
		}
	}

	private void bValiderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bValiderActionPerformed
		if (cbMotifPreSaisi.getSelectedIndex() == 0) {
			motifSaisi = tfMotifAutre.getText();
		}
		else {
			motifSaisi = ((TypeMotifLog) cbMotifPreSaisi.getSelectedItem()).tmlMotif();
		}
		if (motifSaisi != null && motifSaisi.trim().length() > 0) {
			setVisible(false);
		}
	}// GEN-LAST:event_bValiderActionPerformed

	private void tfMotifAutreActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfMotifAutreActionPerformed
		bValiderActionPerformed(evt);
	}// GEN-LAST:event_tfMotifAutreActionPerformed

	private void cbMotifPreSaisiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbMotifPreSaisiActionPerformed
		if (cbMotifPreSaisi.getSelectedIndex() == 0) {
			tfMotifAutre.setVisible(true);
			tfMotifAutre.requestFocus();
		}
		else {
			tfMotifAutre.setVisible(false);
		}
	}// GEN-LAST:event_cbMotifPreSaisiActionPerformed

	private boolean equals(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 == null && s2 != null) {
			return false;
		}
		return s1.equals(s2);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		cbMotifPreSaisi = new javax.swing.JComboBox();
		tfMotifAutre = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		bValider = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Motif de modification / suppression de réservation");
		setAlwaysOnTop(true);
		setModal(true);
		setResizable(false);

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel2.setText("Motif :");

		cbMotifPreSaisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autre...", "Déplacement de cours à la demande de l'enseignant",
				"Déplacement de cours pour cause extérieure", "Annulation de cours", "Réduction du nombre de groupes", "Erreur matérielle" }));
		cbMotifPreSaisi.setMaximumSize(new java.awt.Dimension(273, 20));
		cbMotifPreSaisi.setMinimumSize(new java.awt.Dimension(273, 20));
		cbMotifPreSaisi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbMotifPreSaisiActionPerformed(evt);
			}
		});

		tfMotifAutre.setPreferredSize(new java.awt.Dimension(250, 20));
		tfMotifAutre.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfMotifAutreActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(10, 10, 10)
						.add(cbMotifPreSaisi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(10, 10, 10)
						.add(tfMotifAutre, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE).addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel1Layout
						.createSequentialGroup()
						.add(jPanel1Layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(jPanel1Layout
										.createSequentialGroup()
										.add(20, 20, 20)
										.add(cbMotifPreSaisi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(jPanel1Layout.createSequentialGroup().add(23, 23, 23).add(jLabel2))
								.add(jPanel1Layout
										.createSequentialGroup()
										.add(20, 20, 20)
										.add(tfMotifAutre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(20, Short.MAX_VALUE)));

		getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

		jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

		bValider.setText("Valider");
		bValider.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bValiderActionPerformed(evt);
			}
		});
		jPanel2.add(bValider);

		getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

		jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel1.setText("Donnez un motif (obligatoire) pour l'opération que vous êtes en train de réaliser sur la réservation...");
		jPanel3.add(jLabel1);

		getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bValider;
	private javax.swing.JComboBox cbMotifPreSaisi;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JTextField tfMotifAutre;
	// End of variables declaration//GEN-END:variables

}
