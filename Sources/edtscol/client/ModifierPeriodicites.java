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

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.factory.ReunionFactory;
import org.cocktail.superplan.client.factory.VerificationFactory;
import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.LogReservation;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.ResaExamen;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ReservationObjet;
import org.cocktail.superplan.client.metier.Salles;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.gestionreservation.InspecteurCtrl;
import fr.univlr.common.utilities.CalendarHandler;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;

public class ModifierPeriodicites extends EOInterfaceController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	public EODisplayGroup eodPeriodicites;
	public JTextField tInfos;
	public JCheckBox checkToutSelect;
	public EOTable tablePeriodicites;

	private EOEditingContext eContext;
	private NSArray periodicites;
	private TimeHandler timeHandler;
	int jour, heDeb, miDeb, heFin, miFin;
	private Reservation reservation;
	private ReunionFactory reunionFactory;

	public static final NSArray SORT_PERIODICITE_ASC = new NSArray(EOSortOrdering.sortOrderingWithKey("dateDeb", EOSortOrdering.CompareAscending));

	public ModifierPeriodicites(EOEditingContext eContext, Reservation resa, int jour, int heDeb, int miDeb, int heFin, int miFin) {
		super(eContext);

		this.eContext = editingContext();

		this.periodicites = resa.periodicites();
		this.reservation = resa;
		this.heDeb = heDeb;
		this.miDeb = miDeb;
		this.heFin = heFin;
		this.miFin = miFin;
		this.jour = jour;

		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());

		reunionFactory = new ReunionFactory(eContext);
	}

	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);

		WidgetHandler.setTableNotEditable(tablePeriodicites);
		NSTimestamp debutSemaine = AppCalendar.storedDebutSemaine();
		NSTimestamp finSemaine = AppCalendar.storedFinSemaine();

		eodPeriodicites.setDelegate(this);
		WidgetHandler.setTableNotEditable(tablePeriodicites);
		WindowHandler.setWaitCursor(this.component());

		DBHandler.invalidateObjects(eContext, periodicites);

		eodPeriodicites.setObjectArray(periodicites);
		NSMutableArray selection = new NSMutableArray();

		if (periodicites.count() == 1) {
			eodPeriodicites.setSelectedObjects(periodicites);
		}
		else {
			for (int i = 0; i < periodicites.count(); i++) {
				Periodicite period = (Periodicite) periodicites.objectAtIndex(i);
				NSTimestamp debut = period.dateDeb();

				if (((debut.compare(debutSemaine) == NSComparator.OrderedAscending) || (debut.compare(debutSemaine) == NSComparator.OrderedSame))
						&& ((debut.compare(finSemaine) == NSComparator.OrderedDescending) || (debut.compare(finSemaine) == NSComparator.OrderedSame))) {
					selection.addObject(period);
				}
			}
			eodPeriodicites.setSelectedObjects(selection);
		}
		WindowHandler.setDefaultCursor(this.component());
	}

	/** action selectionner tout ou deselectionner tout */
	public void toutSelect() {
		if (checkToutSelect.isSelected()) {
			eodPeriodicites.setSelectedObjects(eodPeriodicites.allObjects());
		}
		else {
			eodPeriodicites.setSelectedObject(null);
		}
		eodPeriodicites.updateDisplayedObjects();
	}

	public void envoyerMail(Periodicite per, NSTimestamp oldDeb, NSTimestamp oldFin) {
		NSTimestamp deb = per.dateDeb();
		NSTimestamp fin = per.dateFin();
		Reservation resa = per.reservation();
		String emailAgent = (String) app.userInfo("email");
		String sujet = "Modification d'une date de réservation";
		IndividuUlr agent = resa.individuAgent();
		NSArray<ResaSalles> resaSalles = resa.resaSalles();
		NSArray<ReservationObjet> resaObjets = resa.reservationObjets();
		NSArray<IndividuUlr> occupants = (NSArray<IndividuUlr>) resa.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		StringBuffer msg = new StringBuffer();
		msg.append("Emanant de " + ((IndividuUlr) app.userInfo("individu")).prenomNom());
		msg.append("\nLes dates et/ou horaires de la réservation suivante ont été déplacés");
		for (int j = 0; j < resaSalles.count(); j++) {
			ResaSalles resaSal = resaSalles.objectAtIndex(j);
			String libSalle = resaSal.salle().cLocal();
			if (resaSal.salle().salEtage() != null) {
				libSalle = libSalle + " - Etage " + resaSal.salle().salEtage();
			}
			if (resaSal.salle().salPorte() != null) {
				libSalle = libSalle + " - Porte " + resaSal.salle().salPorte();
			}
			msg.append("\nBatiment " + libSalle);
		}
		msg.append("\nObjet : " + resa.resaCommentaire());
		msg.append("\nAncien horaire le ");
		msg.append(FormatHandler.dateToStr(oldDeb, "%A %d/%m/%Y"));
		msg.append("\n de ");
		msg.append(FormatHandler.dateToStr(oldDeb, "%H:%M"));
		msg.append("H \u00e0 ");
		msg.append(FormatHandler.dateToStr(oldFin, "%H:%M"));
		msg.append("H\n");
		msg.append("\nNouvel horaire le ");
		msg.append(FormatHandler.dateToStr(deb, "%A %d/%m/%Y"));
		msg.append("\n de ");
		msg.append(FormatHandler.dateToStr(deb, "%H:%M"));
		msg.append("H \u00e0 ");
		msg.append(FormatHandler.dateToStr(fin, "%H:%M"));
		msg.append("H\n");
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSMutableArray<Salles> requestedSalles = new NSMutableArray<Salles>();
		for (int i = 0; i < resaSalles.count(); i++) {
			Salles sal = resaSalles.objectAtIndex(i).salle();
			requestedSalles.addObject(sal);
		}
		NSMutableArray<ResaObjet> requestedObjets = new NSMutableArray<ResaObjet>();
		for (int i = 0; i < resaObjets.count(); i++) {
			ResaObjet obj = resaObjets.objectAtIndex(i).resaObjet();
			requestedObjets.addObject(obj);
		}

		// Envoi du mail cache vers le createur de la resa si ce n'est pas le meme que l'utilisateur actuel
		if (agent.equals(app.userInfo("individu")) == false) {
			NSArray<MaquetteAp> enseignements = null;
			if (resa.reservationAp() != null) {
				enseignements = (NSArray<MaquetteAp>) resa.reservationAp().valueForKey(ReservationAp.MAQUETTE_AP_KEY);
			}
			NSArray<ExamenEntete> examens = null;
			if (resa.resaExamens() != null) {
				examens = (NSArray<ExamenEntete>) resa.resaExamens().valueForKey(ResaExamen.EXAMEN_ENTETE_KEY);
			}
			String emailAgentOrigine = app.getEmailIndividu(agent);
			String ms = app.mailReservation().messageFromResources(reservation, occupants, requestedSalles, null, enseignements, examens, null,
					(IndividuUlr) app.userInfo("individu"), true);
			app.prevenirModification(ms, emailAgent, emailAgentOrigine, null, false);
		}

		if (app.isSendMailDepositaires()) {
			if (requestedSalles.count() > 0 || requestedObjets.count() > 0) {
				NSMutableArray<String> mailDeposSallesEtObjets = new NSMutableArray<String>();
				NSArray<EOGlobalID> gidSalles = DBHandler.globalIDsForObjects(eContext, requestedSalles);
				NSArray<String> tmpMail = (NSArray<String>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session",
						"clientSideRequestMailsDepositairesSalles", new Class[] { NSArray.class, NSArray.class }, new Object[] { gidSalles,
								new NSArray<NSTimestamp>(new NSTimestamp[] { deb, fin }) }, false);
				if (tmpMail != null && tmpMail.count() > 0) {
					mailDeposSallesEtObjets.addObjectsFromArray(tmpMail);
				}
				NSArray<EOGlobalID> gidObjets = DBHandler.globalIDsForObjects(eContext, requestedObjets);
				tmpMail = (NSArray<String>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session",
						"clientSideRequestMailsDepositairesObjets", new Class[] { NSArray.class }, new Object[] { gidObjets }, false);
				if (tmpMail != null && tmpMail.count() > 0) {
					mailDeposSallesEtObjets.addObjectsFromArray(tmpMail);
				}
				if (mailDeposSallesEtObjets.count() > 0) {
					String dest = mailDeposSallesEtObjets.componentsJoinedByString(",");
					MailReservation mailReservation = app.mailReservation();
					mailReservation.setMailInfos(msg.toString(), sujet, dest.toString(), emailAgent);
					mailReservation.enregistrer();
					// mailReservation.show();
				}
			}
		}

		if (app.isSendMailOccupants()) {
			if (occupants.count() > 0 && (occupants.count() > 1 || agent.equals(occupants.objectAtIndex(0)) == false)) {
				NSArray<String> emails = app.getEmailIndividus((NSArray<Integer>) occupants.valueForKey(IndividuUlr.PERS_ID_KEY));
				if (emails != null && emails.count() > 0) {
					String dest = emails.componentsJoinedByString(",");
					MailReservation mailReservation = app.mailReservation();
					mailReservation.setMailInfos(msg.toString(), sujet, dest.toString(), emailAgent);
					mailReservation.enregistrer();
					// mailReservation.show();
				}
			}
		}

	}

	public void valider() {
		WindowHandler.setWaitCursor(this.component());
		NSArray<Periodicite> selects = eodPeriodicites.selectedObjects();

		selects = EOSortOrdering.sortedArrayUsingKeyOrderArray(selects, SORT_PERIODICITE_ASC);

		NSMutableArray<NSTimestamp> newPeriods = new NSMutableArray<NSTimestamp>();
		if (selects.count() == 0) {
			WindowHandler.setDefaultCursor(this.component());
			WindowHandler.showError("Sélectionner la réservation à modifier");
			return;
		}
		NSTimestamp oldDeb = null;
		for (int i = 0; i < selects.count(); i++) {
			Periodicite oldPeriod = selects.objectAtIndex(i);
			oldDeb = oldPeriod.dateDeb();
			int week = timeHandler.weekOfYear(oldDeb);
			int year = (new Integer(FormatHandler.dateToStr(oldDeb, "%Y"))).intValue();
			NSTimestamp[] datesSemaine = timeHandler.getDateRangeForWeek(year, jour, heDeb, miDeb, heFin, miFin, week);

			newPeriods.addObject(datesSemaine[0]);
			newPeriods.addObject(datesSemaine[1]);
		}

		if (app.autoriseReservationFerie() == false) {
			for (int idate = 0; idate < newPeriods.count(); idate++) {
				NSTimestamp currentDateTest = newPeriods.objectAtIndex(idate);
				if (DateCtrl.isHolidayFR(currentDateTest)) {
					WindowHandler.setDefaultCursor(this.component());
					WindowHandler.showError("La réservation en jour férié n'est pas possible.");
					NSNotificationCenter.defaultCenter().postNotification("refreshPlanning", null);
					return;
				}
			}
		}
		if (periodicites.count() == selects.count()) {
			// tout changer
			try {
				if (newPeriods.count() > 1) {

					if (VerificationFactory.testRessourcesPourNouvellesPeriodes(eContext, reservation, newPeriods)) {

						LogReservation.logModificationReservation(eContext, reservation);

						// NSArray periodicites = reservation.periodicites();
						NSArray periodicites = EOSortOrdering.sortedArrayUsingKeyOrderArray(reservation.periodicites(), SORT_PERIODICITE_ASC);

						Periodicite per = null;
						int j = 0;
						for (int i = 0; i < periodicites.count(); i++) {
							per = (Periodicite) periodicites.objectAtIndex(i);
							NSTimestamp deb = newPeriods.objectAtIndex(j);
							j++;
							NSTimestamp fin = newPeriods.objectAtIndex(j);
							j++;

							per.setDateDeb(deb);
							per.setDateFin(fin);
						}
						reservation.setDModification(DateCtrl.now());
						reservation.setIndividuAgentRelationship((IndividuUlr) app.userInfo("individu"));
						app.saveChanges();
						// envoyerMail(per, oldDeb, oldFin);
					}
					else {
						NSNotificationCenter.defaultCenter().postNotification("refreshPlanning", null);
						return;
					}
				}
				else {
					NSNotificationCenter.defaultCenter().postNotification("refreshPlanning", null);
					return;
				}
			}
			catch (Exception ex) {
				app.revertChanges();
				WindowHandler.setDefaultCursor(this.component());
				WindowHandler.showError(ex.getMessage());
				return;
			}
			finally {
				NSNotificationCenter.defaultCenter().postNotification("refreshPlanning", null);
			}
		}
		else {
			// recreer une resa pour les creneaux modifies
			try {
				if (newPeriods.count() > 1) {
					if (VerificationFactory.testRessourcesPourNouvellesPeriodes(eContext, reservation, newPeriods)) {
						LogReservation.logModificationReservation(eContext, reservation);
						EnseignementFactory ensFactory = new EnseignementFactory(eContext);
						ensFactory.copyReservationAvecPeriodicites(reservation, newPeriods, false);
						for (int i = 0; i < selects.count(); i++) {
							reservation.deletePeriodicitesRelationship(selects.objectAtIndex(i));
						}
						app.saveChanges();
					}
				}
				else {
					NSNotificationCenter.defaultCenter().postNotification("refreshPlanning", null);
					return;
				}
			}
			catch (EdtException ex) {
				WindowHandler.showError(ex.getMessage());
				WindowHandler.setDefaultCursor(this.component());
				app.revertChanges();
				return;
			}
			catch (Exception ex) {
				ex.printStackTrace();
				app.revertChanges();
				WindowHandler.showError(ex.getMessage());
				WindowHandler.setDefaultCursor(this.component());
				return;
			}
			finally {
				NSNotificationCenter.defaultCenter().postNotification("refreshPlanning", null);
			}

		} // else
		if (reservation != null) {
			NSArray<Occupant> occupants = reservation.occupants();
			if ((reservation.isTypeEnseignement() || reservation.isTypeExamen() || reservation.isTypeReunion()) && app.isSendMailOccupants()) {
				if (occupants != null && occupants.count() > 0
						&& (occupants.count() > 1 || ((IndividuUlr) app.userInfo("individu")).equals(occupants.objectAtIndex(0).individu()) == false)) {
					InspecteurCtrl.prepareMailPersonne(InspecteurCtrl.MAIL_MODIFICATION, reservation, null, null, null, null, null, null);
				}
			}
		}
		WindowHandler.setDefaultCursor(this.component());
		try {
			WindowHandler.close(this);
		}
		catch (Exception e) {
		}
	}

	public void displayGroupDidChangeSelection(EODisplayGroup group) {

		Periodicite period = (Periodicite) group.selectedObject();
		if (period == null) {
			return;
		}
		int creneauJour = timeHandler.dayOfWeek(period.dateDeb());

		String stDeb = FormatHandler.dateToStr(period.dateDeb(), "%d/%m/%Y");
		String stFin = FormatHandler.dateToStr(period.dateFin(), "%d/%m/%Y");

		NSTimestamp dDeb = FormatHandler.strToDate(stDeb + " " + heDeb + ":" + miDeb + ":0");
		NSTimestamp dFin = FormatHandler.strToDate(stFin + " " + heFin + ":" + miFin + ":0");
		if (creneauJour != jour) {
			String jourModif = FormatHandler.intToDay(jour);
			String jourResa = FormatHandler.intToDay(creneauJour);
			tInfos.setText("Modification : " + jourModif + " au lieu de " + jourResa + " de " + heDeb + ":" + miDeb + " a " + heFin + ":" + miFin);
			CalendarHandler cal = new CalendarHandler();
			cal.setTime(dDeb);
			cal.setDay(jour);
			dDeb = cal.getTime();
			cal.setTime(dFin);
			cal.setDay(jour);
			dFin = cal.getTime();
		}

	}

	public void annuler() {
		// CM
		// WindowHandler.closeModal(this);
		WindowHandler.close(this);
		NSNotificationCenter.defaultCenter().postNotification("refreshPlanning", null);
	}

}
