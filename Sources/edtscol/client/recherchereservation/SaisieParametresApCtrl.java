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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Enumeration;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.metier.CtrlParamAp;
import org.cocktail.superplan.client.metier.CtrlParamApIndividu;
import org.cocktail.superplan.client.metier.CtrlParamApObjet;
import org.cocktail.superplan.client.metier.CtrlParamApSalle;
import org.cocktail.superplan.client.metier.CtrlParamApSalleChoix;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.VMaquetteApGroupes;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOSimpleWindowController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import edtscol.client.MainClient;
import edtscol.client.MainInterface;
import edtscol.client.panier.GestionPanier;
import edtscol.client.panier.Panier;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.TimeHandler;

public class SaisieParametresApCtrl {

	private SaisieParametresApView myView;

	public static final int CREATION = 100;
	public static final int EDITION = 200;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	// public DayList dayList = new DayList();
	private EOEditingContext eContext;
	private MainInterface main;
	private Panier panier;
	private NSArray<VMaquetteApGroupes> aps;
	private FormationHabilitation habilitation;
	private JCheckBox[] jours;

	public TimeHandler timeHandler;

	public SaisieParametresApCtrl(EOEditingContext eContext, MainInterface main, NSArray<VMaquetteApGroupes> aps, FormationHabilitation habilitation,
			boolean modal) {
		this.eContext = eContext;
		this.main = main;
		this.aps = aps;
		this.habilitation = habilitation;
		myView = new SaisieParametresApView((Frame) ((EOSimpleWindowController) main.supercontroller()).window(), modal);
		timeHandler = new TimeHandler();
		timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());
		init();
	}

	public void open() {
		if (aps != null && aps.count() > 0) {
			if (aps.count() == 1) {
				panier.initPanierAvecAp(aps.objectAtIndex(0), habilitation.formationAnnee().fannKey());
			}
			myView.setLocation(WindowHandler.getWindowFromController(main).getX() + 20, WindowHandler.getWindowFromController(main).getY() + 20);
			myView.setVisible(true);
		}
	}

	public void close() {
		myView.setVisible(false);
	}

	private void init() {
		if (panier == null) {
			panier = new Panier(eContext, main);
		}
		panier.init();

		myView.getVuePanier().removeAll();
		myView.getVuePanier().add(panier.currentView(), BorderLayout.CENTER);

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
		myView.getTMinDeb().addFocusListener(timeFocusListener);
		myView.getTHrFin().addFocusListener(timeFocusListener);
		myView.getTMinFin().addFocusListener(timeFocusListener);
		myView.getTHrDuree().addFocusListener(timeFocusListener);
		myView.getTMinDuree().addFocusListener(timeFocusListener);

		panier.setEnabledTab(Panier.TAB_IDX_PERSONNE, true);
		panier.setEnabledTab(Panier.TAB_IDX_ENSEIGNEMENT, false);
		panier.setEnabledTab(Panier.TAB_IDX_EXAMEN, false);
		panier.setEnabledTab(Panier.TAB_IDX_OBJET, true);
		panier.setEnabledTab(Panier.TAB_IDX_SALLE, true);

		jours = new JCheckBox[7];
		jours[0] = myView.getjCheckBoxLun();
		jours[1] = myView.getjCheckBoxMar();
		jours[2] = myView.getjCheckBoxMer();
		jours[3] = myView.getjCheckBoxJeu();
		jours[4] = myView.getjCheckBoxVen();
		jours[5] = myView.getjCheckBoxSam();
		jours[6] = myView.getjCheckBoxDim();

		if (aps != null && aps.count() > 0) {
			ICtrlParam ctrlParam = null;

			// vérifie s'il y a au moins un ctrlAp...
			boolean hasCtrlAps = false;
			for (int i = 0; i < aps.count() && !hasCtrlAps; i++) {
				VMaquetteApGroupes vap = aps.objectAtIndex(i);
				if (vap != null && vap.ctrlParamAp() != null) {
					hasCtrlAps = true;
				}
			}
			// s'il y en a, on vérifie qu'ils sont tous identiques
			if (hasCtrlAps) {
				boolean identicalCtrlAps = true;
				if (aps.count() > 1) {
					VMaquetteApGroupes firstVap = aps.objectAtIndex(0);
					for (int i = 1; i < aps.count() && identicalCtrlAps; i++) {
						VMaquetteApGroupes vap = aps.objectAtIndex(i);
						if (firstVap != null && vap != null) {
							if (firstVap.equalsCtrl(vap) == false) {
								identicalCtrlAps = false;
							}
						}
					}
				}
				// s'ils sont tous identiques, on peut afficher...
				if (identicalCtrlAps) {
					VMaquetteApGroupes vap = aps.lastObject();
					if (vap.ctrlParamAp() != null) {
						ctrlParam = vap.ctrlParamAp();
					}
				}
			}
			// sinon, comme il n'y a aucun ctrlAp, on voit si on a une ctrlHabilitation au-dessus pour affichage
			else {
				if (habilitation != null && habilitation.ctrlParamHabilitation() != null) {
					ctrlParam = habilitation.ctrlParamHabilitation();
				}
			}
			// si on a des infos a afficher...
			if (ctrlParam != null) {
				Integer heuredeb = null, heurefin = null;
				for (int i = 0; i < jours.length; i++) {
					if (ctrlParam.getHeureDeb(i) != null || ctrlParam.getHeureFin(i) != null) {
						jours[i].setSelected(true);
						if (heuredeb == null) {
							heuredeb = ctrlParam.getHeureDeb(i);
						}
						if (heurefin == null) {
							heurefin = ctrlParam.getHeureFin(i);
						}
					}
				}
				if (heuredeb != null && heurefin != null) {
					myView.getTHrDeb().setText(String.valueOf(heuredeb.intValue() / 60));
					myView.getTMinDeb().setText(String.valueOf(heuredeb.intValue() % 60));
					myView.getTHrFin().setText(String.valueOf(heurefin.intValue() / 60));
					myView.getTMinFin().setText(String.valueOf(heurefin.intValue() % 60));
				}

				// si en plus c'est un ctrl de type paramAp, on a des infos supplémentaires a afficher...
				if (ctrlParam instanceof CtrlParamAp) {
					CtrlParamAp cpa = (CtrlParamAp) ctrlParam;
					if (cpa.cpaDuree() != null) {
						myView.getTHrDuree().setText(String.valueOf(cpa.cpaDuree().intValue() / 60));
						myView.getTMinDuree().setText(String.valueOf(cpa.cpaDuree().intValue() % 60));
					}

					NSArray<IndividuUlr> individus = (NSArray<IndividuUlr>) cpa.valueForKeyPath(CtrlParamAp.CTRL_PARAM_AP_INDIVIDUS_KEY + "."
							+ CtrlParamApIndividu.INDIVIDU_KEY);
					panier.addResources(GestionPanier.ressourcesFromRecords(individus, GestionPanier.PERSONNE));

					NSArray<Salles> salles = (NSArray<Salles>) cpa.valueForKeyPath(CtrlParamAp.CTRL_PARAM_AP_SALLES_KEY + "."
							+ CtrlParamApSalle.SALLE_KEY);
					panier.addResources(GestionPanier.ressourcesFromRecords(salles, GestionPanier.SALLE));

					NSArray<Salles> sallesSouhaitees = (NSArray<Salles>) cpa.valueForKeyPath(CtrlParamAp.CTRL_PARAM_AP_SALLE_CHOIXS_KEY + "."
							+ CtrlParamApSalleChoix.SALLE_KEY);
					panier.addResources(GestionPanier.ressourcesFromRecords(sallesSouhaitees, GestionPanier.CHOIX));

					NSArray<ResaObjet> objets = (NSArray<ResaObjet>) cpa.valueForKeyPath(CtrlParamAp.CTRL_PARAM_AP_OBJETS_KEY + "."
							+ CtrlParamApObjet.RESA_OBJET_KEY);
					panier.addResources(GestionPanier.ressourcesFromRecords(objets, GestionPanier.OBJET));
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
				.getTMinFin().getText(), shduree = myView.getTHrDuree().getText(), smduree = myView.getTMinDuree().getText();
		if (StringCtrl.isEmpty(smdeb)) {
			smdeb = "00";
		}
		if (StringCtrl.isEmpty(smfin)) {
			smfin = "00";
		}
		if (StringCtrl.isEmpty(smduree)) {
			smduree = "00";
		}

		if (!testEntries(shdeb, smdeb, shfin, smfin, shduree, smduree)) {
			WindowHandler.setDefaultCursor(myView);
			myView.getBValider().setEnabled(true);
			return false;
		}

		int hdeb = FormatHandler.strToInt(shdeb);
		int mdeb = FormatHandler.strToInt(smdeb);
		int hfin = FormatHandler.strToInt(shfin);
		int mfin = FormatHandler.strToInt(smfin);
		int hduree = FormatHandler.strToInt(shduree);
		int mduree = FormatHandler.strToInt(smduree);
		Integer deb = new Integer((hdeb * 60) + mdeb);
		Integer fin = new Integer((hfin * 60) + mfin);
		Integer duree = new Integer((hduree * 60) + mduree);

		NSArray<IndividuUlr> individus = panier.getResourcesWithType("PERSONNE");
		NSArray<Salles> salles = panier.getResourcesWithType("SALLE");
		NSArray<Salles> choixSalles = panier.getResourcesWithType("CHOIX");
		NSArray<ResaObjet> objets = panier.getResourcesWithType("OBJET");

		Enumeration<VMaquetteApGroupes> apsEnumeration = aps.objectEnumerator();
		while (apsEnumeration.hasMoreElements()) {
			VMaquetteApGroupes vap = apsEnumeration.nextElement();
			CtrlParamAp cpa = vap.ctrlParamAp();
			if (cpa == null) {
				cpa = vap.maquetteAp().createCtrlParamApsRelationship();
			}
			for (int i = 0; i < jours.length; i++) {
				if (jours[i].isSelected()) {
					cpa.setJourHeures(i, deb, fin);
				}
				else {
					cpa.setJourHeures(i, null, null);
				}
			}
			cpa.setMaquetteApRelationship(vap.maquetteAp());
			cpa.setCpaDuree(duree);
			cpa.setScolGroupeGrpRelationship(vap.scolGroupeGrp());

			cpa.deleteAllCtrlParamApIndividusRelationships();
			if (individus != null) {
				Enumeration<IndividuUlr> eIndividus = individus.objectEnumerator();
				while (eIndividus.hasMoreElements()) {
					IndividuUlr individu = eIndividus.nextElement();
					CtrlParamApIndividu cpai = cpa.createCtrlParamApIndividusRelationship();
					cpai.setIndividuRelationship(individu);
				}
			}
			cpa.deleteAllCtrlParamApSallesRelationships();
			if (salles != null) {
				Enumeration<Salles> eSalles = salles.objectEnumerator();
				while (eSalles.hasMoreElements()) {
					Salles salle = eSalles.nextElement();
					CtrlParamApSalle cpas = cpa.createCtrlParamApSallesRelationship();
					cpas.setSalleRelationship(salle);
				}
			}
			cpa.deleteAllCtrlParamApSalleChoixsRelationships();
			if (choixSalles != null) {
				Enumeration<Salles> eChoixSalles = choixSalles.objectEnumerator();
				while (eChoixSalles.hasMoreElements()) {
					Salles choixSalle = eChoixSalles.nextElement();
					CtrlParamApSalleChoix cpasc = cpa.createCtrlParamApSalleChoixsRelationship();
					cpasc.setSalleRelationship(choixSalle);
				}
			}
			cpa.deleteAllCtrlParamApObjetsRelationships();
			if (objets != null) {
				Enumeration<ResaObjet> eObjets = objets.objectEnumerator();
				while (eObjets.hasMoreElements()) {
					ResaObjet objet = eObjets.nextElement();
					CtrlParamApObjet cpao = cpa.createCtrlParamApObjetsRelationship();
					cpao.setResaObjetRelationship(objet);
				}
			}
		}
		try {
			eContext.saveChanges();
			DBHandler.invalidateObjects(eContext, (NSArray<CtrlParamAp>) aps.valueForKey(VMaquetteApGroupes.CTRL_PARAM_AP_KEY));
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

	private boolean testEntries(String hdeb, String mdeb, String hfin, String mfin, String hduree, String mduree) {
		if (hdeb.equals("") || mdeb.equals("")) {
			WindowHandler.showError("Donnez l'heure de début");
			return false;
		}
		if (hfin.equals("") || mfin.equals("")) {
			WindowHandler.showError("Donnez l'heure de fin");
			return false;
		}
		if (hduree.equals("") || mduree.equals("")) {
			WindowHandler.showError("Donnez la durée");
			return false;
		}
		return true;
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
			if (src == myView.getTMinDeb()) {
				myView.getTMinDeb().setText(DEFAULT_VALUE);
			}
			if (src == myView.getTHrFin()) {
				myView.getTHrFin().setText(DEFAULT_VALUE);
			}
			if (src == myView.getTMinFin()) {
				myView.getTMinFin().setText(DEFAULT_VALUE);
			}
			if (src == myView.getTHrDuree()) {
				myView.getTHrDuree().setText(DEFAULT_VALUE);
			}
			if (src == myView.getTMinDuree()) {
				myView.getTMinDuree().setText(DEFAULT_VALUE);
			}
		}
	}

	public SaisieParametresApView getMyView() {
		return myView;
	}

}
