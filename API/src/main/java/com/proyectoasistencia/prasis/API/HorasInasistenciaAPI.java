package com.proyectoasistencia.prasis.API;



import com.proyectoasistencia.prasis.controller.PerfilUsuarioController;
import com.proyectoasistencia.prasis.models.PerfilUsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Horas")
public class HorasInasistenciaAPI {

    private final JdbcTemplate jdbcTemplate;
    private final PerfilUsuarioController perfilUsuarioController;

    @Autowired
    public HorasInasistenciaAPI(JdbcTemplate jdbcTemplate, PerfilUsuarioController perfilUsuarioController) {
        this.jdbcTemplate = jdbcTemplate;
        this.perfilUsuarioController = perfilUsuarioController;
    }

    @RequestMapping("/ActualizarHoras")
    public ResponseEntity<String> ActualizarHoras(
            @RequestBody Map<String, List<Map<String, Object>>> body) {

        List<Map<String, Object>> listaAprendices = body.get("aprendices");
        ConversionSubTablasAPI conversion = new ConversionSubTablasAPI(jdbcTemplate);

        // Procesar cada aprendiz
        for (Map<String, Object> aprendiz : listaAprendices) {
            // Obtener el usuario basado en el documento
            PerfilUsuarioModel usuario = perfilUsuarioController.getUsuario(aprendiz.get("Documento").toString());

            if (usuario == null) {
                return ResponseEntity.badRequest().body("Usuario con documento " + aprendiz.get("Documento") + " no encontrado.");
            }

            // Obtener los IDs necesarios
            Integer idProgramaFormacion = conversion.ProgramaFormacion(usuario.getProgramaFormacion()).getBody();
            Integer idFicha = conversion.FichaToID(usuario.getNumeroFicha()).getBody();
            Integer idUsuario = usuario.getID();

            // Validar que los IDs no sean nulos
            if (idProgramaFormacion == null || idFicha == null || idUsuario == null) {
                return new ResponseEntity<>("Error en la conversión de datos.", HttpStatus.BAD_REQUEST);
            }

            Integer HorasInasistencia = Integer.parseInt(aprendiz.get("HorasInasistencia").toString());

            // Consultar si ya existe un registro para el usuario y el ID de ficha
            String consultaExistencia = "SELECT COUNT(*) FROM horasinasistencia WHERE IDPerfilUsuario = ? AND IDFicha = ?";
            Integer count = jdbcTemplate.queryForObject(consultaExistencia, Integer.class, idUsuario, idFicha);

            if (count != null && count > 0) {
                // Si existe, actualizar el registro sumando las horas de inasistencia
                String consultaUpdate = "UPDATE horasinasistencia SET HorasInasistencia = HorasInasistencia + ? WHERE IDPerfilUsuario = ? AND IDFicha = ?";
                jdbcTemplate.update(consultaUpdate, HorasInasistencia, idUsuario, idFicha);
            } else {
                // Si no existe, insertar un nuevo registro
                String consultaInsert = "INSERT INTO horasinasistencia (IDPerfilUsuario, IDProgramaFormacion, IDFicha, HorasInasistencia) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(consultaInsert, idUsuario, idProgramaFormacion, idFicha, HorasInasistencia);
            }
        }
        return ResponseEntity.ok("Horas actualizadas con éxito");
    }
}


