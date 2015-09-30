import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest extends junit.framework.TestCase{

	@Test
	public void test() {
		PersonTest person = new Person("test");
		assert(person.getName().equals("test"));
	}

}
