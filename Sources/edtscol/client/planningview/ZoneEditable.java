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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import com.webobjects.foundation.NSArray;

public class ZoneEditable extends JPanel implements Scrollable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8984613217123023806L;
	private LinkedList listeComposants;
	private LinkedList listeReservations, listeStatique;
	private int composantActif = 0;
	// le tooltip refait !
	private Message leMessage;
	private Object dernier;

	/**
	 * constructeur
	 */
	public ZoneEditable() {
		super();
		leMessage = new Message();
		this.setBackground(Parametres.COULEUR_FOND);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setPreferredSize(Parametres.TAILLE_EDT_TOTALE); // tout l'emploi du temps
		this.listeComposants = new LinkedList();
		this.listeReservations = new LinkedList();
		listeStatique = new LinkedList();
		this.preparerGrille(true);
	}

	// affiche un message contenu dans la chaine.
	public void afficherMessage(String message, Component component, Point pos) {
		leMessage.setMessage(message, component, pos);
		leMessage.setVisible(true);
		leMessage.requestFocus();
	}

	// affiche le message contenu dans la liste (cas plusieurs lignes).
	public void afficherMessage(NSArray message, Component component, Point pos) {
		leMessage.setMessage(message, component, pos);
		leMessage.setVisible(true);
		leMessage.requestFocus();
	}

	/*
	 * affiche le message contenu dans la liste (cas plusieurs lignes). /!\ cette methode devra etre generalisee et exclusivement utilisee.
	 */
	public void afficherMessage(ArrayList message, Component component, Point pos) {
		leMessage.setMessage(message, component, pos);
		leMessage.setVisible(true);
		leMessage.requestFocus();
	}

	// cache le message actuellement affiché.
	public void retirerMessage() {
		leMessage.setVisible(false);
	}

	/**
	 * changer le type de curseur.
	 */
	public void setSouris(Cursor unCurseur) {
		this.setCursor(unCurseur);
	}

	/**
	 * dessine la grille : classe utilisable en interne seulement.
	 */
	public void preparerGrille(boolean plusieursJours) {
		int nbl = 1;

		int height = (int) Parametres.TAILLE_EDT_TOTALE.getHeight();
		// les verticales
		for (int iLigne = 0; iLigne < Parametres.A4_HEIGHT + 1; iLigne += Parametres.TAILLE_QUART_HEURE * 2) {

			if (nbl == 4) {
				this.addToListeComposants(new Ligne(iLigne, 0, iLigne, height, Color.lightGray));
				nbl = 0;
			}
			else {
				this.addToListeComposants(new Ligne(iLigne, 0, iLigne, height, new Color(0xEE, 0xE8, 0xAA)));
			}
			nbl++;
		}

		if (plusieursJours) {
			// les horizontales
			for (int iLigne = 0; iLigne < 666; iLigne += Parametres.HAUTEUR_JOUR) {
				Ligne uneLigne = new Ligne(0, iLigne, Parametres.A4_HEIGHT, iLigne);
				this.addToListeComposants(uneLigne);
			}
		}
		this.repaint();
	}

	/**
	 * pour ajouter un listener d'evenements
	 */
	public void registerForEvent(MouseMotionListener listener) {
		addMouseMotionListener(listener);
	}

	/**
	 * redefinition du composant via la methode paint(), java 2D.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		int listIndex;
		Drawable drawable;
		// on passe en graphique 2D (meilleur)
		Graphics2D graph = (Graphics2D) g;

		// on dessine ce qu'il y'a dans les listes
		listIndex = 0;

		while (listIndex < listeComposants.size()) {
			drawable = (Drawable) (listeComposants.get(listIndex));
			drawable.draw(graph);
			listIndex++;
		}

		listIndex = 0;
		while (listIndex < listeReservations.size()) {
			drawable = (Drawable) (listeReservations.get(listIndex));
			drawable.draw(graph);
			listIndex++;
		}

		listIndex = 0;
		while (listIndex < listeStatique.size()) {
			drawable = (Drawable) (listeStatique.get(listIndex));
			drawable.draw(graph);
			listIndex++;
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

	// fin implementation Scrollable

	/**
	 * Ajoute un objet a la liste des composants en vue d'etre affiche
	 * 
	 * @param fig
	 *            une figure complexe.
	 */
	/*
	 * public void addToListeComposants(FigureSimple fig) { listeComposants.add(fig); this.repaint(); }
	 */
	/**
	 * Ajoute un objet a la liste des composants en vue d'etre affiche
	 * 
	 * @param fig
	 *            une figure simple.
	 */
	public void addToListeComposants(FigureComplexe fig) {
		listeComposants.add(fig);
		this.repaint();
	}

	public void addToListeStatique(FigureSimple fig) {
		listeStatique.add(fig);
		this.repaint();
	}

	public void addToListeReservations(FigureSimple fig) {
		listeReservations.add(fig);
		this.repaint();
	}

	/**
	 * Supprime un composant de la liste : destruction.
	 * 
	 * @param index
	 *            : l'index du composant a supprimer dans la liste.
	 */
	public void removeFromListeReservations(int index) {
		listeReservations.remove(index);
	}

	/**
	 * Renvoie le composant dans la liste situe a l'index donne
	 * 
	 * @param index
	 *            index du composant dans la liste.
	 * @return objet retourne de type Object, faire un transtypage suivant le cas...
	 */
	public Object getReservationAtIndex(int index) {
		// System.out.println("liste reservation " + listeReservations.get(index));
		return listeReservations.get(index);
	}

	public void setActiveComponent(int n) {
		composantActif = n;
	}

	public int getActiveComponent() {
		return composantActif;
	}

	/**
	 * Vider la liste des composants dans la liste.
	 */
	public void viderListeComposants() {
		this.listeComposants.clear();
		repaint();
	}

	/**
	 * Vider la liste des composants dans la liste : supprimer tous les objets sur l'EDT. Utiliser avec precaution donc.
	 */
	public void viderListeReservations() {
		this.listeReservations.clear();
		this.listeStatique.clear();
		repaint();
	}

	/**
	 * retourne une reference sur la liste des composantes.
	 * 
	 * @return Une LinkedList representant la liste.
	 */

	public LinkedList getListeComposants() {
		return listeReservations;
	}

	public LinkedList getListeReservations() {
		return listeReservations;
	}

	/**
	 * Supprime le dernier composant rentree dans la liste.
	 */
	public void removeLastComposant() {
		try {
			this.dernier = this.listeReservations.removeLast();
			repaint();
		}
		catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Annule la suppression du dernier composant.
	 */
	public void restoreLastComposant() {
		try {
			listeComposants.addLast(this.dernier);
			repaint();
		}
		catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

}
