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
package org.cocktail.superplan.server;

import java.util.ArrayList;

import org.cocktail.superplan.server.factory.EnseignementFactory;
import org.cocktail.superplan.server.metier.IndividuUlr;
import org.cocktail.superplan.server.metier.MaquetteAp;
import org.cocktail.superplan.server.metier.MaquetteParcours;
import org.cocktail.superplan.server.metier.MaquetteSemestre;
import org.cocktail.superplan.server.metier.Occupant;
import org.cocktail.superplan.server.metier.Periodicite;
import org.cocktail.superplan.server.metier.ResaSalles;
import org.cocktail.superplan.server.metier.Reservation;
import org.cocktail.superplan.server.metier.ReservationAp;
import org.cocktail.superplan.server.metier.Salles;
import org.cocktail.superplan.server.metier.ScolGroupeGrp;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;

public class RecopieEdtHandler {

	EOEditingContext eContext;
	EnseignementFactory ensFactory;
	Verification verif;
	TimeHandler timeHandler;
	Session session;
	NSMutableArray messErreur;

	boolean copierCM = true, copierTD = true, copierTP = true, copierGroupes = true, copierOccupants = true, copierSalles = true;

	public RecopieEdtHandler(Session session) {
		this.eContext = session.defaultEditingContext();
		ensFactory = new EnseignementFactory(eContext);
		verif = new Verification(eContext);
		timeHandler = new TimeHandler();
		messErreur = new NSMutableArray();
		timeHandler.setUseAnneeCivile(((Boolean) session.userInfos.valueForKey("anneeCivile")).booleanValue());
	}

	/**
	 * initie la recopie des edts des elements pedagogiques parcours/semesre
	 * 
	 * @param idPar
	 *            le globalID du parcours
	 * @param idSem
	 *            le globalID du semestre
	 * @param fannKey
	 *            represente l'annee univ a recopier
	 * @param fannKeyDest
	 *            l'annee univ de destination
	 * @param weeks
	 *            les semaines a recopier
	 * @param weekDeb
	 *            la semaine de destination de debut de recopier
	 */
	public NSArray recopierEdt(NSDictionary args) throws Exception {

		Number fannKey = (Number) args.objectForKey("fannKey");
		Number fannKeyDest = (Number) args.objectForKey("fannKeyDest");
		String weeks = (String) args.objectForKey("semaines");
		String weekDeb = (String) args.objectForKey("semaineDest");
		copierCM = ((String) args.objectForKey("copierCM")).equals("O");
		copierTD = ((String) args.objectForKey("copierTD")).equals("O");
		copierTP = ((String) args.objectForKey("copierTP")).equals("O");
		copierGroupes = ((String) args.objectForKey("copierGroupes")).equals("O");
		copierOccupants = ((String) args.objectForKey("copierOccupants")).equals("O");
		copierSalles = ((String) args.objectForKey("copierSalles")).equals("O");

		MaquetteParcours parcours = (MaquetteParcours) DBHandler.safeObjectForGlobalID(eContext, (EOGlobalID) args.objectForKey("idParcours"));
		MaquetteSemestre semestre = (MaquetteSemestre) DBHandler.safeObjectForGlobalID(eContext, (EOGlobalID) args.objectForKey("idSemestre"));

		System.out.println("[RecopieEdtHandler:recopierEdt] args = " + args);
		System.out.println("[RecopieEdtHandler:recopierEdt] parcours = " + parcours);
		System.out.println("[RecopieEdtHandler:recopierEdt] semestre = " + semestre);

		StringBuffer strMHCO = new StringBuffer();

		EOQualifier qualTypeAp = null;
		if (!(copierCM && copierTD && copierTP)) {
			if (copierCM) {
				strMHCO.append("mhcoCode='CM'");
			}
			if (copierTD) {
				if (copierCM) {
					strMHCO.append(" or ");
				}
				strMHCO.append("mhcoCode='TD'");
			}
			if (copierTP) {
				if (copierCM || copierTD) {
					strMHCO.append(" or ");
				}
				strMHCO.append("mhcoCode='TP'");
			}
			qualTypeAp = EOQualifier.qualifierWithQualifierFormat(strMHCO.toString(), null);
		}

		NSArray ap = ensFactory.getApForParcoursSemestres(parcours, null, semestre, null, fannKey, qualTypeAp);

		System.out.println("[RecopieEdtHandler:recopierEdt] ap.mapKey = " + ap.valueForKey("mapKey"));

		NSArray periodicites;
		try {
			periodicites = timeHandler.computeWeeksDates(weeks, fannKey.intValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			periodicites = new NSArray();
		}

		ArrayList semaines = TimeHandler.getAllWeeksFromString(weeks, fannKey.intValue(), true);

		int lastWeek = ((Integer) semaines.get(semaines.size() - 1)).intValue();
		int firstWeek = ((Integer) semaines.get(0)).intValue();

		NSArray creneaux = ensFactory.lookupReservationsEnseignement(ap, periodicites);

		System.out.println("[RecopieEdtHandler:recopierEdt] creneaux = " + creneaux);

		int weekStartCopy = FormatHandler.strToInt(weekDeb);

		messErreur.removeAllObjects();

		for (int i = 0; i < creneaux.count(); i++) {
			Periodicite per = (Periodicite) creneaux.objectAtIndex(i);
			NSTimestamp deb = timeHandler.translatePeriodicite(per.dateDeb(), weekStartCopy, firstWeek, lastWeek, fannKeyDest.intValue());
			NSTimestamp fin = timeHandler.translatePeriodicite(per.dateFin(), weekStartCopy, firstWeek, lastWeek, fannKeyDest.intValue());
			messErreur.addObjectsFromArray(copy(per, deb, fin));
		}
		eContext.saveChanges();

		// on remplace les resa par leurs numeros pour les envoyer au client
		for (int i = 0; i < messErreur.count(); i++) {
			NSMutableDictionary dico = (NSMutableDictionary) messErreur.objectAtIndex(i);
			Reservation resa = (Reservation) dico.objectForKey("resa");
			Object key = DBHandler.primaryKey(eContext, resa, "resaKey");
			if (key != null) {
				dico.setObjectForKey(key, "resa");
			}
			else {
				dico.setObjectForKey(new Integer(-1), "resa");
			}
		}
		return messErreur;
	}

	public NSArray copy(Periodicite periodicite, NSTimestamp deb, NSTimestamp fin) throws Exception {
		String FD = "%d/%m/%Y %H:%M";
		NSMutableArray localMessage = new NSMutableArray();

		Reservation resa = periodicite.reservation();
		NSTimestamp now = new NSTimestamp();
		Reservation newResa = Reservation.createReservation(eContext, now, now, resa.tlocCode());
		newResa.addObjectToBothSidesOfRelationshipWithKey(resa.individuAgent(), Reservation.INDIVIDU_AGENT_KEY);
		NSArray periodes = new NSArray(new Object[] { deb, fin });

		StringBuffer msg = new StringBuffer();

		NSArray tmp;
		if (copierOccupants) {
			tmp = (NSArray) resa.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		}
		else {
			tmp = new NSArray();
		}

		for (int i = 0; i < tmp.count(); i++) {
			IndividuUlr currentInd = (IndividuUlr) tmp.objectAtIndex(i);
			if (verif.getNonDisponibliteIndividu(periodes, currentInd, null).count() == 0) {
				Occupant occ = Occupant.createOccupant(eContext, new Integer(0), currentInd);
				newResa.addObjectToBothSidesOfRelationshipWithKey(occ, Reservation.OCCUPANTS_KEY);
			}
			else {
				msg = new StringBuffer();
				msg.append(currentInd.nomPrenom() + " pas libre entre ");
				msg.append(FormatHandler.dateToStr(deb, FD) + " et " + FormatHandler.dateToStr(fin, FD));
				NSMutableDictionary dico = new NSMutableDictionary(new Object[] { newResa, msg.toString() }, new Object[] { "resa", "message" });
				localMessage.addObject(dico);
				System.out.println("Occupant non libre");
			}

		}

		if (copierSalles) {
			tmp = (NSArray) resa.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		}
		else {
			tmp = new NSArray();
		}

		for (int i = 0; i < tmp.count(); i++) {
			Salles currentSal = (Salles) tmp.objectAtIndex(i);

			if (verif.verifSallePourModification(null, periodes, currentSal).count() == 0) {
				ResaSalles rs = ResaSalles.createResaSalles(eContext);
				rs.setResaSalEtat("N");
				rs.setResaSalDate(now);
				rs.addObjectToBothSidesOfRelationshipWithKey(currentSal, ResaSalles.SALLE_KEY);
				newResa.addObjectToBothSidesOfRelationshipWithKey(rs, Reservation.RESA_SALLES_KEY);
			}
			else {
				msg = new StringBuffer();
				msg.append("Salle " + currentSal.salPorte() + " pas libre entre ");
				msg.append(FormatHandler.dateToStr(deb, FD) + " et " + FormatHandler.dateToStr(fin, FD));
				NSMutableDictionary dico = new NSMutableDictionary(new Object[] { newResa, msg.toString() }, new Object[] { "resa", "message" });
				localMessage.addObject(dico);
				System.out.println("Salle non libre");
			}
		}

		tmp = resa.reservationAp();

		for (int i = 0; i < tmp.count(); i++) {
			ReservationAp resAp = (ReservationAp) tmp.objectAtIndex(i);
			MaquetteAp oldAp = resAp.maquetteAp();
			ScolGroupeGrp oldGrp = resAp.scolGroupeGrp();
			MaquetteAp currentAp = ensFactory.getApTranfertMap(oldAp);
			if (currentAp != null) {
				ScolGroupeGrp currentGrp = null;
				if (oldGrp != null && copierGroupes) {
					currentGrp = ensFactory.getGrpTranfertGrp(oldGrp);
				}

				boolean testContinue = false;
				if (currentGrp != null) {
					testContinue = (verif
							.getNonDisponibiliteGroupe(null, periodes, currentAp, currentGrp, false,
									((Application) Application.application()).isAppControleApsOccupation(),
									((Application) Application.application()).isAppControleApsNonObligatoiresOccupation(),
									((Application) Application.application()).config().booleanForKey("APP_CONTROLE_GROUPES_OCCUPATION"), true)
							.objectAtIndex(1).count() == 0);
				}
				else {
					testContinue = (verif
							.getNonDisponibiliteAp(null, periodes, currentAp, false, true,
									((Application) Application.application()).isAppControleApsOccupation(),
									((Application) Application.application()).isAppControleApsNonObligatoiresOccupation(), false).objectAtIndex(1)
							.count() == 0);
				}

				if (testContinue) {
					ReservationAp currentResAp = ReservationAp.createReservationAp(eContext, new Integer(0));
					newResa.addObjectToBothSidesOfRelationshipWithKey(currentResAp, Reservation.RESERVATION_AP_KEY);

					currentResAp.addObjectToBothSidesOfRelationshipWithKey(currentGrp, ReservationAp.SCOL_GROUPE_GRP_KEY);
					DBHandler.invalidateObject(eContext, currentAp);
					currentResAp.addObjectToBothSidesOfRelationshipWithKey(currentAp, ReservationAp.MAQUETTE_AP_KEY);
				}
				else {
					msg = new StringBuffer();
					msg.append("Etudiants de \"" + currentAp.mapLibelle() + "\" pas libres entre ");
					msg.append(FormatHandler.dateToStr(deb, FD) + " et " + FormatHandler.dateToStr(fin, FD));
					NSMutableDictionary dico = new NSMutableDictionary(new Object[] { newResa, msg.toString() }, new Object[] { "resa", "message" });
					localMessage.addObject(dico);
				}
			}
			else {
				// ligne de rapport - Ap non transfere
				msg = new StringBuffer("L'AP \"" + oldAp.mapLibelle());
				msg.append("\" n'a pas \u00e9t\u00e9 recopi\u00e9 pour l'ann\u00e9e demand\u00e9e.");
				NSMutableDictionary dico = new NSMutableDictionary(new Object[] { newResa, msg.toString() }, new Object[] { "resa", "message" });
				localMessage.addObject(dico);
			}
		}

		Periodicite period = Periodicite.createPeriodicite(eContext, deb, fin, new Integer(0));
		newResa.addObjectToBothSidesOfRelationshipWithKey(period, Reservation.PERIODICITES_KEY);
		System.out.println("copy");

		return localMessage;
	}

}