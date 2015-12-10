
import java.util.ArrayList;

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

    @Override
    public String toString() {
        String ret = "name: " + name;
        if (null != telephonenumber) {
            ret += " telephone number: " + telephonenumber;
        }
        return ret;

    }

    public static void main(String[] args) {
        ArrayList<Person> people = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            people.add(new Person("me" + i));
        }
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long free = runtime.freeMemory();
        long max = runtime.maxMemory();
        long used = max - free;
        System.out.println("used memory: " + used + " Bytes");
        System.out.println("used memory: " + (used / Math.pow(2, 20)) + " MB");
    }

}
