package org.example.memory;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeAllocator {

  private static final Unsafe unsafe;

  static {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);

      unsafe = (Unsafe) field.get(null);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private final long size;
  private long address;

  public UnsafeAllocator(long size) {
    address = unsafe.allocateMemory(size);
    this.size = size;
  }

  public long getSize() {
    return size;
  }

  public long getAddress() {
    return address;
  }

  public void free() {
    unsafe.freeMemory(address);
    address = 0;
  }
}
