package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.model.Aplication;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс для хранения истории переводов
 */
@Repository
public class DataBase {
    private List<Aplication> information = new ArrayList<>();

    public void addInformation(Aplication aplication) {
        information.add(aplication);
        System.out.println("Информация добавлена в базу");
    }
}
