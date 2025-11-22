package com.visitas.auth.dto;

import com.visitas.auth.model.Visit;

import java.time.LocalDateTime;

public class VisitDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private Long technicianId;
    private String technicianUsername;
    private Long supervisorId;
    private String supervisorUsername;
    private LocalDateTime scheduledAt;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String notes;
    private String status;

    // Constructor que toma una entidad Visit y la convierte a VisitDTO
    public VisitDTO(Visit visit) {
        this.id = visit.getId();
        this.clientId = visit.getClient() != null ? visit.getClient().getId() : null;
        this.clientName = visit.getClient() != null ? visit.getClient().getName() : null;
        this.technicianId = visit.getTechnician() != null ? visit.getTechnician().getId() : null;
        this.technicianUsername = visit.getTechnician() != null ? visit.getTechnician().getUsername() : null;
        this.supervisorId = visit.getSupervisor() != null ? visit.getSupervisor().getId() : null;
        this.supervisorUsername = visit.getSupervisor() != null ? visit.getSupervisor().getUsername() : null;
        this.scheduledAt = visit.getScheduledAt();
        this.checkIn = visit.getCheckIn();
        this.checkOut = visit.getCheckOut();
        this.notes = visit.getNotes();

        this.status = visit.getCheckOut() != null ? "Finalizada" : (visit.getCheckIn() != null ? "En sitio" : "Pendiente");
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public Long getTechnicianId() { return technicianId; }
    public void setTechnicianId(Long technicianId) { this.technicianId = technicianId; }
    public String getTechnicianUsername() { return technicianUsername; }
    public void setTechnicianUsername(String technicianUsername) { this.technicianUsername = technicianUsername; }
    public Long getSupervisorId() { return supervisorId; }
    public void setSupervisorId(Long supervisorId) { this.supervisorId = supervisorId; }
    public String getSupervisorUsername() { return supervisorUsername; }
    public void setSupervisorUsername(String supervisorUsername) { this.supervisorUsername = supervisorUsername; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public LocalDateTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDateTime checkIn) { this.checkIn = checkIn; }
    public LocalDateTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDateTime checkOut) { this.checkOut = checkOut; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
