package jobqueue;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author L00131070
 */
public class JobQueueTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test of constructor method, of class JobQueue.
	 * 
	 * @throws InterruptedException
	 *
	 */
	public void testJobQueue() throws InterruptedException {
		System.out.println("testJobQueue");
		JobQueue queue = new JobQueue();
		assertTrue(queue != null);
		assertTrue(queue.isRunning());
		Thread.sleep(500);
		System.out.println("Queue shutdown");
		queue.setRunning(false);
		assertTrue(!queue.isRunning());
		Thread.sleep(500);
		System.out.println("Queue startup");
		queue.setRunning(true);
		assertTrue(queue.isRunning());
		System.out.println("Test testConstructor successful");
	}

	/**
	 * Test of add method, of class JobQueue.
	 * 
	 * @throws InterruptedException
	 *
	 */
	public void testAdd() throws InterruptedException {
		System.out.println("testAdd");
		JobQueue queue = new JobQueue();
		if (queue.add(null)) {
			fail(" NULL JOB");
		}
		for (int i = 0; i < 40; i++) {
			Job job = new TestJob();
			boolean test = queue.add(job);
			if (!test) {
				assertTrue(!test && i == 20);
				System.out.println(i + " " + test + " waiting 1s");
				Thread.sleep(1000);
				test = queue.add(job);
			}
		}
		queue.setRunning(false);
		System.out.println("Test testAdd successful");
	}

	/**
	 * Test of setRunning method, of class JobQueue.
	 *
	 * @throws InterruptedException
	 *
	 */
	public void testSetRunning() throws InterruptedException {
		System.out.println("testSetRunning");
		JobQueue queue = new JobQueue();
		assertTrue(queue.isRunning());
		Thread.sleep(1000);
		queue.setRunning(false);
		assertTrue(!queue.isRunning());
		assertTrue(queue.getWaitingThreads().getQueueLength() == 0);
		assertTrue(queue.getFullQueue().availablePermits() == 0);
		assertTrue(queue.getThreads().isEmpty());
		assertTrue(queue.getQueue().isEmpty());
		queue.setRunning(true);
		assertTrue(queue.isRunning());
		Thread.sleep(1000);
		assertTrue(queue.getWaitingThreads().getQueueLength() == 10);
		assertTrue(queue.getFullQueue().availablePermits() == 20);
		assertTrue(queue.getThreads().size() == 10);
		assertTrue(queue.getQueue().isEmpty());

		System.out.println("Test testSetRunning successful");
	}

	/**
	 * Test of handling exceptions thrown from doJob of the job interface in
	 * class JobQueue Thread.
	 */
	public void testErrorJob() {
		System.out.println("testErrorJob");
		JobQueue queue = new JobQueue();
		queue.add(new TestErrorJob());
		queue.setRunning(false);
		System.out.println("Test testErrorJob successful");
	}

	/**
	 * Test of handleThrowables method, of class JobQueue.
	 */
	public void testHandleThrowables() {
		System.out.println("Test testHandleThrowables");
		Throwable ex = new Exception("TestException");
		JobQueue instance = new JobQueue();
		instance.handleThrowables(ex);
		System.err.flush();
		System.out.println("Test testHandleThrowables successful");
	}

}
