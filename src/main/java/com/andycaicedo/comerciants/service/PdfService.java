package com.andycaicedo.comerciants.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.andycaicedo.comerciants.entity.Comerciant;
import com.andycaicedo.comerciants.entity.Establishment;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Service
public class PdfService {
    
    public byte[] generatePDF(Comerciant comerciant) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument)) {
            addTitleText(document, "Formulario: "+ comerciant.getName());
            addTable(document, comerciant);
            document.add(new Paragraph("\n"));
            addTitleText(document, "Establecimientos");
            document.add(new Paragraph("\n"));
            addTableEstablish(document, comerciant.getEstablishments());
        }
        return outputStream.toByteArray();
    }

    private void addTitleText(Document document, String titleText) {
        Paragraph paragraph = new Paragraph(titleText).setBold().setFontSize(20).setMarginTop(20);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
    }

    private void addTable(Document document, Comerciant comerciant) {
        Table table = new Table(6);
        table.addCell("Nombre");
        table.addCell("Correo electronico");
        table.addCell("Ciudad");
        table.addCell("Departamento");
        table.addCell("Telefono");
        table.addCell("Fecha de registro");
        table.addCell(comerciant.getName());
        table.addCell(comerciant.getEmail());
        table.addCell(comerciant.getCity().getName());
        table.addCell(comerciant.getDepartment().getName());
        table.addCell(comerciant.getPhone());
        table.addCell(comerciant.getRegistrationDate().toString());
        document.add(table);
    }

    private void addTableEstablish(Document document, List<Establishment> establishments) {
        Table table = new Table(4).setWidth(UnitValue.createPercentValue(100));
        table.addCell("Nombre de Sucursal");
        table.addCell("Ingresos");
        table.addCell("Número de Empleados");
        for (Establishment establishment : establishments) {
            table.addCell(establishment.getName());
            table.addCell(String.valueOf(establishment.getRevenue()));
            table.addCell(String.valueOf(establishment.getEmployee_count()));
        }
        document.add(table);
    }
}
