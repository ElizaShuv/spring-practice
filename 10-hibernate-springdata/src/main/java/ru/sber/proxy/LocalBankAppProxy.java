package ru.sber.proxy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.model.Cart;
import ru.sber.model.PayCard;
import ru.sber.model.Product;
import ru.sber.model.ProductCart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.sber.service.CartServiceInterface;
import ru.sber.service.ProductServiceInterface;


/**
 * Класс для работы оплаты продуктов
 */
@Component
    public class LocalBankAppProxy implements BankAppProxy {
    private final CartServiceInterface cartService;
    private final ProductServiceInterface productService;

    public LocalBankAppProxy(CartServiceInterface cartService, ProductServiceInterface productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    private List<PayCard> payCardList = new ArrayList<>(List.of(
            new PayCard(1, BigDecimal.valueOf(10000)),
            new PayCard(2, BigDecimal.valueOf(100))
    ));

    @Override
    public Optional<PayCard> getPayCard(long cardNum) {
        return payCardList.stream()
                .filter(payCard -> payCard.getCardNum() == cardNum)
                .findAny();
    }

    @Transactional
    @Override
    public ResponseEntity<String> isPayment(long cardNum, long basketId) {
        Optional<PayCard> payCard = getPayCard(cardNum);
        if (payCard.isPresent()) {
            Optional<Cart> clientBasket = cartService.getBasketById(basketId);
            if (clientBasket.isPresent()) {
                Cart basket = clientBasket.get();
                List<ProductCart> productCarts = basket.getProductCarts();
                BigDecimal totalPrice = BigDecimal.ZERO;

                // Общая стоимость товаров в корзине
                for (ProductCart productCart : productCarts) {
                    Product product = productCart.getProduct();
                    BigDecimal productPrice = product.getPrice().multiply(BigDecimal.valueOf(productCart.getCountCartProducts()));
                    totalPrice = totalPrice.add(productPrice);
                }

                BigDecimal result = payCard.get().getBalance().subtract(totalPrice);
                int comparisonResult = result.compareTo(BigDecimal.ZERO);

                if (comparisonResult >= 0) {
                    // Проверка наличия достаточного количества продуктов в магазине
                    for (ProductCart productCart : productCarts) {
                        long productId = productCart.getProduct().getProductId();
                        int purchasedCount = productCart.getCountCartProducts();

                        Optional<Product> optionalProduct = productService.findById(productId);
                        if (optionalProduct.isPresent()) {
                            Product storedProduct = optionalProduct.get();
                            int availableCount = storedProduct.getCount();

                            if (purchasedCount > availableCount) {
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Недостаточное количество продуктов в магазине");
                            }
                            storedProduct.setCount(availableCount - purchasedCount);
                        }
                    }

                    // Обновление количества продуктов в корзине
                    for (ProductCart productCart : productCarts) {
                        long productId = productCart.getProduct().getProductId();
                        int purchasedCount = productCart.getCountCartProducts();

                        ProductCart cartProduct = basket.getProductCarts().stream()
                                .filter(pc -> pc.getProduct().getProductId() == productId)
                                .findFirst()
                                .orElse(null);

                        if (cartProduct != null) {
                            cartProduct.setCountCartProducts(cartProduct.getCountCartProducts() - purchasedCount);
                        }
                    }
                    // Удаление продуктов из корзины, если их получилось купить
                    basket.getProductCarts().removeIf(pc -> pc.getCountCartProducts() <= 0);
                    payCard.get().setBalance(result);
                    return ResponseEntity.ok("Оплата прошла успешно");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Недостаточно средств на карте");
                }
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при обработке платежа");
    }
}