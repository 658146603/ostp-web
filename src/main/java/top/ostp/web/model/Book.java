package top.ostp.web.model;

import java.io.Serializable;

/**
 * book
 * @author 
 */
public class Book implements Serializable {
    private String isbn;

    private String name;

    private Long price;

    private byte[] cover;

    private static final long serialVersionUID = 1L;

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

    public void setPrice(Long price) {
        this.price = price;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }
}