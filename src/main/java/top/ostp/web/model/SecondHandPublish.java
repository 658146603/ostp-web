package top.ostp.web.model;

public class SecondHandPublish {
    private String id;
    private Student person;
    private Book book;
    private double price;
    private long exchange;
    private long status;

    public SecondHandPublish() {
    }

    public SecondHandPublish(Student person, Book book, double price, long exchange, long status) {
        this.person = person;
        this.book = book;
        this.price = price;
        this.exchange = exchange;
        this.status = status;
    }

    public SecondHandPublish(String id, Student person, Book book, double price, long exchange, long status) {
        this.id = id;
        this.person = person;
        this.book = book;
        this.price = price;
        this.exchange = exchange;
        this.status = status;
    }

    public Student getPerson() {
        return person;
    }

    public void setPerson(Student person) {
        this.person = person;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
