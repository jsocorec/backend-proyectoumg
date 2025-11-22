package com.visitas.auth.controller;

import com.visitas.auth.model.Role;
import com.visitas.auth.model.User;
import com.visitas.auth.repository.RoleRepository;
import com.visitas.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Crear nuevo usuario
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User userFromClient) {
        try {

            if (userRepository.findByUsername(userFromClient.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
            }

            String rolNombre = userFromClient.getRole().getName();
            Optional<Role> roleOpt = roleRepository.findByName(rolNombre);
            if (!roleOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null); // Retorna 400 Bad Request si el rol no existe
            }
            Role rolePersistido = roleOpt.get();
            userFromClient.setRole(rolePersistido);

            // --- HASHEA LA CONTRASEÑA ANTES DE GUARDAR ---
            if (userFromClient.getPassword() != null && !userFromClient.getPassword().isEmpty()) {
                userFromClient.setPassword(passwordEncoder.encode(userFromClient.getPassword()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            User nuevoUsuario = userRepository.save(userFromClient);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
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
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();

        // Actualiza username y email si vienen en userDetails
        if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
            // Opcional: Verifica si el nuevo username ya existe en otro usuario
            Optional<User> existingUserWithSameUsername = userRepository.findByUsername(userDetails.getUsername());
            if (existingUserWithSameUsername.isPresent() && !existingUserWithSameUsername.get().getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict si el username ya está tomado
            }
            user.setUsername(userDetails.getUsername());
        }
        if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
            user.setEmail(userDetails.getEmail());
        }

        // --- HASHEA LA CONTRASEÑA SOLO SI SE PROPORCIONA UNA NUEVA ---
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        // NOTA: Si userDetails.getPassword() es nulo o vacío, la contraseña existente del usuario NO se modifica.

        // Asume que userDetails.getRole() trae el objeto Role completo o su ID.
        // Si userDetails.getRole() es nulo, no se actualiza el rol.
        if (userDetails.getRole() != null) {
            String roleNombre = userDetails.getRole().getName(); // Asume que el objeto Role dentro de userDetails tiene el nombre
            Optional<Role> roleOpt = roleRepository.findByName(roleNombre);
            if (roleOpt.isPresent()) {
                user.setRole(roleOpt.get()); // Asigna el rol persistido
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Retorna 400 Bad Request si el rol no existe
            }
        }

        userRepository.save(user);
        return ResponseEntity.ok(user);
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
