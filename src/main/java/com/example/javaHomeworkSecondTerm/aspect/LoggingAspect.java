package com.example.javaHomeworkSecondTerm.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.Duration;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.javaHomeworkSecondTerm.controller..*(..))")
    public void logMethodCall() {
        System.out.println("Метод контроллера вызван");
    }

    @Around("execution(* com.example.javaHomeworkSecondTerm.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        Object result = joinPoint.proceed();
        Instant end = Instant.now();
        System.out.printf("Время выполнения %s: %d мс%n", joinPoint.getSignature().getName(), Duration.between(start, end).toMillis());
        return result;
    }
}
