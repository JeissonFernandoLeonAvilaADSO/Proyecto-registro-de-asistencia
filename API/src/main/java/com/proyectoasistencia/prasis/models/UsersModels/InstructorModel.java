package com.proyectoasistencia.prasis.models.UsersModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class InstructorModel extends PerfilUsuarioModel {

    @JsonProperty("Fichas")
    private List<Integer> fichas;

    @JsonProperty("ProgramasFormacion")
    private List<String> programasFormacion;

    @JsonProperty("JornadasFormacion")
    private List<String> jornadasFormacion;

    @JsonProperty("NivelesFormacion")
    private List<String> nivelesFormacion;

    @JsonProperty("CentrosFormacion")
    private List<String> sedes;

    @JsonProperty("Areas")
    private List<String> areas;
}
