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

import java.util.EventObject;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.RepartTypeGroupe;
import org.cocktail.superplan.client.metier.Secretariat;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.VTreeObjet;
import org.cocktail.superplan.client.metier.VTreeSalles;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class JTreeStructureUlr extends JScrollPane implements TreeModelListener {
	private static final long serialVersionUID = 7600723085028344005L;

	private static final String DEFAULT_ROOT = "Racine";

	JTree tree;
	Object root;
	EOEditingContext eContext;
	private StructuresAutoriseesModel treeModel;
	EOQualifier qualifierRestriction;
	Vector pathList;
	protected NSArray structures;
	private int type;
	public IndividuUlr individu;

	public JTreeStructureUlr(EOEditingContext ec, int type, IndividuUlr individu) {
		super();
		pathList = new Vector();
		eContext = ec;
		root = DEFAULT_ROOT;
		this.individu = individu;
		this.type = type;
		treeModel = new StructuresAutoriseesModel();
		tree = new JTree(treeModel);

		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(renderer.getClosedIcon());
		tree.setCellRenderer(renderer);

		tree.addTreeExpansionListener(new StructuresAutoriseesTreeExpansionListener());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeModel.generate();

		tree.setVisible(true);
		this.setViewportView(tree);
		tree.addTreeSelectionListener(new StructuresAutoriseesTreeSelectionListener());
		// System.out.println("type dans new " + type);
	}

	public void addTreeChangeSelectionListener(TreeChangeSelectionListener l) {
		this.listenerList.add(TreeChangeSelectionListener.class, l);
	}

	public void addTreeSelectionListener(TreeSelectionListener listener) {
		tree.addTreeSelectionListener(listener);
	}

	protected void fireTreeDidChangeSelection() {
		Object selection = selectedObject();
		if (selection == null) {
			return;
		}
		TreeChangeSelectionListener[] listeners = listenerList.getListeners(TreeChangeSelectionListener.class);

		EventObject e = new EventObject(selection);
		for (int i = listeners.length - 1; i >= 0; i--) {
			listeners[i].treeSelectionChanged(e);
		}
	}

	/**
	 * 
	 * 
	 * 
	 */

	public TreePath treePathForStructure(StructureUlr record) {
		TreePath path = null;

		if (record != null) {
			Vector vector = new Vector(), inverse = new Vector();

			StructureUlr tmpRecord = record;
			do {
				vector.add(tmpRecord);
				tmpRecord = tmpRecord.pere();
			}
			while (tmpRecord != null && tmpRecord.cStructure() != null);

			vector.add(root);
			for (int i = vector.size(); i > 0; i--) {
				inverse.add(vector.elementAt(i - 1));
			}
			path = new TreePath(inverse.toArray());
		}
		else {
			path = null;
		}
		return path;
	}

	public void setSelectionPath(TreePath path) {
		tree.setSelectionPath(path);
		tree.scrollPathToVisible(path);
	}

	public void setSelectionPaths(TreePath[] paths) {
		tree.setSelectionPaths(paths);
		tree.scrollPathToVisible(paths[0]);
	}

	public void setSelectedObject(StructureUlr record) {
		TreePath path = treePathForStructure(record);
		tree.setSelectionPath(path);
		tree.scrollPathToVisible(path);
	}

	public void selectKey(Object value) {
		EOKeyValueQualifier qualifier = new EOKeyValueQualifier(StructureUlr.C_STRUCTURE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, value);
		NSArray tmp = StructureUlr.fetchStructureUlrs(eContext, qualifier, null);

		if (tmp.count() > 0) {
			setSelectedObject((StructureUlr) tmp.objectAtIndex(0));
		}

	}

	public void setEnabled(boolean tof) {
		tree.setEnabled(tof);
	}

	private class StructuresAutoriseesModel implements TreeModel {

		NSArray rootChilds;
		Vector listenerVector;

		/**
		 * 
		 */
		public StructuresAutoriseesModel() {
			listenerVector = new Vector();
		}

		public void generate() {
			fireTreeStructureChanged();
		}

		private void fireTreeStructureChanged() {
			int count = listenerVector.size();
			for (int i = 0; i < count; i++) {
				((TreeModelListener) listenerVector.elementAt(i)).treeStructureChanged(null);
			}
		}

		public Object getRoot() {
			return root;
		}

		/** retourne les fils du groupe struct ou les groupes de 1er niveau si struct==null */
		private NSArray _filsStructure(EOGenericRecord struct) {
			EOQualifier qualifier;
			if (struct == null) {
				if (type == 1 || type == 3) {
					qualifier = EOQualifier.qualifierWithQualifierFormat(StructureUlr.C_TYPE_STRUCTURE_KEY + " = 'E'", null);
					NSArray temp = StructureUlr.fetchStructureUlrs(
							eContext,
							qualifier,
							new NSArray(EOSortOrdering.sortOrderingWithKey(StructureUlr.LL_STRUCTURE_KEY,
									EOSortOrdering.CompareCaseInsensitiveAscending)));
					return temp;
				}
				if (type == 2) {
					qualifier = EOQualifier.qualifierWithQualifierFormat(StructureUlr.LC_STRUCTURE_KEY + " = 'DEPOS'", null);
					NSArray temp = StructureUlr.fetchStructureUlrs(
							eContext,
							qualifier,
							new NSArray(EOSortOrdering.sortOrderingWithKey(StructureUlr.LL_STRUCTURE_KEY,
									EOSortOrdering.CompareCaseInsensitiveAscending)));
					return temp;
				}
				if (type == 4) {
					NSMutableArray args = new NSMutableArray();
					args.addObject(individu);
					qualifier = EOQualifier.qualifierWithQualifierFormat("(" + StructureUlr.REPART_TYPE_GROUPE_KEY + "."
							+ RepartTypeGroupe.TGRP_CODE_KEY + " = 'A' or " + StructureUlr.REPART_TYPE_GROUPE_KEY + "."
							+ RepartTypeGroupe.TGRP_CODE_KEY + " = 'AG') and " + StructureUlr.REPART_STRUCTURES_KEY + "."
							+ RepartStructure.INDIVIDU_ULR_KEY + " = %@", args);
					NSArray temp = StructureUlr.fetchStructureUlrs(
							eContext,
							qualifier,
							new NSArray(EOSortOrdering.sortOrderingWithKey(StructureUlr.LL_STRUCTURE_KEY,
									EOSortOrdering.CompareCaseInsensitiveAscending)));
					return temp;
				}
				if (type == 5) {
					NSMutableArray args = new NSMutableArray();
					args.addObject(individu);
					args.addObject(individu);
					qualifier = EOQualifier.qualifierWithQualifierFormat(StructureUlr.REPART_TYPE_GROUPE_KEY + "." + RepartTypeGroupe.TGRP_CODE_KEY
							+ " = 'S' and ( " + StructureUlr.SECRETARIATS_KEY + "." + Secretariat.INDIVIDU_ULR_KEY + " = %@ or "
							+ StructureUlr.RESPONSABLE_KEY + " = %@)", args);
					NSArray temp = StructureUlr.fetchStructureUlrs(
							eContext,
							qualifier,
							new NSArray(EOSortOrdering.sortOrderingWithKey(StructureUlr.LL_STRUCTURE_KEY,
									EOSortOrdering.CompareCaseInsensitiveAscending)));
					return temp;
				}
				if (type == 6) {
					return VTreeObjet.firstColumnObjets(eContext);

				}
				if (type == 7) {
					qualifier = EOQualifier.qualifierWithQualifierFormat(VTreeSalles.C_STRUCTURE_PERE_KEY + " = '0'", null);
					NSArray temp = VTreeSalles.fetchVTreeSalleses(
							eContext,
							qualifier,
							new NSArray(EOSortOrdering.sortOrderingWithKey(VTreeSalles.LL_STRUCTURE_KEY,
									EOSortOrdering.CompareCaseInsensitiveAscending)));
					return temp;
				}
			}
			else {
				NSArray temp = new NSArray();
				if (type < 6) {
					temp = ((StructureUlr) struct).fils();
				}
				if (type == 6) {
					// temp = ((VTreeObjets) struct).toObjetFils();
					return ((VTreeObjet) struct).sortedChildren();
				}
				if (type == 7) {
					temp = ((VTreeSalles) struct).toSalleFils();
				}
				NSArray tri = new NSArray(EOSortOrdering.sortOrderingWithKey("llStructure", EOSortOrdering.CompareCaseInsensitiveAscending));
				return EOSortOrdering.sortedArrayUsingKeyOrderArray(temp, tri);
			}
			return null;
		}

		public Object getChild(Object arg0, int arg1) {
			if (arg0 == root) {
				return _filsStructure(null).objectAtIndex(arg1);
			}
			else {
				return _filsStructure((EOGenericRecord) arg0).objectAtIndex(arg1);
			}
		}

		public int getChildCount(Object arg0) {
			if (arg0 == root) {
				return _filsStructure(null).count();
			}
			else {
				return _filsStructure((EOGenericRecord) arg0).count();
			}
		}

		public boolean isLeaf(Object arg0) {
			/*
			 * if (arg0 == root) { return false; } else { if (arg0 instanceof EOGenericRecord) { return
			 * _filsStructure((EOGenericRecord)arg0).count()==0; } else { return true; } }
			 */
			if (arg0 instanceof VTreeSalles) {
				VTreeSalles vs = (VTreeSalles) arg0;
				if (vs.persId().intValue() == 0) {
					return true;
				}
				else {
					return false;
				}
			}
			/*
			 * if (arg0 instanceof VTreeObjets) { VTreeObjets vo = (VTreeObjets) arg0; if (vo.persId().intValue() == 0) { return true; }
			 * else { return false; } }
			 */
			if (arg0 instanceof VTreeObjet) {
				VTreeObjet objet = (VTreeObjet) arg0;
				if (objet.niveau().intValue() == VTreeObjet.NIVEAU_OBJET) {
					return true;
				}
				else {
					return false;
				}
			}

			return false;
		}

		public void valueForPathChanged(TreePath arg0, Object arg1) {
		}

		public int getIndexOfChild(Object arg0, Object arg1) {
			if (arg0 == null || arg1 == null) {
				return -1;
			}
			int index;
			if (arg0 == root) {
				index = _filsStructure(null).indexOfObject(arg1);
				if (index == NSArray.NotFound) {
					return -1;
				}
				return index;
			}
			else {
				index = _filsStructure((EOGenericRecord) arg0).indexOfObject(arg1);
				if (index == NSArray.NotFound) {
					return -1;
				}
				return index;
			}
		}

		public void addTreeModelListener(TreeModelListener arg0) {
			listenerVector.add(arg0);
		}

		public void removeTreeModelListener(TreeModelListener arg0) {
			listenerVector.remove(arg0);
		}

	}

	public class StructuresAutoriseesTreeExpansionListener implements TreeExpansionListener {
		public StructuresAutoriseesTreeExpansionListener() {
		}

		public void treeExpanded(TreeExpansionEvent event) {
			TreePath path = event.getPath();
			pathList.add(path);
		}

		public void treeCollapsed(TreeExpansionEvent event) {
			TreePath path = event.getPath();
			pathList.remove(path);
		}

	}

	/** **************************************************************************************************************** */
	public class StructuresAutoriseesTreeSelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			fireTreeDidChangeSelection();
		}
	}

	public void treeNodesChanged(TreeModelEvent arg0) {
	}

	public void treeNodesInserted(TreeModelEvent arg0) {
	}

	public void treeNodesRemoved(TreeModelEvent arg0) {
	}

	public void treeStructureChanged(TreeModelEvent arg0) {
		tree.treeDidChange();
	}

	public EOGenericRecord selectedObject() {
		Object selection = tree.getLastSelectedPathComponent();
		if (selection instanceof EOGenericRecord) {
			return (EOGenericRecord) selection;
		}
		else {
			return null;
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public IndividuUlr getIndividu() {
		return individu;
	}

	public void setIndividu(IndividuUlr individu) {
		this.individu = individu;
	}
}
