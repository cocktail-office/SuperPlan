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

package org.cocktail.superplan.client.metier;

import com.webobjects.foundation.NSValidation;

public class Reservation extends _Reservation {

	// Attributs non visibles
	public static final String RESA_KEY_KEY = "resaKey";
	public static final String NO_INDIVIDU_CLIENT_KEY = "noIndividuClient";

	public static final String RESA_KEY_COLKEY = "RESA_KEY";
	public static final String NO_INDIVIDU_CLIENT_COLKEY = "NO_INDIVIDU_CLIENT";

	public static final String TLOC_SEMESTRE = "SEM";
	public static final String TLOC_CM = "CM";
	public static final String TLOC_TD = "TD";
	public static final String TLOC_TP = "TP";
	public static final String TLOC_REUNION = "r";
	public static final String TLOC_REUNION_SERVICE = "s";
	public static final String TLOC_REUNION_PRIVE = "p";
	public static final String TLOC_EXAMEN = "E";

	public Reservation() {
		super();
	}

	public boolean isTypeReservationScol() {
		return typeLocation() != null && typeLocation().tlocAppli().equals("SCOL");
	}

	public boolean hasHistorique() {
		return logReservations() != null && logReservations().count() > 0;
	}

	public boolean isEmpty() {
		return (resaSalles() == null || resaSalles().count() == 0) && (resaExamens() == null || resaExamens().count() == 0)
				&& (sallesSouhaitees() == null || sallesSouhaitees().count() == 0) && (reservationAp() == null || reservationAp().count() == 0)
				&& (reservationObjets() == null || reservationObjets().count() == 0)
				&& (reservationsSemestres() == null || reservationsSemestres().count() == 0) && (occupants() == null || occupants().count() == 0);
	}

	public boolean isTypeEnseignement() {
		return tlocCode() != null
				&& (tlocCode().equals(TLOC_SEMESTRE) || tlocCode().equals(TLOC_CM) || tlocCode().equals(TLOC_TD) || tlocCode().equals(TLOC_TP));
	}

	public boolean isTypeExamen() {
		return tlocCode() != null && tlocCode().equals(TLOC_EXAMEN);
	}

	public boolean isTypeReunion() {
		return tlocCode() != null
				&& (tlocCode().equals(TLOC_REUNION) || tlocCode().equals(TLOC_REUNION_SERVICE) || tlocCode().equals(TLOC_REUNION_PRIVE));
	}

	public void validateForInsert() throws NSValidation.ValidationException {
		this.validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForInsert();
	}

	public void validateForUpdate() throws NSValidation.ValidationException {
		this.validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForUpdate();
	}

	public void validateForDelete() throws NSValidation.ValidationException {
		super.validateForDelete();
	}

	/**
	 * Apparemment cette methode n'est pas appelée.
	 * 
	 * @see com.webobjects.eocontrol.EOValidation#validateForUpdate()
	 */
	public void validateForSave() throws NSValidation.ValidationException {
		validateObjectMetier();
		validateBeforeTransactionSave();
		super.validateForSave();

	}

	/**
	 * Peut etre appele à partir des factories.
	 * 
	 * @throws NSValidation.ValidationException
	 */
	public void validateObjectMetier() throws NSValidation.ValidationException {

	}

	/**
	 * A appeler par les validateforsave, forinsert, forupdate.
	 * 
	 */
	private final void validateBeforeTransactionSave() throws NSValidation.ValidationException {

	}

}
