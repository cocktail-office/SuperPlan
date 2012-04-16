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
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.webobjects.eoapplication.EOInterfaceController;

public class ULRDateTime extends JPanel implements ChangeListener {

	private static final long serialVersionUID = -6287472211024339448L;
	private CalendarPanel calendarPanel;
	private JTextField calendarpanelTextField;
	private String strDate = "";
	private String strHeure = "";

	EOInterfaceController owner;

	public ULRDateTime(EOInterfaceController owner) {
		this.owner = owner;
		createGUI();
	}

	public ULRDateTime() {
		createGUI();
	}

	protected void timeChanged(String time) {
		strHeure = time;
		calendarpanelTextField.setText(strDate + " " + strHeure);
	}

	private void createGUI() {

		setBorder(BorderFactory.createEtchedBorder());
		calendarPanel = new CalendarPanel();
		calendarPanel.addChangeListener(this);

		calendarpanelTextField = new JTextField();
		calendarpanelTextField.setEditable(true);
		// calendarpanelTextField.setInputVerifier(new DateVerifier());

		calendarpanelTextField.setHorizontalAlignment(SwingConstants.CENTER);
		calendarpanelTextField.setBackground(new java.awt.Color(0xFFE4C4));
		add(calendarpanelTextField, BorderLayout.NORTH);
		add(calendarPanel, BorderLayout.CENTER);
		stateChanged(null);
	}

	public void stateChanged(ChangeEvent e) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		strDate = df.format(calendarPanel.getCalendar().getTime());
		calendarpanelTextField.setText(strDate + " " + strHeure);
	}

	public String getDateChoisie() {
		return calendarpanelTextField.getText();
	}

	class DateVerifier extends InputVerifier {

		public boolean verify(JComponent input) {
			JTextField tf = (JTextField) input;
			return tf.getText().matches("\\d\\d/\\d\\d/\\d\\d\\d\\d\\s\\d\\d:\\d\\d");
		}
	}

}
