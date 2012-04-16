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
package fr.univlr.utilities;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabelJour extends JLabel implements MouseListener {

	private static final long serialVersionUID = 5288827643137987074L;

	/**
	 * Creates the LabelJour
	 * 
	 * @param day
	 *            The Day of the Month for this Label
	 * @param monthPanel
	 *            The MonthPanel using this
	 */
	public LabelJour(int day, PanelMois monthPanel) {
		super(Integer.toString(day));

		_day = day;
		_monthPanel = monthPanel;

		setHorizontalAlignment(SwingConstants.RIGHT);
		addMouseListener(this);
	}

	/**
	 * Sets the selction of this LabelJour
	 * 
	 * @param selected
	 *            Selected ?
	 */
	public void setSelected(boolean selected) {
		_selected = selected;

		if (_selected) {
			setOpaque(true);
			setBackground(PanelMois.SELECTED_BACKGROUND_COLOR);
			setForeground(PanelMois.SELECTED_FONT_COLOR);
		}
		else {
			setOpaque(false);
			setForeground(PanelMois.FONT_COLOR);
		}
	}

	/**
	 * Is this LabelJour selected ?
	 * 
	 * @return Selected
	 */
	public boolean isSelected() {
		return _selected;
	}

	/**
	 * Listens to MouseClick-Events and updates the State
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		_monthPanel.setSelectedDayOfMonth(_day);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * The MonthPanel for this LabelJour
	 */
	private PanelMois _monthPanel;
	/**
	 * The Day this LabelJour is representing
	 */
	private int _day;
	/**
	 * Is this LabelJour selected
	 */
	private boolean _selected = false;
}