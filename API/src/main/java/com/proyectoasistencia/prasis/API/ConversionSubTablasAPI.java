package com.proyectoasistencia.prasis.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class ConversionSubTablasAPI {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ConversionSubTablasAPI(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


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

    @RequestMapping(value = "Conversion/Ambiente_Str_to_ID/{Ambiente}")
    public ResponseEntity<Integer> Ambiente(@PathVariable String Ambiente){
        return obtenerID("ambientes", "Ambiente", Ambiente);
    }

    @RequestMapping(value = "Conversion/Ficha_Int_to_ID/{fichaInt}")
    public ResponseEntity<Integer> FichaToID(@PathVariable Integer fichaInt){
        return obtenerIDPorFicha(fichaInt);
    }

    private ResponseEntity<Integer> obtenerID(String tabla, String columna, String valor) {
        try {

            // Decodifica el valor recibido
            String decodedValor = URLDecoder.decode(valor, StandardCharsets.UTF_8.toString());
            // Reemplaza el carácter especial por espacios
            String tipoStr = decodedValor.replace("_", " ");
            // Realiza la consulta SQL con el valor restaurado
            String consulta = "SELECT ID FROM " + tabla + " WHERE " + columna + " = ?";
            Integer ID = jdbcTemplate.queryForObject(consulta, new Object[]{tipoStr}, Integer.class);

            return new ResponseEntity<>(ID, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Integer> obtenerIDPorFicha(Integer ficha) {
        try {
            // Realiza la consulta SQL con el valor de la ficha
            String consulta = "SELECT ID FROM fichas WHERE NumeroFicha = ?";
            Integer ID = jdbcTemplate.queryForObject(consulta, new Object[]{ficha}, Integer.class);
            System.out.println(ID);
            return new ResponseEntity<>(ID, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "Conversion/FichasPorPrograma/{idPrograma}")
    public ResponseEntity<List<Integer>> obtenerFichasPorPrograma(@PathVariable Integer idPrograma) {
        try {
            List<Integer> fichas = obtenerFichasPorProgramaFromDB(idPrograma);
            if (fichas.isEmpty()) {
                return new ResponseEntity<>(fichas, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fichas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Integer> obtenerFichasPorProgramaFromDB(Integer idPrograma) {
        String consulta = """
                            SELECT NumeroFicha FROM fichas
                                INNER JOIN db_proyecto_asistencia.programaformacion p on fichas.IDProgramaFormacion = p.ID
                                    WHERE IDProgramaFormacion = ?""";
        return jdbcTemplate.query(consulta, new Object[]{idPrograma}, (rs, rowNum) -> rs.getInt("NumeroFicha"));
    }
}

