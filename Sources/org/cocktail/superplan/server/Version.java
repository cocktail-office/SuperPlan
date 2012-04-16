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

package org.cocktail.superplan.server;

import org.cocktail.fwkcktlwebapp.server.version.A_CktlVersion;

// Versions...
public final class Version extends A_CktlVersion {

	// Nom de l'appli
	private static final String APPLI_ID = "SuperPlan";

	// Version appli-
	private static final String VERSIONDATE = "30/03/2012";

	public static final int VERSIONNUMMAJ = 1;
	public static final int VERSIONNUMMIN = 4;
	public static final int VERSIONNUMPATCH = 0;
	public static final int VERSIONNUMBUILD = 19;

	// Version minimum de la base de donnees necessaire pour fonctionner avec cette version
	private static final String MIN_APPLI_BD_VERSION = "1.4.0.4";

	// Pour affichage en ligne de commande...
	public static void main(String argv[]) {
		System.out.println("");
		System.out.println(APPLI_ID);
		System.out.println("Version " + VERSIONNUMMAJ + "." + VERSIONNUMMIN + "." + VERSIONNUMPATCH + "." + VERSIONNUMBUILD + "." + " du "
				+ VERSIONDATE);
		System.out.println("(c) " + VERSIONDATE.substring(VERSIONDATE.length() - 4) + " Association Cocktail");
		System.out.println("");
		System.out.println("Version minimum de la base de donnees necessaire : " + MIN_APPLI_BD_VERSION);
		System.out.println("");
	}

	public CktlVersionRequirements[] dependencies() {
		return new CktlVersionRequirements[] {
				new CktlVersionRequirements(new org.cocktail.fwkcktlwebapp.server.version.CktlVersionWebObjects(), "5.2.4", null, true),
				new CktlVersionRequirements(new org.cocktail.fwkcktlwebapp.server.version.CktlVersionJava(), "1.5", null, false),
				new CktlVersionRequirements(new org.cocktail.fwkcktlwebapp.server.version.Version(), null, null, false),
				new CktlVersionRequirements(new VersionDB(), MIN_APPLI_BD_VERSION, null, true) };
	}

	public String name() {
		return APPLI_ID;
	}

	public String date() {
		return VERSIONDATE;
	}

	public int versionNumMaj() {
		return VERSIONNUMMAJ;
	}

	public int versionNumMin() {
		return VERSIONNUMMIN;
	}

	public int versionNumPatch() {
		return VERSIONNUMPATCH;
	}

	public int versionNumBuild() {
		return VERSIONNUMBUILD;
	}

}
