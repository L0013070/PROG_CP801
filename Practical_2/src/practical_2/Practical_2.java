/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practical_2;

/**
 *
 * @author L00131070
 */
public class Practical_2 {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("First run");
        Thread thread1 = new Thread(new Thread1());
        Thread thread2 = new Thread(new Thread2());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Second run");
        thread1 = new Thread(new Thread1());
        thread2 = new Thread(new Thread2());
        thread2.setPriority(thread2.getThreadGroup().getMaxPriority());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

}

class Thread1 implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread1 start");
        for (int i = 0; i < 11; i++) {
            if ((i & 0x1) == 0) {
                System.out.println("Thread1: "+i);
            }
        }
        System.out.println("Thread1 end");
    }

}

class Thread2 implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread2 start");
        for (int i = 1; i < 12; i++) {
            if (i % 2 == 1) {
                System.out.println("Thread2: "+i);
            }
        }
        System.out.println("Thread2 end");
    }

}
