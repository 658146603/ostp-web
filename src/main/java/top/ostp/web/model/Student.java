package top.ostp.web.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    private String id;
    private String name;
    private Clazz clazz;
    private String password;
    private int balance;
    private String email;
}
