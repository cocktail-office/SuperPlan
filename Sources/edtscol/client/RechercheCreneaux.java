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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Mission;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

import edtscol.client.gestionreservation.ConfigurationReservation;
import edtscol.client.panier.Panier;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.Log;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.SwapView;
import fr.univlr.utilities.TimeHandler;
import fr.univlr.utilities.ULRDateTimeWindow;

public class RechercheCreneaux extends EOInterfaceController {

	public JButton bAfficherInspecteur, btRechCreneaux, bDebRecherche, bFinRecherche;
	public EOMatrix matTypeRes;
	public SwapView swapPanier;
	public JTextField tDateDebut, tDateFin, tHDebutCreneau, tHDuree, tHFinCreneau, tMDebutCreneau, tMDuree, tMFinCreneau;
	public JCheckBox cSamedi, cDimanche, cToutes;
	public EODisplayGroup eodCreneaux;

	private MainInterface mainInterface;

	private Panier panier;
	private EOEditingContext eContext;

	public RechercheCreneaux(EOEditingContext eContext, MainInterface mainInterface) {
		super(eContext);
		this.eContext = eContext;
		this.mainInterface = mainInterface;
	}

	@SuppressWarnings("rawtypes")
	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		this.init();
		this.initUI();
	}

	protected void init() {
		panier = new Panier(eContext, mainInterface);
		EOArchive.loadArchiveNamed("Panier", panier, "edtscol.client.panier", panier.disposableRegistry());
		panier.init();
		swapPanier.setContentView(panier.currentView());
		NSTimestamp deb = new NSTimestamp();
		tDateDebut.setText(FormatHandler.dateToStr(deb, "%d/%m/%Y"));
		GregorianCalendar gDeb = new GregorianCalendar();
		gDeb.setTime(deb);
		gDeb.add(GregorianCalendar.DAY_OF_MONTH, 6);
		deb = new NSTimestamp(gDeb.getTime());
		tDateFin.setText(FormatHandler.dateToStr(deb, "%d/%m/%Y"));
		tHDebutCreneau.setText("08");
		tMDebutCreneau.setText("00");
		tHFinCreneau.setText("19");
		tMFinCreneau.setText("00");
		tHDuree.setText("02");
		tMDuree.setText("00");
	}

	/** permet de commencer une reservation avec le creneau selectionne */
	public void afficherDansInspecteur() {
		NSKeyValueCoding creneau = (NSKeyValueCoding) eodCreneaux.selectedObject();
		if (creneau == null) {
			return;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		cal.setTime((NSTimestamp) creneau.valueForKey("date"));
		String semaine = String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
		NSTimestamp heureDeb = (NSTimestamp) creneau.valueForKey("heureDebut");
		NSTimestamp heureFin = (NSTimestamp) creneau.valueForKey("heureFin");
		Salles sal = (Salles) creneau.valueForKey("salle");
		Integer jour = new Integer(cal.get(Calendar.DAY_OF_WEEK));

		NSArray<NSKeyValueCoding> ressources = panier.getContenuPanier();
		NSMutableArray<NSKeyValueCoding> tmpRes = new NSMutableArray<NSKeyValueCoding>();
		for (int i = 0; i < ressources.count(); i++) {
			NSKeyValueCoding res = ressources.objectAtIndex(i);
			if (((String) res.valueForKey("resType")).equals("SALLE")) {
				if (((Salles) res.valueForKey("resRecord")) == sal) {
					tmpRes.addObject(res);
				}
			}
			else {
				tmpRes.addObject(res);
			}
		}

		NSDictionary<String, Object> dicoIns = new NSDictionary<String, Object>(new Object[] { tmpRes, semaine,
				FormatHandler.dateToStr(heureDeb, "%H:%M"), FormatHandler.dateToStr(heureFin, "%H:%M"), jour }, new String[] { "ressources",
				"semaine", "heureDeb", "heureFin", "jour" });

		NSNotificationCenter.defaultCenter().postNotification("creneauLibreTrouve", dicoIns);
	}

	// Gestion des calendriers de choix de dates
	public void choixDateDebRecherche(Object sender) {
		new ULRDateTimeWindow(this, sender, "setDateDebRecherche");
	}

	public void choixDateFinRecherche(Object sender) {
		new ULRDateTimeWindow(this, sender, "setDateFinRecherche");
	}

	public void setDateDebRecherche(String dateDeb) {
		tDateDebut.setText(dateDeb);
	}

	public void setDateFinRecherche(String dateFin) {
		tDateFin.setText(dateFin);
	}

	// /

	public void rechercherCreneaux() {
		String sHDuree = tHDuree.getText();
		String sMDuree = tMDuree.getText();

		if (sHDuree.equals("")) {
			WindowHandler.showError("Donner la durée souhaitée...");
			return;
		}

		if (sMDuree.equals("")) {
			sMDuree = "0";
			tMDuree.setText(sMDuree);
		}

		String sDebutRech = tDateDebut.getText();
		String sFinRech = tDateFin.getText();

		if (sDebutRech.equals("") || sFinRech.equals("")) {
			WindowHandler.showError("Donner la date de début et de fin de recherche.");
			return;
		}

		WindowHandler.setWaitCursor(this);

		int hDuree = FormatHandler.strToInt(sHDuree);
		int mDuree = FormatHandler.strToInt(sMDuree);
		if (hDuree < 0 || mDuree < 0) {
			WindowHandler.showError("La durée donnée est incorrecte.");
			return;
		}

		NSArray<Object> creneauxLibres = getCreneauxLibres(eContext, panier, getDebutCreneau(), getFinCreneau(), (hDuree * 60) + mDuree);

		WidgetHandler.flushDisplayGroup(eodCreneaux);
		EOSortOrdering sortCreneau = EOSortOrdering.sortOrderingWithKey("dateDeb", EOSortOrdering.CompareAscending);
		eodCreneaux.setSortOrderings(new NSArray<EOSortOrdering>(sortCreneau));
		int j = 0;
		for (int i = 0; i < creneauxLibres.count(); i++) {
			HashMap<String, Object> currentCreneau = (HashMap<String, Object>) creneauxLibres.objectAtIndex(i);
			Salles salle = (Salles) currentCreneau.get("salle");
			NSTimestamp leDebut = (NSTimestamp) currentCreneau.get("debut");
			NSTimestamp laFin = (NSTimestamp) currentCreneau.get("fin");
			if (laFin != null || leDebut != null) {
				String jourStr = FormatHandler.dayString(leDebut);
				if (!cSamedi.isSelected() && jourStr.equals("Samedi")) {
					continue;
				}
				if (!cDimanche.isSelected() && jourStr.equals("Dimanche")) {
					continue;
				}
				if (salle != null) {
					Object[] contents = { salle, jourStr, leDebut, leDebut, laFin };
					String[] keys = { "salle", "jour", "date", "heureDebut", "heureFin" };
					NSDictionary<String, Object> dico = new NSDictionary<String, Object>(contents, keys);
					eodCreneaux.insertObjectAtIndex(dico, j);
				}
				else {
					Object[] contents = { jourStr, leDebut, leDebut, laFin };
					String[] keys = { "jour", "date", "heureDebut", "heureFin" };
					NSDictionary<String, Object> dico = new NSDictionary<String, Object>(contents, keys);
					eodCreneaux.insertObjectAtIndex(dico, j);
				}
				j++;
			}
		}
		WindowHandler.setDefaultCursor(this);
	}

	public static void log(int[] intArray) {
		for (int i = 0; i < intArray.length; i++) {
			if (i % 1440 == 0) {
				System.out.println("");
			}
			if (i % 1440 < 480 || i % 1440 > 1200) {
				continue;
			}
			if (i % 60 == 0) {
				System.out.print(" ");
			}
			System.out.print(intArray[i]);
		}
		System.out.println("");
	}

	public static void logSimple(int[] intArray) {
		for (int i = 0; i < intArray.length; i++) {
			if (i % 1440 == 0) {
				System.out.println("");
			}
			System.out.print(intArray[i] + " ");
		}
		System.out.println("");
	}

	private static NSArray<Salles> getSallesMax(NSArray<?> creneauxLibresParSemainesAPlacer) {
		NSMutableArray<Salles> sallesMax = new NSMutableArray<Salles>();
		// Détermination de la salle la plus stable...
		HashMap<Salles, Integer> nbSemainesPourSalle = new HashMap<Salles, Integer>();
		for (int i = 0; i < creneauxLibresParSemainesAPlacer.count(); i++) {
			Object creneauxPourLaSemaine = creneauxLibresParSemainesAPlacer.objectAtIndex(i);
			// NSArray<Object> creneauxPourLaSemaine = creneauxLibresParSemainesAPlacer.objectAtIndex(i);
			if (creneauxPourLaSemaine instanceof NSArray) {
				HashMap<Salles, Boolean> salleDejaCompteePourLaSemaine = new HashMap<Salles, Boolean>();
				for (int j = 0; j < ((NSArray<Object>) creneauxPourLaSemaine).count(); j++) {
					HashMap<String, Object> currentCreneau = (HashMap<String, Object>) ((NSArray<Object>) creneauxPourLaSemaine).objectAtIndex(j);
					Salles salle = (Salles) currentCreneau.get("salle");
					if (salleDejaCompteePourLaSemaine.get(salle) == null) {
						Integer nbSemaines = nbSemainesPourSalle.get(salle);
						if (nbSemaines == null) {
							nbSemaines = new Integer(0);
						}
						nbSemainesPourSalle.put(salle, new Integer(nbSemaines.intValue() + 1));
						salleDejaCompteePourLaSemaine.put(salle, Boolean.TRUE);
					}
				}
			}
			else {
				HashMap<String, Object> currentCreneau = (HashMap<String, Object>) creneauxPourLaSemaine;
				Salles salle = (Salles) currentCreneau.get("salle");
				Integer nbSemaines = nbSemainesPourSalle.get(salle);
				if (nbSemaines == null) {
					nbSemaines = new Integer(0);
				}
				nbSemainesPourSalle.put(salle, new Integer(nbSemaines.intValue() + 1));
			}
		}
		Iterator<Salles> itNbSemainesPourSalle = nbSemainesPourSalle.keySet().iterator();
		int max = 0;
		while (itNbSemainesPourSalle.hasNext()) {
			Integer nbSemaine = nbSemainesPourSalle.get(itNbSemainesPourSalle.next());
			if (nbSemaine != null && nbSemaine.intValue() >= max) {
				max = nbSemaine.intValue();
			}
		}
		Iterator<Salles> itNbSemainesPourSalle2 = nbSemainesPourSalle.keySet().iterator();
		while (itNbSemainesPourSalle2.hasNext()) {
			Salles uneSalle = itNbSemainesPourSalle2.next();
			Integer nbSemaine = nbSemainesPourSalle.get(uneSalle);
			if (nbSemaine != null && nbSemaine.intValue() == max) {
				sallesMax.addObject(uneSalle);
			}
		}
		return sallesMax.immutableClone();
	}

	public static NSArray<ConfigurationReservation> regroupeCreneauxLibres(EOEditingContext ec, NSArray<NSArray<Object>> creneauxLibresParSemaines,
			int duree, Integer intervalleMinimum, boolean prioriteSalle) {
		NSMutableArray<ConfigurationReservation> regroupement = new NSMutableArray<ConfigurationReservation>();
		if (creneauxLibresParSemaines == null) {
			return regroupement.immutableClone();
		}

		int dureeAvecIntervalleMinimum = duree;
		if (intervalleMinimum != null) {
			if (intervalleMinimum.intValue() < 0) {
				intervalleMinimum = null;
			}
			else {
				dureeAvecIntervalleMinimum += intervalleMinimum.intValue() * 2;
			}
		}
		NSMutableArray<NSArray<Object>> creneauxLibresParSemainesAPlacer = new NSMutableArray<NSArray<Object>>(creneauxLibresParSemaines);

		NSArray<Salles> sallesMax = null;
		if (prioriteSalle) {
			sallesMax = getSallesMax(creneauxLibresParSemainesAPlacer);
		}
		while (creneauxLibresParSemainesAPlacer.count() > 0) {
			// System.out.println("creneauxLibresParSemainesAPlacer = " + creneauxLibresParSemainesAPlacer);
			// System.out.println("sallesMax = " + sallesMax);

			int[] semaine = new int[10080];
			for (int x = 0; x < semaine.length; x++) {
				semaine[x] = 0;
			}
			for (int i = 0; i < creneauxLibresParSemainesAPlacer.count(); i++) {
				NSArray<Object> creneauxPourLaSemaine = creneauxLibresParSemainesAPlacer.objectAtIndex(i);
				for (int j = 0; j < creneauxPourLaSemaine.count(); j++) {
					HashMap<String, Object> currentCreneau = (HashMap<String, Object>) creneauxPourLaSemaine.objectAtIndex(j);
					Salles salle = (Salles) currentCreneau.get("salle");
					if (prioriteSalle == false || sallesMax.containsObject(salle)) {
						NSTimestamp debut = (NSTimestamp) currentCreneau.get("debut");
						NSTimestamp fin = (NSTimestamp) currentCreneau.get("fin");
						// determiner debut et fin de semaine en int depuis le lundi 00:00 (en minutes)
						int debutInt = timestampToInt(debut);
						int finInt = timestampToInt(fin);
						if (debutInt >= finInt) {
							continue;
						}

						// remplir le semaine avec et le replacer dans le hashmap...
						for (int x = debutInt; x < finInt; x++) {
							semaine[x] += 1;
						}
					}
				}
			}

			// System.out.print("semaine = ");
			// log(semaine);

			NSArray<NSArray<Object>> creneauxLibresParSemainesNouveau = null;
			int debutStable, finStable, debutMax = -1;
			// détermination du(des) creneau(x) le(s) plus stable(s) dans l'horaire, avec durée suffisante...
			int max = 0;
			// recherche du max...
			for (int i = 0; i < semaine.length; i++) {
				if (semaine[i] > max) {
					max = semaine[i];
				}
			}
			// System.out.println("max = " + max);
			while (true) {
				if (max == 0) {
					return regroupement.immutableClone();
				}
				// on cherche toutes les occurences du max avec durée suffisante...
				int[] creneauxMax = new int[semaine.length];
				int creneauxMaxCpt = 0, i;
				for (debutStable = -1, i = 0; i < semaine.length; i++) {
					if (semaine[i] >= max) {
						if (debutStable == -1) {
							debutStable = i;
						}
					}
					else {
						if (debutStable != -1 && (i - debutStable) >= dureeAvecIntervalleMinimum) {
							creneauxMax[creneauxMaxCpt++] = debutStable;
							creneauxMax[creneauxMaxCpt++] = i - 1;
						}
						debutStable = -1;
					}
				}
				if (i == semaine.length && debutStable != -1 && (i - debutStable) >= dureeAvecIntervalleMinimum) {
					creneauxMax[creneauxMaxCpt++] = debutStable;
					creneauxMax[creneauxMaxCpt++] = i - 1;
				}
				// si aucun créneau de durée suffisante trouvé, on relance avec max-1
				if (creneauxMaxCpt == 0) {
					System.out.println("Aucun créneau trouvé pour max " + max + "... recherche max-1.");
					max--;
					continue;
				}

				// on a au moins un créneau de durée suffisante, il faut refaire correspondre avec les périodes pour contrôler si ce ne
				// sont pas des fake (périodes qui se chevauchent ou s'additionnent mais qui ne font pas la durée sur le creneau max)...
				// System.out.print("creneauxMax pour max " + max + " = ");
				// logSimple(creneauxMax);

				// on cherche lequel de ces créneaux au max correspond au maximum de créneaux libres de départ pour la durée
				int maxCreneauMax = 0;
				for (i = 0; i < creneauxMaxCpt;) {
					debutStable = creneauxMax[i++];
					finStable = creneauxMax[i++];
					// System.out.println("debutStable = " + debutStable);
					// System.out.println("finStable = " + finStable);
					int comptageNbCreneaux = 0;
					NSMutableArray<NSArray<Object>> creneauxLibresParSemainesNouveauTemp = new NSMutableArray<NSArray<Object>>();
					// comptage du nombre de créneaux qui correspondent...
					for (int aa = 0; aa < creneauxLibresParSemainesAPlacer.count(); aa++) {
						NSArray<Object> creneauxPourLaSemaine = creneauxLibresParSemainesAPlacer.objectAtIndex(aa);
						NSMutableArray<Object> creneauxPourLaSemaineNouveauTemp = new NSMutableArray<Object>();
						for (int j = 0; j < creneauxPourLaSemaine.count(); j++) {
							HashMap<String, Object> currentCreneau = (HashMap<String, Object>) creneauxPourLaSemaine.objectAtIndex(j);
							Salles salle = (Salles) currentCreneau.get("salle");
							if (prioriteSalle == false || sallesMax.containsObject(salle)) {
								int debutDuCreneau = timestampToInt((NSTimestamp) currentCreneau.get("debut"));
								int finDuCreneau = timestampToInt((NSTimestamp) currentCreneau.get("fin"));
								boolean creneauValide = false;
								if (debutDuCreneau <= debutStable) {
									if (finDuCreneau >= finStable) {
										creneauValide = true;
									}
									else {
										if (finDuCreneau - dureeAvecIntervalleMinimum >= debutStable) {
											creneauValide = true;
										}
									}
								}
								else {
									if (finDuCreneau <= finStable) {
										if (finDuCreneau - debutDuCreneau >= dureeAvecIntervalleMinimum) {
											creneauValide = true;
										}
									}
									else {
										if (debutDuCreneau + dureeAvecIntervalleMinimum <= finStable) {
											creneauValide = true;
										}
									}
								}
								if (creneauValide) {
									creneauxPourLaSemaineNouveauTemp.addObject(currentCreneau);
									if (debutStable < debutDuCreneau) {
										debutStable = debutDuCreneau;
									}
									if (finStable > finDuCreneau) {
										finStable = finDuCreneau;
									}
									// if (prioriteSalle) {
									// break;
									// }
								}
							}
						}
						if (creneauxPourLaSemaineNouveauTemp.count() > 0) {
							comptageNbCreneaux++;
							creneauxLibresParSemainesNouveauTemp.addObject(creneauxPourLaSemaineNouveauTemp);
						}
					}
					if (comptageNbCreneaux > maxCreneauMax) {
						maxCreneauMax = comptageNbCreneaux;
						debutMax = debutStable;
						creneauxLibresParSemainesNouveau = creneauxLibresParSemainesNouveauTemp.immutableClone();
					}
					if (maxCreneauMax == max) {
						break;
					}
				}
				if (maxCreneauMax == 0) {
					System.out.println("Aucun créneau trouvé pour max " + max + "... recherche max-1...");
					max--;
				}
				else {
					break;
				}
			}

			// System.out.println("creneauxLibresParSemainesNouveau avant tri pour ne garder qu'une seule salle  = " +
			// creneauxLibresParSemainesNouveau);

			// parmi ces creneaux par semaine, on peut en avoir plusieurs par semaine pour différentes salles, il ne faut garder que ceux
			// avec la salle la plus régulière...
			if (creneauxLibresParSemainesNouveau != null && creneauxLibresParSemainesNouveau.count() > 0) {
				NSMutableArray<NSArray<Object>> creneauxLibresParSemainesNouveauTemp = new NSMutableArray<NSArray<Object>>();
				NSArray<Salles> sallesMaxLocal = null;
				if (prioriteSalle) {
					sallesMaxLocal = getSallesMax(creneauxLibresParSemainesNouveau);
				}
				for (int aa = 0; aa < creneauxLibresParSemainesNouveau.count(); aa++) {
					NSArray<Object> creneauxPourLaSemaine = creneauxLibresParSemainesNouveau.objectAtIndex(aa);
					for (int j = 0; j < creneauxPourLaSemaine.count(); j++) {
						HashMap<String, Object> currentCreneau = (HashMap<String, Object>) creneauxPourLaSemaine.objectAtIndex(j);
						Salles salle = (Salles) currentCreneau.get("salle");
						if (prioriteSalle == false || sallesMaxLocal.containsObject(salle)) {
							creneauxLibresParSemainesNouveauTemp.addObject(new NSArray<Object>(currentCreneau));
							break;
						}
					}
				}
				creneauxLibresParSemainesNouveau = creneauxLibresParSemainesNouveauTemp.immutableClone();
			}

			// on ote ces semaines résolues des semaines à traiter
			// System.out.println("creneauxLibresParSemainesNouveau = " + creneauxLibresParSemainesNouveau);
			for (int i = 0; i < creneauxLibresParSemainesNouveau.count(); i++) {
				HashMap<String, Object> currentCreneau = (HashMap<String, Object>) creneauxLibresParSemainesNouveau.objectAtIndex(i).objectAtIndex(0);
				for (int i2 = 0; i2 < creneauxLibresParSemainesAPlacer.count(); i2++) {
					NSArray<Object> creneauxPourLaSemaine = creneauxLibresParSemainesAPlacer.objectAtIndex(i2);
					if (creneauxPourLaSemaine.containsObject(currentCreneau)) {
						creneauxLibresParSemainesAPlacer.removeObjectAtIndex(i2);
						break;
					}
				}
			}
			// puis on regroupe ces semaines sur le créneau debutMax / finMax
			int debutMaxAvecIntervalleMinimum = debutMax;
			if (intervalleMinimum != null) {
				debutMaxAvecIntervalleMinimum = debutMaxAvecIntervalleMinimum + intervalleMinimum.intValue();
			}
			for (int i = 0; i < creneauxLibresParSemainesNouveau.count(); i++) {
				HashMap<String, Object> currentCreneau = (HashMap<String, Object>) creneauxLibresParSemainesNouveau.objectAtIndex(i).objectAtIndex(0);
				Salles salle = (Salles) currentCreneau.get("salle");
				NSTimestamp debutTS = (NSTimestamp) currentCreneau.get("debut");
				NSTimestamp debutCalcule = intToTimestamp(debutMaxAvecIntervalleMinimum, debutTS);
				NSTimestamp finCalcule = intToTimestamp(debutMaxAvecIntervalleMinimum + duree, debutTS);
				// System.out.println("salle = " + salle);
				// System.out.println("debutCalcule = " + debutCalcule);
				// System.out.println("finCalcule = " + finCalcule);
				if (updateConfigResa(regroupement, salle, debutCalcule, finCalcule) == false) {
					// si aucun existant avant, on crée une nouvelle config resa...
					ConfigurationReservation config = new ConfigurationReservation(salle, new NSArray<NSTimestamp>(new NSTimestamp[] { debutCalcule,
							finCalcule }));
					regroupement.addObject(config);
				}
			}
		}
		// System.out.println("regroupement = " + regroupement);
		return regroupement.immutableClone();
	}

	private static NSTimestamp intToTimestamp(int x, NSTimestamp timestamp) {
		// determiner date pour timestamp décalé de x minutes depuis le lundi 00:00
		return timestamp.timestampByAddingGregorianUnits(0, 0, 0, 0, x - timestampToInt(timestamp), 0);
	}

	private static int timestampToInt(NSTimestamp timestamp) {
		// determiner minute en int depuis le lundi 00:00 (en minutes)
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(timestamp);
		// jour...
		int x = calendar.get(GregorianCalendar.DAY_OF_WEEK);
		x = (x == 1 ? 6 : x - 2) * 1440;
		// heures
		x += (calendar.get(GregorianCalendar.HOUR_OF_DAY) * 60);
		// minutes
		x += calendar.get(GregorianCalendar.MINUTE);
		return x;
	}

	private static boolean updateConfigResa(NSArray<ConfigurationReservation> configResaArray, Salles salle, NSTimestamp debut, NSTimestamp fin) {
		String debutStr = DateCtrl.dateToString(debut, "%A %H:%M:%S");
		String finStr = DateCtrl.dateToString(fin, "%A %H:%M:%S");
		for (int i = 0; i < configResaArray.count(); i++) {
			ConfigurationReservation configResa = configResaArray.objectAtIndex(i);
			if ((configResa.getSalle() == null && salle == null) || configResa.getSalle().equals(salle)) {
				if (configResa.getPeriodicites() != null && configResa.getPeriodicites().count() > 0) {
					NSTimestamp configResaDebut = configResa.getPeriodicites().objectAtIndex(0);
					if (configResaDebut != null && DateCtrl.dateToString(configResaDebut, "%A %H:%M:%S").equals(debutStr)) {
						NSTimestamp configResaFin = configResa.getPeriodicites().objectAtIndex(1);
						if (configResaFin != null && DateCtrl.dateToString(configResaFin, "%A %H:%M:%S").equals(finStr)) {
							configResa.addPeriodicite(debut, fin);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static NSArray<Object> getCreneauxLibres(EOEditingContext ec, Panier panier, NSTimestamp debutCreneau, NSTimestamp finCreneau, int duree) {
		NSArray<IndividuUlr> personnes = panier.getResourcesWithType("PERSONNE");
		NSArray<Salles> salles = panier.getResourcesWithType("SALLE");
		NSArray<ResaObjet> objets = panier.getResourcesWithType("OBJET");
		NSArray<NSKeyValueCoding> apGrp = panier.getResourcesWithType("ENSEIGNEMENT_INSP");
		NSArray<Salles> choixSalles = panier.getResourcesWithType("CHOIX");
		return getCreneauxLibres(ec, personnes, salles, objets, apGrp, choixSalles, debutCreneau, finCreneau, duree, null, null, null, null, null,
				null, null);
	}

	public static NSArray<Object> getCreneauxLibres(EOEditingContext ec, NSArray<IndividuUlr> personnes, NSArray<Salles> salles,
			NSArray<ResaObjet> objets, NSArray<NSKeyValueCoding> apGrp, NSArray<Salles> choixSalles, NSTimestamp debutCreneau,
			NSTimestamp finCreneau, int duree, Integer intervalleMinimum, NSArray<Periodicite> periodicitesCache, NSArray<Mission> missionsCache,
			NSDictionary<NSKeyValueCoding, NSArray<NSTimestamp>> apsCache, Boolean avecControleApsObligatoires,
			Boolean avecControleApsNonObligatoires, Boolean avecControleGroupes) {
		Log logDetails = new Log(false);
		NSMutableArray<NSTimestamp> sommeOccupationsPersonnes = new NSMutableArray<NSTimestamp>();
		NSMutableArray<NSTimestamp> sommeOccupationsSalles = new NSMutableArray<NSTimestamp>();
		NSMutableArray<NSTimestamp> sommeOccupationsObjets = new NSMutableArray<NSTimestamp>();
		NSMutableArray<NSTimestamp> sommeOccupationsApGrp = new NSMutableArray<NSTimestamp>();
		EnseignementFactory enseignementFactory = new EnseignementFactory(ec);

		if (intervalleMinimum != null) {
			debutCreneau = debutCreneau.timestampByAddingGregorianUnits(0, 0, 0, 0, -intervalleMinimum.intValue(), 0);
			finCreneau = finCreneau.timestampByAddingGregorianUnits(0, 0, 0, 0, intervalleMinimum.intValue(), 0);
			duree = duree + intervalleMinimum.intValue() * 2;
		}
		logDetails.startOp("RechercheCreneaux.getCreneauxLibres.personnes");
		for (int i = 0; i < personnes.count(); i++) {
			IndividuUlr currentIndividu = personnes.objectAtIndex(i);
			NSArray<NSTimestamp> tmpArray = enseignementFactory.getNonDisponibiliteIndividu(new NSArray<NSTimestamp>(new NSTimestamp[] {
					debutCreneau, finCreneau }), currentIndividu, null, periodicitesCache, missionsCache);
			sommeOccupationsPersonnes.addObjectsFromArray(tmpArray);
		}
		logDetails.endOp("FIN RechercheCreneaux.getCreneauxLibres.personnes");

		logDetails.startOp("RechercheCreneaux.getCreneauxLibres.salles");
		for (int i = 0; i < salles.count(); i++) {
			Salles currentSalle = salles.objectAtIndex(i);
			NSArray<NSTimestamp> tmpArray = enseignementFactory.getNonDisponibiliteSalle(new NSArray<NSTimestamp>(new NSTimestamp[] { debutCreneau,
					finCreneau }), currentSalle, null, periodicitesCache);
			sommeOccupationsSalles.addObjectsFromArray(tmpArray);
		}
		logDetails.endOp("FIN RechercheCreneaux.getCreneauxLibres.salles");

		logDetails.startOp("RechercheCreneaux.getCreneauxLibres.objets");
		for (int i = 0; i < objets.count(); i++) {
			NSArray<NSTimestamp> tmpArray = enseignementFactory.getNonDisponibiliteObjet(new NSArray<NSTimestamp>(new NSTimestamp[] { debutCreneau,
					finCreneau }), objets.objectAtIndex(i), null, periodicitesCache);
			sommeOccupationsObjets.addObjectsFromArray(tmpArray);
		}
		logDetails.endOp("FIN RechercheCreneaux.getCreneauxLibres.objets");

		logDetails.startOp("RechercheCreneaux.getCreneauxLibres.apGrp");
		for (int i = 0; i < apGrp.count(); i++) {
			NSKeyValueCoding elem = apGrp.objectAtIndex(i);
			if (apsCache == null) {
				MaquetteAp currentAp = (MaquetteAp) elem.valueForKey("AP");
				Object currentGrp = elem.valueForKey("GRP");

				if (currentGrp == null || currentGrp instanceof String) {
					NSArray<NSTimestamp> tmpArray = (NSArray<NSTimestamp>) enseignementFactory.getNonDisponibiliteAp(currentAp,
							new NSArray<NSTimestamp>(new NSTimestamp[] { debutCreneau, finCreneau }), null, false, avecControleApsObligatoires,
							avecControleApsNonObligatoires).objectAtIndex(1);
					sommeOccupationsApGrp.addObjectsFromArray(tmpArray);
				}
				else {
					NSArray<NSTimestamp> tmpArray = enseignementFactory.getNonDisponibiliteGroupe(currentAp, (ScolGroupeGrp) currentGrp,
							new NSArray<NSTimestamp>(new NSTimestamp[] { debutCreneau, finCreneau }), null, avecControleApsObligatoires,
							avecControleApsNonObligatoires, avecControleGroupes, Boolean.FALSE);
					sommeOccupationsApGrp.addObjectsFromArray(tmpArray);
				}
			}
			else {
				NSArray<NSTimestamp> tmpArray = apsCache.objectForKey(elem);
				if (tmpArray != null && tmpArray.count() > 1) {
					for (int x = 0; x < tmpArray.count(); x += 2) {
						NSTimestamp deb = tmpArray.objectAtIndex(x);
						NSTimestamp fin = tmpArray.objectAtIndex(x + 1);
						if (deb.before(finCreneau) && fin.after(debutCreneau)) {
							sommeOccupationsApGrp.addObject(deb);
							sommeOccupationsApGrp.addObject(fin);
						}
					}
				}
			}
		}
		logDetails.endOp("FIN RechercheCreneaux.getCreneauxLibres.apGrp");

		NSMutableArray<Object> creneauxLibres = new NSMutableArray<Object>();
		if (choixSalles != null && choixSalles.count() > 0) {
			java.util.Enumeration<Salles> choixSallesEnum = choixSalles.objectEnumerator();
			while (choixSallesEnum.hasMoreElements()) {
				Salles choixSalle = choixSallesEnum.nextElement();
				NSMutableArray<NSTimestamp> sommeOccupations = new NSMutableArray<NSTimestamp>();
				sommeOccupations.addObjectsFromArray(sommeOccupationsPersonnes);
				sommeOccupations.addObjectsFromArray(sommeOccupationsSalles);
				sommeOccupations.addObjectsFromArray(sommeOccupationsObjets);
				sommeOccupations.addObjectsFromArray(sommeOccupationsApGrp);
				NSArray<NSTimestamp> tmpArray = enseignementFactory.getNonDisponibiliteSalle(new NSArray<NSTimestamp>(new NSTimestamp[] {
						debutCreneau, finCreneau }), choixSalle, null, periodicitesCache);
				sommeOccupations.addObjectsFromArray(tmpArray);
				NSMutableArray<HashMap<String, Object>> mapArray = new NSMutableArray<HashMap<String, Object>>();
				for (int n = 0; n < sommeOccupations.count(); n += 2) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("salle", choixSalle);
					map.put("debut", sommeOccupations.objectAtIndex(n));
					map.put("fin", sommeOccupations.objectAtIndex(n + 1));
					mapArray.addObject(map);
				}
				NSArray<HashMap<String, Object>> creneauxTries = null;
				try {
					creneauxTries = mapArray.sortedArrayUsingComparator(new CreneauxComparator());
				}
				catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				creneauxLibres.addObjectsFromArray(calculCreneaux(creneauxTries, duree, debutCreneau, finCreneau, choixSalle));
			}
		}
		else {
			NSMutableArray<NSTimestamp> sommeOccupations = new NSMutableArray<NSTimestamp>();
			sommeOccupations.addObjectsFromArray(sommeOccupationsPersonnes);
			sommeOccupations.addObjectsFromArray(sommeOccupationsSalles);
			sommeOccupations.addObjectsFromArray(sommeOccupationsObjets);
			sommeOccupations.addObjectsFromArray(sommeOccupationsApGrp);

			NSMutableArray<HashMap<String, Object>> mapArray = new NSMutableArray<HashMap<String, Object>>();
			for (int n = 0; n < sommeOccupations.count(); n += 2) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("salle", null);
				map.put("debut", sommeOccupations.objectAtIndex(n));
				map.put("fin", sommeOccupations.objectAtIndex(n + 1));
				mapArray.addObject(map);
			}

			NSArray<HashMap<String, Object>> creneauxTries = null;
			try {
				creneauxTries = mapArray.sortedArrayUsingComparator(new CreneauxComparator());
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			creneauxLibres.addObjectsFromArray(calculCreneaux(creneauxTries, duree, debutCreneau, finCreneau, null));
		}

		return creneauxLibres;
	}

	/**
	 * parmis les resa passees en parametre dans un ordre trie ascendant, recupere les creneaux vides entre les resa, sous forme "dateDebut
	 * et dateFin"
	 */
	private static NSArray<Object> calculCreneaux(NSArray<HashMap<String, Object>> creneauxTries, int dureeMinutes, NSTimestamp debRech,
			NSTimestamp finRech, Salles salle) {
		String sDebCreneau = FormatHandler.dateToStr(debRech, "%H:%M");
		String sFinCreneau = FormatHandler.dateToStr(finRech, "%H:%M");
		NSMutableArray<Object> creneauxLibres = new NSMutableArray<Object>();

		if (creneauxTries == null || creneauxTries.count() == 0) {
			int diff = diffDate(debRech, finRech);
			genererJour(debRech, creneauxLibres, diff + 1, dureeMinutes, sDebCreneau, sFinCreneau, 0, salle);
			return creneauxLibres;
		}

		// on ote les creneaux "caches" par d'autres plus grands... simplifie l'algo suivant...
		NSMutableArray<HashMap<String, Object>> creneauxTriesNettoyes = new NSMutableArray<HashMap<String, Object>>();
		int count = creneauxTries.count();
		for (int i = 0; i < count;) {
			HashMap<String, Object> cCourant = creneauxTries.objectAtIndex(i++);
			creneauxTriesNettoyes.addObject(cCourant);
			while (i < count) {
				HashMap<String, Object> cSuivant = creneauxTries.objectAtIndex(i);
				if (((NSTimestamp) cSuivant.get("fin")).after((NSTimestamp) cCourant.get("fin"))) {
					break;
				}
				i++;
			}
		}

		NSTimestamp debSuivant = null;
		int dm = 1;
		int pm = 0;
		for (int i = 0; i < creneauxTriesNettoyes.count(); i++) {
			pm = dm;
			dm = 0;
			HashMap<String, Object> cCourant = creneauxTriesNettoyes.objectAtIndex(i);
			NSTimestamp debCourant = (NSTimestamp) cCourant.get("debut");
			NSTimestamp finCourant = (NSTimestamp) cCourant.get("fin");
			Salles salleCourante = (Salles) cCourant.get("salle");
			if (i == 0) {
				int diff = diffDate(debRech, debCourant);
				if (diff >= 0) {
					genererJour(debRech, creneauxLibres, diff, dureeMinutes, sDebCreneau, sFinCreneau, 0, salleCourante);
				}
			}
			if (i == creneauxTriesNettoyes.count() - 1) {
				dm = 1;
				debSuivant = null;
			}
			else {
				HashMap<String, Object> cSuivant = creneauxTriesNettoyes.objectAtIndex(i + 1);
				debSuivant = (NSTimestamp) cSuivant.get("debut");

				dm = 0;
				if (!FormatHandler.dateToStr(debCourant, "%d/%m/%Y").equals(FormatHandler.dateToStr(debSuivant, "%d/%m/%Y"))) {
					dm = 1;
				}
			}
			if (pm == 1) {
				NSTimestampFormatter formatter = new NSTimestampFormatter("%d/%m/%Y");
				String myDebut = formatter.format(debCourant) + " " + sDebCreneau;
				NSTimestamp dateDebJour = FormatHandler.strToDate(myDebut, "%d/%m/%Y %H:%M");
				if (dateDebJour.before(debCourant)) {
					int longueur = TimeHandler.minutesSeparatingDates(dateDebJour, debCourant);
					if (longueur >= dureeMinutes) {
						ArrayList<HashMap<String, Object>> list = traiterCandidat(dateDebJour, debCourant, dureeMinutes, salleCourante);
						creneauxLibres.addObjects(list.toArray());
					}
				}
			}
			if (dm == 0) {
				if (finCourant.before(debSuivant)) {
					int longueur = TimeHandler.minutesSeparatingDates(finCourant, debSuivant);
					if (longueur >= dureeMinutes) {
						ArrayList<HashMap<String, Object>> list = traiterCandidat(finCourant, debSuivant, dureeMinutes, salleCourante);
						creneauxLibres.addObjects(list.toArray());
					}
				}
			}
			if (dm == 1) {
				String myFin = FormatHandler.dateToStr(finCourant, "%d/%m/%Y") + " " + sFinCreneau;
				NSTimestamp dateFinJour = FormatHandler.strToDate(myFin, "%d/%m/%Y %H:%M");
				if (finCourant.before(dateFinJour)) {
					int longueur = TimeHandler.minutesSeparatingDates(finCourant, dateFinJour);
					if (longueur >= dureeMinutes) {
						ArrayList<HashMap<String, Object>> list = traiterCandidat(finCourant, dateFinJour, dureeMinutes, salleCourante);
						creneauxLibres.addObjects(list.toArray());
					}
				}
				// combler les jours sans réservations
				int diff = 0;
				if (debSuivant != null) {
					diff = diffDate(debCourant, debSuivant);
					if (diff > 1) {
						genererJour(debCourant, creneauxLibres, diff - 1, dureeMinutes, sDebCreneau, sFinCreneau, 1, salleCourante);
					}
				}
				else {
					diff = diffDate(debCourant, finRech);
					if (diff > 0) {
						genererJour(debCourant, creneauxLibres, diff, dureeMinutes, sDebCreneau, sFinCreneau, 1, salleCourante);
					}
				}
			}
		}
		return creneauxLibres;
	}

	private static void genererJour(NSTimestamp t1, NSMutableArray<Object> creneauxLibres, int nbj, int dureeMinutes, String debCreneau,
			String finCreneau, int demain, Salles salle) {
		GregorianCalendar gDeb = new GregorianCalendar();
		gDeb.setTime(FormatHandler.midnightTime(t1));
		if (demain == 1) {
			gDeb.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}
		for (int i = 0; i < nbj; i++) {
			NSTimestamp deb = new NSTimestamp(gDeb.getTime());
			String sDeb = FormatHandler.dateToStr(deb, "%d/%m/%Y") + " " + debCreneau;
			deb = FormatHandler.strToDate(sDeb, "%d/%m/%Y %H:%M");
			String sFin = FormatHandler.dateToStr(deb, "%d/%m/%Y") + " " + finCreneau;
			NSTimestamp fin = FormatHandler.strToDate(sFin, "%d/%m/%Y %H:%M");
			int longueur = TimeHandler.minutesSeparatingDates(deb, fin);
			if (longueur >= dureeMinutes) {
				ArrayList<HashMap<String, Object>> list = traiterCandidat(deb, fin, dureeMinutes, salle);
				creneauxLibres.addObjects(list.toArray());
			}
			gDeb.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}
	}

	/** traite le creneau candidat et renvoie le decoupage prevu */
	private static ArrayList<HashMap<String, Object>> traiterCandidat(NSTimestamp debut, NSTimestamp fin, int reqDureeMinute, Salles salle) {

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		int longueur = TimeHandler.minutesSeparatingDates(debut, fin);
		int jDeb = FormatHandler.dayOfMonth(debut);
		int jFin = FormatHandler.dayOfMonth(fin);
		int nbJ = jFin - jDeb;

		// decoupage en creneaux utiles.
		if (nbJ == 0 && longueur >= reqDureeMinute) {
			HashMap<String, Object> creneau = new HashMap<String, Object>();
			creneau.put("salle", salle);
			creneau.put("debut", debut);
			creneau.put("fin", fin);
			list.add(creneau);
		}
		else {

			for (int i = jDeb; i < jFin + 1; i++) {
				NSTimestamp currentDebut, currentFin, currentDay;
				if (i == jDeb) {
					currentDebut = debut;
					currentFin = FormatHandler.endOfDay(currentDebut);
				}
				else
					if (i == jFin) {
						currentDebut = FormatHandler.midnightTime(fin);
						currentFin = fin;
					}
					else {
						currentDay = FormatHandler.replaceDayOfMonthInDate(i, debut);
						currentDebut = FormatHandler.midnightTime(currentDay);
						currentFin = FormatHandler.endOfDay(currentDay);
					}

				HashMap<String, Object> creneau = new HashMap<String, Object>();
				if (i > jFin) {
					currentFin = fin;
				}
				longueur = TimeHandler.minutesSeparatingDates(currentDebut, currentFin);
				if (longueur >= reqDureeMinute) {
					creneau.put("salle", salle);
					creneau.put("debut", currentDebut);
					creneau.put("fin", currentFin);
				}
				list.add(creneau);
			}
		}
		return list;
	}

	private NSTimestamp getDebutCreneau() {
		String sDebCreneau = "00";
		if (tHDebutCreneau.getText().length() > 0) {
			sDebCreneau = tHDebutCreneau.getText();
		}
		if (tMDebutCreneau.getText().length() > 0) {
			sDebCreneau = sDebCreneau + ":" + tMDebutCreneau.getText();
		}
		else {
			sDebCreneau = sDebCreneau + ":00";
		}
		return FormatHandler.strToDate(tDateDebut.getText() + " " + sDebCreneau, "%d/%m/%Y %H:%M");
	}

	private NSTimestamp getFinCreneau() {
		String sFinCreneau = "23";
		if (tHFinCreneau.getText().length() > 0) {
			sFinCreneau = tHFinCreneau.getText();
		}
		if (tMFinCreneau.getText().length() > 0) {
			sFinCreneau = sFinCreneau + ":" + tMFinCreneau.getText();
		}
		else {
			sFinCreneau = sFinCreneau + ":59";
		}
		return FormatHandler.strToDate(tDateFin.getText() + " " + sFinCreneau, "%d/%m/%Y %H:%M");
	}

	private static int diffDate(NSTimestamp t1, NSTimestamp t2) {
		GregorianCalendar startDate = new GregorianCalendar();
		startDate.setTime(FormatHandler.midnightTime(t1));
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.setTime(FormatHandler.midnightTime(t2));
		return (int) ((endDate.getTimeInMillis() - startDate.getTimeInMillis()) / 86400000);
	}

	/** permet de comparer chaques créneau par sa date de debut */
	private static class CreneauxComparator extends NSComparator {
		public int compare(Object object1, Object object2) throws NSComparator.ComparisonException {
			HashMap<?, ?> o1 = (HashMap<?, ?>) object1;
			HashMap<?, ?> o2 = (HashMap<?, ?>) object2;
			NSTimestamp time1 = (NSTimestamp) o1.get("debut");
			NSTimestamp time2 = (NSTimestamp) o2.get("debut");
			if (time1.equals(time2)) {
				time1 = (NSTimestamp) o2.get("fin");
				time2 = (NSTimestamp) o1.get("fin");
			}
			return time1.compare(time2);
		}
	}

	private void initUI() {
		WidgetHandler.decorateButton("calendrier : choisir la date de debut", null, "cal", bDebRecherche);
		WidgetHandler.decorateButton("calendrier : choisir la date de fin", null, "cal", bFinRecherche);
		cToutes.setVisible(false);
	}

}
