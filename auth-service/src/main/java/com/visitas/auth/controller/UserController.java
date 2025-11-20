package com.visitas.auth.controller;

import com.visitas.auth.model.Role;
import com.visitas.auth.model.User;
import com.visitas.auth.repository.RoleRepository;
import com.visitas.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Crear nuevo usuario
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User userFromClient) {
        try {
            String rolNombre = userFromClient.getRole().getName();
            Optional<Role> roleOpt = roleRepository.findByName(rolNombre);
            if (!roleOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }
            Role rolePersistido = roleOpt.get();

            userFromClient.setRole(rolePersistido);

            User nuevoUsuario = userRepository.save(userFromClient);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Obtener un usuario por ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Modificar usuario existente
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());

            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(userDetails.getPassword());
            }

            String roleNombre = userDetails.getRole() != null ? userDetails.getRole().getName() : null;
            if (roleNombre != null && !roleNombre.isEmpty()) {
                Optional<Role> roleOpt = roleRepository.findByName(roleNombre);
                if (roleOpt.isPresent()) {
                    user.setRole(roleOpt.get());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            }

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar usuario
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
