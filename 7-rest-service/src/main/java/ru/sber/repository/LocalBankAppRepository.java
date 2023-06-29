package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Basket;
import ru.sber.model.PayCard;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.sber.model.Client;
/**
 * Класс для работы с банковской картой
 */
@Repository

public class LocalBankAppRepository implements BankAppRepository {
    private ClientRepository clientRepository;
    private BasketRepository basketRepository;


    public LocalBankAppRepository(ClientRepository clientRepository, BasketRepository basketRepository) {
        this.clientRepository = clientRepository;
        this.basketRepository = basketRepository;
    }

    private List<PayCard> payCardList = new ArrayList<>(List.of(
            new PayCard(1,BigDecimal.valueOf(1000))
    )
    );
    @Override
    public Optional<PayCard> getPayCard(long clientId, BigDecimal balance) {
        Optional<Client> client = clientRepository.getById(clientId);
        if (client.isPresent()) {
            PayCard payCard = new PayCard(clientId, balance);
            payCardList.add(payCard);
            return Optional.of(payCard);
        }
        return Optional.empty();
    }

    @Override
    public Optional<PayCard> findById(long clientId) {
        return payCardList.stream()
                .filter(payCard -> payCard.getClientId() == clientId)
                .findAny();
    }
    @Override
    public boolean isPayment(long clientId) {
        Optional<PayCard> payCard = findById(clientId);
        if(payCard.isPresent()){
            Optional<Basket> clientBasket = basketRepository.getBasketById(clientId);
            if(clientBasket.isPresent()) {
                List<Product> products = clientBasket.get().getProductList();
                BigDecimal totalPrice = null;
                for (Product product : products) {
                    totalPrice = product.getPrice().multiply(BigDecimal.valueOf(product.getCount()));
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
