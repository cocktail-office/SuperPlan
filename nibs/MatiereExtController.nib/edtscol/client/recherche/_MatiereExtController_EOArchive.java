// _MatiereExtController_EOArchive.java
// Generated by EnterpriseObjects palette at vendredi 28 mars 2008 10 h 54 Europe/Paris

package edtscol.client.recherche;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eointerface.swing.EOViewLayout;
import com.webobjects.foundation.NSDisposableRegistry;

public class _MatiereExtController_EOArchive extends com.webobjects.eoapplication.EOArchive {
    com.webobjects.eocontrol.EODataSource _eoDataSource0;
    com.webobjects.eocontrol.EOEditingContext _eoEditingContext0;
    com.webobjects.eointerface.EODisplayGroup _eoDisplayGroup0;
    com.webobjects.eointerface.EOTableAssociation _eoTableAssociation0;
    com.webobjects.eointerface.EOTableColumnAssociation _eoTableColumnAssociation0, _eoTableColumnAssociation1, _eoTableColumnAssociation2, _eoTableColumnAssociation3;
    com.webobjects.eointerface.swing.EOFrame _eoFrame0;
    com.webobjects.eointerface.swing.EOMatrix _nsMatrix0, _nsMatrix1;
    com.webobjects.eointerface.swing.EOTable _nsTableView0;
    com.webobjects.eointerface.swing.EOTable._EOTableColumn _eoTableColumn0, _eoTableColumn1, _eoTableColumn2, _eoTableColumn3;
    com.webobjects.eointerface.swing.EOTextField _nsTextField0, _nsTextField1, _nsTextField2, _nsTextField3, _nsTextField4;
    com.webobjects.eointerface.swing.EOView _nsBox0, _nsBox1, _nsBox2, _nsBox3;
    com.webobjects.foundation.NSNumberFormatter _nsNumberFormatter0;
    javax.swing.JButton _nsButton0;
    javax.swing.JCheckBox _nsButton1;
    javax.swing.JComboBox _popup0, _popup1, _popup2, _popup3, _popup4, _popup5;
    javax.swing.JPanel _nsView0;
    javax.swing.JRadioButton _jRadioButton0, _jRadioButton1, _jRadioButton2, _jRadioButton3, _jRadioButton4, _jRadioButton5;

    public _MatiereExtController_EOArchive(Object owner, NSDisposableRegistry registry) {
        super(owner, registry);
    }

    protected void _construct() {
        Object owner = _owner();
        EOArchive._ObjectInstantiationDelegate delegate = (owner instanceof EOArchive._ObjectInstantiationDelegate) ? (EOArchive._ObjectInstantiationDelegate)owner : null;
        Object replacement;

        super._construct();

        _nsTextField4 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField1111");
        _nsTextField3 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField111");
        _nsBox3 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "NSView");
        _nsBox2 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "NSBox");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bChoixGroupesAp")) != null)) {
            _nsButton0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton0");
        } else {
            _nsButton0 = (javax.swing.JButton)_registered(new javax.swing.JButton("Choisir groupes"), "NSButton");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "checkSemPrec")) != null)) {
            _nsButton1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JCheckBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton1");
        } else {
            _nsButton1 = (javax.swing.JCheckBox)_registered(new javax.swing.JCheckBox(" Année complète"), "NSButton4");
        }

        _jRadioButton5 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Tous"), "NSButtonCell1");
        _jRadioButton4 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Pref."), "NSButtonCell");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "matPrefDip")) != null)) {
            _nsMatrix1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOMatrix)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsMatrix1");
        } else {
            _nsMatrix1 = (com.webobjects.eointerface.swing.EOMatrix)_registered(new com.webobjects.eointerface.swing.EOMatrix(2, 1, 4, 2), "NSMatrix11");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "lblGrpAp")) != null)) {
            _nsTextField2 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField2");
        } else {
            _nsTextField2 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField11");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "comboGroupesSem")) != null)) {
            _popup5 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JComboBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_popup5");
        } else {
            _popup5 = (javax.swing.JComboBox)_registered(new javax.swing.JComboBox(), "NSPopUpButton21");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "comboSemestres")) != null)) {
            _popup4 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JComboBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_popup4");
        } else {
            _popup4 = (javax.swing.JComboBox)_registered(new javax.swing.JComboBox(), "NSPopUpButton2");
        }

        _jRadioButton3 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Tout"), "");
        _jRadioButton2 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("TP"), "");
        _jRadioButton1 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("TD"), "");
        _jRadioButton0 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("CM"), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "matTypeAp")) != null)) {
            _nsMatrix0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOMatrix)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsMatrix0");
        } else {
            _nsMatrix0 = (com.webobjects.eointerface.swing.EOMatrix)_registered(new com.webobjects.eointerface.swing.EOMatrix(1, 4, 4, 2), "NSMatrix1");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tGrade")) != null)) {
            _nsTextField1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField1");
        } else {
            _nsTextField1 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField2");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "comboGroupes")) != null)) {
            _popup3 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JComboBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_popup3");
        } else {
            _popup3 = (javax.swing.JComboBox)_registered(new javax.swing.JComboBox(), "NSPopUpButton31");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "comboAps")) != null)) {
            _popup2 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JComboBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_popup2");
        } else {
            _popup2 = (javax.swing.JComboBox)_registered(new javax.swing.JComboBox(), "NSPopUpButton3");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "comboParcours")) != null)) {
            _popup1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JComboBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_popup1");
        } else {
            _popup1 = (javax.swing.JComboBox)_registered(new javax.swing.JComboBox(), "NSPopUpButton1");
        }

        _eoTableColumn3 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tableHabilitation")) != null)) {
            _nsTableView0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTable)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTableView0");
        } else {
            _nsTableView0 = (com.webobjects.eointerface.swing.EOTable)_registered(new com.webobjects.eointerface.swing.EOTable(), "");
        }

        _eoTableColumnAssociation3 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn3, _nsTableView0), "");
        _eoTableColumn2 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation2 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn2, _nsTableView0), "");
        _nsNumberFormatter0 = (com.webobjects.foundation.NSNumberFormatter)_registered(new com.webobjects.foundation.NSNumberFormatter("0;-0"), "");
        _eoTableColumn1 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation1 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn1, _nsTableView0), "");
        _eoTableAssociation0 = (com.webobjects.eointerface.EOTableAssociation)_registered(new com.webobjects.eointerface.EOTableAssociation(_nsTableView0), "");
        _eoEditingContext0 = ((com.webobjects.eocontrol.EOEditingContext.substitutionEditingContext() != null) ? com.webobjects.eocontrol.EOEditingContext.substitutionEditingContext() : (com.webobjects.eocontrol.EOEditingContext)_registered(new com.webobjects.eocontrol.EOEditingContext(), "EditingContext"));

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodHabilitation.dataSource")) != null)) {
            _eoDataSource0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eocontrol.EODataSource)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDataSource0");
        } else {
            _eoDataSource0 = (com.webobjects.eocontrol.EODataSource)_registered(com.webobjects.eoapplication.EODataSourceFactory.defaultDataSourceFactory().newMasterDataSource(_eoEditingContext0, "FormationHabilitation", null), "DataSource");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodHabilitation")) != null)) {
            _eoDisplayGroup0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.EODisplayGroup)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDisplayGroup0");
        } else {
            _eoDisplayGroup0 = (com.webobjects.eointerface.EODisplayGroup)_registered(new com.webobjects.eointerface.EODisplayGroup(), "FormationHabilitation");
        }

        _eoTableColumn0 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");
        _eoTableColumnAssociation0 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn0, _nsTableView0), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "comboAnnees")) != null)) {
            _popup0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JComboBox)replacement;
            _replacedObjects.setObjectForKey(replacement, "_popup0");
        } else {
            _popup0 = (javax.swing.JComboBox)_registered(new javax.swing.JComboBox(), "NSPopUpButton");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tDiplome")) != null)) {
            _nsTextField0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField0");
        } else {
            _nsTextField0 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField");
        }

        _nsBox1 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "NSView");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "viewMatiereExt")) != null)) {
            _nsBox0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOView)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsBox0");
        } else {
            _nsBox0 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "NSBox");
        }

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
        _nsButton0.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "selectionnerGroupes", _nsButton0), ""));

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _connect(_owner(), _nsButton0, "bChoixGroupesAp");
        }

        if (_replacedObjects.objectForKey("_nsButton1") == null) {
            _connect(_owner(), _nsButton1, "checkSemPrec");
        }

        if (_replacedObjects.objectForKey("_nsMatrix1") == null) {
            _connect(_owner(), _nsMatrix1, "matPrefDip");
        }

        if (_replacedObjects.objectForKey("_nsTextField2") == null) {
            _connect(_owner(), _nsTextField2, "lblGrpAp");
        }

        if (_replacedObjects.objectForKey("_nsBox0") == null) {
            _connect(_owner(), _nsBox0, "viewMatiereExt");
        }

        if (_replacedObjects.objectForKey("_popup5") == null) {
            _popup5.setModel(new javax.swing.DefaultComboBoxModel());
            _popup5.addItem("Item1");
            _popup5.addItem("Item2");
            _popup5.addItem("Item3");
        }

        if (_replacedObjects.objectForKey("_popup5") == null) {
            _connect(_owner(), _popup5, "comboGroupesSem");
        }

        if (_replacedObjects.objectForKey("_popup4") == null) {
            _popup4.setModel(new javax.swing.DefaultComboBoxModel());
            _popup4.addItem("Item1");
            _popup4.addItem("Item2");
            _popup4.addItem("Item3");
        }

        if (_replacedObjects.objectForKey("_popup4") == null) {
            _connect(_owner(), _popup4, "comboSemestres");
        }

        if (_replacedObjects.objectForKey("_nsMatrix0") == null) {
            _connect(_owner(), _nsMatrix0, "matTypeAp");
        }

        _nsTextField1.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "chercherDiplome", _nsTextField1), ""));

        if (_replacedObjects.objectForKey("_nsTextField1") == null) {
            _connect(_owner(), _nsTextField1, "tGrade");
        }

        if (_replacedObjects.objectForKey("_popup3") == null) {
            _popup3.setModel(new javax.swing.DefaultComboBoxModel());
            _popup3.addItem("Item1");
            _popup3.addItem("Item2");
            _popup3.addItem("Item3");
        }

        if (_replacedObjects.objectForKey("_popup3") == null) {
            _connect(_owner(), _popup3, "comboGroupes");
        }

        if (_replacedObjects.objectForKey("_popup2") == null) {
            _popup2.setModel(new javax.swing.DefaultComboBoxModel());
            _popup2.addItem("Item1");
            _popup2.addItem("Item2");
            _popup2.addItem("Item3");
        }

        if (_replacedObjects.objectForKey("_popup2") == null) {
            _connect(_owner(), _popup2, "comboAps");
        }

        if (_replacedObjects.objectForKey("_popup1") == null) {
            _popup1.setModel(new javax.swing.DefaultComboBoxModel());
            _popup1.addItem("Item1");
            _popup1.addItem("Item2");
            _popup1.addItem("Item3");
        }

        if (_replacedObjects.objectForKey("_popup1") == null) {
            _connect(_owner(), _popup1, "comboParcours");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _connect(_owner(), _eoDisplayGroup0, "eodHabilitation");
        }

        if (_replacedObjects.objectForKey("_nsTableView0") == null) {
            _connect(_owner(), _nsTableView0, "tableHabilitation");
        }

        if (_replacedObjects.objectForKey("_popup0") == null) {
            _popup0.setModel(new javax.swing.DefaultComboBoxModel());
            _popup0.addItem("Item1");
            _popup0.addItem("Item2");
            _popup0.addItem("Item3");
        }

        if (_replacedObjects.objectForKey("_popup0") == null) {
            _connect(_owner(), _popup0, "comboAnnees");
        }

        if (_replacedObjects.objectForKey("_nsTextField0") == null) {
            _connect(_owner(), _nsTextField0, "tDiplome");
        }

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _connect(_owner(), _eoFrame0, "component");
        }

        _nsTextField0.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "chercherDiplome", _nsTextField0), ""));
    }

    protected void _init() {
        super._init();
        _setFontForComponent(_nsTextField4, "Lucida Grande", 10, Font.PLAIN);
        _nsTextField4.setEditable(false);
        _nsTextField4.setOpaque(false);
        _nsTextField4.setText("Grade");
        _nsTextField4.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField4.setSelectable(false);
        _nsTextField4.setEnabled(true);
        _nsTextField4.setBorder(null);
        _setFontForComponent(_nsTextField3, "Lucida Grande", 10, Font.PLAIN);
        _nsTextField3.setEditable(false);
        _nsTextField3.setOpaque(false);
        _nsTextField3.setText("Abrev. diplome");
        _nsTextField3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField3.setSelectable(false);
        _nsTextField3.setEnabled(true);
        _nsTextField3.setBorder(null);
        if (!(_nsBox3.getLayout() instanceof EOViewLayout)) { _nsBox3.setLayout(new EOViewLayout()); }
        _popup4.setSize(118, 22);
        _popup4.setLocation(10, 0);
        ((EOViewLayout)_nsBox3.getLayout()).setAutosizingMask(_popup4, EOViewLayout.MinYMargin);
        _nsBox3.add(_popup4);
        _popup5.setSize(118, 26);
        _popup5.setLocation(10, 35);
        ((EOViewLayout)_nsBox3.getLayout()).setAutosizingMask(_popup5, EOViewLayout.MinYMargin);
        _nsBox3.add(_popup5);
        _nsButton1.setSize(126, 17);
        _nsButton1.setLocation(6, 20);
        ((EOViewLayout)_nsBox3.getLayout()).setAutosizingMask(_nsButton1, EOViewLayout.MinYMargin);
        _nsBox3.add(_nsButton1);
        if (!(_nsBox2.getLayout() instanceof EOViewLayout)) { _nsBox2.setLayout(new EOViewLayout()); }
        _nsBox3.setSize(132, 64);
        _nsBox3.setLocation(2, 2);
        ((EOViewLayout)_nsBox2.getLayout()).setAutosizingMask(_nsBox3, EOViewLayout.MinYMargin);
        _nsBox2.add(_nsBox3);
        _nsBox2.setBorder(new com.webobjects.eointerface.swing._EODefaultBorder("", true, "Lucida Grande", 13, Font.PLAIN));

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _setFontForComponent(_nsButton0, "Lucida Grande", 13, Font.PLAIN);
            _nsButton0.setMargin(new Insets(0, 2, 0, 2));
        }

        if (_replacedObjects.objectForKey("_nsButton1") == null) {
            _setFontForComponent(_nsButton1, "Lucida Grande", 12, Font.PLAIN);
        }

        _setFontForComponent(_jRadioButton5, "Lucida Grande", 13, Font.PLAIN);
        _setFontForComponent(_jRadioButton4, "Lucida Grande", 13, Font.PLAIN);

        if (_replacedObjects.objectForKey("_nsMatrix1") == null) {
            _jRadioButton4.setSize(58, 18);
            _jRadioButton4.setLocation(0, 0);
            _nsMatrix1.add(_jRadioButton4);
            _jRadioButton5.setSize(58, 18);
            _jRadioButton5.setLocation(0, 20);
            _nsMatrix1.add(_jRadioButton5);
            _setFontForComponent(_nsMatrix1, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_nsTextField2") == null) {
            _setFontForComponent(_nsTextField2, "Lucida Grande", 11, Font.PLAIN);
            _nsTextField2.setEditable(false);
            _nsTextField2.setOpaque(false);
            _nsTextField2.setText("Groupes de l'AP ");
            _nsTextField2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            _nsTextField2.setSelectable(false);
            _nsTextField2.setEnabled(true);
            _nsTextField2.setBorder(null);
        }

        if (_replacedObjects.objectForKey("_popup5") == null) {
            _setFontForComponent(_popup5, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_popup4") == null) {
            _setFontForComponent(_popup4, "Lucida Grande", 9, Font.PLAIN);
        }

        _setFontForComponent(_jRadioButton3, "Lucida Grande", 13, Font.PLAIN);
        _setFontForComponent(_jRadioButton2, "Lucida Grande", 13, Font.PLAIN);
        _setFontForComponent(_jRadioButton1, "Lucida Grande", 13, Font.PLAIN);
        _setFontForComponent(_jRadioButton0, "Lucida Grande", 13, Font.PLAIN);

        if (_replacedObjects.objectForKey("_nsMatrix0") == null) {
            _jRadioButton0.setSize(52, 18);
            _jRadioButton0.setLocation(0, 0);
            _nsMatrix0.add(_jRadioButton0);
            _jRadioButton1.setSize(52, 18);
            _jRadioButton1.setLocation(56, 0);
            _nsMatrix0.add(_jRadioButton1);
            _jRadioButton2.setSize(52, 18);
            _jRadioButton2.setLocation(112, 0);
            _nsMatrix0.add(_jRadioButton2);
            _jRadioButton3.setSize(52, 18);
            _jRadioButton3.setLocation(168, 0);
            _nsMatrix0.add(_jRadioButton3);
            _setFontForComponent(_nsMatrix0, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_nsTextField1") == null) {
            _setFontForComponent(_nsTextField1, "Lucida Grande", 13, Font.PLAIN);
            _nsTextField1.setEditable(true);
            _nsTextField1.setOpaque(true);
            _nsTextField1.setText("");
            _nsTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            _nsTextField1.setSelectable(true);
            _nsTextField1.setEnabled(true);
        }

        if (_replacedObjects.objectForKey("_popup3") == null) {
            _setFontForComponent(_popup3, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_popup2") == null) {
            _setFontForComponent(_popup2, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_popup1") == null) {
            _setFontForComponent(_popup1, "Lucida Grande", 13, Font.PLAIN);
        }

        _eoTableColumn3.setMinWidth(10);
        _eoTableColumn3.setMaxWidth(1000);
        _eoTableColumn3.setPreferredWidth(237);
        _eoTableColumn3.setWidth(237);
        _eoTableColumn3.setResizable(true);
        _eoTableColumn3.setHeaderValue("Diplome");
       // _eoTableColumnAssociation3.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "formationSpecialisation.scolFormationDiplome.fdipAbreviation");
        _eoTableColumnAssociation3.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "libelleColonne1");
        _eoTableColumnAssociation3.establishConnection();
        _eoTableColumn2.setMinWidth(92);
        _eoTableColumn2.setMaxWidth(1000);
        _eoTableColumn2.setPreferredWidth(92);
        _eoTableColumn2.setWidth(92);
        _eoTableColumn2.setResizable(true);
        _eoTableColumn2.setHeaderValue("Sp\u00e9cialisation");
        //_eoTableColumnAssociation2.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "formationSpecialisation.fspnLibelle");
        _eoTableColumnAssociation2.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "libelleColonne2");
        _eoTableColumnAssociation2.establishConnection();
        _nsNumberFormatter0.setLocalizesPattern(true);
        _eoTableColumn1.setMinWidth(40);
        _eoTableColumn1.setMaxWidth(1000);
        _eoTableColumn1.setPreferredWidth(44);
        _eoTableColumn1.setWidth(44);
        _eoTableColumn1.setResizable(true);
        _eoTableColumn1.setHeaderValue("Niveau");
        if ((_eoTableColumn1.getCellRenderer() instanceof DefaultTableCellRenderer) || (_eoTableColumn1.getCellRenderer() == null)) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        	_eoTableColumn1.setCellRenderer(renderer);
        }
        _eoTableColumnAssociation1.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "fhabNiveau");
        if (_eoTableColumnAssociation1.canSupportValueFormatter()) { _eoTableColumnAssociation1.setValueFormatter(_nsNumberFormatter0); }
        _eoTableColumnAssociation1.establishConnection();
        _eoTableAssociation0.bindAspect(com.webobjects.eointerface.EOAssociation.SourceAspect, _eoDisplayGroup0, "");
        _eoTableAssociation0.setSortsByColumnOrder(true);
        _eoTableAssociation0.establishConnection();

        if (_replacedObjects.objectForKey("_eoDataSource0") == null) {
            _connect(_eoDataSource0, _eoEditingContext0, "editingContext");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _connect(_eoDisplayGroup0, _eoDataSource0, "dataSource");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _eoDisplayGroup0.setValidatesChangesImmediately(false);
            _eoDisplayGroup0.setFetchesOnLoad(false);
            _eoDisplayGroup0.setUsesOptimisticRefresh(false);
            _eoDisplayGroup0.setSelectsFirstObjectAfterFetch(true);
        }

        _eoTableColumnAssociation0.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "formationSpecialisation.scolFormationDiplome.fgraCode");
        _eoTableColumnAssociation0.establishConnection();
        _eoTableColumn0.setMinWidth(10);
        _eoTableColumn0.setMaxWidth(1000);
        _eoTableColumn0.setPreferredWidth(38);
        _eoTableColumn0.setWidth(38);
        _eoTableColumn0.setResizable(true);
        _eoTableColumn0.setHeaderValue("Grade");
        if ((_eoTableColumn0.getHeaderRenderer() != null)) {
        	((DefaultTableCellRenderer)(_eoTableColumn0.getHeaderRenderer())).setHorizontalAlignment(javax.swing.JTextField.LEFT);
        }

        if (_replacedObjects.objectForKey("_nsTableView0") == null) {
            _nsTableView0.table().addColumn(_eoTableColumn0);
            _nsTableView0.table().addColumn(_eoTableColumn1);
            _nsTableView0.table().addColumn(_eoTableColumn3);
            _nsTableView0.table().addColumn(_eoTableColumn2);
            _setFontForComponent(_nsTableView0.table().getTableHeader(), "Monaco", 10, Font.PLAIN);
            _nsTableView0.table().setRowHeight(16);
        }

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

        if (!(_nsBox1.getLayout() instanceof EOViewLayout)) { _nsBox1.setLayout(new EOViewLayout()); }
        _nsTextField0.setSize(160, 22);
        _nsTextField0.setLocation(57, 12);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField0);
        _nsTableView0.setSize(390, 129);
        _nsTableView0.setLocation(6, 39);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTableView0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTableView0);
        _popup0.setSize(115, 26);
        _popup0.setLocation(282, 11);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_popup0, EOViewLayout.MinYMargin);
        _nsBox1.add(_popup0);
        _popup1.setSize(389, 26);
        _popup1.setLocation(8, 171);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_popup1, EOViewLayout.MinYMargin);
        _nsBox1.add(_popup1);
        _popup2.setSize(247, 26);
        _popup2.setLocation(150, 219);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_popup2, EOViewLayout.MinYMargin);
        _nsBox1.add(_popup2);
        _popup3.setSize(144, 26);
        _popup3.setLocation(253, 246);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_popup3, EOViewLayout.MinYMargin);
        _nsBox1.add(_popup3);
        _nsTextField2.setSize(95, 14);
        _nsTextField2.setLocation(146, 252);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField2, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField2);
        _nsTextField1.setSize(42, 22);
        _nsTextField1.setLocation(7, 12);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField1, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField1);
        _nsMatrix0.setSize(220, 18);
        _nsMatrix0.setLocation(165, 200);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsMatrix0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsMatrix0);
        _nsBox2.setSize(136, 68);
        _nsBox2.setLocation(6, 202);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsBox2, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsBox2);
        _nsTextField3.setSize(81, 14);
        _nsTextField3.setLocation(59, 1);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField3, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField3);
        _nsTextField4.setSize(81, 14);
        _nsTextField4.setLocation(6, 1);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTextField4, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTextField4);
        _nsMatrix1.setSize(58, 38);
        _nsMatrix1.setLocation(221, 2);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsMatrix1, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsMatrix1);
        _nsButton0.setSize(155, 26);
        _nsButton0.setLocation(235, 246);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton0);

        if (_replacedObjects.objectForKey("_nsBox0") == null) {
            if (!(_nsBox0.getLayout() instanceof EOViewLayout)) { _nsBox0.setLayout(new EOViewLayout()); }
            _nsBox1.setSize(402, 275);
            _nsBox1.setLocation(2, 2);
            ((EOViewLayout)_nsBox0.getLayout()).setAutosizingMask(_nsBox1, EOViewLayout.MinYMargin);
            _nsBox0.add(_nsBox1);
            _nsBox0.setBorder(new com.webobjects.eointerface.swing._EODefaultBorder("", true, "Lucida Grande", 13, Font.PLAIN));
        }

        if (!(_nsView0.getLayout() instanceof EOViewLayout)) { _nsView0.setLayout(new EOViewLayout()); }
        _nsBox0.setSize(406, 279);
        _nsBox0.setLocation(10, 12);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsBox0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsBox0);

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _nsView0.setSize(426, 301);
            _eoFrame0.setTitle("Window");
            _eoFrame0.setLocation(486, 612);
            _eoFrame0.setSize(426, 301);
        }
    }
}
