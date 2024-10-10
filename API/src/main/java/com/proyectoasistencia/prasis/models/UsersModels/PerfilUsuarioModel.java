package com.proyectoasistencia.prasis.models.UsersModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.sql.Date;

@Jacksonized
@Data
@SuperBuilder
public class PerfilUsuarioModel {

    @JsonProperty("User")
    private String user;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Documento")
    private String documento;

    @JsonProperty("TipoDocumento")
    private String tipoDocumento;

    @JsonProperty("Nombres")
    private String nombres;

    @JsonProperty("Apellidos")
    private String apellidos;

    @JsonProperty("FecNacimiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    @JsonProperty("Telefono")
    private String telefono;

    @JsonProperty("Correo")
    private String correo;

    @JsonProperty("Genero")
    private String genero;

    @JsonProperty("Residencia")
    private String residencia;

    @JsonProperty("Estado")
    private String estado;
}