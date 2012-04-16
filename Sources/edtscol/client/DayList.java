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

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class DayList extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -430218059846490930L;
	private static final String[] dataList = { "lun", "mar", "mer", "jeu", "ven", "sam", "dim" };
	private JList list;

	public DayList() {
		super();
		list = new JList(dataList);
		list.setPreferredSize(this.getPreferredSize());
		list.setVisibleRowCount(1);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setViewportView(list);
		list.setSelectedIndex(0);
	}

	public void setEnabled(boolean enabled) {
		list.setEnabled(enabled);
	}

	public void setMultipleSelectionAllowed(boolean multipleSelectionAllowed) {
		if (multipleSelectionAllowed) {
			list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		else {
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}

	/** retourne l'element Calendar du jour selectionne */
	public int getSelectedDay() {
		int index = list.getSelectedIndex();
		int retour = 0;
		switch (index) {
		case 0:
			retour = Calendar.MONDAY;
			break;
		case 1:
			retour = Calendar.TUESDAY;
			break;
		case 2:
			retour = Calendar.WEDNESDAY;
			break;
		case 3:
			retour = Calendar.THURSDAY;
			break;
		case 4:
			retour = Calendar.FRIDAY;
			break;
		case 5:
			retour = Calendar.SATURDAY;
			break;
		case 6:
			retour = Calendar.SUNDAY;
			break;
		}
		return retour;
	}

	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}

	/** selectionne le jour selectionne */
	public void setSelectedIndex(int index) {
		list.setSelectedIndex(index);
	}

	/** meme chose mais en static, retourne l'element Calendar du jour d'index passe en parametre */
	public static int getSelectedDay(int index) {
		int retour = 0;
		switch (index) {
		case 0:
			retour = Calendar.MONDAY;
			break;
		case 1:
			retour = Calendar.TUESDAY;
			break;
		case 2:
			retour = Calendar.WEDNESDAY;
			break;
		case 3:
			retour = Calendar.THURSDAY;
			break;
		case 4:
			retour = Calendar.FRIDAY;
			break;
		case 5:
			retour = Calendar.SATURDAY;
			break;
		case 6:
			retour = Calendar.SUNDAY;
			break;
		}
		return retour;
	}

	// Multi-selection.
	public ArrayList<Integer> getSelectedDays() {
		ArrayList<Integer> selectedDays = new ArrayList<Integer>();
		int[] indices = list.getSelectedIndices();

		for (int i = 0; i < indices.length; i++) {
			int currentIndex = indices[i];
			selectedDays.add(new Integer(DayList.getSelectedDay(currentIndex)));
		}
		return selectedDays;
	}

}
