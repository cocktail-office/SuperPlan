// _RechercheCreneaux_EOArchive.java
// Generated by EnterpriseObjects palette at jeudi 27 avril 2006 18 h 15 Europe/Paris

package edtscol.client;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eointerface.swing.EOViewLayout;
import com.webobjects.foundation.NSDisposableRegistry;

public class _RechercheCreneaux_EOArchive extends com.webobjects.eoapplication.EOArchive {
    com.webobjects.eocontrol.EODataSource _eoDataSource0;
    com.webobjects.eocontrol.EOEditingContext _eoEditingContext0;
    com.webobjects.eointerface.EODisplayGroup _eoDisplayGroup0;
    com.webobjects.eointerface.EOTableAssociation _eoTableAssociation0;
    com.webobjects.eointerface.EOTableColumnAssociation _eoTableColumnAssociation0, _eoTableColumnAssociation1, _eoTableColumnAssociation2, _eoTableColumnAssociation3, _eoTableColumnAssociation4;
    com.webobjects.eointerface.swing.EOFrame _eoFrame0;
    com.webobjects.eointerface.swing.EOTable _nsTableView0;
    com.webobjects.eointerface.swing.EOTable._EOTableColumn _eoTableColumn0, _eoTableColumn1, _eoTableColumn2, _eoTableColumn3, _eoTableColumn4;
    com.webobjects.eointerface.swing.EOTextField _nsTextField0, _nsTextField1, _nsTextField10, _nsTextField11, _nsTextField12, _nsTextField13, _nsTextField14, _nsTextField2, _nsTextField3, _nsTextField4, _nsTextField5, _nsTextField6, _nsTextField7, _nsTextField8, _nsTextField9;
    com.webobjects.eointerface.swing.EOView _nsBox0, _nsBox1;
    com.webobjects.foundation.NSTimestampFormatter _nsTimestampFormatter0, _nsTimestampFormatter1, _nsTimestampFormatter2;
    fr.univlr.utilities.SwapView _nsCustomView0;
    javax.swing.JButton _nsButton0, _nsButton1, _nsButton2, _nsButton3;
    javax.swing.JCheckBox _nsButton4, _nsButton5, _nsButton6;
    javax.swing.JPanel _nsView0;

    public _RechercheCreneaux_EOArchive(Object owner, NSDisposableRegistry registry) {
        super(owner, registry);
    }

    protected void _construct() {
        Object owner = _owner();
        EOArchive._ObjectInstantiationDelegate delegate = (owner instanceof EOArchive._ObjectInstantiationDelegate) ? (EOArchive._ObjectInstantiationDelegate)owner : null;
        Object replacement;

        super._construct();

        _eoEditingContext0 = ((com.webobjects.eocontrol.EOEditingContext.substitutionEditingContext() != null) ? com.webobjects.eocontrol.EOEditingContext.substitutionEditingContext() : (com.webobjects.eocontrol.EOEditingContext)_registered(new com.webobjects.eocontrol.EOEditingContext(), "EditingContext"));
        _eoDataSource0 = (com.webobjects.eocontrol.EODataSource)_registered(com.webobjects.eoapplication.EODataSourceFactory.defaultDataSourceFactory().newMasterDataSource(_eoEditingContext0, "Periodicite", null), "DataSource");
        _nsTextField14 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField1");
        _nsTextField13 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField1311");
        _nsTextField12 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField131");
        _nsTextField11 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField13");
        _nsTextField10 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField11111");
        _nsTextField9 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField1111");
        _nsTextField8 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField111");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "cToutes")) != null)) {
            _nsButton6 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JCheckBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton6");
        } else {
            _nsButton6 = (javax.swing.JCheckBox)_registered(new javax.swing.JCheckBox("Toutes les salles disponibles dans le m\u00eame cr\u00e9neau"), "NSButton42");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "cDimanche")) != null)) {
            _nsButton5 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JCheckBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton5");
        } else {
            _nsButton5 = (javax.swing.JCheckBox)_registered(new javax.swing.JCheckBox("Dimanche"), "NSButton41");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "cSamedi")) != null)) {
            _nsButton4 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JCheckBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton4");
        } else {
            _nsButton4 = (javax.swing.JCheckBox)_registered(new javax.swing.JCheckBox("Samedi"), "NSButton4");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bFinRecherche")) != null)) {
            _nsButton3 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton3");
        } else {
            _nsButton3 = (javax.swing.JButton)_registered(new javax.swing.JButton("Button"), "NSButton11");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bDebRecherche")) != null)) {
            _nsButton2 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton2");
        } else {
            _nsButton2 = (javax.swing.JButton)_registered(new javax.swing.JButton("Button"), "NSButton2");
        }

        _nsTimestampFormatter2 = (com.webobjects.foundation.NSTimestampFormatter)_registered(new com.webobjects.foundation.NSTimestampFormatter("%H:%M"), "");
        _eoTableColumn4 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _nsTableView0 = (com.webobjects.eointerface.swing.EOTable)_registered(new com.webobjects.eointerface.swing.EOTable(), "");
        _eoTableColumnAssociation4 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn4, _nsTableView0), "");
        _nsTimestampFormatter1 = (com.webobjects.foundation.NSTimestampFormatter)_registered(new com.webobjects.foundation.NSTimestampFormatter("%H:%M"), "");
        _eoTableColumn3 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation3 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn3, _nsTableView0), "");
        _nsTimestampFormatter0 = (com.webobjects.foundation.NSTimestampFormatter)_registered(new com.webobjects.foundation.NSTimestampFormatter("%d/%m/%Y"), "");
        _eoTableColumn2 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation2 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn2, _nsTableView0), "");
        _eoTableColumn1 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation1 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn1, _nsTableView0), "");
        _eoTableColumn0 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation0 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn0, _nsTableView0), "");
        _eoTableAssociation0 = (com.webobjects.eointerface.EOTableAssociation)_registered(new com.webobjects.eointerface.EOTableAssociation(_nsTableView0), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodCreneaux")) != null)) {
            _eoDisplayGroup0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.EODisplayGroup)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDisplayGroup0");
        } else {
            _eoDisplayGroup0 = (com.webobjects.eointerface.EODisplayGroup)_registered(new com.webobjects.eointerface.EODisplayGroup(), "Creneaux");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bAfficherInspecteur")) != null)) {
            _nsButton1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton1");
        } else {
            _nsButton1 = (javax.swing.JButton)_registered(new javax.swing.JButton("Afficher dans l'inspecteur"), "NSButton1");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "btRechCreneaux")) != null)) {
            _nsButton0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton0");
        } else {
            _nsButton0 = (javax.swing.JButton)_registered(new javax.swing.JButton("Rechercher les cr\u00e9neaux libres"), "NSButton");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tMFinCreneau")) != null)) {
            _nsTextField7 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField7");
        } else {
            _nsTextField7 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField121");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tHFinCreneau")) != null)) {
            _nsTextField6 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField6");
        } else {
            _nsTextField6 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField31");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tMDebutCreneau")) != null)) {
            _nsTextField5 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField5");
        } else {
            _nsTextField5 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField12");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tHDebutCreneau")) != null)) {
            _nsTextField4 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField4");
        } else {
            _nsTextField4 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField3");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tDateFin")) != null)) {
            _nsTextField3 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField3");
        } else {
            _nsTextField3 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField21");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tDateDebut")) != null)) {
            _nsTextField2 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField2");
        } else {
            _nsTextField2 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField2");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tMDuree")) != null)) {
            _nsTextField1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField1");
        } else {
            _nsTextField1 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField11");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tHDuree")) != null)) {
            _nsTextField0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField0");
        } else {
            _nsTextField0 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "swapPanier")) != null)) {
            _nsCustomView0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (fr.univlr.utilities.SwapView)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsCustomView0");
        } else {
            _nsCustomView0 = (fr.univlr.utilities.SwapView)_registered(new fr.univlr.utilities.SwapView(), "View");
        }

        _nsBox1 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "NSView");
        _nsBox0 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "NSBox");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "component")) != null)) {
            _eoFrame0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOFrame)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoFrame0");
        } else {
            _eoFrame0 = (com.webobjects.eointerface.swing.EOFrame)_registered(new com.webobjects.eointerface.swing.EOFrame(), "MainWindow");
        }

        _nsView0 = (JPanel)_eoFrame0.getContentPane();
    }

    protected void _awaken() {
        super._awaken();

        if (_replacedObjects.objectForKey("_nsButton6") == null) {
            _connect(_owner(), _nsButton6, "cToutes");
        }

        if (_replacedObjects.objectForKey("_nsButton5") == null) {
            _connect(_owner(), _nsButton5, "cDimanche");
        }

        if (_replacedObjects.objectForKey("_nsButton4") == null) {
            _connect(_owner(), _nsButton4, "cSamedi");
        }

        _nsButton3.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "choixDateFinRecherche", _nsButton3), ""));

        if (_replacedObjects.objectForKey("_nsButton3") == null) {
            _connect(_owner(), _nsButton3, "bFinRecherche");
        }

        _nsButton2.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "choixDateDebRecherche", _nsButton2), ""));

        if (_replacedObjects.objectForKey("_nsButton2") == null) {
            _connect(_owner(), _nsButton2, "bDebRecherche");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _connect(_owner(), _eoDisplayGroup0, "eodCreneaux");
        }

        _nsButton1.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "afficherDansInspecteur", _nsButton1), ""));

        if (_replacedObjects.objectForKey("_nsButton1") == null) {
            _connect(_owner(), _nsButton1, "bAfficherInspecteur");
        }

        _nsButton0.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "rechercherCreneaux", _nsButton0), ""));

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _connect(_owner(), _nsButton0, "btRechCreneaux");
        }

        if (_replacedObjects.objectForKey("_nsTextField7") == null) {
            _connect(_owner(), _nsTextField7, "tMFinCreneau");
        }

        if (_replacedObjects.objectForKey("_nsTextField6") == null) {
            _connect(_owner(), _nsTextField6, "tHFinCreneau");
        }

        if (_replacedObjects.objectForKey("_nsTextField5") == null) {
            _connect(_owner(), _nsTextField5, "tMDebutCreneau");
        }

        if (_replacedObjects.objectForKey("_nsTextField4") == null) {
            _connect(_owner(), _nsTextField4, "tHDebutCreneau");
        }

        if (_replacedObjects.objectForKey("_nsTextField3") == null) {
            _connect(_owner(), _nsTextField3, "tDateFin");
        }

        if (_replacedObjects.objectForKey("_nsTextField2") == null) {
            _connect(_owner(), _nsTextField2, "tDateDebut");
        }

        if (_replacedObjects.objectForKey("_nsTextField1") == null) {
            _connect(_owner(), _nsTextField1, "tMDuree");
        }

        if (_replacedObjects.objectForKey("_nsTextField0") == null) {
            _connect(_owner(), _nsTextField0, "tHDuree");
        }

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _connect(_owner(), _eoFrame0, "component");
        }

        if (_replacedObjects.objectForKey("_nsCustomView0") == null) {
            _connect(_owner(), _nsCustomView0, "swapPanier");
        }
    }

    protected void _init() {
        super._init();
        _connect(_eoDataSource0, _eoEditingContext0, "editingContext");
        _setFontForComponent(_nsTextField14, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
        _nsTextField14.setEditable(false);
        _nsTextField14.setOpaque(false);
        _nsTextField14.setText("Panier de ressources");
        _nsTextField14.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField14.setSelectable(false);
        _nsTextField14.setEnabled(true);
        _nsTextField14.setBorder(null);
        _setFontForComponent(_nsTextField13, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
        _nsTextField13.setEditable(false);
        _nsTextField13.setOpaque(false);
        _nsTextField13.setText("Creneau demand\u00e9");
        _nsTextField13.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField13.setSelectable(false);
        _nsTextField13.setEnabled(true);
        _nsTextField13.setBorder(null);
        _setFontForComponent(_nsTextField12, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
        _nsTextField12.setEditable(false);
        _nsTextField12.setOpaque(false);
        _nsTextField12.setText("Date de recherche");
        _nsTextField12.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField12.setSelectable(false);
        _nsTextField12.setEnabled(true);
        _nsTextField12.setBorder(null);
        _setFontForComponent(_nsTextField11, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
        _nsTextField11.setEditable(false);
        _nsTextField11.setOpaque(false);
        _nsTextField11.setText("Dur\u00e9e r\u00e9servation");
        _nsTextField11.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField11.setSelectable(false);
        _nsTextField11.setEnabled(true);
        _nsTextField11.setBorder(null);
        _setFontForComponent(_nsTextField10, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
        _nsTextField10.setEditable(false);
        _nsTextField10.setOpaque(false);
        _nsTextField10.setText(":");
        _nsTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _nsTextField10.setSelectable(false);
        _nsTextField10.setEnabled(true);
        _nsTextField10.setBorder(null);
        _setFontForComponent(_nsTextField9, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
        _nsTextField9.setEditable(false);
        _nsTextField9.setOpaque(false);
        _nsTextField9.setText(":");
        _nsTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _nsTextField9.setSelectable(false);
        _nsTextField9.setEnabled(true);
        _nsTextField9.setBorder(null);
        _setFontForComponent(_nsTextField8, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
        _nsTextField8.setEditable(false);
        _nsTextField8.setOpaque(false);
        _nsTextField8.setText(":");
        _nsTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _nsTextField8.setSelectable(false);
        _nsTextField8.setEnabled(true);
        _nsTextField8.setBorder(null);

        if (_replacedObjects.objectForKey("_nsButton6") == null) {
            _setFontForComponent(_nsButton6, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_nsButton5") == null) {
            _setFontForComponent(_nsButton5, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_nsButton4") == null) {
            _setFontForComponent(_nsButton4, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_nsButton3") == null) {
            _setFontForComponent(_nsButton3, "Lucida Grande", 13, Font.PLAIN);
            _nsButton3.setMargin(new Insets(0, 2, 0, 2));
        }

        if (_replacedObjects.objectForKey("_nsButton2") == null) {
            _setFontForComponent(_nsButton2, "Lucida Grande", 13, Font.PLAIN);
            _nsButton2.setMargin(new Insets(0, 2, 0, 2));
        }

        _eoTableColumnAssociation4.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "heureFin");
        if (_eoTableColumnAssociation4.canSupportValueFormatter()) { _eoTableColumnAssociation4.setValueFormatter(_nsTimestampFormatter2); }
        _eoTableColumnAssociation4.establishConnection();
        _eoTableColumn4.setMinWidth(25);
        _eoTableColumn4.setMaxWidth(1000);
        _eoTableColumn4.setPreferredWidth(68);
        _eoTableColumn4.setWidth(68);
        _eoTableColumn4.setResizable(true);
        _eoTableColumn4.setHeaderValue("Fin");
        if ((_eoTableColumn4.getCellRenderer() instanceof DefaultTableCellRenderer) || (_eoTableColumn4.getCellRenderer() == null)) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        	_eoTableColumn4.setCellRenderer(renderer);
        }
        _eoTableColumnAssociation3.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "heureDebut");
        if (_eoTableColumnAssociation3.canSupportValueFormatter()) { _eoTableColumnAssociation3.setValueFormatter(_nsTimestampFormatter1); }
        _eoTableColumnAssociation3.establishConnection();
        _eoTableColumn3.setMinWidth(10);
        _eoTableColumn3.setMaxWidth(1000);
        _eoTableColumn3.setPreferredWidth(65);
        _eoTableColumn3.setWidth(65);
        _eoTableColumn3.setResizable(true);
        _eoTableColumn3.setHeaderValue("D\u00e9but");
        if ((_eoTableColumn3.getCellRenderer() instanceof DefaultTableCellRenderer) || (_eoTableColumn3.getCellRenderer() == null)) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        	_eoTableColumn3.setCellRenderer(renderer);
        }
        _eoTableColumnAssociation2.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "date");
        if (_eoTableColumnAssociation2.canSupportValueFormatter()) { _eoTableColumnAssociation2.setValueFormatter(_nsTimestampFormatter0); }
        _eoTableColumnAssociation2.establishConnection();
        _eoTableColumn2.setMinWidth(10);
        _eoTableColumn2.setMaxWidth(1000);
        _eoTableColumn2.setPreferredWidth(80);
        _eoTableColumn2.setWidth(80);
        _eoTableColumn2.setResizable(true);
        _eoTableColumn2.setHeaderValue("Date");
        if ((_eoTableColumn2.getCellRenderer() instanceof DefaultTableCellRenderer) || (_eoTableColumn2.getCellRenderer() == null)) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        	_eoTableColumn2.setCellRenderer(renderer);
        }
        _eoTableColumnAssociation1.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "jour");
        _eoTableColumnAssociation1.establishConnection();
        _eoTableColumn1.setMinWidth(10);
        _eoTableColumn1.setMaxWidth(1000);
        _eoTableColumn1.setPreferredWidth(52);
        _eoTableColumn1.setWidth(52);
        _eoTableColumn1.setResizable(true);
        _eoTableColumn1.setHeaderValue("Jour");
        _eoTableColumnAssociation0.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "salle");
        _eoTableColumnAssociation0.establishConnection();
        _eoTableColumn0.setMinWidth(10);
        _eoTableColumn0.setMaxWidth(1000);
        _eoTableColumn0.setPreferredWidth(64);
        _eoTableColumn0.setWidth(64);
        _eoTableColumn0.setResizable(true);
        _eoTableColumn0.setHeaderValue("Salle");
        _nsTableView0.table().addColumn(_eoTableColumn0);
        _nsTableView0.table().addColumn(_eoTableColumn1);
        _nsTableView0.table().addColumn(_eoTableColumn2);
        _nsTableView0.table().addColumn(_eoTableColumn3);
        _nsTableView0.table().addColumn(_eoTableColumn4);
        _setFontForComponent(_nsTableView0.table().getTableHeader(), "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
        _nsTableView0.table().setRowHeight(19);
        _eoTableAssociation0.bindAspect(com.webobjects.eointerface.EOAssociation.SourceAspect, _eoDisplayGroup0, "");
        _eoTableAssociation0.setSortsByColumnOrder(true);
        _eoTableAssociation0.establishConnection();

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _eoDisplayGroup0.setValidatesChangesImmediately(false);
            _eoDisplayGroup0.setFetchesOnLoad(false);
            _eoDisplayGroup0.setUsesOptimisticRefresh(false);
            _eoDisplayGroup0.setSelectsFirstObjectAfterFetch(true);
            _eoDisplayGroup0.setLocalKeys(new com.webobjects.foundation.NSArray(new Object[] {"jour", "date", "heureDebut", "heureFin", "salle"}));
        }

        if (_replacedObjects.objectForKey("_nsButton1") == null) {
            _setFontForComponent(_nsButton1, "Lucida Grande", 13, Font.PLAIN);
            _nsButton1.setMargin(new Insets(0, 2, 0, 2));
        }

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _setFontForComponent(_nsButton0, "Lucida Grande", 13, Font.PLAIN);
            _nsButton0.setMargin(new Insets(0, 2, 0, 2));
        }

        if (_replacedObjects.objectForKey("_nsTextField7") == null) {
            _setFontForComponent(_nsTextField7, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
            _nsTextField7.setEditable(true);
            _nsTextField7.setOpaque(true);
            _nsTextField7.setText("");
            _nsTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            _nsTextField7.setSelectable(true);
            _nsTextField7.setEnabled(true);
        }

        if (_replacedObjects.objectForKey("_nsTextField6") == null) {
            _setFontForComponent(_nsTextField6, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
            _nsTextField6.setEditable(true);
            _nsTextField6.setOpaque(true);
            _nsTextField6.setText("");
            _nsTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            _nsTextField6.setSelectable(true);
            _nsTextField6.setEnabled(true);
        }

        if (_replacedObjects.objectForKey("_nsTextField5") == null) {
            _setFontForComponent(_nsTextField5, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
            _nsTextField5.setEditable(true);
            _nsTextField5.setOpaque(true);
            _nsTextField5.setText("");
            _nsTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            _nsTextField5.setSelectable(true);
            _nsTextField5.setEnabled(true);
        }

        if (_replacedObjects.objectForKey("_nsTextField4") == null) {
            _setFontForComponent(_nsTextField4, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
            _nsTextField4.setEditable(true);
            _nsTextField4.setOpaque(true);
            _nsTextField4.setText("");
            _nsTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            _nsTextField4.setSelectable(true);
            _nsTextField4.setEnabled(true);
        }

        if (_replacedObjects.objectForKey("_nsTextField3") == null) {
            _setFontForComponent(_nsTextField3, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
            _nsTextField3.setEditable(true);
            _nsTextField3.setOpaque(true);
            _nsTextField3.setText("");
            _nsTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            _nsTextField3.setSelectable(true);
            _nsTextField3.setEnabled(true);
        }

        if (_replacedObjects.objectForKey("_nsTextField2") == null) {
            _setFontForComponent(_nsTextField2, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
            _nsTextField2.setEditable(true);
            _nsTextField2.setOpaque(true);
            _nsTextField2.setText("");
            _nsTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            _nsTextField2.setSelectable(true);
            _nsTextField2.setEnabled(true);
        }

        if (_replacedObjects.objectForKey("_nsTextField1") == null) {
            _setFontForComponent(_nsTextField1, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
            _nsTextField1.setEditable(true);
            _nsTextField1.setOpaque(true);
            _nsTextField1.setText("");
            _nsTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            _nsTextField1.setSelectable(true);
            _nsTextField1.setEnabled(true);
        }

        if (_replacedObjects.objectForKey("_nsTextField0") == null) {
            _setFontForComponent(_nsTextField0, "Lucida Grande", 11, Font.PLAIN + Font.BOLD);
            _nsTextField0.setEditable(true);
            _nsTextField0.setOpaque(true);
            _nsTextField0.setText("");
            _nsTextField0.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            _nsTextField0.setSelectable(true);
            _nsTextField0.setEnabled(true);
        }

        if (!(_nsBox1.getLayout() instanceof EOViewLayout)) { _nsBox1.setLayout(new EOViewLayout()); }
        _nsCustomView0.setSize(420, 500);
        _nsCustomView0.setLocation(5, 22);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsCustomView0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsCustomView0);
        _nsTextField0.setSize(22, 19);
        _nsTextField0.setLocation(543, 38);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField0);
        _nsTextField1.setSize(22, 19);
        _nsTextField1.setLocation(573, 38);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField1, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField1);
        _nsTextField8.setSize(15, 14);
        _nsTextField8.setLocation(562, 40);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField8, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField8);
        _nsTextField2.setSize(89, 19);
        _nsTextField2.setLocation(542, 68);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField2, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField2);
        _nsTextField3.setSize(89, 19);
        _nsTextField3.setLocation(670, 68);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField3, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField3);
        _nsTextField4.setSize(22, 19);
        _nsTextField4.setLocation(542, 99);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField4, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField4);
        _nsTextField5.setSize(22, 19);
        _nsTextField5.setLocation(577, 99);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField5, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField5);
        _nsTextField9.setSize(15, 14);
        _nsTextField9.setLocation(563, 102);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField9, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField9);
        _nsTextField6.setSize(22, 19);
        _nsTextField6.setLocation(630, 99);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField6, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField6);
        _nsTextField7.setSize(22, 19);
        _nsTextField7.setLocation(663, 99);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField7, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField7);
        _nsTextField10.setSize(15, 14);
        _nsTextField10.setLocation(650, 102);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField10, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField10);
        _nsTextField11.setSize(102, 14);
        _nsTextField11.setLocation(441, 43);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField11, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField11);
        _nsTextField12.setSize(102, 14);
        _nsTextField12.setLocation(441, 70);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField12, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField12);
        _nsTextField13.setSize(102, 14);
        _nsTextField13.setLocation(441, 99);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField13, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField13);
        _nsButton2.setSize(23, 21);
        _nsButton2.setLocation(635, 66);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton2, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton2);
        _nsButton3.setSize(23, 21);
        _nsButton3.setLocation(763, 66);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton3, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton3);
        _nsButton4.setSize(80, 17);
        _nsButton4.setLocation(692, 94);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton4, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton4);
        _nsButton5.setSize(95, 17);
        _nsButton5.setLocation(692, 114);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton5, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton5);
        _nsButton0.setSize(226, 26);
        _nsButton0.setLocation(500, 170);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton0);
        _nsTableView0.setSize(361, 256);
        _nsTableView0.setLocation(433, 214);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTableView0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTableView0);
        _nsButton1.setSize(226, 26);
        _nsButton1.setLocation(508, 489);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton1, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton1);
        _nsTextField14.setSize(129, 14);
        _nsTextField14.setLocation(5, 5);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField14, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField14);
        _nsButton6.setSize(349, 17);
        _nsButton6.setLocation(441, 137);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton6, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton6);
        if (!(_nsBox0.getLayout() instanceof EOViewLayout)) { _nsBox0.setLayout(new EOViewLayout()); }
        _nsBox1.setSize(828, 533);
        _nsBox1.setLocation(2, 2);
        ((EOViewLayout)_nsBox0.getLayout()).setAutosizingMask(_nsBox1, EOViewLayout.MinYMargin);
        _nsBox0.add(_nsBox1);
        _nsBox0.setBorder(new com.webobjects.eointerface.swing._EODefaultBorder("", true, "Lucida Grande", 13, Font.PLAIN));
        if (!(_nsView0.getLayout() instanceof EOViewLayout)) { _nsView0.setLayout(new EOViewLayout()); }
        _nsBox0.setSize(832, 537);
        _nsBox0.setLocation(4, 8);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsBox0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsBox0);

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _nsView0.setSize(846, 555);
            _eoFrame0.setTitle("Window");
            _eoFrame0.setLocation(310, 283);
            _eoFrame0.setSize(846, 555);
        }
    }
}