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
package org.cocktail.superplan.client.factory;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Mission;
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
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.StructuresAutorisees;
import org.cocktail.superplan.client.metier.TypeLocation;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import edtscol.client.VerifDisponibilite;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;

public class ReunionFactory {

	public static final int PRERESERVATION = 1;
	public static final int RESERVATION = 2;
	public static final String FORMAT = "%d/%m/%Y %H:%M";

	private EOEditingContext eContext;
	private GenericFactory genFactory;
	private EODistributedObjectStore objectStore;

	/** constructeur avec editingContext */
	public ReunionFactory(EOEditingContext eContext) {
		this.eContext = eContext;
		genFactory = new GenericFactory(eContext);
		objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
	}

	public EOEditingContext editingContext() {
		return eContext;
	}

	public EODistributedObjectStore objectStore() {
		return objectStore;
	}

	public GenericFactory genericFactory() {
		return genFactory;
	}

	public NSArray<NSTimestamp> getNonDisponibiliteIndividu(NSArray<NSTimestamp> periodicites, IndividuUlr individu, Reservation resa,
			NSArray<Periodicite> periodicitesCache, NSArray<Mission> missionsCache) {
		return VerifDisponibilite.getNonDisponibiliteIndividu(eContext, periodicites, individu, resa, periodicitesCache, missionsCache);
	}

	public NSArray<NSTimestamp> getNonDisponibiliteSalle(NSArray<NSTimestamp> periodicites, Salles salle, Reservation resa,
			NSArray<Periodicite> periodicitesCache) {
		return VerifDisponibilite.getNonDisponibiliteSalle(eContext, periodicites, salle, resa, periodicitesCache);
	}

	public NSArray<NSTimestamp> getNonDisponibiliteObjet(NSArray<NSTimestamp> periodicites, ResaObjet objet, Reservation resa,
			NSArray<Periodicite> periodicitesCache) {
		return VerifDisponibilite.getNonDisponibiliteObjet(eContext, periodicites, objet, resa, periodicitesCache);
	}

	public NSArray<NSArray<?>> getNonDisponibiliteAp(MaquetteAp ap, NSArray<NSTimestamp> periodicites, Reservation resT, boolean avecAps,
			Boolean avecControleApsObligatoires, Boolean avecControleApsNonObligatoires) {
		EOGlobalID gidResa = null;
		if (resT != null) {
			gidResa = eContext.globalIDForObject(resT);
		}
		EOGlobalID gidAp = eContext.globalIDForObject(ap);
		NSArray<NSArray<?>> a = (NSArray<NSArray<?>>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session",
				"clientSideRequestGetNonDisponibiliteAp", new Class[] { EOGlobalID.class, NSArray.class, EOGlobalID.class, Boolean.class,
						Boolean.class, Boolean.class }, new Object[] { gidResa, periodicites, gidAp, Boolean.valueOf(avecAps),
						avecControleApsObligatoires, avecControleApsNonObligatoires }, false);
		return a;
	}

	public NSArray<NSArray<?>> getNonDisponibiliteGroupeAvecAps(MaquetteAp ap, ScolGroupeGrp grp, NSArray<NSTimestamp> periodicites,
			Reservation resT, boolean avecAps, Boolean avecControleApsObligatoires, Boolean avecControleApsNonObligatoires,
			Boolean avecControleGroupes, Boolean avecStopPremiereIndispo) {
		EOGlobalID gidResa = null;
		if (resT != null) {
			gidResa = eContext.globalIDForObject(resT);
		}
		EOGlobalID gidAp = null;
		if (ap != null) {
			gidAp = eContext.globalIDForObject(ap);
		}
		EOGlobalID gidGroupe = null;
		if (grp != null) {
			gidGroupe = eContext.globalIDForObject(grp);
		}
		if (gidGroupe != null) {
			return (NSArray<NSArray<?>>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestGetNonDisponibiliteGroupe",
					new Class[] { EOGlobalID.class, NSArray.class, EOGlobalID.class, EOGlobalID.class, Boolean.class, Boolean.class, Boolean.class,
							Boolean.class, Boolean.class }, new Object[] { gidResa, periodicites, gidAp, gidGroupe, Boolean.valueOf(avecAps),
							avecControleApsObligatoires, avecControleApsNonObligatoires, avecControleGroupes, avecStopPremiereIndispo }, false);
		}
		return new NSArray<NSArray<?>>();
	}

	public NSArray<NSTimestamp> getNonDisponibiliteGroupe(MaquetteAp ap, ScolGroupeGrp grp, NSArray<NSTimestamp> periodicites, Reservation resT,
			Boolean avecControleApsObligatoires, Boolean avecControleApsNonObligatoires, Boolean avecControleGroupes, Boolean avecStopPremiereIndispo) {
		NSArray<NSArray<?>> a = getNonDisponibiliteGroupeAvecAps(ap, grp, periodicites, resT, false, avecControleApsObligatoires,
				avecControleApsNonObligatoires, avecControleGroupes, avecStopPremiereIndispo);
		if (a == null) {
			return new NSArray<NSTimestamp>();
		}
		return (NSArray<NSTimestamp>) a.objectAtIndex(1);
	}

	private NSArray<IndividuUlr> verifDisponibilites(Reservation reservation, NSArray<NSTimestamp> periodicites, NSArray<IndividuUlr> personnes,
			NSArray<StructuresAutorisees> groupes, NSArray<Salles> salles, NSArray<Salles> choixSalles, NSArray<ResaObjet> objets)
			throws EdtException {
		NSMutableArray<IndividuUlr> toutesLesPersonnes = new NSMutableArray<IndividuUlr>();
		NSMutableArray<IndividuUlr> personnesAGarder = new NSMutableArray<IndividuUlr>();

		// personnes
		toutesLesPersonnes.addObjectsFromArray(personnes);
		if (groupes != null) {
			for (int i = 0; i < groupes.count(); i++) {
				StructuresAutorisees structureAutorisee = groupes.objectAtIndex(i);
				toutesLesPersonnes.addObjectsFromArray(structureAutorisee.structureMembers());
			}
		}
		String messageIndividus = "";
		for (int i = 0; i < toutesLesPersonnes.count(); i++) {
			IndividuUlr currentIndividu = toutesLesPersonnes.objectAtIndex(i);
			NSArray<NSTimestamp> dispoInd = getNonDisponibiliteIndividu(periodicites, currentIndividu, reservation, null, null);
			if (dispoInd == null) {
				throw new EdtException("Erreur sur le serveur");
			}
			if (dispoInd.count() > 0) {
				NSTimestamp d1 = dispoInd.objectAtIndex(0);
				NSTimestamp d2 = dispoInd.objectAtIndex(1);
				messageIndividus = messageIndividus + "La personne " + currentIndividu + " n'est pas libre entre ";
				messageIndividus = messageIndividus + FormatHandler.dateToStr(d1, FORMAT) + " et " + FormatHandler.dateToStr(d2, FORMAT) + "\n";
				if (toutesLesPersonnes.count() == 1) {
					throw new EdtException(messageIndividus);
				}
			}
			else {
				personnesAGarder.addObject(currentIndividu);
			}
		}
		if (toutesLesPersonnes.count() > 0 && personnesAGarder.count() == 0) {
			throw new EdtException("Aucune personne n'est disponible, réunion annulée.");
		}
		if (messageIndividus.length() > 0) {
			if (!WindowHandler.showConfirmation(messageIndividus + "Confirmez-vous la réunion ?")) {
				throw new EdtException("Réunion annulée.");
			}
		}

		// verification disponibilite salles
		NSMutableArray<Salles> sumSalles = new NSMutableArray<Salles>();
		if (salles != null) {
			sumSalles.addObjectsFromArray(salles);
		}
		if (choixSalles != null) {
			sumSalles.addObjectsFromArray(choixSalles);
		}
		for (int i = 0; i < sumSalles.count(); i++) {
			Salles salle = sumSalles.objectAtIndex(i);
			NSArray<NSTimestamp> dispoSalle = getNonDisponibiliteSalle(periodicites, salle, reservation, null);
			if (dispoSalle == null) {
				throw new EdtException("Erreur sur le serveur");
			}
			if (dispoSalle.count() > 0) {
				NSTimestamp d1 = dispoSalle.objectAtIndex(0);
				NSTimestamp d2 = dispoSalle.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, FORMAT) + " et " + FormatHandler.dateToStr(d2, FORMAT);
				throw new EdtException("La salle " + salle.salPorte() + " n'est pas libre entre " + msg);
			}
		}

		// les objets
		if (objets != null) {
			for (int i = 0; i < objets.count(); i++) {
				ResaObjet currentObjet = objets.objectAtIndex(i);
				NSArray<NSTimestamp> dispo = getNonDisponibiliteObjet(periodicites, currentObjet, reservation, null);
				if (dispo.count() > 0) {
					NSTimestamp d1 = dispo.objectAtIndex(0);
					NSTimestamp d2 = dispo.objectAtIndex(1);
					String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
					throw new EdtException("L'objet " + currentObjet.roLibelle1() + " n'est pas libre entre " + msg);
				}
			}
		}

		return personnesAGarder;
	}

	/** affecte les periodicites a la reservation */
	public void affecterPeriodicites(Reservation reservation, NSArray<NSTimestamp> periodicites) throws EdtException {
		for (int i = 0; i < periodicites.count(); i += 2) {
			NSTimestamp pDebut = periodicites.objectAtIndex(i);
			NSTimestamp pFin = periodicites.objectAtIndex(i + 1);

			if (pFin.compare(pDebut) == NSComparator.OrderedAscending) {
				throw new EdtException("la date de debut est apres la date de fin : veuillez verifier les dates et horaires saisies !");
			}

			if (pFin.compare(pDebut) == NSComparator.OrderedSame) {
				throw new EdtException("la date de debut est la même que la date de fin : verifier les horaires donnes");
			}

			Periodicite currentPeriodicite = Periodicite.createPeriodicite(eContext, pDebut, pFin, new Integer(0));
			reservation.addToPeriodicitesRelationship(currentPeriodicite);
		}
	}

	/** affecte les occupants a la reservation */
	public void affecterOccupants(Reservation reservation, NSArray<IndividuUlr> individus) throws EdtException {
		for (int i = 0; i < individus.count(); i++) {
			IndividuUlr currentIndividu = individus.objectAtIndex(i);
			DBHandler.invalidateObject(eContext, currentIndividu);
			Occupant currentOccupant = Occupant.createOccupant(eContext, new Integer(1), currentIndividu);
			reservation.addToOccupantsRelationship(currentOccupant);
		}
	}

	/** affecte les salles a la reservation en validant directement si agent est depositaire */
	public void affecterSalles(Reservation reservation, NSArray<Salles> salles, IndividuUlr agent, NSTimestamp when, boolean avecControles)
			throws EdtException {
		SalleFactory salleFactory = new SalleFactory(eContext);
		for (int i = 0; i < salles.count(); i++) {
			Salles currentSalle = salles.objectAtIndex(i);
			if (avecControles) {
				if (SalleFactory.testIndividuADroitReserverSalle(eContext, agent, currentSalle,
						((MainClient) EOApplication.sharedApplication()).isReservationSalleParDepositaire()) == false) {
					throw new EdtException("Vous n'avez pas le droit de réserver cette salle (" + currentSalle.salPorte() + ")");
				}
			}
			ResaSalles currentResaSalle = ResaSalles.createResaSalles(eContext);
			DBHandler.invalidateObject(eContext, currentSalle);
			currentResaSalle.setSalleRelationship(currentSalle);
			reservation.addToResaSallesRelationship(currentResaSalle);

			if (salleFactory.estDepositaireDeSalle(agent, currentSalle)) {
				currentResaSalle.setResaSalEtat("O");
			}
			else {
				currentResaSalle.setResaSalEtat("N");
			}
			currentResaSalle.setResaSalDate(when);
		}
	}

	/** affecte les objets a la reservation en validant directement si agent est depositaire */
	public void affecterObjets(Reservation reservation, NSArray<ResaObjet> objets, IndividuUlr agent, boolean avecControles) throws EdtException {
		if (objets == null) {
			return;
		}
		for (int i = 0; i < objets.count(); i++) {
			ResaObjet currentObjet = objets.objectAtIndex(i);
			if (avecControles) {
				if (VerificationFactory.testIndividuADroitReserverObjet(eContext, agent, currentObjet) == false) {
					throw new EdtException("Vous n'avez pas le droit de réserver cet objet (" + currentObjet.roLibelle1() + ")");
				}
			}
			ReservationObjet currentResaObjet = ReservationObjet.createReservationObjet(eContext, null);
			DBHandler.invalidateObject(eContext, currentObjet);
			currentResaObjet.setResaObjetRelationship(currentObjet);

			if (VerificationFactory.testIndividuEstDepositaireObjet(eContext, agent, currentObjet)) {
				currentResaObjet.setResaEtat("R");
			}
			else {
				currentResaObjet.setResaEtat("P");
			}
			currentResaObjet.setReservationRelationship(reservation);
		}
	}

	public void affecterEtValiderSalles(Reservation reservation, NSArray<Salles> salles, NSTimestamp when) throws EdtException {
		for (int i = 0; i < salles.count(); i++) {
			Salles currentSalle = salles.objectAtIndex(i);
			ResaSalles currentResaSalle = ResaSalles.createResaSalles(eContext);
			DBHandler.invalidateObject(eContext, currentSalle);
			currentResaSalle.setSalleRelationship(currentSalle);
			reservation.addToResaSallesRelationship(currentResaSalle);
			currentResaSalle.setResaSalEtat("O");
			currentResaSalle.setResaSalDate(when);
		}
	}

	/** affecte la salle a la reservation */
	public void affecterChoixSalles(Reservation reservation, NSArray<Salles> choixSalles, IndividuUlr agent, boolean avecControles)
			throws EdtException {
		if (choixSalles == null) {
			return;
		}
		for (int i = 0; i < choixSalles.count(); i++) {
			Salles currentSalle = choixSalles.objectAtIndex(i);
			if (avecControles) {
				if (SalleFactory.testIndividuADroitReserverSalle(eContext, agent, currentSalle,
						((MainClient) EOApplication.sharedApplication()).isReservationSalleParDepositaire()) == false) {
					throw new EdtException("Vous n'avez pas le droit de réserver cette salle (" + currentSalle.salPorte() + ")");
				}
			}
			SalleSouhaitee currentSalleSouhaitee = SalleSouhaitee.createSalleSouhaitee(eContext);
			DBHandler.invalidateObject(eContext, currentSalle);
			currentSalleSouhaitee.setSalleRelationship(currentSalle);
			currentSalleSouhaitee.setSouPosition(new Integer(i));
			reservation.addToSallesSouhaiteesRelationship(currentSalleSouhaitee);
		}
	}

	/** methode pour supprimer des creneaux d'une reservation avec delegation a -ReunionFactory- */
	public void supprimerCreneauxPourResa(Reservation reservation, NSArray<Periodicite> periodsSupprimables) throws EdtException {
		if (reservation.tlocCode().equals("E")) {
			NSArray<ResaExamen> exams = reservation.resaExamens();
			for (int k = 0; k < exams.count(); k++) {
				ExamenEntete entete = exams.objectAtIndex(k).examenEntete();
				if (entete.eentTraite().intValue() == 3) {
					throw new EdtException("Les étiquettes pour l'examen " + entete.eentLibelle()
							+ " ont déjà été éditées.\nImpossible de retirer la semaine.");
				}
			}
		}

		for (int i = 0; i < periodsSupprimables.count(); i++) {
			Periodicite period = periodsSupprimables.objectAtIndex(i);
			reservation.removeFromPeriodicitesRelationship(period);
			eContext.deleteObject(period);
		}
		reservation.setDModification(DateCtrl.now());
		IndividuUlr agent = (IndividuUlr) ((MainClient) EOApplication.sharedApplication()).userInfo("individu");
		reservation.setIndividuAgentRelationship(agent);
		boolean retour = true;
		String msg = null;
		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (Exception exe) {
			msg = exe.getMessage();
			exe.printStackTrace();
			eContext.revert();
			retour = false;
		}
		finally {
			eContext.unlock();
		}
		if (!retour) {
			throw new EdtException("Erreur suppression periodicite :\n" + msg);
		}
	}

	/** pour une verification precise, l'utiliser avec une periodicite seulement, sinon les messages d'alertes seront imprecis */
	public void addPeriodsForReservation(NSArray<NSTimestamp> periodicites, Reservation reservation) throws EdtException {

		if (((MainClient) EOApplication.sharedApplication()).autoriseReservationFerie() == false) {
			for (int idate = 0; idate < periodicites.count(); idate++) {
				NSTimestamp currentDateTest = periodicites.objectAtIndex(idate);
				if (DateCtrl.isHolidayFR(currentDateTest)) {
					throw new EdtException("La réservation en jour férié n'est pas possible.");
				}
			}
		}
		NSArray<Salles> salles = (NSArray<Salles>) reservation.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		NSArray<Salles> choix = (NSArray<Salles>) reservation.valueForKeyPath(Reservation.SALLES_SOUHAITEES_KEY + "." + SalleSouhaitee.SALLE_KEY);
		NSArray<IndividuUlr> occ = (NSArray<IndividuUlr>) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		NSArray<MaquetteAp> ap = (NSArray<MaquetteAp>) reservation.valueForKeyPath(Reservation.RESERVATION_AP_KEY + "."
				+ ReservationAp.MAQUETTE_AP_KEY);
		NSArray<ExamenEntete> exams = (NSArray<ExamenEntete>) reservation.valueForKeyPath(Reservation.RESA_EXAMENS_KEY + "."
				+ ResaExamen.EXAMEN_ENTETE_KEY);

		for (int i = 0; i < occ.count(); i++) {
			IndividuUlr individu = occ.objectAtIndex(i);
			NSArray<NSTimestamp> dispoIndividus = null;
			try {
				dispoIndividus = this.getNonDisponibiliteIndividu(periodicites, individu, null, null, null);
			}
			catch (Exception e) {
			}

			if (dispoIndividus == null) {
				throw new EdtException("Erreur sur le serveur");
			}

			if (dispoIndividus.count() > 0) {
				NSTimestamp d1 = dispoIndividus.objectAtIndex(0);
				NSTimestamp d2 = dispoIndividus.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException(individu.cCivilite() + " " + individu.nomPrenom() + " n'est pas libre entre " + msg);
			}
		}

		NSArray<Salles> sumSalles = salles.arrayByAddingObjectsFromArray(choix);
		for (int i = 0; i < sumSalles.count(); i++) {
			Salles salle = sumSalles.objectAtIndex(i);
			NSArray<NSTimestamp> dispoSalle = null;
			try {
				dispoSalle = this.getNonDisponibiliteSalle(periodicites, salle, null, null);
			}
			catch (Exception e) {
			}

			if (dispoSalle == null) {
				throw new EdtException("Erreur sur le serveur");
			}

			if (dispoSalle.count() > 0) {
				NSTimestamp d1 = dispoSalle.objectAtIndex(0);
				NSTimestamp d2 = dispoSalle.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException("La salle " + salle.salPorte() + " n'est pas libre entre " + msg);
			}
		}

		// test des groupes
		NSMutableArray<ScolGroupeGrp> groupes = new NSMutableArray<ScolGroupeGrp>();
		NSArray<ReservationAp> tmp = reservation.reservationAp();
		for (int i = 0; i < tmp.count(); i++) {
			ReservationAp resAp = tmp.objectAtIndex(i);
			if (resAp.ggrpKey() != null) {
				NSArray<NSTimestamp> res = getNonDisponibiliteGroupe(resAp.maquetteAp(), resAp.scolGroupeGrp(), periodicites, null, null, null, null,
						null);
				if (res.count() > 0) {
					NSTimestamp d1 = res.objectAtIndex(0);
					NSTimestamp d2 = res.objectAtIndex(1);
					String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
					throw new EdtException("Le Groupe " + resAp.scolGroupeGrp() + " n'est pas disponible entre " + msg);
				}
			}
		}

		NSArray<ResaExamen> tmp2 = reservation.resaExamens();
		for (int i = 0; i < tmp2.count(); i++) {
			ResaExamen resExam = tmp2.objectAtIndex(i);
			if (resExam.ggrpKey() != null) {
				NSArray<NSTimestamp> res = getNonDisponibiliteGroupe(null, resExam.scolGroupeGrp(), periodicites, null, null, null, null, null);
				if (res.count() > 0) {
					NSTimestamp d1 = res.objectAtIndex(0);
					NSTimestamp d2 = res.objectAtIndex(1);
					String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
					throw new EdtException("Le Groupe " + resExam.scolGroupeGrp() + " n'est pas disponible entre " + msg);
				}
			}
		}

		if (groupes.count() == 0) {
			// test des APs si pas de groupe.
			for (int idx = 0; idx < ap.count(); idx++) {
				MaquetteAp lAp = ap.objectAtIndex(idx);
				NSArray<NSArray<?>> dispo = getNonDisponibiliteAp(lAp, periodicites, null, true, null, null);
				NSArray<NSTimestamp> dispoDate = (NSArray<NSTimestamp>) dispo.objectAtIndex(1);
				if (dispoDate.count() > 0) {
					NSTimestamp d1 = dispoDate.objectAtIndex(0);
					NSTimestamp d2 = dispoDate.objectAtIndex(1);
					String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
					msg = "L'AP " + lAp.mapLibelle() + " n'est pas disponible entre " + msg;
					if (((MainClient) EOApplication.sharedApplication()).isAppControleApsOccupationBof()) {
						if (WindowHandler.showConfirmation(msg + "\nAbandonner ?")) {
							throw new EdtException(null);
						}
					}
					else {
						throw new EdtException(msg);
					}
				}
			}

			// test des examens si pas de groupes.
			for (int idx = 0; idx < exams.count(); idx++) {
				ExamenEntete exam = exams.objectAtIndex(idx);
				EOGlobalID gidEx = eContext.globalIDForObject(exam);
				EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
				NSArray<NSTimestamp> res = (NSArray<NSTimestamp>) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session",
						"clientSideRequestGetNonDisponibliteExamen", new Class[] { NSArray.class, EOGlobalID.class }, new Object[] { periodicites,
								gidEx }, false);

				if (res.count() > 0) {
					NSTimestamp d1 = res.objectAtIndex(0);
					NSTimestamp d2 = res.objectAtIndex(1);
					String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
					throw new EdtException("L'Examen " + exam.eentLibelle() + " est deja reserv\u00e9 entre " + msg);
				}
			}

		}

		// finalement affectation de la periodicite :
		this.affecterPeriodicites(reservation, periodicites);
		IndividuUlr agent = (IndividuUlr) ((MainClient) EOApplication.sharedApplication()).userInfo("individu");
		reservation.setIndividuAgentRelationship(agent);
		reservation.setDModification(DateCtrl.now());

		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (Exception exe) {
			exe.printStackTrace();
			eContext.revert();
		}
		finally {
			eContext.unlock();
		}

	}

	/** creer une reunion avec les ressources passees en parametre */
	public Reservation creerReunion(Reservation reservation, IndividuUlr agent, NSArray<NSTimestamp> periodicites, NSArray<IndividuUlr> personnes,
			NSArray<StructuresAutorisees> groupes, NSArray<Salles> salles, NSArray<Salles> choixSalles, NSArray<ResaObjet> objets,
			String commentaire, int typeAffichage, boolean saveChanges, boolean avecControles) throws EdtException {

		NSArray<IndividuUlr> tmpPers = personnes;
		if (avecControles) {
			tmpPers = verifDisponibilites(reservation, periodicites, personnes, groupes, salles, choixSalles, objets);
		}

		NSTimestamp now = DateCtrl.now();
		Reservation currentReservation = Reservation.createReservation(eContext, now, now, "r");
		DBHandler.invalidateObject(eContext, agent);
		currentReservation.setIndividuAgentRelationship(agent);

		currentReservation.setResaCommentaire(commentaire);
		switch (typeAffichage) {
		case 0:
			currentReservation.setTlocCode("s"); // reunion visibilite structure
			currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "s"));
			break;
		case 1:
			currentReservation.setTlocCode("r"); // reunion visibilite publique
			currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "r"));
			break;
		case 2:
			currentReservation.setTlocCode("p"); // reunion visibilite privee
			currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "p"));
			break;
		}

		this.affecterPeriodicites(currentReservation, periodicites);
		this.affecterOccupants(currentReservation, tmpPers);
		this.affecterObjets(currentReservation, objets, agent, true);
		this.affecterSalles(currentReservation, salles, agent, now, true);
		this.affecterChoixSalles(currentReservation, choixSalles, agent, true);

		boolean retour = true;
		if (saveChanges) {
			try {
				eContext.lock();
				eContext.saveChanges();
			}
			catch (Exception exe) {
				exe.printStackTrace();
				eContext.revert();
				retour = false;
			}
			finally {
				eContext.unlock();
			}
		}

		if (retour) {
			return currentReservation;
		}
		else {
			return null;
		}
	}

	/** modifier une reunion */
	public Reservation modifierReunion(Reservation reservation, IndividuUlr agent, NSArray<NSTimestamp> periodicites, NSArray<IndividuUlr> personnes,
			NSArray<StructuresAutorisees> groupes, NSArray<Salles> salles, NSArray<Salles> choixSalles, NSArray<ResaObjet> objets,
			String commentaire, int typeAffichage) throws EdtException {

		NSArray<IndividuUlr> tmpPers = verifDisponibilites(reservation, periodicites, personnes, groupes, salles, choixSalles, objets);

		if (!genericFactory().removeResourcesToReservation(reservation, true)) {
			throw new EdtException("Impossible de modifier la reservation : contactez le Service Info SVP");
		}

		Reservation currentReservation = reservation;
		currentReservation.setIndividuAgentRelationship(agent);

		NSTimestamp now = new NSTimestamp();
		currentReservation.setResaCommentaire(commentaire);
		currentReservation.setTlocCode("r"); // reunion visibilite publique
		currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "r"));
		switch (typeAffichage) {
		case 0:
			currentReservation.setTlocCode("s"); // reunion visibilite structure
			currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "s"));
			break;
		case 1:
			currentReservation.setTlocCode("r"); // reunion visibilite publique
			currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "r"));
			break;
		case 2:
			currentReservation.setTlocCode("p"); // reunion visibilite privee
			currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "p"));
			break;
		}
		currentReservation.setDModification(DateCtrl.now());
		this.affecterPeriodicites(currentReservation, periodicites);
		this.affecterOccupants(currentReservation, tmpPers);
		this.affecterSalles(currentReservation, salles, agent, now, true);
		this.affecterChoixSalles(currentReservation, choixSalles, agent, true);

		this.affecterObjets(currentReservation, objets, agent, true);

		boolean retour = true;
		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (Exception exe) {
			exe.printStackTrace();
			eContext.revert();
			retour = false;
		}
		finally {
			eContext.unlock();
		}

		if (retour) {
			return currentReservation;
		}
		else {
			return null;
		}
	}

	/** cree une reservation de type 'blocage' pour les ressources passees en parametre */
	public Reservation creerBlocage(Reservation reservation, IndividuUlr agent, NSArray<NSTimestamp> periodicites, NSArray<IndividuUlr> personnes,
			NSArray<Salles> salles, NSArray<ResaObjet> objets, String commentaire) throws EdtException {

		NSArray<IndividuUlr> tmpPers = verifDisponibilites(reservation, periodicites, personnes, null, salles, null, objets);

		NSTimestamp now = DateCtrl.now();
		Reservation currentReservation = Reservation.createReservation(eContext, now, now, "b");
		DBHandler.invalidateObject(eContext, agent);
		currentReservation.setIndividuAgentRelationship(agent);

		currentReservation.setResaCommentaire(commentaire);
		this.affecterPeriodicites(currentReservation, periodicites);
		this.affecterOccupants(currentReservation, tmpPers);
		this.affecterSalles(currentReservation, salles, agent, now, true);
		this.affecterObjets(currentReservation, objets, agent, true);

		boolean retour = true;
		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (Exception exe) {
			exe.printStackTrace();
			eContext.revert();
			retour = false;
		}
		finally {
			eContext.unlock();
		}

		if (retour) {
			return currentReservation;
		}
		else {
			return null;
		}
	}

	/** cree une reservation de type 'blocage' pour les ressources passees en parametre */
	public Reservation modifierBlocage(Reservation reservation, IndividuUlr agent, NSArray<NSTimestamp> periodicites, NSArray<IndividuUlr> personnes,
			NSArray<Salles> salles, String commentaire) throws EdtException {

		NSArray<IndividuUlr> tmpPers = verifDisponibilites(reservation, periodicites, personnes, null, salles, null, null);

		if (!genericFactory().removeResourcesToReservation(reservation, true)) {
			throw new EdtException("Impossible de modifier la reservation : contactez le Service Info SVP");
		}

		Reservation currentReservation = reservation;
		currentReservation.setIndividuAgentRelationship(agent);

		NSTimestamp now = new NSTimestamp();
		currentReservation.setResaCommentaire(commentaire);
		currentReservation.setTlocCode("b"); // blocage
		currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "b"));
		currentReservation.setDModification(now);
		this.affecterPeriodicites(currentReservation, periodicites);
		this.affecterOccupants(currentReservation, tmpPers);
		this.affecterSalles(currentReservation, salles, agent, now, true);

		boolean retour = true;
		try {
			eContext.lock();
			eContext.saveChanges();
		}
		catch (Exception exe) {
			exe.printStackTrace();
			eContext.revert();
			retour = false;
		}
		finally {
			eContext.unlock();
		}

		if (retour) {
			return currentReservation;
		}
		else {
			return null;
		}
	}

}
