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
package edtscol.client.semainecontroller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class WeekElement extends JLabel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 8906401369607093432L;
	public static final Color SELECTED = new Color(0xDAA520/* 0xFFA07A */);
	public static final Color TEXT_SELECT = new Color(0xCD5C5C);
	public static final Color NORMAL = Color.BLACK;

	private boolean state = false;
	private int value;

	private WeekSelector weekSelector;

	public static boolean pressed = false;

	public WeekElement(WeekSelector weekSelector, int value) {
		super();
		this.weekSelector = weekSelector;
		setOpaque(true);
		setSize(30, 30);
		this.value = value;
		String strValue;
		if (value < 10) {
			strValue = "0" + String.valueOf(value);
		}
		else {
			strValue = String.valueOf(value);
		}
		setText(strValue);
		setHorizontalAlignment(SwingConstants.CENTER);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public int getValue() {
		return value;
	}

	public boolean isSelected() {
		return state;
	}

	public void setSelected(boolean state) {
		this.state = state;
		processChanges();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		state = !state;
		boolean succeed = true;
		if (state == false) {
			succeed = weekSelector.retirerSemaine(new Integer(value));
			if (!succeed) {
				state = !state;
			}
		}
		else {
			succeed = weekSelector.ajouterSemaine(new Integer(value));
			if (!succeed) {
				state = !state;
			}
		}
		processChanges();
	}

	/** change l'aspect graphique de la semaine */
	private void processChanges() {
		if (state) {
			setForeground(NORMAL);
			setBackground(TEXT_SELECT); // couleur recuperee du planningView
			setBorder(BorderFactory.createLineBorder(TEXT_SELECT));
		}
		else {
			setForeground(NORMAL);
			setBackground(Color.WHITE);
			setBorder(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
	}

}
