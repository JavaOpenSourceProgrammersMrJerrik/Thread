package com.njq.nongfadai.thread.condition;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest2 {
	final static Lock lock = new ReentrantLock();

	static Condition mainCondition = lock.newCondition();

	static Condition subCondition = lock.newCondition();

	public static void main(String[] args) throws InterruptedException {
		ConditionTest2 conditionTest = new ConditionTest2();

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
	main线程 subCondition signal
	subCondition 唤醒
	子线程唤醒*/

	private void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.lock();
					System.out.println("mainCondition await");
					mainCondition.await();

					System.out.println("subCondition await");
					subCondition.await();

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
