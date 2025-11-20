package com.visitas.auth.controller;

import com.visitas.auth.model.Client;
import com.visitas.auth.repository.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Listar todos
    @GetMapping
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        return clientOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nuevo
    @PostMapping
    public Client create(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client clientDetails) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Client client = clientOpt.get();
        client.setName(clientDetails.getName());
        client.setAddress(clientDetails.getAddress());
        client.setLatitude(clientDetails.getLatitude());
        client.setLongitude(clientDetails.getLongitude());
        client.setEmail(clientDetails.getEmail());
        clientRepository.save(client);
        return ResponseEntity.ok(client);
    }

    // Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
