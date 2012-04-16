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

import org.cocktail.superplan.client.metier.ContrainteSemaine;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;

public class WeekToggleButton extends JToggleButton {
	private GregorianCalendar date;

	private static final Color BACKGROUND_DEFAULT = Color.white;
	private static final Color BACKGROUND_DEFAULT_DISABLED = Color.lightGray;
	private static final Color BACKGROUND_SELECTED = new Color(200, 200, 245);

	private static final Color FOREGROUND_DEFAULT = new Color(0, 150, 0);
	private static final Color FOREGROUND_ORANGE = new Color(190, 140, 15);
	private static final Color FOREGROUND_RED = Color.red;

	private static final Border BORDER_DEFAULT = BorderFactory.createLineBorder(FOREGROUND_DEFAULT, 1);
	private static final Border BORDER_DEFAULT_DISABLED = BorderFactory.createLineBorder(Color.gray, 1);
	private static final Border BORDER_ORANGE = BorderFactory.createLineBorder(FOREGROUND_ORANGE, 1);
	private static final Border BORDER_RED = BorderFactory.createLineBorder(FOREGROUND_RED, 1);

	private Font fontDefault, fontCurrent;

	private ContrainteSemaine contrainteSemaine;

	public WeekToggleButton() {
		super();
		init(new Dimension(24, 16), 11);
	}

	public WeekToggleButton(int width, int height, int fontSize) {
		super();
		init(new Dimension(width, height), fontSize);
	}

	private void init(Dimension size, int fontSize) {
		fontDefault = new Font(null, Font.PLAIN, fontSize);
		fontCurrent = new Font(null, Font.BOLD, fontSize + 6);
		setBorderPainted(true);
		setFocusPainted(false);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		ToolTipManager.sharedInstance().registerComponent(this);
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setSelected(isSelected());
			}
		});
	}

	public void deleteCtrlCascade(EOEditingContext ec) throws Exception {
		contrainteSemaine().deleteAllCtrlCascade();
		ec.deleteObject(contrainteSemaine());
		try {
			ec.saveChanges();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!");
		}
		setContrainteSemaine(null);
	}

	public GregorianCalendar date() {
		return date;
	}

	public NSTimestamp dateNSTimestamp() {
		if (date() == null) {
			return null;
		}
		return new NSTimestamp(date().getTime());
	}

	public void setDate(GregorianCalendar date) {
		this.date = (date == null ? null : (GregorianCalendar) date.clone());
	}

	public int week() {
		if (date() == null) {
			return -1;
		}
		return date().get(GregorianCalendar.WEEK_OF_YEAR);
	}

	public Border getBorder() {
		return (isFull() ? BORDER_RED : isEmpty() ? isEnabled() ? BORDER_DEFAULT : BORDER_DEFAULT_DISABLED : BORDER_ORANGE);
	}

	public Color getForeground() {
		return (isFull() ? FOREGROUND_RED : isEmpty() ? FOREGROUND_DEFAULT : FOREGROUND_ORANGE);
	}

	public Color getBackground() {
		return (isSelected() ? BACKGROUND_SELECTED : isEnabled() ? BACKGROUND_DEFAULT : BACKGROUND_DEFAULT_DISABLED);
	}

	public Font getFont() {
		return (isSelected() ? fontCurrent : fontDefault);
	}

	public String getText() {
		return String.valueOf(week());
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

	public ContrainteSemaine contrainteSemaine() {
		return contrainteSemaine;
	}

	public void setContrainteSemaine(ContrainteSemaine contrainteSemaine) {
		this.contrainteSemaine = contrainteSemaine;
	}

	public boolean isFull() {
		if (contrainteSemaine() == null) {
			return false;
		}
		boolean b = contrainteSemaine().isFull();
		return b;
	}

	public boolean isEmpty() {
		return contrainteSemaine() == null;
	}
}
