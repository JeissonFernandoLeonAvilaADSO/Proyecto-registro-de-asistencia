package com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProgramaFormacionDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SedeDataService sedeDataService;

    @Autowired
    private JornadaFormacionDataService jornadaFormacionDataService;

    @Autowired
    private NivelFormacionDataService nivelFormacionDataService;

    @Autowired
    private AreaDataService areaDataService;

    // Obtener todos los programas de formación
    public List<Map<String, Object>> getAllProgramasFormacion() {
        String sql = """
                SELECT pf.ID, pf.ProgramaFormacion, jf.JornadasFormacion, nf.NivelFormacion, 
                       s.CentroFormacion, a.Area
                FROM programaformacion pf
                JOIN jornadaformacion jf ON pf.IDJornadaFormacion = jf.ID
                JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
                JOIN sede s ON pf.IDSede = s.ID
                JOIN areas a ON pf.IDArea = a.ID
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // Agregar un programa de formación
    public boolean createProgramaFormacion(Map<String, Object> programa) {
        // Obtener los IDs correspondientes desde otros servicios
        Integer sedeId = sedeDataService.getSedeIdByCentroFormacion((String) programa.get("CentroFormacion"));
        Integer jornadaId = jornadaFormacionDataService.getJornadaIdByValue((String) programa.get("JornadasFormacion"));
        Integer nivelId = nivelFormacionDataService.getNivelIdByValue((String) programa.get("NivelFormacion"));
        Integer areaId = areaDataService.getAreaIdByValue((String) programa.get("Area"));

        if (sedeId == null) {
            throw new IllegalArgumentException("el id de sede no es valido");
        }
        if (jornadaId == null){
            throw new IllegalArgumentException("el id de jornada no es valido");
        }
        if (nivelId == null){
            throw new IllegalArgumentException("el id de nivel no es valido");
        }
        if (areaId == null){
            throw new IllegalArgumentException("el area no es valido");
        }


        String sql = """
                INSERT INTO programaformacion 
                (ProgramaFormacion, IDJornadaFormacion, IDNivelFormacion, IDSede, IDArea) 
                VALUES (?, ?, ?, ?, ?)
                """;

        int rowsInserted = jdbcTemplate.update(sql,
                programa.get("ProgramaFormacion"),
                jornadaId,
                nivelId,
                sedeId,
                areaId);

        return rowsInserted > 0;
    }

    // Actualizar un programa de formación
    public boolean updateProgramaFormacion(int id, Map<String, Object> programa) {
        System.out.println(programa);
        // Obtener los IDs correspondientes desde otros servicios
        Integer sedeId = sedeDataService.getSedeIdByCentroFormacion((String) programa.get("CentroFormacion"));
        Integer jornadaId = jornadaFormacionDataService.getJornadaIdByValue((String) programa.get("JornadasFormacion"));
        Integer nivelId = nivelFormacionDataService.getNivelIdByValue((String) programa.get("NivelFormacion"));
        Integer areaId = areaDataService.getAreaIdByValue((String) programa.get("Area"));
        System.out.println(sedeId);
        System.out.println(jornadaId);
        System.out.println(nivelId);
        System.out.println(areaId);

        if (sedeId == null) {
            throw new IllegalArgumentException("el id de sede no es valido");
        }
        if (jornadaId == null){
            throw new IllegalArgumentException("el id de jornada no es valido");
        }
        if (nivelId == null){
            throw new IllegalArgumentException("el id de nivel no es valido");
        }
        if (areaId == null){
            throw new IllegalArgumentException("el area no es valido");
        }


        String sql = """
                UPDATE programaformacion 
                SET ProgramaFormacion = ?, IDJornadaFormacion = ?, IDNivelFormacion = ?, IDSede = ?, IDArea = ?
                WHERE ID = ?
                """;

        int updatedRows = jdbcTemplate.update(sql,
                programa.get("ProgramaFormacion"),
                jornadaId,
                nivelId,
                sedeId,
                areaId,
                id);

        return updatedRows > 0;
    }

    // Eliminar un programa de formación
    public boolean deleteProgramaFormacion(int id) {
        String sql = "DELETE FROM programaformacion WHERE ID = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }

    // Método para obtener un programa de formación por su ID
    public Map<String, Object> getProgramaFormacionById(int id) {
        String sql = """
                SELECT pf.ID, pf.ProgramaFormacion, jf.JornadasFormacion, nf.NivelFormacion, 
                       s.CentroFormacion, a.Area
                FROM programaformacion pf
                JOIN jornadaformacion jf ON pf.IDJornadaFormacion = jf.ID
                JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
                JOIN sede s ON pf.IDSede = s.ID
                JOIN areas a ON pf.IDArea = a.ID
                WHERE pf.ID = ?
                """;
        try {
            return jdbcTemplate.queryForMap(sql, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Método para obtener el ID del programa de formación por su valor
    public Integer getProgramaFormacionIdByValue(String programaFormacion) {
        String sql = """
                SELECT pf.ID 
                FROM programaformacion pf
                WHERE pf.ProgramaFormacion = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{programaFormacion}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró un programa de formación con el valor: " + programaFormacion);
            return null;
        }
    }

    public Integer obtenerIdProgramaFormacionPorNombre(String nombrePrograma) {
        String sql = "SELECT ID FROM programaformacion WHERE ProgramaFormacion = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{nombrePrograma}, Integer.class);
    }
}
