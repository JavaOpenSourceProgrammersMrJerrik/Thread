package com.njq.nongfadai.thread.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟先调用signal 后调用await的情况  导致线程不能唤醒
 */
public class ConditionTest3 {
	final static Lock lock = new ReentrantLock();

	static Condition mainCondition = lock.newCondition();

	static Condition subCondition = lock.newCondition();

	public static void main(String[] args) throws InterruptedException {
		ConditionTest3 conditionTest = new ConditionTest3();

		conditionTest.start();

		//取消休眠,让signal先执行
	/*	System.out.println("主线程sleep 3秒");
		TimeUnit.SECONDS.sleep(3);*/

		try {
			lock.lock();
			System.out.println("main线程 mainCondition signal");
			mainCondition.signal();
		} finally {
			lock.unlock();
		}
	}
	//output:

	private void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.lock();
					TimeUnit.SECONDS.sleep(1);
					System.out.println("mainCondition await");
					
					//main线程先唤醒,导致唤醒信号丢失,子线程一直阻塞在await这里
					mainCondition.await();
					System.out.println("subCondition await");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}).start();
	}

}
