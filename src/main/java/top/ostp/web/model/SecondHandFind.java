package top.ostp.web.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondHandFind {
    private String id;
    private Student person;
    private Book book;
    private double price;
    private long exchange;
    private long status;
}
