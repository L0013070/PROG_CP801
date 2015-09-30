
public class Person {

	private String name;
	private String telephonenumber;

	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getTelephonenumber() {
		return telephonenumber;
	}

	/**
	 * @param telephonenumber the telephonenumber to set
	 */
	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}

	public Person(String name) {
		this.name = name;
	}
	
	public Person(String name, String telephonenumber) {
		this.name = name;
		this.telephonenumber = telephonenumber;
	}

	public String toString() {
		String ret = "name: " + name;
		if (null != telephonenumber ) {
			ret += " telephone number: " + telephonenumber; 
		}
		return ret;
		
	}

}
