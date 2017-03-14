package com.bgcourse.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Set;

import org.apache.hadoop.io.WritableComparable;

import com.google.gson.Gson;

public class StoreWritable implements WritableComparable<StoreWritable> {

  private Store store;

  public StoreWritable(Store store) {
    this.store = store;
  }

  @Override
  public int compareTo(StoreWritable storeWritable) {
    return this.store.compareTo(storeWritable.store);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    Gson gson = new Gson();
    out.writeUTF(gson.toJson(store));
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    Gson gson = new Gson();
    store = gson.fromJson(in.readUTF(), Store.class);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(store);
  }
}
