package com.proyectoasistencia.prasis.services.DataTablesServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AprendicesDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método para obtener todos los aprendices relacionados con una ficha
    public List<Map<String, Object>> obtenerAprendicesPorFicha(Integer numeroFicha) {
        String sql = """
        SELECT DISTINCT pu.ID AS AprendizID, CONCAT(pu.Nombres, ' ', pu.Apellidos) AS NombreAprendiz, pu.Documento
        FROM aprendiz_claseinstructorficha acif
        INNER JOIN perfilusuario pu ON acif.IDPerfilUsuario = pu.ID
        INNER JOIN claseformacion_instructor_ficha cif ON acif.IDClaseInstructorFicha = cif.ID
        INNER JOIN fichas f ON cif.IDFicha = f.ID
        WHERE f.NumeroFicha = ?
    """;

        try {
            // Log para depuración
            System.out.println("Ejecutando consulta para obtener aprendices relacionados con la ficha: " + numeroFicha);

            // Ejecutar la consulta y obtener la lista de aprendices
            List<Map<String, Object>> aprendices = jdbcTemplate.queryForList(sql, numeroFicha);

            // Log para mostrar los resultados
            System.out.println("Aprendices obtenidos: " + aprendices);

            return aprendices;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontraron aprendices para la ficha: " + numeroFicha);
            return new ArrayList<>(); // Retorna una lista vacía si no se encuentran resultados

        } catch (Exception e) {
            // Imprimir la excepción y lanzar una nueva con un mensaje más claro
            e.printStackTrace();
            throw new RuntimeException("Error al obtener aprendices relacionados con la ficha: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para obtener el ID del aprendiz basado en su documento.
     *
     * @param documento Documento del aprendiz como String.
     * @return ID del aprendiz como int.
     * @throws IllegalArgumentException Si no se encuentra un aprendiz con el documento proporcionado.
     */
    public int obtenerIdAprendizPorDocumento(String documento) {
        // Definir la consulta SQL para obtener el ID del aprendiz por documento.
        String sql = """
            SELECT pu.ID 
            FROM perfilusuario pu
            WHERE pu.Documento = ?
        """;

        try {
            // Ejecutar la consulta y obtener el resultado como Integer.
            Integer idAprendiz = jdbcTemplate.queryForObject(sql, new Object[]{documento}, Integer.class);

            // Verificar que se ha obtenido un ID válido.
            if (idAprendiz == null) {
                throw new IllegalArgumentException("Aprendiz no encontrado con documento: " + documento);
            }

            return idAprendiz;
        } catch (EmptyResultDataAccessException e) {
            // Manejar el caso en que no se encuentra ningún aprendiz con el documento proporcionado.
            System.out.println("No se encontró el aprendiz con documento: " + documento);
            throw new IllegalArgumentException("Aprendiz no encontrado con documento: " + documento);
        } catch (DataAccessException e) {
            // Manejar otros errores relacionados con el acceso a datos.
            System.out.println("Error al acceder a la base de datos al buscar aprendiz con documento: " + documento);
            throw new RuntimeException("Error al acceder a la base de datos.", e);
        }
    }


}
