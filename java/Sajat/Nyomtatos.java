package Sajat;
import java.util.concurrent.Semaphore;

public class Nyomtatos{
    public static final int TASK_NUMBER = 20;
    public static void main(String[] args) {
        Semaphore Szemi = new Semaphore(1);
        NyomtatasFeladat[] feladatok = new NyomtatasFeladat[TASK_NUMBER];
        for (int i = 0; i < TASK_NUMBER; i++) {
            feladatok[i] = new NyomtatasFeladat("Feladat " + (i + 1), Szemi);
            new Thread(feladatok[i]).start();
        }
    }
}

class NyomtatasFeladat implements Runnable {

    private Semaphore Szemi;
    private String name;

    public NyomtatasFeladat(String name, Semaphore Szemi) {
        this.name = name;
        this.Szemi = Szemi;
    }
    @Override
    public void run() {
            try {
                Szami.acquire();
                System.out.println(name + " has started a nyomtatas job");
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println(name + " has finished a nyomtatas job.");
                leftFork.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}