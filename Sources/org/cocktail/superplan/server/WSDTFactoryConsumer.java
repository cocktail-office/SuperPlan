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

import java.util.Iterator;
import java.util.Map;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.webservices.client.WOWebServiceClient;

/**
 * Classe permettant de se connecter au WebService de DT "dtws". <br>
 * 
 * @version 1.0.0
 */
public class WSDTFactoryConsumer {

	private static final String WSName = "dtws";

	private WOWebServiceClient wsClient;
	private String wsName;

	public WSDTFactoryConsumer(String pwsUrl) throws Exception {
		wsName = WSName;
		wsClient = new WOWebServiceClient(new java.net.URL(pwsUrl));
	}

	public NSDictionary activitesMails(String appId, String mailDemandeur) throws Exception {
		return mapToNSDictionary(wsInvoke("activitesMails", new Object[] { appId, mailDemandeur }));
	}

	/**
	 * Méthode utilitaire qui convertit un tableau d'objets Map en NSArray d'objets NSDictionary.
	 * 
	 * @param objs
	 *            Tableau de Map
	 * @return array
	 */
	public NSArray arrayMapsToNSArrayNSDictionarys(Object[] objs) throws Exception {
		// On transforme le tableau de map en NSArray de NSDictionary
		NSMutableArray tmpres = new NSMutableArray();
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] instanceof Map) {
				Map object = (Map) objs[i];
				tmpres.addObject(mapToNSDictionary(object));
			}
			else {
				tmpres.addObject(objs[i]);
			}
		}
		return tmpres.immutableClone();
	}

	public NSDictionary mapToNSDictionary(Object obj) throws Exception {
		Map object = (Map) obj;
		NSMutableDictionary res = new NSMutableDictionary(object.values().size());
		Iterator itKeys = object.keySet().iterator();
		Iterator itObj = object.values().iterator();
		while (itObj.hasNext()) {
			Object o = itObj.next();
			if (o instanceof Object[]) {
				o = arrayMapsToNSArrayNSDictionarys((Object[]) o);
			}
			res.setObjectForKey(o == null ? NSKeyValueCoding.NullValue : o, itKeys.next());
		}
		return res.immutableClone();
	}

	/**
	 * Equivaut à wsClient.invoke(getWsName(),operationName,args);
	 * 
	 * @param operationName
	 * @param args
	 * @return Un objet résultat s'il y a lieu
	 */
	public Object wsInvoke(String operationName, Object[] args) {
		return wsClient.invoke(getWsName(), operationName, args);
	}

	public WOWebServiceClient getWsClient() {
		return wsClient;
	}

	public String getWsName() {
		return wsName;
	}

}
