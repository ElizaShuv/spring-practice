package ru.sber.repository;

import ru.sber.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    /**
     * Добавляет товар
     * @param product информация о товаре
     * @return возвращает id добавленного товара
     */
    long save(Product product);

    /**
     * Выполняет поиск товара по id
     * @param productId id товара
     * @return возвращает результат поиска
     */
    Optional<Product> findById(long productId);

    /**
     * Выполняет поиск товаров по названию
     * @param productName название товара
     * @return возвращает список товаров
     */
    List<Product> findAll(String productName);

    /**
     * Обновляет информацию о товаре
     * @param product товар, который нужно изменить
     * @return возвращает true или false в зависимости от того, обновлена информация или нет
     */
    boolean update(Product product);

    /**
     * Удаляет товар по id
     * @param id id товара
     * @return возвращает true или false в зависимости от того, получилось удалить товар или нет
     */
    boolean deleteById(long id);
}
