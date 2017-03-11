package com.bgcourse;

import com.bgcourse.services.MovieRatingService;

public class ClusterDataPopulation {

  public static void main(String[] args) {
    MovieRatingService service = new MovieRatingService();
    service.addBanchOfRecordsWithPredefinedPrimaryKeys();
  }
}
