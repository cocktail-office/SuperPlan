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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Zoom extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8201621671601696909L;
	private LinkedList liste;

	public Zoom() {
		super();
		this.setPreferredSize(Parametres.TAILLE_DU_ZOOM);
		this.setBackground(Parametres.COULEUR_FOND);
		this.initialiser();
	}

	// construction d'un panneau de zoom d'un jour.
	public Zoom(LinkedList uneListe) {
		super();
		this.liste = uneListe;
		this.setPreferredSize(Parametres.TAILLE_DU_ZOOM);
		this.setBackground(Parametres.COULEUR_FOND);
		this.initialiser();
	}

	private void initialiser() {

	}

	public void addToListeComposants(FigureSimple fig) {
		liste.add(fig);
	}

	public void addToListeComposants(FigureComplexe fig) {
		liste.add(fig);
	}

	public void removeFromListeComposants(int index) {
		liste.remove(index);
	}

	// pour repaindre le composant.
	public void paint(Graphics g) {
		super.paint(g);
		int listIndex;
		Drawable drawable;
		// on passe en graphique 2D (meilleur)
		Graphics2D graph = (Graphics2D) g;

		// on dessine ce qu'il y'a dans la liste
		listIndex = 0;

		while (listIndex < liste.size()) {
			drawable = (Drawable) (liste.get(listIndex));
			drawable.draw(graph);
			listIndex++;
		}
	}

}
