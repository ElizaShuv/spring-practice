package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.aspects.NotEmptyAspect;
import ru.sber.config.ProjectConfig;
import ru.sber.service.ArgsMethodService;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(NotEmptyAspect.class.getName());
    public static void main(String[] args) {
        var c = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var service = c.getBean(ArgsMethodService.class);
        try{
            service.stringArgsMethod("one","two");
            service.collectionArgsMethod(Set.of(1,2,3));
            service.bothArgsMethod(List.of(1),"numbers");
            service.bothArgsMethod(List.of(),"");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
