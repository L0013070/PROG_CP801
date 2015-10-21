package jobqueue;

public interface Job {
	
	public void doJob();
	public void setError(Exception exeption);
	

}
