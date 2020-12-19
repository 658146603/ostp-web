package top.ostp.web.model;

import lombok.*;

@Data
@ToString
public class SecondHandPublish {
    private String id;
    private Student person;
    private Book book;
    private double price;
    private long exchange;
    private long status;

    public SecondHandPublish() {
    }

    public SecondHandPublish(String id, Student person, Book book, double price, long exchange, long status) {
        this.id = id;
        this.person = person;
        this.book = book;
        this.price = price;
        this.exchange = exchange;
        this.status = status;
    }
}
