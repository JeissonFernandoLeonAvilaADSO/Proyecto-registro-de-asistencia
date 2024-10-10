package com.proyectoasistencia.prasis.controllers.UsersControllers;

import com.proyectoasistencia.prasis.models.UsersModels.InstructorModel;
import com.proyectoasistencia.prasis.services.UsersServices.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    // Crear un nuevo instructor
    @PostMapping
    public ResponseEntity<InstructorModel> createInstructor(@RequestBody InstructorModel instructor) {
        InstructorModel createdInstructor = instructorService.createInstructor(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInstructor);
    }

    // Obtener un instructor por documento
    @GetMapping("/{documento}")
    public ResponseEntity<?> getInstructor(@PathVariable String documento) {
        InstructorModel instructor = instructorService.getInstructor(documento);
        if (instructor != null) {
            if ("Deshabilitado".equalsIgnoreCase(instructor.getEstado())) {
                // Retornar un Map con un mensaje
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "El instructor está deshabilitado");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            } else {
                return ResponseEntity.ok(instructor);
            }
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "No se encontró el instructor");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    // Obtener todos los instructores
    @GetMapping
    public ResponseEntity<List<InstructorModel>> getAllInstructors() {
        List<InstructorModel> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    // Actualizar un instructor existente
    @PutMapping("/{documento}")
    public ResponseEntity<InstructorModel> updateInstructor(@PathVariable String documento, @RequestBody InstructorModel instructor) {
        InstructorModel updatedInstructor = instructorService.updateInstructor(documento, instructor);
        if (updatedInstructor != null) {
            return ResponseEntity.ok(updatedInstructor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar un instructor por documento
    @DeleteMapping("/{documento}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable String documento) {
        boolean deleted = instructorService.deleteInstructor(documento);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para habilitar un Instructor
    @PutMapping("/habilitar/{documento}")
    public ResponseEntity<?> habilitarInstructor(@PathVariable String documento) {
        boolean resultado = instructorService.habilitarInstructor(documento);
        Map<String, String> response = new HashMap<>();
        if (resultado) {
            response.put("mensaje", "Instructor habilitado exitosamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("mensaje", "No se pudo habilitar el Instructor. Verifique que el documento sea correcto y que el Instructor exista.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Endpoint para inhabilitar un Instructor
    @PutMapping("/inhabilitar/{documento}")
    public ResponseEntity<?> inhabilitarInstructor(@PathVariable String documento) {
        boolean resultado = instructorService.inhabilitarInstructor(documento);
        Map<String, String> response = new HashMap<>();
        if (resultado) {
            response.put("mensaje", "Instructor inhabilitado exitosamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("mensaje", "No se pudo inhabilitar el Instructor. Verifique que el documento sea correcto y que el Instructor exista.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}

