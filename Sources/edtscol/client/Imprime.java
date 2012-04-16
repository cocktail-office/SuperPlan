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

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.cocktail.superplan.client.metier.IndividuUlr;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.utilities.FileHandler;

public class Imprime extends Thread {

	private Document document;
	public NSArray allResasOfWeek;
	public NSTimestamp dateDeb, dateFin;
	public IndividuUlr leUser;
	public IndividuUlr depos;
	public Object record;
	private PdfWriter writer;
	private PdfContentByte cb;
	private String chemin;
	private float lgs;
	public boolean editionDT, printColor;
	public String formation, calendrier;
	public int[][] tab = new int[7][96];
	public int[] jTab = new int[7];

	public Imprime(String formation, String calendrier, NSArray allResasOfWeek, NSTimestamp dateDeb, Object record, boolean printColor) {
		super();
		this.allResasOfWeek = allResasOfWeek;
		this.dateDeb = dateDeb;
		this.record = record;
		this.formation = formation;
		this.calendrier = calendrier;
		this.printColor = printColor;
		String targetDir = System.getProperty("java.io.tmpdir");
		if (targetDir.endsWith(System.getProperties().getProperty("file.separator")) != true) {
			targetDir = targetDir + System.getProperties().getProperty("file.separator");
		}
		chemin = targetDir + "MonAgenda.pdf";
		start();
	}

	public void run() {
		ouvrirDocument();
		imprimerEdt();
		fermerDocument();
		afficher();
	}

	private void ouvrirDocument() {
		try {
			document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
			writer = PdfWriter.getInstance(document, new FileOutputStream(chemin));

			initMetaDocument();
			document.open();
		}
		catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
		catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	private void imprimerEdt() {
		try {
			cb = writer.getDirectContent();
			cb.setColorFill(Color.black);
			cb.setColorStroke(Color.black);
			GregorianCalendar dateDebSemaine = new GregorianCalendar();
			dateDebSemaine.setTime(dateDeb);
			GregorianCalendar dateFinSemaine = (GregorianCalendar) dateDebSemaine.clone();
			dateFinSemaine.add(GregorianCalendar.DAY_OF_MONTH, 6);
			dateFin = new NSTimestamp(dateFinSemaine.getTime());
			ecrireTexteHor(formation, 100.0f, 560.0f, 750.0f, 50.0f, 0.0f, 0.0f, 14);
			ecrireTexteHor(calendrier, 100.0f, 545.0f, 750.0f, 50.0f, 0.0f, 0.0f, 14);
			for (int i = 0; i < allResasOfWeek.count(); i++) {
				NSArray tmp = (NSArray) allResasOfWeek.objectAtIndex(i);
				for (int j = 0; j < tmp.count(); j++) {
					NSDictionary resa = (NSDictionary) tmp.objectAtIndex(j);
					NSTimestamp rDeb = (NSTimestamp) resa.objectForKey("debut");
					NSTimestamp rFin = (NSTimestamp) resa.objectForKey("fin");
					GregorianCalendar deb = new GregorianCalendar();
					GregorianCalendar fin = new GregorianCalendar();
					deb.setTime(rDeb);
					fin.setTime(rFin);
					deb.setFirstDayOfWeek(Calendar.MONDAY);
					int day = deb.get(Calendar.DAY_OF_WEEK);
					int h1 = deb.get(Calendar.HOUR_OF_DAY);
					int m1 = deb.get(Calendar.MINUTE);
					int h2 = fin.get(Calendar.HOUR_OF_DAY);
					int m2 = fin.get(Calendar.MINUTE);
					// int i1 = h1 * 4 + m1 / 15;
					// int i2 = h2 * 4 + m2 / 15 - 1;
					caseHoraireEdt((float) day, (float) h1, (float) m1, (float) h2, (float) m2, (float) i, (float) allResasOfWeek.count(),
							(NSArray) resa.objectForKey("texte"), (String) resa.objectForKey("libelle"));
				}
			}
			legendeJourEdt(dateDeb, 20.0f, 530.0f, 80.0f, 72.0f);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ecrireTexteHor(String texte, float x, float y, float l, float h, float ph, float pv, int szf) {
		float x1, y1;
		try {
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			cb.setFontAndSize(bf, szf);
			x1 = x + ph;
			y1 = y - pv;
			if (bf.getWidthPoint(texte, szf) > l) {
				cesure(bf, szf, texte, x1, y1, l, h);
			}
			else {
				cb.beginText();
				cb.setTextMatrix(x1, y1);
				cb.showText(texte);
				cb.endText();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void caseHoraireEdt(float day, float h1, float m1, float h2, float m2, float i, float nb, NSArray motif, String libelle) {
		float x1, y1, ht1, l1;
		float qx = 672.0f / 48.0f;
		float hx = 72.0f / nb;
		if (h1 < 8.0f) {
			h1 = 8.0f;
			m1 = 0.0f;
		}
		if (h2 > 20.0f) {
			h2 = 20.0f;
			m2 = 0.0f;
		}
		x1 = 80.0f + 20.0f + qx * ((h1 - 8.0f) * 4.0f + (m1 / 15.0f));
		if (day == 1) {
			day = 8;
		}
		y1 = 530.0f - (72.0f * (day - 2.0f)) - ((i + 1.0f) * hx);
		float diff = ((h2 * 4.0f) + (m2 / 15.0f) - (h1 * 4.0f) - (m1 / 15.0f));
		l1 = qx * diff;
		ht1 = hx;
		try {
			cb.setLineWidth(1.0f);
			cb.rectangle(x1, y1, l1, ht1);
			if (printColor) {
				int iRect = (int) i;
				switch (iRect % 4) {
				case 0:
					cb.setColorFill(Color.yellow);
					break;
				case 1:
					cb.setColorFill(Color.orange);
					break;
				case 2:
					cb.setColorFill(Color.green);
					break;
				case 3:
					cb.setColorFill(Color.cyan);
					break;
				case 4:
					cb.setColorFill(Color.pink);
					break;
				default:
					break;
				}
				cb.fill();
				cb.setColorFill(Color.black);
			}
			cb.stroke();

			// cb.setColorFill(Color.black);
			cb.setLineWidth(0.2f);
			cb.rectangle(x1, y1, l1, ht1);
			cb.stroke();
			String motifText = (libelle != null ? libelle + "-" : "");
			cb.setLineWidth(1.0f);
			for (int j = 0; j < motif.count(); j++) {
				if (j > 0) {
					motifText = motifText + "-";
				}
				motifText = motifText + (String) (motif.objectAtIndex(j));
			}
			// ecrireTexteHor(motifText, x1, y1 + ht1 - 8.0f, l1, ht1, 0.0f, 0.f, 8);
			// ecrireTexteHor(motifText, x1, y1 + ht1 - 6.0f, l1, ht1, 2.0f, 0.f, 8);
			int fontSize = (ht1 > 8) ? 8 : (ht1 > 6) ? 6 : (ht1 > 5) ? 5 : 4;
			ecrireTexteHor(motifText, x1, y1 + ht1 - fontSize, l1, ht1, 2.0f, 0.f, fontSize);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cesure(BaseFont bf, int szf, String texte, float x, float y, float l, float h) {
		float x1 = x;
		float y1 = y;
		int lg = texte.length();
		int ic = 1;
		int pav = 0;
		while (ic < lg) {
			while (bf.getWidthPoint(texte.substring(pav, ic), szf) < l) {
				ic++;
				if (ic > lg) {
					break;
				}
			}
			cb.beginText();
			cb.setTextMatrix(x1, y1);
			cb.showText(texte.substring(pav, ic - 1));
			cb.endText();
			y1 = y1 - szf;
			pav = ic - 1;
			if (y1 < y - h + szf) {
				break;
			}
		}
	}

	private void legendeJourEdt(NSTimestamp deb, float x, float y, float l, float h) {
		float x1, y1, x2;
		NSTimestamp currentDate = new NSTimestamp();
		NSTimestamp currentDeb = new NSTimestamp();
		GregorianCalendar gDeb = new GregorianCalendar();
		gDeb.setTime(deb);

		for (int i = 0; i < 7; i++) {
			try {
				currentDate = new NSTimestamp(gDeb.getTime());
				currentDeb = FormatHandler.midnightTime(currentDate);
				x1 = x;
				y1 = y - (h * (i + 1));
				cb.rectangle(x1, y1, l, h);
				x2 = x1 + l;
				cb.rectangle(x2, y1, (float) 672.0, h);
				cb.stroke();
				String texte[] = FormatHandler.dateToStr(currentDeb, "%A %d/%m/%y").split(" ");
				ecrireTexteHor(texte[0], x1 + (float) 10.0, y1 + (float) 30.0, 80.0f, 80.0f, 5.0f, 5.0f, 14);
				ecrireTexteHor(texte[1], x1 + 10.0f, y1 + (float) 18.0, 80.0f, 80.0f, 5.0f, 5.0f, 14);
				gDeb.add(Calendar.DAY_OF_WEEK, 1);
			}
			catch (Exception e) {
			}
		}
		graduationEdt(x + l, y, 672.0f, h, 7);
	}

	private void graduationEdt(float x, float y, float l, float h, int nb) {
		float taille = l / (nb - 1);
		float x1, y1;
		y1 = y;
		x1 = x;
		for (int i = 0; i < nb; i++) {
			try {
				if (i > 0) {
					x1 = x1 + taille;
				}
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

				if (i == 0 || i == nb - 1) {
					cb.setLineWidth(1.0f);
				}
				else {
					cb.setLineWidth(0.1f);
				}

				cb.moveTo(x1, y1);
				cb.lineTo(x1, y1 - (h * 7.0f));
				cb.stroke();

				cb.setLineWidth(0.2f);
				if (i < nb - 1) {
					for (int j = 1; j < 4; j++) {
						float tq = (taille / 04.f) * j;
						cb.moveTo(x1 + tq, y1);
						cb.lineTo(x1 + tq, y1 - (h * 7.0f));
						cb.stroke();
					}
				}
				cb.setLineWidth(1.0f);
				cb.beginText();
				bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				cb.setFontAndSize(bf, 12);
				cb.setTextMatrix(x1 - 5.0f, y1 + 1.0f);
				int heure = i * 2 + 8;
				String texte = new Integer(heure).toString() + "h";
				cb.showText(texte);
				cb.endText();
			}
			catch (Exception e) {
			}
		}
		// ligne de fin de journée
		y1 = y1 - taille;
		x1 = x + l;
		cb.moveTo(x1, y1);
		cb.lineTo(x1 + lgs, y1);
		cb.stroke();
	}

	private void fermerDocument() {
		document.close();
	}

	private void initMetaDocument() {
		document.addTitle("Agenda");
		document.addSubject("Planning");
		document.addKeywords("Agenda");
		document.addAuthor("user");
		document.addCreator("SuperPlan");
		document.addProducer();
		document.addCreationDate();
		document.addHeader("Agenda - emploi du temps", "Planinng d'une semaine");
	}

	protected void afficher() {
		FileHandler fileHandler = new FileHandler();
		if (fileHandler != null) {
			try {
				fileHandler.openFile(chemin);
			}
			catch (Exception e) {
				System.out.println("Erreur a l'ouverture du pdf :" + e.getMessage());
			}
		}
	}

}
