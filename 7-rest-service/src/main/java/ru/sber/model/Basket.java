package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 * Корзина
 */
@Data
@AllArgsConstructor
public class Basket {
    private long basketId;
    private List<Product> productList;
    private String promocode;
}
