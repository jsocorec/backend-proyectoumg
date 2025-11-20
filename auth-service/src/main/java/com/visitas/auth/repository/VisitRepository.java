package com.visitas.auth.repository;

import com.visitas.auth.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByScheduledAtBetween(LocalDateTime start, LocalDateTime end);
}
