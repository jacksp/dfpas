package com.dfp.utiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class FileUtils {

    private static long getTimeStamp() {
	Calendar calendar = Calendar.getInstance();
	return calendar.getTimeInMillis();
    }

    // TODO el prefijo del fichero anexo debe ser el codigo de la reclamcion
    // generado
    public static File stream2file(FileItemStream item, String sExtension) throws Exception {
	InputStream in = item.openStream();
	String sName = item.getName();
	while (sName.length() < 12) {
	    sName = "x" + sName;
	}

	final File tempFile = File.createTempFile(FilenameUtils.getBaseName(sName) + "-", "." + sExtension);
	tempFile.deleteOnExit();
	try {
	    FileOutputStream out = new FileOutputStream(tempFile);
	    IOUtils.copy(in, out);
	} catch (Exception e) {
	    throw new Exception("Error al convertir el fichero::"+e.getMessage()); 
	}
	return tempFile;
    }

    public static File toFileFromByteArray(byte[] byteArray) throws Exception {
	File tempFile = File.createTempFile("doc" + getTimeStamp(), ".jpg");
	try {
	    FileOutputStream fos = new FileOutputStream(tempFile);
	    fos.write(byteArray);
	} catch (Exception e) {
	    throw new Exception("Error al convertir el fichero::"+e.getMessage()); 
	}
	return tempFile;
    }

}
