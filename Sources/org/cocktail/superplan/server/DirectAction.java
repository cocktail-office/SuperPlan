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

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.superplan.server.ics.PublishCalendarPage;
import org.cocktail.superplan.server.ics.SimpleEvent;
import org.cocktail.superplan.server.metier.IndividuUlr;
import org.cocktail.superplan.server.metier.ResaObjet;
import org.cocktail.superplan.server.metier.Salles;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WODirectAction;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.cri.planning.PartagePlanning;
import fr.univlr.cri.planning.SPOccupation;

public class DirectAction extends WODirectAction {

	EOEditingContext ec = new EOEditingContext();

	PlanningFormationProxy plannningFormProxy;

	public DirectAction(WORequest aRequest) {
		super(aRequest);

	}

	public WOActionResults defaultAction() {
		return pageWithName("Main");
	}

	/**
	 * edt d'un semestre pour le serveur de planning
	 * 
	 * @return
	 */
	public WOActionResults agendaSemestreAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());

		Integer mrsemKey = (Integer) dicoParams.valueForKey("mrsemKey");
		if (mrsemKey == null) {
			return new WOResponse();
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		NSArray<NSDictionary<String, Object>> listReservations = new PlanningFormationProxy(session).getPlanningParcoursSemestre(mrsemKey, null,
				null, true, true, true);

		NSMutableArray<SPOccupation> spOccupations = new NSMutableArray<SPOccupation>();
		if (listReservations == null) {
			return PartagePlanning.reponsePlanning(spOccupations, 0, "Pas d'occupation.");
		}

		for (int i = 0; i < listReservations.count(); i++) {
			NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				buffText.append(listStrings.get(j)).append(" - ");
			}
			spOccupations.addObject(new SPOccupation(aDebut, aFin, "edt", buffText.toString()));
		}
		WOResponse resultat = PartagePlanning.reponsePlanning(spOccupations, 1, null);
		session.terminate();
		return resultat;
	}

	/**
	 * ics d'un semestre
	 * 
	 * @return
	 */
	public WOActionResults edtSemestreAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());

		Integer mrsemKey = (Integer) dicoParams.valueForKey("mrsemKey");
		if (mrsemKey == null) {
			return new WOResponse();
		}
		Session session = (Session) session();
		session.setLazyMode(true);

		NSArray<NSDictionary<String, Object>> listReservations = new PlanningFormationProxy(session).getPlanningParcoursSemestre(mrsemKey, null,
				null, true, true, true);

		if (listReservations == null) {
			return new WOResponse();
		}

		PublishCalendarPage resultat = new PublishCalendarPage(session.context());
		resultat.setCalendarName("Semestre " + mrsemKey);
		resultat.setProdId(new Version().txtVersion());
		for (int i = 0; i < listReservations.count(); i++) {
			NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				buffText.append(listStrings.get(j)).append(" - ");
			}
			resultat.addEvent(new SimpleEvent(aDebut, aFin, buffText.toString(), String.valueOf(i)));
		}
		session.terminate();
		return resultat;
	}

	public WOActionResults agendaSalleAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());

		Integer salNumero = (Integer) dicoParams.valueForKey("salNumero");
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
		if (salNumero == null || debut == null || fin == null) {
			return new WOResponse();
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		Salles salle = Salles.fetchSalles(ec, Salles.SAL_NUMERO_KEY, salNumero);
		if (salle == null) {
			return PartagePlanning.reponsePlanning(new NSArray<SPOccupation>(), 0, "Aucune salle avec le numéro " + salNumero + " !!!");
		}
		NSArray<NSDictionary<String, Object>> listReservations = session.clientSideRequestPlanningSalle(debut, fin, ec.globalIDForObject(salle),
				new Integer(0), false);

		NSMutableArray<SPOccupation> spOccupations = new NSMutableArray<SPOccupation>();
		if (listReservations == null) {
			return PartagePlanning.reponsePlanning(spOccupations, 0, "Pas d'occupation.");
		}

		for (int i = 0; i < listReservations.count(); i++) {
			NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				buffText.append(listStrings.get(j)).append(" - ");
			}
			spOccupations.addObject(new SPOccupation(aDebut, aFin, "edt", buffText.toString()));
		}
		WOResponse resultat = PartagePlanning.reponsePlanning(spOccupations, 1, null);
		session.terminate();
		return resultat;
	}

	/**
	 * ics d'une salle
	 * 
	 * @return
	 */
	public WOActionResults edtSalleAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());

		Object salNumeroObject = dicoParams.valueForKey("salNumero");
		if (salNumeroObject == null) {
			return new WOResponse();
		}
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
		if (debut == null) {
			debut = DateCtrl.now().timestampByAddingGregorianUnits(0, -6, 0, 0, 0, 0);
		}
		if (fin == null) {
			fin = DateCtrl.now().timestampByAddingGregorianUnits(0, 6, 0, 0, 0, 0);
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		String[] salNumeros = null;
		if (salNumeroObject instanceof Integer) {
			salNumeros = new String[1];
			salNumeros[0] = ((Integer) salNumeroObject).toString();
		}
		else {
			if (((String) salNumeroObject).indexOf(",") >= 0) {
				salNumeros = ((String) salNumeroObject).split(",");
			}
			if (((String) salNumeroObject).indexOf(";") >= 0) {
				salNumeros = ((String) salNumeroObject).split(";");
			}
		}

		if (salNumeros == null || salNumeros.length == 0) {
			return new WOResponse();
		}
		boolean affichePorte = false;
		if (salNumeros.length > 1) {
			affichePorte = true;
		}

		PublishCalendarPage resultat = new PublishCalendarPage(session.context());
		resultat.setProdId(new Version().txtVersion());
		if (salNumeros.length > 1) {
			resultat.setCalendarName("Salles");
		}
		for (int j = 0; j < salNumeros.length; j++) {
			try {
				Integer salNumero = new Integer(salNumeros[j]);
				Salles salle = Salles.fetchSalles(ec, Salles.SAL_NUMERO_KEY, salNumero);
				if (salle == null) {
					continue;
				}
				if (salNumeros.length == 1) {
					resultat.setCalendarName("Salle " + salle.salPorte() + " (" + salle.local().appellation() + ")");
				}
				NSArray<NSDictionary<String, Object>> listReservations = session.clientSideRequestPlanningSalle(debut, fin,
						ec.globalIDForObject(salle), new Integer(0), affichePorte);

				if (listReservations == null) {
					continue;
				}

				for (int i = 0; i < listReservations.count(); i++) {
					NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
					NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
					NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
					NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
					StringBuffer buffText = new StringBuffer();
					for (int k = 0; k < listStrings.count(); k++) {
						buffText.append(listStrings.get(k)).append(" - ");
					}
					resultat.addEvent(new SimpleEvent(aDebut, aFin, buffText.toString(), salNumero.toString() + "-" + i));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		session.terminate();
		return resultat;
	}

	// public WOActionResults edtSalleAction() {
	// NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());
	//
	// Integer salNumero = (Integer) dicoParams.valueForKey("salNumero");
	// if (salNumero == null) {
	// return new WOResponse();
	// }
	// NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
	// NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
	// if (debut == null) {
	// debut = DateCtrl.now().timestampByAddingGregorianUnits(0, -6, 0, 0, 0, 0);
	// }
	// if (fin == null) {
	// fin = DateCtrl.now().timestampByAddingGregorianUnits(0, 6, 0, 0, 0, 0);
	// }
	//
	// Session session = (Session) session();
	// session.setLazyMode(true);
	//
	// Salles salle = Salles.fetchSalles(ec, Salles.SAL_NUMERO_KEY, salNumero);
	// if (salle == null) {
	// return new WOResponse();
	// }
	// NSArray<NSDictionary<String, Object>> listReservations = session.clientSideRequestPlanningSalle(debut, fin,
	// ec.globalIDForObject(salle),
	// new Integer(1), false);
	//
	// if (listReservations == null) {
	// return new WOResponse();
	// }
	//
	// PublishCalendarPage resultat = new PublishCalendarPage(session.context());
	// resultat.setCalendarName("Salle " + salle.salPorte() + " (" + salle.local().appellation() + ")");
	// resultat.setProdId(new Version().txtVersion());
	// for (int i = 0; i < listReservations.count(); i++) {
	// NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
	// NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
	// NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
	// NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
	// StringBuffer buffText = new StringBuffer();
	// for (int j = 0; j < listStrings.count(); j++) {
	// buffText.append(listStrings.get(j)).append(" - ");
	// }
	// resultat.addEvent(new SimpleEvent(aDebut, aFin, buffText.toString(), String.valueOf(i)));
	// }
	// session.terminate();
	// return resultat;
	// }

	/**
	 * edt d'un objet pour le serveur de planning
	 * 
	 * @return
	 */
	public WOActionResults agendaObjetAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());

		Integer roKey = (Integer) dicoParams.valueForKey("roKey");
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
		if (roKey == null || debut == null || fin == null) {
			return new WOResponse();
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		ResaObjet objet = ResaObjet.fetchResaObjet(ec, ResaObjet.RO_KEY_KEY, roKey);
		if (objet == null) {
			return PartagePlanning.reponsePlanning(new NSArray<SPOccupation>(), 0, "Aucun objet avec le numéro " + roKey + " !!!");
		}
		NSArray<NSDictionary<String, Object>> listReservations = session.clientSideRequestPlanningObjet(debut, fin, ec.globalIDForObject(objet));

		NSMutableArray<SPOccupation> spOccupations = new NSMutableArray<SPOccupation>();
		if (listReservations == null) {
			return PartagePlanning.reponsePlanning(spOccupations, 0, "Pas d'occupation.");
		}

		for (int i = 0; i < listReservations.count(); i++) {
			NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				buffText.append(listStrings.get(j)).append(" - ");
			}
			spOccupations.addObject(new SPOccupation(aDebut, aFin, "edt", buffText.toString()));
		}
		WOResponse resultat = PartagePlanning.reponsePlanning(spOccupations, 1, null);
		session.terminate();
		return resultat;
	}

	/**
	 * ics d'un objet
	 * 
	 * @return
	 */
	public WOActionResults edtObjetAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());

		Integer roKey = (Integer) dicoParams.valueForKey("roKey");
		if (roKey == null) {
			return new WOResponse();
		}
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
		if (debut == null) {
			debut = DateCtrl.now().timestampByAddingGregorianUnits(0, -6, 0, 0, 0, 0);
		}
		if (fin == null) {
			fin = DateCtrl.now().timestampByAddingGregorianUnits(0, 6, 0, 0, 0, 0);
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		ResaObjet objet = ResaObjet.fetchResaObjet(ec, ResaObjet.RO_KEY_KEY, roKey);
		if (objet == null) {
			return new WOResponse();
		}
		NSArray<NSDictionary<String, Object>> listReservations = session.clientSideRequestPlanningObjet(debut, fin, ec.globalIDForObject(objet));
		if (listReservations == null) {
			return new WOResponse();
		}

		PublishCalendarPage resultat = new PublishCalendarPage(session.context());
		resultat.setCalendarName(objet.roLibelle1());
		resultat.setProdId(new Version().txtVersion());
		for (int i = 0; i < listReservations.count(); i++) {
			NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				buffText.append(listStrings.get(j)).append(" - ");
			}
			resultat.addEvent(new SimpleEvent(aDebut, aFin, buffText.toString(), String.valueOf(i)));
		}
		session.terminate();
		return resultat;
	}

	/**
	 * pour le serveur de planning
	 * 
	 * @return
	 */
	public WOActionResults agendaPourPeriodeAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());
		Number noIndividu = (Number) dicoParams.valueForKey("noIndividu");
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");

		Session session = (Session) session();
		session.setLazyMode(true);

		NSMutableArray<SPOccupation> spOccupations = new NSMutableArray<SPOccupation>();
		NSArray<IndividuUlr> list = DBHandler.fetchData(ec, IndividuUlr.ENTITY_NAME, IndividuUlr.NO_INDIVIDU_KEY, noIndividu);
		if (list == null) {
			WOResponse resultat = new WOResponse();
			resultat = PartagePlanning.reponsePlanning(spOccupations, 0, "Aucun individu !!!");
			return resultat;
		}
		IndividuUlr individu = list.lastObject();

		int status = 1;
		String errMessage = "";

		NSArray<NSDictionary<String, Object>> resaObjets = session.clientSideRequestPlanningIndividu(debut, fin, ec.globalIDForObject(individu),
				new Integer(1));
		if (resaObjets == null) {
			return PartagePlanning.reponsePlanning(spOccupations, 0, "Aucun planning !!!");
		}

		for (int i = 0; i < resaObjets.count(); i++) {
			NSDictionary<String, Object> dico = resaObjets.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			String text = ((NSArray<String>) dico.valueForKey("texte")).lastObject();
			SPOccupation spOccupation = new SPOccupation(aDebut, aFin, (String) dico.valueForKey("typeTemps"), text);
			spOccupations.addObject(spOccupation);
		}
		WOResponse resultat = PartagePlanning.reponsePlanning(spOccupations, status, errMessage);
		session.terminate();
		return resultat;
	}

	/**
	 * ics complet (= agendaPourPeriode du serveur de planning)
	 * 
	 * @return
	 */
	public WOActionResults edtPourPeriodeAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());
		Number noIndividu = (Number) dicoParams.valueForKey("noIndividu");
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
		if (noIndividu == null) {
			return new WOResponse();
		}
		if (debut == null) {
			debut = DateCtrl.now().timestampByAddingGregorianUnits(0, -6, 0, 0, 0, 0);
		}
		if (fin == null) {
			fin = DateCtrl.now().timestampByAddingGregorianUnits(0, 6, 0, 0, 0, 0);
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		NSArray<IndividuUlr> list = DBHandler.fetchData(ec, IndividuUlr.ENTITY_NAME, IndividuUlr.NO_INDIVIDU_KEY, noIndividu);
		if (list == null) {
			return new WOResponse();
		}
		IndividuUlr individu = list.lastObject();
		NSArray<NSDictionary<String, Object>> listReservations = session.clientSideRequestPlanningIndividu(debut, fin,
				ec.globalIDForObject(individu), new Integer(1));
		if (listReservations == null) {
			return new WOResponse();
		}

		PublishCalendarPage resultat = new PublishCalendarPage(session.context());
		resultat.setCalendarName(individu.nomPrenom());
		resultat.setProdId(new Version().txtVersion());
		for (int i = 0; i < listReservations.count(); i++) {
			NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			String text = ((NSArray<String>) dico.valueForKey("texte")).lastObject();
			resultat.addEvent(new SimpleEvent(aDebut, aFin, text, String.valueOf(i)));
		}
		session.terminate();
		return resultat;
	}

	/**
	 * edt d'un étudiant pour le serveur de planning
	 * 
	 * @return
	 */
	public WOActionResults agendaEtudiantAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());

		Integer noIndividu = (Integer) dicoParams.valueForKey("noIndividu");
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
		if (noIndividu == null || debut == null || fin == null) {
			return new WOResponse();
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		NSArray<NSDictionary<String, Object>> listReservations = new PlanningFormationProxy(session).getPlanningEtudiant(noIndividu, debut, fin);
		NSMutableArray<SPOccupation> spOccupations = new NSMutableArray<SPOccupation>();
		if (listReservations == null) {
			return PartagePlanning.reponsePlanning(spOccupations, 0, "Pas d'occupation.");
		}

		for (int i = 0; i < listReservations.count(); i++) {
			NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				buffText.append(listStrings.get(j)).append(" - ");
			}
			spOccupations.addObject(new SPOccupation(aDebut, aFin, "edt", buffText.toString()));
		}
		WOResponse resultat = PartagePlanning.reponsePlanning(spOccupations, 1, null);
		session.terminate();
		return resultat;
	}

	/**
	 * ics d'un étudiant
	 * 
	 * @return
	 */
	public WOActionResults edtEtudiantAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());

		Integer noIndividu = (Integer) dicoParams.valueForKey("noIndividu");
		if (noIndividu == null) {
			return new WOResponse();
		}
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
		if (debut == null) {
			debut = DateCtrl.now().timestampByAddingGregorianUnits(0, -6, 0, 0, 0, 0);
		}
		if (fin == null) {
			fin = DateCtrl.now().timestampByAddingGregorianUnits(0, 6, 0, 0, 0, 0);
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		NSArray<NSDictionary<String, Object>> listReservations = new PlanningFormationProxy(session).getPlanningEtudiant(noIndividu, debut, fin);

		if (listReservations == null) {
			return new WOResponse();
		}

		PublishCalendarPage resultat = new PublishCalendarPage(session.context());
		resultat.setCalendarName("Etudiant " + noIndividu);
		resultat.setProdId(new Version().txtVersion());
		for (int i = 0; i < listReservations.count(); i++) {
			NSDictionary<String, Object> dico = listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				buffText.append(listStrings.get(j)).append(" - ");
			}
			resultat.addEvent(new SimpleEvent(aDebut, aFin, buffText.toString(), String.valueOf(i)));
		}
		session.terminate();
		return resultat;
	}

	/**
	 * Pour le serveur de planning...
	 * 
	 * @return
	 */
	public WOActionResults agendaGroupeAction() {
		NSDictionary<String, Object> dicoParams = PartagePlanning.dicoParams(request());
		Integer ggrpKey = (Integer) dicoParams.valueForKey("ggrpKey");
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");
		if (ggrpKey == null || debut == null || fin == null) {
			return new WOResponse();
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		NSArray<NSDictionary<String, Object>> listReservations = new PlanningFormationProxy(session).getPlanningGroupe(ggrpKey, debut, fin);

		int status = 1;
		String errMessage = "";
		NSMutableArray<SPOccupation> spOccupations = new NSMutableArray<SPOccupation>();
		if (listReservations == null) {
			return PartagePlanning.reponsePlanning(spOccupations, 0, "Pas d'occupation.");
		}

		for (int i = 0; i < listReservations.count(); i++) {
			NSMutableDictionary<String, Object> dico = (NSMutableDictionary<String, Object>) listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray<String> listStrings = (NSArray<String>) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				String currentString = listStrings.get(j);
				buffText.append(currentString).append("\n");
			}
			spOccupations.addObject(new SPOccupation(aDebut, aFin, "edt", buffText.toString()));
		}
		WOResponse resultat = PartagePlanning.reponsePlanning(spOccupations, status, errMessage);
		session.terminate();
		return resultat;
	}

	/**
	 * Pour le serveur de planning... plutot foireux, éviter de l'utiliser...
	 * 
	 * @return
	 * @deprecated
	 */
	public WOActionResults agendaFormationAction() {

		NSDictionary dicoParams = PartagePlanning.dicoParams(request());
		Number fspnKey = (Number) dicoParams.valueForKey("fspnKey");
		NSTimestamp debut = (NSTimestamp) dicoParams.valueForKey("debut");
		NSTimestamp fin = (NSTimestamp) dicoParams.valueForKey("fin");

		if (fspnKey == null || debut == null || fin == null) {
			return new WOResponse();
		}

		Session session = (Session) session();
		session.setLazyMode(true);

		// TEST
		/*
		 * Number fspnKey = new Integer(2722); NSTimestamp debut = FormatHandler.strToDate("07/06/2009","%d/%m/%Y"); NSTimestamp fin =
		 * FormatHandler.strToDate("15/06/2009","%d/%m/%Y"); //
		 */

		NSArray listReservations = new PlanningFormationProxy(session).getPlanningFormation(fspnKey, debut, fin);

		int status = 1;
		String errMessage = "";

		NSMutableArray spOccupations = new NSMutableArray();
		if (listReservations == null) {
			return PartagePlanning.reponsePlanning(spOccupations, 0, "Pas d'occupation.");
		}

		for (int i = 0; i < listReservations.count(); i++) {
			NSMutableDictionary dico = (NSMutableDictionary) listReservations.objectAtIndex(i);
			NSTimestamp aDebut = (NSTimestamp) dico.valueForKey("debut");
			NSTimestamp aFin = (NSTimestamp) dico.valueForKey("fin");
			NSArray listStrings = (NSArray) dico.valueForKey("texte");
			StringBuffer buffText = new StringBuffer();
			for (int j = 0; j < listStrings.count(); j++) {
				String currentString = (String) listStrings.get(j);
				buffText.append(currentString).append("\n");
			}
			SPOccupation spOccupation = new SPOccupation(aDebut, aFin, "edt", buffText.toString());
			spOccupations.addObject(spOccupation);
		}
		WOResponse resultat = PartagePlanning.reponsePlanning(spOccupations, status, errMessage);
		session.terminate();
		return resultat;

	}

}
