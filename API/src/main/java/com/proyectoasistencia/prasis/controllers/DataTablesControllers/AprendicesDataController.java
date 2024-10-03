package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import com.proyectoasistencia.prasis.services.DataTablesServices.AprendicesDataService;
import com.proyectoasistencia.prasis.services.UsersServices.AprendizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Data/Aprendices")
public class AprendicesDataController {

    @Autowired
    private AprendicesDataService aprendicesDataService;

    @Autowired
    private AprendizService aprendizService;


    // Endpoint para obtener todos los aprendices relacionados con una ficha
    @GetMapping("/porFicha")
    public ResponseEntity<List<Map<String, Object>>> obtenerAprendicesPorFicha(@RequestParam Integer Ficha) {
        System.out.println("Obteniendo aprendices relacionados con la ficha: " + Ficha);
        List<Map<String, Object>> aprendices = aprendicesDataService.obtenerAprendicesPorFicha(Ficha);
        return ResponseEntity.ok(aprendices);
    }

    // Obtener todos los aprendices relacionados con una ficha
    @GetMapping("/CompletosPorFicha/{numeroFicha}")
    public ResponseEntity<List<AprendizModel>> obtenerAprendicesCompletosPorFicha(@PathVariable Integer numeroFicha) {
        try {
            // Obtener los aprendices por número de ficha
            List<Map<String, Object>> aprendicesBasicos = aprendicesDataService.obtenerAprendicesPorFicha(numeroFicha);

            if (aprendicesBasicos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }

            // Para cada aprendiz, obtener detalles adicionales usando su documento
            List<AprendizModel> aprendicesDetallados = aprendicesBasicos.stream()
                    .map(aprendiz -> {
                        String documento = (String) aprendiz.get("Documento");
                        return aprendizService.getAprendiz(documento);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(aprendicesDetallados);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(AprendizModel.builder()
                            .user("Error")
                            .programaFormacion("No se pudo obtener los detalles de los aprendices")
                            .build()));
        }
    }

}
