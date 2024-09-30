package com.proyectoasistencia.prasis.controllers.DataTablesControllers.ResidenciaDataControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ResidenciaDataService.MunicipioDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Municipios")
public class MunicipioDataController {

    @Autowired
    private MunicipioDataService municipioDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> obtenerTodosLosMunicipios() {
        return municipioDataService.obtenerTodosLosMunicipios();
    }

    @PostMapping
    public void crearMunicipio(@RequestBody Map<String, Object> municipio) {
        String nombreMunicipio = municipio.get("nombre_municipio").toString();
        String nombreDepartamento = municipio.get("nombre_departamento").toString();
        municipioDataService.crearMunicipio(nombreMunicipio, nombreDepartamento);
    }

    @PutMapping("/{id}")
    public void actualizarMunicipio(@PathVariable Integer id, @RequestBody Map<String, Object> municipio) {
        String nombreMunicipio = municipio.get("nombre_municipio").toString();
        String nombreDepartamento = municipio.get("nombre_departamento").toString();
        municipioDataService.actualizarMunicipio(id, nombreMunicipio, nombreDepartamento);
    }

    @DeleteMapping("/{id}")
    public void eliminarMunicipio(@PathVariable Integer id) {
        municipioDataService.eliminarMunicipio(id);
    }
}
