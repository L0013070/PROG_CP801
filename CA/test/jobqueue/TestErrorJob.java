package jobqueue;

class TestErrorJob implements Job {

	private static int nextId = 1;
	private int id;

	TestErrorJob() {
		id = nextId++;
	}

	@Override
	public void doJob() {
		System.out.println("start errorjob: " + id);
		String test = null;
		test.length();
		System.out.println("end errorjob: " + id);
	}

	@Override
	public void setError(Throwable error) {
		System.out.println("ERROR: "+error.getMessage());
	}

}
