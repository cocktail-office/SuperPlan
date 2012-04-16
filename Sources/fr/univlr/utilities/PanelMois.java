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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelMois extends JPanel {

	private static final long serialVersionUID = 6231376460662974245L;
	/**
	 * Color for the Background
	 */
	public static final Color BACKGROUND_COLOR = UIManager.getColor("TextField.background");
	/**
	 * Font Color for the not-selected Day
	 */
	public static final Color FONT_COLOR = UIManager.getColor("TextField.foreground");
	/**
	 * Background Color for the selected Day
	 */
	public static final Color SELECTED_BACKGROUND_COLOR = UIManager.getColor("TextField.selectionBackground");
	/**
	 * Font Color for the selected Day
	 */
	public static final Color SELECTED_FONT_COLOR = UIManager.getColor("TextField.selectionForeground");
	/**
	 * Background Color for the Header
	 */
	public static final Color HEADER_BACKGROUND_COLOR = UIManager.getColor("TextField.inactiveForeground");
	/**
	 * Font Color for the Header
	 */
	public static final Color HEADER_FONT_COLOR = UIManager.getColor("TextField.inactiveBackground");

	/**
	 * Creates a PanelMois using the current Date and current Local settings.
	 */
	public PanelMois() {
		init(Calendar.getInstance(), Locale.getDefault());
	}

	/**
	 * Creates a PanelMois using the cal-Date and current Locale Settings. It doesn't use the Locale in the Calendar-Object !
	 * 
	 * @param cal
	 *            Calendar to use
	 */
	public PanelMois(Calendar cal) {
		init(cal, Locale.getDefault());
	}

	/**
	 * Creates a PanelMois using the current Date and the given Locale Settings.
	 * 
	 * @param locale
	 *            Locale to use
	 */
	public PanelMois(Locale locale) {
		init(Calendar.getInstance(locale), locale);
	}

	/**
	 * Creates a PanelMois using the given Date and Locale
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param locale
	 *            Locale to use
	 */
	public PanelMois(Calendar cal, Locale locale) {
		init(cal, locale);
	}

	/**
	 * Initialize the PanelMois
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param loc
	 *            Locale to use
	 */
	private void init(Calendar cal, Locale loc) {
		_cal = Calendar.getInstance(loc);
		_cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		_cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		_cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		_locale = loc;
		createGUI();
	}

	/**
	 * Creates the GUI
	 */
	private void createGUI() {
		setLayout(new BorderLayout());
		add(createHeader(), BorderLayout.NORTH);
		add(createTable(), BorderLayout.CENTER);
	}

	/**
	 * Creates a JPanel containing the Header
	 * 
	 * @return Header
	 */
	private JPanel createHeader() {
		JPanel header = new JPanel();
		header.setLayout(new GridLayout(1, 7, 1, 1));
		header.setBackground(HEADER_BACKGROUND_COLOR);

		SimpleDateFormat format = new SimpleDateFormat("E", _locale);
		Calendar cal = (Calendar) _cal.clone();

		char[] letters = new char[7];

		for (int i = 0; i < 7; i++) {
			letters[cal.get(Calendar.DAY_OF_WEEK) - 1] = format.format(cal.getTime()).charAt(0);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		}

		int pos = cal.getFirstDayOfWeek() - 1;

		for (int i = 0; i < 7; i++) {
			JLabel empty = new JLabel("" + letters[pos]);

			pos++;
			if (pos > 6) {
				pos = 0;
			}

			empty.setHorizontalAlignment(SwingConstants.CENTER);
			empty.setForeground(HEADER_FONT_COLOR);
			header.add(empty);
		}
		return header;
	}

	/**
	 * Creates a JPanel containing the Day-Table
	 * 
	 * @return Day Table
	 */
	private JPanel createTable() {
		_days = new ArrayList();

		JPanel table = new JPanel();
		table.setBackground(BACKGROUND_COLOR);

		table.setLayout(new GridLayout(6, 7, 1, 1));

		int position = 0;

		Calendar cal = (Calendar) _cal.clone();

		cal.set(Calendar.DAY_OF_MONTH, 1);

		int month = cal.get(Calendar.MONTH);

		int firstDay = cal.get(Calendar.DAY_OF_WEEK);

		if (firstDay == 0) {
			firstDay--;
		}
		else {
			firstDay -= cal.getFirstDayOfWeek();
		}

		if (firstDay < 0) {
			firstDay += 7;
		}

		while (position < firstDay) {
			JLabel empty = new JLabel();
			table.add(empty);
			position++;
		}

		int curDay = _cal.get(Calendar.DAY_OF_MONTH);

		while ((position < 42) && (cal.get(Calendar.MONTH) == month)) {
			LabelJour day = new LabelJour(cal.get(Calendar.DAY_OF_MONTH), this);
			table.add(day);

			_days.add(day);

			if (curDay == cal.get(Calendar.DAY_OF_MONTH)) {
				day.setSelected(true);
			}

			position++;
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		}

		while (position < 42) {
			JLabel empty = new JLabel();
			table.add(empty);
			position++;
		}

		return table;
	}

	/**
	 * Sets the current Date using a Calendar-Object
	 * 
	 * @param cal
	 *            Calendar to show
	 */
	public void setCalendar(Calendar cal) {
		_cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		_cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		_cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));

		removeAll();
		createGUI();
		updateUI();
		setBackground(BACKGROUND_COLOR);
	}

	/**
	 * Returns the current Date using a Calendar-Object
	 * 
	 * @return current Date
	 */
	public Calendar getCalendar() {
		return _cal;
	}

	/**
	 * Sets the selected Day of Month
	 * 
	 * @param day
	 *            selected Day
	 */
	public void setSelectedDayOfMonth(int day) {

		if (_enabled && (day > 0) && (day <= _days.size())) {
			int oldday = _cal.get(Calendar.DAY_OF_MONTH);

			LabelJour LabelJour = (LabelJour) _days.get(oldday - 1);
			LabelJour.setSelected(false);

			_cal.set(Calendar.DAY_OF_MONTH, day);

			LabelJour = (LabelJour) _days.get(day - 1);
			LabelJour.setSelected(true);
			updateUI();

			fireChangeEvent();
		}

	}

	/**
	 * Gets the selected Day of the Month
	 * 
	 * @return selected Day
	 */
	public int getSelectedDayOfMonth() {
		return _cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Adds a Changelistener to this JCalendarComboBox.
	 * 
	 * It will be called everytime the ComboBox is closed and the Date was changed
	 * 
	 * @param listener
	 *            ChangeListener
	 */
	public void addChangeListener(ChangeListener listener) {
		_changeListener.add(listener);
	}

	/**
	 * Removes a ChangeListener from this JCalendarComboBox
	 * 
	 * @param listener
	 *            listener to remove
	 */
	public void removeChangeListener(ChangeListener listener) {
		_changeListener.remove(listener);
	}

	/**
	 * Gets all ChangeListeners
	 * 
	 * @return all ChangeListeners
	 */
	public ChangeListener[] getChangeListener() {
		return (ChangeListener[]) _changeListener.toArray();
	}

	/**
	 * Fires the ChangeEvent
	 */
	protected void fireChangeEvent() {
		if (!_fireingChangeEvent) {
			_fireingChangeEvent = true;
			ChangeEvent event = new ChangeEvent(this);

			for (int i = 0; i < _changeListener.size(); i++) {
				((ChangeListener) _changeListener.get(i)).stateChanged(event);
			}

			_fireingChangeEvent = false;
		}

	}

	/**
	 * Enables/Disables the ComboBox
	 * 
	 * @param enabled
	 *            Enabled ?
	 */
	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	/**
	 * Component enabled ?
	 */
	private boolean _enabled = true;

	/**
	 * The current Calendar
	 */
	private Calendar _cal;
	/**
	 * The locale to use
	 */
	private Locale _locale;
	/**
	 * All Day-Buttons in this MonthPanel
	 */
	private ArrayList _days;

	/**
	 * The list of ChangeListeners
	 */
	private ArrayList _changeListener = new ArrayList();
	/**
	 * Currently firing an ChangeEvent?
	 */
	private boolean _fireingChangeEvent = false;
}