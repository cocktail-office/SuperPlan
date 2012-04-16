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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import edtscol.client.PlanningView;

public class JoursView extends JPanel implements Scrollable, MouseListener {

	private static final long serialVersionUID = -3581029870797694365L;
	private LinkedList liste;
	private Hashtable laSemaine;
	private GregorianCalendar nc;
	String aujourdhui;
	int unJour = -1;
	private PlanningView planningView;

	/**
	 * Constructeur principal.
	 */
	public JoursView(String tzString) {
		super();
		this.setPreferredSize(Parametres.TAILLE_JOURS_VIEW);
		this.setBackground(Parametres.COULEUR_FOND);
		this.addMouseListener(this);
		TimeZone zone_paris = TimeZone.getTimeZone(tzString);
		Locale loc_france = Locale.FRANCE;
		nc = new GregorianCalendar(zone_paris, loc_france);

		nc.setMinimalDaysInFirstWeek(4);
		nc.setFirstDayOfWeek(Calendar.MONDAY);

		aujourdhui = Parametres.formatDate.format(new Date());
		initialiser();

	}

	/**
	 * @param layout
	 *            le layout manager.
	 * @param isDoubleBuffered
	 *            si double buffer ou non.
	 */
	public JoursView(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	/**
	 * Affecte la date au calendrier.
	 * 
	 * @param date
	 */
	public void setTime(Date date) {
		nc.setTime(date);
		if (unSeulJour()) {
			this.initUnJour();
		}
		else {
			this.initialiser();
		}
	}

	private void initialiser() {

		liste = new LinkedList();
		Calendrier cal = new Calendrier();
		cal.affecterDates(nc);
		laSemaine = cal.getSemaine();
		ArrayList lesJours = cal.getJours();
		int nj = 0;
		String dateDuJour;
		for (int i = Parametres.HAUTEUR_DISP_HEURES; i < Parametres.HAUTEUR_EDT; i += Parametres.HAUTEUR_JOUR) {
			ArrayList date = new ArrayList();
			String leJour = (String) lesJours.get(nj);

			date.add(leJour);
			dateDuJour = (String) laSemaine.get(leJour);
			date.add(dateDuJour);
			if (dateDuJour.equals(aujourdhui)) {
				liste.add(new TexteEDT(0, i, Parametres.HAUTEUR_JOUR, Parametres.TAILLE_HEURE * 2, date, Parametres.COLOR_JOUR_SELECTIONNE));
			}
			else {
				liste.add(new TexteEDT(0, i, Parametres.HAUTEUR_JOUR, Parametres.TAILLE_HEURE * 2, date, Parametres.COLOR_ZONE_HEURE));
			}
			nj++;
		}
		this.repaint();
	}

	private void initUnJour() {

		liste = new LinkedList();
		Calendrier cal = new Calendrier();
		cal.affecterDates(nc);
		laSemaine = cal.getSemaine();

		ArrayList date = new ArrayList();

		DateFormatSymbols dfs_fr = new DateFormatSymbols(Locale.FRANCE);
		String[] jours = dfs_fr.getWeekdays();

		String leJour = jours[unJour + nc.getFirstDayOfWeek()].toUpperCase();

		date.add(leJour);
		String dateDuJour = (String) laSemaine.get(leJour);
		date.add(dateDuJour);

		if (nc.equals(aujourdhui)) {
			liste.add(new TexteEDT(0, Parametres.HAUTEUR_DISP_HEURES, Parametres.TAILLE_DU_ZOOM.height, Parametres.TAILLE_HEURE * 2, date,
					Parametres.COLOR_JOUR_SELECTIONNE));
		}
		else {
			liste.add(new TexteEDT(0, Parametres.HAUTEUR_DISP_HEURES, Parametres.TAILLE_DU_ZOOM.height, Parametres.TAILLE_HEURE * 2, date,
					Parametres.COLOR_ZONE_HEURE));
		}

		this.repaint();
	}

	/*
	 * (redefinition de paint pour ecrire les heures sur le composant.)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// passage en graphique 2D.
		Graphics2D graph = (Graphics2D) g;

		for (int i = 0; i < liste.size(); i++) {
			((TexteEDT) liste.get(i)).draw(graph);
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

	protected void detaillerJour(int j) {
		if (!unSeulJour()) {
			unJour = j;
			this.initUnJour();
		}
		else {
			unJour = -1;
			this.initialiser();
		}
		planningView.refresh();
	}

	// debut implementation MouseListener.
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		int y;
		int jour = 0;

		if (e.getClickCount() == 2) {
			y = e.getY();
			for (int pos = Parametres.HAUTEUR_DISP_HEURES; pos < Parametres.HAUTEUR_EDT; pos += Parametres.HAUTEUR_JOUR) {

				if (Parametres.dansFourchette(y, pos, pos + Parametres.HAUTEUR_JOUR)) {
					detaillerJour(jour);
					break;
				}
				jour++;
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public int getUnJour() {
		return unJour;
	}

	public boolean unSeulJour() {
		return unJour != -1;
	}

	public void setPlanningView(PlanningView planningView) {
		this.planningView = planningView;
	}

}
