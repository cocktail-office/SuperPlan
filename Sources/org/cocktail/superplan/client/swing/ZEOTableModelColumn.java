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
package org.cocktail.superplan.client.swing;

import java.awt.Color;
import java.awt.Component;
import java.text.Format;
import java.text.ParseException;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSKeyValueCodingAdditions;

/**
 * Represente une colonne, avec ses particularites que l'objet ZEOTable peut appeller pour realiser l'affichage.
 * 
 * @author Rodolphe Prin
 */
public class ZEOTableModelColumn {
	public final static int DEFAULT_CELL_MARGIN_LEFT = 2;
	public final static int DEFAULT_CELL_MARGIN_RIGHT = 2;
	public final static int DEFAULT_CELL_MARGIN_TOP = 0;
	public final static int DEFAULT_CELL_MARGIN_BOTTOM = 0;

	private int cellMarginLeft = DEFAULT_CELL_MARGIN_LEFT;
	private int cellMarginRight = DEFAULT_CELL_MARGIN_RIGHT;
	private int cellMarginTop = DEFAULT_CELL_MARGIN_TOP;
	private int cellMarginBottom = DEFAULT_CELL_MARGIN_BOTTOM;

	private EODisplayGroup myDg;
	private String attributeName;
	private String title;
	private int preferredWidth;
	private int alignment;
	private Format formatDisplay;
	private Format formatEdit;

	private boolean isEditable;
	private boolean isResizable;
	private Class columnClass;
	private TableCellEditor tableCellEditor;
	private TableCellRenderer tableCellRenderer;
	private Modifier myModifier;

	public ZEOTableModelColumn(final EODisplayGroup dg, final String attName, final String vTitle) {
		this(dg, attName, vTitle, -1);
	}

	public ZEOTableModelColumn(final EODisplayGroup dg, final String attName, final String vTitle, final int vpreferredWidth) {
		super();
		myDg = dg;
		initObject(attName, vTitle, vpreferredWidth, -1, null);
	}

	private void initObject(final String attName, final String vTitle, final int vpreferredWidth, final int valignment, final Format vFormat) {
		attributeName = attName;
		title = vTitle;
		preferredWidth = vpreferredWidth;
		setAlignment(valignment);
		setFormatDisplay(vFormat);

		isResizable = true;

	}

	/**
	 * @return L'alignement a utiliser pour la colonne (SwingConstants.CENTER, etc).
	 */
	public int getAlignment() {
		return alignment;
	}

	/**
	 * @return
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @return
	 */
	public Format getFormatDisplay() {
		return formatDisplay;
	}

	/**
	 * @return
	 */
	public int getPreferredWidth() {
		return preferredWidth;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * L'alignement a utiliser pour la colonne (facultatif).
	 * 
	 * @param align
	 *            SwingConstants.CENTER, etc.
	 */
	public void setAlignment(final int align) {
		if (align != -1 && tableCellRenderer == null) {
			setTableCellRenderer(new ZEOTableCellRenderer());
		}
		alignment = align;
	}

	/**
	 * Specifie le format a appliquer pour l'affichage des valeurs de la colonne (facultatif).
	 * 
	 * @param format
	 */
	public void setFormatDisplay(final Format format) {
		if (format != null && tableCellRenderer == null) {
			setTableCellRenderer(new ZEOTableCellRenderer());
		}
		this.formatDisplay = format;
	}

	/**
	 * Specifie le titre de la colonne.
	 * 
	 * @param string
	 */
	public void setTitle(final String string) {
		title = string;
	}

	/**
	 * @param row
	 * @return La valeur que la table va afficher.
	 */
	public Object getValueAtRow(final int row) {
		if (attributeName.indexOf(".") > 0) {
			return ((NSKeyValueCodingAdditions) (myDg.displayedObjects().objectAtIndex(row))).valueForKeyPath(attributeName);
		}
		return ((NSKeyValueCoding) (myDg.displayedObjects().objectAtIndex(row))).valueForKey(attributeName);
	}

	/**
	 * Appelee par la table pour mettre e jour la valeur dans le modele (en l'occurence la valeur de l'attribut de l'EOEnterpriseObject est
	 * mise a jour). Cette methode est appelee seulement si isEditable est atrue. Si un formatEdit est defini, il est utilise, sinon si un
	 * format d'affichage est defini il est utilise, sinon la valeur affectee est en string. si un modifier est defini il est utilise, sinon
	 * la methode takeValueForKey est appele pour l'EOEnterpriseObject correspondant.
	 * 
	 * @param value
	 * @param row
	 */
	public void setValueAtRow(final Object value, final int row) {
		Object tmpValue = value;
		// System.out.println("setvalue appelee sur le row : " + row +" -> "+tmpValue);
		// System.out.println("setvalue appelee sur le row : " + row +" -> "+tmpValue.getClass());

		Format formatTmp = formatEdit;
		if (formatEdit == null) {
			formatTmp = formatDisplay;
		}

		if ((formatTmp != null) && (tmpValue != null)) {
			try {
				// if (tmpValue.toString().length()==0) {
				// tmpValue = null;
				// }
				tmpValue = formatTmp.parseObject(tmpValue.toString());
				// System.out.println("value apres formatage  -> "+tmpValue);
				// System.out.println("value apres formatage CLASSE -> "+tmpValue.getClass().getName());
			}
			catch (ParseException e) {
				// e.printStackTrace();
				tmpValue = null;
			}
		}

		if (myModifier == null) {
			((NSKeyValueCoding) (myDg.displayedObjects().objectAtIndex(row))).takeValueForKey(tmpValue, attributeName);
		}
		else {
			myModifier.setValueAtRow(tmpValue, row);
		}

	}

	/**
	 * @return
	 */
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * Force la colonne comme etant modifiable. Attention, le row doit egalement etre modifiable (verifier s'il y a un
	 * ZEOTableModel.IZEOTableModelProvider implemente).
	 * 
	 * @param b
	 */
	public void setEditable(final boolean b) {
		isEditable = b;
	}

	/**
	 * @return
	 */
	public EODisplayGroup getMyDg() {
		return myDg;
	}

	/**
	 * @return
	 */
	public Class getColumnClass() {
		return columnClass;
	}

	/**
	 * Spcifie la classe de la colonne. Le fait d'indiquer la classe permet a Swing d'afficher la colonne autrement qu'en texte dans
	 * certains cas (ex. le type Boolean est affiche sous forme de case a cocher).
	 * 
	 * @param class1
	 */
	public void setColumnClass(final Class class1) {
		columnClass = class1;
	}

	/**
	 * @return
	 */
	public boolean isResizable() {
		return isResizable;
	}

	/**
	 * @param b
	 */
	public void setResizable(final boolean b) {
		isResizable = b;
	}

	/**
	 * @return
	 */
	public TableCellEditor getTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * @param editor
	 */
	public void setTableCellEditor(final TableCellEditor editor) {
		tableCellEditor = editor;
	}

	/**
	 * @return
	 */
	public Format getFormatEdit() {
		return formatEdit;
	}

	/**
	 * @param format
	 */
	public void setFormatEdit(final Format format) {
		formatEdit = format;
	}

	/**
	 * Permet de definir un modifier pour la colonne (la methode setValueAtRow du modifier sera appelee lorsqu'une valeur sera mise a jour).
	 * Si le modifier n'est pas defini, les valeurs seront mises en jour via un takeValueForKey classique.
	 * 
	 * @author Rodolphe Prin
	 */
	public interface Modifier {
		/**
		 * 
		 * @param value
		 * @param row
		 */
		public void setValueAtRow(Object value, int row);
	}

	/**
	 * @return
	 */
	public Modifier getMyModifier() {
		return myModifier;
	}

	/**
	 * @param modifier
	 */
	public void setMyModifier(final Modifier modifier) {
		myModifier = modifier;
	}

	public static class ZEOTextFieldTableCelleditor extends DefaultCellEditor {
		private final JTextField myTextField;

		/**
		 * @param textField
		 */
		public ZEOTextFieldTableCelleditor(JTextField textField) {
			super(textField);
			myTextField = textField;
			myTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#EE8827")));
		}

		/**
		 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
		 */
		public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
			// ZLogger.verbose("getTableCellEditorComponent");
			// ZLogger.verbose("listeners = " + ((AbstractDocument)myTextField.getDocument()).getDocumentListeners().length);
			// DocumentListener dl =
			// ((AbstractDocument)myTextField.getDocument()).getDocumentListeners()[((AbstractDocument)myTextField.getDocument()).getDocumentListeners().length-1];
			// myTextField.getDocument().removeDocumentListener(dl);
			final JTextField tmp = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
			tmp.selectAll();
			// myTextField.getDocument().addDocumentListener(dl);
			return tmp;
		}

		/**
		 * @see javax.swing.DefaultCellEditor#shouldSelectCell(java.util.EventObject)
		 */
		public boolean shouldSelectCell(final EventObject anEvent) {
			myTextField.selectAll();
			return super.shouldSelectCell(anEvent);
		}

		/**
		 * @return Returns the myTextField.
		 */
		public JTextField getMyTextField() {
			return myTextField;
		}
	}

	public static class ZEONumFieldTableCellEditor extends ZEOTextFieldTableCelleditor {
		private Format _format;

		/**
		 * @param textField
		 */
		public ZEONumFieldTableCellEditor(JTextField textField, Format _format) {
			super(textField);
			this._format = _format;
		}

		/**
		 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
		 */
		public Object getCellEditorValue() {
			final String s = (String) super.getCellEditorValue();
			return s.replace('.', ',');
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return super.getTableCellEditorComponent(table, null, isSelected, row, column);
			}
			return super.getTableCellEditorComponent(table, _format.format(value), isSelected, row, column);
		}
	}

	public static class ZEODateFieldTableCellEditor extends ZEOTextFieldTableCelleditor {
		private Format _format;

		/**
		 * @param textField
		 */
		public ZEODateFieldTableCellEditor(JTextField textField, Format _format) {
			super(textField);
			this._format = _format;
		}

		/**
		 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
		 */
		public Object getCellEditorValue() {
			final Object val = super.getCellEditorValue();
			return val;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return super.getTableCellEditorComponent(table, null, isSelected, row, column);
			}
			return super.getTableCellEditorComponent(table, _format.format(value), isSelected, row, column);
		}

	}

	//
	// public static class ZEOComboboxTableCellEditor extends DefaultCellEditor {
	// private ZEOComboBoxModel _comboboxModel;
	//
	// /**
	// * @param textField
	// */
	// public ZEOComboboxTableCellEditor(JComboBox combobox, ZEOComboBoxModel comboboxModel) {
	// super(combobox);
	// _comboboxModel = comboboxModel;
	// combobox.setModel(comboboxModel);
	// }
	//
	//
	// /**
	// * @see javax.swing.DefaultCellEditor#getCellEditorValue()
	// */
	// public Object getCellEditorValue() {
	// final Object val = super.getCellEditorValue();
	//
	// return val;
	// }
	//
	// public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	//
	//
	//
	// }
	//
	//
	// }

	/**
	 * Permet d'indiquer les marges des cellules
	 * 
	 * @param top
	 * @param left
	 * @param bottom
	 * @param right
	 */
	public void setCellMargin(final int top, final int left, final int bottom, final int right) {
		cellMarginTop = top;
		cellMarginLeft = left;
		cellMarginBottom = bottom;
		cellMarginRight = right;
	}

	public final int getCellMarginBottom() {
		return cellMarginBottom;
	}

	public final int getCellMarginLeft() {
		return cellMarginLeft;
	}

	public final int getCellMarginRight() {
		return cellMarginRight;
	}

	public final int getCellMarginTop() {
		return cellMarginTop;
	}

	public final void setPreferredWidth(int preferredWidth) {
		this.preferredWidth = preferredWidth;
	}

	public final TableCellRenderer getTableCellRenderer() {
		return tableCellRenderer;
	}

	public final void setTableCellRenderer(TableCellRenderer tableCellRenderer) {
		this.tableCellRenderer = tableCellRenderer;
	}

}