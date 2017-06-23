package com.njq.nongfadai.thread.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Mobin on 2016/3/28. conndition的使用
 */
public class ConditionTest {
	public static void main(String[] args) {

		final Commons common = new Commons();
		new Thread(new Runnable() {
			public void run() {
				for (int i = 1; i <= 1; i++) {
					common.methodA(i);
				}
			}
		}).start();

		for (int i = 1; i <= 1; i++) {
			common.methodB(i);
		}

	}
}

class Commons {
	private boolean flag = true;
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	public void methodA(int i) {
		lock.lock();
		try {

			while (!flag) { // 用while而不用if可以避免虚假唤醒
				try {
					System.out.println("methodA while");
					TimeUnit.SECONDS.sleep(3);
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 1; j <= 10; j++) {
				System.out.println("methodA  " + j + " loop of " + i);
			}
			flag = false;
			
			System.out.println("methodA 准备唤醒其他线程");
			TimeUnit.SECONDS.sleep(2);
			condition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void methodB(int i) {
		lock.lock();
		try {
			while (flag) {
				try {
					System.out.println("methodB while");
					TimeUnit.SECONDS.sleep(3);
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 1; j <= 10; j++) {
				System.out.println("methodB " + j + " loop of  " + i);
			}
			flag = true;
			
			System.out.println("methodB 准备唤醒其他线程");
			TimeUnit.SECONDS.sleep(2);
			condition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
