package com.proyectoasistencia.prasis.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.jdbc.core.JdbcTemplate;

@RestController
public class ConversionSubTablasAPI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "Conversion/TipoDoc_Str_to_ID/{TipoDocStr}")
    public Integer TipoDocData_Str_to_ID(@PathVariable String TipoDocStr) {
        String consulta = "select ID from tipodocumento WHERE TipoDocumento = ?";
        Integer IDTipoDoc = jdbcTemplate.queryForObject(consulta, new Object[]{TipoDocStr} ,Integer.class);
        return IDTipoDoc;
    }

    @RequestMapping(value = "Conversion/TipoGenero_Str_to_ID/{TipoGeneroStr}")
    public Integer TipoGenero(@PathVariable String TipoGeneroStr){
        String consulta = "select ID from genero WHERE TiposGeneros = ?";
        Integer IDTipoGenero = jdbcTemplate.queryForObject(consulta, new Object[]{TipoGeneroStr} ,Integer.class);
        return IDTipoGenero;

    }

    @RequestMapping(value = "Conversion/TipoRol_Str_to_ID/{TipoRolStr}")
    public Integer TipoRol(@PathVariable String TipoRolStr){
        String consulta = "select ID from rol WHERE TipoRol = ?";
        Integer IDTipoRol = jdbcTemplate.queryForObject(consulta, new Object[]{TipoRolStr} ,Integer.class);
        return IDTipoRol;

    }

    @RequestMapping(value = "Conversion/TipoSede_Str_to_ID/{TipoSedeStr}")
    public Integer TipoSede(@PathVariable String TipoSedeStr){
        String consulta = "select ID from sede WHERE CentroFormacion = ?";
        Integer IDTipoSede = jdbcTemplate.queryForObject(consulta, new Object[]{TipoSedeStr} ,Integer.class);
        return IDTipoSede;
    }

}
