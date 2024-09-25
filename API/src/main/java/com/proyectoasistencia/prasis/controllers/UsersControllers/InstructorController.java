package com.proyectoasistencia.prasis.controllers.UsersControllers;

import com.proyectoasistencia.prasis.models.UsersModels.InstructorModel;
import com.proyectoasistencia.prasis.services.UsersServices.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<InstructorModel> getInstructor(@PathVariable String documento) {
        InstructorModel instructor = instructorService.getInstructor(documento);
        if (instructor != null) {
            return ResponseEntity.ok(instructor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
}

