package com.bgcourse.entities;

public class MovieRating {
  private String distribution;
  private int votes;
  private float rank;
  private String title;

  public MovieRating() {
  }

  public MovieRating(String distribution, int votes, float rank, String title) {
    this.distribution = distribution;
    this.votes = votes;
    this.rank = rank;
    this.title = title;
  }

  public String getDistribution() {
    return distribution;
  }

  public int getVotes() {
    return votes;
  }

  public float getRank() {
    return rank;
  }

  public String getTitle() {
    return title;
  }
}
