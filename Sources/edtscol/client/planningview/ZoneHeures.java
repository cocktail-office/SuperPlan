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
package edtscol.client.planningview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public class ZoneHeures extends JPanel implements Scrollable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -671387444493178664L;
	private LinkedList liste;
	private ArrayList heures;

	/**
	 * Construteur principal.
	 */
	public ZoneHeures() {
		super();
		this.setPreferredSize(Parametres.TAILLE_ZONE_HEURES);
		this.setBackground(Parametres.COULEUR_FOND);
		this.initialiser();
	}

	/**
	 * Initialisation du composant.
	 */
	public void initialiser() {

		liste = new LinkedList();

		heures = new ArrayList(11);

		for (int i = 2; i < 24; i += 2) {

			if (i < 10) {
				heures.add("0" + String.valueOf(i));
			}
			else {
				heures.add(String.valueOf(i));
			}
		}

		int hr = 0;

		for (int i = Parametres.TAILLE_HEURE * 2; i < Parametres.A4_HEIGHT; i += Parametres.TAILLE_HEURE * 2) {
			String heure = (String) heures.get(hr);
			hr++;
			liste.add(new TexteEDT(i, 5, heure, Parametres.COLOR_ZONE_HEURE));
		}

	}

	/*
	 * (redefinition de paint pour ecrire les heures sur le composant.)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// passage en graphique 2D.
		Graphics2D graph = (Graphics2D) g;

		for (int i = 0; i < liste.size(); i++) {
			((TexteEDT) liste.get(i)).draw(graph);
		}

	}

	/**
	 * implementation de l'interface Scrollable.
	 */
	public Dimension getPreferredScrollableViewportSize() {
		return this.getPreferredSize();
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 20;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 1;
	}
	// fin implementation Scrollable.

}
