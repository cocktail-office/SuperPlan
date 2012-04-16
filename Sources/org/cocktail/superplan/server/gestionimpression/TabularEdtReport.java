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
package org.cocktail.superplan.server.gestionimpression;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.cocktail.superplan.server.metier.Reservation;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;

public class TabularEdtReport {

	public static final String FORMAT_DATE_HOUR = "%d/%m/%Y %H:%M";
	public static final String FORMAT_DATE = "%d/%m/%Y";
	public static final String FORMAT_HOUR = "%H:%M";

	public static final Font BOLD_ITALIC = new Font(Font.TIMES_ROMAN, 10, Font.BOLD | Font.ITALIC);

	private EOEditingContext editingContext;

	public TabularEdtReport(EOEditingContext ec) {
		this.editingContext = ec;
	}

	/*
	 * chaque element du tableau creneau est un NSDictionary avec aux max les clefs suivantes : reservation(EOGlobalID), debut(NSTimestamp),
	 * fin(NSTimestamp), jour(int), salleValide(O,N),texte,type,ccolor,
	 */
	public NSData genererPdf(NSArray creneaux, NSTimestamp debutSemaine, String semaine, String libelleFormation) throws DocumentException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Document document = new Document();
		PdfWriter.getInstance(document, os);
		document.open();
		document.setMargins(0, 0, 0, 0);

		Font font = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.DARK_GRAY);

		PdfPTable headerTable = new PdfPTable(1);

		PdfPCell cell = new PdfPCell();
		Phrase ph = new Phrase();
		ph.add(new Chunk(libelleFormation, font));
		cell.setPhrase(ph);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		headerTable.addCell(cell);

		font = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.DARK_GRAY);
		cell = new PdfPCell();
		ph = new Phrase();
		ph.add(new Chunk(semaine, font));
		cell.setPhrase(ph);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		headerTable.addCell(cell);

		headerTable.setWidthPercentage(100);
		headerTable.setSpacingAfter(5.0f);

		document.add(headerTable);

		PdfPTable pdfTable = creerCreneauxTable(creneaux, debutSemaine);

		pdfTable.setWidthPercentage(100);

		document.add(pdfTable);
		document.close();
		return new NSData(os.toByteArray());
	}

	public PdfPTable creerCreneauxTable(NSArray creneaux, NSTimestamp debutSemaine) {

		PdfPTable pdfTable = new PdfPTable(4);
		Object currentObjet;
		NSDictionary creneau;

		Integer jour;

		NSMutableArray lun, mar, mer, jeu, ven, sam, dim;

		lun = new NSMutableArray();
		mar = new NSMutableArray();
		mer = new NSMutableArray();
		jeu = new NSMutableArray();
		ven = new NSMutableArray();
		sam = new NSMutableArray();
		dim = new NSMutableArray();

		for (int i = 0; i < creneaux.count(); i++) {

			currentObjet = creneaux.objectAtIndex(i);

			if (currentObjet instanceof NSArray) {
				creneau = (NSDictionary) ((NSArray) currentObjet).objectAtIndex(0);
			}
			else {
				creneau = (NSDictionary) currentObjet;
			}

			jour = (Integer) creneau.valueForKey("jour");

			switch (jour.intValue()) {

			case Calendar.MONDAY:
				lun.addObject(currentObjet);
				break;

			case Calendar.TUESDAY:
				mar.addObject(currentObjet);
				break;

			case Calendar.WEDNESDAY:
				mer.addObject(currentObjet);
				break;

			case Calendar.THURSDAY:
				jeu.addObject(currentObjet);
				break;

			case Calendar.FRIDAY:
				ven.addObject(currentObjet);
				break;

			case Calendar.SATURDAY:
				sam.addObject(currentObjet);
				break;

			case Calendar.SUNDAY:
				dim.addObject(currentObjet);
				break;
			}
		}

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(debutSemaine);

		NSTimestamp dSemaine = new NSTimestamp(cal.getTime());

		ecrireCreneauxJour("Lundi", lun, dSemaine, pdfTable);

		cal.add(Calendar.DAY_OF_YEAR, 1);
		dSemaine = new NSTimestamp(cal.getTime());
		ecrireCreneauxJour("Mardi", mar, dSemaine, pdfTable);

		cal.add(Calendar.DAY_OF_YEAR, 1);
		dSemaine = new NSTimestamp(cal.getTime());
		ecrireCreneauxJour("Mercredi", mer, dSemaine, pdfTable);

		cal.add(Calendar.DAY_OF_YEAR, 1);
		dSemaine = new NSTimestamp(cal.getTime());
		ecrireCreneauxJour("Jeudi", jeu, dSemaine, pdfTable);

		cal.add(Calendar.DAY_OF_YEAR, 1);
		dSemaine = new NSTimestamp(cal.getTime());
		ecrireCreneauxJour("Vendredi", ven, dSemaine, pdfTable);

		cal.add(Calendar.DAY_OF_YEAR, 1);
		dSemaine = new NSTimestamp(cal.getTime());
		ecrireCreneauxJour("Samedi", sam, dSemaine, pdfTable);

		cal.add(Calendar.DAY_OF_YEAR, 1);
		dSemaine = new NSTimestamp(cal.getTime());
		ecrireCreneauxJour("Dimanche", dim, dSemaine, pdfTable);

		return pdfTable;
	}

	public void ecrireCreneauxJour(String nomJour, NSArray creneaux, NSTimestamp date, PdfPTable pdfTable) {

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.YELLOW);
		Phrase ph = new Phrase();
		ph.add(new Chunk(nomJour, BOLD_ITALIC));
		cell.setPhrase(ph);
		pdfTable.addCell(cell);

		cell = new PdfPCell();
		ph = new Phrase();
		ph.add(new Chunk(FormatHandler.dateToStr(date, FORMAT_DATE), BOLD_ITALIC));
		cell.setPhrase(ph);
		cell.setColspan(3);
		pdfTable.addCell(cell);

		Object obj;

		for (int i = 0; i < creneaux.count(); i++) {
			obj = creneaux.objectAtIndex(i);
			if (obj instanceof NSArray) {
				for (int j = 0; j < ((NSArray) obj).count(); j++) {
					ecrireCreneau((NSDictionary) ((NSArray) obj).objectAtIndex(j), pdfTable);
				}
			}
			else {
				ecrireCreneau((NSDictionary) obj, pdfTable);
			}
		}
	}

	public void ecrireCreneau(NSDictionary creneau, PdfPTable pdfTable) {

		NSTimestamp debut = (NSTimestamp) creneau.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) creneau.valueForKey("fin");
		NSArray texte = (NSArray) creneau.valueForKey("texte");
		// String ccolor = (String)creneau.valueForKey("ccolor");
		EOGlobalID idResa = (EOGlobalID) creneau.valueForKey("reservation");

		String type;
		Reservation resa = null;
		if (idResa != null) {
			resa = (Reservation) editingContext.faultForGlobalID(idResa, editingContext);
			type = resa.typeLocation().tlocLibelle();
		}
		else {
			type = "autre";
		}

		pdfTable.addCell(FormatHandler.dateToStr(debut, FORMAT_HOUR) + " - " + FormatHandler.dateToStr(fin, FORMAT_HOUR));
		pdfTable.addCell(type);

		PdfPCell cell = new PdfPCell(new Phrase(texte.componentsJoinedByString(" - ")));
		cell.setColspan(2);
		pdfTable.addCell(cell);
	}

}
