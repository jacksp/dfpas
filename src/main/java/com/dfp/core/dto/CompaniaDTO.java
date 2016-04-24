package com.dfp.core.dto;

/**
 * Created by Alberto on 30/12/2015.
 */
public class CompaniaDTO {

    private String nombreCompania = "";

    private String idNacion = "";

    private String codigo = "";

    private String email = "";

    public String getNombreCompania() {
        return nombreCompania;
    }

    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania = nombreCompania;
    }

    public String getIdNacion() {
        return idNacion;
    }

    public void setIdNacion(String idNacion) {
        this.idNacion = idNacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
