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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.foundation.NSArray;

import edtscol.client.GestionPlanning;
import edtscol.client.PlanningView;

public class CaseReservation extends FigureSimple implements Drawable {

	private int height, width;
	int typeDessin = 1;
	private int posX, posY;
	private String texte;
	NSArray textes;
	private Color color, colSalleValide = new Color(0xFF6347), colObjetValide = new Color(0x6347FF);
	private String appli;
	private EOGlobalID resa;
	private PlanningView planningView = null;

	FontMetrics fmetrics;
	boolean multiLigne = false;
	// par defaut oui, une fois l'objet est finalise, on passe a non.
	boolean resizeable = false;
	boolean deplacable = false;

	boolean plusieursJours = false;

	private Color borderColor, alternateBorderColor;
	// ajout pour recaler le texte.
	int fontHeight, fontWidth;
	int stringWidth;

	private boolean salleValide = false, objetValide = false;

	/** CONSTRUCTEUR UTILISE POUR LES AFFICHAGES */
	public CaseReservation(int x, int y, int height, int width, int typeD, NSArray texte, Color color, EOGlobalID resa, String appli,
			boolean deplacer, PlanningView planningView) {
		super(x, y);
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		this.typeDessin = typeD;
		this.textes = texte;
		this.color = color;
		multiLigne = true;
		this.resa = resa;
		this.appli = appli;
		this.deplacable = deplacer;
		this.borderColor = Parametres.RESA_BORDER;
		this.planningView = planningView;
	}

	// NOUVEAU CONSTRUCTEUR POUR L'AFFICHAGE
	public CaseReservation(int x, int y, int height, int width, int typeD, NSArray texte, String code, EOGlobalID resa, boolean deplacer,
			PlanningView planningView) {
		super(x, y);
		// System.out.println("4 x " + x + " y " + y);
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		this.typeDessin = typeD;
		this.textes = texte;
		this.color = GestionPlanning.reservationColor(code);
		multiLigne = true;
		this.resa = resa;
		this.deplacable = deplacer;
		if (code == null || code.equals("H")) {
			this.borderColor = new Color(0x00, 0xBF, 0xFF, 0x80);
			alternateBorderColor = new Color(0x00, 0xBF, 0xFF, 0x80);
		}
		else {
			this.borderColor = Parametres.RESA_BORDER;
		}
		this.planningView = planningView;
	}

	/** *********************************************************************************** */
	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.fillRect(panelCentreX, panelCentreY, width, height);

		// affichage du texte
		if (textes.count() > 0) {
			this.ecrireTexte(g2d);
		}

		// affectation de la bordure.
		g2d.setColor(borderColor);
		g2d.drawRect(panelCentreX, panelCentreY, width, height);

		// si resa salle validee : on affiche un petit carré rouge :
		if (salleValide && !objetValide) {
			int px = (panelCentreX + width - 12);
			int py = (panelCentreY + height - 12);
			g2d.setColor(colSalleValide);
			g2d.fill3DRect(px, py, 12, 12, true);
		}
		// si resa objet validee : on affiche un petit carré bleu :
		if (objetValide && !salleValide) {
			int px = (panelCentreX + width - 12);
			int py = (panelCentreY + height - 12);
			g2d.setColor(colObjetValide);
			g2d.fill3DRect(px, py, 12, 12, true);
		}
		// si resa salle ET objet validee : on affiche 2 carrés :
		if (objetValide && salleValide) {
			int px = (panelCentreX + width - 12);
			int py = (panelCentreY + height - 12);
			g2d.setColor(colSalleValide);
			g2d.fill3DRect(px, py, 8, 8, true);
			px = (panelCentreX + width - 8);
			py = (panelCentreY + height - 8);
			g2d.setColor(colObjetValide);
			g2d.fill3DRect(px, py, 8, 8, true);
		}
	}

	private void ecrireTexte(Graphics2D g) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < textes.count(); i++) {
			String element = (String) textes.objectAtIndex(i);
			if (i > 0) {
				sb.append(" - ");
			}
			sb.append(element);
		}
		// CM debug

		g.setColor(Parametres.NOIR);
		String s = sb.toString();

		int strlen = s.length();
		// sortir de l'affichage du texte.
		if (strlen == 0) {
			return;
		}

		// formule de calcul dynamique de la taille de la font.
		int szFont = (int) (7.0 * (((double) height) / ((double) Parametres.HAUTEUR_JOUR)) + 6.0);

		if (height == Parametres.HAUTEUR_JOUR) {
			szFont = 11;
		}

		if (szFont < 5 || szFont > 14) {
			szFont = 8;
		}

		Font font = new Font("Helvetica", Font.PLAIN, szFont);
		AttributedString as = new AttributedString(s);
		as.addAttribute(TextAttribute.FONT, font);
		AttributedCharacterIterator aci = as.getIterator();

		FontRenderContext frc = g.getFontRenderContext();
		LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
		Insets insets = getInsets();
		int x = insets.left + panelCentreX;
		int y = insets.top + panelCentreY;

		int y0 = y;
		int wrappingWidth = width - insets.left - insets.right;
		if (wrappingWidth < 0) {
			wrappingWidth = width;
			if (wrappingWidth < 0) {
				wrappingWidth = 5;
			}
			x = panelCentreX;
		}

		TextLayout textLayout;
		// Positionnement du texte dynamique en fonction de l'affichage
		int horizontalBarValue = 0;
		boolean dynamicTextPosition = false;
		if (planningView != null) {
			horizontalBarValue = planningView.getHorizontalBarValue();
			dynamicTextPosition = true;
		}
		while (lbm.getPosition() < aci.getEndIndex()) {
			textLayout = lbm.nextLayout(wrappingWidth);
			y += textLayout.getAscent();
			if (y - y0 > height) {
				break;
			}
			// if (centeredText) {
			// x = panelCentreX + insets.left + ((wrappingWidth - (int) textLayout.getAdvance()) / 2);
			// }
			int finalX = x;
			if (dynamicTextPosition) {
				if (horizontalBarValue > x) {
					finalX = horizontalBarValue + 2;
					if (finalX + (int) textLayout.getAdvance() > width + panelCentreX - insets.right) {
						finalX = width + panelCentreX - insets.right - (int) textLayout.getAdvance();
					}
					if (finalX < x) {
						finalX = x;
					}
				}
			}
			textLayout.draw(g, finalX, y);
			y += textLayout.getDescent() + textLayout.getLeading();
		}

	}

	/** *********************************************************************************** */

	protected Insets getInsets() {
		return new Insets(4, 4, 4, 4);
	}

	public void printParam() {
	}

	public void setSalleValide(boolean valide) {
		this.salleValide = valide;
	}

	public void setObjetValide(boolean valide) {
		this.objetValide = valide;
	}

	public void setCode(String cc) {
	}

	/**
	 * fixe la couleur de la bordure de la resa.
	 * 
	 * @param laCouleur
	 *            couleur choisie
	 */
	public void setBorderColor(Color laCouleur) {
		borderColor = laCouleur;
	}

	/**
	 * Remet la couleur par defaut pour la bordure, celle definie dans la classe Parametres
	 * 
	 * @see Parametres#RESA_BORDER
	 */
	public void setDefaultBorderColor() {
		if (alternateBorderColor != null) {
			borderColor = alternateBorderColor;
		}
		else {
			borderColor = Parametres.RESA_BORDER;
		}
	}

	/**
	 * donne la position x de la resa.
	 * 
	 * @return entier abscisse.
	 */
	public int getX() {
		return panelCentreX;
	}

	/**
	 * donne la position en y de la resa.
	 * 
	 * @return entier ordonnee
	 */
	public int getY() {
		return panelCentreY;
	}

	/**
	 * retourne la largeur
	 * 
	 * @return entier width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * retourne la hauteur de la resa.
	 * 
	 * @return entier height
	 */
	public int getHeight() {
		return height;
	}

	// Mutateurs
	/**
	 * fixe la position X du composant resa
	 * 
	 * @param x
	 *            abscisse du coin superieur gauche
	 */
	public void setX(int x) {
		panelCentreX = x;
	}

	/**
	 * fixe la position Y du composant resa
	 * 
	 * @param y
	 *            ordonee du coin superieur gauche
	 */
	public void setY(int y) {
		panelCentreY = y;
	}

	/**
	 * fixe la largeur du composant resa
	 * 
	 * @param w
	 *            largeur width du composant
	 */

	public void setWidth(int w) {
		width = w;
	}

	/**
	 * fixe la hauteur du composant resa
	 * 
	 * @param h
	 *            hauteur height du composant
	 */
	public void setHeight(int h) {
		height = h;
	}

	/**
	 * teste si le composant est donne comme redimensionable.
	 * 
	 * @return vrai ou faux (true, false).
	 */
	public boolean isResizeable() {
		return resizeable;
	}

	/**
	 * fixe la propriete redimensionable
	 * 
	 * @param on
	 *            vrai ou faux (true, false).
	 */
	public void setResizeable(boolean on) {
		resizeable = on;
	}

	public String getText() {
		if (textes == null) {
			return texte;
		}
		else {
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < textes.count(); i++) {
				str.append((String) textes.objectAtIndex(textes.count() - i - 1));
			}
			return str.toString();
		}
	}

	/** retourne le numero de reservation associé */
	public EOGlobalID getResa() {
		return resa;
	}

	public String getAppli() {
		return appli;
	}

	/** retoune les commentaire de cette reservation */
	public NSArray getStrings() {
		if (textes == null) {
			textes = new NSArray();
		}
		return textes;
	}

	public boolean isDeplacable() {
		return deplacable;
	}

	public String toString() {
		return ("[x=" + posX + " y=" + posY + " h=" + height + " w=" + width + "]");
	}

}
