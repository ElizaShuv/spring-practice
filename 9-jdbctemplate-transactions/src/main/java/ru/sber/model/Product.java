package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
/**
 * Продукт
 */
@Data
@AllArgsConstructor
public class Product {
    private long productId;
    private String productName;
    private BigDecimal price;
    private int count;
}
