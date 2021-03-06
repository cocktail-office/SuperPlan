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

public class SaisieParametresHabilitationView extends javax.swing.JDialog {

	public SaisieParametresHabilitationView(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroupJours = new javax.swing.ButtonGroup();
		bValider = new javax.swing.JButton();
		bAnnuler = new javax.swing.JButton();
		jPanel465 = new javax.swing.JPanel();
		labelHrDebut = new javax.swing.JLabel();
		labelHrFin = new javax.swing.JLabel();
		tHrDeb = new javax.swing.JTextField();
		tMinDeb = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		tHrFin = new javax.swing.JTextField();
		jLabel5 = new javax.swing.JLabel();
		tMinFin = new javax.swing.JTextField();
		jPanel1 = new javax.swing.JPanel();
		jCheckBoxLun = new javax.swing.JCheckBox();
		jCheckBoxMar = new javax.swing.JCheckBox();
		jCheckBoxMer = new javax.swing.JCheckBox();
		jCheckBoxJeu = new javax.swing.JCheckBox();
		jCheckBoxVen = new javax.swing.JCheckBox();
		jCheckBoxSam = new javax.swing.JCheckBox();
		jCheckBoxDim = new javax.swing.JCheckBox();

		setTitle("Saisie des paramètres de la formation");
		setResizable(false);

		bValider.setMaximumSize(new java.awt.Dimension(30, 30));
		bValider.setMinimumSize(new java.awt.Dimension(30, 30));
		bValider.setPreferredSize(new java.awt.Dimension(30, 30));

		bAnnuler.setMaximumSize(new java.awt.Dimension(30, 30));
		bAnnuler.setMinimumSize(new java.awt.Dimension(30, 30));
		bAnnuler.setPreferredSize(new java.awt.Dimension(30, 30));

		jPanel465.setBorder(javax.swing.BorderFactory.createTitledBorder("Paramètres de la formation"));
		jPanel465.setPreferredSize(new java.awt.Dimension(260, 60));

		labelHrDebut.setFont(new java.awt.Font("Tahoma", 0, 10));
		labelHrDebut.setText("Heures de");

		labelHrFin.setFont(new java.awt.Font("Tahoma", 0, 10));
		labelHrFin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelHrFin.setText("à");

		tHrDeb.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
		tHrDeb.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		tHrDeb.setMaximumSize(new java.awt.Dimension(26, 19));
		tHrDeb.setMinimumSize(new java.awt.Dimension(26, 19));
		tHrDeb.setPreferredSize(new java.awt.Dimension(26, 19));
		tHrDeb.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tHrDebActionPerformed(evt);
			}
		});

		tMinDeb.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
		tMinDeb.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		tMinDeb.setMaximumSize(new java.awt.Dimension(26, 19));
		tMinDeb.setMinimumSize(new java.awt.Dimension(26, 19));
		tMinDeb.setPreferredSize(new java.awt.Dimension(26, 19));

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10));
		jLabel4.setText(":");

		tHrFin.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
		tHrFin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		tHrFin.setMaximumSize(new java.awt.Dimension(26, 19));
		tHrFin.setMinimumSize(new java.awt.Dimension(26, 19));
		tHrFin.setPreferredSize(new java.awt.Dimension(26, 19));

		jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10));
		jLabel5.setText(":");

		tMinFin.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
		tMinFin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		tMinFin.setMaximumSize(new java.awt.Dimension(26, 19));
		tMinFin.setMinimumSize(new java.awt.Dimension(26, 19));
		tMinFin.setPreferredSize(new java.awt.Dimension(26, 19));

		jCheckBoxLun.setText("Lundi");
		jPanel1.add(jCheckBoxLun);

		jCheckBoxMar.setText("Mardi");
		jPanel1.add(jCheckBoxMar);

		jCheckBoxMer.setText("Mercredi");
		jPanel1.add(jCheckBoxMer);

		jCheckBoxJeu.setText("Jeudi");
		jPanel1.add(jCheckBoxJeu);

		jCheckBoxVen.setText("Vendredi");
		jPanel1.add(jCheckBoxVen);

		jCheckBoxSam.setText("Samedi");
		jPanel1.add(jCheckBoxSam);

		jCheckBoxDim.setText("Dimanche");
		jPanel1.add(jCheckBoxDim);

		org.jdesktop.layout.GroupLayout jPanel465Layout = new org.jdesktop.layout.GroupLayout(jPanel465);
		jPanel465.setLayout(jPanel465Layout);
		jPanel465Layout.setHorizontalGroup(jPanel465Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel465Layout
						.createSequentialGroup()
						.addContainerGap()
						.add(jPanel465Layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.add(jPanel465Layout
										.createSequentialGroup()
										.add(labelHrDebut, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(tHrDeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jLabel4)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(tMinDeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(labelHrFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(tHrFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jLabel5)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(tMinFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		jPanel465Layout.setVerticalGroup(jPanel465Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel465Layout
						.createSequentialGroup()
						.add(jPanel465Layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(labelHrDebut)
								.add(tHrDeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jLabel4)
								.add(tMinDeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(labelHrFin)
								.add(tHrFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jLabel5)
								.add(tMinFin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
						.add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.add(jPanel465, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
						.add(18, 18, 18)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(bValider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(bAnnuler, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout
										.createSequentialGroup()
										.add(19, 19, 19)
										.add(bValider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
										.add(bAnnuler, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(layout
										.createSequentialGroup()
										.addContainerGap()
										.add(jPanel465, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void tHrDebActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tHrDebActionPerformed
	}// GEN-LAST:event_tHrDebActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SaisieParametresApView dialog = new SaisieParametresApView(new javax.swing.JFrame(), true);
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
	protected javax.swing.JButton bAnnuler;
	protected javax.swing.JButton bValider;
	protected javax.swing.ButtonGroup buttonGroupJours;
	protected javax.swing.JCheckBox jCheckBoxDim;
	protected javax.swing.JCheckBox jCheckBoxJeu;
	protected javax.swing.JCheckBox jCheckBoxLun;
	protected javax.swing.JCheckBox jCheckBoxMar;
	protected javax.swing.JCheckBox jCheckBoxMer;
	protected javax.swing.JCheckBox jCheckBoxSam;
	protected javax.swing.JCheckBox jCheckBoxVen;
	protected javax.swing.JLabel jLabel4;
	protected javax.swing.JLabel jLabel5;
	protected javax.swing.JPanel jPanel1;
	protected javax.swing.JPanel jPanel465;
	protected javax.swing.JLabel labelHrDebut;
	protected javax.swing.JLabel labelHrFin;
	protected javax.swing.JTextField tHrDeb;
	protected javax.swing.JTextField tHrFin;
	protected javax.swing.JTextField tMinDeb;
	protected javax.swing.JTextField tMinFin;

	// End of variables declaration//GEN-END:variables

	public javax.swing.JButton getBValider() {
		return bValider;
	}

	public javax.swing.JButton getBAnnuler() {
		return bAnnuler;
	}

	public javax.swing.JTextField getTHrDeb() {
		return tHrDeb;
	}

	public javax.swing.JTextField getTHrFin() {
		return tHrFin;
	}

	public javax.swing.JTextField getTMinDeb() {
		return tMinDeb;
	}

	public javax.swing.JTextField getTMinFin() {
		return tMinFin;
	}

	public javax.swing.JCheckBox getjCheckBoxDim() {
		return jCheckBoxDim;
	}

	public javax.swing.JCheckBox getjCheckBoxJeu() {
		return jCheckBoxJeu;
	}

	public javax.swing.JCheckBox getjCheckBoxLun() {
		return jCheckBoxLun;
	}

	public javax.swing.JCheckBox getjCheckBoxMar() {
		return jCheckBoxMar;
	}

	public javax.swing.JCheckBox getjCheckBoxMer() {
		return jCheckBoxMer;
	}

	public javax.swing.JCheckBox getjCheckBoxSam() {
		return jCheckBoxSam;
	}

	public javax.swing.JCheckBox getjCheckBoxVen() {
		return jCheckBoxVen;
	}

}
