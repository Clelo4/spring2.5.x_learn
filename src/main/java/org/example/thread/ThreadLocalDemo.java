package org.example.thread;

public class ThreadLocalDemo {

  private final static ThreadLocal<String> threadLocal = new ThreadLocal<>();

  public static void main(String[] args) {

    Thread threadOne = new Thread(() -> {
      threadLocal.set("abc");

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }

      System.out.printf("Thread %d threadLocal value is : %s\n", Thread.currentThread().getId(), threadLocal.get());
    });

    Thread threadTwo = new Thread(() -> {
      threadLocal.set("123");

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }

      System.out.printf("Thread %d threadLocal value is : %s\n", Thread.currentThread().getId(), threadLocal.get());
    });

    threadOne.start();
    threadTwo.start();

    try {
      threadOne.join();
      threadTwo.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
