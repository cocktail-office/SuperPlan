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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

/**
 * Table qui affiche un modele de type {@link org.cocktail.zutil.client.wo.ZEOTableModel}. Le modele en question peut etre �ventuellement
 * contenu dans un modele de type TableSorter.<br>
 * Pour affecter un renderer, d�rivez ZEOTableCellRenderer et surchargez les deux m�thodes de la classe.
 * 
 * N.B: Pour avoir un scrolling horizontal, appeler la m�thode <b>setAutoResizeMode(JTable.AUTO_RESIZE_OFF);</b> sur la table.
 * 
 * @author Rodolphe Prin
 */
public class ZEOTable extends MyJTable {
	private final ArrayList myListeners = new ArrayList();
	private JPopupMenu popup;
	private JPopupMenu popupHeader;

	private IZEOTableCellRenderer myRenderer;
	private MouseListener popupListener;
	private MouseListener popupHeaderListener;

	/**
	 * @param dm
	 */
	public ZEOTable(TableModel dm) {
		super(dm);
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		initGUI();
	}

	/**
	 * Utiliser plutot ZEOTableModelColumn.setTableCellRenderer pour chaque colonne.
	 * 
	 * @deprecated
	 * @param dm
	 * @param renderer
	 */
	public ZEOTable(TableModel dm, IZEOTableCellRenderer renderer) {
		super(dm);
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		initGUI();
		if (renderer != null) {
			setMyRenderer(renderer);
		}
	}

	public final void initGUI() {
		ToolTipManager.sharedInstance().unregisterComponent(this);
		ToolTipManager.sharedInstance().unregisterComponent(this.getTableHeader());
	}

	/**
	 * Repond � la selection dans la table.
	 * 
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(final ListSelectionEvent e) {
		super.valueChanged(e);
		final ListSelectionModel lsm = (ListSelectionModel) e.getSource();

		// Dans ca cas, getValueIsAdjusting est false quand l'utilisateur relache la souris/clavier
		// ca doit �tre plus performant
		final boolean isAdjusting = e.getValueIsAdjusting();

		if (!isAdjusting) {
			if (lsm.isSelectionEmpty()) {
				getDataModel().updateSelection(null);
			}
			else {
				final int minIndex = lsm.getMinSelectionIndex();
				final int maxIndex = lsm.getMaxSelectionIndex();
				// Vector selections = new Vector();
				final ArrayList selections = new ArrayList();
				for (int i = minIndex; i <= maxIndex; i++) {

					if (lsm.isSelectedIndex(i)) {
						selections.add(new Integer(getRowIndexInModel(i)));
					}
				}
				getDataModel().updateSelectedIndexes(new NSArray(selections.toArray()));
			}
			// On notifie du changement de selection qu'une fois que l'utilisateur a relach� la souris/clavier
			fireOnSelectionChanged();
		}

		// a tester... peut etre remettre
		// fireOnSelectionChanged();
	}

	/**
	 * Preselectionne des rows de la tables.
	 * 
	 * @param indexes
	 */
	public void forceNewSelection(final NSArray indexes) {
		getSelectionModel().clearSelection();
		for (int i = 0; i < indexes.count(); i++) {
			final Integer element = (Integer) indexes.objectAtIndex(i);
			// changeSelection(element.intValue(),0,false,true);
			getSelectionModel().addSelectionInterval(element.intValue(), element.intValue());
		}
		scrollToSelection();
	}

	public void forceNewSelection(final int[] indexes) {
		getSelectionModel().clearSelection();
		if (indexes == null || indexes.length == 0) {
			return;
		}
		for (int i = 0; i < indexes.length; i++) {
			getSelectionModel().addSelectionInterval(indexes[i], indexes[i]);
		}
		scrollToSelection();
	}

	/**
	 * Preselectionne des objets dans la table
	 * 
	 * @param objects
	 */
	public void forceNewSelectionOfObjects(final NSArray objects) {
		getSelectionModel().clearSelection();
		final NSMutableArray sels = new NSMutableArray();
		if (objects != null) {
			for (int i = 0; i < objects.count(); i++) {
				final Object element = objects.objectAtIndex(i);
				int r = getDataModel().getMyDg().displayedObjects().indexOfObject(element);
				if (r != -1) {
					sels.addObject(new Integer(getRowIndexInTableFromModel(r)));
				}
			}
		}
		forceNewSelection(sels);
	}

	/**
	 * @return L'objet selection dans le display group affich� (se base sur l'inde selectionn� de la table)
	 */
	public Object getSelectedObject() {
		if (getSelectedRow() >= 0) {
			return getDataModel().getMyDg().displayedObjects().objectAtIndex(getRowIndexInModel(getSelectedRow()));
		}
		return null;
	}

	public Object getObjectAtRow(final int rowIndex) {
		if (rowIndex >= 0) {
			return getDataModel().getMyDg().displayedObjects().objectAtIndex(getRowIndexInModel(rowIndex));
		}
		return null;
	}

	/**
	 * Renvoie l'index du row dans le model � partir de son index dans la table.
	 * 
	 * @param i
	 * @return
	 */
	public int getRowIndexInModel(final int i) {
		if (this.dataModel instanceof TableSorter) {
			// on transforme les index en ceux du modele d'origine (non tries)
			return ((TableSorter) this.dataModel).modelIndex(i);
		}
		return i;
	}

	/**
	 * Renvoi l'index d'un row � partir de son index dans le model
	 * 
	 * @param i
	 * @return
	 */
	public int getRowIndexInTableFromModel(final int i) {
		if (this.dataModel instanceof TableSorter) {
			// on transforme les index en ceux du modele d'origine (non tries)
			return ((TableSorter) this.dataModel).viewIndex(i);
		}
		return i;
	}

	public ZEOTableModel getDataModel() {
		if (this.dataModel instanceof TableSorter) {
			return (ZEOTableModel) ((TableSorter) this.dataModel).getTableModel();
		}
		return (ZEOTableModel) this.dataModel;
	}

	public class DataTableMouseListener extends MouseAdapter {
		public void mouseClicked(final MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (e.getClickCount() == 2 && getDataModel().getSelectedEOObjects() != null) {
					fireOnDbClick();
				}
			}
		}
	}

	public interface ZEOTableListener {
		public void onDbClick();

		public void onSelectionChanged();
	}

	/**
	 * Ajoute un listener � ce composant.
	 * 
	 * @param listener
	 */
	public void addListener(final ZEOTableListener listener) {
		myListeners.add(listener);
	}

	/**
	 * Supprime un listener de ce composant.
	 * 
	 * @param listener
	 */
	public void removeListener(final ZEOTableListener listener) {
		myListeners.remove(listener);
	}

	private void fireOnDbClick() {
		final int len = myListeners.size();
		for (int i = 0; i < len; i++) {
			((ZEOTableListener) myListeners.get(i)).onDbClick();
		}
	}

	private void fireOnSelectionChanged() {
		if (myListeners != null) {
			final int len = myListeners.size();
			for (int i = 0; i < len; i++) {
				((ZEOTableListener) myListeners.get(i)).onSelectionChanged();
			}
		}
	}

	/**
	 * @param preferedWidths
	 */
	private void initColumnWidth() {
		// Integer tmpwidth;
		for (int indexColone = 0; indexColone < this.getColumnModel().getColumnCount(); indexColone++) {
			final int l = getDataModel().getColumn(convertColumnIndexToModel(indexColone)).getPreferredWidth();
			// System.out.println(indexColone+" preferedwidth:"+l);
			if (l != -1) {
				this.getColumnModel().getColumn(indexColone).setPreferredWidth(l);
				final boolean isResizable = getDataModel().getColumn(convertColumnIndexToModel(indexColone)).isResizable();
				this.getColumnModel().getColumn(indexColone).setResizable(isResizable);
				if (!isResizable) {
					this.getColumnModel().getColumn(indexColone).setMaxWidth(l);
					this.getColumnModel().getColumn(indexColone).setMinWidth(l);
				}
			}
		}
	}

	public void setModel(TableModel dataModel) {
		super.setModel(dataModel);

		initColumnWidth();
		initColumnEditors();
		initColumnRenderers();
		setMyRenderer(myRenderer);
		this.addMouseListener(new DataTableMouseListener());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		buildPopupHeader();

	}

	/**
	 *
	 */
	private void initColumnEditors() {
		for (int indexColone = 0; indexColone < this.getColumnModel().getColumnCount(); indexColone++) {
			final TableCellEditor editor = getDataModel().getColumn(convertColumnIndexToModel(indexColone)).getTableCellEditor();
			if (editor != null) {
				this.getColumnModel().getColumn(indexColone).setCellEditor(editor);
			}
		}
	}

	/**
	 * Affecte � la colonne le renderer affect� � son modele.
	 * 
	 */
	public void initColumnRenderers() {
		for (int indexColone = 0; indexColone < this.getColumnModel().getColumnCount(); indexColone++) {
			final TableCellRenderer editor = getDataModel().getColumn(convertColumnIndexToModel(indexColone)).getTableCellRenderer();
			if (editor != null) {
				this.getColumnModel().getColumn(indexColone).setCellRenderer(editor);
			}
		}
	}

	public void initColumnRenderers(final IZEOTableCellRenderer renderer) {
		for (int indexColone = 0; indexColone < this.getColumnModel().getColumnCount(); indexColone++) {
			// final TableCellRenderer editor = getDataModel().getColumn(convertColumnIndexToModel(indexColone)).getTableCellRenderer();
			if (renderer != null) {
				this.getColumnModel().getColumn(indexColone).setCellRenderer(renderer);
			}
		}
	}

	/**
	 * Listener polur g�rer l'affichage d'un popupMenu sur clic droit.
	 */
	class PopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(final MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(final MouseEvent e) {
			if (e.isPopupTrigger()) {
				if (popup != null) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}

	/**
	 * Listener polur g�rer l'affichage d'un popupMenu sur clic droit.
	 */
	class PopupHeaderListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(final MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(final MouseEvent e) {
			if (e.isPopupTrigger()) {
				if (popupHeader != null) {
					popupHeader.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}

	public JPopupMenu getPopup() {
		return popup;
	}

	public void setPopup(final JPopupMenu menu) {
		popup = menu;
		if (menu != null) {
			if (popupListener == null) {
				popupListener = new PopupListener();
			}
			this.addMouseListener(popupListener);
		}
	}

	public void cacheObjects() {
		final int nbRows = getModel().getRowCount();
		final int nbCols = getModel().getColumnCount();
		for (int i = 0; i < nbRows; i++) {
			for (int j = 0; j < nbCols; j++) {
				getModel().getValueAt(i, j);
			}
		}
	}

	public final void setMyRenderer(final IZEOTableCellRenderer renderer) {
		initColumnRenderers(renderer);
	}

	/**
	 * Met � jour l'affichage de la table en mettant d'abord � jour le modele et en synchronisant l'affichage de la selection avec le
	 * displaygroup du modele. A appeler lorsque les donn�es du modele ont chang�.
	 */
	public void updateData() {
		getDataModel().updateInnerRowCount();
		// On memorise les preselections car l'appel � fireTableDataChanged() vide la selection dans le displaygroup
		// final NSArray<Integer> selectionIndex = getDataModel().getMyDg().selectionIndexes();
		NSArray selectedEOObjects = getDataModel().getSelectedEOObjects();
		getDataModel().fireTableDataChanged();
		// forceNewSelection(selectionIndex);
		forceNewSelectionOfObjects(selectedEOObjects);
	}

	public void clearData() {
		getDataModel().getMyDg().setObjectArray(null);
		updateData();
	}

	public boolean stopCellEditing() {
		if (isEditing()) {
			return getCellEditor().stopCellEditing();
		}
		return true;
	}

	public void cancelCellEditing() {
		if (isEditing()) {
			getCellEditor().cancelCellEditing();
		}
	}

	protected void buildPopupHeader() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem("Elastique");
		menuItem.setSelected(getAutoResizeMode() == AUTO_RESIZE_ALL_COLUMNS);
		menuItem.addItemListener(new MyMenuItemListener());

		popupHeader = new JPopupMenu();
		popupHeader.add(menuItem);
		popupHeader.addSeparator();

		setPopupHeader(popupHeader);
	}

	private final class MyMenuItemListener implements ItemListener {

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent e) {
			JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getItem();
			if (menuItem.isSelected()) {
				setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
			}
			else {
				setAutoResizeMode(AUTO_RESIZE_OFF);
			}
		}

	}

	public void scrollToVisible(int rowIndex, int vColIndex) {
		if (!(this.getParent() instanceof JViewport)) {
			return;
		}
		final JViewport viewport = (JViewport) getParent();
		final Rectangle rect = this.getCellRect(rowIndex, vColIndex, true);
		final Point pt = viewport.getViewPosition();
		rect.setLocation(rect.x - pt.x, rect.y - pt.y);
		viewport.scrollRectToVisible(rect);
	}

	public void scrollToSelection() {
		scrollToVisible(getSelectedRow(), 0);
	}

	public void scrollToSelection(Object obj) {
		forceNewSelectionOfObjects(new NSArray(new Object[] { obj }));
		scrollToSelection();
	}

	public static class ZImageCell extends JPanel {
		final Image[] _images;

		public ZImageCell(final Image[] images) {
			this._images = images;
			setBackground(Color.WHITE);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // paint background

			for (int i = 0; i < _images.length; i++) {
				final Image img = _images[i];
				g.drawImage(img, 15 * i, 0, this);
			}
		}
	}

	public JPopupMenu getPopupHeader() {
		return popupHeader;
	}

	public void setPopupHeader(JPopupMenu menu) {
		this.popupHeader = menu;
		if (menu != null) {
			if (popupHeaderListener == null) {
				popupHeaderListener = new PopupHeaderListener();
			}
			if (this.getTableHeader() != null) {
				this.getTableHeader().addMouseListener(popupHeaderListener);
			}
		}
	}
}
