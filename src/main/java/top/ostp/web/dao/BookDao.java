package top.ostp.web.dao;

import top.ostp.web.model.Book;

public interface BookDao {
    int deleteByISBN(String isbn);

    int insert(Book record);

    Book selectByISBN(String isbn);

    int updateByISBN(Book record);
}