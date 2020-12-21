package top.ostp.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    String id;
    String password;
    int su;
    College college;

    public Admin erasePassword() {
        return new Admin(id, "", su, college);
    }
}
