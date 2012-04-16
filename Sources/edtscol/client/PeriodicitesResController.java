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
package edtscol.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.Reservation;

import com.webobjects.eoapplication.EOController;
import com.webobjects.eoapplication.EOSimpleWindowController;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.utilities.GenericListHandler;

public class PeriodicitesResController extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3196023120548273231L;
	private Container cont;
	private JButton bValider, bFermer;
	private MainInterface owner;
	private Reservation resa;
	private GenericListHandler listSemaines;
	private int x, y;
	private NSMutableArray<NSTimestamp> semaines;

	public PeriodicitesResController(MainInterface owner, int x, int y, Reservation resa) {
		super((Frame) ((EOSimpleWindowController) ((EOController) owner).supercontroller()).window(), true);
		setTitle("Choisir les semaines :");
		this.x = x;
		this.y = y;
		this.owner = owner;
		this.resa = resa;
		initUI();
		initData();
	}

	private void initData() {

		NSArray<NSTimestamp> debuts = (NSArray<NSTimestamp>) resa.valueForKeyPath(Reservation.PERIODICITES_KEY + "." + Periodicite.DATE_DEB_KEY);
		NSMutableArray<Semaine> objets = new NSMutableArray<PeriodicitesResController.Semaine>();
		for (int i = 0; i < debuts.count(); i++) {
			objets.addObject(new Semaine(debuts.objectAtIndex(i)));
		}
		listSemaines.setObjects(objets);
	}

	private void initUI() {
		setSize(350, 220);
		Window ownerWindow = ((EOSimpleWindowController) ((EOController) owner).supercontroller()).window();
		int xMain = ownerWindow.getBounds().x;
		int yMain = ownerWindow.getBounds().y;
		setLocation((xMain + x + 30), yMain + y);
		setResizable(false);
		cont = getContentPane();
		cont.setLayout(new BorderLayout(3, 3));
		JPanel pan = new JPanel(new BorderLayout(3, 3));
		pan.setPreferredSize(new Dimension(350, 220));
		listSemaines = new GenericListHandler();
		pan.add(listSemaines, BorderLayout.CENTER);
		cont.add(pan, BorderLayout.CENTER);

		bValider = new JButton(new GroupeAction("Valider"));
		bFermer = new JButton(new GroupeAction("Fermer"));
		bValider.setPreferredSize(new Dimension(70, 20));
		bFermer.setPreferredSize(new Dimension(70, 20));
		JPanel panB = new JPanel(new GridLayout(1, 2));
		panB.add(bValider);
		panB.add(bFermer);
		cont.add(panB, BorderLayout.SOUTH);

		pack();
	}

	private void valider() {
		semaines = new NSMutableArray<NSTimestamp>();
		NSArray<Semaine> selectionSems = listSemaines.getSelectedItems();
		for (int i = 0; i < selectionSems.count(); i++) {
			Semaine sem = selectionSems.objectAtIndex(i);
			semaines.addObject(sem.dateDeb());
			semaines.addObject(sem.dateFin());
		}
	}

	public NSArray<NSTimestamp> getSelectedSemaines() {
		return semaines;
	}

	public void annuler() {
		semaines = new NSMutableArray<NSTimestamp>();
	}

	private void fermer() {
		setVisible(false);
	}

	/** representation d'une semaine pour l'affichage et le calcul du debut et de fin de semaine */
	private class Semaine {

		private int noSemaine;
		private NSTimestamp debut, fin;
		private final String format = "%d/%m/%Y";

		public Semaine(NSTimestamp date) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setMinimalDaysInFirstWeek(4);
			cal.setTime(date);
			noSemaine = cal.get(Calendar.WEEK_OF_YEAR);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			debut = new NSTimestamp(cal.getTime());
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			fin = new NSTimestamp(cal.getTime());
		}

		public NSTimestamp dateDeb() {
			return debut;
		}

		public NSTimestamp dateFin() {
			return fin;
		}

		public int noSemaine() {
			return noSemaine;
		}

		public String toString() {
			return "Semaine : " + noSemaine + " [" + FormatHandler.dateToStr(debut, format) + "-" + FormatHandler.dateToStr(fin, format) + "]";
		}

	}

	/** class Action des boutons */
	protected class GroupeAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9195394572083777918L;

		public GroupeAction(String title) {
			super(title);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bValider) {
				valider();
				fermer();
			}
			else {
				annuler();
			}
		}
	}

}
