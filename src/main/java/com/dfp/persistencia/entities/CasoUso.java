package com.dfp.persistencia.entities;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Alberto on 30/12/2015.
 */
@Entity
public class CasoUso {

    @Id //id de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)//el proovedor de persistencia gnerar un valor al insertarlo y se lo da
    private String id;

    private String idCasoUso = "";

    private String nombreCasoUso = "";

    private String formulaCalculo = "";

    private Reclamacion parent = null;

    // En la Entidad Reclamacion se referencia una instancia simple de la Entidad Reclamacion.
    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Reclamacion getParent() {
        return parent;
    }

    public void setParent(final Reclamacion parent) {
        this.parent = parent;
    }

    @ElementCollection
    private Map<String,String> parametrosCalculo = null;

    private String condicionesCaso = "";

    public String getIdCasoUso() {
        return idCasoUso;
    }

    public void setIdCasoUso(String idCasoUso) {
        this.idCasoUso = idCasoUso;
    }

    public String getNombreCasoUso() {
        return nombreCasoUso;
    }

    public void setNombreCasoUso(String nombreCasoUso) {
        this.nombreCasoUso = nombreCasoUso;
    }

    public String getFormulaCalculo() {
        return formulaCalculo;
    }

    public void setFormulaCalculo(String formulaCalculo) {
        this.formulaCalculo = formulaCalculo;
    }

    public Map getParametrosCalculo() {
        return parametrosCalculo;
    }

    public void setParametrosCalculo(Map parametrosCalculo) {
        this.parametrosCalculo = parametrosCalculo;
    }

    public String getCondicionesCaso() {
        return condicionesCaso;
    }

    public void setCondicionesCaso(String condicionesCaso) {
        this.condicionesCaso = condicionesCaso;
    }
}
