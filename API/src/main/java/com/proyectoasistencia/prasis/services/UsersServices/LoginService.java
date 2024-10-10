package com.proyectoasistencia.prasis.services.UsersServices;

import com.proyectoasistencia.prasis.models.UsersModels.AdminModel;
import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import com.proyectoasistencia.prasis.models.UsersModels.InstructorModel;
import com.proyectoasistencia.prasis.services.DataTablesServices.ClaseFormacionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class LoginService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private AprendizService aprendizService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ClaseFormacionDataService cfd;

    // Método para obtener el documento basado en el usuario y la contraseña
    private String obtenerDocumentoPorUsuarioYContrasena(String user, String password) {
        String sql = """
            SELECT pu.Documento
            FROM perfilusuario pu
            INNER JOIN usuario u ON pu.IDUsuario = u.ID
            WHERE u.Usuario = ?
        """;

        try {
            // Validamos que el usuario existe
            String storedPassword = jdbcTemplate.queryForObject("SELECT u.Contraseña FROM usuario u WHERE u.Usuario = ?", new Object[]{user}, String.class);

            if (passwordEncoder.matches(password, storedPassword)) {
                // Si la contraseña es correcta, devolvemos el documento asociado
                return jdbcTemplate.queryForObject(sql, new Object[]{user}, String.class);
            } else {
                throw new RuntimeException("Credenciales incorrectas: la contraseña no coincide.");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("No se encontró el usuario: " + user);
        }
    }

    public Map<String, Object> login(String user, String password, String role) {
        Map<String, Object> response = new HashMap<>();

        // Depuración: Validación inicial del rol
        System.out.println("Iniciando proceso de login. Usuario: " + user + ", Rol: " + role);

        if (role.equalsIgnoreCase("ADMIN")) {
            // Depuración: Procesando login para administrador
            System.out.println("Validando credenciales de Administrador.");

            AdminModel admin = adminService.login(user, password);
            if (admin != null) {
                response.put("User", admin.getUser());
                response.put("Role", "Admin");

                // Depuración: Login exitoso para administrador
                System.out.println("Administrador autenticado: " + admin.getUser());
            } else {
                System.err.println("Credenciales incorrectas para el Administrador.");
                throw new RuntimeException("Credenciales incorrectas para el Administrador.");
            }

        } else {
            // Buscar el documento asociado al nombre de usuario y la contraseña
            String documento = obtenerDocumentoPorUsuarioYContrasena(user, password);

            if (role.equalsIgnoreCase("INSTRUCTOR")) {
                // Depuración: Procesando login para instructor
                System.out.println("Validando credenciales de Instructor.");

                // Usar el documento para buscar al instructor
                InstructorModel instructor = instructorService.getInstructor(documento);
                if (instructor != null) {
                    // Obtener la clase asociada al instructor
                    Map<String, Object> claseFormacion = cfd.obtenerClasePorDocumentoInstructor(documento);
                    if (claseFormacion == null || !claseFormacion.containsKey("NombreClase")) {
                        // No se encontró ninguna clase para el instructor
                        throw new RuntimeException("No se encontró ninguna clase para el instructor con documento: " + documento);
                    }

                    String nombreClase = (String) claseFormacion.get("NombreClase");

                    response.put("FullName", instructor.getNombres() + " " + instructor.getApellidos());
                    response.put("Documento", instructor.getDocumento());
                    response.put("TipoDocumento", instructor.getTipoDocumento());
                    response.put("ClaseFormacion", nombreClase);
                    response.put("Role", "Instructor");

                    // Depuración: Login exitoso para instructor
                    System.out.println("Instructor autenticado: " + instructor.getNombres() + " " + instructor.getApellidos());
                } else {
                    throw new RuntimeException("No se encontró el instructor con el documento: " + documento);
                }

            } else if (role.equalsIgnoreCase("APRENDIZ")) {
                // Depuración: Procesando login para aprendiz
                System.out.println("Validando credenciales de Aprendiz.");

                // Usar el documento para buscar al aprendiz
                AprendizModel aprendiz = aprendizService.getAprendiz(documento);
                if (aprendiz != null) {
                    response.put("FullName", aprendiz.getNombres() + " " + aprendiz.getApellidos());
                    response.put("Documento", aprendiz.getDocumento());
                    response.put("TipoDocumento", aprendiz.getTipoDocumento());
                    response.put("Role", "Aprendiz");

                    // Depuración: Login exitoso para aprendiz
                    System.out.println("Aprendiz autenticado: " + aprendiz.getNombres() + " " + aprendiz.getApellidos());
                } else {
                    throw new RuntimeException("No se encontró el aprendiz con el documento: " + documento);
                }

            } else {
                throw new RuntimeException("Rol no válido.");
            }
        }

        return response;
    }
}
