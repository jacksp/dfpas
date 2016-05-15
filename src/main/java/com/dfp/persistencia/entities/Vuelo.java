package com.dfp.persistencia.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dfp.core.dto.VueloDTO;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
@Table(name = "vuelo")
public class Vuelo {

    @Id //id de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)//el proovedor de persistencia gnerar un valor al insertarlo y se lo da
    @Column(name = "id")
    private int id;


	@Column(name = "codigoVuelo")
    private String codigoVuelo = "";

    @Column(name = "idCodigoCompania")
    private String idCodigoCompania = "";
    
    @Column(name = "aeropuertoOrigen")
    private String aeropuertoOrigen = "";
    
    @Column(name = "aeropuertoDestino")
    private String aeropuertoDestino = "";

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
//
//	@OneToOne(cascade=CascadeType.ALL)
//    private Compania compania = null;


	public Set<Pasajero> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(Set<Pasajero> pasajeros) {
		this.pasajeros = pasajeros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vuelo")
	private Set<Pasajero> pasajeros = new HashSet<Pasajero>(0);	

    

//    public Compania getCompania() {
//        return compania;
//    }
//
//    public void setCompania(Compania compania) {
//        this.compania = compania;
//    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
    
    
    public void populate(VueloDTO vueloDTO) {
		this.setCodigoVuelo(vueloDTO.getCodigoVuelo());
		this.setAeropuertoOrigen(vueloDTO.getAeropuertoOrigen());
		this.setAeropuertoDestino(vueloDTO.getAeropuertoDestino());	
    }

}
