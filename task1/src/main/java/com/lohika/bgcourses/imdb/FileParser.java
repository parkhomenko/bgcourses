package com.lohika.bgcourses.imdb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

import com.lohika.bgcourses.imdb.ratings.MovieRating;
import com.lohika.bgcourses.imdb.ratings.MovieRatingLineParser;

public class FileParser {

  private String file;
  private MovieRatingLineParser movieRatingLineParser;
  private int totalLinesPart;

  public FileParser(String fileName, MovieRatingLineParser movieRatingLineParser) {
    file = FileParser.class.getClassLoader().getResource(fileName).getFile();
    this.movieRatingLineParser = movieRatingLineParser;
    totalLinesPart = getNumberOfLines() / 10;
  }

  private int getNumberOfLines() {
    int lines = 0;
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(
            new FileInputStream(file), StandardCharsets.UTF_8))) {
      while (reader.readLine() != null) {
        lines++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }

  public void collect(Consumer<MovieRating> consumer) {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(
            new FileInputStream(file), StandardCharsets.UTF_8))) {
      String line;
      int lines = 0;
      int progress = 0;
      while ((line = reader.readLine()) != null) {
        lines++;
        Optional<MovieRating> movieRating = movieRatingLineParser.parse(line);
        if (movieRating.isPresent()) {
          if (lines >= progress * totalLinesPart) {
            System.out.print("#");
            progress++;
          }
          consumer.accept(movieRating.get());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
