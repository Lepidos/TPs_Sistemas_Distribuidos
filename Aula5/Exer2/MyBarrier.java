import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBarrier {
    private int n; // number of threads to wait for
    private int count;
    private Lock lock;
    private Condition condition;
    private int epoch;
    public MyBarrier(int n) {
	this.epoch = 0;
        this.n = n;
	this.count = 0;
        this.lock = new ReentrantLock();
	this.condition = lock.newCondition();

    }

    public void await() throws InterruptedException{
        lock.lock();
        System.out.println("Barrier await working");
        try {
		int epochThread = this.epoch;
          //  count = 0; // number of threads that have arrived
           this.count++;
            if (this.count < n ) {
		while (epochThread == epoch )
	                condition.await(); // wait until all threads have arrived

            } else {
	    	condition.signalAll();
            	// release all threads
            	System.out.println("All " + n + " threads have arrived, releasing...");
		epoch ++;
		count = 0;
	    }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int numThreads = 5;
        MyBarrier barrier = new MyBarrier(numThreads);
	Thread[] threads = new Thread[20];
	for (int i = 0; i < 20; i++) {
	    threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
		    try {
		    	barrier.await();
                    } catch (InterruptedException e) {
			return;
		    }
                    System.out.println("Thread " + (Thread.currentThread().getId() + 1) + " has started...");
                }
            });
            threads[i].start();
	}
	for (int i = 0; i < 20; i++) {
        	try {
         		threads[i].join();
	        } catch (InterruptedException e) {
			Thread.currentThread().interrupt();
	        }
	}
    }
}
