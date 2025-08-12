package org.example.reference;

public class StrongReferenceDemo {

    public static void main(String[] args) {
        // Strong reference example
        Object strongReference = new Object();

        // The object is strongly reachable, so it won't be garbage collected
        System.out.println("Strong reference created: " + strongReference);

        // Nullify the strong reference
        strongReference = null;

        System.gc();
        System.out.println("After setting strongReference to null, strongReference is: " + strongReference); // 输出: After setting user to null and GC, user is: null
    }
}
