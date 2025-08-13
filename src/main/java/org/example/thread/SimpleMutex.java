package org.example.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SimpleMutex implements Lock {

  private static class Sync extends AbstractQueuedSynchronizer {
    @Override
    protected boolean isHeldExclusively() {
      return getState() == 1 && getExclusiveOwnerThread() == Thread.currentThread();
    }

    @Override
    public boolean tryAcquire(int arg) {
      // AQS的state初始值为0，表示锁未占用
      // 我们使用CAS（Compare-And-Swap）操作尝试将state从0变为1
      if (compareAndSetState(0, 1)) {
        // 如果成功，表示当前线程获得了锁
        setExclusiveOwnerThread(Thread.currentThread());
        return true;
      }
      // 如果失败，表示锁已被其他线程占用
      return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
      // 如果state部位1，表示锁未被当前线程持有
      if (getState() != 1 || getExclusiveOwnerThread() != Thread.currentThread()) {
        throw new IllegalMonitorStateException();
      }
      // 将state设置为0，表示锁已释放
      setExclusiveOwnerThread(null);
      setState(0);
      return true;
    }

    // 提供一个Condition，使其支持await/signal
    Condition newCondition() {
      return new ConditionObject();
    }
  }

  // 我们将所有操作都委托给内部的Sync实例
  private final Sync sync = new Sync();

  @Override
  public void lock() {
    // 调用AQS的模板方法acquire。它会调用我们重写的tryAcquire
    sync.acquire(1);
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    sync.acquireInterruptibly(1);
  }

  @Override
  public boolean tryLock() {
    return sync.tryAcquire(1);
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return sync.tryAcquireNanos(1, unit.toNanos(time));
  }

  @Override
  public void unlock() {
    sync.release(1);
  }

  @Override
  public Condition newCondition() {
    return sync.newCondition();
  }

  public boolean isLocked() {
    return sync.isHeldExclusively();
  }
}
