package com.dfp.utiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class FileUtils {

    private static long getTimeStamp() {
	Calendar calendar = Calendar.getInstance();
	return calendar.getTimeInMillis();
    }
    
    private static File compresssionImage(InputStream in, String sName, String sExtension) throws IOException{
	
	BufferedImage image = ImageIO.read(in);
	
	//File compressedImageFile = new File("C:\\Users\\amfranco\\Downloads\\compress.jpg");
	
	final File compressedImageFile = File.createTempFile(FilenameUtils.getBaseName(sName) + "-", "." + sExtension);
	
	
	OutputStream os =new FileOutputStream(compressedImageFile);

	Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
	ImageWriter writer = (ImageWriter) writers.next();

	ImageOutputStream ios = ImageIO.createImageOutputStream(os);
	writer.setOutput(ios);

	ImageWriteParam param = writer.getDefaultWriteParam();
	param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	param.setCompressionQuality(0.05f);
	      
	writer.write(null, new IIOImage(image, null, null), param);
	os.close();
	ios.close();
	writer.dispose();
	compressedImageFile.deleteOnExit();
	return compressedImageFile;
    }
    
    private static File createZipTemp(InputStream in, String sName, String sExtension) throws Exception{
   	final File tempFile = File.createTempFile(FilenameUtils.getBaseName(sName) + "-", ".zip");
   	
   	byte[] buffer = new byte[1024];
   	try {
   	    FileOutputStream fos = new FileOutputStream(tempFile);
   	    ZipOutputStream zos = new ZipOutputStream(fos);
   	    ZipEntry ze= new ZipEntry(sName+"."+sExtension);
   	    zos.putNextEntry(ze);   	    
   	    int len;   	    
		while ((len = in.read(buffer)) > 0) {
			zos.write(buffer, 0, len);
		}
		in.close();
    		zos.closeEntry();
    		zos.close();   	    
   	} catch (Exception e) {
   	    throw new Exception("Error al convertir el fichero::"+e.getMessage()); 
   	}
   	tempFile.deleteOnExit();
   	return tempFile;
       }
    
    private static File createTemp(InputStream in, String sName, String sExtension) throws Exception{
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

    // TODO el prefijo del fichero anexo debe ser el codigo de la reclamcion
    // generado
    public static File stream2file(FileItemStream item, String sExtension) throws Exception {
	InputStream in = item.openStream();
	
	String sName = item.getName();
	while (sName.length() < 12) {
	    sName = "x" + sName;
	}
	
	if(sExtension.toUpperCase().equals("JPG")  )
	    return  compresssionImage( in,  sName, sExtension);
	else
	if(sExtension.toUpperCase().equals("PNG") ||  sExtension.toUpperCase().equals("GIF") )
	    return  createTemp( in,  sName, sExtension);
	else 
	    return  createZipTemp( in,  sName, sExtension);
	 //   return  createTemp( in,  sName, sExtension);

	/*final File tempFile = File.createTempFile(FilenameUtils.getBaseName(sName) + "-", "." + sExtension);
	tempFile.deleteOnExit();
	try {
	    FileOutputStream out = new FileOutputStream(tempFile);
	    IOUtils.copy(in, out);
	} catch (Exception e) {
	    throw new Exception("Error al convertir el fichero::"+e.getMessage()); 
	} 
	
	return tempFile;*/
	
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
