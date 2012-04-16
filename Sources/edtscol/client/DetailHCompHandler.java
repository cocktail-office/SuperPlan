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

import javax.swing.JButton;

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eoapplication.EOModalDialogController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.eointerface.swing.EOView;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.utilities.SwapView;

/**
 * Controleur permettant de lancer la validation ou la devalidation des Heures Comp.
 */
public class DetailHCompHandler extends EOModalDialogController {

	public EODisplayGroup eodHcompRecup, eodPeriodicite;
	public EOTable tableHcompRecup, tablePeriodicite;
	public SwapView viewHComp;
	public EOView viewNonValide, viewValide;
	public JButton bImprimerValide, bImprimerNonValide;
	private MaquetteAp ap;
	private IndividuUlr individu;

	private HCompCtrl hCompCtrl;
	private EOEditingContext eContext;

	public DetailHCompHandler(HCompCtrl hCompCtrl) {
		super();
		this.hCompCtrl = hCompCtrl;
		EOEditingContext.setSubstitutionEditingContext(eContext);
		EOArchive.loadArchiveNamed("DetailHCompHandler", this, "edtscol.client", this.disposableRegistry());
		establishConnection();
	}

	protected void connectionWasEstablished() {
		WidgetHandler.setTableNotEditable(tableHcompRecup);
		WidgetHandler.setTableNotEditable(tablePeriodicite);
	}

	protected void componentDidBecomeVisible() {
		setWindowTitle(this.window(), "Consultation - Validation de HComp.");
		initWidget();
	}

	/** voirDetailNonValide */
	public void voirDetailNonValide(MaquetteAp ap, IndividuUlr individu, NSArray hcomp) {
		this.ap = ap;
		this.individu = individu;
		eodPeriodicite.setObjectArray(hcomp);
		viewHComp.setContentView(viewNonValide);
		activateWindow();
	}

	/** voirDetailValide */
	public void voirDetailValide(MaquetteAp ap, IndividuUlr individu, NSArray hComp) {
		this.ap = ap;
		this.individu = individu;
		eodHcompRecup.setObjectArray(hComp);
		viewHComp.setContentView(viewValide);
		activateWindow();
	}

	/** voir HCompCtrl.devaliderHcomp puis HCompHandler.devaliderHcomp */
	public void devaliderHComp() {
		NSArray hcomp = eodHcompRecup.selectedObjects();
		NSMutableArray allHcomp = eodHcompRecup.displayedObjects().mutableClone();
		if (hCompCtrl.devaliderHcomp(hcomp)) {
			allHcomp.removeObjectsInArray(hcomp);
			eodHcompRecup.setObjectArray(allHcomp);
			hCompCtrl.rechercher();
		}
	}

	/** voir HCompCtrl.validerHcomp puis HCompHandler.validerHcomp */
	public void validerHComp() {
		NSArray periodicites = eodPeriodicite.selectedObjects();
		NSMutableArray allPeriodicites = eodPeriodicite.displayedObjects().mutableClone();
		if (hCompCtrl.validerHcomp(ap, individu, periodicites)) {
			allPeriodicites.removeObjectsInArray(periodicites);
			eodPeriodicite.setObjectArray(allPeriodicites);
			hCompCtrl.rechercher();
		}
	}

	public void imprimerHeuresValides() {
		hCompCtrl.imprimerHeuresValides(ap);
	}

	public void imprimerHeuresNonValides() {
		hCompCtrl.imprimerHeuresNonValides(ap);
	}

	private void initWidget() {
		WidgetHandler.decorateButton("Imprimer la liste", null, "smallprint", bImprimerValide);
		WidgetHandler.decorateButton("Imprimer la liste", null, "smallprint", bImprimerNonValide);
	}

}