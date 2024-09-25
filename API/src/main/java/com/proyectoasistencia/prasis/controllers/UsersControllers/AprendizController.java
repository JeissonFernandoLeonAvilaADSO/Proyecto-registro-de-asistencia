package com.proyectoasistencia.prasis.controllers.UsersControllers;

import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import com.proyectoasistencia.prasis.services.UsersServices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<AprendizModel> getAprendiz(@PathVariable String documento) {
        AprendizModel aprendiz = aprendizService.getAprendiz(documento);
        if (aprendiz != null) {
            return ResponseEntity.ok(aprendiz);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
}
