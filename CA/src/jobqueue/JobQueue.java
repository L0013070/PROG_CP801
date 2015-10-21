/**
 * 
 */
package jobqueue;


import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/**
 * @author L00131070
 *
 */
public class JobQueue {
	Semaphore waitingThreads;
	Semaphore fullQueue;
	
	ConcurrentLinkedQueue<Job> queue = new ConcurrentLinkedQueue<Job>();

	public JobQueue() {
		waitingThreads = new Semaphore(0);
		fullQueue = new Semaphore(20, true);
	}
	
	public boolean add(Job job) {
		try {
			fullQueue.tryAcquire(1,1000, TimeUnit.MILLISECONDS);
			queue.add(job);
		} catch (InterruptedException ex) {
			return false;
		}
		return true;
	}
}
