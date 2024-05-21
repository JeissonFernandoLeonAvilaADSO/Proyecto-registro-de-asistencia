package com.proyectoasistencia.prasis.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class ConversionSubTablasAPI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "Conversion/TipoDoc_Str_to_ID/{TipoDocStr}")
    public ResponseEntity<Integer> TipoDocData_Str_to_ID(@PathVariable String TipoDocStr) {
        return obtenerID("tipodocumento", "TipoDocumento", TipoDocStr);
    }

    @RequestMapping(value = "Conversion/TipoGenero_Str_to_ID/{TipoGeneroStr}")
    public ResponseEntity<Integer> TipoGenero(@PathVariable String TipoGeneroStr){
        return obtenerID("genero", "TiposGeneros", TipoGeneroStr);
    }

    @RequestMapping(value = "Conversion/TipoRol_Str_to_ID/{TipoRolStr}")
    public ResponseEntity<Integer> TipoRol(@PathVariable String TipoRolStr){
        return obtenerID("rol", "TipoRol", TipoRolStr);
    }

    @RequestMapping(value = "Conversion/TipoSede_Str_to_ID/{TipoSedeStr}")
    public ResponseEntity<Integer> TipoSede(@PathVariable String TipoSedeStr){
        return obtenerID("sede", "CentroFormacion", TipoSedeStr);
    }

    @RequestMapping(value = "Conversion/ProgramaFormacion_Str_to_ID/{ProgramaFormacionStr}")
    public ResponseEntity<Integer> ProgramaFormacion(@PathVariable String ProgramaFormacionStr){
        return obtenerID("programaformacion", "ProgramaFormacion", ProgramaFormacionStr);
    }

    @RequestMapping(value = "Conversion/JornadaFormacion_Str_to_ID/{JornadaFormacionStr}")
    public ResponseEntity<Integer> JornadaFormacion(@PathVariable String JornadaFormacionStr){
        return obtenerID("jornadaformacion", "JornadasFormacion", JornadaFormacionStr);
    }

    @RequestMapping(value = "Conversion/NivelFormacion_Str_to_ID/{NivelFormacionStr}")
    public ResponseEntity<Integer> NivelFormacion(@PathVariable String NivelFormacionStr){
        return obtenerID("nivelformacion", "NivelFormacion", NivelFormacionStr);
    }

    private ResponseEntity<Integer> obtenerID(String tabla, String columna, String valor) {
        try {
            // Decodifica el valor del parámetro
            String decodedValor = URLDecoder.decode(valor, StandardCharsets.UTF_8.toString());

            String consulta = "select ID from " + tabla + " WHERE " + columna + " = ?";
            Integer ID = jdbcTemplate.queryForObject(consulta, new Object[]{decodedValor}, Integer.class);
            return new ResponseEntity<>(ID, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace(); // Loguea la excepción para depuración
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

