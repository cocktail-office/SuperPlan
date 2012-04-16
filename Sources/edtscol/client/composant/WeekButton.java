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
package edtscol.client.composant;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.ToolTipManager;

import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;

public class WeekButton extends JButton {
	private GregorianCalendar date;

	private static final Color BACKGROUND_DEFAULT = Color.white;
	private static final Color FOREGROUND_DEFAULT = Color.darkGray;
	private static final Color FOREGROUND_SELECTED = Color.red;

	private static final Font FONT_DEFAULT = new Font(null, Font.PLAIN, 9);
	private static final Font FONT_NOT_EMPTY = new Font(null, Font.BOLD, 9);

	public WeekButton() {
		super();
		init(new Dimension(16, 16));
	}

	public WeekButton(int width, int height) {
		super();
		init(new Dimension(width, height));
	}

	private void init(Dimension size) {
		// setBorder(BorderFactory.createEmptyBorder());
		setBorder(BorderFactory.createLineBorder(Color.red));
		setBorderPainted(false);
		setBackground(BACKGROUND_DEFAULT);
		setForeground(FOREGROUND_DEFAULT);
		setFocusPainted(false);
		setFont(FONT_DEFAULT);
		setPreferredSize(size);
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	public GregorianCalendar date() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = (GregorianCalendar) date.clone();
	}

	public void setSelected(boolean selected) {
		setForeground(selected ? FOREGROUND_SELECTED : FOREGROUND_DEFAULT);
		setBorderPainted(selected);
	}

	public void setEmpty(boolean empty) {
		setFont(empty ? FONT_DEFAULT : FONT_NOT_EMPTY);
	}

	public int week() {
		if (date() == null) {
			return -1;
		}
		return date().get(GregorianCalendar.WEEK_OF_YEAR);
	}

	public String getText() {
		if (date() == null) {
			return "";
		}
		return String.valueOf(date().get(GregorianCalendar.WEEK_OF_YEAR));
	}

	public String getToolTipText() {
		if (date() == null) {
			return "";
		}
		GregorianCalendar localDate = (GregorianCalendar) date().clone();
		StringBuffer sb = new StringBuffer("du ");
		sb.append(FormatHandler.dateToStr(new NSTimestamp(localDate.getTime()), "%d/%m/%Y"));
		localDate.add(GregorianCalendar.DATE, 6);
		sb.append(" au ");
		sb.append(FormatHandler.dateToStr(new NSTimestamp(localDate.getTime()), "%d/%m/%Y"));
		return sb.toString();
	}
}
