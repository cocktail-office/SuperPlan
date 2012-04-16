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
package edtscol.client.recherche;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import fr.univlr.utilities.GenericListHandler;

public class GroupeSelector extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private GenericListHandler listGroupes;
	private JButton bValider, bAnnuler;
	
	private boolean validate = false;
	
	

	public GroupeSelector(JComponent sender) {
		super((Frame) null, "Selection des groupes", true);
		//this.setLocationRelativeTo(sender);
		this.setLocationRelativeTo(null);
		initUI();
	}

	private void initUI() {
		setSize(300, 300);
		listGroupes = new GenericListHandler();
		getContentPane().setLayout(new BorderLayout(4, 4));
		getContentPane().add(new JScrollPane(listGroupes), BorderLayout.CENTER);
		bValider = new JButton("Valider");
		bAnnuler = new JButton("Annuler");

		bValider.addActionListener(this);
		bAnnuler.addActionListener(this);

		JPanel panButtons = new JPanel();
		panButtons.add(bAnnuler);
		panButtons.add(bValider);
		getContentPane().add(panButtons, BorderLayout.SOUTH);

	}

	public void setListGroupes(NSArray groupes) {
		NSMutableArray objets = new NSMutableArray(MatiereExtController.TOUS_LES_GROUPES);
		objets.addObjectsFromArray(groupes);
		listGroupes.setObjects(objets);
	}

	public NSArray getSelectedGroupes() {
		return listGroupes.getSelectedItems();
	}

	private void valider() {
		validate = true;
		setVisible(false);
	}

	private void annuler() {
		listGroupes.setObjects(new NSArray());
		validate = false;
		setVisible(false);
		
	}

	public void actionPerformed(ActionEvent arg0) {
		Object src = arg0.getSource();
		if (src == bAnnuler) {
			annuler();
		}
		else
			if (src == bValider) {
				valider();
			}
	}
	
	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}


}
