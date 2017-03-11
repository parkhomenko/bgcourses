package com.lohika.bgcourses.imdb.ratings;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Optional;

import org.junit.Test;

public class MovieRatingLineParserTest {

  MovieRatingLineParser movieRatingLineParser = new MovieRatingLineParser();

  @Test
  public void shouldReturnShawshankMovieInfo() {
    String line = "      0000000125  1775660   9.2  The Shawshank Redemption (1994)";
    Optional<MovieRating> movieRatingWrapper = movieRatingLineParser.parse(line);
    assertThat(movieRatingWrapper.isPresent(), is(true));

    MovieRating movieRating = movieRatingWrapper.get();
    assertThat(movieRating.getDistribution(), is("0000000125"));
    assertThat(movieRating.getVotes(), is(1775660));
    assertThat(movieRating.getRank(), is(9.2));
    assertThat(movieRating.getTitle(), is("The Shawshank Redemption (1994)"));
  }

  @Test
  public void infoShouldNotBePresent() {
    String line = "  weighted rank = (v/(v+k))*X + (k/(v+k))*C";
    Optional<MovieRating> movieRatingWrapper = movieRatingLineParser.parse(line);
    assertThat(movieRatingWrapper.isPresent(), is(false));
  }
}
