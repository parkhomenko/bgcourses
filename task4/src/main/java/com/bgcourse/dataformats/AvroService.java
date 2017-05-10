package com.bgcourse.dataformats;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import com.bgcourse.domain.Movie;

public class AvroService {

  public void write(Movie movie, OutputStream outputStream) throws IOException {
    DatumWriter<Movie> writer = new SpecificDatumWriter<>(Movie.class);
    Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
    writer.write(movie, encoder);
    encoder.flush();
    outputStream.close();
  }

  public Movie read(InputStream inputStream) throws IOException {
    DatumReader<Movie> reader = new SpecificDatumReader<>(Movie.class);
    Decoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
    return reader.read(null, decoder);
  }
}
