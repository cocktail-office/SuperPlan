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

import javax.swing.tree.DefaultMutableTreeNode;

import com.webobjects.eocontrol.EOGenericRecord;

public class CRITreeNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 609017016418651191L;
	protected EOGenericRecord nodeRecord;
	protected String key, parentKey, fieldForDisplay;

	// Constructeur par defaut cree un noeud vide, principalement pour le noeud racine (non affiche)
	public CRITreeNode() {
		super();
		nodeRecord = null;
		key = null;
		parentKey = null;
		fieldForDisplay = null;
	}

	// constructeur avec un objet a afficher, eventuellement pour le noeud racine
	// s'il doit etre affiche
	public CRITreeNode(Object nodeValue) {
		super(nodeValue);
		nodeRecord = null;
		key = null;
		parentKey = null;
		fieldForDisplay = null;
	}

	// constructeur de CRITreeNode, recoit un generique record + les valeurs
	// key, parentKey et fieldForDisplay.
	// utilise par CRITreeView
	public CRITreeNode(EOGenericRecord nodeValue, String keyValue, String parentKeyValue, String fieldForDisplayValue) {
		super(nodeValue.valueForKey(fieldForDisplayValue));
		nodeRecord = nodeValue;
		key = keyValue;
		parentKey = parentKeyValue;
		fieldForDisplay = fieldForDisplayValue;
	}

	// ajoute un noeud fils au noeud appele
	public void add(CRITreeNode newChild) {
		super.add(newChild);
	}

	// definit le record associe a un noeud
	public void setRecord(EOGenericRecord nodeValue) {
		nodeRecord = nodeValue;
	}

	// retourne le record associe a un noeud
	public EOGenericRecord record() {
		return nodeRecord;
	}

	// definit le champ key de l'enregistrement
	public void setKey(String keyValue) {
		key = keyValue;
	}

	// retourne la valeur contenue dans le champ defini par key
	// ou null si celui-ci n'est pas defini
	public Object keyValue() {
		if (key != null) {
			return nodeRecord.valueForKey(key);
		}
		else {
			return null;
		}
	}

	// definit la valeur du champ contenant la cle pere
	public void setParentKey(String parentKeyValue) {
		parentKey = parentKeyValue;
	}

	// retourne la valeur contenue dans le champ defini par parentKey ou
	// null s'il n'est pas defini
	public Object parentKeyValue() {
		if (parentKey != null) {
			return nodeRecord.valueForKey(parentKey);
		}
		else {
			return null;
		}
	}

	// retourne la valeur contenue dans le champ defini par fieldForDisplay
	// ou null s'il n'est pas defini
	public Object displayValue() {
		if (fieldForDisplay != null) {
			return nodeRecord.valueForKey(fieldForDisplay);
		}
		else {
			return null;
		}
	}

}
