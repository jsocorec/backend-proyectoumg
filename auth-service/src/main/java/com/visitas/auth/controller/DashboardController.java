package com.visitas.auth.controller;

import com.visitas.auth.dto.DashboardSummaryDTO;
import com.visitas.auth.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Cualquier rol autenticado puede acceder a su dashboard
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<DashboardSummaryDTO> getDashboard() {
        DashboardSummaryDTO summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }
}
