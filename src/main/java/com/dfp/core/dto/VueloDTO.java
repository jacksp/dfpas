package com.dfp.core.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alberto on 30/12/2015.
 */
public class VueloDTO {
	
	private SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm"); 


    private String codigoVuelo = "";

    private String idCodigoCompania = "";

    
    private String aeropuertoDestino="";
	
	 private String aeropuertoOrigen="";

	public VueloDTO(String idVuelo,
	    String aeropuertoSalida, String aeropuertoLlegada) {
	    this.codigoVuelo = idVuelo;
	    this.aeropuertoOrigen= aeropuertoSalida;
	    this.aeropuertoDestino = aeropuertoLlegada;
	}

	public VueloDTO() {
	    // TODO Auto-generated constructor stub
	}

	public String getAeropuertoOrigen() {
		return aeropuertoOrigen;
	}

	public void setAeropuertoOrigen(String aeropuertoOrigen) {
		this.aeropuertoOrigen = aeropuertoOrigen;
	}

	public String getAeropuertoDestino() {
		return aeropuertoDestino;
	}

	public void setAeropuertoDestino(String aeropuertoDestino) {
		this.aeropuertoDestino = aeropuertoDestino;
	}

	public String getCodigoVuelo() {
		return codigoVuelo;
	}

	public void setCodigoVuelo(String codigoVuelo) {
		this.codigoVuelo = codigoVuelo;
	}

	public String getIdCodigoCompania() {
		return idCodigoCompania;
	}

	public void setIdCodigoCompania(String idCodigoCompania) {
		this.idCodigoCompania = idCodigoCompania;
	}

//	public Date getHllegadaprevista() {
//		return hllegadaprevista;
//	}
//
//	public void setHllegadaprevista(String hllegadaprevista) {
//		Date date;
//		try {
//			date = dt.parse(hllegadaprevista);
//			this.hllegadaprevista = date;
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//	}
//
//	public Date getHsalidaprevista() {
//		return hsalidaprevista;
//	}
//
//	public void setHsalidaprevista(String hsalidaprevista) {
//		Date date;
//		try {
//			date = dt.parse(hsalidaprevista);
//			this.hsalidaprevista = date;
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//	}

  


}
