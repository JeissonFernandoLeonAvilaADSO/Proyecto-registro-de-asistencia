package com.proyectoasistencia.prasis.controllers.DataTablesControllers.ResidenciaDataControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ResidenciaDataService.BarrioDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Barrios")
public class BarrioDataController {

    @Autowired
    private BarrioDataService barrioDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> obtenerTodosLosBarrios() {
        return barrioDataService.obtenerTodosLosBarrios();
    }

    @PostMapping
    public void crearBarrio(@RequestBody Map<String, Object> barrio) {
        String nombreBarrio = barrio.get("nombre_barrio").toString();
        String nombreMunicipio = barrio.get("nombre_municipio").toString();
        barrioDataService.crearBarrio(nombreBarrio, nombreMunicipio);
    }

    @PutMapping("/{id}")
    public void actualizarBarrio(@PathVariable Integer id, @RequestBody Map<String, Object> barrio) {
        String nombreBarrio = barrio.get("nombre_barrio").toString();
        String nombreMunicipio = barrio.get("nombre_municipio").toString();
        barrioDataService.actualizarBarrio(id, nombreBarrio, nombreMunicipio);
    }

    @DeleteMapping("/{id}")
    public void eliminarBarrio(@PathVariable Integer id) {
        barrioDataService.eliminarBarrio(id);
    }
}
