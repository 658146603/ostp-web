package top.ostp.web.model;

public class Admin {
    String id;
    String password;
    int su;
    College college;

    public Admin(String id, String password, int su, College college) {
        this.id = id;
        this.password = password;
        this.su = su;
        this.college = college;
    }

    public Admin() {
    }

    public Admin(String uid, String password) {
        this.id = uid;
        this.password = password;
    }

    public Admin erasePassword() {
        return new Admin(id, "", su, college);
    }

    public String getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public int getSu() {
        return this.su;
    }

    public College getCollege() {
        return this.college;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSu(int su) {
        this.su = su;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        if (su != admin.su) return false;
        if (!id.equals(admin.id)) return false;
        if (!password.equals(admin.password)) return false;
        return college.equals(admin.college);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + su;
        result = 31 * result + college.hashCode();
        return result;
    }

    public String toString() {
        return "Admin(id=" + this.getId() + ", password=" + this.getPassword() + ", su=" + this.getSu() + ", college=" + this.getCollege() + ")";
    }
}
