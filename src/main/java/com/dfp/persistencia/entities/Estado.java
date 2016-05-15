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

/**
 * Created by amfranco on 21/01/2016.
 */
@Entity
@Table(name = "estado")
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id = null;

	@Column(name = "secEstado")
	private Integer secEstado = null;

	@Column(name = "nombreEstado")
	private String nombreEstado = null;

	@Column(name = "descripcionEstado")
	private String descripcionEstado = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Reclamacion> getReclamaciones() {
		return reclamaciones;
	}

	public void setReclamaciones(Set<Reclamacion> reclamaciones) {
		this.reclamaciones = reclamaciones;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estado")
	private Set<Reclamacion> reclamaciones = new HashSet<Reclamacion>(0);

	public Integer getSecEstado() {
		return secEstado;
	}

	public void setSecEstado(Integer secEstado) {
		this.secEstado = secEstado;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
}
