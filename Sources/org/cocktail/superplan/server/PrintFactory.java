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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.cocktail.fwkcktlwebapp.common.print.CktlPrinter;
import org.cocktail.fwkcktlwebapp.common.util.CktlXMLWriter;
import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.fwkcktlwebapp.common.util.StreamCtrl;
import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.fwkcktlwebapp.server.CktlConfig;
import org.cocktail.superplan.server.gestionimpression.CoursImpress;
import org.cocktail.superplan.server.gestionimpression.JourImpress;
import org.cocktail.superplan.server.gestionimpression.ListeCours;
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

public class PrintFactory {

	public static final int COURS = 1;
	public static final int AGENDA = 2;
	public static final int OBJET = 3;

	Session session;

	private NSTimestamp debutSemaine;
	private NSArray resas;
	private int debImpress = -1, finImpress = -1;
	private boolean withColors = true;

	private NSMutableArray lun, mar, mer, jeu, ven, sam, dim;

	// elements utiles au serveur de PDFs.
	private String maquetteID;
	private CktlPrinter printer;
	private byte[] xmlbytes;

	CktlConfig cfg;

	public PrintFactory(CktlConfig cfg, Session session) {
		this.cfg = cfg;
		this.session = session;
		this.init();
	}

	public void setReservations(NSArray reservs) {
		ServerSideReservationClassifier resaClassifier = new ServerSideReservationClassifier();
		resas = resaClassifier.classifyReservations(reservs);
	}

	private void init() {
		Number debIm = (Number) session.prefUser("heureDebImpress");
		Number finIm = (Number) session.prefUser("heureFinImpress");

		if (debIm == null) {
			debImpress = 7;
		}
		else {
			debImpress = debIm.intValue();
		}

		if (finIm == null) {
			finImpress = 20;
		}
		else {
			finImpress = finIm.intValue();
		}
		maquetteID = cfg.stringForKey("MAQUETTE_PLANNING");
	}

	/**
	 * impression immédiate du pdf param xmlstream le fichier d'entree param la taille du fichier d'entree param maquetteID la maquette a
	 * faire appel dans Six return InputStream le fichier de sortie
	 */
	private InputStream genererPDF(InputStream xmlStream, int streamSize, String maquetteID) throws Exception {
		InputStream stream = null;
		try {
			printer = CktlPrinter.newDefaultInstance(cfg);
		}
		catch (ClassNotFoundException e) {
			// Le pilote n'a pas ete trouve
			e.printStackTrace();
			NSLog.out.appendln("Erreur instantiation CktlPrinter : " + e.getMessage());
			return stream;
		}

		stream = printer.printFileImmediate(maquetteID, xmlStream, streamSize);
		// int jobId = printer.getJobID();
		// On verifie si l'operation s'est bien passé
		if (printer.hasSuccess()) {
			return stream;
		}
		else {
			NSLog.out.appendln("Erreur Six :  " + printer.getMessage());
			return null;
		}
	}

	/**
	 * lance l'edition de l'EDT
	 */
	public NSData imprimerEdt(String semaine, String libelleFormation, NSTimestamp debut, boolean color, boolean impressionHoraires) throws Exception {
		if (resas == null) {
			throw new Exception("[PrintFactory:imprimerEdt] Il n'y a rien a imprimer (resas == null) !!");
		}
		this.debutSemaine = debut;
		this.withColors = color;

		lun = new NSMutableArray();
		mar = new NSMutableArray();
		mer = new NSMutableArray();
		jeu = new NSMutableArray();
		ven = new NSMutableArray();
		sam = new NSMutableArray();
		dim = new NSMutableArray();

		// recuperation des resas par jour :
		for (int id = 0; id < resas.count(); id++) {
			Object currentObjet = resas.objectAtIndex(id);
			Number jour;
			NSDictionary uneResa;
			if (currentObjet instanceof NSArray) {
				uneResa = (NSDictionary) ((NSArray) currentObjet).objectAtIndex(0);
			}
			else {
				uneResa = (NSDictionary) currentObjet;
			}

			jour = (Number) uneResa.valueForKey("jour");
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

		// fin recuperation des resas par jour
		NSTimestamp dateSuivante = new NSTimestamp(debutSemaine);
		JourImpress lundiImpress = getJourImpression(lun, "Lundi", dateSuivante);
		dateSuivante = dateSuivante.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		JourImpress mardiImpress = getJourImpression(mar, "Mardi", dateSuivante);
		dateSuivante = dateSuivante.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		JourImpress mercrediImpress = getJourImpression(mer, "Mercredi", dateSuivante);
		dateSuivante = dateSuivante.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		JourImpress jeudiImpress = getJourImpression(jeu, "Jeudi", dateSuivante);
		dateSuivante = dateSuivante.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
		JourImpress vendrediImpress = getJourImpression(ven, "Vendredi", dateSuivante);

		JourImpress samediImpress = null;
		if (sam.count() > 0) {
			dateSuivante = dateSuivante.timestampByAddingGregorianUnits(0, 0, 1, 0, 0, 0);
			samediImpress = getJourImpression(sam, "Samedi", dateSuivante);
		}

		// pour XML
		// String xmlFileName = System.getProperty("user.home")+"/ServerPlanning.xml";
		// CktlXMLWriter w = new CktlXMLWriter(xmlFileName);

		// pour PDF
		StringWriter aStringWriter = new StringWriter();
		CktlXMLWriter w = new CktlXMLWriter(aStringWriter);
		w.setCharsToEscape("<>&");

		w.startDocument();
		w.startElement("entete");
		w.writeElement("diplome", libelleFormation);
		w.writeElement("semaine", semaine);
		w.writeElement("dateImpression", DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(new Date()));

		w.startElement("affichageHeure");
		w.writeElement("heureDepart", String.valueOf(debImpress));
		w.writeElement("nbsHeuresAffichees", String.valueOf(finImpress - debImpress));
		w.endElement();

		w.startElement("impressionHoraires");
		w.writeElement("value", impressionHoraires ? "TRUE" : "FALSE");
		w.endElement();

		this.writeNodesForDay(lundiImpress, w);

		this.writeNodesForDay(mardiImpress, w);

		this.writeNodesForDay(mercrediImpress, w);

		this.writeNodesForDay(jeudiImpress, w);

		this.writeNodesForDay(vendrediImpress, w);

		if (samediImpress != null) {
			this.writeNodesForDay(samediImpress, w);
		}

		w.endElement();
		w.endDocument();
		w.close();

		xmlbytes = aStringWriter.toString().getBytes();
		InputStream pdfStream = genererPDF(new ByteArrayInputStream(xmlbytes), xmlbytes.length, maquetteID);
		if (pdfStream == null) {
			throw new Exception("Le flux PDF est nul.");
		}
		ByteArrayOutputStream tmpByteArrayOutputStream = new ByteArrayOutputStream();
		StreamCtrl.writeContentToStream(pdfStream, tmpByteArrayOutputStream, -1);

		return new NSData(tmpByteArrayOutputStream.toByteArray());
	}

	private void writeNodesForDay(JourImpress jourImpress, CktlXMLWriter w) throws Exception {

		w.startElement("jour");
		w.startElement("groupes");
		w.writeElement("nomDuJour", jourImpress.jourName());
		w.writeElement("dateDuJour", jourImpress.jourDate());
		w.writeElement("nbGroupe", String.valueOf(jourImpress.nbCreneaux()));
		w.endElement();

		w.startElement("listCoursJournee");
		w.writeElement("nbCreneau", String.valueOf(jourImpress.nbCreneaux()));

		int currentNoCr = -1;
		for (int i = 0; i < jourImpress.nbCreneaux(); i++) {
			NSArray listCours = jourImpress.listCours().listeCoursForCreneau(i);
			if (currentNoCr != -1) {
				w.endElement();
			}
			w.startElement("creneau");
			currentNoCr++;
			for (int j = 0; j < listCours.count(); j++) {
				CoursImpress unCours = (CoursImpress) listCours.objectAtIndex(j);

				w.startElement("cours");
				w.writeElement("width", String.valueOf(unCours.width()));
				w.writeElement("height", String.valueOf(unCours.height()));

				if (unCours.etat() == CoursImpress.REEL) {
					w.writeElement("color", unCours.color());
					w.startElement("texte");
					if (unCours.heuredeb() != null) {
						w.writeElement("heuredeb", DateCtrl.dateToString(unCours.heuredeb(), "%H:%M"));
					}
					if (unCours.heurefin() != null) {
						w.writeElement("heurefin", DateCtrl.dateToString(unCours.heurefin(), "%H:%M"));
					}
					if (unCours.contenu() != null) {
						w.writeElement("contenu", unCours.contenu());
					}
					if (unCours.occupants() != null) {
						w.writeElement("occupants", unCours.occupants());
					}
					if (unCours.salles() != null) {
						w.writeElement("salles", unCours.salles());
					}
					w.endElement();
				}
				w.endElement(); // <cours>
			}
		}
		w.endElement(); // <creneau>
		w.endElement(); // <listCoursJournee>
		w.endElement(); // <jour>
	}

	/** determiner la subdivision maximale */
	protected int getVerticalMax(NSArray res) {

		int verticalMax = 1;
		for (int i = 0; i < res.count(); i++) {
			Object act = res.objectAtIndex(i);
			if (act instanceof NSArray) {
				int localMax = ((NSArray) act).count();
				if (localMax > verticalMax) {
					verticalMax = localMax;
				}
			}
		}
		return verticalMax;
	}

	protected JourImpress getJourImpression(NSArray resJour, String jourName, NSTimestamp date) throws Exception {

		String contenu, occupants, salles;
		NSArray txt;
		String color;

		int vmax = getVerticalMax(resJour);

		JourImpress currentJour = new JourImpress(vmax);
		currentJour.setJourName(jourName);
		currentJour.setJourDate(FormatHandler.dateToStr(date, "%d/%m/%Y"));

		// on remplis la liste des cours du jour
		for (int i = 0; i < resJour.count(); i++) {
			boolean isComplex;
			Object act = resJour.objectAtIndex(i);
			NSArray myArray = null;
			NSDictionary myResa = null;

			if (act instanceof NSDictionary) { // cas simple
				myResa = (NSDictionary) act;
				isComplex = false;
			}
			else { // cas intersection
				myArray = (NSArray) act;
				isComplex = true;
			}

			if (!isComplex) {
				// Cas simple
				NSTimestamp debut = getDateWhithFormat(((NSTimestamp) myResa.objectForKey("debut")));
				NSTimestamp fin = getDateWhithFormat(((NSTimestamp) myResa.objectForKey("fin")));
				// On regarde si le cours est dans la zone d'impression
				if (fin.before(dateDebutImpress(currentJour.timestampJourDate())) || debut.after(dateFinImpress(currentJour.timestampJourDate()))) {
					// cours en dehors de la zone d'impression
					// on NE FAIT RIEN.....
				}
				else {
					// si a cheval sur les bord, on tronque le cours
					// Pour le bord gauche
					if (debut.before(dateDebutImpress(currentJour.timestampJourDate()))
							&& fin.after(dateDebutImpress(currentJour.timestampJourDate()))) {
						debut = dateDebutImpress(currentJour.timestampJourDate());
					}
					// Pour le bord droit
					if (debut.before(dateFinImpress(currentJour.timestampJourDate())) && fin.after(dateFinImpress(currentJour.timestampJourDate()))) {
						fin = dateFinImpress(currentJour.timestampJourDate());
					}

					// Creation du cours
					int width = TimeHandler.minutesSeparatingDates(debut, fin);
					int height = vmax;

					txt = (NSArray) myResa.objectForKey("texte");
					contenu = StringCtrl.replace((String) txt.objectAtIndex(0), "&", "&amp;");
					// if (myResa.objectForKey("libelle") != null) {
					// // pdm
					// contenu = contenu + " - ";
					// // pdm commente la ligne suivante
					// // contenu = contenu + "Salle : ";
					//
					// contenu = contenu + myResa.objectForKey("libelle");
					// }
					if (txt.count() > 1) {
						occupants = (String) txt.objectAtIndex(1);
					}
					else {
						occupants = null;
					}
					if (txt.count() > 2) {
						salles = StringCtrl.replace((String) txt.objectAtIndex(2), "&", "&amp;");
					}
					else {
						salles = null;
					}

					// avoir la couleur de la reservation : cas simple
					String ccolor = (String) myResa.objectForKey("ccolor");

					String colorCode = null;
					if (ccolor != null) {
						if (ccolor.startsWith("0x")) {
							colorCode = ccolor.substring(2);
						}
						else {
							colorCode = reservationColor(ccolor);
						}
					}

					EOGlobalID gidRes = (EOGlobalID) myResa.objectForKey("reservation");
					if (gidRes != null) {
						Reservation reserv = (Reservation) session.defaultEditingContext().faultForGlobalID(gidRes, session.defaultEditingContext());

						color = colorCode;
					}
					else
						if (ccolor != null) {
							color = colorCode;
						}
						else {
							color = reservationColor("z"); // inconnu
						}

					// System.out.println("PrintFactory simple color : " + color);
					//
					// System.out.println(">>>>< contenu = "+contenu);
					// int entier = 13/0;
					//
					CoursImpress cours = new CoursImpress(width, height, color, contenu, occupants, salles, 0, debut);
					currentJour.ajouterCours(cours);
				}
			}
			else {
				// Cas complex
				ListeCours globalListe = new ListeCours();
				NSTimestamp debutMin = null;
				NSTimestamp finMax = null;
				int heightTotal = 0;
				for (int j = 0; j < myArray.count(); j++) {
					NSTimestamp debut = getDateWhithFormat(((NSTimestamp) ((NSDictionary) myArray.objectAtIndex(j)).objectForKey("debut")));
					NSTimestamp fin = getDateWhithFormat(((NSTimestamp) ((NSDictionary) myArray.objectAtIndex(j)).objectForKey("fin")));

					// On regarde si le cours est dans la zone d'impression
					if (fin.before(dateDebutImpress(currentJour.timestampJourDate())) || debut.after(dateFinImpress(currentJour.timestampJourDate()))) {
						// cours en dehors de la zone d'impression
						// on NE FAIT RIEN.....
					}
					else {
						// si a cheval sur les bord, on tronque le cours
						// Pour le bord gauche
						if (debut.before(dateDebutImpress(currentJour.timestampJourDate()))
								&& fin.after(dateDebutImpress(currentJour.timestampJourDate()))) {
							debut = dateDebutImpress(currentJour.timestampJourDate());
						}
						// Pour le bord droit
						if (debut.before(dateFinImpress(currentJour.timestampJourDate()))
								&& fin.after(dateFinImpress(currentJour.timestampJourDate()))) {
							fin = dateFinImpress(currentJour.timestampJourDate());
						}

						// calcul du min et du max du groupe complex
						if (debutMin == null || debutMin.after(debut)) {
							debutMin = debut;
						}
						if (finMax == null || finMax.before(fin)) {
							finMax = fin;
						}

						// Creation du cours
						int width = TimeHandler.minutesSeparatingDates(debut, fin);
						int height = 0;

						if (myArray.count() < vmax && j == myArray.count() - 1) {
							height = vmax - heightTotal;
						}
						else {
							height = vmax / myArray.count();
						}

						NSDictionary currentResa = (NSDictionary) myArray.objectAtIndex(j);

						txt = (NSArray) currentResa.objectForKey("texte");
						contenu = StringCtrl.replace((String) txt.objectAtIndex(0), "&", "&amp;");
						// if (currentResa.objectForKey("libelle") != null) {
						// // pdm
						// contenu = contenu + " - ";
						// // pdm commente la ligne suivante
						// // contenu = contenu + "Salle : ";
						//
						// contenu = contenu + currentResa.objectForKey("libelle");
						// }

						if (txt.count() > 1) {
							occupants = StringCtrl.replace((String) txt.objectAtIndex(1), "&", "&amp;");
						}
						else {
							occupants = null;
						}
						if (txt.count() > 2) {
							salles = StringCtrl.replace((String) txt.objectAtIndex(2), "&", "&amp;");
						}
						else {
							salles = null;
						}

						// avoir la couleur de la reservation
						String typeRes = null;

						String ccolor = (String) currentResa.objectForKey("ccolor");

						String colorCode = null;

						if (ccolor != null) {
							if (ccolor.startsWith("0x")) {
								colorCode = ccolor.substring(2);
							}
							else {
								colorCode = reservationColor(ccolor);
							}
						}

						EOGlobalID gidRes = (EOGlobalID) currentResa.objectForKey("reservation");
						if (gidRes != null) {
							Reservation reserv = (Reservation) session.defaultEditingContext().faultForGlobalID(gidRes,
									session.defaultEditingContext());
							color = colorCode;
						}
						else
							if (typeRes != null) {
								color = reservationColor(typeRes);
							}
							else {
								color = reservationColor("z"); // inconnu
							}

						CoursImpress cours;

						// si le dernier cours de la liste, et nb impaire, alors height+1

						cours = new CoursImpress(width, height, color, contenu, occupants, salles, heightTotal, debut);
						heightTotal += height;
						ListeCours currentListe = new ListeCours();
						currentListe.addObject(cours);
						globalListe.addObjects(currentListe);
					}
				}
				globalListe.setDateDebutMin(debutMin);
				globalListe.setDateFinMax(finMax);
				currentJour.listCours().addObjects(globalListe);
			}
		}

		// Date de debut d'impression
		NSTimestamp deb = dateDebutImpress(currentJour.timestampJourDate());
		// Date de fin d'impression
		NSTimestamp fin = dateFinImpress(currentJour.timestampJourDate());

		// On ajoute les blancs entre les cours Simple-Simple et les Simple-Complex
		// le 0 = on commence au creneau zero
		currentJour.setListCours(this.paddingImpress(currentJour.listCours(), deb, fin, currentJour.nbCreneaux(), 0, vmax));

		return currentJour;
	}

	// permet d'etre sur d'avoir le bon TimeZone avec les NSTimestamp
	public NSTimestamp getDateWhithFormat(NSTimestamp date) {
		return date;
		// String daterep = FormatHandler.dateToStr(date, "%d/%m/%Y %H:%M:%S");
		// return FormatHandler.strToDate(daterep, "%d/%m/%Y %H:%M:%S");
	}

	// Renvoie la date de debut d'impression sous forme de NSTimestamp
	public NSTimestamp dateDebutImpress(NSTimestamp date) {
		GregorianCalendar calendar = new GregorianCalendar();
		// Indispensable......

		// calendar.setTimeZone(session.clientSideRequestDefaultTimeZone());
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, debImpress);
		calendar.set(Calendar.MINUTE, 0);
		calendar.setTime(calendar.getTime());
		return getDateWhithFormat(new NSTimestamp(calendar.getTime()));
	}

	// Renvoie la date de fin d'impression sous forme de NSTimestamp
	public NSTimestamp dateFinImpress(NSTimestamp date) {
		GregorianCalendar calendar = new GregorianCalendar();
		// Indispensable......
		// calendar.setTimeZone(session.clientSideRequestDefaultTimeZone());
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, finImpress);
		calendar.set(Calendar.MINUTE, 0);
		calendar.setTime(calendar.getTime());
		return getDateWhithFormat(new NSTimestamp(calendar.getTime()));
	}

	// Permet de creer un cours vide sur toute la journee
	public CoursImpress coursVide(int noCreneau) {
		CoursImpress cours = new CoursImpress((finImpress - debImpress) * 60, 1, noCreneau);
		cours.setEtat(CoursImpress.FICTIF);
		return cours;
	}

	public ListeCours paddingImpress(ListeCours listCours, NSTimestamp maxDebut, NSTimestamp minFin, int nbCreneaux, int noCreneau, int vmax) {
		// Si pas de cours et creneau 0, alors un cours vide sur toute la journee
		if (listCours.count() == 0) {
			int videWidth = TimeHandler.minutesSeparatingDates(maxDebut, minFin);
			CoursImpress cours = new CoursImpress(videWidth, 1, noCreneau, maxDebut);
			listCours.addObject(cours);
			return listCours;
		}
		else {
			NSTimestamp heurefin;
			NSTimestamp heureDeb;
			ListeCours tmp = new ListeCours();
			int width = 0;

			// calcul pour le bord gauche
			if (!listCours.isComplexAtIndex(0)) {
				heureDeb = ((CoursImpress) listCours.objectAtIndex(0)).heuredeb();
			}
			else {
				heureDeb = ((ListeCours) listCours.objectAtIndex(0)).dateDebutMin();
			}
			width = TimeHandler.minutesSeparatingDates(maxDebut, heureDeb);
			if (width > 0) {
				CoursImpress cours = new CoursImpress(width, nbCreneaux, noCreneau * nbCreneaux, maxDebut);
				tmp.addObject(cours);
			}
			tmp.addObject(listCours.objectAtIndex(0));

			// Pour les cours en general
			for (int i = 0; i < listCours.count() - 1; i++) {

				if (!listCours.isComplexAtIndex(i)) {
					heureDeb = ((CoursImpress) listCours.objectAtIndex(i)).heurefin();
				}
				else {
					heureDeb = ((ListeCours) listCours.objectAtIndex(i)).dateFinMax();
				}
				if (!listCours.isComplexAtIndex(i + 1)) {
					heurefin = ((CoursImpress) listCours.objectAtIndex(i + 1)).heuredeb();
				}
				else {
					heurefin = ((ListeCours) listCours.objectAtIndex(i + 1)).dateDebutMin();
				}

				width = TimeHandler.minutesSeparatingDates(heureDeb, heurefin);
				// Si largeur >0 on cree un cours vide
				if (width > 0) {
					CoursImpress cours = new CoursImpress(width, nbCreneaux, noCreneau * nbCreneaux, maxDebut);
					tmp.addObject(cours);
				}
				tmp.addObject(listCours.objectAtIndex(i + 1));
			}

			// Pour le bord droit
			if (!listCours.isComplexAtIndex(listCours.count() - 1)) {
				heureDeb = ((CoursImpress) listCours.lastObject()).heurefin();
			}
			else {
				heureDeb = ((ListeCours) listCours.lastObject()).dateFinMax();
			}
			width = TimeHandler.minutesSeparatingDates(heureDeb, minFin);
			if (width > 0) {
				CoursImpress cours = new CoursImpress(width, nbCreneaux, noCreneau * nbCreneaux, maxDebut);
				tmp.addObject(cours);
			}

			// Pour les complexs....
			for (int i = 0; i < tmp.count(); i++) {
				if (tmp.isComplexAtIndex(i)) {
					// on recupere la liste de liste de cours
					ListeCours currentListe = (ListeCours) tmp.objectAtIndex(i);

					// On recupere le min et le max du groupe complex
					heureDeb = ((ListeCours) tmp.objectAtIndex(i)).dateDebutMin();
					heurefin = ((ListeCours) tmp.objectAtIndex(i)).dateFinMax();

					// Pour chaque liste de la liste:
					for (int j = 0; j < vmax; j++) {
						if (j < currentListe.count()) {
							ListeCours tmpListe = (ListeCours) currentListe.objectAtIndex(j);
							// On remplace la liste de cours par la nouvelle avec les cours vides
							int heightcompex = (nbCreneaux % currentListe.count() == 0) ? (nbCreneaux / currentListe.count())
									: (nbCreneaux / currentListe.count()) + 1;
							currentListe.replaceObjectAtIndex(paddingImpressComplex(tmpListe, heureDeb, heurefin, heightcompex, j), j);
						}
					}
				}
			}
			return tmp;
		}// fin else
	}

	// Permet de cree les cours vides d'un complex
	private ListeCours paddingImpressComplex(ListeCours listCours, NSTimestamp maxDebut, NSTimestamp minFin, int nbCreneaux, int noCreneau) {
		if (listCours.count() == 0) {
			// Si la liste est vide, alors on cree un cours vide sur toute la largeur du groupe complex
			int videWidth = TimeHandler.minutesSeparatingDates(maxDebut, minFin);
			CoursImpress cours = new CoursImpress(videWidth, nbCreneaux, noCreneau * nbCreneaux, maxDebut);
			listCours.addObject(cours);
			return listCours;
		}
		else {
			// Il n' y a que 2 cas possible:
			// 1) Blanc avant le cours
			// 2) Blanc apres le cours
			// (Il y a qu'un seul cours par liste)
			NSTimestamp heurefin;
			NSTimestamp heureDeb;
			ListeCours tmp = new ListeCours();
			int width = 0;

			CoursImpress cours = (CoursImpress) listCours.objectAtIndex(0);
			// Pour le bord gauche
			heureDeb = cours.heuredeb();
			width = TimeHandler.minutesSeparatingDates(maxDebut, heureDeb);
			if (width > 0) {
				CoursImpress newcours = new CoursImpress(width, cours.height(), cours.numeroCreneau(), maxDebut);
				tmp.addObject(newcours);
			}
			// Le cours
			tmp.addObject(listCours.objectAtIndex(0));
			// pour le bord droit
			heurefin = cours.heurefin();
			width = TimeHandler.minutesSeparatingDates(heurefin, minFin);
			if (width > 0) {
				CoursImpress newcours = new CoursImpress(width, cours.height(), cours.numeroCreneau(), maxDebut);
				tmp.addObject(newcours);
			}
			return tmp;
		}
	}

	private String reservationColor(String code) {

		if (withColors) {
			return getColor(code);
		}
		else {
			return getGrayscale(code);
		}
	}

	private String getColor(String code) {

		if (code == null) {
			return "FFFFFF";

		}
		if (code.equals("CM")) {
			return "00FFFF";
		}

		if (code.equals("TD")) {
			return "90EE90";
			// return "006400"; // vers foncé
		}

		if (code.equals("TP")) {
			return "F0E68C";
			// return "FFD700"; //jaune
		}

		if (code.equals("SEM")) {
			return "B4FF8F";
			// return "FFD700"; //jaune
		}

		if (code.equals("E")) {
			return "FF0000"; // rouge
		}

		// Non enseignement Salles
		if (code.equals("f")) {
			return "8B0000"; // rouge foncé
		}

		if (code.equals("n")) {
			return "0B008B"; // magenta
		}

		if (code.equals("r")) {
			return "FF8C00"; // orange
		}

		if (code.equals("DT")) {
			return "A4BF9B";
		}

		if (code.equals("t")) {
			return "D2691E"; // orange foncé
		}

		// Resas AGENDA
		if (code.equals("z") || (code.equals("R")) || (code.equals("P")) || (code.equals("*"))) {
			return "FFEBCD";
		}

		return "FFFFFF";
	}

	/** reservationGrayscale */
	private String getGrayscale(String code) {

		if (code == null) {
			return "FFFFFF";
		}

		if (code.equals("CM")) {
			return "A9A9A9";
		}

		if (code.equals("TD")) {
			return "BCBCBC";
		}

		if (code.equals("TP")) {
			return "DCDCDC";
		}

		if (code.equals("E")) {
			return "A9A9A9";
		}
		else {
			return "A9A9A9";
		}

	}

}
