package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.AprendicesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Aprendices")
public class AprendicesDataController {

    @Autowired
    private AprendicesDataService aprendicesDataService;

    // Endpoint para obtener todos los aprendices relacionados con una ficha
    @GetMapping("/porFicha")
    public ResponseEntity<List<Map<String, Object>>> obtenerAprendicesPorFicha(@RequestParam Integer Ficha) {
        System.out.println("Obteniendo aprendices relacionados con la ficha: " + Ficha);
        List<Map<String, Object>> aprendices = aprendicesDataService.obtenerAprendicesPorFicha(Ficha);
        return ResponseEntity.ok(aprendices);
    }

}
