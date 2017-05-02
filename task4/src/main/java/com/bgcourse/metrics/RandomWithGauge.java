package com.bgcourse.metrics;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

public class RandomWithGauge {

  private final Random random;

  public RandomWithGauge(MetricRegistry metrics, String name) {
    random = new Random();
    metrics.register(MetricRegistry.name(RandomWithGauge.class, name, "random_number"),
        (Gauge<Integer>) () -> random.nextInt(100));
  }

  public static void main(String[] args) {
    MetricRegistry metrics = new MetricRegistry();
    graphiteReport(metrics);
    new RandomWithGauge(metrics, "randomizer");
    wait5Seconds();
  }

  static void graphiteReport(MetricRegistry metrics) {
    Graphite graphite = new Graphite(new InetSocketAddress("localhost", 2003));
    GraphiteReporter reporter = GraphiteReporter.forRegistry(metrics)
        .prefixedWith("java.localhost")
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .filter(MetricFilter.ALL)
        .build(graphite);
    reporter.start(1, TimeUnit.SECONDS);
  }

  static void wait5Seconds() {
    try {
      Thread.sleep(30 * 1000);
    } catch (InterruptedException e) {
    }
  }
}
