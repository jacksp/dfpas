package com.dfp.core.dto;

import java.util.Map;

/**
 * Created by Alberto on 30/12/2015.
 */
public class CasoUsoDTO {

    private String idCasoUso = "";

    private String nombreCasoUso = "";

    private String formulaCalculo = "";

    private Map parametrosCalculo = null;

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
