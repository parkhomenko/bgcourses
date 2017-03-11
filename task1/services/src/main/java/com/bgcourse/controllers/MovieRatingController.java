package com.bgcourse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bgcourse.entities.MovieRating;
import com.bgcourse.services.MovieRatingService;

@RestController
public class MovieRatingController {

  @Autowired
  MovieRatingService movieRatingService;

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public void addMovie(@RequestBody MovieRating movieRating) {
    movieRatingService.addRecord(movieRating);
  }

  @RequestMapping("/addpack")
  public void addMoviePack() {
    movieRatingService.addBanchOfRecords();
  }

  @RequestMapping("/title/full/{text}")
  public List<MovieRating> getByTitle(@PathVariable String text) {
    return movieRatingService.getRatingsByTitle(text);
  }

  @RequestMapping("/title/{prefix}")
  public List<MovieRating> getByPrefix(@PathVariable String prefix) {
    return movieRatingService.getRatingsByPrefix(prefix);
  }
}
