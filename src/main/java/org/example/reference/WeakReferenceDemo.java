package org.example.reference;

public class WeakReferenceDemo {

  public static void main(String[] args) {
    // Weak reference example
    Object strongReference = new Object();
    java.lang.ref.WeakReference<Object> weakReference = new java.lang.ref.WeakReference<>(strongReference);

    // The object is strongly reachable, so it won't be garbage collected
    System.out.println("Weak reference created: " + weakReference.get());

    // Nullify the strong reference
    strongReference = null;

    // Suggest to the JVM to run garbage collection
    System.gc();

    // The weak reference will return null, as it is no longer strongly reachable
    System.out.println("After GC, weakReference points to: " + weakReference.get());
  }
}
