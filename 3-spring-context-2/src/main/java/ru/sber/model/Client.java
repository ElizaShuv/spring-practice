package ru.sber.model;

/**
 * Сущность клиента
 */
public class Client {
    private String clientID;
    private String phone;

    public Client(String clientID, String phone) {
        this.clientID = clientID;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
