package fr.univlr.common.utilities;

import java.awt.Color;
import java.awt.Image;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import com.webobjects.eoapplication.client.EOClientResourceBundle;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.EOMasterDetailAssociation;
import com.webobjects.eointerface.EOTableAssociation;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class WidgetHandler {

	public static final EOClientResourceBundle bundle = new EOClientResourceBundle();

	public static EOClientResourceBundle getResourceBundle() {
		return bundle;
	}

	public static ImageIcon imageIcon(String name) {
		Object data = null;
		ImageIcon imageicon;
		try {
			data = bundle.getObject(name);
		}
		catch (Exception e) {
			data = null;
		}
		finally {
			if (data != null) {
				imageicon = (ImageIcon) data;
			}
			else {
				imageicon = null;
			}
		}
		return imageicon;
	}

	public static Image image(String name) {
		return imageIcon(name).getImage();
	}

	public static void decorateButton(String img, JButton button) {
		decorateButton(null, img, button);
	}

	public static void decorateButton(String tooltip, String text, String img, JButton button) {
		if (tooltip != null) {
			button.setToolTipText(tooltip);
		}
		decorateButton(text, img, button);
	}

	public static void decorateButton(String text, String img, JButton button) {
		button.setText(text);
		if (img != null) {
			button.setIcon(imageIcon(img));
		}
		if (text != null) {
			button.setHorizontalAlignment(SwingConstants.LEFT);
		}
		else {
			button.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}

	/** applique la couleur au JLabel */
	public static void colorJLabel(JLabel lbl, Color color) {
		lbl.setOpaque(true);
		lbl.setBackground(color);
	}

	/**
	 * Rendre une table (EOTable) non editable : a utiliser partout ou il y a des tables lors de connectionWasEstablished de preference.
	 */
	public static void setTableNotEditable(EOTable aTable) {
		JTable actualTable = aTable.table();
		for (Enumeration enumerat = actualTable.getColumnModel().getColumns(); enumerat.hasMoreElements(); ((TableColumn) enumerat.nextElement())
				.setCellEditor(null)) {
			;
		}
	}

	/**
	 * interdit la selection dans une table a appeller dans connectionWasEstablished
	 */
	public static void disableTableSelection(EOTable aTable) {
		aTable.table().setRowSelectionAllowed(false);
		aTable.table().setColumnSelectionAllowed(false);
		aTable.table().setCellSelectionEnabled(false);
	}

	/** deselectionne tous les objets du displayGroup */
	public static void selectNoneInDisplayGroup(EODisplayGroup dg) {
		dg.setSelectedObjects(null);
		dg.updateDisplayedObjects();
	}

	/** selectionne tous les objets du displayGroup */
	public static void selectAllInDisplayGroup(EODisplayGroup dg) {
		dg.setSelectedObjects(dg.displayedObjects());
		dg.updateDisplayedObjects();
	}

	/** purge le contenu du displayGroup passe en parametre */
	public static void flushDisplayGroup(EODisplayGroup disp) {
		disp.setObjectArray(new NSArray());
		WidgetHandler.informObservingAssociation(disp);
	}

	/** permet d'ajouter des objets a un EODisplayGroup d'un seul coup */
	public static void addObjectsToEOD(NSArray objects, EODisplayGroup eod) {
		if (objects != null) {
			for (int i = 0; i < objects.count(); i++) {
				eod.insertObjectAtIndex(objects.objectAtIndex(i), 0);
			}
		}
	}

	/** enleve l'objet du displayGroup sans faire de delete sur les objets */
	public static void removeObjectFromEOD(Object object, EODisplayGroup eod) {
		NSMutableArray tmp = eod.displayedObjects().mutableClone();
		tmp.removeObject(object);
		eod.setObjectArray(tmp);
		informObservingAssociation(eod);
	}

	/** enleve les objets du displayGroup sans faire de delete sur les objets */
	public static void removeObjectsFromEOD(NSArray objects, EODisplayGroup eod) {
		NSMutableArray tmp = eod.displayedObjects().mutableClone();
		tmp.removeObjectsInArray(objects);
		eod.setObjectArray(tmp);
		informObservingAssociation(eod);
	}

	/** retourne tous les items d'un comboBox d'un coup */
	public static NSArray getAllComboBoxItems(JComboBox combo) {
		NSMutableArray list = new NSMutableArray();
		for (int i = 0; i < combo.getItemCount(); i++) {
			list.addObject(combo.getItemAt(i));
		}
		return list;
	}

	public static void setObjectsToComboBox(Object firestObject, NSArray objects, JComboBox combo) {
		if (combo != null) {
			combo.removeAllItems();
			if (firestObject != null) {
				combo.addItem(firestObject);
			}
		}
		addObjectsToComboBox(objects, combo);
		combo.setSelectedItem(firestObject);
	}

	/** affecte les objets au JComboBox apres netoyage */
	public static void setObjectsToComboBox(NSArray objects, JComboBox combo) {
		if (combo != null) {
			combo.removeAllItems();
		}
		addObjectsToComboBox(objects, combo);
	}

	/** ajoute les objets au comboBox sans supprimer les objets existants */
	public static void addObjectsToComboBox(NSArray objects, JComboBox combo) {
		if (objects != null) {
			for (int i = 0; i < objects.count(); i++) {
				combo.addItem(objects.objectAtIndex(i));
			}
		}
	}

	/** dit aux jtables qui sont liees au displayGroup de se rafraichir */
	public static void informObservingAssociation(EODisplayGroup aTable) {
		int nbAssociations;
		NSArray observingAssociations = aTable.observingAssociations();
		if ((nbAssociations = observingAssociations.count()) > 0) {
			for (int i = 0; i < nbAssociations; i++) {

				String assocClassName = observingAssociations.objectAtIndex(i).getClass().getName();
				if (assocClassName.equals("com.webobjects.eointerface.EOTableAssociation")) {
					((EOTableAssociation) observingAssociations.objectAtIndex(i)).subjectChanged();
				}

				if (assocClassName.equals("com.webobjects.eointerface.EOMasterDetailAssociation")) {
					EOMasterDetailAssociation anAssociation = (EOMasterDetailAssociation) observingAssociations.objectAtIndex(i);
					anAssociation.subjectChanged();
					EODisplayGroup detailGroup = (EODisplayGroup) anAssociation.object();
					informObservingAssociation(detailGroup);
				}
			}
		}
	}

}
