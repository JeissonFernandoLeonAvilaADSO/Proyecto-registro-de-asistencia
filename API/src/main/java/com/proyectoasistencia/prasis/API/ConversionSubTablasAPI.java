package com.proyectoasistencia.prasis.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
public class ConversionSubTablasAPI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "Conversion/TipoDoc_Str_to_ID/{TipoDocStr}")
    public ResponseEntity<Integer> TipoDocData_Str_to_ID(@PathVariable String TipoDocStr) {
        try {
            String consulta = "select ID from tipodocumento WHERE TipoDocumento = ?";
            Integer IDTipoDoc = jdbcTemplate.queryForObject(consulta, new Object[]{TipoDocStr} ,Integer.class);
            return new ResponseEntity<>(IDTipoDoc, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "Conversion/TipoGenero_Str_to_ID/{TipoGeneroStr}")
    public ResponseEntity<Integer> TipoGenero(@PathVariable String TipoGeneroStr){
        try {
            String consulta = "select ID from genero WHERE TiposGeneros = ?";
            Integer IDTipoGenero = jdbcTemplate.queryForObject(consulta, new Object[]{TipoGeneroStr} ,Integer.class);
            return new ResponseEntity<>(IDTipoGenero, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "Conversion/TipoRol_Str_to_ID/{TipoRolStr}")
    public ResponseEntity<Integer> TipoRol(@PathVariable String TipoRolStr){
        try {
            String consulta = "select ID from rol WHERE TipoRol = ?";
            Integer IDTipoRol = jdbcTemplate.queryForObject(consulta, new Object[]{TipoRolStr} ,Integer.class);
            return new ResponseEntity<>(IDTipoRol, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "Conversion/TipoSede_Str_to_ID/{TipoSedeStr}")
    public ResponseEntity<Integer> TipoSede(@PathVariable String TipoSedeStr){
        try {
            String consulta = "select ID from sede WHERE CentroFormacion = ?";
            Integer IDTipoSede = jdbcTemplate.queryForObject(consulta, new Object[]{TipoSedeStr} ,Integer.class);
            return new ResponseEntity<>(IDTipoSede, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "Conversion/ProgramaFormacion_Str_to_ID/{ProgramaFormacionStr}")
    public ResponseEntity<Integer> ProgramaFormacion(@PathVariable String ProgramaFormacionStr){
        try {
            String consulta = "select ID from programaformacion WHERE ProgramaFormacion = ?";
            Integer IDProgramaFormacion = jdbcTemplate.queryForObject(consulta, new Object[]{ProgramaFormacionStr} ,Integer.class);
            return new ResponseEntity<>(IDProgramaFormacion, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "Conversion/JornadaFormacion_Str_to_ID/{JornadaFormacionStr}")
    public ResponseEntity<Integer> JornadaFormacion(@PathVariable String JornadaFormacionStr){
        try {
            String consulta = "select ID from jornadaformacion WHERE JornadasFormacion = ?";
            Integer IDJornadaFormacion = jdbcTemplate.queryForObject(consulta, new Object[]{JornadaFormacionStr} ,Integer.class);
            return new ResponseEntity<>(IDJornadaFormacion, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "Conversion/NivelFormacion_Str_to_ID/{NivelFormacionStr}")
    public ResponseEntity<Integer> NivelFormacion(@PathVariable String NivelFormacionStr){
        try {
            String consulta = "select ID from nivelformacion WHERE NivelFormacion = ?";
            Integer IDNivelFormacion = jdbcTemplate.queryForObject(consulta, new Object[]{NivelFormacionStr} ,Integer.class);
            return new ResponseEntity<>(IDNivelFormacion, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}

