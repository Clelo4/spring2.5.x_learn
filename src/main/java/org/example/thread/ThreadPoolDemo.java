package org.example.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolDemo {

  public static void main(String[] args) {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20,
        0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>(50));
    threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
  }
}
