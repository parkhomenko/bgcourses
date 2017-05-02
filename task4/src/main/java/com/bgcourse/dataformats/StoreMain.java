package com.bgcourse.dataformats;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class StoreMain extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {
    Job job = Job.getInstance(getConf());
    job.setJobName("store conversion");
    job.setJarByClass(StoreMain.class);

    job.setMapOutputKeyClass(IntWritable.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(NullWritable.class);
    //job.setOutputValueClass(StoreWritable.class);

    //job.setMapperClass(StoreMapper.class);
    //job.setReducerClass(StoreReducer.class);

    Path inputFilePath = new Path(args[0]);
    Path outputFilePath = new Path(args[1]);

    FileInputFormat.addInputPath(job, inputFilePath);
    FileOutputFormat.setOutputPath(job, outputFilePath);

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new StoreMain(), args);
    System.exit(exitCode);
  }
}
