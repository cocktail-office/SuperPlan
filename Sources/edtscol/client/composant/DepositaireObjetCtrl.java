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
package edtscol.client.composant;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.cocktail.superplan.client.metier.FormationAnnee;
import org.cocktail.superplan.client.metier.IndividuUlr;
import org.cocktail.superplan.client.metier.Periodicite;
import org.cocktail.superplan.client.metier.RepartStructure;
import org.cocktail.superplan.client.metier.ResaObjet;
import org.cocktail.superplan.client.metier.ResaObjetDepositaire;
import org.cocktail.superplan.client.metier.Reservation;
import org.cocktail.superplan.client.metier.ReservationObjet;
import org.cocktail.superplan.client.swing.ZEOTable;
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
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

import edtscol.client.MailReservation;
import edtscol.client.MainClient;
import fr.univlr.common.utilities.FormatHandler;
import fr.univlr.common.utilities.WindowHandler;
import fr.univlr.utilities.GenericListHandler;
import fr.univlr.utilities.TimeHandler;

public class DepositaireObjetCtrl extends javax.swing.JDialog {
	/*
	 * private static final int RECHERCHE = 1; private static final int VALIDATION = 2; private static final int MODIFICATION = 3;
	 */
	private static final int VIDE = 4;

	private static final int OK_OBJET = 10;
	private static final int REFUS_OBJET = 11;
	private static final int AUTRE_OBJET = 12;

	private EOEditingContext eContext;
	// private VTreeObjet selectedNode;

	private EODisplayGroup eodResa, eodPeriodicites; // eodResaObjetDepositaire,
	private ZEOTable tableResa, tablePeriodicites; // tableResaObjetDepositaire,

	private MainClient app = (MainClient) EOApplication.sharedApplication();
	private GenericListHandler listObjets;
	private Component parent;

	public DepositaireObjetCtrl(Component parent, EOEditingContext context) {
		super((Frame) parent, "Gestion des objets", true);
		eContext = context;
		initComponents();
		initExtra();
		this.parent = parent;
	}

	public void open() {
		this.setLocation(parent.getX() + 20, parent.getY() + 20);
		this.setVisible(true);
	}

	public void close() {
		this.setVisible(false);
	}

	private void initExtra() {

		listObjets = new GenericListHandler();
		listObjets.setPreferredSize(new Dimension(200, 100));
		panelObjets.setLayout(new BorderLayout());
		panelObjets.add(listObjets, BorderLayout.CENTER);

		listObjets.getJList().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) listSelectionEvent.getSource();
					Object selectionValues[] = list.getSelectedValues();
					if (selectionValues.length > 0) {

					}
				}
			}
		});

		NSArray tmp = app.getFormationAnnees();
		comboAnnee.removeAllItems();
		for (int i = 0; i < tmp.count(); i++) {
			FormationAnnee fAnnee = (FormationAnnee) tmp.objectAtIndex(i);
			comboAnnee.addItem(fAnnee);
			if (fAnnee.fannCourante().equals("O")) {
				comboAnnee.setSelectedItem(fAnnee);
			}
		}

		eodResa = new EODisplayGroup();
		Vector columns = new Vector();
		columns.add(new ZEOTableModelColumn(eodResa, "type", "Type"));
		columns.add(new ZEOTableModelColumn(eodResa, "agent", "Agent"));
		columns.add(new ZEOTableModelColumn(eodResa, "commentaire", "Commentaire"));
		tableResa = new ZEOTable(new ZEOTableModel(eodResa, columns));
		JScrollPane tableScroll = new JScrollPane(tableResa);

		panelReservations.setLayout(new BorderLayout());
		tableScroll.setPreferredSize(new Dimension(380, 220));
		panelReservations.add(tableScroll, BorderLayout.CENTER);

		tableResa.addListener(new MyTableListener(eodResa));

		eodPeriodicites = new EODisplayGroup();
		columns = new Vector();
		columns.add(new ZEOTableModelColumn(eodPeriodicites, "jour", "Jour"));
		columns.add(new ZEOTableModelColumn(eodPeriodicites, "semaine", "Semaine"));
		columns.add(new ZEOTableModelColumn(eodPeriodicites, "date", "Date"));
		columns.add(new ZEOTableModelColumn(eodPeriodicites, "heureDeb", "Heure Deb"));
		columns.add(new ZEOTableModelColumn(eodPeriodicites, "heureFin", "Heure Fin"));
		tablePeriodicites = new ZEOTable(new ZEOTableModel(eodPeriodicites, columns));
		tableScroll = new JScrollPane(tablePeriodicites);

		panelCreneaux.setLayout(new BorderLayout());
		tableScroll.setPreferredSize(new Dimension(380, 220));
		panelCreneaux.add(tableScroll, BorderLayout.CENTER);

		/*
		 * EODistributedDataSource doctorantSource = new EODistributedDataSource(eContext,"ResaObjetDepositaire"); eodResaObjetDepositaire =
		 * new EODisplayGroup(); eodResaObjetDepositaire.setDataSource(doctorantSource); //eodDoctorant.fetch(); java.util.Vector columns =
		 * new java.util.Vector(); columns.add(new ZEOTableModelColumn(eodResaObjetDepositaire,"structureUlr.llStructure","Libelle"));
		 * 
		 * //ZEOTableModelColumn anneeInscCol = new ZEOTableModelColumn(eodResaObjetDepositaire,"historique.histAnneeScol","Inscription");
		 * //columns.add(anneeInscCol);
		 * 
		 * 
		 * ZEOTableModel resaObjetDepositaireModel = new ZEOTableModel(eodResaObjetDepositaire,columns); tableResaObjetDepositaire = new
		 * ZEOTable(resaObjetDepositaireModel);
		 * 
		 * 
		 * tableScroll = new JScrollPane(tableResaObjetDepositaire);
		 * tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		 * tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 * 
		 * tableScroll.setPreferredSize(panelTableDepos.getPreferredSize()); panelTableDepos.setLayout(new BorderLayout());
		 * panelTableDepos.add(tableScroll,BorderLayout.NORTH);
		 */

		tSemaine.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				chargerPourSemaine();
			}
		});

		chargerPourSemaine();

	}

	public void chargerPourSemaine() {

		NSArray emptyArray = new NSArray();
		eodPeriodicites.setObjectArray(emptyArray);
		tablePeriodicites.updateData();

		listObjets.setObjects(emptyArray);

		if (!tSemaine.getText().equals("")) {
			int semaine = FormatHandler.strToInt(tSemaine.getText());
			if (semaine < 0 || semaine > 53) {
				WindowHandler.showError("Ce numéro de semaine n'est pas valable");
				return;
			}
			FormationAnnee fAnnee = (FormationAnnee) comboAnnee.getSelectedItem();
			boolean anneeCivile = ((Boolean) app.userInfo("anneeCivile")).booleanValue();
			int annee = TimeHandler.getYearForWeek(fAnnee, semaine, anneeCivile);
			// CM modif pour avoir 8 semaines de reservations depositaire a partir du dimanche
			NSTimestamp[] jDebut = TimeHandler.getBeginAndEndOfWeek(semaine - 1, annee);
			semaine = semaine + 7;
			if (semaine > 53) {
				semaine = 53;
			}
			NSTimestamp[] jFin = TimeHandler.getBeginAndEndOfWeek(semaine, annee);
			chargerReservations(jDebut[1], jFin[0]);
		}
		else {
			chargerReservations(null, null);
		}
	}

	/** charge les reservations du depositaire */
	public void chargerReservations(NSTimestamp debut, NSTimestamp fin) {
		app.waitingHandler().setMessage("Chargement des réservations qui restent à valider au cours des 8 prochaines semaines");
		app.waitingHandler().setIntro("Dépositaire : ");
		// CM pour vider le displayGroup
		eodResa.setObjectArray(null);

		NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
		qualifiers.addObject(new EOKeyValueQualifier(Reservation.RESERVATION_OBJETS_KEY + "." + ReservationObjet.RESA_OBJET_KEY + "."
				+ ResaObjet.RESA_OBJET_DEPOSITAIRES_KEY + "." + ResaObjetDepositaire.REPART_STRUCTURES_KEY + "." + RepartStructure.INDIVIDU_ULR_KEY,
				EOKeyValueQualifier.QualifierOperatorEqual, app.userInfo("individu")));

		if (debut != null && fin != null) {
			qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Reservation.PERIODICITES_KEY + "." + Periodicite.DATE_DEB_KEY
					+ " < %@ and " + Reservation.PERIODICITES_KEY + "." + Periodicite.DATE_FIN_KEY + " > %@", new NSArray<NSTimestamp>(
					new NSTimestamp[] { fin, debut })));
		}
		qualifiers.addObject(EOQualifier.qualifierWithQualifierFormat(Reservation.RESERVATION_OBJETS_KEY + "." + ReservationObjet.RESA_ETAT_KEY
				+ " = 'P'", null));
		EOQualifier qResaObjets = new EOAndQualifier(qualifiers);
		EOSortOrdering sortDate = EOSortOrdering.sortOrderingWithKey(Reservation.D_MODIFICATION_KEY, EOSortOrdering.CompareCaseInsensitiveAscending);
		NSArray<Reservation> resas = Reservation.fetchReservations(eContext, qResaObjets, new NSArray<EOSortOrdering>(sortDate));

		// on charge les reservations non validees dont l'utilisateur est depositaire de leurs salles.
		for (int i = 0; i < resas.count(); i++) {
			this.afficherReservation(resas.objectAtIndex(i));
		}

		tableResa.updateData();
		tableResa.updateUI();

		app.waitingHandler().close();
	}

	private void afficherReservation(Reservation reservation) {
		if (reservation == null) {
			return;
		}
		try {
			String type = null;
			if (reservation.typeLocation() != null) {
				type = reservation.typeLocation().tlocLibelle();
			}
			String commentaire = reservation.resaCommentaire();
			String nomAgent = reservation.individuAgent().nomPrenom();
			if (type == null) {
				type = "";
			}
			if (commentaire == null) {
				commentaire = "";
			}
			if (nomAgent == null) {
				nomAgent = "Inconnu : contacter Service Info";
			}

			String[] keys = { "type", "agent", "commentaire", "reservation", "etatValidation" };
			Object[] contents = { type, nomAgent, commentaire, reservation, Boolean.FALSE };

			eodResa.insertObjectAtIndex(new NSMutableDictionary<String, Object>(contents, keys), 0);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
	 * method is always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		panelReservations = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		panelCreneaux = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		panelObjets = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		bValider = new javax.swing.JButton();
		bRefuser = new javax.swing.JButton();
		bAutre = new javax.swing.JButton();
		bQuitter = new javax.swing.JButton();
		comboAnnee = new javax.swing.JComboBox();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		tSemaine = new javax.swing.JTextField();
		bChargerSemaine = new javax.swing.JButton();

		jLabel1.setText("Réservations à valider");

		org.jdesktop.layout.GroupLayout panelReservationsLayout = new org.jdesktop.layout.GroupLayout(panelReservations);
		panelReservations.setLayout(panelReservationsLayout);
		panelReservationsLayout.setHorizontalGroup(panelReservationsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 389,
				Short.MAX_VALUE));
		panelReservationsLayout.setVerticalGroup(panelReservationsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 257,
				Short.MAX_VALUE));

		jLabel2.setText("Créneaux de la réservation");

		org.jdesktop.layout.GroupLayout panelCreneauxLayout = new org.jdesktop.layout.GroupLayout(panelCreneaux);
		panelCreneaux.setLayout(panelCreneauxLayout);
		panelCreneauxLayout.setHorizontalGroup(panelCreneauxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 359,
				Short.MAX_VALUE));
		panelCreneauxLayout.setVerticalGroup(panelCreneauxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 257,
				Short.MAX_VALUE));

		jLabel3.setText("Objets de la réservation");

		panelObjets.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		org.jdesktop.layout.GroupLayout panelObjetsLayout = new org.jdesktop.layout.GroupLayout(panelObjets);
		panelObjets.setLayout(panelObjetsLayout);
		panelObjetsLayout.setHorizontalGroup(panelObjetsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 273,
				Short.MAX_VALUE));
		panelObjetsLayout
				.setVerticalGroup(panelObjetsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 94, Short.MAX_VALUE));

		jLabel4.setText("Action à effectuer");

		bValider.setText("Valider le prêt de l'objet");
		bValider.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bValiderActionPerformed(evt);
			}
		});

		bRefuser.setText("Refuser le prêt");
		bRefuser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bRefuserActionPerformed(evt);
			}
		});

		bAutre.setText("Affecter un autre objet");
		bAutre.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bAutreActionPerformed(evt);
			}
		});

		bQuitter.setText("Quitter");
		bQuitter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bQuitterActionPerformed(evt);
			}
		});

		comboAnnee.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

		jLabel5.setText("Année Scolaire");

		jLabel6.setText("Semaine à traiter ");

		bChargerSemaine.setText("Charger");
		bChargerSemaine.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bChargerSemaineActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
								.add(org.jdesktop.layout.GroupLayout.LEADING,
										layout.createSequentialGroup()
												.add(layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(panelObjets, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).add(jLabel3))
												.add(18, 18, 18)
												.add(layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(jLabel4)
														.add(bValider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(bRefuser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(layout
																.createSequentialGroup()
																.add(bAutre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 135, Short.MAX_VALUE)
																.add(bQuitter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90,
																		org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
								.add(org.jdesktop.layout.GroupLayout.LEADING,
										layout.createSequentialGroup()
												.add(layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(jLabel1)
														.add(panelReservations, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(layout
														.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(jLabel2)
														.add(panelCreneaux, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
								.add(layout
										.createSequentialGroup()
										.add(jLabel6)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(tSemaine, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(18, 18, 18)
										.add(bChargerSemaine)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 241, Short.MAX_VALUE)
										.add(jLabel5)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(comboAnnee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 144,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(comboAnnee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jLabel5)
								.add(jLabel6)
								.add(tSemaine, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(bChargerSemaine))
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout.createSequentialGroup().addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 28, Short.MAX_VALUE)
										.add(jLabel2).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
								.add(layout.createSequentialGroup().add(18, 18, 18).add(jLabel1)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
								.add(panelCreneaux, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.add(panelReservations, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
								.add(layout
										.createSequentialGroup()
										.add(jLabel3)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(panelObjets, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.add(layout.createSequentialGroup().add(jLabel4).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(bValider).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(bRefuser)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(bAutre).add(bQuitter))))
						.addContainerGap()));

		pack();
	}// </editor-fold>

	private void validerResaObjet() {
		ReservationObjet resaObj = (ReservationObjet) listObjets.getSelectedItem();

		if (resaObj == null) {
			return;
		}

		resaObj.setResaEtat("R");
		resaObj.setDateValidation(new NSTimestamp());
		resaObj.addObjectToBothSidesOfRelationshipWithKey((IndividuUlr) app.userInfo("individu"), "individuValideur");

		app.saveChanges();

		NSDictionary dicoMail = preparerMailResaObjet(resaObj, OK_OBJET, null);

		if (dicoMail != null) {
			MailReservation mailReservation = app.mailReservation();
			mailReservation.setMailInfos(dicoMail);
			mailReservation.show();
		}

		chargerPourSemaine();
	}

	private void refuserResaObjet() {
		ReservationObjet resaObj = (ReservationObjet) listObjets.getSelectedItem();

		if (resaObj == null) {
			return;
		}

		// resaObj.setResaEtat("A");
		// resaObj.setDateValidation(new NSTimestamp());
		// resaObj.addObjectToBothSidesOfRelationshipWithKey((IndividuUlr) app.userInfo("individu"), "individuValideur");

		NSDictionary dicoMail = preparerMailResaObjet(resaObj, REFUS_OBJET, null);

		Reservation resa = resaObj.reservation();
		resa.removeFromReservationObjetsRelationship(resaObj);
		eContext.deleteObject(resaObj);
		if (resa.isEmpty()) {
			if (WindowHandler
					.showConfirmation("Il n'y a rien d'autre que cet objet dans cette réservation, cela supprimera donc la réservation totalement... continuer ?") == false) {
				app.revertChanges();
				return;
			}
			resa.deleteAllPeriodicitesRelationships();
			eContext.deleteObject(resa);
		}
		app.saveChanges();

		if (dicoMail != null) {
			MailReservation mailReservation = app.mailReservation();
			mailReservation.setMailInfos(dicoMail);
			mailReservation.show();
		}

		chargerPourSemaine();
	}

	private NSDictionary preparerMailResaObjet(ReservationObjet resaObj, int typeMessage, NSArray objetsRemplacement) {

		ResaObjet objet = resaObj.resaObjet();
		StringBuffer sujet = new StringBuffer("[SuperPlan]");
		Reservation resa = resaObj.reservation();

		StringBuffer message = new StringBuffer("Votre demande de prêt d'objet : ");
		message.append(objet.toString());
		message.append(" a été ");

		if (typeMessage == OK_OBJET) {
			sujet.append("Reservation acceptée de : ");
			message.append(" validée ");
		}

		if (typeMessage == REFUS_OBJET) {
			sujet.append("Reservation refusée de : ");
			message.append(" refusée ");
		}

		if (typeMessage == AUTRE_OBJET) {
			sujet.append("Reservation modifiée de : ");
			message.append(" modifiée en vous affectant un autre objet équivalent ");
		}

		message.append("par : \n");
		message.append(((IndividuUlr) app.userInfo("individu")).nomPrenom());

		if (resa.periodicites() != null && resa.periodicites().count() > 0) {
			NSMutableArray<Periodicite> periodicites = resa.periodicites().mutableClone();
			NSTimestampFormatter formatterJour = new NSTimestampFormatter("%A %d %B %Y");
			NSTimestampFormatter formatterHeure = new NSTimestampFormatter("%H:%M");
			try {
				periodicites.sortUsingComparator(new NSComparator() {
					public int compare(Object arg0, Object arg1) throws ComparisonException {
						return ((Periodicite) arg0).dateDeb().compare(((Periodicite) arg1).dateDeb());
					}
				});
			}
			catch (NSComparator.ComparisonException e) {
				e.printStackTrace();
			}
			message.append("\npour la(les) date(s) :\n");
			for (int i = 0; i < periodicites.count(); i++) {
				Periodicite p = periodicites.objectAtIndex(i);
				message.append("- le " + formatterJour.format(p.dateDeb()) + " de " + formatterHeure.format(p.dateDeb()) + " à "
						+ formatterHeure.format(p.dateFin()) + "\n");
			}
		}
		sujet.append(objet.toString());

		String to = app.getEmailIndividu(resa.individuAgent());
		String from = (String) app.userInfo("email");

		if (to == null || from == null) {
			return null;
		}
		else {
			NSMutableDictionary dico = new NSMutableDictionary();
			dico.setObjectForKey(from, "expediteur");
			dico.setObjectForKey(to, "destinataire");
			dico.setObjectForKey(sujet.toString(), "sujet");
			dico.setObjectForKey(message.toString(), "texte");
			return dico;
		}
	}

	private void bValiderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bValiderActionPerformed
		validerResaObjet();
	}// GEN-LAST:event_bValiderActionPerformed

	private void bRefuserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bRefuserActionPerformed
		refuserResaObjet();
	}// GEN-LAST:event_bRefuserActionPerformed

	private void bAutreActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAutreActionPerformed
	}// GEN-LAST:event_bAutreActionPerformed

	private void bQuitterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bQuitterActionPerformed
		setVisible(false);
	}// GEN-LAST:event_bQuitterActionPerformed

	private void bChargerSemaineActionPerformed(java.awt.event.ActionEvent evt) {
		chargerPourSemaine();
	}

	private class MyTableListener implements ZEOTable.ZEOTableListener {

		EODisplayGroup eod;

		public MyTableListener(EODisplayGroup eod) {
			this.eod = eod;
		}

		public void onDbClick() {
			// valider();
		}

		public void onSelectionChanged() {
			eod.selectedObject();
			displayGroupDidChangeSelection(eod);
		}
	}

	public void displayGroupDidChangeSelection(EODisplayGroup eod) {
		if ((eod == eodResa) && (eodResa.selectedObject() != null)) {
			NSKeyValueCoding currentCreneau = (NSKeyValueCoding) eodResa.selectedObject();
			afficherRessources(currentCreneau);
		}

		/*
		 * if (group == eodObjet) { NSKeyValueCoding ressource = (NSKeyValueCoding) eodRessource.selectedObject(); if (ressource == null) {
		 * return; } String resType = (String) ressource.valueForKey("resType"); if (resType.equals("SALLE") || resType.equals("CHOIX")) {
		 * mode = VALIDATION; type = ((resType.equals("SALLE")) ? RES_SALLE : RES_CHOIX); changeState(); } else { mode = VIDE;
		 * changeState(); } }
		 */
	}

	private void afficherRessources(NSKeyValueCoding creneau) {
		eodPeriodicites.setObjectArray(new NSArray());

		Reservation reservation = (Reservation) creneau.valueForKey("reservation");
		NSArray objets = reservation.reservationObjets();

		listObjets.setObjects(objets);
		// TODO : afficher les objets dans la liste ici.

		NSArray periods = reservation.periodicites();
		NSMutableArray __periodicites = new NSMutableArray();
		Periodicite period;
		NSTimestamp debut, fin;
		String jour;
		TimeHandler timeHandler;
		Number semaine;
		for (int i = 0; i < periods.count(); i++) {
			period = (Periodicite) periods.objectAtIndex(i);
			debut = period.dateDeb();
			fin = period.dateFin();
			jour = FormatHandler.dayString(debut);
			timeHandler = new TimeHandler();
			timeHandler.setUseAnneeCivile(((Boolean) app.userInfo("anneeCivile")).booleanValue());

			semaine = new Integer(timeHandler.weekOfYear(debut));
			Object[] cles = { "jour", "date", "semaine", "heureDeb", "heureFin", "periodicite" };
			String debStr = FormatHandler.dateToStr(debut, "%H:%M");
			String finStr = FormatHandler.dateToStr(fin, "%H:%M");
			Object[] valeurs = { jour, debut, semaine, debStr, finStr, period };
			__periodicites.addObject(new NSDictionary(valeurs, cles));
		}
		try {
			eodPeriodicites.setObjectArray(__periodicites.sortedArrayUsingComparator(new PeriodicitesComparator()));
		}
		catch (NSComparator.ComparisonException e) {
			e.printStackTrace();
			WindowHandler.showError("Depositaire : Erreur recuperation dates");
		}
		eodPeriodicites.setSelectedObject(null);

		tablePeriodicites.updateData();
		tablePeriodicites.updateUI();

	}

	public class PeriodicitesComparator extends NSComparator {

		public int compare(Object object1, Object object2) throws NSComparator.ComparisonException {
			int semaine1 = ((Number) ((NSDictionary) object1).objectForKey("semaine")).intValue();
			int semaine2 = ((Number) ((NSDictionary) object2).objectForKey("semaine")).intValue();

			if (semaine1 > semaine2) {
				return NSComparator.OrderedDescending;
			}
			else {
				return NSComparator.OrderedAscending;
			}
		}
	}

	// Variables declaration - do not modify
	private javax.swing.JButton bAutre;
	private javax.swing.JButton bChargerSemaine;
	private javax.swing.JButton bQuitter;
	private javax.swing.JButton bRefuser;
	private javax.swing.JButton bValider;
	private javax.swing.JComboBox comboAnnee;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JPanel panelCreneaux;
	private javax.swing.JPanel panelObjets;
	private javax.swing.JPanel panelReservations;
	private javax.swing.JTextField tSemaine;
	// End of variables declaration

}
