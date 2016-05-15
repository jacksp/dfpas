package com.dfp.persistencia.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dfp.core.dto.PasajeroDTO;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
@Table(name = "pasajero")
public class Pasajero {

	@Id // id de la entidad
	@GeneratedValue(strategy = GenerationType.IDENTITY) // el proovedor de
														// persistencia gnerar
														// un valor al
														// insertarlo y se lo da
	@Column(name = "id")
	private int id;

	@Column(name = "nombre")
	private String nombre = "";

	@Column(name = "apellidos")
	private String apellidos = "";

	@Column(name = "tipoDocumento")
	private String tipoDocumento = "";

	@Column(name = "idDocumento")
	private String idDocumento = "";

	@Column(name = "email")
	private String email = "";

	@Column(name = "facebook")
	private String facebook = "";

	@Column(name = "twitter")
	private String twitter = "";

	@Column(name = "telefono")
	private String telefono = "";

	public void populate(PasajeroDTO pasajeroDTO) {
		this.setNombre(pasajeroDTO.getNombre());
		this.setApellidos(pasajeroDTO.getApellidos());
		this.setEmail(pasajeroDTO.getEmail());
		this.setTelefono(pasajeroDTO.getTelefono());
	}
	
	
	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    //con el cascadetype.all se insertan tambi�n la entidad pasajero al insertar reclamaci�n
	@JoinColumn(name = "idVuelo", nullable = false)
	private Vuelo vuelo;
	
	
	public Vuelo getVuelo() {
		return vuelo;
	}
	
	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}

	
	public Set<Reclamacion> getReclamaciones() {
		return reclamaciones;
	}

	public void setReclamaciones(Set<Reclamacion> reclamaciones) {
		this.reclamaciones = reclamaciones;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pasajero")
	private Set<Reclamacion> reclamaciones = new HashSet<Reclamacion>(0);
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
