package com.visitas.auth.controller;

import com.visitas.auth.model.Visit;
import com.visitas.auth.repository.VisitRepository;
import com.visitas.auth.service.NotificationService;
import com.visitas.auth.util.ReportGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/visits")
public class VisitController {
    private final VisitRepository visitRepository;
    private final NotificationService notificationService;

    public VisitController(VisitRepository visitRepository, NotificationService notificationService) {
        this.visitRepository = visitRepository;
        this.notificationService = notificationService;
    }

    // Listar todas las visitas
    @GetMapping
    public List<Visit> getAll() {
        return visitRepository.findAll();
    }

    // Obtener visita por ID
    @GetMapping("/{id}")
    public ResponseEntity<Visit> getById(@PathVariable Long id) {
        Optional<Visit> opt = visitRepository.findById(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva visita
    @PostMapping
    public Visit create(@RequestBody Visit visit) {
        return visitRepository.save(visit);
    }

    // Actualizar una visita existente
    @PutMapping("/{id}")
    public ResponseEntity<Visit> update(@PathVariable Long id, @RequestBody Visit visitDetails) {
        Optional<Visit> opt = visitRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Visit visit = opt.get();

        if (visitDetails.getClient() != null) {
            visit.setClient(visitDetails.getClient());
        }

        if (visitDetails.getTechnician() != null) {
            visit.setTechnician(visitDetails.getTechnician());
        }

        if (visitDetails.getSupervisor() != null) {
            visit.setSupervisor(visitDetails.getSupervisor());
        }

        // Campos directos de la visita (no relaciones)
        if (visitDetails.getScheduledAt() != null) visit.setScheduledAt(visitDetails.getScheduledAt());
        if (visitDetails.getCheckIn() != null) visit.setCheckIn(visitDetails.getCheckIn());
        if (visitDetails.getCheckInLat() != null) visit.setCheckInLat(visitDetails.getCheckInLat());
        if (visitDetails.getCheckInLng() != null) visit.setCheckInLng(visitDetails.getCheckInLng());
        if (visitDetails.getCheckOut() != null) visit.setCheckOut(visitDetails.getCheckOut());
        if (visitDetails.getCheckOutLat() != null) visit.setCheckOutLat(visitDetails.getCheckOutLat());
        if (visitDetails.getCheckOutLng() != null) visit.setCheckOutLng(visitDetails.getCheckOutLng());
        if (visitDetails.getNotes() != null) visit.setNotes(visitDetails.getNotes());

        visitRepository.save(visit);
        return ResponseEntity.ok(visit);
    }

    // Borrar una visita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (visitRepository.existsById(id)) {
            visitRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Listar visitas de hoy
    @GetMapping("/today")
    public List<Visit> getTodayVisits() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        return visitRepository.findByScheduledAtBetween(startOfDay, endOfDay);
    }

    @PostMapping("/{id}/finish")
    public ResponseEntity<Map<String, String>> finalizarVisita(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String emailCliente = body.get("email");
        Optional<Visit> optVisit = visitRepository.findById(id);
        if (optVisit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Visit visit = optVisit.get();

        visit.setCheckOut(LocalDateTime.now());
        visit.setNotes("Visita finalizada y reporte generado");
        visitRepository.save(visit);

        Map<String, String> response = new HashMap<>();
        try {
            byte[] pdfBytes = ReportGenerator.generateVisitReport(visit);
            String asunto = "Informe de su visita";
            String cuerpo = "Estimado cliente,\n\nAdjunto encontrará el informe de su visita.";
            notificationService.sendNotification(visit.getId(), emailCliente, asunto, cuerpo, pdfBytes);

            response.put("message", "Visita finalizada y reporte enviado correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error al generar o enviar el reporte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> downloadVisitReport(@PathVariable Long id) {
        Optional<Visit> opt = visitRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Visit visit = opt.get();
        try {
            byte[] pdfBytes = ReportGenerator.generateVisitReport(visit);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "visita_" + id + ".pdf");
            headers.setContentLength(pdfBytes.length);
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
