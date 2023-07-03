package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sber.model.Client;
import ru.sber.service.ClientServiceInterface;


import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {

    private ClientServiceInterface clientRepository;

    public ClientController(ClientServiceInterface clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public ResponseEntity<?> registrationClient(@RequestBody Client client) {
        log.info("Регистрация клиента {}", client);

        long productId = clientRepository.saveClient(client);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public List<Map<String, Object>> getClients(@RequestParam(required = false) Long clientId) {
        log.info("Список клиентов по id {}", clientId);
        List<Map<String, Object>> clientResponses = clientRepository.findClient(clientId);
        return clientResponses;
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable long clientId) {
        boolean isDeleted = clientRepository.deleteById(clientId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
