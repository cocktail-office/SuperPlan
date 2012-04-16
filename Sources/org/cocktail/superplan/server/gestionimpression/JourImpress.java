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

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;

public class JourImpress {

	private String jourName;
	private String jourDate;
	private int nbCreneaux;
	private ListeCours listeCours;

	/** constructeur * */
	public JourImpress(int nbCreneaux) {
		this.nbCreneaux = nbCreneaux;
		init();

	}

	private void init() {
		listeCours = new ListeCours();
	}

	public int nbCreneaux() {
		return nbCreneaux;
	}

	public void setJourName(String nom) {
		this.jourName = nom;
	}

	public String jourName() {
		return jourName;
	}

	public void setJourDate(String date) {
		jourDate = date;
	}

	public String jourDate() {
		return jourDate;
	}

	public NSTimestamp timestampJourDate() {
		return FormatHandler.strToDate(jourDate + " 12:00:00", "%d/%m/%Y %H:%M:%S");
	}

	public int getCoursCount() {
		return listeCours.count();
	}

	public ListeCours listCours() {
		return listeCours;
	}

	public void setListCours(ListeCours listCours) {
		this.listeCours = listCours;
	}

	public CoursImpress getCoursAtIndex(int idx) {
		return (CoursImpress) listeCours.objectAtIndex(idx);
	}

	public void ajouterCours(CoursImpress cours) {
		listeCours.addObject(cours);
	}

	public void ajouterListeCours(ListeCours liste) {
		listeCours.addObjects(liste);
	}

	/** ajoute un cours fictif ou reel * */
	public void ajouterCours(CoursImpress cours, int index) {
		listeCours.insertObjectAtIndex(cours, index);
	}

	/** retourne la liste des cours de la journee pour le noCreneau donne * */
	public NSArray listCoursForNoCreneau(int noCreneau) {
		NSMutableArray tmpList = new NSMutableArray();
		for (int i = 0; i < listeCours.count(); i++) {
			CoursImpress currentCours = (CoursImpress) listeCours.objectAtIndex(i);
			if (currentCours.numeroCreneau() == noCreneau) {
				tmpList.addObject(currentCours);
			}
		}
		return tmpList;
	}

	public String toString() {
		String string = "";
		for (int i = 0; i < this.listeCours.count(); i++) {
			string += this.listeCours.objectAtIndex(i).toString() + "\n";
		}
		return string;
	}

	public String toStringDebug() {
		String str = "";
		for (int i = 0; i < listeCours.count(); i++) {
			str += "\n" + listeCours.objectAtIndex(i).toString();
		}
		return str;
	}

}
