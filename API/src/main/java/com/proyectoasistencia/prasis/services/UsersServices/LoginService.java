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

import java.util.*;

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

        // Depuración: Validación inicial del rol y credenciales
        System.out.println("Iniciando proceso de login. Usuario: " + user + ", Rol: " + role);

        // Validación de credenciales para ADMIN
        if (role.equalsIgnoreCase("ADMIN")) {
            System.out.println("Validando credenciales de Administrador.");
            AdminModel admin = adminService.login(user, password);

            if (admin != null) {
                response.put("User", admin.getUser());
                response.put("Role", "Admin");
                System.out.println("Administrador autenticado: " + admin.getUser());
            } else {
                System.err.println("Credenciales incorrectas para el Administrador.");
                throw new RuntimeException("Credenciales incorrectas para el Administrador.");
            }

        } else {
            // Validación de credenciales para INSTRUCTOR o APRENDIZ
            String documento = obtenerDocumentoPorUsuarioYContrasena(user, password);
            if (documento == null) {
                throw new RuntimeException("Credenciales incorrectas o usuario no encontrado.");
            }

            switch (role.toUpperCase()) {
                case "INSTRUCTOR":
                    System.out.println("Validando credenciales de Instructor.");
                    InstructorModel instructor = instructorService.getInstructor(documento);

                    if (instructor != null) {
                        List<Map<String, Object>> clasesFormacion = cfd.obtenerClasePorDocumentoInstructor(documento);
                        if (clasesFormacion == null || clasesFormacion.isEmpty()) {
                            throw new RuntimeException("No se encontró ninguna clase para el instructor con documento: " + documento);
                        }

                        // Usamos un Set para eliminar duplicados, basándonos en el nombre de la clase o el ID
                        Set<String> nombresClasesUnicas = new HashSet<>();
                        List<Map<String, Object>> clasesUnicas = new ArrayList<>();

                        for (Map<String, Object> clase : clasesFormacion) {
                            String nombreClase = (String) clase.get("NombreClase");  // o "IDClase" si prefieres usar el ID
                            if (nombresClasesUnicas.add(nombreClase)) {
                                // Si el set añade exitosamente el nombre, es porque no estaba antes (es único)
                                clasesUnicas.add(clase);
                            }
                        }

                        response.put("FullName", instructor.getNombres() + " " + instructor.getApellidos());
                        response.put("Documento", instructor.getDocumento());
                        response.put("TipoDocumento", instructor.getTipoDocumento());
                        response.put("Role", "Instructor");
                        response.put("Clases", clasesUnicas); // Agregamos las clases únicas al response
                        System.out.println("Instructor autenticado: " + instructor.getNombres() + " " + instructor.getApellidos());

                    } else {
                        throw new RuntimeException("No se encontró el instructor con el documento: " + documento);
                    }
                    break;


                case "APRENDIZ":
                    System.out.println("Validando credenciales de Aprendiz.");
                    AprendizModel aprendiz = aprendizService.getAprendiz(documento);

                    if (aprendiz != null) {
                        response.put("FullName", aprendiz.getNombres() + " " + aprendiz.getApellidos());
                        response.put("Documento", aprendiz.getDocumento());
                        response.put("TipoDocumento", aprendiz.getTipoDocumento());
                        response.put("Role", "Aprendiz");
                        System.out.println("Aprendiz autenticado: " + aprendiz.getNombres() + " " + aprendiz.getApellidos());

                    } else {
                        throw new RuntimeException("No se encontró el aprendiz con el documento: " + documento);
                    }
                    break;

                default:
                    throw new RuntimeException("Rol no válido.");
            }
        }

        return response;
    }
}
