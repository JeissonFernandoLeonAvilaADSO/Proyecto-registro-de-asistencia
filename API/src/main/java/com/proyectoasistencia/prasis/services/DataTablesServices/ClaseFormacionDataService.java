package com.proyectoasistencia.prasis.services.DataTablesServices;

import com.proyectoasistencia.prasis.services.UsersServices.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClaseFormacionDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener todas las clases de formación con sus instructores
    public List<Map<String, Object>> obtenerClasesConInstructor() {
        String sql = "SELECT c.ID AS IDClase, c.NombreClase AS NombreClase, c.ID AS IDClase ,pu.Documento AS DocumentoInstructor, CONCAT(pu.Nombres, ' ', pu.Apellidos) AS NombreInstructor, pu.Correo AS CorreoInstructor " +
                "FROM ClaseFormacion c " +
                "INNER JOIN instructor i ON c.IDInstructor = i.ID " +
                "INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID";

        System.out.println("Ejecutando consulta para obtener clases con instructor: " + sql);

        List<Map<String, Object>> clasesConInstructor = jdbcTemplate.queryForList(sql);
        System.out.println("Clases con instructor obtenidas: " + clasesConInstructor);

        return clasesConInstructor;  // Asegúrate de que siempre se retorne una lista válida (nunca null)
    }


//    // Obtener instructores con sus clases para la ComboBox
//    public List<Map<String, Object>> obtenerInstructoresParaComboBox() {
//        String sql = "SELECT i.ID, pu.Documento AS DocumentoInstructor, " +
//                "cf.ID AS IDClase," +
//                "CONCAT(pu.Nombres, ' ', pu.Apellidos) AS NombreInstructor, " +
//                "pu.Correo AS CorreoInstructor, " +
//                "cf.NombreClase " +  // Añadir el NombreClase
//                "FROM instructor i " +
//                "INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID " +
//                "INNER JOIN ClaseFormacion cf ON cf.IDInstructor = i.ID";  // Asegura todos los instructores
//
//        System.out.println("Ejecutando consulta para obtener instructores para la ComboBox: " + sql);
//
//        // Ejecutar la consulta y obtener los resultados
//        List<Map<String, Object>> instructores = jdbcTemplate.queryForList(sql);
//
//        // Imprimir los resultados para depuración
//        System.out.println("Instructores obtenidos: " + instructores);
//
//        return instructores;  // Asegúrate de que nunca retorne null
//    }

    // Crear una nueva clase de formación
    public void crearClase(String nombreClase, String documentoInstructor) {
        String sql = "INSERT INTO claseformacion (NombreClase, IDInstructor) " +
                "VALUES (?, (SELECT i.ID FROM instructor i INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID WHERE pu.Documento = ?))";

        System.out.println("Ejecutando consulta para crear clase: " + sql + ", Nombre: " + nombreClase + ", Documento Instructor: " + documentoInstructor);
        jdbcTemplate.update(sql, nombreClase, documentoInstructor);
        System.out.println("Clase creada.");
    }

    // Actualizar una clase de formación existente
    public void actualizarClase(Integer id, String nombreClase, String documentoInstructor) {
        String sql = "UPDATE claseformacion " +
                "SET NombreClase = ?, IDInstructor = (SELECT i.ID FROM instructor i INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID WHERE pu.Documento = ?) " +
                "WHERE ID = ?";

        System.out.println("Ejecutando consulta para actualizar clase: " + sql + ", ID: " + id + ", Nombre: " + nombreClase + ", Documento Instructor: " + documentoInstructor);
        jdbcTemplate.update(sql, nombreClase, documentoInstructor, id);
        System.out.println("Clase actualizada.");
    }

    // Eliminar una clase de formación
    public void eliminarClase(Integer id) {
        String sql = "DELETE FROM claseformacion WHERE ID = ?";
        System.out.println("Ejecutando consulta para eliminar clase: " + sql + ", ID: " + id);
        jdbcTemplate.update(sql, id);
        System.out.println("Clase eliminada.");
    }

    // Obtener el ID de una clase por su nombre
    public Integer obtenerIdClasePorNombre(String nombreClase) {
        String sql = "SELECT ID FROM claseformacion WHERE NombreClase = ?";
        System.out.println("Ejecutando consulta para obtener ID de clase: " + sql + ", Nombre: " + nombreClase);
        return jdbcTemplate.queryForObject(sql, new Object[]{nombreClase}, Integer.class);
    }

    // Obtener todas las clases de formación
    public List<Map<String, Object>> obtenerTodasLasClases() {
        String sql = "SELECT ID, NombreClase FROM claseformacion";
        System.out.println("Ejecutando consulta para obtener todas las clases: " + sql);
        List<Map<String, Object>> clases = jdbcTemplate.queryForList(sql);
        System.out.println("Clases obtenidas: " + clases);
        return clases;
    }

    public Map<String, Object> obtenerClasePorDocumentoInstructor(String documentoInstructor) {
        String sql = """
                    SELECT cf.ID, cf.NombreClase 
                    FROM claseformacion cf
                    INNER JOIN claseformacion_fichas cff ON cf.ID = cff.IDClaseFormacion
                    INNER JOIN instructor_ficha inf ON cff.IDFicha = inf.IDFicha
                    INNER JOIN instructor i ON inf.IDInstructor = i.ID
                    INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID
                    WHERE pu.Documento = ?
                    LIMIT 1
                """;

        try {
            // queryForMap devuelve un solo resultado
            Map<String, Object> resultado = jdbcTemplate.queryForMap(sql, documentoInstructor);
            return resultado;
        } catch (EmptyResultDataAccessException e) {
            // Manejar cuando no se encuentra ningún resultado
            System.out.println("No se encontró ninguna clase para el instructor con documento: " + documentoInstructor);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la clase de formación para el instructor con documento: " + documentoInstructor, e);
        }
    }
}