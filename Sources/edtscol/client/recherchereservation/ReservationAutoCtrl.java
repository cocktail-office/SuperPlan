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
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import org.cocktail.fwkcktlwebapp.common.util.StringCtrl;
import org.cocktail.superplan.client.EdtExpertNotCompleteException;
import org.cocktail.superplan.client.factory.ExpertFactory;
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
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import edtscol.client.MainClient;
import edtscol.client.MainInterface;
import edtscol.client.gestionreservation.InspecteurCtrl;
import fr.univlr.common.utilities.EdtException;
import fr.univlr.common.utilities.Log;
import fr.univlr.common.utilities.WidgetHandler;
import fr.univlr.common.utilities.WindowHandler;

public class ReservationAutoCtrl {

	private ReservationAutoView myView;

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private EOEditingContext eContext;
	private MainInterface main;
	private NSArray<VMaquetteApGroupes> aps;
	private FormationHabilitation habilitation;

	public ReservationAutoCtrl(EOEditingContext eContext, MainInterface main, NSArray<VMaquetteApGroupes> aps, FormationHabilitation habilitation,
			boolean modal) {
		this.eContext = eContext;
		this.main = main;
		this.aps = aps;
		this.habilitation = habilitation;
		myView = new ReservationAutoView((Frame) ((EOSimpleWindowController) main.supercontroller()).window(), modal);
		init();
	}

	public void open() {
		if (aps != null && aps.count() > 0) {
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

		myView.getjCheckBoxIntervalleMinimum().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setIntervalleMinimum(myView.getjCheckBoxIntervalleMinimum().isSelected());
			}
		});
		myView.getjCheckBoxIntervalleMinimum().setSelected(true);
		setIntervalleMinimum(true);

		myView.gettSemaines().setText("" + (new GregorianCalendar().get(Calendar.WEEK_OF_YEAR) + 1));

		// FIXME voir si on peut remettre ça en place...
		myView.getRbSaveSiTous().setVisible(false);
		myView.getRbSaveToutLeTemps().setVisible(false);
		myView.getRbSaveToutLeTemps().setSelected(true);
	}

	private void setIntervalleMinimum(boolean intervalleMinimum) {
		if (intervalleMinimum) {
			Integer intervalleMinimumValue = app.getPrefUser().intervalleMinimum();
			if (intervalleMinimumValue == null) {
				intervalleMinimumValue = new Integer(5);
			}
			myView.gettIntervalleTempsMinimum().setEnabled(true);
			myView.gettIntervalleTempsMinimum().setText(String.valueOf(intervalleMinimumValue.intValue()));
		}
		else {
			myView.gettIntervalleTempsMinimum().setEnabled(false);
			myView.gettIntervalleTempsMinimum().setText(null);
		}
	}

	public void valider() {
		if (enregistrer()) {
			close();
		}
	}

	public void annuler() {
		close();
	}

	private boolean enregistrer() {
		Log log = new Log(false);
		WindowHandler.setWaitCursor(myView);
		String semainesString = myView.gettSemaines().getText();
		String intervalleMinimumString = myView.gettIntervalleTempsMinimum().getText();
		boolean isPlaceSiParamsAp = myView.getRbPlaceSiParams().isSelected();
		boolean isSaveSiTous = myView.getRbSaveSiTous().isSelected();
		boolean isPrioriteSalle = myView.getRbPrioriteSalle().isSelected();
		boolean isGroupesSelection = myView.getRbGroupesSelection().isSelected();
		boolean isGroupesTous = myView.getRbGroupesTous().isSelected();
		boolean isBloqueHeures = myView.getjCheckBoxBloqueHeures().isSelected();
		boolean isEviteSemainesDejaPlacees = myView.getRbEviteSemainesDejaPlaceesOui().isSelected();
		boolean isIntervalleMinimum = myView.getjCheckBoxIntervalleMinimum().isSelected();
		Integer intervalleMinimum = null;

		try {
			if (StringCtrl.isEmpty(semainesString)) {
				throw new Exception("Semaine(s) obligatoire(s) !");
			}
			if (isIntervalleMinimum && StringCtrl.isEmpty(intervalleMinimumString) == false) {
				intervalleMinimum = new Integer(intervalleMinimumString);
			}

			NSArray<VMaquetteApGroupes> apsATraiter;
			if (isGroupesSelection) {
				apsATraiter = aps;
			}
			else {
				NSMutableArray<VMaquetteApGroupes> apsATraiterTemp = new NSMutableArray<VMaquetteApGroupes>();
				Enumeration<VMaquetteApGroupes> apsEnumeration = aps.objectEnumerator();
				while (apsEnumeration.hasMoreElements()) {
					VMaquetteApGroupes vap = apsEnumeration.nextElement();
					NSArray<VMaquetteApGroupes> groupesAp = vap.maquetteAp().vMaquetteApGroupeses(
							new EOKeyValueQualifier(VMaquetteApGroupes.SCOL_GROUPE_GRP_KEY, EOKeyValueQualifier.QualifierOperatorNotEqual,
									NSKeyValueCoding.NullValue));
					boolean hasGroupes = (groupesAp != null && groupesAp.count() > 0);
					if (isGroupesTous || hasGroupes == false) {
						VMaquetteApGroupes vapAAjouter = vap.maquetteAp().vMaquetteApGroupes(null);
						if (apsATraiterTemp.containsObject(vapAAjouter) == false) {
							apsATraiterTemp.addObject(vapAAjouter);
						}
					}
					else {
						if (hasGroupes) {
							Enumeration<VMaquetteApGroupes> aEnum = groupesAp.objectEnumerator();
							while (aEnum.hasMoreElements()) {
								VMaquetteApGroupes vapAAjouter = aEnum.nextElement();
								if (apsATraiterTemp.containsObject(vapAAjouter) == false) {
									apsATraiterTemp.addObject(vapAAjouter);
								}
							}
						}
					}
				}
				apsATraiter = apsATraiterTemp.immutableClone();
			}

			NSMutableArray<String> problemes = new NSMutableArray<String>();
			Enumeration<VMaquetteApGroupes> apsEnumeration = apsATraiter.objectEnumerator();
			int i = 1;
			// eContext.lock();
			while (apsEnumeration.hasMoreElements()) {
				log.startOp("suivant: " + i);
				VMaquetteApGroupes vap = apsEnumeration.nextElement();
				ICtrlParam ctrlParam = vap.ctrlParamAp();
				if (ctrlParam == null && habilitation != null) {
					ctrlParam = habilitation.ctrlParamHabilitation();
				}
				if (ctrlParam == null || ctrlParam.isUtilisable() == false) {
					if (isSaveSiTous) {
						throw new Exception("Tous les APs ne peuvent être placés (il manque des paramètres), opération annulée.");
					}
					i++;
					continue;
				}
				// arrivé là, on a un ctrlParam utilisable... à voir si on le veut complet (individu et salle) et si c'est le cas...
				if (isPlaceSiParamsAp && ctrlParam.isComplet() == false) {
					if (isSaveSiTous) {
						throw new Exception("Tous les APs ne peuvent être placés (il manque des paramètres AP, individu/salle), opération annulée.");
					}
					i++;
					continue;
				}
				// on a un ctrlParam qui nous convient (avec ou sans individu/salle, peu importe)
				ExpertFactory ef = new ExpertFactory(eContext);
				ef.setAnnee(habilitation.fannKey());
				ef.setDebut(ctrlParam.getHeureDeb());
				ef.setFin(ctrlParam.getHeureFin());
				ef.setDuree(ctrlParam.getDuree());
				ef.setJours(ctrlParam.jours());
				ef.setIntervalleMinimum(intervalleMinimum);
				ef.setBloqueHeures(isBloqueHeures);
				ef.setEviteSemainesAvecAPDejaPlace(isEviteSemainesDejaPlacees);
				ef.setSemaines(semainesString);
				ef.setTypeReservation(InspecteurCtrl.TYPE_RESA_ENSEIGNEMENT);
				ef.setCommentaire(null);
				ef.setVisibilite(new Integer(1));
				NSMutableDictionary<String, Object> enseigns = new NSMutableDictionary<String, Object>();
				enseigns.takeValueForKey(vap.maquetteAp(), "AP");
				enseigns.takeValueForKey(vap.scolGroupeGrp(), "GRP");
				ef.setEnseigns(new NSArray<NSKeyValueCoding>(enseigns));
				if (ctrlParam instanceof CtrlParamAp) {
					if (((CtrlParamAp) ctrlParam).ctrlParamApIndividus() != null) {
						ef.setPersonnes((NSArray<IndividuUlr>) ((CtrlParamAp) ctrlParam).ctrlParamApIndividus().valueForKey(
								CtrlParamApIndividu.INDIVIDU_KEY));
					}
					if (((CtrlParamAp) ctrlParam).ctrlParamApSalles() != null) {
						ef.setSalles((NSArray<Salles>) ((CtrlParamAp) ctrlParam).ctrlParamApSalles().valueForKey(CtrlParamApSalle.SALLE_KEY));
					}
					if (((CtrlParamAp) ctrlParam).ctrlParamApSalleChoixs() != null) {
						ef.setChoixSalles((NSArray<Salles>) ((CtrlParamAp) ctrlParam).ctrlParamApSalleChoixs().valueForKey(
								CtrlParamApSalleChoix.SALLE_KEY));
					}
					if (((CtrlParamAp) ctrlParam).ctrlParamApObjets() != null) {
						ef.setObjets((NSArray<ResaObjet>) ((CtrlParamAp) ctrlParam).ctrlParamApObjets().valueForKey(CtrlParamApObjet.RESA_OBJET_KEY));
					}
				}
				try {
					ef.enregistrementExpert(false, isPrioriteSalle, true);
				}
				catch (EdtExpertNotCompleteException e) {
					problemes.addObject("AP non placé certaines semaines: " + vap.maquetteAp() + " - Semaines " + e.getSemaines());
				}
				catch (EdtException e) {
					if (e.isBloquant() && isSaveSiTous) {
						throw new Exception("Opération annulée, tous les APs sélectionnés ne peuvent être placés ! (" + e.getMessage() + ")");
					}
					if (e.isBloquant()) {
						problemes.addObject("AP non placé: " + vap.maquetteAp());
					}
				}
				log.endOp("FIN suivant: " + i++);
			}
			eContext.saveChanges();
			// eContext.unlock();
			if (problemes.count() > 0) {
				problemes.sortUsingComparator(NSComparator.DescendingStringComparator);
				WindowHandler.showInfo("Tous les APs n'ont pu être placés... Détails :\n- " + problemes.componentsJoinedByString("\n- "));
			}
		}
		catch (Throwable t) {
			WindowHandler.showError(t.getMessage());
			t.printStackTrace();
			app.sendMailException(t);
			eContext.revert();
			// eContext.unlock();
			WindowHandler.setDefaultCursor(myView);
			return false;
		}

		WindowHandler.setDefaultCursor(myView);
		return true;
	}

	public ReservationAutoView getMyView() {
		return myView;
	}

}
