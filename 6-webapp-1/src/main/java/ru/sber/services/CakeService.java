package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.model.Cake;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, содержащий список тортов и метод для их поиска
 */
@Service
public class CakeService {
    private List<Cake> cakes = new ArrayList<>(List.of(
            new Cake("Морковный торт", "Пряные морковно-ореховые бисквиты с сырным кремом и апельсиновым конфитюром", "img/cake9.jpg", "морковь, орехи, сырный крем, апельсин"),
        new Cake("Красный бархат","Бисквиты с легким шоколадным вкусом, сырным кремом и вишневым компоте", "img/cake10.jpg", "вишня, шоколад, сырный крем"),
        new Cake("Лавандовый торт","Ароматные лавандовые бисквиты, сырный крем и черничное компоте", "img/cake11.jpg", "лаванда, сырный крем, черника"),
        new Cake("Сникерс","Шоколадные бисквиты, сырно-шоколадный мусс с начинкой из соленой карамели и арахиса", "img/cake12.jpg", "шоколад, мусс, соленая карамель, арахис"),
        new Cake("Торт с чизкейком","Ванильные бисквиты, ягодная начинка и нежнейший чизкейк", "img/cake16.jpg", "ваниль, чизкейк"),
        new Cake("Чернично-ванильный","Ванильные бисквиты, черничный конфитюр и пломбирный крем", "img/cake14.jpg", "ваниль, черника, пломбирный крем")

)) ;

    public List<Cake> findAll() {
        return cakes;
    }

    public List<Cake> findCake(String cake_name) {
        List<Cake> foundCakes = new ArrayList<>();
        for (Cake cake : cakes) {
            if (cake.getCake_name().equalsIgnoreCase(cake_name) || cake.getInfo().toLowerCase().contains(cake_name.toLowerCase()) || cake.getTag().toLowerCase().contains(cake_name.toLowerCase())) {
                foundCakes.add(cake);
            }
        }
        return foundCakes;
    }

}
