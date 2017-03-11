package com.lohika.bgcourses.imdb.ratings;

import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieRatingLineParser {

  private static final String regex = "\\s{6}([^\\s]*)\\s{2}([^\\s]*)\\s{3}([^\\s]*)\\s{2}(.*)";
  Pattern pattern;

  public MovieRatingLineParser() {
    pattern = Pattern.compile(regex);
  }

  public Optional<MovieRating> parse(String line) {
    Matcher matcher = pattern.matcher(line);
    if (matcher.matches()) {
      String distribution = matcher.group(1);
      String votesGroup = matcher.group(2);
      int votes = Integer.parseInt(votesGroup.isEmpty() ? "0" : votesGroup);
      String rankGroup = matcher.group(3);
      float rank = Float.parseFloat(rankGroup.isEmpty() ? "0.0" : rankGroup);
      String title = matcher.group(4);

      return Optional.of(new MovieRating(distribution, votes, rank, title));
    }

    return Optional.empty();
  }
}
