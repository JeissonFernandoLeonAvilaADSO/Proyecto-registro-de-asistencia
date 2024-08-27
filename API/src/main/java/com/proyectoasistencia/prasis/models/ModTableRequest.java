package com.proyectoasistencia.prasis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ModTableRequest {

    @JsonProperty("TablaAnterior")
    private Map<String, Object> TablaAnterior;

    @JsonProperty("TablaEntrante")
    private Map<String, Object> TablaEntrante;

    // Getters y Setters
    public Map<String, Object> getTablaAnterior() {
        return TablaAnterior;
    }

    public void setTablaAnterior(Map<String, Object> tablaAnterior) {
        System.out.println("Setting TablaAnterior: " + tablaAnterior);
        this.TablaAnterior = tablaAnterior;
    }

    public Map<String, Object> getTablaEntrante() {
        return TablaEntrante;
    }

    public void setTablaEntrante(Map<String, Object> tablaEntrante) {
        System.out.println("Setting TablaEntrante: " + tablaEntrante);
        this.TablaEntrante = tablaEntrante;
    }
}


