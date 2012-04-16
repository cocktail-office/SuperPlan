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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

public class Calendrier {

	private GregorianCalendar dateDuLundi;
	private GregorianCalendar dateCourante;
	private ArrayList lesJours;

	public Calendrier() {
		super();
		initJours();
	}

	private void initJours() {
		lesJours = new ArrayList(7);
		lesJours.add("LUNDI");
		lesJours.add("MARDI");
		lesJours.add("MERCREDI");
		lesJours.add("JEUDI");
		lesJours.add("VENDREDI");
		lesJours.add("SAMEDI");
		lesJours.add("DIMANCHE");
	}

	public ArrayList getJours() {
		if (lesJours != null) {
			return lesJours;
		}
		else {
			this.initJours();
			return lesJours;
		}
	}

	public void semaineSuivante() {
		dateDuLundi.add(Calendar.WEEK_OF_YEAR, 1);
		// faire un affichage.
	}

	public void semainePrecedente() {
		dateDuLundi.add(Calendar.WEEK_OF_YEAR, -1);
		// faire un affichage.
	}

	protected void affecterDates(GregorianCalendar nouveauLundi) {
		dateDuLundi = nouveauLundi;
		// recherche de la date correspondante.
		dateDuLundi.add(Calendar.DAY_OF_WEEK, -((dateDuLundi.get(Calendar.DAY_OF_WEEK) + (7 - Calendar.MONDAY)) % 7));

		dateCourante = new GregorianCalendar();
		dateCourante.setMinimalDaysInFirstWeek(4);
		dateCourante.setFirstDayOfWeek(Calendar.MONDAY);
		dateCourante.setTime(dateDuLundi.getTime());
	}

	/**
	 * retourne un dico avec comme cles les jours de la semaine et les valeurs, les dates correspondantes.
	 * 
	 * @return le dico en question en Hashtable.
	 */
	public Hashtable getSemaine() {

		Hashtable semaine = new Hashtable();

		for (int index = 0; index < 7; index++) {
			String leJour = (String) lesJours.get(index);
			String laDate = Parametres.formatDate.format(dateCourante.getTime());
			semaine.put(leJour, laDate);
			dateCourante.add(Calendar.HOUR_OF_DAY, 24); // mise a jours pour le lendemain.
		}

		return semaine;
	}

}
