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

import java.awt.Component;
import java.text.Format;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ZEOTableCellRenderer extends DefaultTableCellRenderer implements IZEOTableCellRenderer {

	/**
	 * Renvoie le composant utilise pour affiche le contenu de la cellule.
	 * 
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int,
	 *      int)
	 */
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
			final int row, final int column) {
		// ZLogger.verbose("ZEOTableCellRenderer.getTableCellRendererComponent : " + value);

		final JLabel myComp = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		final ZEOTableModelColumn col = ((ZEOTable) table).getDataModel().getColumn(table.convertColumnIndexToModel(column));

		// Appliquer le format
		final Format format = col.getFormatDisplay();
		if ((value != null) && (format != null)) {
			myComp.setText(format.format(value));
		}

		// Appliquer l'alignement
		final int align = col.getAlignment();
		if (align != -1) {
			myComp.setHorizontalAlignment(align);
		}

		// les marges
		// myComp.setBorder(BorderFactory.createEmptyBorder(col.getCellMarginTop(), col.getCellMarginLeft(), col.getCellMarginBottom(),
		// col.getCellMarginRight()));

		return myComp;
	}
}