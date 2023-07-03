package ru.sber.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
/**
 * Банковская карта
 */


@Data
@AllArgsConstructor

public class PayCard {

    private long cardNum;

    private BigDecimal balance;
}