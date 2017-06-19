package com.njq.nongfadai.thread.masterworker;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
	private Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>();

	private Map<String, Thread> threadMap = new HashMap<String, Thread>();

	private Map<String, Object> resultMap = new HashMap<String, Object>();

	private int defaultCapicity = 3;

	private int workerCount;

	public Master(Worker worker, int workerCount) {
		worker.setWorkQueue(workQueue);
		worker.setResultMap(resultMap);
		this.workerCount = workerCount > 0 ? workerCount : defaultCapicity;
		for (int i = 0; i < this.workerCount; i++) {
			threadMap.put("Runnable-" + i, new Thread(worker, "worker-thread-" + i));
		}
	}

	public boolean isComplete() {
		for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
			if (entry.getValue().getState() != Thread.State.TERMINATED) {
				return false;
			}
		}
		return true;
	}

	public void submit(Object job) {
		workQueue.add(job);
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void execute() {
		for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
			entry.getValue().start();
		}

		System.out.println("所有worker均已启动: " + threadMap);
	}

}
