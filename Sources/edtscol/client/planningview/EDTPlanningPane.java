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

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class EDTPlanningPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2066172875514747180L;
	private JScrollBar horizontalBar;
	private JScrollBar verticalBar;

	/**
	 * Constructeur par defaut, prend une zoneEditable, le planning a afficher.
	 * 
	 * @param zone
	 *            la zone editable de l'EDT
	 */
	public EDTPlanningPane(Component zone) {
		super(zone);
		initialiser();
	}

	public int getHorizontalBarValue() {
		return horizontalBar.getValue();
	}

	/**
	 * un autre.. Juste au cas ou. cree tout seul la zone editable : ne pas utiliser.
	 */
	public EDTPlanningPane() {
		super();
		initialiser();
	}

	/**
	 * Permet d'initialiser le composant EDT.
	 */
	private void initialiser() {
		this.setBorder(BorderFactory.createLineBorder(Parametres.NOIR));
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		horizontalBar = this.getHorizontalScrollBar();
		verticalBar = this.getVerticalScrollBar();
	}

	/**
	 * fixe le viewport au debut de l'heure selectionnee.
	 * 
	 * @param heureDeb
	 *            l'heure(en flottant) a partir de laquelle l'EDT sera visible par defaut.
	 */
	public void setDebutVisible(double heureDeb) {
		int valDeb = (int) (heureDeb * Parametres.TAILLE_HEURE) + 1;

		for (int i = 1; i < valDeb; i += Parametres.TAILLE_HEURE * 2) {
			horizontalBar.setValue(450);
		}
		verticalBar.setValue(0);
	}

	/**
	 * fait defiler le planning horizontalement vers la droite. Action sur le JSCrollBar horizontal
	 */
	public void versDroite() {
		int actuelleValeur = horizontalBar.getValue();
		if (actuelleValeur < horizontalBar.getMaximum()) {
			int val = actuelleValeur + Parametres.TAILLE_HEURE;
			horizontalBar.setValue(val);
		}
	}

	/**
	 * fait defiler le planning horizontalement vers la gauche. Action sur le JSCrollBar horizontal
	 */
	public void versGauche() {
		int actuelleValeur = horizontalBar.getValue();
		if (actuelleValeur > horizontalBar.getMinimum()) {
			horizontalBar.setValue(actuelleValeur - Parametres.TAILLE_HEURE);
		}
	}

	/**
	 * fait defiler le planning verticalement vers le haut. Action sur le JSCrollBar vertical
	 */
	public void versHaut() {
		int actuelleValeur = verticalBar.getValue();
		if (actuelleValeur > verticalBar.getMinimum()) {
			verticalBar.setValue(actuelleValeur - Parametres.HAUTEUR_JOUR);
		}
	}

	/**
	 * fait defiler le planning verticalement vers le bas. Action sur le JSCrollBar vertical
	 */
	public void versBas() {
		int actuelleValeur = verticalBar.getValue();
		if (actuelleValeur < verticalBar.getMaximum()) {
			verticalBar.setValue(actuelleValeur + Parametres.HAUTEUR_JOUR);
		}
	}

}
