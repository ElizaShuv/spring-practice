package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
/**
 * Банковская карта
 */
@Data
@AllArgsConstructor
public class PayCard {
    private long clientId;
    private BigDecimal balance;

}
