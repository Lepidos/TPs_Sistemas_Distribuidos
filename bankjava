public class Bank {
    HashMap<String, Double> accounts = new HashMap<>();

    

    public void open(String accountNumber) {
        accounts.put(accountNumber, 0.0);
        System.out.println("Account opened successfully!");
    }

    public double balance(String accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            return accounts.get(accountNumber);
        } else {
            System.out.println("Account not found!");
            return -1;
        }
    }

    public void deposit(String accountNumber, double amount) {
        if (accounts.containsKey(accountNumber)) {
            accounts.put(accountNumber, accounts.get(accountNumber) + amount);
            accounts.put(accountNumber, accounts.getOrDefault(accountNumber, 0.0) + amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        if (accounts.containsKey(accountNumber)) {
            if (accounts.get(accountNumber) >= amount) {
                accounts.put(accountNumber, accounts.get(accountNumber) - amount);
                System.out.println("Withdrawal successful!");
            } else {
                System.out.println("Insufficient funds!");
            }
        } else {
            System.out.println("Account not found!");
        }
    }
}
