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
package org.cocktail.superplan.server.gestionimpression;

import com.webobjects.foundation.NSTimestamp;

public class CoursImpress {
	public int width;
	public int height;
	public String color;
	public String contenu, occupants, salles;
	public int numeroCreneau;
	public NSTimestamp heuredeb;
	public static final int REEL = 1;
	public static final int FICTIF = 2;
	public static final int CACHE = 3;

	private int etat;

	public CoursImpress(int width, int height, String color, String contenu, String occupants, String salles, int numeroCreneau, NSTimestamp heure) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.contenu = contenu;
		this.occupants = occupants;
		this.salles = salles;
		this.numeroCreneau = numeroCreneau;
		this.heuredeb = heure;
		this.etat = REEL;
	}

	public CoursImpress(int width, int height, int numeroCreneau, NSTimestamp heure) {
		this.width = width;
		this.height = height;
		this.numeroCreneau = numeroCreneau;
		this.heuredeb = heure;
	}

	public CoursImpress(int width, int height, int numeroCreneau) {
		this.width = width;
		this.height = height;
		this.numeroCreneau = numeroCreneau;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public int etat() {
		return this.etat;
	}

	public int numeroCreneau() {
		return numeroCreneau;
	}

	public NSTimestamp heurefin() {
		return heuredeb.timestampByAddingGregorianUnits(0, 0, 0, 0, width, 0);
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public String color() {
		return color;
	}

	public String contenu() {
		return contenu;
	}

	public String occupants() {
		return occupants;
	}

	public String salles() {
		return salles;
	}

	public NSTimestamp heuredeb() {
		return heuredeb;
	}

	public String toString() {
		return contenu;
	}
}
