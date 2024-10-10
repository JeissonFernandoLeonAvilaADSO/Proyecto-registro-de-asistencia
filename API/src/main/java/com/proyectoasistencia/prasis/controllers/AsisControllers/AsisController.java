package com.proyectoasistencia.prasis.controllers.AsisControllers;


import com.proyectoasistencia.prasis.services.AsisServices.AsisService;
import com.proyectoasistencia.prasis.services.AsisServices.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            System.out.println(datosAsistencia);
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

    @GetMapping("/listar/InstructorAsis")
    public ResponseEntity<List<Map<String, Object>>> listarAsistenciasPorFicha(@RequestParam String documentoInstructor) {
        try {
            System.out.println("Buscando asistencias para el instructor con documento: " + documentoInstructor);
            List<Map<String, Object>> asistencias = asisService.listarAsistenciasPorInstructor(documentoInstructor);

            // Convertir el archivo BLOB a base64 para incluirlo en la respuesta JSON
            asistencias.forEach(asistencia -> {
                byte[] archivoBlob = (byte[]) asistencia.get("ArchivoExcel");
                if (archivoBlob != null) {
                    String archivoBase64 = Base64.getEncoder().encodeToString(archivoBlob);
                    System.out.println(archivoBase64);
                    asistencia.put("ArchivoExcel", archivoBase64);  // Reemplazar el BLOB con el string base64
                } else {
                    System.out.println("Archivo de asistencia no encontrado");
                }
            });
            System.out.println("asistencias encontradas: " + asistencias);

            return ResponseEntity.ok(asistencias);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/FiltroListarAsistencias")
    public ResponseEntity<List<Map<String, Object>>> listarAsistencias(
            @RequestParam(value = "DocumentoInstructor") String documentoInstructor,
            @RequestParam(required = false, value = "ambiente") String ambiente,
            @RequestParam(required = false, value = "ProgramaFormacion") String programaFormacion,
            @RequestParam(required = false, value = "ficha") Integer ficha
    ) {
        try {
            List<Map<String, Object>> asistencias = asisService.FiltroListarAsistencias(documentoInstructor, ambiente, programaFormacion, ficha);
            return ResponseEntity.ok(asistencias);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint para listar asistencias agrupadas por clase de formación para un aprendiz específico.
     * URL: /Asistencia/listarAsistenciasAgrupadasPorClase?documento=DOCUMENTO_DEL_APRENDIZ
     *
     * @param documento Documento del aprendiz como String.
     * @return Lista de asistencias agrupadas por clase de formación.
     */
    @GetMapping("/listarAsistenciasAgrupadasPorClase")
    public ResponseEntity<?> listarAsistenciasAgrupadasPorClase(@RequestParam String documento) {
        // Validar el parámetro 'documento'
        if (documento == null || documento.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El parámetro 'documento' es obligatorio y no puede estar vacío.");
        }
        try {
            List<Map<String, Object>> asistenciasAgrupadas = asisService.listarAsistenciasAgrupadasPorClase(documento);

            if (asistenciasAgrupadas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron asistencias para el documento proporcionado.");
            }

            return ResponseEntity.ok(asistenciasAgrupadas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Endpoint para listar todas las asistencias de un aprendiz específico.
     * URL: /Asistencia/listarAsistenciasPorAprendiz?documento=DOCUMENTO_DEL_APRENDIZ
     *
     * @param documento Documento del aprendiz como String.
     * @return Lista de asistencias con detalles y archivo Excel en Base64.
     */
    @GetMapping("/listarAsistenciasPorAprendiz")
    public ResponseEntity<?> listarAsistenciasPorAprendiz(@RequestParam String documento) {
        // Validar el parámetro 'documento'
        if (documento == null || documento.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El parámetro 'documento' es obligatorio y no puede estar vacío.");
        }
        try {
            List<Map<String, Object>> asistencias = asisService.listarAsistenciasPorAprendiz(documento);

            if (asistencias.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron asistencias para el documento proporcionado.");
            }

            // Convertir el archivo BLOB a base64 para incluirlo en la respuesta JSON
            asistencias.forEach(asistencia -> {
                byte[] archivoBlob = (byte[]) asistencia.get("ArchivoExcel");
                if (archivoBlob != null) {
                    String archivoBase64 = Base64.getEncoder().encodeToString(archivoBlob);
                    asistencia.put("ArchivoExcel", archivoBase64);  // Reemplazar el BLOB con el string base64
                } else {
                    asistencia.put("ArchivoExcel", null);  // Si no hay archivo, poner null
                }
            });

            return ResponseEntity.ok(asistencias);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Endpoint para listar detalles de inasistencias por clase de formación para un aprendiz específico.
     * URL: /Asistencia/listarDetallesInasistenciasPorClase?documento=DOCUMENTO_DEL_APRENDIZ
     *
     * @param documento Documento del aprendiz como String.
     * @return Lista de detalles de inasistencias por clase de formación con soporte en Base64.
     */
    @GetMapping("/listarDetallesInasistenciasPorClase")
    public ResponseEntity<?> listarDetallesInasistenciasPorClase(@RequestParam String documento) {
        // Validar el parámetro 'documento'
        if (documento == null || documento.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El parámetro 'documento' es obligatorio y no puede estar vacío.");
        }
        try {
            List<Map<String, Object>> detallesInasistencias = asisService.listarDetallesInasistenciasPorClase(documento);

            if (detallesInasistencias.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron detalles de inasistencias para el documento proporcionado.");
            }

            // Convertir el soporte BLOB a base64 para incluirlo en la respuesta JSON, si existe
            detallesInasistencias.forEach(detalle -> {
                byte[] soporteBlob = (byte[]) detalle.get("Soporte");
                if (soporteBlob != null) {
                    String soporteBase64 = Base64.getEncoder().encodeToString(soporteBlob);
                    detalle.put("Soporte", soporteBase64);  // Reemplazar el BLOB con el string base64
                } else {
                    detalle.put("Soporte", null);  // Si no hay soporte, poner null
                }
            });

            return ResponseEntity.ok(detallesInasistencias);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Endpoint para obtener las inasistencias de un aprendiz en una semana (7 días) a partir de una fecha de inicio.
     * URL: /Asistencia/ObtenerUsuarioHorasInasistenciaPorSemana
     *
     * @param documento   Documento del aprendiz.
     * @param fechaInicio Fecha de inicio de la semana, en formato 'YYYY-MM-DD'.
     * @return Lista de inasistencias en la semana especificada.
     */
    @GetMapping("/ObtenerUsuarioHorasInasistenciaPorSemana")
    public ResponseEntity<?> obtenerUsuarioHorasInasistenciaPorSemana(
            @RequestParam("documento") String documento,
            @RequestParam("fechaInicio") String fechaInicio) {
        try {
            // Validar los parámetros
            if (documento == null || documento.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El parámetro 'documento' es obligatorio y no puede estar vacío.");
            }
            if (fechaInicio == null || fechaInicio.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El parámetro 'fechaInicio' es obligatorio y no puede estar vacío.");
            }

            List<Map<String, Object>> inasistencias = asisService.obtenerInasistenciasPorSemana(documento, fechaInicio);
            if (inasistencias.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron inasistencias para el documento y la semana proporcionados.");
            }
            return ResponseEntity.ok(inasistencias);
        } catch (Exception e) {
            e.printStackTrace(); // Para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    @PostMapping("/subirSoporte/{idRegistroActividad}")
    public ResponseEntity<?> subirSoporte(@PathVariable("idRegistroActividad") int idRegistroActividad,
                                          @RequestParam("file") MultipartFile file) {
        try {
            // Llamar al servicio para guardar el soporte PDF
            asisService.guardarSoportePDF(idRegistroActividad, file);
            return ResponseEntity.ok("Soporte PDF subido exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el soporte PDF: " + e.getMessage());
        }
    }

}
