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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarPanel extends JPanel implements ItemListener, ChangeListener {

	private static final long serialVersionUID = -1922284374308812535L;

	/**
	 * Creates a Calendar using the current Date and current Local settings.
	 */
	public CalendarPanel() {
		createGUI(Calendar.getInstance(), Locale.getDefault(), DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()));
	}

	/**
	 * Creates a Calendar using the cal-Date and current Locale Settings. It doesn't use the Locale in the Calendar-Object !
	 * 
	 * @param cal
	 *            Calendar to use
	 */
	public CalendarPanel(Calendar cal) {
		createGUI(cal, Locale.getDefault(), DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()));
	}

	/**
	 * Creates a Calendar using the current Date and the given Locale Settings.
	 * 
	 * @param locale
	 *            Locale to use
	 */
	public CalendarPanel(Locale locale) {
		createGUI(Calendar.getInstance(locale), locale, DateFormat.getDateInstance(DateFormat.MEDIUM, locale));
	}

	/**
	 * Creates a Calender using the given Date and Locale
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param locale
	 *            Locale to use
	 */
	public CalendarPanel(Calendar cal, Locale locale) {
		createGUI(cal, locale, DateFormat.getDateInstance(DateFormat.MEDIUM, locale));
	}

	/**
	 * Creates a Calender using the given Calendar, Locale and DateFormat.
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param locale
	 *            Locale to use
	 * @param dateFormat
	 *            DateFormat for the ComboBox
	 */
	public CalendarPanel(Calendar cal, Locale locale, DateFormat dateFormat) {
		createGUI(cal, locale, dateFormat);
	}

	/**
	 * Creates the GUI
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param locale
	 *            Locale to use
	 * @param dateFormat
	 *            DateFormat to use
	 */
	private void createGUI(Calendar cal, Locale locale, DateFormat dateFormat) {
		_locale = locale;
		_cal = Calendar.getInstance(locale);
		_cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		_cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		_cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		_format = dateFormat;

		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		_month = createMonth();
		_month.addItemListener(this);
		add(_month, c);

		_year = createYear();
		_year.addItemListener(this);

		c.gridwidth = GridBagConstraints.REMAINDER;

		add(_year, c);

		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 1);

		_monthPanel = new PanelMois(_cal, _locale);
		_monthPanel.addChangeListener(this);

		add(_monthPanel, c);
	}

	/**
	 * Creates a JComboBox filled with year values (1900-2100)
	 * 
	 * @return JComboBox with Years
	 */
	private JComboBox createYear() {
		JComboBox year = new JComboBox();

		for (int i = 1900; i <= 2100; i++) {
			year.addItem("" + i);
		}

		year.setSelectedIndex(_cal.get(Calendar.YEAR) - 1900);

		return year;
	}

	/**
	 * Creates a JComboBox filled with Months. The name for the Month is created using the locale given in the constructor.
	 * 
	 * @return JComboBox filled with Months
	 */
	private JComboBox createMonth() {
		JComboBox month = new JComboBox();

		SimpleDateFormat format = new SimpleDateFormat("MMMMM", _locale);

		Calendar currentCal = Calendar.getInstance();

		for (int i = 0; i < 12; i++) {
			currentCal.set(Calendar.DAY_OF_MONTH, 15);
			currentCal.set(Calendar.MONTH, i);
			currentCal.set(Calendar.YEAR, _cal.get(Calendar.YEAR));
			String myString = format.format(currentCal.getTime());
			month.addItem(myString);
		}

		month.setSelectedIndex(_cal.get(Calendar.MONTH));

		return month;
	}

	/**
	 * Updates the Calendar
	 */
	private void updateCalendar() {
		_cal.set(Calendar.MONTH, _month.getSelectedIndex());
		_cal.set(Calendar.YEAR, _year.getSelectedIndex() + 1900);
		_cal.set(Calendar.DAY_OF_MONTH, _monthPanel.getSelectedDayOfMonth());

		_monthPanel.setCalendar(_cal);
	}

	/**
	 * Returns the current selected Date as Calender-Object
	 * 
	 * @return current selected Date
	 */
	public Calendar getCalendar() {
		updateCalendar();
		return _cal;
	}

	/**
	 * Sets the current selected Date
	 * 
	 * @param cal
	 *            the Date to select
	 */
	public void setCalendar(Calendar cal) {
		_cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		_cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		_cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));

		_month.setSelectedIndex(_cal.get(Calendar.MONTH));
		_year.setSelectedIndex(_cal.get(Calendar.YEAR) - 1900);

		_monthPanel.setCalendar(_cal);
	}

	/**
	 * Returns a String-Representation of this Calendar using the DateFormat given in the Constructor
	 * 
	 * @return String-Representation of this Calendar
	 */
	public String toString() {
		updateCalendar();
		return _format.format(_cal.getTime());
	}

	/**
	 * Returns a String-Representation of this Calendar using the given DateFormat
	 * 
	 * @param format
	 *            DateFormat to use
	 * @return String-Representation of this Calendar
	 */
	public String toString(DateFormat format) {
		updateCalendar();
		return format.format(_cal.getTime());
	}

	/**
	 * Recieves StateChanges from the ComboBoxes for Month/Year and updates the Calendar
	 * 
	 * @param e
	 *            ItemEvent
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		updateCalendar();
		if (_listenermode == FIRE_EVERYTIME) {
			fireChangeEvent(_month);
		}
	}

	/**
	 * Recieves StateChanges from the MonthPanel and updates the Calendar
	 * 
	 * @param e
	 *            ChangeEvent
	 * 
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		updateCalendar();
		fireChangeEvent(this);
	}

	/**
	 * Adds a Changelistener to this CalendarPanel.
	 * 
	 * It will be called every time the selected Date changes.
	 * 
	 * @param listener
	 *            ChangeListener
	 */
	public void addChangeListener(ChangeListener listener) {
		_changeListener.add(listener);
	}

	/**
	 * Removes a ChangeListener from this CalendarPanel
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
	protected void fireChangeEvent(javax.swing.JComponent who) {
		if (!_fireingChangeEvent) {
			_fireingChangeEvent = true;
			ChangeEvent event = new ChangeEvent(who);

			for (int i = 0; i < _changeListener.size(); i++) {
				((ChangeListener) _changeListener.get(i)).stateChanged(event);
			}

			_fireingChangeEvent = false;
		}

	}

	/**
	 * Sets the Mode when the FireChangeEvent is called. Use FIRE_EVERYTIME or FIRE_DAYCHANGES as parameter.
	 * 
	 * @param mode
	 *            The Mode of the Listener
	 */
	public void setListenerModus(int mode) {
		_listenermode = mode;
	}

	/**
	 * Enables/Disables the ComboBox
	 * 
	 * @param enabled
	 *            Enabled ?
	 */
	public void setEnabled(boolean enabled) {
		_month.setEnabled(enabled);
		_year.setEnabled(enabled);
		_monthPanel.setEnabled(enabled);
	}

	/**
	 * Fires everytime the Date changes
	 */
	public static final int FIRE_EVERYTIME = 1;
	/**
	 * Fires only if the Day changes
	 */
	public static final int FIRE_DAYCHANGES = 2;

	/**
	 * When does FireEvent() fire events? Every time there is an update or only if the Day was changed?
	 */
	private int _listenermode = FIRE_EVERYTIME;

	/**
	 * The current Date
	 */
	private Calendar _cal;
	/**
	 * The DateFormat for Output
	 */
	private DateFormat _format;
	/**
	 * The Locale to use
	 */
	private Locale _locale;

	/**
	 * The JComboBox for Month-Selection
	 */
	private JComboBox _month;
	/**
	 * The JComboBox for Year-Selection
	 */
	private JComboBox _year;
	/**
	 * The PanelMois for Day-Selection
	 */
	private PanelMois _monthPanel;

	/**
	 * The list of ChangeListeners
	 */
	private ArrayList _changeListener = new ArrayList();
	/**
	 * Currently firing an ChangeEvent?
	 */
	private boolean _fireingChangeEvent = false;

}