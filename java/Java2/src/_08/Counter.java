package _08;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    AtomicInteger count = new AtomicInteger();

    public void inc() {
        count.incrementAndGet();
        System.out.println(count);
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        for (int i = 0; i < 10_000; i++) {
            new MyThread(counter).start();
        }

        Thread.sleep(2000);

        System.out.println("Final result: " + counter.count);
    }
}


class MyThread extends Thread {

    Counter counter;
    
    MyThread(Counter counter){
        this.counter = counter;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        counter.inc();
    }
}

// https://docs.oracle.com/javase/8/docs/api/index.html?java/util/concurrent/package-summary.html