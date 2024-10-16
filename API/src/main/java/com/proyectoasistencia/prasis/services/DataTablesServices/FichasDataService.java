package com.proyectoasistencia.prasis.services.DataTablesServices;

import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.JornadaFormacionDataService;
import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.ProgramaFormacionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class FichasDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProgramaFormacionDataService programaFormacionDataService;

    @Autowired
    private JornadaFormacionDataService jornadaFormacionDataService;

    // Obtener todas las fichas
    public List<Map<String, Object>> obtenerTodasLasFichas() {
        String sql = "SELECT f.ID, f.NumeroFicha FROM fichas f";
        return jdbcTemplate.queryForList(sql);
    }

    // Obtener ficha por número
    public Integer obtenerIdFichaPorNumero(Integer numeroFicha) {
        validarNumeroFicha(numeroFicha);
        String sql = "SELECT ID FROM fichas WHERE NumeroFicha = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{numeroFicha}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró la ficha con el número: " + numeroFicha);
        }
    }

    // Crear una nueva ficha
    public void crearFicha(Integer numeroFicha, String programaFormacion, String jornadaFormacion) {
        // Validaciones adicionales
        validarNumeroFicha(numeroFicha);
        validarCampo(programaFormacion, "ProgramaFormacion");
        validarCampo(jornadaFormacion, "JornadaFormacion");

        // Verificar si el número de ficha ya existe para evitar duplicados
        if (fichaExiste(numeroFicha)) {
            throw new IllegalArgumentException("Ya existe una ficha con el número: " + numeroFicha);
        }

        // Obtener IDs correspondientes desde otros servicios
        Integer idProgramaFormacion = programaFormacionDataService.getProgramaFormacionIdByValue(programaFormacion);
        if (idProgramaFormacion == null) {
            throw new IllegalArgumentException("El programa de formación '" + programaFormacion + "' no existe.");
        }

        Integer idJornadaFormacion = jornadaFormacionDataService.getJornadaIdByValue(jornadaFormacion);
        if (idJornadaFormacion == null) {
            throw new IllegalArgumentException("La jornada de formación '" + jornadaFormacion + "' no existe.");
        }

        String sql = "INSERT INTO fichas (NumeroFicha, IDProgramaFormacion, IDJornadaFormacion) VALUES (?, ?, ?)";

        try {
            jdbcTemplate.update(sql, numeroFicha, idProgramaFormacion, idJornadaFormacion);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Error de integridad al crear la ficha: " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la ficha: " + e.getMessage());
        }
    }

    // Actualizar ficha existente
    public void actualizarFicha(Integer id, Integer numeroFicha, String programaFormacion, String jornadaFormacion) {
        // Validaciones adicionales
        validarIdFicha(id);
        validarNumeroFicha(numeroFicha);
        validarCampo(programaFormacion, "ProgramaFormacion");
        validarCampo(jornadaFormacion, "JornadaFormacion");

        // Verificar si la ficha existe antes de actualizar
        if (!fichaExistePorId(id)) {
            throw new IllegalArgumentException("No se encontró la ficha con el ID: " + id);
        }

        // Verificar si el número de ficha ya está siendo usado por otra ficha
        Integer fichaIdExistente = obtenerIdFichaPorNumero(numeroFicha);
        if (fichaIdExistente != null && !fichaIdExistente.equals(id)) {
            throw new IllegalArgumentException("Ya existe otra ficha con el número: " + numeroFicha);
        }

        // Obtener IDs correspondientes desde otros servicios
        Integer idProgramaFormacion = programaFormacionDataService.getProgramaFormacionIdByValue(programaFormacion);
        if (idProgramaFormacion == null) {
            throw new IllegalArgumentException("El programa de formación '" + programaFormacion + "' no existe.");
        }

        Integer idJornadaFormacion = jornadaFormacionDataService.getJornadaIdByValue(jornadaFormacion);
        if (idJornadaFormacion == null) {
            throw new IllegalArgumentException("La jornada de formación '" + jornadaFormacion + "' no existe.");
        }

        String sql = "UPDATE fichas SET NumeroFicha = ?, IDProgramaFormacion = ?, IDJornadaFormacion = ? WHERE ID = ?";

        try {
            int rowsUpdated = jdbcTemplate.update(sql, numeroFicha, idProgramaFormacion, idJornadaFormacion, id);
            if (rowsUpdated == 0) {
                throw new IllegalArgumentException("No se pudo actualizar la ficha con el ID: " + id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Error de integridad al actualizar la ficha: " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la ficha: " + e.getMessage());
        }
    }

    // Eliminar ficha
    public void eliminarFicha(Integer id) {
        validarIdFicha(id);

        // Verificar si la ficha existe antes de eliminar
        if (!fichaExistePorId(id)) {
            throw new IllegalArgumentException("No se encontró la ficha con el ID: " + id);
        }

        String sql = "DELETE FROM fichas WHERE ID = ?";

        try {
            int rowsDeleted = jdbcTemplate.update(sql, id);
            if (rowsDeleted == 0) {
                throw new IllegalArgumentException("No se pudo eliminar la ficha con el ID: " + id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Error de integridad al eliminar la ficha: " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la ficha: " + e.getMessage());
        }
    }

    // Obtener los detalles del programa de formación por ficha
    public Map<String, Object> obtenerProgramaFormacionPorFicha(Integer numeroFicha) {
        validarNumeroFicha(numeroFicha);

        String sql = """
        SELECT 
            f.NumeroFicha,
            pf.ProgramaFormacion,
            jf.JornadasFormacion,
            nf.NivelFormacion,
            s.CentroFormacion AS Sede,
            a.Area
        FROM fichas f
        INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
        INNER JOIN jornadaformacion jf ON f.IDJornadaFormacion = jf.ID
        INNER JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
        INNER JOIN sede s ON pf.IDSede = s.ID
        INNER JOIN areas a ON pf.IDArea = a.ID
        WHERE f.NumeroFicha = ?
    """;

        try {
            return jdbcTemplate.queryForMap(sql, numeroFicha);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró un programa de formación asociado a la ficha: " + numeroFicha);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los detalles del programa de formación: " + e.getMessage());
        }
    }

    // Obtener todas las clases de formación con los detalles del instructor
    public List<Map<String, Object>> obtenerClasesConInstructor() {
        String sql = """
        SELECT cf.ID AS ClaseID, cf.NombreClase, pu.Nombres, pu.Apellidos, pu.Documento, pu.Correo
        FROM claseformacion cf
        INNER JOIN claseformacion_instructor_ficha cif ON cf.ID = cif.IDClaseFormacion
        INNER JOIN perfilusuario pu ON cif.IDPerfilUsuario = pu.ID
    """;

        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las clases con instructor: " + e.getMessage());
        }
    }

    // Obtener fichas asociadas al nombre del programa de formación
    public List<Map<String, Object>> obtenerFichasPorPrograma(String nombrePrograma) {
        validarCampo(nombrePrograma, "NombrePrograma");

        // Obtener el ID del programa de formación usando el nombre
        Integer idProgramaFormacion = programaFormacionDataService.obtenerIdProgramaFormacionPorNombre(nombrePrograma);
        if (idProgramaFormacion == null) {
            throw new IllegalArgumentException("No se encontró el programa de formación: " + nombrePrograma);
        }

        // Usar el ID para obtener las fichas asociadas
        String sql = "SELECT f.ID, f.NumeroFicha FROM fichas f WHERE f.IDProgramaFormacion = ?";
        try {
            return jdbcTemplate.queryForList(sql, idProgramaFormacion);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las fichas por programa de formación: " + e.getMessage());
        }
    }

    // Obtener fichas por documento del instructor
    public List<Map<String, Object>> obtenerFichasPorDocumentoInstructor(String documentoInstructor) {
        validarCampo(documentoInstructor, "DocumentoInstructor");

        String sql = """
        SELECT f.NumeroFicha 
        FROM claseformacion_instructor_ficha cif
        INNER JOIN perfilusuario pu ON cif.IDPerfilUsuario = pu.ID
        INNER JOIN fichas f ON cif.IDFicha = f.ID
        WHERE pu.Documento = ?
    """;

        try {
            List<Map<String, Object>> fichas = jdbcTemplate.queryForList(sql, documentoInstructor);
            if (fichas.isEmpty()) {
                throw new IllegalArgumentException("No se encontraron fichas para el instructor con documento: " + documentoInstructor);
            }
            return fichas;
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontraron fichas para el instructor con documento: " + documentoInstructor);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las fichas por documento del instructor: " + e.getMessage());
        }
    }

    // Métodos auxiliares de verificación de fichas
    private boolean fichaExiste(Integer numeroFicha) {
        String sql = "SELECT COUNT(*) FROM fichas WHERE NumeroFicha = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{numeroFicha}, Integer.class);
        return count != null && count > 0;
    }

    private boolean fichaExistePorId(Integer id) {
        String sql = "SELECT COUNT(*) FROM fichas WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    // Métodos de validación
    private void validarNumeroFicha(Integer numeroFicha) {
        if (numeroFicha == null || numeroFicha <= 0) {
            throw new IllegalArgumentException("El número de ficha debe ser un valor positivo y no nulo.");
        }
    }

    private void validarCampo(String campo, String nombreCampo) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' no puede estar vacío.");
        }
    }

    // Asociar una ficha con un instructor y una clase de formación
    public void asociarFichaInstructorClase(Integer idFicha, Integer idInstructor, Integer idClaseFormacion) {
        // Verificar si ya existe la asociación
        String verificarSql = "SELECT COUNT(*) FROM claseformacion_instructor_ficha WHERE IDClaseFormacion = ? AND IDFicha = ? AND IDPerfilUsuario = ?";
        Integer count = jdbcTemplate.queryForObject(verificarSql, new Object[]{idClaseFormacion, idFicha, idInstructor}, Integer.class);
        if (count != null && count > 0) {
            throw new IllegalArgumentException("La asociación ya existe.");
        }

        // Insertar la nueva asociación
        String insertarSql = "INSERT INTO claseformacion_instructor_ficha (IDClaseFormacion, IDFicha, IDPerfilUsuario) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertarSql, idClaseFormacion, idFicha, idInstructor);
    }

    // Editar asociación entre ficha, instructor y clase de formación
    public void editarAsociacionFichaInstructorClase(Integer idClaseAnterior, Integer idClaseNueva,
                                                     Integer idFichaAnterior, Integer idFichaNuevo,
                                                     Integer idInstructorAnterior, Integer idInstructorNuevo) {
        // Verificar si la nueva asociación ya existe
        String verificarSql = "SELECT COUNT(*) FROM claseformacion_instructor_ficha WHERE IDClaseFormacion = ? AND IDFicha = ? AND IDPerfilUsuario = ?";
        Integer count = jdbcTemplate.queryForObject(verificarSql, new Object[]{idClaseNueva, idFichaNuevo, idInstructorNuevo}, Integer.class);
        if (count != null && count > 0) {
            throw new IllegalArgumentException("La nueva asociación ya existe.");
        }

        // Actualizar la asociación
        String actualizarSql = """
        UPDATE claseformacion_instructor_ficha 
        SET IDClaseFormacion = ?, IDFicha = ?, IDPerfilUsuario = ? 
        WHERE IDClaseFormacion = ? AND IDFicha = ? AND IDPerfilUsuario = ?
    """;
        int rows = jdbcTemplate.update(actualizarSql, idClaseNueva, idFichaNuevo, idInstructorNuevo, idClaseAnterior, idFichaAnterior, idInstructorAnterior);
        if (rows == 0) {
            throw new IllegalArgumentException("Asociación original no encontrada.");
        }
    }

    // Eliminar una asociación existente
    public void eliminarAsociacionFichaInstructorClase(Integer idClaseFormacion, Integer idFicha, Integer idInstructor) {
        // Verificar si la asociación existe
        String verificarSql = "SELECT COUNT(*) FROM claseformacion_instructor_ficha WHERE IDClaseFormacion = ? AND IDFicha = ? AND IDPerfilUsuario = ?";
        Integer count = jdbcTemplate.queryForObject(verificarSql, new Object[]{idClaseFormacion, idFicha, idInstructor}, Integer.class);
        if (count == null || count == 0) {
            throw new IllegalArgumentException("Asociación no encontrada.");
        }

        // Eliminar la asociación
        String eliminarSql = "DELETE FROM claseformacion_instructor_ficha WHERE IDClaseFormacion = ? AND IDFicha = ? AND IDPerfilUsuario = ?";
        jdbcTemplate.update(eliminarSql, idClaseFormacion, idFicha, idInstructor);
    }

    // Obtener todas las fichas con detalles
    public List<Map<String, Object>> obtenerTodasLasFichasConDetalles() {
        String sql = """
        SELECT 
            f.ID,
            f.NumeroFicha,
            pf.ProgramaFormacion,
            jf.JornadasFormacion AS JornadaFormacion
        FROM fichas f
        INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
        INNER JOIN jornadaformacion jf ON f.IDJornadaFormacion = jf.ID
    """;

        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las fichas con detalles: " + e.getMessage());
        }
    }

    // Obtener todas las asociaciones
    public List<Map<String, Object>> obtenerAsociaciones() {
        String sql = """
        SELECT cf.NombreClase,
               CONCAT(pu.Nombres, ' ', pu.Apellidos) AS Instructor,
               f.NumeroFicha,
               jf.JornadasFormacion
        FROM claseformacion_instructor_ficha cif
        INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
        INNER JOIN jornadaformacion jf ON cf.IDJornadaFormacion = jf.ID
        INNER JOIN perfilusuario pu ON cif.IDPerfilUsuario = pu.ID
        INNER JOIN fichas f ON cif.IDFicha = f.ID
    """;
        return jdbcTemplate.queryForList(sql);
    }
    // Método para validar la existencia de una ficha por su número
    public void validarFicha(Integer numeroFicha) {
        // Validar que el número de ficha no sea nulo o menor o igual a 0
        if (numeroFicha == null || numeroFicha <= 0) {
            throw new IllegalArgumentException("El número de ficha debe ser un valor positivo y no nulo.");
        }

        // Consulta SQL para verificar si la ficha existe en la base de datos
        String sql = "SELECT COUNT(*) FROM fichas WHERE NumeroFicha = ?";

        try {
            Integer count = jdbcTemplate.queryForObject(sql, new Object[]{numeroFicha}, Integer.class);

            // Verificar si la ficha no existe
            if (count == null || count == 0) {
                throw new IllegalArgumentException("No se encontró una ficha con el número: " + numeroFicha);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al validar la ficha: " + e.getMessage());
        }
    }

    private void validarIdFicha(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la ficha debe ser un valor positivo y no nulo.");
        }
    }

}
