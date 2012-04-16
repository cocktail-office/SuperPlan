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
package edtscol.client.composant.recherche;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTable.ZEOTableListener;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedDataSource;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class RechercheIndividu extends javax.swing.JDialog {

	private EOEditingContext eContext;
	private EODisplayGroup eodIndividu = new EODisplayGroup();
	private ZEOTable tableIndividu;
	private IndividuUlr selectedIndividu;

	public RechercheIndividu(Dialog window, EOEditingContext context) {
		super(window, "Rechercher un individu", true);
		eContext = context;
		initComponents();
		initExtra();
	}

	private void initExtra() {
		eodIndividu.setDataSource(new EODistributedDataSource(eContext, IndividuUlr.ENTITY_NAME));

		Vector<ZEOTableModelColumn> columns = new Vector<ZEOTableModelColumn>();
		columns.add(new ZEOTableModelColumn(eodIndividu, IndividuUlr.C_CIVILITE_KEY, "Civilité"));
		columns.add(new ZEOTableModelColumn(eodIndividu, IndividuUlr.NOM_USUEL_KEY, "Nom"));
		columns.add(new ZEOTableModelColumn(eodIndividu, IndividuUlr.PRENOM_KEY, "Prénom"));
		columns.add(new ZEOTableModelColumn(eodIndividu, IndividuUlr.IND_QUALITE_KEY, "Qualité"));

		tableIndividu = new ZEOTable(new ZEOTableModel(eodIndividu, columns));
		tableIndividu.addListener(new TableListener());

		JScrollPane tableScroll = new JScrollPane(tableIndividu);
		add(tableScroll, BorderLayout.CENTER);
		setPreferredSize(new Dimension(450, 350));
		pack();
	}

	private class TableListener implements ZEOTableListener {
		public void onDbClick() {
			bValiderActionPerformed(null);
		}

		public void onSelectionChanged() {
		}
	}

	private void rechercher() {
		String nom = tNom.getText().trim();
		String prenom = tPrenom.getText().trim();
		if (nom.equals("") && prenom.equals("")) {
			return;
		}
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.addObject(new EOKeyValueQualifier(IndividuUlr.NOM_USUEL_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*" + nom + "*"));
		quals.addObject(new EOKeyValueQualifier(IndividuUlr.PRENOM_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*" + prenom + "*"));
		quals.addObject(new EOKeyValueQualifier(IndividuUlr.TEM_VALIDE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O"));
		if (rbRechercheEtudiants.isSelected()) {
			// etudiants
			quals.addObject(IndividuUlr.getQualifierForStudent());
		}
		else {
			// non etudiants
			quals.addObject(IndividuUlr.getQualifierForNonStudent());
		}

		NSArray<IndividuUlr> data = IndividuUlr.fetchIndividuUlrs(eContext, new EOAndQualifier(quals), new NSArray<EOSortOrdering>(
				new EOSortOrdering[] { IndividuUlr.SORT_NOM, IndividuUlr.SORT_PRENOM }));
		eodIndividu.setObjectArray(data);
		tableIndividu.updateData();
	}

	private void tNomActionPerformed(java.awt.event.ActionEvent evt) {
		rechercher();
	}

	private void tPrenomActionPerformed(java.awt.event.ActionEvent evt) {
		rechercher();
	}

	private void bAnnulerActionPerformed(java.awt.event.ActionEvent evt) {
		setSelectedIndividu(null);
		setVisible(false);
	}

	private void bValiderActionPerformed(java.awt.event.ActionEvent evt) {
		setSelectedIndividu((IndividuUlr) eodIndividu.selectedObject());
		setVisible(false);
	}

	public IndividuUlr getSelectedIndividu() {
		return selectedIndividu;
	}

	private void setSelectedIndividu(IndividuUlr selectedIndividu) {
		this.selectedIndividu = selectedIndividu;
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		tNom = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		tPrenom = new javax.swing.JTextField();
		rbRechercheNonEtudiants = new javax.swing.JRadioButton();
		rbRechercheEtudiants = new javax.swing.JRadioButton();
		jPanel3 = new javax.swing.JPanel();
		bValider = new javax.swing.JButton();
		bAnnuler = new javax.swing.JButton();

		jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 2));

		jLabel1.setText("Nom :");
		jPanel1.add(jLabel1);

		tNom.setPreferredSize(new java.awt.Dimension(80, 20));
		tNom.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tNomActionPerformed(evt);
			}
		});
		jPanel1.add(tNom);

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		jLabel2.setText("Prénom :");
		jPanel1.add(jLabel2);

		tPrenom.setPreferredSize(new java.awt.Dimension(60, 20));
		tPrenom.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tPrenomActionPerformed(evt);
			}
		});
		jPanel1.add(tPrenom);

		buttonGroup1.add(rbRechercheNonEtudiants);
		rbRechercheNonEtudiants.setSelected(true);
		rbRechercheNonEtudiants.setText("Non étudiants");
		rbRechercheNonEtudiants.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rbRechercheNonEtudiantsActionPerformed(evt);
			}
		});
		jPanel1.add(rbRechercheNonEtudiants);

		buttonGroup1.add(rbRechercheEtudiants);
		rbRechercheEtudiants.setText("Etudiants");
		rbRechercheEtudiants.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rbRechercheEtudiantsActionPerformed(evt);
			}
		});
		jPanel1.add(rbRechercheEtudiants);

		getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

		jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 2, 2));

		bValider.setText("Valider");
		bValider.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bValiderActionPerformed(evt);
			}
		});
		jPanel3.add(bValider);

		bAnnuler.setText("Annuler");
		bAnnuler.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAnnulerActionPerformed(evt);
			}
		});
		jPanel3.add(bAnnuler);

		getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void rbRechercheNonEtudiantsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbRechercheNonEtudiantsActionPerformed
		rechercher();
	}// GEN-LAST:event_rbRechercheNonEtudiantsActionPerformed

	private void rbRechercheEtudiantsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbRechercheEtudiantsActionPerformed
		rechercher();
	}// GEN-LAST:event_rbRechercheEtudiantsActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bAnnuler;
	private javax.swing.JButton bValider;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JRadioButton rbRechercheEtudiants;
	private javax.swing.JRadioButton rbRechercheNonEtudiants;
	private javax.swing.JTextField tNom;
	private javax.swing.JTextField tPrenom;
	// End of variables declaration//GEN-END:variables

}
