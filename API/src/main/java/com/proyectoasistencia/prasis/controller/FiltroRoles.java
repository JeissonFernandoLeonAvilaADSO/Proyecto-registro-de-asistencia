package com.proyectoasistencia.prasis.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FiltroRoles {

    @RequestMapping(value = "Verificacion/RolInstructor/{IDUsuario}")
    public boolean VerificarRolInstructor(@PathVariable int IDUsuario){
        String consulta = "";
        return true;
    }

}
