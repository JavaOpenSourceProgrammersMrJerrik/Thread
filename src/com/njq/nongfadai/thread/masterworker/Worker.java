package com.njq.nongfadai.thread.masterworker;

import java.util.Map;
import java.util.Queue;

public abstract class Worker implements Runnable {
	private Queue<Object> workQueue;

	private Map<String, Object> resultMap;

	protected abstract Object handle(Object input);

	public void run() {
		while (true) {
			Object input = workQueue.poll();
			if (input == null) {
				break;
			}
			Object result = handle(input);
			resultMap.put(Integer.toString(input.hashCode()), result);
		}
	}

	public void setWorkQueue(Queue<Object> workQueue) {
		this.workQueue = workQueue;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

}
