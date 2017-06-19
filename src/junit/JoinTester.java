package junit;

import java.util.concurrent.TimeUnit;

public class JoinTester {

	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("1 begin");
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("1 end");
			}
		});

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("2 begin");
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("2 end");
			}
		});
		
		thread1.start();
		thread2.start();
		
		thread2.join();
		System.out.println("finished");
	}
}
