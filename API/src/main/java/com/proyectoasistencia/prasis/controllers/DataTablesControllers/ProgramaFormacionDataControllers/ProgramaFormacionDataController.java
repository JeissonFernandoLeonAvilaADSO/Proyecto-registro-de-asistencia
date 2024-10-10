package com.proyectoasistencia.prasis.controllers.DataTablesControllers.ProgramaFormacionDataControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.ProgramaFormacionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/ProgramaFormacion")
public class ProgramaFormacionDataController {

    @Autowired
    private ProgramaFormacionDataService programaFormacionDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> getAllProgramasFormacion() {
        return programaFormacionDataService.getAllProgramasFormacion();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProgramaFormacionById(@PathVariable int id) {
        Map<String, Object> programa = programaFormacionDataService.getProgramaFormacionById(id);
        if (programa != null) {
            return ResponseEntity.ok(programa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createProgramaFormacion(@RequestBody Map<String, Object> programaFormacion) {
        System.out.println(programaFormacion);
        programaFormacionDataService.createProgramaFormacion(programaFormacion);
        return ResponseEntity.ok("Programa de formación creado exitosamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProgramaFormacion(@PathVariable int id, @RequestBody Map<String, Object> programaFormacion) {
        boolean updated = programaFormacionDataService.updateProgramaFormacion(id, programaFormacion);
        if (updated) {
            return ResponseEntity.ok("Programa de formación actualizado exitosamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProgramaFormacion(@PathVariable int id) {
        boolean deleted = programaFormacionDataService.deleteProgramaFormacion(id);
        if (deleted) {
            return ResponseEntity.ok("Programa de formación eliminado exitosamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

