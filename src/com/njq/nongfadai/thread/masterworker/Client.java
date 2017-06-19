package com.njq.nongfadai.thread.masterworker;

import java.util.Map;

public class Client {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Master master = new Master(new PlusWorker(), 10);
		for (int i = 0; i < 100; i++) {
			master.submit(i);
		}
		master.execute();

		while (!master.isComplete()) {
			
		}
		
		Map<String, Object> resultMap = master.getResultMap();
		System.out.println("resultMap: " + resultMap);
		System.out.println("总任务个数: " + PlusWorker.getTotalCount());
		long endTime = System.currentTimeMillis();
		System.out.println("总耗时: " + (endTime - startTime)/1000.0);
	}
}
