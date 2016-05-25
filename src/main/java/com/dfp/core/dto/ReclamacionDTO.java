package com.dfp.core.dto;

import java.util.Date;
import java.util.List;

import com.dfp.persistencia.entities.Reclamacion;

/**
 * Created by Alberto on 30/12/2015.
 */
public class ReclamacionDTO {


    private String codigoReclamacion = "";

    private String idPasajero = "";

    private List idCasosUso = null;

    private String textoReclamacion = "";

    private Date fechaReclamacion = null;

    private Date fechaVuelo = null;

    private List documentosAnexos = null;

    private EstadoDTO estadoDTO = null;

    private Double importeReclamacion = null;

    private VueloDTO vuelo = null;

    private Date horaInicioVueloPrevista = null;

    private Date horaFinVueloPrevista = null;

    private Date horaInicioVueloReal = null;

    private Date horaFinVueloReal = null;

    private String casoUso = "";

    private Integer id = 0;
    
    public ReclamacionDTO(){};

    public ReclamacionDTO(//String nombre, String apellidos, String email, String telefono
	    PasajeroDTO pasajeroDTO,VueloDTO vueloDTO, Date hsalidaPrevista, Date hsalidaReal,
	    Date hllegadaprevista, Date hllegadareal, String comentarios, String aceptarCondiciones, String codigoReclamacion) {
		this.horaFinVueloPrevista = hllegadaprevista;
		this.horaInicioVueloPrevista= hsalidaPrevista;
		this.horaInicioVueloReal=hsalidaReal;
		this.horaFinVueloReal=hllegadareal;
		this.textoReclamacion=comentarios;
		this.pasajero= pasajeroDTO;
		this.vuelo= vueloDTO;
		this.codigoReclamacion= codigoReclamacion;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getCasoUso() {
	return casoUso;
    }

    public void setCasoUso(String casoUso) {
	this.casoUso = casoUso;
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

    public VueloDTO getVuelo() {
	return vuelo;
    }

    public void setVuelo(VueloDTO vuelo) {
	this.vuelo = vuelo;
    }

    public PasajeroDTO getPasajero() {
	return pasajero;
    }

    public void setPasajero(PasajeroDTO pasajero) {
	this.pasajero = pasajero;
    }

    private String idvuelo = "";

    private PasajeroDTO pasajero;

    public String getIdvuelo() {
	return idvuelo;
    }

    public void setIdvuelo(String idvuelo) {
	this.idvuelo = idvuelo;
    }

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

    public void populateFromEntity(Reclamacion claim) {
	PasajeroDTO pasajero = new PasajeroDTO();
	try {
	    pasajero.setApellidos(claim.getPasajero().getApellidos());
	    pasajero.setNombre(claim.getPasajero().getNombre());
	    pasajero.setIdDocumento(claim.getPasajero().getIdDocumento());
	    pasajero.setEmail(claim.getPasajero().getEmail());
	    pasajero.setTelefono(claim.getPasajero().getTelefono());
	    pasajero.setTipoDocumento(claim.getPasajero().getTipoDocumento());
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}

	this.setPasajero(pasajero);

	EstadoDTO estado = new EstadoDTO();
	try {
	    estado.setSecEstado(claim.getEstado().getSecEstado());
	    estado.setDescripcionEstado(claim.getEstado().getDescripcionEstado());
	    estado.setNombreEstado(claim.getEstado().getNombreEstado());
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}

	this.setEstadoDTO(estado);

	if (claim.getCodigoReclamacion() != null)
	    this.setCodigoReclamacion(claim.getCodigoReclamacion());

	if (claim.getTextoReclamacion() != null)
	    this.setTextoReclamacion(claim.getTextoReclamacion());

	if (claim.getHoraFinVueloPrevista() != null)
	    this.setHoraFinVueloPrevista(claim.getHoraFinVueloPrevista());

	if (claim.getHoraInicioVueloPrevista() != null)
	    this.setHoraInicioVueloPrevista(claim.getHoraInicioVueloPrevista());

	if (claim.getHoraFinVueloReal() != null)
	    this.setHoraFinVueloReal(claim.getHoraFinVueloReal());

	if (claim.getHoraInicioVueloReal() != null)
	    this.setHoraInicioVueloReal(claim.getHoraInicioVueloReal());

	if (claim.getId() != null)
	    this.setId(claim.getId());

    }

}
