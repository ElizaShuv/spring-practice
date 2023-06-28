package ru.sber.repository;

import ru.sber.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    long save(Product product);
    Optional<Product> findById(long productId);
    List<Product> findAll(String productName);
    boolean update(Product product);
    boolean deleteById(long id);
}
