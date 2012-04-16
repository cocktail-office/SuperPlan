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
package edtscol.client.gestioncontraintes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.EventListener;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.event.MouseInputAdapter;

import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

public class JPlanning extends JPanel {
	public static final int SELECTION_CONTINUE = 0;
	public static final int SELECTION_RECTANGULAIRE = 1;
	public static final int SELECTION_VERTICALE = 2;
	public static final int SELECTION_HORIZONTALE = 3;

	// public static final Color COULEUR_DE_FOND = UIManager.getColor("TextField.background");
	// public static final Color COULEUR_DE_SELECTION = UIManager.getColor("TextField.selectionBackground");
	public static final Color COULEUR_DE_FOND = new Color(100, 220, 100, 100);
	public static final Color COULEUR_DE_SELECTION = new Color(220, 100, 100, 100);

	private int largeurCase;
	private int hauteurCase;
	private int largeurEspace;
	private int hauteurEspace;
	private int nbCasesLargeur;
	private int nbCasesHauteur;
	private int modeSelection;
	private Point debutSelection;
	private Point finSelection;
	private Vector<Color> couleursDuPlanning = new Vector<Color>();
	private EventListenerList listenerList = new EventListenerList();
	private PlanningEvent planningEvent = null;
	private boolean enabled = true;

	public JPlanning(int largeurCase, int hauteurCase, int largeurEspace, int hauteurEspace, int nbCasesLargeur, int nbCasesHauteur, int modeSelection) {
		this.largeurCase = largeurCase;
		this.hauteurCase = hauteurCase;
		this.largeurEspace = largeurEspace;
		this.hauteurEspace = hauteurEspace;
		this.nbCasesLargeur = nbCasesLargeur;
		this.nbCasesHauteur = nbCasesHauteur;
		this.modeSelection = modeSelection;
		setPreferredSize(new Dimension(largeurCase * nbCasesLargeur + largeurEspace * (nbCasesLargeur - 1), hauteurCase * nbCasesHauteur
				+ hauteurEspace * (nbCasesHauteur - 1)));
		setMinimumSize(getPreferredSize());
		setMaximumSize(getPreferredSize());
		for (int indexCase = 0; indexCase < nbCasesHauteur * nbCasesLargeur; indexCase++) {
			couleursDuPlanning.add(COULEUR_DE_FOND);
		}

		JPlanningListener leListener = new JPlanningListener();
		addMouseListener(leListener);
		addMouseMotionListener(leListener);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public NSDictionary getZonesGraphiquesSelectionneesParCouleur() {
		NSMutableDictionary leDico = new NSMutableDictionary();

		// Recup des zones selectionnees
		NSDictionary lesZonesDeSelection = new NSDictionary((Dictionary) getSelectedAreas(), true);
		NSArray lesCles = lesZonesDeSelection.allKeys();

		int indexSelection;
		for (indexSelection = 0; indexSelection < lesCles.count(); indexSelection++) {
			// Recup des extremites des zones
			Point pointDebut = (Point) lesCles.objectAtIndex(indexSelection);
			Point pointFin = (Point) lesZonesDeSelection.objectForKey(pointDebut);
			// Recup des couleurs
			NSArray lesCouleurs = new NSArray(getColorOfArea(pointDebut, pointFin, modeSelection).toArray());

			int indexCouleur;
			Color laCouleurPrecedente = null;
			int debutXCourant = (int) pointDebut.getX();
			int debutYCourant = (int) pointDebut.getY();
			for (indexCouleur = 0; indexCouleur < lesCouleurs.count(); indexCouleur++) {
				if (!((Color) lesCouleurs.objectAtIndex(indexCouleur)).equals(laCouleurPrecedente) && (laCouleurPrecedente != null)) {
					// Calcul des nouvelles extremites
					Point nouveauPointDebut = new Point(debutXCourant, debutYCourant);
					Point nouveauPointFin = new Point((((int) pointDebut.getX()) + indexCouleur - 1) % nbCasesLargeur, ((int) pointDebut.getY())
							+ ((((int) pointDebut.getX()) + indexCouleur - 1) / nbCasesLargeur));
					// Ajout au dico
					leDico.setObjectForKey(nouveauPointFin, nouveauPointDebut);
					// Reinit des positions
					debutXCourant = (((int) pointDebut.getX()) + indexCouleur) % nbCasesLargeur;
					debutYCourant = ((int) pointDebut.getY()) + ((((int) pointDebut.getX()) + indexCouleur) / nbCasesLargeur);
				}
				// Reinit de la couleur
				laCouleurPrecedente = (Color) lesCouleurs.objectAtIndex(indexCouleur);
			}
			// Calcul des nouvelles extremites
			Point nouveauPointDebut = new Point(debutXCourant, debutYCourant);
			Point nouveauPointFin = new Point((((int) pointDebut.getX()) + indexCouleur - 1) % nbCasesLargeur, ((int) pointDebut.getY())
					+ ((((int) pointDebut.getX()) + indexCouleur - 1) / nbCasesLargeur));
			// Ajout au dico
			leDico.setObjectForKey(nouveauPointFin, nouveauPointDebut);
		}

		return leDico;
	}

	/**
	 * Renvoie les zones selectionnees avec leurs heures et leurs jours de semaine (pas leurs dates). Le dictionnaire de retour est
	 * constitue d'un enregistrement par zones (une par ligne). Chaque enregistrement a pour cle le debut de la zone et pour valeur la fin.
	 * Les timestamps renvoyes contiennent les informations conernant la poisition dans la semaine mais sans referentiel dans l'annee ou le
	 * mois.
	 * 
	 * @return Un dictionnaire des zones
	 */
	public NSDictionary getZonesTemporellesSelectionnees() {
		NSMutableDictionary leDico = new NSMutableDictionary();

		NSDictionary leDicoGraphique = new NSDictionary((Dictionary) getSelectedAreas(), true);
		NSArray lesCles = leDicoGraphique.allKeys();

		// Pour chaque zone
		for (int indexZone = 0; indexZone < lesCles.count(); indexZone++) {
			GregorianCalendar leDebutZoneT = new GregorianCalendar();
			GregorianCalendar laFinZoneT = new GregorianCalendar();

			Point leDebutZoneG = (Point) lesCles.objectAtIndex(indexZone);
			Point laFinZoneG = (Point) leDicoGraphique.objectForKey(lesCles.objectAtIndex(indexZone));

			// Conversion des extremites
			leDebutZoneT.set(Calendar.DAY_OF_WEEK, (int) leDebutZoneG.getY() + Calendar.MONDAY);
			leDebutZoneT.set(Calendar.HOUR_OF_DAY, ((int) leDebutZoneG.getX() / 4));
			leDebutZoneT.set(Calendar.MINUTE, ((int) leDebutZoneG.getX() % 4) * 15);

			laFinZoneT.set(Calendar.DAY_OF_WEEK, (int) laFinZoneG.getY() + Calendar.MONDAY);
			if (((int) laFinZoneG.getX()) == 95) {
				laFinZoneT.set(Calendar.HOUR_OF_DAY, 23);
				laFinZoneT.set(Calendar.MINUTE, 59);
				laFinZoneT.set(Calendar.SECOND, 59);
				laFinZoneT.set(Calendar.MILLISECOND, 999);
			}
			else {
				laFinZoneT.set(Calendar.HOUR_OF_DAY, ((int) laFinZoneG.getX() / 4));
				laFinZoneT.set(Calendar.MINUTE, (((int) laFinZoneG.getX() % 4) * 15) + 15);
			}

			// Ajout au dico
			leDico.setObjectForKey(laFinZoneT, leDebutZoneT);
		}
		return leDico;
	}

	public boolean afficherDansLePlanning(NSArray lesObjets, String cleDebut, String cleFin, Color couleur, Color couleurAlternative) {
		for (int index = 0; index < lesObjets.count(); index++) {
			if (lesObjets.objectAtIndex(index) == NSKeyValueCoding.NullValue) {
				continue;
			}

			// Recup des champs
			NSTimestamp leDebut = (NSTimestamp) ((EOGenericRecord) lesObjets.objectAtIndex(index)).valueForKey(cleDebut);
			NSTimestamp laFin = (NSTimestamp) ((EOGenericRecord) lesObjets.objectAtIndex(index)).valueForKey(cleFin);

			// Conversion des couleurs
			Color laCouleur = couleur;
			if (laCouleur == null) {
				laCouleur = JPlanning.COULEUR_DE_SELECTION;
			}
			Color laCouleurAlternative = couleurAlternative;

			afficherCreneau(leDebut, laFin, laCouleur, laCouleurAlternative);
		}
		repaint();
		return true;
	}

	public boolean afficherCreneau(NSTimestamp debut, NSTimestamp fin, Color laCouleur, Color laCouleurAlternative) {
		// Recup des Calendar
		NSTimestamp leCalendarDebut = debut;
		NSTimestamp leCalendarFin = fin;

		GregorianCalendar gcDebut = new GregorianCalendar(), gcFin = new GregorianCalendar();
		gcDebut.setTime(leCalendarDebut);
		gcFin.setTime(leCalendarFin);

		int lHeureDebut = gcDebut.get(GregorianCalendar.HOUR_OF_DAY);
		int lHeureFin = gcFin.get(GregorianCalendar.HOUR_OF_DAY);
		int laMinuteDebut = gcDebut.get(GregorianCalendar.MINUTE);
		int laMinuteFin = gcFin.get(GregorianCalendar.MINUTE);
		int leJourDebut = gcDebut.get(GregorianCalendar.DAY_OF_WEEK);
		int leJourFin = gcFin.get(GregorianCalendar.DAY_OF_WEEK);

		// Conversion en cases
		int xDebut = ((lHeureDebut) * 4) + (laMinuteDebut / 15);
		int xFin = ((lHeureFin) * 4) + (laMinuteFin / 15);
		if (laMinuteFin == 59) {
			xFin++;
		}
		if (xFin > 0) {
			xFin = xFin - 1; // BUG
		}
		int yDebut = ((leJourDebut + (7 - Calendar.MONDAY)) % 7);
		int yFin = ((leJourFin + (7 - Calendar.MONDAY)) % 7);

		// Affichage des cases
		if ((xDebut > 0) && (getColorOfCell(xDebut - 1, yDebut).equals(laCouleur))) {
			addColoredArea(new Point(xDebut, yDebut), new Point(xFin, yFin), laCouleurAlternative, modeSelection);
		}
		else {
			addColoredArea(new Point(xDebut, yDebut), new Point(xFin, yFin), laCouleur, modeSelection);
		}

		return true;
	}

	private int _getFirstDayOfWeek(NSTimestamp time) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4); // le premier Jeudi de Janvier...
		cal.setTime(time);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.setTime(cal.getTime());
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	private int _getNumberOfDaysInMonth(NSTimestamp time) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4); // le premier Jeudi de Janvier...
		cal.setTime(time);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.setTime(cal.getTime());
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Renvoie les zones de selection en fonction de leurs couleurs avec leurs heures et leurs jours de semaine (pas leurs dates). Le
	 * dictionnaire de retour est constitue d'un enregistrement par zones (une par ligne). Chaque enregistrement a pour cle le debut de la
	 * zone et pour valeur la fin. Les timestamps renvoyes contiennent les informations conernant la poisition dans la semaine mais sans
	 * referentiel dans l'annee ou le mois.
	 * 
	 * @return Un dictionnaire des zones
	 */
	public NSDictionary getZonesTemporellesSelectionneesParCouleur() {
		NSMutableDictionary leDico = new NSMutableDictionary();

		NSDictionary leDicoGraphique = getZonesGraphiquesSelectionneesParCouleur();
		NSArray lesCles = leDicoGraphique.allKeys();

		// Pour chaque zone
		for (int indexZone = 0; indexZone < lesCles.count(); indexZone++) {
			GregorianCalendar leDebutZoneT = new GregorianCalendar();
			GregorianCalendar laFinZoneT = new GregorianCalendar();
			Point leDebutZoneG = (Point) lesCles.objectAtIndex(indexZone);
			Point laFinZoneG = (Point) leDicoGraphique.objectForKey(lesCles.objectAtIndex(indexZone));

			// Conversion des extremites
			leDebutZoneT.set(Calendar.DAY_OF_WEEK, (int) leDebutZoneG.getY() + Calendar.MONDAY);
			leDebutZoneT.set(Calendar.HOUR_OF_DAY, ((int) leDebutZoneG.getX() / 4) + 7);
			leDebutZoneT.set(Calendar.MINUTE, ((int) leDebutZoneG.getX() % 4) * 15);

			laFinZoneT.set(Calendar.DAY_OF_WEEK, (int) laFinZoneG.getY() + Calendar.MONDAY);
			laFinZoneT.set(Calendar.HOUR_OF_DAY, ((int) laFinZoneG.getX() / 4) + 7);
			laFinZoneT.set(Calendar.MINUTE, (((int) laFinZoneG.getX() % 4) * 15) + 15);

			// Ajout au dico
			leDico.setObjectForKey(laFinZoneT, leDebutZoneT);
		}
		return leDico;
	}

	protected Vector<Integer> getIndexesOf(Point debut, Point fin, int mode) {
		Vector<Integer> leTableau = new Vector<Integer>();
		if (debut != null && fin != null) {
			int xDebut = debut.x;
			int yDebut = debut.y;
			int xFin = fin.x;
			int yFin = fin.y;
			if (xDebut > nbCasesLargeur - 1) {
				xDebut = nbCasesLargeur - 1;
			}
			if (yDebut > nbCasesHauteur - 1) {
				yDebut = nbCasesHauteur - 1;
			}
			if (xFin > nbCasesLargeur - 1) {
				xFin = nbCasesLargeur - 1;
			}
			if (yFin > nbCasesHauteur - 1) {
				yFin = nbCasesHauteur - 1;
			}
			if (xDebut < 0) {
				xDebut = 0;
			}
			if (yDebut < 0) {
				yDebut = 0;
			}
			if (xFin < 0) {
				xFin = 0;
			}
			if (yFin < 0) {
				yFin = 0;
			}
			label0: switch (mode) {
			default:
				break;

			case 0: // '\0'
				int positionInitiale = xDebut + yDebut * nbCasesLargeur;
				int positionFinale = xFin + yFin * nbCasesLargeur;
				if (positionInitiale > positionFinale) {
					int pos = positionInitiale;
					positionInitiale = positionFinale;
					positionFinale = pos;
				}
				for (int indexCase = positionInitiale; indexCase <= positionFinale; indexCase++) {
					leTableau.add(new Integer(indexCase));
				}

				break;

			case 1: // '\001'
				if (xDebut > xFin) {
					int x = xDebut;
					xDebut = xFin;
					xFin = x;
				}
				if (yDebut > yFin) {
					int y = yDebut;
					yDebut = yFin;
					yFin = y;
				}
				int indexCaseHauteur = yDebut;
				do {
					if (indexCaseHauteur > yFin) {
						break label0;
					}
					for (int indexCaseLargeur = xDebut; indexCaseLargeur <= xFin; indexCaseLargeur++) {
						leTableau.add(new Integer(indexCaseLargeur + indexCaseHauteur * nbCasesLargeur));
					}

					indexCaseHauteur++;
				}
				while (true);

			case 2: // '\002'
				if (yDebut > yFin) {
					int y = yDebut;
					yDebut = yFin;
					yFin = y;
				}
				for (int indexCaseH = yDebut; indexCaseH <= yFin; indexCaseH++) {
					leTableau.add(new Integer(xDebut + indexCaseH * nbCasesLargeur));
				}

				break;

			case 3: // '\003'
				if (xDebut > xFin) {
					int x = xDebut;
					xDebut = xFin;
					xFin = x;
				}
				for (int indexCaseLargeur = xDebut; indexCaseLargeur <= xFin; indexCaseLargeur++) {
					leTableau.add(new Integer(indexCaseLargeur + yDebut * nbCasesLargeur));
				}

				break;
			}
		}
		return leTableau;
	}

	/**
	 * Renvoie la couleur de la cellule definie par ses coordonnees.
	 * 
	 * @param xCellule
	 *            Coordonnee horizontale de la cellule
	 * @param yCellule
	 *            Coordonnee verticale de la cellule
	 * 
	 * @return La couleur de la cellule
	 */
	public Color getColorOfCell(int xCellule, int yCellule) {
		return couleursDuPlanning.elementAt(xCellule + (yCellule * nbCasesLargeur));
	}

	public Vector<Color> getColorOfArea(Point debut, Point fin, int mode) {
		Vector<Color> leTableau = new Vector<Color>();
		Vector<Integer> leTableauDIndex = getIndexesOf(debut, fin, mode);
		for (int indexCellule = 0; indexCellule < leTableauDIndex.size(); indexCellule++) {
			leTableau.add(couleursDuPlanning.elementAt((leTableauDIndex.elementAt(indexCellule)).intValue()));
		}

		return leTableau;
	}

	public Hashtable<Point, Point> getSelectedAreas() {
		Hashtable<Point, Point> leDico = new Hashtable<Point, Point>();
		if (debutSelection != null && finSelection != null) {
			int xDebut = debutSelection.x;
			int yDebut = debutSelection.y;
			int xFin = finSelection.x;
			int yFin = finSelection.y;
			if (xDebut > nbCasesLargeur - 1) {
				xDebut = nbCasesLargeur - 1;
			}
			if (yDebut > nbCasesHauteur - 1) {
				yDebut = nbCasesHauteur - 1;
			}
			if (xFin > nbCasesLargeur - 1) {
				xFin = nbCasesLargeur - 1;
			}
			if (yFin > nbCasesHauteur - 1) {
				yFin = nbCasesHauteur - 1;
			}
			if (xDebut < 0) {
				xDebut = 0;
			}
			if (yDebut < 0) {
				yDebut = 0;
			}
			if (xFin < 0) {
				xFin = 0;
			}
			if (yFin < 0) {
				yFin = 0;
			}
			switch (modeSelection) {
			case 0: // '\0'
				int positionInitiale = xDebut + yDebut * nbCasesLargeur;
				int positionFinale = xFin + yFin * nbCasesLargeur;
				if (positionInitiale > positionFinale) {
					int pos = positionInitiale;
					positionInitiale = positionFinale;
					positionFinale = pos;
				}
				leDico.put(new Point(positionInitiale % nbCasesLargeur, positionInitiale / nbCasesLargeur), new Point(
						positionFinale % nbCasesLargeur, positionFinale / nbCasesLargeur));
				break;

			case 1: // '\001'
				if (xDebut > xFin) {
					int x = xDebut;
					xDebut = xFin;
					xFin = x;
				}
				if (yDebut > yFin) {
					int y = yDebut;
					yDebut = yFin;
					yFin = y;
				}
				for (int indexY = yDebut; indexY <= yFin; indexY++) {
					leDico.put(new Point(xDebut, indexY), new Point(xFin, indexY));
				}
				break;

			case 2: // '\002'
				if (yDebut > yFin) {
					int y = yDebut;
					yDebut = yFin;
					yFin = y;
				}
				for (int indexY = yDebut; indexY <= yFin; indexY++) {
					leDico.put(new Point(xDebut, indexY), new Point(xDebut, indexY));
				}
				break;

			case 3: // '\003'
				if (xDebut > xFin) {
					int x = xDebut;
					xDebut = xFin;
					xFin = x;
				}
				leDico.put(new Point(xDebut, yDebut), new Point(xFin, yDebut));
				break;
			}
		}
		return leDico;
	}

	public boolean isSelected() {
		getIndexesOf(debutSelection, finSelection, modeSelection);
		return debutSelection != null && finSelection != null;
	}

	public boolean isEmptySelection() {
		Vector<Integer> leTableauDIndex = getIndexesOf(debutSelection, finSelection, modeSelection);
		for (int indexCellule = 0; indexCellule < leTableauDIndex.size(); indexCellule++) {
			if (!couleursDuPlanning.elementAt((leTableauDIndex.elementAt(indexCellule)).intValue()).equals(COULEUR_DE_FOND)) {
				return false;
			}
		}

		return true;
	}

	public boolean isFullSelection() {
		Vector<Integer> leTableauDIndex = getIndexesOf(debutSelection, finSelection, modeSelection);
		if (leTableauDIndex.size() == 0) {
			return false;
		}
		for (int indexCellule = 0; indexCellule < leTableauDIndex.size(); indexCellule++) {
			if (couleursDuPlanning.elementAt((leTableauDIndex.elementAt(indexCellule)).intValue()).equals(COULEUR_DE_FOND)) {
				return false;
			}
		}

		return true;
	}

	public boolean isColoredCell(int xCellule, int yCellule) {
		return couleursDuPlanning.elementAt(xCellule + yCellule * nbCasesLargeur).equals(COULEUR_DE_FOND);
	}

	public boolean isSelectedCell(int xCellule, int yCellule) {
		if (debutSelection != null && finSelection != null) {
			int xDebut = debutSelection.x;
			int yDebut = debutSelection.y;
			int xFin = finSelection.x;
			int yFin = finSelection.y;
			if (xDebut > nbCasesLargeur - 1) {
				xDebut = nbCasesLargeur - 1;
			}
			if (yDebut > nbCasesHauteur - 1) {
				yDebut = nbCasesHauteur - 1;
			}
			if (xFin > nbCasesLargeur - 1) {
				xFin = nbCasesLargeur - 1;
			}
			if (yFin > nbCasesHauteur - 1) {
				yFin = nbCasesHauteur - 1;
			}
			if (xDebut < 0) {
				xDebut = 0;
			}
			if (yDebut < 0) {
				yDebut = 0;
			}
			if (xFin < 0) {
				xFin = 0;
			}
			if (yFin < 0) {
				yFin = 0;
			}
			switch (modeSelection) {
			case 0: // '\0'
				int positionInitiale = xDebut + yDebut * nbCasesLargeur;
				int positionFinale = xFin + yFin * nbCasesLargeur;
				int positionCellule = xCellule + yCellule * nbCasesLargeur;
				if (positionInitiale > positionFinale) {
					int pos = positionInitiale;
					positionInitiale = positionFinale;
					positionFinale = pos;
				}
				return positionCellule >= positionInitiale && positionCellule <= positionFinale;

			case 1: // '\001'
				if (xDebut > xFin) {
					int x = xDebut;
					xDebut = xFin;
					xFin = x;
				}
				if (yDebut > yFin) {
					int y = yDebut;
					yDebut = yFin;
					yFin = y;
				}
				return xCellule >= xDebut && xCellule <= xFin && yCellule >= yDebut && yCellule <= yFin;

			case 2: // '\002'
				if (yDebut > yFin) {
					int y = yDebut;
					yDebut = yFin;
					yFin = y;
				}
				return xCellule == xDebut && yCellule >= yDebut && yCellule <= yFin;

			case 3: // '\003'
				if (xDebut > xFin) {
					int x = xDebut;
					xDebut = xFin;
					xFin = x;
				}
				return yCellule == yDebut && xCellule >= xDebut && yCellule <= xFin;
			}
		}
		return false;
	}

	public void clearSelection() {
		debutSelection = null;
		finSelection = null;
		repaint();
	}

	public void clearPlanning() {
		couleursDuPlanning.removeAllElements();
		for (int indexCase = 0; indexCase < nbCasesHauteur * nbCasesLargeur; indexCase++) {
			couleursDuPlanning.add(COULEUR_DE_FOND);
		}
		repaint();
	}

	public void addColoredArea(Point debut, Point fin, Color couleur, int mode) {
		Vector<Integer> leTableauDIndex = getIndexesOf(debut, fin, mode);
		for (int indexCellule = 0; indexCellule < leTableauDIndex.size(); indexCellule++) {
			couleursDuPlanning.setElementAt(couleur, (leTableauDIndex.elementAt(indexCellule)).intValue());
		}
		repaint();
	}

	public void addMultiColoredArea(Point debut, Point fin, Vector<Color> couleurs, int mode) {
		Vector<Integer> leTableauDIndex = getIndexesOf(debut, fin, mode);
		for (int indexCellule = 0; indexCellule < leTableauDIndex.size(); indexCellule++) {
			couleursDuPlanning.setElementAt(couleurs.elementAt((leTableauDIndex.elementAt(indexCellule)).intValue()),
					(leTableauDIndex.elementAt(indexCellule)).intValue());
		}
		repaint();
	}

	public void paintComponent(Graphics leGraphics) {
		super.paintComponent(leGraphics);
		getInsets();
		int xOrigine = 0;
		int yOrigine = 0;
		for (int indexCaseHauteur = 0; indexCaseHauteur < nbCasesHauteur; indexCaseHauteur++) {
			for (int indexCaseLargeur = 0; indexCaseLargeur < nbCasesLargeur; indexCaseLargeur++) {
				leGraphics.setColor(couleursDuPlanning.elementAt(indexCaseLargeur + indexCaseHauteur * nbCasesLargeur));
				leGraphics.fillRect(xOrigine + indexCaseLargeur * largeurCase + (indexCaseLargeur * largeurEspace), yOrigine + indexCaseHauteur
						* hauteurCase + (indexCaseHauteur * hauteurEspace), largeurCase, hauteurCase);
			}

		}

		leGraphics.setColor(COULEUR_DE_SELECTION);
		Vector<Integer> leTableauDIndex = getIndexesOf(debutSelection, finSelection, modeSelection);
		for (int indexCellule = 0; indexCellule < leTableauDIndex.size(); indexCellule++) {
			int indexCase = (leTableauDIndex.elementAt(indexCellule)).intValue();
			leGraphics.setColor((couleursDuPlanning.elementAt(indexCase)).darker());
			leGraphics.fillRect(xOrigine + (indexCase % nbCasesLargeur) * largeurCase + (indexCase % nbCasesLargeur * largeurEspace), yOrigine
					+ (indexCase / nbCasesLargeur) * hauteurCase + (indexCase / nbCasesLargeur * hauteurEspace), largeurCase, hauteurCase);
		}

	}

	public void addPlanningListener(PlanningListener l) {
		listenerList.add(PlanningListener.class, l);
	}

	public void removePlanningListener(PlanningListener l) {
		listenerList.remove(PlanningListener.class, l);
	}

	protected void firePlanningSelectionDidChange() {
		Object listeners[] = listenerList.getListenerList();
		if (debutSelection != null && finSelection != null) {
			for (int indexListener = listeners.length - 2; indexListener >= 0; indexListener -= 2) {
				if (listeners[indexListener] == (PlanningListener.class)) {
					planningEvent = new PlanningEvent(this, debutSelection, finSelection, modeSelection);
					((PlanningListener) listeners[indexListener + 1]).planningSelectionDidChange(planningEvent);
				}
			}
		}
		else {
			for (int indexListener = listeners.length - 2; indexListener >= 0; indexListener -= 2) {
				if (listeners[indexListener] == (PlanningListener.class)) {
					planningEvent = new PlanningEvent(this, new Point(-1, -1), new Point(-1, -1), modeSelection);
					((PlanningListener) listeners[indexListener + 1]).planningSelectionDidChange(planningEvent);
				}
			}
		}
	}

	private class JPlanningListener extends MouseInputAdapter {

		public void mousePressed(MouseEvent e) {
			if (enabled) {
				int xFin = e.getX() / (largeurEspace + largeurCase);
				int yFin = e.getY() / (hauteurEspace + hauteurCase);
				if (xFin > nbCasesLargeur - 1) {
					xFin = nbCasesLargeur - 1;
				}
				if (yFin > nbCasesHauteur - 1) {
					yFin = nbCasesHauteur - 1;
				}
				if (xFin < 0) {
					xFin = 0;
				}
				if (yFin < 0) {
					yFin = 0;
				}
				debutSelection = new Point(xFin, yFin);
				finSelection = new Point(xFin, yFin);
				repaint();
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (enabled) {
				int xFin = e.getX() / (largeurEspace + largeurCase);
				int yFin = e.getY() / (hauteurEspace + hauteurCase);
				if (xFin > nbCasesLargeur - 1) {
					xFin = nbCasesLargeur - 1;
				}
				if (yFin > nbCasesHauteur - 1) {
					yFin = nbCasesHauteur - 1;
				}
				if (xFin < 0) {
					xFin = 0;
				}
				if (yFin < 0) {
					yFin = 0;
				}
				finSelection = new Point(xFin, yFin);
				repaint();
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (enabled) {
				int xFin = e.getX() / (largeurEspace + largeurCase);
				int yFin = e.getY() / (hauteurEspace + hauteurCase);
				if (xFin > nbCasesLargeur - 1) {
					xFin = nbCasesLargeur - 1;
				}
				if (yFin > nbCasesHauteur - 1) {
					yFin = nbCasesHauteur - 1;
				}
				if (xFin < 0) {
					xFin = 0;
				}
				if (yFin < 0) {
					yFin = 0;
				}
				finSelection = new Point(xFin, yFin);
				repaint();
				firePlanningSelectionDidChange();
			}
		}

	}

	public interface PlanningListener extends EventListener {
		public abstract void planningSelectionDidChange(PlanningEvent planningevent);
	}

	public class PlanningEvent extends EventObject {

		protected Point firstIndex;
		protected Point lastIndex;
		protected int modeSelection;

		public PlanningEvent(Object source, Point firstIndex, Point lastIndex, int modeSelection) {
			super(source);
			this.firstIndex = firstIndex;
			this.lastIndex = lastIndex;
			this.modeSelection = modeSelection;
		}

		public Point getFirstIndex() {
			return firstIndex;
		}

		public Point getLastIndex() {
			return lastIndex;
		}

		public int getSelectionMode() {
			return modeSelection;
		}

	}

}
