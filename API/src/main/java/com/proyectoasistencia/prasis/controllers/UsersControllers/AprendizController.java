package com.proyectoasistencia.prasis.controllers.UsersControllers;

import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import com.proyectoasistencia.prasis.services.UsersServices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Aprendiz")
public class AprendizController {

    @Autowired
    private AprendizService aprendizService;

    // Crear un nuevo aprendiz
    @PostMapping
    public ResponseEntity<AprendizModel> createAprendiz(@RequestBody AprendizModel aprendiz) {
        AprendizModel createdAprendiz = aprendizService.createAprendiz(aprendiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAprendiz);
    }

    // Obtener un aprendiz por documento
    @GetMapping("/{documento}")
    public ResponseEntity<?> getAprendiz(@PathVariable String documento) {
        AprendizModel aprendiz = aprendizService.getAprendiz(documento);
        if (aprendiz != null) {
            if ("Deshabilitado".equalsIgnoreCase(aprendiz.getEstado())) {
                // Retornar un Map con un mensaje
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "El aprendiz está deshabilitado");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            } else {
                return ResponseEntity.ok(aprendiz);
            }
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "No se encontró el aprendiz");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Obtener todos los aprendices (opcional)
    @GetMapping
    public ResponseEntity<List<AprendizModel>> getAllAprendices(@RequestParam Integer ficha) {
        List<AprendizModel> aprendices = aprendizService.getAllAprendicesFicha(ficha);
        return ResponseEntity.ok(aprendices);
    }

    // Actualizar un aprendiz existente
    @PutMapping("/{documento}")
    public ResponseEntity<AprendizModel> updateAprendiz(@PathVariable String documento, @RequestBody AprendizModel aprendiz) {
        AprendizModel updatedAprendiz = aprendizService.updateAprendiz(documento, aprendiz);
        if (updatedAprendiz != null) {
            return ResponseEntity.ok(updatedAprendiz);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar un aprendiz por documento
    @DeleteMapping("/{documento}")
    public ResponseEntity<Void> deleteAprendiz(@PathVariable String documento) {
        boolean deleted = aprendizService.deleteAprendiz(documento);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para habilitar un Aprendiz
    @PutMapping("/habilitar/{documento}")
    public ResponseEntity<?> habilitarAprendiz(@PathVariable String documento) {
        boolean resultado = aprendizService.habilitarAprendiz(documento);
        Map<String, String> response = new HashMap<>();
        if (resultado) {
            response.put("mensaje", "Aprendiz habilitado exitosamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("mensaje", "No se pudo habilitar el Aprendiz. Verifique que el documento sea correcto y que el Aprendiz exista.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Endpoint para inhabilitar un Aprendiz
    @PutMapping("/inhabilitar/{documento}")
    public ResponseEntity<?> inhabilitarAprendiz(@PathVariable String documento) {
        boolean resultado = aprendizService.inhabilitarAprendiz(documento);
        Map<String, String> response = new HashMap<>();
        if (resultado) {
            response.put("mensaje", "Aprendiz inhabilitado exitosamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("mensaje", "No se pudo inhabilitar el Aprendiz. Verifique que el documento sea correcto y que el Aprendiz exista.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
