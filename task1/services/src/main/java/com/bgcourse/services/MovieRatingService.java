package com.bgcourse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bgcourse.entities.MovieRating;
import com.bgcourse.repository.RatingRepository;
import com.bgcourse.utils.FileParser;

import javax.annotation.PostConstruct;

@Service
public class MovieRatingService {

  public static final int NUMBER_OF_PACKS = 200;

  @Autowired
  FileParser fileParser;

  @Autowired
  RatingRepository ratingRepository;

  @PostConstruct
  public void setUp() {
    fileParser.setFile("imdb/ratings.list");
  }

  public List<MovieRating> getRatingsByPrefix(String prefix) {
    return ratingRepository.getRatings(prefix);
  }

  public List<MovieRating> getRatingsByTitle(String title) {
    return ratingRepository.getRatingsByTitle(title);
  }

  public void addBanchOfRecords() {
    for (int i = 0; i < NUMBER_OF_PACKS; i++) {
      System.out.print(i + ": [");
      fileParser.collect(ratingRepository::addBatch);
      ratingRepository.executeBatch();
      System.out.println("]");
    }
  }

  public void addBanchOfRecordsWithPredefinedPrimaryKeys() {
    for (int i = 0; i < NUMBER_OF_PACKS; i++) {
      System.out.print(i + ": [");
      fileParser.collect(ratingRepository::addBatchOfUpdatingRecords);
      ratingRepository.executeBatch();
      System.out.println("]");
    }
  }

  public void addRecord(MovieRating movieRating) {
    ratingRepository.add(movieRating);
  }
}
