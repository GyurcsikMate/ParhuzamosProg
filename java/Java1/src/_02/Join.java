
package _02;

public class Join {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting 10 threads...");

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MyRunnable());
            threads[i].start();
            threads[i].join(); // Wait for the thread to finish before starting the next one
        }

        System.out.println("All threads have finished.");
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " started.");
            try {
                Thread.sleep(1000); // Simulating some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finished.");
        }
    }
}


/*
Írjunk egy olyan programot, ami 10 szálat indít (tetszőleges ideig fut altatás segítségével) 
és minden szál esetén várjuk meg, hogy befejeződjön mielőtt a következő szálat elindítanánk.


Írjunk egy olyan programot, ami rekurzívan hoz létre 10 szálat 
(azaz az adott szál run() metódusában hozzuk létre a következőt). 
A legkésőbb létrehozott szál fejezze be először a futását.
*/