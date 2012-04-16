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

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSValidation;

public class ContrainteJour extends _ContrainteJour {

	// Attributs non visibles
	public static final String CTJ_KEY_KEY = "ctjKey";
	public static final String CTS_KEY_KEY = "ctsKey";

	public static final String CTJ_KEY_COLKEY = "CTJ_KEY";
	public static final String CTS_KEY_COLKEY = "CTS_KEY";

	public ContrainteJour() {
		super();
	}

	private void log(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i] + " ");
		}
	}

	public void inverseSelection() {
		byte[] quarts = quarts();
		deleteAllContrainteHeuresRelationships();
		for (int i = 0; i < 96; i++) {
			quarts[i] = (quarts[i] == 0 ? (byte) 1 : (byte) 0);
		}
		int indexDebut = -1;
		for (int i = 0; i <= 96; i++) {
			if (i < 96 && quarts[i] == 1) {
				if (indexDebut == -1) {
					indexDebut = i;
				}
			}
			else {
				if (indexDebut != -1) {
					// on a une nouvelle tranche horaire
					ContrainteHeure ctrl = createContrainteHeuresRelationship();
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(ctjDate());
					gc.set(Calendar.HOUR_OF_DAY, indexDebut / 4);
					gc.set(Calendar.MINUTE, (indexDebut % 4) * 15);
					ctrl.setCthHeureDebut(new NSTimestamp(gc.getTime()));
					if (i == 96) {
						gc.set(Calendar.HOUR_OF_DAY, 23);
						gc.set(Calendar.MINUTE, 59);
						gc.set(Calendar.SECOND, 59);
						gc.set(Calendar.MILLISECOND, 999);
					}
					else {
						gc.set(Calendar.HOUR_OF_DAY, i / 4);
						gc.set(Calendar.MINUTE, (i % 4) * 15);
					}
					ctrl.setCthHeureFin(new NSTimestamp(gc.getTime()));

					indexDebut = -1;
				}
			}

		}
	}

	public byte[] quarts() {
		byte[] quarts = new byte[96];
		for (int i = 0; i < 96; i++) {
			quarts[i] = 0;
		}
		if (contrainteHeures() != null) {
			Enumeration<ContrainteHeure> enumHeures = contrainteHeures().objectEnumerator();
			while (enumHeures.hasMoreElements()) {
				byte[] quartsHeure = enumHeures.nextElement().quarts();
				for (int i = 0; i < 96; i++) {
					if (quartsHeure[i] == 1) {
						quarts[i] = 1;
					}
				}
			}
		}
		return quarts;
	}

	public boolean isFull() {
		byte[] quarts = quarts();
		for (int i = 0; i < 96; i++) {
			if (quarts[i] == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty() {
		return (contrainteHeures() == null || contrainteHeures().count() == 0);
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
