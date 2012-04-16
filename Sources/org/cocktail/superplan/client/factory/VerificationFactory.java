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

import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.ResaExamen;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaObjetDepositaire;
import org.cocktail.superplan.client.metier.ResaObjetReserve;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.Secretariat;
import org.cocktail.superplan.client.metier.StructureUlr;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;

public class VerificationFactory {

	private static final String FORMAT = "%d/%m/%Y %H:%M";

	public static boolean testIndividuADroitReserverObjet(EOEditingContext ec, IndividuUlr individu, ResaObjet objet) {
		if (individu == null || objet == null) {
			return false;
		}
		if (testIndividuEstDepositaireObjet(ec, individu, objet)) {
			return true;
		}
		if (objet.resaObjetReserves() == null || objet.resaObjetReserves().count() == 0) {
			return true;
		}
		NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>();
		andQuals.addObject(new EOKeyValueQualifier(ResaObjetReserve.RESA_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, objet));

		NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>();
		orQuals.addObject(new EOKeyValueQualifier(ResaObjetReserve.REPART_STRUCTURES_KEY + "." + RepartStructure.INDIVIDU_ULR_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, individu));
		orQuals.addObject(new EOKeyValueQualifier(ResaObjetReserve.STRUCTURE_ULR_KEY + "." + StructureUlr.SECRETARIATS_KEY + "."
				+ Secretariat.INDIVIDU_ULR_KEY, EOKeyValueQualifier.QualifierOperatorEqual, individu));
		orQuals.addObject(new EOKeyValueQualifier(ResaObjetReserve.STRUCTURE_ULR_KEY + "." + StructureUlr.RESPONSABLE_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, individu));
		andQuals.addObject(new EOOrQualifier(orQuals));
		NSArray<ResaObjetReserve> a = ResaObjetReserve.fetchResaObjetReserves(ec, new EOAndQualifier(andQuals), null);

		return (a != null && a.count() > 0);
	}

	public static boolean testIndividuEstDepositaireObjet(EOEditingContext ec, IndividuUlr individu, ResaObjet objet) {
		if (individu == null || objet == null) {
			return false;
		}
		if (objet.resaObjetDepositaires() == null || objet.resaObjetDepositaires().count() == 0) {
			return false;
		}
		NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>();
		andQuals.addObject(new EOKeyValueQualifier(ResaObjetDepositaire.RESA_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, objet));
		andQuals.addObject(new EOKeyValueQualifier(ResaObjetDepositaire.REPART_STRUCTURES_KEY + "." + RepartStructure.INDIVIDU_ULR_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, individu));
		NSArray<ResaObjetDepositaire> a = ResaObjetDepositaire.fetchResaObjetDepositaires(ec, new EOAndQualifier(andQuals), null);
		return (a != null && a.count() > 0);
	}

	public static boolean testRessourcesPourNouvellesPeriodes(EOEditingContext ec, Reservation resa, NSArray periods) throws EdtException {
		NSArray occupants = (NSArray) resa.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		NSArray salles = (NSArray) resa.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		NSArray examens = resa.resaExamens();
		NSArray enseignements = resa.reservationAp();
		NSArray<NSTimestamp> dispo = null;

		EnseignementFactory ensFactory = new EnseignementFactory(ec);
		String msg;
		String msgDate;
		for (int i = 0; i < occupants.count(); i++) {
			dispo = ensFactory.getNonDisponibiliteIndividu(periods, (IndividuUlr) occupants.objectAtIndex(i), resa, null, null);
			if (dispo.count() > 0) {
				msgDate = FormatHandler.dateToStr(dispo.objectAtIndex(0), FORMAT) + " et " + FormatHandler.dateToStr(dispo.objectAtIndex(1), FORMAT);
				msg = ((IndividuUlr) occupants.objectAtIndex(i)).nomPrenom() + " n'est pas libre entre " + msgDate;
				throw new EdtException(msg);
			}
		}
		for (int i = 0; i < salles.count(); i++) {
			dispo = ensFactory.getNonDisponibiliteSalle(periods, (Salles) salles.objectAtIndex(i), resa, null);
			if (dispo.count() > 0) {
				msgDate = FormatHandler.dateToStr(dispo.objectAtIndex(0), FORMAT) + " et " + FormatHandler.dateToStr(dispo.objectAtIndex(1), FORMAT);
				msg = "La salle " + ((Salles) salles.objectAtIndex(i)).salPorte() + " n'est pas libre entre " + msgDate;
				throw new EdtException(msg);
			}
		}
		ReservationAp resAp = null;
		for (int i = 0; i < enseignements.count(); i++) {
			resAp = (ReservationAp) enseignements.objectAtIndex(i);
			if (resAp.scolGroupeGrp() == null) {
				NSArray<NSArray<?>> indispo = ensFactory.getNonDisponibiliteAp(resAp.maquetteAp(), periods, resa, true, null, null);
				dispo = (NSArray<NSTimestamp>) indispo.objectAtIndex(1);
				if (dispo.count() > 0) {
					NSArray<Integer> dispoAp = (NSArray<Integer>) indispo.objectAtIndex(0);
					msgDate = FormatHandler.dateToStr(dispo.objectAtIndex(0), FORMAT) + " et "
							+ FormatHandler.dateToStr(dispo.objectAtIndex(1), FORMAT);
					msg = "Tous les groupes de " + resAp.maquetteAp().mapLibelle() + " ne sont pas libres entre le " + msgDate;
					msg += " (créneau occupé par l'AP : "
							+ MaquetteAp.fetchMaquetteAp(ensFactory.editingContext(), MaquetteAp.MAP_KEY_KEY, dispoAp.objectAtIndex(0)) + ")";
					if (((MainClient) EOApplication.sharedApplication()).isAppControleApsOccupationBof()) {
						if (WindowHandler.showConfirmation(msg + "\nAbandonner ?")) {
							return false;
						}
					}
					else {
						throw new EdtException(msg);
					}
				}
			}
			else {
				dispo = ensFactory.getNonDisponibiliteGroupe(resAp.maquetteAp(), resAp.scolGroupeGrp(), periods, resa, null, null, null, null);

				if (dispo.count() > 0) {
					msgDate = FormatHandler.dateToStr(dispo.objectAtIndex(0), FORMAT) + " et "
							+ FormatHandler.dateToStr(dispo.objectAtIndex(1), FORMAT);
					msg = "Le groupe " + resAp.scolGroupeGrp().ggrpCode() + " n'est pas libre entre le " + msgDate;
					throw new EdtException(msg);
				}
			}
		}
		ResaExamen resEx = null;
		for (int i = 0; i < examens.count(); i++) {
			resEx = (ResaExamen) examens.objectAtIndex(i);
			if (resEx.scolGroupeGrp() == null) {
				dispo = ensFactory.indisponibiliteExamen(resEx.examenEntete(), periods, null);
			}
			else {
				dispo = ensFactory.getNonDisponibiliteGroupe(null, resEx.scolGroupeGrp(), periods, null, null, null, null, null);
			}

			if (dispo.count() > 0) {
				msgDate = FormatHandler.dateToStr(dispo.objectAtIndex(0), FORMAT) + " et " + FormatHandler.dateToStr(dispo.objectAtIndex(1), FORMAT);
				if (resEx.scolGroupeGrp() == null) {
					msg = "Tous les groupes de " + resEx.examenEntete().eentLibelle() + " ne sont pas libres entre " + msgDate;
				}
				else {
					msg = "Le groupe " + resEx.scolGroupeGrp().ggrpCode() + " n'est pas libre entre " + msgDate;
				}
				throw new EdtException(msg);
			}
		}
		return true;
	}

}
