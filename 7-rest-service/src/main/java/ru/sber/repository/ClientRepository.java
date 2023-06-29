package ru.sber.repository;

import ru.sber.model.Client;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClientRepository {
    long saveClient(Client client);

    List<Map<String, Object>> findClient(Long clientId);

    boolean deleteById(long clientId);
    Optional<Client> getById(long clientID);

}
