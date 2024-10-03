import java.lang.Thread;

class IncrementBalance implements Runnable {
	private Bank b;
	private int v;
	private int i;

	public IncrementBalance(Bank b, int v, int i) {
		this.b = b;
		this.v = v;
		this.i = i;

	}
	public void run() {
		this.b.deposit(1000);
	}
}

class Main {
	
	public static void main(String[] args) throws InterruptedException {

		int numThreads = 600;
		Thread[] t = new Thread[numThreads];
		Bank banco = new Bank();
		for (int i = 0; i < numThreads; i++) {
			t[i] = new Thread( new IncrementBalance(banco, 1000, 10));
		}
		for (int i = 0; i < numThreads; i++) {
			t[i].start();
		}
		for (int i = 0; i < numThreads; i++) {
			t[i].join();

		}
		System.out.println(banco.balance());
	}

}
