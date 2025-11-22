package com.visitas.auth.service;

import com.visitas.auth.dto.DashboardSummaryDTO;
import com.visitas.auth.dto.VisitDTO;
import com.visitas.auth.model.User;
import com.visitas.auth.model.Visit;
import com.visitas.auth.repository.ClientRepository;
import com.visitas.auth.repository.UserRepository;
import com.visitas.auth.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VisitRepository visitRepository;

    public DashboardSummaryDTO getDashboardSummary() {
        // Obtener el usuario autenticado actualmente
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Cargar el usuario completo para acceder a su rol y ID
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado: " + currentUsername));

        DashboardSummaryDTO dashboard = new DashboardSummaryDTO();
        dashboard.setRole(currentUser.getRole().getName());
        dashboard.setWelcomeMessage("Bienvenido, " + currentUser.getUsername() + "!");

        // Lógica para llenar el DTO según el rol
        String userRole = currentUser.getRole().getName().toUpperCase();
        switch (userRole) {
            case "ADMIN":
                fillAdminDashboard(dashboard);
                break;
            case "SUPERVISOR":
                fillSupervisorDashboard(dashboard, currentUser.getId());
                break;
            case "TECNICO":
            case "TECH":
                fillTechnicianDashboard(dashboard, currentUser.getId());
                break;
            default:
                dashboard.setWelcomeMessage("Acceso restringido o rol no reconocido para dashboard.");
                break;
        }
        return dashboard;
    }

    private void fillAdminDashboard(DashboardSummaryDTO dashboard) {
        dashboard.setTotalUsers(userRepository.count());
        dashboard.setTotalClients(clientRepository.count());
        dashboard.setTotalVisitsSystem(visitRepository.count());
        dashboard.setVisitsByStatusSystem(visitRepository.countVisitsByStatus());

        List<Visit> allVisits = visitRepository.findAll();
        dashboard.setAssignedVisitsDetail(allVisits.stream().map(VisitDTO::new).collect(Collectors.toList()));
    }

    private void fillSupervisorDashboard(DashboardSummaryDTO dashboard, Long supervisorId) {

        List<Visit> supervisorVisits = visitRepository.findBySupervisorId(supervisorId);
        dashboard.setTotalVisitsAssigned((long) supervisorVisits.size());
        dashboard.setVisitsByStatusAssigned(visitRepository.countVisitsByStatusForSupervisor(supervisorId));
        dashboard.setAssignedVisitsDetail(supervisorVisits.stream().map(VisitDTO::new).collect(Collectors.toList()));
    }

    private void fillTechnicianDashboard(DashboardSummaryDTO dashboard, Long technicianId) {
        // Obtener solo las visitas asignadas a este técnico
        List<Visit> technicianVisits = visitRepository.findByTechnicianId(technicianId);
        dashboard.setTotalVisitsAssigned((long) technicianVisits.size());
        dashboard.setVisitsByStatusAssigned(visitRepository.countVisitsByStatusForTechnician(technicianId));
        dashboard.setAssignedVisitsDetail(technicianVisits.stream().map(VisitDTO::new).collect(Collectors.toList()));
    }
}
