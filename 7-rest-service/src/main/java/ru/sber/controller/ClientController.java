package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Client;
import ru.sber.repository.ClientRepository;


import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {

    private ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public long registrationClient(@RequestBody Client client) {
        log.info("Регистрация клиента {}", client);
        return clientRepository.saveClient(client);
    }

    @GetMapping
    public List<Map<String, Object>> getClients(@RequestParam(required = false) Long clientId) {
        log.info("Список клиентов по id {}", clientId);
        List<Map<String, Object>> clientResponses = clientRepository.findClient(clientId);
        return clientResponses;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = clientRepository.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
