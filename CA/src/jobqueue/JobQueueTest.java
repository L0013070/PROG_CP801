package jobqueue;

import junit.framework.TestCase;

public class JobQueueTest extends TestCase {

	public void testJobQueue() {
		JobQueue queue = new JobQueue();
		assertTrue(queue != null);
	}

	public void testAdd() {
		JobQueue queue = new JobQueue();
		try {
			queue.add(null);
			fail(" NULL JOB");
		} catch (Exception ex) {
		}
		for (int i = 0; i < 30; i++) {
			boolean test = queue.add(new TestJob());
			if (i > 10) {
				assertTrue(queue.waitingThreads.availablePermits() > 0);
			}
			// System.out.println(i+" "+test);
		}
		boolean test = queue.add(new TestJob());
		if (test) {
			fail("semaphore off");
		}
	}

}
