package mintazh;

import java.util.concurrent.Semaphore;

public class PrinterCopier {
    // Semaphore for simulating the printing capability as a semaphore, can print 2 pages at the same time.
    private final Semaphore printerSemaphore = new Semaphore(2);
    // Semaphore for simulating the copying capability as a semaphore. Not specified how much can be copied,
    // so it is assumed to be implicitly one.
    private final Semaphore copierSemaphore = new Semaphore(1);

    public void copy() {
        try { // Try-catch block is needed by both semaphore and sleep.
            copierSemaphore.acquire(); // Acquire semaphore for copying.
            System.out.println("Started copying for thread/job: "+ Thread.currentThread().getName());
            Thread.sleep(550); // Simulated duration for copying.
            System.out.println("Completed copying for thread/job: }"+ Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            copierSemaphore.release(); // Release semaphore after copying.
        }
    }

    public void print() { // Try-catch block is needed by both semaphore and sleep.
        try {
            printerSemaphore.acquire(); // Acquire semaphore for printing.
            System.out.println("Started printing for thread/job: }"+ Thread.currentThread().getName());
            Thread.sleep(400); // Simulated duration for printing.
            System.out.println("Started printing for thread/job:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            printerSemaphore.release(); // Release semaphore after printing.
        }
    }

    public static void main(String[] args) {
        PrinterCopier printerCopier = new PrinterCopier();

        for (int i = 0; i < 20; i++) {
            new Thread(printerCopier::copy,"copier job/thread no. "+i).start();
            new Thread(printerCopier::print, "printer job/thread no. "+i).start();
        }
    }
}