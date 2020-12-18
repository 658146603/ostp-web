package top.ostp.web.model;

public class Student {

    private String id;
    private String name;
    private Clazz clazz;
    private String password;
    private int balance;
    private String email;

    public Student() {
    }

    public Student(String id, String name, Clazz clazz, String password, int balance, String email) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
        this.password = password;
        this.balance = balance;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Clazz getClazz() {
        return clazz;
    }

    public void setClass(Clazz clazz) {
        this.clazz = clazz;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", clazz=" + clazz +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", email='" + email + '\'' +
                '}';
    }
}
