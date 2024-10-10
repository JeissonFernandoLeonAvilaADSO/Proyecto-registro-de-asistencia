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

    // Método para obtener todas las clases de formación con los detalles del instructor
    public List<Map<String, Object>> obtenerClasesConInstructor() {
        String sql = """
            SELECT cf.ID AS ClaseID, cf.NombreClase, pu.Nombres, pu.Apellidos, pu.Documento, pu.Correo
            FROM claseformacion cf
            INNER JOIN instructor i ON cf.IDInstructor = i.ID
            INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID
        """;

        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las clases con instructor: " + e.getMessage());
        }
    }

    // Método para obtener las fichas asociadas al nombre del programa de formación
    public List<Map<String, Object>> obtenerFichasPorPrograma(String nombrePrograma) {
        validarCampo(nombrePrograma, "NombrePrograma");

        // Primero, obtenemos el ID del programa de formación usando el nombre
        Integer idProgramaFormacion = programaFormacionDataService.obtenerIdProgramaFormacionPorNombre(nombrePrograma);
        if (idProgramaFormacion == null) {
            throw new IllegalArgumentException("No se encontró el programa de formación: " + nombrePrograma);
        }

        // Luego, usamos el ID para obtener las fichas asociadas
        String sql = "SELECT f.ID, f.NumeroFicha FROM fichas f WHERE f.IDProgramaFormacion = ?";
        try {
            return jdbcTemplate.queryForList(sql, idProgramaFormacion);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las fichas por programa de formación: " + e.getMessage());
        }
    }

    // Obtener todas las fichas por el documento del instructor
    public List<Map<String, Object>> obtenerFichasPorDocumentoInstructor(String documentoInstructor) {
        validarCampo(documentoInstructor, "DocumentoInstructor");

        String sql = """
            SELECT f.NumeroFicha 
            FROM instructor_ficha i_f
            INNER JOIN instructor i ON i_f.IDInstructor = i.ID
            INNER JOIN fichas f ON i_f.IDFicha = f.ID
            INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID
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

    // Métodos auxiliares para verificar la existencia de fichas
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

    private void validarIdFicha(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la ficha debe ser un valor positivo y no nulo.");
        }
    }

    private void validarCampo(String campo, String nombreCampo) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' no puede estar vacío.");
        }
    }
    /**
     * Asocia una ficha con una clase de formación.
     *
     * @param idClaseFormacion El ID de la clase de formación.
     * @param idFicha          El ID de la ficha.
     * @throws IllegalArgumentException Si la clase de formación o la ficha no existen, o si la asociación ya existe.
     */
    public void asociarFichaConClase(Integer idClaseFormacion, Integer idFicha) {
        // Verificar si la clase de formación existe
        verificarExistenciaClase(idClaseFormacion);
        verificarExistenciaFicha(idFicha);

        // Verificar si la asociación ya existe
        String sqlVerificarAsociacion = "SELECT COUNT(*) FROM claseformacion_fichas WHERE IDClaseFormacion = ? AND IDFicha = ?";
        Integer countAsociacion = jdbcTemplate.queryForObject(sqlVerificarAsociacion, new Object[]{idClaseFormacion, idFicha}, Integer.class);
        if (countAsociacion != null && countAsociacion > 0) {
            throw new IllegalArgumentException("La ficha con ID " + idFicha + " ya está asociada a la clase de formación con ID " + idClaseFormacion + ".");
        }

        // Insertar la asociación
        String sqlInsertarAsociacion = "INSERT INTO claseformacion_fichas (IDClaseFormacion, IDFicha) VALUES (?, ?)";
        jdbcTemplate.update(sqlInsertarAsociacion, idClaseFormacion, idFicha);
    }

    // Método para editar la asociación entre una ficha y una clase de formación
    public void editarAsociacionFichaAClase(Integer idClaseAnterior, Integer idClaseNueva, Integer idFichaAnterior, Integer idFichaNueva) {
        // Verificar si las clases y fichas anteriores y nuevas existen
        verificarExistenciaClase(idClaseAnterior);
        verificarExistenciaClase(idClaseNueva);
        verificarExistenciaFicha(idFichaAnterior);
        verificarExistenciaFicha(idFichaNueva);

        // Verificar si la nueva asociación ya existe
        String sqlVerificarAsociacion = "SELECT COUNT(*) FROM claseformacion_fichas WHERE IDClaseFormacion = ? AND IDFicha = ?";
        Integer countAsociacion = jdbcTemplate.queryForObject(sqlVerificarAsociacion, new Object[]{idClaseNueva, idFichaNueva}, Integer.class);
        if (countAsociacion != null && countAsociacion > 0) {
            throw new IllegalArgumentException("La ficha con ID " + idFichaNueva + " ya está asociada a la clase de formación con ID " + idClaseNueva + ".");
        }

        // Realizar la actualización
        String sql = "UPDATE claseformacion_fichas SET IDClaseFormacion = ?, IDFicha = ? WHERE IDClaseFormacion = ? AND IDFicha = ?";
        jdbcTemplate.update(sql, idClaseNueva, idFichaNueva, idClaseAnterior, idFichaAnterior);
    }

    // Método para eliminar la relación entre una ficha y una clase de formación
    public void eliminarAsociacionFichaAClase(Integer idClase, Integer idFicha) {
        // Verificar si la clase y la ficha existen
        verificarExistenciaClase(idClase);
        verificarExistenciaFicha(idFicha);

        // Verificar si la asociación existe antes de eliminar
        String sqlVerificarAsociacion = "SELECT COUNT(*) FROM claseformacion_fichas WHERE IDClaseFormacion = ? AND IDFicha = ?";
        Integer countAsociacion = jdbcTemplate.queryForObject(sqlVerificarAsociacion, new Object[]{idClase, idFicha}, Integer.class);
        if (countAsociacion == null || countAsociacion == 0) {
            throw new IllegalArgumentException("La ficha con ID " + idFicha + " no está asociada a la clase de formación con ID " + idClase + ".");
        }

        // Realizar la eliminación
        String sql = "DELETE FROM claseformacion_fichas WHERE IDClaseFormacion = ? AND IDFicha = ?";
        jdbcTemplate.update(sql, idClase, idFicha);
    }

    // Nuevo método para obtener todas las fichas con detalles
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


    // Método auxiliar para verificar la existencia de una clase
    private void verificarExistenciaClase(Integer idClase) {
        String sqlVerificarClase = "SELECT COUNT(*) FROM claseformacion WHERE ID = ?";
        Integer countClase = jdbcTemplate.queryForObject(sqlVerificarClase, new Object[]{idClase}, Integer.class);
        if (countClase == null || countClase == 0) {
            throw new IllegalArgumentException("La clase de formación con ID " + idClase + " no existe.");
        }
    }

    // Método auxiliar para verificar la existencia de una ficha
    private void verificarExistenciaFicha(Integer idFicha) {
        String sqlVerificarFicha = "SELECT COUNT(*) FROM fichas WHERE ID = ?";
        Integer countFicha = jdbcTemplate.queryForObject(sqlVerificarFicha, new Object[]{idFicha}, Integer.class);
        if (countFicha == null || countFicha == 0) {
            throw new IllegalArgumentException("La ficha con ID " + idFicha + " no existe.");
        }
    }
}
