package com.visitas.auth.config;

import com.visitas.auth.model.Role;
import com.visitas.auth.model.User;
import com.visitas.auth.repository.RoleRepository;
import com.visitas.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            Role admin = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

            Role supervisor = roleRepository.findByName("SUPERVISOR")
                    .orElseGet(() -> roleRepository.save(new Role("SUPERVISOR")));

            Role technician = roleRepository.findByName("TECHNICIAN")
                    .orElseGet(() -> roleRepository.save(new Role("TECHNICIAN")));

            if (userRepository.findByUsername("admin").isEmpty()) {
                User u = new User();
                u.setUsername("admin");
                u.setPassword(encoder.encode("admin"));
                u.setEmail("admin@example.com");
                u.setRole(admin);
                userRepository.save(u);
                System.out.println("Usuario admin creado: admin / admin");
            } else {
                System.out.println("Usuario admin ya existe");
            }
        };
    }
}
