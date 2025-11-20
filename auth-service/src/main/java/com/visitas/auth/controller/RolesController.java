package com.visitas.auth.controller;

import com.visitas.auth.repository.RoleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
public class RolesController {
    private final RoleRepository roleRepository;

    public RolesController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/auth/roles")
    public List<Map<String, Object>> getRoles() {
        return roleRepository.findAll().stream()
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", r.getId());
                    map.put("name", r.getName());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
