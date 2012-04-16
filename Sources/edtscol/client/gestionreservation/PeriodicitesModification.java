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

import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.Reservation;

import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;

import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;

public class PeriodicitesModification extends EOInterfaceController {

	public EODisplayGroup eodDates;
	public EOTable tableDates;
	public JLabel lblInfos;
	public JButton bSelectionnees;
	private GregorianCalendar cal;
	private InspecteurCtrl owner;

	public PeriodicitesModification(InspecteurCtrl owner) {
		super(owner.editingContext());
		this.owner = owner;
		cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4); // le premier Jeudi de Janvier...
		// (bug enorme du jdk-1.4.x)
	}

	protected void componentDidBecomeVisible() {

		StringBuffer sAide = new StringBuffer();
		sAide.append("<html>");
		sAide.append("Appliquer les modifications pour les periodicites :");
		sAide.append("<br>");
		sAide.append("selectionnées (Selectionnées) ou bien toutes (Toutes)");
		sAide.append("</html>");
		lblInfos.setFont(new Font("Helvetica", 14, Font.PLAIN));
		lblInfos.setOpaque(true);
		lblInfos.setBackground(new Color(0xE6E6FA));
		lblInfos.setText(sAide.toString());
		lblInfos.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		Reservation reservation = owner.getReservation();
		NSArray periodicites = reservation.periodicites();
		WidgetHandler.setTableNotEditable(tableDates);

		eodDates.setObjectArray(new NSArray());

		for (int i = 0; i < periodicites.count(); i++) {
			Periodicite currentPeriod = (Periodicite) periodicites.objectAtIndex(i);
			cal.setTime(currentPeriod.dateDeb());
			cal.setTime(cal.getTime());
			Number noSemaine = new Integer(cal.get(Calendar.WEEK_OF_YEAR));
			NSDictionary dico = new NSDictionary(new Object[] { noSemaine, currentPeriod.dateDeb(), currentPeriod.dateFin(), currentPeriod },
					new Object[] { "semaine", "dateDeb", "dateFin", "eoPeriode" });
			// CM 04/04/2007
			eodDates.insertObjectAtIndex(dico, 0);
		}
		eodDates.updateDisplayedObjects();
		if (eodDates.displayedObjects().count() < 2) {
			bSelectionnees.setEnabled(false);
		}
		else {
			bSelectionnees.setEnabled(true);
		}
	}

	public void annuler() {
		owner.setDatesModification(null, false);
		fermer();
	}

	private void fermer() {
		WindowHandler.closeModal(this);
	}

	public void validerSemainesSelectionnees() {
		NSArray selection = eodDates.selectedObjects();
		boolean all = false;
		if (selection.count() == eodDates.displayedObjects().count()) {
			all = true;
		}
		owner.setDatesModification(selection, all);
		fermer();
	}

	public void validerToutesSemaines() {
		NSArray selection = eodDates.displayedObjects();
		owner.setDatesModification(selection, true);
		fermer();
	}

}
