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
import java.awt.Container;
import java.awt.Window;
import java.text.SimpleDateFormat;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.webobjects.eoapplication.EOController;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSSelector;

import fr.univlr.common.utilities.WindowHandler;

public class ULRDateTimeWindow extends JDialog implements ChangeListener {

	private static final long serialVersionUID = 2976459099214365893L;
	private CalendarPanel calendarPanel;
	private JTextField calendarpanelTextField;
	private String strDate = "";
	private Object owner;
	private String callback;
	private Object sender;

	public ULRDateTimeWindow(Object owner, JDialog dlg, Object sender, String callback) {
		super(dlg, true);
		this.owner = owner;
		this.callback = callback;
		this.sender = sender;
		init(dlg);
	}

	public ULRDateTimeWindow(Object owner, Object sender, String callback) {
		this.owner = owner;
		this.callback = callback;
		this.sender = sender;
		init(null);
	}

	private void init(Window win) {
		setTitle("Date");
		createGUI();
		setResizable(false);
		pack();
		if (win == null) {
			win = WindowHandler.getWindowFromController((EOController) owner);
		}
		int xMain = win.getBounds().x;
		int yMain = win.getBounds().y;
		int xB = ((JComponent) sender).getX();
		int yB = ((JComponent) sender).getY();
		this.setLocation((xMain + xB + 30), yMain + yB);
		setVisible(true);
	}

	private void createGUI() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout(5, 5));
		calendarPanel = new CalendarPanel();
		calendarPanel.addChangeListener(this);
		calendarpanelTextField = new JTextField();
		calendarpanelTextField.setEditable(false);
		calendarpanelTextField.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(calendarpanelTextField, BorderLayout.NORTH);
		container.add(calendarPanel, BorderLayout.CENTER);
	}

	public void stateChanged(ChangeEvent e) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		strDate = df.format(calendarPanel.getCalendar().getTime());
		calendarpanelTextField.setText(strDate);
		try {
			if (!e.getSource().getClass().getName().equals("javax.swing.JComboBox")) {
				NSSelector.invoke(callback, new Class[] { String.class }, owner, new Object[] { strDate });
			}
		}
		catch (Exception exe) {
			NSLog.out.appendln("ULRDateTime : " + exe.getMessage());
			exe.printStackTrace();
		}

		if (!e.getSource().getClass().getName().equals("javax.swing.JComboBox")) {
			this.hide();
		}
	}

	class DateVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			JTextField tf = (JTextField) input;
			return tf.getText().matches("\\d\\d/\\d\\d/\\d\\d\\d\\d");
		}
	}
}
