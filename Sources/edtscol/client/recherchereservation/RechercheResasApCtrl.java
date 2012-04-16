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
package edtscol.client.recherchereservation;

import java.awt.Component;
import java.util.Enumeration;

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.swing.ZEOTable.ZEOTableListener;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotificationCenter;

import fr.univlr.common.utilities.WidgetHandler;

public class RechercheResasApCtrl {

	private static RechercheResasApCtrl sharedInstance;

	private EOEditingContext ec;
	private Component parent;
	private MaquetteAp maquetteAp;
	private ScolGroupeGrp groupe;

	private EODisplayGroup eodResas = new EODisplayGroup();
	private EODisplayGroup eodIntervenants = new EODisplayGroup();

	private RechercheResasApView myView;

	public RechercheResasApCtrl(EOEditingContext editingContext, Component parent, MaquetteAp maquetteAp, ScolGroupeGrp groupe) {
		super();

		myView = new RechercheResasApView(new javax.swing.JFrame(), true, eodResas, eodIntervenants);
		ec = editingContext;
		this.parent = parent;
		init();
		setMaquetteApGroupe(maquetteAp, groupe);
	}

	private void init() {
		myView.getBtFermer().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				myView.setVisible(false);
			}
		});

		myView.getBtInspecter().setToolTipText("Ouvrir cette réservation dans l'inspecteur");
		myView.getBtInspecter().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				inspecter();
			}
		});

		myView.getMyEOTableResas().addListener(new ListenerTable());

		EOSortOrdering sortResas = EOSortOrdering.sortOrderingWithKey(Periodicite.DATE_DEB_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);
		eodResas.setSortOrderings(new NSArray<EOSortOrdering>(sortResas));

		EOSortOrdering sortIntervenants = EOSortOrdering.sortOrderingWithKey(Occupant.INDIVIDU_KEY + "." + IndividuUlr.NOM_PRENOM_KEY,
				EOSortOrdering.CompareAscending);
		eodIntervenants.setSortOrderings(new NSArray<EOSortOrdering>(sortIntervenants));
	}

	public static RechercheResasApCtrl sharedInstance(EOEditingContext editingContext, Component parent, MaquetteAp maquetteAp, ScolGroupeGrp groupe) {
		if (sharedInstance == null) {
			sharedInstance = new RechercheResasApCtrl(editingContext, parent, maquetteAp, groupe);
		}
		else {
			sharedInstance.setMaquetteApGroupe(maquetteAp, groupe);
		}
		return sharedInstance;
	}

	public void setMaquetteApGroupe(MaquetteAp maquetteAp, ScolGroupeGrp groupe) {
		this.maquetteAp = maquetteAp;
		this.groupe = groupe;
		StringBuffer title = new StringBuffer();
		if (maquetteAp != null) {
			title.append(maquetteAp.toString());
		}
		else {
			title.append("AP inconnue !");
		}
		if (groupe != null) {
			title.append(" - Groupe : " + groupe.ggrpLibelle());
		}
		myView.setTitle(title.toString());
		updateEodResas();
	}

	public void open() {
		myView.setLocation(parent.getX() + 20, parent.getY() + 20);
		myView.setVisible(true);
	}

	public void close() {
		myView.setVisible(false);
	}

	private void inspecter() {
		Periodicite periodicite = (Periodicite) eodResas.selectedObject();
		if (periodicite != null) {
			close();
			RechercheApsCtrl.sharedInstance(null, ec).close();
			NSDictionary<String, Object> dictio = new NSDictionary<String, Object>(periodicite.reservation(), "reservation");
			NSNotificationCenter.defaultCenter().postNotification("inspecterReservation", dictio);
		}
	}

	private void updateEodResas() {
		if (maquetteAp == null) {
			return;
		}
		NSMutableArray<Periodicite> resas = new NSMutableArray<Periodicite>();
		Enumeration<ReservationAp> e = maquetteAp.reservationAps().objectEnumerator();
		while (e.hasMoreElements()) {
			ReservationAp resaAp = e.nextElement();
			if (groupe == null || groupe.equals(resaAp.scolGroupeGrp())) {
				resas.addObjectsFromArray(resaAp.reservation().periodicites());
			}
		}
		eodResas.setObjectArray(resas);
		myView.getMyEOTableResas().updateData();
		WidgetHandler.selectNoneInDisplayGroup(eodResas);
	}

	private void updateEodIntervenants() {
		if (eodResas.selectedObject() != null) {
			Reservation resa = ((Periodicite) eodResas.selectedObject()).reservation();
			eodIntervenants.setObjectArray(resa.occupants());
		}
		else {
			eodIntervenants.setObjectArray(null);
		}
		myView.getMyEOTableIntervenants().updateData();
		WidgetHandler.selectNoneInDisplayGroup(eodIntervenants);
	}

	private class ListenerTable implements ZEOTableListener {

		public void onDbClick() {
			inspecter();
		}

		public void onSelectionChanged() {
			updateEodIntervenants();
		}
	}

}
