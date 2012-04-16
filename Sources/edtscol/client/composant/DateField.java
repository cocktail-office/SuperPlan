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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;

public class DateField extends JPanel implements ActionListener {

	private JTextField field;
	private JButton button;
	private JDialog owner;
	private Object delegate;
	private String methodeToInvoke;

	public DateField(JDialog owner) {
		_initUI();
		this.owner = owner;
	}

	private void _initUI() {
		setLayout(new FlowLayout());
		field = new JTextField(10);
		field.setHorizontalAlignment(SwingConstants.CENTER);
		button = new JButton();
		button.addActionListener(this);

		ImageIcon icoMinical = WidgetHandler.imageIcon("minical");
		button.setIcon(icoMinical);
		Dimension dim = new Dimension(22, 22);
		button.setSize(dim);
		add(field);
		add(button);
		setBorder(BorderFactory.createEtchedBorder());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button || e.getSource() == field) {
			new DateDlgChooser(this, owner, button, "setDate");
		}
	}

	public void setDelegate(Object delegate, String methodeToInvoke) {
		this.delegate = delegate;
		this.methodeToInvoke = methodeToInvoke;
	}

	public void setDate(String date) {
		field.setText(date);
		if (delegate != null && methodeToInvoke != null && date != null && !date.equals("")) {
			try {
				Method method = delegate.getClass().getMethod(methodeToInvoke, new Class[] { String.class });
				if (method != null) {
					method.invoke(delegate, new Object[] { date });
				}
			}
			catch (Exception e) {
				System.out.println("DateField : setDate : " + e.getMessage());
			}
		}
	}

	public String getText() {
		return field.getText();
	}

	public void setText(String text) {
		field.setText(text);
	}

	public void setTimestamp(NSTimestamp time) {
		String value = FormatHandler.dateToStr(time, "%d/%m/%Y");
		if (value == null) {
			value = "";
		}

		field.setText(value);
	}

	public NSTimestamp getTimestamp() {
		String txt = field.getText().trim();
		if (!txt.equals("")) {
			return FormatHandler.strToDate(field.getText(), "%d/%m/%Y");
		}
		else {
			return null;
		}
	}

}
