package com.proyectoasistencia.prasis.services.AsisServices;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {
    public static byte[] generarExcelAsistencia(Map<String, Object> datosAsistencia) throws IOException {
        // Crear un nuevo libro de trabajo
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Asistencia");

        // Establecer estilos para el encabezado
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);

        // Estilo para el título principal
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle subTitleStyle = workbook.createCellStyle();
        subTitleStyle.setAlignment(HorizontalAlignment.CENTER);

        // Validar que los datos existan
        String tipoAsistencia = datosAsistencia.get("TipoAsistencia") != null ? datosAsistencia.get("TipoAsistencia").toString() : "Asistencia no especificada";
        String programaFormacion = datosAsistencia.get("ProgramaFormacion") != null ? datosAsistencia.get("ProgramaFormacion").toString() : "Programa no especificado";
        String ficha = datosAsistencia.get("Ficha") != null ? datosAsistencia.get("Ficha").toString() : "Ficha no especificada";
        String nivelFormacion = datosAsistencia.get("NivelFormacion") != null ? datosAsistencia.get("NivelFormacion").toString() : "Nivel no especificado";
        String sede = datosAsistencia.get("Sede") != null ? datosAsistencia.get("Sede").toString() : "Sede no especificada";
        String instructor = datosAsistencia.get("Instructor") != null ? datosAsistencia.get("Instructor").toString() : "Instructor no especificado";
        String claseFormacion = datosAsistencia.get("ClaseFormacion") != null ? datosAsistencia.get("ClaseFormacion").toString() : "Clase no especificada";
        String fecha = datosAsistencia.get("Fecha") != null ? datosAsistencia.get("Fecha").toString() : new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Agregar los datos del encabezado
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));  // Merge cells for title
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(tipoAsistencia);
        titleCell.setCellStyle(titleStyle);

        // Programa de formación
        Row programRow = sheet.createRow(1);
        Cell programCell = programRow.createCell(0);
        programCell.setCellValue("Programa de formación: " + programaFormacion);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));
        programCell.setCellStyle(subTitleStyle);

        // Ficha
        Row fichaRow = sheet.createRow(2);
        Cell fichaCell = fichaRow.createCell(0);
        fichaCell.setCellValue("Ficha: " + ficha);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
        fichaCell.setCellStyle(subTitleStyle);

        // Nivel de formación
        Row nivelRow = sheet.createRow(3);
        Cell nivelCell = nivelRow.createCell(0);
        nivelCell.setCellValue("Nivel de formación: " + nivelFormacion);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
        nivelCell.setCellStyle(subTitleStyle);

        // Sede
        Row sedeRow = sheet.createRow(4);
        Cell sedeCell = sedeRow.createCell(0);
        sedeCell.setCellValue("Sede: " + sede);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 7));
        sedeCell.setCellStyle(subTitleStyle);

        // Instructor y Clase Formacion
        Row instructorRow = sheet.createRow(5);
        Cell instructorCell = instructorRow.createCell(0);
        instructorCell.setCellValue("Instructor: " + instructor);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 7));
        instructorCell.setCellStyle(subTitleStyle);

        Row claseRow = sheet.createRow(6);
        Cell claseCell = claseRow.createCell(0);
        claseCell.setCellValue("Clase Formacion: " + claseFormacion);
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 7));
        claseCell.setCellStyle(subTitleStyle);

        Row fechaRow = sheet.createRow(7);
        Cell fechaCell = fechaRow.createCell(0);
        fechaCell.setCellValue("Fecha: " + fecha);
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 7));
        fechaCell.setCellStyle(subTitleStyle);


        // Espacio en blanco
        sheet.createRow(8);

        // Encabezados de la tabla
        Row headerRow = sheet.createRow(9);
        String[] headers = {"#", "Documento", "Nombre", "Teléfono", "Correo", "Género", "Residencia", "Horas de Inasistencia"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Obtener la lista de aprendices
        List<Map<String, Object>> listaAprendices = (List<Map<String, Object>>) datosAsistencia.get("ListaAprendices");

        // Empezar a llenar las filas con los aprendices
        int rowNum = 10;  // Iniciamos en la fila 7 (índice 6)
        for (int i = 0; i < listaAprendices.size(); i++) {
            Map<String, Object> aprendiz = listaAprendices.get(i);

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);  // Columna de numeración (1, 2, 3, ...)
            row.createCell(1).setCellValue(aprendiz.get("Documento").toString());
            row.createCell(2).setCellValue(aprendiz.get("Nombre").toString());
            row.createCell(3).setCellValue(aprendiz.get("Telefono").toString());
            row.createCell(4).setCellValue(aprendiz.get("Correo").toString());
            row.createCell(5).setCellValue(aprendiz.get("Genero").toString());
            row.createCell(6).setCellValue(aprendiz.get("Residencia").toString());

            // Aplicar formato condicional basado en el valor de Horas de Inasistencia
            Cell horasCell = row.createCell(7);
            int horasInasistencia = Integer.parseInt(aprendiz.get("HorasInasistencia").toString());
            horasCell.setCellValue(horasInasistencia);

            // Crear estilos para las celdas según las horas de inasistencia
            CellStyle estiloBueno = workbook.createCellStyle();
            estiloBueno.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            estiloBueno.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle estiloNeutral = workbook.createCellStyle();
            estiloNeutral.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            estiloNeutral.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle estiloIncorrecto = workbook.createCellStyle();
            estiloIncorrecto.setFillForegroundColor(IndexedColors.RED.getIndex());  // Rojo claro
            estiloIncorrecto.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Asignar estilos de acuerdo a las horas de inasistencia
            if (horasInasistencia == 0) {
                horasCell.setCellStyle(estiloBueno);
            } else if (horasInasistencia > 0 && horasInasistencia < 5) {
                horasCell.setCellStyle(estiloNeutral);
            } else if (horasInasistencia >= 5) {
                horasCell.setCellStyle(estiloIncorrecto);
            }
        }

        // Autoajustar las columnas al contenido
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Convertir el workbook en un array de bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        guardarExcel(workbook, "asistencia.xlsx");
        workbook.close();
        return outputStream.toByteArray();
    }

    public static void guardarExcel(Workbook workbook, String rutaSalida) throws IOException {
        // Escribir el archivo de Excel
        try (FileOutputStream fileOut = new FileOutputStream(rutaSalida)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    public static void abrirExcel(String rutaSalida) throws IOException {
        File archivo = new File(rutaSalida);

        if (!Desktop.isDesktopSupported()) {
            System.out.println("El sistema no soporta Desktop, no se puede abrir el archivo automáticamente.");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if (archivo.exists()) {
            desktop.open(archivo);
        } else {
            System.out.println("El archivo no existe.");
        }
    }
}
