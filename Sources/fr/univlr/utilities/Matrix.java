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

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.foundation.NSLog;

/** classe contenant des methodes statiques pour la gestion de matrix de RadioButton */
public class Matrix {

	/** renvoi le bouton selectionne */
	public static JRadioButton getSelected(EOMatrix laMatrice) {
		int i, nbComponents;

		nbComponents = laMatrice.getComponentCount();

		for (i = 0; i < nbComponents; i++) {
			if (((JRadioButton) laMatrice.getComponent(i)).isSelected()) {
				return (JRadioButton) laMatrice.getComponent(i);
			}
		}

		return null;
	}

	public static void getSelectedComponents(EOMatrix laMatrice) {
	}

	/** renvoi le component selectionne */
	public static JButton getSelectedButton(EOMatrix laMatrice) {
		int i, nbComponents;

		nbComponents = laMatrice.getComponentCount();

		for (i = 0; i < nbComponents; i++) {
			if (((JButton) laMatrice.getComponent(i)).isSelected()) {
				return (JButton) laMatrice.getComponent(i);
			}
		}

		return null;
	}

	/** renvoi le nombre de boutons radios selectionnes. */
	public static int getSelectedCount(EOMatrix laMatrice) {
		int i, nbComponents, compteur = 0;

		nbComponents = laMatrice.getComponentCount();
		for (i = 0; i < nbComponents; i++) {
			if (((JRadioButton) laMatrice.getComponent(i)).isSelected()) {
				compteur++;
			}
		}

		return -1;

	}

	/** renvoi l'index du bouton selectionne (0 ...) */
	public static int getSelectedIndex(EOMatrix laMatrice) {
		int i, nbComponents;

		nbComponents = laMatrice.getComponentCount();
		for (i = 0; i < nbComponents; i++) {
			if (((JRadioButton) laMatrice.getComponent(i)).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	/** selectionne tous les composants. */
	public static void selectAll(EOMatrix laMatrice) {
		int nbComponents = laMatrice.getComponentCount();
		for (int i = 0; i < nbComponents; i++) {
			((JRadioButton) laMatrice.getComponent(i)).setSelected(true);
		}

	}

	/**
	 * formatage de la eomatrix<br>
	 * voir code
	 */
	public static void formateMatrix(EOMatrix matrix) {
		int i;
		int compte = matrix.getComponentCount();
		for (i = 0; i < compte; i++) {
			Object element = matrix.getComponent(i);
			if (element instanceof JToggleButton) {
				((JToggleButton) element).setFont(new Font("Helvetica", Font.PLAIN, 10));
				// ((JToggleButton)element).setBorder(null);
				((JToggleButton) element).setMargin(new Insets(0, 0, 0, 0));
			}
			if (element instanceof JButton) {
				((JButton) element).setFont(new Font("Helvetica", Font.PLAIN, 10));
			}
			// ((JButton)element).setBorder(null);
			((JButton) element).setMargin(new Insets(0, 0, 0, 0));
		}
	}

	/** deselectionne tous les composants. */
	public static void selectNone(EOMatrix laMatrice) {
		int nbComponents = laMatrice.getComponentCount();
		for (int i = 0; i < nbComponents; i++) {
			((JRadioButton) laMatrice.getComponent(i)).setSelected(false);
		}
	}

	/** retourne le label du bouton radio selectionne */
	public static String getSelectedText(EOMatrix matrix) {
		int selIndex = getSelectedIndex(matrix);
		JRadioButton bt = getButtonAtIndex(selIndex, matrix);
		String text = "";
		try {
			text = bt.getText();
		}
		catch (Exception exp) {
			NSLog.out.appendln(exp.getMessage());
		}
		return text;
	}

	/** renvoi le bouton a l'index donne en parametre */
	public static JRadioButton getButtonAtIndex(int index, EOMatrix laMatrice) {
		return (JRadioButton) laMatrice.getComponent(index);
	}

	/** desactive le bouton a l'index donne */
	public static void setDisabledIndex(int index, EOMatrix laMatrice) {
		getButtonAtIndex(index, laMatrice).setEnabled(false);
	}

	/** desactive le bouton a l'index donne */
	public static void enableIndex(int index, EOMatrix laMatrice) {
		getButtonAtIndex(index, laMatrice).setEnabled(true);
	}

	/** affectation d'une infobulle au bouton designe par l'index */
	public static void setToolTipAtIndex(String tooltip, int idx, EOMatrix laMatrice) {
		getButtonAtIndex(idx, laMatrice).setToolTipText(tooltip);
	}

	/** @deprecated */
	public static void disableIndex(int index, EOMatrix laMatrice) {
		getButtonAtIndex(index, laMatrice).setEnabled(false);
	}

	public static void talk(String msg) {
		System.out.println("MSG:" + msg);
	}

	/** selectionne le bouton a l'index donnee en parametre */
	public static void selectButtonAtIndex(int index, EOMatrix laMatrice) {
		JRadioButton component;
		laMatrice.invalidate();
		component = (JRadioButton) laMatrice.getComponent(index);
		component.invalidate();
		component.setSelected(true);
		component.validate();
		laMatrice.validate();
	}

	/** selectionne le bouton a l"index donnee */
	public static void setSelectedIndex(int index, EOMatrix laMatrice) {
		selectButtonAtIndex(index, laMatrice);
	}

	/** active ou desactive toute la matrice */
	public static void setEnabled(boolean vf, EOMatrix laMatrice) {
		int i, nbComponents;

		laMatrice.setEnabled(vf);

		nbComponents = laMatrice.getComponentCount();
		for (i = 0; i < nbComponents; i++) {
			((JRadioButton) laMatrice.getComponent(i)).setEnabled(vf);
		}
	}

	/** place le listener passe en parametre sur tous les boutons de la EOMatrix */
	public static void setListener(ActionListener listener, EOMatrix matrice) {

		for (int i = 0; i < matrice.getComponentCount(); i++) {
			JRadioButton radioButton = getButtonAtIndex(i, matrice);
			radioButton.addActionListener(listener);
		}
	}

}
