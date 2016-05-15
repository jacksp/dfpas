package com.dfp.persistencia.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dfp.core.dto.ReclamacionDTO;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
@Table(name = "reclamacion")
public class Reclamacion {

	public Integer getId() {
		return id;
	}

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
		


	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "textoReclamacion")
	private String textoReclamacion = "";

	@Column(name = "fechaReclamacion")
	private Date fechaReclamacion = null;


	@Column(name = "horaInicioVueloPrevista")
	private Date horaInicioVueloPrevista = null;

	@Column(name = "horaFinVueloPrevista")
	private Date horaFinVueloPrevista = null;

	@Column(name = "horaInicioVueloReclamacion")
	private Date horaInicioVueloReal = null;

	

	@Column(name = "horaFinVueloReclamacion")
	private Date horaFinVueloReal = null;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEstado", nullable = false)
	private Estado estado;

	public Estado getEstado() {
		return this.estado;
	}
    
    public void setEstado(Estado estado) {
		this.estado = estado;
	}
    
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    //con el cascadetype.all se insertan tambi�n la entidad pasajero al insertar reclamaci�n
    @JoinColumn(name = "idPasajero", nullable = false)
	private Pasajero pasajero;


	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}
	






	public Date getFechaReclamacion() {
		return fechaReclamacion;
	}

	public void setFechaReclamacion(Date fechaReclamacion) {
		this.fechaReclamacion = fechaReclamacion;
	}




	public String getCodigoReclamacion() {
		return codigoReclamacion;
	}

	public void setCodigoReclamacion(String codigoReclamacion) {
		this.codigoReclamacion = codigoReclamacion;
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
		
		if (reclamacion.getTextoReclamacion() != null)
			this.setTextoReclamacion(reclamacion.getTextoReclamacion());
		
		if (reclamacion.getHoraFinVueloPrevista()!=null)
			this.setHoraFinVueloPrevista(reclamacion.getHoraFinVueloPrevista());
		
		if (reclamacion.getHoraInicioVueloPrevista()!=null)
			this.setHoraInicioVueloPrevista(reclamacion.getHoraInicioVueloPrevista());

		if (reclamacion.getHoraInicioVueloReal()!=null)
			this.setHoraInicioVueloReal(reclamacion.getHoraInicioVueloReal());

		if (reclamacion.getHoraFinVueloReal()!=null)
			this.setHoraFinVueloReal(reclamacion.getHoraFinVueloReal());

	}


	public Date getHoraInicioVueloPrevista() {
		return horaInicioVueloPrevista;
	}

	public void setHoraInicioVueloPrevista(Date horaInicioVueloPrevista) {
		this.horaInicioVueloPrevista = horaInicioVueloPrevista;
	}

	public Date getHoraFinVueloPrevista() {
		return horaFinVueloPrevista;
	}

	public void setHoraFinVueloPrevista(Date horaFinVueloPrevista) {
		this.horaFinVueloPrevista = horaFinVueloPrevista;
	}

	public Date getHoraInicioVueloReal() {
		return horaInicioVueloReal;
	}

	public void setHoraInicioVueloReal(Date horaInicioVueloReal) {
		this.horaInicioVueloReal = horaInicioVueloReal;
	}

	public Date getHoraFinVueloReal() {
		return horaFinVueloReal;
	}

	public void setHoraFinVueloReal(Date horaFinVueloReal) {
		this.horaFinVueloReal = horaFinVueloReal;
	}
}
