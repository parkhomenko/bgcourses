package com.bgcourse.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StoreMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

  @Override
  protected void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    String line = value.toString();
    String[] parts = line.split(" ");

    int id = Integer.parseInt(parts[2]);
    String data = parts[3] + "," + parts[4];

    context.write(new IntWritable(id), new Text(data));
  }
}
