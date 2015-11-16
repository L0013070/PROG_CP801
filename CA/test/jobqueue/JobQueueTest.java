package jobqueue;

import junit.framework.TestCase;

/**
 *
 * @author L00131070
 */
public class JobQueueTest extends TestCase {

    /**
     *
     */
    public void testJobQueue() {
        System.out.println("testJobQueue");
        JobQueue queue = new JobQueue();
        assertTrue(queue != null);
        assertTrue(queue.isRunning());
        System.out.println("Queue shutdown");
        queue.setRunning(false);
        assertTrue(!queue.isRunning());
        System.out.println("Queue startup");
        queue.setRunning(true);
        assertTrue(queue.isRunning());
        System.out.println("Test testConstructor successful");
    }

    /**
     *
     */
    public void testAdd() {
        System.out.println("testAdd");
        JobQueue queue = new JobQueue();
        try {
            queue.add(null);
            fail(" NULL JOB");
        } catch (NullPointerException ex) {
        }
        for (int i = 0; i < 40; i++) {
            Job job = new TestJob();
            boolean test = queue.add(job);
            if (!test) {
                System.out.println(i + " " + test + " waiting 1s");
                try {
                    Thread.sleep(1000);
                    test = queue.add(job);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        queue.setRunning(false);
        System.out.println("Test testAdd successful");
    }

    /**
     *
     */
    public void testSetRunning() {
        System.out.println("testSetRunning");
        JobQueue queue = new JobQueue();
        assertTrue(queue.isRunning());
        queue.setRunning(true);
        assertTrue(queue.isRunning());
        queue.setRunning(false);
        assertTrue(!queue.isRunning());
        queue.setRunning(false);
        assertTrue(!queue.isRunning());
        queue.setRunning(true);
        assertTrue(queue.isRunning());
        queue.setRunning(false);
        assertTrue(!queue.isRunning());
        System.out.println("Test testSetRunning successful");
    }

    /**
     *
     */
    public void testErrorJob() {
        System.out.println("testErrorJob");
        JobQueue queue = new JobQueue();
        queue.add(new TestErrorJob());
        queue.setRunning(false);
        System.out.println("Test testErrorJob successful");
    }

}