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
package edtscol.client.panier;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.cocktail.superplan.client.factory.EnseignementFactory;
import org.cocktail.superplan.client.factory.SalleFactory;
import org.cocktail.superplan.client.factory.VerificationFactory;
import org.cocktail.superplan.client.metier.DepositaireSalles;
import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.FormationHabilitation;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.MaquetteAp;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.Salles;
import org.cocktail.superplan.client.metier.ScolGroupeObjet;
import org.cocktail.superplan.client.metier.StructureUlr;
import org.cocktail.superplan.client.metier.VMaquetteApGroupes;
import org.cocktail.superplan.client.metier.VParcoursAp;

import com.webobjects.eoapplication.EOApplication;
import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eoapplication.EOInterfaceController;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.eointerface.swing.EOTable;
import com.webobjects.eointerface.swing.EOView;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import edtscol.client.MainClient;
import edtscol.client.MainInterface;
import edtscol.client.VerifDisponibilite;
import edtscol.client.gestionreservation.InspecteurCtrl;
import edtscol.client.recherche.MatiereExtController;
import edtscol.client.recherche.Recherche;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.Matrix;

public class Panier extends EOInterfaceController {

	// Les index des différents onglets du panier
	public static final int TAB_IDX_PERSONNE = 0;
	public static final int TAB_IDX_SALLE = 1;
	public static final int TAB_IDX_ENSEIGNEMENT = 2;
	public static final int TAB_IDX_EXAMEN = 3;
	public static final int TAB_IDX_OBJET = 4;

	public JButton bAjoutRessource, bSupRessource;
	public EOView currentView;
	public EODisplayGroup eodRessources;
	public JTabbedPane tabsRessources;
	public EOTable tvRessources;

	private EOEditingContext eContext;
	private MainInterface mainInterface;
	private InspecteurCtrl owner;
	private Recherche recherche;

	private VMaquetteApGroupes vapUniqueNonVisible = null;

	protected int typeRecherche;
	protected int typeReservation;

	public Panier(EOEditingContext eContext, MainInterface mainInterface) {
		super(eContext);
		this.mainInterface = mainInterface;
		this.eContext = eContext;
		EOArchive.loadArchiveNamed("Panier", this, null, null);
		this.establishConnection();
	}

	public void rechargerEnseignements(FormationAnnee fAnnee) {
		MatiereExtController matiereCtrl = recherche.getMatiereController();
		matiereCtrl.rechargerEnseignements(fAnnee);
	}

	/**  */
	// CM edtscol verif disponibilite 10/04/2007
	public void init() {
		this.owner = null;
		initCommun();
	}

	public void init(InspecteurCtrl ic) {
		this.owner = ic;
		initCommun();
	}

	public void initCommun() {

		this.initWidgets();
		recherche = new Recherche(eContext, mainInterface, this, Recherche.INSPECTEUR);
		EOArchive.loadArchiveNamed("Recherche", recherche, "edtscol.client.recherche", recherche.disposableRegistry());
		// recherche.createSubComponents();

		if (recherche == null) {
			return;
		}

		if (tabsRessources == null) {
			return;
		}

		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(recherche.getIndividuController().currentView());
		tabsRessources.addTab("Personne", p1);

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(recherche.getRechercheSalle().currentView());
		tabsRessources.addTab("Salle", p2);

		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		p3.add(recherche.getMatiereController().currentView());
		tabsRessources.addTab("Enseignement", p3);

		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
		p4.add(recherche.getExamenController().currentView());
		tabsRessources.addTab("Examen", p4);

		JPanel p5 = new JPanel();
		p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));
		p5.add(recherche.getObjetController().currentView());
		tabsRessources.addTab("Objet", p5);

		tabsRessources.addChangeListener(new TabListener());

	}

	public void initPanier(NSArray<NSDictionary<String, Object>> lesInfos) {
		// Init du panier avec ressource passée en param...
		if (lesInfos == null || lesInfos.count() == 0) {
			return;
		}
		NSDictionary<String, Object> unInfo = lesInfos.objectAtIndex(0);
		Integer type = (Integer) unInfo.objectForKey("type");

		if (type.intValue() == 0) {
			for (int i = 0; i < lesInfos.count(); i++) {
				NSDictionary<String, Object> informations = lesInfos.objectAtIndex(i);
				IndividuUlr currentInd = (IndividuUlr) informations.objectForKey("resRecord");
				String[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
				Object[] objects = { "PERSONNE", currentInd.nomPrenom(), NSKeyValueCoding.NullValue, "1", currentInd };
				NSDictionary<String, Object> ressource = new NSDictionary<String, Object>(objects, keys);
				eodRessources.insertObjectAtIndex(ressource, 0);
				eodRessources.updateDisplayedObjects();
			}
		}
		if (type.intValue() == 1) {
			for (int i = 0; i < lesInfos.count(); i++) {
				NSDictionary<String, Object> informations = lesInfos.objectAtIndex(i);
				if (!checkSalle(informations)) {
					continue;
				}
				Salles currentSalle = (Salles) informations.objectForKey("resRecord");
				if (SalleFactory.testIndividuADroitReserverSalle(eContext, (IndividuUlr) mainInterface.app.userInfo("individu"), currentSalle,
						mainInterface.app.isReservationSalleParDepositaire())) {
					String libelle = currentSalle.salPorte() + " - " + currentSalle.local().appellation();
					String[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
					Object[] objects = { "SALLE", libelle, NSKeyValueCoding.NullValue, "1", currentSalle };
					NSDictionary<String, Object> ressource = new NSDictionary<String, Object>(objects, keys);
					eodRessources.insertObjectAtIndex(ressource, 0);
					eodRessources.updateDisplayedObjects();
				}
			}
		}
		if (type.intValue() == 2) {
			NSDictionary<String, Object> informations = lesInfos.objectAtIndex(0);
			owner.setSelectedIndex(0, owner.getMyView().getMatTypeResa());
			tabsRessources.setSelectedIndex(2);
			if (informations.objectForKey("formation") != null) {
				// if (informations.objectForKey("anneeScol") != null && informations.objectForKey("formation") != null
				// && informations.objectForKey("semestre") != null) {
				// NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>(2);
				// quals.addObject(new EOKeyValueQualifier(FormationHabilitation.FORMATION_ANNEE_KEY + "." + FormationAnnee.FANN_DEBUT_KEY,
				// EOKeyValueQualifier.QualifierOperatorEqual, informations.objectForKey("anneeScol")));
				// quals.addObject(new EOKeyValueQualifier(FormationHabilitation.FORMATION_SPECIALISATION_KEY,
				// EOKeyValueQualifier.QualifierOperatorEqual, informations.objectForKey("formation")));
				// NSArray<FormationHabilitation> array = FormationHabilitation.fetchFormationHabilitations(eContext, new
				// EOAndQualifier(quals), null);
				// System.out.println("array = " + array);
				// recherche.getMatiereExtController().eodHabilitation.setObjectArray(array);
				recherche.getMatiereController().eodHabilitation.setObjectArray(new NSArray<FormationHabilitation>(
						(FormationHabilitation) informations.objectForKey("formation")));
				recherche.getMatiereController().eodHabilitation.updateDisplayedObjects();
			}

			if (informations.objectForKey("resRecord") != null && informations.objectForKey("semestre") != null) {
				recherche.getMatiereController().comboParcours.setSelectedItem(informations.objectForKey("resRecord"));
				recherche.getMatiereController().comboSemestres.setSelectedItem(informations.objectForKey("semestre"));
				if (informations.objectForKey("typeAp") != null) {
					Matrix.setSelectedIndex(((Integer) informations.objectForKey("typeAp")).intValue(), recherche.getMatiereController().matTypeAp);
				}
				recherche.getMatiereController().afficherAps();
				if (informations.objectForKey("ap") != null) {
					MaquetteAp ap = (MaquetteAp) informations.objectForKey("ap");
					NSArray<VParcoursAp> vParcoursAps = ap.vParcoursAps(new EOKeyValueQualifier(VParcoursAp.PARCOURS_KEY,
							EOKeyValueQualifier.QualifierOperatorEqual, informations.objectForKey("resRecord")));
					if (vParcoursAps != null && vParcoursAps.count() > 0) {
						recherche.getMatiereController().comboAps.setSelectedItem(vParcoursAps.objectAtIndex(0));
					}
				}
				recherche.getMatiereController().afficherGroupesSemestre();
				if (informations.objectForKey("groupe") != null) {
					recherche.getMatiereController().comboGroupes.setSelectedItem(informations.objectForKey("groupe"));
				}
			}
		}
		if (type.intValue() == 4) {
			for (int i = 0; i < lesInfos.count(); i++) {
				NSDictionary<String, Object> informations = lesInfos.objectAtIndex(i);
				ResaObjet currentObjet = (ResaObjet) informations.objectForKey("resRecord");
				// vérifie si on a le droit d'utiliser cet objet...
				if (VerificationFactory.testIndividuADroitReserverObjet(eContext, (IndividuUlr) mainInterface.app.userInfo("individu"), currentObjet)) {
					String[] keys = { "resType", "resLibelle", "resDepos", "resUnite", "resRecord" };
					Object[] objects = { "OBJET", currentObjet.roLibelle1(), NSKeyValueCoding.NullValue, "2", currentObjet };
					NSDictionary<String, Object> ressource = new NSDictionary<String, Object>(objects, keys);
					eodRessources.insertObjectAtIndex(ressource, 0);
					eodRessources.updateDisplayedObjects();
				}
			}
		}
	}

	public void initPanierAvecAp(VMaquetteApGroupes vap, Integer fannKey) {
		if (vap == null || fannKey == null) {
			return;
		}
		// présentation des personnes responsables par défaut de la formation
		NSArray<IndividuUlr> individus = new EnseignementFactory(eContext).getResponsablesAp(vap.maquetteAp(), fannKey);
		if (individus != null) {
			for (int i = 0; i < individus.count(); i++) {
				recherche.getIndividuController().eodIndividuUlr.insertObjectAtIndex(individus.objectAtIndex(i), 0);
			}
			recherche.getIndividuController().eodIndividuUlr.updateDisplayedObjects();
		}
		vapUniqueNonVisible = vap;
	}

	/** active ou desactive les boutons du panier */
	public void autoriserModification(boolean autoriser) {
		bAjoutRessource.setEnabled(autoriser);
		bSupRessource.setEnabled(autoriser);
	}

	/** valide ou grise l'onglet a l'index donne */
	public void setEnabledTab(int idxTab, boolean state) {
		tabsRessources.setEnabledAt(idxTab, state);
	}

	public EOView currentView() {
		return currentView;
	}

	/** retourne le contenu du panier */
	public NSArray<NSKeyValueCoding> getContenuPanier() {
		return eodRessources.displayedObjects();
	}

	/** retourne les ressources du type passe */
	@SuppressWarnings("rawtypes")
	public NSArray getResourcesWithType(String type) {

		NSMutableArray<Object> arrayObj = new NSMutableArray<Object>();

		if (type.equals("ENSEIGNEMENT_INSP")) {

			eodRessources.setQualifier(EOQualifier.qualifierWithQualifierFormat("resType='ENSEIGNEMENT'", null));
			eodRessources.updateDisplayedObjects();
			NSArray<NSKeyValueCoding> objets = eodRessources.displayedObjects();
			eodRessources.setQualifier(null);
			eodRessources.updateDisplayedObjects();
			for (int i = 0; i < objets.count(); i++) {
				NSMutableDictionary<String, Object> tbl = new NSMutableDictionary<String, Object>();
				tbl.takeValueForKey((objets.objectAtIndex(i)).valueForKey("resRecord"), "AP");
				tbl.takeValueForKey((objets.objectAtIndex(i)).valueForKey("resUnite"), "GRP");
				arrayObj.addObject(tbl);
			}

		}
		else
			if (type.equals("SEMESTRE_INSP")) {

				eodRessources.setQualifier(EOQualifier.qualifierWithQualifierFormat("resType='SEMESTRE'", null));
				eodRessources.updateDisplayedObjects();
				NSArray<NSKeyValueCoding> objets = eodRessources.displayedObjects();
				eodRessources.setQualifier(null);
				eodRessources.updateDisplayedObjects();
				for (int i = 0; i < objets.count(); i++) {
					NSMutableDictionary<String, Object> tbl = new NSMutableDictionary<String, Object>();
					tbl.takeValueForKey((objets.objectAtIndex(i)).valueForKey("resRecord"), "SEM");
					tbl.takeValueForKey((objets.objectAtIndex(i)).valueForKey("resUnite"), "GRP");
					arrayObj.addObject(tbl);
				}

			}
			else {

				if (type.equals("EXAMEN")) {
					eodRessources.setQualifier(EOQualifier.qualifierWithQualifierFormat("resType='EXAMEN'", null));
					eodRessources.updateDisplayedObjects();
					NSArray<NSKeyValueCoding> objets = eodRessources.displayedObjects();
					eodRessources.setQualifier(null);
					eodRessources.updateDisplayedObjects();
					for (int i = 0; i < objets.count(); i++) {
						NSMutableDictionary<String, Object> tbl = new NSMutableDictionary<String, Object>();
						tbl.takeValueForKey((objets.objectAtIndex(i)).valueForKey("resRecord"), "EXAM");
						tbl.takeValueForKey((objets.objectAtIndex(i)).valueForKey("resUnite"), "GRP");
						arrayObj.addObject(tbl);
					}
				}
				else {
					eodRessources.setQualifier(EOQualifier.qualifierWithQualifierFormat("resType='" + type + "'", null));
					eodRessources.updateDisplayedObjects();
					NSArray<NSKeyValueCoding> objets = eodRessources.displayedObjects();
					eodRessources.setQualifier(null);
					eodRessources.updateDisplayedObjects();
					for (int i = 0; i < objets.count(); i++) {
						arrayObj.addObject((objets.objectAtIndex(i)).valueForKey("resRecord"));
					}
				}

			}
		return arrayObj;
	}

	/** retourne le groupe scolarite selectionne */
	public ScolGroupeObjet getGrpScolSelectionne() {
		Object selection = recherche.getMatiereController().getGrpScolSelectionne();
		if (selection != null) {
			return (ScolGroupeObjet) selection;
		}
		else {
			return null;
		}
	}

	/* Test si le panier contient l'objet passé en paramètre parmi ses ressources */
	public boolean containsObject(Object objet) {

		if (objet == null) {
			return false;
		}

		NSArray<NSKeyValueCoding> elementsPanier = this.getContenuPanier();
		for (int i = 0; i < elementsPanier.count(); i++) {
			Object elementPanier = (elementsPanier.objectAtIndex(i)).valueForKey("resRecord");
			if (elementPanier.equals(objet)) {
				return true;
			}
		}
		return false;
	}

	/** ajoute les ressources selectionnees au panier */
	public void ajouterRessource() {
		try {
			NSArray<NSDictionary<String, Object>> selectedObjects = recherche.selectedRessources(typeRecherche);
			if (typeRecherche == GestionPanier.ENSEIGNEMENT) {
				if (typeReservation == InspecteurCtrl.TYPE_RESA_ENSEIGNEMENT) {
					NSArray<Object> objetsPanier = getResourcesWithType("ENSEIGNEMENT_INSP");

					if (selectedObjects.count() == 0) {
						return;
					}

					// notification chargement des enseignants de l'AP dans l'inspecteur : si option choisie par utilisateur
					if (owner != null && ((MainClient) EOApplication.sharedApplication()).getPrefUser().useRespAp().equals("O")) {
						WindowHandler.setWaitCursor(this.component());
						MaquetteAp lAp = (MaquetteAp) (selectedObjects.objectAtIndex(0)).objectForKey("resRecord");
						owner.afficherResponsablesAp(lAp);
						WindowHandler.setDefaultCursor(this.component());
					}

					// test repetition de selection de ressource : <pas parfait>
					for (int i = 0; i < objetsPanier.count(); i++) {
						for (int j = 0; j < selectedObjects.count(); j++) {
							NSDictionary<String, Object> curObj = (NSDictionary<String, Object>) objetsPanier.objectAtIndex(i);
							EOGenericRecord apPanier = (EOGenericRecord) curObj.objectForKey("AP");
							Object grpPanier = curObj.objectForKey("GRP");

							NSDictionary<String, Object> selObj = selectedObjects.objectAtIndex(j);
							EOGenericRecord objetsAAjouter = (EOGenericRecord) selObj.objectForKey("resRecord");
							Object objGrp = selObj.objectForKey("resUnite");

							if (objGrp instanceof String) {
								if (objetsAAjouter.equals(apPanier)) {
									WindowHandler.showError("L'AP est deja mis dans le panier");
									return;
								}
							}
							else {
								EOGenericRecord grp = (EOGenericRecord) objGrp;
								if (!(grpPanier instanceof String) && ((EOGenericRecord) grpPanier).equals(grp)) {
									WindowHandler.showError("Le groupe est deja mis dans le panier");
									return;
								}
							}
						}
					}
				}
				else
					if (typeReservation == InspecteurCtrl.TYPE_RESA_REUNION_ETUDIANT) {

						NSArray<Object> objetsPanier = getResourcesWithType("SEMESTRE_INSP");

						NSDictionary<String, Object> selObj = null;
						EOGenericRecord semPanier = null;
						EOGenericRecord objetsAAjouter = null;
						Object grpPanier = null;
						Object objGrp = null;

						// test repetition de selection de ressource : <pas parfait>
						for (int i = 0; i < objetsPanier.count(); i++) {

							NSDictionary<String, Object> curObj = (NSDictionary<String, Object>) objetsPanier.objectAtIndex(i);
							semPanier = (EOGenericRecord) curObj.objectForKey("SEM");
							grpPanier = curObj.objectForKey("GRP");

							for (int j = 0; j < selectedObjects.count(); j++) {

								selObj = selectedObjects.objectAtIndex(j);
								objetsAAjouter = (EOGenericRecord) selObj.objectForKey("resRecord");
								objGrp = selObj.objectForKey("resUnite");

								if (semPanier == objetsAAjouter) {
									if (grpPanier instanceof String) {
										WindowHandler.showError("Toute la promo est déjà dans le panier !");
										return;
									}

									if (objGrp instanceof String) {
										WindowHandler.showError("Un groupe de la promo est déjà dans le panier !\n"
												+ "Pour mettre toute la promo, il faut d'abord retirer du panier les groupes déjà présents.");
										return;
									}
									else {

										if (objGrp == grpPanier) {
											WindowHandler.showError("Le groupe est dejà présent dans le panier !");
											return;
										}
									}
								}

								// cas même groupe et semestres différents.
								if (objGrp == grpPanier) {
									WindowHandler.showError("Le groupe est dejà présent dans le panier, même si semestre différent.");
									return;
								}
							}
						}
						// UP : 30/09/2009
						// Prise en compte de la reservation de semestre...

						// ajouerRessourceEnseignementReunionEtudiant(selectedObjects);
					}
			}

			if (typeRecherche == GestionPanier.EXAMEN) {
				NSArray<Object> objetsPanier = getResourcesWithType("EXAMEN");
				for (int i = 0; i < objetsPanier.count(); i++) {
					for (int j = 0; j < selectedObjects.count(); j++) {
						EOGenericRecord objetPanier = (EOGenericRecord) ((NSKeyValueCoding) objetsPanier.objectAtIndex(i)).valueForKey("EXAM");
						EOGenericRecord objetsAAjouter = (EOGenericRecord) ((NSKeyValueCoding) selectedObjects.objectAtIndex(j))
								.valueForKey("resRecord");
						if (objetsAAjouter.equals(objetPanier)) {
							WindowHandler.showError("L'Examen est deja mis dans le panier");
							return;
						}
					}
				}
			}

			if (typeRecherche == GestionPanier.PERSONNE) {
				NSArray<Object> individusPanier = getResourcesWithType("PERSONNE");
				if (recherche.getIndividuController() != null && recherche.getIndividuController().isTypeRechercheGroupe() == false) {
					if (owner != null && owner.getMyView().getCbExpert().isSelected() == false) {
						for (int i = 0; i < selectedObjects.count(); i++) {
							IndividuUlr currentIndividu = (IndividuUlr) (selectedObjects.objectAtIndex(i)).objectForKey("resRecord");
							boolean dispo = VerifDisponibilite.verifDisponibiliteIndividu(owner, currentIndividu);
							if (!dispo) {
								return;
							}
						}
					}
					for (int i = 0; i < individusPanier.count(); i++) {
						for (int j = 0; j < selectedObjects.count(); j++) {
							EOGenericRecord individuPanier = (EOGenericRecord) individusPanier.objectAtIndex(i);
							EOGenericRecord individuAAjouter = (EOGenericRecord) (selectedObjects.objectAtIndex(j)).objectForKey("resRecord");
							if (individuAAjouter.equals(individuPanier)) {
								WindowHandler.showError("La personne est déjà dans le panier !");
								return;
							}
						}
					}
				}
			}

			if (typeRecherche == GestionPanier.SALLE) {
				if (selectedObjects == null || selectedObjects.count() == 0) {
					return;
				}
				if (selectedObjects.objectAtIndex(0).objectForKey("resType").equals("SALLE") && selectedObjects.count() > 1) {
					if (WindowHandler.showConfirmation("Vous ajoutez plusieurs salles sans cocher 'à choix', est-ce voulu ?") == false) {
						return;
					}
				}
				if (selectedObjects.objectAtIndex(0).objectForKey("resType").equals("CHOIX")
						&& (owner != null && owner.getMyView() != null && owner.getMyView().getCbExpert().isSelected() == false)) {
					if (WindowHandler.showConfirmation("Vous ajoutez une salle 'à choix' sans être en mode expert, est-ce voulu ?") == false) {
						return;
					}
				}
				NSMutableArray<Object> allSalles = new NSMutableArray<Object>();
				allSalles.addObjectsFromArray(getResourcesWithType("SALLE"));
				allSalles.addObjectsFromArray(getResourcesWithType("CHOIX"));

				// Verification si déjà présent dans le panier
				for (int i = 0; i < allSalles.count(); i++) {
					for (int j = 0; j < selectedObjects.count(); j++) {
						EOGenericRecord objetPanier = (EOGenericRecord) allSalles.objectAtIndex(i);
						EOGenericRecord objetsAAjouter = (EOGenericRecord) (selectedObjects.objectAtIndex(j)).objectForKey("resRecord");
						if (objetsAAjouter.equals(objetPanier)) {
							WindowHandler.showError("La salle est déjà dans le panier !");
							return;
						}
					}
				}

				// CM Edtscol 10/04/2007 - on vérifie si la salle est disponible
				if (owner != null && owner.getMyView().getCbExpert().isSelected() == false) {
					for (int i = 0; i < selectedObjects.count(); i++) {
						NSDictionary<String, Object> element = selectedObjects.objectAtIndex(i);
						Salles currentSalle = (Salles) element.objectForKey("resRecord");

						// vérifie si on a le droit d'utiliser cette salle...
						if (SalleFactory.testIndividuADroitReserverSalle(eContext, (IndividuUlr) mainInterface.app.userInfo("individu"),
								currentSalle, mainInterface.app.isReservationSalleParDepositaire()) == false) {
							WindowHandler.showError("Vous n'avez pas le droit de réserver cette salle (" + currentSalle.salPorte() + ")");
							return;
						}

						// UP 03/12/2009 : On ne verifie pas la disponiblite si salles aux "choix".
						if (element.objectForKey("resType").equals("SALLE")
								&& VerifDisponibilite.verifDisponibiliteSalle(owner, currentSalle) == false) {
							return;
						}
					}
				}

				for (int i = 0; i < selectedObjects.count(); i++) {
					NSDictionary<String, Object> element = selectedObjects.objectAtIndex(i);
					if (!checkSalle(element)) {
						return;
					}
				}
			}

			if (typeRecherche == GestionPanier.OBJET) {
				NSArray<Object> objetsPanier = getResourcesWithType("OBJET");
				// CM Edtscol 10/04/2007 - on vérifie si l'objet est disponible
				if (owner != null && owner.getMyView().getCbExpert().isSelected() == false) {
					for (int i = 0; i < selectedObjects.count(); i++) {
						ResaObjet currentObjet = (ResaObjet) (selectedObjects.objectAtIndex(i)).objectForKey("resRecord");
						// vérifie si on a le droit d'utiliser cet objet...
						if (VerificationFactory.testIndividuADroitReserverObjet(eContext, (IndividuUlr) mainInterface.app.userInfo("individu"),
								currentObjet) == false) {
							WindowHandler.showError("Vous n'avez pas le droit de réserver cet objet (" + currentObjet.roLibelle1() + ")");
							return;
						}
						boolean dispo = VerifDisponibilite.verifDisponibiliteObjet(owner, currentObjet);
						if (!dispo) {
							return;
						}
					}
				}
				for (int i = 0; i < objetsPanier.count(); i++) {
					for (int j = 0; j < selectedObjects.count(); j++) {
						EOGenericRecord objetPanier = (EOGenericRecord) objetsPanier.objectAtIndex(i);
						EOGenericRecord objetsAAjouter = (EOGenericRecord) (selectedObjects.objectAtIndex(j)).objectForKey("resRecord");
						if (objetsAAjouter.equals(objetPanier)) {
							WindowHandler.showError("L'objet est déjà mis dans le panier");
							return;
						}
					}
				}
			}

			if (selectedObjects != null) {
				for (int i = 0; i < selectedObjects.count(); i++) {
					eodRessources.insertObjectAtIndex(selectedObjects.objectAtIndex(i), 0);

				}
			}
			eodRessources.updateDisplayedObjects();
		}
		catch (Throwable t) {
			t.printStackTrace();
			mainInterface.app.sendMailException(t);
		}
	}

	public void addResources(NSArray<NSDictionary<String, Object>> ressources) {
		for (int i = 0; i < ressources.count(); i++) {
			eodRessources.insertObjectAtIndex(ressources.objectAtIndex(i), 0);
		}
		eodRessources.updateDisplayedObjects();
	}

	public void addResourcesWithSalleControl(NSArray<NSDictionary<String, Object>> ressources) {
		for (int i = 0; i < ressources.count(); i++) {
			NSDictionary<String, Object> element = ressources.objectAtIndex(i);
			if (!checkSalle(element)) {
				continue;
			}
			eodRessources.insertObjectAtIndex(element, 0);
		}
		eodRessources.updateDisplayedObjects();
	}

	private boolean checkSalle(NSDictionary<String, Object> element) {
		if (element != null) {
			Salles currentSalle = (Salles) element.objectForKey("resRecord");
			if (currentSalle != null) {
				// if (mainInterface.app.isReservationSalleParDepositaire() == true) {
				// // on vérifie que :
				// // - soit la salle n'a aucun dépositaire défini ==> ok
				// // - soit l'agent est dépositaire de la salle ==> ok
				// NSArray<NSArray<IndividuUlr>> depositaires = (NSArray<NSArray<IndividuUlr>>)
				// currentSalle.valueForKeyPath(Salles.DEPOSITAIRES_KEY
				// + "." + DepositaireSalles.STRUCTURE_ULR_KEY + "." + StructureUlr.REPART_STRUCTURES_KEY + "."
				// + RepartStructure.INDIVIDU_ULR_KEY);
				// if (depositaires != null && depositaires.count() > 0 && depositaires.objectAtIndex(0) != null
				// && depositaires.objectAtIndex(0).count() > 0) {
				// IndividuUlr currentAgent = (IndividuUlr) mainInterface.app.userInfo("individu");
				// if (new SalleFactory(eContext).estDepositaireDeSalle(currentAgent, currentSalle) == false) {
				// WindowHandler.showError("La salle [ " + currentSalle.libelleComplet()
				// + " ] n'est pas réservable.\nVous n'êtes pas dépositaire de la salle !");
				// return false;
				// }
				// }
				// }
				if (currentSalle.salDescriptif() != null && currentSalle.salDescriptif().length() > 200) {
					IndividuUlr currentAgent = (IndividuUlr) mainInterface.app.userInfo("individu");
					if (new SalleFactory(eContext).estDepositaireDeSalle(currentAgent, currentSalle) == false) {
						if (WindowHandler.showConfirmation(currentSalle.salDescriptif() + "\nConfirmez-vous l'utilisation de cette salle ?") == false) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/** retire les ressources selectionnees du panier */
	public void supprimerRessource() {
		if (eodRessources.selectedObject() == null) {
			return;
		}
		if (((NSKeyValueCoding) eodRessources.selectedObject()).valueForKey("resType").toString().equals("SALLE") == true) {
			// ressource de type salle
			if (mainInterface.app.isReservationSalleParDepositaire() == true) {
				// seul le dépositaire de la salle peut retirer la salle de la réservation
				// on vérifie que :
				// - soit la salle n'a aucun dépositaire défini ==> ok
				// - soit l'agent est dépositaire de la salle ==> ok
				NSDictionary<String, Object> curObj = (NSDictionary<String, Object>) eodRessources.selectedObject();
				Salles currentSalle = (Salles) curObj.objectForKey("resRecord");
				NSArray<NSArray<IndividuUlr>> depositaires = (NSArray<NSArray<IndividuUlr>>) currentSalle.valueForKeyPath(Salles.DEPOSITAIRES_KEY
						+ "." + DepositaireSalles.STRUCTURE_ULR_KEY + "." + StructureUlr.REPART_STRUCTURES_KEY + "."
						+ RepartStructure.INDIVIDU_ULR_KEY);
				if (depositaires != null && depositaires.count() > 0 && depositaires.objectAtIndex(0) != null
						&& depositaires.objectAtIndex(0).count() > 0) {
					IndividuUlr currentAgent = (IndividuUlr) mainInterface.app.userInfo("individu");
					if (new SalleFactory(eContext).estDepositaireDeSalle(currentAgent, currentSalle) == false) {
						WindowHandler.showError("La salle ne peut être retirée de la réservation.\nVous n'êtes pas dépositaire de la salle !");
						return;
					}
				}
			}
		}
		eodRessources.deleteSelection();
		eodRessources.updateDisplayedObjects();
	}

	/** vide les objets affiches dans le panier */
	public void viderPanier() {
		eodRessources.setSelectedObjects(eodRessources.allObjects());
		eodRessources.deleteSelection();
		eodRessources.setObjectArray(NSArray.EmptyArray);
		eodRessources.updateDisplayedObjects();
	}

	/** ce listener est tres important car permet de determiner quel type de ressource est actuellement actif */
	private class TabListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			typeRecherche = tabsRessources.getSelectedIndex();
		}
	}

	private void initWidgets() {

		WidgetHandler.decorateButton("ajouter la ressource au panier", null, "down", bAjoutRessource);
		WidgetHandler.decorateButton("retirer la ressource du panier", null, "up", bSupRessource);
		WidgetHandler.setTableNotEditable(tvRessources);
	}

	public void setTypeReservation(int typeresa) {
		typeReservation = typeresa;
		if (typeReservation == InspecteurCtrl.TYPE_RESA_REUNION_ETUDIANT) {
			recherche.setDisposition(Recherche.SEMESTRE_INSP);
		}
		if (typeReservation == InspecteurCtrl.TYPE_RESA_ENSEIGNEMENT) {
			recherche.setDisposition(Recherche.INSPECTEUR);
		}

	}

	public VMaquetteApGroupes getVapUniqueNonVisible() {
		return vapUniqueNonVisible;
	}

}
