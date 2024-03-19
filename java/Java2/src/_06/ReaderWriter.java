package _06;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriter {

    public static final int READERS_NUMBER = 10;
    public static final int WRITERS_NUMBER = 20;
    public static final int MAX_READERS_NUMBER = 2;

    public static void main(String[] args) {
        Book book = new Book(MAX_READERS_NUMBER);
        for (int i = 0; i<WRITERS_NUMBER; i++) {
            Thread thread = new Thread(new Writer(book));
            thread.start();
        }
        for (int i = 0; i<READERS_NUMBER; i++) {
            Thread thread = new Thread(new Reader(book));
            thread.start();
        }
    }
}

class Book {

    private int maxReaders;
    private int readersNumber;
    private boolean isWriting = false;
    private ReentrantLock lock = new ReentrantLock();
    private Condition writing = lock.newCondition();
    private Condition reading = lock.newCondition();

    public Book(int maxReaders) {
        this.maxReaders = maxReaders;
    }

    public void write() {
        lock.lock();
        try {
            while (isWriting || readersNumber > 0) {
                writing.await(); // Wait if another writer is writing or readers are reading
            }
            isWriting = true;
            System.out.println(Thread.currentThread().getName() + " is writing.");
            Thread.sleep(new Random().nextInt(1000)); // Simulate writing
            isWriting = false;
            reading.signalAll(); // Signal waiting readers
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void read() {
        lock.lock();
        try {
            while (isWriting) {
                reading.await(); // Wait if a writer is writing
            }
            if (readersNumber < maxReaders) {
                readersNumber++;
                System.out.println(Thread.currentThread().getName() + " is reading.");
                Thread.sleep(new Random().nextInt(1000)); // Simulate reading
                readersNumber--;
                if (readersNumber == 0) {
                    writing.signal(); // Signal waiting writer
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


class Writer implements Runnable {

    private Book book;

    public Writer(Book book) {
        super();
        this.book = book;
    }

    @Override
    public void run() {
        book.write();
    }

}

class Reader implements Runnable {

    private Book book;

    public Reader(Book book) {
        super();
        this.book = book;
    }

    @Override
    public void run() {
        book.read();
    }
}
