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
package edtscol.client.semainecontroller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.cocktail.superplan.client.factory.ReunionFactory;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.LogReservation;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.ResaExamen;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ReservationObjet;
import org.cocktail.superplan.client.metier.SalleSouhaitee;
import org.cocktail.superplan.client.metier.Salles;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MailReservation;
import edtscol.client.MainClient;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;

public class SemaineController extends JDialog implements IWeekSelectionObserver {

	private static final long serialVersionUID = -2037275762443700606L;
	private static final int AJOUT = 1;
	private static final int SUPPR = 2;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	public WeekSelector weekSelector;
	private Reservation reservation;
	private TimeHandler timeHandler;
	private ReunionFactory reunionFactory;
	private boolean adroitModif;
	private NSMutableDictionary hashPeriodiciteSemaine;
	boolean anneeCivile;

	public SemaineController(Frame frame, Reservation reservation, boolean adroitModif) {
		super(frame, "Selection de semaines");
		this.adroitModif = adroitModif;
		timeHandler = new TimeHandler();
		anneeCivile = ((Boolean) app.userInfo("anneeCivile")).booleanValue();
		timeHandler.setUseAnneeCivile(anneeCivile);

		this.reservation = reservation;
		reunionFactory = new ReunionFactory(reservation.editingContext());
		hashPeriodiciteSemaine = new NSMutableDictionary();
		initUI();
		initSemaines();
	}

	private void initUI() {
		setSize(300, 180);
		setResizable(false);
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		// weekSelector = new WeekSelector(this,app.getCurrentFannKey());
		weekSelector = new WeekSelector(this, app.getCurrentDisplayedYear());
		container.add(weekSelector, BorderLayout.CENTER);
		StringBuffer bfr = new StringBuffer();
		bfr.append("<html>");
		bfr.append("Sélectionner ou déselectionner une semaine");
		bfr.append("<br>");
		bfr.append("pour l'ajouter ou la retirer à la réservation");
		bfr.append("</html>");
		JLabel lbl = new JLabel(bfr.toString());
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setOpaque(true);
		lbl.setBackground(new Color(0xE6E6FA));
		lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		container.add(lbl, BorderLayout.NORTH);
		setVisible(true);
	}

	/** permet de calculer les periodicites en fonction des donnees saisies/choisies */
	public ArrayList<Integer> weeksFromPeriodicites(NSArray periodicites) {
		ArrayList<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < periodicites.count(); i++) {
			Periodicite period = (Periodicite) periodicites.objectAtIndex(i);
			NSTimestamp dateDeb = period.dateDeb();

			Integer week = new Integer(timeHandler.weekOfYear(dateDeb));
			lst.add(week);
			hashPeriodiciteSemaine.setObjectForKey(period, week);
		}
		return lst;
	}

	private void initSemaines() {
		weekSelector.setSelectedWeeks(weeksFromPeriodicites(reservation.periodicites()));
	}

	public boolean addWeek(Integer semaine) {
		if (!adroitModif) {
			return false;
		}
		NSArray tmp = reservation.periodicites();
		// boolean accord = WindowHandler.showConfirmation("Voulez-vous ajouter la semaine " + semaine.intValue() + " à la réservation ?");
		// if ((tmp.count() == 0) || (!accord)) {
		if (tmp.count() == 0) {
			return false;
		}
		Periodicite period = (Periodicite) tmp.objectAtIndex(0);

		NSTimestamp dateDeb = period.dateDeb();
		NSTimestamp dateFin = period.dateFin();

		String hDebut = FormatHandler.dateToStr(dateDeb, "%H");
		String mDebut = FormatHandler.dateToStr(dateDeb, "%M");
		String hFin = FormatHandler.dateToStr(dateFin, "%H");
		String mFin = FormatHandler.dateToStr(dateFin, "%M");

		int annee = TimeHandler.getYearFor(dateFin, anneeCivile);

		int jour = timeHandler.dayOfWeek(dateDeb);
		String theWeek = String.valueOf(semaine.intValue());

		NSArray periodicites = new NSArray();
		try {
			periodicites = timeHandler.computePeriodicites(jour, theWeek, hDebut, hFin, mDebut, mFin, annee);
		}
		catch (EdtException e) {
			WindowHandler.showError(e.getMessage());
		}

		try {
			LogReservation.logModificationReservation(reservation.editingContext(), reservation);
			reunionFactory.addPeriodsForReservation(periodicites, reservation);
			weeksFromPeriodicites(reservation.periodicites());
		}
		catch (Exception ex) {
			String msg;
			if (ex instanceof EdtException) {
				msg = ex.getMessage();
			}
			else {
				msg = "Erreur ajout de semaine\n" + ex.getMessage();
			}
			if (msg != null) {
				WindowHandler.showError(msg);
			}
			app.revertChanges();
			return false;
		}
		mail(periodicites, AJOUT);
		return true;
	}

	public boolean removeWeek(Integer semaine) {
		if (!adroitModif) {
			return false;
		}

		Periodicite per = (Periodicite) hashPeriodiciteSemaine.objectForKey(semaine);
		// String msg = "Confirmez-vous la suppression de la semaine " + semaine + " de la réservation ?";
		NSArray perSuppr = new NSArray(new Object[] { per.dateDeb(), per.dateFin() });

		// if ((per != null) && (WindowHandler.showConfirmation(msg))) {
		if (per != null) {
			try {
				if (reservation.periodicites().count() == 1) {
					WindowHandler.showError("Veuillez supprimer la réservation au non pas la seule semaine restante");
					return false;
				}
				else {
					LogReservation.logModificationReservation(reservation.editingContext(), reservation);
					reunionFactory.supprimerCreneauxPourResa(reservation, new NSArray(per));
				}
			}
			catch (Exception e) {
				WindowHandler.showError("Erreur suppression de semaine : " + e.getMessage());
				app.revertChanges();
				return false;
			}
		}
		mail(perSuppr, SUPPR);
		return true;
	}

	private void mail(NSArray periodicites, int operation) {
		IndividuUlr agent = (IndividuUlr) app.userInfo("individu");
		IndividuUlr agentOrigine = reservation.individuAgent();

		String emailAgent = app.getEmailIndividu(agent), emailAgentOrigine = app.getEmailIndividu(agentOrigine);
		String ms = messageReservation(reservation, periodicites, operation);
		if (agentOrigine.equals(agent) == false) {
			app.prevenirModification(ms, emailAgent, emailAgentOrigine, null, false);
		}

		if (app.isSendMailDepositaires()) {
			if ((reservation.resaSalles() != null && reservation.resaSalles().count() != 0)
					|| (reservation.reservationObjets() != null && reservation.reservationObjets().count() != 0)) {
				NSMutableArray<String> mailDeposSallesEtObjets = new NSMutableArray<String>();
				EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();

				if (reservation.resaSalles() != null) {
					NSArray<EOGlobalID> gidSalles = DBHandler.globalIDsForObjects(reservation.editingContext(), (NSArray<Salles>) reservation
							.resaSalles().valueForKey(ResaSalles.SALLE_KEY));
					NSArray<String> tmpMail = (NSArray<String>) objectStore.invokeRemoteMethodWithKeyPath(reservation.editingContext(), "session",
							"clientSideRequestMailsDepositairesSalles", new Class[] { NSArray.class, NSArray.class },
							new Object[] { gidSalles, null }, false);
					if (tmpMail != null && tmpMail.count() > 0) {
						mailDeposSallesEtObjets.addObjectsFromArray(tmpMail);
					}
				}
				if (reservation.reservationObjets() != null) {
					NSArray<EOGlobalID> gidObjets = DBHandler.globalIDsForObjects(reservation.editingContext(), (NSArray<ResaObjet>) reservation
							.reservationObjets().valueForKey(ReservationObjet.RESA_OBJET_KEY));
					NSArray<String> tmpMail = (NSArray<String>) objectStore.invokeRemoteMethodWithKeyPath(reservation.editingContext(), "session",
							"clientSideRequestMailsDepositairesObjets", new Class[] { NSArray.class }, new Object[] { gidObjets }, false);
					if (tmpMail != null && tmpMail.count() > 0) {
						mailDeposSallesEtObjets.addObjectsFromArray(tmpMail);
					}
				}
				if (mailDeposSallesEtObjets.count() > 0) {
					String dest = mailDeposSallesEtObjets.componentsJoinedByString(",");
					MailReservation mailReservation = app.mailReservation();
					mailReservation.setMailInfos(ms, operation == AJOUT ? "Ajout d'une périodicité" : "Suppression d'une périodicité", dest,
							emailAgent);
					mailReservation.enregistrer();
					// mailReservation.show();
				}
			}
		}

		if (app.isSendMailOccupants()) {
			if (reservation.occupants() != null && reservation.occupants().count() > 0
					&& (reservation.occupants().count() > 1 || agent.equals(reservation.occupants().objectAtIndex(0)) == false)) {
				NSArray<String> emails = app.getEmailIndividus((NSArray<Integer>) ((NSArray<IndividuUlr>) reservation.occupants().valueForKey(
						Occupant.INDIVIDU_KEY)).valueForKey(IndividuUlr.PERS_ID_KEY));
				if (emails != null && emails.count() > 0) {
					String dest = emails.componentsJoinedByString(",");
					MailReservation mailReservation = app.mailReservation();
					mailReservation.setMailInfos(ms, operation == AJOUT ? "Ajout d'une périodicité" : "Suppression d'une périodicité", dest,
							emailAgent);
					mailReservation.enregistrer();
					// mailReservation.show();
				}
			}
		}
	}

	private String messageReservation(Reservation resa, NSArray period, int ajoutSuppression) {
		NSArray occupants = (NSArray) resa.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		NSArray salles = (NSArray) resa.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		NSArray choixSalles = (NSArray) resa.valueForKeyPath(Reservation.SALLES_SOUHAITEES_KEY + "." + SalleSouhaitee.SALLE_KEY);
		resa.periodicites();
		NSArray enseignements = (NSArray) resa.valueForKeyPath(Reservation.RESERVATION_AP_KEY + "." + ReservationAp.MAQUETTE_AP_KEY);
		NSArray examens = (NSArray) resa.valueForKeyPath(Reservation.RESA_EXAMENS_KEY + "." + ResaExamen.EXAMEN_ENTETE_KEY);

		String msg = app.mailReservation().messageFromResources(resa, occupants, salles, choixSalles, enseignements, examens, null,
				(IndividuUlr) app.userInfo("individu"), false);
		StringBuffer bfr = new StringBuffer();
		if (ajoutSuppression == AJOUT) {
			bfr.append("***** Ajout d'une périodicité *****\n");
		}
		else {
			bfr.append("***** Suppression d'une périodicité *****\n");
		}
		bfr.append(FormatHandler.dateToStr((NSTimestamp) period.objectAtIndex(0), "%d/%m/%Y %H:%M"));
		bfr.append(" - ");
		bfr.append(FormatHandler.dateToStr((NSTimestamp) period.objectAtIndex(1), "%d/%m/%Y %H:%M"));

		bfr.append("\n\n");
		bfr.append(msg);

		return bfr.toString();
	}
}
