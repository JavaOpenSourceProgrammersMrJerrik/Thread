package com.njq.nongfadai.thread;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Demo {

	public static void main(String[] args) {
		Queue<String> queue = new ConcurrentLinkedQueue<String>();
		queue.add("hello");
		
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}

}
