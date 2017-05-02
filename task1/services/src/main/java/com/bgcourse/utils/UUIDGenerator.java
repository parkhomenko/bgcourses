package com.bgcourse.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UUIDGenerator {

  private List<UUID> uuids;

  @PostConstruct
  public void setUp() {
    uuids = new ArrayList<>(5);
    for (int i = 0; i < uuids.size(); i++) {
      UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
      uuids.add(uid.randomUUID());
    }
  }

  public String getUUID() {
    UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    return uid.randomUUID().toString();
  }

  public String getPredefinedUUID() {
    int index = new Random().nextInt(5);
    return uuids.get(index).toString();
  }

  public String getRandomString() {
    Random random = new Random();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 1024; i++) {
      char symbol = (char) random.nextInt(256);
      builder.append(symbol);
    }
    return builder.toString();
  }
}
