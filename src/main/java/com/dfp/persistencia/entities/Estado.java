package com.dfp.persistencia.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by amfranco on 21/01/2016.
 */
@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Integer secEstado = 0;

    private String nombreEstado = "";

    private String descripcionEstado = "";
    
    @OneToOne(mappedBy = "estado")
    private Reclamacion reclamacion = null;


    public Reclamacion getReclamacion() {
		return reclamacion;
	}

	public void setReclamacion(Reclamacion reclamacion) {
		this.reclamacion = reclamacion;
	}

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
