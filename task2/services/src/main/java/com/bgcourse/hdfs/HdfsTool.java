package com.bgcourse.hdfs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class HdfsTool extends Configured implements Tool {

  public static void main(String[] args) throws Exception {
    int returnCode = ToolRunner.run(new HdfsTool(), args);
    System.exit(returnCode);
  }

  @Override
  public int run(String[] args) throws Exception {

    OptionBuilder optionBuilder = OptionBuilder.createOptions(args);

    if (optionBuilder.isHelp()) {
      optionBuilder.printHelp();
    }

    if (optionBuilder.isWriteOperation()) {
      String[] files = optionBuilder.getParameters("write");
      writeFileToHdfs(files[0], files[1]);
    }

    if (optionBuilder.isReadOperation()) {
      String[] files = optionBuilder.getParameters("read");
      readFileToHdfs(files[0], files[1]);
    }

    return 0;
  }

  private void readFileToHdfs(String localFile, String remoteFile)
      throws URISyntaxException, IOException {

    FileSystem fileSystem = FileSystem.get(getConfiguration());

    Path hdfsFile = new Path(remoteFile);
    InputStream inputStream = fileSystem.open(hdfsFile);
    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile));
    IOUtils.copyBytes(inputStream, outputStream, getConfiguration());
  }

  private void writeFileToHdfs(String localFile, String remoteFile)
      throws IOException, URISyntaxException {

    FileSystem fileSystem = FileSystem.get(getConfiguration());

    Path hdfsFile = new Path(remoteFile);
    if (fileSystem.exists(hdfsFile)) {
      fileSystem.delete(hdfsFile, true);
    }

    OutputStream outputStream = fileSystem.create(hdfsFile);
    InputStream inputStream = new BufferedInputStream(new FileInputStream(localFile));
    IOUtils.copyBytes(inputStream, outputStream, getConfiguration());
  }

  private Configuration getConfiguration() {
    Configuration configuration = getConf();
    configuration.set("fs.defaultFS", "hdfs://localhost:9000");
    return configuration;
  }
}
