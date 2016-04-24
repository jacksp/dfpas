package com.dfp.persistencia.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
public class Pasajero {


    @Id //id de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)//el proovedor de persistencia gnerar un valor al insertarlo y se lo da
    private String id;

    private String nombre = "";

    private String apellidos = "";

    private String idPasajero = "";

    private String tipoDocumento = "";

    private String email = "";

    private String facebook = "";

    private String twitter = "";

    @OneToOne(cascade=CascadeType.ALL)
    private DireccionPasajero direccionPasajero = null;

    @OneToMany(fetch = FetchType.EAGER,mappedBy="parentPasajero",cascade = CascadeType.PERSIST)
    private List<Reclamacion> reclamacion = null;

    public List<Reclamacion> getReclamacion() {
        return reclamacion;
    }

    public void setReclamacion(List<Reclamacion> reclamacion) {
        this.reclamacion = reclamacion;
    }

    //@ManyToOne
    private Vuelo parentVuelo = null;

    // En la Entidad Vuelo se referencia una instancia simple de la Entidad Pasajero.
    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade =CascadeType.PERSIST)
    public Vuelo getParentVuelo() {
        return parentVuelo;
    }

    public void setParentVuelo(final Vuelo parent) {
        this.parentVuelo = parent;
    }

   /* private Vuelo parent = null;*/

    public DireccionPasajero getDireccionPasajero() {
        return direccionPasajero;
    }

    public void setDireccionPasajero(DireccionPasajero direccionPasajero) {
        this.direccionPasajero = direccionPasajero;
    }

    public String getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(String idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

}
