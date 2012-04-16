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
package edtscol.client.panier;

import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.ResaExamen;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ReservationSemestre;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.StructuresAutorisees;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;

public class GestionPanier {

	public static final int PERSONNE = 0;
	public static final int SALLE = 1;
	public static final int ENSEIGNEMENT = 2;
	public static final int EXAMEN = 3;
	public static final int OBJET = 4;
	public static final int GROUPE = 5;
	public static final int CHOIX = 6;
	public static final int GROUPE_SCOL = 7;
	public static final int ENSEIGN_AP = 8;

	public static final int ENSEIGNEMENT_INSP = 9;
	public static final int SEMESTRE_GRP_INSP = 10;

	/** constitue les ressources panier pour les individus */
	public static NSArray ressourceIndividus(NSArray individus) {
		NSMutableArray retour = new NSMutableArray();
		for (int i = 0; i < individus.count(); i++) {
			IndividuUlr currentIndividu = (IndividuUlr) individus.objectAtIndex(i);
			String libelle = currentIndividu.nomUsuel() + " " + currentIndividu.prenom();
			Object[] objects = { "PERSONNE", libelle, NSKeyValueCoding.NullValue, "1", currentIndividu };
			Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			NSDictionary ressource = new NSDictionary(objects, keys);
			retour.addObject(ressource);
		}
		return retour;
	}

	/** constitue les ressources panier pour les groupes */
	public static NSArray ressourceGroupes(NSArray groupes) {
		NSMutableArray retour = new NSMutableArray();
		for (int i = 0; i < groupes.count(); i++) {
			StructuresAutorisees currentStructure = (StructuresAutorisees) groupes.objectAtIndex(i);
			String libelle = currentStructure.libelleGroupe();
			Object[] objects = { "GROUPE", libelle, NSKeyValueCoding.NullValue, "GROUPE", currentStructure };
			Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			NSDictionary ressource = new NSDictionary(objects, keys);
			retour.addObject(ressource);
		}
		return retour;
	}

	/** constitue les ressources panier pour les salles */
	public static NSArray<NSDictionary<String, Object>> ressourceSalles(NSArray<Salles> salles) {
		NSMutableArray<NSDictionary<String, Object>> retour = new NSMutableArray<NSDictionary<String, Object>>();
		for (int i = 0; i < salles.count(); i++) {
			Salles currentSalle = salles.objectAtIndex(i);
			String libelle = currentSalle.salPorte() + " - " + currentSalle.local().appellation();
			Object[] objects = { "SALLE", libelle, NSKeyValueCoding.NullValue, "1", currentSalle };
			String[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			NSDictionary<String, Object> ressource = new NSDictionary<String, Object>(objects, keys);
			retour.addObject(ressource);
		}
		return retour;
	}

	/** constitue les ressources panier pour les salles */
	public static NSArray ressourceChoix(NSArray salles) {
		NSMutableArray retour = new NSMutableArray();
		for (int i = 0; i < salles.count(); i++) {
			Salles currentSalle = (Salles) salles.objectAtIndex(i);
			String libelle = currentSalle.salPorte() + " - " + currentSalle.local().appellation();
			Object[] objects = { "CHOIX", libelle, NSKeyValueCoding.NullValue, "1", currentSalle };
			Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			NSDictionary ressource = new NSDictionary(objects, keys);
			retour.addObject(ressource);
		}
		return retour;
	}

	/** constitue les ressources panier pour les salles */
	public static NSArray ressourceObjets(NSArray objets) {
		NSMutableArray retour = new NSMutableArray();
		for (int i = 0; i < objets.count(); i++) {
			ResaObjet currentObjet = (ResaObjet) objets.objectAtIndex(i);
			String libelle = currentObjet.roLibelle1();
			Object[] objects = { "OBJET", libelle, NSKeyValueCoding.NullValue, "1", currentObjet };
			Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			NSDictionary ressource = new NSDictionary(objects, keys);
			retour.addObject(ressource);
		}
		return retour;
	}

	/** constitue les ressources panier pour les AP dans le mode inspecteur : resa deja existante */
	public static NSArray ressourceApsInspection(NSArray resAps) {
		NSMutableArray retour = new NSMutableArray();
		ReservationAp currentResAp = null;
		MaquetteAp currentAp = null;
		ScolGroupeGrp grp = null;
		String libelle = null;

		for (int i = 0; i < resAps.count(); i++) {
			currentResAp = (ReservationAp) resAps.objectAtIndex(i);
			currentAp = currentResAp.maquetteAp();
			grp = currentResAp.scolGroupeGrp();

			NSDictionary ressource;

			if (currentAp != null) {
				libelle = currentAp.toString();
				if (grp != null) {
					Object[] objects = { "ENSEIGNEMENT", libelle, NSKeyValueCoding.NullValue, grp, currentAp };
					Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
					ressource = new NSDictionary(objects, keys);
				}
				else {
					Object[] objects = { "ENSEIGNEMENT", libelle, NSKeyValueCoding.NullValue, "(Tous)", currentAp };
					Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
					ressource = new NSDictionary(objects, keys);
				}

				retour.addObject(ressource);
			}
		}
		return retour;
	}

	public static NSArray ressourceSemetresGroupesInspection(NSArray reservationsSemestres) {
		NSMutableArray retour = new NSMutableArray();
		ReservationSemestre currentResSem = null;
		MaquetteSemestre currentSem = null;
		ScolGroupeGrp grp = null;
		String libelle = null;

		for (int i = 0; i < reservationsSemestres.count(); i++) {
			currentResSem = (ReservationSemestre) reservationsSemestres.objectAtIndex(i);
			currentSem = currentResSem.semestre();
			grp = currentResSem.scolGroupeGrp();

			System.out.println("currentResSem=" + currentResSem);
			System.out.println("grp=" + grp);

			NSDictionary ressource;
			if (currentSem != null) {
				libelle = currentSem.toString();
				if (grp != null) {
					Object[] objects = { "SEMESTRE", libelle, NSKeyValueCoding.NullValue, grp, currentSem };
					Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
					ressource = new NSDictionary(objects, keys);
				}
				else {
					Object[] objects = { "SEMESTRE", libelle, NSKeyValueCoding.NullValue, "(TOUS)", currentSem };
					Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
					ressource = new NSDictionary(objects, keys);
				}

				retour.addObject(ressource);
			}
		}
		System.out.println("ressourceSemetresGroupesInspection=" + retour);
		return retour;
	}

	public static NSArray resourcesExamens(NSArray resaExams) {
		NSMutableArray retour = new NSMutableArray();
		ResaExamen resExam = null;
		ExamenEntete eentete = null;
		ScolGroupeGrp grp = null;
		String libelle = null;
		for (int i = 0; i < resaExams.count(); i++) {
			resExam = (ResaExamen) resaExams.objectAtIndex(i);
			eentete = resExam.examenEntete();
			grp = resExam.scolGroupeGrp();
			libelle = eentete.eentLibelle();
			NSDictionary ressource;
			if (grp != null) {
				Object[] objects = { "EXAMEN", libelle, NSKeyValueCoding.NullValue, grp, eentete };
				Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
				ressource = new NSDictionary(objects, keys);
			}
			else {
				Object[] objects = { "EXAMEN", libelle, NSKeyValueCoding.NullValue, "(Tous)", eentete };
				Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
				ressource = new NSDictionary(objects, keys);
			}

			retour.addObject(ressource);
		}
		return retour;
	}

	/** constitue les ressources panier pour les AP */
	public static NSArray ressourceAp(NSArray aps) {
		NSMutableArray retour = new NSMutableArray();
		for (int i = 0; i < aps.count(); i++) {
			MaquetteAp currentAp = (MaquetteAp) aps.objectAtIndex(i);
			String libelle = currentAp.mapLibelle();
			Object[] objects = { "ENSEIGNEMENT", libelle, NSKeyValueCoding.NullValue, "(Tous)", currentAp };
			Object[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
			NSDictionary ressource = new NSDictionary(objects, keys);
			retour.addObject(ressource);
		}
		return retour;
	}

	/** renvoi la ressource selectionnee */
	public static NSArray ressourcesFromRecords(NSArray ressources, int type) {

		if (type == PERSONNE) {
			return ressourceIndividus(ressources);
		}

		if (type == GROUPE) {
			return ressourceGroupes(ressources);
		}

		if (type == EXAMEN) {
			return resourcesExamens(ressources);
		}

		if (type == ENSEIGN_AP) {
			return ressourceAp(ressources);
		}

		if (type == ENSEIGNEMENT_INSP) {
			return ressourceApsInspection(ressources);
		}

		if (type == SALLE) {
			return ressourceSalles(ressources);
		}

		if (type == CHOIX) {
			return ressourceChoix(ressources);
		}

		if (type == OBJET) {
			return ressourceObjets(ressources);
		}

		if (type == SEMESTRE_GRP_INSP) {
			return ressourceSemetresGroupesInspection(ressources);
		}

		return null;
	}

}
