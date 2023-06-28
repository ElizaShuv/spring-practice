package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Basket;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Класс, содержащий методы для работы с корзиной
 */
@Repository
public class LocalBasketRepository implements BasketRepository{

    private final List<Basket> baskets = new ArrayList<>(List.of(
            new Basket(1, new ArrayList<>(List.of(new Product(3, "Персик", BigDecimal.valueOf(30),1))), "CODE3")
    ));

    private final ProductRepository productRepository;

    public LocalBasketRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public boolean addProductToBasket(long basketId, long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            for (Basket basket : baskets) {
                if (basket.getBasketId() == basketId) {
                    Product newProduct = new Product(product.getProductId(), product.getProductName(), product.getPrice(), product.getCount());
                    basket.getProductList().add(newProduct);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Optional<Basket> getBasketById(long basketId) {
        return baskets.stream()
                .filter(basket -> basket.getBasketId() == basketId)
                .findFirst();
    }



}
