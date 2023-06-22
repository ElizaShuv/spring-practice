package ru.sber.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Аспект для проверки, что аргументы метода не null и не пустые
 */
@Aspect
@Component
public class NotEmptyAspect {
    @Before("@annotation(NotEmpty)")
    public void argumentsCheck(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?>[] parameterTypes = signature.getParameterTypes();

        for (int i = 0; i < arguments.length; i++) {
            Object arg = arguments[i];
            Class<?> parameterType = parameterTypes[i];
            if (arg == null) {
                throw new IllegalArgumentException("Аргумент не может быть null");
            } else if (parameterType == String.class) {
                if (((String) arg).isEmpty()) {
                    throw new IllegalArgumentException("Аргумент не может быть пустым");
                }
            } else if (Collection.class.isAssignableFrom(parameterType)) {
                if (((Collection<?>) arg).isEmpty()) {
                    throw new IllegalArgumentException("Аргумент не может быть пустым");
                }
            }
        }
    }
}


