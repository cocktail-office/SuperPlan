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
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteEc;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionAp;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteResponsableEc;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ReservationObjet;
import org.cocktail.superplan.client.metier.ReservationSemestre;
import org.cocktail.superplan.client.metier.SalleSouhaitee;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolDroitDiplome;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.ScolGroupeObjet;
import org.cocktail.superplan.client.metier.ScolGroupeObjetElp;
import org.cocktail.superplan.client.metier.ScolGroupeObjetVdi;
import org.cocktail.superplan.client.metier.TypeLocation;
import org.cocktail.superplan.client.metier.VParcoursAp;
import org.cocktail.superplan.client.metier.VParcoursEx;
import org.cocktail.superplan.client.metier.VRespAp;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;

public class EnseignementFactory extends ReunionFactory {

	private EOEditingContext eContext;
	private MaquetteEc maquetteEc;
	private GenericFactory genericFactory;
	// private ReunionFactory reunionFactory;
	private EODistributedObjectStore objectStore;

	public EnseignementFactory(EOEditingContext eContext) {
		super(eContext);
		this.eContext = eContext;
		this.genericFactory = new GenericFactory(eContext);
		objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
	}

	/** setter editingContext */
	public void setEditingContext(EOEditingContext ec) {
		this.eContext = ec;
	}

	/** getter editingContext */
	public EOEditingContext getEditingContext() {
		return this.eContext;
	}

	public MaquetteEc getEcFromAutorisation() {
		return null;
	}

	public MaquetteAp getApFromAutorisation() {
		MaquetteAp uneAp = null;
		return uneAp;
	}

	/* utiliser ces methodes */
	/** retourne les groupes -GrpGpeObj- de l'EC actuellement traite */
	public NSArray getGroupeObjetFromEc(MaquetteEc uneEc) {
		NSArray aps = (NSArray) uneEc.valueForKeyPath(MaquetteEc.MAQUETTE_REPARTITION_APS_KEY + "." + MaquetteRepartitionAp.MAQUETTE_AP_KEY);
		NSMutableArray grpObjs = new NSMutableArray();
		for (int i = 0; i < aps.count(); i++) {
			MaquetteAp currentAp = (MaquetteAp) aps.objectAtIndex(i);
			grpObjs.addObjectsFromArray(currentAp.scolGroupeObjets());
		}
		return grpObjs;
	}

	/** retourne les groupes -GrpGroupe- de l'EC actuellement traite */
	public NSArray getGroupesFromEc(MaquetteEc uneEc) {
		NSArray aps = (NSArray) maquetteEc.valueForKeyPath(MaquetteEc.MAQUETTE_REPARTITION_APS_KEY + "." + MaquetteRepartitionAp.MAQUETTE_AP_KEY);
		NSMutableArray groupes = new NSMutableArray();
		for (int i = 0; i < aps.count(); i++) {
			MaquetteAp currentAp = (MaquetteAp) aps.objectAtIndex(i);
			groupes.addObjectsFromArray((NSArray) currentAp.valueForKeyPath(MaquetteAp.SCOL_GROUPE_OBJETS_KEY + "."
					+ ScolGroupeObjet.SCOL_GROUPE_GRP_KEY));
		}
		return groupes;
	}

	/* OK */

	/** retourne les -GrpObj- depuis l'Ap passe en parametres */
	public NSArray getGrpObjFromAp(MaquetteAp uneAp) {
		return uneAp.scolGroupeObjets();
	}

	/** retourne les -GrpGroupe- depuis l'Ap passe en parametres */
	public NSArray getGroupesFromAp(MaquetteAp uneAp) {
		return (NSArray) uneAp.valueForKeyPath(MaquetteAp.SCOL_GROUPE_OBJETS_KEY + "." + ScolGroupeObjet.SCOL_GROUPE_GRP_KEY);
	}

	/* Partie de creation d'EDT */

	/**
	 * cree une nouvelle reservation avec les informations de la reservation passee en parameres sauf les periodicites qui sont remplacees
	 * par celles passees en parametres
	 */
	public Reservation copyReservationAvecPeriodicites(Reservation reservation, NSArray periodicites, boolean save) throws EdtException {

		Reservation currentReservation = Reservation.createReservation(eContext, DateCtrl.now(), DateCtrl.now(), reservation.tlocCode());

		NSArray salles = (NSArray) reservation.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		NSArray sallesSouhaitees = (NSArray) reservation.valueForKeyPath(Reservation.SALLES_SOUHAITEES_KEY + "." + SalleSouhaitee.SALLE_KEY);
		NSArray occupants = (NSArray) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		NSArray objets = (NSArray) reservation.valueForKeyPath(Reservation.RESERVATION_OBJETS_KEY + "." + ReservationObjet.RESA_OBJET_KEY);
		currentReservation.setResaCommentaire(reservation.resaCommentaire());

		NSArray resaAp = reservation.reservationAp();

		for (int i = 0; i < resaAp.count(); i++) {
			ReservationAp currentResaAp = (ReservationAp) resaAp.objectAtIndex(i);
			MaquetteAp ap = currentResaAp.maquetteAp();
			ScolGroupeGrp grp = currentResaAp.scolGroupeGrp();
			ReservationAp currentReservationAp = ReservationAp.createReservationAp(eContext, new Integer(0));
			currentReservation.addToReservationApRelationship(currentReservationAp);
			currentReservationAp.setScolGroupeGrpRelationship(grp);
			DBHandler.invalidateObject(eContext, ap);
			currentReservationAp.setMaquetteApRelationship(ap);
		}

		currentReservation.setIndividuAgentRelationship(reservation.individuAgent());

		super.affecterOccupants(currentReservation, occupants);
		super.affecterPeriodicites(currentReservation, periodicites);
		super.affecterSalles(currentReservation, salles, reservation.individuAgent(), DateCtrl.now(), true);
		super.affecterChoixSalles(currentReservation, sallesSouhaitees, reservation.individuAgent(), true);
		super.affecterObjets(currentReservation, objets, reservation.individuAgent(), true);

		if (save) {
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
		else {
			return currentReservation;
		}
	}

	/** methode pour supprimer des creneaux d'une reservation avec delegation a -ReunionFactory- */
	public void supprimerCreneauxPourResa(Reservation reservation, NSArray periodsSupprimables) throws EdtException {
		super.supprimerCreneauxPourResa(reservation, periodsSupprimables);
	}

	public static NSArray ecsForAp(EOEditingContext ec, MaquetteAp ap) {
		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(MaquetteRepartitionAp.MAQUETTE_AP_KEY + " = %@", new NSArray(ap));
		NSArray a = MaquetteRepartitionAp.fetchMaquetteRepartitionAps(ec, qualifier, null);
		return (NSArray) a.valueForKey(MaquetteRepartitionAp.MAQUETTE_EC_KEY);
	}

	public static String libelleEcsForAp(EOEditingContext ec, MaquetteAp ap) {
		StringBuffer buffer = new StringBuffer();
		NSArray ecs = ecsForAp(ec, ap);
		for (int i = 0; i < ecs.count(); i++) {
			buffer.append(ecs.objectAtIndex(i));
		}
		return buffer.toString();
	}

	public Reservation modifierEdt(Reservation reservation, IndividuUlr agent, NSArray occupants, NSArray periodicites, NSArray apGrps,
			NSArray salles, NSArray choixSalles, NSArray objets, String commentaire) throws EdtException {

		String format = "%d/%m/%Y %H:%M";
		// EOGlobalID gidResa = eContext.globalIDForObject(reservation);

		for (int i = 0; i < apGrps.count(); i++) {
			NSDictionary apGroupe = (NSDictionary) apGrps.objectAtIndex(i);
			MaquetteAp ap = (MaquetteAp) apGroupe.valueForKey("AP");
			ScolGroupeGrp grp = null;
			Object objet = apGroupe.valueForKey("GRP");

			if (!(objet instanceof String)) {
				grp = (ScolGroupeGrp) apGroupe.valueForKey("GRP");
			}

			// teste si groupes non occupes

			if (grp != null) {
				NSArray<NSArray<?>> dispo = getNonDisponibiliteGroupeAvecAps(ap, grp, periodicites, reservation, true, null, null, null, Boolean.TRUE);
				NSArray<NSTimestamp> dispoDate = (NSArray<NSTimestamp>) dispo.objectAtIndex(1);
				if (dispoDate.count() != 0) {
					NSArray<Integer> dispoAp = (NSArray<Integer>) dispo.objectAtIndex(0);
					NSTimestamp d1 = dispoDate.objectAtIndex(0);
					NSTimestamp d2 = dispoDate.objectAtIndex(1);
					String msg = FormatHandler.dateToStr(d1, format) + " et " + FormatHandler.dateToStr(d2, format);
					msg += " (créneau occupé par l'AP : " + MaquetteAp.fetchMaquetteAp(eContext, MaquetteAp.MAP_KEY_KEY, dispoAp.objectAtIndex(0))
							+ ")";
					throw new EdtException("Le groupe " + grp + " n'est pas libre entre " + msg);
				}
			}
			else {
				NSArray<NSArray<?>> dispo = getNonDisponibiliteAp(ap, periodicites, reservation, true, null, null);
				NSArray<NSTimestamp> dispoDate = (NSArray<NSTimestamp>) dispo.objectAtIndex(1);
				if (dispoDate.count() != 0) {
					NSArray<Integer> dispoAp = (NSArray<Integer>) dispo.objectAtIndex(0);
					NSTimestamp d1 = dispoDate.objectAtIndex(0);
					NSTimestamp d2 = dispoDate.objectAtIndex(1);

					String msg = FormatHandler.dateToStr(d1, format) + " et " + FormatHandler.dateToStr(d2, format);
					msg += " (créneau occupé par l'AP : " + MaquetteAp.fetchMaquetteAp(eContext, MaquetteAp.MAP_KEY_KEY, dispoAp.objectAtIndex(0))
							+ ")";
					msg = "L'AP " + ap.mapLibelle() + " n'est pas libre entre le " + msg;
					if (((MainClient) EOApplication.sharedApplication()).isAppControleApsOccupationBof()) {
						if (WindowHandler.showConfirmation(msg + "\nAbandonner ?")) {
							return null;
						}
					}
					else {
						throw new EdtException(msg);
					}
				}
			}

		}

		// reunionFactory = new ReunionFactory(eContext);

		// verfification disponibilite salles
		NSArray sumSalles = salles.arrayByAddingObjectsFromArray(choixSalles);
		for (int i = 0; i < sumSalles.count(); i++) {
			Salles salle = (Salles) sumSalles.objectAtIndex(i);
			NSArray dispoSalle = null;
			try {
				dispoSalle = super.getNonDisponibiliteSalle(periodicites, salle, reservation, null);
			}
			catch (Exception e) {
			}

			if (dispoSalle == null) {
				throw new EdtException("Erreur sur le serveur");
			}

			if (dispoSalle.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispoSalle.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispoSalle.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException("La salle " + salle.salPorte() + " n'est pas libre entre " + msg);
			}
		}

		// verfification disponibilite individus
		for (int i = 0; i < occupants.count(); i++) {
			IndividuUlr individu = (IndividuUlr) occupants.objectAtIndex(i);
			NSArray dispoIndividus = null;
			try {
				dispoIndividus = getNonDisponibiliteIndividu(periodicites, individu, reservation, null, null);
			}
			catch (Exception e) {
			}

			if (dispoIndividus == null) {
				throw new EdtException("Erreur sur le serveur");
			}

			if (dispoIndividus.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispoIndividus.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispoIndividus.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException(individu.cCivilite() + " " + individu.nomPrenom() + " n'est pas libre entre " + msg);
			}
		}

		for (int i = 0; i < objets.count(); i++) {
			ResaObjet currentObjet = (ResaObjet) objets.objectAtIndex(i);
			for (int k = 0; k < periodicites.count(); k += 2) {
				NSTimestamp d1 = (NSTimestamp) periodicites.objectAtIndex(k);
				NSTimestamp d2 = (NSTimestamp) periodicites.objectAtIndex(k + 1);

				if (getNonDisponibiliteObjet(new NSArray<NSTimestamp>(new NSTimestamp[] { d1, d2 }), currentObjet, reservation, null).count() > 0) {
					String msg = FormatHandler.dateToStr(d1, FORMAT) + " et " + FormatHandler.dateToStr(d2, FORMAT);
					throw new EdtException("L'objet " + currentObjet.roLibelle1() + " n'est pas libre entre " + msg);
				}

			}
		}

		if (!genericFactory.removeResourcesToReservation(reservation, true)) {
			throw new EdtException("Impossible de modifier la reservation : contactez le Service Info SVP");
		}

		Reservation currentReservation = reservation;

		NSTimestamp now = DateCtrl.now();

		String tloc = "CM";
		for (int i = 0; i < apGrps.count(); i++) {
			NSDictionary<String, Object> apGroupe = (NSDictionary<String, Object>) apGrps.objectAtIndex(i);
			MaquetteAp ap = (MaquetteAp) apGroupe.valueForKey("AP");
			DBHandler.invalidateObject(eContext, ap);
			ap = MaquetteAp.fetchMaquetteAp(eContext, MaquetteAp.MAP_KEY_KEY, ap.mapKey());
			ScolGroupeGrp grp = null;
			Object objet = apGroupe.valueForKey("GRP");
			// un bug fait sortir le groupe de l'editingContext : solution : le refetcher a partir de son ggrpKey !
			if (objet instanceof ScolGroupeGrp) {
				grp = (ScolGroupeGrp) apGroupe.valueForKey("GRP");
				grp = ScolGroupeGrp.fetchScolGroupeGrp(eContext, ScolGroupeGrp.GGRP_KEY_KEY, grp.ggrpKey());
			}

			ReservationAp currentReservationAp = ReservationAp.createReservationAp(eContext, new Integer(0));
			currentReservationAp.setScolGroupeGrpRelationship(grp);
			currentReservationAp.setMaquetteApRelationship(ap);
			currentReservation.addToReservationApRelationship(currentReservationAp);
			tloc = ap.mhcoCode();
		}

		currentReservation.setResaCommentaire(commentaire);
		currentReservation.setTlocCode(tloc);
		currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, tloc));
		currentReservation.setIndividuAgentRelationship(agent);
		currentReservation.setDModification(now);

		super.affecterOccupants(currentReservation, occupants);
		super.affecterPeriodicites(currentReservation, periodicites);
		super.affecterSalles(currentReservation, salles, agent, now, true);
		super.affecterChoixSalles(currentReservation, choixSalles, agent, true);

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

	/** retourne les groupes rattaches a l'AP */
	public static NSArray<ScolGroupeGrp> getGroupesForAp(EOEditingContext eContext, MaquetteAp ap, MaquetteSemestre semestre) {
		if (ap != null) {
			EOSortOrdering sortGrp = EOSortOrdering.sortOrderingWithKey(ScolGroupeGrp.GGRP_CODE_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(ScolGroupeGrp.SCOL_GROUPE_OBJET_ELPS_KEY + "." + ScolGroupeObjetElp.MAQUETTE_AP_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, ap));
			if (semestre != null) {
				quals.addObject(new EOKeyValueQualifier(ScolGroupeGrp.SCOL_GROUPE_OBJET_VDIS_KEY + "." + ScolGroupeObjetVdi.SEMESTRE_KEY,
						EOKeyValueQualifier.QualifierOperatorEqual, semestre));
			}
			return ScolGroupeGrp.fetchScolGroupeGrps(eContext, new EOAndQualifier(quals), new NSArray<EOSortOrdering>(sortGrp));
		}
		else {
			return new NSArray<ScolGroupeGrp>();
		}
	}

	/** renvoie une chaine décrivant le Diplome/Semestre/Parcours de l'AP */
	public String detailDiplomePourAp(MaquetteAp ap) {

		EOQualifier qualAp = DBHandler.getSimpleQualifier(VParcoursAp.AP_KEY, ap);
		NSArray vParcAPs = VParcoursAp.fetchVParcoursAps(eContext, qualAp, null);
		vParcAPs = DBHandler.retirerMultiples(vParcAPs);

		StringBuffer bfr = new StringBuffer();
		for (int i = 0; i < vParcAPs.count(); i++) {
			VParcoursAp currentVParcAp = (VParcoursAp) vParcAPs.objectAtIndex(i);

			bfr.append(currentVParcAp.diplome().fgraCode());
			bfr.append(" ");
			FormationSpecialisation spe = currentVParcAp.specialisation();

			// NSArray habs = spe.habilitations();
			// Number niv = ((FormationHabilitation) habs.objectAtIndex(0)).fhabNiveau();

			int msemOrdre = currentVParcAp.semestre().msemOrdre().intValue();
			int niveau = (msemOrdre + msemOrdre % 2) / 2;
			bfr.append(niveau);
			bfr.append(" : ");
			bfr.append(currentVParcAp.diplome().fdipAbreviation());

			if (spe.fspnLibelle() != null) {
				bfr.append("\n - Specialité : ");
				bfr.append(spe.fspnLibelle());
			}
			bfr.append("\n - ");
			bfr.append(currentVParcAp.parcours().mparLibelle());
			bfr.append("\n - ");
			bfr.append("Semestre ");
			bfr.append(currentVParcAp.semestre().msemOrdre().toString());

			if (i < vParcAPs.count() - 1) {
				bfr.append("\n&\n");
			}
		}

		return bfr.toString();
	}

	/** renvoie une chaine décrivant le Diplome/Semestre/Parcours de l'entete d'examen */
	public String detailDiplomePourExamen(ExamenEntete examenEntete) {
		EOQualifier qualAp = DBHandler.getSimpleQualifier(VParcoursEx.EXAMEN_ENTETE_KEY, examenEntete);
		NSArray array = VParcoursEx.fetchVParcoursExes(eContext, qualAp, null);
		array = DBHandler.retirerMultiples(array);

		StringBuffer bfr = new StringBuffer();
		for (int i = 0; i < array.count(); i++) {
			VParcoursEx currentVParcEx = (VParcoursEx) array.objectAtIndex(i);

			bfr.append(currentVParcEx.diplome().fgraCode());
			bfr.append(" ");
			FormationSpecialisation spe = currentVParcEx.specialisation();

			NSArray habs = spe.habilitations();

			Number niv = ((FormationHabilitation) habs.objectAtIndex(0)).fhabNiveau();

			bfr.append(niv.toString());
			bfr.append(" : ");
			bfr.append(currentVParcEx.diplome().fdipAbreviation());

			if (spe.fspnLibelle() != null) {
				bfr.append("\n - Specialit\u00e9 : ");
				bfr.append(spe.fspnLibelle());
			}
			bfr.append("\n - ");
			bfr.append(currentVParcEx.parcours().mparLibelle());
			bfr.append("\n - ");
			bfr.append("Semestre ");
			bfr.append(currentVParcEx.semestre().msemOrdre().toString());

			if (i < array.count() - 1) {
				bfr.append("\n&\n");
			}
		}

		return bfr.toString();
	}

	public Reservation creerEdtEnseignement(Reservation resT, IndividuUlr agent, NSArray<IndividuUlr> occupants, NSArray<NSTimestamp> periodicites,
			NSArray<NSKeyValueCoding> apGrps, NSArray<Salles> salles, NSArray<Salles> choixSalles, NSArray<ResaObjet> objets, String commentaire,
			boolean saveChanges, boolean avecControles) throws EdtException {

		String format = "%d/%m/%Y %H:%M";
		if (avecControles) {
			// aps
			for (int i = 0; i < apGrps.count(); i++) {
				NSKeyValueCoding apGroupe = apGrps.objectAtIndex(i);
				MaquetteAp ap = (MaquetteAp) apGroupe.valueForKey("AP");
				ScolGroupeGrp grp = null;
				Object objet = apGroupe.valueForKey("GRP");

				if (!(objet instanceof String)) {
					grp = (ScolGroupeGrp) apGroupe.valueForKey("GRP");
				}

				if (grp != null) {
					NSArray<NSArray<?>> dispo = getNonDisponibiliteGroupeAvecAps(ap, grp, periodicites, resT, true, null, null, null, Boolean.TRUE);
					NSArray<NSTimestamp> dispoDate = (NSArray<NSTimestamp>) dispo.objectAtIndex(1);
					if (dispoDate.count() > 0) {
						NSArray<Integer> dispoAp = (NSArray<Integer>) dispo.objectAtIndex(0);
						NSTimestamp d1 = dispoDate.objectAtIndex(0);
						NSTimestamp d2 = dispoDate.objectAtIndex(1);
						String msg = FormatHandler.dateToStr(d1, format) + " et " + FormatHandler.dateToStr(d2, format);
						msg += " (créneau occupé par l'AP : "
								+ MaquetteAp.fetchMaquetteAp(eContext, MaquetteAp.MAP_KEY_KEY, dispoAp.objectAtIndex(0)) + ")";
						throw new EdtException("Le groupe " + grp + " n'est pas libre entre " + msg);
					}
				}
				else {
					NSArray<NSArray<?>> dispo = getNonDisponibiliteAp(ap, periodicites, resT, true, null, null);
					NSArray<NSTimestamp> dispoDate = (NSArray<NSTimestamp>) dispo.objectAtIndex(1);
					if (dispoDate.count() > 0) {
						NSArray<Integer> dispoAp = (NSArray<Integer>) dispo.objectAtIndex(0);
						NSTimestamp d1 = dispoDate.objectAtIndex(0);
						NSTimestamp d2 = dispoDate.objectAtIndex(1);

						String msg = FormatHandler.dateToStr(d1, format) + " et " + FormatHandler.dateToStr(d2, format);
						msg += " (créneau occupé par l'AP : "
								+ MaquetteAp.fetchMaquetteAp(eContext, MaquetteAp.MAP_KEY_KEY, dispoAp.objectAtIndex(0)) + ")";
						msg = "Les étudiants de '" + ap.mapLibelle() + "' sont occupés entre le " + msg;
						if (((MainClient) EOApplication.sharedApplication()).isAppControleApsOccupationBof()) {
							if (WindowHandler.showConfirmation(msg + "\nAbandonner ?")) {
								return null;
							}
						}
						else {
							throw new EdtException(msg);
						}
					}
				}
			}

			// salles
			NSArray<Salles> sumSalles = salles.arrayByAddingObjectsFromArray(choixSalles);
			for (int i = 0; i < sumSalles.count(); i++) {
				Salles salle = sumSalles.objectAtIndex(i);
				NSArray<NSTimestamp> dispoSalle = null;
				try {
					dispoSalle = super.getNonDisponibiliteSalle(periodicites, salle, resT, null);
				}
				catch (Exception e) {
					e.printStackTrace();
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

			// individus
			for (int i = 0; i < occupants.count(); i++) {
				IndividuUlr individu = occupants.objectAtIndex(i);
				NSArray<NSTimestamp> dispoIndividus = null;
				try {
					dispoIndividus = super.getNonDisponibiliteIndividu(periodicites, individu, resT, null, null);
				}
				catch (Exception e) {
					e.printStackTrace();
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

			// objets
			for (int i = 0; i < objets.count(); i++) {
				ResaObjet currentObjet = objets.objectAtIndex(i);
				NSArray<NSTimestamp> dispo = super.getNonDisponibiliteObjet(periodicites, currentObjet, resT, null);
				if (dispo.count() > 0) {
					NSTimestamp d1 = dispo.objectAtIndex(0);
					NSTimestamp d2 = dispo.objectAtIndex(1);
					String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
					throw new EdtException("L'objet " + currentObjet.roLibelle1() + " n'est pas libre entre " + msg);
				}
			}
		}

		NSTimestamp now = new NSTimestamp();
		Reservation currentReservation = Reservation.createReservation(eContext, now, now, "CM");
		String tloc = "CM";
		for (int i = 0; i < apGrps.count(); i++) {
			NSKeyValueCoding apGroupe = apGrps.objectAtIndex(i);
			MaquetteAp ap = (MaquetteAp) apGroupe.valueForKey("AP");
			ScolGroupeGrp grp = null;
			Object objet = apGroupe.valueForKey("GRP");
			if (objet != null && !(objet instanceof String)) {
				grp = (ScolGroupeGrp) apGroupe.valueForKey("GRP");
			}
			ReservationAp currentReservationAp = ReservationAp.createReservationAp(eContext, new Integer(0));
			currentReservation.addToReservationApRelationship(currentReservationAp);
			currentReservationAp.setScolGroupeGrpRelationship(grp);
			DBHandler.invalidateObject(eContext, ap);
			currentReservationAp.setMaquetteApRelationship(ap);
			tloc = ap.mhcoCode();
		}

		currentReservation.setResaCommentaire(commentaire);
		currentReservation.setTlocCode(tloc);
		currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, tloc));

		affecterOccupants(currentReservation, occupants);
		affecterPeriodicites(currentReservation, periodicites);
		affecterSalles(currentReservation, salles, agent, now, avecControles);
		affecterChoixSalles(currentReservation, choixSalles, agent, avecControles);
		affecterObjets(currentReservation, objets, agent, avecControles);

		currentReservation.setIndividuAgentRelationship(agent);

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

	/**
	 * creation de reservation avec les etudiants de tout un parcours/semeste ou un groupe d'étudiants
	 */
	public Reservation creerEdtSemestre(Reservation resT, IndividuUlr agent, NSArray occupants, NSArray periodicites, NSArray semestresGroupes,
			NSArray salles, NSArray choixSalles, NSArray objets, String commentaire) throws EdtException {

		String format = "%d/%m/%Y %H:%M";
		NSDictionary<String, Object> semestreGroupe = null;
		ScolGroupeGrp groupe = null;
		MaquetteSemestre semestre = null;

		for (int i = 0; i < semestresGroupes.count(); i++) {
			semestreGroupe = (NSDictionary<String, Object>) semestresGroupes.objectAtIndex(i);
			semestre = (MaquetteSemestre) semestreGroupe.valueForKey("SEM");

			Object objet = semestreGroupe.valueForKey("GRP");
			if (!(objet instanceof String)) {
				groupe = (ScolGroupeGrp) semestreGroupe.valueForKey("GRP");
			}

			// teste si groupes non occupes
			boolean groupeLibre = true;
			boolean semestreLibre = true;

			if (groupe != null) {

				NSArray dispoGroupes = getNonDisponibiliteGroupe(null, groupe, periodicites, resT, null, null, null, null);

				groupeLibre = (dispoGroupes.count() == 0);

				if (!groupeLibre) {
					NSTimestamp d1 = (NSTimestamp) dispoGroupes.objectAtIndex(0);
					NSTimestamp d2 = (NSTimestamp) dispoGroupes.objectAtIndex(1);
					String msg = FormatHandler.dateToStr(d1, format) + " et " + FormatHandler.dateToStr(d2, format);
					throw new EdtException("Le groupe " + groupe + " n'est pas libre entre " + msg);
				}
			}
			else {
				NSArray indispoSemestre = indisponibiliteSemestre(semestre, periodicites, resT);
				semestreLibre = (indispoSemestre.count() == 0);
				if (!semestreLibre) {
					NSTimestamp d1 = (NSTimestamp) indispoSemestre.objectAtIndex(0);
					NSTimestamp d2 = (NSTimestamp) indispoSemestre.objectAtIndex(1);

					String msg = FormatHandler.dateToStr(d1, format) + " et " + FormatHandler.dateToStr(d2, format);
					throw new EdtException("Les étudiants de '" + semestre.toString() + "' sont déjà occupés entre " + msg);
				}
			}
		}

		// verfification disponibilite salles
		NSArray sumSalles = salles.arrayByAddingObjectsFromArray(choixSalles);
		for (int i = 0; i < sumSalles.count(); i++) {
			Salles salle = (Salles) sumSalles.objectAtIndex(i);
			NSArray dispoSalle = null;

			try {
				dispoSalle = super.getNonDisponibiliteSalle(periodicites, salle, resT, null);
			}
			catch (Exception e) {
			}

			if (dispoSalle == null) {
				throw new EdtException("Erreur sur le serveur");
			}

			if (dispoSalle.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispoSalle.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispoSalle.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException("La salle " + salle.salPorte() + " n'est pas libre entre " + msg);
			}
		}

		// verfification disponibilite individus
		for (int i = 0; i < occupants.count(); i++) {
			IndividuUlr individu = (IndividuUlr) occupants.objectAtIndex(i);
			NSArray dispoIndividus = null;

			try {
				dispoIndividus = super.getNonDisponibiliteIndividu(periodicites, individu, resT, null, null);
			}
			catch (Exception e) {
			}

			if (dispoIndividus == null) {
				throw new EdtException("Erreur sur le serveur");
			}

			if (dispoIndividus.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispoIndividus.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispoIndividus.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException(individu.cCivilite() + " " + individu.nomPrenom() + " n'est pas libre entre " + msg);
			}
		}

		// les objets
		for (int i = 0; i < objets.count(); i++) {
			ResaObjet currentObjet = (ResaObjet) objets.objectAtIndex(i);
			NSArray dispo = super.getNonDisponibiliteObjet(periodicites, currentObjet, resT, null);
			if (dispo.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispo.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispo.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException("L'objet " + currentObjet.roLibelle1() + " n'est pas libre entre " + msg);
			}
		}

		Reservation currentReservation = Reservation.createReservation(eContext, DateCtrl.now(), DateCtrl.now(), Reservation.TLOC_SEMESTRE);
		NSTimestamp now = new NSTimestamp();

		for (int i = 0; i < semestresGroupes.count(); i++) {
			semestreGroupe = (NSDictionary) semestresGroupes.objectAtIndex(i);
			semestre = (MaquetteSemestre) semestreGroupe.valueForKey("SEM");

			Object objet = semestreGroupe.valueForKey("GRP");
			if (!(objet instanceof String)) {
				groupe = (ScolGroupeGrp) semestreGroupe.valueForKey("GRP");
			}

			ReservationSemestre currentResSem = ReservationSemestre.createReservationSemestre(eContext, new Integer(0));

			currentReservation.addToReservationsSemestresRelationship(currentResSem);
			currentResSem.setScolGroupeGrpRelationship(groupe);
			currentResSem.setSemestreRelationship(semestre);

		}

		currentReservation.setResaCommentaire(commentaire);

		super.affecterOccupants(currentReservation, occupants);
		super.affecterPeriodicites(currentReservation, periodicites);
		super.affecterSalles(currentReservation, salles, agent, now, true);
		super.affecterChoixSalles(currentReservation, choixSalles, agent, true);

		this.affecterObjets(currentReservation, objets, agent, true);

		currentReservation.setIndividuAgentRelationship(agent);

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

	// VERIFICATION DES INDISPONIBILITES DES RESSOURCES ENSEIGNEMENT ...

	public NSArray indisponibiliteExamen(ExamenEntete examen, NSArray periodicites, Reservation resT) {
		EOGlobalID gidResa = null;
		if (resT != null) {
			gidResa = eContext.globalIDForObject(resT);
		}

		EOGlobalID gidExamen = eContext.globalIDForObject(examen);
		return (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestVerifExamenPourModification", new Class[] {
				EOGlobalID.class, NSArray.class, EOGlobalID.class }, new Object[] { gidResa, periodicites, gidExamen }, false);

	}

	public NSArray indisponibiliteSemestre(MaquetteSemestre semestre, NSArray periodicites, Reservation resT) {
		EOGlobalID gidResa = null;
		if (resT != null) {
			gidResa = eContext.globalIDForObject(resT);
		}

		EOGlobalID gidSemestre = eContext.globalIDForObject(semestre);
		return (NSArray) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestVerifSemestrePourModification",
				new Class[] { EOGlobalID.class, NSArray.class, EOGlobalID.class }, new Object[] { gidResa, periodicites, gidSemestre }, false);
	}

	/** retourne les responsables de l'AP */
	public NSArray getResponsablesAp(MaquetteAp ap, Number annee) {
		EOQualifier qualResp = DBHandler.getQualifier(VRespAp.AP_KEY + " = %@ and " + VRespAp.FANN_KEY_KEY + " = %@", new NSArray(new Object[] { ap,
				annee }));
		NSArray tmp = VRespAp.fetchVRespAps(eContext, qualResp, null);
		return (NSArray) tmp.valueForKey(VRespAp.INDIVIDU_KEY);
	}

	/** retourne true si l'individu est responsable de l'EC, false sinon */
	public boolean isResponsableEc(Number persID, MaquetteEc ec) {
		NSArray resp = (NSArray) ec.valueForKeyPath(MaquetteEc.MAQUETTE_RESPONSABLE_ECS_KEY + "." + MaquetteResponsableEc.PERS_ID_KEY);

		for (int i = 0; i < resp.count(); i++) {
			if (((Number) resp.objectAtIndex(i)).intValue() == persID.intValue()) {
				return true;
			}
		}
		return false;
	}

	/** ajoute l'individu comme responsable Enseignant de l'EC */
	public void addToResponsablesEc(Number persID, Number annee, MaquetteEc ec) throws Exception {
		MaquetteResponsableEc mrespEc = MaquetteResponsableEc.createMaquetteResponsableEc(eContext, (Integer) annee, "E");
		mrespEc.setPersId((Integer) persID);
		ec.addToMaquetteResponsableEcsRelationship(mrespEc);
		eContext.saveChanges();
	}

	public NSArray getHabilitationsPourDroit(String diplome, String grade, Number annee, Number droit) {

		NSMutableArray sumQualifiers = new NSMutableArray();
		if (!diplome.equals("")) {
			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY + " caseInsensitiveLike '*"
					+ diplome + "*'" + " or " + FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY + " caseInsensitiveLike '*"
					+ diplome + "*'", null));
		}

		if (!grade.equals("")) {
			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FGRA_CODE_KEY + " caseInsensitiveLike '*" + grade
					+ "*'", null));
		}

		sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FANN_KEY_KEY + " = %@", new NSArray(annee)));

		if (droit != null) {
			EOQualifier qualDroits = DBHandler.getSimpleQualifier(FormationHabilitation.DROIT_DIPLOMES_KEY + "." + ScolDroitDiplome.DLOG_KEY_KEY
					+ " = %@", droit);
			sumQualifiers.addObject(qualDroits);
		}

		return FormationHabilitation.fetchFormationHabilitations(eContext, new EOAndQualifier(sumQualifiers), null);
	}

	/** retourne les semestres de l'habilitation */
	public NSArray getSemestres(FormationHabilitation hab, MaquetteParcours parc, FormationAnnee annee) {
		int niveau = hab.fhabNiveau().intValue();
		int ordre1 = niveau * 2 - 1;
		int ordre2 = ordre1 + 1;

		EOQualifier qualSem = EOQualifier.qualifierWithQualifierFormat("(" + MaquetteRepartitionSem.SEMESTRE_KEY + "."
				+ MaquetteSemestre.MSEM_ORDRE_KEY + " = %@ or " + MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY
				+ " = %@) and " + MaquetteRepartitionSem.PARCOURS_KEY + " = %@ and " + MaquetteRepartitionSem.FANN_KEY_KEY + " = %@", new NSArray(
				new Object[] { new Integer(ordre1), new Integer(ordre2), parc, annee.fannKey() }));

		return (NSArray) MaquetteRepartitionSem.fetchMaquetteRepartitionSem(eContext, qualSem).valueForKey(MaquetteRepartitionSem.SEMESTRE_KEY);
	}

}
