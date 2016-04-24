package com.dfp.core.dto;

import com.dfp.persistencia.entities.Estado;

import java.util.Date;
import java.util.List;

/**
 * Created by Alberto on 30/12/2015.
 */
public class ReclamacionDTO {
	

	public PasajeroDTO getPasajero() {
		return pasajero;
	}

	public void setPasajero(PasajeroDTO pasajero) {
		this.pasajero = pasajero;
	}

	private String idvuelo="";
	
	private PasajeroDTO pasajero;

   

	public String getIdvuelo() {
		return idvuelo;
	}

	public void setIdvuelo(String idvuelo) {
		this.idvuelo = idvuelo;
	}

	private String codigoReclamacion = "";

    private String idPasajero = "";

    private List idCasosUso = null;

    private String textoReclamacion = "";

    private Date fechaReclamacion = null;

    private Date fechaVuelo = null;

    private List documentosAnexos = null;

    private EstadoDTO estadoDTO = null;

    private Double importeReclamacion=null;

    public Double getImporteReclamacion() {
        return importeReclamacion;
    }

    public void setImporteReclamacion(Double importeReclamacion) {
        this.importeReclamacion = importeReclamacion;
    }

    public EstadoDTO getEstadoDTO() {
        return estadoDTO;
    }

    public void setEstadoDTO(EstadoDTO estadoDTO) {
        this.estadoDTO = estadoDTO;
    }

    public List getDocumentosAnexos() {
        return documentosAnexos;
    }

    public void setDocumentosAnexos(List documentosAnexos) {
        this.documentosAnexos = documentosAnexos;
    }

    public Date getFechaVuelo() {
        return fechaVuelo;
    }

    public void setFechaVuelo(Date fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

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

    public List getIdCasosUso() {
        return idCasosUso;
    }

    public void setIdCasosUso(List idCasosUso) {
        this.idCasosUso = idCasosUso;
    }

    public String getTextoReclamacion() {
        return textoReclamacion;
    }

    public void setTextoReclamacion(String textoReclamacion) {
        this.textoReclamacion = textoReclamacion;
    }

    public Date getFechaReclamacion() {
        return fechaReclamacion;
    }

    public void setFechaReclamacion(Date fechaReclamacion) {
        this.fechaReclamacion = fechaReclamacion;
    }
}
