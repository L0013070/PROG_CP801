package jobqueue;

/**
 *
 * @author Dietmar
 */
public interface Job {
	
    /**
     *
     */
    public void doJob();

    /**
     *
     * @param error
     */
    public void setError(Throwable error);
	

}
