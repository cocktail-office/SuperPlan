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

import java.math.BigDecimal;

import org.cocktail.superplan.client.metier.DepositaireSalles;
import org.cocktail.superplan.client.metier.DetailPourcentage;
import org.cocktail.superplan.client.metier.ImplantationGeo;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.Local;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.TypeSalle;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

public class SalleFactory {

	private EOEditingContext eContext;

	public SalleFactory(EOEditingContext eContext) {
		this.eContext = eContext;
	}

	public static boolean testIndividuADroitReserverSalle(EOEditingContext ec, IndividuUlr individu, Salles salle,
			boolean isReservationSalleParDepositaire) {
		if (individu == null || salle == null) {
			return false;
		}
		if (new SalleFactory(ec).estDepositaireDeSalle(individu, salle)) {
			return true;
		}

		if (isReservationSalleParDepositaire == true) {
			// on vérifie que :
			// - soit la salle n'a aucun dépositaire défini ==> ok
			// - soit l'agent est dépositaire de la salle ==> ok
			NSArray<NSArray<IndividuUlr>> depositaires = (NSArray<NSArray<IndividuUlr>>) salle.valueForKeyPath(Salles.DEPOSITAIRES_KEY + "."
					+ DepositaireSalles.STRUCTURE_ULR_KEY + "." + StructureUlr.REPART_STRUCTURES_KEY + "." + RepartStructure.INDIVIDU_ULR_KEY);
			if (depositaires != null && depositaires.count() > 0 && depositaires.objectAtIndex(0) != null
					&& depositaires.objectAtIndex(0).count() > 0) {
				return false;
			}
		}

		if (salle.detailPourcentages() == null || salle.detailPourcentages().count() == 0) {
			return true;
		}
		NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>();
		andQuals.addObject(new EOKeyValueQualifier(DetailPourcentage.SALLES_KEY, EOKeyValueQualifier.QualifierOperatorEqual, salle));
		andQuals.addObject(new EOKeyValueQualifier(DetailPourcentage.DET_POURCENTAGE_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThan,
				new BigDecimal(0)));
		andQuals.addObject(new EOKeyValueQualifier(DetailPourcentage.REPART_STRUCTURES_KEY + "." + RepartStructure.INDIVIDU_ULR_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, individu));
		NSArray<DetailPourcentage> a = DetailPourcentage.fetchDetailPourcentages(ec, new EOAndQualifier(andQuals), null);
		return (a != null && a.count() > 0);
	}

	/** recherche les salles libres pour les creneaux passes et le qualifier de restriction */
	public NSArray<Salles> rechercheSallesLibres(NSArray<NSTimestamp> periodicites, EOQualifier salleQualifier) {
		NSMutableArray<EOQualifier> qualifs = new NSMutableArray<EOQualifier>();
		for (int i = 0; i < periodicites.count(); i += 2) {
			NSTimestamp debut = periodicites.objectAtIndex(i);
			NSTimestamp fin = periodicites.objectAtIndex(i + 1);
			String sQual = "vSallePeriodicite.dateFin>=%@ and vSallePeriodicite.dateDeb<=%@";
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(sQual, new NSArray<NSTimestamp>(new NSTimestamp[] { debut, fin }));
			qualifs.addObject(qualifier);
		}
		EOQualifier qualifPeriod = new EOOrQualifier(qualifs);
		NSArray<Salles> sallesOccupees = Salles.fetchSalleses(eContext, qualifPeriod, null);
		NSMutableArray<Salles> salles = new NSMutableArray<Salles>(Salles.fetchSalleses(eContext, salleQualifier, null));
		salles.removeObjectsInArray(sallesOccupees);
		return salles;
	}

	/** retourne les structures depositaires de l'individu */
	public NSArray<RepartStructure> structuresDepositaire(IndividuUlr individu) {
		EOQualifier qualDepos = EOQualifier.qualifierWithQualifierFormat(RepartStructure.INDIVIDU_ULR_KEY + " = %@ and "
				+ RepartStructure.STRUCTURE_ULR_KEY + "." + StructureUlr.LC_STRUCTURE_KEY + " = 'DEPOS'", new NSArray<IndividuUlr>(individu));
		return RepartStructure.fetchRepartStructures(eContext, qualDepos, null);
	}

	public NSArray<RepartStructure> structuresDepositaireObjets(IndividuUlr individu) {
		EOQualifier qualDepos = EOQualifier.qualifierWithQualifierFormat(RepartStructure.INDIVIDU_ULR_KEY + " = %@", new NSArray<IndividuUlr>(
				individu));
		return RepartStructure.fetchRepartStructures(eContext, qualDepos, null);
	}

	/** teste si la personne est presente dans au moins une structure depositaire */
	public boolean estDepositaire(IndividuUlr individu) {
		return (this.structuresDepositaire(individu).count() > 0);
	}

	/** verifie si l'individu est depositaire de la salle ... */
	public boolean estDepositaireDeSalle(IndividuUlr individu, Salles salle) {
		NSArray<RepartStructure> repartStructureDeposAgent = structuresDepositaire(individu);
		NSArray<StructureUlr> structureDeposAgent = (NSArray<StructureUlr>) repartStructureDeposAgent.valueForKey(RepartStructure.STRUCTURE_ULR_KEY);

		if (structureDeposAgent.count() == 0) {
			return false;
		}
		else {
			EOQualifier qSalle = EOQualifier.qualifierWithQualifierFormat(DepositaireSalles.SALLE_KEY + " = %@", new NSArray<Salles>(salle));
			NSArray<DepositaireSalles> depositairesSalle = DepositaireSalles.fetchDepositaireSalleses(eContext, qSalle, null);
			NSArray<StructureUlr> structureDeposDeLaSalle = (NSArray<StructureUlr>) depositairesSalle
					.valueForKey(DepositaireSalles.STRUCTURE_ULR_KEY);

			boolean estDepos = false;

			for (int i = 0; i < structureDeposDeLaSalle.count(); i++) {
				StructureUlr structSalle = structureDeposDeLaSalle.objectAtIndex(i);
				for (int j = 0; j < structureDeposAgent.count(); j++) {
					StructureUlr structDeposAgent = structureDeposAgent.objectAtIndex(j);
					if (structSalle.equals(structDeposAgent)) {
						return true;
					}

				}
			}
			return estDepos;
		}
	}

	public NSArray<TypeSalle> getTypesSalles() {
		return TypeSalle.fetchTypeSalles(
				eContext,
				null,
				new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(TypeSalle.TSAL_LIBELLE_KEY,
						EOSortOrdering.CompareCaseInsensitiveAscending)));
	}

	public NSArray<Local> getLocaux() {
		return Local
				.fetchLocals(
						eContext,
						null,
						new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(Local.APPELLATION_KEY,
								EOSortOrdering.CompareCaseInsensitiveAscending)));
	}

	public NSArray<ImplantationGeo> getLocalisationsGeo() {
		EOSortOrdering sortLG = EOSortOrdering.sortOrderingWithKey(ImplantationGeo.LL_IMPLANTATION_GEO_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);
		NSArray<ImplantationGeo> a = ImplantationGeo.fetchImplantationGeos(eContext, null, new NSArray<EOSortOrdering>(sortLG));
		return a;
	}

}
