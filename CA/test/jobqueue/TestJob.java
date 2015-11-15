package jobqueue;

class TestJob implements Job {
	
	private static int nextId = 1;
	private int id;
	
	TestJob() {
		id = nextId++;
	}

	@Override
	public void doJob() {
		try {
			System.out.println("start job: " + id);
			Thread.sleep(1000);
			System.out.println("end job: "+id);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public void setError(Throwable error) {
		// TODO Auto-generated method stub

	}

}
