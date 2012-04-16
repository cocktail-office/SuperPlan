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

import org.cocktail.fwkcktlwebapp.common.database.CktlUserInfoDB;
import org.cocktail.superplan.server.metier.DepositaireSalles;
import org.cocktail.superplan.server.metier.Gardien;
import org.cocktail.superplan.server.metier.GardienSalle;
import org.cocktail.superplan.server.metier.IndividuUlr;
import org.cocktail.superplan.server.metier.PrefUser;
import org.cocktail.superplan.server.metier.RepartStructure;
import org.cocktail.superplan.server.metier.ResaObjet;
import org.cocktail.superplan.server.metier.ResaObjetDepositaire;
import org.cocktail.superplan.server.metier.Salles;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;

import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.TextHandler;

public class MailHandler {

	private Application application;
	private EOEditingContext eContext;
	private IndividuUlr agent = null;

	public MailHandler(Application application, Session session) {
		this.application = application;
		eContext = session.defaultEditingContext();
		agent = session.agent();
	}

	/** recupere les emails des depositaires des salles donnees en parametre */
	/**
	 * @param idSalles
	 * @param pourSemaineEnCours
	 *            Indique si le mail qui va être envoyé concerne une création/modification/suppression de résa dans la semaine en cours
	 * @return
	 */
	NSArray<String> mailsDespositairesSalles(NSArray<EOGlobalID> idSalles, boolean pourSemaineEnCours) {
		NSMutableArray<String> mails = new NSMutableArray<String>();
		for (int i = 0; i < idSalles.count(); i++) {
			EOGlobalID currentID = idSalles.objectAtIndex(i);
			Salles currentSalle = (Salles) eContext.faultForGlobalID(currentID, eContext);
			mails.addObjectsFromArray(emailDepositairesSalle(currentSalle, pourSemaineEnCours));
		}
		return mails;
	}

	NSArray<String> mailsDespositairesObjets(NSArray<EOGlobalID> idObjets) {
		NSMutableArray<String> mails = new NSMutableArray<String>();
		for (int i = 0; i < idObjets.count(); i++) {
			EOGlobalID currentID = idObjets.objectAtIndex(i);
			ResaObjet currentObjet = (ResaObjet) eContext.faultForGlobalID(currentID, eContext);
			mails.addObjectsFromArray(emailDepositairesObjet(currentObjet));
		}
		return mails;
	}

	/** renvoie la liste des emails des depositaires de la salle */
	/**
	 * @param salle
	 * @param pourSemaineEnCours
	 *            Si faux, le mail ne concerne pas une résa dans la semaine en cours, donc certains depositaires peuvent ne pas vouloir
	 *            recevoir ce mail (si leur préférence demande de ne recevoir les mails que pour les modifs dans la semaine en cours)
	 * @return
	 */
	NSArray<String> emailDepositairesSalle(Salles salle, boolean pourSemaineEnCours) {
		CktlUserInfoDB lrUserInfo = new CktlUserInfoDB(application.dataBus());
		EOQualifier qDepos = new EOKeyValueQualifier(DepositaireSalles.SALLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, salle);
		NSArray<DepositaireSalles> depositaires = DBHandler.fetchData(eContext, DepositaireSalles.ENTITY_NAME, qDepos);
		NSMutableArray<String> listMails = new NSMutableArray<String>();

		for (int i = 0; i < depositaires.count(); i++) {
			DepositaireSalles currentDepos = depositaires.objectAtIndex(i);

			String cStructure = currentDepos.cStructure();
			EOQualifier qRepart = EOQualifier.qualifierWithQualifierFormat(RepartStructure.C_STRUCTURE_KEY + " = '" + cStructure + "'", null);

			NSArray<RepartStructure> reparts = DBHandler.fetchData(eContext, RepartStructure.ENTITY_NAME, qRepart);
			for (int j = 0; j < reparts.count(); j++) {
				Number persId = (reparts.objectAtIndex(j)).persId();
				try {
					// si pourSemaineEnCours false, regarde d'abord si cet agent veut recevoir les mails des modifs hors semaine en
					// cours
					if (!pourSemaineEnCours) {
						IndividuUlr individu = (IndividuUlr) (reparts.objectAtIndex(j)).individuUlr().objectAtIndex(0);
						PrefUser rec = (PrefUser) DBHandler.fetchUniqueData(eContext, PrefUser.ENTITY_NAME, PrefUser.INDIVIDU_KEY, individu);
						if (rec != null) {
							if (rec.pasMailHorsSemaine() != null && rec.pasMailHorsSemaine().intValue() == 1) {
								continue;
							}
						}
					}
				}
				catch (Exception e) {
				}

				// if (agent == null || agent.persId().intValue() != persId.intValue()) {
				lrUserInfo.compteForPersId(persId, false);
				String mail = lrUserInfo.email();
				if (mail != null && TextHandler.isEmailValid(mail)) {
					listMails.addObject(mail);
				}
				else {
					NSLog.out.appendln("@email non valide : " + mail + " persID = " + persId);
				}
				// }
			}
		}
		return listMails;
	}

	NSArray<String> emailDepositairesObjet(ResaObjet objet) {
		CktlUserInfoDB lrUserInfo = new CktlUserInfoDB(application.dataBus());
		EOQualifier qualifier = new EOKeyValueQualifier(ResaObjetDepositaire.RESA_OBJET_KEY, EOKeyValueQualifier.QualifierOperatorEqual, objet);
		NSArray<ResaObjetDepositaire> depositaires = ResaObjetDepositaire.fetchResaObjetDepositaires(eContext, qualifier, null);
		NSMutableArray<String> listeMails = new NSMutableArray<String>();

		for (int i = 0; i < depositaires.count(); i++) {
			ResaObjetDepositaire currentDepos = depositaires.objectAtIndex(i);
			NSArray<RepartStructure> reparts = currentDepos.repartStructures();
			for (int j = 0; j < reparts.count(); j++) {
				Number persId = reparts.objectAtIndex(j).persId();
				if (agent == null || agent.persId().intValue() != persId.intValue()) {
					lrUserInfo.compteForPersId(persId, false);
					String mail = lrUserInfo.email();
					if (mail != null && TextHandler.isEmailValid(mail)) {
						listeMails.addObject(mail);
					}
					else {
						NSLog.out.appendln("@email non valide : " + mail + " persID = " + persId);
					}
				}
			}
		}
		return listeMails;
	}

	/** */
	NSDictionary emailGardienSalle(EOGlobalID idSalle) {
		try {

			Salles currentSalle = (Salles) eContext.faultForGlobalID(idSalle, eContext);
			CktlUserInfoDB lrUserInfo = new CktlUserInfoDB(application.dataBus());
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(GardienSalle.SALLE_KEY + " = %@", new NSArray(currentSalle));
			NSArray depositaires = DBHandler.fetchData(eContext, GardienSalle.ENTITY_NAME, qualifier);
			NSArray arrayRepartStruct = (NSArray) depositaires.valueForKey(GardienSalle.REPART_STRUCTURES_KEY);
			NSMutableArray gardienSalle = new NSMutableArray();
			RepartStructure currentRepart;
			for (int i = 0; i < arrayRepartStruct.count(); i++) {
				NSArray currentArrayRepart = (NSArray) arrayRepartStruct.objectAtIndex(i);
				for (int j = 0; j < currentArrayRepart.count(); j++) {
					currentRepart = (RepartStructure) currentArrayRepart.objectAtIndex(j);
					lrUserInfo.compteForPersId(currentRepart.persId(), false);
					gardienSalle.addObject(lrUserInfo.email());
				}
			}

			qualifier = EOQualifier.qualifierWithQualifierFormat(Gardien.LOCAL_KEY + " = %@", new NSArray(currentSalle.local()));
			depositaires = DBHandler.fetchData(eContext, Gardien.ENTITY_NAME, qualifier);

			NSMutableArray gardienBatiment = new NSMutableArray();
			for (int i = 0; i < depositaires.count(); i++) {
				Gardien currentGardien = (Gardien) depositaires.objectAtIndex(i);
				NSArray reparts = currentGardien.repartStructures();
				for (int j = 0; j < reparts.count(); j++) {
					currentRepart = (RepartStructure) reparts.objectAtIndex(j);
					lrUserInfo.compteForPersId(currentRepart.persId(), false);
					gardienBatiment.addObject(lrUserInfo.email());
				}

			}

			return new NSDictionary(new Object[] { gardienSalle, gardienBatiment }, new Object[] { "gardienSalle", "gardienBatiment" });
		}
		catch (Throwable th) {
			NSLog.out.appendln("ERREUR:" + th.getMessage());
			th.printStackTrace();
			return null;
		}
	}

}
