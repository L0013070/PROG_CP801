/**
 * 
 */
package jobqueue;

import java.util.ArrayList;
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

	ArrayList<Thread> threads = new ArrayList<Thread>(10);
	ConcurrentLinkedQueue<Job> queue = new ConcurrentLinkedQueue<Job>();

	public JobQueue() {
		waitingThreads = new Semaphore(0);
		fullQueue = new Semaphore(20, true);
		for (int i = 0; i < 10; i++) {
			QueueThread thread = new QueueThread(this);
			threads.add(thread);
			thread.start();
		}
	}

	protected Job getJob() {
		try {
			waitingThreads.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queue.remove();
	}

	public boolean add(Job job) {
		if (null == job) {
			throw new NullPointerException("no null job allowed");
		}

		try {
			if (!fullQueue.tryAcquire(1, 1000, TimeUnit.MILLISECONDS)) {
				return false;
			}
			;
			queue.add(job);
			waitingThreads.release();
		} catch (InterruptedException ex) {
			return false;
		}
		return true;
	}
	
	protected void finalize() {
		   System.out.println("Finalizing...");
		   fullQueue.release(10);
		   for (Thread thread : threads) {
			   try {
				thread.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
			
		   System.out.println("Finalized.");
		
	}
}

final class QueueThread extends Thread {

	JobQueue queue = null;

	QueueThread(JobQueue queue) {
		this.queue = queue;
	}

	public void run() {
		System.out.println("Thread: "+queue.threads.indexOf(this)+" starting");
		Job job = queue.getJob();
		while (null != job) {
			System.out.println("Thread: "+queue.threads.indexOf(this)+" doing job");
			job.doJob();
			System.out.println("Thread: "+queue.threads.indexOf(this)+" finished job");
			queue.fullQueue.release();
			System.out.println("Thread: "+queue.threads.indexOf(this)+" getting job");
			job = queue.getJob();
		}
		System.out.println("Thread: "+queue.threads.indexOf(this)+" ending");
	}
	
}
