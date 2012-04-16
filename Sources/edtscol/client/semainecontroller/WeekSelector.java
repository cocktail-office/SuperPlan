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
package edtscol.client.semainecontroller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.webobjects.foundation.NSArray;

public class WeekSelector extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2312541075643540853L;
	private ArrayList listWeeks;
	private IWeekSelectionObserver semaineController;
	private Number anneeScolaire;

	/**
	 * 
	 */
	public WeekSelector(IWeekSelectionObserver semaineController, Number anneeScolaire) {
		super();
		this.semaineController = semaineController;
		this.anneeScolaire = anneeScolaire;
		listWeeks = new ArrayList();
		initUI();
	}

	private void initUI() {

		setSize(290, 170);
		setPreferredSize(new Dimension(290, 170));
		setLayout(new GridLayout(6, 10));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		NSArray<Integer> semAnnee = FCalendarHandler.listeSemainesForAnneeScolaire(anneeScolaire);
		for (int i = 0; i < semAnnee.count(); i++) {
			WeekElement we = new WeekElement(this, semAnnee.objectAtIndex(i).intValue());
			listWeeks.add(we);
			add(we);
		}
	}

	public void deselectAll() {
		for (int j = 0; j < listWeeks.size(); j++) {
			((WeekElement) listWeeks.get(j)).setSelected(false);
		}
	}

	public void setSelectedWeeks(ArrayList elements) {
		deselectAll();

		for (int i = 0; i < elements.size(); i++) {
			int currentWeek = ((Integer) elements.get(i)).intValue();

			for (int j = 0; j < listWeeks.size(); j++) {
				WeekElement we = (WeekElement) listWeeks.get(j);
				if (we.getValue() == currentWeek) {
					we.setSelected(true);
					break;
				}
			}
		}
	}

	public ArrayList getSelectedWeeks() {
		ArrayList list = new ArrayList();
		for (int j = 0; j < listWeeks.size(); j++) {
			WeekElement currentWeek = (WeekElement) listWeeks.get(j);
			if (currentWeek.isSelected()) {
				list.add(new Integer(currentWeek.getValue()));
			}
		}
		return list;
	}

	public boolean ajouterSemaine(Integer semaine) {
		return semaineController.addWeek(semaine);
	}

	public boolean retirerSemaine(Integer semaine) {
		return semaineController.removeWeek(semaine);
	}

}
