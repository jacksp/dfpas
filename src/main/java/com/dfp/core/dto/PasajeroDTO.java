package com.dfp.core.dto;

/**
 * Created by Alberto on 30/12/2015.
 */
public class PasajeroDTO {

    private String nombre = "";

    private String apellidos = "";

    private String idPasajero = "";

    private String tipoDocumento = "";

    public PasajeroDTO(String nombre, String apellidos, String email, String telefono) {
	this.nombre = nombre;
	this.apellidos = apellidos;
	this.email = email;
	this.telefono = telefono;
    }
    
    public PasajeroDTO(){
	
    }

    public String getIdDocumento() {
	return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
	this.idDocumento = idDocumento;
    }

    private String idDocumento = "";

    public String getTelefono() {
	return telefono;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    private String telefono = "";

    private String email = "";

    private String facebook = "";

    private String twitter = "";

    private String direccion = "";

    public String getIdPasajero() {
	return idPasajero;
    }

    public void setIdPasajero(String idPasajero) {
	this.idPasajero = idPasajero;
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

    public String getDireccion() {
	return direccion;
    }

    public void setDireccion(String direccion) {
	this.direccion = direccion;
    }

}
