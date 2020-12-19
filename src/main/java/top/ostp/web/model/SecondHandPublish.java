package top.ostp.web.model;

public class SecondHandPublish {
    private Student person;
    private Book book;

    public Student getPerson() {
        return person;
    }

    public void setPerson(Student person) {
        this.person = person;
    }

    public SecondHandPublish() {
    }

    public SecondHandPublish(Student person, Book book, double price, long exchange, long status) {
        this.person = person;
        this.book = book;
        this.price = price;
        this.exchange = exchange;
        this.status = status;
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

    private double price;
    private long exchange;
    private long status;
}
