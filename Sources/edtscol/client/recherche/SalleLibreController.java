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
package edtscol.client.recherche;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.metier.DepositaireSalles;
import org.cocktail.superplan.client.metier.ImplantationGeo;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.Local;
import org.cocktail.superplan.client.metier.Prises;
import org.cocktail.superplan.client.metier.RepartBatImpGeo;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.ResaSalles;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.TypeSalle;
import org.cocktail.superplan.server.metier.Periodicite;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOMatrix;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import edtscol.client.MainClient;
import edtscol.client.URLUtilitaire;
import edtscol.client.composant.DescriptionSalleCtrl;
import edtscol.client.panier.GestionPanier;
import fr.univlr.common.utilities.DBHandler;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.Matrix;

public class SalleLibreController extends EOInterfaceController implements ActionListener {

	private static final int IDX_SALLES_LIBRES = 0;
	private static final int IDX_TOUTES_SALLES = 1;

	private static final int IDX_DEPOSITAIRE = 0;
	private static final int IDX_DEPOSITAIRE_OU_NON = 1;

	private static final NSArray<EOSortOrdering> SORT_ORDERING_SALLES = new NSArray<EOSortOrdering>(new EOSortOrdering[] {
			EOSortOrdering.sortOrderingWithKey(Salles.SAL_ETAGE_KEY, EOSortOrdering.CompareCaseInsensitiveAscending),
			EOSortOrdering.sortOrderingWithKey(Salles.SAL_PORTE_KEY, EOSortOrdering.CompareCaseInsensitiveAscending) });

	public JButton bRecherche, bValider;
	public JCheckBox cTv, cRetro, cEcran, cInsonorise, cObscure, cVideo, cTel, cInfo;
	public JComboBox comboTypeSalle, comboBatiments, comboImplantationGeo;
	public EODisplayGroup eodSalle;
	public EOMatrix matDeposToutes, matLibre;
	public EOTable tableSalle;
	public JTextField tCapacite, tEtage, tPorte;
	public JCheckBox cbFiltreCapacite;

	private SalleFactory salleFactory;
	private EOEditingContext eContext;

	private NSArray<NSTimestamp> periodesRecherche;
	private NSArray<Salles> selectedObjects, displayedObjects;
	private Integer filtreCapacite = null;

	private boolean modeDepositaire = false;

	public SalleLibreController(EOEditingContext eContext) {
		super(eContext);
		this.eContext = eContext;
		salleFactory = new SalleFactory(eContext);
	}

	public SalleLibreController(EOEditingContext eContext, Integer filtreCapacite) {
		super(eContext);
		this.eContext = eContext;
		salleFactory = new SalleFactory(eContext);
		this.filtreCapacite = filtreCapacite;
	}

	protected void controllerDidLoadArchive(@SuppressWarnings("rawtypes") NSDictionary namedObjects) {
		super.controllerDidLoadArchive(namedObjects);
		init();
	}

	protected void init() {

		try {
			Matrix.setSelectedIndex(IDX_DEPOSITAIRE_OU_NON, matDeposToutes);
			Matrix.setSelectedIndex(IDX_SALLES_LIBRES, matLibre);
			Matrix.setListener(new DeposMatrixListener(), matDeposToutes);
			Matrix.setListener(new LibreMatrixListener(), matLibre);

			MainClient app = (MainClient) EOApplication.sharedApplication();

			if (salleFactory.estDepositaire((IndividuUlr) app.userInfo("individu"))) {
				Matrix.setSelectedIndex(IDX_DEPOSITAIRE, matDeposToutes); // depositaire
				eodSalle.setObjectArray(this.getSallesDepositaire());
			}
			else {
				Matrix.setSelectedIndex(IDX_DEPOSITAIRE_OU_NON, matDeposToutes); // recherche normale
				Matrix.setDisabledIndex(IDX_DEPOSITAIRE, matDeposToutes);
			}

			WidgetHandler.setObjectsToComboBox("(Tous)", salleFactory.getLocalisationsGeo(), comboImplantationGeo);
			comboImplantationGeo.addActionListener(this);

			fetchLocauxForImplantationGeo();

			WidgetHandler.setObjectsToComboBox("(Tous)", salleFactory.getTypesSalles(), comboTypeSalle);
			WidgetHandler.setTableNotEditable(tableSalle);

			if (isModeDepositaire()) {
				Matrix.setDisabledIndex(IDX_DEPOSITAIRE, matDeposToutes);
				Matrix.setDisabledIndex(IDX_DEPOSITAIRE_OU_NON, matDeposToutes);

				Matrix.setDisabledIndex(IDX_SALLES_LIBRES, matLibre);
				Matrix.setDisabledIndex(IDX_TOUTES_SALLES, matLibre);
			}
			if (filtreCapacite != null && filtreCapacite.intValue() > 0) {
				cbFiltreCapacite.setToolTipText("N'afficher que les salles ayant une capacité suffisante pour le nombre d'étudiants prévus.");
				cbFiltreCapacite.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						rechercherSalle();
					}
				});
			}
			else {
				cbFiltreCapacite.setVisible(false);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afficherDescription(Object sender) {
		Salles selectedSalle = (Salles) eodSalle.selectedObject();
		if (selectedSalle != null) {
			DescriptionSalleCtrl descSalle = new DescriptionSalleCtrl(WindowHandler.getWindowFromController(this), eContext);
			descSalle.setSalle(selectedSalle);
			descSalle.open();
		}
	}

	public void rechercherSalle() {
		WindowHandler.setWaitCursor(this);

		NSMutableArray<EOQualifier> sumQualif = new NSMutableArray<EOQualifier>();

		sumQualif.addObject(new EOKeyValueQualifier(Salles.SAL_RESERVABLE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, "O"));
		sumQualif.addObject(new EOKeyValueQualifier(Salles.D_ANNULATION_KEY, EOKeyValueQualifier.QualifierOperatorEqual, NSKeyValueCoding.NullValue));

		if (comboBatiments.getSelectedItem() instanceof Local) {
			sumQualif.addObject(DBHandler.getSimpleQualifier(Salles.LOCAL_KEY, comboBatiments.getSelectedItem()));
		}
		else {
			if (comboImplantationGeo.getSelectedItem() instanceof ImplantationGeo) {
				sumQualif.addObject(DBHandler.getSimpleQualifier(Salles.LOCAL_KEY + "." + Local.REPART_BAT_IMP_GEOS_KEY + "."
						+ RepartBatImpGeo.IMPLANTATION_GEO_KEY, comboImplantationGeo.getSelectedItem()));
			}
		}

		if (comboTypeSalle.getSelectedItem() instanceof TypeSalle) {
			sumQualif.addObject(DBHandler.getSimpleQualifier(Salles.TYPE_SALLE_KEY, comboTypeSalle.getSelectedItem()));
		}

		String capa = tCapacite.getText();
		String etage = tEtage.getText();
		String porte = tPorte.getText();

		if (!capa.equals("")) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.SAL_CAPACITE_KEY + " >= " + capa, null));
		}

		if (!etage.equals("")) {
			sumQualif.addObject(DBHandler.getSimpleQualifier(Salles.SAL_ETAGE_KEY, etage));
		}

		if (!porte.equals("")) {
			sumQualif.addObject(new EOKeyValueQualifier(Salles.SAL_PORTE_KEY, EOKeyValueQualifier.QualifierOperatorCaseInsensitiveLike, "*" + porte
					+ "*"));
		}

		if (cEcran.isSelected()) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.SAL_ECRAN_KEY + " = 'O'", null));
		}

		if (cRetro.isSelected()) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.SAL_RETRO_KEY + " = 'O'", null));
		}

		if (cTv.isSelected()) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.SAL_TELEVISION_KEY + " = 'O'", null));
		}

		if (cObscure.isSelected()) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.SAL_OBSCUR_KEY + " = 'O'", null));
		}

		if (cInsonorise.isSelected()) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.SAL_INSONORISEE_KEY + " = 'O'", null));
		}
		// CM
		if (cInfo.isSelected()) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.PRISESS_KEY + "." + Prises.PRI_TYPE_KEY + " = 'I'", null));
		}

		if (cTel.isSelected()) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.PRISESS_KEY + "." + Prises.PRI_TYPE_KEY + " = 'T'", null));
		}

		if (cVideo.isSelected()) {
			sumQualif.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.PRISESS_KEY + "." + Prises.PRI_TYPE_KEY + " = 'V'", null));
		}
		// fin CM

		boolean isSearchInDepositaire = Matrix.getSelectedIndex(matDeposToutes) == IDX_DEPOSITAIRE;
		boolean isSearchLibreSelection = Matrix.getSelectedIndex(matLibre) == IDX_SALLES_LIBRES;
		boolean isSearchForPeriode = (periodesRecherche != null) && (periodesRecherche.count() > 0);
		boolean isSearchLibre = isSearchLibreSelection && isSearchForPeriode;

		if (isSearchInDepositaire) {
			MainClient app = (MainClient) EOApplication.sharedApplication();
			NSArray<RepartStructure> repartDepositaires = salleFactory.structuresDepositaire((IndividuUlr) app.userInfo("individu"));
			NSArray<StructureUlr> structDepos = (NSArray<StructureUlr>) repartDepositaires.valueForKey(RepartStructure.STRUCTURE_ULR_KEY);

			NSMutableArray<EOQualifier> sumDepos = new NSMutableArray<EOQualifier>();
			for (int i = 0; i < structDepos.count(); i++) {
				sumDepos.addObject(EOQualifier.qualifierWithQualifierFormat(Salles.DEPOSITAIRES_KEY + "." + DepositaireSalles.STRUCTURE_ULR_KEY
						+ " = %@", new NSArray<Object>(structDepos.objectAtIndex(i))));
			}
			sumQualif.addObject(new EOOrQualifier(sumDepos));
		}

		if (cbFiltreCapacite.isSelected() && filtreCapacite != null && filtreCapacite.intValue() > 0) {
			NSMutableArray<EOQualifier> orQuals = new NSMutableArray<EOQualifier>();
			orQuals.addObject(new EOKeyValueQualifier(Salles.SAL_CAPACITE_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThanOrEqualTo,
					filtreCapacite));
			orQuals.addObject(new EOKeyValueQualifier(Salles.SAL_CAPACITE_KEY, EOKeyValueQualifier.QualifierOperatorEqual, NSKeyValueCoding.NullValue));
			sumQualif.addObject(new EOOrQualifier(orQuals));
		}

		NSArray<Salles> salles = Salles.fetchSalleses(eContext, new EOAndQualifier(sumQualif), SORT_ORDERING_SALLES);
		if (isSearchLibre) {
			EOQualifier qualifierDates = qualifierFromPeriodicites(periodesRecherche);
			if (qualifierDates != null) {
				NSArray<ResaSalles> resaSallesOccupees = (NSArray<ResaSalles>) ResaSalles.fetchResaSalleses(eContext, qualifierDates, null);
				if (resaSallesOccupees != null && resaSallesOccupees.count() > 0) {
					NSMutableArray<Salles> sallesOccupees = new NSMutableArray<Salles>();
					for (int i = 0; i < resaSallesOccupees.count(); i++) {
						ResaSalles rs = resaSallesOccupees.objectAtIndex(i);
						if (sallesOccupees.containsObject(rs.salle()) == false) {
							sallesOccupees.addObject(rs.salle());
						}
					}
					NSMutableArray<Salles> sallesLibres = salles.mutableClone();
					sallesLibres.removeObjectsInArray(sallesOccupees);
					salles = sallesLibres.immutableClone();
				}
			}
		}

		eodSalle.setObjectArray(salles);
		URLUtilitaire.informObservingAssociations(eodSalle);
		WindowHandler.setDefaultCursor(this);
	}

	private EOQualifier qualifierFromPeriodicites(NSArray<NSTimestamp> periodicites) {
		if (periodicites != null) {
			NSMutableArray<EOQualifier> qDates = new NSMutableArray<EOQualifier>();
			for (int i = 0; i < periodicites.count(); i += 2) {
				NSTimestamp debut = periodicites.objectAtIndex(i);
				NSTimestamp fin = periodicites.objectAtIndex(i + 1);
				NSMutableArray<EOQualifier> qDate = new NSMutableArray<EOQualifier>();
				qDate.addObject(new EOKeyValueQualifier(ResaSalles.RESERVATION_KEY + "." + Reservation.PERIODICITES_KEY + "."
						+ Periodicite.DATE_DEB_KEY, EOKeyValueQualifier.QualifierOperatorLessThan, fin));
				qDate.addObject(new EOKeyValueQualifier(ResaSalles.RESERVATION_KEY + "." + Reservation.PERIODICITES_KEY + "."
						+ Periodicite.DATE_FIN_KEY, EOKeyValueQualifier.QualifierOperatorGreaterThan, debut));
				qDates.addObject(new EOAndQualifier(qDate));
			}
			return new EOOrQualifier(qDates);
		}
		return null;
	}

	public void setPeriodicitesRecherche(NSArray<NSTimestamp> periodicites) {
		this.periodesRecherche = periodicites;
	}

	public void valider(Object sender) {
		selectedObjects = eodSalle.selectedObjects();
		displayedObjects = eodSalle.displayedObjects();
		WindowHandler.closeModal(this);
	}

	public NSArray<Salles> getSallesTrouvees() {
		return displayedObjects;
	}

	public NSArray<Salles> getSallesSelectionnees() {
		return selectedObjects;
	}

	public NSArray<NSDictionary<String, Object>> getSallesSelectionneesFormatRessource(int format) {
		return GestionPanier.ressourcesFromRecords(selectedObjects, format);
	}

	public void fermer() {
		selectedObjects = new NSArray<Salles>();
		displayedObjects = new NSArray<Salles>();
		WindowHandler.closeModal(this);
	}

	/** getSalleDepositaire */
	private NSArray<Salles> getSallesDepositaire() {
		MainClient app = (MainClient) EOApplication.sharedApplication();
		NSArray<RepartStructure> repartDepositaires = salleFactory.structuresDepositaire((IndividuUlr) app.userInfo("individu"));
		NSArray<StructureUlr> structDepos = (NSArray<StructureUlr>) repartDepositaires.valueForKey(RepartStructure.STRUCTURE_ULR_KEY);

		NSMutableArray<EOQualifier> sum = new NSMutableArray<EOQualifier>();
		for (int i = 0; i < structDepos.count(); i++) {
			sum.addObject(EOQualifier.qualifierWithQualifierFormat(DepositaireSalles.STRUCTURE_ULR_KEY + " = %@",
					new NSArray<Object>(structDepos.objectAtIndex(i))));
		}
		EOQualifier sumQualifier = new EOOrQualifier(sum);
		NSArray<DepositaireSalles> depositairesSalles = DepositaireSalles.fetchDepositaireSalleses(eContext, sumQualifier, null);

		return (NSArray<Salles>) depositairesSalles.valueForKey(DepositaireSalles.SALLE_KEY);
	}

	private void fetchLocauxForImplantationGeo() {
		ImplantationGeo imp = null;
		if (comboImplantationGeo.getSelectedItem() instanceof ImplantationGeo) {
			imp = (ImplantationGeo) comboImplantationGeo.getSelectedItem();
		}
		EOQualifier qualLoc = null;
		if (imp != null) {
			qualLoc = EOQualifier.qualifierWithQualifierFormat(Local.REPART_BAT_IMP_GEOS_KEY + "." + RepartBatImpGeo.IMPLANTATION_GEO_KEY + " = %@",
					new NSArray<ImplantationGeo>(imp));
		}
		EOSortOrdering localOrdering = EOSortOrdering.sortOrderingWithKey(Local.APPELLATION_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);
		NSArray<Local> locaux = Local.fetchLocals(eContext, qualLoc, new NSArray<EOSortOrdering>(localOrdering));
		WidgetHandler.setObjectsToComboBox("(Tous)", locaux, comboBatiments);
		comboBatiments.setSelectedIndex(0);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comboImplantationGeo) {
			fetchLocauxForImplantationGeo();
			rechercherSalle();
		}
		if (e.getSource() == comboBatiments || e.getSource() == comboTypeSalle) {
			rechercherSalle();
		}
	}

	private class DeposMatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			rechercherSalle();
		}
	}

	private class LibreMatrixListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			rechercherSalle();
		}
	}

	public boolean isModeDepositaire() {
		return modeDepositaire;
	}

	public void setModeDepositaire(boolean mode) {
		this.modeDepositaire = mode;
	}

	public void setModeFinder(boolean mode) {
		setModeDepositaire(!mode);
	}

}
