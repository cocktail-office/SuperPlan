/*
 * Copyright COCKTAIL (www.cocktail.org), 2001, 2012 
 * 
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software. You can use, 
 * modify and/or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty and the software's author, the holder of the
 * economic rights, and the successive licensors have only limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading, using, modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean that it is complicated to manipulate, and that also
 * therefore means that it is reserved for developers and experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and, more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package org.cocktail.superplan.client.composant;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.LogReservation;
import org.cocktail.superplan.client.metier.LogReservationAp;
import org.cocktail.superplan.client.metier.LogReservationExamen;
import org.cocktail.superplan.client.metier.LogReservationObjet;
import org.cocktail.superplan.client.metier.LogReservationOccupant;
import org.cocktail.superplan.client.metier.LogReservationPeriod;
import org.cocktail.superplan.client.metier.LogReservationSalle;
import org.cocktail.superplan.client.metier.LogReservationSemestre;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.swing.TableSorter;
import org.cocktail.superplan.client.swing.ZEOTable;
import org.cocktail.superplan.client.swing.ZEOTable.ZEOTableListener;
import org.cocktail.superplan.client.swing.ZEOTableModel;
import org.cocktail.superplan.client.swing.ZEOTableModelColumn;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSComparator.ComparisonException;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import edtscol.client.composant.recherche.RechercheIndividu;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;
import fr.univlr.utilities.ULRDateTimeWindow;

public class LogsHistorisation extends javax.swing.JDialog {

	private static LogsHistorisation sharedInstance;
	private Component parent;
	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EOEditingContext eContext;
	public EODisplayGroup eodLogReservation = new EODisplayGroup(), eodDetailLogReservation = new EODisplayGroup();
	public ZEOTable tableLogReservation, tableDetailLogReservation;
	private TimeHandler timeHandler;
	private IndividuUlr agentEradiqueur = null;
	private Integer resaKey = null;

	/** Creates new form LogsHistorisation */
	public LogsHistorisation(Component parent, boolean modal) {
		super(new javax.swing.JFrame(), modal);
		initComponents();
		this.parent = parent;
		this.eContext = app.editingContext();
		init();
	}

	private void init() {
		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());

		btDateSuppression.setText(null);
		btDateSuppression.setIcon(WidgetHandler.imageIcon("minical"));
		btAgentEradiqueur.setText(null);
		btAgentEradiqueur.setIcon(WidgetHandler.imageIcon("minifind"));
		initTables();
		tableLogReservation.addListener(new ListenerTableLogReservation());
		chercherLogReservation();
	}

	public static LogsHistorisation sharedInstance(Component parent) {
		if (sharedInstance == null) {
			sharedInstance = new LogsHistorisation(parent, false);
		}
		return sharedInstance;
	}

	public void open() {
		setResaKey(null);
		panelFiltres.setVisible(true);
		chercherLogReservation();
		setLocation();
		setVisible(true);
	}

	public void open(Reservation resa) {
		if (resa == null || resa.hasHistorique() == false) {
			WindowHandler.showInfo("Aucun historique pour cette réservation...");
			return;
		}
		Integer resaKey = (Integer) app.primaryKeyFromEO(resa, Reservation.RESA_KEY_KEY);
		if (resaKey != null) {
			setResaKey(resaKey);
			chercherLogReservation();
			panelFiltres.setVisible(false);
			setLocation();
			setVisible(true);
		}
	}

	private void setFiltresEnabled(boolean enabled) {
		btAgentEradiqueur.setEnabled(enabled);
		btDateSuppression.setEnabled(enabled);
		btFiltrer.setEnabled(enabled);
	}

	public Integer resaKey() {
		return resaKey;
	}

	public void setResaKey(Integer resaKey) {
		this.resaKey = resaKey;
		setFiltresEnabled(resaKey == null);
	}

	public void setDateSuppression(String dateDeb) {
		tfDateSuppression.setText(dateDeb);
	}

	private void initTables() {
		// LogReservation...
		Vector<ZEOTableModelColumn> myColsLogReservation = new Vector<ZEOTableModelColumn>();

		ZEOTableModelColumn col = new ZEOTableModelColumn(eodLogReservation, "logResaKey", "No résa", 35);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(Integer.class);
		myColsLogReservation.add(col);
		col = new ZEOTableModelColumn(eodLogReservation, "date", "Date", 110);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(NSTimestamp.class);
		col.setFormatDisplay(DateFormat.getDateTimeInstance());
		myColsLogReservation.add(col);
		col = new ZEOTableModelColumn(eodLogReservation, "logAction", "Action", 80);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(String.class);
		myColsLogReservation.add(col);
		col = new ZEOTableModelColumn(eodLogReservation, "individuLogAgent", "Agent", 80);
		col.setAlignment(SwingConstants.LEFT);
		myColsLogReservation.add(col);
		col = new ZEOTableModelColumn(eodLogReservation, "logMotif", "Motif de modif/supp", 120);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(String.class);
		myColsLogReservation.add(col);
		col = new ZEOTableModelColumn(eodLogReservation, "tlocLibelle", "Type", 30);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(String.class);
		myColsLogReservation.add(col);
		col = new ZEOTableModelColumn(eodLogReservation, "resaCommentaire", "Commentaire", 60);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(String.class);
		myColsLogReservation.add(col);
		col = new ZEOTableModelColumn(eodLogReservation, "periodicites", "Dates", 250);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(String.class);
		myColsLogReservation.add(col);

		ZEOTableModel myTableModelLogReservation = new ZEOTableModel(eodLogReservation, myColsLogReservation);
		TableSorter myTableSorterLogReservation = new TableSorter(myTableModelLogReservation);

		tableLogReservation = new ZEOTable(myTableSorterLogReservation);
		myTableSorterLogReservation.setTableHeader(tableLogReservation.getTableHeader());

		tableLogReservation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		viewTableLogReservation.setLayout(new BorderLayout());
		viewTableLogReservation.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableLogReservation.removeAll();
		viewTableLogReservation.add(new JScrollPane(tableLogReservation), BorderLayout.CENTER);

		// DetailLogReservation...
		Vector<ZEOTableModelColumn> myColsDetailLogReservation = new Vector<ZEOTableModelColumn>();

		col = new ZEOTableModelColumn(eodDetailLogReservation, "type", "Ressource", 30);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(String.class);
		myColsDetailLogReservation.add(col);
		col = new ZEOTableModelColumn(eodDetailLogReservation, "description", "Description", 300);
		col.setAlignment(SwingConstants.LEFT);
		col.setColumnClass(String.class);
		myColsDetailLogReservation.add(col);

		ZEOTableModel myTableModelDetailLogReservation = new ZEOTableModel(eodDetailLogReservation, myColsDetailLogReservation);
		TableSorter myTableSorterDetailLogReservation = new TableSorter(myTableModelDetailLogReservation);

		tableDetailLogReservation = new ZEOTable(myTableSorterDetailLogReservation);
		myTableSorterDetailLogReservation.setTableHeader(tableDetailLogReservation.getTableHeader());

		tableDetailLogReservation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		viewTableDetailLogReservation.setLayout(new BorderLayout());
		viewTableDetailLogReservation.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		viewTableDetailLogReservation.removeAll();
		viewTableDetailLogReservation.add(new JScrollPane(tableDetailLogReservation), BorderLayout.CENTER);

	}

	private void chercherAgentEradiqueur() {
		RechercheIndividu indFinder = new RechercheIndividu(this, eContext);
		indFinder.setLocationRelativeTo(tfAgentEradiqueur);
		indFinder.setVisible(true);
		setAgentEradiqueur(indFinder.getSelectedIndividu());
	}

	private void chercherLogReservation() {
		eodLogReservation.setObjectArray(null);

		NSArray<LogReservation> logs = fetchLogReservation();
		if (logs == null || logs.count() == 0) {
			return;
		}

		if (resaKey() != null) {
			eodLogReservation.setObjectArray(displayLogResas(logs));
		}
		else {
			NSMutableArray<NSDictionary<Object, Object>> logsDisplayed = new NSMutableArray<NSDictionary<Object, Object>>();
			for (int indexResa = 0; indexResa < logs.count(); indexResa++) {
				LogReservation log = logs.objectAtIndex(indexResa);
				logsDisplayed.addObject(displayOneLogResaSuppression(log));
			}
			eodLogReservation.setObjectArray(logsDisplayed);
		}

		eodLogReservation.setSelectedObject(null);
		tableLogReservation.updateData();
		chercherDetailLogReservation();
	}

	private NSArray<LogReservation> fetchLogReservation() {
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		if (resaKey() != null) {
			quals.addObject(new EOKeyValueQualifier(LogReservation.LOG_RESA_KEY_KEY, EOKeyValueQualifier.QualifierOperatorEqual, resaKey()));
		}
		else {
			quals.addObject(new EOKeyValueQualifier(LogReservation.LOG_ACTION_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
					LogReservation.ACTION_SUPPRESSION));
			if (StringCtrl.isEmpty(tfAgentEradiqueur.getText()) == false) {
				quals.addObject(new EOKeyValueQualifier(LogReservation.INDIVIDU_LOG_AGENT_KEY, EOKeyValueQualifier.QualifierOperatorEqual,
						getAgentEradiqueur()));
			}
			if (StringCtrl.isEmpty(tfDateSuppression.getText()) == false) {
				quals.addObject(new EOKeyValueQualifier(LogReservation.LOG_DATE_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThan, DateCtrl
						.stringToDate(tfDateSuppression.getText())));
			}
		}
		EOSortOrdering sortDate = new EOSortOrdering("logDate", EOSortOrdering.CompareDescending);
		NSArray<EOSortOrdering> sortOrderArray = new NSArray<EOSortOrdering>(sortDate);
		return LogReservation.fetchLogReservations(eContext, new EOAndQualifier(quals), sortOrderArray);
	}

	private NSArray<NSDictionary<Object, Object>> displayLogResas(NSArray<LogReservation> logs) {
		NSMutableArray<NSDictionary<Object, Object>> logsDisplayed = new NSMutableArray<NSDictionary<Object, Object>>();
		if (logs == null || logs.count() == 0) {
			return logsDisplayed;
		}
		try {
			logs = logs.sortedArrayUsingComparator(new NSComparator() {
				public int compare(Object arg0, Object arg1) throws ComparisonException {
					return ((LogReservation) arg0).logDate().compare(((LogReservation) arg1).logDate());
				}
			});
		}
		catch (ComparisonException e) {
			e.printStackTrace();
			return logsDisplayed;
		}
		for (int indexResa = 0; indexResa < logs.count(); indexResa++) {
			LogReservation log = logs.objectAtIndex(indexResa);
			LogReservation logPrecedent = null;
			if (indexResa > 0) {
				logPrecedent = logs.objectAtIndex(indexResa - 1);
			}
			logsDisplayed.addObject(displayOneLogResa(log, logPrecedent));
		}
		if (logs.lastObject().logAction().equals(LogReservation.ACTION_SUPPRESSION)) {
			logsDisplayed.addObject(displayOneLogResaSuppression(logs.lastObject()));
		}

		return logsDisplayed;
	}

	private NSMutableDictionary<Object, Object> displayOneLogResa(LogReservation log, LogReservation logPrecedent) {
		SimpleDateFormat dfJour = new SimpleDateFormat("E");
		SimpleDateFormat dfHeures = new SimpleDateFormat("H:mm");

		NSMutableDictionary<Object, Object> disp = new NSMutableDictionary<Object, Object>();
		disp.setObjectForKey(log, "logReservation");
		disp.setObjectForKey(logPrecedent != null ? logPrecedent.logAction() : log.logOldDCreation().equals(log.logOldDModification()) ? "Création"
				: "Modification", "logAction");
		disp.setObjectForKey(logPrecedent != null && logPrecedent.logMotif() != null ? logPrecedent.logMotif() : "", "logMotif");
		disp.setObjectForKey(log.logResaKey(), "logResaKey");
		disp.setObjectForKey(log.logOldDModification(), "date");
		disp.setObjectForKey(log.individuOldAgent(), "individuLogAgent");
		disp.setObjectForKey(log.typeLocation().tlocLibelle(), "tlocLibelle");
		disp.setObjectForKey(log.logOldResaCommentaire() != null ? log.logOldResaCommentaire() : "", "resaCommentaire");
		if (log.logReservationPeriods() != null && log.logReservationPeriods().count() > 0) {
			StringBuffer periods = new StringBuffer();
			LogReservationPeriod logPeriodFirst = (LogReservationPeriod) log.logReservationPeriods().objectAtIndex(0);
			periods.append(dfJour.format(logPeriodFirst.logOldDateDeb()));
			periods.append(" de ");
			periods.append(dfHeures.format(logPeriodFirst.logOldDateDeb()));
			periods.append(" à ");
			periods.append(dfHeures.format(logPeriodFirst.logOldDateFin()));
			periods.append(" - Semaine(s) ");
			periods.append(getSemainesString(log));

			disp.setObjectForKey(periods.toString(), "periodicites");
		}
		return disp;
	}

	private NSMutableDictionary<Object, Object> displayOneLogResaSuppression(LogReservation log) {
		SimpleDateFormat dfJour = new SimpleDateFormat("E");
		SimpleDateFormat dfHeures = new SimpleDateFormat("H:mm");
		NSMutableDictionary<Object, Object> disp = new NSMutableDictionary<Object, Object>();
		disp.setObjectForKey(log, "logReservation");
		disp.setObjectForKey(log.logAction(), "logAction");
		disp.setObjectForKey(log.logMotif() != null ? log.logMotif() : "", "logMotif");
		disp.setObjectForKey(log.logResaKey(), "logResaKey");
		disp.setObjectForKey(log.logDate(), "date");
		disp.setObjectForKey(log.individuLogAgent(), "individuLogAgent");
		disp.setObjectForKey(log.typeLocation().tlocLibelle(), "tlocLibelle");
		disp.setObjectForKey(log.logOldResaCommentaire() != null ? log.logOldResaCommentaire() : "", "resaCommentaire");
		if (log.logReservationPeriods() != null && log.logReservationPeriods().count() > 0) {
			StringBuffer periods = new StringBuffer();
			LogReservationPeriod logPeriodFirst = (LogReservationPeriod) log.logReservationPeriods().objectAtIndex(0);
			periods.append(dfJour.format(logPeriodFirst.logOldDateDeb()));
			periods.append(" de ");
			periods.append(dfHeures.format(logPeriodFirst.logOldDateDeb()));
			periods.append(" à ");
			periods.append(dfHeures.format(logPeriodFirst.logOldDateFin()));
			periods.append(" - Semaine(s) ");
			periods.append(getSemainesString(log));

			disp.setObjectForKey(periods.toString(), "periodicites");
		}
		return disp;
	}

	private String getSemainesString(LogReservation log) {
		if (log.logReservationPeriods() != null && log.logReservationPeriods().count() > 0) {
			NSMutableArray<NSDictionary<String, NSTimestamp>> periodsArray = new NSMutableArray<NSDictionary<String, NSTimestamp>>();
			for (int indexPeriods = 0; indexPeriods < log.logReservationPeriods().count(); indexPeriods++) {
				LogReservationPeriod logPeriod = (LogReservationPeriod) log.logReservationPeriods().objectAtIndex(indexPeriods);
				NSDictionary<String, NSTimestamp> dico = new NSDictionary<String, NSTimestamp>(new NSTimestamp[] { logPeriod.logOldDateDeb(),
						logPeriod.logOldDateFin() }, new String[] { "dateDeb", "dateFin" });
				periodsArray.addObject(dico);
			}
			Hashtable<String, Object> per = timeHandler.decomputePeriodicites(periodsArray);
			return (String) per.get("semaines");
		}
		return "";
	}

	private void chercherDetailLogReservation() {
		eodDetailLogReservation.setObjectArray(null);
		if (eodLogReservation.selectedObject() != null) {
			LogReservation log = (LogReservation) ((NSDictionary<Object, Object>) eodLogReservation.selectedObject()).valueForKey("logReservation");
			NSMutableArray<NSDictionary<Object, Object>> logsDisplayed = new NSMutableArray<NSDictionary<Object, Object>>();
			logsDisplayed.addObjectsFromArray(logReservationAps(log));
			logsDisplayed.addObjectsFromArray(logReservationExamens(log));
			logsDisplayed.addObjectsFromArray(logReservationObjets(log));
			logsDisplayed.addObjectsFromArray(logReservationOccupants(log));
			logsDisplayed.addObjectsFromArray(logReservationSalles(log));
			logsDisplayed.addObjectsFromArray(logReservationSemestres(log));

			eodDetailLogReservation.setObjectArray(logsDisplayed);
		}
		eodDetailLogReservation.setSelectedObject(null);
		tableDetailLogReservation.updateData();
	}

	private NSArray<NSDictionary<Object, Object>> logReservationAps(LogReservation log) {
		NSMutableArray<NSDictionary<Object, Object>> logs = new NSMutableArray<NSDictionary<Object, Object>>();
		if (log.logReservationAps() != null) {
			for (int i = 0; i < log.logReservationAps().count(); i++) {
				LogReservationAp logAp = (LogReservationAp) log.logReservationAps().objectAtIndex(i);
				NSMutableDictionary<Object, Object> disp = new NSMutableDictionary<Object, Object>();
				disp.setObjectForKey("Enseignement", "type");
				disp.setObjectForKey(logAp.toString(), "description");
				logs.addObject(disp);
			}
		}
		return logs;
	}

	private NSArray<NSDictionary<Object, Object>> logReservationExamens(LogReservation log) {
		NSMutableArray<NSDictionary<Object, Object>> logs = new NSMutableArray<NSDictionary<Object, Object>>();
		if (log.logReservationExamens() != null) {
			for (int i = 0; i < log.logReservationExamens().count(); i++) {
				LogReservationExamen logExamen = (LogReservationExamen) log.logReservationExamens().objectAtIndex(i);
				NSMutableDictionary<Object, Object> disp = new NSMutableDictionary<Object, Object>();
				disp.setObjectForKey("Examen", "type");
				disp.setObjectForKey(logExamen.toString(), "description");
				logs.addObject(disp);
			}
		}
		return logs;
	}

	private NSArray<NSDictionary<Object, Object>> logReservationObjets(LogReservation log) {
		NSMutableArray<NSDictionary<Object, Object>> logs = new NSMutableArray<NSDictionary<Object, Object>>();
		if (log.logReservationObjets() != null) {
			for (int i = 0; i < log.logReservationObjets().count(); i++) {
				LogReservationObjet logObjet = (LogReservationObjet) log.logReservationObjets().objectAtIndex(i);
				NSMutableDictionary<Object, Object> disp = new NSMutableDictionary<Object, Object>();
				disp.setObjectForKey("Objet", "type");
				disp.setObjectForKey(logObjet.toString(), "description");
				logs.addObject(disp);
			}
		}
		return logs;
	}

	private NSArray<NSDictionary<Object, Object>> logReservationOccupants(LogReservation log) {
		NSMutableArray<NSDictionary<Object, Object>> logs = new NSMutableArray<NSDictionary<Object, Object>>();
		if (log.logReservationOccupants() != null) {
			for (int i = 0; i < log.logReservationOccupants().count(); i++) {
				LogReservationOccupant logOccupant = (LogReservationOccupant) log.logReservationOccupants().objectAtIndex(i);
				NSMutableDictionary<Object, Object> disp = new NSMutableDictionary<Object, Object>();
				disp.setObjectForKey("Individu", "type");
				disp.setObjectForKey(logOccupant.toString(), "description");
				logs.addObject(disp);
			}
		}
		return logs;
	}

	private NSArray<NSDictionary<Object, Object>> logReservationSalles(LogReservation log) {
		NSMutableArray<NSDictionary<Object, Object>> logs = new NSMutableArray<NSDictionary<Object, Object>>();
		if (log.logReservationSalles() != null) {
			for (int i = 0; i < log.logReservationSalles().count(); i++) {
				LogReservationSalle logSalle = (LogReservationSalle) log.logReservationSalles().objectAtIndex(i);
				NSMutableDictionary<Object, Object> disp = new NSMutableDictionary<Object, Object>();
				disp.setObjectForKey("Salle", "type");
				disp.setObjectForKey(logSalle.toString(), "description");
				logs.addObject(disp);
			}
		}
		return logs;
	}

	private NSArray<NSDictionary<Object, Object>> logReservationSemestres(LogReservation log) {
		NSMutableArray<NSDictionary<Object, Object>> logs = new NSMutableArray<NSDictionary<Object, Object>>();
		if (log.logReservationSemestres() != null) {
			for (int i = 0; i < log.logReservationSemestres().count(); i++) {
				LogReservationSemestre logSemestre = (LogReservationSemestre) log.logReservationSemestres().objectAtIndex(i);
				NSMutableDictionary<Object, Object> disp = new NSMutableDictionary<Object, Object>();
				disp.setObjectForKey("Semestre/promo", "type");
				disp.setObjectForKey(logSemestre.toString(), "description");
				logs.addObject(disp);
			}
		}
		return logs;
	}

	public IndividuUlr getAgentEradiqueur() {
		return agentEradiqueur;
	}

	public void setAgentEradiqueur(IndividuUlr agentEradiqueur) {
		this.agentEradiqueur = agentEradiqueur;
		if (agentEradiqueur == null) {
			tfAgentEradiqueur.setText(null);
		}
		else {
			tfAgentEradiqueur.setText(agentEradiqueur.toString());
		}
	}

	private class ListenerTableLogReservation implements ZEOTableListener {
		public void onSelectionChanged() {
			chercherDetailLogReservation();
		}

		public void onDbClick() {
			if (panelFiltres.isVisible()) {
				if (resaKey() != null) {
					setResaKey(null);
					chercherLogReservation();
				}
				else {
					if (eodLogReservation.selectedObject() != null) {
						Integer logResaKey = (Integer) ((NSDictionary<Object, Object>) eodLogReservation.selectedObject()).valueForKey("logResaKey");
						setResaKey(logResaKey);
						chercherLogReservation();
					}
				}
			}
		}
	}

	private void setLocation() {
		if (parent != null) {
			setLocation(parent.getX() + 20, parent.getY() + 20);
		}
		else {
			java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		btFermer = new javax.swing.JButton();
		jSplitPane1 = new javax.swing.JSplitPane();
		viewTableLogReservation = new javax.swing.JPanel();
		viewTableDetailLogReservation = new javax.swing.JPanel();
		panelFiltres = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jPanelxxx = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		tfAgentEradiqueur = new javax.swing.JTextField();
		btAgentEradiqueur = new javax.swing.JButton();
		jSeparator3 = new javax.swing.JSeparator();
		jLabel4 = new javax.swing.JLabel();
		tfDateSuppression = new javax.swing.JTextField();
		btDateSuppression = new javax.swing.JButton();
		jLabel5 = new javax.swing.JLabel();
		btFiltrer = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Consultation des logs d'historisation");

		jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

		btFermer.setText("Fermer");
		btFermer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btFermerActionPerformed(evt);
			}
		});
		jPanel1.add(btFermer);

		getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

		jSplitPane1.setDividerLocation(250);
		jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

		org.jdesktop.layout.GroupLayout viewTableLogReservationLayout = new org.jdesktop.layout.GroupLayout(viewTableLogReservation);
		viewTableLogReservation.setLayout(viewTableLogReservationLayout);
		viewTableLogReservationLayout.setHorizontalGroup(viewTableLogReservationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 893, Short.MAX_VALUE));
		viewTableLogReservationLayout.setVerticalGroup(viewTableLogReservationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 249, Short.MAX_VALUE));

		jSplitPane1.setTopComponent(viewTableLogReservation);

		org.jdesktop.layout.GroupLayout viewTableDetailLogReservationLayout = new org.jdesktop.layout.GroupLayout(viewTableDetailLogReservation);
		viewTableDetailLogReservation.setLayout(viewTableDetailLogReservationLayout);
		viewTableDetailLogReservationLayout.setHorizontalGroup(viewTableDetailLogReservationLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(0, 893, Short.MAX_VALUE));
		viewTableDetailLogReservationLayout.setVerticalGroup(viewTableDetailLogReservationLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(0, 141, Short.MAX_VALUE));

		jSplitPane1.setBottomComponent(viewTableDetailLogReservation);

		getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

		panelFiltres.setLayout(new javax.swing.BoxLayout(panelFiltres, javax.swing.BoxLayout.PAGE_AXIS));

		jPanel3.setBackground(new java.awt.Color(255, 255, 232));
		jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 3, 3));

		jLabel1.setBackground(new java.awt.Color(255, 255, 255));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("Double-cliquez sur une ligne de suppression pour visualiser l'historique, double-cliquez pour revenir à la liste de suppressions...");
		jLabel1.setAlignmentY(0.0F);
		jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jLabel1.setRequestFocusEnabled(false);
		jPanel3.add(jLabel1);

		panelFiltres.add(jPanel3);

		jPanelxxx.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtres..."));
		jPanelxxx.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabel3.setText("Agent éradiqueur:");
		jPanelxxx.add(jLabel3);

		tfAgentEradiqueur.setFont(new java.awt.Font("Tahoma", 0, 10));
		tfAgentEradiqueur.setEnabled(false);
		tfAgentEradiqueur.setPreferredSize(new java.awt.Dimension(110, 20));
		jPanelxxx.add(tfAgentEradiqueur);

		btAgentEradiqueur.setText("A");
		btAgentEradiqueur.setToolTipText("Sélectionner un individu...");
		btAgentEradiqueur.setPreferredSize(new java.awt.Dimension(23, 23));
		btAgentEradiqueur.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btAgentEradiqueurActionPerformed(evt);
			}
		});
		jPanelxxx.add(btAgentEradiqueur);

		jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
		jSeparator3.setMinimumSize(new java.awt.Dimension(5, 20));
		jSeparator3.setPreferredSize(new java.awt.Dimension(5, 20));
		jPanelxxx.add(jSeparator3);

		jLabel4.setText("Date suppression >");
		jPanelxxx.add(jLabel4);

		tfDateSuppression.setEnabled(false);
		tfDateSuppression.setPreferredSize(new java.awt.Dimension(70, 20));
		jPanelxxx.add(tfDateSuppression);

		btDateSuppression.setText("D");
		btDateSuppression.setToolTipText("Sélectionner une date...");
		btDateSuppression.setPreferredSize(new java.awt.Dimension(23, 23));
		btDateSuppression.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btDateSuppressionActionPerformed(evt);
			}
		});
		jPanelxxx.add(btDateSuppression);

		jLabel5.setText("   ...   ");
		jPanelxxx.add(jLabel5);

		btFiltrer.setText("Filtrer");
		btFiltrer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btFiltrerActionPerformed(evt);
			}
		});
		jPanelxxx.add(btFiltrer);

		panelFiltres.add(jPanelxxx);

		getContentPane().add(panelFiltres, java.awt.BorderLayout.NORTH);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btAgentEradiqueurActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btAgentEradiqueurActionPerformed
		chercherAgentEradiqueur();
	}// GEN-LAST:event_btAgentEradiqueurActionPerformed

	private void btDateSuppressionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btSelectDateActionPerformed
		new ULRDateTimeWindow(this, this, btDateSuppression, "setDateSuppression");
	}// GEN-LAST:event_btSelectDateActionPerformed

	private void btFermerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btFermerActionPerformed
		setVisible(false);
	}// GEN-LAST:event_btFermerActionPerformed

	private void btFiltrerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btValiderFiltreNoResaActionPerformed
		chercherLogReservation();
	}// GEN-LAST:event_btValiderFiltreNoResaActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				LogsHistorisation dialog = new LogsHistorisation(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btAgentEradiqueur;
	private javax.swing.JButton btDateSuppression;
	private javax.swing.JButton btFermer;
	private javax.swing.JButton btFiltrer;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanelxxx;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JPanel panelFiltres;
	private javax.swing.JTextField tfAgentEradiqueur;
	private javax.swing.JTextField tfDateSuppression;
	private javax.swing.JPanel viewTableDetailLogReservation;
	private javax.swing.JPanel viewTableLogReservation;
	// End of variables declaration//GEN-END:variables

}
