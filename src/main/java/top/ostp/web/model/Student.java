package top.ostp.web.model;

public class Student {
    private String id;
    private String name;
    private Clazz clazz;
    private String password;
    private int balance;
    private String email;

    public Student(String id, String name, Clazz clazz, String password, int balance, String email) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
        this.password = password;
        this.balance = balance;
        this.email = email;
    }

    public Student() {
    }

    public Student(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Student erasePassword() {
        return new Student(id, name, clazz, "", balance, email);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clazz getClazz() {
        return this.clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "Student(id=" + this.getId() + ", name=" + this.getName() + ", clazz=" + this.getClazz() + ", password=" + this.getPassword() + ", balance=" + this.getBalance() + ", email=" + this.getEmail() + ")";
    }
}
