package com.dfp.core.dto;

/**
 * Created by amfranco on 03/02/2016.
 */
public class EstadoDTO {

    private Integer secEstado = 0;

    private String nombreEstado = "";

    private String descripcionEstado = "";

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
