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
package fr.univlr.utilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;

import com.webobjects.eointerface.swing.EOView;

public class SwapView extends EOView {

	private static final long serialVersionUID = 5696130844466066825L;

	/** constructeur utilise par interface builder */
	public SwapView() {
		super();
		this.setLayout(new BorderLayout());
	}

	public SwapView(EOView laView) {
		super();
		this.setLayout(new BorderLayout());
		this.removeAll();
		this.setContentView(laView);
	}

	/** ajoute une view au swap. */
	public boolean setContentView(EOView aComponent) {
		if (aComponent == null) {
			if (this.getComponentCount() > 0) {
				this.getComponent(0).setVisible(false);
				this.getComponent(0).invalidate();
				this.removeAll();
				this.validate();
			}
			return false;
		}

		if (this.getComponentCount() == 0) {
			aComponent.setLocation(0, 0);
			aComponent.setVisible(true);
			aComponent.validate();
			this.add(aComponent);
			this.setVisible(true);
			this.validate();
			return true;
		}

		if (aComponent == this.getComponent(0)) {
			return true;
		}

		// suppression de la vue en cours et ajout de la nouvelle
		this.getComponent(0).setVisible(false);
		this.getComponent(0).invalidate();
		this.removeAll();
		aComponent.setLocation(0, 0);
		aComponent.setVisible(true);
		aComponent.validate();
		this.add(aComponent);
		this.validate();
		return true;
	}

	/** affecte un objet de type JComponent dans la vue */
	public boolean setContentView(Container aContainer) {
		return setComponent(aContainer);
	}

	/** affecte un objet de type JComponent dans la vue */
	public boolean setContentView(JComponent aComponent) {
		return setComponent(aComponent);
	}

	/**
	 * ajoute une JComponent au swap. au cas ou le composant a ajouter n'est pas un EOView.
	 */
	public boolean setComponent(Component aComponent) {
		if (aComponent == null) {
			return false;
		}

		if (this.getComponentCount() == 0) {
			this.add(aComponent);
			aComponent.setVisible(true);
			aComponent.validate();
			this.validate();
			return true;
		}

		if (aComponent == this.getComponent(0)) {
			return true;
		}

		// suppression de la vue en cours et ajout de la nouvelle
		this.getComponent(0).setVisible(false);
		this.getComponent(0).invalidate();
		this.removeAll();
		this.add(aComponent);
		aComponent.setVisible(true);
		aComponent.validate();
		this.validate();
		return true;
	}

	/** Retourne la vue en cours */
	public EOView getContentView() {
		if (this.getComponentCount() == 0) {
			return null;
		}
		else {
			return (EOView) this.getComponent(0);
		}
	}

}
