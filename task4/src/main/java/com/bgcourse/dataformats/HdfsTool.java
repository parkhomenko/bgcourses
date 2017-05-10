package com.bgcourse.dataformats;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.bgcourse.domain.Movie;

public class HdfsTool extends Configured implements Tool {

  AvroService avroService = new AvroService();
  ParquetService parquetService = new ParquetService();

  public static void main(String[] args) throws Exception {
    int returnCode = ToolRunner.run(new HdfsTool(), args);
    System.exit(returnCode);
  }

  @Override
  public int run(String[] args) throws Exception {
    //writes file in avro format to hdfs
    writeFile("/user/hadoop/ratings/ratings-avro.list", Format.AVRO);

    //reads file from hdfs in avro format
    readFile("/user/hadoop/ratings/ratings-avro.list", Format.AVRO);

    //writes file in parquest format to hdfs
    writeFile("/user/hadoop/ratings/ratings-parquet.list", Format.PARQUET);

    //reads file from hdfs in parquet format
    readFile("/user/hadoop/ratings/ratings-parquet.list", Format.PARQUET);

    return 0;
  }

  private void readFile(String remoteFile, Format format) throws IOException {
    FileSystem fileSystem = FileSystem.get(getConfiguration());
    Path hdfsFile = new Path(remoteFile);
    Movie movie;
    switch (format) {
      case AVRO:
        InputStream inputStream = fileSystem.open(hdfsFile);
        movie = avroService.read(inputStream);
        break;
      case PARQUET:
        movie = parquetService.read(hdfsFile);
        break;
      default:
        throw new IllegalArgumentException();
    }
    System.out.println(movie);
  }

  private void writeFile(String remoteFile, Format format) throws IOException {
    FileSystem fileSystem = FileSystem.get(getConfiguration());
    Path hdfsFile = new Path(remoteFile);
    Movie movie = Movie.newBuilder()
        .setTitle("Guardians of the Galaxy")
        .setRating(10.0f)
        .setVotes(1000)
        .build();
    switch (format) {
      case AVRO:
        OutputStream outputStream = fileSystem.create(hdfsFile);
        avroService.write(movie, outputStream);
        break;
      case PARQUET:
        parquetService.write(movie, hdfsFile);
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  private Configuration getConfiguration() {
    Configuration configuration = getConf();
    configuration.set("fs.defaultFS", "hdfs://localhost:9000");
    return configuration;
  }
}
