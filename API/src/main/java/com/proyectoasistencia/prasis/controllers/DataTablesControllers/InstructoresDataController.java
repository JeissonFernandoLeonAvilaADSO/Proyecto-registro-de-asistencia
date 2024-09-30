package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.InstructoresDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Instructores")
public class InstructoresDataController {
    @Autowired
    private InstructoresDataService instructoresDataService;

    // Obtener todos los instructores
    @GetMapping("/todos")
    public List<Map<String, Object>> obtenerTodosLosInstructores() {
        return instructoresDataService.obtenerTodosLosInstructores();
    }

    @GetMapping("/obtenerDocumento")
    public ResponseEntity<String> obtenerDocumentoPorNombreEIDClase(
            @RequestParam String nombreInstructor) {
        String documento = instructoresDataService.obtenerDocumentoPorNombreEIDClase(nombreInstructor);
        if (documento != null) {
            return ResponseEntity.ok(documento);
        } else {
            return ResponseEntity.badRequest().body("No se encontró el documento para el instructor especificado.");
        }
    }
}
