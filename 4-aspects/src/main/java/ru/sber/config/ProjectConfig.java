package ru.sber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
/**
 * Конфигурация
 */
@Configuration
@ComponentScan(basePackages = "ru.sber")
@EnableAspectJAutoProxy
public class ProjectConfig {
}