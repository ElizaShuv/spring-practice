package ru.sber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.model.Cart;
import ru.sber.model.Client;
import ru.sber.repository.ClientRepository;

import java.sql.*;
import java.util.*;
/**
 * Класс, содержащий методы для работы с клиентом
 */
@Service
public class ClientService implements ClientServiceInterface {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public long saveClient(Client client) {
        Cart cart = new Cart();
        cart.setProductCarts(new ArrayList<>());
        client.setClientBasket(cart);
        clientRepository.save(client);

        return client.getClientId();
    }


    @Override
    public List<Map<String, Object>> findClient(Long clientId) {
        return clientRepository.findClient(clientId);
    }

    @Override
    public boolean deleteById(long clientId) {
        clientRepository.deleteById(clientId);
        return true;
    }

}
