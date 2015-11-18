package lab7;

public class ExceptionTest {
	
	static int value = 0; 

	static public void test1(int i) {
		value = value + i;
		if ((i & 0xFFFFFFFF) == 1000000000) {
			System.out.println("this should never be seen");
		}
	}
	
	 static private void test2(int i) throws Exception {
		value = value + i;
		if ((i & 0x01) == 1) {
			throw new Exception("test2");
		}
	}

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		for (int i = 1; i < 0xFFFFFFF; i++) {
			test1(i);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Test1: "+time+"ms");
		time = System.currentTimeMillis();
		for (int i = 1; i < 0xFFFFFFF; i++) {
			try {
				test2(i);
			} catch (Exception e) {
			}
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Test2: "+time+"ms");
	}

}
