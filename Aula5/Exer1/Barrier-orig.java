import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private int n; // number of threads to wait for
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public Barrier(int n) {
        this.n = n;
    }

    public void await() throws InterruptedException, BrokenBarrierException {
        lock.lock();
        try {
            int count = 0; // number of threads that have arrived
            while (count < n) {
                count++;
                condition.await(); // wait until all threads have arrived
            }
            // release all threads
            System.out.println("All " + n + " threads have arrived, releasing...");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int numThreads = 5;
        Barrier barrier = new Barrier(numThreads);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            executor.submit(() -> {
                System.out.println("Thread " + threadIndex + " has started...");
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread " + threadIndex + " has finished.");
            });
        }

        executor.shutdown();
    }
}
