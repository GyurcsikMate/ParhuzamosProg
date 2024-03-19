package _10;

import java.util.Calendar;
import java.util.Random;
import java.util.*;
import java.util.concurrent.*;

class MyRunnable implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(Thread.currentThread().getName());   
    }
}

class MyCallable implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        Thread.sleep(new Random().nextInt(10)*100);
        System.out.println(Thread.currentThread().getName());   
        return new Random().nextInt(10)*100;
    }
    
}

public class Executor {
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for(int i = 0; i<15; i++) {
            executor.execute(new MyRunnable());
        }
        
        System.out.println(executor.isShutdown() + " " + executor.isTerminated());
        executor.shutdown();
        System.out.println(executor.isShutdown() + " " + executor.isTerminated());
    }   
}
