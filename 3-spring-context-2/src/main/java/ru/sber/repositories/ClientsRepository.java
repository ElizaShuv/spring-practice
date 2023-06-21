package ru.sber.repositories;

import ru.sber.model.Client;

import java.util.List;


public interface ClientsRepository {
    List<Client> getClients();
}
