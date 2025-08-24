package org.example.concurrency;

import java.util.concurrent.ConcurrentHashMap;

public class Collections {

  public static void testConcurrencyHashMap() {
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    map.put("key1", "value1");

    map.get("key1");
  }

  public static void main(String[] args) {
    testConcurrencyHashMap();
  }
}
