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
package edtscol.client.composant;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.FileHandler;

public class ReportsProxy {

	private EOEditingContext ectx;
	private NSDictionary userInfos;

	public ReportsProxy(EOEditingContext ectx, NSDictionary userInfos) {
		this.ectx = ectx;
		this.userInfos = userInfos;
	}

	public NSData generateReport(String reportID, NSDictionary reportParams) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSData data = (NSData) objectStore.invokeRemoteMethodWithKeyPath(ectx, "session", "clientSideRequestGenerateReport", new Class[] {
				String.class, NSDictionary.class }, new Object[] { reportID, reportParams }, false);
		return data;
	}

	public void openPDFDocument(NSData pdfData, String fileName) {
		if (pdfData != null) {
			try {
				FileHandler fileHandler = new FileHandler();
				String filePath = fileHandler.dataToXXX(pdfData, fileName, "pdf");
				fileHandler.openFile(filePath);
			}
			catch (Exception e) {
				e.printStackTrace();
				WindowHandler.showError("Ereur a l'ouverture du fichier :" + e.getMessage());
			}
		}

	}

	public void printOccupationLotSalle(Integer lotKey, String dateDeb) {

		NSMutableDictionary params = new NSMutableDictionary();
		params.setObjectForKey(lotKey, "LOT_KEY");
		params.setObjectForKey(dateDeb, "D_DEB");
		NSData data = generateReport("JASPER_OCCUPATION_LOT", params);
		openPDFDocument(data, "OccupationLot_" + lotKey.intValue());
	}

}
