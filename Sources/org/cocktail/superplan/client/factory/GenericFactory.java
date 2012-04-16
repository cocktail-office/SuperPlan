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
import org.cocktail.superplan.client.metier.ScolGroupeGrp;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.foundation.NSArray;

import edtscol.client.MainClient;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;

public class GenericFactory {

	private EOEditingContext eContext;

	public GenericFactory(EOEditingContext eContext) {
		this.eContext = eContext;
	}

	/** cree une instance de l'enregistrement d'entite entity et avec l'editingContext eContext */
	public static EOGenericRecord getInstance(EOEditingContext eContext, String entity) {
		EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(entity);
		EOGenericRecord instance = (EOGenericRecord) descriptionClass.createInstanceWithEditingContext(eContext, null);
		eContext.insertObject(instance);
		return instance;
	}

	/**  */
	public boolean removePeriodicites(NSArray<Periodicite> periodicites, Reservation reservation, boolean sc) throws EdtException {

		if (reservation.periodicites().count() < periodicites.count()) {
			throw new EdtException("Le nombre de semaines à supprimer est supérieur à celui de la réservation");
		}

		if (reservation.periodicites().count() == periodicites.count()) {
			throw new EdtException("Plutôt que de supprimer toutes les périodicités de la réservation,\nmerci de supprimer la réservation elle-même");
		}

		for (int i = 0; i < periodicites.count(); i++) {
			Periodicite period = periodicites.objectAtIndex(i);
			reservation.removeFromPeriodicitesRelationship(period);
			eContext.deleteObject(period);
		}

		reservation.setDModification(DateCtrl.now());
		IndividuUlr agent = (IndividuUlr) ((MainClient) EOApplication.sharedApplication()).userInfo("individu");
		reservation.setIndividuAgentRelationship(agent);

		boolean retour = true;
		if (sc) {
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
		return retour;
	}

	public boolean removeResourcesToReservation(Reservation reservation, boolean saveChanges) {

		DBHandler.invalidateObject(eContext, reservation);

		reservation.deleteAllPeriodicitesRelationships();
		reservation.deleteAllOccupantsRelationships();
		reservation.deleteAllResaSallesRelationships();
		reservation.deleteAllReservationObjetsRelationships();
		reservation.deleteAllReservationApRelationships();
		// on retire les examens a la resa
		NSArray<ResaExamen> temporary = reservation.resaExamens();
		while (temporary.count() > 0) {
			ResaExamen resExam = temporary.lastObject();

			// on met l'exam a non traite.
			resExam.examenEntete().setEentTraite(new Integer(0));

			if (resExam.scolGroupeGrp() != null) {
				ScolGroupeGrp grp = resExam.scolGroupeGrp();

				eContext.forgetObject(grp);
				resExam.setScolGroupeGrpRelationship(null);
			}
			reservation.removeFromResaExamensRelationship(resExam);
			eContext.deleteObject(resExam);
		}
		reservation.deleteAllSallesSouhaiteesRelationships();

		boolean retour = true;
		if (saveChanges) {
			try {
				eContext.lock();
				eContext.saveChanges();
				DBHandler.invalidateObject(eContext, reservation);
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
		return retour;
	}

	/** supprime une reservation apres liberation correcte des ressources occupees */
	public boolean deleteReservation(Reservation reservation, boolean saveChanges) throws EdtException {

		DBHandler.invalidateObject(eContext, reservation);
		reservation.deleteAllPeriodicitesRelationships();
		reservation.deleteAllOccupantsRelationships();
		reservation.deleteAllResaSallesRelationships();
		reservation.deleteAllReservationObjetsRelationships();
		reservation.deleteAllReservationApRelationships();
		// retirer les examens
		NSArray<ResaExamen> temporary = reservation.resaExamens();
		for (int i = 0; i < temporary.count(); i++) {
			ResaExamen resExam = temporary.objectAtIndex(i);
			ExamenEntete entete = resExam.examenEntete();
			// if (entete.eentTraite().intValue() == 3) {
			// throw new EdtException("Les étiquettes pour l'examen " + entete.eentLibelle()
			// + " ont déjà été éditées.\nImpossible de supprimer la réservation.");
			// }

			// on met l'exam a non traite.
			if (resExam.examenEntete().eentTraite().intValue() == 1) {
				resExam.examenEntete().setEentTraite(new Integer(0));
			}

			if (resExam.scolGroupeGrp() != null) {
				ScolGroupeGrp grp = resExam.scolGroupeGrp();
				eContext.forgetObject(grp);
				resExam.setScolGroupeGrpRelationship(null);
			}
			eContext.deleteObject(resExam);
		}

		reservation.deleteAllSallesSouhaiteesRelationships();

		eContext.deleteObject(reservation);
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
		return retour;
	}

}
