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
package edtscol.client.planningview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;

import com.webobjects.foundation.NSArray;

public class Message extends JWindow {

	JTextArea area;

	public Message() {
		super();
		this.setSize(400, 300);
		area = new JTextArea();
		area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		area.setLineWrap(true);
		area.setFont(new Font("Helvetica", Font.PLAIN, 10));
		JScrollPane scrollPane = new JScrollPane(area);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	public void setMessage(String msg, Component component, Point pos) {
		if (component != null) {
			Point p = component.getLocationOnScreen();
			this.setLocation(p.x + pos.x, p.y + pos.y);
		}
		else {
			this.setLocation(pos.x, pos.y);
		}
		area.setText(msg);
	}

	public void setMessage(NSArray msgs, Component component, Point pos) {
		if (component != null) {
			Point p = component.getLocationOnScreen();
			this.setLocation(p.x + pos.x, p.y + pos.y);
		}
		else {
			this.setLocation(pos.x, pos.y);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < msgs.count(); i++) {
			str.append((String) msgs.objectAtIndex(i));
		}
		area.setText(str.toString());
	}

	public void setMessage(ArrayList msgs, Component component, Point pos) {
		if (component != null) {
			Point p = component.getLocationOnScreen();
			this.setLocation(p.x + pos.x, p.y + pos.y);
		}
		else {
			this.setLocation(pos.x, pos.y);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < msgs.size(); i++) {
			str.append((String) msgs.get(i));
		}
		area.setText(str.toString());
	}
}
