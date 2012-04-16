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
import java.awt.Dimension;
import java.text.DateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.swing.TableSorter;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSTimestamp;

public class RechercheResasApView extends javax.swing.JDialog {

	protected EODisplayGroup eodResas, eodIntervenants;
	protected ZEOTable myEOTableResas, myEOTableIntervenants;

	/** Creates new form RechercheApsView */
	public RechercheResasApView(java.awt.Frame parent, boolean modal, EODisplayGroup displayGroupResas, EODisplayGroup displayGroupIntervenants) {
		super(parent, modal);
		initComponents();
		eodResas = displayGroupResas;
		eodIntervenants = displayGroupIntervenants;
		initGui();
	}

	public void initGui() {
		Vector<ZEOTableModelColumn> myColsResas = new Vector<ZEOTableModelColumn>();

		ZEOTableModelColumn col = new ZEOTableModelColumn(eodResas, Periodicite.SEMAINE_KEY, "Semaine", 20);
		col.setAlignment(SwingConstants.CENTER);
		col.setColumnClass(Number.class);
		myColsResas.add(col);
		col = new ZEOTableModelColumn(eodResas, Periodicite.DATE_DEB_KEY, "Date", 80);
		col.setAlignment(SwingConstants.CENTER);
		col.setColumnClass(NSTimestamp.class);
		col.setFormatDisplay(DateFormat.getDateInstance());
		myColsResas.add(col);
		col = new ZEOTableModelColumn(eodResas, Periodicite.DATE_DEB_KEY, "Heure début", 80);
		col.setAlignment(SwingConstants.CENTER);
		col.setColumnClass(NSTimestamp.class);
		col.setFormatDisplay(DateFormat.getTimeInstance());
		myColsResas.add(col);
		col = new ZEOTableModelColumn(eodResas, Periodicite.DATE_FIN_KEY, "Heure fin", 80);
		col.setAlignment(SwingConstants.CENTER);
		col.setColumnClass(NSTimestamp.class);
		col.setFormatDisplay(DateFormat.getTimeInstance());
		myColsResas.add(col);

		ZEOTableModel myTableModel = new ZEOTableModel(eodResas, myColsResas);
		TableSorter myTableSorter = new TableSorter(myTableModel);

		myEOTableResas = new ZEOTable(myTableSorter);
		myEOTableResas.getTableHeader().setPreferredSize(new Dimension(10, 16));
		myTableSorter.setTableHeader(myEOTableResas.getTableHeader());

		myEOTableResas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		viewTableResas.setLayout(new BorderLayout());
		viewTableResas.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableResas.removeAll();
		viewTableResas.add(new JScrollPane(myEOTableResas), BorderLayout.CENTER);

		//
		Vector<ZEOTableModelColumn> myColsIntervenants = new Vector<ZEOTableModelColumn>();

		col = new ZEOTableModelColumn(eodIntervenants, Occupant.INDIVIDU_KEY + "." + IndividuUlr.NOM_PRENOM_KEY, "Intervenants", 100);
		col.setAlignment(SwingConstants.CENTER);
		myColsIntervenants.add(col);

		ZEOTableModel myTableModelIntervenants = new ZEOTableModel(eodIntervenants, myColsIntervenants);
		TableSorter myTableSorterIntervenants = new TableSorter(myTableModelIntervenants);

		myEOTableIntervenants = new ZEOTable(myTableSorterIntervenants);
		myEOTableIntervenants.getTableHeader().setPreferredSize(new Dimension(10, 16));
		myTableSorterIntervenants.setTableHeader(myEOTableIntervenants.getTableHeader());

		myEOTableIntervenants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		viewTableIntervenants.setLayout(new BorderLayout());
		viewTableIntervenants.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableIntervenants.removeAll();
		viewTableIntervenants.add(new JScrollPane(myEOTableIntervenants), BorderLayout.CENTER);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		viewTableResas = new javax.swing.JPanel();
		btFermer = new javax.swing.JButton();
		btInspecter = new javax.swing.JButton();
		viewTableIntervenants = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Recherche des APs");

		viewTableResas.setLayout(new java.awt.BorderLayout());

		btFermer.setText("Fermer");

		btInspecter.setText("Inspecter");

		viewTableIntervenants.setLayout(new java.awt.BorderLayout());

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(org.jdesktop.layout.GroupLayout.TRAILING, btFermer)
								.add(layout
										.createSequentialGroup()
										.add(viewTableResas, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(layout
												.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
												.add(btInspecter)
												.add(viewTableIntervenants, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 288,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(viewTableResas, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
								.add(org.jdesktop.layout.GroupLayout.TRAILING,
										layout.createSequentialGroup()
												.add(viewTableIntervenants, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 83, Short.MAX_VALUE).add(btInspecter)))
						.add(19, 19, 19).add(btFermer).addContainerGap()));

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
	protected javax.swing.JButton btInspecter;
	protected javax.swing.JPanel viewTableIntervenants;
	protected javax.swing.JPanel viewTableResas;

	// End of variables declaration//GEN-END:variables

	public javax.swing.JButton getBtFermer() {
		return btFermer;
	}

	public void setBtFermer(javax.swing.JButton btFermer) {
		this.btFermer = btFermer;
	}

	public javax.swing.JButton getBtInspecter() {
		return btInspecter;
	}

	public void setBtInspecter(javax.swing.JButton btInspecter) {
		this.btInspecter = btInspecter;
	}

	public javax.swing.JPanel getViewTableIntervenants() {
		return viewTableIntervenants;
	}

	public void setViewTableIntervenants(javax.swing.JPanel viewTableIntervenants) {
		this.viewTableIntervenants = viewTableIntervenants;
	}

	public javax.swing.JPanel getViewTableResas() {
		return viewTableResas;
	}

	public void setViewTableResas(javax.swing.JPanel viewTableResas) {
		this.viewTableResas = viewTableResas;
	}

	public ZEOTable getMyEOTableResas() {
		return myEOTableResas;
	}

	public void setMyEOTableResas(ZEOTable myEoTableResas) {
		myEOTableResas = myEoTableResas;
	}

	public ZEOTable getMyEOTableIntervenants() {
		return myEOTableIntervenants;
	}

	public void setMyEOTableIntervenants(ZEOTable myEoTableIntervenants) {
		myEOTableIntervenants = myEoTableIntervenants;
	}

}
