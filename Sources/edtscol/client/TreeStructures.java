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
package edtscol.client;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.cocktail.superplan.client.metier.VGroupePersonne;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class TreeStructures extends JScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1230016052620801861L;
	// private Date debut;
	private EOEditingContext editingContext;
	private EOQualifier qualifierForFirstColumn, restrictionQualifier;
	public JTree tree;
	StructureTreeModel treeModel;

	public NSArray allFeuilles;// ying ajouter

	public TreeStructures(EOEditingContext edContext, EOQualifier firstColumn, EOQualifier restriction) {
		super();
		editingContext = edContext;
		qualifierForFirstColumn = firstColumn;
		restrictionQualifier = restriction;
		treeModel = new StructureTreeModel(qualifierForFirstColumn, restrictionQualifier);
		tree = new JTree(treeModel);
		// tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

		tree.setVisible(true);
		setViewportView(tree);

		treeModel.generate();

	}

	public void addTreeChangeSelectionListener(TreeChangeSelectionListener l) {
		this.listenerList.add(TreeChangeSelectionListener.class, l);
	}

	public void addTreeSelectionListener(TreeSelectionListener listener) {
		tree.addTreeSelectionListener(listener);
	}

	public Object selectedObject() {
		Object objet = tree.getLastSelectedPathComponent();
		return objet;
	}

	public NSArray selectedNotes() {
		/*
		 * if(!treeModel.isLeaf(tree.getLastSelectedPathComponent()))//cas node return null; else{
		 */
		NSMutableArray allObjectsSelectionned = new NSMutableArray();
		int nbrNodeSelected = tree.getSelectionCount();
		int[] allFeuillesSelectionned = new int[nbrNodeSelected];//
		allFeuillesSelectionned = tree.getSelectionRows();
		// System.out.println("getMinSelectionRow="+tree.getMinSelectionRow());
		for (int i = 0; i < nbrNodeSelected; i++) {
			allObjectsSelectionned.addObject((tree.getPathForRow(allFeuillesSelectionned[i])).getLastPathComponent());
		}
		// System.out.println("allObjectsSelectionned="+allObjectsSelectionned);

		return allObjectsSelectionned;
		// }

	}

	public void searchFeilles(Object node, NSMutableArray desFeuilles) {
		if (treeModel.isLeaf(node)) {// cas feuille
			desFeuilles.addObject(node);
		}
		else {
			int nbrChilds = treeModel.getChildCount(node);
			for (int i = 0; i < nbrChilds; i++) {
				Object theChild = treeModel.getChild(node, i);
				searchFeilles(theChild, desFeuilles);
			}
		}

	}

	public NSArray getAllFeuilles(Object node) {// ying ajouter
		NSMutableArray allFeuilles = new NSMutableArray();
		searchFeilles(node, allFeuilles);
		return allFeuilles;
	}

	public NSArray getAllFeuilles_MultiSelect(NSArray nodes) {// ying ajouter pour la multi-selections
		NSMutableArray allFeuilles = new NSMutableArray();
		for (int i = 0; i < nodes.count(); i++) {
			searchFeilles(nodes.objectAtIndex(i), allFeuilles);
		}
		return allFeuilles;
	}

	/**
	 * @author jfguilla
	 * 
	 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
	 *         Comments
	 */
	private class StructureTreeModel implements TreeModel {
		private NSArray firstColumn;
		private Vector treeModelListeners;

		String rootNode = "Liste des services ou groupes";
		EOQualifier qualifierForFirstColumn;
		EOQualifier restrictionQualifier;

		// EOSortOrdering sortLibelle;
		// NSMutableArray listeSorts;

		/**
		 * 
		 */
		public StructureTreeModel(EOQualifier firstColumn, EOQualifier restriction) {
			super();
			qualifierForFirstColumn = firstColumn;
			restrictionQualifier = restriction;
			// sortLibelle = EOSortOrdering.sortOrderingWithKey(StructureUlr.LL_STRUCTURE_KEY, EOSortOrdering.CompareAscending);

			// listeSorts = new NSMutableArray();
			// listeSorts.addObject(sortLibelle);

			treeModelListeners = new Vector();
			generate();
		}

		/**
		 * 
		 */
		private void generate() {
			fireTreeStructureChanged(rootNode);
		}

		/**
		 * The only event raised by this model is TreeStructureChanged with the root as path, i.e. the whole tree has changed.
		 */
		protected void fireTreeStructureChanged(Object changedNode) {
			int len = treeModelListeners.size();
			TreeModelEvent e = new TreeModelEvent(this, new Object[] { changedNode });
			for (int i = 0; i < len; i++) {
				((TreeModelListener) treeModelListeners.elementAt(i)).treeStructureChanged(e);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeModel#getRoot()
		 */
		public Object getRoot() {
			return rootNode;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
		 */
		public Object getChild(Object parent, int index) {
			Object returnedValue;
			if (parent == rootNode) {
				NSArray listeChilds = firstColumn();
				// System.out.println("listeChilds.objectAtIndex(index) " + (listeChilds.objectAtIndex(index)).getClass());
				returnedValue = listeChilds.objectAtIndex(index);
			}
			else {
				NSArray childs = ((VGroupePersonne) parent).structuresFilsSansCycle();// structuresFils();
				// System.out.println("fils " + index + " " + childs.objectAtIndex(index));
				returnedValue = childs.objectAtIndex(index);
			}
			return returnedValue;
		}

		/**
		 * 
		 */
		private NSArray firstColumn() {
			if (firstColumn == null) {
				EOFetchSpecification spec = new EOFetchSpecification(VGroupePersonne.ENTITY_NAME, qualifierForFirstColumn, null);
				firstColumn = editingContext.objectsWithFetchSpecification(spec);
			}
			return firstColumn;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
		 */
		public int getChildCount(Object parent) {
			int count;
			if (parent == rootNode) {
				count = firstColumn().count();
			}
			else {
				// System.out.println("classe dee treeSalle " + parent.getClass());
				NSArray childs = ((VGroupePersonne) parent).structuresFilsSansCycle();// structuresFils();
				count = childs.count();
			}
			return count;
		}

		public boolean isLeaf(Object arg0) {
			if (arg0 instanceof VGroupePersonne) {
				VGroupePersonne struc = (VGroupePersonne) arg0;
				if (struc.cStructure().startsWith("P")) {
					return true;
				}
			}
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
		 */
		public void valueForPathChanged(TreePath arg0, Object arg1) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
		 */
		public int getIndexOfChild(Object parent, Object child) {
			NSArray childs = null;
			int result = -1;

			if (parent == rootNode) {
				childs = firstColumn();
			}
			else {
				childs = ((VGroupePersonne) parent).structuresFilsSansCycle();// structuresFils();
			}

			if (childs != null) {
				result = childs.indexOfObject(child);
				if (result == NSArray.NotFound) {
					result = -1;
				}
			}

			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
		 */
		public void addTreeModelListener(TreeModelListener arg0) {
			treeModelListeners.addElement(arg0);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
		 */
		public void removeTreeModelListener(TreeModelListener arg0) {
			treeModelListeners.removeElement(arg0);
		}

	}

}
