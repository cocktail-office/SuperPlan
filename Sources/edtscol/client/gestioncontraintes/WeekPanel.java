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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.cocktail.superplan.client.metier.ContrainteSemaine;
import org.cocktail.superplan.client.metier.FormationAnnee;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

public class WeekPanel extends JPanel {
	private static final int DEFAULT_BUTTON_WIDTH = 24;
	private static final int DEFAULT_BUTTON_HEIGHT = 16;
	private static final int DEFAULT_BUTTON_FONT_SIZE = 11;

	private static final int DEFAULT_HGAP = 2;
	private static final int DEFAULT_VGAP = 2;

	private WeekToggleButton[] weekButtons = null;
	private boolean useAnneeCivile = false;
	private ActionListener actionListenerOnLeftClic = null;
	private ActionListener actionListenerOnRightClic = null;
	private boolean isMultipleSelectEnabled = false;

	private int buttonWidth, buttonHeight, buttonFontSize;
	private int hgap, vgap;

	public WeekPanel(boolean useAnneeCivile, ActionListener actionListenerOnLeftClic, ActionListener actionListenerOnRightClic) {
		this.useAnneeCivile = useAnneeCivile;
		this.actionListenerOnLeftClic = actionListenerOnLeftClic;
		this.actionListenerOnRightClic = actionListenerOnRightClic;
		this.buttonWidth = DEFAULT_BUTTON_WIDTH;
		this.buttonHeight = DEFAULT_BUTTON_HEIGHT;
		this.buttonFontSize = DEFAULT_BUTTON_FONT_SIZE;
		this.hgap = DEFAULT_HGAP;
		this.vgap = DEFAULT_VGAP;
		init();
	}

	public WeekPanel(boolean useAnneeCivile, ActionListener actionListenerOnLeftClic, ActionListener actionListenerOnRightClic, int buttonWidth,
			int buttonHeight, int buttonFontSize, int hgap, int vgap) {
		this.useAnneeCivile = useAnneeCivile;
		this.actionListenerOnLeftClic = actionListenerOnLeftClic;
		this.actionListenerOnRightClic = actionListenerOnRightClic;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		this.buttonFontSize = buttonFontSize;
		this.hgap = hgap;
		this.vgap = vgap;
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout(0, 0));
		this.removeAll();

		String[] months;
		if (useAnneeCivile) {
			months = new String[] { "J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D" };
		}
		else {
			months = new String[] { "A", "S", "O", "N", "D", "J", "F", "M", "A", "M", "J", "J" };
		}

		Box weeksPanel = Box.createHorizontalBox();
		weeksPanel.add(Box.createHorizontalGlue());

		Font f = new Font(null, Font.ITALIC, buttonFontSize - 2);
		Dimension d = new Dimension(buttonWidth, buttonFontSize);

		Box colBox = Box.createVerticalBox();
		colBox.setAlignmentX(Box.CENTER_ALIGNMENT);
		colBox.setAlignmentY(Box.TOP_ALIGNMENT);

		JLabel lab = new JLabel(months[0], SwingConstants.CENTER);
		lab.setFont(f);
		lab.setPreferredSize(d);
		lab.setMinimumSize(d);
		lab.setMaximumSize(d);
		colBox.add(lab);
		colBox.add(weekButtons()[0]);
		for (int i = 1; i < weekButtons().length; i++) {
			if (i % 5 == 0) {
				weeksPanel.add(colBox);
				weeksPanel.add(Box.createRigidArea(new Dimension(hgap, 1)));
				colBox = Box.createVerticalBox();
				colBox.setAlignmentX(Box.CENTER_ALIGNMENT);
				colBox.setAlignmentY(Box.TOP_ALIGNMENT);

				JLabel label = new JLabel(months[i / 5], SwingConstants.CENTER);
				label.setFont(f);
				label.setPreferredSize(d);
				label.setMinimumSize(d);
				label.setMaximumSize(d);
				colBox.add(label);
			}
			else {
				colBox.add(Box.createRigidArea(new Dimension(1, vgap)));
			}
			colBox.add(weekButtons()[i]);
		}
		weeksPanel.add(colBox);
		weeksPanel.add(Box.createHorizontalGlue());
		this.add(weeksPanel, BorderLayout.CENTER);
	}

	public void updateFormationAnnee(FormationAnnee formationAnnee) {
		if (formationAnnee == null) {
			return;
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		if (useAnneeCivile == false) {
			gc.set(formationAnnee.fannDebut().intValue(), 7, 1, 0, 0, 0);
		}
		else {
			gc.set(formationAnnee.fannDebut().intValue(), 0, 1, 0, 0, 0);
		}
		if (gc.get(Calendar.DAY_OF_WEEK) == 1) {
			gc.add(Calendar.DAY_OF_MONTH, -6);
		}
		else {
			if (gc.get(Calendar.DAY_OF_WEEK) > 2) {
				gc.add(Calendar.DAY_OF_MONTH, -(gc.get(Calendar.DAY_OF_WEEK) - 2));
			}
		}
		weekButtons()[0].setDate(gc);
		weekButtons()[0].setContrainteSemaine(null);
		weekButtons()[0].setEnabled(false);
		gc.add(GregorianCalendar.DATE, 7);
		for (int i = 1, count = 1; i < weekButtons().length;) {
			weekButtons()[i].setVisible(true);
			weekButtons()[i].setDate(gc);
			weekButtons()[i].setContrainteSemaine(null);
			gc.add(GregorianCalendar.DATE, 7);
			i++;
			count++;
			int dayOfMonth = gc.get(GregorianCalendar.DAY_OF_MONTH);
			if (count == 5) {
				count = 0;
			}
			else {
				if (dayOfMonth < 5 || dayOfMonth > gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) - 2) {
					weekButtons()[i].setVisible(false);
					weekButtons()[i].setDate(null);
					weekButtons()[i].setContrainteSemaine(null);
					count = 0;
					i++;
				}
			}
		}
		clearSelection(null);
		// updateUI();
		repaint();
	}

	public void update(NSArray<ContrainteSemaine> ctrls, boolean selecting, boolean enabling) {
		for (int i = 0; i < weekButtons().length; i++) {
			weekButtons()[i].setContrainteSemaine(null);
			if (enabling) {
				weekButtons()[i].setEnabled(false);
			}
		}
		if (ctrls != null) {
			for (int i = 0; i < ctrls.count(); i++) {
				ContrainteSemaine ctrl = ctrls.objectAtIndex(i);
				WeekToggleButton b = weekButtonFor(ctrl.ctsDate());
				if (b != null) {
					b.setContrainteSemaine(ctrl);
					if (selecting && isMultipleSelectEnabled) {
						b.setSelected(true);
					}
					if (enabling) {
						b.setEnabled(true);
					}
				}
			}
		}
		// updateUI();
		repaint();
	}

	public ArrayList<WeekToggleButton> getButtonsSelected() {
		ArrayList<WeekToggleButton> a = new ArrayList<WeekToggleButton>();
		for (int i = 0; i < weekButtons().length; i++) {
			if (weekButtons()[i].isSelected()) {
				a.add(weekButtons()[i]);
			}
		}
		return a;
	}

	public ArrayList<ContrainteSemaine> getCtrlsSelected() {
		ArrayList<ContrainteSemaine> a = new ArrayList<ContrainteSemaine>();
		for (int i = 0; i < weekButtons().length; i++) {
			if (weekButtons()[i].isSelected() && weekButtons()[i].contrainteSemaine() != null) {
				a.add(weekButtons()[i].contrainteSemaine());
			}
		}
		return a;
	}

	public WeekToggleButton getCurrentButton() {
		return getButtonSelected();
	}

	public WeekToggleButton getButtonSelected() {
		if (isMultipleSelectEnabled) {
			return null;
		}
		for (int i = 0; i < weekButtons().length; i++) {
			if (weekButtons()[i].isSelected()) {
				return weekButtons()[i];
			}
		}
		return null;
	}

	public ContrainteSemaine getCtrlSelected() {
		if (isMultipleSelectEnabled) {
			return null;
		}
		for (int i = 0; i < weekButtons().length; i++) {
			if (weekButtons()[i].isSelected()) {
				return weekButtons()[i].contrainteSemaine();
			}
		}
		return null;
	}

	public WeekToggleButton weekButtonFor(ContrainteSemaine ctrl) {
		WeekToggleButton wButton = null;
		for (int i = 0; i < weekButtons().length; i++) {
			if (weekButtons()[i].contrainteSemaine() != null && weekButtons()[i].contrainteSemaine().equals(ctrl)) {
				wButton = weekButtons()[i];
				break;
			}
		}
		return wButton;
	}

	public WeekToggleButton weekButtonFor(NSTimestamp date) {
		WeekToggleButton wButton = null;
		for (int i = 0; i < weekButtons().length; i++) {
			NSTimestamp buttonDate = weekButtons()[i].dateNSTimestamp();
			if (buttonDate != null && buttonDate.equals(date)) {
				wButton = weekButtons()[i];
				break;
			}
		}
		return wButton;
	}

	private void clearSelection(WeekToggleButton b) {
		for (int i = 0; i < weekButtons().length; i++) {
			if (weekButtons()[i].isEnabled()) {
				if (b == null || !b.equals(weekButtons()[i])) {
					weekButtons()[i].setSelected(false);
				}
			}
		}
	}

	private void onLeftClic(ActionEvent evt) {
		if (evt.getSource() != null) {
			WeekToggleButton b = (WeekToggleButton) evt.getSource();
			if (isMultipleSelectEnabled == false && b.isSelected()) {
				clearSelection(b);
			}
			if (actionListenerOnLeftClic != null) {
				actionListenerOnLeftClic.actionPerformed(evt);
			}
		}
	}

	private void onRightClic(MouseEvent evt) {
		if (evt.getSource() != null) {
			WeekToggleButton b = (WeekToggleButton) evt.getSource();
			if (actionListenerOnRightClic != null) {
				actionListenerOnRightClic.actionPerformed(new ActionEvent(b, Event.ACTION_EVENT, b.getText()));
			}
			if (b.isSelected() == false && isMultipleSelectEnabled == false) {
				clearSelection(b);
				b.setSelected(true);
			}
			b.repaint();
			if (actionListenerOnLeftClic != null) {
				actionListenerOnLeftClic.actionPerformed(new ActionEvent(b, Event.ACTION_EVENT, b.getText()));
			}
		}
	}

	public WeekToggleButton[] weekButtons() {
		if (weekButtons == null) {
			weekButtons = new WeekToggleButton[60];
			ActionListener myAL = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					onLeftClic(evt);
				}
			};
			MouseListener myML = new MouseListener() {
				public void mouseReleased(MouseEvent mouseevent) {
					if (mouseevent.getButton() == MouseEvent.BUTTON3) {
						onRightClic(mouseevent);
					}
				}

				public void mousePressed(MouseEvent mouseevent) {
				}

				public void mouseExited(MouseEvent mouseevent) {
				}

				public void mouseEntered(MouseEvent mouseevent) {
				}

				public void mouseClicked(MouseEvent mouseevent) {
				}
			};
			for (int i = 0; i < weekButtons.length; i++) {
				WeekToggleButton b = new WeekToggleButton(buttonWidth, buttonHeight, buttonFontSize);
				b.addActionListener(myAL);
				b.addMouseListener(myML);
				weekButtons[i] = b;
			}
		}
		return weekButtons;
	}

	public void setEnabled(boolean enabled) {
		for (int i = 0; i < weekButtons().length; i++) {
			weekButtons()[i].setEnabled(enabled);
		}
	}

	public boolean isMultipleSelectEnabled() {
		return isMultipleSelectEnabled;
	}

	public void setMultipleSelectEnabled(boolean isMultipleSelectEnabled) {
		this.isMultipleSelectEnabled = isMultipleSelectEnabled;
	}

}
