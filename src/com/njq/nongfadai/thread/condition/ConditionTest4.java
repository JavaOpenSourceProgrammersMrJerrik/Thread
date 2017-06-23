package com.njq.nongfadai.thread.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用不同的锁 创建Condition
 * @author Jerrik
 *
 */
public class ConditionTest4 {
	final static Lock lock = new ReentrantLock();
	final static Lock lock2 = new ReentrantLock();

	static Condition mainCondition = lock.newCondition();

	static Condition subCondition = lock2.newCondition();

	public static void main(String[] args) throws InterruptedException {
		ConditionTest4 conditionTest = new ConditionTest4();

		conditionTest.start();

		System.out.println("主线程sleep 3秒");
		TimeUnit.SECONDS.sleep(3);

		try {
			lock.lock();
			System.out.println("main线程 mainCondition signal");
			mainCondition.signal();
		} finally {
			lock.unlock();
		}

		System.out.println("main线程 sleep 2秒");
		TimeUnit.SECONDS.sleep(2);

		try {
			lock.lock();
			System.out.println("main线程 subCondition signal");
			subCondition.signal();

			System.out.println("subCondition 唤醒");
		} finally {
			lock.unlock();
		}
	}
	//output:
/*	主线程sleep 3秒
	mainCondition await
	main线程 mainCondition signal
	main线程 sleep 2秒
	subCondition await
	Exception in thread "Thread-0" java.lang.IllegalMonitorStateException
		at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:155)
		at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1260)
		at java.util.concurrent.locks.AbstractQueuedSynchronizer.fullyRelease(AbstractQueuedSynchronizer.java:1723)
		at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2040)
		at com.njq.nongfadai.thread.condition.ConditionTest4$1.run(ConditionTest4.java:70)
		at java.lang.Thread.run(Thread.java:745)
	main线程 subCondition signal
	Exception in thread "main" java.lang.IllegalMonitorStateException
		at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.signal(AbstractQueuedSynchronizer.java:1941)
		at com.njq.nongfadai.thread.condition.ConditionTest4.main(ConditionTest4.java:43)

*/
	private void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.lock();
					System.out.println("mainCondition await");
					mainCondition.await();

					System.out.println("subCondition await");
					subCondition.await();//当子线程释放lock上的锁时  抛出异常

					System.out.println("子线程唤醒");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}).start();
	}

}
