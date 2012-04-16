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
import java.awt.Font;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JToggleButton;
import javax.swing.ToolTipManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.superplan.client.metier.ContrainteJour;

import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;

public class DayToggleButton extends JToggleButton {
	private GregorianCalendar date;

	private static final Color BACKGROUND_DEFAULT = Color.white;
	private static final Color BACKGROUND_DEFAULT_DISABLED = Color.lightGray;

	private static final Color FOREGROUND_DEFAULT = new Color(0, 150, 0);
	private static final Color FOREGROUND_SELECTED = Color.red;

	private static final Border BORDER_DEFAULT = BorderFactory.createLineBorder(FOREGROUND_DEFAULT);
	private static final Border BORDER_DEFAULT_DISABLED = BorderFactory.createLineBorder(Color.gray);
	private static final Border BORDER_SELECTED = BorderFactory.createLineBorder(FOREGROUND_SELECTED);

	private Font fontDefault;

	private ContrainteJour contrainteJour;

	public DayToggleButton() {
		super();
		init(new Dimension(24, 16), 11);
	}

	public DayToggleButton(int width, int height, int fontSize) {
		super();
		init(new Dimension(width, height), fontSize);
	}

	private void init(Dimension size, int fontSize) {
		fontDefault = new Font(null, Font.BOLD, fontSize);
		setBorderPainted(true);
		setFocusPainted(false);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		ToolTipManager.sharedInstance().registerComponent(this);
		addChangeListener(new ChangeListener() {
			public void stateChanged(@SuppressWarnings("unused") ChangeEvent e) {
				setSelected(isSelected());
			}
		});
	}

	public GregorianCalendar date() {
		return date;
	}

	public NSTimestamp dateNSTimestamp() {
		return new NSTimestamp(date().getTime());
	}

	public void setDate(GregorianCalendar date) {
		this.date = (GregorianCalendar) date.clone();
	}

	public int day() {
		if (date() == null) {
			return -1;
		}
		return DateCtrl.getDayOfWeek(dateNSTimestamp());
	}

	public Border getBorder() {
		return (isSelected() ? BORDER_SELECTED : isEnabled() ? BORDER_DEFAULT : BORDER_DEFAULT_DISABLED);
	}

	public Color getForeground() {
		return (isSelected() ? FOREGROUND_SELECTED : FOREGROUND_DEFAULT);
	}

	public Color getBackground() {
		return (isEnabled() ? BACKGROUND_DEFAULT : BACKGROUND_DEFAULT_DISABLED);
	}

	public Font getFont() {
		return (fontDefault);
	}

	public String getToolTipText() {
		if (date() == null) {
			return "";
		}
		return FormatHandler.dateToStr(new NSTimestamp(date().getTime()), "%d/%m/%Y");
	}

	public ContrainteJour contrainteJour() {
		return contrainteJour;
	}

	public void setContrainteJour(ContrainteJour contrainteJour) {
		this.contrainteJour = contrainteJour;
	}

}
