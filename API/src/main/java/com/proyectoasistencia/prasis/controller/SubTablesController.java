package com.proyectoasistencia.prasis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SubTablesController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "TipoDocData")
    public List<String> getTiposDocumento(){
        String consulta = "SELECT TipoDocumento from tipodocumento";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("TipoDocumento");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "TipoActividadData")
    public List<String> getEstadosActividad(){
        String consulta = "SELECT TipoActividad from actividad";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("TipoActividad");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "EstadoAsistenciaData")
    public List<String> getEstadosAsistencia(){
        String consulta = "SELECT EstadoAsistencia from estadoasistencia";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("EstadoAsistencia");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "GeneroData")
    public List<String> getGeneros(){
        String consulta = "SELECT TiposGeneros from genero";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("TiposGeneros");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "JornadaFormacionData")
    public List<String> getjornadaformacion(){
        String consulta = "SELECT JornadasFormacion from jornadaformacion";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("JornadasFormacion");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "NivelFormacionData")
    public List<String> getnivelformacion(){
        String consulta = "SELECT NivelFormacion from nivelformacion";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("NivelFormacion");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "ProgramaFormacionData")
    public List<String> getprogramaformacion(){
        String consulta = "SELECT ProgramaFormacion from programaformacion";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("ProgramaFormacion");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "RolData")
    public List<String> getRol(){
        String consulta = "SELECT TipoRol from rol";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("TipoRol");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "SedeData")
    public List<String> getSede(){
        String consulta = "SELECT CentroFormacion from sede";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("CentroFormacion");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "AmbientesData")
    public List<String> getAmbientes(){
        String consulta = "SELECT Ambiente from ambientes";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("Ambiente");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "FichasData")
    public List<String> getFichas(){
        String consulta = "SELECT NumeroFicha from fichas";
        try {
            return jdbcTemplate.query(consulta, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("NumeroFicha");
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "Fichas-FormacionData")
    public List<Map<String, Object>> getFichasFormacion() {
        String consulta = """
                SELECT pf.ProgramaFormacion, NumeroFicha FROM fichas
                INNER JOIN programaformacion AS pf ON pf.ID = fichas.IDProgramaFormacion""";

        try {
            return jdbcTemplate.query(consulta, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> row = new HashMap<>();
                    row.put("ProgramaFormacion", rs.getString("ProgramaFormacion"));
                    row.put("NumeroFicha", rs.getString("NumeroFicha"));
                    return row;
                }
            });
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza de la excepción
            return new ArrayList<>(); // Retorna una lista vacía en caso de error
        }
    }
}
