package top.ostp.web.model;

import java.util.Objects;

public class SecondHandPublish {
    private String id;
    private Student person;
    private SecondHandFind second;
    private Book book;

    @Override
    public String toString() {
        return "SecondHandPublish{" +
                "id='" + id + '\'' +
                ", person=" + person +
                ", book=" + book +
                ", price=" + price +
                ", exchange=" + exchange +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecondHandPublish that = (SecondHandPublish) o;

       return Objects.equals(id, that.id);
    }

    private double price;
    private long exchange;
    private long status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getPerson() {
        return person;
    }

    public void setPerson(Student person) {
        this.person = person;
    }

    public SecondHandFind getSecond() {
        return second;
    }

    public void setSecond(SecondHandFind second) {
        this.second = second;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getExchange() {
        return exchange;
    }

    public void setExchange(long exchange) {
        this.exchange = exchange;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

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
