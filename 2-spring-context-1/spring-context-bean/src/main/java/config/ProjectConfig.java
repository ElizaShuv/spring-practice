package config;

import beans.Cat;
import beans.Dog;
import beans.Parrot;
import beans.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    public Parrot parrot1() {
        Parrot p = new Parrot();
        p.setName("Попугай №1");
        return p;
    }

    @Bean
    public Parrot parrot2() {
        Parrot p = new Parrot();
        p.setName("Попугай №2");
        return p;
    }

    @Bean
    public Dog dog() {
        Dog d = new Dog();
        d.setName("Сырок");
        return d;
    }
    @Bean
    public Cat cat(){
        Cat c = new Cat();
        c.setName("Гибискис");
        return c;
    }

    @Bean
    public Person person() {
        Person p = new Person();
        p.setName("Елизавета");
        p.setFirstParrot(parrot1());
        p.setSecondParrot(parrot2());
        p.setDog(dog());
        p.setCat(cat());
        return p;
    }


}
