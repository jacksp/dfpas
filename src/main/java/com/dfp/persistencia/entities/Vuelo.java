package com.dfp.persistencia.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
public class Vuelo {

    @Id //id de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)//el proovedor de persistencia gnerar un valor al insertarlo y se lo da
    private String id;

    private String codigoVuelo = "";

    private String idCodigoCompania = "";

    private Date horaInicioVuelo = null;

    private Date horaFinVuelo = null;

    @OneToOne(cascade=CascadeType.ALL)
    private Compania compania = null;
    
    @OneToOne(cascade=CascadeType.ALL)
    private Aeropuerto aeropuerto = null;
    

    // La entidad Pasajero referencia una colección de la Entidad Vehiculo
    // La entidad Pasajero es la Dueña de la relacion.
    @OneToMany(fetch = FetchType.EAGER,mappedBy="parentVuelo",cascade = CascadeType.PERSIST)
    private List<Pasajero> pasajeros;


    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Compania getCompania() {
        return compania;
    }

    public void setCompania(Compania compania) {
        this.compania = compania;
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
