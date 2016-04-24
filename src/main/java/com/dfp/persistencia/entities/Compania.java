package com.dfp.persistencia.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
public class Compania {

    @Id //id de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)//el proovedor de persistencia gnerar un valor al insertarlo y se lo da
    private String id;

    private String nombreCompania = "";

    private String idNacion = "";

    private String codigo = "";

    private String email = "";

    @OneToOne(mappedBy = "compania")
    private Vuelo vuelo = null;

    public DireccionCompania getDireccionCompania() {
        return direccionCompania;
    }

    public void setDireccionCompania(DireccionCompania direccionCompania) {
        this.direccionCompania = direccionCompania;
    }

    @OneToOne(cascade=CascadeType.ALL)
    private DireccionCompania direccionCompania = null;



    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }


    public String getNombreCompania() {
        return nombreCompania;
    }

    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania = nombreCompania;
    }

    public String getIdNacion() {
        return idNacion;
    }

    public void setIdNacion(String idNacion) {
        this.idNacion = idNacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
