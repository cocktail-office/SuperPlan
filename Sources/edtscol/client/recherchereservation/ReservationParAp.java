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
package edtscol.client.recherchereservation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationDiplome;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.FormationSpecialisation;
import org.cocktail.superplan.client.metier.HoraireCode;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteParcours;
import org.cocktail.superplan.client.metier.MaquetteRepartitionSem;
import org.cocktail.superplan.client.metier.MaquetteSemestre;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.PrefScol;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ScolDroitDiplome;
import org.cocktail.superplan.client.metier.VParcoursAp;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EODialogController;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.eointerface.swing.EOTextField;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import edtscol.client.MainInterface;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.FileHandler;
import fr.univlr.utilities.Matrix;
import fr.univlr.utilities.TimeHandler;

/**
 * Controle de recherche de reservation par ap MaquetteAp, c'est une premiere version, permet d'exporter les resultats dans un fichier excel
 * -clientSideResquest- TODO : permettre la recherche en designant une date de debut et une date de fin en option de la recherche sur tout
 * le semestre -MaquetteSemestre-
 */

public class ReservationParAp extends EOInterfaceController {

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	public JButton bFermer, bInspecter, bRechCreneau;
	public EODisplayGroup eodHabilitation, eodPeriodes, eodOccupants;
	public EOTable tableHabilitation, tablePeriodes, tableOccupants;
	public EOTextField tGrade, tDiplome;
	public JComboBox comboSemestre, comboAnnees, comboParcours, comboAp, comboHoraireCode;
	public JTextField tReserve, tSeuil;
	public JTextField labelMapValeur, labelMapGroupePrevu;
	public EOMatrix matPrefDip;

	private TimeHandler timeHandler;

	private EOEditingContext eContext;

	private EOSortOrdering sortDiplome, sortNiveau;

	public ReservationParAp(EOEditingContext eContext, MainInterface main) {
		super(eContext);
		this.eContext = eContext;
		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());
	}

	protected void componentDidBecomeVisible() {
		this.afficherPreferences();
	}

	protected void controllerDidLoadArchive(NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		this.init();
		this.initWidgets();
		JComboListener comboListener = new JComboListener();
		comboSemestre.addActionListener(comboListener);
		comboParcours.addActionListener(comboListener);
		comboAp.addActionListener(comboListener);
		comboHoraireCode.addActionListener(comboListener);

		sortDiplome = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
				+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		sortNiveau = EOSortOrdering.sortOrderingWithKey(FormationHabilitation.FHAB_NIVEAU_KEY, EOSortOrdering.CompareAscending);

		eodPeriodes.setDelegate(this);

	}

	public void displayGroupDidChangeSelection(EODisplayGroup group) {
		if (group == eodPeriodes) {
			try {
				NSDictionary period = (NSDictionary) eodPeriodes.selectedObject();
				if (period != null) {
					EOGenericRecord resa = ((Periodicite) period.valueForKey("periodicite")).reservation();
					EOQualifier qualifOcc = DBHandler.getSimpleQualifier(Occupant.RESERVATION_KEY, resa);
					DBHandler.fetchDisplayGroup(eodOccupants, qualifOcc);
				}
				else {
					eodOccupants.setObjectArray(null);
				}
			}
			catch (Exception e) {
				NSLog.out.appendln("ReservationParAp" + e.getMessage());
				rechercherCreneau(this);
			}
		}
		if (group == eodHabilitation) {
			clean();
			FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
			if (selectedHabilitation != null && selectedHabilitation.formationSpecialisation() != null) {
				this.afficherParcours(selectedHabilitation.formationSpecialisation());
				PrefScol prefScol = getPrefScolForHabilitation(selectedHabilitation);
				if (prefScol != null && prefScol.maquetteRepartitionSem() != null) {
					comboParcours.setSelectedItem(prefScol.maquetteRepartitionSem().parcours());
					comboSemestre.setSelectedItem(prefScol.maquetteRepartitionSem().semestre());
				}
			}
		}
	}

	private PrefScol getPrefScolForHabilitation(FormationHabilitation habilitation) {
		if (habilitation == null) {
			return null;
		}
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>(2);
		quals.addObject(new EOKeyValueQualifier(PrefScol.INDIVIDU_KEY, EOKeyValueQualifier.QualifierOperatorEqual, app.userInfo("individu")));
		quals.addObject(new EOKeyValueQualifier(PrefScol.ANNEE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, comboAnnees.getSelectedItem()));
		NSArray<PrefScol> prefScols = habilitation.prefScols(new EOAndQualifier(quals));
		if (prefScols != null && prefScols.count() > 0) {
			return prefScols.objectAtIndex(0);
		}
		return null;
	}

	private void init() {
		NSArray formationAnnee = app.getFormationAnnees();
		comboAnnees.removeAllItems();
		for (int i = 0; i < formationAnnee.count(); i++) {
			FormationAnnee fAnnee = (FormationAnnee) formationAnnee.objectAtIndex(i);
			comboAnnees.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnees.setSelectedItem(fAnnee);
			}
		}

		Matrix.setSelectedIndex(0, matPrefDip);
		Matrix.setListener(new PrefMatrixListener(), matPrefDip);
	}

	private void afficherParcours(FormationSpecialisation specialisation) {
		comboParcours.removeAllItems();

		EOQualifier qParcours = EOQualifier.qualifierWithQualifierFormat(MaquetteParcours.FORMATION_SPECIALISATION_KEY + " = %@", new NSArray(
				specialisation));

		EOSortOrdering sortParcours = EOSortOrdering.sortOrderingWithKey(MaquetteParcours.MPAR_LIBELLE_KEY,
				EOSortOrdering.CompareCaseInsensitiveAscending);

		NSArray parcours = MaquetteParcours.fetchMaquetteParcourses(eContext, qParcours, new NSArray(sortParcours));
		for (int i = 0; i < parcours.count(); i++) {
			comboParcours.addItem(parcours.objectAtIndex(i));
		}

		afficherSemestres();
	}

	public void rechercherAp(Object sender) {

		WindowHandler.setWaitCursor(this);

		eodHabilitation.setDelegate(null);

		String grade = tGrade.getText();
		String diplome = tDiplome.getText();

		NSMutableArray sumQualifiers = new NSMutableArray();

		if (!diplome.equals("")) {
			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY + " caseInsensitiveLike '*"
					+ diplome + "*'" + " or " + FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FDIP_ABREVIATION_KEY + " caseInsensitiveLike '*"
					+ diplome + "*'", null));
		}

		if (!grade.equals("")) {
			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FORMATION_SPECIALISATION_KEY + "."
					+ FormationSpecialisation.SCOL_FORMATION_DIPLOME_KEY + "." + FormationDiplome.FGRA_CODE_KEY + " caseInsensitiveLike '*" + grade
					+ "*'", null));
		}

		sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.FANN_KEY_KEY + " = %@", new NSArray(
				((FormationAnnee) comboAnnees.getSelectedItem()).fannKey())));

		// si edtCreateur : peut voir tous les diplomes
		boolean edtCreateur = ((Boolean) app.userInfo("edtCreateur")).booleanValue();
		NSArray droits = (NSArray) app.userInfo("droits");
		if (!edtCreateur && droits.count() > 0) {
			EOQualifier qualDroits = EOQualifier.qualifierWithQualifierFormat(FormationHabilitation.DROIT_DIPLOMES_KEY + "."
					+ ScolDroitDiplome.DLOG_KEY_KEY + " = %@", new NSArray(droits.objectAtIndex(0)));
			sumQualifiers.addObject(qualDroits);
		}

		eodHabilitation.setSortOrderings(new NSArray(new Object[] { sortDiplome, sortNiveau }));
		EOQualifier totalQualifier = new EOAndQualifier(sumQualifiers);

		DBHandler.fetchDisplayGroup(eodHabilitation, totalQualifier, null, false);
		WidgetHandler.selectNoneInDisplayGroup(eodHabilitation);
		eodHabilitation.setDelegate(this);

		if (eodHabilitation.displayedObjects().count() == 0) {
			bRechCreneau.setEnabled(false);
			clean();
		}

		WindowHandler.setDefaultCursor(this);
	}

	/** permet d'afficher les semestres du parcours actuellement affiche */
	private void afficherSemestres() {

		WindowHandler.setWaitCursor(this);

		FormationHabilitation selectedHabilitation = (FormationHabilitation) eodHabilitation.selectedObject();
		Object parcours = comboParcours.getSelectedItem();

		if (selectedHabilitation == null || parcours == null) {
			return;
		}

		int niveau = selectedHabilitation.fhabNiveau().intValue();
		int ordre1 = niveau * 2 - 1;
		int ordre2 = ordre1 + 1;

		FormationAnnee fAnnee = (FormationAnnee) comboAnnees.getSelectedItem();

		EOQualifier qualSem = EOQualifier.qualifierWithQualifierFormat("(" + MaquetteRepartitionSem.SEMESTRE_KEY + "."
				+ MaquetteSemestre.MSEM_ORDRE_KEY + " = %@ or " + MaquetteRepartitionSem.SEMESTRE_KEY + "." + MaquetteSemestre.MSEM_ORDRE_KEY
				+ " = %@) and " + MaquetteRepartitionSem.PARCOURS_KEY + " = %@ and " + MaquetteRepartitionSem.FANN_KEY_KEY + " = %@", new NSArray(
				new Object[] { new Integer(ordre1), new Integer(ordre2), parcours, fAnnee.fannKey() }));

		NSArray semestres = (NSArray) MaquetteRepartitionSem.fetchMaquetteRepartitionSems(eContext, qualSem, null).valueForKey(
				MaquetteRepartitionSem.SEMESTRE_KEY);
		comboSemestre.removeAllItems();
		for (int i = 0; i < semestres.count(); i++) {
			comboSemestre.addItem(semestres.objectAtIndex(i));
		}

		this.afficherAps();
		WindowHandler.setDefaultCursor(this);
	}

	/** afficherAps */
	public void afficherAps() {
		FormationAnnee selectedAnnee = (FormationAnnee) comboAnnees.getSelectedItem();
		Number fannKey = selectedAnnee.fannKey();
		MaquetteSemestre selectedSemestre = (MaquetteSemestre) comboSemestre.getSelectedItem();

		NSArray aps = new NSArray();

		if (selectedAnnee != null && selectedSemestre != null) {

			String mhcoCode = null;
			HoraireCode selection = (HoraireCode) comboHoraireCode.getSelectedItem();
			if (selection != null) {
				mhcoCode = selection.mhcoCode();
			}

			NSMutableArray sumQualifiers = new NSMutableArray();
			if (mhcoCode != null) {
				sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(MaquetteAp.MHCO_CODE_KEY + " = '" + mhcoCode + "'", null));
			}

			sumQualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.FANN_KEY_KEY
					+ " = %@ and " + MaquetteAp.V_PARCOURS_APS_KEY + "." + VParcoursAp.SEMESTRE_KEY + " = %@", new NSArray(new Object[] { fannKey,
					selectedSemestre })));

			EOQualifier qualAp = new EOAndQualifier(sumQualifiers);

			aps = MaquetteAp.fetchMaquetteAps(eContext, qualAp, null);
		}
		else {
			return;
		}

		comboAp.removeAllItems();
		for (int i = 0; i < aps.count(); i++) {
			comboAp.addItem(aps.objectAtIndex(i));
		}
		if (comboAp.getSelectedItem() != null) {
			bRechCreneau.setEnabled(true);
		}

	}

	/** affiche les habilitations preferees */
	public void afficherPreferences() {
		if (Matrix.getSelectedIndex(matPrefDip) == 0) {
			eodHabilitation.setDelegate(null);
			eodHabilitation.setSortOrderings(new NSArray(new Object[] { sortDiplome, sortNiveau }));
			eodHabilitation.setObjectArray(app.getPreferencesHabilitations((FormationAnnee) comboAnnees.getSelectedItem()));
			WidgetHandler.selectNoneInDisplayGroup(eodHabilitation);
			eodHabilitation.setDelegate(this);
			if (eodHabilitation.displayedObjects().count() == 0) {
				bRechCreneau.setEnabled(false);
				clean();
			}
		}
		else {
			rechercherAp(this);
		}
	}

	public void rechercherCreneau(Object sender) {
		WindowHandler.setWaitCursor(this);

		MaquetteAp currentAp = (MaquetteAp) comboAp.getSelectedItem();

		if (currentAp == null) {
			return;
		}

		java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(Locale.FRENCH);
		numberFormat.setMaximumFractionDigits(2);
		double total = currentAp.mapValeur().doubleValue();
		Number grpPrevus = currentAp.mapGroupePrevu();
		if (grpPrevus != null) {
			tSeuil.setText(numberFormat.format(total * grpPrevus.doubleValue()));
		}
		else {
			tSeuil.setText(numberFormat.format(total));
		}
		labelMapValeur.setText(numberFormat.format(total));
		labelMapGroupePrevu.setText(numberFormat.format(grpPrevus.doubleValue()));

		EOQualifier qualAP = DBHandler.getSimpleQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.RESERVATION_AP_KEY + "."
				+ ReservationAp.MAQUETTE_AP_KEY, currentAp);

		EOSortOrdering sortDate = EOSortOrdering.sortOrderingWithKey("dateDeb", EOSortOrdering.CompareDescending);
		eodPeriodes.setSortOrderings(new NSArray(sortDate));
		WidgetHandler.flushDisplayGroup(eodPeriodes);

		NSArray periods = Periodicite.fetchPeriodicites(eContext, qualAP, null);
		for (int i = 0; i < periods.count(); i++) {
			Periodicite period = (Periodicite) periods.objectAtIndex(i);
			Number semaine = new Integer(timeHandler.weekOfYear(period.dateDeb()));

			NSDictionary dico = new NSDictionary(new Object[] { semaine, period.dateDeb(), period.dateFin(), period }, new Object[] { "semaine",
					"dateDeb", "dateFin", "periodicite" });
			eodPeriodes.insertObjectAtIndex(dico, 0);
		}
		eodPeriodes.updateDisplayedObjects();

		if (eodPeriodes.selectedObjects().count() > 0) {
			calculerTotaux();
			bInspecter.setEnabled(true);
		}
		else {
			bInspecter.setEnabled(false);
		}

		WindowHandler.setDefaultCursor(this);
	}

	/** calcul les totaux des heures deja reserves */
	public void calculerTotaux() {

		NSArray creneaux = eodPeriodes.displayedObjects();
		int cpt = 0;

		for (int i = 0; i < creneaux.count(); i++) {
			NSDictionary currentPeriod = (NSDictionary) creneaux.objectAtIndex(i);
			cpt += TimeHandler.minutesSeparatingDates((NSTimestamp) currentPeriod.valueForKey("dateDeb"),
					(NSTimestamp) currentPeriod.valueForKey("dateFin"));
		}

		java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(Locale.FRENCH);
		numberFormat.setMaximumFractionDigits(2);

		tReserve.setText(numberFormat.format((cpt / 60.0)));
	}

	public void fermer(Object sender) {
		((EODialogController) supercontroller()).closeWindow();
	}

	public void inspecter(Object sender) {
		NSDictionary currentPeriode = (NSDictionary) eodPeriodes.selectedObject();
		if (currentPeriode != null) {
			Reservation resa = ((Periodicite) currentPeriode.valueForKey("periodicite")).reservation();
			NSDictionary dictio = new NSDictionary(resa, "reservation");
			NSNotificationCenter.defaultCenter().postNotification("inspecterReservation", dictio);
		}
	}

	/** faire une sortie xls des donnees affichees (les reservations de l'AP) */
	public void sortieXls() {

		NSDictionary currentPeriode = null;
		StringBuffer tmp = null;
		NSArray occupants = null;
		NSArray creneaux = eodPeriodes.displayedObjects();
		NSMutableArray data = new NSMutableArray();
		NSMutableDictionary dico = new NSMutableDictionary();

		MaquetteSemestre sem = (MaquetteSemestre) comboSemestre.getSelectedItem();

		FormationAnnee annee = (FormationAnnee) comboAnnees.getSelectedItem();
		FormationHabilitation hab = (FormationHabilitation) eodHabilitation.selectedObject();

		MaquetteParcours parcours = (MaquetteParcours) comboParcours.getSelectedItem();
		MaquetteAp ap = (MaquetteAp) comboAp.getSelectedItem();

		FormationSpecialisation spe = hab.formationSpecialisation();

		if (hab == null || spe == null || parcours == null || sem == null || ap == null) {
			return;
		}

		app.waitingHandler().setMessage("Cr\u00e9ation d'un fichier Excel en cours ...");

		tmp = new StringBuffer();
		dico.setObjectForKey(spe.scolFormationDiplome().fdipAbreviation(), "diplome");

		if (spe.fspnLibelle() != null) {
			dico.setObjectForKey(spe.fspnLibelle(), "specialite");
		}

		tmp = new StringBuffer();
		tmp.append(annee.fannDebut());
		tmp.append("/");
		tmp.append(annee.fannFin());
		dico.setObjectForKey(tmp.toString(), "annee");

		dico.setObjectForKey(parcours.mparLibelle(), "parcours");

		dico.setObjectForKey(sem.toString(), "semestre");
		dico.setObjectForKey(ap.mapLibelle(), "ap");

		dico.setObjectForKey(tReserve.getText(), "reserve");
		dico.setObjectForKey(tSeuil.getText(), "seuil");

		for (int i = 0; i < creneaux.count(); i++) {
			currentPeriode = (NSDictionary) creneaux.objectAtIndex(i);
			tmp = new StringBuffer();
			tmp.append(currentPeriode.objectForKey("semaine").toString());
			tmp.append("$");
			NSTimestamp date = (NSTimestamp) currentPeriode.objectForKey("dateDeb");
			tmp.append(FormatHandler.dateToStr(date, "%d/%m/%Y : %H:%M"));
			tmp.append("$");
			date = (NSTimestamp) currentPeriode.objectForKey("dateFin");
			tmp.append(FormatHandler.dateToStr(date, "%d/%m/%Y : %H:%M"));
			tmp.append("$");
			occupants = ((Periodicite) currentPeriode.valueForKey("periodicite")).reservation().occupants();
			if (occupants.count() > 0) {
				for (int j = 0; j < occupants.count(); j++) {
					Occupant currentOcc = (Occupant) occupants.objectAtIndex(j);
					tmp.append(currentOcc.individu().nomPrenom());
					if (j != 0 && (j < occupants.count() - 1)) {
						tmp.append(" - ");
					}
				}
			}
			else {
				tmp.append("NC");
			}
			data.addObject(tmp.toString());
		}

		dico.setObjectForKey(data, "data");

		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSData excelData = (NSData) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestExelReservationParAp",
				new Class[] { NSDictionary.class }, new Object[] { dico }, false);

		FileHandler fileHandler = new FileHandler();
		if (excelData != null && fileHandler != null) {
			String fname = "ListeCreneaux";
			try {
				String filePath = fileHandler.dataToXXX(excelData, fname, "xls");
				fileHandler.openFile(filePath);
			}
			catch (Exception e) {
				app.waitingHandler().close();
				WindowHandler.showError(e.getMessage());
			}
		}
		else {
			WindowHandler.showError("Erreur de generation du fichier");
		}
		app.waitingHandler().close();
	}

	private void clean() {
		tSeuil.setText("");
		tReserve.setText("");
		comboParcours.removeAllItems();
		comboSemestre.removeAllItems();
		comboAp.removeAllItems();
		eodOccupants.setObjectArray(null);
		eodPeriodes.setObjectArray(null);
	}

	/** initialise les composants graphiques de la fenetre */
	public void initWidgets() {

		comboSemestre.removeAllItems();
		comboAp.removeAllItems();
		comboParcours.removeAllItems();
		comboHoraireCode.removeAllItems();
		NSArray horaireCodes = app.getHoraireCodes();
		for (int i = 0; i < horaireCodes.count(); i++) {
			comboHoraireCode.addItem(horaireCodes.objectAtIndex(i));
		}

		bRechCreneau.setEnabled(false);
		bInspecter.setEnabled(false);
		tReserve.setEditable(false);
		tReserve.setBackground(new Color(0xD3D3D3));

		tSeuil.setEditable(false);
		tSeuil.setBackground(new Color(0xD3D3D3));

		WidgetHandler.decorateButton(null, "Rechercher les creneaux", "find", bRechCreneau);
		WidgetHandler.decorateButton(null, "Inspecter", "info", bInspecter);
		WidgetHandler.decorateButton(null, "Fermer", "cancel", bFermer);

		WidgetHandler.setTableNotEditable(tableHabilitation);
		WidgetHandler.setTableNotEditable(tablePeriodes);
		WidgetHandler.setTableNotEditable(tableOccupants);
	}

	private class JComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == comboParcours) {
				afficherSemestres();
			}
			else
				if (e.getSource() == comboSemestre) {
					afficherAps();
				}
				else
					if (e.getSource() == comboHoraireCode) {
						afficherAps();
					}
		}
	}

	/** class d'ecoute du groupe de radioboutons de changement de recherche depositaire/tous */
	private class PrefMatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			afficherPreferences();
		}
	}

}
