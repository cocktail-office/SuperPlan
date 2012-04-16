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

import java.awt.Color;
import java.awt.Graphics;

public class Ligne extends FigureComplexe implements Drawable {
	private int panelFinX;
	private int panelFinY;
	private Color color;

	/**
	 * Constructeur no 1
	 * 
	 * @param xBegin
	 *            abscisse debut
	 * @param yBegin
	 *            ordonnee debut
	 * @param xEnd
	 *            abscisse fin
	 * @param yEnd
	 *            ordonnee fin
	 */
	public Ligne(int xBegin, int yBegin, int xEnd, int yEnd) {
		panelDebX = xBegin;
		panelDebY = yBegin;
		panelFinX = xEnd;
		panelFinY = yEnd;
		color = Color.darkGray;
	}

	/**
	 * Constructeur no 2
	 * 
	 * @param xBegin
	 *            abscisse debut
	 * @param yBegin
	 *            ordonnee debut
	 * @param xEnd
	 *            abscisse fin
	 * @param yEnd
	 *            ordonnee fin
	 * @param color
	 *            la couleur voulue pour la ligne
	 */
	public Ligne(int xBegin, int yBegin, int xEnd, int yEnd, Color color) {
		panelDebX = xBegin;
		panelDebY = yBegin;
		panelFinX = xEnd;
		panelFinY = yEnd;
		this.color = color;
	}

	/**
	 * methode de dessin de la ligne, implementation de l'interface Drawable. on redessine les horizontales et les verticales.
	 */
	public void draw(Graphics g) {
		g.drawLine(panelDebX, panelDebY, panelFinX, panelFinY);
		g.setColor(color);
	}

}
