package fr.univlr.common.utilities;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.webobjects.eoapplication.EOController;
import com.webobjects.eoapplication.EODialogController;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eoapplication.EOModalDialogController;
import com.webobjects.eoapplication.EOSimpleWindowController;

public class WindowHandler {

	public static void runModal(EOInterfaceController ctrl, String title) {
		EOModalDialogController.runControllerInNewModalDialog(ctrl, title);
	}

	public static void run(EOInterfaceController ctrl, String title) {
		EODialogController.runControllerInNewDialog(ctrl, title);
	}

	public static void closeModal(EOInterfaceController ctrl) {
		((EOModalDialogController) ctrl.supercontroller()).closeWindow();
	}

	public static void close(EOInterfaceController ctrl) {
		((EODialogController) ctrl.supercontroller()).closeWindow();
	}

	/** affiche un message en utilisant JOptionPane */
	public static void showInfo(String info) {
		JOptionPane.showMessageDialog(null, info, "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	/** affiche un message en utilisant JOptionPane */
	public static void showError(String erreur) {
		JOptionPane.showMessageDialog(null, erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean showConfirmation(String question) {
		int choix = JOptionPane.showConfirmDialog(null, question, "Confirmation", JOptionPane.YES_NO_OPTION);
		return ((choix == 0) ? true : false);
	}

	public static void setCursor(int type, EOInterfaceController controller) {
		Container container = (((EOSimpleWindowController) controller.supercontroller()).window());
		Component[] children = container.getComponents();
		for (int i = 0; i < container.getComponentCount(); i++) {
			((Container) children[i]).setCursor(Cursor.getPredefinedCursor(type));
		}
	}

	public static void setWaitCursor(EOInterfaceController controller) {
		setCursor(Cursor.WAIT_CURSOR, controller);
	}

	public static void setDefaultCursor(EOInterfaceController controller) {
		setCursor(Cursor.DEFAULT_CURSOR, controller);
	}

	// ///
	public static void setWaitCursor(EOModalDialogController controller) {
		setWaitCursor(controller.component());
	}

	public static void setDefaultCursor(EOModalDialogController controller) {
		setDefaultCursor(controller.component());
	}

	// ///

	public static void setWaitCursor(Component controller) {
		controller.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	public static void setDefaultCursor(Component controller) {
		controller.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/** retourne l'objet Window du controleur passe en parametre */
	public static Window getWindowFromController(EOInterfaceController controller) {
		if (controller != null) {
			return ((EOSimpleWindowController) controller.supercontroller()).window();
		}
		else {
			return null;
		}
	}

	/** renvoie l'objet JFrame du controleur passe en parametre, c'est possible pour le */
	public static JFrame getJFrameFromController(EOInterfaceController controller) {
		if (controller != null) {
			return (JFrame) getWindowFromController(controller);
		}
		else {
			return null;
		}
	}

	/** retourne l'objet Window du controleur passe en parametre */
	public static Window getWindowFromController(EOModalDialogController controller) {
		if (controller != null) {
			return controller.window();
		}
		else {
			return null;
		}
	}

	/** renvoie l'objet JFrame du controleur passe en parametre, c'est possible pour le */
	public static JDialog getJDialogFromController(EOModalDialogController controller) {
		if (controller != null) {
			return (JDialog) controller.window();
		}
		else {
			return null;
		}
	}

	public static Window getWindowFromController(EOController controller) {
		if (controller instanceof EOModalDialogController) {
			return getWindowFromController((EOModalDialogController) controller);
		}
		else
			if (controller instanceof EOInterfaceController) {
				return getWindowFromController((EOInterfaceController) controller);
			}
			else {
				return null;
			}
	}

}
