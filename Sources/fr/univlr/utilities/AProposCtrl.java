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
package fr.univlr.utilities;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class AProposCtrl extends JDialog {

	private static final long serialVersionUID = 3388729578351252883L;
	private Hashtable infos;
	private JButton bOk;

	/**
	 * Constructeur
	 * 
	 * @param infos
	 *            doit contenir les valeurs applicationName, version et date
	 * @param frame
	 */
	public AProposCtrl(Hashtable infos, JFrame frame) {
		super(frame, true);
		setSize(300, 180);
		setTitle("A Propos");
		this.infos = infos;
		buildUI();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void buildUI() {
		Container cont = getContentPane();
		JPanel pan = new JPanel(new GridLayout(4, 1, 4, 4));
		cont.setLayout(new GridLayout(1, 1, 5, 5));
		cont.add(pan);
		Color tfBg = new Color(0xCFCFCF);
		JTextField lblVersion = new JTextField(infos.get("applicationName") + " version " + infos.get("version"));
		JTextField lblDate = new JTextField(" Release du " + infos.get("date"));
		JTextField lblJvm = new JTextField(" Java version " + System.getProperty("java.version"));
		lblVersion.setBackground(tfBg);
		lblDate.setBackground(tfBg);
		lblJvm.setBackground(tfBg);

		lblVersion.setEditable(false);
		lblDate.setEditable(false);
		lblJvm.setEditable(false);
		Border lBorder = BorderFactory.createLineBorder(Color.BLACK);
		lblVersion.setBorder(lBorder);
		lblDate.setBorder(lBorder);
		lblJvm.setBorder(lBorder);

		pan.add(lblVersion);
		pan.add(lblDate);
		pan.add(lblJvm);

		JPanel panButton = new JPanel();
		bOk = new JButton(new ButtonAction("Ok"));
		panButton.add(bOk);
		pan.add(panButton);
	}

	private void ok() {
		setVisible(false);
	}

	private class ButtonAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2501000306104849937L;

		public ButtonAction(String name) {
			super();
			this.putValue(NAME, name);
		}

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == bOk) {
				ok();
			}
		}
	}

}
