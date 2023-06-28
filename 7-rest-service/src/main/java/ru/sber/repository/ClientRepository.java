package ru.sber.repository;

import ru.sber.model.Client;
import java.util.List;
import java.util.Map;

public interface ClientRepository {
    long saveClient(Client client);

    List<Map<String, Object>> findAll(Long clientId);

    boolean deleteById(long clientId);

}
