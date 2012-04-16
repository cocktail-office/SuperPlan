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
package edtscol.client.composant;

import java.util.Vector;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.cocktail.superplan.client.metier.ImplantationGeo;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.RepartBatImpGeo;
import org.cocktail.superplan.client.metier.ResaFamilleObjet;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaTypeObjet;
import org.cocktail.superplan.client.metier.Salles;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;

import fr.univlr.common.utilities.DBHandler;

public class ObjetTreeModel implements TreeModel {

	public static String ROOT_NODE = "Liste des familles";
	private NSArray<ResaFamilleObjet> firstColumn = null;

	EOEditingContext eContext;
	private ImplantationGeo implantationGeo = null;
	private IndividuUlr utilisateur;
	private boolean showReservableOnly = true;

	private Vector<TreeModelListener> listenerList = new Vector<TreeModelListener>();

	public ObjetTreeModel(EOEditingContext ec, boolean showReservableOnly) {
		eContext = ec;
		this.showReservableOnly = showReservableOnly;
	}

	public void setFilterByImplantationGeo(ImplantationGeo impGeo) {
		implantationGeo = impGeo;
	}

	public IndividuUlr getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(IndividuUlr utilisateur) {
		this.utilisateur = utilisateur;
	}

	// retourne les familles d'objets
	// C'est le fetch first column
	public NSArray<ResaFamilleObjet> getFamilles() {
		if (firstColumn == null || firstColumn.count() == 0) {
			firstColumn = DBHandler.fetchData(eContext, ResaFamilleObjet.ENTITY_NAME, (EOQualifier) null, ResaFamilleObjet.ObjetsSort);
		}
		return firstColumn;
	}

	// retourne la deuxieme column : les Types contenus dans une famille
	public NSArray<ResaTypeObjet> getTypesForFamille(ResaFamilleObjet famille) {
		return famille.resaTypesObjet();
	}

	// retourne la 3eme colonne
	public NSArray<ResaObjet> getObjetsForType(ResaTypeObjet type) {
		EOQualifier qualVisibilite = null;
		if (showReservableOnly) {
			qualVisibilite = new EOKeyValueQualifier(ResaObjet.RO_RESERVABLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O");
		}
		else {
			qualVisibilite = new EOKeyValueQualifier(ResaObjet.RO_ACCES_KEY, EOKeyValueQualifier.QualifierOperatorEqual, NSKeyValueCoding.NullValue);
		}
		if (implantationGeo != null) {
			EOQualifier qualObj = EOQualifier.qualifierWithQualifierFormat(ResaObjet.SALLE_KEY + " = %@ or " + ResaObjet.SALLE_KEY + "."
					+ Salles.REPARTS_BAT_IMP_GEO_KEY + "." + RepartBatImpGeo.IMPLANTATION_GEO_KEY + " = %@", new NSArray<Object>(new Object[] {
					NSKeyValueCoding.NullValue, implantationGeo }));

			EOQualifier qualType = new EOKeyValueQualifier(ResaObjet.RESA_TYPE_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, type);
			EOQualifier andQual = new EOAndQualifier(new NSArray<EOQualifier>(new EOQualifier[] { qualVisibilite, qualObj, qualType }));
			return ResaObjet.fetchResaObjets(eContext, andQual, null);
		}
		else {
			return type.resaObjets(qualVisibilite);
		}
	}

	private NSArray _getChildren(Object parent) {
		NSArray list;
		if (parent == ROOT_NODE) {
			list = getFamilles();
		}
		else
			if (parent instanceof ResaFamilleObjet) {
				list = getTypesForFamille((ResaFamilleObjet) parent);
			}
			else
				if (parent instanceof ResaTypeObjet) {
					list = getObjetsForType((ResaTypeObjet) parent);
				}
				else {
					list = new NSArray();
				}

		return list;
	}

	public Object getChild(Object parent, int index) {
		return _getChildren(parent).objectAtIndex(index);
	}

	public int getChildCount(Object parent) {
		return _getChildren(parent).count();
	}

	public int getIndexOfChild(Object parent, Object index) {
		return _getChildren(parent).indexOfObject(index);
	}

	public Object getRoot() {
		return ROOT_NODE;
	}

	public boolean isLeaf(Object objet) {
		return getChildCount(objet) == 0;
	}

	public void addTreeModelListener(TreeModelListener listener) {
		if (listener != null && !listenerList.contains(listener)) {
			listenerList.addElement(listener);
		}
	}

	public void removeTreeModelListener(TreeModelListener listener) {
		if (listener != null) {
			listenerList.removeElement(listener);
		}
	}

	public void valueForPathChanged(TreePath arg0, Object arg1) {
	}

}
