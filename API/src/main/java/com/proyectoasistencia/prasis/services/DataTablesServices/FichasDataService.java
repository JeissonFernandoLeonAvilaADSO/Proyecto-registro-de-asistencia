package com.proyectoasistencia.prasis.services.DataTablesServices;

import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.ProgramaFormacionDataService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Obtener todas las fichas
    public List<Map<String, Object>> obtenerTodasLasFichas() {
        String sql = "SELECT f.ID, f.NumeroFicha FROM fichas f ";
        return jdbcTemplate.queryForList(sql);
    }

    // Obtener ficha por número
    public Integer obtenerIdFichaPorNumero(Integer numeroFicha) {
        String sql = "SELECT ID FROM fichas WHERE NumeroFicha = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{numeroFicha}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró la ficha con el número: " + numeroFicha);
        }
    }

    // Crear una nueva ficha
    public void crearFicha(Integer numeroFicha, String programaFormacion) {
        Integer idProgramaFormacion = programaFormacionDataService.getProgramaFormacionIdByValue(programaFormacion);
        String sql = "INSERT INTO fichas (NumeroFicha, IDProgramaFormacion) VALUES (?, ?)";
        jdbcTemplate.update(sql, numeroFicha, idProgramaFormacion);
    }

    // Actualizar ficha existente
    public void actualizarFicha(Integer id, Integer numeroFicha, String programaFormacion) {
        Integer idProgramaFormacion = programaFormacionDataService.getProgramaFormacionIdByValue(programaFormacion);
        String sql = "UPDATE fichas SET NumeroFicha = ?, IDProgramaFormacion = ? WHERE ID = ?";
        jdbcTemplate.update(sql, numeroFicha, idProgramaFormacion, id);
    }

    // Eliminar ficha
    public void eliminarFicha(Integer id) {
        String sql = "DELETE FROM fichas WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }

    // Obtener los detalles del programa de formación por ficha
    public Map<String, Object> obtenerProgramaFormacionPorFicha(Integer numeroFicha) {
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
        INNER JOIN jornadaformacion jf ON pf.IDJornadaFormacion = jf.ID
        INNER JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
        INNER JOIN sede s ON pf.IDSede = s.ID
        INNER JOIN areas a ON pf.IDArea = a.ID
        WHERE f.NumeroFicha = ?
    """;

        try {
            return jdbcTemplate.queryForMap(sql, numeroFicha);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró un programa de formación asociado a la ficha: " + numeroFicha);
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

        return jdbcTemplate.queryForList(sql);
    }

    // Método para obtener las fichas asociadas al ID del programa de formación
    public List<Map<String, Object>> obtenerFichasPorPrograma(String nombrePrograma) {
        // Primero, obtenemos el ID del programa de formación usando el nombre
        Integer idProgramaFormacion = programaFormacionDataService.obtenerIdProgramaFormacionPorNombre(nombrePrograma);

        // Luego, usamos el ID para obtener las fichas asociadas
        String sql = "SELECT f.ID, f.NumeroFicha FROM fichas f WHERE f.IDProgramaFormacion = ?";
        return jdbcTemplate.queryForList(sql, idProgramaFormacion);
    }
}
