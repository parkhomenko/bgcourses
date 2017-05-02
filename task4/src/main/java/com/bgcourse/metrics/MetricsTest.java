package com.bgcourse.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

public class MetricsTest {

  static final MetricRegistry metrics = new MetricRegistry();

  public static void main(String[] args) {
    startReport();
    Meter requests = metrics.meter("requests");
    RandomNumberGenerator generator = new RandomNumberGenerator(requests);
    Thread thread = new Thread(generator);
    thread.start();

    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  static void startReport() {
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build();
    reporter.start(1, TimeUnit.SECONDS);
  }
}
