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

import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Enumeration;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.cocktail.superplan.client.metier.CtrlParamHabilitation;
import org.cocktail.superplan.client.metier.FormationHabilitation;

import com.webobjects.eoapplication.EOSimpleWindowController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import edtscol.client.MainInterface;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;

public class SaisieParametresHabilitationCtrl {

	private SaisieParametresHabilitationView myView;

	private EOEditingContext eContext;
	private MainInterface main;
	private NSArray<FormationHabilitation> habilitations;
	private JCheckBox[] jours;

	public SaisieParametresHabilitationCtrl(EOEditingContext eContext, MainInterface main, NSArray<FormationHabilitation> habilitations, boolean modal) {
		this.eContext = eContext;
		this.main = main;
		this.habilitations = habilitations;
		myView = new SaisieParametresHabilitationView((Frame) ((EOSimpleWindowController) main.supercontroller()).window(), modal);
		init();
	}

	public void open() {
		if (habilitations != null && habilitations.count() > 0) {
			myView.setLocation(WindowHandler.getWindowFromController(main).getX() + 20, WindowHandler.getWindowFromController(main).getY() + 20);
			myView.setVisible(true);
		}
	}

	public void close() {
		myView.setVisible(false);
	}

	private void init() {
		WidgetHandler.decorateButton("Valider", null, "save", myView.getBValider());
		WidgetHandler.decorateButton("Annuler", null, "cancel", myView.getBAnnuler());

		myView.getBValider().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				valider();
			}
		});
		myView.getBAnnuler().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				annuler();
			}
		});

		TimeFocusListener timeFocusListener = new TimeFocusListener();
		myView.getTHrDeb().addFocusListener(timeFocusListener);
		myView.getTHrFin().addFocusListener(timeFocusListener);
		myView.getTMinDeb().addFocusListener(timeFocusListener);
		myView.getTMinFin().addFocusListener(timeFocusListener);

		jours = new JCheckBox[7];
		jours[0] = myView.getjCheckBoxLun();
		jours[1] = myView.getjCheckBoxMar();
		jours[2] = myView.getjCheckBoxMer();
		jours[3] = myView.getjCheckBoxJeu();
		jours[4] = myView.getjCheckBoxVen();
		jours[5] = myView.getjCheckBoxSam();
		jours[6] = myView.getjCheckBoxDim();

		if (habilitations != null && habilitations.count() > 0) {
			boolean identicalHabilitations = true;
			if (habilitations.count() > 1) {
				FormationHabilitation firstHab = habilitations.objectAtIndex(0);
				for (int i = 1; i < habilitations.count() && identicalHabilitations; i++) {
					FormationHabilitation hab = habilitations.objectAtIndex(i);
					if (firstHab != null && hab != null) {
						if (firstHab.equalsCtrl(hab) == false) {
							identicalHabilitations = false;
						}
					}
				}
			}
			if (identicalHabilitations) {
				FormationHabilitation habilitation = habilitations.lastObject();
				if (habilitation.ctrlParamHabilitation() != null) {
					CtrlParamHabilitation cph = habilitation.ctrlParamHabilitation();
					Integer heuredeb = null, heurefin = null;
					for (int i = 0; i < jours.length; i++) {
						if (cph.getHeureDeb(i) != null || cph.getHeureFin(i) != null) {
							jours[i].setSelected(true);
							if (heuredeb == null) {
								heuredeb = cph.getHeureDeb(i);
							}
							if (heurefin == null) {
								heurefin = cph.getHeureFin(i);
							}
						}
					}
					if (heuredeb != null && heurefin != null) {
						myView.getTHrDeb().setText(String.valueOf(heuredeb.intValue() / 60));
						myView.getTMinDeb().setText(String.valueOf(heuredeb.intValue() % 60));
						myView.getTHrFin().setText(String.valueOf(heurefin.intValue() / 60));
						myView.getTMinFin().setText(String.valueOf(heurefin.intValue() % 60));
					}
				}
			}
		}
	}

	public void valider() {
		if (enregistrer()) {
			close();
		}
	}

	private boolean enregistrer() {
		WindowHandler.setWaitCursor(myView);
		String shdeb = myView.getTHrDeb().getText(), shfin = myView.getTHrFin().getText(), smdeb = myView.getTMinDeb().getText(), smfin = myView
				.getTMinFin().getText();
		int hdeb = FormatHandler.strToInt(shdeb);
		int mdeb = FormatHandler.strToInt(smdeb);
		int hfin = FormatHandler.strToInt(shfin);
		int mfin = FormatHandler.strToInt(smfin);
		Integer deb = new Integer((hdeb * 60) + mdeb);
		Integer fin = new Integer((hfin * 60) + mfin);

		Enumeration<FormationHabilitation> habilitationsEnumeration = habilitations.objectEnumerator();
		while (habilitationsEnumeration.hasMoreElements()) {
			FormationHabilitation habilitation = habilitationsEnumeration.nextElement();
			CtrlParamHabilitation cph = habilitation.ctrlParamHabilitation();
			if (cph == null) {
				cph = habilitation.createCtrlParamHabilitationsRelationship();
			}
			for (int i = 0; i < jours.length; i++) {
				if (jours[i].isSelected()) {
					cph.setJourHeures(i, deb, fin);
				}
				else {
					cph.setJourHeures(i, null, null);
				}
			}
		}
		try {
			eContext.saveChanges();
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		WindowHandler.setDefaultCursor(myView);
		return true;
	}

	public void annuler() {
		close();
	}

	private class TimeFocusListener implements FocusListener {
		private static final String DEFAULT_VALUE = "00";

		public void focusGained(FocusEvent e) {
		}

		public void focusLost(FocusEvent e) {
			Object src = e.getSource();
			String value = ((JTextField) src).getText();
			value = value.trim();
			if (!value.equals("")) {
				return;
			}
			if (src == myView.getTHrDeb()) {
				myView.getTHrDeb().setText(DEFAULT_VALUE);
			}
			if (src == myView.getTHrFin()) {
				myView.getTHrFin().setText(DEFAULT_VALUE);
			}
			if (src == myView.getTMinDeb()) {
				myView.getTMinDeb().setText(DEFAULT_VALUE);
			}
			if (src == myView.getTMinFin()) {
				myView.getTMinFin().setText(DEFAULT_VALUE);
			}
		}
	}

	public SaisieParametresHabilitationView getMyView() {
		return myView;
	}

}
