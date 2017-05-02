package com.bgcourse.metrics;

import java.util.Random;

import com.codahale.metrics.Meter;

public class RandomNumberGenerator implements Runnable {

  Random random = new Random();
  Meter requests;

  public RandomNumberGenerator(Meter requests) {
    this.requests = requests;
  }

  @Override
  public void run() {
    while (true) {
      int number = random.nextInt(1000);
      requests.mark(number);
    }
  }
}
