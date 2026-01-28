package com.example.javaHomeworkSecondTerm.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

  @Bean
  public CircuitBreakerConfig circuitBreakerConfig() {
    return CircuitBreakerConfig.custom()
        .failureRateThreshold(50)
        .slidingWindowSize(100)
        .minimumNumberOfCalls(10)
        .waitDurationInOpenState(Duration.ofMillis(10000))
        .permittedNumberOfCallsInHalfOpenState(5)
        .build();
  }

  @Bean
  public RateLimiterConfig rateLimiterConfig() {
    return RateLimiterConfig.custom()
        .limitForPeriod(10)
        .limitRefreshPeriod(Duration.ofSeconds(1))
        .timeoutDuration(Duration.ofMillis(500))
        .build();
  }

  @Bean
  public TimeLimiterConfig timeLimiterConfig() {
    return TimeLimiterConfig.custom()
        .timeoutDuration(Duration.ofMillis(500))
        .build();
  }
}
