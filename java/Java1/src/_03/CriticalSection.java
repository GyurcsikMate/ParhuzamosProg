package _03;

public class CriticalSection {

    public static final int N = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        
        Buffer buffer = new Buffer();
        for (int i = 0; i<N; i++) {
            Thread t = new Thread(new MyRunnable(buffer));
            t.start();
        }
    }
}

class MyRunnable implements Runnable {
    
    private Buffer buffer;

    public MyRunnable(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        buffer.kritikusSzakasz();
        buffer.kritikusSzakaszBlokk();
    }

}

class Buffer {
    
    public synchronized void kritikusSzakasz() {
        System.out.println("Kritikus szakaszba lepett: " + Thread.currentThread().getName());
        System.out.println("Kritikus szakaszbol kilepett: " + Thread.currentThread().getName());
    }

    public void kritikusSzakaszBlokk() {
        synchronized(this) {
            System.out.println("Kritikus szakaszba (blokk) lepett: " + Thread.currentThread().getName());
            System.out.println("Kritikus szakaszbol (blokk) kilepett: " + Thread.currentThread().getName());
        }
    }
}
