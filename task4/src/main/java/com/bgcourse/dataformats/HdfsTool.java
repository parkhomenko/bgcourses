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

  MovieAvroHdfsService service = new MovieAvroHdfsService();

  public static void main(String[] args) throws Exception {
    int returnCode = ToolRunner.run(new HdfsTool(), args);
    System.exit(returnCode);
  }

  @Override
  public int run(String[] args) throws Exception {
    //writes file in avro format to hdfs
    writeFile("/user/hadoop/ratings/ratings.list", Format.AVRO);

    //reads file from hdfs in avro format
    readFile("/user/hadoop/ratings/ratings.list", Format.AVRO);

    return 0;
  }

  private void readFile(String remoteFile, Format format) throws IOException {
    FileSystem fileSystem = FileSystem.get(getConfiguration());
    Path hdfsFile = new Path(remoteFile);
    InputStream inputStream = fileSystem.open(hdfsFile);
    switch (format) {
      case AVRO:
        Movie movie = service.readFromHdfs(inputStream);
        break;
      case PARQUET:
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  private void writeFile(String remoteFile, Format format) throws IOException {
    FileSystem fileSystem = FileSystem.get(getConfiguration());
    OutputStream outputStream = fileSystem.create(new Path(remoteFile));
    switch (format) {
      case AVRO:
        Movie movie = Movie.newBuilder()
            .setTitle("Guardians of the Galaxy")
            .setRating(10.0f)
            .setVotes(1000)
            .build();
        service.writeToHdfs(movie, outputStream);
        break;
      case PARQUET:
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
