package com.bgcourse.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UUIDGenerator {

  public String getUUID() {
    UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    return uid.randomUUID().toString();
  }
}
