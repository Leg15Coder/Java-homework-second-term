package com.example.javaHomeworkSecondTerm.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class LoggingAspect {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Before("execution(* com.example.javaHomeworkSecondTerm.controller..*(..))")
    public void logMethodCall() {
        System.out.println("Метод контроллера вызван");
    }

    @Around("execution(* com.example.javaHomeworkSecondTerm.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        counter.incrementAndGet();
        Object result = joinPoint.proceed();
        counter.incrementAndGet();
        Instant end = Instant.now();
        System.out.printf("Время выполнения %s: %d мс%n", joinPoint.getSignature().getName(), Duration.between(start, end).toMillis());
        return result;
    }

    public int getCounter() {
        return counter.get();
    }
}
