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

import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Colonne qui lit une arrayList de HashMap. Specifiez la cle de la HashMap.
 * 
 * @author rodolphe.prin@univ-lr.fr
 */
public class ZEOTableModelColumnHashMap extends ZEOTableModelColumn {
	private final ArrayList _myRows;

	/**
	 * @param rows
	 *            Les enregistrements a afficher (arraylist de Map)
	 * @param keyName
	 *            Nom de la cle de la map associee a cette colonne
	 * @param vTitle
	 *            Titre de la colonne
	 */
	public ZEOTableModelColumnHashMap(ArrayList rows, String keyName, String vTitle, int width) {
		super(null, keyName, vTitle, width);
		_myRows = rows;
	}

	/**
	 * @see org.cocktail.zutil.client.wo.table.ZEOTableModelColumn#setValueAtRow(java.lang.Object, int)
	 */
	public void setValueAtRow(final Object value, final int row) {
		Object tmpValue = value;
		Format formatTmp = getFormatEdit();
		if (getFormatEdit() == null) {
			formatTmp = getFormatDisplay();
		}

		if ((formatTmp != null) && (tmpValue != null)) {
			try {
				tmpValue = formatTmp.parseObject(tmpValue.toString());
				// System.out.println("value apres formatage  -> "+value);
			}
			catch (ParseException e) {
				e.printStackTrace();
				return;
			}
		}
		((Map) _myRows.get(row)).put(getAttributeName(), tmpValue);
	}

	/**
	 * @see org.cocktail.zutil.client.wo.table.ZEOTableModelColumn#getValueAtRow(int)
	 */
	public Object getValueAtRow(final int row) {
		return ((Map) _myRows.get(row)).get(getAttributeName());
	}

}
