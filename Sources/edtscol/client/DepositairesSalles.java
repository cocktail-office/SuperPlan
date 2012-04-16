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
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.factory.ReunionFactory;
import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.metier.DepositaireSalles;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.SalleSouhaitee;
import org.cocktail.superplan.client.metier.Salles;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.panier.GestionPanier;
import edtscol.client.recherche.SalleLibreController;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;

public class DepositairesSalles extends EOInterfaceController {

	private static final int RECHERCHE = 1;
	private static final int VALIDATION = 2;
	private static final int MODIFICATION = 3;
	private static final int VIDE = 4;

	private static final int RES_SALLE = 10;
	private static final int RES_CHOIX = 11;

	private static final int VALID_SALLE = 20;
	private static final int MODIF_SALLE = 21;
	private int mode = 0;
	private int type;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EOEditingContext eContext;
	public JComboBox comboLocal, comboAnnee;
	public EODisplayGroup eodResa, eodSalle, eodRessource, eodPeriodicites;
	public EOTable tablePeriodiciteSalle, tableSalle, tableRessource, tablePeriodicites;
	public JButton bValider, bModifier, bRefuser, bRechercher;
	public EOMatrix matOccupants;
	public JLabel lblAide;
	public JTextField tSemaine;

	private int heureFermeture;
	private boolean mailMandatory = false;
	private boolean canSendMail = true;

	private EnseignementFactory ensFactory;
	private int noSemaine = app.noSemaine();

	NSArray personnes = null;
	NSArray salles = null;
	NSArray choixSalles = null;
	NSArray objets = null;
	NSArray enseignement = null;

	public DepositairesSalles(EOEditingContext eContext) {
		super(eContext);
		this.eContext = editingContext();
		ensFactory = new EnseignementFactory(eContext);
	}

	/** initialisations au chargement de l'interface */
	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		initWidgets();
		eodResa.setSelectsFirstObjectAfterFetch(false);

		NSArray tmp = app.getFormationAnnees();
		comboAnnee.removeAllItems();
		for (int i = 0; i < tmp.count(); i++) {
			FormationAnnee fAnnee = (FormationAnnee) tmp.objectAtIndex(i);
			comboAnnee.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnee.setSelectedItem(fAnnee);
			}
		}

		eodRessource.setDelegate(this);
		tSemaine.setText(String.valueOf(noSemaine));
		chargerPourSemaine();
		// chargerReservations();
		eodResa.setDelegate(this);
		String hFerm = app.grhumParametre("EDT_HEURE_FERMETURE");
		if (hFerm == null) {
			heureFermeture = 21;
		}
		else {
			heureFermeture = (new Integer(hFerm)).intValue();
		}

	}

	/** callback affichage de la fenetre */
	protected void componentDidBecomeVisible() {
		matOccupants.setVisible(false);
		EtatValidationCellRenderer evRenderer = new EtatValidationCellRenderer();
		evRenderer.connectTable(tablePeriodiciteSalle);
		eodResa.willChange();
	}

	public void chargerPourSemaine() {

		if (!tSemaine.getText().equals("")) {
			int semaine = FormatHandler.strToInt(tSemaine.getText());
			if (semaine < 0 || semaine > 53) {
				WindowHandler.showError("Ce numero de semaine n'est pas valable");
				return;
			}
			FormationAnnee fAnnee = (FormationAnnee) comboAnnee.getSelectedItem();
			boolean anneeCivile = ((Boolean) app.userInfo("anneeCivile")).booleanValue();
			int annee = TimeHandler.getYearForWeek(fAnnee, semaine, anneeCivile);
			// CM modif pour avoir 8 semaines de reservations depositaire a partir du dimanche
			NSTimestamp[] jDebut = TimeHandler.getBeginAndEndOfWeek(semaine - 1, annee);
			semaine = semaine + 7;
			if (semaine > 53) {
				semaine = 53;
			}
			NSTimestamp[] jFin = TimeHandler.getBeginAndEndOfWeek(semaine, annee);
			chargerReservations(jDebut[1], jFin[0]);
		}
		else {
			chargerReservations(null, null);
		}
	}

	/** charge les reservations du depositaire */
	public void chargerReservations(NSTimestamp debut, NSTimestamp fin) {
		app.waitingHandler().setMessage("Chargement des reservations qui restent a valider au cours des 8 prochaines semaines");
		app.waitingHandler().setIntro("Depositaire : ");
		// CM pour vider le displayGroup
		eodResa.setObjectArray(null);

		SalleFactory salleFactory = new SalleFactory(eContext);
		NSArray repartDepositaires = salleFactory.structuresDepositaire((IndividuUlr) app.userInfo("individu"));

		NSArray structDepos = (NSArray) repartDepositaires.valueForKey(RepartStructure.STRUCTURE_ULR_KEY);

		NSMutableArray sum = new NSMutableArray();
		for (int i = 0; i < structDepos.count(); i++) {
			sum.addObject(EOQualifier.qualifierWithQualifierFormat(DepositaireSalles.STRUCTURE_ULR_KEY + " = %@",
					new NSArray(structDepos.objectAtIndex(i))));
		}
		EOQualifier sumQualifier = new EOOrQualifier(sum);

		NSArray depositairesSalles = DBHandler.fetchData(eContext, DepositaireSalles.ENTITY_NAME, sumQualifier);

		sum = new NSMutableArray();
		NSMutableArray sumChoix = new NSMutableArray();
		for (int i = 0; i < depositairesSalles.count(); i++) {
			Salles currentSalle = ((DepositaireSalles) depositairesSalles.objectAtIndex(i)).salle();

			sum.addObject(EOQualifier.qualifierWithQualifierFormat(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY + " = %@", new NSArray(
					currentSalle)));
			sumChoix.addObject(EOQualifier.qualifierWithQualifierFormat(Reservation.SALLES_SOUHAITEES_KEY + "." + SalleSouhaitee.SALLE_KEY + " = %@",
					new NSArray(currentSalle)));
		}

		sumQualifier = new EOOrQualifier(sum);
		EOQualifier qualDate = null;
		if (debut != null && fin != null) {
			qualDate = EOQualifier.qualifierWithQualifierFormat(Reservation.PERIODICITES_KEY + "." + Periodicite.DATE_DEB_KEY + " < %@ and "
					+ Reservation.PERIODICITES_KEY + "." + Periodicite.DATE_FIN_KEY + " > %@", new NSArray(new Object[] { fin, debut }));
		}

		NSMutableArray qualifiers = new NSMutableArray();
		qualifiers.addObject(sumQualifier);
		if (qualDate != null) {
			qualifiers.addObject(qualDate);
		}

		qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Reservation.RESA_SALLES_KEY + "." + ResaSalles.RESA_SAL_ETAT_KEY + " = 'N'",
				null));
		EOQualifier qResaSalles = new EOAndQualifier(qualifiers);
		// CM tri par date de modification
		EOSortOrdering sortDate = EOSortOrdering.sortOrderingWithKey(Reservation.D_MODIFICATION_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);
		NSArray resas = DBHandler.fetchData(eContext, Reservation.ENTITY_NAME, qResaSalles, sortDate);

		// on charge les reservations non validees dont l'utilisateur est depositaire de leurs salles.
		for (int i = 0; i < resas.count(); i++) {
			this.afficherReservation((Reservation) resas.objectAtIndex(i));
		}

		// les salles aux choix
		qualifiers = new NSMutableArray();
		qualifiers.addObject(new EOOrQualifier(sumChoix));
		if (qualDate != null) {
			qualifiers.addObject(qualDate);
		}

		qResaSalles = new EOAndQualifier(qualifiers);

		resas = DBHandler.fetchData(eContext, Reservation.ENTITY_NAME, qResaSalles);

		// on charge les reservations non validees dont l'utilisateur est depositaire de leurs salles.
		for (int i = 0; i < resas.count(); i++) {
			this.afficherReservation((Reservation) resas.objectAtIndex(i));
		}

		// force l'affichage de la premiere ligne au debut.
		displayGroupDidChangeSelection(eodResa);
		app.waitingHandler().close();
	}

	/** affiche la reservation dans le panneau des depositaires */
	private void afficherReservation(Reservation reservation) {
		if (reservation == null) {
			return;
		}
		try {
			String type = reservation.typeLocation().tlocLibelle();
			String commentaire = reservation.resaCommentaire();
			String nomAgent = reservation.individuAgent().nomPrenom();
			if (type == null) {
				type = "";
			}
			if (commentaire == null) {
				commentaire = "";
			}
			if (nomAgent == null) {
				nomAgent = "Inconnu : contacter Service Info";
			}

			Object[] keys = { "type", "agent", "commentaire", "reservation", "etatValidation" };
			Object[] contents = { type, nomAgent, commentaire, reservation, Boolean.FALSE };

			eodResa.insertObjectAtIndex(new NSMutableDictionary(contents, keys), 0);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** callback delegate */
	public void displayGroupDidChangeSelection(EODisplayGroup group) {
		if ((group == eodResa) && (eodResa.selectedObject() != null)) {
			NSKeyValueCoding currentCreneau = (NSKeyValueCoding) eodResa.selectedObject();
			this.afficherRessources(currentCreneau);
		}

		if (group == eodRessource) {
			NSKeyValueCoding ressource = (NSKeyValueCoding) eodRessource.selectedObject();
			if (ressource == null) {
				return;
			}
			String resType = (String) ressource.valueForKey("resType");
			if (resType.equals("SALLE") || resType.equals("CHOIX")) {
				mode = VALIDATION;
				type = ((resType.equals("SALLE")) ? RES_SALLE : RES_CHOIX);
				changeState();
			}
			else {
				mode = VIDE;
				changeState();
			}
		}
	}

	private void changeState() {
		if (mode == VALIDATION) {
			bValider.setEnabled(true);
			// bModifier.setEnabled(false);
			bRefuser.setEnabled(true);
			bRechercher.setEnabled(true);
		}
		if (mode == VIDE) {
			bValider.setEnabled(false);
			// bModifier.setEnabled(false);
			bRefuser.setEnabled(false);
			bRechercher.setEnabled(false);
		}
		if (mode == RECHERCHE) {
			bValider.setEnabled(false);
			bModifier.setEnabled(true);
			// bRefuser.setEnabled(true);
			bRechercher.setEnabled(false);
			// comboLocal.setEnabled(true);
		}
		if (mode == MODIFICATION) {
			bValider.setEnabled(false);
			// bModifier.setEnabled(false);
			bRefuser.setEnabled(true);
			bRechercher.setEnabled(true);
			// comboLocal.setEnabled(false);
		}
	}

	/** affiche les ressources du creneau selectionne */
	private void afficherRessources(NSKeyValueCoding creneau) {
		eodRessource.setObjectArray(new NSArray());
		eodPeriodicites.setObjectArray(new NSArray());

		Reservation reservation = (Reservation) creneau.valueForKey("reservation");
		NSArray occupants = (NSArray) reservation.valueForKeyPath(Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY);
		NSArray salles = (NSArray) reservation.valueForKeyPath(Reservation.RESA_SALLES_KEY + "." + ResaSalles.SALLE_KEY);
		NSArray sallesSouhaitees = (NSArray) reservation.valueForKeyPath(Reservation.SALLES_SOUHAITEES_KEY + "." + SalleSouhaitee.SALLE_KEY);

		NSArray enseignements = (NSArray) reservation.valueForKeyPath(Reservation.RESERVATION_AP_KEY + "." + ReservationAp.MAQUETTE_AP_KEY);
		addResources(GestionPanier.ressourcesFromRecords(occupants, GestionPanier.PERSONNE), false);
		addResources(GestionPanier.ressourcesFromRecords(enseignements, GestionPanier.ENSEIGN_AP), false);
		addResources(GestionPanier.ressourcesFromRecords(sallesSouhaitees, GestionPanier.CHOIX), false);
		addResources(GestionPanier.ressourcesFromRecords(salles, GestionPanier.SALLE), true);

		NSArray periods = reservation.periodicites();
		NSMutableArray __periodicites = new NSMutableArray();
		Periodicite period;
		NSTimestamp debut, fin;
		String jour;
		TimeHandler timeHandler;
		Number semaine;
		for (int i = 0; i < periods.count(); i++) {
			period = (Periodicite) periods.objectAtIndex(i);
			debut = period.dateDeb();
			fin = period.dateFin();
			jour = FormatHandler.dayString(debut);
			timeHandler = new TimeHandler();
			timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());

			semaine = new Integer(timeHandler.weekOfYear(debut));
			Object[] cles = { "jour", "date", "semaine", "heureDeb", "heureFin", "periodicite" };
			Object[] valeurs = { jour, debut, semaine, debut, fin, period };
			__periodicites.addObject(new NSDictionary(valeurs, cles));
		}
		try {
			eodPeriodicites.setObjectArray(__periodicites.sortedArrayUsingComparator(new PeriodicitesComparator()));
		}
		catch (NSComparator.ComparisonException e) {
			e.printStackTrace();
			WindowHandler.showError("Depositaire : Erreur recuperation dates");
		}
		eodPeriodicites.setSelectedObject(null);

	}

	/** insert les ressources passees dans le panier */
	public void addResources(NSArray ressources, boolean select) {
		for (int i = 0; i < ressources.count(); i++) {
			Object currentRes = ressources.objectAtIndex(i);
			eodRessource.insertObjectAtIndex(currentRes, 0);
			if (select) {
				eodRessource.setSelectedObject(currentRes);
			}
		}
		// eodRessource.updateDisplayedObjects();
	}

	public void validerSalle() {

		boolean modificationSucceed = true;
		NSKeyValueCoding ressource = (NSKeyValueCoding) eodRessource.selectedObject();
		String mailText = this.texteDescriptionRessources();

		NSKeyValueCoding currentCreneau = (NSKeyValueCoding) eodResa.selectedObject();
		Reservation reservation = (Reservation) currentCreneau.valueForKey("reservation");

		String chaineDates = datesReservation(reservation);
		String commentaire = reservation.resaCommentaire();

		StringBuffer sallesRemplacement = new StringBuffer();

		Salles salleReservee = null;

		if (type == RES_SALLE) {
			NSArray resaSalles = reservation.resaSalles();
			salleReservee = (Salles) ressource.valueForKey("resRecord");
			resaSalles.valueForKey(ResaSalles.SALLE_KEY);

			for (int i = 0; i < resaSalles.count(); i++) {
				ResaSalles currentResaSalle = (ResaSalles) resaSalles.objectAtIndex(i);
				Salles salle = currentResaSalle.salle();
				if (salleReservee == salle) {
					currentResaSalle.setResaSalEtat("O");
					sallesRemplacement.append("Salle " + salleReservee.salPorte() + " Batiment " + salleReservee.local().appellation());
					if (app.saveChanges()) {
						WindowHandler.showInfo("Salle definitivement affectee");
						eodRessource.deleteSelection();

						bValider.setEnabled(false);
					}
					else {
						modificationSucceed = false;
					}
				}
			}
		}

		if (type == RES_CHOIX) {
			NSArray sallesSouhaitees = reservation.sallesSouhaitees();
			salleReservee = (Salles) ressource.valueForKey("resRecord");

			for (int i = 0; i < sallesSouhaitees.count(); i++) {
				SalleSouhaitee currentSalleSouhaitee = (SalleSouhaitee) sallesSouhaitees.objectAtIndex(i);
				Salles salle = currentSalleSouhaitee.salle();
				if (salleReservee == salle) {

					ReunionFactory reunionFactory = new ReunionFactory(eContext);
					try {
						reservation.deleteAllSallesSouhaiteesRelationships();
						reunionFactory.affecterEtValiderSalles(reservation, new NSArray(salle), new NSTimestamp());
						sallesRemplacement.append("Salle N:" + salleReservee.salPorte() + " Bat.:" + salleReservee.local().appellation());
					}
					catch (EdtException e) {
						WindowHandler.showError(e.getMessage());
					}

					if (app.saveChanges()) {
						WindowHandler.showInfo("Salle definitivement affectee");
						eodRessource.deleteSelection();
						bValider.setEnabled(false);
					}
					else {
						modificationSucceed = false;
					}
				}
			}
		}

		if (salleReservee == null) {
			WindowHandler.showError("Probleme pour recuperer le mail du depositaire de salle");
			return;
		}

		NSDictionary dicoMails = getEmailPourSalle(salleReservee);
		NSArray mailGardien = (NSArray) dicoMails.objectForKey("gardienSalle");
		NSArray gardienBatiment = (NSArray) dicoMails.objectForKey("gardienBatiment");
		if (mailGardien.count() > 0) {
			mailMandatory = true;
		}
		if (mailGardien.count() == 0) {
			canSendMail = false;
		}

		String destinataires = mailGardien.componentsJoinedByString(",") + "," + gardienBatiment.componentsJoinedByString(",");

		// envoi de mail
		if (modificationSucceed) {
			eodRessource.setSelectedObject(ressource);
			eodRessource.deleteSelection();

			//
			((NSMutableDictionary) eodResa.selectedObject()).takeValueForKey(Boolean.TRUE, "etatValidation");
			eodResa.updateDisplayedObjects();

			String mailAgentCreateur = app.getEmailIndividu(reservation.individuAgent());
			if (mailAgentCreateur != null && !mailAgentCreateur.equals("")) {
				envoyerMail(VALID_SALLE, mailAgentCreateur, null, sallesRemplacement.toString(), chaineDates, mailText, commentaire, reservation);
			}

			if (canSendMail || mailMandatory) {
				envoyerMail(VALID_SALLE, destinataires, null, sallesRemplacement.toString(), chaineDates, mailText, commentaire, reservation);
			}

			eodPeriodicites.setObjectArray(new NSArray());
			eodResa.deleteSelection();
			displayGroupDidChangeSelection(eodResa);
		}
	}

	/** refuser une salle en la retirant de la reservation */
	public void refuserSalle() {

		NSKeyValueCoding ressource = (NSKeyValueCoding) eodRessource.selectedObject();
		NSKeyValueCoding currentCreneau = (NSKeyValueCoding) eodResa.selectedObject();
		Reservation reservation = (Reservation) currentCreneau.valueForKey("reservation");

		if (type == RES_SALLE) {
			NSArray resaSalles = reservation.resaSalles();
			Salles salleReservee = (Salles) ressource.valueForKey("resRecord");
			for (int i = 0; i < resaSalles.count(); i++) {
				ResaSalles currentResaSalle = (ResaSalles) resaSalles.objectAtIndex(i);
				Salles salle = currentResaSalle.salle();
				if (salleReservee == salle) {
					if (WindowHandler.showConfirmation("Confirmez vous la suppression de cette salle pour la r\u00e9servation ?")) {

						reservation.removeFromResaSallesRelationship(currentResaSalle);
						eContext.deleteObject(currentResaSalle);
						app.saveChanges();
						eodRessource.deleteSelection();
						eodRessource.updateDisplayedObjects();
						bValider.setEnabled(false);
						// CM refus par le depositaire
						StringBuffer mailText = new StringBuffer();
						StringBuffer html = new StringBuffer();
						String dest = app.getEmailIndividu(reservation.individuAgent());
						String exp = (String) app.userInfo("email");
						String sujet = "Annulation de r\u00e9servation de salle par le depositaire";
						mailText.append("Salle : " + salle.salPorte() + " Batiment : " + salle.local().appellation());
						html.append("<TABLE><TR><TD>Salle<TD><B>" + salle.salPorte() + " Batiment&nbsp;" + salle.local().appellation() + "</B>");
						NSArray aPer = reservation.periodicites();
						String heureDebut = "";
						String heureFin = "";
						if (aPer.count() > 0) {
							Periodicite period = (Periodicite) aPer.objectAtIndex(0);
							heureDebut = FormatHandler.dateToStr(period.dateDeb(), "%H:%M");
							heureFin = FormatHandler.dateToStr(period.dateFin(), "%H:%M");
							html.append("<TR><TD>Date<TD>" + FormatHandler.dateToStr(period.dateDeb(), "%A %d %B %Y"));
							heureDebut = heureDebut.replaceAll(":", "H");
							heureFin = heureFin.replaceAll(":", "H");
							html.append("<TR><TD>Horaire<TD>" + heureDebut + " &agrave; " + heureFin);
							mailText.append("\n\n" + FormatHandler.dateToStr(period.dateDeb(), "%A %d %B %Y"));
							mailText.append("\n Horaire " + heureDebut + " a " + heureFin);
						}
						html.append("</TABLE>");
						MailReservation mailRes = new MailReservation(app);
						mailRes.setModeSuperUser(true);
						mailRes.setMailInfos(mailText.toString(), sujet, dest, exp);
						mailRes.show();
					}
				}
			}
		}

		if (type == RES_CHOIX) {
			NSArray sallesSouhaitees = reservation.sallesSouhaitees();
			Salles salleReservee = (Salles) ressource.valueForKey("resRecord");

			for (int i = 0; i < sallesSouhaitees.count(); i++) {
				SalleSouhaitee currentSalleSouhaitee = (SalleSouhaitee) sallesSouhaitees.objectAtIndex(i);
				Salles salle = currentSalleSouhaitee.salle();
				if (salleReservee == salle) {

					reservation.removeFromSallesSouhaiteesRelationship(currentSalleSouhaitee);
					eContext.deleteObject(currentSalleSouhaitee);
					// verifier et reaffecter la priorite
					if (app.saveChanges()) {
						WindowHandler.showInfo("Salle retiree des choix de salles exprimes");
					}
				}
			}
		}
	}

	private NSDictionary getEmailPourSalle(Salles salle) {
		if (salle == null) {
			return null;
		}

		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSDictionary emails = (NSDictionary) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestEmailGardienSalle",
				new Class[] { EOGlobalID.class }, new Object[] { eContext.globalIDForObject(salle) }, false);
		return emails;
	}

	private String datesReservation(Reservation reservation) {
		NSArray periodicites = reservation.periodicites();
		StringBuffer stDate = new StringBuffer();
		TimeHandler timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());
		java.util.Hashtable periods = timeHandler.decomputePeriodicites(periodicites);

		NSArray elems = NSArray.componentsSeparatedByString((String) periods.get("heureFin"), ":");
		Integer heure = new Integer((String) elems.objectAtIndex(0));

		if (heure.intValue() >= heureFermeture) {
			canSendMail = true;
			return "";
		}
		else {
			canSendMail = false;
		}

		stDate.append("Semaine(s) : " + periods.get("semaines"));
		stDate.append("\n");
		stDate.append(FormatHandler.intToDay(((Integer) periods.get("jour")).intValue()));
		stDate.append("\nde " + periods.get("heureDeb"));
		stDate.append("\u00e0 " + periods.get("heureFin") + "\n");
		return stDate.toString();
	}

	public void modifierSalle() {
		mode = MODIFICATION;
		changeState();
	}

	private void remplacerSalle(Salles salleDeRemplacement) {
		boolean modificationSucceed = true;

		StringBuffer sallesRetirees = new StringBuffer();
		StringBuffer sallesRemplacement = new StringBuffer();

		NSKeyValueCoding ressource = (NSKeyValueCoding) eodRessource.selectedObject();

		String mailText = this.texteDescriptionRessources();

		NSKeyValueCoding currentCreneau = (NSKeyValueCoding) eodResa.selectedObject();
		Reservation reservation = (Reservation) currentCreneau.valueForKey("reservation");

		// verification si la salle est libre...
		ReunionFactory reunionFactory = new ReunionFactory(eContext);
		NSMutableArray pers = new NSMutableArray();
		NSArray periodicitesResa = reservation.periodicites();
		for (int k = 0; k < periodicitesResa.count(); k++) {
			pers.addObject(((Periodicite) periodicitesResa.objectAtIndex(k)).dateDeb());
			pers.addObject(((Periodicite) periodicitesResa.objectAtIndex(k)).dateFin());
		}
		NSArray dispoSalle = null;

		try {
			dispoSalle = reunionFactory.getNonDisponibiliteSalle(pers, salleDeRemplacement, null, null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (dispoSalle == null) {
			WindowHandler.showError("Erreur sur le serveur");
			return;
		}
		if (dispoSalle.count() > 0) {
			NSTimestamp d1 = (NSTimestamp) dispoSalle.objectAtIndex(0);
			NSTimestamp d2 = (NSTimestamp) dispoSalle.objectAtIndex(1);
			String msg = FormatHandler.dateToStr(d1, "d/%m/%Y %H:%M") + " et " + FormatHandler.dateToStr(d2, " d/%m/%Y %H:%M");
			WindowHandler.showError("La salle " + salleDeRemplacement.salPorte() + " n'est pas libre entre " + msg);
			return;
		}

		NSDictionary dicoMails = getEmailPourSalle(salleDeRemplacement);
		NSArray mailGardien = (NSArray) dicoMails.objectForKey("gardienSalle");
		NSArray gardienBatiment = (NSArray) dicoMails.objectForKey("gardienBatiment");

		if (mailGardien.count() > 0) {
			mailMandatory = true;
		}
		System.out.println("mailGardien " + mailGardien.count());
		if (mailGardien.count() == 0) {
			// System.out.println("canSendMail a faux " + mailGardien.count());
			canSendMail = false;
		}
		StringBuffer destinataires = new StringBuffer();
		if (mailGardien.count() > 0) {
			destinataires.append(mailGardien.componentsJoinedByString(",") + ",");
		}
		if (gardienBatiment.count() > 0) {
			destinataires.append(gardienBatiment.componentsJoinedByString(","));
		}

		// String chaineDates = this.datesReservation(reservation);
		String chaineDates = datesReservation(reservation);
		String commentaire = reservation.resaCommentaire();

		if (type == RES_SALLE) {
			NSArray<ResaSalles> resaSalles = reservation.resaSalles();

			Salles salleReservee = (Salles) ressource.valueForKey("resRecord");

			for (int i = 0; i < resaSalles.count(); i++) {
				ResaSalles currentResaSalle = resaSalles.objectAtIndex(i);
				Salles salle = currentResaSalle.salle();
				if (salleReservee == salle) {

					currentResaSalle.removeObjectFromBothSidesOfRelationshipWithKey(salle, "salle");
					currentResaSalle.addObjectToBothSidesOfRelationshipWithKey(salleDeRemplacement, "salle");
					currentResaSalle.setResaSalEtat("O");
					sallesRetirees.append("Salle " + salle.salPorte() + " Batiment :" + salle.local().appellation() + "\n");
					sallesRemplacement.append("Salle " + salleDeRemplacement.salPorte() + " Batiment :" + salleDeRemplacement.local().appellation()
							+ "\n");

					if (app.saveChanges()) {
						WindowHandler.showInfo("Salle definitivement affectee");
						bValider.setEnabled(false);
					}
					else {
						modificationSucceed = false;
					}
				}
			}
		}

		if (type == RES_CHOIX) {
			NSArray<SalleSouhaitee> sallesSouhaitees = reservation.sallesSouhaitees();
			Salles salleReservee = (Salles) ressource.valueForKey("resRecord");

			for (int i = 0; i < sallesSouhaitees.count(); i++) {
				SalleSouhaitee currentSalleSouhaitee = sallesSouhaitees.objectAtIndex(i);
				Salles salle = currentSalleSouhaitee.salle();
				if (salleReservee == salle) {
					sallesRetirees.append("Choix Salle :" + salle.salPorte() + " Batiment " + salle.local().appellation() + "\n");
					reunionFactory = new ReunionFactory(eContext);

					try {
						reservation.deleteAllSallesSouhaiteesRelationships();
						reunionFactory.affecterEtValiderSalles(reservation, new NSArray<Salles>(salleDeRemplacement), new NSTimestamp());
						sallesRemplacement.append("Salle :" + salleDeRemplacement.salPorte() + " Batiment "
								+ salleDeRemplacement.local().appellation() + "\n");
					}
					catch (EdtException e) {
						WindowHandler.showError(e.getMessage());
					}
					if (app.saveChanges()) {
						WindowHandler.showInfo("Salle definitivement affectee");
						bValider.setEnabled(false);
					}
					else {
						modificationSucceed = false;
					}
				}
			}
		}

		// envoi de mail
		if (modificationSucceed) {
			eodRessource.setSelectedObject(ressource);
			eodRessource.deleteSelection();

			((NSMutableDictionary) eodResa.selectedObject()).takeValueForKey(Boolean.TRUE, "etatValidation");
			eodResa.updateDisplayedObjects();

			// envoie de mail à l'agent qui a cree la reservation.
			String mailAgentCreateur = app.getEmailIndividu(reservation.individuAgent());
			this.envoyerMail(MODIF_SALLE, mailAgentCreateur, sallesRetirees.toString(), sallesRemplacement.toString(), chaineDates, mailText,
					commentaire, reservation);

			// CM pas de gardien
			// envoie de mail au gardien des batiments & salles.
			/*
			 * if(canSendMail || mailMandatory)
			 * this.envoyerMail(MODIF_SALLE,destinataires.toString(),sallesRetirees.toString(),sallesRemplacement
			 * .toString(),chaineDates,mailText,commentaire,reservation); else NSLog.out.appendln("Impossible d'envoyer mail");
			 */
		}

	}

	public void envoyerMail(int type, String dest, String sallesRetirees, String sallesRemplacement, String chaineDates, String textMail,
			String commentaire, Reservation reservation) {

		StringBuffer mailText = new StringBuffer();
		StringBuffer html = new StringBuffer();
		mailText.append("Reservation : \n");
		html.append("<HTML>");
		if (type == MODIF_SALLE) {
			html.append("<TABLE width=600><TR><TD>Nous vous informons de la modification de reservation de salle suivante :</TR></TABLE>");
			html.append("<TABLE width=600><TR><TD width=200>Salle retir&egrave;e<TD>" + sallesRetirees);
			html.append("</TABLE>");
			mailText.append(sallesRetirees);
			mailText.append("\n\nremplacees par :\n");
			html.append("<P>remplac&egrave;e par");
		}
		else
			if (type == VALID_SALLE) {
				html.append("<TABLE><TR><TD><FONT SIZE=3><B>Nous vous confirmons la r&eacute;servation de(s) salle(s) suivante(s) :</B></TR></TABLE>");
			}
		html.append("<TABLE width=600 FONT SIZE=3>");
		mailText.append(sallesRemplacement);
		html.append("<TR><TD width=200>Salle(s) r&eacute;serv&eacute;e(s)<TD>" + sallesRemplacement);
		html.append("</TABLE>");
		mailText.append("\nReservation initiale :\n");

		String mailHtml = this.texteDescriptionRessourcesHtml();
		html.append("<TABLE><TR><TD><B>R&eacute;servation(s) initiale(s) souhait&eacute;e(s)</B></TD></TABLE>");
		html.append("<TABLE>");
		html.append(mailHtml);
		mailText.append(textMail);
		mailText.append("\n\nPeriodicites :\n");
		NSArray aPer = reservation.periodicites();
		String heureDebut = "";
		String heureFin = "";
		if (aPer.count() > 0) {
			Periodicite period = (Periodicite) aPer.objectAtIndex(0);
			heureDebut = FormatHandler.dateToStr(period.dateDeb(), "%H:%M");
			heureFin = FormatHandler.dateToStr(period.dateFin(), "%H:%M");
			html.append("<TR><TD>Date<TD>" + FormatHandler.dateToStr(period.dateDeb(), "%A %d %B %Y"));
			heureDebut = heureDebut.replaceAll(":", "H");
			heureFin = heureFin.replaceAll(":", "H");
			html.append("<TR><TD>Horaire<TD>" + heureDebut + " &agrave; " + heureFin);
		}

		mailText.append(chaineDates);
		if (commentaire != null) {
			if (!commentaire.equals("")) {
				mailText.append("\nCommentaire :\n" + commentaire);
			}
		}
		html.append("</TABLE>");
		String sujet = "";
		switch (type) {
		case VALID_SALLE:
			sujet = "Validation reservation de salle par le depositaire";
			break;
		case MODIF_SALLE:
			sujet = "Validation et modification de reservation de salle par le depositaire";
			break;
		}

		String exp = (String) app.userInfo("email");
		MailReservation mailRes = new MailReservation(app);
		mailRes.setModeSuperUser(true);
		mailRes.setMailInfos(mailText.toString(), sujet, dest, exp);
		mailRes.show();
	}

	public void rechercherSalle() {

		mode = RECHERCHE;

		NSKeyValueCoding obj = (NSKeyValueCoding) eodResa.selectedObject();

		if (obj != null) {
			Reservation resa = (Reservation) obj.valueForKey("reservation");
			NSArray periods = resa.periodicites();
			NSMutableArray dates = new NSMutableArray();

			Periodicite aPeriod;
			for (int i = 0; i < periods.count(); i++) {
				aPeriod = (Periodicite) periods.objectAtIndex(i);
				dates.addObject(aPeriod.dateDeb());
				dates.addObject(aPeriod.dateFin());
			}

			if (dates.count() > 0) {
				SalleLibreController ctrl = new SalleLibreController(eContext);
				ctrl.setPeriodicitesRecherche(dates);
				ctrl.setModeDepositaire(true);
				WindowHandler.runModal(ctrl, "Recherche étendue de salle");

				NSArray sallesSelectionnees = ctrl.getSallesSelectionnees();
				if (sallesSelectionnees.count() == 0) {
					return;
				}

				remplacerSalle((Salles) sallesSelectionnees.objectAtIndex(0));
			}

		}

		changeState();
	}

	/** enumere textuellement les descriptions des ressources dans le panier */
	public String texteDescriptionRessources() {

		personnes = getResourcesWithType("PERSONNE");
		salles = getResourcesWithType("SALLE");
		choixSalles = getResourcesWithType("CHOIX");
		objets = getResourcesWithType("OBJET");
		enseignement = getResourcesWithType("ENSEIGNEMENT");

		StringBuffer ressourcesResa = new StringBuffer();

		ressourcesResa.append("Occupants :\n");
		for (int i = 0; i < personnes.count(); i++) {
			IndividuUlr currentIndividu = (IndividuUlr) personnes.objectAtIndex(i);
			ressourcesResa.append(currentIndividu.nomUsuel() + " " + currentIndividu.prenom() + "\n");
		}

		for (int i = 0; i < enseignement.count(); i++) {
			if (i == 0) {
				ressourcesResa.append("\n\nEnseignements :\n");
			}
			MaquetteAp currentEns = (MaquetteAp) enseignement.objectAtIndex(i);

			ressourcesResa.append(ensFactory.detailDiplomePourAp(currentEns));
			ressourcesResa.append("\n - AP : ");

			ressourcesResa.append(currentEns.mapLibelle() + "\n");
		}

		if (salles.count() > 0) {
			ressourcesResa.append("\n\nSalles :\n");
			for (int i = 0; i < salles.count(); i++) {
				Salles currentSalle = (Salles) salles.objectAtIndex(i);
				ressourcesResa.append(currentSalle.salPorte() + " Batiment :" + currentSalle.local().appellation());
			}
		}

		if (choixSalles.count() > 0) {
			ressourcesResa.append("\n\nSalles au choix :\n");
			for (int i = 0; i < choixSalles.count(); i++) {
				Salles currentSalle = (Salles) choixSalles.objectAtIndex(i);
				ressourcesResa.append(currentSalle.salPorte() + " Batiment :" + currentSalle.local().appellation());
			}
		}

		return ressourcesResa.toString();
	}

	/** enumere textuellement les descriptions des ressources dans le panier */
	public String texteDescriptionRessourcesHtml() {
		// CM en variables globales
		StringBuffer ressourcesResa = new StringBuffer();
		if (salles.count() > 0) {
			for (int i = 0; i < salles.count(); i++) {
				if (i == 0) {
					ressourcesResa.append("<TR><TD>Salle(s)<TD>");
				}
				else {
					ressourcesResa.append("<TR><TD><TD>");
				}
				Salles currentSalle = (Salles) salles.objectAtIndex(i);
				ressourcesResa.append("Salle&nbsp;" + currentSalle.salPorte() + " Batiment :&nbsp;" + currentSalle.local().appellation());
			}
		}

		if (choixSalles.count() > 0) {
			for (int i = 0; i < choixSalles.count(); i++) {
				if (i == 0) {
					ressourcesResa.append("<TR><TD>Salle(s) au choix<TD>");
				}
				else {
					ressourcesResa.append("<TR><TD><TD>");
				}
				Salles currentSalle = (Salles) choixSalles.objectAtIndex(i);
				ressourcesResa.append(currentSalle.salPorte() + " Batiment :&nbsp;" + currentSalle.local().appellation());
			}
		}
		for (int i = 0; i < personnes.count(); i++) {
			if (i == 0) {
				ressourcesResa.append("<TR><TD>Occupants(s)<TD>");
			}
			else {
				ressourcesResa.append("<TR><TD><TD>");
			}
			IndividuUlr currentIndividu = (IndividuUlr) personnes.objectAtIndex(i);
			ressourcesResa.append(currentIndividu.prenom() + " " + currentIndividu.nomUsuel());
		}
		for (int i = 0; i < objets.count(); i++) {
			if (i == 0) {
				ressourcesResa.append("<TR><TD>Mat&eacute;riels(s)<TD>");
			}
			else {
				ressourcesResa.append("<TR><TD><TD>");
			}
			ResaObjet currentObjet = (ResaObjet) objets.objectAtIndex(i);
			ressourcesResa.append(currentObjet.roLibelle1());
		}

		return ressourcesResa.toString();
	}

	/** retourne les ressources du type passe */
	public NSArray getResourcesWithType(String type) {
		eodRessource.setQualifier(EOQualifier.qualifierWithQualifierFormat("resType='" + type + "'", null));
		eodRessource.updateDisplayedObjects();
		NSArray objets = eodRessource.displayedObjects();
		eodRessource.setQualifier(null);
		eodRessource.updateDisplayedObjects();
		NSMutableArray arrayObj = new NSMutableArray();
		for (int i = 0; i < objets.count(); i++) {
			arrayObj.addObject(((NSKeyValueCoding) objets.objectAtIndex(i)).valueForKey("resRecord"));
		}
		return arrayObj;
	}

	private void initWidgets() {

		StringBuffer sAide = new StringBuffer();
		sAide.append("<html>");
		sAide.append("Selectionner une reservation \u00e0 valider, puis ");
		sAide.append("<br>");
		sAide.append("dans la table des ressources en dessous, selectionner");
		sAide.append("<br>");
		sAide.append("la salle et la valider ou la modifier");
		sAide.append("</html>");

		lblAide.setOpaque(true);
		lblAide.setBackground(new Color(0xE6E6FA));
		lblAide.setText(sAide.toString());
		lblAide.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		WidgetHandler.decorateButton("Rechercher d'autres salles a affecter", null, "find", bRechercher);
		WidgetHandler.decorateButton("Valider cette salle", null, "ok", bValider);
		WidgetHandler.decorateButton("Refuser cette salle", null, "clean", bRefuser);
		WidgetHandler.setTableNotEditable(tablePeriodiciteSalle);
		WidgetHandler.setTableNotEditable(tablePeriodicites);
		WidgetHandler.setTableNotEditable(tableRessource);
	}

	public class PeriodicitesComparator extends NSComparator {

		public int compare(Object object1, Object object2) throws NSComparator.ComparisonException {
			int semaine1 = ((Number) ((NSDictionary) object1).objectForKey("semaine")).intValue();
			int semaine2 = ((Number) ((NSDictionary) object2).objectForKey("semaine")).intValue();

			if (semaine1 > semaine2) {
				return NSComparator.OrderedDescending;
			}
			else {
				return NSComparator.OrderedAscending;
			}
		}
	}

	class EtatValidationCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 6416026720705960397L;

		public EtatValidationCellRenderer() {
			super();
		}

		public void connectTable(EOTable laTable) {
			int indexColone;
			for (indexColone = 0; indexColone < laTable.table().getColumnModel().getColumnCount(); indexColone++) {
				laTable.table().getColumnModel().getColumn(indexColone).setCellRenderer(this);
			}
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			Component leComposant = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			NSDictionary obj = (NSDictionary) eodResa.displayedObjects().objectAtIndex(row);

			if (((Boolean) obj.objectForKey("etatValidation")).booleanValue() == true) {
				leComposant.setBackground(Color.RED);
			}
			else
				if (((Boolean) obj.objectForKey("etatValidation")).booleanValue() == false) {
					leComposant.setBackground(new Color(0x3CB371));
				}

			if (isSelected) {
				leComposant.setBackground(Color.black);
				leComposant.setForeground(Color.white);
			}

			return leComposant;
		}

	}
}
