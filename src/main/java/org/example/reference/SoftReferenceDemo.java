package org.example.reference;

public class SoftReferenceDemo {
  public static void main(String[] args) {
    // Soft reference example
    Object strongReference = new Object();
    java.lang.ref.SoftReference<Object> softReference = new java.lang.ref.SoftReference<>(strongReference);

    // The object is strongly reachable, so it won't be garbage collected
    System.out.println("Soft reference created: " + softReference.get());

    // Nullify the strong reference
    strongReference = null;

    // Suggest to the JVM to run garbage collection
    System.gc();

    // The object may still be available through the soft reference
    System.out.println("After GC, softReference points to: " + softReference.get());

    // Allocate a large amount of memory to trigger garbage collection of soft references
    try {
      byte[] largeArray = new byte[100 * 1024 * 1024]; // 100 MB
    } catch (OutOfMemoryError e) {
      System.out.println("Caught OutOfMemoryError");
    }

    // After memory pressure, the soft reference may have been cleared
    System.out.println("After memory allocation, softReference points to: " + softReference.get());
  }
}
