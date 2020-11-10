package int101.base;

public class Person {
    private static int nextId;
    private final int id;
    private String firstname;
    private String lastname;

    public Person(String firstname, String lastname) {
        this.id = nextId++;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public Person setFirstname(String firstname) { this.firstname = firstname; return this; }
    public Person setLastname(String lastname) { this.lastname = lastname; return this; }
    public boolean matchFirstname(String firstname) {
        if (this.firstname==null) return firstname==null;
        return this.firstname.equals(firstname);
    }
    public boolean matchLastname(String lastname) {
        if (this.lastname==null) return lastname==null;
        return this.lastname.equals(lastname);
    }
    public boolean matchName(String firstname, String lastname) {
        return matchFirstname(firstname) && matchLastname(lastname);
    }

    @Override
    public String toString() {
        return "Person[" + id + ":" + firstname + "-" + lastname + ']';
    }
    
}
