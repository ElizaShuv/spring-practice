package ru.sber.repository;

import ru.sber.model.Basket;

import java.util.Optional;

public interface BasketRepository {
    /**
     * Добавляет товар в корзину
     * @param basketId id корзины
     * @param productId id товара
     * @return возвращает корзину с добавленным товаром
     */
    Optional<Basket> addProductToBasket(long basketId, long productId);

    /**
     * Получает корзину по id
     * @param basketId id корзины
     * @return Возвращает найденную корзину
     */
    Optional<Basket> getBasketById(long basketId);

    /**
     * Изменяет количество товара в корзине
     * @param basketId id корзины
     * @param productId id товара
     * @param count id новое количество товара
     * @return Возвращает корзину с измененным количеством товара
     */
    Optional<Basket> changeProductCount(long basketId, long productId, int count);

    /**
     * Удаляет товар из корзины
     * @param basketId id корзины
     * @param productId id продукта
     * @return Возвращает корзину с удаленным продуктом
     */
    Optional<Basket> deleteProductToBasket(long basketId, long productId);

}
