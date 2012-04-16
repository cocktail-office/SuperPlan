/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PreferencesDiplomeView.java
 *
 * Created on 8 sept. 2011, 15:25:17
 */

package edtscol.client;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.swing.TableSorter;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eointerface.EODisplayGroup;

/**
 * 
 * @author pdemeyer
 */
public class PreferencesDiplomeView extends javax.swing.JDialog {

	protected EODisplayGroup eodHabilitations, eodHabilitationsChoisies;
	protected ZEOTable myEOTableHabilitations, myEOTableHabilitationsChoisies;

	/** Creates new form PreferencesDiplomeView */
	public PreferencesDiplomeView(java.awt.Frame parent, boolean modal, EODisplayGroup displayGroupHabilitations,
			EODisplayGroup displayGroupHabilitationsChoisies) {
		super(parent, modal);
		initComponents();
		eodHabilitations = displayGroupHabilitations;
		eodHabilitationsChoisies = displayGroupHabilitationsChoisies;
		initGui();
	}

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

		ZEOTableModel myTableModel = new ZEOTableModel(eodHabilitations, myColsHabilitations);
		TableSorter myTableSorter = new TableSorter(myTableModel);

		myEOTableHabilitations = new ZEOTable(myTableSorter);
		myTableSorter.setTableHeader(myEOTableHabilitations.getTableHeader());

		myEOTableHabilitations.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		viewTableHabilitations.setLayout(new BorderLayout());
		viewTableHabilitations.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableHabilitations.removeAll();
		viewTableHabilitations.add(new JScrollPane(myEOTableHabilitations), BorderLayout.CENTER);

		Vector<ZEOTableModelColumn> myColsHabilitationsChoisies = new Vector<ZEOTableModelColumn>();

		col = new ZEOTableModelColumn(eodHabilitationsChoisies, PrefScol.HABILITATION_KEY + "." + FormationHabilitation.FORMATION_SPECIALISATION_KEY
				+ "." + FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FGRA_CODE_KEY, "Grade", 20);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitationsChoisies.add(col);
		col = new ZEOTableModelColumn(eodHabilitationsChoisies, PrefScol.HABILITATION_KEY + "." + FormationHabilitation.FHAB_NIVEAU_KEY, "Niveau", 20);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitationsChoisies.add(col);
		col = new ZEOTableModelColumn(eodHabilitationsChoisies, PrefScol.HABILITATION_KEY + "." + FormationHabilitation.FORMATION_SPECIALISATION_KEY
				+ "." + FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY, "Diplome", 200);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitationsChoisies.add(col);
		col = new ZEOTableModelColumn(eodHabilitationsChoisies, PrefScol.HABILITATION_KEY + "." + FormationHabilitation.FORMATION_SPECIALISATION_KEY
				+ "." + FormationSpecialisation.FSPN_LIBELLE_KEY, "Spécialisation", 100);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitationsChoisies.add(col);
		col = new ZEOTableModelColumn(eodHabilitationsChoisies, PrefScol.MAQUETTE_REPARTITION_SEM_KEY, "Semestre / Parcours", 100);
		col.setAlignment(SwingConstants.CENTER);
		myColsHabilitationsChoisies.add(col);

		ZEOTableModel myTableModelChoisies = new ZEOTableModel(eodHabilitationsChoisies, myColsHabilitationsChoisies);
		TableSorter myTableSorterChoisies = new TableSorter(myTableModelChoisies);

		myEOTableHabilitationsChoisies = new ZEOTable(myTableSorterChoisies);
		myTableSorterChoisies.setTableHeader(myEOTableHabilitationsChoisies.getTableHeader());

		myEOTableHabilitationsChoisies.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		viewTableHabilitationsChoisies.setLayout(new BorderLayout());
		viewTableHabilitationsChoisies.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableHabilitationsChoisies.removeAll();
		viewTableHabilitationsChoisies.add(new JScrollPane(myEOTableHabilitationsChoisies), BorderLayout.CENTER);

	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfGrade = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tfDiplome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbAnnee = new javax.swing.JComboBox();
        btChercher = new javax.swing.JButton();
        viewTableHabilitationsChoisies = new javax.swing.JPanel();
        btSupprimer = new javax.swing.JButton();
        btAjouter = new javax.swing.JButton();
        viewTableHabilitations = new javax.swing.JPanel();
        cbParcours = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();

        setTitle("Préférences de diplômes");

        jLabel1.setText("Grade");

        jLabel2.setText("Diplome / abrév.");

        btChercher.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btChercher.setText("Chercher");

        viewTableHabilitationsChoisies.setLayout(new java.awt.BorderLayout());

        btSupprimer.setFont(new java.awt.Font("Tahoma", 0, 10));
        btSupprimer.setText("Oter");

        btAjouter.setFont(new java.awt.Font("Tahoma", 0, 10));
        btAjouter.setText("Choisir");

        viewTableHabilitations.setLayout(new java.awt.BorderLayout());

        jLabel3.setText("Semestre / Parcours (facultatif)  :");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(285, 285, 285)
                        .add(btSupprimer)
                        .add(10, 10, 10)
                        .add(btAjouter))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, viewTableHabilitationsChoisies, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(cbParcours, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 524, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(tfGrade, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(18, 18, 18)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(tfDiplome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 305, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(btChercher, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(81, 81, 81)
                                        .add(cbAnnee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 144, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                        .add(27, 27, 27)))
                .addContainerGap())
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .addContainerGap()
                    .add(viewTableHabilitations, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 705, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(27, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(11, 11, 11)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tfGrade, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(tfDiplome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(btChercher)))
                    .add(cbAnnee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(180, 180, 180)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(cbParcours, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(btAjouter, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(btSupprimer))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(viewTableHabilitationsChoisies, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(57, 57, 57)
                    .add(viewTableHabilitations, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 161, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(251, Short.MAX_VALUE)))
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
				PreferencesDiplomeView dialog = new PreferencesDiplomeView(new javax.swing.JFrame(), true, null, null);
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
    private javax.swing.JButton btAjouter;
    private javax.swing.JButton btChercher;
    private javax.swing.JButton btSupprimer;
    private javax.swing.JComboBox cbAnnee;
    private javax.swing.JComboBox cbParcours;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField tfDiplome;
    private javax.swing.JTextField tfGrade;
    private javax.swing.JPanel viewTableHabilitations;
    private javax.swing.JPanel viewTableHabilitationsChoisies;
    // End of variables declaration//GEN-END:variables
	public javax.swing.JButton getBtAjouter() {
		return btAjouter;
	}

	public javax.swing.JButton getBtChercher() {
		return btChercher;
	}

	public javax.swing.JButton getBtSupprimer() {
		return btSupprimer;
	}

	public javax.swing.JComboBox getCbAnnee() {
		return cbAnnee;
	}

	public javax.swing.JComboBox getCbParcours() {
		return cbParcours;
	}

	public javax.swing.JTextField getTfGrade() {
		return tfGrade;
	}

	public javax.swing.JTextField getTfDiplome() {
		return tfDiplome;
	}

	public javax.swing.JPanel getViewTableHabilitationsChoisies() {
		return viewTableHabilitationsChoisies;
	}

	public javax.swing.JPanel getViewTableHabilitations() {
		return viewTableHabilitations;
	}

	public ZEOTable getMyEOTableHabilitations() {
		return myEOTableHabilitations;
	}

	public ZEOTable getMyEOTableHabilitationsChoisies() {
		return myEOTableHabilitationsChoisies;
	}

}
