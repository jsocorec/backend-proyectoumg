package com.visitas.auth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;
    private Long technicianId;
    private Long supervisorId;

    private LocalDateTime scheduledAt;

    private LocalDateTime checkIn;
    private Double checkInLat;
    private Double checkInLng;

    private LocalDateTime checkOut;
    private Double checkOutLat;
    private Double checkOutLng;

    @Lob
    private String notes;

    public Visit() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getTechnicianId() { return technicianId; }
    public void setTechnicianId(Long technicianId) { this.technicianId = technicianId; }

    public Long getSupervisorId() { return supervisorId; }
    public void setSupervisorId(Long supervisorId) { this.supervisorId = supervisorId; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public LocalDateTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDateTime checkIn) { this.checkIn = checkIn; }

    public Double getCheckInLat() { return checkInLat; }
    public void setCheckInLat(Double checkInLat) { this.checkInLat = checkInLat; }

    public Double getCheckInLng() { return checkInLng; }
    public void setCheckInLng(Double checkInLng) { this.checkInLng = checkInLng; }

    public LocalDateTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDateTime checkOut) { this.checkOut = checkOut; }

    public Double getCheckOutLat() { return checkOutLat; }
    public void setCheckOutLat(Double checkOutLat) { this.checkOutLat = checkOutLat; }

    public Double getCheckOutLng() { return checkOutLng; }
    public void setCheckOutLng(Double checkOutLng) { this.checkOutLng = checkOutLng; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
