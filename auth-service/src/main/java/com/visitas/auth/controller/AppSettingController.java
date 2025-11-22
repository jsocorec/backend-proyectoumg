package com.visitas.auth.controller;

import com.visitas.auth.model.AppSetting;
import com.visitas.auth.service.AppSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/settings")
public class AppSettingController {

    @Autowired
    private AppSettingService appSettingService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AppSetting>> getAllSettings() {
        return ResponseEntity.ok(appSettingService.getAllSettings());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AppSetting> getSettingById(@PathVariable Long id) {
        return appSettingService.getSettingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/key/{key}")
    public ResponseEntity<AppSetting> getSettingByKey(@PathVariable String key) {
        return appSettingService.getSettingByKey(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AppSetting> createSetting(@RequestBody AppSetting newSetting) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appSettingService.createSetting(newSetting));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AppSetting> updateSetting(@PathVariable Long id, @RequestBody AppSetting updatedSetting) {
        try {
            AppSetting result = appSettingService.updateSetting(id, updatedSetting);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        appSettingService.deleteSetting(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/key/{key}")
    public ResponseEntity<String> getPublicSettingByKey(@PathVariable String key) {

        if ("GOOGLE_MAPS_API_KEY".equalsIgnoreCase(key)) {
            return appSettingService.getSettingByKey(key)
                    .map(AppSetting::getSettingValue)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
