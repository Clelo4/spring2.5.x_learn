package org.example.memory;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import sun.misc.Unsafe;

public class PhantomReferenceMemoryHolder {
  private static final Unsafe unsafe;

  private static final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

  private static final Thread CLEANER_THREAD;

  private static final List<MemoryCleaner> activeCleaners = new LinkedList<>();

  static {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);

      unsafe = (Unsafe) field.get(null);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    CLEANER_THREAD = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          MemoryCleaner memoryCleaner = (MemoryCleaner) referenceQueue.remove();

          memoryCleaner.clean();

          activeCleaners.remove(memoryCleaner);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }, "MemoryCleanerThread");

    CLEANER_THREAD.setDaemon(true);
    CLEANER_THREAD.start();
    System.out.println("清理线程已启动...");
  }

  // 这是一个普通的业务对象，我们希望在它被回收时，释放相关资源
  private final Object businessObject;

  public PhantomReferenceMemoryHolder(long size) {
    businessObject = new Object();

    long address = unsafe.allocateMemory(size);
    System.out.printf("[Phantom] 分配了 %d 字节内存, 地址: %d%n", size, address);

    MemoryCleaner memoryCleaner = new MemoryCleaner(businessObject, referenceQueue, address);
    activeCleaners.add(memoryCleaner);
  }

  private static class MemoryCleaner extends PhantomReference<Object> {
    private long address;
    /**
     * Creates a new phantom reference that refers to the given object and is registered with the given
     * queue.
     *
     * <p> It is possible to create a phantom reference with a <tt>null</tt>
     * queue, but such a reference is completely useless: Its <tt>get</tt> method will always return
     * null and, since it does not have a queue, it will never be enqueued.
     *
     * @param referent the object the new phantom reference will refer to
     * @param q        the queue with which the reference is to be registered, or <tt>null</tt> if
     *                 registration is not required
     */
    public MemoryCleaner(Object referent, ReferenceQueue<? super Object> q, long address) {
      super(referent, q);
      this.address = address;
    }

    public void clean() {
      if (address !=0 ) {
        unsafe.freeMemory(address);
        System.out.printf("[Phantom] 内存地址 %d 已被 Cleaner 线程释放!%n", address);
        address = 0;
      }
    }

    public static void main(String[] args) throws InterruptedException {
      System.out.println("--- Testing PhantomReference ---");
      // 为了让businessObject被回收，我们需要将它的持有者也置为null
      PhantomReferenceMemoryHolder holder = new PhantomReferenceMemoryHolder(2048); // 2 KB

      // 解除引用
      holder = null;

      // 建议GC
      System.gc();

      // 等待清理线程执行
      Thread.sleep(1000);

      System.out.printf("The size of activeCleaners: %d\n", activeCleaners.size());
      System.out.println("--- Test PhantomReference Finished ---");
    }
  }
}
