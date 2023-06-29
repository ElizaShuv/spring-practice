package ru.sber.repository;

import ru.sber.model.Basket;

import java.util.Optional;

public interface BasketRepository {
    Optional<Basket> addProductToBasket(long basketId, long productId);
    Optional<Basket> getBasketById(long basketId);
    Optional<Basket> changeProductCount(long basketId, long productId, int count);
    Optional<Basket> deleteProductToBasket(long basketId, long productId);
    Basket createBasket(long basketId);
}
