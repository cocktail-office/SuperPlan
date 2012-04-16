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

import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.metier.ExamenEntete;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.Salles;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eoapplication.EOModalDialogController;
import com.webobjects.eointerface.swing.EOTextArea;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;

public class MailReservation extends EOModalDialogController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	public JButton bEnvoyer;
	public EOTextArea taCommentaire, taMessage;
	public JTextField tCC, tDest, tSujet;
	private String expediteur, sujet, destinataire, message;

	public MailReservation(MainClient app) {
		super();
		this.app = app;
		EOArchive.loadArchiveNamed("MailReservation", this, "edtscol.client", this.disposableRegistry());
		establishConnection();
	}

	protected void componentDidBecomeVisible() {
		setWindowTitle(window(), "Envoyer un courriel");
		tCC.setBackground(ParamsHandler.C_BACKGRD_E);
		tSujet.setBackground(ParamsHandler.C_BACKGRD_NO);
		tDest.setBackground(ParamsHandler.C_BACKGRD_NO);
		taCommentaire.textComponent().setText("");
		tCC.setText("");
		taMessage.setEditable(false);
		tSujet.setEditable(false);
		tDest.setEditable(false);
		WidgetHandler.decorateButton("Envoyer le mail de réservation", "Envoyer", "mail", bEnvoyer);
		taMessage.setText(message);
		tSujet.setText(sujet);
		tDest.setText(destinataire);
	}

	public void setModeSuperUser(boolean mode) {
	}

	public void setMailInfos(NSDictionary dicoMail) {
		if (dicoMail == null) {
			return;
		}
		message = (String) dicoMail.objectForKey("texte");
		sujet = (String) dicoMail.objectForKey("sujet");
		if (StringCtrl.isEmpty(sujet)) {
			sujet = "Réservation EdT";
		}
		destinataire = (String) dicoMail.objectForKey("destinataire");
		expediteur = (String) dicoMail.objectForKey("expediteur");
	}

	public void setMailInfos(String message, String sujet, String destinataire, String expediteur) {
		try {
			this.message = message;
			this.sujet = StringCtrl.isEmpty(sujet) ? "Réservation EdT" : sujet;
			this.sujet = sujet;
			this.destinataire = destinataire;
			this.expediteur = expediteur;
		}
		catch (Exception e) {
			WindowHandler.showError("Impossible d'avertir par mail");
		}
	}

	public void close() {
		this.deactivateWindow();
	}

	public void annuler() {
		close();
	}

	public void show() {
		setWindowTitle(this.window(), "Mail de notification de réservation");
		this.activateWindow();
	}

	public void enregistrer() {
		String cc = tCC.getText();
		String msg = message;
		if (taCommentaire.textComponent().getText() != null && taCommentaire.textComponent().getText().length() > 0) {
			msg = msg + "\nRemarques : " + taCommentaire.textComponent().getText();
		}

		if (!StringCtrl.isEmpty(sujet) && !StringCtrl.isEmpty(msg.toString()) && !StringCtrl.isEmpty(expediteur) && !StringCtrl.isEmpty(destinataire)) {
			app.remoteSendMail(expediteur, destinataire, cc, sujet, msg.toString());
		}
		else {
			WindowHandler.showError("Envoi de mail impossible.\nIl manque un ou plusieurs paramètres.");
		}

	}

	public void valider() {
		enregistrer();
		close();
	}

	/* le callback fournis par EOSimpleWindowController lors de la fermeture de la fenetre */
	// CM pour empecher l'envoi
	public void windowClosing(WindowEvent e) {
		/*
		 * if(!sent) { if(!modeSuperUser) enregistrer(); } sent=false;
		 */
		close();
	}

	public String messageFromResources(Reservation resa, NSArray occupants, NSArray salles, NSArray choixSalles, NSArray enseignements,
			NSArray examens, NSArray subPeriods, IndividuUlr subAgent, boolean avecDates) {

		StringBuffer message = new StringBuffer();

		message.append("Agent : " + subAgent.nomUsuel() + " " + subAgent.prenom());
		message.append(MainClient.RETURN);
		message.append(MainClient.RETURN);

		if (avecDates) {
			NSArray<Periodicite> periodicites = resa.periodicites();
			String heureDebut = null;
			String heureFin = null;
			NSMutableArray<NSTimestamp> tblPeriods = new NSMutableArray<NSTimestamp>();
			if (subPeriods == null) {
				if (periodicites.count() > 0) {
					Periodicite period = periodicites.objectAtIndex(0);
					heureDebut = FormatHandler.dateToStr(period.dateDeb(), "%H:%M");
					heureFin = FormatHandler.dateToStr(period.dateFin(), "%H:%M");
				}

				for (int i = 0; i < periodicites.count(); i++) {
					Periodicite period = periodicites.objectAtIndex(i);
					tblPeriods.addObject(period.dateDeb());
					tblPeriods.addObject(period.dateFin());
				}
			}
			else {
				if (subPeriods.count() > 1) {
					heureDebut = FormatHandler.dateToStr((NSTimestamp) subPeriods.objectAtIndex(0), "%H:%M");
					heureFin = FormatHandler.dateToStr((NSTimestamp) subPeriods.objectAtIndex(1), "%H:%M");
				}
				tblPeriods.addObjectsFromArray(subPeriods);

			}

			message.append("Pour les dates :");
			for (int i = 0; i < tblPeriods.count(); i += 2) {
				message.append(MainClient.RETURN);
				message.append("   " + FormatHandler.dateToStr(tblPeriods.objectAtIndex(i)));
			}

			message.append(MainClient.RETURN);
			message.append("de " + heureDebut + " à " + heureFin);

			message.append(MainClient.RETURN);
			message.append(MainClient.RETURN);
		}

		if (salles != null) {
			message.append("Salle(s) :");
			for (int i = 0; i < salles.count(); i++) {
				Salles salle = (Salles) salles.objectAtIndex(i);
				message.append(MainClient.RETURN);
				message.append("   - No " + salle.salPorte() + " - Bat. " + salle.local().appellation() + "(Etage " + salle.salEtage() + ")");
			}
		}

		if (choixSalles != null) {
			if (choixSalles.count() > 0) {
				message.append(MainClient.RETURN);
				message.append(MainClient.RETURN);
				message.append("Salles au choix :");
				for (int i = 0; i < choixSalles.count(); i++) {
					Salles salle = (Salles) choixSalles.objectAtIndex(i);
					message.append(MainClient.RETURN);
					message.append("   - No " + salle.salPorte() + " - Bat. " + salle.local().appellation() + " (Etage " + salle.salEtage() + ")");
				}
			}
		}

		message.append(MainClient.RETURN);
		message.append(MainClient.RETURN);

		if (occupants != null) {
			message.append("Occupant(s) : ");
			for (int i = 0; i < occupants.count(); i++) {
				IndividuUlr occupant = (IndividuUlr) occupants.objectAtIndex(i);
				message.append(MainClient.RETURN);
				message.append("   - " + occupant.nomUsuel() + " " + occupant.prenom());
			}
		}

		message.append(MainClient.RETURN);
		message.append(MainClient.RETURN);

		if (enseignements != null) {
			int cnt = enseignements.count();
			if (cnt > 0) {
				message.append("Enseignement(s) : ");
			}
			for (int i = 0; i < enseignements.count(); i++) {
				MaquetteAp currentAP = (MaquetteAp) enseignements.objectAtIndex(i);

				// message.append(app.ensFactory().detailDiplomePourAp(currentAP));
				message.append(MainClient.RETURN);
				message.append("   - AP : ");
				message.append(currentAP.mapLibelle());
			}
		}

		if (examens != null) {
			int cnt = examens.count();
			if (cnt > 0) {
				message.append("Examen(s) : ");
			}
			for (int i = 0; i < examens.count(); i++) {
				Object objExamen = examens.objectAtIndex(i);
				ExamenEntete examEntete = null;
				if (objExamen instanceof ExamenEntete) {
					examEntete = (ExamenEntete) objExamen;
				}
				else {
					examEntete = (ExamenEntete) ((NSDictionary<String, Object>) objExamen).valueForKey("EXAM");
				}
				if (examEntete != null) {
					message.append(MainClient.RETURN);
					message.append("   - " + examEntete.eentLibelle());
				}
			}
		}

		String com = resa.resaCommentaire();
		if (com != null) {
			message.append(MainClient.RETURN);
			message.append(MainClient.RETURN);
			message.append("Commentaire : ");
			message.append(com);
		}

		return message.toString();
	}

}