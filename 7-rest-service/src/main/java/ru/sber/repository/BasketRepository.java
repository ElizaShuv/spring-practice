package ru.sber.repository;

import ru.sber.model.Basket;

import java.util.Optional;

public interface BasketRepository {
    boolean addProductToBasket(long basketId, long productId);
    Optional<Basket> getBasketById(long basketId);

}
