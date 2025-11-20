package com.visitas.auth.util;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import java.io.ByteArrayOutputStream;
import com.visitas.auth.model.Visit;

public class ReportGenerator {

    public static byte[] generateVisitReport(Visit visit) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Título
        document.add(new Paragraph("Reporte de Visita").setBold().setFontSize(20));
        document.add(new Paragraph("ID: " + visit.getId()));
        document.add(new Paragraph("Cliente ID: " + visit.getClientId()));
        document.add(new Paragraph("Técnico ID: " + visit.getTechnicianId()));
        document.add(new Paragraph("Supervisor ID: " + visit.getSupervisorId()));
        document.add(new Paragraph("Programada: " + visit.getScheduledAt().toString()));
        if (visit.getCheckIn() != null) {
            document.add(new Paragraph("Ingreso: " + visit.getCheckIn().toString()));
        }
        if (visit.getCheckOut() != null) {
            document.add(new Paragraph("Egreso: " + visit.getCheckOut().toString()));
        }
        if (visit.getNotes() != null) {
            document.add(new Paragraph("Notas: " + visit.getNotes()));
        }

        document.close();
        return baos.toByteArray();
    }
}
