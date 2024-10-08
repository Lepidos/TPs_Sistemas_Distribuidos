import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

  ReentrantLock lock = new ReentrantLock();
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
	lock.lock();
	try {
		return savings.balance();
	} finally {
		lock.unlock();
	}
  }

  // Deposit
  boolean deposit(int value) {
	lock.lock();
	try {
    		return savings.deposit(value);
	} finally {
		lock.unlock();
	}
  }
}
