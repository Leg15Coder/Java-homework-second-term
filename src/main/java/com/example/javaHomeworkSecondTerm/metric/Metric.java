package com.example.javaHomeworkSecondTerm.metric;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

@Component
public class Metric {
  private final Timer processingTimer;
  private final MeterRegistry meterRegistry;

  public Metric(MeterRegistry meterRegistry) {
    this.processingTimer = Timer.builder("outbox.process.timer")
        .description("Время, затраченное на обработку исходящих сообщений")
        .publishPercentiles(0.5, 0.75, 0.95, 0.99)
        .publishPercentileHistogram()
        .register(meterRegistry);

    this.meterRegistry = meterRegistry;
  }

  public void incrementError() {
    meterRegistry.counter("outbox.process.result.count", "status", "error").increment();
  }

  public void incrementSuccess() {
    meterRegistry.counter("outbox.process.result.count", "status", "success").increment();
  }

  public void recordWithTimer(Runnable runnable) {
    this.processingTimer.record(runnable);
  }
}
