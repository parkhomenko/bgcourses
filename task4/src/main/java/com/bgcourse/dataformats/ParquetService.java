package com.bgcourse.dataformats;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import com.bgcourse.domain.Movie;

public class ParquetService {

  public void write(Movie movie, Path path) throws IOException {
    ParquetWriter<Movie> writer = AvroParquetWriter.<Movie>builder(path)
        .withSchema(Movie.SCHEMA$)
        .withCompressionCodec(CompressionCodecName.SNAPPY)
        .withPageSize(ParquetWriter.DEFAULT_PAGE_SIZE)
        .withDictionaryEncoding(true)
        .build();
    writer.write(movie);
    writer.close();
  }

  public Movie read(Path path) throws IOException {
    ParquetReader<Movie> reader = AvroParquetReader.<Movie>builder(path)
        .withCompatibility(true)
        .build();
    return reader.read();
  }
}
