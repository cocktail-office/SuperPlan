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

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSSelector;

public class GenericListHandler extends JScrollPane {

	private static final long serialVersionUID = -3115126853011781213L;
	private JList list;
	private MyListModel listModel;
	private Object owner;
	private String callback;

	public GenericListHandler() {
		super();
		init();
	}

	public GenericListHandler(Object owner, String acallback) {
		super();
		this.owner = owner;
		this.callback = acallback;
		init();
	}

	private void init() {
		listModel = new MyListModel();
		list = new JList(listModel);
		// list.setCellRenderer(new MyCellRenderer());
		list.setVisibleRowCount(1);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.setViewportView(list);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new MyListListener());
	}

	public JList getJList() {
		return list;
	}

	// UP : 24/11/2008 & 06/07/2009
	public NSArray getSelectedItems() {
		Object[] selObjects = list.getSelectedValues();

		System.out.println("selObjects=" + selObjects);
		NSMutableArray listeSelection = new NSMutableArray();

		Object current;
		for (int i = 0; i < selObjects.length; i++) {
			current = selObjects[i];
			if (current != null) {
				listeSelection.addObject(current);
			}
			System.out.println(">>" + selObjects[i]);
		}

		return listeSelection;
	}

	public Object getSelectedItem() {
		return list.getSelectedValue();
	}

	public void setObjects(NSArray objects) {
		if (objects != null) {
			listModel.setObjects(objects);
		}
		else {
			listModel.setObjects(new NSArray());
		}
	}

	/** Listener de changement de selection */
	private class MyListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (!event.getValueIsAdjusting()) {

				if (callback != null && owner != null) {
					Object selection = list.getSelectedValue();
					try {
						NSSelector.invoke(callback, new Class[] { Object.class }, owner, new Object[] { selection });
					}
					catch (Exception exe) {
						exe.printStackTrace();
					}
				}
			}
		}
	}

	/** classe model du JList */
	private class MyListModel extends AbstractListModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3738488467562203492L;
		NSArray data = new NSArray();
		int size = 0;

		public void setObjects(NSArray objects) {
			this.data = objects;
			size = objects.count();
			fireContentsChanged(this, 0, size);
		}

		public int getSize() {
			return size;
		}

		public Object getElementAt(int index) {
			if (data.count() > index) {
				return data.objectAtIndex(index);
			}
			else {
				return null;
			}
		}
	}

}
