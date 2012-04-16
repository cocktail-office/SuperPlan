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
package fr.univlr.cri.javaclient;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedDataSource;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOView;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotificationCenter;

/**
 * Affichage d'une vue arborescente dans un JTree.<br>
 * <br>
 * Similaire au fonctionnement du CRITreeView mais nécessite une vue associee pour s'afficher.<br>
 * Utiliser de preference cette classe au CRITreeView car le CRITreeView ne permet pas l'affichage de menus contextuels<br>
 * <br>
 * 
 * Dans le fichier .nib, declarer un outlet <i>treeTable</i> connecte a l'EODisplayGroup associe et un outlet <i>treeView</i> connecte a
 * l'EOView ou l'arbre doit s'afficher.<br>
 * <br>
 * 
 * Implemente la construction de l'arbre a partir des cles et cle pere comme dans le NSBrowser.<br>
 * Pour le moment : charge l'arbre de facon statique au lancement de initialize().<br>
 * Cree un arbre a partir des elements (key et parentKey) ou parentRelationship, affiche les valeurs contenues dans le champ
 * fieldForDisplay.<br>
 * Lorsque l'utilisateur clique ou doubleClique sur un noeud, une notification est envoyee (CRITreeViewDidClick ou
 * CRITreeViewDidDoubleClick). Le dictionnaire userInfo de la notification contient l'enregistrement selectionne et un booleen correspondant
 * a isLeaf :<br>
 * - cles : "selectedRecord" (l'enregistrement correspondant au noeud selectionne) & "isLeaf".<br>
 * - setRootVisible(boolean) permet de definir si le noeud racine doit etre affiche ou non<br>
 * - setRestrictionQualifier(EOQualifier) permet de definir le qualifier qui sera applique a tous les enregistrements (par ex : username =
 * 'nom').<br>
 * - setQualifierForFirstColumn(EOQualifier) permet de definir le qualifier utilise pour les noeuds du permier niveau<br>
 * - selectedObject() permet de recuperer l'objet selectionne dans le displayGroup associe<br>
 * <br>
 * Remarque importante : dans l'implementation presente, le "fecth on load" ne doit pas etre active si la table ne contient pas tous les
 * enregistrements (si le restriction qualifier ne contient pas tous les enregistrements de la table).<br>
 */
public class CRITree extends Object {
	public static final String TREE_DID_CLICK = "CRITreeDidClick";
	public static final String TREE_DID_DOUBLE_CLICK = "CRITreeDidDoubleClick";

	// DisplayGroup lie a la table concernee et la view conteneur
	public EOView treeView;
	public EODisplayGroup treeTable;
	// les elements graphiques
	protected JScrollPane theScrollPane;
	// protected JTree theTree;
	protected JTree theTree;
	// les attributs
	protected CRITreeNode rootNode;
	protected String key, parentKey, fieldForDisplay, tableName, title = "Cat\u00e9gorie";
	protected String parentRelationship;
	protected boolean isDynamic, isRootVisible, isRootSelected;
	protected EOQualifier restrictionQualifier, qualifierForFirstColumn;

	/**
	 * Creation d'un tree view vide
	 */
	public CRITree() {
		// comportements par defaut
		rootNode = null;
		isDynamic = false;
		isRootVisible = false;
	}

	/**
	 * Creation d'un tree view avec noeud racine defini
	 */
	public CRITree(Object rootObject) {
		isDynamic = false;
		isRootVisible = false;
		rootNode = new CRITreeNode(rootObject);
	}

	public EOView treeView() {
		return treeView;
	}

	/**
	 * Initialisation du treeView : Tous les attributs doivent avoir ete initialises : key, parentKey, fieldForDisplay,
	 * qualifierForFirstColumn et, optionellement, restrictionQualifier
	 */
	public boolean initialize(boolean selectFirst) {

		// Verifier que treeTable est bien definie
		if (treeTable == null) {
			NSLog.out.appendln(this.getClass().getName() + " : la table 'treeTable' n'est pas definie");
			return false;
		}
		// Controle des attributs qui doivent imperativement etre definis
		if ((((key == null) || (parentKey == null)) && (parentRelationship == null)) || (fieldForDisplay == null)) {
			NSLog.out.appendln(this.getClass().getName()
					+ " : un ou plusieurs attributs obligatoires ((key ou parenRelationship), parentKey, fieldForDisplay) ne sont pas definis");
			return false;
		}
		// Verifier que le choix est clair entre key, parentRelationship est
		// bien clair !!!
		if ((key != null) && (parentRelationship != null)) {
			NSLog.out.appendln(this.getClass().getName()
					+ " : les attributs key et parentRelationship sont incompatibles, l'une des deux doit etre nulle.");
			return false;
		}

		// Chargement initial de la table...
		// treeTable.setDelegate(this);
		tableName = ((EODistributedDataSource) treeTable.dataSource()).fetchSpecification().entityName();

		// Si le noeud racine n'a pas encore ete cree...
		if (rootNode == null) {
			rootNode = new CRITreeNode("");
		}

		// Initialisation de l'arbre
		this.update((javax.swing.tree.TreePath) null);
		this.createTree(selectFirst);
		return true;

	}

	/**
	 * Chargement de la table associee a l'arbre a afficher. Tient compte du restrictionQualifier.
	 */
	private void fetchTreeTable() {
		EOFetchSpecification fetchSpec;
		EOSortOrdering aSort = EOSortOrdering.sortOrderingWithKey(fieldForDisplay, EOSortOrdering.CompareCaseInsensitiveAscending);
		NSArray fetchResult;

		// Recherche de tous les enregistrements dont on aura besoin...
		fetchSpec = new EOFetchSpecification(tableName, restrictionQualifier, new NSArray(aSort));
		fetchSpec.setRefreshesRefetchedObjects(true);
		fetchResult = treeTable.dataSource().editingContext().objectsWithFetchSpecification(fetchSpec);
		treeTable.setObjectArray(fetchResult);

		/**
		 * for(int i=0;i<fetchResult.count();i++)
		 * NSLog.out.appendln(">"+((fr.univlr.cri.geide.client.TypeCourrier)fetchResult.objectAtIndex(i)).typeLibelle() );
		 */
	}

	/**
	 * Creation de l'arbre a partir du rootNode
	 */
	private void createTree(boolean selectFirst) {

		// Si c'est a l'initialisation
		if (theScrollPane == null) {
			theTree = new JTree(rootNode);
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setLeafIcon(renderer.getClosedIcon());
			theTree.setCellRenderer(renderer);

			// ajout et affichage de l'arbre
			theTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			theTree.setRootVisible(isRootVisible);
			// theTree.setExpandsSelectedPaths(true);
			theTree.setScrollsOnExpand(true);
			addMouseListener(new CRITreeMouseListener());
			// Creation du scroll pane qui le contient
			theScrollPane = new JScrollPane(theTree);
			treeView.setLayout(new BorderLayout());

			treeView.setBorder(BorderFactory.createEmptyBorder());
			treeView.add(theScrollPane);
			treeView.validate();

		}
		// Un arbre a deja ete cree
		else {
			theTree = new JTree(rootNode);
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setLeafIcon(renderer.getClosedIcon());
			theTree.setCellRenderer(renderer);

			// ajout et affichage de l'arbre
			theTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			theTree.setRootVisible(isRootVisible);
			theTree.setScrollsOnExpand(true);
			addMouseListener(new CRITreeMouseListener());
			theScrollPane = new JScrollPane(theTree);
			treeView.removeAll();
			treeView.add(theScrollPane);
			treeView.validate();
		}
		if (selectFirst) {
			// Selection du premier noeud
			theTree.setSelectionRow(0);
			theTree.expandPath(theTree.getSelectionPath());
			// Notifier la selection
			treeViewDidClick((CRITreeNode) theTree.getLastSelectedPathComponent());
		}
	}

	/**
	 * Retourne un NSArray qui contient les enfants d'un noeuds.
	 */
	protected NSArray findChilds(CRITreeNode theNode) {
		EOQualifier baseQualifier, globalQualifier;
		EOFetchSpecification fetchSpec;
		EOSortOrdering aSort = EOSortOrdering.sortOrderingWithKey(fieldForDisplay, EOSortOrdering.CompareCaseInsensitiveAscending);
		NSMutableArray fetchResult;

		// Si on est au premier niveau
		if (theNode == rootNode) {
			baseQualifier = qualifierForFirstColumn;
		}
		// Les autres niveaux
		else {
			if (key != null) {
				// baseQualifier =
				// EOQualifier.qualifierWithQualifierFormat("%@=%@ and %@<>%@",
				// new NSArray(new Object[]{parentKey,
				// theNode.keyValue(), key, theNode.keyValue()}));
				baseQualifier = EOQualifier.qualifierWithQualifierFormat(parentKey + "=%@ and" + key + " <> %@",
						new NSArray(new Object[] { theNode.keyValue(), theNode.keyValue() }));
			}
			else {
				// baseQualifier = EOQualifier.qualifierWithQualifierFormat("%@
				// = %@", new NSArray(new Object[]{parentRelationship,
				// theNode.record()}));
				baseQualifier = EOQualifier.qualifierWithQualifierFormat(parentRelationship + "= %@", new NSArray(theNode.record()));
			}
		}

		// Recherche des enregistrements dans la table
		if (treeTable.fetchesOnLoad() && restrictionQualifier != null) {
			// Si la table est en fetch on load : il faut appliquer le
			// restriction qualifier (on ne sait jamais)
			globalQualifier = new EOAndQualifier(new NSArray(new Object[] { restrictionQualifier, baseQualifier }));
			fetchSpec = new EOFetchSpecification(tableName, globalQualifier, new NSArray(aSort));
			fetchResult = new NSMutableArray(treeTable.dataSource().editingContext().objectsWithFetchSpecification(fetchSpec));
		}
		else {
			// Si la table est chargee "manuellement" ou qu'il n'y a pas de
			// restriction qualifier
			fetchResult = new NSMutableArray(EOQualifier.filteredArrayWithQualifier(treeTable.allObjects(), baseQualifier));
			EOSortOrdering.sortArrayUsingKeyOrderArray(fetchResult, new NSArray(aSort));
		}
		// On evite de "boucler" en cas de probleme
		if (fetchResult.containsObject(theNode.record())) {
			fetchResult.removeObject(theNode.record());
		}
		return fetchResult;
	}

	/**
	 * Definition de la table treeTable, a utiliser lorsque le treeView n'est pas cree par IB.
	 */
	public void setTreeTable(EODisplayGroup aTable) {
		treeTable = aTable;
	}

	/**
	 * Definition du nom de la table (obligatoire lorsque le fetch on load est desactive.
	 */
	public void setTableName(String name) {
		tableName = name;
	}

	/**
	 * Definit les champs cles et cles pere pour la construction de l'arbre. Cette option est incompatible avec l'utilisation de
	 * parentRelationship.
	 */
	public void setKeyParentKey(String keyValue, String parentKeyValue) {
		if ((keyValue != null) && (parentKeyValue != null)) {
			key = keyValue;
			parentKey = parentKeyValue;
			parentRelationship = null;
			if (qualifierForFirstColumn == null) {
				qualifierForFirstColumn = EOQualifier.qualifierWithQualifierFormat(keyValue + "=" + parentKeyValue, null);
			}
		}
		else {
			key = null;
			parentKey = null;
			NSLog.out.appendln("CRITreeViewMenu.setKeyParentKey() - key = null et/ou parentKey = null");
		}
		// Definition du qualifierForFirstColumn par default
	}

	/**
	 * Definit le nom de la relation fils -> pere pour la construction de l'arbre. Cette option est incompatible avec l'utilisation de key
	 * et parentKey. L'utilisation de parentRelationship est compatible avec l'utilisation des methodes de EOF telles que insertObject(),
	 * addObjectToBtohSidesOfRelationshipWithKey(), etc. car la cle primaire peut etre masquee.
	 */
	public void setParentRelationship(String relationName) {
		if (relationName != null) {
			parentRelationship = relationName;
			key = null;
			parentKey = null;
			if (qualifierForFirstColumn == null) {
				qualifierForFirstColumn = EOQualifier.qualifierWithQualifierFormat(parentRelationship + "=%@",
						new NSArray(NSKeyValueCoding.NullValue));
			}
		}
		else {
			parentRelationship = null;
			NSLog.out.appendln("CRITreeViewMenu.setParentRelationship() - parentRelationship = null");
		}
	}

	/**
	 * Definit le titre qui s'affiche en haut du treeview.
	 */
	public void setTitle(String aTitle) {
		title = aTitle;
	}

	/**
	 * Retourne le titre courant.
	 */
	public String title() {
		return title;
	}

	/**
	 * Methode pour definir si le tree view est dynamic : - dynamic : recherche les enfants d'un noeud lors du clic sur ce noeud, - static :
	 * charge tous les enregistrements au debut. Pour le moment tous les treeViews sont statiques
	 */
	public void setDynamic(boolean tof) {
		isDynamic = tof;
	}

	/**
	 * Teste si un treeView est dynamique : - true : le treeView est dynamique - false sinon
	 */
	public boolean isDynamic() {
		return isDynamic;
	}

	/**
	 * Permet de definir si le noeud racine de l'arbre est affiche ou non :
	 * 
	 * @param tof
	 *            : true si le noeud racine doit etre affiche false sinon Remarque : ce parametre est dynamique et peut etre modifie a tout
	 *            moment.
	 */
	public void setRootVisible(boolean tof) {
		isRootVisible = tof;
		theTree.setRootVisible(isRootVisible);
		// Fonctionne bien sans cela, a remettre si necessiare
		// this.invalidate();
		// this.validate();
	}

	/**
	 * Teste si le noeud racine est visible ou non
	 * 
	 * @return : true si le noeud est visible false sinon.
	 */
	public boolean isRootVisible() {
		return isRootVisible;
	}

	/**
	 * Teste si c'est l'objet rootNode qui est selectionne.
	 * 
	 * @return : true si c'est la racine qui est selectionnee false sinon.
	 */
	public boolean isRootSelected() {
		return isRootSelected;
	}

	/**
	 * Selectionne le noeud root.
	 */
	public void selectRoot() {
		TreePath rootPath = new TreePath(rootNode);
		theTree.setSelectionPath(rootPath);
	}

	/**
	 * Determine si le treeView est actif ou non (enabled). S'il n'est pas actif, il est affiche mais aucun noeud ne peut etre selectionne.
	 * 
	 * @param aBool
	 *            : true pour rendre le treeView Actif, false sinon
	 */
	public void setEnabled(boolean aBool) {
		theTree.setEnabled(aBool);
		theTree.validate();
	}

	/**
	 * Definit le qualifier qui permet de restreindre la liste des enregistrements accessible dans la table qui represente l'arbre.
	 * 
	 * @param theQualifier
	 *            : EOQualifier reprensentant les criteres de restriction. Si null, pas de critere de restriction.
	 */
	public void setRestrictionQualifier(EOQualifier theQualifier) {
		restrictionQualifier = theQualifier;
	}

	/**
	 * Definit le qualifier pour la premiere colonne : critere de selection des enregistrements de la premiere colonne de l'arbre.
	 * 
	 * @param theQualifier
	 *            : EOQualifier reprensentant les criteres de selection des enregistrements a la racine.
	 */
	public void setQualifierForFirstColumn(EOQualifier theQualifier) {
		qualifierForFirstColumn = theQualifier;
	}

	/**
	 * Definit le nom du champ destine a l'affichage.
	 * 
	 * @param fieldName
	 *            : nom du champ representant la valeur a afficher sur les noeuds.
	 */
	public void setFieldForDisplay(String fieldName) {
		fieldForDisplay = fieldName;
	}

	/**
	 * Retourne un NSArray qui contient les elements du chemin selectionne.
	 * 
	 * @return : un NSArray contenant les differents elements du chemin, en commencant par le noeud racine.
	 */
	public NSArray pathElements() {
		NSArray lArray;
		TreePath lePath = theTree.getSelectionPath();
		lArray = new NSArray(lePath.getPath());

		return lArray;
	}

	/**
	 * Retourne le chemin selectionne sous forme de TreePath
	 */
	public TreePath selectedPath() {
		return theTree.getSelectionPath();
	}

	/**
	 * Selectionne le chemin passe en parametre et l'ouvre si necessaire.
	 * 
	 * @param aTreePath
	 *            : le chemin a selectionner dans l'arbre.
	 */
	public void setSelectedPath(TreePath aTreePath) {
		/*
		 * theTree.setSelectionPath(aTreePath); theTree.scrollPathToVisible(aTreePath); theTree.expandPath(aTreePath); // informer les
		 * observers que le treeView a change de selection treeViewDidClick((CRITreeNode)theTree.getLastSelectedPathComponent());
		 */
		selectAndShowPath(aTreePath);
	}

	/**
	 * Retourne le EOGenericRecord representant l'object selectionne.
	 */
	public EOGenericRecord selectedObject() {
		return (EOGenericRecord) treeTable.selectedObject();
	}

	/**
	 * Retourne true si le noeud selectionne est une feuille, false sinon
	 */
	public boolean selectedObjectIsLeaf() {
		CRITreeNode theNode = (CRITreeNode) theTree.getLastSelectedPathComponent();
		return theNode.isLeaf();
	}

	/**
	 * Retourne le CRITreeNode selectionne : permet d'obtenir ce tree node pour avoir la liste de ses enfants
	 */
	public CRITreeNode selectedNode() {
		return (CRITreeNode) theTree.getLastSelectedPathComponent();
	}

	/**
	 * Retourne le noeud contenu dans le tree path passe en parametre
	 */
	public CRITreeNode nodeAtPath(TreePath aPath) {
		return (CRITreeNode) aPath.getLastPathComponent();
	}

	/**
	 * Retourne la valeur de la cle de l'objet selectionne ou null si aucun objet n'est selectionne.
	 */
	public Object selectedKey() {
		if (key != null) {
			if (treeTable.selectedObject() != null) {
				return ((EOGenericRecord) treeTable.selectedObject()).valueForKey(key);
			}
			else {
				return null;
			}
		}
		else {
			NSLog.out.appendln("La valeur de key est nulle, impossible de retourner selectedKey().");
			return null;
		}
	}

	/**
	 * Recherche le noeud dont la cle (key) est egale a la valeur passee en parametre et l'affiche dans l'arbre. Selectionne le noeud
	 * correspondant, l'affiche est notifie les observers que le treeview a change.
	 * 
	 * @param value
	 *            : valeur de la cle de l'objet que l'on souhaite selectionner.
	 */
	public void selectKey(Object value) {
		CRITreeNode currentNode = null;
		TreePath leTreePath;
		boolean found = false;

		if (key == null) {
			NSLog.out.appendln("La valeur de key est nulle, impossible de selectionner l'objet.");
			return;
		}
		// Recherche du noeud correspondant a partir de la racine :
		// - Enumeration en profondeur d'abord
		Enumeration enumm = rootNode.depthFirstEnumeration();
		// - Recherche du noeud contenant la valeur
		while (enumm.hasMoreElements()) {
			currentNode = (CRITreeNode) enumm.nextElement();
			// si c'est le noeud racine : pas d'enregistrement
			if (currentNode == rootNode) {
				continue;
			}
			// comparaison des deux valeurs
			if (currentNode.keyValue().equals(value)) {
				// si la valeur est identique, quitter la boucle
				found = true;
				break;
			}
		}
		// Si l'on a parcouru toute l'enum et qu'on a rien trouve
		if (!found) {
			currentNode = rootNode;
		}

		// si le noeud est null ou si c'est la racine
		if (currentNode == null || currentNode == rootNode) {
			// selectionner le noeud racine
			leTreePath = new TreePath(rootNode);
		}
		else {
			// Contruction du path jusqu'au noeud
			TreeNode[] lePath = currentNode.getPath();
			leTreePath = new TreePath(lePath);
			// Selection du path dans l'arbre
		}
		//
		selectAndShowPath(leTreePath);
	}

	/**
	 * Recherche le noeud qui contient l'object passe en parametre et le selectionne.
	 */
	public void selectObject(Object anObject) {
		CRITreeNode currentNode = null;
		TreePath leTreePath;
		boolean found = false;

		// Recherche du noeud correspondant a partir de la racine :
		// - Enumeration en profondeur d'abord
		Enumeration enumm = rootNode.depthFirstEnumeration();
		// - Recherche du noeud contenant la valeur
		while (enumm.hasMoreElements()) {
			currentNode = (CRITreeNode) enumm.nextElement();
			// si c'est le noeud racine : pas d'enregistrement
			if (currentNode == rootNode) {
				continue;
			}
			// comparaison des deux valeurs
			if (currentNode.record() == anObject) {
				// si la valeur est identique, quitter la boucle
				found = true;
				break;
			}
		}

		// Si l'on a parcouru toute l'enum et qu'on a rien trouve...
		if (!found) {
			currentNode = rootNode;
		}

		// si le noeud est null ou si c'est la racine
		if (currentNode == null || currentNode == rootNode) {
			// selectionner le noeud racine
			leTreePath = new TreePath(rootNode);
		}
		else {
			// Contruction du path jusqu'au noeud
			TreeNode[] lePath = currentNode.getPath();
			leTreePath = new TreePath(lePath);
			// Selection du path dans l'arbre
		}

		selectAndShowPath(leTreePath);

	}

	/**
	 * Selectionne le chemin passe en parametre et tente de le positionner dans la vue
	 */
	private void selectAndShowPath(TreePath aPath) {
		int originRow;
		int destinationRow;
		int showRow;

		// Recuperer la ligne selectionnee en cours
		originRow = theTree.getMinSelectionRow();
		// Selectionner le nouveau chemin
		theTree.setSelectionPath(aPath);
		theTree.expandPath(aPath);
		// Recuperer la nouvelle ligne a selectionner
		destinationRow = theTree.getRowForPath(aPath);
		// Si on remonte
		if (destinationRow <= originRow) {
			// Calcul de la ligne a afficher
			showRow = destinationRow - (theTree.getVisibleRowCount() / 2);
			if (showRow < 0) {
				showRow = 0;
			}
			// Positionner la ligne
			theTree.scrollRowToVisible(showRow);
		}
		// Si on descend
		else {
			// Calcul de la ligne a afficher
			showRow = (destinationRow + (theTree.getVisibleRowCount() / 2));
			if (showRow >= theTree.getRowCount()) {
				showRow = theTree.getRowCount() - 1;
			}
			theTree.scrollRowToVisible(showRow);
		}

		// Informer les observers que le treeView a change de selection
		treeViewDidClick((CRITreeNode) theTree.getLastSelectedPathComponent());
	}

	/**
	 * Mise a jour d'une partie de l'arbre, par exemple apres ajout ou suppression de descendants. Refetch tous les descendants dans la
	 * table pour prendre en compte les dernieres modifications. Parametre - aTreePath : le treePath a partir duquel la mise a jour doit
	 * etre effectuee. Si null, met a jour tout l'arbre.
	 */
	public void update(TreePath aTreePath) {
		CRITreeNode updateNode, currentNode, newNode;
		NSArray childs, previousLevel;
		NSMutableArray currentLevel;
		int itLevel, itChilds;
		int levelMax, childMax;

		if (aTreePath == null) {
			// Mise a jour complete
			updateNode = rootNode;
		}
		else {
			// Recuperer le node selectionne
			theTree.invalidate();
			theTree.setSelectionPath(aTreePath);
			updateNode = (CRITreeNode) theTree.getLastSelectedPathComponent();
		}
		// Rechargement de la table
		fetchTreeTable();
		// Suppression des descendants
		updateNode.removeAllChildren();
		// Reconstruire l'arborescence du node
		previousLevel = new NSArray(updateNode);
		do {
			currentLevel = new NSMutableArray();
			levelMax = previousLevel.count();
			for (itLevel = 0; itLevel < levelMax; itLevel++) {
				currentNode = (CRITreeNode) previousLevel.objectAtIndex(itLevel);
				childs = findChilds(currentNode);
				childMax = childs.count();
				for (itChilds = 0; itChilds < childMax; itChilds++) {
					newNode = new CRITreeNode((EOGenericRecord) childs.objectAtIndex(itChilds), key, parentKey, fieldForDisplay);
					currentNode.add(newNode);
					currentLevel.addObject(newNode);
				}
			}
			previousLevel = currentLevel;

			// Tant qu'il y a des enregistrements qui peuvent avoir des
			// descendants
		}
		while (previousLevel.count() > 0);

		this.createTree(false);
		if (aTreePath != null) {
			this.setSelectedPath(aTreePath);
		}

	}

	/**
	 * Retourne l'arbre reel (le JTree) utilise. A utiliser avec precautions, en lecture seulement.
	 */
	public JTree tree() {
		return theTree;
	}

	/**
	 * Retourne le scroll pane utilise par le treeView.
	 */
	public JScrollPane scrollPane() {
		return theScrollPane;
	}

	/**
	 * Representation de l'arbre sous forme de String. Peut etre utilise pour l'affichage...
	 */
	public String treeRepresentation() {
		return childrenRepresentation(rootNode);
	}

	/**
	 * Representation de l'arbre a partir d'un noeud donne
	 */
	public String childrenRepresentation(TreeNode node) {
		int i;
		StringBuffer buffer = new StringBuffer();
		Enumeration enumm = node.children();
		CRITreeNode leNode;
		TreeNode[] lePath;

		while (enumm.hasMoreElements()) {
			leNode = (CRITreeNode) enumm.nextElement();
			// System.out.println(leNode.displayValue());
			lePath = leNode.getPath();
			for (i = 0; i < lePath.length; i++) {
				if (i < lePath.length - 1) {
					if (i != 0) {
						buffer.append("    ");
					}
				}
				else {
					buffer.append("/" + ((CRITreeNode) lePath[i]).displayValue() + "\n");
				}
			}
			if (!childrenRepresentation(leNode).equals("")) {
				buffer.append(childrenRepresentation(leNode));
			}

		}
		return buffer.toString();
	}

	/**
	 * Selectionne l'element choisi dans le display group pour pouvoir repondre a displayGroup.selectedObject(). Envoi la notification
	 * CRITreeViewDidClick. Le dictionnaire userInfo lie a la notification contient deux valeurs : - "selectedRecord" : l'enregistrement
	 * selectionne - "isLeaf" : reponse du noeud a isLeaf "TRUE" ou "FALSE"
	 */
	protected void treeViewDidClick(CRITreeNode node) {
		// Plus besoin de filtrer, on a deja charge la table avec tous les
		// enregistrements valide
		// treeTable.setQualifier(restrictionQualifier);
		// treeTable.updateDisplayedObjects();
		if (node == rootNode) {
			treeTable.setSelectedObject(null);
			isRootSelected = true;
			NSArray keyArray = new NSArray(new Object[] { "isLeaf" });
			NSArray valueArray = new NSArray(new Object[] { "FALSE" });
			NSDictionary userDictionary = new NSDictionary(valueArray, keyArray);
			NSNotificationCenter.defaultCenter().postNotification(TREE_DID_CLICK, this, userDictionary);
		}
		else {
			isRootSelected = false;
			if (!treeTable.selectObject(node.record())) {
				NSLog.out.appendln(this.getClass().getName() + "ERREUR : selection treeTable a echoue");
				return;
			}

			NSArray keyArray = new NSArray(new Object[] { "selectedRecord", "isLeaf" });
			NSArray valueArray = new NSArray(new Object[] { node.record(), (node.isLeaf() ? "TRUE" : "FALSE") });
			NSDictionary userDictionary = new NSDictionary(valueArray, keyArray);
			NSNotificationCenter.defaultCenter().postNotification(TREE_DID_CLICK, this, userDictionary);
		}
	}

	/**
	 * Selectionne l'element choisi dans le display group pour pouvoir repondre a displayGroup.selectedObject(). Envoi la notification
	 * CRITreeViewDidClick. Le dictionnaire userInfo lie a la notification contient deux valeurs : - "selectedRecord" : l'enregistrement
	 * selectionne ou null si aucun enregistrement selectionne (ROOT) - "isLeaf" : reponse du noeud a isLeaf "TRUE" ou "FALSE"
	 */
	protected void treeViewDidDoubleClick(CRITreeNode node) {
		// Plus besoin de filtrer, on a deja charge la table avec tous les
		// enregistrements valide
		// treeTable.setQualifier(restrictionQualifier);
		// treeTable.updateDisplayedObjects();

		if (node == rootNode) {
			treeTable.setSelectedObject(null);
			isRootSelected = true;
			NSArray keyArray = new NSArray(new Object[] { "isLeaf" });
			NSArray valueArray = new NSArray(new Object[] { "FALSE" });
			NSDictionary userDictionary = new NSDictionary(valueArray, keyArray);
			NSNotificationCenter.defaultCenter().postNotification(TREE_DID_CLICK, this, userDictionary);
		}
		else {
			// on selectionne l'objet dans le displayGroup
			if (!treeTable.selectObject(node.record())) {
				NSLog.out.appendln(this.getClass().getName() + "ERREUR : selection treeTable a echoue");
				// ATTENTION : ajouter envoi d'erreur par exception et/ou NSLog
				return;
			}
			// preparation du dictionnaire pour userInfo()
			NSArray keyArray = new NSArray(new Object[] { "selectedRecord", "isLeaf" });
			NSArray valueArray = new NSArray(new Object[] { node.record(), (node.isLeaf() ? "TRUE" : "FALSE") });
			NSDictionary userDictionary = new NSDictionary(valueArray, keyArray);
			// envoi notification
			NSNotificationCenter.defaultCenter().postNotification(TREE_DID_DOUBLE_CLICK, this, userDictionary);
		}
	}

	//
	// Methode
	/*
	 * public boolean displayGroupShouldFetch(EODisplayGroup group) { if(group == treeTable) { return true; } return false; }
	 */

	/**
	 * Possibilite d'ajouter un listener personnel pour la gestion du treeView (exemple click droit).
	 */
	public void addMouseListener(MouseListener l) {
		theTree.addMouseListener(l);
	}

	/**
	 * permet d'ouvir le premier noeud de l'arbre sans declencher une notification de selection
	 */
	public void expandFirstNode() {
		try {
			theTree.setSelectionRow(0);
			theTree.expandPath(theTree.getSelectionPath());
		}
		catch (Exception e) {
			NSLog.out.appendln(this.getClass().getName() + " - " + e.getMessage());
		}
	}

	/**
	 * Classe privee CRITreeMouseListener : - Gere les clicks de souris dans le treeView.
	 */
	private class CRITreeMouseListener extends MouseAdapter {
		// methode executee lorsque la souris a clicke
		// recupere le noeud selectionne lors du click et envoie
		// la fonction correspondante : treeViewDidClick ou
		// treeViewDidDoubleClick
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);

			CRITreeNode node;
			int selRow;
			// Si le tree est disabled, on ne peut pas changer
			if (!theTree.isEnabled()) {
				return;
			}
			if (e.getButton() == MouseEvent.BUTTON1) {
				// TreePath selPath;
				selRow = theTree.getRowForLocation(e.getX(), e.getY());
				// selPath = theTree.getPathForLocation(e.getX(), e.getY());
				// si un element a ete selectionne
				if (selRow != -1) {
					// recuperer le noeud selectionne
					node = (CRITreeNode) theTree.getLastSelectedPathComponent();
					if (node == null) {
						return;
					}
					if (e.getClickCount() == 1) {
						// lance la methode de CRITreeView
						treeViewDidClick(node);
					}
					// double click
					else
						if (e.getClickCount() == 2) {
							// lance la methode ...
							treeViewDidDoubleClick(node);
						}
				}
			}
		}
	}

}
