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
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingHandler extends JFrame {

	private static final long serialVersionUID = 6677993154046515190L;
	private static final Color BACKGROUND = new Color(0x5F9EA0);
	String title, intro, message;
	JComponent myContentPane;
	JLabel sujet, texte;

	private static WaitingHandler instance;

	public static WaitingHandler getInstance(String aTitle, String anIntro, String aMessage, Dimension dim) {
		if (instance == null) {
			instance = new WaitingHandler(aTitle, anIntro, aMessage, dim);
		}
		else {
			instance.setTitle(aTitle);
			instance.setIntro(anIntro);
			instance.setMessage(aMessage);
		}
		return instance;
	}

	private WaitingHandler(String aTitle, String anIntro, String aMessage, Dimension dim) {
		super();
		title = aTitle;
		message = aMessage;
		intro = anIntro;
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		// Recuperer les dimensions de l'ecran
		int x = (int) this.getGraphicsConfiguration().getBounds().getWidth();
		int y = (int) this.getGraphicsConfiguration().getBounds().getHeight();

		myContentPane = createUI(dim);
		myContentPane.setOpaque(true);

		this.setContentPane(myContentPane);
		this.setLocation((x / 2) - ((int) this.getContentPane().getMinimumSize().getWidth() / 2), ((y / 2) - ((int) this.getContentPane()
				.getMinimumSize().getHeight() / 2)));
		// prevenir la fermeture de l'application en cas de souci
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Display the window.
		this.pack();
		this.setVisible(true);
		this.paintAll(this.getGraphics());

	}

	//
	// Methode privee, creation du panneau et des composants a afficher
	private JPanel createUI(Dimension dim) {
		// Create the labels.
		sujet = new JLabel(intro + "  ");
		texte = new JLabel(message);
		sujet.setLabelFor(texte);

		// Create the panel we'll return and set up the layout.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(BACKGROUND);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		sujet.setAlignmentX(Component.CENTER_ALIGNMENT);
		texte.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add the labels to the content pane.
		panel.add(sujet);
		panel.add(Box.createVerticalStrut(5)); // extra space
		panel.add(texte);

		// Add a vertical spacer that also guarantees us a minimum width:
		panel.add(Box.createRigidArea(dim));

		return panel;
	}

	/**
	 * Methode statique qui permet de recuperer directement une frame avec un message d'attente.
	 * 
	 * @param aTitle
	 *            : titre de la fenetre
	 * @param anIntro
	 *            : message d'introduction. Ex : "Traitement en cours : "
	 * @param aMessage
	 *            : message d'information. Ex : "Chargement des valeurs..."
	 */
	public static WaitingHandler getWaitingHandler(String aTitle, String anIntro, String aMessage, Dimension dim) {
		return new WaitingHandler(aTitle, anIntro, aMessage, dim);
	}

	/**
	 * Modification du titre de la fenetre. Prend en compte la modification immediatement.
	 */
	public void setTitle(String aTitle) {
		title = aTitle;
		this.setTitle(title);
		this.paintAll(this.getGraphics());
	}

	/**
	 * Modification du message d'introduction de la fenetre. Prend en compte la modification immediatement.
	 */
	public void setIntro(String anIntro) {
		intro = anIntro;
		sujet.setText(intro + " : ");
		this.paintAll(this.getGraphics());
	}

	/**
	 * Modification du message d'information de la fenetre. Prend en compte la modification immediatement.
	 */
	public void setMessage(String aMessage) {
		if (!this.isVisible()) {
			this.setVisible(true);
		}

		message = aMessage;
		texte.setText(message);
		this.paintAll(this.getGraphics());
	}

	/**
	 * Fermeture de la fenetre d'attente.
	 */
	/*
	 * public void close() { this.setVisible(false); this.paintAll(this.getGraphics()); }
	 */
	public void close() {
		this.setVisible(false);
		this.dispose();
		// this.update(this.getGraphics());
	}

}
