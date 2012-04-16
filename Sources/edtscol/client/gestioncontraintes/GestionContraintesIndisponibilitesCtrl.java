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
package edtscol.client.gestioncontraintes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.cocktail.superplan.client.factory.ContrainteFactory;
import org.cocktail.superplan.client.metier.ContrainteHeure;
import org.cocktail.superplan.client.metier.ContrainteJour;
import org.cocktail.superplan.client.metier.ContrainteSemaine;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.IndividuUlr;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.client.EOClientResourceBundle;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import edtscol.client.composant.recherche.RechercheIndividu;
import edtscol.client.gestioncontraintes.JPlanning.PlanningEvent;
import fr.univlr.common.utilities.WindowHandler;

public class GestionContraintesIndisponibilitesCtrl {

	private static final int PLANNING_HEURES_LARGEUR_CASE = 12;
	private static final int PLANNING_HEURES_HAUTEUR_CASE = 22;
	private static final int PLANNING_HEURES_HGAP = 1;
	private static final int PLANNING_HEURES_VGAP = 2;

	private static GestionContraintesIndisponibilitesCtrl sharedInstance;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EOEditingContext ec;

	private FormationAnnee formationAnneeEnCours;

	private GestionContraintesIndisponibilitesView myView;

	private WeekPanel weekPanel;
	private DayPanel dayPanel;

	private TitledBorder borderDayPanel;

	private JPlanning dayPlanning;
	private JScrollPane scrollPaneDayPlanning;

	private JPopupButton boutonSelectionSemaines, boutonRecopieSemaines;
	private JPopupButton boutonSelectionJours, boutonRecopieJours;

	private boolean recopieEnabled = false;

	private IRessourceContrainte selectedRessource = null;

	public GestionContraintesIndisponibilitesCtrl(EOEditingContext editingContext, boolean recopieEnabled) {
		super();

		myView = new GestionContraintesIndisponibilitesView();
		ec = editingContext;
		this.recopieEnabled = recopieEnabled;
		init();
	}

	private void init() {
		// Semaines...
		initGUISemaines();

		// Jours...
		initGUIJours();
	}

	public void setFormationAnnee(FormationAnnee annee) {
		this.formationAnneeEnCours = annee;
		weekPanel.updateFormationAnnee(annee);
	}

	public FormationAnnee formationAnnee() {
		return formationAnneeEnCours;
	}

	private void refresh() {
		updateSemaines(getSelectedRessource());
	}

	public void updateSemaines(IRessourceContrainte ressource) {
		setSelectedRessource(ressource);
		NSArray<ContrainteSemaine> ctrls = null;
		if (ressource != null) {
			ctrls = ressource.getContrainteSemaines(formationAnneeEnCours);
		}
		weekPanel.update(ctrls, true, false);
		updateJours(weekPanel.getCurrentButton());
	}

	private void updateJours(WeekToggleButton b) {
		if (b != null && b.isSelected()) {
			borderDayPanel.setTitle("Semaine " + b.week() + " - " + b.getToolTipText());

			ContrainteSemaine ctrl = b.contrainteSemaine();
			dayPanel.update(b.date(), ctrl);

			if (ctrl != null) {
				updateHeures(ctrl.contrainteJours());
			}
			else {
				dayPlanning.clearPlanning();
			}
		}
		else {
			borderDayPanel.setTitle(" ");
			dayPanel.update(null, (ContrainteSemaine) null);
			dayPlanning.clearPlanning();
		}
		myView.getPanelJours().repaint();
	}

	private void updateHeures(ContrainteJour contrainteJour) {
		if (contrainteJour != null) {
			dayPlanning.afficherDansLePlanning(contrainteJour.contrainteHeures(), ContrainteHeure.CTH_HEURE_DEBUT_KEY,
					ContrainteHeure.CTH_HEURE_FIN_KEY, null, null);
		}
	}

	private void updateHeures(NSArray<ContrainteJour> arrayContrainteJour) {
		dayPlanning.clearPlanning();
		if (arrayContrainteJour != null) {
			for (int i = 0; i < arrayContrainteJour.count(); i++) {
				ContrainteJour ctj = arrayContrainteJour.objectAtIndex(i);
				updateHeures(ctj);
			}
		}
	}

	// Actions...

	private void displayWeekButton(WeekToggleButton b) {
		try {
			updateJours(b);
		}
		catch (Exception e) {
			e.printStackTrace();
			ec.revert();
			refresh();
			WindowHandler.showError(e.getMessage());
		}
	}

	private void setWeekButtonsFull(WeekToggleButton[] buttons, boolean full) {
		if (buttons != null) {
			for (int i = 0; i < buttons.length; i++) {
				WeekToggleButton button = buttons[i];
				setWeekButtonFull(button, full);
			}
		}
		updateJours(weekPanel.getCurrentButton());
	}

	private void setWeekButtonFull(WeekToggleButton b, boolean full) {
		if (b == null) {
			return;
		}
		if (formationAnnee() == null || getSelectedRessource() == null) {
			b.setSelected(false);
			b.repaint();
			return;
		}
		try {
			if (full) {
				if (b.contrainteSemaine() != null) {
					b.deleteCtrlCascade(ec);
				}
				if (b.contrainteSemaine() == null) {
					ContrainteSemaine ctrl = ContrainteFactory.createContrainteSemaineCascade(ec, b.date(), formationAnnee(), getSelectedRessource());
					if (app.saveChanges() == false) {
						throw new Exception("Impossible de sauvegarder, problème d'enregistrement !");
					}
					b.setContrainteSemaine(ctrl);
				}
			}
			else {
				if (b.contrainteSemaine() != null) {
					b.deleteCtrlCascade(ec);
				}
			}
			b.repaint();
		}
		catch (Exception e) {
			e.printStackTrace();
			ec.revert();
			refresh();
			WindowHandler.showError(e.getMessage());
		}
	}

	/**
	 * Action on click sur un bouton de jour, pour le sélectionner ou le dé-sélectionner
	 * 
	 * @param evt
	 */
	public void selectDay(ActionEvent evt) {
		if (evt.getSource() == null || weekPanel.getCurrentButton() == null) {
			return;
		}
		try {
			// vérif si le ctrl semaine existe, sinon on le crée
			if (weekPanel.getCurrentButton().contrainteSemaine() == null) {
				ContrainteSemaine ctrl = ContrainteFactory.createContrainteSemaine(ec, weekPanel.getCurrentButton().date(), formationAnnee(),
						getSelectedRessource());
				if (app.saveChanges() == false) {
					throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!!");
				}
				weekPanel.getCurrentButton().setContrainteSemaine(ctrl);
				weekPanel.getCurrentButton().repaint();
			}
			ContrainteSemaine ctsCurrent = weekPanel.getCurrentButton().contrainteSemaine();
			if (ctsCurrent == null) {
				return;
			}

			DayToggleButton b = (DayToggleButton) evt.getSource();
			if (b.isSelected()) {
				if (b.contrainteJour() == null) {
					ContrainteJour ctrl = ContrainteFactory.createContrainteJourCascade(ec, ctsCurrent, b.date());
					ctsCurrent.addToContrainteJoursRelationship(ctrl);
					if (app.saveChanges() == false) {
						throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!!!");
					}
					b.setContrainteJour(ctrl);
				}
			}
			else {
				if (b.contrainteJour() != null) {
					b.contrainteJour().deleteAllContrainteHeuresRelationships();
					ctsCurrent.deleteContrainteJoursRelationship(b.contrainteJour());
					if (app.saveChanges() == false) {
						throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!!!!");
					}
					b.setContrainteJour(null);
					// si plus aucune journée dans cette semaine, supprime la semaine
					if (ctsCurrent.contrainteJours() == null || ctsCurrent.contrainteJours().count() == 0) {
						ec.deleteObject(ctsCurrent);
						if (app.saveChanges() == false) {
							throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!!...");
						}
						weekPanel.getCurrentButton().setContrainteSemaine(null);
					}
				}
			}
			weekPanel.getCurrentButton().repaint();
			updateHeures(ctsCurrent.contrainteJours());
		}
		catch (Exception e) {
			e.printStackTrace();
			ec.revert();
			refresh();
			WindowHandler.showError(e.getMessage());
		}
	}

	public void selectHours(PlanningEvent event) {
		if (weekPanel.getCurrentButton() == null) {
			return;
		}
		NSDictionary<GregorianCalendar, GregorianCalendar> dico = dayPlanning.getZonesTemporellesSelectionnees();
		if (dico == null || dico.count() == 0) {
			return;
		}
		try {
			// vérif si le ctrl semaine existe, sinon on le crée
			if (weekPanel.getCurrentButton().contrainteSemaine() == null) {
				ContrainteSemaine ctrl = ContrainteFactory.createContrainteSemaine(ec, weekPanel.getCurrentButton().date(), formationAnnee(),
						getSelectedRessource());
				if (app.saveChanges() == false) {
					throw new Exception("Impossible de sauvegarder, problème d'enregistrement !...");
				}
				weekPanel.getCurrentButton().setContrainteSemaine(ctrl);
				weekPanel.getCurrentButton().repaint();
			}
			ContrainteSemaine ctsCurrent = weekPanel.getCurrentButton().contrainteSemaine();
			if (ctsCurrent == null) {
				return;
			}

			Enumeration<GregorianCalendar> dates = dico.keyEnumerator();
			while (dates.hasMoreElements()) {
				GregorianCalendar debut = dates.nextElement(), fin = dico.objectForKey(debut);

				// vérif si le ctrl jour existe, sinon on le crée
				DayToggleButton dayButton = dayPanel.dayButtonFor(debut.get(Calendar.DAY_OF_WEEK));
				if (dayButton == null) {
					return;
				}
				if (dayButton.contrainteJour() == null) {
					ContrainteJour ctrl = ctsCurrent.createContrainteJoursRelationship();
					ctrl.setCtjDate(dayButton.dateNSTimestamp());
					ctrl.setCtjNoJour(dayButton.day());
					if (app.saveChanges() == false) {
						throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!...");
					}
					dayButton.setContrainteJour(ctrl);
					dayButton.setSelected(true);
				}
				ContrainteJour ctjCurrent = dayButton.contrainteJour();
				if (ctjCurrent == null) {
					return;
				}

				// heures...
				debut.set(Calendar.YEAR, dayButton.date().get(Calendar.YEAR));
				debut.set(Calendar.MONTH, dayButton.date().get(Calendar.MONTH));
				debut.set(Calendar.WEEK_OF_YEAR, dayButton.date().get(Calendar.WEEK_OF_YEAR));
				debut.set(Calendar.SECOND, 0);
				debut.set(Calendar.MILLISECOND, 0);
				fin.set(Calendar.YEAR, dayButton.date().get(Calendar.YEAR));
				fin.set(Calendar.MONTH, dayButton.date().get(Calendar.MONTH));
				fin.set(Calendar.WEEK_OF_YEAR, dayButton.date().get(Calendar.WEEK_OF_YEAR));

				int[] day = new int[1440];
				for (int i = 0; i < day.length; i++) {
					day[i] = 0;
				}
				if (ctjCurrent.contrainteHeures() != null) {
					for (int i = 0; i < ctjCurrent.contrainteHeures().count(); i++) {
						ContrainteHeure cth = (ContrainteHeure) ctjCurrent.contrainteHeures().objectAtIndex(i);
						for (int x = timestampToInt(cth.cthHeureDebut()); x < timestampToInt(cth.cthHeureFin()); x++) {
							day[x] = 1;
						}
					}
				}
				for (int x = timestampToInt(new NSTimestamp(debut.getTime())); x < timestampToInt(new NSTimestamp(fin.getTime())); x++) {
					day[x] = (day[x] == 0 ? 1 : 0);
				}

				ctjCurrent.deleteAllContrainteHeuresRelationships();
				int debutInt = -1;
				for (int i = 0; i < day.length; i++) {
					if (day[i] == 1 && debutInt == -1) {
						debutInt = i;
					}
					if ((day[i] == 0 || i == day.length - 1) && debutInt != -1) {
						ContrainteHeure cth = ctjCurrent.createContrainteHeuresRelationship();
						cth.setCthHeureDebut(intToTimestamp(debutInt, ctjCurrent.ctjDate()));
						cth.setCthHeureFin(intToTimestamp(i, ctjCurrent.ctjDate()));
						debutInt = -1;
					}
				}
				if (app.saveChanges() == false) {
					throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!!...");
				}

				// si plus aucune heure dans cette journée, supprime la journée
				if (ctjCurrent.contrainteHeures() == null || ctjCurrent.contrainteHeures().count() == 0) {
					ctsCurrent.deleteContrainteJoursRelationship(ctjCurrent);
					if (app.saveChanges() == false) {
						throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!!...");
					}
					dayButton.setContrainteJour(null);
					dayButton.setSelected(false);
					// si plus aucune journée dans cette semaine, supprime la semaine
					if (ctsCurrent.contrainteJours() == null || ctsCurrent.contrainteJours().count() == 0) {
						ec.deleteObject(ctsCurrent);
						if (app.saveChanges() == false) {
							throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!!...");
						}
						weekPanel.getCurrentButton().setContrainteSemaine(null);
					}
				}
				dayPlanning.clearSelection();
				updateHeures(ctsCurrent.contrainteJours());
				weekPanel.getCurrentButton().repaint();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			ec.revert();
			refresh();
			WindowHandler.showError(e.getMessage());
		}
	}

	public void setEnabled(boolean enabled) {
		weekPanel.setEnabled(enabled);
		boutonSelectionSemaines.setEnabled(enabled);
		boutonRecopieSemaines.setEnabled(enabled);
		dayPanel.setEnabled(enabled);
		dayPlanning.setEnabled(enabled);
		boutonSelectionJours.setEnabled(enabled);
		boutonRecopieJours.setEnabled(enabled);
	}

	private class ActionSelectionnerSemaines extends AbstractAction {
		public ActionSelectionnerSemaines() {
			super.putValue(NAME, "Tout...");
			super.putValue(SHORT_DESCRIPTION, "Sélectionner toutes les semaines (non déjà sélectionnées)");
			super.putValue(SMALL_ICON, new EOClientResourceBundle().getObject("selectAll"));
		}

		public void actionPerformed(ActionEvent e) {
			if (getSelectedRessource() == null) {
				showWarning("Aucune ressource n'est sélectionnée");
				return;
			}
			if (!showConfirmation("Sélectionner toutes les semaines ?")) {
				return;
			}
			setWeekButtonsFull(weekPanel.weekButtons(), true);
		}
	}

	private class ActionDeselectionnerSemaines extends AbstractAction {
		public ActionDeselectionnerSemaines() {
			super.putValue(NAME, "Rien...");
			super.putValue(SHORT_DESCRIPTION, "Désélectionner toutes les semaines");
			super.putValue(SMALL_ICON, new EOClientResourceBundle().getObject("deselectAll"));
		}

		public void actionPerformed(ActionEvent e) {
			if (getSelectedRessource() == null) {
				showWarning("Aucune ressource n'est sélectionnée");
				return;
			}
			if (!showConfirmation("Désélectionner toutes les semaines ???")) {
				return;
			}
			setWeekButtonsFull(weekPanel.weekButtons(), false);
		}
	}

	private class ActionRecopierSemainesFull extends AbstractAction {
		public ActionRecopierSemainesFull() {
			super.putValue(NAME, "Tout...");
			super.putValue(SHORT_DESCRIPTION, "Recopier tout (semaines + jours + heures) sur une autre ressource...");
			super.putValue(SMALL_ICON, new EOClientResourceBundle().getObject("recopierplusplus"));
		}

		public void actionPerformed(ActionEvent e) {
			if (getSelectedRessource() == null) {
				showWarning("Aucune ressource n'est sélectionnée");
				return;
			}

			IRessourceContrainte ressourceTo = null;
			if (getSelectedRessource() instanceof IndividuUlr) {
				RechercheIndividu indFinder = new RechercheIndividu(GestionContraintesCtrl.sharedInstance(ec, null).getMyView(), ec);
				indFinder.setLocationRelativeTo(boutonRecopieSemaines);
				indFinder.setVisible(true);
				ressourceTo = indFinder.getSelectedIndividu();
				if (ressourceTo == null || ressourceTo.equals(getSelectedRessource())) {
					return;
				}
			}

			try {
				if (ressourceTo != null) {
					// vérification de contraintes existantes et nettoyage si besoin...

					NSArray<ContrainteSemaine> ctrls = ressourceTo.getContrainteSemaines(formationAnnee());
					if (ctrls != null && ctrls.count() > 0) {
						if (WindowHandler
								.showConfirmation("Attention: cette ressource a déjà des contraintes enregistrées pour cette année. Les écraser ?") == false) {
							return;
						}
					}
					for (int i = 0; i < ctrls.count(); i++) {
						ContrainteSemaine cts = ctrls.objectAtIndex(i);
						cts.deleteAllCtrlCascade();
						ec.deleteObject(cts);
						if (app.saveChanges() == false) {
							throw new Exception("Impossible de sauvegarder, problème d'enregistrement !");
						}
					}

					// recopie complète...
					ctrls = getSelectedRessource().getContrainteSemaines(formationAnnee());
					if (ctrls != null) {
						for (int i = 0; i < ctrls.count(); i++) {
							ContrainteSemaine ctsFrom = ctrls.objectAtIndex(i);
							ContrainteSemaine ctsTo = ContrainteFactory.createContrainteSemaine(ec, ctsFrom.ctsDate(), ctsFrom.formationAnnee(),
									ressourceTo);
							if (ctsFrom.contrainteJours() != null) {
								for (int j = 0; j < ctsFrom.contrainteJours().count(); j++) {
									ContrainteJour ctjFrom = (ContrainteJour) ctsFrom.contrainteJours().objectAtIndex(j);
									ContrainteJour ctjTo = ContrainteJour.createContrainteJour(ec, ctjFrom.ctjDate(), ctjFrom.ctjNoJour(), ctsTo);
									if (ctjFrom.contrainteHeures() != null) {
										for (int k = 0; k < ctjFrom.contrainteHeures().count(); k++) {
											ContrainteHeure cthFrom = (ContrainteHeure) ctjFrom.contrainteHeures().objectAtIndex(k);
											ContrainteHeure.createContrainteHeure(ec, cthFrom.cthHeureDebut(), cthFrom.cthHeureFin(), ctjTo);
										}
									}
								}
							}
						}
						if (app.saveChanges() == false) {
							throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!");
						}
					}
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				ec.revert();
				WindowHandler.showError(ex.getMessage());
			}
		}
	}

	private class ActionInverserSelectionJours extends AbstractAction {
		public ActionInverserSelectionJours() {
			super.putValue(NAME, "Inverser...");
			super.putValue(SHORT_DESCRIPTION, "Inverser la sélection des jours");
			super.putValue(SMALL_ICON, new EOClientResourceBundle().getObject("selectAll"));
		}

		public void actionPerformed(ActionEvent e) {
			if (getSelectedRessource() == null) {
				showWarning("Aucune ressource n'est sélectionnée");
				return;
			}
			if (weekPanel.getCurrentButton() == null) {
				showWarning("Aucune semaine n'est sélectionnée !");
				return;
			}
			if (!showConfirmation("Inverser la sélection ???")) {
				return;
			}
			try {
				DayToggleButton[] buttons = dayPanel.dayButtons();
				for (int i = 0; i < buttons.length; i++) {
					DayToggleButton button = buttons[i];
					if (button.isSelected() == false) {
						// aucune sélection sur ce jour, on le sélectionne en entier
						button.setSelected(true);
						selectDay(new ActionEvent(button, -100, button.getText()));
					}
					else {
						if (button.contrainteJour() != null) {
							button.contrainteJour().inverseSelection();
							if (button.contrainteJour().isEmpty()) {
								button.setSelected(false);
								selectDay(new ActionEvent(button, -100, button.getText()));
							}
						}
					}
				}
				if (app.saveChanges() == false) {
					throw new Exception("Impossible de sauvegarder, problème d'enregistrement !!");
				}
				updateJours(weekPanel.getCurrentButton());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				ec.revert();
				refresh();
				WindowHandler.showError(ex.getMessage());
			}
		}
	}

	private class ActionRecopierJoursSemainesVides extends AbstractAction {
		public ActionRecopierJoursSemainesVides() {
			super.putValue(NAME, "Cette semaine sur les semaines vides...");
			super.putValue(SHORT_DESCRIPTION, "Recopier cette semaine type sur les semaines VIDES");
			super.putValue(SMALL_ICON, new EOClientResourceBundle().getObject("recopierplus"));
		}

		public void actionPerformed(ActionEvent e) {
			if (getSelectedRessource() == null) {
				showWarning("Aucune ressource n'est sélectionnée");
				return;
			}
			if (weekPanel.getCurrentButton() == null || weekPanel.getCurrentButton().contrainteSemaine() == null) {
				showWarning("Aucune semaine n'est sélectionnée !");
				return;
			}
			if (!showConfirmation("Vous allez recopier cette semaine type sur toutes les autres semaines VIDES... ok ?")) {
				return;
			}
			try {
				WeekToggleButton[] buttons = weekPanel.weekButtons();
				for (int i = 0; i < buttons.length; i++) {
					WeekToggleButton button = buttons[i];
					if (weekPanel.getCurrentButton().equals(button)) {
						continue;
					}
					if (button.isEnabled()) {
						if (button.date() != null && button.contrainteSemaine() == null) {
							weekPanel.getCurrentButton().contrainteSemaine().duplicateCascade(button.date());
						}
					}
				}
				if (app.saveChanges() == false) {
					throw new Exception("Impossible de sauvegarder, problème d'enregistrement !");
				}
				refresh();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				ec.revert();
				refresh();
				WindowHandler.showError(ex.getMessage());
			}
		}
	}

	private class ActionRecopierJoursToutesSemaines extends AbstractAction {
		public ActionRecopierJoursToutesSemaines() {
			super.putValue(NAME, "Cette semaine sur toutes semaines...");
			super.putValue(SHORT_DESCRIPTION, "Recopier cette semaine type sur TOUTES les semaines");
			super.putValue(SMALL_ICON, new EOClientResourceBundle().getObject("recopierplusplus"));
		}

		public void actionPerformed(ActionEvent e) {
			if (getSelectedRessource() == null) {
				showWarning("Aucune ressource n'est sélectionnée");
				return;
			}
			if (weekPanel.getCurrentButton() == null || weekPanel.getCurrentButton().contrainteSemaine() == null) {
				showWarning("Aucune semaine n'est sélectionnée !");
				return;
			}
			if (!showConfirmation("Vous allez recopier cette semaine type sur TOUTES les autres semaines... ok ?")) {
				return;
			}
			try {
				WeekToggleButton[] buttons = weekPanel.weekButtons();
				for (int i = 0; i < buttons.length; i++) {
					WeekToggleButton button = buttons[i];
					if (weekPanel.getCurrentButton().equals(button)) {
						continue;
					}
					if (button.isEnabled()) {
						if (button.date() != null) {
							if (button.contrainteSemaine() != null) {
								button.deleteCtrlCascade(ec);
							}
							if (button.contrainteSemaine() == null) {
								weekPanel.getCurrentButton().contrainteSemaine().duplicateCascade(button.date());
							}
						}
					}
				}
				if (app.saveChanges() == false) {
					throw new Exception("Impossible de sauvegarder, problème d'enregistrement !");
				}
				refresh();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				ec.revert();
				refresh();
				WindowHandler.showError(ex.getMessage());
			}
		}
	}

	//

	public class DayPlanningChangeListener implements edtscol.client.gestioncontraintes.JPlanning.PlanningListener {
		public void planningSelectionDidChange(PlanningEvent e) {
			selectHours(e);
		}
	}

	public GestionContraintesIndisponibilitesView getMyView() {
		return myView;
	}

	private void initGUISemaines() {
		myView.getPanelSemaines().setLayout(new BorderLayout());
		myView.getPanelSemaines().removeAll();

		// Center...
		ActionListener onLeftClic = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				displayWeekButton((WeekToggleButton) evt.getSource());
			}
		};
		ActionListener onRightClic = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				WeekToggleButton b = (WeekToggleButton) evt.getSource();
				setWeekButtonFull(b, !b.isFull());
			}
		};
		weekPanel = new WeekPanel(((Boolean) app.userInfo("anneeCivile")).booleanValue(), onLeftClic, onRightClic, 50, 24, 11, 4, 2);
		myView.getPanelSemaines().add(weekPanel, BorderLayout.CENTER);

		// LineEnd...
		Box boxBoutonsSemaines = Box.createVerticalBox();

		boutonSelectionSemaines = new JPopupButton("Sélection");
		boutonSelectionSemaines.addActionItem(new ActionSelectionnerSemaines());
		boutonSelectionSemaines.addActionItem(new ActionDeselectionnerSemaines());
		boutonSelectionSemaines.setAlignmentX(Box.CENTER_ALIGNMENT);
		boxBoutonsSemaines.add(boutonSelectionSemaines);

		boxBoutonsSemaines.add(Box.createVerticalStrut(4));

		if (recopieEnabled) {
			boutonRecopieSemaines = new JPopupButton("Recopie");
			boutonRecopieSemaines.addActionItem(new ActionRecopierSemainesFull());
			boutonRecopieSemaines.setAlignmentX(Box.CENTER_ALIGNMENT);
			boxBoutonsSemaines.add(boutonRecopieSemaines);
		}

		myView.getPanelSemaines().add(boxBoutonsSemaines, BorderLayout.LINE_END);
	}

	private void initGUIJours() {
		myView.getPanelJours().setLayout(new BorderLayout(15, 0));
		myView.getPanelJours().removeAll();

		// Center...
		Box panelDayCenter = Box.createHorizontalBox();
		borderDayPanel = BorderFactory.createTitledBorder(panelDayCenter.getBorder(), "Jours & Horaires", TitledBorder.LEFT, TitledBorder.TOP,
				new Font(null, Font.PLAIN, 10));
		panelDayCenter.setBorder(borderDayPanel);

		ActionListener alJours = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				selectDay(evt);
			}
		};
		dayPanel = new DayPanel(alJours, BoxLayout.PAGE_AXIS, 42, PLANNING_HEURES_HAUTEUR_CASE, 12, PLANNING_HEURES_VGAP);
		Box boxDayPanel = Box.createVerticalBox();
		boxDayPanel.setAlignmentY(Box.TOP_ALIGNMENT);
		boxDayPanel.add(Box.createVerticalStrut(12));
		boxDayPanel.add(dayPanel);
		boxDayPanel.add(Box.createVerticalStrut(14));
		panelDayCenter.add(boxDayPanel);

		panelDayCenter.add(Box.createHorizontalStrut(2));

		Box fullDayPlanningPanel = Box.createVerticalBox();
		JPanel dayScale = new JPanel(new FlowLayout(FlowLayout.CENTER, PLANNING_HEURES_HGAP, 0));
		dayScale.setBorder(null);
		Font f = new Font(null, Font.PLAIN, 9);
		Font f2 = new Font(null, Font.PLAIN, 3);
		Dimension d = new Dimension(PLANNING_HEURES_LARGEUR_CASE, 12);
		for (int i = 0; i < 24; i++) {
			JLabel lab = new JLabel(String.valueOf(i), SwingConstants.CENTER);
			lab.setFont(f);
			lab.setPreferredSize(d);
			lab.setMinimumSize(d);
			lab.setMaximumSize(d);
			dayScale.add(lab);
			for (int j = 0; j < 3; j++) {
				JLabel label15min = new JLabel("|", SwingConstants.CENTER);
				label15min.setFont(f2);
				label15min.setPreferredSize(d);
				label15min.setMinimumSize(d);
				label15min.setMaximumSize(d);
				dayScale.add(label15min);
			}
		}
		JLabel lab = new JLabel("", SwingConstants.CENTER);
		lab.setPreferredSize(d);
		lab.setMinimumSize(d);
		lab.setMaximumSize(d);
		dayScale.add(lab);
		fullDayPlanningPanel.add(dayScale);

		dayPlanning = new JPlanning(PLANNING_HEURES_LARGEUR_CASE, PLANNING_HEURES_HAUTEUR_CASE, PLANNING_HEURES_HGAP, PLANNING_HEURES_VGAP, 96, 7,
				JPlanning.SELECTION_HORIZONTALE);
		dayPlanning.setBorder(null);
		dayPlanning.addPlanningListener(new DayPlanningChangeListener());
		fullDayPlanningPanel.add(dayPlanning);

		scrollPaneDayPlanning = new JScrollPane(fullDayPlanningPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneDayPlanning.setBorder(null);
		scrollPaneDayPlanning.setAlignmentY(Box.TOP_ALIGNMENT);
		panelDayCenter.add(scrollPaneDayPlanning);

		myView.getPanelJours().add(panelDayCenter, BorderLayout.CENTER);

		// LineEnd...
		Box boxBoutonsJours = Box.createVerticalBox();

		boutonSelectionJours = new JPopupButton("Sélection");
		boutonSelectionJours.addActionItem(new ActionInverserSelectionJours());
		boutonSelectionJours.setAlignmentX(Box.CENTER_ALIGNMENT);
		boxBoutonsJours.add(boutonSelectionJours);

		boxBoutonsJours.add(Box.createVerticalStrut(4));

		boutonRecopieJours = new JPopupButton("Recopie");
		boutonRecopieJours.addActionItem(new ActionRecopierJoursSemainesVides());
		boutonRecopieJours.addActionItem(new ActionRecopierJoursToutesSemaines());
		boutonRecopieJours.setAlignmentX(Box.CENTER_ALIGNMENT);

		boxBoutonsJours.add(boutonRecopieJours);

		boxBoutonsJours.add(Box.createVerticalGlue());

		// ajout d'une légende
		Dimension dLabels = new Dimension(100, 15);
		JLabel legend = new JLabel("Légende :");
		legend.setAlignmentX(Box.CENTER_ALIGNMENT);
		legend.setPreferredSize(dLabels);
		legend.setMinimumSize(dLabels);
		legend.setMaximumSize(dLabels);
		boxBoutonsJours.add(legend);

		JTextField legendSelected = new JTextField("Indisponible");
		JTextField legendUnselected = new JTextField("Libre");
		legendUnselected.setAlignmentX(Box.CENTER_ALIGNMENT);
		legendUnselected.setBackground(JPlanning.COULEUR_DE_FOND);
		legendUnselected.setBorder(null);
		legendUnselected.setEditable(false);
		legendUnselected.setPreferredSize(dLabels);
		legendUnselected.setMinimumSize(dLabels);
		legendUnselected.setMaximumSize(dLabels);

		legendSelected.setAlignmentX(Box.CENTER_ALIGNMENT);
		legendSelected.setBackground(JPlanning.COULEUR_DE_SELECTION);
		legendSelected.setBorder(null);
		legendSelected.setEditable(false);
		legendSelected.setPreferredSize(dLabels);
		legendSelected.setMinimumSize(dLabels);
		legendSelected.setMaximumSize(dLabels);

		boxBoutonsJours.add(legendUnselected);
		boxBoutonsJours.add(legendSelected);

		myView.getPanelJours().add(boxBoutonsJours, BorderLayout.LINE_END);
	}

	private class JPopupButton extends JButton {

		private JPopupMenu menu = new JPopupMenu();

		public JPopupButton(String text) {
			super(text, (ImageIcon) new EOClientResourceBundle().getObject("d"));
			setHorizontalTextPosition(SwingConstants.LEADING);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menu.show(JPopupButton.this, -1, JPopupButton.this.getHeight());
				}
			});
		}

		public void addActionItem(Action action) {
			menu.add(new JMenuItem(action));
		}
	}

	private boolean showConfirmation(String message) {
		return JOptionPane.showConfirmDialog(myView, message, "Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION;
	}

	private void showWarning(String message) {
		JOptionPane.showMessageDialog(myView, message, "Information", JOptionPane.WARNING_MESSAGE);
	}

	public void onWindowOpened() {
		scrollPaneDayPlanning.getHorizontalScrollBar().setValue((PLANNING_HEURES_LARGEUR_CASE + PLANNING_HEURES_HGAP) * 28 + 3);
	}

	private static NSTimestamp intToTimestamp(int x, NSTimestamp timestamp) {
		// determiner date pour timestamp décalé de x minutes depuis le début du jour 00:00
		int seconds = (x == 1439 ? 59 : 0);
		return timestamp.timestampByAddingGregorianUnits(0, 0, 0, 0, x - timestampToInt(timestamp), seconds);
	}

	private static int timestampToInt(NSTimestamp timestamp) {
		// determiner minute en int depuis le début du jour 00:00 (en minutes)
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(timestamp);
		// heures
		int x = (calendar.get(GregorianCalendar.HOUR_OF_DAY) * 60);
		// minutes
		x += calendar.get(GregorianCalendar.MINUTE);
		return x;
	}

	public WeekPanel getWeekPanel() {
		return weekPanel;
	}

	public IRessourceContrainte getSelectedRessource() {
		return selectedRessource;
	}

	public void setSelectedRessource(IRessourceContrainte selectedRessource) {
		this.selectedRessource = selectedRessource;
	}

}
