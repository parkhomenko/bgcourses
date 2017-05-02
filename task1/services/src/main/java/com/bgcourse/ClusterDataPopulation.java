package com.bgcourse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.bgcourse.services.MovieRatingService;

public class ClusterDataPopulation {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 5; i++) {
      executorService.execute(() -> {
        MovieRatingService service = new MovieRatingService();
        service.addRecords();
      });
    }
    executorService.shutdown();
  }
}
