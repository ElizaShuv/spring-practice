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

    private  List<Basket> baskets = new ArrayList<>(List.of(
            new Basket(1, new ArrayList<>(List.of(new Product(3, "Персик", BigDecimal.valueOf(30),1))), "")
    ));

    private final ProductRepository productRepository;

    public LocalBasketRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public Optional<Basket> addProductToBasket(long basketId, long productId) {
        Optional<Basket> basket = getBasketById(basketId);
        Optional<Product> product = productRepository.findById(productId);
        if (basket.isPresent() && product.isPresent()) {
            Product addProduct = new Product(product.get().getProductId(), product.get().getProductName(), product.get().getPrice(), product.get().getCount());
            basket.get().getProductList().add(addProduct);
            return basket;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Basket> getBasketById(long basketId) {
        return baskets.stream()
                .filter(basket -> basket.getBasketId() == basketId)
                .findFirst();
    }

    @Override
    public Optional<Basket> changeProductCount(long basketId, long productId, int count) {
        Optional<Basket> basket = getBasketById(basketId);
        if (basket.isPresent()) {
            List<Product> productList = basket.get().getProductList();
            for (Product existingProduct : productList) {
                if (existingProduct.getProductId() == productId) {
                   existingProduct.setCount(count);
                    return basket;
                }
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<Basket> deleteProductToBasket(long basketId, long productId) {
        Optional<Basket> basket = getBasketById(basketId);
        Optional<Product> product = productRepository.findById(productId);
        if (basket.isPresent() && product.isPresent()) {
            basket.get().getProductList().removeIf(existingProduct -> existingProduct.getProductId() == productId);
            return basket;
        }
        return Optional.empty();
    }

    public Basket createBasket(long basketId) {
        List<Product> products = new ArrayList<>();
        Basket basket = new Basket(basketId, products, "");
        baskets.add(basket);
        return basket;
    }


}


