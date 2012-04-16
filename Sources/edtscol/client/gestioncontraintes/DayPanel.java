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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.cocktail.superplan.client.metier.ContrainteJour;
import org.cocktail.superplan.client.metier.ContrainteSemaine;

import com.webobjects.foundation.NSArray;

public class DayPanel extends JPanel {
	private static final int DEFAULT_BUTTON_WIDTH = 24;
	private static final int DEFAULT_BUTTON_HEIGHT = 24;
	private static final int DEFAULT_BUTTON_FONT_SIZE = 12;

	private static final int DEFAULT_GAP = 2;

	private static final int DEFAULT_BOX_AXIS = BoxLayout.PAGE_AXIS;

	private DayToggleButton[] dayButtons = null;
	private ActionListener actionListenerOnClick;
	private boolean isMultipleSelectEnabled = true;

	private int buttonWidth, buttonHeight, buttonFontSize;
	private int gap;
	private int boxAxis;

	public DayPanel(ActionListener actionListenerOnClick) {
		this.actionListenerOnClick = actionListenerOnClick;
		this.boxAxis = DEFAULT_BOX_AXIS;
		this.buttonWidth = DEFAULT_BUTTON_WIDTH;
		this.buttonHeight = DEFAULT_BUTTON_HEIGHT;
		this.buttonFontSize = DEFAULT_BUTTON_FONT_SIZE;
		this.gap = DEFAULT_GAP;
		init();
	}

	/**
	 * @param actionListenerOnClick
	 *            the action to perform when a button click occurs
	 * @param boxAxis
	 *            the axis for displaying this panel: BoxLayout.PAGE_AXIS (default) or BoxLayout.LINE_AXIS
	 * @param buttonWidth
	 *            the width in pixels of a day button
	 * @param buttonHeight
	 *            the height in pixels of a day button
	 * @param buttonFontSize
	 *            the font size for displaying first letter of the day in each day button
	 * @param gap
	 *            the gap in pixels between each day button
	 */
	public DayPanel(ActionListener actionListenerOnClick, int boxAxis, int buttonWidth, int buttonHeight, int buttonFontSize, int gap) {
		this.actionListenerOnClick = actionListenerOnClick;
		this.boxAxis = boxAxis;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		this.buttonFontSize = buttonFontSize;
		this.gap = gap;
		init();
	}

	private void init() {
		Box dayButtons;
		if (boxAxis == BoxLayout.PAGE_AXIS) {
			dayButtons = Box.createVerticalBox();
		}
		else {
			dayButtons = Box.createHorizontalBox();
		}
		for (int i = 0; i < 7; i++) {
			dayButtons.add(dayButtons()[i]);
			if (boxAxis == BoxLayout.PAGE_AXIS) {
				dayButtons.add(Box.createRigidArea(new Dimension(1, gap)));
			}
			else {
				dayButtons.add(Box.createRigidArea(new Dimension(gap, 1)));
			}
		}
		this.setLayout(new BorderLayout());
		this.add(dayButtons, BorderLayout.CENTER);
	}

	public void update(GregorianCalendar date, ContrainteSemaine ctrl) {
		for (int i = 0; i < dayButtons().length; i++) {
			dayButtons()[i].setContrainteJour(null);
			dayButtons()[i].setSelected(false);
		}
		GregorianCalendar gc = null;
		if (date != null) {
			gc = (GregorianCalendar) date.clone();
		}
		else {
			if (ctrl != null) {
				gc = new GregorianCalendar();
				gc.setTime(ctrl.ctsDate());
			}
		}
		if (gc != null) {
			gc.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
			for (int i = 0; i < dayButtons().length; i++) {
				dayButtons()[i].setDate(gc);
				gc.add(GregorianCalendar.DATE, 1);
			}
		}
		if (ctrl != null) {
			updateValues(ctrl.contrainteJours());
		}
		this.updateUI();
	}

	public void setSelected(ContrainteJour ctrl, boolean selected, boolean enabled) {
		if (ctrl == null) {
			clearSelection();
		}
		else {
			DayToggleButton b = dayButtonFor(ctrl.ctjNoJour());
			if (b != null) {
				boolean wasSelected = b.isSelected();
				if (selected && isMultipleSelectEnabled == false) {
					clearSelection();
				}
				b.setContrainteJour(selected ? ctrl : null);
				b.setSelected(selected);
				b.setEnabled(enabled);
				if (wasSelected != selected) {
					actionListenerOnClick.actionPerformed(new ActionEvent(b, Event.ACTION_EVENT, b.getText()));
				}
			}
		}
	}

	private void updateValues(NSArray ctrls) {
		if (ctrls == null) {
			return;
		}
		for (int i = 0; i < ctrls.count(); i++) {
			ContrainteJour ctrl = (ContrainteJour) ctrls.objectAtIndex(i);
			Integer no = ctrl.ctjNoJour();
			DayToggleButton b = dayButtonFor(no.intValue());
			b.setContrainteJour(ctrl);
			b.setSelected(true);
		}
	}

	private void clearSelection() {
		for (int i = 0; i < dayButtons().length; i++) {
			dayButtons()[i].setSelected(false);
		}
	}

	public DayToggleButton dayButtonFor(int day) {
		return dayButtons()[day == 1 ? 6 : day - 2];
	}

	public DayToggleButton[] dayButtons() {
		if (dayButtons == null) {
			dayButtons = new DayToggleButton[7];
			ActionListener myAL = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					onClick(evt);
				}
			};
			for (int i = 0; i < 7; i++) {
				DayToggleButton b = new DayToggleButton(buttonWidth, buttonHeight, buttonFontSize);
				b.addActionListener(myAL);
				dayButtons[i] = b;
			}
		}
		dayButtons[0].setText("L");
		dayButtons[1].setText("M");
		dayButtons[2].setText("M");
		dayButtons[3].setText("J");
		dayButtons[4].setText("V");
		dayButtons[5].setText("S");
		dayButtons[6].setText("D");
		return dayButtons;
	}

	private void onClick(ActionEvent evt) {
		if (isMultipleSelectEnabled == false) {
			if (evt.getSource() != null) {
				DayToggleButton b = (DayToggleButton) evt.getSource();
				if (b.isSelected()) {
					clearSelection();
					b.setSelected(true);
				}
			}
		}
		actionListenerOnClick.actionPerformed(evt);
	}

	public void setEnabled(boolean enabled) {
		for (int i = 0; i < dayButtons().length; i++) {
			dayButtons()[i].setEnabled(enabled);
		}
	}

	public boolean isMultipleSelectEnabled() {
		return isMultipleSelectEnabled;
	}

	public void setMultipleSelectEnabled(boolean isMultipleSelectEnabled) {
		this.isMultipleSelectEnabled = isMultipleSelectEnabled;
	}

}
