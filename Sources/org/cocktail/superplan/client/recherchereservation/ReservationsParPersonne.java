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
package org.cocktail.superplan.client.recherchereservation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.MaquetteEc;
import org.cocktail.superplan.client.metier.Occupant;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.ResaExamen;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationAp;
import org.cocktail.superplan.client.metier.ScolGroupeGrp;
import org.cocktail.superplan.client.metier.TypeLocation;
import org.cocktail.superplan.client.swing.TableSorter;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTable.ZEOTableListener;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;
import org.cocktail.superplan.client.utilities.HeuresFormat;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedObjectStore;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.FileHandler;
import fr.univlr.utilities.TimeHandler;

public class ReservationsParPersonne extends javax.swing.JDialog {

	private static ReservationsParPersonne sharedInstance;
	private Component parent;
	private MainClient app = (MainClient) EOApplication.sharedApplication();
	public EODisplayGroup eodIndividus = new EODisplayGroup(), eodReservations = new EODisplayGroup();
	public ZEOTable tableIndividus, tableReservations;
	private EOEditingContext eContext;
	private TimeHandler timeHandler;

	/** Creates new form ReservationsParPersonne */
	public ReservationsParPersonne(Component parent, boolean modal, EOEditingContext eContext) {
		super(new javax.swing.JFrame(), modal);
		initComponents();
		this.parent = parent;
		this.eContext = eContext;
		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());
		init();
	}

	public static ReservationsParPersonne sharedInstance(Component parent, EOEditingContext editingContext) {
		if (sharedInstance == null) {
			sharedInstance = new ReservationsParPersonne(parent, false, editingContext);
		}
		return sharedInstance;
	}

	public void open() {
		setLocation(parent.getX() + 20, parent.getY() + 20);
		setVisible(true);
		tfRechercheNom.requestFocus();
	}

	private void init() {
		NSArray<FormationAnnee> tmp = app.getFormationAnnees();
		comboAnnee.removeAllItems();
		for (int i = 0; i < tmp.count(); i++) {
			FormationAnnee fAnnee = tmp.objectAtIndex(i);
			comboAnnee.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnee.setSelectedItem(fAnnee);
			}
		}
		comboAnnee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				chercherReservations();
			}
		});

		comboType.removeAllItems();
		NSArray<TypeLocation> types = TypeLocation.fetchTypeLocations(
				eContext,
				new EOKeyValueQualifier(TypeLocation.TLOC_APPLI_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual, "EDT"),
				new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(TypeLocation.TLOC_LIBELLE_KEY,
						EOSortOrdering.CompareCaseInsensitiveAscending)));
		comboType.addItem("(TOUT)");
		if (types != null) {
			for (int i = 0; i < types.count(); i++) {
				comboType.addItem(types.objectAtIndex(i));
			}
		}
		comboType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				chercherReservations();
			}
		});

		EOSortOrdering sortIndividus = EOSortOrdering.sortOrderingWithKey(IndividuUlr.NOM_PRENOM_KEY, EOSortOrdering.CompareAscending);
		eodIndividus.setSortOrderings(new NSArray<EOSortOrdering>(sortIndividus));
		RechercheIndividuListener myRechercheListener = new RechercheIndividuListener();
		tfRechercheNom.addActionListener(myRechercheListener);
		tfRecherchePrenom.addActionListener(myRechercheListener);
		rbRechercheEtudiants.addActionListener(myRechercheListener);
		rbRechercheNonEtudiants.addActionListener(myRechercheListener);
		boutonRecherchePersonnes.addActionListener(myRechercheListener);

		EOSortOrdering sortReservations = new EOSortOrdering(Periodicite.DATE_DEB_KEY, EOSortOrdering.CompareAscending);
		eodReservations.setSortOrderings(new NSArray<EOSortOrdering>(sortReservations));

		boutonInspecter.setEnabled(false);

		initTables();
		tableIndividus.addListener(new ListenerTableIndividus());
		tableReservations.addListener(new ListenerTableReservations());
	}

	private class ListenerTableIndividus implements ZEOTableListener {
		public void onSelectionChanged() {
			eodReservations.setObjectArray(null);
			tableReservations.updateData();
		}

		public void onDbClick() {
		}
	}

	private class ListenerTableReservations implements ZEOTableListener {
		public void onSelectionChanged() {
			if (eodReservations.selectedObject() != null) {
				NSDictionary<String, Object> dico = (NSDictionary<String, Object>) eodReservations.selectedObject();
				if (dico != null && dico.valueForKey("reservation") != null) {
					boutonInspecter.setEnabled(true);
					return;
				}
			}
			boutonInspecter.setEnabled(false);
		}

		public void onDbClick() {
		}
	}

	private class RechercheIndividuListener implements ActionListener {
		public RechercheIndividuListener() {
			super();
		}

		public void actionPerformed(ActionEvent anAction) {
			NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
			quals.addObject(new EOKeyValueQualifier(IndividuUlr.TEM_VALIDE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O"));
			if (StringCtrl.isEmpty(tfRechercheNom.getText()) == false) {
				quals.addObject(new EOKeyValueQualifier(IndividuUlr.NOM_USUEL_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*"
						+ tfRechercheNom.getText() + "*"));
			}
			if (StringCtrl.isEmpty(tfRecherchePrenom.getText()) == false) {
				quals.addObject(new EOKeyValueQualifier(IndividuUlr.PRENOM_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*"
						+ tfRecherchePrenom.getText() + "*"));
			}
			if (rbRechercheNonEtudiants.isSelected()) {
				quals.addObject(IndividuUlr.getQualifierForNonStudent());
			}
			else {
				quals.addObject(IndividuUlr.getQualifierForStudent());
			}
			eodIndividus.setObjectArray(IndividuUlr.fetchIndividuUlrs(eContext, new EOAndQualifier(quals), null));
			tableIndividus.updateData();
		}
	}

	private void initTables() {
		// individus...
		Vector<ZEOTableModelColumn> myColsIndividus = new Vector<ZEOTableModelColumn>();

		ZEOTableModelColumn col = new ZEOTableModelColumn(eodIndividus, IndividuUlr.NOM_PRENOM_KEY, "Identité", 200);
		col.setAlignment(SwingConstants.LEFT);
		myColsIndividus.add(col);
		col = new ZEOTableModelColumn(eodIndividus, IndividuUlr.IND_QUALITE_KEY, "Qualité", 100);
		col.setAlignment(SwingConstants.LEFT);
		myColsIndividus.add(col);

		ZEOTableModel myTableModelIntervenants = new ZEOTableModel(eodIndividus, myColsIndividus);
		TableSorter myTableSorterIntervenants = new TableSorter(myTableModelIntervenants);

		tableIndividus = new ZEOTable(myTableSorterIntervenants);
		myTableSorterIntervenants.setTableHeader(tableIndividus.getTableHeader());

		tableIndividus.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		viewTableIndividus.setLayout(new BorderLayout());
		viewTableIndividus.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableIndividus.removeAll();
		viewTableIndividus.add(new JScrollPane(tableIndividus), BorderLayout.CENTER);

		// reservations...
		Vector<ZEOTableModelColumn> myColsReservations = new Vector<ZEOTableModelColumn>();

		col = new ZEOTableModelColumn(eodReservations, "type", "Type", 30);
		col.setAlignment(SwingConstants.LEFT);
		myColsReservations.add(col);
		col = new ZEOTableModelColumn(eodReservations, "motif", "AP / Examen / Motif", 180);
		col.setAlignment(SwingConstants.LEFT);
		myColsReservations.add(col);
		col = new ZEOTableModelColumn(eodReservations, "semaine", "Semaine", 20);
		col.setAlignment(SwingConstants.CENTER);
		col.setColumnClass(Integer.class);
		myColsReservations.add(col);
		col = new ZEOTableModelColumn(eodReservations, "dateDeb", "Date", 50);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(NSTimestamp.class);
		col.setFormatDisplay(DateFormat.getDateInstance());
		myColsReservations.add(col);
		col = new ZEOTableModelColumn(eodReservations, "dateDeb", "Heure début", 40);
		col.setAlignment(SwingConstants.CENTER);
		col.setColumnClass(NSTimestamp.class);
		col.setFormatDisplay(DateFormat.getTimeInstance(DateFormat.SHORT));
		myColsReservations.add(col);
		col = new ZEOTableModelColumn(eodReservations, "dateFin", "Heure fin", 40);
		col.setAlignment(SwingConstants.CENTER);
		col.setColumnClass(NSTimestamp.class);
		col.setFormatDisplay(DateFormat.getTimeInstance(DateFormat.SHORT));
		myColsReservations.add(col);
		col = new ZEOTableModelColumn(eodReservations, "duree", "Durée", 20);
		col.setAlignment(SwingConstants.RIGHT);
		col.setColumnClass(BigDecimal.class);
		col.setFormatDisplay(new HeuresFormat());
		myColsReservations.add(col);

		ZEOTableModel myTableModelReservations = new ZEOTableModel(eodReservations, myColsReservations);
		TableSorter myTableSorterReservations = new TableSorter(myTableModelReservations);

		tableReservations = new ZEOTable(myTableSorterReservations);
		myTableSorterReservations.setTableHeader(tableReservations.getTableHeader());

		tableReservations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		viewTableReservations.setLayout(new BorderLayout());
		viewTableReservations.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableReservations.removeAll();
		viewTableReservations.add(new JScrollPane(tableReservations), BorderLayout.CENTER);
	}

	public void close() {
		setVisible(false);
	}

	public void chercherReservations() {
		eodReservations.setObjectArray(null);

		IndividuUlr selectedIndividu = (IndividuUlr) eodIndividus.selectedObject();
		Object selectedType = comboType.getSelectedItem();
		FormationAnnee fAnnee = (FormationAnnee) comboAnnee.getSelectedItem();

		if (selectedType == null || fAnnee == null || selectedIndividu == null) {
			return;
		}

		NSTimestamp[] bornesAnnee = MainClient.getDateRangeForAnneeUniv(fAnnee);

		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.OCCUPANTS_KEY + "." + Occupant.INDIVIDU_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, selectedIndividu));
		quals.addObject(new EOKeyValueQualifier(Periodicite.DATE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThan, bornesAnnee[0]));
		quals.addObject(new EOKeyValueQualifier(Periodicite.DATE_DEB_KEY, EOKeyValueQualifier.QualifierOperatorLessThan, bornesAnnee[1]));
		if (selectedType instanceof TypeLocation) {
			quals.addObject(new EOKeyValueQualifier(Periodicite.RESERVATION_KEY + "." + Reservation.TYPE_LOCATION_KEY,
					EOKeyValueQualifier.QualifierOperatorEqual, selectedType));
		}

		EOSortOrdering sortReservations = new EOSortOrdering(Periodicite.DATE_DEB_KEY, EOSortOrdering.CompareAscending);
		NSArray<Periodicite> periods = Periodicite.fetchPeriodicites(eContext, new EOAndQualifier(quals), new NSArray<EOSortOrdering>(
				sortReservations));

		NSMutableArray<NSDictionary<Object, Object>> resas = new NSMutableArray<NSDictionary<Object, Object>>();
		for (int i = 0; i < periods.count(); i++) {
			Periodicite period = periods.objectAtIndex(i);
			Reservation resa = period.reservation();

			String type = "NC";
			if (resa.typeLocation() != null) {
				type = resa.typeLocation().tlocLibelle();
			}

			String motif = resa.resaCommentaire();
			if (resa.tlocCode().equals("E")) {
				motif = construireMotifPourExamens(resa);
			}
			else {
				if (resa.isTypeReservationScol()) {
					motif = construireMotifPourReservationScol(resa);
				}
			}

			Number semaine = new Integer(timeHandler.weekOfYear(period.dateDeb()));
			BigDecimal duree = BigDecimal.valueOf(period.dateFin().getTime() - period.dateDeb().getTime());
			duree = duree.divide(new BigDecimal(3600000), BigDecimal.ROUND_HALF_UP);
			NSDictionary<Object, Object> disp = new NSDictionary<Object, Object>(new Object[] { semaine, period.dateDeb(), period.dateFin(), duree,
					motif, type, resa }, new Object[] { "semaine", "dateDeb", "dateFin", "duree", "motif", "type", "reservation" });
			resas.addObject(disp);
		}
		if (cbRegrouperParAps.isSelected() || cbRegrouperParEcs.isSelected()) {
			NSMutableArray<NSDictionary<Object, Object>> resasRegroupees = new NSMutableArray<NSDictionary<Object, Object>>();
			NSMutableDictionary<Object, BigDecimal> dureeApsOuEcs = new NSMutableDictionary<Object, BigDecimal>();
			for (int i = 0; i < resas.count(); i++) {
				NSDictionary<Object, Object> dico = resas.objectAtIndex(i);
				Reservation resa = (Reservation) dico.objectForKey("reservation");
				if (resa.reservationAp() == null || resa.reservationAp().count() == 0) {
					resasRegroupees.addObject(dico);
					continue;
				}
				NSMutableArray<Object> apOuEcDejaComptabilise = new NSMutableArray<Object>();
				for (int x = 0; x < resa.reservationAp().count(); x++) {
					Object apOuEc = ((ReservationAp) resa.reservationAp().objectAtIndex(x)).maquetteAp();
					if (cbRegrouperParEcs.isSelected()) {
						apOuEc = ((MaquetteAp) apOuEc).vScolMaquetteApEc().maquetteEc();
					}
					if (apOuEcDejaComptabilise.contains(apOuEc)) {
						continue;
					}
					BigDecimal duree = dureeApsOuEcs.objectForKey(apOuEc);
					if (duree == null) {
						duree = new BigDecimal(0);
					}
					duree = duree.add((BigDecimal) dico.objectForKey("duree"));
					dureeApsOuEcs.setObjectForKey(duree, apOuEc);
					apOuEcDejaComptabilise.addObject(apOuEc);
				}
			}
			Enumeration<Object> enumDureeAps = dureeApsOuEcs.keyEnumerator();
			while (enumDureeAps.hasMoreElements()) {
				Object obj = enumDureeAps.nextElement();
				if (obj instanceof MaquetteAp) {
					NSDictionary<Object, Object> disp = new NSDictionary<Object, Object>(new Object[] { dureeApsOuEcs.objectForKey(obj),
							((MaquetteAp) obj).toStringEcCode(), ((MaquetteAp) obj).mhcoCode() }, new Object[] { "duree", "motif", "type" });
					resasRegroupees.addObject(disp);
				}
				else {
					NSDictionary<Object, Object> disp = new NSDictionary<Object, Object>(new Object[] { dureeApsOuEcs.objectForKey(obj),
							((MaquetteEc) obj).toStringEcCode() }, new Object[] { "duree", "motif" });
					resasRegroupees.addObject(disp);
				}
			}
			eodReservations.setObjectArray(resasRegroupees);
		}
		else {
			eodReservations.setObjectArray(resas);
		}
		eodReservations.setSelectedObject(null);
		tableReservations.updateData();
		if (eodReservations.selectedObject() != null) {
			boutonInspecter.setEnabled(true);
		}
		else {
			boutonInspecter.setEnabled(false);
		}
	}

	private String construireMotifPourReservationScol(Reservation resa) {
		String motif;
		NSArray<ReservationAp> resaAps = resa.reservationAp();
		StringBuffer aps = new StringBuffer();
		MaquetteAp apPrecedent = null;
		ScolGroupeGrp grpPrecedent = null;
		for (int j = 0; j < resaAps.count(); j++) {
			MaquetteAp ap = resaAps.objectAtIndex(j).maquetteAp();
			if (apPrecedent == null || !apPrecedent.equals(ap)) {
				try {
					if (j > 0) {
						aps.append(" ; ");
					}
					aps.append(ap.vScolMaquetteApEc().maquetteEc().mecCode() + " - ");
				}
				catch (Exception e) {
				}
				aps.append(ap.mapLibelle());
				apPrecedent = ap;
			}
			ScolGroupeGrp grp = resaAps.objectAtIndex(j).scolGroupeGrp();
			if (grp != null) {
				if (grpPrecedent == null || !grpPrecedent.equals(grp)) {
					aps.append(" (");
					aps.append(grp.ggrpCode());
					aps.append(")");
					grpPrecedent = grp;
				}
			}
			if (j > 0 && j < resaAps.count() - 2) {
				aps.append("-");
			}
		}
		motif = aps.toString();
		return motif;
	}

	private String construireMotifPourExamens(Reservation resa) {
		String motif;
		NSArray<ResaExamen> resaExams = resa.resaExamens();
		StringBuffer exams = new StringBuffer();
		for (int j = 0; j < resaExams.count(); j++) {
			ResaExamen resaExam = resaExams.objectAtIndex(j);
			if (j > 0) {
				exams.append(" ; ");
			}
			exams.append(resaExam.examenEntete().ec().mecCode() + " - ");
			exams.append(resaExam.examenEntete().eentLibelle());
			if (resaExam.scolGroupeGrp() != null) {
				exams.append("/");
				exams.append(resaExam.scolGroupeGrp().ggrpCode());
			}
		}
		motif = exams.toString();
		return motif;
	}

	private String normalize(Object obj, String valueIfEmpty) {
		return (obj == null ? valueIfEmpty : StringCtrl.normalize(obj.toString(), valueIfEmpty));
	}

	public void exporter() {

		app.waitingHandler().setMessage("Création d'un fichier Excel en cours ...");

		NSMutableDictionary<String, Object> dico = new NSMutableDictionary<String, Object>();

		constitutionTableau(dico);
		exporterTableauEnExcel(dico);

		app.waitingHandler().close();
	}

	private void exporterTableauEnExcel(NSMutableDictionary<String, Object> dico) {
		EODistributedObjectStore objectStore = (EODistributedObjectStore) EOEditingContext.defaultParentObjectStore();
		NSData excelData = (NSData) objectStore.invokeRemoteMethodWithKeyPath(eContext, "session", "clientSideRequestExcelReservationParPersonne",
				new Class[] { NSDictionary.class }, new Object[] { dico }, false);

		FileHandler fileHandler = new FileHandler();
		if (excelData != null && fileHandler != null) {
			String fname = "ListeCreneauxIndividu";
			try {
				String filePath = fileHandler.dataToXXX(excelData, fname, "xls");
				fileHandler.openFile(filePath);
			}
			catch (Exception e) {
				WindowHandler.showError(e.getMessage());
			}
		}
		else {
			WindowHandler.showError("Erreur de generation du fichier");
		}
	}

	private void constitutionTableau(NSMutableDictionary<String, Object> dico) {

		NSMutableArray<NSArray<String>> data = new NSMutableArray<NSArray<String>>();

		FormationAnnee annee = (FormationAnnee) comboAnnee.getSelectedItem();
		IndividuUlr selectedIndividu = (IndividuUlr) eodIndividus.selectedObject();

		dico.takeValueForKey(annee.fannDebut() + " / " + annee.fannFin(), "annee");
		dico.takeValueForKey(selectedIndividu.nomUsuel() + " " + selectedIndividu.prenom(), "individu");

		for (int i = 0; i < eodReservations.displayedObjects().count(); i++) {
			NSDictionary<String, Object> disp = (NSDictionary<String, Object>) eodReservations.displayedObjects().objectAtIndex(i);

			NSMutableArray<String> ligneTableau = new NSMutableArray<String>();
			ligneTableau.addObject(normalize(disp.valueForKey("type"), "..."));
			ligneTableau.addObject(normalize(disp.valueForKey("motif"), "..."));
			ligneTableau.addObject(normalize(disp.valueForKey("semaine"), "..."));
			NSTimestamp dateDeb = (NSTimestamp) disp.valueForKey("dateDeb");
			if (dateDeb != null) {
				ligneTableau.addObject(FormatHandler.dateToStr(dateDeb, "%d/%m/%Y"));
				ligneTableau.addObject(FormatHandler.dateToStr(dateDeb, "%H:%M"));
			}
			else {
				ligneTableau.addObject("...");
				ligneTableau.addObject("...");
			}
			NSTimestamp dateFin = (NSTimestamp) disp.valueForKey("dateFin");
			if (dateFin != null) {
				ligneTableau.addObject(FormatHandler.dateToStr(dateFin, "%H:%M"));
			}
			else {
				ligneTableau.addObject("...");
			}
			BigDecimal duree = (BigDecimal) disp.valueForKey("duree");
			if (duree != null) {
				ligneTableau.addObject(new HeuresFormat().format(duree));
			}
			else {
				ligneTableau.addObject("...");
			}
			data.addObject(ligneTableau.immutableClone());
		}
		dico.takeValueForKey(data, "data");
	}

	public void inspecter() {
		NSDictionary<String, Object> dico = (NSDictionary<String, Object>) eodReservations.selectedObject();
		if (dico != null) {
			NSDictionary<String, Object> dictio = new NSDictionary<String, Object>(dico.valueForKey("reservation"), "reservation");
			NSNotificationCenter.defaultCenter().postNotification("inspecterReservation", dictio);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		jPanel6 = new javax.swing.JPanel();
		comboAnnee = new javax.swing.JComboBox();
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		tfRechercheNom = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		tfRecherchePrenom = new javax.swing.JTextField();
		rbRechercheNonEtudiants = new javax.swing.JRadioButton();
		rbRechercheEtudiants = new javax.swing.JRadioButton();
		boutonRecherchePersonnes = new javax.swing.JButton();
		viewTableIndividus = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		comboType = new javax.swing.JComboBox();
		boutonRechercheReservations = new javax.swing.JButton();
		cbRegrouperParAps = new javax.swing.JCheckBox();
		cbRegrouperParEcs = new javax.swing.JCheckBox();
		viewTableReservations = new javax.swing.JPanel();
		jPanel4 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		boutonExporter = new javax.swing.JButton();
		boutonInspecter = new javax.swing.JButton();
		jPanel5 = new javax.swing.JPanel();
		boutonFermer = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Recherche de réservations / personne");
		getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

		jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

		comboAnnee.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		comboAnnee.setPreferredSize(new java.awt.Dimension(120, 20));
		jPanel6.add(comboAnnee);

		getContentPane().add(jPanel6);

		jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
		jLabel1.setText("Recherche :");
		jPanel1.add(jLabel1);

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel2.setText("Nom");
		jLabel2.setPreferredSize(new java.awt.Dimension(40, 14));
		jPanel1.add(jLabel2);

		tfRechercheNom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		tfRechercheNom.setPreferredSize(new java.awt.Dimension(150, 20));
		jPanel1.add(tfRechercheNom);

		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel3.setText("Prénom");
		jLabel3.setPreferredSize(new java.awt.Dimension(45, 14));
		jPanel1.add(jLabel3);

		tfRecherchePrenom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		tfRecherchePrenom.setPreferredSize(new java.awt.Dimension(150, 20));
		jPanel1.add(tfRecherchePrenom);

		buttonGroup1.add(rbRechercheNonEtudiants);
		rbRechercheNonEtudiants.setSelected(true);
		rbRechercheNonEtudiants.setText("Non étudiants");
		jPanel1.add(rbRechercheNonEtudiants);

		buttonGroup1.add(rbRechercheEtudiants);
		rbRechercheEtudiants.setText("Etudiants");
		jPanel1.add(rbRechercheEtudiants);

		boutonRecherchePersonnes.setText("Chercher");
		jPanel1.add(boutonRecherchePersonnes);

		getContentPane().add(jPanel1);

		org.jdesktop.layout.GroupLayout viewTableIndividusLayout = new org.jdesktop.layout.GroupLayout(viewTableIndividus);
		viewTableIndividus.setLayout(viewTableIndividusLayout);
		viewTableIndividusLayout.setHorizontalGroup(viewTableIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 801,
				Short.MAX_VALUE));
		viewTableIndividusLayout.setVerticalGroup(viewTableIndividusLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 210,
				Short.MAX_VALUE));

		getContentPane().add(viewTableIndividus);

		jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel4.setText("Type :");
		jPanel2.add(jLabel4);

		comboType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
		comboType.setPreferredSize(new java.awt.Dimension(180, 20));
		jPanel2.add(comboType);

		boutonRechercheReservations.setText("Chercher les réservations...");
		boutonRechercheReservations.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chercherReservations(evt);
			}
		});
		jPanel2.add(boutonRechercheReservations);

		cbRegrouperParAps.setText("Regrouper par APs");
		cbRegrouperParAps.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		cbRegrouperParAps.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		cbRegrouperParAps.setPreferredSize(new java.awt.Dimension(170, 23));
		cbRegrouperParAps.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbRegrouperParApsActionPerformed(evt);
			}
		});
		jPanel2.add(cbRegrouperParAps);

		cbRegrouperParEcs.setText("Regrouper par ECs");
		cbRegrouperParEcs.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		cbRegrouperParEcs.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		cbRegrouperParEcs.setPreferredSize(new java.awt.Dimension(150, 23));
		cbRegrouperParEcs.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbRegrouperParEcsActionPerformed(evt);
			}
		});
		jPanel2.add(cbRegrouperParEcs);

		getContentPane().add(jPanel2);

		org.jdesktop.layout.GroupLayout viewTableReservationsLayout = new org.jdesktop.layout.GroupLayout(viewTableReservations);
		viewTableReservations.setLayout(viewTableReservationsLayout);
		viewTableReservationsLayout.setHorizontalGroup(viewTableReservationsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				0, 801, Short.MAX_VALUE));
		viewTableReservationsLayout.setVerticalGroup(viewTableReservationsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0,
				210, Short.MAX_VALUE));

		getContentPane().add(viewTableReservations);

		jPanel4.setLayout(new java.awt.BorderLayout());

		boutonExporter.setText("Exporter");
		boutonExporter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exporter(evt);
			}
		});

		boutonInspecter.setText("Inspecter");
		boutonInspecter.setMargin(new java.awt.Insets(2, 2, 2, 2));
		boutonInspecter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				inspecter(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel3Layout.createSequentialGroup().addContainerGap()
						.add(boutonInspecter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(18, 18, 18)
						.add(boutonExporter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel3Layout.createSequentialGroup().addContainerGap()
						.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(boutonInspecter).add(boutonExporter))
						.addContainerGap()));

		jPanel4.add(jPanel3, java.awt.BorderLayout.WEST);

		boutonFermer.setText("Fermer");
		boutonFermer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				close(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel5Layout.createSequentialGroup().addContainerGap()
						.add(boutonFermer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel5Layout.createSequentialGroup().addContainerGap().add(boutonFermer).addContainerGap()));

		jPanel4.add(jPanel5, java.awt.BorderLayout.EAST);

		getContentPane().add(jPanel4);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void cbRegrouperParApsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbRegrouperParApsActionPerformed
		if (cbRegrouperParAps.isSelected()) {
			cbRegrouperParEcs.setSelected(false);
		}
		chercherReservations();
	}// GEN-LAST:event_cbRegrouperParApsActionPerformed

	private void cbRegrouperParEcsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbRegrouperParEcsActionPerformed
		if (cbRegrouperParEcs.isSelected()) {
			cbRegrouperParAps.setSelected(false);
		}
		chercherReservations();
	}// GEN-LAST:event_cbRegrouperParEcsActionPerformed

	private void close(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_close
		setVisible(false);
	}// GEN-LAST:event_close

	private void inspecter(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_inspecter
		inspecter();
	}// GEN-LAST:event_inspecter

	private void exporter(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exporter
		exporter();
	}// GEN-LAST:event_exporter

	private void chercherReservations(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chercherReservations
		chercherReservations();
	}// GEN-LAST:event_chercherReservations

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton boutonExporter;
	private javax.swing.JButton boutonFermer;
	private javax.swing.JButton boutonInspecter;
	private javax.swing.JButton boutonRecherchePersonnes;
	private javax.swing.JButton boutonRechercheReservations;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JCheckBox cbRegrouperParAps;
	private javax.swing.JCheckBox cbRegrouperParEcs;
	private javax.swing.JComboBox comboAnnee;
	private javax.swing.JComboBox comboType;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JRadioButton rbRechercheEtudiants;
	private javax.swing.JRadioButton rbRechercheNonEtudiants;
	private javax.swing.JTextField tfRechercheNom;
	private javax.swing.JTextField tfRecherchePrenom;
	private javax.swing.JPanel viewTableIndividus;
	private javax.swing.JPanel viewTableReservations;
	// End of variables declaration//GEN-END:variables

}
