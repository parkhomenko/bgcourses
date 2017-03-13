package com.bgcourse.hdfs;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class OptionBuilder {

  private CommandLine cmdLine;
  private Options options;

  public static OptionBuilder createOptions(String[] args) throws ParseException {
    Option writeOption = Option.builder("w")
        .longOpt("write")
        .required(false)
        .numberOfArgs(2)
        .argName("[local] [remote]")
        .desc("writes a file to hdfs")
        .build();

    Option readOption = Option.builder("r")
        .longOpt("read")
        .required(false)
        .numberOfArgs(2)
        .argName("[local] [remote]")
        .desc("reads a file from hdfs")
        .build();

    Option helpOption = Option.builder("h")
        .longOpt("help")
        .required(false)
        .desc("shows this message")
        .build();

    CommandLineParser parser = new DefaultParser();

    OptionBuilder optionBuilder = new OptionBuilder();
    optionBuilder.options = new Options();
    optionBuilder.options.addOption(writeOption);
    optionBuilder.options.addOption(readOption);
    optionBuilder.options.addOption(helpOption);
    optionBuilder.cmdLine = parser.parse(optionBuilder.options, args);

    return optionBuilder;
  }

  public boolean isHelp() {
    return cmdLine.hasOption("help");
  }

  public boolean isWriteOperation() {
    return cmdLine.hasOption("write") && !cmdLine.hasOption("read");
  }

  public boolean isReadOperation() {
    return cmdLine.hasOption("read") && !cmdLine.hasOption("write");
  }

  public String[] getParameters(String operation) {
    return cmdLine.getOptionValues(operation);
  }

  public void printHelp() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("com.bgcourse.hdfs.HdfsTool", options);
  }
}
