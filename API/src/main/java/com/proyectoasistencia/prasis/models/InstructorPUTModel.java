package com.proyectoasistencia.prasis.models;


import lombok.Getter;
import lombok.Setter;

public class InstructorPUTModel {
    private boolean CambiarDocumento;
    private boolean CambiarTipoDoc;
    private boolean CambiarNombres;
    private boolean CambiarApellidos;
    private boolean CambiarGenero;
    private boolean CambiarTelefono;
    private boolean CambiarArea;
    private boolean CambiarRol;
    private boolean CambiarSede;
    private int NuevoDocumento;
    private int NuevoTipoDoc;
    private String NuevosNombres;
    private String NuevosApellidos;
    private int NuevoGenero;
    private int NuevoTelefono;
    private String NuevoArea;
    private int NuevoRol;
    private int NuevaSede;

    public boolean isCambiarDocumento() {
        return CambiarDocumento;
    }

    public void setCambiarDocumento(boolean cambiarDocumento) {
        CambiarDocumento = cambiarDocumento;
    }

    public boolean isCambiarTipoDoc() {
        return CambiarTipoDoc;
    }

    public void setCambiarTipoDoc(boolean cambiarTipoDoc) {
        CambiarTipoDoc = cambiarTipoDoc;
    }

    public boolean isCambiarNombres() {
        return CambiarNombres;
    }

    public void setCambiarNombres(boolean cambiarNombres) {
        CambiarNombres = cambiarNombres;
    }

    public boolean isCambiarApellidos() {
        return CambiarApellidos;
    }

    public void setCambiarApellidos(boolean cambiarApellidos) {
        CambiarApellidos = cambiarApellidos;
    }

    public boolean isCambiarGenero() {
        return CambiarGenero;
    }

    public void setCambiarGenero(boolean cambiarGenero) {
        CambiarGenero = cambiarGenero;
    }

    public boolean isCambiarTelefono() {
        return CambiarTelefono;
    }

    public void setCambiarTelefono(boolean cambiarTelefono) {
        CambiarTelefono = cambiarTelefono;
    }

    public boolean isCambiarArea() {
        return CambiarArea;
    }

    public void setCambiarArea(boolean cambiarArea) {
        CambiarArea = cambiarArea;
    }

    public boolean isCambiarRol() {
        return CambiarRol;
    }

    public void setCambiarRol(boolean cambiarRol) {
        CambiarRol = cambiarRol;
    }

    public boolean isCambiarSede() {
        return CambiarSede;
    }

    public void setCambiarSede(boolean cambiarSede) {
        CambiarSede = cambiarSede;
    }

    public int getNuevoDocumento() {
        return NuevoDocumento;
    }

    public void setNuevoDocumento(int nuevoDocumento) {
        NuevoDocumento = nuevoDocumento;
    }

    public int getNuevoTipoDoc() {
        return NuevoTipoDoc;
    }

    public void setNuevoTipoDoc(int nuevoTipoDoc) {
        NuevoTipoDoc = nuevoTipoDoc;
    }

    public String getNuevosNombres() {
        return NuevosNombres;
    }

    public void setNuevosNombres(String nuevosNombres) {
        NuevosNombres = nuevosNombres;
    }

    public String getNuevosApellidos() {
        return NuevosApellidos;
    }

    public void setNuevosApellidos(String nuevosApellidos) {
        NuevosApellidos = nuevosApellidos;
    }

    public int getNuevoGenero() {
        return NuevoGenero;
    }

    public void setNuevoGenero(int nuevoGenero) {
        NuevoGenero = nuevoGenero;
    }

    public int getNuevoTelefono() {
        return NuevoTelefono;
    }

    public void setNuevoTelefono(int nuevoTelefono) {
        NuevoTelefono = nuevoTelefono;
    }

    public String getNuevoArea() {
        return NuevoArea;
    }

    public void setNuevoArea(String nuevoArea) {
        NuevoArea = nuevoArea;
    }

    public int getNuevoRol() {
        return NuevoRol;
    }

    public void setNuevoRol(int nuevoRol) {
        NuevoRol = nuevoRol;
    }

    public int getNuevaSede() {
        return NuevaSede;
    }

    public void setNuevaSede(int nuevaSede) {
        NuevaSede = nuevaSede;
    }
}
