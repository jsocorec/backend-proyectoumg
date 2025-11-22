package com.visitas.auth.util;

import com.visitas.auth.model.Visit;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

public class ReportGenerator {

    public static byte[] generateVisitReport(Visit visit) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        document.add(new Paragraph("Informe de Visita").setTextAlignment(TextAlignment.CENTER).setFontSize(20));
        document.add(new Paragraph(" ").setFontSize(12));

        document.add(new Paragraph("ID de Visita: " + visit.getId()));

        if (visit.getClient() != null) {
            document.add(new Paragraph("Cliente: " + visit.getClient().getName() + " (ID: " + visit.getClient().getId() + ")"));
            document.add(new Paragraph("Dirección: " + visit.getClient().getAddress()));
            document.add(new Paragraph("Coordenadas Cliente: " + visit.getClient().getLatitude() + ", " + visit.getClient().getLongitude()));
        } else {
            document.add(new Paragraph("Cliente: No especificado"));
        }


        if (visit.getTechnician() != null) {
            document.add(new Paragraph("Técnico: " + visit.getTechnician().getUsername() + " (ID: " + visit.getTechnician().getId() + ")"));
        } else {
            document.add(new Paragraph("Técnico: No asignado"));
        }


        if (visit.getSupervisor() != null) {
            document.add(new Paragraph("Supervisor: " + visit.getSupervisor().getUsername() + " (ID: " + visit.getSupervisor().getId() + ")"));
        } else {
            document.add(new Paragraph("Supervisor: No asignado"));
        }

        document.add(new Paragraph("Fecha Programada: " + (visit.getScheduledAt() != null ? visit.getScheduledAt().format(formatter) : "-")));
        document.add(new Paragraph("Hora de Ingreso: " + (visit.getCheckIn() != null ? visit.getCheckIn().format(formatter) : "-")));
        document.add(new Paragraph("Hora de Egreso: " + (visit.getCheckOut() != null ? visit.getCheckOut().format(formatter) : "-")));
        document.add(new Paragraph("Notas: " + (visit.getNotes() != null ? visit.getNotes() : "-")));

        if (visit.getCheckInLat() != null && visit.getCheckInLng() != null) {
            document.add(new Paragraph("Coordenadas Ingreso: " + visit.getCheckInLat() + ", " + visit.getCheckInLng()));
        }
        if (visit.getCheckOutLat() != null && visit.getCheckOutLng() != null) {
            document.add(new Paragraph("Coordenadas Egreso: " + visit.getCheckOutLat() + ", " + visit.getCheckOutLng()));
        }


        document.close();
        return baos.toByteArray();
    }
}
