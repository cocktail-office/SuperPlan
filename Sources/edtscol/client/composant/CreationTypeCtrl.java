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

import javax.swing.JDialog;

public class CreationTypeCtrl extends JDialog {

	private String label;

	/** Creates new form CreationTypeCtrl */
	public CreationTypeCtrl(JDialog owner, String title) {
		super(owner, title, true);
		initComponents();
		setTitle(title);
		setLocation(owner.getX() + 20, owner.getY() + 20);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		tLabel = new javax.swing.JTextField();
		bValider = new javax.swing.JButton();
		bAnnuler = new javax.swing.JButton();

		jLabel1.setText("Libellé :");

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
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
								.add(layout
										.createSequentialGroup()
										.add(bAnnuler, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(bValider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(layout
										.createSequentialGroup()
										.add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(tLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 348,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel1)
								.add(tLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 36, Short.MAX_VALUE)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(bValider).add(bAnnuler)).add(30, 30, 30)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void bValiderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bValiderActionPerformed
		setLabel(tLabel.getText());
		setVisible(false);// GEN-LAST:event_bValiderActionPerformed
	}

	private void bAnnulerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAnnulerActionPerformed
		label = null;
		setVisible(false);
	}// GEN-LAST:event_bAnnulerActionPerformed

	public String getLabel() {
		return label;
	}

	public void setLabel(String aLabel) {
		label = aLabel;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bAnnuler;
	private javax.swing.JButton bValider;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField tLabel;
	// End of variables declaration//GEN-END:variables

}