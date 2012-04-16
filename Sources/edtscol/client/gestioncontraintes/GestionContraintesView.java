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
package edtscol.client.gestioncontraintes;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.swing.TableSorter;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eointerface.EODisplayGroup;

public class GestionContraintesView extends javax.swing.JDialog {

	protected EODisplayGroup eodIndividus;
	protected ZEOTable myEOTableIndividus;

	public GestionContraintesView(EODisplayGroup displayGroupIndividus) {
		super();
		initComponents();
		eodIndividus = displayGroupIndividus;
		initGui();
	}

	private class MyWindowAdapter extends WindowAdapter {
		public void windowOpened(WindowEvent e) {
			GestionContraintesCtrl.sharedInstance(null, null).onWindowOpened();
		}
	}

	public void initGui() {
		this.addWindowListener(new MyWindowAdapter());

		Vector<ZEOTableModelColumn> myColsIntervenants = new Vector<ZEOTableModelColumn>();

		ZEOTableModelColumn col = new ZEOTableModelColumn(eodIndividus, IndividuUlr.NOM_PRENOM_KEY, "Identité", 100);
		col.setAlignment(SwingConstants.LEFT);
		myColsIntervenants.add(col);
		col = new ZEOTableModelColumn(eodIndividus, IndividuUlr.IND_QUALITE_KEY, "Qualité", 100);
		col.setAlignment(SwingConstants.LEFT);
		myColsIntervenants.add(col);

		ZEOTableModel myTableModelIntervenants = new ZEOTableModel(eodIndividus, myColsIntervenants);
		TableSorter myTableSorterIntervenants = new TableSorter(myTableModelIntervenants);

		myEOTableIndividus = new ZEOTable(myTableSorterIntervenants);
		myTableSorterIntervenants.setTableHeader(myEOTableIndividus.getTableHeader());

		myEOTableIndividus.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		viewTableIndividus.setLayout(new BorderLayout());
		viewTableIndividus.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableIndividus.removeAll();
		viewTableIndividus.add(new JScrollPane(myEOTableIndividus), BorderLayout.CENTER);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        cbAnnee = new javax.swing.JComboBox();
        btFermer = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelIndividus = new javax.swing.JPanel();
        rbRechercheNonEtudiants = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        tfRecherchePrenom = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        rbRechercheEtudiants = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        tfRechercheNom = new javax.swing.JTextField();
        viewTableIndividus = new javax.swing.JPanel();
        boutonRecherche = new javax.swing.JButton();
        jPanelIndisponibilitesIndividus = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion des contraintes");

        cbAnnee.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btFermer.setText("Fermer");

        buttonGroup1.add(rbRechercheNonEtudiants);
        rbRechercheNonEtudiants.setSelected(true);
        rbRechercheNonEtudiants.setText("Non étudiants");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Prénom");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nom");

        buttonGroup1.add(rbRechercheEtudiants);
        rbRechercheEtudiants.setText("Etudiants");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Recherche :");

        org.jdesktop.layout.GroupLayout viewTableIndividusLayout = new org.jdesktop.layout.GroupLayout(viewTableIndividus);
        viewTableIndividus.setLayout(viewTableIndividusLayout);
        viewTableIndividusLayout.setHorizontalGroup(
            viewTableIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 874, Short.MAX_VALUE)
        );
        viewTableIndividusLayout.setVerticalGroup(
            viewTableIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 168, Short.MAX_VALUE)
        );

        boutonRecherche.setText("Chercher");

        org.jdesktop.layout.GroupLayout jPanelIndisponibilitesIndividusLayout = new org.jdesktop.layout.GroupLayout(jPanelIndisponibilitesIndividus);
        jPanelIndisponibilitesIndividus.setLayout(jPanelIndisponibilitesIndividusLayout);
        jPanelIndisponibilitesIndividusLayout.setHorizontalGroup(
            jPanelIndisponibilitesIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 874, Short.MAX_VALUE)
        );
        jPanelIndisponibilitesIndividusLayout.setVerticalGroup(
            jPanelIndisponibilitesIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 393, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jPanelIndividusLayout = new org.jdesktop.layout.GroupLayout(jPanelIndividus);
        jPanelIndividus.setLayout(jPanelIndividusLayout);
        jPanelIndividusLayout.setHorizontalGroup(
            jPanelIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelIndividusLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, viewTableIndividus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelIndisponibilitesIndividus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelIndividusLayout.createSequentialGroup()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tfRechercheNom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tfRecherchePrenom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(27, 27, 27)
                        .add(rbRechercheNonEtudiants)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(rbRechercheEtudiants)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 55, Short.MAX_VALUE)
                        .add(boutonRecherche)))
                .add(10, 10, 10))
        );
        jPanelIndividusLayout.setVerticalGroup(
            jPanelIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelIndividusLayout.createSequentialGroup()
                .add(jPanelIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(boutonRecherche)
                    .add(jLabel2)
                    .add(tfRechercheNom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(tfRecherchePrenom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(rbRechercheNonEtudiants, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(rbRechercheEtudiants, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(5, 5, 5)
                .add(viewTableIndividus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelIndisponibilitesIndividus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Individus", jPanelIndividus);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(792, 792, 792)
                        .add(btFermer))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(cbAnnee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(cbAnnee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btFermer)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton boutonRecherche;
    protected javax.swing.JButton btFermer;
    protected javax.swing.ButtonGroup buttonGroup1;
    protected javax.swing.JComboBox cbAnnee;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JPanel jPanelIndisponibilitesIndividus;
    protected javax.swing.JPanel jPanelIndividus;
    protected javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JRadioButton rbRechercheEtudiants;
    protected javax.swing.JRadioButton rbRechercheNonEtudiants;
    protected javax.swing.JTextField tfRechercheNom;
    protected javax.swing.JTextField tfRecherchePrenom;
    protected javax.swing.JPanel viewTableIndividus;
    // End of variables declaration//GEN-END:variables

	public ZEOTable getMyEOTableIndividus() {
		return myEOTableIndividus;
	}

	public javax.swing.JTextField getTfRechercheNom() {
		return tfRechercheNom;
	}

	public javax.swing.JButton getBoutonRecherche() {
		return boutonRecherche;
	}

	public javax.swing.JRadioButton getRbRechercheEtudiants() {
		return rbRechercheEtudiants;
	}

	public javax.swing.JRadioButton getRbRechercheNonEtudiants() {
		return rbRechercheNonEtudiants;
	}

	public javax.swing.JTextField getTfRecherchePrenom() {
		return tfRecherchePrenom;
	}

	public javax.swing.JComboBox getCbAnnee() {
		return cbAnnee;
	}

	public javax.swing.JButton getBtFermer() {
		return btFermer;
	}

	public javax.swing.JPanel getjPanelIndisponibilitesIndividus() {
		return jPanelIndisponibilitesIndividus;
	}

}