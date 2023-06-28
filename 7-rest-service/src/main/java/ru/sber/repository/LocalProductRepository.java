package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
/**
 * Класс, содержащий методы для работы с продуктами
 */
@Repository
public class LocalProductRepository implements ProductRepository{
    private List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Яблоко", BigDecimal.valueOf(50),1 ),
            new Product(2, "Арбуз", BigDecimal.valueOf(150),1),
            new Product(3, "Персик", BigDecimal.valueOf(30),1)
    ));
@Override
    public long save(Product product) {
        long productId = generateId();
        product.setProductId(productId);

        products.add(product);
        return productId;
    }
    @Override
    public Optional<Product> findById(long productId) {
        return products.stream()
                .filter(product -> product.getProductId() == productId)
                .findAny();
    }
    @Override
    public List<Product> findAll(String productName) {
        if (productName == null) {
            return products;
        }

        return products.stream()
                .filter(product -> product.getProductName().equals(productName))
                .toList();
    }
    @Override
    public boolean update(Product product) {
        for (Product p : products) {
            if (p.getProductId() == product.getProductId()) {
                p.setProductName(product.getProductName());
                p.setPrice(product.getPrice());
                p.setCount(product.getCount());

                return true;
            }
        }
        return false;
    }
    @Override
    public boolean deleteById(long id) {
        return products.removeIf(product -> product.getProductId() == id);
    }

    private long generateId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}


