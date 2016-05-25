package com.dfp.core.dto;

/**
 * Created by Alberto on 30/12/2015.
 */
public class NacionDTO {

    private String nombreNacion = "";

    private String codigoNacion = "";
    
    private String codigoPhone = "";

    public String getCodigoPhone() {
		return codigoPhone;
	}

	public void setCodigoPhone(String codigoPhone) {
		this.codigoPhone = codigoPhone;
	}

	public String getNombreNacion() {
        return nombreNacion;
    }

    public void setNombreNacion(String nombreNacion) {
        this.nombreNacion = nombreNacion;
    }

    public String getCodigoNacion() {
        return codigoNacion;
    }

    public void setCodigoNacion(String codigoNacion) {
        this.codigoNacion = codigoNacion;
    }
}
