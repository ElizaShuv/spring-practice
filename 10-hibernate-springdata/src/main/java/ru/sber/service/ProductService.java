package ru.sber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.sber.model.Product;
import ru.sber.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

/**
 * Класс, содержащий методы для работы с продуктами
 */
@Service
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long save(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct.getProductId();
    }

    @Override
    public Optional<Product> findById(long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> findAll(String productName) {
        if (productName == null) {
            return productRepository.findAll();
        } else {
            return productRepository.findByProductNameContaining(productName);
        }
    }

    @Override
    public boolean update(Product product) {
        if (productRepository.existsById(product.getProductId())) {
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

