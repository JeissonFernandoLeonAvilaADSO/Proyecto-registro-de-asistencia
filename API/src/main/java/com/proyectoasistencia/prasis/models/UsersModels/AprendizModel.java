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

import java.util.List;
import java.util.Map;


@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AprendizModel extends PerfilUsuarioModel {

    @JsonProperty("Vinculaciones")
    private List<Map<String, Object>> vinculaciones;
}