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
package org.cocktail.superplan.server.gestionimpression;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ReportsGenerator {

	String dbConnectionURL, username, password, jdbcDriverName;
	Connection connection = null;

	public ReportsGenerator(String dbConnectionURL, String username, String password, String jdbcDriverName) {
		this.dbConnectionURL = dbConnectionURL;
		this.username = username;
		this.password = password;
		this.jdbcDriverName = jdbcDriverName;
	}

	public byte[] getPdfBytesForReport(String reportPathName, Map parameters) throws Exception {

		Class.forName(jdbcDriverName);
		connection = DriverManager.getConnection(dbConnectionURL, username, password);

		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPathName, parameters, connection);

		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		JRPdfExporter jrpdfexporter = new JRPdfExporter();
		jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytearrayoutputstream);
		jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		jrpdfexporter.exportReport();

		connection.close();

		return bytearrayoutputstream.toByteArray();
	}

}
