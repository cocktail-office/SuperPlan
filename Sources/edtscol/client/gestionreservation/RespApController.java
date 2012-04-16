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
package edtscol.client.gestionreservation;

import javax.swing.JButton;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eoapplication.EOModalDialogController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import edtscol.client.panier.GestionPanier;
import fr.univlr.common.utilities.WidgetHandler;

public class RespApController extends EOModalDialogController {

	public EODisplayGroup eodIndividu;
	public EOTable tableIndividu;
	public JButton bValider, bAnnuler;

	private InspecteurCtrl owner;

	public RespApController(InspecteurCtrl owner) {
		super();
		EOEditingContext.setSubstitutionEditingContext(owner.editingContext());
		this.owner = owner;
		EOArchive.loadArchiveNamed("RespApController", this, "edtscol.client.gestionreservation", this.disposableRegistry());
		establishConnection();
	}

	public void valider() {
		NSArray objetsSelectionnes = eodIndividu.selectedObjects();

		if (objetsSelectionnes != null) {

			NSMutableArray objetsNouveaux = new NSMutableArray();
			for (int i = 0; i < objetsSelectionnes.count(); i++) {
				EOGenericRecord record = (EOGenericRecord) objetsSelectionnes.objectAtIndex(i);
				if (!owner.panier().containsObject(record)) {
					objetsNouveaux.addObject(record);
				}
			}

			if (objetsNouveaux.count() > 0) {
				NSArray resIndividus = GestionPanier.ressourcesFromRecords(objetsNouveaux, GestionPanier.PERSONNE);
				owner.panier().addResources(resIndividus);
			}
		}
		annuler();
	}

	protected void componentDidBecomeVisible() {
		setWindowTitle(this.window(), "Intervenants dans l'AP selectionn\u00e9 :");
		WidgetHandler.setTableNotEditable(tableIndividu);
	}

	public void afficher(NSArray individus) {
		eodIndividu.setObjectArray(individus);
		this.activateWindow();
	}

	public void annuler() {
		this.deactivateWindow();
	}

}
