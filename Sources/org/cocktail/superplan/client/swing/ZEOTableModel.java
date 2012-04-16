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

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

/**
 * Modele a associer a une JTable qui permet d'affichier le contenu d'un displayGroup.
 * 
 * @see org.cocktail.zutil.client.wo.ZEOTable
 * @see org.cocktail.zutil.client.wo.ZEOSelectionDialog
 * @author Rodolphe Prin
 */
public class ZEOTableModel extends AbstractTableModel {
	protected EODisplayGroup myDg;
	protected int innerColCount;
	protected int innerRowCount;
	protected ArrayList colTitles;
	protected ArrayList colAttributeNames;
	protected ArrayList myColumns;
	protected IZEOTableModelProvider myProvider;

	public ZEOTableModel(EODisplayGroup dg, Collection vColumns) {
		super();
		myDg = dg;

		myColumns = new ArrayList(vColumns.size());
		myColumns.addAll(vColumns);

		final int x = vColumns.size();
		colAttributeNames = new ArrayList(x);
		colTitles = new ArrayList(x);

		for (int i = 0; i < x; i++) {
			colAttributeNames.add(((ZEOTableModelColumn) myColumns.get(i)).getAttributeName());
			colTitles.add(((ZEOTableModelColumn) myColumns.get(i)).getTitle());
		}
		updateInnerRowCount();
		updateInnerColCount();
	}

	public int getColumnCount() {
		return innerColCount;
	}

	public int getRowCount() {
		return innerRowCount;
	}

	/**
	 * Récupère la valeur depuis le displaygroup
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return ((ZEOTableModelColumn) myColumns.get(columnIndex)).getValueAtRow(rowIndex);
	}

	/**
	 * Renvoie la classe d'une colonne pour permettre a Swing d'afficher la bonne case.
	 * 
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	public Class getColumnClass(final int columnIndex) {
		Class res = null;
		if (getColumn(columnIndex).getColumnClass() == null) {
			res = super.getColumnClass(columnIndex);
		}
		else {
			res = getColumn(columnIndex).getColumnClass();
		}
		return res;
	}

	public String getColumnName(final int column) {
		return (String) colTitles.get(column);
	}

	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		boolean res = false;
		if (myProvider == null) {
			res = getColumn(columnIndex).isEditable();
		}
		else {
			res = (getColumn(columnIndex).isEditable() && myProvider.isRowEditable(rowIndex));
		}
		return res;
	}

	protected void updateSelection(final NSArray newSel) {
		myDg.setSelectedObjects(newSel);
	}

	/**
	 * Met a jour la selection du displaygroup
	 * 
	 * @param array
	 *            tableau des index du modele selectionnes.
	 */
	public void updateSelectedIndexes(final NSArray array) {
		final NSMutableArray sel = new NSMutableArray();
		// sel.removeAllObjects();
		for (int i = 0; i < array.count(); i++) {
			// sel.addObject((EOEnterpriseObject) myDg.displayedObjects().objectAtIndex(((Integer)array.objectAtIndex(i)).intValue()) );
			// Normalement la reference a EOEnterpriseObject est inutile
			sel.addObject(myDg.displayedObjects().objectAtIndex(((Integer) array.objectAtIndex(i)).intValue()));
		}
		updateSelection(sel);
	}

	/**
	 * Renvoie les objets selectionnes dans le displaygroup
	 */
	public NSArray getSelectedEOObjects() {
		return myDg.selectedObjects();
	}

	public Object getSelectedObject() {
		return myDg.selectedObject();
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
	public int getInnerRowCount() {
		return innerRowCount;
	}

	/**
	 * @param i
	 */
	public void setInnerRowCount(final int i) {
		innerRowCount = i;
	}

	/**
	 * @return
	 */
	// public Vector getColAttributeNames() {
	// return colAttributeNames;
	// }

	/**
	 * A appeler lorsque le nombre d'enregistrement du modele change (ce n'est pas fait en dynamique pour des raisons de performance).
	 */
	public final void updateInnerRowCount() {
		innerRowCount = myDg.displayedObjects().count();
	}

	public final void updateInnerColCount() {
		innerColCount = colAttributeNames.size();
	}

	/**
	 * Renvoie le nom de l'attribut correspondant a l'index de colonne
	 * 
	 * @param i
	 */
	public String getAttributeNameForColIndex(final int i) {
		return (String) colAttributeNames.get(i);
	}

	public ZEOTableModelColumn getColumn(final int i) {
		return (ZEOTableModelColumn) myColumns.get(i);
	}

	// public ZEOTableModelColumn getColumn(String attName) {
	// return (ZEOTableModelColumn)myColumns.elementAt(colAttributeNames.indexOf(attName));
	// }

	/*
	 * Methode a implementer des qu'on n'est pas en lecture seule.
	 */
	public void setValueAt(final Object value, final int row, final int col) {
		if (getColumn(col).isEditable()) {
			getColumn(col).setValueAtRow(value, row);
			fireTableCellUpdated(row, col);
		}
	}

	/**
	 * Renvoie toutes les EOs dont la colonne est egale a value.
	 * 
	 * @param value
	 * @param col
	 * @return
	 */
	public ArrayList getRowsForValueAtCol(final Object value, final int col) {
		final ArrayList res = new ArrayList();
		for (int i = 0; i < innerRowCount; i++) {
			if (getValueAt(i, col).equals(value)) {
				res.add(myDg.displayedObjects().objectAtIndex(i));
			}
		}
		return res;
	}

	/**
	 * Change la valeur d'une colonne pour tous les rows.
	 * 
	 * @param value
	 * @param col
	 */
	public void setValueForCol(final Object value, final int col) {
		for (int i = 0; i < innerRowCount; i++) {
			setValueAt(value, i, col);
		}
	}

	/**
	 * @return
	 */
	public ArrayList getMyColumns() {
		return myColumns;
	}

	/**
	 * Methode a appeler pour informer le modele qu'il doit rafraichir l'affichage l'ensemble de la table a partir des donnees. L'appel a
	 * cette methode entraine la perte de la ligne selectionnee par l'utilisateur. Si les donnees mises a jour se limitent a quelques
	 * cellules, utiliser plutot fireTableCellUpdated.
	 */
	public void fireTableDataChanged() {
		// System.out.println("ZEOTableModel.fireTableDataChanged() "+this);
		super.fireTableDataChanged();
	}

	/**
	 * Methode a utiliser pour informer le modele que la valeur affichee dans une cellule donnee doit etre rafrachie (la donnee a ete mide a
	 * jour). Cette methode a l'avantage de conserver la selection de l'utilisateur, contrairement a la methode fireTableDataChanged().
	 */
	public void fireTableCellUpdated(final int row, final int col) {
		super.fireTableCellUpdated(row, col);
	}

	public void fireTableRowUpdated(final int row) {
		for (int i = 0; i < innerColCount; i++) {
			fireTableCellUpdated(row, i);
		}
	}

	public interface IZEOTableModelProvider {
		/**
		 * Indique si une ligne est editable (a utiliser en combinaison avec setEditable() de la colonne). Si non defini on considere que la
		 * ligne est editable.
		 */
		public boolean isRowEditable(int row);
	}

	/**
	 * @return
	 */
	public IZEOTableModelProvider getMyProvider() {
		return myProvider;
	}

	/**
	 * @param provider
	 */
	public void setMyProvider(final IZEOTableModelProvider provider) {
		myProvider = provider;
	}

}
