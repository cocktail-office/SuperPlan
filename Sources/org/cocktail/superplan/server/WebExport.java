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
package org.cocktail.superplan.server;

import org.cocktail.fwkcktlwebapp.common.util.GEDClient;
import org.cocktail.superplan.server.metier.EdtGedexport;

import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;

public class WebExport {

	private Integer noIndividu;
	private GEDClient gedClient;
	private Session session;
	private String appliId;

	public WebExport(Session session) {
		this.session = session;

		String host = session.config().stringForKey("GEDFS_SERVICE_HOST");
		Integer port = new Integer(session.config().intForKey("GEDFS_SERVICE_PORT"));
		this.noIndividu = (Integer) session.noIndividu();
		// appliId = session.config().stringForKey("APP_ID");
		appliId = "EDT";

		gedClient = new GEDClient(host, port.intValue(), noIndividu.intValue(), appliId);
	}

	public boolean exportWebLastDisplay(String titre, Number fannKey, Number msemKey, Number ggrpKey, Number weekNumber, NSTimestamp debut,
			NSTimestamp fin) {

		StringBuffer chCal = new StringBuffer();
		chCal.append("Semaine ");
		chCal.append(String.valueOf(weekNumber));
		chCal.append(" du ");
		chCal.append(FormatHandler.dateToStr(debut, "%d/%m/%Y"));
		chCal.append(" au ");
		chCal.append(FormatHandler.dateToStr(fin, "%d/%m/%Y"));

		// on genere un pdf colore
		NSData pdfData = session.clientSideRequestPrintLastDisplay(chCal.toString(), titre, debut, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

		String motClef = null;

		StringBuffer docName = new StringBuffer();
		docName.append("PDF_EDT_");
		docName.append(String.valueOf(msemKey));
		docName.append("_");
		docName.append(String.valueOf(weekNumber));
		docName.append("_");
		docName.append(String.valueOf(fannKey));

		if (ggrpKey != null) {
			docName.append("_");
			docName.append(String.valueOf(ggrpKey.intValue()));
		}

		motClef = docName.toString();
		docName.append(".pdf");

		int documentID = -1;
		if (pdfData != null) {

			EOQualifier qualDoc = EOQualifier.qualifierWithQualifierFormat(EdtGedexport.MOT_CLEF_KEY + " = '" + motClef + "'", null);
			NSArray exports = DBHandler.fetchData(session.defaultEditingContext(), EdtGedexport.ENTITY_NAME, qualDoc);

			// modification existant
			if (exports.count() > 0) {
				EdtGedexport edtGedExport = (EdtGedexport) exports.objectAtIndex(0);
				documentID = edtGedExport.docId().intValue();
				if (gedClient.remplacerDocument(pdfData.bytes(), titre, docName.toString(), documentID)) {
					edtGedExport.setNoIndividu(noIndividu);
					edtGedExport.setDModification(new NSTimestamp());

					if (!saveChanges()) {
						return false;
					}
				}
				else {
					return false;
				}
			}
			// creation d'un nouvel enregistrement
			else {
				try {
					documentID = gedClient.enregistrerDocument(pdfData.bytes(), titre, docName.toString(), appliId);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				// creation de l'enregistrement EDT_GEDEXPORT
				if (documentID != -1) {
					EdtGedexport edtGedExport = (EdtGedexport) DBHandler.getInstance(session.defaultEditingContext(), EdtGedexport.ENTITY_NAME);
					edtGedExport.setDocId(new Integer(documentID));
					edtGedExport.setMotClef(motClef);
					edtGedExport.setNoIndividu(noIndividu);
					NSTimestamp now = new NSTimestamp();
					edtGedExport.setDCreation(now);
					edtGedExport.setDModification(now);

					if (!saveChanges()) {
						return false;
					}
				}
				else {
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	public boolean saveChanges() {
		boolean retour = true;
		try {
			session.defaultEditingContext().lock();
			session.defaultEditingContext().saveChanges();
		}
		catch (Exception exe) {
			exe.printStackTrace();
			session.defaultEditingContext().revert();
			retour = false;
		}
		finally {
			session.defaultEditingContext().unlock();
		}
		return retour;
	}

}
