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
import java.awt.Dimension;
import java.lang.ref.WeakReference;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class MyJTable extends JTable {
	private WeakReference<TableCellRenderer> wrappedHeaderRendererRef = null;
	private TableCellRenderer wrapperHeaderRenderer = null;
	
	public MyJTable(TableModel dm) {
        super(dm);
        
		try {

			int height=15;
			if (getFont()!=null)
				height=getFontMetrics(getFont()).getHeight();
			getTableHeader().setPreferredSize(new Dimension(getTableHeader().getWidth(), height+4));
			
		}
		catch (Exception ex) {
		}


	}
	
	private class MyTableColumn extends TableColumn {
		MyTableColumn(int modelIndex) {
			super(modelIndex);
		}
		public TableCellRenderer getHeaderRenderer() {
			TableCellRenderer defaultHeaderRenderer = 
				MyJTable.this.getTableHeader().getDefaultRenderer();
			if (wrappedHeaderRendererRef == null 
					|| wrappedHeaderRendererRef.get() != defaultHeaderRenderer) {
				wrappedHeaderRendererRef = 
					new WeakReference<TableCellRenderer>(defaultHeaderRenderer);
				wrapperHeaderRenderer = 
					new DecoratedHeaderRenderer(defaultHeaderRenderer);
			}
			return wrapperHeaderRenderer;
		}
	}
	public void createDefaultColumnsFromModel() {
		TableModel m = getModel();
		if (m != null) {
			// Remove any current columns
			TableColumnModel cm = getColumnModel();
			while (cm.getColumnCount() > 0) {
				cm.removeColumn(cm.getColumn(0));
			}

			// Create new columns from the data model info
			for (int i = 0; i < m.getColumnCount(); i++) {
				TableColumn newColumn = new MyTableColumn(i);
				addColumn(newColumn);
			}
		}
	}

	private class DecoratedHeaderRenderer implements TableCellRenderer {

		public DecoratedHeaderRenderer(TableCellRenderer render){
			this.render = render;
		}
		private TableCellRenderer render;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component c = render.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
			return c;
		}

	}


}
