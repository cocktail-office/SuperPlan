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

public class ReservationAutoView extends javax.swing.JDialog {

	public ReservationAutoView(java.awt.Frame parent, boolean modal) {
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

        buttonGroupSave = new javax.swing.ButtonGroup();
        buttonGroupPlacementTous = new javax.swing.ButtonGroup();
        buttonGroupPriorite = new javax.swing.ButtonGroup();
        buttonGroupGroupes = new javax.swing.ButtonGroup();
        buttonGroupEviteSemainesDejaPlacees = new javax.swing.ButtonGroup();
        vueOptions = new javax.swing.JPanel();
        rbSaveSiTous = new javax.swing.JRadioButton();
        rbSaveToutLeTemps = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jCheckBoxIntervalleMinimum = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tIntervalleTempsMinimum = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        rbPlaceSiParams = new javax.swing.JRadioButton();
        rbPlaceTous = new javax.swing.JRadioButton();
        jSeparator3 = new javax.swing.JSeparator();
        labelPriorite = new javax.swing.JLabel();
        rbPrioriteHoraire = new javax.swing.JRadioButton();
        rbPrioriteSalle = new javax.swing.JRadioButton();
        jSeparator4 = new javax.swing.JSeparator();
        rbGroupesSelection = new javax.swing.JRadioButton();
        labelPriorite1 = new javax.swing.JLabel();
        rbGroupesTous = new javax.swing.JRadioButton();
        rbGroupesAp = new javax.swing.JRadioButton();
        jSeparator5 = new javax.swing.JSeparator();
        labelPriorite2 = new javax.swing.JLabel();
        rbEviteSemainesDejaPlaceesOui = new javax.swing.JRadioButton();
        rbEviteSemainesDejaPlaceesNon = new javax.swing.JRadioButton();
        bValider = new javax.swing.JButton();
        bAnnuler = new javax.swing.JButton();
        jPanel465 = new javax.swing.JPanel();
        labelSemaineDebut = new javax.swing.JLabel();
        tSemaines = new javax.swing.JTextField();
        jCheckBoxBloqueHeures = new javax.swing.JCheckBox();

        setTitle("Réservation automatique");
        setResizable(false);

        vueOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        vueOptions.setPreferredSize(new java.awt.Dimension(420, 491));
        vueOptions.setLayout(new javax.swing.BoxLayout(vueOptions, javax.swing.BoxLayout.PAGE_AXIS));

        buttonGroupSave.add(rbSaveSiTous);
        rbSaveSiTous.setSelected(true);
        rbSaveSiTous.setText("Enregister uniquement si tous les APs sont placés");
        rbSaveSiTous.setAlignmentY(0.0F);
        vueOptions.add(rbSaveSiTous);

        buttonGroupSave.add(rbSaveToutLeTemps);
        rbSaveToutLeTemps.setText("Enregistrer les APs qui peuvent être placés, et tant pis pour les autres");
        vueOptions.add(rbSaveToutLeTemps);

        jSeparator1.setRequestFocusEnabled(false);
        vueOptions.add(jSeparator1);

        jCheckBoxIntervalleMinimum.setText("Laisser un intervalle de temps minimum entre chaque réservation");
        vueOptions.add(jCheckBoxIntervalleMinimum);

        jPanel1.setMaximumSize(new java.awt.Dimension(400, 32767));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 31));

        jLabel2.setText("Temps minimum :");
        jPanel1.add(jLabel2);

        tIntervalleTempsMinimum.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(tIntervalleTempsMinimum);

        jLabel1.setText("(minutes)");
        jPanel1.add(jLabel1);

        vueOptions.add(jPanel1);

        jSeparator2.setRequestFocusEnabled(false);
        vueOptions.add(jSeparator2);

        buttonGroupPlacementTous.add(rbPlaceSiParams);
        rbPlaceSiParams.setSelected(true);
        rbPlaceSiParams.setText("Placer uniquement les APs avec salle et enseignant");
        rbPlaceSiParams.setAlignmentY(0.0F);
        vueOptions.add(rbPlaceSiParams);

        buttonGroupPlacementTous.add(rbPlaceTous);
        rbPlaceTous.setText("Placer tous les APs sélectionnés, même sans salle et/ou sans enseignant");
        vueOptions.add(rbPlaceTous);

        jSeparator3.setRequestFocusEnabled(false);
        vueOptions.add(jSeparator3);

        labelPriorite.setText("Pour les réservations avec des salles à choix, privilégier la régularité sur :");
        vueOptions.add(labelPriorite);

        buttonGroupPriorite.add(rbPrioriteHoraire);
        rbPrioriteHoraire.setSelected(true);
        rbPrioriteHoraire.setText("l'horaire");
        rbPrioriteHoraire.setAlignmentY(0.0F);
        vueOptions.add(rbPrioriteHoraire);

        buttonGroupPriorite.add(rbPrioriteSalle);
        rbPrioriteSalle.setText("la salle");
        vueOptions.add(rbPrioriteSalle);

        jSeparator4.setRequestFocusEnabled(false);
        vueOptions.add(jSeparator4);

        buttonGroupGroupes.add(rbGroupesSelection);
        rbGroupesSelection.setSelected(true);
        rbGroupesSelection.setText("Placer exactement la sélection");
        vueOptions.add(rbGroupesSelection);

        labelPriorite1.setText("Ou sinon, pour chaque AP contenant des groupes :");
        vueOptions.add(labelPriorite1);

        buttonGroupGroupes.add(rbGroupesTous);
        rbGroupesTous.setText("Placer uniquement l'AP entière (sans aucun groupe = toute la promo)");
        rbGroupesTous.setAlignmentY(0.0F);
        vueOptions.add(rbGroupesTous);

        buttonGroupGroupes.add(rbGroupesAp);
        rbGroupesAp.setText("Placer uniquement les groupes de l'AP");
        vueOptions.add(rbGroupesAp);

        jSeparator5.setRequestFocusEnabled(false);
        vueOptions.add(jSeparator5);

        labelPriorite2.setText("Pour les réservations d'enseignement :");
        vueOptions.add(labelPriorite2);

        buttonGroupEviteSemainesDejaPlacees.add(rbEviteSemainesDejaPlaceesOui);
        rbEviteSemainesDejaPlaceesOui.setSelected(true);
        rbEviteSemainesDejaPlaceesOui.setText("Ne pas placer les semaines où l'AP est déjà réservé");
        rbEviteSemainesDejaPlaceesOui.setAlignmentY(0.0F);
        vueOptions.add(rbEviteSemainesDejaPlaceesOui);

        buttonGroupEviteSemainesDejaPlacees.add(rbEviteSemainesDejaPlaceesNon);
        rbEviteSemainesDejaPlaceesNon.setText("Placer toutes les semaines (et tant pis pour les semaines en doublon & +)");
        vueOptions.add(rbEviteSemainesDejaPlaceesNon);

        bValider.setMaximumSize(new java.awt.Dimension(30, 30));
        bValider.setMinimumSize(new java.awt.Dimension(30, 30));
        bValider.setPreferredSize(new java.awt.Dimension(30, 30));

        bAnnuler.setMaximumSize(new java.awt.Dimension(30, 30));
        bAnnuler.setMinimumSize(new java.awt.Dimension(30, 30));
        bAnnuler.setPreferredSize(new java.awt.Dimension(30, 30));

        jPanel465.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel465.setPreferredSize(new java.awt.Dimension(260, 60));

        labelSemaineDebut.setText("Semaine(s)");

        tSemaines.setFont(new java.awt.Font("Tahoma", 0, 10));
        tSemaines.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tSemaines.setMaximumSize(new java.awt.Dimension(26, 19));
        tSemaines.setMinimumSize(new java.awt.Dimension(26, 19));
        tSemaines.setPreferredSize(new java.awt.Dimension(26, 19));

        jCheckBoxBloqueHeures.setSelected(true);
        jCheckBoxBloqueHeures.setText("Bloque au nombre d'heures prévues");
        jCheckBoxBloqueHeures.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBoxBloqueHeures.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        org.jdesktop.layout.GroupLayout jPanel465Layout = new org.jdesktop.layout.GroupLayout(jPanel465);
        jPanel465.setLayout(jPanel465Layout);
        jPanel465Layout.setHorizontalGroup(
            jPanel465Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel465Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel465Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jCheckBoxBloqueHeures, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel465Layout.createSequentialGroup()
                        .add(labelSemaineDebut, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tSemaines, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 155, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(226, Short.MAX_VALUE))
        );
        jPanel465Layout.setVerticalGroup(
            jPanel465Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel465Layout.createSequentialGroup()
                .add(jPanel465Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelSemaineDebut)
                    .add(tSemaines, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(7, 7, 7)
                .add(jCheckBoxBloqueHeures, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(vueOptions, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .add(jPanel465, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 469, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(bValider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bAnnuler, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel465, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(vueOptions, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(123, 123, 123)
                        .add(bValider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(59, 59, 59)
                        .add(bAnnuler, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    protected javax.swing.ButtonGroup buttonGroupEviteSemainesDejaPlacees;
    protected javax.swing.ButtonGroup buttonGroupGroupes;
    protected javax.swing.ButtonGroup buttonGroupPlacementTous;
    protected javax.swing.ButtonGroup buttonGroupPriorite;
    protected javax.swing.ButtonGroup buttonGroupSave;
    protected javax.swing.JCheckBox jCheckBoxBloqueHeures;
    protected javax.swing.JCheckBox jCheckBoxIntervalleMinimum;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel465;
    protected javax.swing.JSeparator jSeparator1;
    protected javax.swing.JSeparator jSeparator2;
    protected javax.swing.JSeparator jSeparator3;
    protected javax.swing.JSeparator jSeparator4;
    protected javax.swing.JSeparator jSeparator5;
    protected javax.swing.JLabel labelPriorite;
    protected javax.swing.JLabel labelPriorite1;
    protected javax.swing.JLabel labelPriorite2;
    protected javax.swing.JLabel labelSemaineDebut;
    protected javax.swing.JRadioButton rbEviteSemainesDejaPlaceesNon;
    protected javax.swing.JRadioButton rbEviteSemainesDejaPlaceesOui;
    protected javax.swing.JRadioButton rbGroupesAp;
    protected javax.swing.JRadioButton rbGroupesSelection;
    protected javax.swing.JRadioButton rbGroupesTous;
    protected javax.swing.JRadioButton rbPlaceSiParams;
    protected javax.swing.JRadioButton rbPlaceTous;
    protected javax.swing.JRadioButton rbPrioriteHoraire;
    protected javax.swing.JRadioButton rbPrioriteSalle;
    protected javax.swing.JRadioButton rbSaveSiTous;
    protected javax.swing.JRadioButton rbSaveToutLeTemps;
    protected javax.swing.JTextField tIntervalleTempsMinimum;
    protected javax.swing.JTextField tSemaines;
    protected javax.swing.JPanel vueOptions;
    // End of variables declaration//GEN-END:variables

	public javax.swing.JPanel getVueOptions() {
		return vueOptions;
	}

	public javax.swing.JButton getBValider() {
		return bValider;
	}

	public javax.swing.JButton getBAnnuler() {
		return bAnnuler;
	}

	public javax.swing.JCheckBox getjCheckBoxIntervalleMinimum() {
		return jCheckBoxIntervalleMinimum;
	}

	public javax.swing.JRadioButton getRbSaveSiTous() {
		return rbSaveSiTous;
	}

	public javax.swing.JRadioButton getRbSaveToutLeTemps() {
		return rbSaveToutLeTemps;
	}

	public javax.swing.JTextField gettIntervalleTempsMinimum() {
		return tIntervalleTempsMinimum;
	}

	public javax.swing.JRadioButton getRbPlaceSiParams() {
		return rbPlaceSiParams;
	}

	public javax.swing.JRadioButton getRbPlaceTous() {
		return rbPlaceTous;
	}

	public javax.swing.JCheckBox getjCheckBoxBloqueHeures() {
		return jCheckBoxBloqueHeures;
	}

	public javax.swing.JTextField gettSemaines() {
		return tSemaines;
	}

	public javax.swing.JRadioButton getRbPrioriteHoraire() {
		return rbPrioriteHoraire;
	}

	public javax.swing.JRadioButton getRbPrioriteSalle() {
		return rbPrioriteSalle;
	}

	public javax.swing.JRadioButton getRbGroupesAp() {
		return rbGroupesAp;
	}

	public javax.swing.JRadioButton getRbGroupesTous() {
		return rbGroupesTous;
	}

	public javax.swing.JRadioButton getRbGroupesSelection() {
		return rbGroupesSelection;
	}

	public javax.swing.JRadioButton getRbEviteSemainesDejaPlaceesNon() {
		return rbEviteSemainesDejaPlaceesNon;
	}

	public javax.swing.JRadioButton getRbEviteSemainesDejaPlaceesOui() {
		return rbEviteSemainesDejaPlaceesOui;
	}

}
