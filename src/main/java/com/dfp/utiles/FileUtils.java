package com.dfp.utiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class FileUtils {
	
	public static final String PREFIX = "stream2file";

	//TODO el prefijo del fichero anexo debe ser el c�digo de la reclamcion generado
	 public static File stream2file (FileItemStream item,String sExtension) throws IOException {
		 	InputStream in = item.openStream();
		 	
	        final File tempFile = File.createTempFile(item.getName(), "."+sExtension);
	        tempFile.deleteOnExit();
	        try  {
	        	FileOutputStream out = new FileOutputStream(tempFile);
	            IOUtils.copy(in, out);
	        }catch (Exception e) {
				// TODO: handle exception
			}
	        return tempFile;
	    }


	


}
