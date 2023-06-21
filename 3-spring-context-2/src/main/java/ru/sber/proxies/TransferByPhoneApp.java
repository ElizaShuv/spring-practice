package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.model.Aplication;
/**
 * Класс для перевода денежных средств по номеру телефона
 */
@Component
public class TransferByPhoneApp {

    public void transferMassage(Aplication aplication) {
        System.out.printf("Выполнен перевод в размере %s руб. на номер %s %n", aplication.getSum(), aplication.getPhone());

    }
}
