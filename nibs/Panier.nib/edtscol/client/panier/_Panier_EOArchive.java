// _Panier_EOArchive.java
// Generated by EnterpriseObjects palette at lundi 25 juin 2007 15 h 01 Europe/Paris

package edtscol.client.panier;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JPanel;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eointerface.swing.EOViewLayout;
import com.webobjects.foundation.NSDisposableRegistry;

public class _Panier_EOArchive extends com.webobjects.eoapplication.EOArchive {
    com.webobjects.eointerface.EODisplayGroup _eoDisplayGroup0;
    com.webobjects.eointerface.EOTableAssociation _eoTableAssociation0;
    com.webobjects.eointerface.EOTableColumnAssociation _eoTableColumnAssociation0, _eoTableColumnAssociation1, _eoTableColumnAssociation2;
    com.webobjects.eointerface.swing.EOFrame _eoFrame0;
    com.webobjects.eointerface.swing.EOTable _nsTableView0;
    com.webobjects.eointerface.swing.EOTable._EOTableColumn _eoTableColumn0, _eoTableColumn1, _eoTableColumn2;
    com.webobjects.eointerface.swing.EOView _nsBox0, _nsBox1;
    javax.swing.JButton _nsButton0, _nsButton1;
    javax.swing.JPanel _nsView0;
    javax.swing.JTabbedPane _nsCustomView0;

    public _Panier_EOArchive(Object owner, NSDisposableRegistry registry) {
        super(owner, registry);
    }

    protected void _construct() {
        Object owner = _owner();
        EOArchive._ObjectInstantiationDelegate delegate = (owner instanceof EOArchive._ObjectInstantiationDelegate) ? (EOArchive._ObjectInstantiationDelegate)owner : null;
        Object replacement;

        super._construct();

        _eoTableColumn2 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tvRessources")) != null)) {
            _nsTableView0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTable)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTableView0");
        } else {
            _nsTableView0 = (com.webobjects.eointerface.swing.EOTable)_registered(new com.webobjects.eointerface.swing.EOTable(), "NSTableView");
        }

        _eoTableColumnAssociation2 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn2, _nsTableView0), "");
        _eoTableColumn1 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "NSTableColumn1");
        _eoTableColumnAssociation1 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn1, _nsTableView0), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bSupRessource")) != null)) {
            _nsButton1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton1");
        } else {
            _nsButton1 = (javax.swing.JButton)_registered(new javax.swing.JButton("Button"), "NSButton1");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "tabsRessources")) != null)) {
            _nsCustomView0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JTabbedPane)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsCustomView0");
        } else {
            _nsCustomView0 = (javax.swing.JTabbedPane)_registered(new javax.swing.JTabbedPane(), "View");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "bAjoutRessource")) != null)) {
            _nsButton0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton0");
        } else {
            _nsButton0 = (javax.swing.JButton)_registered(new javax.swing.JButton("Button"), "NSButton");
        }

        _eoTableAssociation0 = (com.webobjects.eointerface.EOTableAssociation)_registered(new com.webobjects.eointerface.EOTableAssociation(_nsTableView0), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "eodRessources")) != null)) {
            _eoDisplayGroup0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.EODisplayGroup)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoDisplayGroup0");
        } else {
            _eoDisplayGroup0 = (com.webobjects.eointerface.EODisplayGroup)_registered(new com.webobjects.eointerface.EODisplayGroup(), "Ressource");
        }

        _eoTableColumn0 = (com.webobjects.eointerface.swing.EOTable._EOTableColumn)_registered(new com.webobjects.eointerface.swing.EOTable._EOTableColumn(), "NSTableColumn");
        _eoTableColumnAssociation0 = (com.webobjects.eointerface.EOTableColumnAssociation)_registered(new com.webobjects.eointerface.EOTableColumnAssociation(_eoTableColumn0, _nsTableView0), "");
        _nsBox1 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "NSView");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "currentView")) != null)) {
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
        _nsButton1.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "supprimerRessource", _nsButton1), ""));

        if (_replacedObjects.objectForKey("_nsButton1") == null) {
            _connect(_owner(), _nsButton1, "bSupRessource");
        }

        if (_replacedObjects.objectForKey("_nsCustomView0") == null) {
            _connect(_owner(), _nsCustomView0, "tabsRessources");
        }

        if (_replacedObjects.objectForKey("_nsBox0") == null) {
            _connect(_owner(), _nsBox0, "currentView");
        }

        if (_replacedObjects.objectForKey("_nsTableView0") == null) {
            _connect(_owner(), _nsTableView0, "tvRessources");
        }

        _nsButton0.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "ajouterRessource", _nsButton0), ""));

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _connect(_owner(), _nsButton0, "bAjoutRessource");
        }

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _connect(_owner(), _eoFrame0, "component");
        }

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _connect(_owner(), _eoDisplayGroup0, "eodRessources");
        }
    }

    protected void _init() {
        super._init();
        _eoTableColumn2.setMinWidth(31);
        _eoTableColumn2.setMaxWidth(1000);
        _eoTableColumn2.setPreferredWidth(114);
        _eoTableColumn2.setWidth(114);
        _eoTableColumn2.setResizable(true);
        _eoTableColumn2.setHeaderValue("Unit\u00e9");
        _eoTableColumnAssociation2.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "resUnite");
        _eoTableColumnAssociation2.establishConnection();
        _eoTableColumn1.setMinWidth(40);
        _eoTableColumn1.setMaxWidth(1000);
        _eoTableColumn1.setPreferredWidth(276);
        _eoTableColumn1.setWidth(276);
        _eoTableColumn1.setResizable(true);
        _eoTableColumn1.setHeaderValue("Libelle");
        _eoTableColumnAssociation1.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "resLibelle");
        _eoTableColumnAssociation1.establishConnection();

        if (_replacedObjects.objectForKey("_nsButton1") == null) {
            _setFontForComponent(_nsButton1, "Lucida Grande", 13, Font.PLAIN);
            _nsButton1.setMargin(new Insets(0, 2, 0, 2));
        }

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _setFontForComponent(_nsButton0, "Lucida Grande", 13, Font.PLAIN);
            _nsButton0.setMargin(new Insets(0, 2, 0, 2));
        }

        _eoTableAssociation0.bindAspect(com.webobjects.eointerface.EOAssociation.SourceAspect, _eoDisplayGroup0, "");
        _eoTableAssociation0.setSortsByColumnOrder(true);
        _eoTableAssociation0.establishConnection();

        if (_replacedObjects.objectForKey("_eoDisplayGroup0") == null) {
            _eoDisplayGroup0.setValidatesChangesImmediately(false);
            _eoDisplayGroup0.setFetchesOnLoad(false);
            _eoDisplayGroup0.setUsesOptimisticRefresh(false);
            _eoDisplayGroup0.setSelectsFirstObjectAfterFetch(true);
            _eoDisplayGroup0.setLocalKeys(new com.webobjects.foundation.NSArray(new Object[] {"resType", "resLibelle", "resRecord", "resUnite"}));
        }

        _eoTableColumnAssociation0.bindAspect(com.webobjects.eointerface.EOAssociation.ValueAspect, _eoDisplayGroup0, "resType");
        _eoTableColumnAssociation0.establishConnection();
        _eoTableColumn0.setMinWidth(40);
        _eoTableColumn0.setMaxWidth(1000);
        _eoTableColumn0.setPreferredWidth(105);
        _eoTableColumn0.setWidth(105);
        _eoTableColumn0.setResizable(true);
        _eoTableColumn0.setHeaderValue("Type");

        if (_replacedObjects.objectForKey("_nsTableView0") == null) {
            _nsTableView0.table().addColumn(_eoTableColumn0);
            _nsTableView0.table().addColumn(_eoTableColumn1);
            _nsTableView0.table().addColumn(_eoTableColumn2);
            _setFontForComponent(_nsTableView0.table().getTableHeader(), "Lucida Grande", 11, Font.PLAIN);
            _nsTableView0.table().setRowHeight(20);
        }

        if (!(_nsBox1.getLayout() instanceof EOViewLayout)) { _nsBox1.setLayout(new EOViewLayout()); }
        _nsTableView0.setSize(406, 133);
        _nsTableView0.setLocation(11, 376);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsTableView0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsTableView0);
        _nsButton0.setSize(26, 24);
        _nsButton0.setLocation(244, 337);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton0);
        _nsCustomView0.setSize(418, 305);
        _nsCustomView0.setLocation(-1, 26);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsCustomView0, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsCustomView0);
        _nsButton1.setSize(26, 24);
        _nsButton1.setLocation(142, 337);
        ((EOViewLayout)_nsBox1.getLayout()).setAutosizingMask(_nsButton1, EOViewLayout.MinYMargin);
        _nsBox1.add(_nsButton1);

        if (_replacedObjects.objectForKey("_nsBox0") == null) {
            if (!(_nsBox0.getLayout() instanceof EOViewLayout)) { _nsBox0.setLayout(new EOViewLayout()); }
            _nsBox1.setSize(417, 520);
            _nsBox1.setLocation(2, 2);
            ((EOViewLayout)_nsBox0.getLayout()).setAutosizingMask(_nsBox1, EOViewLayout.MinYMargin);
            _nsBox0.add(_nsBox1);
            _nsBox0.setBorder(new com.webobjects.eointerface.swing._EODefaultBorder("", true, "Lucida Grande", 13, Font.PLAIN));
        }

        if (!(_nsView0.getLayout() instanceof EOViewLayout)) { _nsView0.setLayout(new EOViewLayout()); }
        _nsBox0.setSize(421, 524);
        _nsBox0.setLocation(10, 4);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsBox0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsBox0);

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _nsView0.setSize(458, 552);
            _eoFrame0.setTitle("Window");
            _eoFrame0.setLocation(509, 324);
            _eoFrame0.setSize(458, 552);
        }
    }
}
