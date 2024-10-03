import java.util.*;


class Bank {

    private static class Account {
        private ReentrantLock lockAccount;
        private int balance;
        Account(int balance) {
            this.balance = balance;
            this.lockAccount = new ReentrantLock();
        }
        int balance() {
            try {
                this.lockAccount.lock();
                return balance;
            } finally {
                  this.lockAccount.unlock();
            }
        }
        boolean deposit(int value) {
            this.lockAccount.lock();
            try {
                balance += value;
                return true;
            } finally {
                  this.lockAccount.unlock();
            }
        }
        boolean withdraw(int value) {
            this.lockAccount.lock();
            try {
	            if (value > balance)
	                return false;
	            balance -= value;
	            return true;
            } finally {
                  this.lockAccount.unlock();
            }

        }
        void lock(void) {
		this.lockAccount.lock();
	}
      	void unlock(void) {
		this.lockAccount.unlock();
	}
    } // END OF ACCOUNT CLASS

    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;
    private ReentrantLock lockBank;

    Bank () {
        this.lockBank = new ReentrantLock();
    }
    void lock(void) {
	this.lockBank.lock();
    }
    void unlock(void) {
	this.lockBank.unlock();
    }

    // create account and return account id
    public int createAccount(int balance) {
        this.lockBank.lock();
	try {
            Account c = new Account(balance);
            int id = nextId;
            nextId += 1;
            map.put(id, c);
            return id;
        } finally {
            this.lockBank.unlock();
        }
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        this.lockBank.lock();
	try {
            Account c = map.remove(id);
            c.lock();
            if (c == null)
                return 0;
         } finally {
            this.lockBank.unlock();
         }
         try {
            return c.balance();
         } finally {
            c.unlock();
         }

    }

    // account balance; 0 if no such account
    public int balance(int id) {
	this.lockBank.lock();
        try {
            Account c = map.get(id);
            if (c == null)
                return 0;
            c.lock();
        } finally {
     	    this.lockBank.unlock();
        }
        try {
            return c.balance();
        } finally {
            c.unlock();
        }
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        Account c = map.get(id);
        if (c == null)
            return false;
        return c.deposit(value);
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        Account c = map.get(id);
        if (c == null)
            return false;
        return c.withdraw(value);
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        Account cfrom, cto;
        cfrom = map.get(from);
        cto = map.get(to);
        if (cfrom == null || cto ==  null)
            return false;
        return cfrom.withdraw(value) && cto.deposit(value);
    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        Account[] accounts = new Account[ids.length];
        lockBank.lock();
        try {
            int total = 0;
            for (int i : ids) {
                Account c = map.get(i);
                if (c == null)
                      accounts[i] = map.get(c);
//                    return 0;
                      c.lock.lock();
                total += c.balance();
            }
        } finally {
            lockBank.unlock();
        }
  	for (int i : ids) {
             total += accounts[i].balance;
             accounts[i].unlock();
	}
        return total;
  }

}
