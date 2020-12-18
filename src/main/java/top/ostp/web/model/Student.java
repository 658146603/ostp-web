package top.ostp.web.model;

public class Student {

  private String id;
  private String name;
  private Clazz clazz;
  private String password;
  private String balance;
  private String email;


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


  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
