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

    // Se elimina la inyección de JornadaFormacionDataService ya que la columna se ha movido a fichas
    // @Autowired
    // private JornadaFormacionDataService jornadaFormacionDataService;

    @Autowired
    private NivelFormacionDataService nivelFormacionDataService;

    @Autowired
    private AreaDataService areaDataService;

    // Obtener todos los programas de formación
    public List<Map<String, Object>> getAllProgramasFormacion() {
        String sql = """
                SELECT pf.ID, pf.ProgramaFormacion, nf.NivelFormacion, 
                       s.CentroFormacion, a.Area
                FROM programaformacion pf
                JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
                JOIN sede s ON pf.IDSede = s.ID
                JOIN areas a ON pf.IDArea = a.ID
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // Agregar un programa de formación
    public boolean createProgramaFormacion(Map<String, Object> programa) {
        // Verificar que los campos obligatorios no estén vacíos
        if (isNullOrEmpty(programa.get("ProgramaFormacion"))) {
            throw new IllegalArgumentException("El campo 'ProgramaFormacion' no puede estar vacío.");
        }
        if (isNullOrEmpty(programa.get("NivelFormacion"))) {
            throw new IllegalArgumentException("El campo 'NivelFormacion' no puede estar vacío.");
        }
        if (isNullOrEmpty(programa.get("CentroFormacion"))) {
            throw new IllegalArgumentException("El campo 'CentroFormacion' no puede estar vacío.");
        }
        if (isNullOrEmpty(programa.get("Area"))) {
            throw new IllegalArgumentException("El campo 'Area' no puede estar vacío.");
        }

        // Obtener los IDs correspondientes desde otros servicios
        Integer sedeId = sedeDataService.getSedeIdByCentroFormacion((String) programa.get("CentroFormacion"));
        // Integer jornadaId = jornadaFormacionDataService.getJornadaIdByValue((String) programa.get("JornadasFormacion")); // Eliminado
        Integer nivelId = nivelFormacionDataService.getNivelIdByValue((String) programa.get("NivelFormacion"));
        Integer areaId = areaDataService.getAreaIdByValue((String) programa.get("Area"));

        if (sedeId == null) {
            throw new IllegalArgumentException("El ID de sede no es válido.");
        }
        // if (jornadaId == null){
        //     throw new IllegalArgumentException("El ID de jornada no es válido.");
        // }
        if (nivelId == null){
            throw new IllegalArgumentException("El ID de nivel no es válido.");
        }
        if (areaId == null){
            throw new IllegalArgumentException("El área no es válida.");
        }

        String sql = """
                INSERT INTO programaformacion 
                (ProgramaFormacion, IDNivelFormacion, IDSede, IDArea) 
                VALUES (?, ?, ?, ?)
                """;

        int rowsInserted = jdbcTemplate.update(sql,
                programa.get("ProgramaFormacion"),
                nivelId,
                sedeId,
                areaId);

        return rowsInserted > 0;
    }

    // Actualizar un programa de formación
    public boolean updateProgramaFormacion(int id, Map<String, Object> programa) {
        System.out.println(programa);

        // Verificar que los campos obligatorios no estén vacíos
        if (isNullOrEmpty(programa.get("ProgramaFormacion"))) {
            throw new IllegalArgumentException("El campo 'ProgramaFormacion' no puede estar vacío.");
        }
        if (isNullOrEmpty(programa.get("NivelFormacion"))) {
            throw new IllegalArgumentException("El campo 'NivelFormacion' no puede estar vacío.");
        }
        if (isNullOrEmpty(programa.get("CentroFormacion"))) {
            throw new IllegalArgumentException("El campo 'CentroFormacion' no puede estar vacío.");
        }
        if (isNullOrEmpty(programa.get("Area"))) {
            throw new IllegalArgumentException("El campo 'Area' no puede estar vacío.");
        }

        // Obtener los IDs correspondientes desde otros servicios
        Integer sedeId = sedeDataService.getSedeIdByCentroFormacion((String) programa.get("CentroFormacion"));
        // Integer jornadaId = jornadaFormacionDataService.getJornadaIdByValue((String) programa.get("JornadasFormacion")); // Eliminado
        Integer nivelId = nivelFormacionDataService.getNivelIdByValue((String) programa.get("NivelFormacion"));
        Integer areaId = areaDataService.getAreaIdByValue((String) programa.get("Area"));
        System.out.println(sedeId);
        // System.out.println(jornadaId); // Eliminado
        System.out.println(nivelId);
        System.out.println(areaId);

        if (sedeId == null) {
            throw new IllegalArgumentException("El ID de sede no es válido.");
        }
        // if (jornadaId == null){
        //     throw new IllegalArgumentException("El ID de jornada no es válido.");
        // }
        if (nivelId == null){
            throw new IllegalArgumentException("El ID de nivel no es válido.");
        }
        if (areaId == null){
            throw new IllegalArgumentException("El área no es válida.");
        }

        String sql = """
                UPDATE programaformacion 
                SET ProgramaFormacion = ?, IDNivelFormacion = ?, IDSede = ?, IDArea = ?
                WHERE ID = ?
                """;

        int updatedRows = jdbcTemplate.update(sql,
                programa.get("ProgramaFormacion"),
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
                SELECT pf.ID, pf.ProgramaFormacion, nf.NivelFormacion, 
                       s.CentroFormacion, a.Area
                FROM programaformacion pf
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

    // Método auxiliar para verificar si un objeto está vacío o es nulo
    private boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        // Puedes añadir más condiciones si necesitas verificar otros tipos de datos
        return false;
    }
}
