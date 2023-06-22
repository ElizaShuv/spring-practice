package ru.sber.service;

import org.springframework.stereotype.Service;
import ru.sber.aspects.NotEmpty;


import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Класс реализующий методы с аннотацией @NotEmpty
 */
@Service
public class ArgsMethodService {
    private Logger logger = Logger.getLogger(ArgsMethodService.class.getName());

    @NotEmpty
    public void stringArgsMethod(String str1, String str2){
        logger.info("Заданы строки");
    }
    @NotEmpty
    public void collectionArgsMethod(Set<Integer> numbers){
        logger.info("Задана коллекция");
    }
    @NotEmpty
    public void  bothArgsMethod(List<Integer> numbers, String str ){
        logger.info("Задана коллекция и строка");
    }

    public void setLogger(Logger logger) {
        this.logger=logger;
    }
}
