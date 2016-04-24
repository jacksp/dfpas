package com.dfp.persistencia.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
public class Nacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    @OneToOne(mappedBy = "nacion")
    private DireccionPasajero direccionPasajero;




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
