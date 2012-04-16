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
import java.awt.Cursor;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

public class Parametres {

	public static final SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");

	public static final int A4_HEIGHT = 1440; // 1131

	public static final int HAUTEUR_DISP_HEURES = 20;
	public static final int HAUTEUR_JOUR = 105;

	public static final int HAUTEUR_EDT = HAUTEUR_JOUR * 7;

	// public static final Dimension TAILLE_PLANNING = new Dimension(940, 620);
	public static final Dimension TAILLE_JOURS_VIEW = new Dimension(120, 800);

	public static final Dimension TAILLE_EDT_TOTALE = new Dimension(1440, HAUTEUR_EDT + HAUTEUR_DISP_HEURES);

	public static final Dimension TAILLE_ZONE_HEURES = new Dimension(1440, HAUTEUR_DISP_HEURES);
	/**
	 * pour le zoom sur l'EDT.
	 */
	public static final Dimension TAILLE_DU_ZOOM = new Dimension(1440, (HAUTEUR_JOUR * 5) + HAUTEUR_DISP_HEURES);

	public static final Dimension TAILLE_BOUTON = new Dimension(15, 15);
	// juste pour le test
	public static final Dimension TAILLE_MAIN = new Dimension(950, 700);

	public static final int TAILLE_HEURE = 60;
	public static final int TAILLE_QUART_HEURE = 15;
	public static final int TAILLE_MINUTE = (TAILLE_HEURE / 60);

	/**
	 * types de pointeur de souris utilises
	 */
	public static final Cursor MAIN = new Cursor(Cursor.HAND_CURSOR);
	public static final Cursor REDIMENSION = new Cursor(Cursor.NW_RESIZE_CURSOR);

	/**
	 * Quelques couleurs bien utiles
	 */
	public static final Color RESA_BORDER = new Color(1.0f, 0.5f, 0);
	public static final Color COLOR_JOUR_SELECTIONNE = new Color(0x98, 0xFB, 0x98);
	public static final Color SELECTED_BORDER = new Color(0x55, 0x00, 0xFF);
	public static final Color COLOR_ZONE_HEURE = new Color(0x7F, 0xD4, 0xFF, 0x3C); // avec transparence...
	public static final Color COULEUR_FOND = new Color(0xFF, 0xFF, 0xFF);
	public static final Color NOIR = new Color(0x00, 0x00, 0x00);

	// les chaines
	public static final String SAUT_LIGNE = "\n";

	/**
	 * teste si nombre est dans [borneInf,borneSup] ou non.
	 */
	public static boolean dansFourchette(int nombre, int borneInf, int borneSup) {

		if ((nombre == borneInf) || (nombre == borneSup)) {
			return true;
		}

		if ((nombre > borneInf) && (nombre < borneSup)) {
			return true;
		}

		return false;
	}

	/**
	 * retourne la valeur la plus proche de nombre parmi (borneInf,borneSup)
	 */
	public static int getPlusProche(int nombre, int borneInf, int borneSup) {
		int mod1 = 0, mod2 = 0;

		mod1 = Math.abs(nombre - borneInf);
		mod2 = Math.abs(borneSup - nombre);
		if (mod1 > mod2) {
			// return borneSup;
			return borneInf;
		}
		if (mod2 > mod1) {
			return borneInf;
		}

		return borneInf;
	}

}
