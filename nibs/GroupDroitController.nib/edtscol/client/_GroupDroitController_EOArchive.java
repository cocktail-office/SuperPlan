// _GroupDroitController_EOArchive.java
// Generated by EnterpriseObjects palette at mercredi 11 avril 2007 13 h 41 Europe/Paris

package edtscol.client;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JPanel;

import com.webobjects.eoapplication.EOArchive;
import com.webobjects.eointerface.swing.EOViewLayout;
import com.webobjects.foundation.NSDisposableRegistry;

public class _GroupDroitController_EOArchive extends com.webobjects.eoapplication.EOArchive {
    com.webobjects.eocontrol.EOEditingContext _eoEditingContext0;
    com.webobjects.eointerface.swing.EOFrame _eoFrame0;
    com.webobjects.eointerface.swing.EOMatrix _nsMatrix0, _nsMatrix1;
    com.webobjects.eointerface.swing.EOTextField _nsTextField0, _nsTextField1, _nsTextField2, _nsTextField3, _nsTextField4;
    com.webobjects.eointerface.swing.EOView _nsCustomView0;
    javax.swing.JButton _nsButton0;
    javax.swing.JPanel _nsView0;
    javax.swing.JRadioButton _jRadioButton0, _jRadioButton1, _jRadioButton2, _jRadioButton3, _jRadioButton4;

    public _GroupDroitController_EOArchive(Object owner, NSDisposableRegistry registry) {
        super(owner, registry);
    }

    protected void _construct() {
        Object owner = _owner();
        EOArchive._ObjectInstantiationDelegate delegate = (owner instanceof EOArchive._ObjectInstantiationDelegate) ? (EOArchive._ObjectInstantiationDelegate)owner : null;
        Object replacement;

        super._construct();

        _eoEditingContext0 = ((com.webobjects.eocontrol.EOEditingContext.substitutionEditingContext() != null) ? com.webobjects.eocontrol.EOEditingContext.substitutionEditingContext() : (com.webobjects.eocontrol.EOEditingContext)_registered(new com.webobjects.eocontrol.EOEditingContext(), "EditingContext"));
        _nsTextField4 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField13");
        _nsTextField3 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField12");
        _nsTextField2 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField11");
        _nsTextField1 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField1");
        _jRadioButton4 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Mes groupes"), "");
        _jRadioButton3 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Mes services"), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "typeChoix")) != null)) {
            _nsMatrix1 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOMatrix)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsMatrix1");
        } else {
            _nsMatrix1 = (com.webobjects.eointerface.swing.EOMatrix)_registered(new com.webobjects.eointerface.swing.EOMatrix(1, 2, 2, 2), "NSMatrix11");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "nbIndiv")) != null)) {
            _nsTextField0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOTextField)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsTextField0");
        } else {
            _nsTextField0 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField");
        }

        _jRadioButton2 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Motifs"), "");
        _jRadioButton1 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Noms"), "");
        _jRadioButton0 = (javax.swing.JRadioButton)_registered(new javax.swing.JRadioButton("Noms et motifs"), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "mOption")) != null)) {
            _nsMatrix0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOMatrix)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsMatrix0");
        } else {
            _nsMatrix0 = (com.webobjects.eointerface.swing.EOMatrix)_registered(new com.webobjects.eointerface.swing.EOMatrix(1, 3, 0, 0), "NSMatrix1");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "ok")) != null)) {
            _nsButton0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (javax.swing.JButton)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsButton0");
        } else {
            _nsButton0 = (javax.swing.JButton)_registered(new javax.swing.JButton("VALIDER"), "NSButton1");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "treeView")) != null)) {
            _nsCustomView0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOView)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsCustomView0");
        } else {
            _nsCustomView0 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "View");
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

        if (_replacedObjects.objectForKey("_nsMatrix1") == null) {
            _connect(_owner(), _nsMatrix1, "typeChoix");
        }

        if (_replacedObjects.objectForKey("_nsTextField0") == null) {
            _connect(_owner(), _nsTextField0, "nbIndiv");
        }

        if (_replacedObjects.objectForKey("_nsMatrix0") == null) {
            _connect(_owner(), _nsMatrix0, "mOption");
        }

        _nsButton0.addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "ok", _nsButton0), ""));

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _connect(_owner(), _nsButton0, "ok");
        }

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _connect(_owner(), _eoFrame0, "component");
        }

        if (_replacedObjects.objectForKey("_nsCustomView0") == null) {
            _connect(_owner(), _nsCustomView0, "treeView");
        }
    }

    protected void _init() {
        super._init();
        _setFontForComponent(_nsTextField4, "Lucida Grande", 11, Font.PLAIN);
        _nsTextField4.setEditable(false);
        _nsTextField4.setOpaque(false);
        _nsTextField4.setText("ne sont plus \u00e9dit\u00e9s. Voulez-vous tout de m\u00eame \u00e9diter avec l'agenda avec ?");
        _nsTextField4.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField4.setSelectable(false);
        _nsTextField4.setEnabled(true);
        _nsTextField4.setBorder(null);
        _setFontForComponent(_nsTextField3, "Lucida Grande", 11, Font.PLAIN);
        _nsTextField3.setEditable(false);
        _nsTextField3.setOpaque(false);
        _nsTextField3.setText("personne(s), les motifs et noms");
        _nsTextField3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField3.setSelectable(false);
        _nsTextField3.setEnabled(true);
        _nsTextField3.setBorder(null);
        _setFontForComponent(_nsTextField2, "Lucida Grande", 11, Font.PLAIN);
        _nsTextField2.setEditable(false);
        _nsTextField2.setOpaque(false);
        _nsTextField2.setText("ou moins lisible. Au dessus de");
        _nsTextField2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField2.setSelectable(false);
        _nsTextField2.setEnabled(true);
        _nsTextField2.setBorder(null);
        _setFontForComponent(_nsTextField1, "Lucida Grande", 11, Font.PLAIN);
        _nsTextField1.setEditable(false);
        _nsTextField1.setOpaque(false);
        _nsTextField1.setText("Selon le nombres de personnes impliqu\u00e9es, l'agenda multiple est plus ");
        _nsTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField1.setSelectable(false);
        _nsTextField1.setEnabled(true);
        _nsTextField1.setBorder(null);
        _setFontForComponent(_jRadioButton4, "Lucida Grande", 13, Font.PLAIN);
        _setFontForComponent(_jRadioButton3, "Lucida Grande", 13, Font.PLAIN);

        if (_replacedObjects.objectForKey("_nsMatrix1") == null) {
            _jRadioButton3.setSize(113, 17);
            _jRadioButton3.setLocation(0, 0);
            _nsMatrix1.add(_jRadioButton3);
            _jRadioButton4.setSize(113, 17);
            _jRadioButton4.setLocation(115, 0);
            _nsMatrix1.add(_jRadioButton4);
            _setFontForComponent(_nsMatrix1, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_nsTextField0") == null) {
            _setFontForComponent(_nsTextField0, "Lucida Grande", 13, Font.PLAIN);
            _nsTextField0.setEditable(false);
            _nsTextField0.setOpaque(true);
            _nsTextField0.setText("");
            _nsTextField0.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            _nsTextField0.setSelectable(true);
            _nsTextField0.setEnabled(false);
        }

        _setFontForComponent(_jRadioButton2, "Lucida Grande", 13, Font.PLAIN);
        _setFontForComponent(_jRadioButton1, "Lucida Grande", 13, Font.PLAIN);
        _setFontForComponent(_jRadioButton0, "Lucida Grande", 13, Font.PLAIN);

        if (_replacedObjects.objectForKey("_nsMatrix0") == null) {
            _jRadioButton0.setSize(129, 18);
            _jRadioButton0.setLocation(0, 0);
            _nsMatrix0.add(_jRadioButton0);
            _jRadioButton1.setSize(129, 18);
            _jRadioButton1.setLocation(130, 0);
            _nsMatrix0.add(_jRadioButton1);
            _jRadioButton2.setSize(129, 18);
            _jRadioButton2.setLocation(260, 0);
            _nsMatrix0.add(_jRadioButton2);
            _setFontForComponent(_nsMatrix0, "Lucida Grande", 13, Font.PLAIN);
        }

        if (_replacedObjects.objectForKey("_nsButton0") == null) {
            _setFontForComponent(_nsButton0, "Lucida Grande", 13, Font.PLAIN);
            _nsButton0.setMargin(new Insets(0, 2, 0, 2));
        }

        if (!(_nsView0.getLayout() instanceof EOViewLayout)) { _nsView0.setLayout(new EOViewLayout()); }
        _nsCustomView0.setSize(500, 347);
        _nsCustomView0.setLocation(7, 30);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsCustomView0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsCustomView0);
        _nsButton0.setSize(98, 27);
        _nsButton0.setLocation(410, 456);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsButton0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsButton0);
        _nsMatrix0.setSize(390, 18);
        _nsMatrix0.setLocation(11, 445);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsMatrix0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsMatrix0);
        _nsTextField1.setSize(382, 20);
        _nsTextField1.setLocation(11, 385);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsTextField1, EOViewLayout.MinYMargin);
        _nsView0.add(_nsTextField1);
        _nsTextField2.setSize(176, 14);
        _nsTextField2.setLocation(11, 404);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsTextField2, EOViewLayout.MinYMargin);
        _nsView0.add(_nsTextField2);
        _nsTextField0.setSize(23, 22);
        _nsTextField0.setLocation(178, 398);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsTextField0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsTextField0);
        _nsTextField3.setSize(181, 14);
        _nsTextField3.setLocation(201, 404);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsTextField3, EOViewLayout.MinYMargin);
        _nsView0.add(_nsTextField3);
        _nsTextField4.setSize(412, 14);
        _nsTextField4.setLocation(11, 424);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsTextField4, EOViewLayout.MinYMargin);
        _nsView0.add(_nsTextField4);
        _nsMatrix1.setSize(229, 18);
        _nsMatrix1.setLocation(26, 6);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsMatrix1, EOViewLayout.MinYMargin);
        _nsView0.add(_nsMatrix1);

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _nsView0.setSize(526, 492);
            _eoFrame0.setTitle("Choix des groupes ou individus");
            _eoFrame0.setLocation(411, 328);
            _eoFrame0.setSize(526, 492);
        }
    }
}
