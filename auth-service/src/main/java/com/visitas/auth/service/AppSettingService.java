// src/main/java/com/visitas/auth/service/AppSettingService.java
package com.visitas.auth.service;

import com.visitas.auth.model.AppSetting;
import com.visitas.auth.repository.AppSettingRepository;
import com.visitas.auth.util.AppSettingKeys; // Importa las nuevas claves
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;

@Service
public class AppSettingService {

    @Autowired
    private AppSettingRepository appSettingRepository;

    @PostConstruct
    public void initDefaultSettings() {

        if (appSettingRepository.findBySettingKey(AppSettingKeys.GOOGLE_MAPS_API_KEY).isEmpty()) {
            appSettingRepository.save(new AppSetting(null, AppSettingKeys.GOOGLE_MAPS_API_KEY, "TU_API_KEY_POR_DEFECTO", "Clave de API para la integración con Google Maps"));
        }

        if (appSettingRepository.findBySettingKey(AppSettingKeys.EMAIL_HOST).isEmpty()) {
            appSettingRepository.save(new AppSetting(null, AppSettingKeys.EMAIL_HOST, "mail.jesusnazarenodelasalvacion.com", "Servidor SMTP para envío de correos."));
        }
        if (appSettingRepository.findBySettingKey(AppSettingKeys.EMAIL_PORT).isEmpty()) {
            appSettingRepository.save(new AppSetting(null, AppSettingKeys.EMAIL_PORT, "587", "Puerto SMTP para envío de correos."));
        }
        if (appSettingRepository.findBySettingKey(AppSettingKeys.EMAIL_USERNAME).isEmpty()) {
            appSettingRepository.save(new AppSetting(null, AppSettingKeys.EMAIL_USERNAME, "inscripciones@jesusnazarenodelasalvacion.com", "Usuario para autenticación SMTP."));
        }
        if (appSettingRepository.findBySettingKey(AppSettingKeys.EMAIL_PASSWORD).isEmpty()) {
            appSettingRepository.save(new AppSetting(null, AppSettingKeys.EMAIL_PASSWORD, "Inscripciones2023", "Contraseña para autenticación SMTP."));
        }
        if (appSettingRepository.findBySettingKey(AppSettingKeys.EMAIL_USE_TLS).isEmpty()) {
            appSettingRepository.save(new AppSetting(null, AppSettingKeys.EMAIL_USE_TLS, "true", "Habilitar STARTTLS para el envío de correos (true/false)."));
        }

    }

    public List<AppSetting> getAllSettings() { /* ... */ return appSettingRepository.findAll(); }
    public Optional<AppSetting> getSettingByKey(String key) { /* ... */ return appSettingRepository.findBySettingKey(key); }
    public Optional<AppSetting> getSettingById(Long id) { /* ... */ return appSettingRepository.findById(id); }
    public AppSetting updateSetting(Long id, AppSetting updatedSetting) { /* ... */ return null; }
    public AppSetting createSetting(AppSetting newSetting) { /* ... */ return null; }
    public void deleteSetting(Long id) { /* ... */ }
}
