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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Store store = (Store) o;

    if (id != store.id) {
      return false;
    }
    if (Double.compare(store.sum, sum) != 0) {
      return false;
    }
    return hosts != null ? hosts.equals(store.hosts) : store.hosts == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = id;
    temp = Double.doubleToLongBits(sum);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (hosts != null ? hosts.hashCode() : 0);
    return result;
  }
}
