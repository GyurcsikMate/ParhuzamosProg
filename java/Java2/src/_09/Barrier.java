package _09;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.Random;

public class Barrier {

    public static void main(String[] args) {
        
        CyclicBarrier cb = new CyclicBarrier(10);
        
        for(int i=0; i<10; i++) {
            new MyThread(cb).start();
        }
       
    }
}

class MyThread extends Thread {

    CyclicBarrier cb;
    
    MyThread(CyclicBarrier cb){
        this.cb=cb;
    }
    
    @Override
    public void run() {
        System.out.println("Valami műveletet végzünk..");
        
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        
        System.out.println("A művelet után..");
    }
}
