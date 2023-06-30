package ru.sber.proxy;

import org.springframework.stereotype.Component;
import ru.sber.model.Basket;
import ru.sber.model.PayCard;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.sber.repository.BasketRepository;


/**
 * Класс для работы с банковской картой
 */
@Component

public class LocalBankAppProxy implements BankAppProxy {
    private BasketRepository basketRepository;

    public LocalBankAppProxy(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    private List<PayCard> payCardList = new ArrayList<>(List.of(
            new PayCard(1,BigDecimal.valueOf(1000))
    )
    );
    @Override
    public Optional<PayCard> getPayCard(long cardNum) {
        return payCardList.stream()
                .filter(payCard -> payCard.getCardNum() == cardNum)
                .findAny();
    }
    @Override
    public boolean isPayment(long cardNum, long basketId) {
        Optional<PayCard> payCard = getPayCard(cardNum);
        if (payCard.isPresent()) {
            Optional<Basket> clientBasket = basketRepository.getBasketById(basketId);
            if (clientBasket.isPresent()) {
                List<Product> products = clientBasket.get().getProductList();
                BigDecimal totalPrice = BigDecimal.ZERO;
                for (Product product : products) {
                    BigDecimal productPrice = product.getPrice().multiply(BigDecimal.valueOf(product.getCount()));
                    totalPrice = totalPrice.add(productPrice);
                }
                BigDecimal result = payCard.get().getBalance().subtract(totalPrice);

                int comparisonResult = result.compareTo(BigDecimal.ZERO);

                if (comparisonResult >= 0) {
                    payCard.get().setBalance(result);
                    return true;
                }
            }
        }
        return false;
    }

}
