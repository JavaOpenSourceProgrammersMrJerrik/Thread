package com.njq.nongfadai.thread.masterworker;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 加法
 * 
 * @author Jerrik
 */
public class PlusWorker extends Worker {

	static Random random = new Random();

	static final AtomicLong counter = new AtomicLong(0);

	@Override
	protected Object handle(Object input) {
		try {
			System.out.println(Thread.currentThread().getName() + " 正在处理任务-" + counter.incrementAndGet());
			TimeUnit.MILLISECONDS.sleep(/*random.nextInt(5000)*/10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer i = (Integer) input;
		return i * i * i;
	}

	static long getTotalCount() {
		return counter.get();
	}

}
