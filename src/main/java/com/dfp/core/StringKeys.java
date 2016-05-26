package com.dfp.core;

import java.text.SimpleDateFormat;

public class StringKeys {
    
    public static final String DIR_DATA_OPENSHIFT = ""; 
    
    public static final String urlBase = "http://defensadelpasajero.com";
    
    public static final String urlPdf = "http://wscentral-dfpas.rhcloud.com/PdfServlet?idClaim=";

    public static final String urlEstadoAcepta = "http://wscentral-dfpas.rhcloud.com/rest/claimservices/aceptaSiguienteEstadoReclamacion?claimId=";
    
    public static final String urlEstadoRechaza = "http://wscentral-dfpas.rhcloud.com/rest/claimservices/rechazaSiguienteEstadoReclamacion?claimId=";
    
    public static final String mailTecnico = "ladefensadelpasajero@gmail.com";
    
    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public static final String RECLAMADAAEROLINEA = "Reclamada a la aerolinea";

    public static final String RECLAMADAUNION = "Reclamada comisión europea";
}
