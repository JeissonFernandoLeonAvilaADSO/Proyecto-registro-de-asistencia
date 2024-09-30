package com.proyectoasistencia.prasis.services.UsersServices;

import com.proyectoasistencia.prasis.models.UsersModels.AdminModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para el login de administrador
    public AdminModel login(String user, String password) {
        String sql = "SELECT AdminUser, AdminPass FROM administrador WHERE AdminUser = ?";
        try {
            AdminModel admin = jdbcTemplate.queryForObject(sql, new Object[]{user}, (rs, rowNum) ->
                    AdminModel.builder()
                            .user(rs.getString("AdminUser"))
                            .password(rs.getString("AdminPass"))
                            .build()
            );

            if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
                return admin;
            } else {
                throw new RuntimeException("Credenciales incorrectas para el administrador.");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Usuario de administrador no encontrado.");
        }
    }

    // CRUD básico
    public List<AdminModel> obtenerTodosLosAdministradores() {
        String sql = "SELECT AdminUser, AdminPass FROM administrador";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                AdminModel.builder()
                        .user(rs.getString("AdminUser"))
                        .password(rs.getString("AdminPass"))
                        .build()
        );
    }

    public void crearAdministrador(AdminModel admin) {
        String sql = "INSERT INTO administrador (AdminUser, AdminPass) VALUES (?, ?)";
        jdbcTemplate.update(sql, admin.getUser(), passwordEncoder.encode(admin.getPassword()));
    }

    public void actualizarAdministrador(String user, AdminModel admin) {
        String sql = "UPDATE administrador SET AdminPass = ? WHERE AdminUser = ?";
        jdbcTemplate.update(sql, passwordEncoder.encode(admin.getPassword()), user);
    }

    public void eliminarAdministrador(String user) {
        String sql = "DELETE FROM administrador WHERE AdminUser = ?";
        jdbcTemplate.update(sql, user);
    }
}
