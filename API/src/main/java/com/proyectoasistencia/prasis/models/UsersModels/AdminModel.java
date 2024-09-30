package com.proyectoasistencia.prasis.models.UsersModels;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminModel {

    @JsonProperty("AdminUser")
    public String user;

    @JsonProperty("AdminPassword")
    public String password;
}

