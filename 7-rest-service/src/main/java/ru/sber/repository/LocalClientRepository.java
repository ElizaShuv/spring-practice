package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Basket;
import ru.sber.model.Client;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Класс, содержащий методы для работы с клиентом
 */
@Repository
public class LocalClientRepository implements ClientRepository {
    private List<Client> clients = new ArrayList<>(List.of(
            new Client(1,"Иван", "ivan", "11111","ivan@mail.ru", new Basket(1,List.of(new Product(1,"Яблоко", BigDecimal.valueOf(50),1)), "CODE1")
    )));

    @Override
        public long saveClient(Client client) {
            long clientId = generateId();
            client.setClientId(clientId);

            clients.add(client);
            return clientId;
    }
    @Override
    public List<Map<String, Object>> findAll(Long clientId) {
        return clients.stream()
                .filter(client -> clientId == null || client.getClientId() == clientId)
                .map(client -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("clientId", client.getClientId());
                    response.put("clientName", client.getClientName());
                    response.put("email", client.getEmail());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(long clientId) {
        return clients.removeIf(product -> product.getClientId() == clientId);
    }

    private long generateId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}