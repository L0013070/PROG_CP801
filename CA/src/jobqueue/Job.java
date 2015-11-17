package jobqueue;

/**
 *  The Job Interface defines the functions to be implemented to use the JobQueue.
 * 
 * @author Dietmar
 */
public interface Job {
	
    /**
     * The job executed by the JobQueue
     */
    public void doJob();

    /**
     * All errors and exceptions thrown by doJob() during the execution are sent here for processing.
     * 
     * @param error the error/exception thrown by doJob()
     */
    public void setError(Throwable error);
	

}
