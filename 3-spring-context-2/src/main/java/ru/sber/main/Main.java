package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.model.Aplication;
import ru.sber.services.AppService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var appService = context.getBean(AppService.class);

        Aplication aplication = new Aplication("+79211234567", BigDecimal.valueOf(150));
        appService.transferProcess(aplication);
    }
}
