package ru.sber.service;

import ru.sber.model.Client;
import java.util.List;
import java.util.Map;

public interface ClientServiceInterface {
    /**
     * Добавляет клиента
     * @param client Данные клиента
     * @return возвращает id клиента
     */
    long saveClient(Client client);

    /**
     * Выбирает часть информации о клиенте
     * @param clientId id клиента
     * @return возвращает часть информации о клиенте
     */
    List<Map<String, Object>> findClient(Long clientId);

    /**
     * Удаляет клиента
     * @param clientId id клиента
     * @return возвращает true или false в зависимости от того, удалился клиент или нет
     */
    boolean deleteById(long clientId);

}
