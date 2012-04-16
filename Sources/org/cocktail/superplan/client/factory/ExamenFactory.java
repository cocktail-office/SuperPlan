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
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.ResaExamen;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.TypeLocation;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;

public class ExamenFactory extends EnseignementFactory {

	private static final String FORMAT = "%d/%m/%Y %H:%M";
	private EOEditingContext eContext;

	public ExamenFactory(EOEditingContext eContext) {
		super(eContext);
		this.eContext = eContext;
	}

	public EOEditingContext editingContext() {
		return eContext;
	}

	/** permet d'affecter l'horaire reel de debut et de fin d'examen */
	private boolean affecterHoraireEntete(ExamenEntete entete, NSArray<NSTimestamp> periodicites) {
		boolean retour = true;
		NSTimestamp localDeb, localFin;
		try {
			localDeb = periodicites.objectAtIndex(0);
			localFin = periodicites.objectAtIndex(1);

			entete.setEentDateDebut(localDeb);
			entete.setEentDateFin(localFin);
		}
		catch (Exception e) {
			e.printStackTrace();
			retour = false;
		}
		return retour;
	}

	/** permet de modifier une reservation de type EXAMEN, si l'examen n'a pas encore ete traite */
	public Reservation modifierExamen(Reservation reservation, IndividuUlr agent, NSArray occupants, NSArray periodicites, NSArray entetesGrps,
			NSArray salles, NSArray choixSalles, NSArray objets, String commentaire) throws EdtException {

		if (periodicites == null || periodicites.count() == 0) {
			throw new EdtException("Aucune date passée pour modifier la réservation d'examen !");
		}
		if (periodicites.count() > 2) {
			throw new EdtException("Une réservation d'examen ne peut être modifiée sur plusieurs semaines !");
		}
		EOGlobalID gidResa = eContext.globalIDForObject(reservation);

		for (int i = 0; i < entetesGrps.count(); i++) {
			NSDictionary enteteGroupe = (NSDictionary) entetesGrps.objectAtIndex(i);
			ExamenEntete examEntete = (ExamenEntete) enteteGroupe.valueForKey("EXAM");
			Object objet = enteteGroupe.valueForKey("GRP");
			ScolGroupeGrp grp = null;
			if (!(objet instanceof String)) {
				grp = (ScolGroupeGrp) enteteGroupe.valueForKey("GRP");
			}
			testExamenDisponible(examEntete, grp, periodicites, reservation);
		}

		// verification disponibilite individus
		for (int i = 0; i < occupants.count(); i++) {
			IndividuUlr individu = (IndividuUlr) occupants.objectAtIndex(i);
			NSArray dispoIndividus = null;
			try {
				dispoIndividus = getNonDisponibiliteIndividu(periodicites, individu, reservation, null, null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			if (dispoIndividus != null && dispoIndividus.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispoIndividus.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispoIndividus.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException(individu.cCivilite() + " " + individu.nomPrenom() + " n'est pas libre entre " + msg);
			}
		}

		// verification disponibilite salles
		NSArray sumSalles = salles.arrayByAddingObjectsFromArray(choixSalles);
		for (int i = 0; i < sumSalles.count(); i++) {
			Salles salle = (Salles) sumSalles.objectAtIndex(i);
			NSArray dispoSalle = null;

			try {
				dispoSalle = getNonDisponibiliteSalle(periodicites, salle, reservation, null);
			}
			catch (Exception e) {
			}

			if (dispoSalle != null && dispoSalle.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispoSalle.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispoSalle.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException("La salle " + salle.salPorte() + " n'est pas libre entre " + msg);
			}
		}

		if (!genericFactory().removeResourcesToReservation(reservation, true)) {
			throw new EdtException("Impossible de modifier la reservation : contactez le Service Info SVP");
		}

		Reservation currentReservation = reservation;

		NSTimestamp now = new NSTimestamp();

		for (int i = 0; i < entetesGrps.count(); i++) {
			NSDictionary enteteGroupe = (NSDictionary) entetesGrps.objectAtIndex(i);
			ExamenEntete examEntete = (ExamenEntete) enteteGroupe.valueForKey("EXAM");
			ScolGroupeGrp grp = null;
			Object objet = enteteGroupe.valueForKey("GRP");

			// un bug fait sortir le groupe de l'editingContext : solution : le refetcher a partir de son ggrpKey !
			if (objet instanceof ScolGroupeGrp) {
				grp = (ScolGroupeGrp) enteteGroupe.valueForKey("GRP");
				grp = ScolGroupeGrp.fetchScolGroupeGrp(eContext, ScolGroupeGrp.GGRP_KEY_KEY, grp.ggrpKey());
			}

			ResaExamen currentResaExamen = ResaExamen.createResaExamen(eContext);

			currentReservation.addToResaExamensRelationship(currentResaExamen);
			currentResaExamen.setScolGroupeGrpRelationship(grp);
			DBHandler.invalidateObject(eContext, examEntete);
			currentResaExamen.setExamenEnteteRelationship(examEntete);
			// affecter l'horaire
			affecterHoraireEntete(examEntete, periodicites);
			if (examEntete.eentTraite().intValue() == 0) {
				examEntete.setEentTraite(new Integer(1));
			}
		}

		currentReservation.setResaCommentaire(commentaire);
		currentReservation.setTlocCode("E");
		currentReservation.setTypeLocationRelationship(TypeLocation.fetchRequiredTypeLocation(eContext, TypeLocation.TLOC_CODE_KEY, "E"));
		currentReservation.setIndividuAgentRelationship(agent);
		currentReservation.setDModification(DateCtrl.now());

		affecterOccupants(currentReservation, occupants);
		affecterPeriodicites(currentReservation, periodicites);
		affecterSalles(currentReservation, salles, agent, now, true);
		affecterChoixSalles(currentReservation, choixSalles, agent, true);

		affecterObjets(currentReservation, objets, agent, true);

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

	/**
	 * permet de creer une reservation de type examen avec les ressources passes, avec nottamment un NSArray entetesGrps qui contient autant
	 * de NSDictionaries que necessaire, chaque NSDictionary doit contenir un ExamenEntete cle="EXAM" et un ScolGroupeObjet ou la chaine
	 * "-1", si EXAM = la chaine -1, alors tous les groupes rattaches a l'EC de l'examen participeront a l'examen
	 */
	public Reservation creerExamen(Reservation resT, IndividuUlr agent, NSArray occupants, NSArray<NSTimestamp> periodicites, NSArray entetesGrps,
			NSArray salles, NSArray choixSalles, NSArray objets, String commentaire) throws EdtException {

		if (periodicites == null || periodicites.count() == 0) {
			throw new EdtException("Aucune date passée pour créer la réservation d'examen !");
		}
		if (periodicites.count() > 2) {
			throw new EdtException("Une réservation d'examen ne peut être créé sur plusieurs semaines !");
		}
		for (int i = 0; i < entetesGrps.count(); i++) {
			NSDictionary enteteGroupe = (NSDictionary) entetesGrps.objectAtIndex(i);
			ExamenEntete examEntete = (ExamenEntete) enteteGroupe.valueForKey("EXAM");
			ScolGroupeGrp grp = null;
			Object objet = enteteGroupe.valueForKey("GRP");

			if (!(objet instanceof String)) {
				grp = (ScolGroupeGrp) enteteGroupe.valueForKey("GRP");
			}
			testExamenDisponible(examEntete, grp, periodicites, resT);
		}

		// verification disponibilite salles
		NSArray sumSalles = salles.arrayByAddingObjectsFromArray(choixSalles);
		for (int i = 0; i < sumSalles.count(); i++) {
			Salles salle = (Salles) sumSalles.objectAtIndex(i);

			NSArray dispoSalle = null;
			try {
				dispoSalle = getNonDisponibiliteSalle(periodicites, salle, resT, null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			if (dispoSalle != null && dispoSalle.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispoSalle.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispoSalle.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException("La salle " + salle.salPorte() + " n'est pas libre entre " + msg);
			}
		}

		// verification disponibilite individus
		for (int i = 0; i < occupants.count(); i++) {
			IndividuUlr individu = (IndividuUlr) occupants.objectAtIndex(i);

			NSArray dispoIndividus = null;
			try {
				dispoIndividus = getNonDisponibiliteIndividu(periodicites, individu, resT, null, null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			if (dispoIndividus != null && dispoIndividus.count() > 0) {
				NSTimestamp d1 = (NSTimestamp) dispoIndividus.objectAtIndex(0);
				NSTimestamp d2 = (NSTimestamp) dispoIndividus.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, "%d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, "%d/%m/%Y %H:%M");
				throw new EdtException(individu.cCivilite() + " " + individu.nomPrenom() + " n'est pas libre entre " + msg);
			}
		}

		Reservation currentReservation = Reservation.createReservation(eContext, DateCtrl.now(), DateCtrl.now(), "E");

		for (int i = 0; i < entetesGrps.count(); i++) {
			NSDictionary enteteGroupe = (NSDictionary) entetesGrps.objectAtIndex(i);
			ExamenEntete examEntete = (ExamenEntete) enteteGroupe.valueForKey("EXAM");
			ScolGroupeGrp grp = null;
			Object objet = enteteGroupe.valueForKey("GRP");

			if (!(objet instanceof String)) {
				grp = (ScolGroupeGrp) enteteGroupe.valueForKey("GRP");
			}

			ResaExamen currentResaExamen = ResaExamen.createResaExamen(eContext);
			currentReservation.addToResaExamensRelationship(currentResaExamen);
			currentResaExamen.setScolGroupeGrpRelationship(grp);
			DBHandler.invalidateObject(eContext, examEntete);
			currentResaExamen.setExamenEnteteRelationship(examEntete);
			// si tout est ok, on met l'exam a traite.

			// affecter l'horaire
			affecterHoraireEntete(examEntete, periodicites);
			if (examEntete.eentTraite().intValue() == 0) {
				examEntete.setEentTraite(new Integer(1));
			}
		}

		currentReservation.setResaCommentaire(commentaire);
		currentReservation.setIndividuAgentRelationship(agent);

		affecterOccupants(currentReservation, occupants);
		affecterPeriodicites(currentReservation, periodicites);
		affecterSalles(currentReservation, salles, agent, DateCtrl.now(), true);
		affecterChoixSalles(currentReservation, choixSalles, agent, true);
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

	private void testExamenDisponible(ExamenEntete examenEntete, ScolGroupeGrp grp, NSArray<NSTimestamp> periodicites, Reservation reservation)
			throws EdtException {
		if (examenEntete == null) {
			throw new EdtException("L'examen à vérifier est null, pas normal !");
		}
		if (grp != null) {
			// test si l'examen est déjà placé sans groupe...
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(ResaExamen.EXAMEN_ENTETE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, examenEntete));
			quals.addObject(new EOKeyValueQualifier(ResaExamen.SCOL_GROUPE_GRP_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
					NSKeyValueCoding.NullValue));
			if (reservation != null) {
				quals.addObject(new EOKeyValueQualifier(ResaExamen.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, reservation));
			}
			NSArray<ResaExamen> resaExamens = ResaExamen.fetchResaExamens(eContext, new EOAndQualifier(quals), null);
			if (resaExamens != null && resaExamens.count() > 0) {
				throw new EdtException("Cet examen est déjà placé (" + DateCtrl.dateToString(examenEntete.eentDateDebut(), "%d/%m/%Y %H:%M") + " - "
						+ DateCtrl.dateToString(examenEntete.eentDateFin(), "%d/%m/%Y %H:%M") + ") !");
			}
			// test si l'examen est déjà placé pour ce groupe...
			quals.removeAllObjects();
			quals.addObject(new EOKeyValueQualifier(ResaExamen.EXAMEN_ENTETE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, examenEntete));
			quals.addObject(new EOKeyValueQualifier(ResaExamen.SCOL_GROUPE_GRP_KEY, EOKeyValueQualifier.QualifierOperatorEqual, grp));
			if (reservation != null) {
				quals.addObject(new EOKeyValueQualifier(ResaExamen.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, reservation));
			}
			resaExamens = ResaExamen.fetchResaExamens(eContext, new EOAndQualifier(quals), null);
			if (resaExamens != null && resaExamens.count() > 0) {
				throw new EdtException("Cet examen est déjà placé pour ce groupe ("
						+ DateCtrl.dateToString(examenEntete.eentDateDebut(), "%d/%m/%Y %H:%M") + " - "
						+ DateCtrl.dateToString(examenEntete.eentDateFin(), "%d/%m/%Y %H:%M") + ") !");
			}

			// test si le groupe est occupé
			NSArray<NSTimestamp> dispoGroupes = getNonDisponibiliteGroupe(null, grp, periodicites, reservation, null, null, null, null);
			if (dispoGroupes != null && dispoGroupes.count() > 0) {
				NSTimestamp d1 = dispoGroupes.objectAtIndex(0);
				NSTimestamp d2 = dispoGroupes.objectAtIndex(1);
				String msg = FormatHandler.dateToStr(d1, FORMAT) + " et " + FormatHandler.dateToStr(d2, FORMAT);
				throw new EdtException("Le groupe " + grp + " n'est pas libre entre " + msg);
			}

			// teste si cet examen est déjà placé pour un autre groupe,
			quals.removeAllObjects();
			quals.addObject(new EOKeyValueQualifier(ResaExamen.EXAMEN_ENTETE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, examenEntete));
			quals.addObject(new EOKeyValueQualifier(ResaExamen.SCOL_GROUPE_GRP_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, grp));
			if (reservation != null) {
				quals.addObject(new EOKeyValueQualifier(ResaExamen.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, reservation));
			}
			resaExamens = ResaExamen.fetchResaExamens(eContext, new EOAndQualifier(quals), null);
			if (resaExamens != null && resaExamens.count() > 0) {
				Periodicite periodiciteExistante = (Periodicite) resaExamens.objectAtIndex(0).reservation().periodicites().objectAtIndex(0);
				NSTimestamp d1 = periodicites.objectAtIndex(0);
				NSTimestamp d2 = periodicites.objectAtIndex(1);
				if (d1.compare(periodiciteExistante.dateDeb()) != NSComparator.OrderedSame
						|| d2.compare(periodiciteExistante.dateFin()) != NSComparator.OrderedSame) {
					// throw new EdtException("L'examen a déjà une date de fixée pour un autre groupe ("
					// + DateCtrl.dateToString(periodiciteExistante.dateDeb(), "%d/%m/%Y %H:%M") + " - "
					// + DateCtrl.dateToString(periodiciteExistante.dateFin(), "%d/%m/%Y %H:%M") +
					// "), vous ne pouvez pas changer la date !");
					if (WindowHandler.showConfirmation("L'examen a déjà une date de fixée pour un autre groupe ("
							+ DateCtrl.dateToString(periodiciteExistante.dateDeb(), "%d/%m/%Y %H:%M") + " - "
							+ DateCtrl.dateToString(periodiciteExistante.dateFin(), "%d/%m/%Y %H:%M") + ")... Continuer quand même ?") == false) {
						throw new EdtException("Enregistrement annulé !");
					}
				}
			}

		}
		else {
			// test si l'examen est déjà placé...
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(ResaExamen.EXAMEN_ENTETE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, examenEntete));
			if (reservation != null) {
				quals.addObject(new EOKeyValueQualifier(ResaExamen.RESERVATION_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, reservation));
			}
			NSArray<ResaExamen> resaExamens = ResaExamen.fetchResaExamens(eContext, new EOAndQualifier(quals), null);
			if (resaExamens != null && resaExamens.count() > 0) {
				throw new EdtException("Cet examen est déjà placé (" + DateCtrl.dateToString(examenEntete.eentDateDebut(), "%d/%m/%Y %H:%M") + " - "
						+ DateCtrl.dateToString(examenEntete.eentDateFin(), "%d/%m/%Y %H:%M") + ") !");
			}

			// test si un ap de l'ec concerné par l'examen est déjà occupé
			NSArray<NSTimestamp> dispoAp = new EnseignementFactory(eContext).indisponibiliteExamen(examenEntete, periodicites, reservation);
			if (dispoAp != null && dispoAp.count() > 0) {
				NSTimestamp d1 = dispoAp.objectAtIndex(0);
				NSTimestamp d2 = dispoAp.objectAtIndex(1);

				String msg = FormatHandler.dateToStr(d1, FORMAT) + " et " + FormatHandler.dateToStr(d2, FORMAT);
				throw new EdtException("L'AP " + examenEntete.eentLibelle() + " n'est pas libre entre " + msg);
			}
		}
	}
}
