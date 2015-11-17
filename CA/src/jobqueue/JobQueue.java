/**
 * 
 *
 */
package jobqueue;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * This class is a service to run given jobs concurrently.<br>
 * The queue starts 10 threads to service the jobs in the queue.<br>
 * A maximum number of 20 jobs can be in the service either in the queue or being executed.<br>
 * If the service is full the add procedure waits for 500ms for a space to become available then returns.<br>
 * 
 * @author L00131070
 *
 */
public class JobQueue {

    /**
     * Defines the maximum number of jobs allowed in the queue environment. This
     * includes running jobs.
     */
    public static final int MAXJOBS = 20;

    /**
     * Defines the number of concurrent threads will be created for job
     * execution.
     */
    public static final int MAXTHREADS = 10;

    /**
     * Defines the ms wait time for add(job).
     */
    public static final int ADDWAIT = 500;

    private final Semaphore waitingThreads;
    private final Semaphore fullQueue;
    private volatile boolean running = false;

    private final ArrayList<Thread> threads = new ArrayList<>(MAXTHREADS);
    private final ConcurrentLinkedQueue<Job> queue = new ConcurrentLinkedQueue<>();

    /**
     * Returns the getJob Semaphore
     * 
     * @return the waitingThreads Semaphore
     */
    protected Semaphore getWaitingThreads() {
        return waitingThreads;
    }

    /**
     * Returns the add Semaphore
     * 
     * @return the fullQueue Semaphore
     */
    protected Semaphore getFullQueue() {
        return fullQueue;
    }

    /**
     * Returns the Running state of the Queue
     * 
     * @return the running state of the queue
     */
    protected final boolean isRunning() {
        return running;
    }

    /**
     * Returns the Thread ArrayList
     * @return the threads array
     */
    protected ArrayList<Thread> getThreads() {
        return threads;
    }

    /**
     *  Creates a JobQueue
     */
    public JobQueue() {
        waitingThreads = new Semaphore(0);
        fullQueue = new Semaphore(MAXJOBS, true);
        setRunning(true);
    }

    /**
     * Waits for a job to be available in the queue and returns the job or if it
     * returns null to end the thread
     *
     * @return the job to be executed or null to initiate ending of the thread
     */
    protected Job getJob() {
        try {
            waitingThreads.acquire();
        } catch (InterruptedException e) {
            handleThrowables(e);
        }
        return queue.poll();
    }

    /**
     * Adds the given job to the queue, if there is space
     *
     * @param job the job to add
     * @return true when successfully added or false when there is no space left
     * in the queue
     */
    public boolean add(Job job) {
        if (null == job) {
            return false;
        }

        try {
            if (!fullQueue.tryAcquire(1, ADDWAIT, TimeUnit.MILLISECONDS)) {
                return false;
            }
        } catch (InterruptedException e) {
            handleThrowables(e);
            return false;
        }
        queue.add(job);
        waitingThreads.release();
        return true;
    }

    /**
     * starts up the queue operation or shuts it down dependent on running
     *
     * @param running if true starts the threads or if false ends all threads.
     */
    protected final void setRunning(boolean running) {
        if (isRunning() == running) {
            return;
        }
        synchronized (this) {
            if (isRunning()) {
                this.running = false;
                waitingThreads.release(MAXTHREADS);
                threads.stream().forEach((thread) -> {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        handleThrowables(e);
                    }
                });
                waitingThreads.drainPermits();
                fullQueue.drainPermits();
                queue.clear();
            } else {
                waitingThreads.drainPermits();
                fullQueue.drainPermits();
                queue.clear();
                this.running = true;
                for (int i = 0; i < threads.size(); i++) {
                    try {
                        threads.get(i).join();
                    } catch (InterruptedException e) {
                        handleThrowables(e);
                    }
                }
                for (int i = 0; i < MAXTHREADS; i++) {
                    threads.add(i, new QueueThread(this));
                    threads.get(i).start();
                }
                fullQueue.release(MAXJOBS);
            }
        }

    }

    @Override
    protected void finalize() {
        if (isRunning()) {
            setRunning(false);
        }
        try {
            super.finalize();
        } catch (Throwable ex) {
            handleThrowables(ex);
        }
    }

    /**
     * All Throwables are handled using this function.
     * To have a different handling override this function.
     * 
     * @param ex the Throwable thrown during the execution.
     * 
     */
    protected void handleThrowables(Throwable ex) {
        System.out.println(ex.getMessage());
        ex.printStackTrace();
    }
}

final class QueueThread extends Thread {

    JobQueue queue = null;

    QueueThread(JobQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Thread: " + queue.getThreads().indexOf(this) + " starting");
        Job job = queue.getJob();
        while (null != job) {
            try {
                job.doJob();
            } catch (Throwable error) {
                job.setError(error);
            }
            queue.getFullQueue().release();
            job = queue.getJob();
        }
        System.out.println("Thread: " + queue.getThreads().indexOf(this) + " ending");
    }

}
