package junit;

public class YeildTester extends Thread {

	public YeildTester(String name) {
		super(name);
	}

	@Override
	public void run() {
		for (int i = 1; i <= 50; i++) {
			System.out.println("" + this.getName() + "-----" + i);
			// 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
			if (i == 30 || i == 31 || i == 32) {
				Thread.currentThread().yield();
			}
		}
	}

	public static void main(String[] args) {
		YeildTester yt1 = new YeildTester("张三");
		YeildTester yt2 = new YeildTester("李四");
		yt1.start();
		yt2.start();
	}
}
