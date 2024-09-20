import java.lang.Thread;

class Bank {

  private static class Account {
    private int balance;
    Account(int balance) { this.balance = balance; }
    int balance() { return balance; }
    boolean deposit(int value) {
      balance += value;
      return true;
    }
  }

  // Our single account, for now
  private Account savings = new Account(0);

  // Account balance
  public int balance() {
    return savings.balance();
  }

  // Deposit
  boolean deposit(int value) {
    return savings.deposit(value);
  }
}

class IncrementBalance implements Runnable {
	private Banco b;
	private int v;
	private int i;

	public IncrementBalance(Banco b, int v, int i) {
		this.b = b;
		this.v = v;
		this.i = i;

	}
	}
	public void run() {
		this.b.deposit(1000);
	}
}

class Main {
	public static void main(String[] args) throws InterruptedException {
		lock = new Reentrantlock();

		int numThreads = 10;
		Thread[] t = new Thread[numThreads];
		Bank banco = new Bank();
		for (int i = 0; i < numThreads; i++) {
			try {
				lock.lock();
				t[i] = new Thread( new IncrementBalance(banco, 1000, 10));
				t[i].start();
			} finally {
				lock.unlock();
			}
		}
		for (int i = 0; i < numThreads; i++) {
			t[i].join();

		}
		System.out.println(banco.balance());
	}

}
