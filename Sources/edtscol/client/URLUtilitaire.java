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
package edtscol.client;

import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.EOMasterDetailAssociation;
import com.webobjects.eointerface.EOTableAssociation;
import com.webobjects.eointerface.EOTextAssociation;
import com.webobjects.foundation.NSArray;

public class URLUtilitaire {
	/**
	 * Informe les EOTableAssociation et les EOMasterDetailAssociation que le DisplayGroup peut avoir change : il arrive que les
	 * associations ne soient pas correctement mises a jour apres modification du displayGroup associe.
	 */
	public static void informObservingAssociations(EODisplayGroup aTable) {
		int nbAssociations;

		NSArray observingAssociations = aTable.observingAssociations();
		// S'il y a des associations
		if ((nbAssociations = observingAssociations.count()) > 0) {
			// Informer les EOTableAssociation et EOMasterDetailAssociation
			for (int i = 0; i < nbAssociations; i++) {
				//
				if (observingAssociations.objectAtIndex(i).getClass().getName().equals("com.webobjects.eointerface.EOTableAssociation")) {
					((EOTableAssociation) observingAssociations.objectAtIndex(i)).subjectChanged();
				}
				//
				if (observingAssociations.objectAtIndex(i).getClass().getName().equals("com.webobjects.eointerface.EOMasterDetailAssociation")) {
					EOMasterDetailAssociation anAssociation = (EOMasterDetailAssociation) observingAssociations.objectAtIndex(i);
					anAssociation.subjectChanged();
					// Informer les observers de l'objet detail
					EODisplayGroup detailGroup = (EODisplayGroup) anAssociation.object();
					informObservingAssociations(detailGroup);
				}
				if (observingAssociations.objectAtIndex(i).getClass().getName().equals("com.webobjects.eointerface.EOTextAssociation")) {
					((EOTextAssociation) observingAssociations.objectAtIndex(i)).subjectChanged();
				}
			}
		}
	}

}
