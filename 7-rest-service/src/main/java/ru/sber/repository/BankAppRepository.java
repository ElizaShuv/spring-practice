package ru.sber.repository;

import ru.sber.model.PayCard;

import java.math.BigDecimal;
import java.util.Optional;

public interface BankAppRepository {
    Optional<PayCard> getPayCard(long clientId, BigDecimal balance);
    public boolean isPayment(long clientId);
    Optional<PayCard> findById(long clientId);
}
