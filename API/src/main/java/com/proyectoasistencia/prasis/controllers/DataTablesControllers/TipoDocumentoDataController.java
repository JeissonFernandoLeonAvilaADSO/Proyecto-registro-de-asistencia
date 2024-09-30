package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.TipoDocumentoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/TipoDocumento")
public class TipoDocumentoDataController {

    @Autowired
    private TipoDocumentoDataService tipoDocumentoDataService;

    // Obtener todos los tipos de documento
    @GetMapping("/todos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosLosTiposDocumento() {
        System.out.println("Obteniendo Tipos de Documentos");
        List<Map<String, Object>> tiposDocumento = tipoDocumentoDataService.obtenerTodosLosTiposDocumento();
        return ResponseEntity.ok(tiposDocumento);
    }

    // Crear un nuevo tipo de documento
    @PostMapping("/crear")
    public ResponseEntity<String> crearTipoDocumento(@RequestParam String tipoDocumento) {
        try {
            // Limpiar posibles duplicados en el valor recibido
            String tipoDocumentoLimpio = tipoDocumento.trim().replaceAll(",.*", "");  // Eliminar cualquier repetición o duplicación después de una coma

            System.out.println("Tipo Documento Recibido (Limpio): " + tipoDocumentoLimpio);  // Verificar el valor limpio recibido
            tipoDocumentoDataService.crearTipoDocumento(tipoDocumentoLimpio);

            return ResponseEntity.ok("Tipo de documento creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el tipo de documento: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarTipoDocumento(@RequestParam Integer id, @RequestParam String tipoDocumento) {
        try {
            // Limpiar posibles duplicados en el valor recibido
            String tipoDocumentoLimpio = tipoDocumento.trim().replaceAll(",.*", "");  // Eliminar cualquier repetición o duplicación después de una coma

            System.out.println("Tipo Documento Recibido (Limpio): " + tipoDocumentoLimpio);  // Verificar el valor limpio recibido
            tipoDocumentoDataService.actualizarTipoDocumento(id, tipoDocumentoLimpio);

            return ResponseEntity.ok("Tipo de documento actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el tipo de documento: " + e.getMessage());
        }
    }

    // Eliminar un tipo de documento
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarTipoDocumento(@RequestParam Integer id) {
        try {
            tipoDocumentoDataService.eliminarTipoDocumento(id);
            return ResponseEntity.ok("Tipo de documento eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el tipo de documento: " + e.getMessage());
        }
    }
}

