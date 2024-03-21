package _07;


import java.util.concurrent.Semaphore;

public class Philosopher {

    public static final int PHILO_NUMBER = 5;

    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[PHILO_NUMBER];

        // Create semaphores for each fork
        for (int i = 0; i < PHILO_NUMBER; i++) {
            forks[i] = new Semaphore(1); // Each fork is initially available
        }

        Philo[] philosophers = new Philo[PHILO_NUMBER];
        for (int i = 0; i < PHILO_NUMBER; i++) {
            philosophers[i] = new Philo("Philosopher " + (i + 1), forks[i], forks[(i + 1) % PHILO_NUMBER]);
            new Thread(philosophers[i]).start();
        }
    }
}

class Philo implements Runnable {

    private Semaphore leftFork;
    private Semaphore rightFork;
    private String name;

    public Philo(String name, Semaphore leftFork, Semaphore rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                // Philosopher thinks
                System.out.println(name + " is thinking.");
                Thread.sleep((long) (Math.random() * 1000));

                // Philosopher tries to acquire left fork
                leftFork.acquire();
                System.out.println(name + " has acquired left fork.");

                // Philosopher tries to acquire right fork
                rightFork.acquire();
                System.out.println(name + " has acquired right fork.");

                // Philosopher eats
                System.out.println(name + " is eating.");
                Thread.sleep((long) (Math.random() * 1000));

                // Philosopher releases forks
                leftFork.release();
                rightFork.release();
                System.out.println(name + " has released forks.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
