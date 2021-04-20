package top.ostp.web.model;

import java.util.Objects;

public class SecondHandFind {
    private String id;
    private Student person;
    private SecondHandPublish second;
    private Book book;
    private double price;
    private long exchange;
    private long status;

    public SecondHandFind(String id, Student person, Book book, double price, long exchange, long status) {
        this.id = id;
        this.person = person;
        this.book = book;
        this.price = price;
        this.exchange = exchange;
        this.status = status;
    }

    public SecondHandFind() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondHandFind that = (SecondHandFind) o;
        return Objects.equals(id, that.id);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getPerson() {
        return this.person;
    }

    public void setPerson(Student person) {
        this.person = person;
    }

    public SecondHandPublish getSecond() {
        return second;
    }

    public void setSecond(SecondHandPublish second) {
        this.second = second;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getExchange() {
        return this.exchange;
    }

    public void setExchange(long exchange) {
        this.exchange = exchange;
    }

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }


    public String toString() {
        return "SecondHandFind(id=" + this.getId() + ", person=" + this.getPerson() + ", book=" + this.getBook() + ", price=" + this.getPrice() + ", exchange=" + this.getExchange() + ", status=" + this.getStatus() + ")";
    }
}
