package com.dfp.persistencia.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dfp.core.dto.ReclamacionDTO;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
@Table(name = "reclamacion")
public class Reclamacion {

	// propiedades
	@Id // id de la entidad
	@GeneratedValue(strategy = GenerationType.IDENTITY) // el proovedor de
														// persistencia gnerar
														// un valor al
														// insertarlo y se lo da
	@Column(name = "id")
	private Integer id;

	@Column(name = "codigoReclamacion")
	private String codigoReclamacion = "";

	@Column(name = "idPasajero")
	private String idPasajero = "";

	@Column(name = "textoReclamacion")
	private String textoReclamacion = "";

	@Column(name = "fechaReclamacion")
	private Date fechaReclamacion = null;

	@Column(name = "fechaVuelo")
	private Date fechaVuelo = null;

	@Column(name = "horaInicioVueloReclamacion")
	private Date horaInicioVueloReclamacion = null;

	@Column(name = "horaFinVueloReclamacion")
	private Date horaFinVueloReclamacion = null;

//	@OneToOne(cascade=CascadeType.ALL)
//	private Estado estado = null;
	
//	@Transient
//	private Double importeReclamacion = null;
//	
//	private Pasajero parentPasajero = null;

	// En la Entidad Vuelo se referencia una instancia simple de la Entidad
	// Pasajero.
//	@ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//	public Pasajero getParentPasajero() {
//		return parentPasajero;
//	}

//	public void setParentPasajero(final Pasajero parentPasajero) {
//		this.parentPasajero = parentPasajero;
//	}
//	
//	public void setIdCasosUso(List<String> idCasosUso) {
//		this.idCasosUso = idCasosUso;
//	}
//
//	public Double getImporteReclamacion() {
//		return importeReclamacion;
//	}
//
//	public void setImporteReclamacion(Double importeReclamacion) {
//		this.importeReclamacion = importeReclamacion;
//	}

	// geters y setters

	

//	public Estado getEstado() {
//		return estado;
//	}
//
//	public void setEstado(Estado estado) {
//		this.estado = estado;
//	}

	public Date getFechaReclamacion() {
		return fechaReclamacion;
	}

	public void setFechaReclamacion(Date fechaReclamacion) {
		this.fechaReclamacion = fechaReclamacion;
	}

	public Date getFechaVuelo() {
		return fechaVuelo;
	}

	public void setFechaVuelo(Date fechaVuelo) {
		this.fechaVuelo = fechaVuelo;
	}

	// private Vuelo parentVuelo = null;

	public Date getHoraInicioVueloReclamacion() {
		return horaInicioVueloReclamacion;
	}

	public void setHoraInicioVueloReclamacion(Date horaInicioVueloReclamacion) {
		this.horaInicioVueloReclamacion = horaInicioVueloReclamacion;
	}

	public Date getHoraFinVueloReclamacion() {
		return horaFinVueloReclamacion;
	}

	public void setHoraFinVueloReclamacion(Date horaFinVueloReclamacion) {
		this.horaFinVueloReclamacion = horaFinVueloReclamacion;
	}

//	@ElementCollection
//	private List<String> idCasosUso = null;

	// En la Entidad Vuelo se referencia una instancia simple de la Entidad
	// Pasajero.
	/*
	 * @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade =
	 * CascadeType.ALL) public Vuelo getParentVuelo() { return parentVuelo; }
	 * 
	 * public void setParentVuelo(final Vuelo parentVuelo) { this.parentVuelo =
	 * parentVuelo; }
	 */

	

	/*
	 * private Estado parentEstado = null;
	 * 
	 * // En la Entidad Vuelo se referencia una instancia simple de la Entidad
	 * Pasajero.
	 * 
	 * @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade =
	 * CascadeType.ALL) public Estado getParentEstado() { return parentEstado; }
	 * 
	 * public void setParentEstado(final Estado parentEstado) {
	 * this.parentEstado = parentEstado; }
	 */
//
//	@ElementCollection
//	private List<String> documentosAnexos = null;

	

//	public List getDocumentosAnexos() {
//		return documentosAnexos;
//	}
//
//	public void setDocumentosAnexos(List documentosAnexos) {
//		this.documentosAnexos = documentosAnexos;
//	}

	public String getCodigoReclamacion() {
		return codigoReclamacion;
	}

	public void setCodigoReclamacion(String codigoReclamacion) {
		this.codigoReclamacion = codigoReclamacion;
	}

	public String getIdPasajero() {
		return idPasajero;
	}

	public void setIdPasajero(String idPasajero) {
		this.idPasajero = idPasajero;
	}

//	public List getIdCasosUso() {
//		return idCasosUso;
//	}

	

	public String getTextoReclamacion() {
		return textoReclamacion;
	}

	public void setTextoReclamacion(String textoReclamacion) {
		this.textoReclamacion = textoReclamacion;
	}

	// populate
	public void populateFromReclamacionDTO(ReclamacionDTO reclamacion) {
		if (reclamacion.getCodigoReclamacion() != null)
			this.setCodigoReclamacion(reclamacion.getCodigoReclamacion());
		if (reclamacion.getFechaVuelo() != null)
			this.setFechaVuelo(reclamacion.getFechaVuelo());
		if (reclamacion.getTextoReclamacion() != null)
			this.setTextoReclamacion(reclamacion.getTextoReclamacion());
//		if (reclamacion.getDocumentosAnexos() != null)
//			this.setDocumentosAnexos(reclamacion.getDocumentosAnexos());


	}
}
