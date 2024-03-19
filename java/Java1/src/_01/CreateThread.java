package _01;

public class CreateThread {

    public static void main(String[] args) {

        Thread myThread = new MyThread();
        Thread myThread2 = new Thread(new MyRunnable());

        myThread.start();
        myThread2.start();
    }
}

class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("MyThread is running.");
        // Add your code here for what you want this thread to do
        for (int i = 0; i < 5; i++) {
            System.out.println("MyThread: " + i);
            try {
                Thread.sleep(500); // Simulate some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("MyThread is finished.");
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("MyRunnable is running.");
        // Add your code here for what you want this thread to do
        for (int i = 0; i < 5; i++) {
            System.out.println("MyRunnable: " + i);
            try {
                Thread.sleep(700); // Simulate some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("MyRunnable is finished.");
    }
}
