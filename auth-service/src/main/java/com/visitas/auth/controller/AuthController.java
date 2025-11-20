package com.visitas.auth.controller;

import com.visitas.auth.model.User;
import com.visitas.auth.repository.UserRepository;
import com.visitas.auth.repository.RoleRepository;
import com.visitas.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost")
//@CrossOrigin(origins = "https://jesusnazarenodelasalvacion.com")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtil jwtUtil;

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (req == null || req.username == null || req.password == null || req.role == null) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Campos inválidos");
            return ResponseEntity.badRequest().body(err);
        }
        if (userRepository.findByUsername(req.username).isPresent()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Username ya existe");
            return ResponseEntity.badRequest().body(err);
        }
        Optional<com.visitas.auth.model.Role> roleOpt = roleRepository.findByName(req.role);
        if (roleOpt.isEmpty()) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Role inválido");
            return ResponseEntity.badRequest().body(err);
        }
        User u = new User();
        u.setUsername(req.username);
        u.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(req.password));
        u.setEmail(req.email);
        u.setRole(roleOpt.get());
        userRepository.save(u);

        Map<String, Object> resp = new HashMap<>();
        resp.put("id", u.getId());
        resp.put("username", u.getUsername());
        resp.put("email", u.getEmail());
        resp.put("role", roleOpt.get().getName());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username, req.password));
            User u = userRepository.findByUsername(req.username).orElseThrow();
            String role = (u.getRole() != null) ? u.getRole().getName() : "";
            String token = jwtUtil.generateToken(u.getUsername(), role);

            Map<String, Object> resp = new HashMap<>();
            resp.put("accessToken", token);
            resp.put("tokenType", "Bearer");
            resp.put("expiresIn", System.currentTimeMillis() + 3600000);
            return ResponseEntity.ok(resp);
        } catch (AuthenticationException ex) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Credenciales inválidas");
            return ResponseEntity.status(401).body(err);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> listUsers() {
        List<Map<String, Object>> users = userRepository.findAll().stream()
                .map(u -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", u.getId());
                    m.put("username", u.getUsername());
                    m.put("email", u.getEmail());
                    m.put("role", u.getRole() != null ? u.getRole().getName() : null);
                    return m;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}