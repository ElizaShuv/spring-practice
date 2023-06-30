package ru.sber.proxy;

import ru.sber.model.PayCard;

import java.util.Optional;

public interface BankAppProxy {
    /**
     * Получает карту по номеру
     * @param cardNum номер карты
     * @return возвращает карту
     */
    public Optional<PayCard> getPayCard(long cardNum);

    /**
     * Проверяет возможность проведения платежа с использованием указанной карты и корзины.
     *
     * @param cardNum   номер  карты
     * @param basketId  id корзины
     * @return возвращает true, если платеж возможен, иначе false
     */
    public boolean isPayment(long cardNum, long basketId);

}
