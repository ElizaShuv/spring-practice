package ru.sber.model;

import java.math.BigDecimal;
/**
 * Сущность перевода
 */
public class Aplication {
    private String phone;
    private BigDecimal sum;

    public Aplication(String phone, BigDecimal sum) {
        this.phone = phone;
        this.sum = sum;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getSum() {
        return sum;
    }
}
