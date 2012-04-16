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

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.StringTokenizer;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.cocktail.superplan.server.gestionimpression.ServerSideReservationClassifier;
import org.cocktail.superplan.server.metier.Reservation;

import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;

public class FileExportFactory {

	public static final int COURS = 1;
	public static final int AGENDA = 2;
	public static final int OBJET = 3;

	private static final Colour cCM = Colour.LIGHT_TURQUOISE;
	private static final Colour cTD = Colour.BRIGHT_GREEN;
	private static final Colour cTP = Colour.VERY_LIGHT_YELLOW;

	Session session;

	private NSArray resa;

	public FileExportFactory(Session session) {
		this.session = session;
	}

	public void setReservations(NSArray reservs, int type) {
		if (type == COURS) {
			ServerSideReservationClassifier resaClassifier = new ServerSideReservationClassifier();
			resa = resaClassifier.classifyReservations(reservs);
		}
		else {
			this.resa = reservs;
		}
	}

	/** ecrit les infos decrivant un creneau dans le WritableSheet passe en parametre */
	public void writeCreneau(NSDictionary creneau, WritableSheet sheet, int row) throws Exception {
		String formatDate = "%H:%M";
		Label lbl;

		Colour backgrd = Colour.VERY_LIGHT_YELLOW;

		EOGlobalID gidRes = (EOGlobalID) creneau.objectForKey("reservation");
		if (gidRes != null) {
			Reservation reserv = (Reservation) session.defaultEditingContext().faultForGlobalID(gidRes, session.defaultEditingContext());
			backgrd = getColour(reserv.tlocCode());
		}

		WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		WritableCellFormat cellFormat = new WritableCellFormat(font);
		cellFormat.setWrap(false);
		cellFormat.setBackground(backgrd);

		lbl = new Label(0, row + 1, FormatHandler.dateToStr((NSTimestamp) creneau.objectForKey("debut"), formatDate), cellFormat);
		// sheet.mergeCells(0,row+1,2,row+1);
		sheet.addCell(lbl);

		lbl = new Label(1, row + 1, FormatHandler.dateToStr((NSTimestamp) creneau.objectForKey("fin"), formatDate), cellFormat);
		// sheet.mergeCells(3,row+1,5,row+1);
		sheet.addCell(lbl);

		NSArray text = (NSArray) creneau.objectForKey("texte");

		lbl = new Label(2, row + 1, (String) text.objectAtIndex(0), cellFormat);
		sheet.mergeCells(2, row + 1, 4, row + 1);
		sheet.addCell(lbl);

		if (text.count() > 1) {
			lbl = new Label(5, row + 1, (String) text.objectAtIndex(1), cellFormat);
			sheet.mergeCells(5, row + 1, 7, row + 1);
			sheet.addCell(lbl);
		}

		if (text.count() > 2) {
			lbl = new Label(8, row + 1, (String) text.objectAtIndex(2), cellFormat);
			sheet.mergeCells(8, row + 1, 10, row + 1);
			sheet.addCell(lbl);
		}
	}

	/**  */
	public NSData getExcelFileForCreneaux(NSDictionary infos) throws Exception {

		String diplome = (String) infos.objectForKey("diplome");
		String specialite = (String) infos.objectForKey("specialite");
		String annee = (String) infos.objectForKey("annee");
		String ap = (String) infos.objectForKey("ap");

		String parcours = (String) infos.objectForKey("parcours");
		String semestre = (String) infos.objectForKey("semestre");

		ByteArrayOutputStream localByteArrayOS = new ByteArrayOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(localByteArrayOS);

		WritableSheet sheet = workbook.createSheet("Reservations/AP", 0);

		WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		WritableFont smallFont = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		WritableCellFormat headCellFormat = new WritableCellFormat(font);
		headCellFormat.setWrap(false);
		headCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		Colour backgrd;

		if (ap.startsWith("CM")) {
			backgrd = cCM;
		}
		else
			if (ap.startsWith("TD")) {
				backgrd = cTD;
			}
			else
				if (ap.startsWith("TP")) {
					backgrd = cTP;
				}
				else {
					backgrd = Colour.GRAY_25;
				}

		headCellFormat.setBackground(backgrd);
		Label lbl;

		int idxRow = 0;

		String headDiplome;
		if (specialite != null) {
			headDiplome = diplome + " - " + specialite;
		}
		else {
			headDiplome = diplome;
		}

		lbl = new Label(0, idxRow, headDiplome + " - Ann\u00e9e Univ. " + annee, headCellFormat);
		sheet.mergeCells(0, idxRow, 8, idxRow);
		sheet.addCell(lbl);

		idxRow = 1;

		lbl = new Label(0, idxRow, parcours, headCellFormat);
		sheet.mergeCells(0, idxRow, 8, idxRow);
		sheet.addCell(lbl);

		idxRow = 2;

		lbl = new Label(0, idxRow, semestre, headCellFormat);
		sheet.mergeCells(0, idxRow, 2, idxRow);
		sheet.addCell(lbl);

		lbl = new Label(3, idxRow, ap, headCellFormat);
		sheet.mergeCells(3, idxRow, 8, idxRow);
		sheet.addCell(lbl);

		// idxRow = 3;

		headCellFormat = new WritableCellFormat(smallFont);

		idxRow = idxRow + 2;

		lbl = new Label(0, idxRow, "Pr\u00e9vu : ", headCellFormat);
		sheet.addCell(lbl);
		lbl = new Label(1, idxRow, (String) infos.objectForKey("seuil"), headCellFormat);
		sheet.addCell(lbl);
		// fin
		lbl = new Label(3, idxRow, "R\u00e9serv\u00e9 : ", headCellFormat);
		sheet.addCell(lbl);
		// intervenant
		lbl = new Label(4, idxRow, (String) infos.objectForKey("reserve"), headCellFormat);
		sheet.addCell(lbl);

		idxRow = idxRow + 2;

		NSMutableArray localArray = new NSMutableArray((NSArray) infos.objectForKey("data"));

		WritableCellFormat cellFormat = new WritableCellFormat(smallFont);
		cellFormat.setWrap(false);
		cellFormat.setBackground(Colour.WHITE);

		while (localArray.count() > 0) {
			String infosCreneau = (String) localArray.removeLastObject();

			idxRow = idxRow + 1;
			StringTokenizer stoken = new StringTokenizer(infosCreneau, "$");

			while (stoken.hasMoreTokens()) {
				// semaine
				String info = stoken.nextToken();

				lbl = new Label(0, idxRow, info, cellFormat);
				// sheet.mergeCells(0,idxRow,iEnd+2,idxRow);
				sheet.addCell(lbl);
				// debut
				info = stoken.nextToken();
				lbl = new Label(1, idxRow, info, cellFormat);
				sheet.mergeCells(1, idxRow, 2, idxRow);
				sheet.addCell(lbl);
				// fin
				info = stoken.nextToken();
				lbl = new Label(3, idxRow, info, cellFormat);
				sheet.mergeCells(3, idxRow, 4, idxRow);
				sheet.addCell(lbl);
				// intervenant
				try {
					info = stoken.nextToken();
					lbl = new Label(5, idxRow, info, cellFormat);
					sheet.mergeCells(5, idxRow, 8, idxRow);
					sheet.addCell(lbl);
				}
				catch (Exception e) {
					NSLog.out.appendln("getExcelFileCreneaux:" + e.getMessage());
				}
			}

		}

		workbook.write();
		workbook.close();

		return new NSData(localByteArrayOS.toByteArray());
	}

	public NSData getExcelFileForReservationParPersonne(NSDictionary infos) throws Exception {

		String annee = (String) infos.objectForKey("annee");
		String individu = (String) infos.objectForKey("individu");

		ByteArrayOutputStream localByteArrayOS = new ByteArrayOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(localByteArrayOS);

		WritableSheet sheet = workbook.createSheet("Reservations/Personne", 0);

		WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		WritableFont smallFont = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		WritableCellFormat headCellFormat = new WritableCellFormat(font);
		headCellFormat.setWrap(false);
		headCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		int idxRow = 0;

		sheet.mergeCells(0, idxRow, 8, idxRow);
		sheet.addCell(new Label(0, idxRow, "Année Univ. " + annee, headCellFormat));

		idxRow = 1;

		sheet.mergeCells(0, idxRow, 8, idxRow);
		sheet.addCell(new Label(0, idxRow, individu, headCellFormat));

		headCellFormat = new WritableCellFormat(smallFont);
		idxRow = idxRow + 2;

		sheet.addCell(new Label(0, idxRow, "Type : ", headCellFormat));
		sheet.addCell(new Label(1, idxRow, "AP / Examen / Motif : ", headCellFormat));
		sheet.addCell(new Label(2, idxRow, "Semaine : ", headCellFormat));
		sheet.addCell(new Label(3, idxRow, "Date : ", headCellFormat));
		sheet.addCell(new Label(4, idxRow, "Début : ", headCellFormat));
		sheet.addCell(new Label(5, idxRow, "Fin : ", headCellFormat));
		sheet.addCell(new Label(6, idxRow, "Durée : ", headCellFormat));

		idxRow++;

		WritableCellFormat cellFormat = new WritableCellFormat(smallFont);
		cellFormat.setWrap(false);
		cellFormat.setBackground(Colour.WHITE);

		Enumeration<NSArray<String>> e = ((NSArray<NSArray<String>>) infos.objectForKey("data")).objectEnumerator();
		while (e.hasMoreElements()) {
			NSArray<String> infosCreneau = e.nextElement();
			idxRow++;
			for (int i = 0; i < infosCreneau.count(); i++) {
				sheet.addCell(new Label(i, idxRow, infosCreneau.objectAtIndex(i), cellFormat));
			}
			// StringTokenizer stoken = new StringTokenizer(infosCreneau, "$");
			// while (stoken.hasMoreTokens()) {
			// // type
			// String info = stoken.nextToken();
			// lbl = new Label(0, idxRow, info, cellFormat);
			// sheet.addCell(lbl);
			// // AP
			// info = stoken.nextToken();
			// lbl = new Label(1, idxRow, info, cellFormat);
			// sheet.addCell(lbl);
			// // Semaine
			// info = stoken.nextToken();
			// lbl = new Label(2, idxRow, info, cellFormat);
			// sheet.addCell(lbl);
			// // date
			// info = stoken.nextToken();
			// lbl = new Label(3, idxRow, info, cellFormat);
			// sheet.addCell(lbl);
			// // heure debut
			// info = stoken.nextToken();
			// lbl = new Label(4, idxRow, info, cellFormat);
			// sheet.addCell(lbl);
			// // heure fin
			// info = stoken.nextToken();
			// lbl = new Label(5, idxRow, info, cellFormat);
			// sheet.addCell(lbl);
			// // durée
			// info = stoken.nextToken();
			// lbl = new Label(6, idxRow, info, cellFormat);
			// sheet.addCell(lbl);
			// }
		}
		workbook.write();
		workbook.close();

		return new NSData(localByteArrayOS.toByteArray());
	}

	/** prepare un fichier Excel de type Emploi du temps */
	public NSData getExcelFileForCours(NSTimestamp debutSemaine, String semaine, String libelleFormation) throws Exception {

		NSDictionary uneResa = null;
		Object currentObjet = null;

		ByteArrayOutputStream tmpByteArrayOutputStream = new ByteArrayOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(tmpByteArrayOutputStream);

		WritableSheet sheet = workbook.createSheet("Emploi du temps", 0);

		WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		cellFormat.setWrap(false);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat.setBackground(Colour.YELLOW);
		Label lbl;
		lbl = new Label(0, 0, libelleFormation, cellFormat);
		sheet.mergeCells(0, 0, 10, 0);
		sheet.addCell(lbl);
		lbl = new Label(0, 1, semaine, cellFormat);
		sheet.mergeCells(0, 1, 10, 1);
		sheet.addCell(lbl);

		NSMutableArray lun = new NSMutableArray();
		NSMutableArray mar = new NSMutableArray();
		NSMutableArray mer = new NSMutableArray();
		NSMutableArray jeu = new NSMutableArray();
		NSMutableArray ven = new NSMutableArray();
		NSMutableArray sam = new NSMutableArray();
		NSMutableArray dim = new NSMutableArray();

		// recuperation des resas par jour :
		for (int id = 0; id < resa.count(); id++) {
			currentObjet = resa.objectAtIndex(id);
			Number jour;
			if (currentObjet instanceof NSArray) {
				uneResa = (NSDictionary) ((NSArray) currentObjet).objectAtIndex(0);
			}
			else {
				uneResa = (NSDictionary) currentObjet;
			}

			jour = (Number) uneResa.valueForKey("jour");

			switch (jour.intValue()) {

			case Calendar.MONDAY:
				if (currentObjet instanceof NSArray) {
					for (int i = 0; i < ((NSArray) currentObjet).count(); i++) {
						lun.addObject(((NSArray) currentObjet).objectAtIndex(i));
					}
				}
				else {
					lun.addObject(currentObjet);
				}
				break;

			case Calendar.TUESDAY:
				if (currentObjet instanceof NSArray) {
					for (int i = 0; i < ((NSArray) currentObjet).count(); i++) {
						mar.addObject(((NSArray) currentObjet).objectAtIndex(i));
					}
				}
				else {
					mar.addObject(currentObjet);
				}
				break;

			case Calendar.WEDNESDAY:
				if (currentObjet instanceof NSArray) {
					for (int i = 0; i < ((NSArray) currentObjet).count(); i++) {
						mer.addObject(((NSArray) currentObjet).objectAtIndex(i));
					}
				}
				else {
					mer.addObject(currentObjet);
				}
				break;

			case Calendar.THURSDAY:
				if (currentObjet instanceof NSArray) {
					for (int i = 0; i < ((NSArray) currentObjet).count(); i++) {
						jeu.addObject(((NSArray) currentObjet).objectAtIndex(i));
					}
				}
				else {
					jeu.addObject(currentObjet);
				}
				break;

			case Calendar.FRIDAY:
				if (currentObjet instanceof NSArray) {
					for (int i = 0; i < ((NSArray) currentObjet).count(); i++) {
						ven.addObject(((NSArray) currentObjet).objectAtIndex(i));
					}
				}
				else {
					ven.addObject(currentObjet);
				}
				break;

			case Calendar.SATURDAY:
				if (currentObjet instanceof NSArray) {
					for (int i = 0; i < ((NSArray) currentObjet).count(); i++) {
						sam.addObject(((NSArray) currentObjet).objectAtIndex(i));
					}
				}
				else {
					sam.addObject(currentObjet);
				}
				break;

			case Calendar.SUNDAY:
				if (currentObjet instanceof NSArray) {
					for (int i = 0; i < ((NSArray) currentObjet).count(); i++) {
						dim.addObject(((NSArray) currentObjet).objectAtIndex(i));
					}
				}
				else {
					dim.addObject(currentObjet);
				}
				break;
			}

		}

		int totalRow = 2;

		NSTimestamp dateLundi = new NSTimestamp(debutSemaine);
		NSTimestamp dateMardi = dateLundi.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		NSTimestamp dateMercredi = dateMardi.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		NSTimestamp dateJeudi = dateMercredi.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		NSTimestamp dateVendredi = dateJeudi.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		NSTimestamp dateSamedi = dateVendredi.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		NSTimestamp dateDimanche = dateSamedi.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);

		totalRow = writeJourIntoExcel(dateLundi, lun, sheet, totalRow);
		totalRow = writeJourIntoExcel(dateMardi, mar, sheet, totalRow);
		totalRow = writeJourIntoExcel(dateMercredi, mer, sheet, totalRow);
		totalRow = writeJourIntoExcel(dateJeudi, jeu, sheet, totalRow);
		totalRow = writeJourIntoExcel(dateVendredi, ven, sheet, totalRow);
		totalRow = writeJourIntoExcel(dateSamedi, sam, sheet, totalRow);
		totalRow = writeJourIntoExcel(dateDimanche, dim, sheet, totalRow);

		lun = null;
		mar = null;
		mer = null;
		jeu = null;
		ven = null;
		sam = null;
		dim = null;

		workbook.write();
		workbook.close();

		return new NSData(tmpByteArrayOutputStream.toByteArray());
	}

	private int writeJourIntoExcel(NSTimestamp dateJour, NSArray resJour, WritableSheet sheet, int row) throws Exception {
		int idxRow = row;
		idxRow = idxRow + 1;

		WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		WritableCellFormat headCellFormat = new WritableCellFormat(font);
		headCellFormat.setWrap(false);

		// ecrire le jour de la semaine
		headCellFormat.setBackground(Colour.BLUE_GREY);
		String dayFullDate = FormatHandler.dateToStr(dateJour, "%A %d/%m/%Y");

		Label lbl = new Label(0, idxRow, dayFullDate, headCellFormat);
		sheet.mergeCells(0, idxRow, 2, idxRow);
		sheet.addCell(lbl);

		for (int i = 0; i < resJour.count(); i++) {
			idxRow++;
			this.writeCreneau((NSDictionary) resJour.objectAtIndex(i), sheet, idxRow);
		}
		return idxRow + 1;
	}

	/** renvoie une couleur jxl par code de reservation */
	private Colour getColour(String code) {

		if (code.equals("CM")) {
			return Colour.SKY_BLUE;
		}
		else
			if (code.equals("TD")) {
				return Colour.LIGHT_GREEN;
			}
			else
				if (code.equals("TP")) {
					return Colour.VERY_LIGHT_YELLOW;
				}
				else
					if (code.equals("r")) {
						return Colour.LIGHT_ORANGE;
					}
					else
						if (code.equals("e")) {
							return Colour.RED;
						}
						else
							if (code.equals("b")) {
								return Colour.WHITE;
							}
							else {
								return Colour.GRAY_25;
							}
	}

}
