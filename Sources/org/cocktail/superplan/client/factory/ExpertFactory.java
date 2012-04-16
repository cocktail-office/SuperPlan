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
package org.cocktail.superplan.client.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.EdtExpertNotCompleteException;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Mission;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ReservationObjet;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.StructuresAutorisees;
import org.cocktail.superplan.client.metier.VMaquetteApGroupes;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import edtscol.client.RechercheCreneaux;
import edtscol.client.VerifDisponibilite;
import edtscol.client.gestionreservation.ConfigurationReservation;
import edtscol.client.gestionreservation.InspecteurCtrl;
import edtscol.client.panier.Panier;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.Log;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;

public class ExpertFactory {

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private IndividuUlr agent = (IndividuUlr) app.userInfo("individu");
	private TimeHandler timeHandler;

	private EOEditingContext eContext;

	private int typeReservation = InspecteurCtrl.TYPE_RESA_REUNION; // réunion par défaut...
	private Integer debut = null, fin = null, duree = null, annee = null, intervalleMinimum = null;
	private ArrayList<Integer> jours;
	private String semaines;
	private NSArray<IndividuUlr> personnes;
	private NSArray<Salles> salles;
	private NSArray<Salles> choixSalles;
	private NSArray<StructuresAutorisees> groupes;
	private NSArray<ResaObjet> objets;
	private NSArray<NSKeyValueCoding> enseigns;
	private String commentaire = ""; // pas de commentaire pas défaut...
	private Integer visibilite = new Integer(1); // public par défaut...
	private boolean isBloqueHeures = false;
	private boolean isEviteSemainesAvecAPDejaPlace = false;

	public ExpertFactory(EOEditingContext eContext) {
		this.eContext = eContext;
		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());
	}

	public Reservation enregistrementExpert(boolean withDialog, boolean prioriteSalle, boolean saveChanges) throws EdtException,
			EdtExpertNotCompleteException {

		if (typeReservation != InspecteurCtrl.TYPE_RESA_REUNION && typeReservation != InspecteurCtrl.TYPE_RESA_ENSEIGNEMENT) {
			throw new EdtException("Le mode de placement automatique est réservé aux réunions et enseignements !", true);
		}
		if (debut == null || fin == null || duree == null) {
			throw new EdtException("Il faut donner une heure de début, une heure de fin et une durée !", true);
		}
		if (jours == null || jours.isEmpty()) {
			throw new EdtException("Aucun jour indiqué pour l'enregistrement !", true);
		}
		if (StringCtrl.isEmpty(semaines)) {
			throw new EdtException("Aucune semaine indiquée pour l'enregistrement !", true);
		}
		if (annee == null) {
			throw new EdtException("Aucune année indiquée pour l'enregistrement !", true);
		}
		if (typeReservation == InspecteurCtrl.TYPE_RESA_REUNION) {
			if (!InspecteurCtrl.testRessourcesReunion(personnes, groupes, salles, choixSalles, objets)) {
				throw new EdtException("Il manque des ressources obligatoires pour enregistrer la réunion !", true);
			}
		}
		if (typeReservation == InspecteurCtrl.TYPE_RESA_ENSEIGNEMENT) {
			if (!InspecteurCtrl.testRessourcesEnseignement(enseigns)) {
				throw new EdtException("Il manque des ressources obligatoires pour enregistrer la réservation d'enseignement !", true);
			}
		}

		int nbSemainesAPlacer = 1000;
		try {
			if (isBloqueHeures && (enseigns != null && enseigns.count() > 0)) {
				MaquetteAp ap = (MaquetteAp) ((NSDictionary<String, Object>) enseigns.objectAtIndex(0)).valueForKey("AP");
				if (ap != null) {
					Object grpObject = ((NSDictionary<String, Object>) enseigns.objectAtIndex(0)).valueForKey("GRP");
					ScolGroupeGrp grp = null;
					if (grpObject != null && grpObject instanceof ScolGroupeGrp) {
						grp = (ScolGroupeGrp) grpObject;
					}
					VMaquetteApGroupes vap = ap.vMaquetteApGroupes(grp);
					if (vap != null) {
						BigDecimal totalAPlacer = vap.totalRestantAPlacer();
						if (totalAPlacer != null && totalAPlacer.signum() == 1) {
							nbSemainesAPlacer = totalAPlacer.multiply(new BigDecimal(60)).divide(new BigDecimal(duree), BigDecimal.ROUND_DOWN)
									.intValue();
						}
						else {
							nbSemainesAPlacer = 0;
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (nbSemainesAPlacer == 0) {
			throw new EdtException("Plus aucune heure à placer pour cet AP !", false);
		}

		if (isEviteSemainesAvecAPDejaPlace && (enseigns != null && enseigns.count() > 0)) {
			NSMutableArray<Integer> newSemainesArray = new NSMutableArray<Integer>();
			NSArray<Integer> semainesArray = TimeHandler.getSemainesArrayFromSemainesString(semaines, annee);

			NSKeyValueCoding elem = enseigns.objectAtIndex(0);
			MaquetteAp ap = (MaquetteAp) elem.valueForKey("AP");
			Object grpObject = elem.valueForKey("GRP");
			ScolGroupeGrp groupe = null;
			if (grpObject != null && grpObject instanceof ScolGroupeGrp) {
				groupe = (ScolGroupeGrp) grpObject;
			}

			boolean anneeCivile = ((Boolean) app.userInfo("anneeCivile")).booleanValue();
			for (int i = 0; i < semainesArray.count(); i++) {
				Integer semaine = semainesArray.objectAtIndex(i);
				NSTimestamp[] be = TimeHandler.getBeginAndEndOfWeek(semaine, TimeHandler.getYearForWeekAndFannKey(semaine, annee, anneeCivile));
				boolean semaineLibre = true;

				NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>();
				andQuals.addObject(new EOKeyValueQualifier(Periodicite.DATE_DEB_KEY, EOKeyValueQualifier.QualifierOperatorLessThan, be[1]));
				andQuals.addObject(new EOKeyValueQualifier(Periodicite.DATE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThan, be[0]));
				EOQualifier qual = new EOAndQualifier(andQuals);
				Enumeration<ReservationAp> e = ap.reservationAps().objectEnumerator();
				while (e.hasMoreElements()) {
					ReservationAp resaAp = e.nextElement();
					if (resaAp != null && resaAp.reservation() != null) {
						if (groupe == null || groupe.equals(resaAp.scolGroupeGrp())) {
							NSArray<Periodicite> periods = resaAp.reservation().periodicites(qual);
							if (periods != null && periods.count() > 0) {
								semaineLibre = false;
								break;
							}
						}
					}
				}
				if (semaineLibre) {
					newSemainesArray.addObject(semaine);
				}
			}
			semaines = newSemainesArray.componentsJoinedByString(";");
		}

		NSMutableArray<NSTimestamp> creneaux = new NSMutableArray<NSTimestamp>();
		try {
			for (int i = 0; i < jours.size(); i++) {
				creneaux.addObjectsFromArray(timeHandler.computePeriodicites(jours.get(i).intValue(), semaines,
						String.valueOf(debut.intValue() / 60), String.valueOf(fin.intValue() / 60), String.valueOf(debut.intValue() % 60),
						String.valueOf(fin.intValue() % 60), annee.intValue()));
			}
			creneaux.sortUsingComparator(NSComparator.AscendingTimestampComparator);
			creneaux = TimeHandler.testCoherenceDates(creneaux);
			creneaux = TimeHandler.retirerIncoherences(creneaux);
		}
		catch (Exception e1) {
			e1.printStackTrace();
			throw new EdtException(e1.getMessage());
		}

		Log log = new Log(false);
		log.startOp("ExpertFactory... getApsCache...");
		NSDictionary<NSKeyValueCoding, NSArray<NSTimestamp>> apsCache = getApsCache(enseigns, creneaux);
		log.endOp("FIN ExpertFactory... getApsCache...");
		NSArray<Periodicite> periodicitesCache = getPeriodicitesCache(personnes, salles, objets, choixSalles, creneaux);
		NSArray<Mission> missionsCache = getMissionsCache(personnes, creneaux);

		log.startOp("ExpertFactory.enregistrementExpert.getCreneauxLibres");
		boolean warning = false;
		NSMutableArray<NSArray<Object>> creneauxLibresParSemaines = new NSMutableArray<NSArray<Object>>();
		Enumeration<NSTimestamp> enumCreneaux = creneaux.objectEnumerator();
		EdtExpertNotCompleteException encException = new EdtExpertNotCompleteException();
		while (enumCreneaux.hasMoreElements() && creneauxLibresParSemaines.count() < nbSemainesAPlacer) {
			NSMutableArray<Object> lesCreneaux = new NSMutableArray<Object>();
			NSTimestamp debutCreneau = null;
			for (int i = 0; i < jours.size(); i++) {
				debutCreneau = enumCreneaux.nextElement();
				NSTimestamp finCreneau = enumCreneaux.nextElement();
				lesCreneaux.addObjectsFromArray(RechercheCreneaux.getCreneauxLibres(eContext, personnes, salles, objets, enseigns, choixSalles,
						debutCreneau, finCreneau, duree.intValue(), intervalleMinimum, periodicitesCache, missionsCache, apsCache, Boolean.TRUE,
						Boolean.TRUE, Boolean.TRUE));
			}
			if (lesCreneaux != null && lesCreneaux.count() > 0) {
				creneauxLibresParSemaines.addObject(lesCreneaux);
			}
			else {
				warning = true;
				encException.addSemaine(timeHandler.weekOfYear(debutCreneau));
			}
		}
		log.endOp("FIN ExpertFactory.enregistrementExpert.getCreneauxLibres");

		if (creneauxLibresParSemaines == null || creneauxLibresParSemaines.count() == 0) {
			throw new EdtException("Aucun créneau disponible !", true);
		}

		if (warning) {
			if (withDialog) {
				if (WindowHandler
						.showConfirmation("Certaines semaines sont indisponibles... Enregistrer quand même pour les autres qui ont des disponibilités ?") == false) {
					throw new EdtException("Certaines semaines indisponibles, abandon...", false);
				}
			}
		}

		// on a la liste des creneaux libres, on va tenter de les regrouper par similitude pour creer le moins de reservations possible
		// en usant des periodicites...
		if (withDialog && choixSalles != null && choixSalles.count() > 0) {
			prioriteSalle = WindowHandler.showConfirmation("Vous avez indiqué des salles au choix... privilégier la régularité sur la salle ?") == true;
		}
		NSArray<ConfigurationReservation> creneauxParSemaine = RechercheCreneaux.regroupeCreneauxLibres(eContext, creneauxLibresParSemaines, duree,
				intervalleMinimum, prioriteSalle);

		if (creneauxParSemaine == null || creneauxParSemaine.count() == 0) {
			throw new EdtException("Problème pour trouver des créneaux libres, abandon...", true);
		}

		Reservation reservation = null;
		Enumeration<ConfigurationReservation> periodicitesDifferentesEnumeration = creneauxParSemaine.objectEnumerator();
		while (periodicitesDifferentesEnumeration.hasMoreElements()) {
			ConfigurationReservation laSemaine = periodicitesDifferentesEnumeration.nextElement();
			NSArray<NSTimestamp> lesPeriodicites = laSemaine.getPeriodicites();
			NSArray<Salles> lesSalles = salles;
			if (laSemaine.getSalle() != null) {
				lesSalles = salles.arrayByAddingObject(laSemaine.getSalle());
			}

			try {
				if (typeReservation == InspecteurCtrl.TYPE_RESA_REUNION) {
					reservation = new ReunionFactory(eContext).creerReunion(null, agent, lesPeriodicites, personnes, groupes, lesSalles, null,
							objets, commentaire, visibilite.intValue(), saveChanges, false);
				}
				if (typeReservation == InspecteurCtrl.TYPE_RESA_ENSEIGNEMENT) {
					reservation = new EnseignementFactory(eContext).creerEdtEnseignement(null, agent, personnes, lesPeriodicites, enseigns,
							lesSalles, null, objets, commentaire, saveChanges, false);
				}
			}
			catch (Exception e) {
				if (saveChanges) {
					eContext.revert();
				}
				e.printStackTrace();
				throw new EdtException(e.getMessage(), true);
			}
		}
		if (warning && !withDialog) {
			encException.setReservation(reservation);
			throw encException;
		}
		return reservation;
	}

	private NSArray<Periodicite> getPeriodicitesCache(NSArray<IndividuUlr> personnes, NSArray<Salles> salles, NSArray<ResaObjet> objets,
			NSArray<Salles> choixSalles, NSArray<NSTimestamp> creneaux) {
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		if (personnes != null && personnes.count() > 0) {
			NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>(personnes.count());
			for (int i = 0; i < personnes.count(); i++) {
				IndividuUlr individu = personnes.objectAtIndex(i);
				orQuals.addObject(new EOKeyValueQualifier(
						Periodicite.RESERVATION_KEY + "." + Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY,
						EOKeyValueQualifier.QualifierOperatorEqual, individu));
			}
			quals.addObject(new EOOrQualifier(orQuals));
		}
		if (salles != null && salles.count() > 0) {
			NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>(salles.count());
			for (int i = 0; i < salles.count(); i++) {
				Salles salle = salles.objectAtIndex(i);
				orQuals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY + "."
						+ ResaSalles.SALLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, salle));
			}
			quals.addObject(new EOOrQualifier(orQuals));
		}
		if (objets != null && objets.count() > 0) {
			NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>(objets.count());
			for (int i = 0; i < objets.count(); i++) {
				ResaObjet objet = objets.objectAtIndex(i);
				orQuals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_OBJETS_KEY + "."
						+ ReservationObjet.RESA_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, objet));
			}
			quals.addObject(new EOOrQualifier(orQuals));
		}
		if (choixSalles != null && choixSalles.count() > 0) {
			NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>(choixSalles.count());
			for (int i = 0; i < choixSalles.count(); i++) {
				Salles salle = choixSalles.objectAtIndex(i);
				orQuals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESA_SALLES_KEY + "."
						+ ResaSalles.SALLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, salle));
			}
			quals.addObject(new EOOrQualifier(orQuals));
		}
		NSMutableArray<EOQualifier> andQuals = new NSMutableArray<EOQualifier>();
		andQuals.addObject(VerifDisponibilite.qualifierFromPeriodicites(creneaux));
		andQuals.addObject(new EOOrQualifier(quals));

		return Periodicite.fetchPeriodicites(eContext, new EOAndQualifier(andQuals), null);
	}

	private NSArray<Mission> getMissionsCache(NSArray<IndividuUlr> personnes, NSArray<NSTimestamp> creneaux) {
		NSMutableArray<Mission> missions = new NSMutableArray<Mission>();
		if (personnes != null) {
			for (int i = 0; i < personnes.count(); i++) {
				missions.addObjectsFromArray(VerifDisponibilite.getMissions(eContext, creneaux, personnes.objectAtIndex(i), null));
			}
		}
		return missions.immutableClone();
	}

	private NSDictionary<NSKeyValueCoding, NSArray<NSTimestamp>> getApsCache(NSArray<NSKeyValueCoding> apGrp, NSArray<NSTimestamp> creneaux) {
		EnseignementFactory enseignementFactory = new EnseignementFactory(eContext);
		NSMutableDictionary<NSKeyValueCoding, NSArray<NSTimestamp>> retour = new NSMutableDictionary<NSKeyValueCoding, NSArray<NSTimestamp>>();
		for (int i = 0; i < apGrp.count(); i++) {
			NSKeyValueCoding elem = apGrp.objectAtIndex(i);
			MaquetteAp currentAp = (MaquetteAp) elem.valueForKey("AP");
			Object grp = elem.valueForKey("GRP");

			if (grp == null || grp instanceof String) {
				NSArray<NSTimestamp> tmpArray = (NSArray<NSTimestamp>) enseignementFactory.getNonDisponibiliteAp(currentAp, creneaux, null, false,
						Boolean.TRUE, Boolean.TRUE).objectAtIndex(1);
				retour.setObjectForKey(tmpArray, elem);
			}
			else {
				NSArray<NSTimestamp> tmpArray = enseignementFactory.getNonDisponibiliteGroupe(currentAp, (ScolGroupeGrp) grp, creneaux, null,
						Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
				retour.setObjectForKey(tmpArray, elem);
			}
		}
		return retour;
	}

	public void setPanier(Panier panier) {
		setPersonnes(panier.getResourcesWithType("PERSONNE"));
		setSalles(panier.getResourcesWithType("SALLE"));
		setChoixSalles(panier.getResourcesWithType("CHOIX"));
		setGroupes(panier.getResourcesWithType("GROUPE"));
		setObjets(panier.getResourcesWithType("OBJET"));
		setEnseigns(panier.getResourcesWithType("ENSEIGNEMENT_INSP"));
	}

	public void setTypeReservation(int typeReservation) {
		this.typeReservation = typeReservation;
	}

	public void setDebut(Integer debut) {
		this.debut = debut;
	}

	public void setFin(Integer fin) {
		this.fin = fin;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	public void setJours(ArrayList<Integer> jours) {
		this.jours = jours;
	}

	public void setSemaines(String semaines) {
		this.semaines = semaines;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public void setPersonnes(NSArray<IndividuUlr> personnes) {
		this.personnes = personnes;
	}

	public void setSalles(NSArray<Salles> salles) {
		this.salles = salles;
	}

	public void setChoixSalles(NSArray<Salles> choixSalles) {
		this.choixSalles = choixSalles;
	}

	public void setGroupes(NSArray<StructuresAutorisees> groupes) {
		this.groupes = groupes;
	}

	public void setObjets(NSArray<ResaObjet> objets) {
		this.objets = objets;
	}

	public void setEnseigns(NSArray<NSKeyValueCoding> enseigns) {
		this.enseigns = enseigns;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public void setVisibilite(Integer visibilite) {
		this.visibilite = visibilite;
	}

	public void setIntervalleMinimum(Integer intervalleMinimum) {
		this.intervalleMinimum = intervalleMinimum;
	}

	public void setBloqueHeures(boolean isBloqueHeures) {
		this.isBloqueHeures = isBloqueHeures;
	}

	public void setEviteSemainesAvecAPDejaPlace(boolean isEviteSemainesAvecAPDejaPlace) {
		this.isEviteSemainesAvecAPDejaPlace = isEviteSemainesAvecAPDejaPlace;
	}

}
