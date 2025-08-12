package org.example.reference;

import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {

  public static void main(String[] args) {
    // Phantom reference example
    Object strongReference = new Object();
    ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
    java.lang.ref.PhantomReference<Object> phantomReference = new java.lang.ref.PhantomReference<>(strongReference, referenceQueue);

    // The object is strongly reachable, so it won't be garbage collected
    System.out.println("Phantom reference created: " + phantomReference.get());

    // Nullify the strong reference
    strongReference = null;

    // Suggest to the JVM to run garbage collection
    System.gc();

    // The phantom reference will not return the object, as it is not strongly reachable
    System.out.println("After GC, phantomReference points to: " + phantomReference.get());

    // The phantom reference can be used with a ReferenceQueue to track when the object is collected
    Object collectedObject = referenceQueue.poll();
    if (collectedObject != null) {
      System.out.println("The object has been collected and the phantom reference is ready for cleanup.");
    } else {
      System.out.println("The object is still reachable, phantom reference not yet cleared.");
    }

    directByteBufferDemo();
  }

  public static void directByteBufferDemo() {
    // Direct ByteBuffer example
    java.nio.ByteBuffer directBuffer = java.nio.ByteBuffer.allocateDirect(1024);
    System.out.println("Direct ByteBuffer created: " + directBuffer);

    // The direct buffer is not garbage collected until the JVM exits
    System.out.println("Direct ByteBuffer is still reachable: " + directBuffer);

    // Nullify the reference to the direct buffer
    directBuffer = null;

    // Suggest to the JVM to run garbage collection
    System.gc();

    System.out.println("After setting directBuffer to null, directBuffer is: " + directBuffer);
  }
}
