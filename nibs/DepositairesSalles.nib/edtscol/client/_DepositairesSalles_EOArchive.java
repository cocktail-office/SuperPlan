// _DepositairesSalles_EOArchive.java
// Generated by EnterpriseObjects palette at vendredi 4 avril 2008 13 h 32 Europe/Paris

package edtscol.client;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eointerface.swing.EOViewLayout;
import com.webobjects.foundation.NSDisposableRegistry;

public class _DepositairesSalles_EOArchive extends com.webobjects.eoapplication.EOArchive {
    com.webobjects.eocontrol.EODataSource _eoDataSource0, _eoDataSource1, _eoDataSource2, _eoDataSource3;
    com.webobjects.eocontrol.EOEditingContext _eoEditingContext0;
    com.webobjects.eointerface.EODisplayGroup _eoDisplayGroup0, _eoDisplayGroup1, _eoDisplayGroup2, _eoDisplayGroup3;
    com.webobjects.eointerface.EOTableAssociation _eoTableAssociation0, _eoTableAssociation1, _eoTableAssociation2;
    com.webobjects.eointerface.EOTableColumnAssociation _eoTableColumnAssociation0, _eoTableColumnAssociation1, _eoTableColumnAssociation10, _eoTableColumnAssociation2, _eoTableColumnAssociation3, _eoTableColumnAssociation4, _eoTableColumnAssociation5, _eoTableColumnAssociation6, _eoTableColumnAssociation7, _eoTableColumnAssociation8, _eoTableColumnAssociation9;
    com.webobjects.eointerface.swing.EOFrame _eoFrame0;
    com.webobjects.eointerface.swing.EOMatrix _nsMatrix0;
    com.webobjects.eointerface.swing.EOTable _nsTableView0, _nsTableView1, _nsTableView2;
    com.webobjects.eointerface.swing.EOTable._EOTableColumn _eoTableColumn0, _eoTableColumn1, _eoTableColumn10, _eoTableColumn2, _eoTableColumn3, _eoTableColumn4, _eoTableColumn5, _eoTableColumn6, _eoTableColumn7, _eoTableColumn8, _eoTableColumn9;
    com.webobjects.eointerface.swing.EOTextField _nsTextField0, _nsTextField1, _nsTextField2, _nsTextField3;
    com.webobjects.eointerface.swing.EOView _nsBox0, _nsBox1;
    com.webobjects.foundation.NSTimestampFormatter _nsTimestampFormatter0, _nsTimestampFormatter1, _nsTimestampFormatter2;
    javax.swing.JButton _nsButton0, _nsButton1, _nsButton2, _nsButton3;
    javax.swing.JComboBox _popup0;
    javax.swing.JLabel _nsCustomView0;
    javax.swing.JPanel _nsView0;
    javax.swing.JRadioButton _jRadioButton0, _jRadioButton1;

    public _DepositairesSalles_EOArchive(Object owner, NSDisposableRegistry registry) {
        super(owner, registry);
    }

    protected void _construct() {
        Object owner = _owner();
        EOArchive._ObjectInstantiationDelegate delegate = (owner instanceof EOArchive._ObjectInstantiationDelegate) ? (EOArchive._ObjectInstantiationDelegate)owner : null;
        Object replacement;

        super._construct();

        _nsTextField3 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField11");
        _nsTextField2 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField1");
        _nsTextField1 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField2");
        _eoTableColumn10 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tablePeriodiciteSalle")) != null)) {
            _nsTableView0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTable)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTableView0");
        } else {
            _nsTableView0 = (com.webobjects.eointerface.swing.EOTable)_registered(new com.webobjects.eointerface.swing.EOTable(), "");
        }

        _eoTableColumnAssociation10 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn10, _nsTableView0), "");
        _nsButton3 = (javax.swing.JButton)_registered(new javax.swing.JButton("Charger"), "NSButton4");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "comboAnnee")) != null)) {
            _popup0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JComboBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_popup0");
        } else {
            _popup0 = (javax.swing.JComboBox)_registered(new javax.swing.JComboBox(), "NSPopUpButton1");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tSemaine")) != null)) {
            _nsTextField0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField0");
        } else {
            _nsTextField0 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "lblAide")) != null)) {
            _nsCustomView0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JLabel)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsCustomView0");
        } else {
            _nsCustomView0 = (javax.swing.JLabel)_registered(new javax.swing.JLabel(), "View");
        }

        _jRadioButton1 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Sans occupant"), "");
        _jRadioButton0 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Avec occupants"), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "matOccupants")) != null)) {
            _nsMatrix0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOMatrix)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsMatrix0");
        } else {
            _nsMatrix0 = (com.webobjects.eointerface.swing.EOMatrix)_registered(new com.webobjects.eointerface.swing.EOMatrix(1, 2, 4, 2), "NSMatrix1");
        }

        _nsTimestampFormatter2 = (com.webobjects.foundation.NSTimestampFormatter)_registered(new com.webobjects.foundation.NSTimestampFormatter("%H:%M"), "");
        _eoTableColumn9 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tablePeriodicites")) != null)) {
            _nsTableView2 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTable)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTableView2");
        } else {
            _nsTableView2 = (com.webobjects.eointerface.swing.EOTable)_registered(new com.webobjects.eointerface.swing.EOTable(), "");
        }

        _eoTableColumnAssociation9 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn9, _nsTableView2), "");
        _nsTimestampFormatter1 = (com.webobjects.foundation.NSTimestampFormatter)_registered(new com.webobjects.foundation.NSTimestampFormatter("%H:%M"), "");
        _eoTableColumn8 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation8 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn8, _nsTableView2), "");
        _nsTimestampFormatter0 = (com.webobjects.foundation.NSTimestampFormatter)_registered(new com.webobjects.foundation.NSTimestampFormatter("%d/%m/%y"), "");
        _eoTableColumn7 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation7 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn7, _nsTableView2), "");
        _eoTableColumn6 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation6 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn6, _nsTableView2), "");
        _eoTableAssociation2 = (com.webobjects.eointerface.EOTableAssociation)_registered(new com.webobjects.eointerface.EOTableAssociation(_nsTableView2), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodPeriodicites")) != null)) {
            _eoDisplayGroup3 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.EODisplayGroup)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDisplayGroup3");
        } else {
            _eoDisplayGroup3 = (com.webobjects.eointerface.EODisplayGroup)_registered(new com.webobjects.eointerface.EODisplayGroup(), "Periodicites");
        }

        _eoTableColumn5 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation5 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn5, _nsTableView2), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bRechercher")) != null)) {
            _nsButton2 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton2");
        } else {
            _nsButton2 = (javax.swing.JButton)_registered(new javax.swing.JButton("Button"), "NSButton2");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bRefuser")) != null)) {
            _nsButton1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton1");
        } else {
            _nsButton1 = (javax.swing.JButton)_registered(new javax.swing.JButton("Button"), "NSButton1");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bValider")) != null)) {
            _nsButton0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton0");
        } else {
            _nsButton0 = (javax.swing.JButton)_registered(new javax.swing.JButton("Button"), "NSButton");
        }

        _eoEditingContext0 = ((com.webobjects.eocontrol.EOEditingContext.substitutionEditingContext() != null) ? com.webobjects.eocontrol.EOEditingContext.substitutionEditingContext() : (com.webobjects.eocontrol.EOEditingContext)_registered(new com.webobjects.eocontrol.EOEditingContext(), "EditingContext"));
        _eoDataSource3 = (com.webobjects.eocontrol.EODataSource)_registered(com.webobjects.eoapplication.EODataSourceFactory.defaultDataSourceFactory().newMasterDataSource(_eoEditingContext0, "Periodicite", null), "DataSource");
        _eoDataSource2 = (com.webobjects.eocontrol.EODataSource)_registered(com.webobjects.eoapplication.EODataSourceFactory.defaultDataSourceFactory().newMasterDataSource(_eoEditingContext0, "VSallePeriodicite", null), "DataSource");
        _eoDataSource1 = (com.webobjects.eocontrol.EODataSource)_registered(com.webobjects.eoapplication.EODataSourceFactory.defaultDataSourceFactory().newMasterDataSource(_eoEditingContext0, "VSallePeriodicite", null), "DataSource");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodSalle.dataSource")) != null)) {
            _eoDataSource0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eocontrol.EODataSource)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDataSource0");
        } else {
            _eoDataSource0 = (com.webobjects.eocontrol.EODataSource)_registered(com.webobjects.eoapplication.EODataSourceFactory.defaultDataSourceFactory().newMasterDataSource(_eoEditingContext0, "Salles", null), "DataSource");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodSalle")) != null)) {
            _eoDisplayGroup2 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.EODisplayGroup)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDisplayGroup2");
        } else {
            _eoDisplayGroup2 = (com.webobjects.eointerface.EODisplayGroup)_registered(new com.webobjects.eointerface.EODisplayGroup(), "Salles");
        }

        _eoTableColumn4 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tableRessource")) != null)) {
            _nsTableView1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTable)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTableView1");
        } else {
            _nsTableView1 = (com.webobjects.eointerface.swing.EOTable)_registered(new com.webobjects.eointerface.swing.EOTable(), "");
        }

        _eoTableColumnAssociation4 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn4, _nsTableView1), "");
        _eoTableColumn3 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation3 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn3, _nsTableView1), "");
        _eoTableAssociation1 = (com.webobjects.eointerface.EOTableAssociation)_registered(new com.webobjects.eointerface.EOTableAssociation(_nsTableView1), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodRessource")) != null)) {
            _eoDisplayGroup1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.EODisplayGroup)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDisplayGroup1");
        } else {
            _eoDisplayGroup1 = (com.webobjects.eointerface.EODisplayGroup)_registered(new com.webobjects.eointerface.EODisplayGroup(), "Ressources");
        }

        _eoTableColumn2 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation2 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn2, _nsTableView1), "");
        _eoTableColumn1 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation1 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn1, _nsTableView0), "");
        _eoTableAssociation0 = (com.webobjects.eointerface.EOTableAssociation)_registered(new com.webobjects.eointerface.EOTableAssociation(_nsTableView0), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodResa")) != null)) {
            _eoDisplayGroup0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.EODisplayGroup)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDisplayGroup0");
        } else {
            _eoDisplayGroup0 = (com.webobjects.eointerface.EODisplayGroup)_registered(new com.webobjects.eointerface.EODisplayGroup(), "Resa");
        }

        _eoTableColumn0 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation0 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn0, _nsTableView0), "");
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
        _nsButton3.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "chargerPourSemaine", _nsButton3), ""));

        if (_replacedObjects.objectForKey("_popup0") == null) {
            _popup0.setModel(new javax.swing.DefaultComboBoxModel());
            _popup0.addItem("Item1");
            _popup0.addItem("Item2");
            _popup0.addItem("Item3");
        }

        if (_replacedObjects.objectForKey("_popup0") == null) {
            _connect(_owner(), _popup0, "comboAnnee");
        }

        _nsTextField0.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "chargerPourSemaine", _nsTextField0), ""));

        if (_replacedObjects.objectForKey("_nsTextField0") == null) {
            _connect(_owner(), _nsTextField0, "tSemaine");
        }

        if (_replacedObjects.objectForKey("_nsCustomView0") == null) {
            _connect(_owner(), _nsCustomView0, "lblAide");
        }

        if (_replacedObjects.objectForKey("_nsMatrix0") == null) {
            _connect(_owner(), _nsMatrix0, "matOccupants");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup3") == null) {
            _connect(_owner(), _eoDisplayGroup3, "eodPeriodicites");
        }

        if (_replacedObjects.objectForKey("_nsTableView2") == null) {
            _connect(_owner(), _nsTableView2, "tablePeriodicites");
        }

        _nsButton2.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "rechercherSalle", _nsButton2), ""));

        if (_replacedObjects.objectForKey("_nsButton2") == null) {
            _connect(_owner(), _nsButton2, "bRechercher");
        }

        _nsButton1.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "refuserSalle", _nsButton1), ""));

        if (_replacedObjects.objectForKey("_nsButton1") == null) {
            _connect(_owner(), _nsButton1, "bRefuser");
        }

        _nsButton0.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "validerSalle", _nsButton0), ""));

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _connect(_owner(), _nsButton0, "bValider");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup2") == null) {
            _connect(_owner(), _eoDisplayGroup2, "eodSalle");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup1") == null) {
            _connect(_owner(), _eoDisplayGroup1, "eodRessource");
        }

        if (_replacedObjects.objectForKey("_nsTableView1") == null) {
            _connect(_owner(), _nsTableView1, "tableRessource");
        }

        if (_replacedObjects.objectForKey("_nsTableView0") == null) {
            _connect(_owner(), _nsTableView0, "tablePeriodiciteSalle");
        }

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _connect(_owner(), _eoFrame0, "component");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _connect(_owner(), _eoDisplayGroup0, "eodResa");
        }
    }

    protected void _init() {
        super._init();
        _setFontForComponent(_nsTextField3, "Lucida Grande", 11, Font.PLAIN);
        _nsTextField3.setEditable(false);
        _nsTextField3.setOpaque(false);
        _nsTextField3.setText("Semaine :");
        _nsTextField3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField3.setSelectable(false);
        _nsTextField3.setEnabled(true);
        _nsTextField3.setBorder(null);
        _setFontForComponent(_nsTextField2, "Lucida Grande", 11, Font.PLAIN);
        _nsTextField2.setEditable(false);
        _nsTextField2.setOpaque(false);
        _nsTextField2.setText("Les cr\u00e9neaux de la reservation selectionn\u00e9e :");
        _nsTextField2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField2.setSelectable(false);
        _nsTextField2.setEnabled(true);
        _nsTextField2.setBorder(null);
        _setFontForComponent(_nsTextField1, "Lucida Grande", 13, Font.PLAIN);
        _nsTextField1.setEditable(false);
        _nsTextField1.setOpaque(false);
        _nsTextField1.setText("Ressources de la reservation");
        _nsTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _nsTextField1.setSelectable(false);
        _nsTextField1.setEnabled(true);
        _nsTextField1.setBorder(null);
        _eoTableColumn10.setMinWidth(35);
        _eoTableColumn10.setMaxWidth(1000);
        _eoTableColumn10.setPreferredWidth(143);
        _eoTableColumn10.setWidth(143);
        _eoTableColumn10.setResizable(true);
        _eoTableColumn10.setHeaderValue("Agent demandeur");
        _eoTableColumnAssociation10.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "agent");
        _eoTableColumnAssociation10.establishConnection();
        _setFontForComponent(_nsButton3, "Lucida Grande", 13, Font.PLAIN);
        _nsButton3.setMargin(new Insets(0, 2, 0, 2));

        if (_replacedObjects.objectForKey("_popup0") == null) {
            _setFontForComponent(_popup0, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_nsTextField0") == null) {
            _setFontForComponent(_nsTextField0, "Lucida Grande", 13, Font.PLAIN);
            _nsTextField0.setEditable(true);
            _nsTextField0.setOpaque(true);
            _nsTextField0.setText("");
            _nsTextField0.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            _nsTextField0.setSelectable(true);
            _nsTextField0.setEnabled(true);
        }

        _setFontForComponent(_jRadioButton1, "Lucida Grande", 13, Font.PLAIN);
        _setFontForComponent(_jRadioButton0, "Lucida Grande", 13, Font.PLAIN);

        if (_replacedObjects.objectForKey("_nsMatrix0") == null) {
            _jRadioButton0.setSize(124, 18);
            _jRadioButton0.setLocation(0, 0);
            _nsMatrix0.add(_jRadioButton0);
            _jRadioButton1.setSize(124, 18);
            _jRadioButton1.setLocation(128, 0);
            _nsMatrix0.add(_jRadioButton1);
            _setFontForComponent(_nsMatrix0, "Lucida Grande", 13, Font.PLAIN);
        }

        _eoTableColumn9.setMinWidth(59);
        _eoTableColumn9.setMaxWidth(1000);
        _eoTableColumn9.setPreferredWidth(87);
        _eoTableColumn9.setWidth(87);
        _eoTableColumn9.setResizable(true);
        _eoTableColumn9.setHeaderValue("Heure Fin");
        if ((_eoTableColumn9.getCellRenderer() instanceof DefaultTableCellRenderer) || (_eoTableColumn9.getCellRenderer() == null)) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        	_eoTableColumn9.setCellRenderer(renderer);
        }
        _eoTableColumnAssociation9.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup3, "heureFin");
        if (_eoTableColumnAssociation9.canSupportValueFormatter()) { _eoTableColumnAssociation9.setValueFormatter(_nsTimestampFormatter2); }
        _eoTableColumnAssociation9.establishConnection();
        _eoTableColumn8.setMinWidth(10);
        _eoTableColumn8.setMaxWidth(1000);
        _eoTableColumn8.setPreferredWidth(94);
        _eoTableColumn8.setWidth(94);
        _eoTableColumn8.setResizable(true);
        _eoTableColumn8.setHeaderValue("Heure D\u00e9but");
        if ((_eoTableColumn8.getCellRenderer() instanceof DefaultTableCellRenderer) || (_eoTableColumn8.getCellRenderer() == null)) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        	_eoTableColumn8.setCellRenderer(renderer);
        }
        _eoTableColumnAssociation8.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup3, "heureDeb");
        if (_eoTableColumnAssociation8.canSupportValueFormatter()) { _eoTableColumnAssociation8.setValueFormatter(_nsTimestampFormatter1); }
        _eoTableColumnAssociation8.establishConnection();
        _eoTableColumn7.setMinWidth(28);
        _eoTableColumn7.setMaxWidth(1000);
        _eoTableColumn7.setPreferredWidth(111);
        _eoTableColumn7.setWidth(111);
        _eoTableColumn7.setResizable(true);
        _eoTableColumn7.setHeaderValue("Date");
        if ((_eoTableColumn7.getCellRenderer() instanceof DefaultTableCellRenderer) || (_eoTableColumn7.getCellRenderer() == null)) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        	_eoTableColumn7.setCellRenderer(renderer);
        }
        _eoTableColumnAssociation7.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup3, "date");
        if (_eoTableColumnAssociation7.canSupportValueFormatter()) { _eoTableColumnAssociation7.setValueFormatter(_nsTimestampFormatter0); }
        _eoTableColumnAssociation7.establishConnection();
        _eoTableColumn6.setMinWidth(10);
        _eoTableColumn6.setMaxWidth(1000);
        _eoTableColumn6.setPreferredWidth(50);
        _eoTableColumn6.setWidth(50);
        _eoTableColumn6.setResizable(true);
        _eoTableColumn6.setHeaderValue("Semaine");
        _eoTableColumnAssociation6.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup3, "semaine");
        _eoTableColumnAssociation6.establishConnection();
        _eoTableAssociation2.bindAspect(com.webobjects.eointerface.EOAssociation.SourceAspect, _eoDisplayGroup3, "");
        _eoTableAssociation2.setSortsByColumnOrder(true);
        _eoTableAssociation2.establishConnection();

        if (_replacedObjects.objectForKey("_eoDisplayGroup3") == null) {
            _eoDisplayGroup3.setValidatesChangesImmediately(false);
            _eoDisplayGroup3.setFetchesOnLoad(false);
            _eoDisplayGroup3.setUsesOptimisticRefresh(false);
            _eoDisplayGroup3.setSelectsFirstObjectAfterFetch(true);
            _eoDisplayGroup3.setLocalKeys(new com.webobjects.foundation.NSArray(new Object[] {"jour", "semaine", "date", "heureDeb", "heureFin"}));
        }

        _eoTableColumnAssociation5.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup3, "jour");
        _eoTableColumnAssociation5.establishConnection();
        _eoTableColumn5.setMinWidth(10);
        _eoTableColumn5.setMaxWidth(1000);
        _eoTableColumn5.setPreferredWidth(62);
        _eoTableColumn5.setWidth(62);
        _eoTableColumn5.setResizable(true);
        _eoTableColumn5.setHeaderValue("Jour");

        if (_replacedObjects.objectForKey("_nsTableView2") == null) {
            _nsTableView2.table().addColumn(_eoTableColumn5);
            _nsTableView2.table().addColumn(_eoTableColumn6);
            _nsTableView2.table().addColumn(_eoTableColumn7);
            _nsTableView2.table().addColumn(_eoTableColumn8);
            _nsTableView2.table().addColumn(_eoTableColumn9);
            _setFontForComponent(_nsTableView2.table().getTableHeader(), "Lucida Grande", 11, Font.PLAIN);
            _nsTableView2.table().setRowHeight(19);
        }

        if (_replacedObjects.objectForKey("_nsButton2") == null) {
            _setFontForComponent(_nsButton2, "Lucida Grande", 13, Font.PLAIN);
            _nsButton2.setMargin(new Insets(0, 2, 0, 2));
        }

        if (_replacedObjects.objectForKey("_nsButton1") == null) {
            _setFontForComponent(_nsButton1, "Lucida Grande", 13, Font.PLAIN);
            _nsButton1.setMargin(new Insets(0, 2, 0, 2));
        }

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _setFontForComponent(_nsButton0, "Lucida Grande", 13, Font.PLAIN);
            _nsButton0.setMargin(new Insets(0, 2, 0, 2));
        }

        _connect(_eoDataSource3, _eoEditingContext0, "editingContext");

        if (_replacedObjects.objectForKey("_eoDataSource0") == null) {
            _connect(_eoDataSource0, _eoEditingContext0, "editingContext");
        }

        _connect(_eoDataSource2, _eoEditingContext0, "editingContext");
        _connect(_eoDataSource1, _eoEditingContext0, "editingContext");

        if (_replacedObjects.objectForKey("_eoDisplayGroup2") == null) {
            _connect(_eoDisplayGroup2, _eoDataSource0, "dataSource");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup2") == null) {
            _eoDisplayGroup2.setValidatesChangesImmediately(false);
            _eoDisplayGroup2.setFetchesOnLoad(false);
            _eoDisplayGroup2.setUsesOptimisticRefresh(false);
            _eoDisplayGroup2.setSelectsFirstObjectAfterFetch(true);
        }

        _eoTableColumn4.setMinWidth(35);
        _eoTableColumn4.setMaxWidth(1000);
        _eoTableColumn4.setPreferredWidth(149);
        _eoTableColumn4.setWidth(149);
        _eoTableColumn4.setResizable(true);
        _eoTableColumn4.setHeaderValue("Code");
        _eoTableColumnAssociation4.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup1, "resUnite");
        _eoTableColumnAssociation4.establishConnection();
        _eoTableColumn3.setMinWidth(10);
        _eoTableColumn3.setMaxWidth(1000);
        _eoTableColumn3.setPreferredWidth(508);
        _eoTableColumn3.setWidth(508);
        _eoTableColumn3.setResizable(true);
        _eoTableColumn3.setHeaderValue("Libelle");
        _eoTableColumnAssociation3.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup1, "resLibelle");
        _eoTableColumnAssociation3.establishConnection();
        _eoTableAssociation1.bindAspect(com.webobjects.eointerface.EOAssociation.SourceAspect, _eoDisplayGroup1, "");
        _eoTableAssociation1.setSortsByColumnOrder(true);
        _eoTableAssociation1.establishConnection();

        if (_replacedObjects.objectForKey("_eoDisplayGroup1") == null) {
            _eoDisplayGroup1.setValidatesChangesImmediately(false);
            _eoDisplayGroup1.setFetchesOnLoad(false);
            _eoDisplayGroup1.setUsesOptimisticRefresh(false);
            _eoDisplayGroup1.setSelectsFirstObjectAfterFetch(true);
            _eoDisplayGroup1.setLocalKeys(new com.webobjects.foundation.NSArray(new Object[] {"resType", "resLibelle", "resUnite"}));
        }

        _eoTableColumnAssociation2.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup1, "resType");
        _eoTableColumnAssociation2.establishConnection();
        _eoTableColumn2.setMinWidth(10);
        _eoTableColumn2.setMaxWidth(1000);
        _eoTableColumn2.setPreferredWidth(101);
        _eoTableColumn2.setWidth(101);
        _eoTableColumn2.setResizable(true);
        _eoTableColumn2.setHeaderValue("Type");

        if (_replacedObjects.objectForKey("_nsTableView1") == null) {
            _nsTableView1.table().addColumn(_eoTableColumn2);
            _nsTableView1.table().addColumn(_eoTableColumn3);
            _nsTableView1.table().addColumn(_eoTableColumn4);
            _setFontForComponent(_nsTableView1.table().getTableHeader(), "Lucida Grande", 11, Font.PLAIN);
            _nsTableView1.table().setRowHeight(19);
        }

        _eoTableColumn1.setMinWidth(79);
        _eoTableColumn1.setMaxWidth(1000);
        _eoTableColumn1.setPreferredWidth(262);
        _eoTableColumn1.setWidth(262);
        _eoTableColumn1.setResizable(true);
        _eoTableColumn1.setHeaderValue("Commentaire");
        _eoTableColumnAssociation1.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "commentaire");
        _eoTableColumnAssociation1.establishConnection();
        _eoTableAssociation0.bindAspect(com.webobjects.eointerface.EOAssociation.SourceAspect, _eoDisplayGroup0, "");
        _eoTableAssociation0.setSortsByColumnOrder(true);
        _eoTableAssociation0.establishConnection();

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _eoDisplayGroup0.setValidatesChangesImmediately(false);
            _eoDisplayGroup0.setFetchesOnLoad(false);
            _eoDisplayGroup0.setUsesOptimisticRefresh(false);
            _eoDisplayGroup0.setSelectsFirstObjectAfterFetch(true);
            _eoDisplayGroup0.setLocalKeys(new com.webobjects.foundation.NSArray(new Object[] {"type", "commentaire", "agent"}));
        }

        _eoTableColumnAssociation0.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "type");
        _eoTableColumnAssociation0.establishConnection();
        _eoTableColumn0.setMinWidth(57);
        _eoTableColumn0.setMaxWidth(1000);
        _eoTableColumn0.setPreferredWidth(100);
        _eoTableColumn0.setWidth(100);
        _eoTableColumn0.setResizable(true);
        _eoTableColumn0.setHeaderValue("Type");

        if (_replacedObjects.objectForKey("_nsTableView0") == null) {
            _nsTableView0.table().addColumn(_eoTableColumn0);
            _nsTableView0.table().addColumn(_eoTableColumn10);
            _nsTableView0.table().addColumn(_eoTableColumn1);
            _setFontForComponent(_nsTableView0.table().getTableHeader(), "Lucida Grande", 11, Font.PLAIN);
            _nsTableView0.table().setRowHeight(19);
        }

        if (!(_nsBox1.getLayout() instanceof EOViewLayout)) { _nsBox1.setLayout(new EOViewLayout()); }
        _nsTableView0.setSize(387, 208);
        _nsTableView0.setLocation(14, 65);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTableView0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTableView0);
        _nsMatrix0.setSize(253, 18);
        _nsMatrix0.setLocation(606, 31);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsMatrix0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsMatrix0);
        _nsTableView1.setSize(784, 222);
        _nsTableView1.setLocation(14, 306);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTableView1, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTableView1);
        _nsButton0.setSize(38, 36);
        _nsButton0.setLocation(808, 307);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton0);
        _nsButton1.setSize(38, 36);
        _nsButton1.setLocation(808, 351);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton1, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton1);
        _nsButton2.setSize(38, 36);
        _nsButton2.setLocation(808, 395);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton2, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton2);
        _nsTextField1.setSize(199, 17);
        _nsTextField1.setLocation(12, 281);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField1, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField1);
        _nsTableView2.setSize(436, 208);
        _nsTableView2.setLocation(409, 62);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTableView2, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTableView2);
        _nsCustomView0.setSize(371, 47);
        _nsCustomView0.setLocation(14, 7);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsCustomView0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsCustomView0);
        _nsTextField2.setSize(261, 14);
        _nsTextField2.setLocation(407, 45);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField2, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField2);
        _nsTextField0.setSize(78, 22);
        _nsTextField0.setLocation(409, 14);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField0);
        _nsButton3.setSize(84, 26);
        _nsButton3.setLocation(493, 12);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton3, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton3);
        _popup0.setSize(140, 26);
        _popup0.setLocation(706, 5);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_popup0, EOViewLayout.MinYMargin);
        _nsBox1.add(_popup0);
        _nsTextField3.setSize(83, 14);
        _nsTextField3.setLocation(407, 0);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField3, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField3);
        if (!(_nsBox0.getLayout() instanceof EOViewLayout)) { _nsBox0.setLayout(new EOViewLayout()); }
        _nsBox1.setSize(871, 542);
        _nsBox1.setLocation(2, 2);
        ((EOViewLayout)_nsBox0.getLayout()).setAutosizingMask(_nsBox1, EOViewLayout.MinYMargin);
        _nsBox0.add(_nsBox1);
        _nsBox0.setBorder(new com.webobjects.eointerface.swing._EODefaultBorder("", true, "Lucida Grande", 13, Font.PLAIN));
        if (!(_nsView0.getLayout() instanceof EOViewLayout)) { _nsView0.setLayout(new EOViewLayout()); }
        _nsBox0.setSize(875, 546);
        _nsBox0.setLocation(13, 14);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsBox0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsBox0);

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _nsView0.setSize(901, 574);
            _eoFrame0.setTitle("Window");
            _eoFrame0.setLocation(287, 371);
            _eoFrame0.setSize(901, 574);
        }
    }
}
