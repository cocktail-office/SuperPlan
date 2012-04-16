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
package fr.univlr.common.utilities;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.eodistribution.client.EODistributedDataSource;
import com.webobjects.eointerface.EODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

public class DBHandler {

	public static EOQualifier qualifierFromPeriodicites(NSArray periodicites) {
		NSMutableArray qDates = new NSMutableArray();
		for (int i = 0; i < periodicites.count(); i += 2) {
			NSTimestamp debut = (NSTimestamp) periodicites.objectAtIndex(i);
			NSTimestamp fin = (NSTimestamp) periodicites.objectAtIndex(i + 1);
			qDates.addObject(EOQualifier.qualifierWithQualifierFormat("dateFin>%@ and dateDeb<%@", new NSArray(new Object[] { debut, fin })));
		}

		EOQualifier qualifierDates = new EOOrQualifier(qDates);
		return qualifierDates;
	}

	/** renvoi un qualifier construit avec l'expression et les valeurs */
	public static EOQualifier getQualifier(String expression, NSArray values) {
		return EOQualifier.qualifierWithQualifierFormat(expression, values);
	}

	/** renvoi un qualifier construit avec l'expression */
	public static EOQualifier getQualifier(String expression) {
		return EOQualifier.qualifierWithQualifierFormat(expression, null);
	}

	/**
	 * renvoi un qualifier construit avec le champs field et la valeur que l'on veut lui associer. exemple :
	 * DBHandler.getSimpleQualifier("individuUlr",eoIndividu)
	 */
	public static EOQualifier getSimpleQualifier(String field, Object value) {
		return EOQualifier.qualifierWithQualifierFormat(field + "=%@", new NSArray(value));
	}

	/**
	 * EOEditingContext context String tableName String key Object value
	 */
	public static EOGenericRecord fetchUniqueData(EOEditingContext context, String tableName, String key, Object value) {
		EOQualifier qualifie = EOQualifier.qualifierWithQualifierFormat(key + "=%@", new NSArray(value));
		NSArray objets = fetchData(context, tableName, qualifie);
		if (objets.count() > 0) {
			return (EOGenericRecord) objets.objectAtIndex(0);
		}
		else {
			return null;
		}
	}

	/**
	 * EOEditingContext eContext, String tableName, EOQualifier leQualifier
	 */
	public static EOGenericRecord fetchUniqueData(EOEditingContext eContext, String tableName, EOQualifier qualifier) {
		NSArray objets = fetchData(eContext, tableName, qualifier);
		if (objets.count() > 0) {
			return (EOGenericRecord) objets.objectAtIndex(0);
		}
		else {
			return null;
		}
	}

	/** fetch avec une chaine et une valeur correspondante */
	public static NSArray fetchData(EOEditingContext context, String tableName, String key, Object value) {
		EOQualifier qualifie = EOQualifier.qualifierWithQualifierFormat(key + "=%@", new NSArray(value));
		return fetchData(context, tableName, qualifie);
	}

	public static NSArray fetchData(EOEditingContext context, String leNomTable, EOQualifier leQualifier) {
		return fetchData(context, leNomTable, leQualifier, null);
	}

	public static NSArray fetchData(EOEditingContext context, String leNomTable, EOQualifier leQualifier, EOSortOrdering sortOrdering) {
		EOFetchSpecification myFetch = new EOFetchSpecification(leNomTable, leQualifier, sortOrdering == null ? null : new NSArray<EOSortOrdering>(
				sortOrdering));
		myFetch.setUsesDistinct(true);
		return context.objectsWithFetchSpecification(myFetch);
	}

	/** retourne l'objet du globalID s'il existe, sinon son fault */
	public static EOGenericRecord safeObjectForGlobalID(EOEditingContext eContext, EOGlobalID gid) {
		EOGenericRecord objFault;
		objFault = (EOGenericRecord) eContext.objectForGlobalID(gid);
		if (objFault != null) {
			return objFault;
		}
		else {
			return (EOGenericRecord) eContext.faultForGlobalID(gid, eContext);
		}
	}

	/** retourne les globalIDs des objets */
	public static NSArray globalIDsForObjects(EOEditingContext eContext, NSArray objects) {
		NSMutableArray gids = new NSMutableArray();
		for (int i = 0; i < objects.count(); i++) {
			gids.addObject(eContext.globalIDForObject((EOEnterpriseObject) objects.objectAtIndex(i)));
		}
		return gids;
	}

	/** retourne des faults d'objets à partir des globalIDs */
	public static NSArray faultsForGlobalIDs(EOEditingContext eContext, NSArray ids) {
		NSMutableArray objects = new NSMutableArray();
		for (int i = 0; i < ids.count(); i++) {
			objects.addObject(eContext.faultForGlobalID((EOGlobalID) ids.objectAtIndex(i), eContext));
		}
		return objects;
	}

	/** retourne les globalIDs des objets */
	public static NSArray objectsForGlobalIDs(EOEditingContext eContext, NSArray ids) {
		NSMutableArray objects = new NSMutableArray();
		EOGenericRecord record = null;
		for (int i = 0; i < ids.count(); i++) {
			record = (EOGenericRecord) eContext.objectForGlobalID((EOGlobalID) ids.objectAtIndex(i));
			if (record != null) {
				objects.addObject(record);
			}
		}
		return objects;
	}

	/** obtenir la valeur de la cle primaire a partir d'un Object quand celle-ci est cachee */
	public static Object primaryKeyForObject(EOEditingContext eContext, EOEnterpriseObject record, String primaryKeyName) {
		try {
			return EOUtilities.primaryKeyForObject(eContext, record).objectForKey(primaryKeyName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** cree une instance de l'enregistrement d'entite entity et avec l'editingContext eContext */
	public static EOGenericRecord getInstance(EOEditingContext eContext, String entity) {
		EOClassDescription descriptionClass = EOClassDescription.classDescriptionForEntityName(entity);
		EOGenericRecord instance = (EOGenericRecord) descriptionClass.createInstanceWithEditingContext(eContext, null);
		eContext.insertObject(instance);
		return instance;
	}

	public static Object primaryKey(EOEditingContext eContext, EOGlobalID gid, String primKey) {
		EOEnterpriseObject record = eContext.faultForGlobalID(gid, eContext);
		if (record != null) {
			return primaryKey(eContext, record, primKey);
		}
		else {
			return null;
		}
	}

	public static Object primaryKey(EOEditingContext eContext, EOEnterpriseObject record, String primKey) {
		try {
			if (record == null) {
				return null;
			}
			else {
				NSDictionary keys = EOUtilities.primaryKeyForObject(eContext, record);
				return keys.objectForKey(primKey);
			}
		} // try
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** enleve tous les autres pointeurs d'un objet */
	public static NSMutableArray retirerMultiples(NSArray array) {
		NSMutableArray resultat = new NSMutableArray();
		for (int i = 0; i < array.count(); i++) {
			Object obj = array.objectAtIndex(i);
			if (!resultat.containsObject(obj)) {
				resultat.addObject(obj);
			}
		}
		return resultat;
	}

	public static void invalidateObjects(EOEditingContext ec, NSArray list) {
		NSMutableArray listGIDs = new NSMutableArray();
		for (int i = 0; i < list.count(); i++) {
			listGIDs.addObject(ec.globalIDForObject((EOEnterpriseObject) list.objectAtIndex(i)));
		}
		ec.invalidateObjectsWithGlobalIDs(listGIDs);
	}

	/** invalide l'object record passe en parametre */
	public static void invalidateObject(EOEditingContext ec, EOGenericRecord record) {
		NSArray listGIDs = new NSArray(new Object[] { ec.globalIDForObject(record) });
		ec.invalidateObjectsWithGlobalIDs(listGIDs);
	}

	public static void fetchDisplayGroup(EODisplayGroup table, EOQualifier qualifier) {
		fetchDisplayGroup(table, qualifier, null, true);
	}

	public static void fetchDisplayGroup(EODisplayGroup table, EOQualifier qualifier, EOSortOrdering sort) {
		if (sort != null) {
			fetchDisplayGroup(table, qualifier, new NSArray(sort), true);
		}
		else {
			fetchDisplayGroup(table, qualifier, null, true);
		}
	}

	public static void fetchDisplayGroup(EODisplayGroup table, EOQualifier leQualifier, NSArray leSort, boolean selectsFirst) {
		String entityName = table.dataSource().classDescriptionForObjects().entityName();
		table.setSelectsFirstObjectAfterFetch(selectsFirst);
		EOFetchSpecification leFetch = new EOFetchSpecification(entityName, leQualifier, leSort);
		leFetch.setUsesDistinct(true);
		leFetch.setRefreshesRefetchedObjects(true);
		((EODistributedDataSource) table.dataSource()).setFetchSpecification(leFetch);
		table.fetch();
	}

}
