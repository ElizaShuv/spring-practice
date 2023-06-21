package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.model.Client;

import java.util.List;
/**
 * Список клиентов
 */
@Repository
public class DBClientsRepository implements ClientsRepository {

    @Override
    public List<Client> getClients() {
        return List.of(
                new Client("1", "+79211234567"),
                new Client("2", "+79211112233"),
                new Client("3", "+79212223344")
        );
    }


}
