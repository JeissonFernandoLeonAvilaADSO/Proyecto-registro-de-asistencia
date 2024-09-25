package com.proyectoasistencia.prasis.controllers.AsisControllers;


import com.proyectoasistencia.prasis.services.AsisServices.AsisService;
import com.proyectoasistencia.prasis.services.AsisServices.ExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Asistencia")
public class AsisController {

    @Autowired
    private AsisService asisService;

    @Autowired
    private ExcelService excelService;

    @PostMapping("/SubirAsistencia")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> subirAsistencia(@RequestBody Map<String, Object> datosAsistencia) {
        try {
            // Procesar la asistencia (agregar aprendices ausentes, etc.)
            Map<String, Object> resultadoAsistencia = asisService.upAsis(datosAsistencia);

            // Generar el archivo Excel con el resultado de la asistencia procesada
            byte[] excelFile = excelService.generarExcelAsistencia(resultadoAsistencia);

            // Registrar la asistencia, subir el archivo Excel y registrar la actividad
            asisService.registrarAsistenciaYActividades(resultadoAsistencia, excelFile);

            System.out.println("Asistencia procesada y archivo Excel registrado correctamente.");
            return ResponseEntity.ok("Asistencia procesada, archivo Excel generado y datos registrados exitosamente.");

        } catch (Exception e) {
            // En caso de cualquier error, la transacción será revertida automáticamente
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al procesar la asistencia: " + e.getMessage());
        }
    }

    // Endpoint para listar todas las asistencias, incluyendo el archivo BLOB
    @GetMapping("/listar")
    public ResponseEntity<List<Map<String, Object>>> listarAsistencias() {
        List<Map<String, Object>> asistencias = asisService.listarAsistencias();

        // Convertir el archivo BLOB a base64 para incluirlo en la respuesta JSON
        asistencias.forEach(asistencia -> {
            byte[] archivoBlob = (byte[]) asistencia.get("ArchivoExcel");
            if (archivoBlob != null) {
                String archivoBase64 = Base64.getEncoder().encodeToString(archivoBlob);
                asistencia.put("ArchivoExcel", archivoBase64);  // Reemplazar el BLOB con el string base64
            }
        });

        return ResponseEntity.ok(asistencias);
    }
}
