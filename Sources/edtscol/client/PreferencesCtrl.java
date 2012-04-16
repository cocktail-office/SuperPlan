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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.metier.PrefUser;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;

public class PreferencesCtrl {

	private PreferencesView myView;
	private Component parent;
	private PrefUser prefUser;
	private MainClient app = (MainClient) EOApplication.sharedApplication();

	public static final String[] VISIBILITE_STRINGS = new String[] { "Service", "Publique", "Privée" };
	public static final String[] TYPE_RESA_STRINGS = new String[] { "Enseignement", "Réunion", "Examen", "Blocage", "Semestre" };

	public PreferencesCtrl(EOEditingContext eContext, Component parent, boolean modal) {
		this.prefUser = app.getPrefUser();
		this.parent = parent;
		myView = new PreferencesView(new javax.swing.JFrame(), modal);
		init();
	}

	public void open() {
		myView.setLocation(parent.getX() + 20, parent.getY() + 20);
		myView.setVisible(true);
	}

	private void init() {

		myView.getBtEnregistrer().addActionListener(new SaveValeursImpressionListener());
		myView.getBtEnregistrer2().addActionListener(new SaveIntervalleMinimumListener());

		WidgetHandler.setObjectsToComboBox(new NSArray<String>(VISIBILITE_STRINGS), myView.getCbVisibilite());
		WidgetHandler.setObjectsToComboBox(new NSArray<String>(TYPE_RESA_STRINGS), myView.getCbTypeResa());

		if (prefUser.anneeCivile() != null) {
			if (prefUser.anneeCivile().intValue() == 1) {
				myView.getAnneeCivileOui().setSelected(true);
			}
		}
		if ("O".equals(prefUser.colorEc())) {
			myView.getColorECOui().setSelected(true);
		}
		if (prefUser.dispParCom().intValue() == 1) {
			myView.getParcoursComOui().setSelected(true);
		}
		if (prefUser.commentScol().intValue() == 1) {
			myView.getComEnsOui().setSelected(true);
		}
		if (prefUser.dispExam().intValue() == 1) {
			myView.getExamOui().setSelected(true);
		}
		if (prefUser.useRespAp().equals("O")) {
			myView.getRespApOui().setSelected(true);
		}
		if (app.isSendMail() == false) {
			myView.getLabelSendMailOccupants().setEnabled(false);
			myView.getSendMailOccupantsOui().setEnabled(false);
			myView.getSendMailOccupantsNon().setEnabled(false);
			myView.getSendMailOccupantsNon().setSelected(true);
		}
		else {
			if ("O".equals(prefUser.sendMailOccupants())) {
				myView.getSendMailOccupantsOui().setSelected(true);
			}
		}
		if (app.isSendMailDepositaires() == false) {
			myView.getSendMailDepositairesNon().setSelected(true);
		}
		if ("O".equals(prefUser.selectionGroupeMultiple())) {
			myView.getMultSelectionGrpOui().setSelected(true);
		}
		if ("O".equals(prefUser.useTooltipPlanning())) {
			myView.getTooltipPlanningOui().setSelected(true);
		}

		if (prefUser.affHorairesHamac() != null) {
			if (prefUser.affHorairesHamac().intValue() == 1) {
				myView.getAffHorairesHamacOui().setSelected(true);
			}
		}

		if (prefUser.defaultVisibilite() == null) {
			myView.getCbVisibilite().setSelectedItem(VISIBILITE_STRINGS[1]);
		}
		else {
			myView.getCbVisibilite().setSelectedItem(prefUser.defaultVisibilite());
		}
		if (prefUser.defaultTypeResa() == null) {
			myView.getCbTypeResa().setSelectedItem(TYPE_RESA_STRINGS[0]);
		}
		else {
			myView.getCbTypeResa().setSelectedItem(prefUser.defaultTypeResa());
		}

		myView.gettDebImpress().setText(String.valueOf(prefUser.heureDebImpress()));
		myView.gettFinImpress().setText(String.valueOf(prefUser.heureFinImpress()));
		if (prefUser.intervalleMinimum() != null) {
			myView.gettIntervalleMinimum().setText(String.valueOf(prefUser.intervalleMinimum()));
		}

		myView.getAnneeCivileOui().addActionListener(new UseAnneeCivile());
		myView.getAnneeCivileNon().addActionListener(new UseAnneeCivile());
		myView.getColorECOui().addActionListener(new ColorECListener());
		myView.getColorECNon().addActionListener(new ColorECListener());
		myView.getParcoursComOui().addActionListener(new ParcoursComListener());
		myView.getParcoursComNon().addActionListener(new ParcoursComListener());
		myView.getComEnsOui().addActionListener(new CommentaireEnsListener());
		myView.getComEnsNon().addActionListener(new CommentaireEnsListener());
		myView.getExamOui().addActionListener(new DispExamListener());
		myView.getExamNon().addActionListener(new DispExamListener());
		myView.getRespApOui().addActionListener(new RespApListener());
		myView.getRespApNon().addActionListener(new RespApListener());
		myView.getSendMailOccupantsOui().addActionListener(new SendMailOccupantsListener());
		myView.getSendMailOccupantsNon().addActionListener(new SendMailOccupantsListener());
		// myView.getSendMailDepositairesOui().addActionListener(new SendMailDepositairesListener());
		// myView.getSendMailDepositairesNon().addActionListener(new SendMailDepositairesListener());
		myView.getMultSelectionGrpOui().addActionListener(new MultSelectionGrpListener());
		myView.getMultSelectionGrpNon().addActionListener(new MultSelectionGrpListener());
		myView.getTooltipPlanningOui().addActionListener(new TooltipPlanningListener());
		myView.getTooltipPlanningNon().addActionListener(new TooltipPlanningListener());
		myView.getCbVisibilite().addActionListener(new VisibiliteListener());
		myView.getCbTypeResa().addActionListener(new TypeResaListener());
		myView.getAffHorairesHamacOui().addActionListener(new AffHorairesHamacListener());
		myView.getAffHorairesHamacNon().addActionListener(new AffHorairesHamacListener());
		myView.gettDebImpress().addActionListener(new SaveValeursImpressionListener());
		myView.gettFinImpress().addActionListener(new SaveValeursImpressionListener());
		myView.gettIntervalleMinimum().addActionListener(new SaveIntervalleMinimumListener());
	}

	private void setColorEc() {
		if (myView.getColorECOui().isSelected()) {
			prefUser.setColorEc("O");
		}
		else {
			prefUser.setColorEc("N");
		}
		app.saveChanges();
	}

	private void setParcoursCom() {
		if (myView.getParcoursComOui().isSelected()) {
			prefUser.setDispParCom(new Integer(1));
		}
		else {
			prefUser.setDispParCom(new Integer(0));
		}
		app.saveChanges();
	}

	private void setAnneeCivile() {
		if (myView.getAnneeCivileOui().isSelected()) {
			prefUser.setAnneeCivile(new Integer(1));
		}
		else {
			prefUser.setAnneeCivile(new Integer(0));
		}
		app.saveChanges();
	}

	public void saveValeursImpression() {
		Integer debImpress = null, finImpress = null;
		try {
			debImpress = new Integer(myView.gettDebImpress().getText());
			finImpress = new Integer(myView.gettFinImpress().getText());
		}
		catch (Exception e) {
			WindowHandler.showError("Merci de saisir des nombres");
			return;
		}

		if (prefUser.heureDebImpress().intValue() != debImpress.intValue() || prefUser.heureFinImpress().intValue() != finImpress.intValue()) {
			prefUser.setHeureDebImpress(debImpress);
			prefUser.setHeureFinImpress(finImpress);
			app.saveChanges();
		}
	}

	public void saveIntervalleMinimum() {
		Integer intervalleMinimum = null;
		try {
			if (StringCtrl.isEmpty(myView.gettIntervalleMinimum().getText()) == false) {
				intervalleMinimum = new Integer(myView.gettIntervalleMinimum().getText());
			}
		}
		catch (Exception e) {
			WindowHandler.showError("Merci de saisir un nombre...");
			return;
		}

		if (prefUser.intervalleMinimum() == null || intervalleMinimum == null
				|| prefUser.intervalleMinimum().intValue() != intervalleMinimum.intValue()) {
			prefUser.setIntervalleMinimum(intervalleMinimum);
			app.saveChanges();
		}
	}

	private void setCommentaireEns() {
		if (myView.getComEnsOui().isSelected()) {
			prefUser.setCommentScol(new Integer(1));
		}
		else {
			prefUser.setCommentScol(new Integer(0));
		}
		app.saveChanges();
	}

	private void setVisibilite() {
		prefUser.setDefaultVisibilite((String) myView.getCbVisibilite().getSelectedItem());
		app.saveChanges();
	}

	private void setTypeResa() {
		prefUser.setDefaultTypeResa((String) myView.getCbTypeResa().getSelectedItem());
		app.saveChanges();
	}

	private void setDispExam() {
		if (myView.getExamOui().isSelected()) {
			prefUser.setDispExam(new Integer(1));
		}
		else {
			prefUser.setDispExam(new Integer(0));
		}
		app.saveChanges();
	}

	public void setRespAp() {
		if (myView.getRespApOui().isSelected()) {
			prefUser.setUseRespAp("O");
		}
		else {
			prefUser.setUseRespAp("N");
		}
		app.saveChanges();
	}

	public void setSendMailOccupants() {
		if (myView.getSendMailOccupantsOui().isSelected()) {
			prefUser.setSendMailOccupants("O");
		}
		else {
			prefUser.setSendMailOccupants("N");
		}
		app.saveChanges();
	}

	// private void setSendMailDepositaires() {
	// if (myView.getSendMailDepositairesOui().isSelected()) {
	// prefUser.setSendMailDepositaires("O");
	// }
	// else {
	// prefUser.setSendMailDepositaires("N");
	// }
	// app.saveChanges();
	//
	// }

	private void setMultSelectionGrp() {
		if (myView.getMultSelectionGrpOui().isSelected()) {
			prefUser.setSelectionGroupeMultiple("O");
		}
		else {
			prefUser.setSelectionGroupeMultiple("N");
		}
		app.saveChanges();
	}

	private void setTooltipPlanning() {
		if (myView.getTooltipPlanningOui().isSelected()) {
			prefUser.setUseTooltipPlanning("O");
		}
		else {
			prefUser.setUseTooltipPlanning("N");
		}
		app.saveChanges();
	}

	private void setAffHorairesHamac() {
		if (myView.getAffHorairesHamacOui().isSelected()) {
			prefUser.setAffHorairesHamac(new Integer(1));
		}
		else {
			prefUser.setAffHorairesHamac(new Integer(0));
		}
		app.saveChanges();
	}

	private class ColorECListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setColorEc();
		}
	}

	private class UseAnneeCivile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setAnneeCivile();
		}
	}

	private class ParcoursComListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setParcoursCom();
		}
	}

	private class CommentaireEnsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setCommentaireEns();
		}
	}

	private class VisibiliteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisibilite();
		}
	}

	private class TypeResaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setTypeResa();
		}
	}

	private class DispExamListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setDispExam();
		}
	}

	private class RespApListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setRespAp();
		}
	}

	private class SendMailOccupantsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setSendMailOccupants();
		}
	}

	// private class SendMailDepositairesListener implements ActionListener {
	// public void actionPerformed(ActionEvent e) {
	// setSendMailDepositaires();
	// }
	// }

	private class MultSelectionGrpListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setMultSelectionGrp();
		}
	}

	private class TooltipPlanningListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setTooltipPlanning();
		}
	}

	private class AffHorairesHamacListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setAffHorairesHamac();
		}
	}

	private class SaveValeursImpressionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			saveValeursImpression();
		}
	}

	private class SaveIntervalleMinimumListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			saveIntervalleMinimum();
		}
	}

}
