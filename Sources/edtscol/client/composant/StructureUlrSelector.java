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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.cocktail.superplan.client.metier.RepartTypeGroupe;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eodistribution.client.EODistributedDataSource;
import com.webobjects.eointerface.EODisplayGroup;

import fr.univlr.common.utilities.DBHandler;

public class StructureUlrSelector extends javax.swing.JDialog {

	private EOEditingContext eContext;
	private EODisplayGroup eodStructureUlr;
	private ZEOTable tableStructureUlr;

	private StructureUlr selectedStructure;

	private boolean deposOnly;

	/** Creates new form StructureUlrSelector */
	public StructureUlrSelector(JDialog window, EOEditingContext context, boolean deposOnly) {
		super(window, "Sélectionner une structure", true);
		eContext = context;
		initComponents();
		initExtra();
		this.deposOnly = deposOnly;
	}

	private void rechercherStructure() {
		String libelle = tLibelleStructure.getText();
		EOQualifier qual;
		if (deposOnly) {
			qual = EOQualifier.qualifierWithQualifierFormat(StructureUlr.LL_STRUCTURE_KEY + " caseInsensitiveLike '*" + libelle + "*' or "
					+ StructureUlr.LC_STRUCTURE_KEY + " like '*" + libelle + "*' and " + StructureUlr.REPART_TYPE_GROUPE_KEY + "."
					+ RepartTypeGroupe.TGRP_CODE_KEY + " <> 'E' and " + StructureUlr.LC_STRUCTURE_KEY + " like 'DEPOS*'", null);
		}
		else {
			qual = EOQualifier.qualifierWithQualifierFormat(StructureUlr.LL_STRUCTURE_KEY + " caseInsensitiveLike '*" + libelle + "*' or "
					+ StructureUlr.LC_STRUCTURE_KEY + " like '*" + libelle + "*' and " + StructureUlr.REPART_TYPE_GROUPE_KEY + "."
					+ RepartTypeGroupe.TGRP_CODE_KEY + " <> 'E'", null);
		}
		DBHandler.fetchDisplayGroup(eodStructureUlr, qual);
		tableStructureUlr.updateData();
		tableStructureUlr.updateUI();
	}

	private void initExtra() {
		EODistributedDataSource structureUlrSource = new EODistributedDataSource(eContext, StructureUlr.ENTITY_NAME);
		eodStructureUlr = new EODisplayGroup();
		eodStructureUlr.setDataSource(structureUlrSource);
		// eodDoctorant.fetch();
		java.util.Vector<ZEOTableModelColumn> columns = new java.util.Vector<ZEOTableModelColumn>();
		columns.add(new ZEOTableModelColumn(eodStructureUlr, StructureUlr.LL_STRUCTURE_KEY, "Libelle"));
		columns.add(new ZEOTableModelColumn(eodStructureUlr, StructureUlr.LC_STRUCTURE_KEY, "Code"));
		columns.add(new ZEOTableModelColumn(eodStructureUlr, StructureUlr.STRUCTURE_ULR_KEY + "." + StructureUlr.LL_STRUCTURE_KEY, "Groupe père"));

		// ZEOTableModelColumn anneeInscCol = new ZEOTableModelColumn(eodResaObjetDepositaire,"historique.histAnneeScol","Inscription");
		// columns.add(anneeInscCol);

		ZEOTableModel structureUlrModel = new ZEOTableModel(eodStructureUlr, columns);
		tableStructureUlr = new ZEOTable(structureUlrModel);

		JScrollPane tableScroll = new JScrollPane(tableStructureUlr);
		tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTableStructures.removeAll();

		tableScroll.setPreferredSize(panelTableStructures.getPreferredSize());

		panelTableStructures.setLayout(new BorderLayout());
		panelTableStructures.add(tableScroll, BorderLayout.NORTH);

		tLibelleStructure.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rechercherStructure();
			}
		});
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		tLibelleStructure = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		panelTableStructures = new javax.swing.JPanel();
		bValider = new javax.swing.JButton();
		bAnnuler = new javax.swing.JButton();

		jLabel1.setText("Libellé ");

		org.jdesktop.layout.GroupLayout panelTableStructuresLayout = new org.jdesktop.layout.GroupLayout(panelTableStructures);
		panelTableStructures.setLayout(panelTableStructuresLayout);
		panelTableStructuresLayout.setHorizontalGroup(panelTableStructuresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0,
				527, Short.MAX_VALUE));
		panelTableStructuresLayout.setVerticalGroup(panelTableStructuresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0,
				247, Short.MAX_VALUE));

		bValider.setText("Valider");
		bValider.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bValiderActionPerformed(evt);
			}
		});

		bAnnuler.setText("Annuler");
		bAnnuler.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAnnulerActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
								.add(panelTableStructures, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.add(layout
										.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
										.add(layout.createSequentialGroup().add(bAnnuler).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(bValider))
										.add(org.jdesktop.layout.GroupLayout.LEADING,
												layout.createSequentialGroup()
														.add(jLabel1)
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
														.add(tLibelleStructure, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 230,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel1)
								.add(tLibelleStructure, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
						.add(panelTableStructures, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 18, Short.MAX_VALUE)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(bValider).add(bAnnuler)).addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void bValiderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bValiderActionPerformed
		selectedStructure = (StructureUlr) eodStructureUlr.selectedObject();
		setVisible(false);
	}// GEN-LAST:event_bValiderActionPerformed

	private void bAnnulerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAnnulerActionPerformed
		selectedStructure = null;
		setVisible(false);
	}// GEN-LAST:event_bAnnulerActionPerformed

	public StructureUlr getSelectedStructure() {
		return selectedStructure;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bAnnuler;
	private javax.swing.JButton bValider;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel panelTableStructures;
	private javax.swing.JTextField tLibelleStructure;
	// End of variables declaration//GEN-END:variables

}
