package com.visitas.auth.service;

import com.visitas.auth.model.Notification;
import com.visitas.auth.repository.NotificationRepository;
import com.visitas.auth.util.EmailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(Long visitId, String recipient, String subject, String body, byte[] pdfBytes) {
        Notification n = new Notification();
        n.setVisitId(visitId);
        n.setRecipient(recipient);
        n.setSubject(subject);
        n.setBody(body);
        n.setSentAt(LocalDateTime.now());
        n.setStatus("PENDING");
        notificationRepository.save(n);

        try {
            EmailSender.sendEmail(recipient, subject, body, pdfBytes);
            n.setStatus("SENT");
            n.setError(null);
        } catch (Exception ex) {
            n.setStatus("FAILED");
            n.setError(ex.getMessage());
        } finally {
            n.setSentAt(LocalDateTime.now());
            notificationRepository.save(n);
        }
    }

}