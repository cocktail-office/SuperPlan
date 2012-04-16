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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.webobjects.eoapplication.EOComponentController;

public class MenuHandler extends JMenuBar {

	private static final long serialVersionUID = 9069000344727374041L;
	private MainInterface owner;
	private static final boolean TEST = false;

	JMenuItem miQuitter, miExcel, miViewlet, miTest, miPrefInterface, miRechercheParAp, miRechercheParPers, miOutilTableauDeBordAps, miAide,
			miContraintes, miVacances, miCreneauxLibres, miDepositaireSalles, miExportWeb, miPrefHab, miApropos, miObjets, miHComp, miRecopie,
			miCouleurEc, miDeposObjets, miLotsSalles, miLogsHistorisation;

	public MenuHandler(MainInterface owner) {
		this.owner = owner;

		miQuitter = new JMenuItem("Quitter");
		miRechercheParAp = new JMenuItem("Reservation/AP...");
		miRechercheParPers = new JMenuItem("Reservation/Personne...");
		miOutilTableauDeBordAps = new JMenuItem("Tableau de bord APs...");
		miAide = new JMenuItem("Manuel utilisateur...");
		miViewlet = new JMenuItem("Presentation visuelle...");
		miContraintes = new JMenuItem("Contraintes...");
		miVacances = new JMenuItem("Vacances scolaires...");
		miCreneauxLibres = new JMenuItem("Creneaux libres...");
		miDepositaireSalles = new JMenuItem("Depositaire salles...");
		miExcel = new JMenuItem("Export Excel");
		miPrefInterface = new JMenuItem("Interface...");
		miExportWeb = new JMenuItem("Export Web");
		miPrefHab = new JMenuItem("Diplomes...");

		miHComp = new JMenuItem("Heures complementaires...");
		miRecopie = new JMenuItem("Recopie d'Edt...");

		miObjets = new JMenuItem("Gestion des objets...");

		miCouleurEc = new JMenuItem("Gestion couleurs des EC");

		miDeposObjets = new JMenuItem("Depositaires objets...");

		miApropos = new JMenuItem("A Propos...");

		miLotsSalles = new JMenuItem("Gestion lots de salles...");

		miLogsHistorisation = new JMenuItem("Logs de suppression...");

		if (TEST) {
			miTest = new JMenuItem("Test");
		}

		MenuAction mAction = new MenuAction();

		miQuitter.addActionListener(mAction);
		miRechercheParAp.addActionListener(mAction);
		miRechercheParPers.addActionListener(mAction);
		miOutilTableauDeBordAps.addActionListener(mAction);
		miAide.addActionListener(mAction);
		miViewlet.addActionListener(mAction);
		miContraintes.addActionListener(mAction);
		miVacances.addActionListener(mAction);
		miCreneauxLibres.addActionListener(mAction);
		miDepositaireSalles.addActionListener(mAction);
		miExcel.addActionListener(mAction);
		miPrefInterface.addActionListener(mAction);
		miExportWeb.addActionListener(mAction);
		miPrefHab.addActionListener(mAction);
		miObjets.addActionListener(mAction);
		miApropos.addActionListener(mAction);
		miHComp.addActionListener(mAction);
		miRecopie.addActionListener(mAction);

		miCouleurEc.addActionListener(mAction);

		miLotsSalles.addActionListener(mAction);

		miLogsHistorisation.addActionListener(mAction);

		if (TEST) {
			miTest.addActionListener(mAction);
		}

		miDeposObjets.addActionListener(mAction);

		JMenu mFichier = new JMenu("Fichier");
		add(mFichier);
		mFichier.add(miExportWeb);
		mFichier.add(miExcel);
		mFichier.addSeparator();
		mFichier.add(miQuitter);

		JMenu mOutils = new JMenu("Outils");
		add(mOutils);
		mOutils.add(miHComp);
		// FIXME pdm foireux la recopie, à revoir !
		// mOutils.addSeparator();
		// mOutils.add(miRecopie);
		mOutils.addSeparator();
		mOutils.add(miContraintes);
		mOutils.add(miVacances);
		mOutils.add(miLogsHistorisation);
		mOutils.addSeparator();
		mOutils.add(miObjets);
		mOutils.add(miLotsSalles);
		mOutils.addSeparator();
		mOutils.add(miOutilTableauDeBordAps);

		JMenu mRechercher = new JMenu("Rechercher");
		add(mRechercher);
		mRechercher.add(miCreneauxLibres);
		mRechercher.addSeparator();
		mRechercher.add(miRechercheParAp);
		mRechercher.add(miRechercheParPers);

		JMenu mDepositaires = new JMenu("Depositaires");
		add(mDepositaires);
		mDepositaires.add(miDepositaireSalles);
		mDepositaires.add(miDeposObjets);

		JMenu mPreferences = new JMenu("Preferences");
		add(mPreferences);
		mPreferences.add(miPrefHab);
		mPreferences.add(miPrefInterface);
		mPreferences.add(miCouleurEc);

		JMenu mAide = new JMenu("Aide");
		add(mAide);
		// mAide.add(miAide);
		// mAide.addSeparator();
		mAide.add(miApropos);
		if (TEST) {
			mAide.add(miTest);
		}

		// affectation du menuBar à l'interface owner.
		((EOComponentController) owner.supercontroller()).component().getRootPane().setJMenuBar(this);
	}

	private class MenuAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7058721735114829545L;

		public MenuAction() {
			super();
		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == miQuitter) {
				owner.quitter();
			}
			if (e.getSource() == miRechercheParAp) {
				owner.rechercheReservationsParAp();
			}
			if (e.getSource() == miRechercheParPers) {
				owner.rechercheReservationsParPersonne();
			}
			if (e.getSource() == miOutilTableauDeBordAps) {
				owner.lancerRechercheAps();
			}
			if (e.getSource() == miVacances) {
				owner.gererVacancesScolaires();
			}
			if (e.getSource() == miCreneauxLibres) {
				owner.rechercheCreneauxLibres();
			}
			if (e.getSource() == miDepositaireSalles) {
				owner.ouvrirDepositaireSalles();
			}
			if (e.getSource() == miExcel) {
				owner.exportExcel();
			}
			if (e.getSource() == miViewlet) {
				owner.afficherViewlet();
			}
			if (e.getSource() == miAide) {
				owner.afficherManuel();
			}
			if (e.getSource() == miPrefInterface) {
				owner.afficherPreferencesInterface();
			}
			if (e.getSource() == miExportWeb) {
				owner.exportWeb();
			}
			if (e.getSource() == miPrefHab) {
				owner.afficherPreferencesHabilitation();
			}
			if (e.getSource() == miObjets) {
				owner.lancerGestionObjets();
			}
			if (e.getSource() == miApropos) {
				owner.afficherAPropos();
			}
			if (e.getSource() == miHComp) {
				owner.afficherHComp();
			}
			if (e.getSource() == miRecopie) {
				owner.afficherRecopieEdt();
			}
			if (e.getSource() == miContraintes) {
				owner.gererContraintes();
			}
			if (e.getSource() == miCouleurEc) {
				owner.afficherGestionCouleurEc();
			}
			if (e.getSource() == miDeposObjets) {
				owner.lancerDepositairesObjets();
			}
			if (e.getSource() == miLotsSalles) {
				owner.lancerGestionLotsSalles();
			}
			if (e.getSource() == miLogsHistorisation) {
				owner.lancerConsultationLogsHistorisation();
			}
		}
	}

}
