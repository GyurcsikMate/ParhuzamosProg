package _02;

public class JoinMegoldas {

    static int threadCount = 0;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting threads recursively...");

        Thread firstThread = new Thread(new MyRecursiveRunnable());
        firstThread.start();
        firstThread.join(); // Wait for the last thread to finish

        System.out.println("All threads have finished.");
    }

    static class MyRecursiveRunnable implements Runnable {
        @Override
        public void run() {
            int threadNumber = ++threadCount;
            if (threadNumber <= 10) {
                Thread nextThread = new Thread(new MyRecursiveRunnable());
                nextThread.start();
                try {
                    nextThread.join(); // Wait for the next thread to finish
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Thread " + threadNumber + " started.");
            try {
                Thread.sleep(1000); // Simulating some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + threadNumber + " finished.");
        }
    }
}
