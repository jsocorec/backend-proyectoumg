package com.visitas.auth.repository;

import com.visitas.auth.dto.VisitStatusSummary;
import com.visitas.auth.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByScheduledAtBetween(LocalDateTime start, LocalDateTime end);

    // Consulta para contar visitas por estado (Pendiente, En sitio, Finalizada) - Para ADMIN
    @Query("SELECT new com.visitas.auth.dto.VisitStatusSummary(CASE " +
            "WHEN v.checkOut IS NOT NULL THEN 'Finalizada' " +
            "WHEN v.checkIn IS NOT NULL THEN 'En sitio' " +
            "ELSE 'Pendiente' END, COUNT(v)) " +
            "FROM Visit v GROUP BY CASE " +
            "WHEN v.checkOut IS NOT NULL THEN 'Finalizada' " +
            "WHEN v.checkIn IS NOT NULL THEN 'En sitio' " +
            "ELSE 'Pendiente' END")
    List<VisitStatusSummary> countVisitsByStatus();

    // Consultas para Supervisor/Técnico
    List<Visit> findByTechnicianId(Long technicianId);
    List<Visit> findBySupervisorId(Long supervisorId);

    // Consulta para contar visitas por estado para un técnico específico
    @Query("SELECT new com.visitas.auth.dto.VisitStatusSummary(CASE " +
            "WHEN v.checkOut IS NOT NULL THEN 'Finalizada' " +
            "WHEN v.checkIn IS NOT NULL THEN 'En sitio' " +
            "ELSE 'Pendiente' END, COUNT(v)) " +
            "FROM Visit v WHERE v.technician.id = :technicianId GROUP BY CASE " +
            "WHEN v.checkOut IS NOT NULL THEN 'Finalizada' " +
            "WHEN v.checkIn IS NOT NULL THEN 'En sitio' " +
            "ELSE 'Pendiente' END")
    List<VisitStatusSummary> countVisitsByStatusForTechnician(@Param("technicianId") Long technicianId);

    // Consulta para contar visitas por estado para un supervisor específico
    @Query("SELECT new com.visitas.auth.dto.VisitStatusSummary(CASE " +
            "WHEN v.checkOut IS NOT NULL THEN 'Finalizada' " +
            "WHEN v.checkIn IS NOT NULL THEN 'En sitio' " +
            "ELSE 'Pendiente' END, COUNT(v)) " +
            "FROM Visit v WHERE v.supervisor.id = :supervisorId GROUP BY CASE " +
            "WHEN v.checkOut IS NOT NULL THEN 'Finalizada' " +
            "WHEN v.checkIn IS NOT NULL THEN 'En sitio' " +
            "ELSE 'Pendiente' END")
    List<VisitStatusSummary> countVisitsByStatusForSupervisor(@Param("supervisorId") Long supervisorId);
}
