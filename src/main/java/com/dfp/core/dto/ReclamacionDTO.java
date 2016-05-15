package com.dfp.core.dto;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dfp.persistencia.entities.Reclamacion;

/**
 * Created by Alberto on 30/12/2015.
 */
public class ReclamacionDTO {
	
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm"); 

	private String codigoReclamacion = "";

    private String idPasajero = "";

    private List idCasosUso = null;

    private String textoReclamacion = "";

    private Date fechaReclamacion = null;

    private Date fechaVuelo = null;

    private List documentosAnexos = null;

    private EstadoDTO estadoDTO = null;

    private Double importeReclamacion=null;
    
    private VueloDTO vuelo =null;
    
	private Date horaInicioVueloPrevista = null;
	
	private Date horaFinVueloPrevista = null;
	
	private Date horaInicioVueloReal = null;
	
	private Date horaFinVueloReal = null;
	
	private String casoUso = "";
	
    
   
    
    public String getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(String casoUso) {
		this.casoUso = casoUso;
	}

	public Date getHoraInicioVueloPrevista() {
		return horaInicioVueloPrevista;
	}

	public void setHoraInicioVueloPrevista(String horaInicioVueloPrevista) {
		Date date;
		try {
			date = df.parse(horaInicioVueloPrevista);
			this.horaInicioVueloPrevista = date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public Date getHoraFinVueloPrevista() {
		return horaFinVueloPrevista;
	}

	public void setHoraFinVueloPrevista(String horaFinVueloPrevista) {
//		this.horaFinVueloPrevista = horaFinVueloPrevista;
		Date date;
		try {
			date = df.parse(horaFinVueloPrevista);
			this.horaFinVueloPrevista = date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public Date getHoraInicioVueloReal() {
		return horaInicioVueloReal;
	}

	public void setHoraInicioVueloReal(String horaInicioVueloReal) {
//		this.horaInicioVueloReal = horaInicioVueloReal;
		Date date;
		try {
			date = df.parse(horaInicioVueloReal);
			this.horaInicioVueloReal = date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public Date getHoraFinVueloReal() {
		return horaFinVueloReal;
	}

	public void setHoraFinVueloReal(String horaFinVueloReal) {
//		this.horaFinVueloReal = horaFinVueloReal;
		Date date;
		try {
			date = df.parse(horaFinVueloReal);
			this.horaFinVueloReal = date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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

	private String idvuelo="";
	
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
		try{
			pasajero.setApellidos(claim.getPasajero().getApellidos());
			pasajero.setNombre(claim.getPasajero().getNombre());
			pasajero.setIdDocumento(claim.getPasajero().getIdDocumento());
			pasajero.setEmail(claim.getPasajero().getEmail());
			pasajero.setTelefono(claim.getPasajero().getTelefono());
			pasajero.setTipoDocumento(claim.getPasajero().getTipoDocumento());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.setPasajero(pasajero);
		
		EstadoDTO estado = new EstadoDTO();
		try{
			estado.setSecEstado(claim.getEstado().getSecEstado());
			estado.setDescripcionEstado(claim.getEstado().getDescripcionEstado());
			estado.setNombreEstado(claim.getEstado().getNombreEstado());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.setEstadoDTO(estado);
		
		if (claim.getCodigoReclamacion()!=null)
			this.setCodigoReclamacion(claim.getCodigoReclamacion());
		
		if (claim.getTextoReclamacion()!=null)
			this.setTextoReclamacion(claim.getTextoReclamacion());
		
		if (claim.getHoraFinVueloPrevista()!=null)
			this.setHoraFinVueloPrevista(df.format(claim.getHoraFinVueloPrevista()));
		
		if (claim.getHoraInicioVueloPrevista()!=null)
			this.setHoraInicioVueloPrevista(df.format(claim.getHoraInicioVueloPrevista()));
		
		if (claim.getHoraFinVueloReal()!=null)
			this.setHoraFinVueloReal(df.format(claim.getHoraFinVueloReal()));
		
		if (claim.getHoraInicioVueloReal()!=null)
			this.setHoraInicioVueloReal(df.format(claim.getHoraInicioVueloReal()));
		
	}

	
}
