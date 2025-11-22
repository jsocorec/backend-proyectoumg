package com.visitas.auth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visit")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Client
    @ManyToOne(fetch = FetchType.EAGER) // EAGER para que se cargue con la visita
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Relación con el técnico (usuario)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technician_id")
    private User technician;

    // Relación con el supervisor (usuario)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supervisor_id")
    private User supervisor;

    private LocalDateTime scheduledAt;
    private LocalDateTime checkIn;
    private Double checkInLat;
    private Double checkInLng;
    private LocalDateTime checkOut;
    private Double checkOutLat;
    private Double checkOutLng;
    private String notes;

    public Visit() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public User getTechnician() { return technician; }
    public void setTechnician(User technician) { this.technician = technician; }
    public User getSupervisor() { return supervisor; }
    public void setSupervisor(User supervisor) { this.supervisor = supervisor; }
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
