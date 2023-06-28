package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Клиент
 */
@Data
@AllArgsConstructor
public class Client {
private long clientId;
    private String clientName;
    private String login;
    private String password;
    private String email;
    private Basket clientBasket;
}
