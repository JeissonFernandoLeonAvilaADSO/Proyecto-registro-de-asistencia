package com.proyectoasistencia.prasis.models.UsersModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AprendizModel extends PerfilUsuarioModel {

    @JsonProperty("NumeroFicha")
    private Integer ficha;

    @JsonProperty("ProgramaFormacion")
    @Size(max = 100, message = "El programa de formación no debe exceder los 100 caracteres")
    private String programaFormacion;

    @JsonProperty("JornadaFormacion")
    private String jornadaFormacion;

    @JsonProperty("NivelFormacion")
    private String nivelFormacion;

    @JsonProperty("Sede")
    private String sede;

    @JsonProperty("Area")
    private String area;
}