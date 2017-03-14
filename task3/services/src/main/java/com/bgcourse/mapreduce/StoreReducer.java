package com.bgcourse.mapreduce;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StoreReducer extends Reducer<IntWritable, Text, NullWritable, StoreWritable> {

  @Override
  protected void reduce(IntWritable key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {

    double sum = 0;
    Set<String> hosts = new HashSet<>();
    for (Text value : values) {
      String[] data = value.toString().split(",");
      sum += Double.parseDouble(data[0]);
      hosts.add(data[1]);
    }

    Store store = new Store(key.get(), sum, hosts);

    context.write(NullWritable.get(), new StoreWritable(store));
  }
}
