package top.ostp.web.model;


import java.io.Serializable;


public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String isbn;
    private String name;
    private Long price;
    private String cover;

    public Book() {
    }

    public Book(String isbn, String name, Long price, String cover) {
        this.isbn = isbn;
        this.name = name;
        this.price = price;
        this.cover = cover;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", cover='" + cover + '\'' +
                '}';
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}