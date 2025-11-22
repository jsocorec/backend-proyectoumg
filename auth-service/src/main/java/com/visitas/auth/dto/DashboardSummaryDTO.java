package com.visitas.auth.dto;

import java.util.List;

public class DashboardSummaryDTO {
    private String role; // Rol del usuario actual
    private String welcomeMessage;
    private Long totalUsers; // Solo para Admin
    private Long totalClients; // Solo para Admin
    private Long totalVisitsSystem; // Total de visitas en todo el sistema (solo Admin)
    private List<VisitStatusSummary> visitsByStatusSystem; // Resumen de visitas por estado (solo Admin)

    private Long totalVisitsAssigned; // Total de visitas asignadas al usuario o a su equipo (Supervisor/Técnico)
    private List<VisitStatusSummary> visitsByStatusAssigned; // Resumen de visitas asignadas por estado (Supervisor/Técnico)
    private List<VisitDTO> assignedVisitsDetail; // Lista de visitas detalladas (para Supervisor/Técnico)

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getWelcomeMessage() { return welcomeMessage; }
    public void setWelcomeMessage(String welcomeMessage) { this.welcomeMessage = welcomeMessage; }

    public Long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }
    public Long getTotalClients() { return totalClients; }
    public void setTotalClients(Long totalClients) { this.totalClients = totalClients; }
    public Long getTotalVisitsSystem() { return totalVisitsSystem; }
    public void setTotalVisitsSystem(Long totalVisitsSystem) { this.totalVisitsSystem = totalVisitsSystem; }
    public List<VisitStatusSummary> getVisitsByStatusSystem() { return visitsByStatusSystem; }
    public void setVisitsByStatusSystem(List<VisitStatusSummary> visitsByStatusSystem) { this.visitsByStatusSystem = visitsByStatusSystem; }

    public Long getTotalVisitsAssigned() { return totalVisitsAssigned; }
    public void setTotalVisitsAssigned(Long totalVisitsAssigned) { this.totalVisitsAssigned = totalVisitsAssigned; }
    public List<VisitStatusSummary> getVisitsByStatusAssigned() { return visitsByStatusAssigned; }
    public void setVisitsByStatusAssigned(List<VisitStatusSummary> visitsByStatusAssigned) { this.visitsByStatusAssigned = visitsByStatusAssigned; }
    public List<VisitDTO> getAssignedVisitsDetail() { return assignedVisitsDetail; }
    public void setAssignedVisitsDetail(List<VisitDTO> assignedVisitsDetail) { this.assignedVisitsDetail = assignedVisitsDetail; }
}
