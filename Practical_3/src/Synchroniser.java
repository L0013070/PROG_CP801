
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author L00131070
 */
public class Synchroniser {

    private static Semaphore semaphore = new Semaphore();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Tester("thread1", semaphore));
        Thread thread2 = new Thread(new Tester("thread2", semaphore));
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        semaphore.release();
    }

}

class Tester implements Runnable {

    private final String name;
    private Semaphore semaphore;

    public Tester(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                semaphore.take();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            semaphore.release();
            System.out.println("Thread: " + name + " done " + i);
        }
    }

}
