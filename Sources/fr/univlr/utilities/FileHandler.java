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

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSMutableArray;

public class FileHandler {
	private static final String LNX_CMD = "gpdf ";
	private static final String MAC_CMD = "open ";
	private final String TEMP_PATH = System.getProperty("java.io.tmpdir").concat("/");
	private String temporaryDir;

	public String getTemporaryDir() {
		return temporaryDir;
	}

	public void setTemporaryDir(String dir) {
		temporaryDir = dir;
	}

	/**
	 * Methode qui renvoie le chemin ou a ete depose le pdf
	 * 
	 * @param data
	 *            les donnees du pdf
	 * @param filename
	 *            le nom du fichier qu'on veut lui donner
	 * @return le chemin du pdf
	 */
	public String dataToPDF(NSData data, String fileName) {
		String filePath = "";

		if (data == null) {
			System.out.println("Impossible de recuperer le PDF.");
			return null;
		}

		setTemporaryDir(TEMP_PATH);
		filePath = getTemporaryDir() + fileName + ".pdf";

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			data.writeToStream(fileOutputStream);
			fileOutputStream.close();
		}
		catch (Exception exception) {
			/*
			 * WindowHandler.showError( "Impossible d'ecrire le fichier PDF sur le disque.\n Verifiez qu'un autre fichier n'est pas deja
			 * ouvert." );
			 */
			exception.printStackTrace();
			return null;
		}
		try {
			File tmpFile = new File(filePath);
			if (!tmpFile.exists()) {
				System.out.println("Le fichier " + filePath + " n'existe pas.");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return filePath;
	}

	/**
	 * ouvre le pdf a partir d'un chemin param filePath le chemin du pdf a ouvrir
	 */
	public void openFile(String filePath) throws Exception {
		File aFile = new File(filePath);
		Runtime runtime = Runtime.getRuntime();
		if (System.getProperty("os.name").startsWith("Windows")) {
			runtime.exec(new String[] { "rundll32", "url.dll,FileProtocolHandler", "\"" + aFile + "\"" });
		}
		else
			if (System.getProperty("os.name").startsWith("Linux")) {
				runtime.exec(LNX_CMD + aFile);
			}
			else {
				runtime.exec(MAC_CMD + aFile);
			}

	}

	public String dataToXXX(NSData data, String fileName, String extension) throws Exception {
		String filePath = "";

		if (data == null) {
			throw new Exception("Impossible de recup\u00e9rer le fichier " + fileName);
		}

		setTemporaryDir(TEMP_PATH);

		filePath = getTemporaryDir() + fileName + "." + extension;

		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		data.writeToStream(fileOutputStream);
		fileOutputStream.close();

		File tmpFile = new File(filePath);
		if (!tmpFile.exists()) {
			throw new Exception("Le fichier " + filePath + " n'existe pas.");
		}

		return filePath;
	}

	/** retourne la liste des fichiers dans un repertoire */
	public static NSArray listFiles(File directory) {
		int i;
		if (directory == null || !directory.exists()) {
			return new NSArray();
		}
		NSMutableArray returnedList = new NSMutableArray();
		File[] dirFileList = directory.listFiles();
		for (i = 0; i < dirFileList.length; i++) {
			if (dirFileList[i].isDirectory()) {
				returnedList.addObjectsFromArray(listFiles(dirFileList[i]));
			}
			else {
				returnedList.addObject(dirFileList[i]);
			}
		}
		return returnedList;
	}

	public static byte[] imageToByteArray(Image img, String formatName) {
		if (img instanceof RenderedImage == false) {
			img = getBufferedImage(img, Transparency.TRANSLUCENT);
		}

		ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
		try {
			ImageIO.write((RenderedImage) img, formatName, byteOS);
		}
		catch (IOException e) {
			return null;
		}
		return byteOS.toByteArray();
	}

	public static BufferedImage getBufferedImage(Image img, int transparency) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage bi = gc.createCompatibleImage(img.getWidth(null), img.getHeight(null), transparency);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return bi;
	}

}
