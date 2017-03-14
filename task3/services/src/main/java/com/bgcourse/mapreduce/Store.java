package com.bgcourse.mapreduce;

import java.util.Set;

public class Store implements Comparable<Store> {
  private int id;
  private double sum;
  private Set<String> hosts;

  public Store(int id, double sum, Set<String> hosts) {
    this.id = id;
    this.sum = sum;
    this.hosts = hosts;
  }

  @Override
  public int compareTo(Store store) {
    return Integer.compare(id, store.id);
  }
}
