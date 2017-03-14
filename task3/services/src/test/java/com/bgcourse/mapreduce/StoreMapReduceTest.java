package com.bgcourse.mapreduce;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

public class StoreMapReduceTest {
  MapDriver<LongWritable, Text, IntWritable, Text> mapDriver;
  ReduceDriver<IntWritable, Text, NullWritable, StoreWritable> reduceDriver;
  MapReduceDriver<LongWritable, Text, IntWritable, Text, NullWritable, StoreWritable> mapReduceDriver;

  @Before
  public void setUp() {
    StoreMapper mapper = new StoreMapper();
    StoreReducer reducer = new StoreReducer();

    mapDriver = MapDriver.newMapDriver(mapper);
    reduceDriver = ReduceDriver.newReduceDriver(reducer);
    mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
  }

  @Test
  public void testMapper() throws IOException {
    mapDriver.withInput(new LongWritable(), new Text("2014-07-02 20:52:39 1 12.01 www.store1.com"));
    mapDriver.withOutput(new IntWritable(1), new Text("12.01,www.store1.com"));
    mapDriver.runTest();
  }

  @Test
  public void testReducer() throws IOException {
    List<Text> values = Arrays.asList(
        new Text("12.01,www.store1.com"),
        new Text("9.14,www.store3.com"));

    Set<String> hosts = new HashSet<>();
    hosts.add("www.store1.com");
    hosts.add("www.store3.com");
    Store store = new Store(1, 21.15, hosts);
    StoreWritable storeWritable = new StoreWritable(store);

    reduceDriver.withInput(new IntWritable(1), values);
    reduceDriver.withOutput(NullWritable.get(), storeWritable);
    reduceDriver.runTest();
  }

  @Test
  public void testMapReduce() {
    Set<String> hosts = new HashSet<>();
    hosts.add("www.store1.com");
    Store store = new Store(1, 12.01, hosts);
    StoreWritable storeWritable = new StoreWritable(store);

    mapReduceDriver.withInput(new LongWritable(), new Text("2014-07-02 20:52:39 1 12.01 www.store1.com"));
    mapReduceDriver.withOutput(NullWritable.get(), storeWritable);
  }
}
