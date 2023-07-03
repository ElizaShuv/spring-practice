package ru.sber.proxy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.model.Cart;
import ru.sber.model.PayCard;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.sber.repository.CartRepository;
import ru.sber.repository.ProductRepository;


/**
 * Класс для работы оплаты продуктов
 */
@Component

public class LocalBankAppProxy implements BankAppProxy {

        private final CartRepository cartRepository;
        private final ProductRepository productRepository;

        public LocalBankAppProxy(CartRepository cartRepository, ProductRepository productRepository) {
            this.cartRepository = cartRepository;
            this.productRepository = productRepository;
        }


        private List<PayCard> payCardList = new ArrayList<>(List.of(
                new PayCard(1, BigDecimal.valueOf(10000)),
                new PayCard(2, BigDecimal.valueOf(100))
        )
        );

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
            Optional<Cart> clientBasket = cartRepository.getBasketById(basketId);
            if (clientBasket.isPresent()) {
                List<Product> products = clientBasket.get().getProductList();
                BigDecimal totalPrice = BigDecimal.ZERO;

                // Общая стоимость товаров в корзине
                for (Product product : products) {
                    BigDecimal productPrice = product.getPrice().multiply(BigDecimal.valueOf(product.getCount()));
                    totalPrice = totalPrice.add(productPrice);
                }

                BigDecimal result = payCard.get().getBalance().subtract(totalPrice);
                int comparisonResult = result.compareTo(BigDecimal.ZERO);

                if (comparisonResult >= 0) {
                    // Наличие достаточного количества продуктов в магазине
                    for (Product product : products) {
                        long productId = product.getProductId();
                        int purchasedCount = product.getCount();

                        Optional<Product> optionalProduct = productRepository.findById(productId);
                        if (optionalProduct.isPresent()) {
                            Product storedProduct = optionalProduct.get();
                            int availableCount = storedProduct.getCount();

                            if (purchasedCount > availableCount) {
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Недостаточное количество продуктов в магазине");
                            }
                        }
                    }

                    // Обновление количество продуктов
                    for (Product product : products) {
                        long productId = product.getProductId();
                        int purchasedCount = product.getCount();

                        Optional<Product> optionalProduct = productRepository.findById(productId);
                        if (optionalProduct.isPresent()) {
                            Product storedProduct = optionalProduct.get();
                            int availableCount = storedProduct.getCount();
                            storedProduct.setCount(availableCount - purchasedCount);
                            productRepository.update(storedProduct);
                        }
                        cartRepository.deleteProductFromBasket(basketId, productId);
                    }

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


