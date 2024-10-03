public class Bank {

    private static class Account {
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;
        }
    }

    // Bank slots and vector of accounts
    private int slots;
    private Account[] av;
    private lock l;
    public Bank(int n) {
        slots=n;
        av=new Account[slots];
        for (int i=0; i<slots; i++) av[i]=new Account(0);
	this.l=new ReentrantLock();
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
	l.lock()
        try {
		return av[id].balance();
	} finally {
		l.unlock();
	}
    }

    // Deposit
    public boolean deposit(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
	l.lock();
        try {
	        return av[id].deposit(value);
	} finally {
		l.unlock();
	}
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        return av[id].withdraw(value);
    }
    public boolean transfer (int from, int to, int value) {
	if ((from < 0 || from >= slots) && (to < 0 || to >= slots))
	l.lock();
	try {
	        return this.whithdraw(from,value) && this.deposit(to,value);
	} finally {
		l.unlock();
	}
	return false;
    }

    public totalBalance() {
	int total=0;
	l.lock();
	try {
	        for (int i=0; i<slots; i++) {
			total += av[i].balance();
		}
	} finally {
		l.unlock();
	}
    }
}
