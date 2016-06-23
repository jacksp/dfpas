package com.dfp.core;

import java.text.SimpleDateFormat;

public class StringKeys {
    
    public static final String DIR_DATA_OPENSHIFT = ""; 
    
    public static final String urlPdf = "http://wscentral-dfpas.rhcloud.com/PdfServlet?idClaim=";

    public static final String urlEstadoAcepta = "http://wscentral-dfpas.rhcloud.com/rest/claimservices/aceptaSiguienteEstadoReclamacion?claimId=";
    
    public static final String urlEstadoRechaza = "http://wscentral-dfpas.rhcloud.com/rest/claimservices/rechazaSiguienteEstadoReclamacion?claimId=";
    
    public static final String mailTecnico = "ladefensadelpasajero@gmail.com";
    
    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    
    public static final String sformatter = "dd/MM/yyyy hh:mm a";
    
    public static final String sformatterretraso = "HH:mm:ss";
    
    public static final SimpleDateFormat formatterDuracionRetraso = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    
    public static final SimpleDateFormat formatterHora = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public static final String RECLAMADAAEROLINEA = "Reclamada a la aerolinea";

    public static final String RECLAMADAUNION = "Reclamada comisión europea";

    public static final String JPG = "JPG";
    
    public static final String JPEG = "JPEG";
    
    public static final String PNG = "PNG";
    
    public static final String GIF = "GIF";
    
    public static final String LOCALEPHONE = "localephone";
    
    public static final String RETRASOSALIDA = "retrasosalida";
    
    public static final String HORASALIDAPREVISTA = "horasalidaprevista";
    
    public static final String HORALLEGADAPREVISTA = "horallegadaprevista";
    
    public static final String RETRASOLLEGADA = "retrasollegada";
    
    public static final String DATESALIDAPREVISTA = "datesalidaprevista";
    
    public static final String DATELLEGADAPREVISTA = "datellegadaprevista";
    
    public static final String[] STRINGLABELSDURATION = {"Days","Day","Días","Día","Dia","Dies"};

	public static final Integer ULTIMONIVEL = new Integer(5);
	
	public static final Integer ULTIMONIVELDENEGADA = new Integer(-1);
    
}
