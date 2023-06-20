package beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {

    private String name = "Елизавета";
    private final Parrot first_parrot;
    private final Parrot second_parrot;
    private final Cat cat;
    private final Dog dog;


    @Autowired
    public Person(Parrot parrot1, Parrot parrot2, Cat cat, Dog dog) {
        this.first_parrot = parrot1;
        this.second_parrot = parrot2;
        this.cat = cat;
        this.dog = dog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parrot getFirstParrot() {
        return first_parrot;
    }

    public Parrot getSecondParrot() {
        return second_parrot;
    }

    public Cat getCat(){
      return cat;
    }

    public Dog getDog(){
        return dog;
    }
}
