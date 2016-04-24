package com.dfp.core.dto;

import java.util.Date;

/**
 * Created by Alberto on 30/12/2015.
 */
public class VueloDTO {


    private String codigoVuelo = "";

    private String idCodigoCompania = "";

    private Date horaInicioVuelo = null;

    private Date horaFinVuelo = null;

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

	public Date getHoraInicioVuelo() {
        return horaInicioVuelo;
    }

    public void setHoraInicioVuelo(Date horaInicioVuelo) {
        this.horaInicioVuelo = horaInicioVuelo;
    }

    public Date getHoraFinVuelo() {
        return horaFinVuelo;
    }

    public void setHoraFinVuelo(Date horaFinVuelo) {
        this.horaFinVuelo = horaFinVuelo;
    }


}
