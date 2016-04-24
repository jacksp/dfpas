package com.dfp.persistencia.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Alberto on 01/01/2016.
 */

@Entity
public class DireccionPasajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String calle="";

    private String numero="";

    private String puerta="";

    private String codigoPostal="";

    private String localidad="";

    private String provincia="";

    /*@OneToOne(mappedBy = "direccion")
    private Compania compania = null;*/

    @OneToOne(mappedBy = "direccionPasajero")
    private Pasajero pasajero = null;

    @OneToOne(cascade=CascadeType.ALL)
    private Nacion nacion = null;

    public Nacion getNacion() {
        return nacion;
    }

    public void setNacion(Nacion nacion) {
        this.nacion = nacion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
