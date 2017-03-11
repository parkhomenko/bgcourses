package com.bgcourse.utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bgcourse.entities.MovieRating;

@RunWith(MockitoJUnitRunner.class)
public class FileParserTest {

  @InjectMocks
  FileParser fileParser;

  @Mock
  MovieRatingLineParser movieRatingLineParser;

  @Mock
  MovieRating movieRating;

  @Mock
  Consumer consumer;

  @Before
  public void setUp() {
    fileParser.setFile("imdb/ratings.list.test");
  }

  @Test
  public void consumerShouldBeCalledTenTimes() {
    when(movieRatingLineParser.parse(anyString())).thenReturn(Optional.of(movieRating));
    fileParser.collect(consumer);

    verify(consumer, times(10)).accept(any());
  }

  @Test
  public void consumerShouldNeverBeCalled() {
    when(movieRatingLineParser.parse(anyString())).thenReturn(Optional.empty());
    fileParser.collect(consumer);

    verify(consumer, never()).accept(any());
  }
}
