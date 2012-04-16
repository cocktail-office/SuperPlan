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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class TexteEDT implements Drawable {

	private String leTexte = "";
	private ArrayList lesChaines;
	private int posX;
	private int posY;
	private Color color;
	private int hauteur;
	private int largeur;

	private boolean auto = true;

	/**
	 * contructeur avec coordonnees du debut, et la chaine.
	 * 
	 * @param debutX
	 *            debutX
	 * @param debutY
	 *            debutY
	 * @param uneChaine
	 *            uneChaine
	 * @param color
	 *            uneColor
	 */

	public TexteEDT(int debutX, int debutY, String uneChaine, Color color) {
		posX = debutX;
		posY = debutY;
		this.leTexte = uneChaine;
		this.color = color;
	}

	public TexteEDT(int debutX, int debutY, int hauteur, int largeur, ArrayList laDate, Color color) {
		auto = false;
		posX = debutX;
		posY = debutY;
		this.lesChaines = laDate;
		this.color = color;
		this.hauteur = hauteur;
		this.largeur = largeur;
	}

	/**
	 * dessiner la chaine.
	 */
	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (auto) {
			g2d.setColor(color);
			g2d.fillRect(posX - 12, posY - 4, 25, 15);
			g2d.setColor(Parametres.NOIR);

			g2d.drawString(leTexte, posX - 8, posY + 9);
			g2d.setColor(Parametres.RESA_BORDER);
			g2d.drawRect(posX - 12, posY - 4, 25, 15);
		}
		else {
			g2d.setColor(color);
			Font fnt = new Font("Helvetica", Font.BOLD, 15);
			g2d.setFont(fnt);
			g2d.fillRect(posX, posY, largeur, hauteur);
			g2d.setColor(Parametres.NOIR);

			g2d.drawString((String) lesChaines.get(0), posX + 15, posY + (largeur / 3));
			g2d.setFont(new Font("Helvetica", Font.ITALIC | Font.BOLD, 15));
			g2d.drawString((String) lesChaines.get(1), posX + 15, posY + (largeur / 2));
			g2d.setColor(Color.darkGray);
			g2d.drawRect(posX, posY, largeur, hauteur);
		}
	}

}
