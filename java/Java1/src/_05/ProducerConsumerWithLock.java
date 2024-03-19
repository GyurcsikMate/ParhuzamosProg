package _05;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWithLock {
    public static void main(String[] args) {

        int numberOfProducer = 20;
        int numberOfConsumer = 10;

        Buffer buffer = new Buffer(10);
        for (int i = 0; i<numberOfProducer; i++) {
            Producer p = new Producer("Producer-" + i, buffer);
            p.start();
        }
        for (int i = 0; i<numberOfConsumer; i++) {
            Consumer c = new Consumer("Consumer-" + i, buffer);
            c.start();
        }
    }
}
class Buffer {
    ReentrantLock lock = new ReentrantLock();
    Condition nonFull = lock.newCondition();
    Condition nonEmpty = lock.newCondition();

    int maxSize;
    ArrayList<Integer> list;

    Buffer(int maxSize) {
        this.maxSize = maxSize;
        list = new ArrayList<>();
    }

    void deposit(int value, Thread t) {
        lock.lock();
        try {
            while (list.size() == maxSize) {
                try {
                    System.out.println(t.getName() + " is waiting to deposit...");
                    nonFull.await(); // Wait if buffer is full
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(value);
            System.out.println(t.getName() + " deposited " + value);
            nonEmpty.signal(); // Signal waiting threads that buffer is not empty
        } finally {
            lock.unlock();
        }
    }

    int extract(Thread t) {
        lock.lock();
        try {
            while (list.isEmpty()) {
                try {
                    System.out.println(t.getName() + " is waiting to extract...");
                    nonEmpty.await(); // Wait if buffer is empty
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int value = list.remove(0);
            System.out.println(t.getName() + " extracted " + value);
            nonFull.signal(); // Signal waiting threads that buffer is not full
            return value;
        } finally {
            lock.unlock();
        }
    }
}


class Producer extends Thread {

    Buffer buffer;

    Producer(String name, Buffer buffer) {
        super(name);
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int value = random.nextInt(100); // Generate random value
            buffer.deposit(value, this);
            try {
                Thread.sleep(random.nextInt(1000)); // Simulate some production time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class Consumer extends Thread {

    Buffer buffer;

    Consumer(String name, Buffer buffer) {
        super(name);
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int value = buffer.extract(this);
            try {
                Thread.sleep(random.nextInt(1000)); // Simulate some consumption time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

