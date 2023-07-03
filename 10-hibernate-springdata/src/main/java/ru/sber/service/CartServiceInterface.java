package ru.sber.service;

import ru.sber.model.Cart;

import java.util.Optional;

public interface CartServiceInterface {
    /**
     * Добавляет товар в корзину
     * @param basketId id корзины
     * @param productId id товара
     * @return возвращает корзину с добавленным товаром
     */
    boolean addProductToBasket(long basketId, long productId, int count);

    /**
     * Получает корзину по id
     * @param basketId id корзины
     * @return Возвращает true или false  зависимости от того, добавлен продукт или нет
     */
    Optional<Cart> getBasketById(long basketId);

    /**
     * Изменяет количество товара в корзине
     * @param basketId id корзины
     * @param productId id товара
     * @param count id новое количество товара
     * @return Возвращает корзину с измененным количеством товара
     */
    Optional<Cart> changeProductCount(long basketId, long productId, int count);

    /**
     * Удаляет товар из корзины
     * @param basketId id корзины
     * @param productId id продукта
     * @return Возвращает корзину с удаленным продуктом
     */
    Optional<Cart> deleteProductFromBasket(long basketId, long productId);

}
