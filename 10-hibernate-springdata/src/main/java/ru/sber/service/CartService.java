package ru.sber.service;

import org.springframework.stereotype.Service;
import ru.sber.model.Cart;

import ru.sber.model.Product;
import ru.sber.model.ProductCart;
import ru.sber.repository.CartRepository;

import java.util.List;
import java.util.Optional;


/**
 * Класс, содержащий методы для работы с корзиной
 */
@Service
public class CartService implements CartServiceInterface {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    @Override
    public boolean addProductToBasket(long basketId, long productId, int count) {
        Optional<Cart> optionalCart = cartRepository.findById(basketId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<ProductCart> productCarts = cart.getProductCarts();

            Optional<ProductCart> optionalProductCart = getProductCartByProductId(productCarts, productId);
            if (optionalProductCart.isPresent()) {
                ProductCart productCart = optionalProductCart.get();
                productCart.setCountCartProducts(productCart.getCountCartProducts() + count);
            } else {
                ProductCart newProductCart = createProductCart(productId, count);
                productCarts.add(newProductCart);
                cart.addProductCart(newProductCart);
            }

            cartRepository.save(cart);
            return true;
        }
        return false;
    }

    private ProductCart createProductCart(long productId, int count) {
        ProductCart productCart = new ProductCart();
        Product product = new Product();
        product.setProductId(productId);
        productCart.setProduct(product);
        productCart.setCountCartProducts(count);
        return productCart;
    }

    private Optional<ProductCart> getProductCartByProductId(List<ProductCart> productCarts, long productId) {
        return productCarts.stream()
                .filter(pc -> pc.getProduct().getProductId() == productId)
                .findFirst();
    }

    @Override
    public Optional<Cart> getBasketById(long basketId) {
        return cartRepository.findById(basketId);
    }

    @Override
    public Optional<Cart> changeProductCount(long basketId, long productId, int count) {
        Optional<Cart> optionalCart = cartRepository.findById(basketId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<ProductCart> productCarts = cart.getProductCarts();

            Optional<ProductCart> optionalProductCart = getProductCartByProductId(productCarts, productId);
            if (optionalProductCart.isPresent()) {
                ProductCart productCart = optionalProductCart.get();
                productCart.setCountCartProducts(count);
                cartRepository.save(cart);
                return Optional.of(cart);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Cart> deleteProductFromBasket(long basketId, long productId) {
        Optional<Cart> optionalCart = cartRepository.findById(basketId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<ProductCart> productCarts = cart.getProductCarts();

            Optional<ProductCart> optionalProductCart = getProductCartByProductId(productCarts, productId);
            if (optionalProductCart.isPresent()) {
                ProductCart productCart = optionalProductCart.get();
                productCarts.remove(productCart);
                cartRepository.save(cart);
                return Optional.of(cart);
            }
        }
        return Optional.empty();
    }


}