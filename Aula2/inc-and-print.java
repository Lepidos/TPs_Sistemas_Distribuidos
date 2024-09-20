import java.lang.Thread;

class Increment implements Runnable {
  public void run() {
    final long I=100;

    for (long i = 0; i < I; i++)
      System.out.println(i);
  }
}

class Main {
	public static void main(String[] args) throws InterruptedException {
		int numThreads = 10;
		Thread[] t = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			t[i] = new Thread( new Increment());
			t[i].start();
		}
		for (int i = 0; i < numThreads; i++) {
			t[i].join();

		}
	}

}
