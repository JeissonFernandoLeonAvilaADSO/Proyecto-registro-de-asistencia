package com.proyectoasistencia.prasis.models.UsersModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Jacksonized
@Data
@SuperBuilder
public class AprendizRequest {

    private AprendizModel aprendiz;
    private List<Integer> fichas;

}
