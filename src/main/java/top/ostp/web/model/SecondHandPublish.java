package top.ostp.web.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SecondHandPublish {
    private String id;
    private Student person;
    private Book book;
    private double price;
    private long exchange;
    private long status;
}
