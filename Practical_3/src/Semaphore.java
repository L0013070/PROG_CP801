/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author L00131070
 */
public class Semaphore {
    
    boolean go = false;
    
    public synchronized void take() throws InterruptedException {
        if (!go) {
            wait();
        }
        go = false;
    }
    
    public synchronized void release() {
        go = true;
        notify();
    }
    
}
