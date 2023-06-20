package main;

import beans.Person;
import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Person p = context.getBean(Person.class);

        System.out.println("Имя: " + p.getName());
        System.out.println("Первый попугай: "+p.getFirstParrot());
        System.out.println("Второй попугай: " + p.getSecondParrot());
        System.out.println("Кот: " + p.getCat());
        System.out.println("Собака: " + p.getDog());
    }
}
